package net.dex.dexcraft.backgroundservices.services;


import com.sun.javafx.application.PlatformImpl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.dex.dexcraft.backgroundservices.BackgroundServices;
import static net.dex.dexcraft.backgroundservices.BackgroundServices.component;
import static net.dex.dexcraft.backgroundservices.BackgroundServices.logAndChangeTooltip;
import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import net.dex.dexcraft.backgroundservices.commons.dao.JsonDAO;
import net.dex.dexcraft.backgroundservices.commons.dto.FtpDTO;
import net.dex.dexcraft.backgroundservices.commons.dto.SessionDTO;
import net.dex.dexcraft.backgroundservices.commons.dto.SystemDTO;
import net.dex.dexcraft.backgroundservices.commons.tools.Close;
import net.dex.dexcraft.backgroundservices.commons.tools.Crypto;
import net.dex.dexcraft.backgroundservices.commons.tools.DexCraftFiles;
import net.dex.dexcraft.backgroundservices.commons.tools.FileIO;
import net.dex.dexcraft.backgroundservices.commons.tools.FtpUtils;
import net.dex.dexcraft.backgroundservices.commons.tools.ZipUtils;
import static net.dex.dexcraft.backgroundservices.services.Validate.GAME_CACHE_PASSWORD;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;


/**
 *This Service monitors if the user changed the username
 * in the internal launcher.
 */
public class BackupService
{
  public static FtpUtils ftp = new FtpUtils();
  private static List<String> taskList = new ArrayList<>();
  public static String gameClient = "";
  public static int SHIGINIMA_WAIT_SECS = 25;
  public static File shigLauncher = new File(DexCraftFiles.runtimeFolder + "/" + BackgroundServices.component + ".exe");
  public static File shigLauncherOld = new File(DexCraftFiles.runtimeFolder + "/" + BackgroundServices.component + ".old");
  public static File runtimeFile = new File(DexCraftFiles.launcherFolder + "/" + BackgroundServices.component + "/.minecraft/launcher_profiles.json");
  public static String gameLock = "";
  public static File gameLockFile;
  public static File gameLockFileOld;
  public static File tempSyncProps = new File (DexCraftFiles.tempFTPFolder + "/syncproperties.json");
  public static boolean bkpDone = false;
  public static boolean closeRequested = false;


  /**
   * Performs the periodical backup of the user data.
   * @param performLocalBackups true if DCBS is disabled or
   * Offline Mode is on
   */
  public static void backup(boolean performLocalBackups)
  {
    try
    {
      switch (component)
      {
        case "dc":
          gameClient = "DexCraft Factions";
          break;
        case "dcpx":
          gameClient = "DexCraft Pixelmon";
          break;
        case "dcvn":
          gameClient = "DexCraft Vanilla";
          break;
        case "dcb":
          gameClient = "DexCraft Beta";
          break;
        default:
          break;
      }
      logAndChangeTooltip("Verificando regras de sincronização...");
      FtpDTO.parseFTPAssets();
      SessionDTO.parseSessionAssets();
      String jsonString = FileUtils.readFileToString(runtimeFile, "UTF-8");
      JSONObject obj = new JSONObject (jsonString);
      JSONObject parentObj = obj.getJSONObject("profiles");
      JSONObject childObj = parentObj.getJSONObject(gameClient);
      gameLock = childObj.getString("lastVersionId");
      gameLockFile = new File(DexCraftFiles.launcherFolder + "/" + component + "/.minecraft/versions" + "/" + gameLock + "/" + gameLock + ".jar");
      gameLockFileOld = new File(DexCraftFiles.launcherFolder + "/" + component + "/.minecraft/versions" + "/" + gameLock + "/" + gameLock + ".old");
      Thread persistance = new Thread(()->
      {
        PlatformImpl.startup(()->{});
        logAndChangeTooltip("Iniciando Serviço de Persistência...");
        try
        {
         int a = 0;
         while (a == 0)
         {
           if ((!isBusy()) && (bkpDone))
           {
             closeRequested = true;
             ftp.disconnect();
             Close.backgroundServices();
           }
           else
           {
             Thread.sleep(5000);
           }
         }
        }
        catch(InterruptedException   ex)
        {
          alerts.exceptionHandler(ex, "EXCEÇÃO em BackupService.backup(boolean)");
          Close.withErrors();
        }
      });
      persistance.start();
      for (int wait = 0; wait < SHIGINIMA_WAIT_SECS; wait++)
      {
        logger.log("INFO", "Verificando se o Shiginima está em execução...");
        if (isBusy()) { wait = SHIGINIMA_WAIT_SECS; }
        Thread.sleep(1000);
      }
      if (!isBusy())
      {
        logger.log("***ERRO***", "ERRO CRÍTICO: SHIGINIMA LAUNCHER NÃO FOI ABERTO DENTRO DO TEMPO HÁBIL");
        alerts.tryAgain();
      }
      else
      {
        logger.log("INFO", "Shiginima executado.");
        while (!closeRequested)
        {
          bkpDone = true;
          startCountdown(Integer.parseInt(SystemDTO.getDCBSSyncTime()));
          bkpDone = false;
          prepareBackup(performLocalBackups,SessionDTO.getSessionUser(), component);
          checkRemoteSyncProps(performLocalBackups);
          performBackup(performLocalBackups);
        }
      }
    }
    catch (IOException | InterruptedException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO em BackupService.backup(boolean)");
      Close.withErrors();
    }
  }


