package net.dex.dexcraft.backgroundservices.commons.tools;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;


/**
 * Class for File Operations with logging.
 */
public class FileIO
{

  private int fileIoErrorCode = 0;
  private File src;
  private File dest;

  /**
   * Copy file or folder operation.
   * @param source the file or folder to be copied.
   * @param destination the copy destination.
   */
  public void copiar(File source, File destination)
  {
    src = new File (source.toString());
    dest = new File (destination.toString());
    if (src.isDirectory())
    {
      if (!dest.isFile())
      {
        if (!dest.exists()) {dest.mkdirs();}
        try
        {
          logger.log("INFO", "Copiando o diretório " + src.toString() + " para o diretório " + dest.toString() + "...");
          FileUtils.copyDirectory(src, dest);
          Iterator<File> files = FileUtils.iterateFilesAndDirs(src,new WildcardFileFilter("*.*"), new WildcardFileFilter("*"));
          files.forEachRemaining((f)->
          {
            logger.log("INFO", "Copiado arquivo/diretório " + f + "");
          });
        }
        catch (IOException ex)
        {
          alerts.exceptionHandler(ex, "EXCEÇÃO EM FileIO.copiar(File, File)");
          // Close.withErrors();
        }
      }
      else
      {
        error(1);
      }
    }
    else
    {
      if (dest.isDirectory())
      {
        if (!dest.exists()) {dest.mkdirs();}
        try
        {
          logger.log("INFO", "Copiando o arquivo " + src.toString() + " para o diretório " + dest.toString() + "");
          FileUtils.copyFileToDirectory(src, dest);
        }
        catch (IOException ex)
        {
          alerts.exceptionHandler(ex, "EXCEÇÃO EM FileIO.copiar(File, File)");
          // Close.withErrors();
        }
      }
      else
      {
        try
        {
          logger.log("INFO", "Copiando o arquivo " + src.toString() + " para " + dest.toString() + "");
          FileUtils.copyFile(src, dest);
        }
        catch (IOException ex)
        {
          alerts.exceptionHandler(ex, "EXCEÇÃO EM FileIO.copiar(File, File)");
          // Close.withErrors();
        }
      }
    }
  }

  /**
   * Move file or folder operation.
   * @param source the file or folder to be moved.
   * @param destination the name and location of the file or folder.
   */
  public void mover(File source, File destination)
  {
    src = new File (source.toString());
    dest = new File (destination.toString());
    logger.log("INFO", "Iniciando movimentação: de " + src.toString() + " para " + dest.toString() + "");
    if (!dest.exists()) {dest.mkdirs();}
    copiar(src, dest);
    if (src.isDirectory())
    {
      excluir(src, true);
    }
    else
    {
      excluir(src, false);
    }
    if (src.exists())
    {
      logger.log("***ERRO***", "EXCEÇÃO EM FileIO.mover(File, File) - ARQUIVO DE ORIGEM " + src.toString() + " AINDA EXISTE");
    }
  }

  /**
   * Delete a file or a folder recursively.
   * @param source the file or folder to be deleted.
   * @param includeParentDir if is desired to delete the
   * parent directory (true) or keep it (false) during
   * exclusion.
   */
  public void excluir(File source, boolean includeParentDir)
  {
    src = new File (source.toString());
    if (src.exists())
    {
      try
      {
      Stream<Path> files = Files.walk(src.toPath());
      files.sorted(Comparator.reverseOrder()).map(Path::toFile)
        .forEach((file) ->
        {
          if (file.isDirectory())
          {
            try
            {
              if((file.toString()).equals(src.toString()))
              {
                if(includeParentDir)
                {
                  logger.log("INFO", "Excluindo diretório pai " + src.toString() + "...");
                  FileUtils.deleteDirectory(file);
                }
              }
            }
            catch (IOException ex)
            {
              logger.log(ex, "EXCEÇÃO em FileIO.excluir(File, boolean)");
              error(3);
            }
          }
          else
          {
            logger.log("INFO", "Excluindo arquivo " + file.toString() + "...");
            FileUtils.deleteQuietly(file);
          }
        });
      files.close();
      }
      catch (IOException ex)
      {
        logger.log(ex, "EXCEÇÃO em FileIO.excluir(File, boolean)");
        error(3);
      }
      if (src.exists())
      {
        if ((src.isFile()) | (includeParentDir))
        {
          logger.log("***ERRO***","Não foi possível remover SOURCE " + src.toString() + "");
          error(3);
        }
      }
    }
    else
    {
      logger.log("***ERRO***", "SOURCE " + src.toString() + " não existe.");
      error(4);
    }
  }

