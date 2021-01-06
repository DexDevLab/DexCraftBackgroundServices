package net.dex.dexcraft.backgroundservices.commons.dto;

import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import net.dex.dexcraft.backgroundservices.commons.dao.JsonDAO;
import net.dex.dexcraft.backgroundservices.commons.tools.DexCraftFiles;

/**
 * DTO for version variables.
 */
public class VersionsDTO
{

  // JSON Utility instance
  private static JsonDAO json = new JsonDAO();

  //****************Launcher version variables********************//
  private static String dexCraftLauncherInitVersion = "null";
  private static String dexCraftLauncherClientVersion = "null";
  private static String dexCraftBackgroundServicesVersion = "null";
  private static String dexCraftFactionsPatchVersion = "null";
  private static String dexCraftPixelmonPatchVersion = "null";
  private static String dexCraftVanillaPatchVersion = "null";
  private static String dexCraftBetaPatchVersion = "null";

  //****************Launcher provisioned version variables********************//
  private static String provisionedInitVersion = "null";
  private static String provisionedClientVersion = "null";
  private static String provisionedBackgroundServicesVersion = "null";
  private static String provisionedFactionsPatchVersion = "null";
  private static String provisionedPixelmonPatchVersion = "null";
  private static String provisionedVanillaPatchVersion = "null";
  private static String provisionedBetaPatchVersion = "null";

  //*******************************************PARSERS*******************************//

  /**
   * PARSE Launcher Init version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseDexCraftLauncherInitVersion(String version)
  {
    VersionsDTO.dexCraftLauncherInitVersion = version;
  }

  /**
   * PARSE Launcher Client version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseDexCraftLauncherClientVersion(String version)
  {
    VersionsDTO.dexCraftLauncherClientVersion = version;
  }


  /**
   * PARSE Launcher Background Services version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseDexCraftBackgroundServicesVersion(String version)
  {
    VersionsDTO.dexCraftBackgroundServicesVersion = version;
  }

  /**
   * PARSE DexCraft Factions Game Client version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseDexCraftFactionsPatchVersion(String version)
  {
    VersionsDTO.dexCraftFactionsPatchVersion = version;
  }

  /**
   * PARSE DexCraft Pixelmon Game Client version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseDexCraftPixelmonPatchVersion(String version)
  {
    VersionsDTO.dexCraftPixelmonPatchVersion = version;
  }

  /**
   * PARSE DexCraft Vanilla Game Client version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseDexCraftVanillaPatchVersion(String version)
  {
    VersionsDTO.dexCraftVanillaPatchVersion = version;
  }

  /**
   * PARSE DexCraft Beta Game Client version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseDexCraftBetaPatchVersion(String version)
  {
    VersionsDTO.dexCraftBetaPatchVersion = version;
  }

  /**
   * PARSE provisioned Init version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseProvisionedInitVersion(String version)
  {
    VersionsDTO.provisionedInitVersion = version;
  }

  /**
   * PARSE provisioned Client version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseProvisionedClientVersion(String version)
  {
    VersionsDTO.provisionedClientVersion = version;
  }


  /**
   * PARSE provisioned Background Services version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseProvisionedBackgroundServicesVersion(String version)
  {
    VersionsDTO.provisionedBackgroundServicesVersion = version;
  }

  /**
   * PARSE provisioned Factions Game Client version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseProvisionedFactionsPatchVersion(String version)
  {
    VersionsDTO.provisionedFactionsPatchVersion = version;
  }

  /**
   * PARSE provisioned Pixelmon Game Client version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseProvisionedPixelmonPatchVersion(String version)
  {
    VersionsDTO.provisionedPixelmonPatchVersion = version;
  }

  /**
   * PARSE provisioned Vanilla Game Client version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseProvisionedVanillaPatchVersion(String version)
  {
    VersionsDTO.provisionedVanillaPatchVersion = version;
  }

  /**
   * PARSE provisioned Beta Game Client version from JSON file.
   * @param version the JSON read method.
   */
  private static void parseProvisionedBetaPatchVersion(String version)
  {
    VersionsDTO.provisionedBetaPatchVersion = version;
  }


