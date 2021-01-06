package net.dex.dexcraft.backgroundservices.commons.check;


import net.dex.dexcraft.backgroundservices.commons.dto.SessionDTO;


/**
 * Check if there is another instance of the program
 * currently running.
 */
public class PreventSecondInstance
{
  /**
   * Check if there is another instance of the program
   * currently running.
   * @param instanceName the name of the instance.
   * @return if the instance is running already (true) or not(false)
   */
  public static boolean isThereAnotherInstance(String instanceName)
  {
    boolean instanceStatus = false;
    switch (instanceName)
    {
      case "Init":
        instanceStatus = SessionDTO.getDexCraftLauncherInitStatus();
        if (!instanceStatus)
        {
          SessionDTO.setDexCraftLauncherInitInstance(true);
          instanceStatus = true;
        }
        break;
      case "Client":
        instanceStatus = SessionDTO.getDexCraftLauncherClientStatus();
        if (!instanceStatus)
        {
          SessionDTO.setDexCraftLauncherClientInstance(true);
        }
        break;
      case "DCBS":
        instanceStatus = SessionDTO.getDexCraftBackgroundServicesStatus();
        if (!instanceStatus)
        {
          SessionDTO.setDexCraftBackgroundServicesInstance(true);
        }
        break;
      default:
        break;
    }
    return instanceStatus;
  }

}
