package net.dex.dexcraft.backgroundservices.commons.tools;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;


/**
 * Alert class with custom alerts, for Swing Applications.
 */
public class ErrorAlerts
{

  private Image image;

  private Throwable exceptionHandlerThrowable;
  private String exceptionHandlerContext;

  /**
   * SET the image for alert window icon.
   * @param img the image.
   */
  public void setImage(Image img)
  {
    this.image = img;
  }

  /**
   * GET the image for alert window icon.
   * @return the image.
   */
  private Image getImage()
  {
    return this.image;
  }

  /**
   * This alert is shown when the admin file
   * isn't found after being requested.
   * This is seen as a critical error and
   * the program can't keep running.
   */
  public void noAdmin()
  {
    logger.log("INFO", "Exibindo Alerts.NoAdmin()...");
    Alert alerts = new Alert(Alert.AlertType.ERROR);
    alerts.initModality(Modality.APPLICATION_MODAL);
    Stage stage = (Stage) alerts.getDialogPane().getScene().getWindow();
    stage.getIcons().add(getImage());
    stage.setOnCloseRequest((e) ->
    {
      Close.quit();
    });
    alerts.getButtonTypes().clear();
    alerts.setTitle("Erro de Inicialização");
    alerts.setHeaderText("Sistema Anti-Palles™");
    alerts.setContentText("O Launcher não foi \"executado como Administrador\".\n"
                          + "Isso pode interferir em diversas funções, como instalação, atualização, verificação de arquivos e produção de memes.\n\n"
                          + "Se certifique em clicar no atalho do DexCraft Launcher com o botão direito do mouse e depois em \"Executar como Administrador\".");
    ButtonType btnok = new ButtonType("OK");
    alerts.getButtonTypes().add(btnok);
    logger.log("INFO", "Aguardando resposta do usuário...");
    Optional<ButtonType> result = alerts.showAndWait();
    if (result.get() == btnok)
    {
      Close.quit();
    }
    logger.log("INFO", "Alerts.NoAdmin() finalizado");
  }

  /**
   * This alert is shown when some critical
   * error is triggered. It can be used when
   * isn't needed a specific message for an
   * error, but needs to close the program
   * anyway.
   */
  public void tryAgain()
  {
    Platform.runLater(()->
    {
      logger.log("INFO", "Exibindo Alerts.TryAgain()...");
      Alert alerts = new Alert(Alert.AlertType.ERROR);
      alerts.initModality(Modality.APPLICATION_MODAL);
      Stage stage = (Stage) alerts.getDialogPane().getScene().getWindow();
      stage.getIcons().add(getImage());
      stage.setOnCloseRequest((e) -> {Close.withErrors();});
      alerts.getButtonTypes().clear();
      alerts.setTitle("Erro Crítico");
      alerts.setHeaderText("Houve um erro crítico durante a execução do DexCraft Launcher.");
      alerts.setContentText("Tente iniciar o Launcher novamente.");
      ButtonType btnok = new ButtonType("Sair");
      alerts.getButtonTypes().add(btnok);
      logger.log("INFO", "Aguardando resposta do usuário...");
      Optional<ButtonType> result = alerts.showAndWait();
      if (result.get() == btnok)
      {
        Close.withErrors();
      }
      logger.log("INFO", "Alerts.TryAgain() finalizado");
    });
  }

  /**
   * This alert is shown when the double instance
   * lock file is found after the program being
   * launched.
   * The first program keeps running while the second
   * is closed.
   */
  public void doubleInstance()
  {
    Platform.runLater(()->
    {
      System.out.println("Exibindo Alerts.DoubleInstance()...");
      Alert alerts = new Alert(Alert.AlertType.ERROR);
      alerts.initModality(Modality.APPLICATION_MODAL);
      Stage stage = (Stage) alerts.getDialogPane().getScene().getWindow();
      stage.getIcons().add(getImage());
      stage.setOnCloseRequest((e) -> {System.exit(0);});
      alerts.getButtonTypes().clear();
      alerts.setTitle("Erro de Inicialização");
      alerts.setHeaderText("Sistema Anti-Palles™ v2.0");
      alerts.setContentText("O Launcher já está sendo executado.\n"
                            + "Feche a atual janela do Launcher e abra o programa novamente.");
      ButtonType btnok = new ButtonType("OK");
      alerts.getButtonTypes().add(btnok);
      System.out.println("Aguardando resposta do usuário...");
      Optional<ButtonType> result = alerts.showAndWait();
      if (result.get() == btnok)
      {
        System.out.println("Encerrando...");
        System.exit(0);
      }
      System.out.println("Alerts.DoubleInstance() finalizado");
    });
  }

