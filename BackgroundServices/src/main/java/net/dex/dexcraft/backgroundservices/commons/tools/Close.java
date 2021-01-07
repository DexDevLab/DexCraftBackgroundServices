package net.dex.dexcraft.backgroundservices.commons.tools;


import javafx.application.Platform;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import net.dex.dexcraft.backgroundservices.commons.dto.SessionDTO;
import org.apache.commons.io.FileUtils;

/**
 * Class for closing program properly,
 * removing temporary folders
 */
public class Close
{

  /**
   * Closes DexCraft Launcher Init. Keeps the instance
   * lock, the log lock and the run folder, needed
   * to the Launcher Client and the DCBS.
   */
  public static void init()
  {
    logger.log("INFO", "Fechando DexCraft Launcher Init...");
    FileUtils.deleteQuietly(DexCraftFiles.adminCheck);
    FileUtils.deleteQuietly(DexCraftFiles.tempFolder);
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
    FileUtils.deleteQuietly(DexCraftFiles.adminCheck);
    FileUtils.deleteQuietly(DexCraftFiles.tempFolder);
    FileUtils.deleteQuietly(DexCraftFiles.playerLock);
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
    FileUtils.deleteQuietly(DexCraftFiles.adminCheck);
    FileUtils.deleteQuietly(DexCraftFiles.tempFolder);
    FileUtils.deleteQuietly(DexCraftFiles.logLock);
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
    FileUtils.deleteQuietly(DexCraftFiles.adminCheck);
    FileUtils.deleteQuietly(DexCraftFiles.tempFolder);
    FileUtils.deleteQuietly(DexCraftFiles.logLock);
    SessionDTO.setDexCraftLauncherInitInstance(false);
    SessionDTO.setDexCraftLauncherClientInstance(false);
    SessionDTO.setDexCraftBackgroundServicesInstance(false);
    SessionDTO.setSessionPassword("null");
    System.exit(0);
  }

  /**
   * Closes normally the program, under user's call.
   */
  public static void quit()
  {
    logger.log("INFO", "Fechando...");
    FileUtils.deleteQuietly(DexCraftFiles.adminCheck);
    FileUtils.deleteQuietly(DexCraftFiles.tempFolder);
    FileUtils.deleteQuietly(DexCraftFiles.logLock);
    SessionDTO.setDexCraftLauncherInitInstance(false);
    SessionDTO.setDexCraftLauncherClientInstance(false);
    SessionDTO.setDexCraftBackgroundServicesInstance(false);
    SessionDTO.setSessionPassword("null");
  }

}