package net.dex.dexcraft.backgroundservices.commons.tools;


import java.io.File;
import javafx.application.Platform;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import net.dex.dexcraft.backgroundservices.commons.dto.SessionDTO;

/**
 * Class for closing program properly,
 * removing temporary folders
 */
public class Close
{

  private static Clean clean = new Clean();

  /**
   * Closes DexCraft Launcher Init. Keeps the instance
   * lock, the log lock and the run folder, needed
   * to the Launcher Client and the DCBS.
   */
  public static void init()
  {
    logger.log("INFO", "Fechando DexCraft Launcher Init...");
    clean.excluir(DexCraftFiles.adminCheck, false);
    clean.excluir(DexCraftFiles.tempFolder, true);
    SessionDTO.setDexCraftLauncherClientInstance(false);
    SessionDTO.setDexCraftBackgroundServicesInstance(false);
    Platform.exit();
  }

  /**
   * Closes DexCraft Launcher Client.
   */
  public static void client()
  {
    logger.log("INFO", "Fechando DexCraft Launcher Client...");
    clean.excluir(DexCraftFiles.adminCheck, false);
    clean.excluir(DexCraftFiles.tempFolder, true);
    clean.excluir(DexCraftFiles.playerLock, false);
    SessionDTO.setDexCraftLauncherInitInstance(true);
    SessionDTO.setDexCraftLauncherClientInstance(true);
    SessionDTO.setDexCraftBackgroundServicesInstance(false);
    Platform.exit();
  }

  /**
   * Closes DexCraft Background Services.
   */
  public static void backgroundServices()
  {
    logger.log("INFO", "Fechando DexCraft Background Services...");
    clean.excluir(DexCraftFiles.adminCheck, false);
    clean.excluir(DexCraftFiles.tempFolder, true);
    clean.excluir(DexCraftFiles.logLock, false);
    SessionDTO.setDexCraftLauncherInitInstance(false);
    SessionDTO.setDexCraftLauncherClientInstance(false);
    SessionDTO.setDexCraftBackgroundServicesInstance(false);
    SessionDTO.setSessionPassword("null");
    System.exit(0);
  }

  /**
   * Closes program after exceptions or critical errors.
   */
  public static void withErrors()
  {
    logger.log("INFO", "PROGRAMA FECHADO COM ERROS!");
    clean.excluir(DexCraftFiles.adminCheck, false);
    clean.excluir(DexCraftFiles.tempFolder, true);
    clean.excluir(DexCraftFiles.logLock, false);
    SessionDTO.setDexCraftLauncherInitInstance(false);
    SessionDTO.setDexCraftLauncherClientInstance(false);
    SessionDTO.setDexCraftBackgroundServicesInstance(false);
    SessionDTO.setSessionPassword("null");
  }

  /**
   * Closes normally the program, under user's call.
   */
  public static void quit()
  {
    logger.log("INFO", "Fechando...");
    clean.excluir(DexCraftFiles.adminCheck, false);
    clean.excluir(DexCraftFiles.tempFolder, true);
    clean.excluir(DexCraftFiles.logLock, false);
    SessionDTO.setDexCraftLauncherInitInstance(false);
    SessionDTO.setDexCraftLauncherClientInstance(false);
    SessionDTO.setDexCraftBackgroundServicesInstance(false);
    SessionDTO.setSessionPassword("null");
  }


  /**
   * Since its common the FileIO Class provide an
   * exception which closes the program,<br>
   * in order to do not prevent program running
   * in absence of some file just for verification
   * the method was overwriten.
   */
  private static class Clean extends FileIO
  {
    @Override
    public void excluir(File source, boolean includeParentDir)
    {
      if (source.exists())
      {
        super.excluir(source, includeParentDir);
      }
      else
      {
        logger.log("INFO", "SOURCE \"" + source.toString() + "\" não foi encontrado.");
      }
    }
  }

}