  /**
   * Throw errors depending of the error code.
   * @param errorCode the error code.
   * @see net.dex.dexcraft.launcher.tools.FileIO.FileIOError
   * @see #fileIoErrorCode
   */
  private void error(int errorCode)
  {
    fileIoErrorCode = errorCode;
    FutureTask<String> fileIoError = new FutureTask<>(new FileIOError());
    logger.log("INFO", "Exibindo alerta de erro FileIOError()...");
    Platform.runLater(fileIoError);
    logger.log("INFO", "Aguardando resposta do usuário...");
    alertLock(fileIoError);
    logger.log("INFO", "Alerta FileIOError() encerrado.");
  }

  /**
   * Prevents thread keep running even before user
   * interaction.
   * @param futureTask the alert to being observed by its
   * Future Task
   */
  private static void alertLock(FutureTask<String> futureTask)
  {
    while(!futureTask.isDone())
    {
      try
      {
        Thread.sleep(1500);
      }
      catch (InterruptedException ex)
      {
        logger.log(ex,"EXCEÇÃO EM FileIO.error().alertLock(FutureTask<String>) - THREAD INTERROMPIDA");
      }
    }
  }


  /**
   * Calls an alert for errors during file
   * operation:<br>
   * 1 - error on copying files or folders (source is a folder but destination is a file).<br>
   * 2 - error on moving files or folders (can't remove source).<br>
   * 3 - error on deleting files or folders (source can't be removed).<br>
   * 4 - error on deleting files or folders (source does not exist).
   * @see #fileIoErrorCode
   * @see #error(int)
   */
  class FileIOError implements Callable
  {

    @Override
    public FileIOError call() throws Exception
    {
      Alert alerts = new Alert(Alert.AlertType.ERROR);
      alerts.initModality(Modality.APPLICATION_MODAL);
      Stage stage = (Stage) alerts.getDialogPane().getScene().getWindow();
      stage.setOnCloseRequest((e) -> {e.consume();});
      alerts.getButtonTypes().clear();
      alerts.setTitle("ERRO CRÍTICO NO PROCESSO");
      switch (fileIoErrorCode)
      {
        case 1:
          alerts.setHeaderText("Falha durante processo de cópia.");
          alerts.setContentText("SOURCE " + src.toString() + " é um diretório, mas DESTINATION " + dest.toString() + " é um arquivo.");
          break;
        case 2:
          alerts.setHeaderText("Falha durante processo de mover.");
          alerts.setContentText("Não foi possível remover SOURCE " + src.toString() + " após copiar.");
          break;
        case 3:
          alerts.setHeaderText("Falha durante processo de excluir.");
          alerts.setContentText("Não foi possível remover SOURCE " + src.toString() + "");
          break;
        case 4:
          alerts.setHeaderText("Falha durante processo de excluir");
          alerts.setContentText("SOURCE " + src.toString() + " não existe.");
          break;
        default:
          break;
      }
      ButtonType btnok = new ButtonType("OK");
      alerts.getButtonTypes().add(btnok);
      Optional<ButtonType> result = alerts.showAndWait();
      if (result.get() == btnok)
      {
        fileIoErrorCode = 0;
        // Close.withErrors();
      }
      return null;
    }
  }

}
