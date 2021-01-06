package net.dex.dexcraft.backgroundservices.commons.dto;


import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import net.dex.dexcraft.backgroundservices.commons.dao.JsonDAO;
import net.dex.dexcraft.backgroundservices.commons.tools.DexCraftFiles;


/**
 * DTO for Session data.
 */
public class SessionDTO
{

  // JSON Utility instance
  private static JsonDAO json = new JsonDAO();

  //****************Session Variables********************//
  private static String nominalUploadSpeed;

  private static String pingURL;
  private static String easterEggURL;
  private static String coreFileURL;

  private static boolean IsDexCraftLauncherInitRunning = false;
  private static boolean IsDexCraftLauncherClientRunning = false;
  private static boolean IsDexCraftBackgroundServicesRunning = false;
  private static String sessionUser;
  private static String sessionPassword;
  private static String lastServer;
  private static boolean offlineMode = false;
  private static boolean forceResourcesUpdate = false;
  private static boolean disableDCBS = false;
  private static boolean backupSingleplayerMaps = true;


   //*******************************************PARSERS*******************************//

  /**
   * PARSE nominal upload speed from JSON file.
   * @param url the JSON read method.
   */
  private static void parseNominalUploadSpeed(String speed)
  {
    nominalUploadSpeed = speed;
  }

  /**
   * PARSE ping test URL from JSON file.
   * @param url the JSON read method.
   */
  private static void parsePingURL(String url)
  {
    pingURL = url;
  }

  /**
   * PARSE "Easter egg" URL from JSON file.
   * @param url the JSON read method.
   */
  private static void parseEasterEggURL(String url)
  {
    easterEggURL = url;
  }

  /**
   * PARSE CoreFile URL from JSON file.
   * @param url the JSON read method.
   */
  private static void parseCoreFileURL(String url)
  {
    coreFileURL = url;
  }

  /**
   * PARSE the Init instance status, from JSON file.
   * @param isRunning the JSON output
   */
  private static void parseDexCraftLauncherInitStatus(boolean isRunning)
  {
    IsDexCraftLauncherInitRunning = isRunning;
  }

  /**
   * PARSE the Client instance status, from JSON file.
   * @param isRunning the JSON output
   */
  private static void parseDexCraftLauncherClientStatus(boolean isRunning)
  {
    IsDexCraftLauncherClientRunning = isRunning;
  }

  /**
   * PARSE the Background Services instance status, from JSON file.
   * @param isRunning the JSON output
   */
  private static void parseDexCraftBackgroundServicesStatus(boolean isRunning)
  {
    IsDexCraftBackgroundServicesRunning = isRunning;
  }

  /**
   * PARSE last session's username (or current username) from JSON file.
   * @param user the JSON read method.
   */
  private static void parseSessionUser(String user)
  {
    sessionUser = user;
  }

  /**
   * PARSE last session's password (or current password) from JSON file.
   * @param pass the JSON read method.
   */
  private static void parseSessionPassword(String pass)
  {
    sessionPassword = pass;
  }

  /**
   * PARSE Last Server selected on client, from JSON file.
   * @param serverIndex the JSON read method.
   */
  private static void parseLastServer(String serverIndex)
  {
    lastServer = serverIndex;
  }

  /**
   * PARSE Offline Mode status from JSON file.
   * @param isSet the JSON read method.
   */
  private static void parseOfflineModeStatus(boolean isSet)
  {
    offlineMode = isSet;
  }

  /**
   * PARSE "Force Resources Update" option from JSON file.
   * @param isSet the JSON read method.
   */
  private static void parseForceResourcesUpdate(boolean isSet)
  {
    forceResourcesUpdate = isSet;
  }

  /**
   * PARSE "Disable DexCraft Background Services" option from JSON file.
   * @param isDisabled the JSON output.
   */
  private static void parseDisableDCBS(boolean isDisabled)
  {
    disableDCBS = isDisabled;
  }

  /**
   * PARSE "Backup Singleplayer Maps" option from JSON file.
   * @param isEnabled the JSON output.
   */
  private static void parseBackupSingleplayerMaps(boolean isEnabled)
  {
    backupSingleplayerMaps = isEnabled;
  }

  //*******************************************GETTERS*******************************//
  /**
   * GET nominal upload speed stored in DTO variable.
   * @return the speed.
   */
  public static String getNominalUploadSpeed()
  {
    return nominalUploadSpeed;
  }

  /**
   * GET ping test URL stored in DTO variable.
   * @return the URL.
   */
  public static String getPingURL()
  {
    return pingURL;
  }

   /**
   * GET "Easter egg" URL stored in DTO variable.
   * @return the URL.
   */
  public static String getEasterEggURL()
  {
    return easterEggURL;
  }

