package net.dex.dexcraft.backgroundservices.commons.dto;


import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import net.dex.dexcraft.backgroundservices.commons.dao.JsonDAO;
import net.dex.dexcraft.backgroundservices.commons.tools.DexCraftFiles;


/**
 * DTO for URLs.
 */
public class UrlsDTO
{
  // JSON Utility instance
  private static JsonDAO json = new JsonDAO();

  //****************Launcher Updates URLs********************//
  private static String launcherResourceFile = "null";
  private static String initUpdate = "null";
  private static String dclUpdate = "null";
  private static String dcbsUpdate = "null";
  private static String jreInstaller = "null";

  //****************Client Updates URLs********************//
  private static String clientDC = "null";
  private static String clientDCPX = "null";
  private static String clientDCVN = "null";
  private static String clientDCB = "null";

  //****************Patch Updates URLs********************//
  private static String patchDC = "null";
  private static String patchDCPX = "null";
  private static String patchDCVN = "null";
  private static String patchDCB = "null";

  //****************Extras URLs********************//
  private static String soundDCPXChocoboV2 = "null";


  //*******************************************PARSERS*******************************//

  /**
   * PARSE the Launcher Resource File URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parseLauncherResourceFile(String url)
  {
    launcherResourceFile = url;
  }

  /**
   * PARSE the Init URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parseInitUpdate(String url)
  {
    initUpdate = url;
  }

  /**
   * PARSE the Client Update URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parseDCLUpdate(String url)
  {
    dclUpdate = url;
  }

  /**
   * PARSE the Background Services Update URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parseDCBSUpdate(String url)
  {
    dcbsUpdate = url;
  }

  /**
   * PARSE the JRE Update URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parseJREInstaller(String url)
  {
    jreInstaller = url;
  }

  /**
   * PARSE the DexCraft Factions Client Update URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parseClientDC(String url)
  {
    clientDC = url;
  }

  /**
   * PARSE the DexCraft Pixelmon Client Update URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parseClientDCPX(String url)
  {
    clientDCPX = url;
  }

   /**
   * PARSE the DexCraft Vanilla Client Update URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parseClientDCVN(String url)
  {
    clientDCVN = url;
  }

   /**
   * PARSE the DexCraft Beta Client Update URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parseClientDCB(String url)
  {
    clientDCB = url;
  }

   /**
   * PARSE the DexCraft Factions Patch Update URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parsePatchDC(String url)
  {
    patchDC = url;
  }

   /**
   * PARSE the DexCraft Pixelmon Patch Update URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parsePatchDCPX(String url)
  {
    patchDCPX = url;
  }

   /**
   * PARSE the DexCraft Vanilla Patch Update URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parsePatchDCVN(String url)
  {
    patchDCVN = url;
  }

   /**
   * PARSE the DexCraft Beta Client Update URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parsePatchDCB(String url)
  {
    patchDCB = url;
  }

   /**
   * PARSE the "No Batidão do Chocobo v2" Soundpack <br>
   * for DexCraft Pixelmon URL from the JSON file.
   * @param url the URL retrieved from JSON.
   */
  private static void parseSoundDCPXChocoboV2(String url)
  {
    soundDCPXChocoboV2 = url;
  }


  //*******************************************GETTERS*******************************//

  /**
   * GET the Resources URL.
   * @return the URL for the package.
   */
  public static String getLauncherResourceFile()
  {
    return launcherResourceFile;
  }

  /**
   * GET the Init URL.
   * @return the URL for the package.
   */
  public static String getInitUpdate()
  {
    return initUpdate;
  }

  /**
   * GET the DexCraft Launcher's Update URL.
   * @return the URL for the package.
   */
  public static String getDCLUpdate()
  {
    return dclUpdate;
  }

  /**
   * GET the DexCraft Background Services' Update URL.
   * @return the URL for the package.
   */
  public static String getDCBSUpdate()
  {
    return dcbsUpdate;
  }

  /**
   * GET the JRE Update URL.
   * @return the URL for the package.
   */
  public static String getJREInstaller()
  {
    return jreInstaller;
  }

