package net.dex.dexcraft.backgroundservices.commons.tools;


import java.io.File;


/**
 * Class containing all File references and objects.
 */
public class DexCraftFiles
{

  //*******************************Main program folders************************************************//

  /** Root directory. */
  public static File mainFolder = new File("C:/DexCraft");
    /** Folder containing the entire Launcher installed files, programs and data. */
  public static File launcherFolder = new File (mainFolder + "/launcher");
  /** Launcher logs location. */
  public static File logFolder = new File(launcherFolder + "/logs");
  /** Temporary folder for common tasks. */
  public static File tempFolder = new File (launcherFolder + "/temp");
  /** Folder containing last sync data made. */
  public static File gameCache = new File(launcherFolder + "/game");
  /** Folder containing 3rd party programs needed to the launcher. */
  public static File launcherBinaries = new File (launcherFolder + "/binaries");
  /** Folder containing the internal launcher and scripts. */
  public static File runtimeFolder = new File(launcherFolder + "/rtm");


  //*********************************Launcher shortcuts************************************************//

  /** Launcher shortcut source location. */
  public static File shortcutSrc = new File(launcherFolder + "/DexCraft Launcher.lnk");
  /** Shortcut destination to Windows default Program Folder. */
  public static File shortcutProgramFolder = new File (System.getenv("APPDATA") + "/Microsoft/Windows/Start Menu/Programs/DexCraft Launcher.lnk");
  /** Shortcut destination to user's Desktop. */
  public static File shortcutUserDesktop = new File ("C:/Users/"+ System.getenv("USERNAME") + "/Desktop/DexCraft Launcher.lnk");
  /** Shortcut destination to Default User's Desktop. */
  public static File shortcutDefaultDesktop = new File ("C:/Users/Default/Desktop/DexCraft Launcher.lnk");


  //******************************Lockers and checkers*************************************************//

  /** File which identifies if the program is running as Administrator. */
  public static File adminCheck = new File ("C:/admin.dc");
  /**
   * File which identifies if the program is logging. Prevents logging on
   * different log files since each Class calls new instances of the same
   * Logger Class.
   */
  public static File logLock = new File (logFolder + "/log.dc");

  /** File which identifies if the Music Player Service is running. **/
  public static File playerLock = new File(launcherFolder + "/player.dc");

  /** File used to identify if Init is present. **/
  public static File integrityCheckInit = new File (launcherFolder + "/DexCraftLauncherInit.jar");
  /** File used to identify if Launcher is present. **/
  public static File integrityCheckClient = new File (launcherFolder + "/DexCraftLauncher.jar");
  /** File used to identify if Background Services is present. **/
  public static File integrityCheckDCBS = new File (launcherFolder + "/DexCraftBackgroundServices.jar");
  /** File used to identify if DexCraft Factions Client is present. **/
  public static File integrityCheckDC = new File (runtimeFolder + "/dc.exe");
  /** File used to identify if DexCraft Pixelmon is present. **/
  public static File integrityCheckDCPX = new File (runtimeFolder + "/dcpx.exe");
  /** File used to identify if DexCraft Vanilla is present. **/
  public static File integrityCheckDCVN = new File (runtimeFolder + "/dcvn.exe");
  /** File used to identify if DexCraft Backup is present. **/
  public static File integrityCheckDCB = new File (runtimeFolder + "/dcb.exe");

  /**
   * The temporary file downloaded only for connection speed tests.
   */
  public static File downloadTestFile = new File(tempFolder + "/10M.iso");


  //*****************************************Launcher main resources folders and files***********************//

  /** Launcher's resources install directory. */
  public static File resFolder = new File(launcherFolder + "/res");
  /** Launcher's resources downloaded package to install. */
  public static File resZip = new File (tempFolder + "/resources.zip");
  /** JRE update downloaded file. **/
  public static File updateJREZip = new File (tempFolder + "/jre.zip");


  //*****************************************Game Client resources folders and files***********************//

  /** Folder containing main configuration files and adjusts, including extras. **/
  public static File srcFolder = new File(launcherFolder + "/src");
  /** DexCraft Factions Game Client resource directory. **/
  public static File srcDC = new File(srcFolder + "/dc");
  /** DexCraft Pixelmon Game Client resource directory. **/
  public static File srcDCPX = new File(srcFolder + "/dcpx");
  /** DexCraft Vanilla Game Client resource directory. **/
  public static File srcDCVN = new File(srcFolder + "/dcvn");
  /** DexCraft Beta Game Client resource directory. **/
  public static File srcDCB = new File(srcFolder + "/dcb");


  //*****************************************Game Client provisioned zip files***********************//

  /** Factions Game Client downloaded install file. **/
  public static File updateDCZip = new File (tempFolder + "/dclclientdc.zip");
  /** Pixelmon Game Client downloaded install file. **/
  public static File updateDCPXZip = new File (tempFolder + "/dclclientdcpx.zip");
  /** Vanilla Game Client downloaded install file. **/
  public static File updateDCVNZip = new File (tempFolder + "/dclclientdcvn.zip");
  /** Beta Game Client downloaded install file. **/
  public static File updateDCBZip = new File (tempFolder + "/dclclientdcb.zip");


  //*****************************************Game Patch provisioned zip files***********************//

  /** Factions Game Patch downloaded install file. **/
  public static File updateDCPatchZip = new File (tempFolder + "/patchdc.zip");
  /** Pixelmon Game Client downloaded install file. **/
  public static File updateDCPXPatchZip = new File (tempFolder + "/patchdcpx.zip");
  /** Vanilla Game Client downloaded install file. **/
  public static File updateDCVNPatchZip = new File (tempFolder + "/patchdcvn.zip");
  /** Beta Game Client downloaded install file. **/
  public static File updateDCBPatchZip = new File (tempFolder + "/patchdcb.zip");


  //******************************************CoreFile assets***********************************************//

  /** File containing CoreFile download URL. */
  public static File coreFileLinkFile = new File (launcherFolder + "/cfurl.json");
  /** CoreFile location after downloaded properly. */
  public static File coreFile = new File(launcherFolder+ "/corecfg.json");

  /** DexCraft Launcher main file asset. This contains important
      information about the Launcher in current execution, as if
      the offline mode was enabled, instance lockers etc.**/
  public static File launcherProperties = new File (launcherFolder + "/properties.json");
  /** Launcher's version update downloaded package to install. */
  public static File updateLauncherZip = new File (tempFolder + "/launcher.zip");
  /** DCBS's version update downloaded package to install. */
  public static File updateDCBSZip = new File (tempFolder + "/dcbs.zip");
  /** Init's version update downloaded package to install. */
  public static File updateInitZip = new File (tempFolder + "/init.zip");


  //******************************************Miscelanneous***********************************************//

  /** This is a matrix file of the Sync Properties file,
   * which store all backups timestamps in local client. */
  public static File syncPropsRoot = new File(launcherFolder + "/syncproperties.json");

  /** Folder containing all ftp temporary data. */
  public static File tempFTPFolder = new File(tempFolder + "/ftp");

}