  //*******************************************GETTERS*******************************//

  /**
   * GET Launcher Init version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getDexCraftLauncherInitVersion()
  {
    return dexCraftLauncherInitVersion;
  }

  /**
   * GET Launcher Client version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getDexCraftLauncherClientVersion()
  {
    return dexCraftLauncherClientVersion;
  }

  /**
   * GET Launcher Background Services version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getDexCraftBackgroundServicesVersion()
  {
    return dexCraftBackgroundServicesVersion;
  }

  /**
   * GET DexCraft Factions Game Client version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getDexCraftFactionsPatchVersion()
  {
    return dexCraftFactionsPatchVersion;
  }

  /**
   * GET DexCraft Pixelmon Game Client version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getDexCraftPixelmonPatchVersion()
  {
    return dexCraftPixelmonPatchVersion;
  }

  /**
   * GET DexCraft Vanilla Game Client version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getDexCraftVanillaPatchVersion()
  {
    return dexCraftVanillaPatchVersion;
  }

  /**
   * GET DexCraft Beta Game Client version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getDexCraftBetaPatchVersion()
  {
    return dexCraftBetaPatchVersion;
  }

  /**
   * GET provisioned Init version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getProvisionedInitVersion()
  {
    return provisionedInitVersion;
  }

  /**
   * GET provisioned Client version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getProvisionedClientVersion()
  {
    return provisionedClientVersion;
  }

  /**
   * GET provisioned Background Services version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getProvisionedBackgroundServicesVersion()
  {
    return provisionedBackgroundServicesVersion;
  }

  /**
   * GET provisioned Factions Game Client version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getProvisionedFactionsPatchVersion()
  {
    return provisionedFactionsPatchVersion;
  }

  /**
   * GET provisioned Pixelmon Game Client version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getProvisionedPixelmonPatchVersion()
  {
    return provisionedPixelmonPatchVersion;
  }

  /**
   * GET provisioned Vanilla Game Client version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getProvisionedVanillaPatchVersion()
  {
    return provisionedVanillaPatchVersion;
  }

  /**
   * GET provisioned Beta Game Client version stored in VersionsDTO variable.
   * @return the version.
   */
  public static String getProvisionedBetaPatchVersion()
  {
    return provisionedBetaPatchVersion;
  }

  //*******************************************SETTERS*******************************//

  /**
   * SET the Launcher Init version into the JSON file<br>
   * and parse it to the VersionsDTO.
   * @param version the update version.
   */
  public static void setDexCraftLauncherInitVersion(String version)
  {
    json.editValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftLauncherInitVersion", version);
    parseDexCraftLauncherInitVersion(version);
  }

   /**
   * SET the Launcher Client version into the JSON file<br>
   *  and parse it to the VersionsDTO.
   * @param version the update version.
   */
  public static void setDexCraftLauncherClientVersion(String version)
  {
    json.editValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftLauncherVersion", version);
    parseDexCraftLauncherClientVersion(version);
  }

  /**
   * SET the Launcher Background Services version into the JSON file<br>
   * and parse it to the VersionsDTO.
   * @param version the update version.
   */
  public static void setDexCraftBackgroundServicesVersion(String version)
  {
    json.editValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftBackgroundServicesVersion", version);
    parseDexCraftBackgroundServicesVersion(version);
  }

   /**
   * SET the DexCraft Factions Game Client version into the JSON file<br>
   * and parse it to the VersionsDTO.
   * @param version the update version.
   */
  public static void setDexCraftFactionsPatchVersion(String version)
  {
    json.editValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftFactionsPatchVersion", version);
    parseDexCraftFactionsPatchVersion(version);
  }

