package net.dex.dexcraft.backgroundservices.commons.check;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import org.apache.commons.io.IOUtils;


/**
 * Check System requirements to run program.
 * Also, return System specifications for logging.
 */
public class SystemRequirements
{


  /**
   * Check if there's any Java installed on machine, and which version it has.
   * @return the Java Runtime Edition version as see in the default program folder.
   */
  public String checkJavaVersion()
  {
    String JREVer = "";
    File javaDir = new File("C:/Program Files/Java");
    if (javaDir.exists())
    {
      File[] JREVersionDirs = javaDir.listFiles();
      for (File file : JREVersionDirs)
      {
        if (file.getName().contains("jre"))
        {
          if(JREVer.equals(""))
          {
            JREVer = file.getName();
          }
          else
          {
            JREVer = JREVer + ", " + file.getName();
          }
        }
      }
    }
    else
    {
      JREVer = "Java Version Unknown";
    }
    return JREVer;
  }

  /**
   * Check the Windows version.
   * @return the Windows version name depending
   * of the System version number.
   */
  public String checkWindowsVersion()
  {
    String vers = System.getProperty("os.version");
    switch (vers)
    {
      case "6.1":
        return "Windows 7";
      case "6.2":
        return "Windows 8";
      case "6.3":
        return "Windows 8.1";
      case "10.0":
        return "Windows 10";
      default:
        return "Unknown Windows Version";
    }
  }

  /**
   * Check if System is running under x86 or x64 bit architeture.
   * @return if the System is of x86 or x64 bits.
   */
  public String checkSystemArch()
  {
    String arch = System.getProperty("os.arch");
    if (arch.equals("amd64"))
    {
      return "x64";
    }
    else
    {
      return "x86";
    }
  }

  /**
   * Check how much RAM the System has. It uses WMIC to get
   * the info, so it isn't precise since it may not count
   * the hardware-reserved RAM set on BIOS.
   * Considering this case, I use an approximated divisor
   * to round the memory value to match the right value in
   * the most accurate way.
   * @return in Integer, the System RAM in GB.
   */
  public int checkSystemRAMGB()
  {
    int ramresult = 0;
    try
    {
      ProcessBuilder getMem = new ProcessBuilder("cmd", "/c", "wmic ComputerSystem get TotalPhysicalMemory");
      String getMemOutput = IOUtils.toString(getMem.start().getInputStream(), StandardCharsets.UTF_8);
      getMemOutput = getMemOutput.replace("TotalPhysicalMemory", "").trim();
      // the divisor basis to return the most accurate value. Change if needed.
      long divisor = 998400000L;
      long memorySize = Long.parseLong(getMemOutput);
      double memorySizeResult = (double) memorySize/divisor;
      float resultdec = (float) memorySizeResult;
      ramresult = Math.round(resultdec);
    }
    catch (IOException ex)
    {
      logger.log(ex, "EXCEÇÃO EM ReqCheck.checkSystemRAM()");
      alerts.tryAgain();
    }
    return ramresult;
  }

}

