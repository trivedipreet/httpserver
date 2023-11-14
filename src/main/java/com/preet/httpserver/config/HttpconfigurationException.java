package com.preet.httpserver.config;

public class HttpconfigurationException extends RuntimeException{
    public HttpconfigurationException(String message){
        super(message);
    }

    public HttpconfigurationException(String message, Throwable cause){
        super(message,cause);
    }

    public HttpconfigurationException(Throwable cause){
        super(cause);
    }
    
}
    