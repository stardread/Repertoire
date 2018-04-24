//package com.supinfo.sun.repertory.midlets;

/**
 *

 
 @author Bontemps Goblet
 
**/

public class Contact {

    private String name, mail, phoneNumber;

    public Contact() {
    }

    public Contact(String name, String mail, String phoneNumber) {
        this.name = name;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void parseByteArray(byte[] record, char separator) {
        if(record != null) {
            String values = new String(record);
            int index = values.indexOf(separator);
            int lastIndex = values.lastIndexOf(separator);
            this.name = values.substring(0, index);
            this.phoneNumber = values.substring(index+1, lastIndex);
            this.mail = values.substring(lastIndex+1);
        }
    }

    public byte[] toByteArray(char separator) {
        return (this.name + separator + this.phoneNumber + separator +
        this.mail).getBytes();
    }

}