  /**
   * GET the DexCraft Factions Client URL.
   * @return the URL for the package.
   */
  public static String getClientDC()
  {
    return clientDC;
  }

  /**
   * GET the DexCraft Pixelmon Client URL.
   * @return the URL for the package.
   */
  public static String getClientDCPX()
  {
    return clientDCPX;
  }

  /**
   * GET the DexCraft Vanilla Client URL.
   * @return the URL for the package.
   */
  public static String getClientDCVN()
  {
    return clientDCVN;
  }

  /**
   * GET the DexCraft Beta Client URL.
   * @return the URL for the package.
   */
  public static String getClientDCB()
  {
    return clientDCB;
  }

  /**
   * GET the DexCraft Factions Patch URL.
   * @return the URL for the package.
   */
  public static String getPatchDC()
  {
    return patchDC;
  }

  /**
   * GET the DexCraft Pixelmon Patch URL.
   * @return the URL for the package.
   */
  public static String getPatchDCPX()
  {
    return patchDCPX;
  }

  /**
   * GET the DexCraft Vanilla Patch URL.
   * @return the URL for the package.
   */
  public static String getPatchDCVN()
  {
    return patchDCVN;
  }

  /**
   * GET the DexCraft Beta Patch URL.
   * @return the URL for the package.
   */
  public static String getPatchDCB()
  {
    return patchDCB;
  }

  /**
   * GET the "No Batidão do Chocobo v2" soundpack URL.
   * @return the URL for the package.
   *
   */
  public static String getSoundDCPXChocoboV2()
  {
    return soundDCPXChocoboV2;
  }

  //*******************************************SETTERS*******************************//
  // URLs variables don't need SETTER methods since methods since
  //  the data into the CoreFile won't be changed, and the Launcher
  //  Properties JSON file doesn't have to store them.


  /**
   * Parse all URLs from JSON into the DTO.
   */
  public static void parseURLs()
  {
    // Interrupt Launcher if asset source is absent
    if (!DexCraftFiles.coreFile.exists())
    {
      logger.log("***ERRO***", "COREFILE NÃO ENCONTRADO.");
      alerts.noCoreFile();
    }
    else
    {
      parseLauncherResourceFile(json.readValue(DexCraftFiles.coreFile, "LauncherUpdates", "LauncherResourceFile"));
      parseInitUpdate(json.readValue(DexCraftFiles.coreFile, "LauncherUpdates", "InitUpdate"));
      parseDCLUpdate(json.readValue(DexCraftFiles.coreFile, "LauncherUpdates", "DCLUpdate"));
      parseDCBSUpdate(json.readValue(DexCraftFiles.coreFile, "LauncherUpdates", "DCBSUpdate"));
      parseJREInstaller(json.readValue(DexCraftFiles.coreFile, "LauncherUpdates", "JREInstaller"));

      parseClientDC(json.readValue(DexCraftFiles.coreFile, "ClientUpdates", "ClientDC"));
      parseClientDCPX(json.readValue(DexCraftFiles.coreFile, "ClientUpdates", "ClientDCPX"));
      parseClientDCVN(json.readValue(DexCraftFiles.coreFile, "ClientUpdates", "ClientDCVN"));
      parseClientDCB(json.readValue(DexCraftFiles.coreFile, "ClientUpdates", "ClientDCB"));

      parsePatchDC(json.readValue(DexCraftFiles.coreFile, "PatchUpdates", "PatchDC"));
      parsePatchDCPX(json.readValue(DexCraftFiles.coreFile, "PatchUpdates", "PatchDCPX"));
      parsePatchDCVN(json.readValue(DexCraftFiles.coreFile, "PatchUpdates", "PatchDCVN"));
      parsePatchDCB(json.readValue(DexCraftFiles.coreFile, "PatchUpdates", "PatchDCB"));

      parseSoundDCPXChocoboV2(json.readValue(DexCraftFiles.coreFile, "Extras", "SoundDCPXChocoboV2"));

      logger.log("INFO", "Assets de URL carregados.");
    }
  }

}
