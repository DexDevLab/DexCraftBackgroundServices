package net.dex.dexcraft.backgroundservices.commons.tools;


import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * UI Tool to interact with the user,
 * as far from default thread as possible.
 */
public class DexUI
{
  private double IMAGE_OPACITY = 1.0;
  private double IMAGE_FIT_WIDTH = 810;
  private double IMAGE_FIT_HEIGHT = 570;
  private Image imgToChange;


  private Stage stg;
  private ImageView mainImageView;

  private ProgressBar pbar;
  private double globalProgressValue = 0;

  private Label mainLb;
  private Label secLb;
  private TextField mainTf;
  private PasswordField mainPf;

  private Button mainBt;
  private MenuBar menuB;

  private ImageView pingIcon;
  private Label pingLabel;
  private Label pingLabelTooltip;


  /**
   * GET the Ping Icon for ping monitoring utility.
   * @return the Ping Icon.
   */
  public ImageView getPingIcon()
  {
    return this.pingIcon;
  }

  /**
   * GET the label which shows current ping<br>
   * on ping monitoring utility.
   * @return the label.
   */
  public Label getPingLabel()
  {
    return this.pingLabel;
  }

  /**
   * GET the label which will have tooltip about ping.
   * @return the label.
   */
  public Label getPingLabelTooltip()
  {
    return this.pingLabelTooltip;
  }

  /**
   * SET the Ping Icon for ping monitoring utility.
   * @param icon the Ping Icon.
   */
  public void setPingIcon(ImageView icon)
  {
    this.pingIcon = icon;
  }

  /**
   * SET the label which shows current ping<br>
   * on ping monitoring utility.
   * @param lb the label.
   */

  public void setPingLabel(Label lb)
  {
    this.pingLabel = lb;
  }

  /**
   * SET the label which will have tooltip about ping.
   * @param lb the label.
   */
  public void setPingLabelTooltip(Label lb)
  {
    this.pingLabelTooltip = lb;
  }

  /**
   * GET the Image that need to be changed.
   * @return the Image.
   */
  public Image getImgToChange()
  {
    return this.imgToChange;
  }

  /**
   * GET the Stage.
   * @return the Stage.
   */
  public Stage getStage()
  {
    return this.stg;
  }

  /**
   * SET the Stage.
   * @param stg the Stage.
   */
  public void setStage(Stage stg)
  {
    this.stg = stg;
  }

  /**
   * GET the Image View of the interface.
   * @return the Image View.
   */
  public ImageView getMainImageView()
  {
    return mainImageView;
  }

  /**
   * SET the Image View of the interface.
   * @param mainImg the Image View of the interface.
   */
  public void setMainImageView(ImageView mainImg)
  {
    this.mainImageView = mainImg;
  }

  /**
   * GET the Image from the ImageView.
   * @return the Image from the ImageView.
   */
  public Image getImage()
  {
    return this.mainImageView.getImage();
  }

   /**
   * Retrieve the UI's menu bar.
   * @return the menu bar
   */
  public MenuBar getMenuBar()
  {
    return this.menuB;
  }

  /**
   * Retrieve the UI's button.
   * @return the button
   */
  public Button getMainButton()
  {
    return this.mainBt;
  }

  /**
   * Sets the image, changing it with an animation.
   * @param img the new image.
   */
  public void setImage(Image img)
  {
    this.imgToChange = img;
    AnimationTimer fadeout = new FadeoutTimer();
    fadeout.start();
  }

  /**
   * Define the UI's Progress Bar.
   * @param pb the Progress Bar
   */
  public void setProgressBar(ProgressBar pb)
  {
    this.pbar = pb;
  }

  /**
   * Define the UI's main label.<br>
   * The main label is a label which informs
   * to the user the most part of the processes.
   * @param lb the main label
   */
  public void setMainLabel(Label lb)
  {
    this.mainLb = lb;
  }

  /**
   * Updates the main label on UI.
   * @param text the text to be shown.
   */
  public void changeMainLabel(String text)
  {
    Platform.runLater(() -> {mainLb.setText(text);});
  }

  /**
   * Change if the main label is visible or not.
   * @param isVisible if the label will keep visible (true) or not (false).
   */
  public void changeMainLabelVisibility(boolean isVisible)
  {
    Platform.runLater(() -> {mainLb.setVisible(isVisible);});
  }

  /**
   * Define the UI's secondary label.<br>
   * The secondary label is a label which displays
   * minor info.
   * @param lb the secondary label
   */
  public void setSecondaryLabel(Label lb)
  {
    this.secLb = lb;
  }

  /**
   * Updates the main label on UI.
   * @param text the text to be shown.
   */
  public void changeSecondaryLabel(String text)
  {
    Platform.runLater(() -> {secLb.setText(text);});
  }

  /**
   * Define the UI's main text field.
   * @param tf the Text Field
   */
  public void setMainTextField(TextField tf)
  {
    this.mainTf = tf;
  }

  /**
   * Updates the main text on UI.
   * @param text the text to be shown.
   */
  public void changeMainTextField(String text)
  {
    Platform.runLater(() -> {mainTf.setText(text);});
  }

  /**
   * Define the UI's main password field.
   * @param pf the Password Field.
   */
  public void setMainPasswordField(PasswordField pf)
  {
    this.mainPf = pf;
  }

