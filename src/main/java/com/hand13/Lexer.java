package com.hand13;

import com.hand13.exception.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;

/**
 * @version $Revision$ $Date$
 * @author $Author$
*/
public class Lexer {
    private boolean isEOF;
    private Reader reader;
    private char previous;
    private boolean usePrevious;
    public Lexer(Reader reader) {
        if (reader.markSupported()) {
            this.reader = reader;
        } else {
            this.reader = new BufferedReader(reader);
        }
        usePrevious = false;
        this.isEOF = false;
    }
    public char next() throws JSONException {
        int c = 0;
        if(usePrevious) {
            c = this.previous;
            this.usePrevious = false;
        }else {
            try {
                c = reader.read();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                throw new JSONException("error");
            }
        }
        if(c <= 0){
            this.isEOF = true;
            return 0;
        }
        this.previous = (char)c;
        return (char)c;
    }

    public void goBack()throws JSONException {
        if(this.usePrevious) {
            throw new JSONException(" i could not go back twice");
        }
        this.usePrevious = true;
    }

    public char nextClean()throws JSONException {
        for (;;) {
            char c = this.next();
            if(c ==0|| c> ' ') {
                return c;
            }
        }
    }
    public String getKey()throws JSONException {
        char c = nextClean();
        StringBuilder result = new StringBuilder();
        if(c == '"') {
            c = nextClean();
            while((c>='a' && c<= 'z') ||(c>= 'A' && c <= 'Z')) {
                result.append(c);
                c = next();
            }
            if(c!= '"'){
                throw new JSONException("error");
            }
        }else {
            throw new JSONException("error while parse");
        }
        return result.toString();
    }
    public String getStringValue()throws JSONException {
        char c = nextClean();
        StringBuilder result = new StringBuilder();
        if(c == '"') {
            c = nextClean();
            while(c >=' ' && c != '"') {
                result.append(c);
                c = next();
            }
            if(c!= '"'){
                throw new JSONException("error");
            }
        }else {
            throw new JSONException("error while parse");
        }
        return result.toString();
    }
    public BigDecimal getNumber()throws JSONException {
        char c = nextClean();
        StringBuilder number = new StringBuilder();
        while(c > '0' && c<'9') {
            number.append(c);
            c = next();
        }
        this.goBack();
        return new BigDecimal(number.toString());
    }
 }
