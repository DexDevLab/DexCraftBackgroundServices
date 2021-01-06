package net.dex.dexcraft.backgroundservices.commons.tools;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import net.dex.dexcraft.backgroundservices.commons.dto.FtpDTO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


/**
 * Utility Class for FTP connection.
 */
public class FtpUtils
{

  private String address;
  private int port;
  private String user;
  private String password;
  private String workingDir;
  private FTPClient ftp = new FTPClient();

  private NumberFormat formatter = new DecimalFormat("#0.0");
  private NumberFormat formatter2 = new DecimalFormat("#0.00");

  double progress = 0;
  long currentTime = 0;
  long startTime = 0;
  private String transferredSize = "";
  private String totalSize = "";
  private String timeEstimatedMsg = "";
  private String estimatedHours = "";
  private String estimatedMinutes = "";
  private String estimatedSeconds = "";
  private String progressPercent = "";
  private String transferSpeed = "";

  /**
   * GET the FTP Server's address.
   * @return the server address.
   */
  public String getAddress()
  {
    return address;
  }

  /**
   * SET the FTP Server's address.
   * @param address the server address.
   */
  public void setAddress(String address)
  {
    this.address = address;
  }

  /**
   * GET the FTP Server's port.
   * @return the server port.
   */
  public int getPort()
  {
    return port;
  }

  /**
   * SET the FTP Server's port.
   * @param port the server oort.
   */
  public void setPort(int port)
  {
    this.port = port;
  }

  /**
   * GET the FTP connection user
   * @return the user
   */
  public String getUser()
  {
    return user;
  }

  /**
   * SET the FTP Server's user
   * @param user the connection user
   */
  public void setUser(String user)
  {
    this.user = user;
  }

  /**
   * GET the FTP connection password.
   * @return the server password
   */
  public String getPassword()
  {
    return password;
  }

  /**
   * SET the FTP Server's password
   * @param password the connection password.
   */
  public void setPassword(String password)
  {
    this.password = password;
  }

  /**
   * GET the FTP connection's working directory
   * @return the working directory.
   */
  public String getWorkingDir()
  {
    return workingDir;
  }

  /**
   * SET the FTP connection's working directory
   * @param workingDir the working directory
   */
  public void setWorkingDir(String workingDir)
  {
    this.workingDir = workingDir;
  }


    /**
   * Get the downloaded file size.
   * @return the size of the downloaded file so far, in bytes.
   */
  public String getTransferredSize() { return this.transferredSize; }

  /**
   * Set the downloaded file size.
   * @param value the size of the downloaded file so far, in bytes.
   */
  private void setTransferredSize(String value) { this.transferredSize = value; }

  /**
   * Get the size of the file on the source.
   * @return the size of the file, in bytes.
   */
  public String getTotalSize() { return this.totalSize; }

  /**
   * Set the size of the file on the source.
   * @param value the size of the file, in bytes.
   */
  private void setTotalSize(String value) { this.totalSize = value; }

  /**
   * Get the text informing the size of the download so far,<br>
   * how much time it will take to the downloaded be completed,<br>
   * and the total download size.
   * @return the download information and progress.
   */
  public String getTimeEstimatedMsg() { return this.timeEstimatedMsg; }

  /**
   * Set the text informing the size of the download so far,<br>
   * how much time it will take to the downloaded be completed,<br>
   * and the total download size.
   * @param value the download information and progress.
   */
  private void setTimeEstimatedMsg(String value) { this.timeEstimatedMsg = value; }

  /**
   * Get how many hours are needed to finish the download.
   * @return the hours remaining.
   */
  public String getEstimatedHours() { return this.estimatedHours; }

  /**
   * Set how many hours are needed to finish the download.
   * @param value the hours remaining
   */
  private void setEstimatedHours(String value) { this.estimatedHours = value; }

  /**
   * Get how many minutes are needed to finish the download.
   * @return the minutes remaining.
   */
  public String getEstimatedMinutes() { return this.estimatedMinutes; }

