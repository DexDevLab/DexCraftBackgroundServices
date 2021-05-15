package net.dex.dexcraft.backgroundservices.commons.tools;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import net.dex.dexcraft.backgroundservices.commons.check.SystemRequirements;
import org.apache.commons.io.FileUtils;


/**
 * Generates a Log.
 */
public class Logger
{

  private DateFormat df;
  private String time;
  private DateFormat df2;
  private File logname;
  private File logdir;
  private File loglock;
  private File logfile;
  private final String line = System.getProperty("line.separator");

  /**
   * Set the log lock. It is used to tell if the logger is running or not.
   * @param lock the file which represents the log lock.
   */
  public void setLogLock(File lock) { this.loglock = new File (lock.toString()); }

  /**
   * Get the log lock. It is used to tell if the logger is running or not.
   * @return the file which represents the log lock.
   */
  private File getLogLock() { return this.loglock; }

  /**
   * Set the date/time format for to show on each log message.
   * @param dateformat the date/format in string.
   */
  public void setMessageFormat(String dateformat) { this.df = new SimpleDateFormat(dateformat); }

  /**
   * Get the date/time format for to show on each log message.
   * @return the date/format in string.
   */
  private String getMessageFormat() { return this.df.toString(); }

  /**
   * Set the date/time format for to show on log filename.
   * @param dateformat the date/format in string.
   */
  public void setLogNameFormat(String dateformat) { this.df2 = new SimpleDateFormat(dateformat); }

  /**
   * Get the date/time format for to show on log filename.
   * @return the date/format in string.
   */
  private String getLogNameFormat() { return this.df2.toString(); }

  /**
   * Set the log file.
   * @param fil the log file.
   */
  public void setLogFile(File fil) { this.logfile = fil; }

  /**
   * Get the log file.
   * @return the full path of the log file.
   */
  public File getLogFile() { return this.logfile; }

  /**
   * Set the log folder where the logs will be stored.
   * @param dir the folder name and its location.
   */
  public void setLogDir(File dir) { this.logdir = dir; }

  /**
   * Set the log folder where the logs will be stored.
   * @return the folder name and its location.
   */
  private File getLogDir() { return this.logdir; }

  /**
   * Get the size of the log directory, with proper
   * measure unit.
   * @return the size of the log directory.
   */
  public String getLogSize()
  {
    String logSizeMsg = "";
    long size = FileUtils.sizeOfDirectory(this.getLogDir());
    long sizeCalculated = 0;
    String sizeMeasurement = "B";
    if (size >= 1048576)
    {
      sizeCalculated = size / 1048576;
      sizeMeasurement = "MB";
    }
    else if (size >= 1024)
    {
      sizeCalculated = size / 1024;
      sizeMeasurement = "KB";
    }
    logSizeMsg = sizeCalculated + sizeMeasurement;
    return logSizeMsg;
  }

  /**
   * Check if the log lock is present.<br>
   * If not, it is considered there is not
   * a log file to this Logger instance.<br> The
   * log file is created, with a header with
   * System specifications and the log lock is created.
   */
  private void checkLock()
  {
    if (!getLogLock().exists())
    {
      try
      {
        FileUtils.touch(getLogLock());
        if (this.logname == null)
        {
          this.logname = new File (this.df2.format(Calendar.getInstance().getTime()) + ".txt");
          this.logfile = new File (this.logdir + File.separator + this.logname);
        }
        SystemRequirements req = new SystemRequirements();
        FileUtils.writeStringToFile(this.logfile,"***********************************************"
                                    +"***********************************************"
                                    +"*************************\n"
                                    +"DexCraft Launcher Logger Utility\n"
                                    +"Executando em " + req.checkWindowsVersion() +" "+  req.checkSystemArch()
                                    +", " + req.checkSystemRAMGB() + "GB RAM, Rodando Java " + req.checkJavaVersion()
                                    +"\n***********************************************"
                                    +"***********************************************"
                                    +"*************************\n", "UTF-8", true);
      }
      catch (IOException ex)
      {
        System.out.println("");
        System.out.println("[***ERRO***] - EXCEÇÃO em Logger.checkLock() - " + ex.getMessage());
        System.out.println("");
      }
    }
    else
    {
      this.logfile = new File(getLatestFileFromDir());
    }
  }


