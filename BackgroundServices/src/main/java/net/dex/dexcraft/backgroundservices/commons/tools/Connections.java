package net.dex.dexcraft.backgroundservices.commons.tools;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


/**
 * Utility Class for connection tests.
 */
public class Connections
{

  private double speedMeterProgress = 0;
  private long downloadSpeed = 0;
  private long nominalUploadSpeed = 0;
  private NumberFormat formatter = new DecimalFormat("#0.0");
  private int EOF = -1;


  /**
   * Set the progress of the speed meter.
   * @param progress the progress value.
   */
  private void setSpeedMeterProgress(double progress)
  {
    this.speedMeterProgress = progress;
  }

  /**
   * Retrieve the speed meter progress.
   * @return the progress value.
   */
  private double getSpeedMeterProgress()
  {
    return this.speedMeterProgress;
  }

  /**
   * Define the download speed based on the test.
   * @param speed the download speed, in Mbps.
   */
  private void setDownloadSpeed(long speed)
  {
    this.downloadSpeed = speed;
  }

  /**
   * The nominal upload speed based on download speed
   * @param speed the upload speed, in Mbps.
   * @see #getNominalUploadSpeed(long)
   */
  private void setNominalUploadSpeed(long speed)
  {
    this.nominalUploadSpeed = speed;
  }

  /**
   * Retrieve nominal upload speed, based on a download.
   * @param url the url containing the file to download.
   * @param testFile the downloaded file name and location.
   * @return the nominal upload speed, in Mbps.
   * @see #getDownloadSpeed(java.lang.String, java.io.File)
   */
  public long getNominalUploadSpeed(String url, File testFile)
  {
    setNominalUploadSpeed(getDownloadSpeed(url, testFile) / 3);
    logger.log("INFO", "Upload Nominal: " + this.nominalUploadSpeed + "Mbps.");
    return this.nominalUploadSpeed;
  }

  /**
   * Retrieve the download speed based on a dummy file download.
   * @param url the url containing the file to download.
   * @param testFile the downloaded file name and location.
   * @return the download speed, in Mbps.
   */
  public long getDownloadSpeed(String url, File testFile)
  {
    logger.log("INFO", "Calculando a velocidade da internet. Aguarde...");
    Thread speedMeter = new Thread(()->
    {
      try
      {
        URL downloadURL = new URL (url);
        InputStream input = downloadURL.openStream();
        URLConnection urlConnection = downloadURL.openConnection();
        urlConnection.connect();
        long fileSize = urlConnection.getContentLength();
        FileOutputStream output = FileUtils.openOutputStream(testFile);
        long startTime = System.currentTimeMillis();
        byte[] buffer = new byte[1024 * 4];
        double divisor = 0;
        long count = 0;
        int n = 0;
        long currentTime = 0;
        while (EOF != (n = input.read(buffer)))
        {
          divisor = (double)fileSize / count ;
          setSpeedMeterProgress(100 / divisor);
          currentTime = System.currentTimeMillis();
          output.write(buffer, 0, n);
          count += n;
          if ((currentTime - startTime) > 20000)
          {
            logger.log("INFO", "ATENÇÃO: O TESTE DE DOWNLOAD EXCEDEU O TEMPO LIMITE.");
            EOF = input.read(buffer);
          }
        }
        setSpeedMeterProgress(100);
        output.close();
        input.close();
        long timeSpent = currentTime - startTime;
        if (timeSpent < 1) timeSpent = 1;
        if (EOF != -1)
        {
          setDownloadSpeed(3);
        }
        else
        {
          setDownloadSpeed(430000 / timeSpent);
        }
        EOF = -1;
      }
      catch (MalformedURLException ex)
      {
        alerts.exceptionHandler(ex, "EXCEÇÃO EM Connections.getDownloadSpeed(String, File)");
      }
      catch (IOException ex)
      {
        alerts.exceptionHandler(ex, "EXCEÇÃO EM Connections.getDownloadSpeed(String, File)");
      }
    });
    speedMeter.start();
    while(speedMeter.isAlive())
    {
      logger.log("INFO", "Calculando a velocidade da internet.... " + formatter.format(this.getSpeedMeterProgress()) + "% concluído.");
      try
      {
        Thread.sleep(1000);
      }
      catch (InterruptedException ex)
      {
        alerts.exceptionHandler(ex, "EXCEÇÃO EM Connections.getDownloadSpeed(String, File)");
      }
    }
    logger.log("INFO", "Calculando a velocidade da internet....100% concluído.");
    logger.log("INFO", "Download Nominal: " + this.downloadSpeed + "Mbps.");
    return this.downloadSpeed;
  }

  /**
   * Retrieve ping value from an address.
   * @param url the url to ping.
   * @return the ping value, in ms.
   */
  public String getPing(String url)
  {
    String result = null;
    try
    {
      ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "ping -n 1 " + url);
      result = IOUtils.toString(pb.start().getInputStream(), StandardCharsets.UTF_8);
      int ind = result.lastIndexOf("ms");
      result = result.substring(ind-3, ind);
      result = result.replace("=", "");
      result = result.trim();
      if (result.equals("0")) result = "1";
    }
    catch (IOException ex)
    {
      alerts.exceptionHandler(ex, "EXCEÇÃO EM Connections.getPing(String)");
    }
    return result;
  }

//  IMPLEMENTATION EXAMPLES
//
//  public static void main(String[] args)
//  {
//    Connections con = new Connections();
//    System.out.println("Download Speed: " + con.getDownloadSpeed("http://ipv4.ikoula.testdebit.info/10M.iso", new File("C:/10M.iso")) + "Mbps");
//    System.out.println("Upload Speed: " + con.getNominalUploadSpeed("http://ipv4.ikoula.testdebit.info/10M.iso", new File("C:/10M.iso")) + "Mbps");
//    int i = 0;
//    while (i == 0)
//    {
//      System.out.println("Ping to the address 8.8.8.8: " + con.getPing("8.8.8.8"));
//      try
//      {
//        Thread.sleep(2000);
//      }
//      catch (InterruptedException ex)
//      {
//        System.out.println(ex.getMessage());
//      }
//    }
//  }

}
