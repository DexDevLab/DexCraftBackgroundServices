package net.dex.dexcraft.backgroundservices.commons.tools;


/**
 * Simple cryptography utility class.
 */
public class Crypto
{

  private static String[] encArray = {"K", "Z", "Y", "G", "L", "B", "H", "X", "A", "I"};
  private static String[] decArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

  /**
   * Encrypt a code.
   * @param numericCode the code in PIN format.
   * @return the encrypted code.
   */
  public static String encrypt(String numericCode)
  {
    String output = numericCode;
    for (int i = 0; i < 10; i++)
    {
      output = output.replace(decArray[i], encArray[i]);
    }
    return output;
  }

  /**
   * Decrypt a code.
   * @param letterCode the encrypted code to decrypt.
   * @return the decrypted code in PIN format.
   */
  public static String decrypt(String letterCode)
  {
    String output = letterCode;
    for (int i = 0; i < 10; i++)
    {
      output = output.replace(encArray[i], decArray[i]);
    }
    return output;
  }


  // EXAMPLE OF HOW TO IMPLEMENT

//  public static void main(String[] args)
//  {
//    encrypt("12874");
//    decrypt("ZYAXL");
//  }

}
