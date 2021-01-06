package net.dex.dexcraft.backgroundservices.commons.check;


import java.io.IOException;
import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import net.dex.dexcraft.backgroundservices.commons.tools.DexCraftFiles;
import org.apache.commons.io.FileUtils;

/**
 * Check if application is running as Administrator.
 */
public class AdminExecution
{
  /**
   * Check if application is running as Administrator.
   */
  public static void AdminExecution()
  {
    /** This file can be created only under Admin permissions,
        since it's created in C:**/
    if (!DexCraftFiles.adminCheck.exists())
    {
      try
      {
        FileUtils.touch(DexCraftFiles.adminCheck);
      }
      catch (IOException ex)
      {
        System.out.println("NÃO FOI POSSÍVEL CRIAR O DexCraftFiles.adminCheck");
        alerts.noAdmin();
      }
    }
    System.out.println("O software está sendo executado como Administrador.");
    FileUtils.deleteQuietly(DexCraftFiles.adminCheck);
  }


}
