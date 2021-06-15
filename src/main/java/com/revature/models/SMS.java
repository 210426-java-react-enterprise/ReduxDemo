package com.revature.models;

public class SMS{

    private String to;
    private String message;

    public String getTo(){
        return to;
    }
    public void setTo(String to){
        this.to = to;
    }
    public String message(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SMS{" +
                "to='" + to + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
