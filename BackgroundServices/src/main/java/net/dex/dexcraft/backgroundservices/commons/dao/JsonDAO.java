package net.dex.dexcraft.backgroundservices.commons.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static net.dex.dexcraft.backgroundservices.commons.Commons.alerts;
import static net.dex.dexcraft.backgroundservices.commons.Commons.logger;
import net.dex.dexcraft.backgroundservices.commons.tools.Close;
import org.json.JSONObject;

/**
 * Manipulates JSON files.
 */
public class JsonDAO
{

  /**
   * Reads JSON file.
   * @param fileJSON the file to be read.
   * @return the JSON object in String format.
   */
  private String readJSON(File fileJSON)
  {
    String jsonData = "";
    BufferedReader br = null;
    try
    {
      String line;
      br = new BufferedReader(new FileReader(fileJSON));
      while ((line = br.readLine()) != null)
      {
        jsonData += line + "\n";
      }
    }
    catch (IOException e)
    {
      alerts.exceptionHandler(e, "EXCEÇÃO EM JSONUtility.getValues(File, String, String)");
      Close.withErrors();
    }
    finally
    {
      try
      {
        if (br != null) br.close();
      }
      catch (IOException ex)
      {
        alerts.exceptionHandler(ex, "EXCEÇÃO EM JSONUtility.getValues(File, String, String)");
        Close.withErrors();
      }
    }
    return jsonData;
  }

  /**
   * Get results in String output from JSON file.
   * @param fileJSON the JSON File to be read.
   * @param category the Object name to get info on JSON. This method
   * accepts only 1 Object (preceding curly brackets).<br>
   * If the file has subclasses (or sub-objects), this method needs
   * to be modified to do so.
   * @param value the String or array to retrieve.
   * @return the results of the parsing on JSON.
   */
  private String getValues(File fileJSON, String category, String value)
  {
    String output = "";
    JSONObject obj = new JSONObject(readJSON(fileJSON));
    if (category == null)
    {
      try
      {
        output = obj.getString(value);
      }
      catch (org.json.JSONException ex)
      {
        try
        {
          output = obj.getJSONArray(value).toString();
        }
        catch (org.json.JSONException x)
        {
          alerts.exceptionHandler(x, "EXCEÇÃO EM JSONUtility.getValues(File, String, String, String)");
          Close.withErrors();
        }
      }
    }
    else
    {
      JSONObject catResult = obj.getJSONObject(category);
      try
      {
        output = catResult.getString(value);
      }
      catch (org.json.JSONException ex)
      {
        try
        {
          output = catResult.getJSONArray(value).toString();
        }
        catch (org.json.JSONException x)
        {
          alerts.exceptionHandler(x, "EXCEÇÃO EM JSONUtility.getValues(File, String, String, String)");
          Close.withErrors();
        }
      }
    }
    return output;
  }


  /**
   * Read object JSON value and return a List.
   * @param fileJSON the JSON File to be read.
   * @param category the Object name to get info on JSON. This method
   * accepts only 1 Object (preceding curly brackets). <br>
   * If the file has subclasses (or sub-objects), this method needs
   * to be modified to do so.
   * @param value the object to retrieve.
   * @return the results of the parsing on JSON.
   */
  public List<String> readList(File fileJSON, String category, String value)
  {
    List<String> output = new ArrayList<>();
    String input = getValues(fileJSON, category, value);
    if (input.contains("[\""))
    {
      input = input.replace("[\"", "\"");
      input = input.replace("\"]", "\"");
      String str[] = input.split(",");
      output = Arrays.asList(str);
    }
    else
    {
      logger.log("***ERRO***", "EXCEÇÃO em JSONUtility.readList(File, String, String, String) - OBJETO NÃO É UM ARRAY");
      alerts.tryAgain();
    }
    return output;
  }


  /**
   * Read object JSON value and return a String.
   * @param fileJSON the JSON File to be read.
   * @param category the Object name to get info on JSON. This method
   * accepts only 1 Object (preceding curly brackets).<br>
   * If the file has subclasses (or sub-objects), this method needs
   * to be modified to do so.
   * @param value the object to retrieve.
   * @return the results of the parsing on JSON.
   */
  public String readValue(File fileJSON, String category, String value)
  {
    String output = "";
    String input = this.getValues(fileJSON, category, value);
    if (!(input.contains("[\"")))
    {
      output = input;
    }
    else
    {
      logger.log("***ERRO***", "EXCEÇÃO em JSONUtility.readValue(File, String, String, String) - OBJETO NÃO É UMA STRING");
      alerts.tryAgain();
    }
    return output;
  }


  /**
   * Edit a value of a JSON File.
   * @param fileJSON the JSON File containing data.
   * @param category the Object name to get info on JSON. This method
   * accepts only 1 Object (preceding curly brackets). <br>
   * If the file has subclasses (or sub-objects), this method needs
   * to be modified to do so.
   * @param jsonKey the object key where the value is located.
   * @param newValue the new value to be put.
   */
  public void editValue(File fileJSON, String category, String jsonKey, String newValue)
  {
    JSONObject jsonObject = new JSONObject (readJSON(fileJSON));
    JSONObject jsonCat = (JSONObject) jsonObject.get(category);
    jsonCat.put(jsonKey, newValue);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonElement je = JsonParser.parseString(jsonObject.toString());
    String output = gson.toJson(je);
    try (FileWriter file = new FileWriter(fileJSON))
    {
      file.write(output);
      file.flush();
    }
    catch (IOException e)
    {
      alerts.exceptionHandler(e, "EXCEÇÃO EM JSONUtility.editValue(File, String, String, String)");
      Close.withErrors();
    }
  }



////   USAGE EXAMPLE
////
////  public static void main(String[] args)
////  {
////    File fileJSON = new File ("C:/corecfg.json");
////    JsonDAO ju = new JsonDAO();
////    List<String> list = new ArrayList<>();
////    list = ju.readList(fileJSON, "versions", "dexCraftLauncherInitVersion");
////    list.forEach((System.out::println));
////    System.out.println(ju.readValue(fileJSON, "versions", "dexCraftdsdsInitVersion"));
////  }

}