  /**
   * GET CoreFile URL stored in DTO variable.
   * @return the URL.
   */
  public static String getCoreFileURL()
  {
    return coreFileURL;
  }

  /**
   * GET Init instance status from DTO variable.
   * @return if the instance is running (true) or not (false).
   */
  public static boolean getDexCraftLauncherInitStatus()
  {
    return IsDexCraftLauncherInitRunning;
  }

  /**
   * GET Client instance status from DTO variable.
   * @return if the instance is running (true) or not (false).
   */
  public static boolean getDexCraftLauncherClientStatus()
  {
    return IsDexCraftLauncherClientRunning;
  }

  /**
   * GET Background Services instance status from DTO variable.
   * @return if the instance is running (true) or not (false).
   */
  public static boolean getDexCraftBackgroundServicesStatus()
  {
    return IsDexCraftBackgroundServicesRunning;
  }

  /**
   * GET last session's user (or current user) stored in DTO variable.
   * @return the username.
   */
  public static String getSessionUser()
  {
    if (sessionUser.equals("null"))
    {
      sessionUser = "";
    }
    return sessionUser;
  }

  /**
   * GET last session's user (or current user) stored in DTO variable.
   * @return the password.
   */
  public static String getSessionPassword()
  {
    return sessionPassword;
  }

  /**
   * GET last session's server selected in Login Screen, stored in DTO variable.
   * @return the last server index, in string.
   */
  public static String getLastServer()
  {
    return lastServer;
  }

  /**
   * GET Offline Mode status stored in DTO variable.
   * @return the Offline Mode status.
   */
  public static boolean isOfflineModeOn()
  {
    return offlineMode;
  }

  /**
   * GET "Force Resources Updates" option, stored in DTO variable.
   * @return the Force Resources Updates' status.
   */
  public static boolean isForceResourcesUpdateOn()
  {
    return forceResourcesUpdate;
  }

  /**
   * GET "Disable DexCraft Background Services" option, stored in DTO variable.
   * @return the Disable DCBS' status.
   */
  public static boolean isDCBSDisabled()
  {
    return  disableDCBS;
  }

  /**
   * GET "Backup Singleplayer Maps" option, stored in DTO variable.
   * @return the option status.
   */
  public static boolean isBackupSingleplayerMapsEnabled()
  {
    return  backupSingleplayerMaps;
  }

   //*******************************************SETTERS*******************************//

  /**
   * SET nominal upload speed into the JSON file.
   * @param speed the nominal speed
   */
  public static void setNominalUploadSpeed(String speed)
  {
    json.editValue(DexCraftFiles.launcherProperties, "SessionProperties", "NominalUploadSpeed", speed);
    parseNominalUploadSpeed(speed);
  }

  /**
   * SET Init instance status into the JSON file.
   * @param isRunning if the instance is running (true) or not (false).
   */
  public static void setDexCraftLauncherInitInstance(boolean isRunning)
  {
    json.editValue(DexCraftFiles.launcherProperties, "SessionProperties", "IsDexCraftLauncherInitRunning", Boolean.toString(isRunning));
    parseDexCraftLauncherInitStatus(isRunning);
  }

  /**
   * SET Client instance status into the JSON file.
   * @param isRunning if the instance is running (true) or not (false).
   */
  public static void setDexCraftLauncherClientInstance(boolean isRunning)
  {
    json.editValue(DexCraftFiles.launcherProperties, "SessionProperties", "IsDexCraftLauncherClientRunning", Boolean.toString(isRunning));
    parseDexCraftLauncherClientStatus(isRunning);
  }

  /**
   * SET Background Services instance status into the JSON file.
   * @param isRunning if the instance is running (true) or not (false).
   */
  public static void setDexCraftBackgroundServicesInstance(boolean isRunning)
  {
    json.editValue(DexCraftFiles.launcherProperties, "SessionProperties", "IsDexCraftBackgroundServicesRunning", Boolean.toString(isRunning));
    parseDexCraftBackgroundServicesStatus(isRunning);
  }

  /**
   * SET the last session user (or current user) into the JSON file<br>
   * and parse it to the DTO.
   * @param user the logged username.
   */
  public static void setSessionUser(String user)
  {
    if (user.equals(""))
    {
      user = "null";
    }
    json.editValue(DexCraftFiles.launcherProperties, "SessionProperties", "SessionUser", user);
    parseSessionUser(user);
  }

  /**
   * SET the last session password (or current password) into the JSON file<br>
   * and parse it to the DTO.
   * @param pass the account password.
   */
  public static void setSessionPassword(String pass)
  {
    json.editValue(DexCraftFiles.launcherProperties, "SessionProperties", "SessionPassword", pass);
    parseSessionPassword(pass);
  }