  /**
   * Set how many minutes are needed to finish the download.
   * @param value the minutes remaining
   */
  private void setEstimatedMinutes(String value) { this.estimatedMinutes = value; }

  /**
   * Get how many seconds are needed to finish the download.
   * @return the seconds remaining.
   */
  public String getEstimatedSeconds() { return this.estimatedSeconds; }

  /**
   * Set how many seconds are needed to finish the download.
   * @param value the seconds remaining
   */
  private void setEstimatedSeconds(String value) { this.estimatedSeconds = value; }

  /**
   * Get the download progress so far.
   * @return the progress value in percent.
   */
  public String getProgressPercent()
  {
    if ((this.progressPercent == null) || (this.progressPercent.isEmpty()))
    {
      setProgressPercent("0");
    }
    return this.progressPercent;
  }

  /**
   * Set the download progress so far.
   * @param value the progress value in percent.
   */
  private void setProgressPercent(String value) { this.progressPercent = value; }

  /**
   * Get the download speed.
   * @return the speed in a proper measure unit.
   */
  public String getTransferSpeed() { return this.transferSpeed; }

  /**
   * Set the download speed.
   * @param value the speed in a proper measure unit.
   */
  public void setTransferSpeed(String value) { this.transferSpeed = value; }


  /**
   * Connect to the FTP Server.
   */
  public void connect()
  {
    try
    {
      ftp.connect(getAddress(), getPort());
      ftp.login(getUser(), getPassword());
      ftp.enterLocalPassiveMode();
      ftp.changeWorkingDirectory(getWorkingDir());
      logger.log("INFO", "FTP: Conectado ao Servidor FTP.");
    }
    catch (IOException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO em FtpUtils.connect()");
    }
  }

  /**
   * Disconnect FTP Server.
   */
  public void disconnect()
  {
    try
    {
      if (ftp.isConnected())
      {
        ftp.disconnect();
      }
      logger.log("INFO", "FTP: Desconectado.");
    }
    catch (IOException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO em FtpUtils.connect()");
    }
  }

  /**
   * Deletes a file in server.
   * @param pathname the pathname of the file.
   */
  public void deleteFile(String pathname)
  {
    try
    {
      ftp.deleteFile(pathname);
    }
    catch (IOException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO em FtpUtils.connect()");
    }
  }


  /**
   * Verifies the existance of a directory on FTP Server.<br>
   * If it does not exist, it will be created.
   * @param dir the absolute path of the remote directory.
   */
  public void checkFolder(String dir)
  {
    try
    {
      if (ftp.cwd(dir) != 250)
      {
        ftp.cwd(FtpDTO.getFtpWorkingDir());
        ftp.makeDirectory(dir);
      }
    }
    catch (IOException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO em FtpUtils.checkFolder(String)");
    }
  }

  /**
   * Check if specific file exists in remote server.
   * @param file the file or folder to verify, in absolute path.
   * @return if the file exists (true) or not (false).
   */
  public boolean fileExists(File file)
  {
    try
    {
      ftp.changeWorkingDirectory(file.getParent());
      FTPFile[] files = ftp.listFiles(file.getName());
      ftp.changeWorkingDirectory(FtpDTO.getFtpWorkingDir());
      return files.length > 0;
    }
    catch (IOException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO em FtpUtils.fileExists(String)");
    }
    return false;
  }


