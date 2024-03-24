package com.ciphersentinel.client.model;

public enum API
{
    LOGIN("/login"),
    VAULT("/vault");

    private String url = "http://localhost:8080";

    API(String url)
    {
        this.url += url;
    }

    public String getUrl()
    {
        return url;
    }
}
