package net.dex.dexcraft.backgroundservices.commons.dto;


import java.util.ArrayList;
import java.util.List;
import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import net.dex.dexcraft.backgroundservices.commons.dao.JsonDAO;
import net.dex.dexcraft.backgroundservices.commons.tools.DexCraftFiles;


/**
 * DTO for System variables (installation,
 * check, implementation etc).
 */
public class SystemDTO
{
  // JSON Utility instance
  private static JsonDAO json = new JsonDAO();

  //**************System Requirements Class assets ***************//
  private static String reqsMinimumRAM = "0";
  private static String reqsMediumRAM = "0";
  private static String reqsMaximumRAM = "0";
  private static String javaVersion = "null";
  private static String minimumMbpsUploadSpeed = "0";
  private static String speedTestFileURL = "null";

  //******************Backup System assets *************************//
  private static String dcbsSyncTime = "999";
  private static List<String> dclBkpDirectivesFull = new ArrayList<>();
  private static List<String> dclBkpDirectivesPartial = new ArrayList<>();

  //***************************************PARSERS*******************************//

  /**
   * PARSE RAM minimum requirements from JSON file.
   * @param value the JSON read output.
   */
  private static void parseReqsMinimumRAM(String value)
  {
    reqsMinimumRAM = value;
  }

  /**
   * PARSE RAM medium requirements from JSON file.
   * @param value the JSON read output.
   */
  private static void parseReqsMediumRAM(String value)
  {
    reqsMediumRAM = value;
  }

  /**
   * PARSE RAM maximum requirements from JSON file.
   * @param value the JSON read output.
   */
  private static void parseReqsMaximumRAM(String value)
  {
    reqsMaximumRAM = value;
  }

  /**
   * PARSE provisioned Java version from JSON file.
   * @param value the JSON read output.
   */
  private static void parseJavaVersion(String value)
  {
    javaVersion = value;
  }

  /**
   * PARSE minimum Mbps upload speed from JSON file.
   * @param value the JSON read output.
   */
  private static void parseMinimumMbpsUploadSpeed(String value)
  {
    minimumMbpsUploadSpeed = value;
  }

  /**
  * PARSE Background Services time synchronization, in minutes.
  * @param value the JSON read output.
  */
  private static void parseDCBSSyncTime(String value)
  {
    dcbsSyncTime = value;
  }

  /**
   * PARSE speed test file URL from JSON file.
   * @param value the JSON read output.
   */
  private static void parseSpeedTestFileURL(String value)
  {
    speedTestFileURL = value;
  }

  /**
   * PARSE full backup list from JSON file.
   * @param value the JSON read output.
   */
  private static void parseDCLBkpDirectivesFull(List<String> value)
  {
    dclBkpDirectivesFull = value;
  }

  /**
   * PARSE partial backup list from JSON file.
   * @param value the JSON read output.
   */
  private static void parseDCLBkpDirectivesPartial(List<String> value)
  {
    dclBkpDirectivesPartial = value;
  }



  //*******************************************GETTERS*******************************//

  /**
   * GET RAM minimum requirements from DTO.
   * @return the minimum RAM requirement
   */
  public static String getReqsMinimumRAM()
  {
    return reqsMinimumRAM;
  }

  /**
   * GET RAM medium requirements from DTO.
   * @return the medium RAM requirement
   */
  public static String getReqsMediumRAM()
  {
    return reqsMediumRAM;
  }

  /**
   * GET RAM maximum requirements from DTO.
   * @return the maximum RAM requirement
   */
  public static String getReqsMaximumRAM()
  {
    return reqsMaximumRAM;
  }

  /**
   * GET provisioned Java version from DTO.
   * @return the java version currently provisioned.
   */
  public static String getJavaVersion()
  {
    return javaVersion;
  }

  /**
   * GET minimum Mbps upload speed from DTO.
   * @return the minimum upload speed in Mbps.
   */
  public static String getMinimumMbpsUploadSpeed()
  {
    return minimumMbpsUploadSpeed;
  }

  /**
   * GET Background Services time synchronization, in minutes.
   * @return the Background Services time synchronization
   */
  public static String getDCBSSyncTime()
  {
    return dcbsSyncTime;
  }

  /**
   * GET speed test file URL from DTO.
   * @return the speed test file URL
   */
  public static String getSpeedTestFileURL()
  {
    return speedTestFileURL;
  }

  /**
   * GET full backup list from DTO.
   * @return the list
   */
  public static List<String> getDCLBkpDirectivesFull()
  {
    return dclBkpDirectivesFull;
  }

  /**
   * GET partial backup list from DTO.
   * @return the list
   */
  public static List<String> getDCLBkpDirectivesPartial()
  {
    return dclBkpDirectivesPartial;
  }

  //*******************************************SETTERS*******************************//
  // System Requirements variables don't need SETTER methods since methods since
  //  the data into the CoreFile won't be changed, and the Launcher
  //  Properties JSON file doesn't have to store them.


  /**
   * Parse assets from JSON file into DTO.
   */
  public static void parseSystemAssets()
  {
    // Interrupt Launcher if asset source is absent
    if (!DexCraftFiles.coreFile.exists())
    {
      System.out.println("COREFILE NÃO ENCONTRADO.");
      alerts.noCoreFile();
    }
    else
    {
      parseReqsMinimumRAM(json.readValue(DexCraftFiles.coreFile, "Installer", "ReqsMinimumRAM"));
      parseReqsMediumRAM(json.readValue(DexCraftFiles.coreFile, "Installer", "ReqsMediumRAM"));
      parseReqsMaximumRAM(json.readValue(DexCraftFiles.coreFile, "Installer", "ReqsMaximumRAM"));
      parseJavaVersion(json.readValue(DexCraftFiles.coreFile, "Installer", "JavaVersion"));
      parseMinimumMbpsUploadSpeed(json.readValue(DexCraftFiles.coreFile, "BackupService", "MinimumMbpsUploadSpeed"));
      parseSpeedTestFileURL(json.readValue(DexCraftFiles.coreFile, "BackupService", "SpeedTestFileURL"));
      parseDCBSSyncTime(json.readValue(DexCraftFiles.coreFile, "BackgroundServices", "DCBSSyncTime"));
      parseDCLBkpDirectivesFull(json.readList(DexCraftFiles.coreFile, "BackupService", "DCLBkpDirectivesFull"));
      parseDCLBkpDirectivesPartial(json.readList(DexCraftFiles.coreFile, "BackupService", "DCLBkpDirectivesPartial"));

      System.out.println("Assets de sistema carregados.");
    }
  }

}
