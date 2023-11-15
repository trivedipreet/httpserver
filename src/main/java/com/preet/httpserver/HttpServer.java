package com.preet.httpserver;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.preet.httpserver.config.Configuration;
import com.preet.httpserver.config.ConfigurationManager;
import com.preet.httpserver.core.ServerListenerThread;

/** 
 * 
 * Driver class for http server
 * Steps: Read config files
 * Open socket to listen to a port
 * read req mssgs
 * open and read files from filesystem
 * write response mssgs
 * 
 * 
 * RFCs 
 * 7230,7231
 * Use scannerless parser
 */
public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args){

        LOGGER.info("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using Port: "+ conf.getPort());
        LOGGER.info("Using WebRoot: "+ conf.getWebroot());
        try{

        
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(),conf.getWebroot());
            serverListenerThread.start();
        }
        catch(IOException e){
            e.printStackTrace();

        }
    }
    //multithreading

    
}