  /**
   * Write the log into the console and externally into the log file.
   *
   * @param throwable - the throwable in case of logging an error
   * @param logmsg - the message to be logged
   */
  public void log (Throwable throwable, String logmsg)
  {
    try
    {
      checkLock();
      this.time = this.df.format(Calendar.getInstance().getTime());
      String logtype = "[***ERRO***]: ";
      Exception ex = new Exception(throwable);
      logmsg = (logmsg + line + ex);
      FileUtils.writeStringToFile(this.logfile, line, "UTF-8", true);
      System.out.println("");
      FileUtils.writeStringToFile(this.logfile, this.time + logtype + logmsg + line, "UTF-8", true);
      System.out.println(time + logtype + logmsg);
      StackTraceElement[] message = throwable.getStackTrace();
      for (StackTraceElement message1 : message)
      {
        FileUtils.writeStringToFile(this.logfile, message1.toString() + line, "UTF-8", true);
        System.out.println(message1.toString());
      }
      FileUtils.writeStringToFile(this.logfile, "" + line, "UTF-8", true);
      System.out.println("");
    }
    catch (IOException ex)
    {
      System.out.println("");
      System.out.println("[***ERRO***] - EXCEÇÃO em Logger.log(Throwable, String) - " + ex.getMessage());
      System.out.println("");
    }
  }

  /**
   * Write the log into the console and externally into the log file.
   *
   * @param logtype - the type of information which is being logged:<br>
   * "INFO" - for general information<br>
   * "ERRO" - for errors
   * @param logmsg - the message to be logged
   */
  public void log (String logtype, String logmsg)
  {
    try
    {
      checkLock();
      this.time = this.df.format(Calendar.getInstance().getTime());
      String finalmsg = "";
      logtype = "[" + logtype + "]: ";
      switch (logtype)
      {
        case "[INFO]: ":
          finalmsg = this.time + logtype + logmsg;
          break;
        case "[***ERRO***]: ":
          finalmsg = line + this.time + logtype + logmsg + line;
          break;
        default:
          throw new IOException();
      }
      System.out.println(finalmsg);
      FileUtils.writeStringToFile(this.logfile, finalmsg + line, "UTF-8", true);
    }
    catch (IOException ex)
    {
      System.out.println("");
      System.out.println("[***ERRO***] - EXCEÇÃO em Logger.log(String, String) - " + ex.getMessage());
      System.out.println("");
    }
  }

  /**
   * Get the most recently modified file
   * from the log folder.
   * @return the log file currently being used on logger.
   */
  private String getLatestFileFromDir()
  {
    File dir = new File(this.getLogDir().toString());
    File[] files = dir.listFiles();
    if (files == null || files.length == 0)
    {
      return null;
    }
    File lastModifiedFile = files[0];
    for (File file : files)
    {
      if (lastModifiedFile.lastModified() < file.lastModified())
      {
        lastModifiedFile = file;
      }
    }
    this.logname = lastModifiedFile;
    return lastModifiedFile.toString();
  }

  /**
   * Perform cleaning of the log folder,
   * preserving the current log file and
   * informing the size of the log folder
   * before cleaning.
   * @return the size of the log folder
   * before cleaning, with proper measuring
   * unit.
   */
  public String cleanLogs()
  {
    File dir = new File(this.getLogDir().toString());
    String sizeBeforeClean = this.getLogSize();
    System.out.println("Limpando o diretório de logs \"" + dir.toString() + "\"...");
    File logToExcludeFromDeletion = new File(getLatestFileFromDir());
    File[] files = dir.listFiles();
    for (File file : files)
    {
      if(!((file.getName().equals(logToExcludeFromDeletion.getName()))
        | (file.getName().equals(getLogLock().getName()))))
      {
        FileUtils.deleteQuietly(file);
      }
    }
    return sizeBeforeClean;
  }



//  /* For Testing. */
//  public static void main(String[] args)
//  {
//    Logger l = new Logger();
//    l.setMessageFormat("yyyy/MM/dd HH:mm:ss");
//    l.setLogNameFormat("yyyy-MM-dd--HH.mm.ss");
//    l.setLogDir(new File("D:/"));
//    l.log(null, "INFO", "Início");
//    l.log(null, "INFO", "Teste1");
//    l.log(null, "INFO", "Teste2");
//    l.log(null, "INFO", "Teste3");
//    l.log(null, "INFO", "Teste4");
//    l.log(null, "INFO", "Teste5");
//    l.log(null, "INFO", "Teste6");
//    l.log(null, "INFO", "Teste7");
//    l.log(null, "INFO", "Teste8");
//    l.log(null, "INFO", "Teste9");
//    try
//    {
//      Thread.sleep(30000);
//    }
//    catch (InterruptedException ex)
//    {
//
//    }
//    l.log(null, "INFO", "Teste10");
//    l.log(null, "INFO", "Teste11");
//    l.log(null, "INFO", "Teste12");
//    l.log(null, "INFO", "Teste13");
////    try
////    {
////      FileUtils.writeStringToFile(new File("C:\blabla.txt"), "blabla", "UTF-8");
////    }
////    catch (IOException ex)
////    {
////      l.log(ex, "ERRO", "Teste de Exceção1");
////    }
//    l.log(null, "INFO", "Teste14");
//    l.log(null, "INFO", "Fim.");
//  }

}




