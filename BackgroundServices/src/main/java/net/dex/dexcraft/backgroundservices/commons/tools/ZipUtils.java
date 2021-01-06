package net.dex.dexcraft.backgroundservices.commons.tools;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import org.apache.commons.io.IOUtils;


/**
 * Utility for manipulate zip files.
 */
public class ZipUtils
{

  private static int totalFiles = 0;
  private static int partialFiles = 0;
  public static String statusMessage = "";

  /**
   * Compress a folder or a file to a zip file, with password.
   * @param sourceDir the directory that will be zipped to.<br>
   * The zip file will be created in its parent directory. <br>
   * The zip file will be named by the directory name.
   * @param password the password to be put into the zip file.
   */
  public static void compressWithPassword(File sourceDir, String password)
  {
    try
    {
      logger.log("INFO","Iniciando processo de compactação...");
      ProcessBuilder pb = new ProcessBuilder("cmd", "/c", DexCraftFiles.launcherBinaries + "/7z/7z.exe"
                    + " a " + sourceDir.getName() + ".7z -y " + "-p" + password + " -mhe "
                    + sourceDir.getName() + "\\*.*")
                    .directory(sourceDir.getParentFile());
      Process p = pb.start();
      p.waitFor();
      logger.log("INFO","Concluído.");
    }
    catch (IOException | InterruptedException ex)
    {
      System.out.println("ERROR");
    }
  }


  /**
   * Extracts a zip file with a password required.
   * @param game parameter to list the root of all the files.
   * Also, defines how much entries the zip file contains, based
   * on this prefix in the entry path.
   * @param destinationDir the destination directory.
   * @param password the password of the zip file.
   */
  public static void extractWithPassword(String game, File destinationDir, String password)
  {
    Thread extract = new Thread(()->
    {
      try
      {
        logger.log("INFO","Iniciando listagem recursiva...");
        ProcessBuilder listZip = new ProcessBuilder("cmd", "/c", DexCraftFiles.launcherBinaries + "/7z/7z.exe"
                      + " l " + destinationDir.getName() + ".7z -y " + "-p" + password)
                      .directory(destinationDir.getParentFile());
        Scanner out = new Scanner(IOUtils.toString(listZip.start().getInputStream(), StandardCharsets.UTF_8));
        while (out.hasNextLine())
        {
          if (out.nextLine().contains(game))
          {
            totalFiles++;
          }
        }
        out.close();
        logger.log("INFO","Concluído.");
        logger.log("INFO","Extraindo arquivos...");
        ProcessBuilder pb  = new ProcessBuilder("cmd", "/c", DexCraftFiles.launcherBinaries + "/7z/7z.exe"
                      + " x " + destinationDir.getName() + ".7z -y " + "-p" + password)
                      .directory(destinationDir.getParentFile());
        Process p = pb.start();
        p.waitFor();
        logger.log("INFO","Concluído.");
      }
      catch (IOException | InterruptedException ex)
      {
        System.out.println("ERROR");
      }
    });
    extract.start();
    statusMessage = "Carregando perfil. Aguarde...";
    while (extract.isAlive())
    {
      if ( (destinationDir.exists()) && (totalFiles > 0) )
      {
        partialFiles = destinationDir.list().length;
        statusMessage = "Carregando perfil... " + partialFiles + " / " + totalFiles;
      }
      try
      {
        Thread.sleep(500);
      }
      catch (InterruptedException ex)
      {
        alerts.exceptionHandler(ex, "EXCEÇÃO em ZipUtils.extractWithPassword(String, File, String)");
      }
    }
    statusMessage = "Carregando perfil... " + totalFiles + " / " + totalFiles;
    partialFiles = 0;
    totalFiles = 0;
  }
}