  /**
   * Uploads a single file to the FTP Server.
   * @param remoteDestPath the remote directory destination relative path.<br>
   * This directory will be verified before trying to upload file into it. If it
   * doesn't exist, it will be created.
   * @param localFilePath the local file path, in absolute path.
   * @return the upload status, as true if succeeded and false if not.
   */
  private boolean uploadFile(String remoteDestPath, String localFilePath)
  {
    checkFolder(getWorkingDir() + "/" + remoteDestPath);
    File localFile = new File(localFilePath);
    try (InputStream inputStream = new FileInputStream(localFile))
    {
      File check = new File(getWorkingDir() + "/" + remoteDestPath + "/" + localFile.getName());
      File checkOld = new File(getWorkingDir() + "/" + remoteDestPath + "/" + localFile.getName() + ".old");
      if (fileExists(checkOld))
      {
        ftp.deleteFile(checkOld.toString());
      }
      if (fileExists(check))
      {
        ftp.rename(check.toString(), checkOld.toString());
      }
      ftp.setFileType(FTP.BINARY_FILE_TYPE);
      boolean upload = ftp.storeFile(check.toString(), inputStream);
      if (upload)
      {
        ftp.deleteFile(checkOld.toString());
        logger.log("INFO", "FTP: Arquivo " + localFile.getName() + " enviado com sucesso para " + getWorkingDir() + "/" + remoteDestPath + ".");
        return true;
      }
    }
    catch (FileNotFoundException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO em FtpUtils.uploadFile(String, String, String)");
    }
    catch (IOException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO em FtpUtils.uploadFile(String, String, String)");
    }
    logger.log("***ERRO***", "FALHA CRÍTICA NO PROCESSO DE UPLOAD DO ARQUIVO " + localFilePath);
    alerts.tryAgain();
    return false;
  }

  /**
   * Uploads a single file to the FTP Server.
   * @param remoteDestPath the remote directory destination relative path.<br>
   * This directory will be verified before trying to upload file into it. If it
   * doesn't exist, it will be created.
   * @param localFilePath the local file path, in absolute path.
   */
  public void uploadFileWithProgress(String remoteDestPath, String localFilePath)
  {
    logger.log("INFO", "FTP: Iniciando upload do arquivo " + localFilePath + "...");
    File localFile = new File(localFilePath);
    long fileSize = localFile.length();
    startTime = System.currentTimeMillis();
    progress = 0;
    Thread upload = new Thread(()->
    {
      uploadFile(remoteDestPath, localFilePath);
    });
    upload.start();
    while (upload.isAlive())
    {
      long size = 0;
      try
      {
        currentTime = System.currentTimeMillis();
        String reply = "";
        try
        {
          File check = new File(getWorkingDir() + "/" + remoteDestPath + "/" + localFile.getName());
          ftp.sendCommand("SIZE", check.toString());
          reply = ftp.getReplyString();
          if ( (!reply.contains("File")) && (!reply.contains("transferred")) )
          {
            reply = reply.trim();
            reply = reply.substring(4, reply.length());
            size = Long.parseLong(reply);
          }
        }
        catch (IOException x)
        {
          System.out.println(reply);
          size = 1;
        }
        double divisor = (fileSize / 100);
        progress = (size / divisor);
        showProgress(progress,fileSize, size, startTime, currentTime);
        Thread.sleep(1000);
      }
      catch (InterruptedException ex)
      {
        alerts.exceptionHandler(ex, "EXCEÇÃO em FtpUtils.uploadFileWithProgress(String, String)");
      }
    }
    progress = 100;
    showProgress(progress, fileSize, fileSize, startTime, currentTime);
  }