  /**
   * SET the DexCraft Pixelmon Game Client version into the JSON file<br>
   * and parse it to the VersionsDTO.
   * @param version the update version.
   */
  public static void setDexCraftPixelmonPatchVersion(String version)
  {
    json.editValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftPixelmonPatchVersion", version);
    parseDexCraftPixelmonPatchVersion(version);
  }

  /**
   * SET the DexCraft Vanilla Game Client version into the JSON file<br>
   * and parse it to the VersionsDTO.
   * @param version the update version.
   */
  public static void setDexCraftVanillaPatchVersion(String version)
  {
    json.editValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftVanillaPatchVersion", version);
    parseDexCraftVanillaPatchVersion(version);
  }

  /**
   * SET the DexCraft Beta Game Client version into the JSON file<br>
   * and parse it to the VersionsDTO.
   * @param version the update version.
   */
  public static void setDexCraftBetaPatchVersion(String version)
  {
    json.editValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftBetaPatchVersion", version);
    parseDexCraftBetaPatchVersion(version);
  }


  // Provisioned variables don't need SETTER methods since methods since
  //  the data into the CoreFile won't be changed, and the Launcher
  //  Properties JSON file doesn't have to store them.


  /**
   * Parse all versions data from JSON.
   */
  public static void parseVersions()
  {
    // Interrupt Launcher if asset source is absent
    if(!DexCraftFiles.launcherProperties.exists())
    {
      logger.log("***ERRO***", "ARQUIVO DE PROPRIEDADES NÃO ENCONTRADO.");
      alerts.tryAgain();
    }
    else
    {
      parseDexCraftLauncherInitVersion(json.readValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftLauncherInitVersion"));
      parseDexCraftLauncherClientVersion(json.readValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftLauncherVersion"));
      parseDexCraftBackgroundServicesVersion(json.readValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftBackgroundServicesVersion"));
      parseDexCraftFactionsPatchVersion(json.readValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftFactionsPatchVersion"));
      parseDexCraftPixelmonPatchVersion(json.readValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftPixelmonPatchVersion"));
      parseDexCraftVanillaPatchVersion(json.readValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftVanillaPatchVersion"));
      parseDexCraftBetaPatchVersion(json.readValue(DexCraftFiles.launcherProperties, "Versions", "DexCraftBetaPatchVersion"));

      logger.log("INFO", "Assets de versões locais carregados.");
    }
  }

  /**
   * Parse all provisioned versions info from JSON.
   */
  public static void parseProvisionedVersions()
  {
    // Interrupt Launcher if asset source is absent
    if (!DexCraftFiles.coreFile.exists())
    {
      logger.log("***ERRO***", "COREFILE NÃO ENCONTRADO.");
      alerts.noCoreFile();
    }
    else
    {
      parseProvisionedInitVersion(json.readValue(DexCraftFiles.coreFile, "Versions", "DexCraftLauncherInitVersion"));
      parseProvisionedClientVersion(json.readValue(DexCraftFiles.coreFile, "Versions", "DexCraftLauncherVersion"));
      parseProvisionedBackgroundServicesVersion(json.readValue(DexCraftFiles.coreFile, "Versions", "DexCraftBackgroundServicesVersion"));
      parseProvisionedFactionsPatchVersion(json.readValue(DexCraftFiles.coreFile, "Versions", "DexCraftFactionsPatchVersion"));
      parseProvisionedPixelmonPatchVersion(json.readValue(DexCraftFiles.coreFile, "Versions", "DexCraftPixelmonPatchVersion"));
      parseProvisionedVanillaPatchVersion(json.readValue(DexCraftFiles.coreFile, "Versions", "DexCraftVanillaPatchVersion"));
      parseProvisionedBetaPatchVersion(json.readValue(DexCraftFiles.coreFile, "Versions", "DexCraftBetaPatchVersion"));

      logger.log("INFO", "Assets de versões provisionadas carregados.");
    }
  }

}
