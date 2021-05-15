package net.dex.dexcraft.backgroundservices;


import com.sun.javafx.application.PlatformImpl;
import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import net.dex.dexcraft.backgroundservices.commons.Commons;
import net.dex.dexcraft.backgroundservices.commons.dto.SessionDTO;
import net.dex.dexcraft.backgroundservices.commons.dto.SystemDTO;
import net.dex.dexcraft.backgroundservices.commons.dto.UrlsDTO;
import net.dex.dexcraft.backgroundservices.commons.tools.DexCraftFiles;
import net.dex.dexcraft.backgroundservices.commons.tools.ErrorAlerts;
import net.dex.dexcraft.backgroundservices.commons.tools.Logger;
import net.dex.dexcraft.backgroundservices.services.BackupService;
import net.dex.dexcraft.backgroundservices.services.Validate;
import net.dex.dexcraft.backgroundservices.services.WatchdogService;


/**
* @author Dex
* @since 04/04/2019
* @version v7.3.0-210116-353
*
* Application for backup and syncronization by time period.
*/
public class BackgroundServices
{
  public static ErrorAlerts alerts = new ErrorAlerts();
  public static Logger logger = new Logger();

  public static boolean performLocalBackups = false;
  public static boolean backupSinglePlayerMaps = true;
  public static int dcbsSyncTime = 999;

  public static List<String> bkpList;

  public static SystemTray tray;
  public static PopupMenu popup;
  public static TrayIcon trayIcon;
  public static BufferedImage img;

  public static String component = "";


  public static void main(String[] args)
  {
    PlatformImpl.startup(()->{});

    //Icon set for alerts's window
    alerts.setImage(new Image(BackgroundServices.class.getResourceAsStream("icon1.jpg")));

    //DexCraft Commons alerts binding
    Commons.setErrorAlerts(alerts);

    //Logger settings
    logger.setLogLock(DexCraftFiles.logLock);
    logger.setMessageFormat("yyyy/MM/dd HH:mm:ss");
    logger.setLogNameFormat("yyyy-MM-dd--HH.mm.ss");
    logger.setLogDir(DexCraftFiles.logFolder);
    //DexCraft Commons logger binding
    Commons.setLogger(logger);

    logger.log("INFO", "Inicializando DexCraft Background Services...");

    //Validates Instance
    Validate.instance("DCBS");

    // Validates component name
    switch (SessionDTO.getLastServer())
    {
      case "0":
        component = "dc";
        break;
      case "1":
        component = "dcpx";
        break;
      case "2":
        component = "dcvn";
        break;
      case "3":
        component = "dcb";
        break;
      default:
        break;
    }

    //Opens Shiginima Launcher
    try
    {
      logger.log("INFO", "Abrindo Shiginima Launcher...");
      new ProcessBuilder("cmd", "/c", component + ".bat").directory(DexCraftFiles.runtimeFolder).start();
      logger.log("INFO", "Launcher Inicializado");
    }
    catch (IOException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO em logAndChangeTooltip(String)");
      // Close.withErrors();
    }

    logger.log("INFO", "Inicializando...");

    // Run Swing UI - Tray Icon
    Runnable runner = () ->
    {
      if (SystemTray.isSupported())
      {
        try
        {
          tray = SystemTray.getSystemTray();
          img = ImageIO.read(BackgroundServices.class.getResource("icon1-2.jpg"));
          popup = new PopupMenu();
          trayIcon = new TrayIcon(img, "DexCraft Background Services", popup);
          try
          {
            tray.add(trayIcon);
          }
          catch (AWTException e)
          {
            alerts.exceptionHandler(e, "EXCEÇÃO em BackgroundServices.start(Stage)");
            // Close.withErrors();
          }
        }
        catch (IOException ex)
        {
          alerts.exceptionHandler(ex, "EXCEÇÃO em BackgroundServices.start(Stage)");
          // Close.withErrors();
        }
      }
      else
      {
        logger.log("***ERRO***", "NÃO HÁ SUPORTE PARA A APLICAÇÃO NA BANDEJA DO SISTEMA.");
      }
    };
    EventQueue.invokeLater(runner);

    logAndChangeTooltip("Iniciando Watchdog...");

    Thread watchdog = new Thread(()->
    {
      PlatformImpl.startup(()->{});
      WatchdogService.watch();
    });
    watchdog.start();

    logAndChangeTooltip("Watchdog Iniciado");

    //Preparing DCBS assets
    UrlsDTO.parseURLs();
    SystemDTO.parseSystemAssets();
    performLocalBackups = ( (SessionDTO.isDCBSDisabled() || SessionDTO.isOfflineModeOn()) );
    if (performLocalBackups)
    {
      logger.log("INFO", "Modo Offline está ativado ou o DCBS foi desativado. Criando rotinas de backup local...");
    }
    backupSinglePlayerMaps = SessionDTO.isBackupSingleplayerMapsEnabled();
    dcbsSyncTime = Integer.parseInt(SystemDTO.getDCBSSyncTime());
    if (backupSinglePlayerMaps)
    {
      bkpList = SystemDTO.getDCLBkpDirectivesFull();
    }
    else
    {
      bkpList = SystemDTO.getDCLBkpDirectivesPartial();
    }

    logAndChangeTooltip("Iniciando Sincronização agendada...");

    Thread backupThread = new Thread(()->
    {
      PlatformImpl.startup(()->{});
      BackupService.backup(performLocalBackups);
    });
    backupThread.start();

    logAndChangeTooltip("Sincronização Iniciada");
  }

  /**
   *  Faz log e altera a Tooltip do ícone da bandeja, de maneira
   *  informativa.
   *
   *  @param text - o texto que deve ser informado no log e exibido
   *  na Tooltip.
   */
  public static void logAndChangeTooltip(String text)
  {
    Runnable runner = () ->
    {
      tray.remove(trayIcon);
      try
      {
        tray.add(trayIcon);
        trayIcon.setToolTip(text);
      }
      catch (AWTException ex)
      {
        alerts.exceptionHandler(ex, "EXCEÇÃO em BackgroundServices.logAndChangeTooltip(String)");
        // Close.withErrors();
      }
    };
    EventQueue.invokeLater(runner);
    logger.log("INFO", text);
    try
    {
      Thread.sleep(500);
    }
    catch (InterruptedException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO em BackgroundServices.logAndChangeTooltip(String)");
      // Close.withErrors();
    }
  }
}
