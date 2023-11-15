package com.preet.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

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
    void parseHttpRequest() {
        HttpRequest request = null;
        try{
            request = httpParser.parseHttpRequest(
            generateValidGETTestCase());
        } catch( HttpParsingException e){
            fail(e);          
        }
        assertNotNull(request);
        assertEquals(request.getMethod(), HttpMethod.GET);
        assertEquals(request.getRequestTarget(), "/");
        assertEquals(request.getOriginalHttpVersion(), "HTTP/1.1");
        assertEquals(request.getBestCompatibleHttpVersion(),HttpVersion.HTTP_1_1);

    }

    @Test
    void parseHttpRequestBadMethod1() {
        HttpRequest request;
        try {
            request = httpParser.parseHttpRequest(
            generateBadTestCaseMethodName1());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }

    
    }

    @Test
    void parseHttpRequestBadMethod2() {
        HttpRequest request;
        try {
            request = httpParser.parseHttpRequest(
            generateBadTestCaseMethodName2());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }

    
    }

    @Test
    void parseHttpRequestInvNumItems1() {
        
        try {
            HttpRequest request = httpParser.parseHttpRequest(
            generateBadTestCaseRequestLineInvNumItems1());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

    
    }

    @Test
    void parseHttpEmptyRequestLine() {
        
        try {
            HttpRequest request = httpParser.parseHttpRequest(
            generateBadTestCaseEmptyRequestLine());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

    
    }
 
    @Test
    void parseHttpRequestLineCRnoLF() {
        
        try {
            HttpRequest request = httpParser.parseHttpRequest(
            generateBadTestCaseRequestLineOnlyCRnoLF());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

    
    }

    @Test
    void parseHttpRequestBadHttpVersion() {
        HttpRequest request;
        try {
            request = httpParser.parseHttpRequest(
            generateBadHttpVersionTestCase());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

    
    }

    @Test
    void parseHttpRequestUnsupportedHttpVersion() {
        HttpRequest request;
        try {
            request = httpParser.parseHttpRequest(
            generateUnsupportedHttpVersionTestCase());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }

    
    }

    @Test
    void parseHttpRequestSupportedHttpVersion() {
        HttpRequest request;
        try {
            request = httpParser.parseHttpRequest(
            generateSupportedHttpVersionTestCase());
        
            assertNotNull(request);
            assertEquals(request.getBestCompatibleHttpVersion(), HttpVersion.HTTP_1_1);
            assertEquals(request.getOriginalHttpVersion(), "HTTP/1.2");
        } catch (HttpParsingException e) {
            fail();
        }

    
    }


private InputStream generateValidGETTestCase(){
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

private InputStream generateBadTestCaseMethodName1(){
    String rawData = "GeT / HTTP/1.1\r\n" +
    "Host: localhost:8080\r\n"+
    "Accept-Language: en-IN,en-GB;q=0.9,en;q=0.8\r\n"
    + "\r\n";
    
    InputStream inputStream = new ByteArrayInputStream(
        rawData.getBytes(
            StandardCharsets.US_ASCII
        )
    );
    return inputStream;
    
}

private InputStream generateBadTestCaseMethodName2(){
    String rawData = "GETTTT / HTTP/1.1\r\n" +
    "Host: localhost:8080\r\n"+
    "Accept-Language: en-IN,en-GB;q=0.9,en;q=0.8\r\n"
    + "\r\n";
    
    InputStream inputStream = new ByteArrayInputStream(
        rawData.getBytes(
            StandardCharsets.US_ASCII
        )
    );
    return inputStream;
    
}

private InputStream generateBadTestCaseRequestLineInvNumItems1(){
    String rawData = "GET / AAAAA HTTP/1.1\r\n" +
    "Host: localhost:8080\r\n"+
    "Accept-Language: en-IN,en-GB;q=0.9,en;q=0.8\r\n"
    + "\r\n";
    
    InputStream inputStream = new ByteArrayInputStream(
        rawData.getBytes(
            StandardCharsets.US_ASCII
        )
    );
    return inputStream;
    
}

private InputStream generateBadTestCaseEmptyRequestLine(){
    String rawData = "\r\n" +
    "Host: localhost:8080\r\n"+
    "Accept-Language: en-IN,en-GB;q=0.9,en;q=0.8\r\n"
    + "\r\n";
    
    InputStream inputStream = new ByteArrayInputStream(
        rawData.getBytes(
            StandardCharsets.US_ASCII
        )
    );
    return inputStream;
    
}
 
private InputStream generateBadTestCaseRequestLineOnlyCRnoLF(){
    String rawData = "GET / HTTP/1.1\r" + // no LF
    "Host: localhost:8080\r\n"+
    "Accept-Language: en-IN,en-GB;q=0.9,en;q=0.8\r\n"
    + "\r\n";
    
    InputStream inputStream = new ByteArrayInputStream(
        rawData.getBytes(
            StandardCharsets.US_ASCII
        )
    );
    return inputStream;
    
}

private InputStream generateBadHttpVersionTestCase(){
    String rawData = "GET / HTP/1.1\r\n" +
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

private InputStream generateUnsupportedHttpVersionTestCase(){
    String rawData = "GET / HTTP/2.1\r\n" +
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


private InputStream generateSupportedHttpVersionTestCase(){
    String rawData = "GET / HTTP/1.2\r\n" +
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