  /**
   * Downloads a single file from the FTP Server.
   * @param remoteSourceDir the directory which contains the file (relative path).
   * @param destinationFile the absolute path of the downloaded file.
   * @return the download status, as true if succeeded and false if not.
   */
  private boolean downloadFile(String remoteSourceDir, File destinationFile)
  {
    try
    {
      ftp.cwd(getWorkingDir() + "/" + remoteSourceDir);
      if (!destinationFile.exists())
      {
        FileUtils.touch(destinationFile);
      }
      OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destinationFile));
      boolean success = ftp.retrieveFile(destinationFile.getName(), outputStream);
      outputStream.close();
      if (!success)
      {
        logger.log("***ERRO***", "FTP: ERRO DESCONHECIDO DURANTE O DOWNLOAD.");
        alerts.tryAgain();
      }
    }
    catch (IOException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO EM FtpUtils.downloadFile(String, File)");
    }
    return false;
  }


  /**
   * Downloads a single file from the FTP Server.
   * @param remoteSourceDir the directory which contains the file (relative path).
   * @param destinationFile the absolute path of the downloaded file.
   */
  public void downloadFileWithProgress(String remoteSourceDir, File destinationFile)
  {
    try
    {
      logger.log("INFO", "FTP: Iniciando download do arquivo " + destinationFile + "...");
      File check = new File(getWorkingDir() + "/" + remoteSourceDir + "/" + destinationFile.getName());
      if (!fileExists(check))
      {
        logger.log("***ERRO***", "FTP: ARQUIVO SOLICITADO NÃO ENCONTRADO.");
        alerts.tryAgain();
      }
      FTPFile file = ftp.mlistFile(getWorkingDir() + "/" + remoteSourceDir + "/" + destinationFile.getName());
      long size = 0;
      try
      {
        size = file.getSize();
      }
      catch (java.lang.NullPointerException x)
      {
        size = 1;
      }
      startTime = System.currentTimeMillis();
      progress = 0;
      Thread download = new Thread(()->
      {
        downloadFile(remoteSourceDir, destinationFile);
      });
      download.start();
      while (download.isAlive())
      {
        try
        {
          currentTime = System.currentTimeMillis();
          long fileSize = destinationFile.length();
          if (fileSize < 1)
          {
            fileSize = 1;
          }
          double divisor = (size / 100);
          progress = (fileSize / divisor);
          showProgress(progress, size, fileSize, startTime, currentTime);
          Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
          alerts.exceptionHandler(ex, "EXCEÇÃO EM FtpUtils.downloadFileWithProgress(String, File)");
        }
      }
      progress = 100;
      showProgress(progress, size, size, startTime, currentTime);
    }
    catch (IOException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO EM FtpUtils.downloadFileWithProgress(String, File)");
    }
  }


  /**
   * Updates the transfer progress using proper byte and time measures.
   * @param progressPercent the progress, in percent.
   * @param fileSize the download size.
   * @param transferredSize the size of the file transferred so far.
   * @param startTime the timestamp which defines when the transfer started.
   * @param currentTime the current timestamp to compare how many miliseconds has passed.
   */
  private void showProgress(double progressPercent, long fileSize, long transferredSize, long startTime, long currentTime)
  {
    long downloadedTime = currentTime - startTime;
    long transferredSizeDiff = (fileSize - (transferredSize/4096));
    if (downloadedTime >= 1000)
    {
      int currentTimeInSeconds = Math.round(downloadedTime /1000);
      double bytesPerSecond = Math.round(transferredSize / currentTimeInSeconds);
      long totalSecondsRemaining = Math.round((transferredSizeDiff / bytesPerSecond) - currentTimeInSeconds);
      int hoursRemaining = 0;
      int minutesRemaining = 0;
      String measurement = "B/s";
      if (bytesPerSecond > 1024)
      {
        measurement = "KB/s";
        bytesPerSecond /= 1024;
        if (bytesPerSecond > 1024)
        {
          measurement = "MB/s";
          bytesPerSecond /= 1024;
        }
      }
      long fileSizeCalculated = 0;
      long transferredSizeCalculated = 0;
      String fileSizeMeasurement = "B";
      String transferredSizeMeasurement = "B";
      if (fileSize >= 1048576)
      {
        fileSizeCalculated = fileSize / 1048576;
        fileSizeMeasurement = "MB";
      }
      else if (fileSize >= 1024)
      {
        fileSizeCalculated = fileSize / 1024;
        fileSizeMeasurement = "KB";
      }
      if (transferredSize >= 1048576)
      {
        transferredSizeCalculated = transferredSize / 1048576;
        transferredSizeMeasurement = "MB";
      }
      else if (transferredSize >= 1024)
      {
        transferredSizeCalculated = transferredSize / 1024;
        transferredSizeMeasurement = "KB";
      }
      String progressOutput = formatter2.format(progressPercent);
      setProgressPercent(progressOutput);
      setTransferredSize(transferredSizeCalculated + transferredSizeMeasurement);
      setTotalSize(fileSizeCalculated + fileSizeMeasurement);
      setTransferSpeed(formatter.format(bytesPerSecond) + measurement);
      setEstimatedHours("");
      setEstimatedMinutes("");
      setEstimatedSeconds("");
      if (totalSecondsRemaining >= 3600)
      {
        totalSecondsRemaining /= 3600;
        totalSecondsRemaining = Math.round(totalSecondsRemaining);
        hoursRemaining = (int) totalSecondsRemaining;
        if (hoursRemaining >= 9999)
        {
          hoursRemaining = 0;
        }
        else
        {
          setEstimatedHours(hoursRemaining + " hora(s), ");
        }
      }
      if (totalSecondsRemaining >= 60)
      {
        totalSecondsRemaining /= 60;
        totalSecondsRemaining = Math.round(totalSecondsRemaining);
        minutesRemaining = (int) totalSecondsRemaining;
        if (minutesRemaining >= 9999)
        {
          minutesRemaining = 0;
        }
        else
        {
          setEstimatedMinutes(minutesRemaining + " minuto(s) e ");
        }
      }
      if (totalSecondsRemaining > 99)
      {
        totalSecondsRemaining = 0;
      }
      else
      {
        setEstimatedSeconds(totalSecondsRemaining + " segundo(s) ");
      }
      if ( (hoursRemaining == 0) && (minutesRemaining == 0) && (totalSecondsRemaining == 0) )
      {
        setTimeEstimatedMsg("Concluindo...");
      }
      // next line follows an example of message to estimated time and their values //
      setTimeEstimatedMsg(getEstimatedHours() + getEstimatedMinutes() + getEstimatedSeconds()
          + "restante(s), " + getTransferredSize() + " / " + getTotalSize() + ", " + getTransferSpeed()
          + ", " + getProgressPercent() + "% concluído");
    }
    else
    {
      setTimeEstimatedMsg("Aguarde...");
    }
  }


