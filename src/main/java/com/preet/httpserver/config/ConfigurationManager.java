package com.preet.httpserver.config;

import com.preet.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.io.JsonProcessingException;

public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager; //instance to use
    private static Configuration myCurrentConfiguration;
    private ConfigurationManager(){

    }
public static ConfigurationManager getInstance(){
    if(myConfigurationManager==null)
        myConfigurationManager = new ConfigurationManager();
    return myConfigurationManager;
}
/*
 * 
 * used to load a config file by the path provided
 */
public void loadConfigurationFile(String filePath){
    FileReader fileReader = null;
    try{
        fileReader = new FileReader(filePath);
    }
    catch(FileNotFoundException e ){
        throw new HttpconfigurationException(e);
    }
    StringBuffer sb = new StringBuffer();
    int i;
    try{
        while((i = fileReader.read()) != -1){
            sb.append((char)i);
        }
    }
    catch(IOException e){
        throw new HttpconfigurationException(e);
    }

    JsonNode conf = null;
    try{
        conf = Json.parse(sb.toString());
    }
    catch(IOException e){
        throw new HttpconfigurationException("Error parsing the configuration file", e);
    }
    try{
        myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
    }
    catch(JsonProcessingException e){
        throw new HttpconfigurationException("Error parsing the configuration file, internal", e);
    }
}
/*
 * Returns the current loaded confign
 */
public Configuration getCurrentConfiguration(){
    if(myCurrentConfiguration==null){
        throw new HttpconfigurationException("No current configuration set.");
    }
    return myCurrentConfiguration;
    }
}