  /**
   * Verifies if the Launcher isn't busy, as running internal
   * launcher or Minecraft.
   * @return the idle status: true if the game is running.
   */
  public static boolean isBusy()
  {
    if (shigLauncher.renameTo(shigLauncherOld))
    {
      logger.log("INFO", "Shiginima NÃO está em execução.");
      shigLauncherOld.renameTo(shigLauncher);
      if (gameLockFile.renameTo(gameLockFileOld))
      {
        logger.log("INFO", "Minecraft NÃO está em execução.");
        gameLockFileOld.renameTo(gameLockFile);
        return false;
      }
    }
    return true;
  }


   /**
   * Performs the backup preparation to change the logged account.
   * @param isLocal true if the backup is local (in case of
   * DCBS is disabled or Offline Mode is on),<br>
   * false otherwise.
   * @param user the username needed to backup
   * @param game the client game that need to be backuped
   */
  public static void prepareBackup(boolean isLocal, String user, String game)
  {
    logAndChangeTooltip("Preparando backup...");
    if (SessionDTO.isBackupSingleplayerMapsEnabled())
    {
      taskList = SystemDTO.getDCLBkpDirectivesFull();
    }
    else
    {
      taskList = SystemDTO.getDCLBkpDirectivesPartial();
    }
    File backupRootFolder;
    String password;
    if (isLocal)
    {
      backupRootFolder = new File(DexCraftFiles.gameCache.toString());
      password = GAME_CACHE_PASSWORD;
    }
    else
    {
      backupRootFolder = new File(DexCraftFiles.tempFTPFolder.toString());
      password = Crypto.decrypt(SessionDTO.getSessionPassword());
    }
    FileIO fio = new FileIO();
    taskList.forEach((item)->
    {
      if (item.lastIndexOf(game) != -1)
      {
        String fileSrc = item.replace("\"", "");
        logAndChangeTooltip("Preparando backup... " + (taskList.indexOf(item)+1) + " / " + taskList.size());
        File destination = new File (backupRootFolder + "/" + user + "/" + fileSrc);
        File src = new File(DexCraftFiles.launcherFolder.toString() + "/" + fileSrc);
        if (src.exists())
        {
          fio.copiar(src, destination);
        }
      }
    });
    logAndChangeTooltip("Preparando backup... " + taskList.size() + " / " + taskList.size());
    logAndChangeTooltip("Preparando backup...");
    ZipUtils.compressWithPassword(new File (backupRootFolder + "/" + user + "/" + game), password);
    fio.excluir(new File (backupRootFolder + "/" + user + "/" + game), true);
  }


  /**
   * Performs FTP Connection.
   */
  public static void connectFtp()
  {
    ftp.setAddress(FtpDTO.getFtpAddress());
    ftp.setPort(FtpDTO.getFtpPort());
    ftp.setUser(FtpDTO.getFtpUser());
    ftp.setPassword(FtpDTO.getFtpPassword());
    ftp.setWorkingDir(FtpDTO.getFtpWorkingDir());
    ftp.connect();
  }


  /**
   * Check for the remote sync properties file.<br>
   * If the user folder on FTP server does not
   * contains the syncproperties.json,<br>
   * it will be created and uploaded.
   * @param isLocal true if the backup is local (in case of
   * DCBS is disabled or Offline Mode is on),<br>
   * false otherwise.
   */
  public static void checkRemoteSyncProps(boolean isLocal)
  {
    if ( (!tempSyncProps.exists()) && (!isLocal) )
    {
      logAndChangeTooltip("Verificando assets de backup...");
      File check = new File(FtpDTO.getFtpWorkingDir() + "/" + SessionDTO.getSessionUser() + "/syncproperties.json");
      connectFtp();
      if (ftp.fileExists(check))
      {
        Thread downloadSyncProps = new Thread(()->
        {
          ftp.downloadFileWithProgress(SessionDTO.getSessionUser(), tempSyncProps);
        });
        downloadSyncProps.start();
        while (downloadSyncProps.isAlive())
        {
          logAndChangeTooltip("Baixando assets...");
          try
          {
            Thread.sleep(1000);
          }
          catch (InterruptedException ex)
          {
            // Thread interruption ignored
          }
        }
        ftp.disconnect();
        logAndChangeTooltip("Baixando assets...concluído");
      }
      else
      {
        try
        {
          FileUtils.copyFile(DexCraftFiles.syncPropsRoot, tempSyncProps);
        }
        catch (IOException ex)
        {
          alerts.exceptionHandler(ex, "EXCEÇÃO em BackupService.checkRemoteSyncProps(boolean)");
          Close.withErrors();
        }
      }
    }
    else
    {
      File localSyncProps = new File(DexCraftFiles.gameCache + "/" + SessionDTO.getSessionUser() + "/syncproperties.json");
      if ( (isLocal) && (!localSyncProps.exists()) )
      {
        try
        {
          FileUtils.copyFile(DexCraftFiles.syncPropsRoot, localSyncProps);
        }
        catch (IOException ex)
        {
          alerts.exceptionHandler(ex, "EXCEÇÃO em BackupService.checkRemoteSyncProps(boolean)");
          Close.withErrors();
        }
      }
    }
  }


