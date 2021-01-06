package net.dex.dexcraft.backgroundservices.commons.check;


import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import net.dex.dexcraft.backgroundservices.commons.dto.VersionsDTO;
import net.dex.dexcraft.backgroundservices.commons.tools.Close;
import net.dex.dexcraft.backgroundservices.commons.tools.DexCraftFiles;


/**
 * Check if provisioned package is outdated.
 */
public class ProvisionedPackage
{

  /**
   * Check if specific package is out of date, considering
   * version value comparison.It doesn't have an update list,
   * it just compares the text between the local file containing
   * the version and the online one.
   * @param packageName the name of the provisioned package
   * @return if the package is outdated (true) or not (false)
   */
  public static boolean isOutdated(String packageName)
  {
    logger.log("INFO", "Identificando e comparando versão informada no arquivo \"" + DexCraftFiles.launcherProperties.toString() + "\"...");
    if (!DexCraftFiles.launcherProperties.exists())
    {
      logger.log("***ERRO***", "ARQUIVO \"" + DexCraftFiles.launcherProperties.toString() + "\" NÃO FOI ENCONTRADO");
      alerts.tryAgain();
      Close.withErrors();
    }
    String versionInstalled = "v0";
    String versionProvisioned = "v999999999";
    switch (packageName)
    {
      case "Resources":
        versionProvisioned = "v0";
        break;
      case "Init":
        versionInstalled = VersionsDTO.getDexCraftLauncherInitVersion();
        versionProvisioned = VersionsDTO.getProvisionedInitVersion();
        break;
      case "Client":
        versionInstalled = VersionsDTO.getDexCraftLauncherClientVersion();
        versionProvisioned = VersionsDTO.getProvisionedClientVersion();
        break;
      case "DCBS":
        versionInstalled = VersionsDTO.getDexCraftBackgroundServicesVersion();
        versionProvisioned = VersionsDTO.getProvisionedBackgroundServicesVersion();
        break;
      case "DCGame":
        versionInstalled = "UPDATED";
        versionProvisioned = "UPDATED";
        break;
      case "DCPXGame":
        versionInstalled = "UPDATED";
        versionProvisioned = "UPDATED";
        break;
      case "DCVNGame":
        versionInstalled = "UPDATED";
        versionProvisioned = "UPDATED";
        break;
      case "DCBGame":
        versionInstalled = "UPDATED";
        versionProvisioned = "UPDATED";
        break;
      case "DCPatchGame":
        versionInstalled = VersionsDTO.getDexCraftFactionsPatchVersion();
        versionProvisioned = VersionsDTO.getProvisionedFactionsPatchVersion();
        break;
      case "DCPXPatchGame":
        versionInstalled = VersionsDTO.getDexCraftPixelmonPatchVersion();
        versionProvisioned = VersionsDTO.getProvisionedPixelmonPatchVersion();
        break;
      case "DCVNPatchGame":
        versionInstalled = VersionsDTO.getDexCraftVanillaPatchVersion();
        versionProvisioned = VersionsDTO.getProvisionedVanillaPatchVersion();
        break;
      case "DCBPatchGame":
        versionInstalled = VersionsDTO.getDexCraftBetaPatchVersion();
        versionProvisioned = VersionsDTO.getProvisionedBetaPatchVersion();
        break;
      default:
        return true;
    }
    if (!versionInstalled.equals(versionProvisioned))
    {
      logger.log("INFO", packageName + " se encontra na versão " + versionInstalled + " e está desatualizado.");
      return true;
    }
    else
    {
      logger.log("INFO", packageName + " está atualizado (" + versionInstalled + ").");
      return false;
    }
  }


  public static boolean isInstalled(String packageName)
  {
    switch (packageName)
    {
      case "Resources":
        if ( (DexCraftFiles.resFolder.exists()) && (DexCraftFiles.resFolder.listFiles().length > 0) )
        {
          return true;
        }
        break;
      case "Init":
        return DexCraftFiles.integrityCheckInit.exists();
      case "Client":
        return DexCraftFiles.integrityCheckClient.exists();
      case "DCBS":
        return DexCraftFiles.integrityCheckDCBS.exists();
      case "DCGame":
        return DexCraftFiles.integrityCheckDC.exists();
      case "DCPXGame":
        return DexCraftFiles.integrityCheckDCPX.exists();
      case "DCVNGame":
        return DexCraftFiles.integrityCheckDCVN.exists();
      case "DCBGame":
        return DexCraftFiles.integrityCheckDCB.exists();
      case "DCPatchGame":
        return DexCraftFiles.srcDC.exists();
      case "DCPXPatchGame":
        return DexCraftFiles.srcDCPX.exists();
      case "DCVNPatchGame":
        return DexCraftFiles.srcDCVN.exists();
      case "DCBPatchGame":
        return DexCraftFiles.srcDCB.exists();
      default:
        break;
    }
    return false;
  }

}