  /**
   * Updates the main password field on UI.
   * @param text the text to be shown.
   */
  public void changeMainPasswordField(String text)
  {
    Platform.runLater(() -> {mainPf.setText(text);});
  }

  /**
   * Define the UI's main button.
   * @param bt the Button
   */
  public void setMainButton(Button bt)
  {
    mainBt = bt;
  }

  public void setMainButtonDisable(boolean isDisabled)
  {
    Platform.runLater(() -> {mainBt.setDisable(isDisabled);});
  }

  /**
   * Define the UI's menu bar.
   * @param mb the Menu Bar
   */
  public void setMenuBar(MenuBar mb)
  {
    menuB = mb;
  }

  /**
   * Retrievethe UI's Progress Bar.
   * @return the progress bar
   */
  public ProgressBar getProgressBar()
  {
    return this.pbar;
  }

  /**
   * Image Changing animation (Fade Out transition).
   */
  private class FadeoutTimer extends AnimationTimer
  {

    @Override
    public void handle(long now)
    {
      doHandle();
    }

    private void doHandle()
    {
      IMAGE_OPACITY -= 0.01;
      getMainImageView().opacityProperty().set(IMAGE_OPACITY );
      if (IMAGE_OPACITY <= 0)
      {
        stop();
        getMainImageView().setPreserveRatio(false);
        getMainImageView().setFitWidth(IMAGE_FIT_WIDTH);
        getMainImageView().setFitHeight(IMAGE_FIT_HEIGHT);
        getMainImageView().setImage(getImgToChange());
        AnimationTimer fadein = new FadeinTimer();
        fadein.start();
      }
    }
  }

  /**
   * Image Changing animation (Fade In transition).
   */
  private class FadeinTimer extends AnimationTimer
  {

    @Override
    public void handle(long now)
    {
      doHandle();
    }

    private void doHandle()
    {
      IMAGE_OPACITY += 0.01;
      getMainImageView().opacityProperty().set(IMAGE_OPACITY );
      if (IMAGE_OPACITY >= 1.0)
      {
        stop();
      }

    }
  }

  /**
   * Set CSS Style on a node or element.
   * @param obj the node or element to apply the style.
   * @param style the FX Style on "-fx" specification.
   */
  public void setStyle(String obj, String style)
  {
    switch (obj)
    {
      case "MainLabel":
        Platform.runLater(() -> {mainLb.setStyle(style);});
        break;
      case "SecLabel":
        Platform.runLater(() -> {secLb.setStyle(style);});
        break;
      case "MainTextField":
        Platform.runLater(() -> {mainTf.setStyle(style);});
        break;
      case "MainPasswordField":
        Platform.runLater(() -> {mainPf.setStyle(style);});
        break;
      default:
        break;
    }
  }


  /**
   * Change the Progress Bar values and updates on UI.
   * @param isValuePercent true if the value is on percent
   * format or not
   * @param value the value of the progress to be shown
   * @param milis the transition interval (in miliseconds)
   * for each percent increased. With this parameter,
   * it is possible to give a smooth transition during
   * progress change.
   */
  public void changeProgress(boolean isValuePercent, double value, long milis)
  {
    try
    {
      // Time between progress bar changes. Increase
        // it to have a bigger gap between progress
        // transitions.
      Thread.sleep(250);
      double progressValue = 0;
      if (isValuePercent)
      {
        progressValue = (value / 100);
      }
      else
      {
        progressValue = value;
      }
      double actualValue = globalProgressValue;
      if (progressValue < actualValue)
      {
        Platform.runLater(() -> {pbar.setProgress(-1);});
      }
      while (actualValue < progressValue)
      {
        actualValue = pbar.getProgress();
        final double adjust = actualValue;
        if (adjust < 0)
        {
          Platform.runLater(() ->
          {
            pbar.setProgress(globalProgressValue);
          });
        }
        else
        {
          Platform.runLater(() ->
          {
            pbar.setProgress(adjust + 0.01);
          });
        }
        Thread.sleep(milis);
      }
      final double resultValue = progressValue;
      Platform.runLater(() -> {pbar.setProgress(resultValue);});
      //Use the next 2 commented lines below to create an animation
        //after reaching the desired percent value (optional)
//      Thread.sleep(700);
      globalProgressValue = pbar.getProgress();
//      Platform.runLater(() -> {pbar.setProgress(-1);});
    }
    catch (InterruptedException ex)
    {
      System.out.println("");
      System.out.println("[***ERRO***] - EXCEÇÃO em DexUI.changeProgress(double, long) - " + ex.getMessage());
      System.out.println("");
    }
  }

  /**
   * Reset the Progress Bar when needed.
   */
  public void resetProgress()
  {
    globalProgressValue = 0.0;
    changeProgress(true, 0, 10);
  }

  /**
   * Constructor for Tooltips.
   * @param text the text to show on Tooltip.
   * @return the Tooltip itself.
   */
  public Tooltip tooltipBuilder(String text)
  {
    Tooltip tooltip = new Tooltip();
    tooltip.setWrapText(true);
    Font font = Font.font("SegoeUI", FontWeight.NORMAL, FontPosture.ITALIC, 12);
    tooltip.setFont(font);
    tooltip.setText(text);
    return tooltip;
  }

}
