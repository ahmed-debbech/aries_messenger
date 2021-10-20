package com.ahmeddebbech.aries_messenger.model;

import androidx.annotation.NonNull;

public class AriesError {

    private int code;
    private String header;
    private String body;

    public AriesError(int code, String header, String body){
        this.setBody(body);
        this.setHeader(header);
        this.setCode(code);
    }
    public AriesError(String header){
        this.setHeader(header);
        this.setBody("");
        this.setCode(-1);
    }
    public AriesError(String header, String body){
        this.setCode(-1);
        this.setHeader(header);
        this.setBody(body);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @NonNull
    @Override
    public String toString() {
        return "{ code: " + code + " header: " + header + " body: " + body + " }";
    }
}