  /**
   * SET the last server selected on client into the JSON file<br>
   * and parse it to the DTO.
   * @param serverIndex the index which represents the server.
   */
  public static void setLastServer(String serverIndex)
  {
    json.editValue(DexCraftFiles.launcherProperties, "SessionProperties", "LastServerIndex", serverIndex);
    parseLastServer(serverIndex);
  }

  /**
   * SET the Offline Mode status into the JSON file<br>
   * and parse it to the DTO.
   * @param isOn if the Offline Mode is enabled (true) or not (false)
   */
  public static void setOfflineModeStatus(boolean isOn)
  {
    json.editValue(DexCraftFiles.launcherProperties, "SessionProperties", "OfflineMode", Boolean.toString(isOn));
    parseOfflineModeStatus(isOn);
  }

  /**
   * SET the Resources Update option into the JSON file<br>
   * and parse it to the DTO.
   * @param isSet if the Force Resources Update option is enabled (true) or not (false)
   */
  public static void setForceResourcesUpdate(boolean isSet)
  {
    json.editValue(DexCraftFiles.launcherProperties, "SessionProperties", "ForceResourcesUpdate", Boolean.toString(isSet));
    parseForceResourcesUpdate(isSet);
  }

  /**
   * SET the Disable DCBS status into the JSON file<br>
   * and parse it to the DTO.
   * @param isDisabled if the Background Services is disabled (true) or not (false)
   */
  public static void setDisableDCBS(boolean isDisabled)
  {
    json.editValue(DexCraftFiles.launcherProperties, "SessionProperties", "DisableBackgroundServices", Boolean.toString(isDisabled));
    parseDisableDCBS(isDisabled);
  }

  /**
   * SET the enabled or disabled status of the<br>
   * Backup Singleplayer Maps option into the JSON file<br>
   * and parse it to the DTO.
   * @param isEnabled if that option is enabled (true) or not (false)
   */
  public static void setBackupSingleplayerMaps(boolean isEnabled)
  {
    json.editValue(DexCraftFiles.launcherProperties, "SessionProperties", "BackupSingleplayerMaps", Boolean.toString(isEnabled));
    parseBackupSingleplayerMaps(isEnabled);
  }


   /**
   * Parse assets from JSON file into DTO.
   */
  public static void parseSessionAssets()
  {
    // Interrupt Launcher if asset source is absent
    if(!DexCraftFiles.launcherProperties.exists())
    {
      System.out.println("ARQUIVO DE PROPRIEDADES NÃO ENCONTRADO.");
      alerts.tryAgain();
    }
    else if(!DexCraftFiles.coreFileLinkFile.exists())
    {
      System.out.println("ARQUIVO DE URL NÃO ENCONTRADO.");
      alerts.tryAgain();
    }
    else
    {
      parseNominalUploadSpeed(json.readValue(DexCraftFiles.launcherProperties, "SessionProperties", "NominalUploadSpeed"));

      parsePingURL(json.readValue(DexCraftFiles.coreFileLinkFile, "URLs", "PingURL"));
      parseEasterEggURL(json.readValue(DexCraftFiles.coreFileLinkFile, "URLs", "EasterEggURL"));
      parseCoreFileURL(json.readValue(DexCraftFiles.coreFileLinkFile, "URLs", "CoreFileURL"));

      parseDexCraftLauncherInitStatus(json.readValue(DexCraftFiles.launcherProperties, "SessionProperties", "IsDexCraftLauncherInitRunning").equals("true"));
      parseDexCraftLauncherClientStatus(json.readValue(DexCraftFiles.launcherProperties, "SessionProperties", "IsDexCraftLauncherClientRunning").equals("true"));
      parseDexCraftBackgroundServicesStatus(json.readValue(DexCraftFiles.launcherProperties, "SessionProperties", "IsDexCraftBackgroundServicesRunning").equals("true"));

      parseSessionUser(json.readValue(DexCraftFiles.launcherProperties, "SessionProperties", "SessionUser"));
      parseSessionPassword(json.readValue(DexCraftFiles.launcherProperties, "SessionProperties", "SessionPassword"));
      parseLastServer(json.readValue(DexCraftFiles.launcherProperties, "SessionProperties", "LastServerIndex"));

      parseOfflineModeStatus(json.readValue(DexCraftFiles.launcherProperties, "SessionProperties", "OfflineMode").equals("true"));
      parseForceResourcesUpdate(json.readValue(DexCraftFiles.launcherProperties, "SessionProperties", "ForceResourcesUpdate").equals("true"));
      parseDisableDCBS(json.readValue(DexCraftFiles.launcherProperties, "SessionProperties", "DisableBackgroundServices").equals("true"));
      parseBackupSingleplayerMaps(json.readValue(DexCraftFiles.launcherProperties, "SessionProperties", "BackupSingleplayerMaps").equals("true"));

      System.out.println("Assets de sessão carregados.");
    }
  }
}
