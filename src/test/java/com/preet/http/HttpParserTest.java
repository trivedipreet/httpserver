package com.preet.http;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@TestInstance(Lifecycle.PER_CLASS)
public class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass(){
        httpParser = new HttpParser();
    }

    @Test
    void testParseHttpRequest() {
        httpParser.parseHttpRequest(generateValidTestCase());

    }

private InputStream generateValidTestCase(){
    String rawData = "GET / HTTP/1.1\r\n" +
    "Host: localhost:8080\r\n"+
    "Connection: keep-alive\r\n"+
    "Upgrade-Insecure-Requests: 1\r\n"+
    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.1 Safari/605.1.15\r\n"+
    "Sec-Fetch-User: ?1\r\n"+
    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"+
    "Sec-Fetch-Site: none\r\n"+
    "Sec-Fetch-Mode: navigate\r\n"+
    "Accept-Encoding: gzip, deflate\r\n"+
    "Accept-Language: en-IN,en-GB;q=0.9,en;q=0.8\r\n"
    + "\r\n";
    
    InputStream inputStream = new ByteArrayInputStream(
        rawData.getBytes(
            StandardCharsets.US_ASCII
        )
    );
    return inputStream;
    
}

}
