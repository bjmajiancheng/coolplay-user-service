package com.coolplay.user.security.security;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by majiancheng on 2019/12/15.
 */
public class LoggerFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(LoggerFilter.class);

    public void doFilter(ServletRequest rawRequest, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        MultiReadHttpServletRequest mrRequest = new MultiReadHttpServletRequest(rawRequest);

        StringBuffer output = new StringBuffer();

        // add the URI
        String uri = mrRequest.getRequestURI();
        output.append(mrRequest.getMethod()).append(" ").append(uri).append(NEWLINE);

        // add the headers
        Enumeration<String> headerNames = mrRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = mrRequest.getHeader(key);
            output.append(key).append(": ").append(value).append(NEWLINE);
        }

        // add the body
        String body = mrRequest.getStringBody();
        output.append(NEWLINE).append(body);

        // write to the log file
        LOG.info(output.toString());
        System.out.println(output.toString());

        // continue on processing this request
        chain.doFilter(mrRequest, response);
    }

    /**
     * Reads the stream into a String which can be re-read later
     * @author ian.chen
     */
    class MultiReadHttpServletRequest extends HttpServletRequestWrapper {

        private String body;

        public MultiReadHttpServletRequest(ServletRequest request) throws IOException {
            super((HttpServletRequest) request);
            body = "";
            BufferedReader bufferedReader = request.getReader();
            String line;
            if (bufferedReader != null)
                while ((line = bufferedReader.readLine()) != null) {
                    body += (line + NEWLINE);
                }
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new StringServletInputStream(this.body);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }

        public String getStringBody() {
            return body;
        }
    }

    /**
     * Makes a stream out of a String
     */
    class StringServletInputStream extends ServletInputStream {
        private int lastIndexRetrieved = -1;
        private ReadListener readListener = null;
        private byte[] myBytes;

        public StringServletInputStream(String myString) {
            try {
                myBytes = myString.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                // this shouldn't ever happen since UTF-8 is valid
                throw new IllegalStateException("JVM did not support UTF-8", e);
            }
        }

        @Override
        public boolean isFinished() {
            return (lastIndexRetrieved == myBytes.length - 1);
        }

        @Override
        public boolean isReady() {
            // I hope this is ok
            return isFinished();
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            this.readListener = readListener;
            if (!isFinished()) {
                try {
                    readListener.onDataAvailable();
                } catch (IOException e) {
                    readListener.onError(e);
                }
            } else {
                try {
                    readListener.onAllDataRead();
                } catch (IOException e) {
                    readListener.onError(e);
                }
            }
        }

        @Override
        public int read() throws IOException {
            int i;
            if (!isFinished()) {
                i = myBytes[lastIndexRetrieved + 1];
                lastIndexRetrieved++;
                if (isFinished() && (readListener != null)) {
                    try {
                        readListener.onAllDataRead();
                    } catch (IOException ex) {
                        readListener.onError(ex);
                        throw ex;
                    }
                }
                return i;
            } else {
                return -1;
            }
        }

        @Override
        public int available() throws IOException {
            return (myBytes.length - lastIndexRetrieved - 1);
        }

        @Override
        public void close() throws IOException {
            lastIndexRetrieved = myBytes.length - 1;
            myBytes = null;
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO no additional configuration required
    }

    public void destroy() {
        // TODO do I need to destroy anything?
    }

    private static final String NEWLINE = "\n";

}