  /**
   * Perform the backup of the data.
   * @param performLocalBackups true if the backup will be only
   * in local account, false otherwise.
   */
  public static void performBackup(boolean performLocalBackups)
  {
    logAndChangeTooltip("Realizando backup...");
    JsonDAO json = new JsonDAO();
    long timestamp = System.currentTimeMillis();
    File localSyncProps = new File(DexCraftFiles.gameCache + "/" + SessionDTO.getSessionUser() + "/syncproperties.json");
    if (performLocalBackups)
    {
      json.editValue(localSyncProps, component.toUpperCase(), "BackupTimestamp", Long.toString(timestamp));
    }
    else
    {
      File backupZip = new File (DexCraftFiles.tempFTPFolder + "/" + SessionDTO.getSessionUser() + "/" + component + ".7z");
      connectFtp();
      ftp.checkFolder(FtpDTO.getFtpWorkingDir() + "/" + SessionDTO.getSessionUser());
      Thread uploadBackupZip = new Thread(()->
      {
        ftp.uploadFileWithProgress(SessionDTO.getSessionUser(), backupZip.toString());
      });
      uploadBackupZip.start();
      while (uploadBackupZip.isAlive())
      {
        logAndChangeTooltip("Enviando backup... " + ftp.getTimeEstimatedMsg());
        try
        {
          Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
          alerts.exceptionHandler(ex, "EXCEÇÃO em BackupService.performBackup(boolean)");
          Close.withErrors();
        }
      }
      logAndChangeTooltip("Enviando backup... 100% concluído");
      json.editValue(tempSyncProps, component.toUpperCase(), "BackupTimestamp", Long.toString(timestamp));
      Thread uploadSyncProps = new Thread(()->
      {
        ftp.uploadFileWithProgress(SessionDTO.getSessionUser(), tempSyncProps.toString());
      });
      uploadSyncProps.start();
      logAndChangeTooltip("Enviando assets...");
      while (uploadSyncProps.isAlive())
      {
        logAndChangeTooltip("Enviando assets...");
        try
        {
          Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
          alerts.exceptionHandler(ex, "EXCEÇÃO em BackupService.performBackup(boolean)");
          Close.withErrors();
        }
      }
      ftp.disconnect();
      logAndChangeTooltip("Enviando assets...concluído");
    }
    logAndChangeTooltip("Backup concluído");
  }

   /**
   *  A countdown with logging and tooltip change.<br>
   *  Also verifies if the game is open.
   *  @param minutes - quantity of minutes to count
   */
  public static void startCountdown(int minutes)
  {
    long end = minutes * 60000;
    long start = 0;
    short a = 0;
    while (start <= end)
    {
      try
      {
        if (a == 0 )
        {
          logger.log("INFO", "Verificando se o DexCraft está em execução...");
          a++;
        }
        int mins = 0;
        if ((end - start) >= 60000) { mins = Math.round((end - start) / 60000); }
        int secs = Math.round(((end - start) - (mins * 60000)) / 1000);
        String minsstr = String.format("%02d", mins);
        String secsstr = String.format("%02d", secs);
        String timer = (minsstr + ":" + secsstr);
        if (!closeRequested)
        {
          logAndChangeTooltip("Sincronizando em " + timer);
          start+= 1000;
        }
        Thread.sleep(1000);
      }
      catch (InterruptedException ex)
      {
        alerts.exceptionHandler(ex, "EXCEÇÃO em BackupService.startCountdown(int)");
        Close.withErrors();
      }
    }
  }

  /**
   * Terminates a program.
   * @param program the executable program name
   */
  public static void terminate(String program)
  {
    try
    {
      new ProcessBuilder("cmd", "/c", "taskkill /im " + program + " /f /t").directory(DexCraftFiles.runtimeFolder).start();
      System.out.println("PROCESSO " + program + " FINALIZADO");
    }
    catch (IOException ex)
    {
      System.out.println("ERRO - " + ex.getMessage());
    }
  }

}
