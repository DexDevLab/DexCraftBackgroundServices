package net.dex.dexcraft.backgroundservices.services;


import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import net.dex.dexcraft.backgroundservices.commons.check.AdminExecution;
import net.dex.dexcraft.backgroundservices.commons.check.PreventSecondInstance;
import net.dex.dexcraft.backgroundservices.commons.dto.SessionDTO;
import net.dex.dexcraft.backgroundservices.commons.tools.DexCraftFiles;


/**
 * Class for program validations
 */
public class Validate
{
  public static String bgImageRandomizerCaller = "null";
  public static boolean isPingServiceOnLoginRunning = false;
  public static boolean isPingServiceOnMainWindowRunning = false;
  public static boolean isJavaVerified = false;

  public static String GAME_CACHE_PASSWORD = "DEXCRAFTCACHE";

  /**
  * Validates Launcher instance, preventing users from<br>
  * opening Launcher without Init.
  * @param instanceName the name os instance (Init, Client
  * or DCBS).
  */
  public static void instance(String instanceName)
  {
    // Read the session assets from JSON properties file
    SessionDTO.parseSessionAssets();
    //Check if program is running as Admin
    AdminExecution.AdminExecution();
    boolean isInstanceInvalid = true;
    switch (instanceName)
    {
      case "Init":
        if (DexCraftFiles.logLock.exists())
        {
          SessionDTO.setDexCraftLauncherInitInstance(true);
          isInstanceInvalid = true;
        }
        else
        {
          isInstanceInvalid = false;
        }
        break;
      case "Client":
        if (PreventSecondInstance.isThereAnotherInstance("Init"))
        {
          isInstanceInvalid = PreventSecondInstance.isThereAnotherInstance("Client");
        }
        break;
      case "DCBS":
        if ( (PreventSecondInstance.isThereAnotherInstance("Init"))
              && PreventSecondInstance.isThereAnotherInstance("Client") )
        {
          isInstanceInvalid = PreventSecondInstance.isThereAnotherInstance("DCBS");
        }
        break;
      default:
        break;
    }
    if (isInstanceInvalid)
    {
      System.out.println("Foi encontrada uma instância do programa na memória.");
      alerts.doubleInstance();
    }
    else
    {
      System.out.println("Não foi encontrada uma instância do programa na memória.");
    }
  }

}
