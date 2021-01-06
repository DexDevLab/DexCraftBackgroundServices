package net.dex.dexcraft.backgroundservices.commons;


import net.dex.dexcraft.backgroundservices.commons.tools.ErrorAlerts;
import net.dex.dexcraft.backgroundservices.commons.tools.Logger;


/**
 * The Main Class, which binds the alerts and the logger.<br>
 * With it, you can call properly the logger and the alerts in
 * your application.
 */
public class Commons
{
  public static ErrorAlerts alerts;
  public static Logger logger;

  /**
   * SET the ErrorAlerts instance.
   * @param instance the ErrorAlerts instance.
   */
  public static void setErrorAlerts(ErrorAlerts instance)
  {
    Commons.alerts = instance;
  }

  /**
   * SET the Logger instance.
   * @param instance the Logger instance.
   */
  public static void setLogger(Logger instance)
  {
    Commons.logger = instance;
  }

}
