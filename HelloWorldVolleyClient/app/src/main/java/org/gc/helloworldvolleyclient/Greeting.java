package org.gc.helloworldvolleyclient;

// model class with matching instance vars
public class Greeting
{
    private String message;
    private String to;

    public String toString()
    {
        return message + " " + to;
    }
}