//FTPClient uploadBkp = new FTPClient();
//          uploadBkp.connect(serverAddress,Integer.parseInt(serverPort));
//          uploadBkp.login(serverUser, serverPassword);
//          uploadBkp.enterLocalPassiveMode();
//          uploadBkp.changeWorkingDirectory (serverPlayerBkp + "/");
//          uploadBkp.makeDirectory(loginuser + ".new");
//          updateTitle("Enviando arquivos. Aguarde...");
//          Thread uploadInstance = new Thread(() ->
//          {
//            try
//            {
//              fileCheck1 = uploadSingleFile(uploadBkp, bkpzip.toString(), serverPlayerBkp + "/" + loginuser + ".new" + "/bkp.zip");
//            }
//            catch (IOException ex)
//            {
//              L.log(null,"ERRO", "ERRO CRÍTICO EM stepDoBkp() - FALHA AO ENVIAR ARQUIVOS");
//            }
//          });
//          uploadInstance.start();
//          monitorUpload(uploadBkp, bkpzip);
//          percent = 1;
//          value = 1;
//          while (percent <101)
//          {
//            try
//            {
//              softProgress(1, percent, 10);
//              Thread.sleep(1000);
//              if (percent == 100) { percent = 101;}
//            }
//            catch (InterruptedException ex)
//            {
//              L.log(ex, "ERRO", "EXCEÇÃO EM UploadProgress THREAD - FALHA NA INTERRUPÇÃO");
//            }
//          }
//          percent = 1;
//          value = -1;
//          updateMessage("");
//          updateProgress(value, 100);
//          try
//          {
//            if (uploadBkp.isConnected())
//            {
//              uploadBkp.logout();
//              uploadBkp.disconnect();
//            }
//          }
//          catch (IOException ex)
//          {
//            L.log(ex, "ERRO", "EXCEÇÃO EM stepValidateCredentials() - IMPOSSÍVEL DESCONECTAR A CONEXÃO FtpUtils EM ABERTO");
//          }

}