  /**
   * This alert is shown when the core file isn't
   * found, even after it had been downloaded.
   */
  public void noCoreFile()
  {
    Platform.runLater(()->
    {
      logger.log("INFO", "Exibindo Alerts.noCoreFile()...");
      Alert alerts = new Alert(Alert.AlertType.ERROR);
      alerts.initModality(Modality.APPLICATION_MODAL);
      Stage stage = (Stage) alerts.getDialogPane().getScene().getWindow();
      stage.getIcons().add(getImage());
      stage.setOnCloseRequest((e) -> {Close.withErrors();});
      alerts.getButtonTypes().clear();
      alerts.setTitle("Erro do CoreFile");
      alerts.setHeaderText("O CoreFile não pôde ser baixado ou carregado no sistema. Causa desconhecida.");
      alerts.setContentText("Tente iniciar o DexCraft Launcher novamente.");
      ButtonType btnok = new ButtonType("Sair");
      alerts.getButtonTypes().add(btnok);
      logger.log("INFO", "Aguardando resposta do usuário...");
      Optional<ButtonType> result = alerts.showAndWait();
      if (result.get() == btnok)
      {
        Close.withErrors();
      }
      logger.log("INFO", "Alerts.noCoreFile() finalizado");
    });
  }

  /**
   * Handles the exception message and throwable, putting it
   * on a window for the user.
   * @param ex the exception throwable
   * @param exceptionMessage the message of the error<br>
   * Creates a customized window to show exceptions
   * and errors to the user.
   */
  public void exceptionHandler(Throwable ex, String exceptionMessage)
  {
    Platform.runLater(()->
    {
      logger.log(ex, exceptionMessage);
      logger.log("INFO", "Exibindo Alerts.exceptionHandler(Throwable, String)");
      exceptionHandlerThrowable = ex;
      exceptionHandlerContext = exceptionMessage;
      Alert alerts = new Alert(Alert.AlertType.ERROR);
      alerts.setTitle("ERRO");
      alerts.setHeaderText("EXCEÇÃO - " + exceptionHandlerThrowable.getMessage());
      alerts.setContentText(exceptionHandlerContext);
      Stage stage = (Stage) alerts.getDialogPane().getScene().getWindow();
      stage.getIcons().add(getImage());
      stage.setOnCloseRequest((e) -> {e.consume();});
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      exceptionHandlerThrowable.printStackTrace(pw);
      String exceptionText = sw.toString();

      Label label = new Label("Descrição completa do erro:");

      TextArea textArea = new TextArea(exceptionText);
      textArea.setEditable(false);
      textArea.setWrapText(true);

      textArea.setMaxWidth(Double.MAX_VALUE);
      textArea.setMaxHeight(Double.MAX_VALUE);
      GridPane.setVgrow(textArea, Priority.ALWAYS);
      GridPane.setHgrow(textArea, Priority.ALWAYS);

      GridPane expContent = new GridPane();
      expContent.setMaxWidth(Double.MAX_VALUE);
      expContent.add(label, 0, 0);
      expContent.add(textArea, 0, 1);

      alerts.getDialogPane().setExpandableContent(expContent);

      logger.log("INFO", "Aguardando resposta do usuário...");

      alerts.showAndWait();

      logger.log("INFO", "Alerts.exceptionHandler(Throwable, String) finalizado");
    });
  }

}
