package net.dex.dexcraft.backgroundservices.commons.dto;


import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import net.dex.dexcraft.backgroundservices.commons.dao.JsonDAO;
import net.dex.dexcraft.backgroundservices.commons.tools.DexCraftFiles;


/**
 * DTO for FTP connection.
 */
public class FtpDTO
{
  // JSON Utility instance
  private static JsonDAO json = new JsonDAO();

  //****************FTP server variables********************//
  private static String ftpAddress;
  private static int ftpPort;
  private static String ftpUser;
  private static String ftpPassword;
  private static String ftpWorkingDir;

  //*******************************************PARSERS*******************************//
  /**
   * PARSE FTP Server address from JSON file.
   * @param address the JSON read method.
   */
  private static void parseFtpAddress(String address)
  {
    ftpAddress = address;
  }

  /**
   * PARSE FTP Server port from JSON file.
   * @param port the JSON read method.
   */
  private static void parseFtpPort(int port)
  {
    ftpPort = port;
  }

  /**
   * PARSE FTP Server user from JSON file.
   * @param user the JSON read method.
   */
  private static void parseFtpUser(String user)
  {
    ftpUser = user;
  }

  /**
   * PARSE FTP Server password from JSON file.
   * @param pass the JSON read method.
   */
  private static void parseFtpPassword(String pass)
  {
    ftpPassword = pass;
  }

  /**
   * PARSE FTP Server working directory from JSON file.
   * @param dir the JSON read method.
   */
  private static void parseFtpWorkingDir(String dir)
  {
    ftpWorkingDir = dir;
  }

  //*******************************************GETTERS*******************************//

  /**
   * GET FTP Server Address stored in DTO variable.
   * @return the address.
   */
  public static String getFtpAddress()
  {
    return ftpAddress;
  }

  /**
   * GET FTP Server Port stored in DTO variable.
   * @return the port.
   */
  public static int getFtpPort()
  {
    return ftpPort;
  }

  /**
   * GET FTP Server Username stored in DTO variable.
   * @return the username.
   */
  public static String getFtpUser()
  {
    return ftpUser;
  }

  /**
   * GET FTP Server Password stored in DTO variable.
   * @return the password;
   */
  public static String getFtpPassword()
  {
    return ftpPassword;
  }

  /**
   * GET FTP Server Working Directory stored in DTO variable.
   * @return the directory.
   */
  public static String getFtpWorkingDir()
  {
    return ftpWorkingDir;
  }

  //*******************************************SETTERS*******************************//
  // FTP Server doesn't need SETTER methods since
  //  the data into the CoreFile won't be changed, and the Launcher
  //  Properties JSON file doesn't have to store them.


  /**
   * Parse assets from JSON file into DTO.
   */
  public static void parseFTPAssets()
  {
    // Interrupt Launcher if asset source is absent
    if (!DexCraftFiles.coreFile.exists())
    {
      logger.log("***ERRO***", "COREFILE NÃO ENCONTRADO.");
      alerts.noCoreFile();
    }
    else
    {
      parseFtpAddress(json.readValue(DexCraftFiles.coreFile, "FtpServer", "ServerWebAddress"));
      parseFtpPort(Integer.parseInt(json.readValue(DexCraftFiles.coreFile, "FtpServer", "ServerPort")));
      parseFtpUser(json.readValue(DexCraftFiles.coreFile, "FtpServer", "ServerUser"));
      parseFtpPassword(json.readValue(DexCraftFiles.coreFile, "FtpServer", "ServerPassword"));
      parseFtpWorkingDir(json.readValue(DexCraftFiles.coreFile, "FtpServer", "ServerPlayerDataLocation"));

      logger.log("INFO", "FTP: Assets carregados.");
    }
  }
}
