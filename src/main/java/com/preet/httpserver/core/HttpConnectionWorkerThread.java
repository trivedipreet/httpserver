package com.preet.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionWorkerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;
    public HttpConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){ 
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try{
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            
            String html = "<html><head><title> Simple Java HTTP Server </title></head><body><h1> This page was served using my simple Java HTTP Server </h1></body></html>";
            
            final String CRLF = "\r\n"; //13,10 Ascii

            //wrap in http response; part of http protocol
            String response = "HTTP/1.1 200 OK" + CRLF + //Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
            //"Content-Type: text/html" + CRLF +
            "Content-Length" + html.getBytes().length + CRLF + 
            CRLF+ 
            html + 
            CRLF + CRLF; 
            // after status line two spec chars to be sent - & line feed 

            outputStream.write(response.getBytes());

            
            
            LOGGER.info("Connection Processing Finished...");
            }
            catch(IOException e){
                e.printStackTrace(); //handle later
                LOGGER.error("Problem with communication: ",e);
            }
            finally {
                if(inputStream != null){
                    try{
                        inputStream.close();
                    } catch(IOException e){}
                }
                if(outputStream != null){
                    try{
                        outputStream.close();
                    }
                    catch(IOException e){}
                }
                if(socket != null){
                    try{
                        socket.close();
                    }catch (IOException e) {}
                }
            
            }
    }
    
}
