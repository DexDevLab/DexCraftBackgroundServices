package net.dex.dexcraft.backgroundservices.services;


import java.io.File;
import java.io.IOException;
import java.util.List;
import static net.dex.dexcraft.backgroundservices.BackgroundServices.component;
import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import net.dex.dexcraft.backgroundservices.commons.dto.SessionDTO;
import net.dex.dexcraft.backgroundservices.commons.tools.DexCraftFiles;
import org.apache.commons.io.FileUtils;


/**
 *This Service monitors if the user changed the username
 * in the internal launcher.
 */
public class WatchdogService
{
  private static String currentUser = SessionDTO.getSessionUser();

  /**
   * Performs the program monitoring.
   */
  public static void watch()
  {
    File launcherAsset = new File (DexCraftFiles.launcherFolder + "/" + component + "/.minecraft/shig.inima");
    int loop = 0;
    while (loop == 0)
    {
      try
      {
        List<String> readList = FileUtils.readLines(launcherAsset, "UTF-8");
        readList.forEach((item) ->
        {
          String checkPrefix = "username.lastused: ";
          if ( (item.contains(checkPrefix)) && (!item.equals(checkPrefix + currentUser)) )
          {
            logger.log("***ERRO***", "NOME DE USUÁRIO ALTERADO DENTRO DO SHIGINIMA! O PROCESSO SERÁ FECHADO.");
            terminate(component + ".exe");
            alerts.tryAgain();
          }
          checkPrefix = "auto.name: ";
          if ( (item.contains(checkPrefix)) && (!item.equals(checkPrefix + currentUser)) )
          {
            logger.log("***ERRO***", "NOME DE USUÁRIO ALTERADO DENTRO DO SHIGINIMA! O PROCESSO SERÁ FECHADO.");
            terminate(component + ".exe");
            alerts.tryAgain();
          }
        });
      }
      catch (IOException ex)
      {
        terminate(component + ".exe");
        alerts.exceptionHandler(ex, "EXCEÇÃO em WatchdogService.watch()");
//        Close.withErrors();
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
    }
    catch (IOException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO em WatchdogService.terminate()");
//      Close.withErrors();
    }
  }

}
