//package com.supinfo.sun.repertory.midlets;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**

 
 @author Bontemps goblet
 
**/

public class RepertoryMidlet extends MIDlet implements CommandListener {

    private List contactList;
    private Form contactForm;
    private Command addCommand;
    private Command modifyCommand, deleteCommand;
    private Display display;
    private TextField name, mail, phoneNumber;
    private RecordStore store;

    public RepertoryMidlet() {

        contactList = new List("Contact", List.IMPLICIT);

        addCommand = new Command("add", Command.ITEM, 1);
        modifyCommand = new Command("modify", Command.ITEM, 2);
        deleteCommand = new Command("delete", Command.CANCEL, 0);

        contactList.addCommand(addCommand);
        contactList.addCommand(modifyCommand);
        contactList.addCommand(deleteCommand);

        contactList.setCommandListener(this);

        display = Display.getDisplay(this);

        try {
            store = RecordStore.openRecordStore("Contact", true);
        } catch (RecordStoreException ex) {
            notifyDestroyed();
        }
        try {
            for (int i = 1; i <= store.getNumRecords(); i++) {

                Contact contact = new Contact();
                try {
                    contact.parseByteArray(store.getRecord(i), '&');
                    contactList.append(contact.getName(), null);
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (RecordStoreNotOpenException ex) {
            ex.printStackTrace();
        }
    }

    public void startApp() {
        display.setCurrent(contactList);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        try {
            store.closeRecordStore();
        } catch (RecordStoreException ex) {
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (d == contactList) {
            if (c == addCommand) {
            contactForm = new Form("Contact");
            name = new TextField("Name", null, 50, TextField.ANY);
            phoneNumber = new TextField("phone number", null, 10, TextField.PHONENUMBER);
            mail = new TextField("mail", null, 255, TextField.EMAILADDR);
            contactForm.append(name);
            contactForm.append(phoneNumber);
            contactForm.append(mail);

            contactForm.addCommand(addCommand);
            contactForm.setCommandListener(this);

            display.setCurrent(contactForm);

            } else if (c == modifyCommand) {
                contactForm = new Form("Contact");
                Contact contact = new Contact();
                try {
                    contact.parseByteArray(store.getRecord(contactList.getSelectedIndex() + 1), '&');
                    name = new TextField("Name", contact.getName(), 50, TextField.ANY);
                    phoneNumber = new TextField("phone number", contact.getPhoneNumber(), 10, TextField.PHONENUMBER);
                    mail = new TextField("mail", contact.getMail(), 255, TextField.EMAILADDR);
                    contactForm.append(name);
                    contactForm.append(phoneNumber);
                    contactForm.append(mail);

                    contactForm.addCommand(modifyCommand);
                    contactForm.setCommandListener(this);

                    display.setCurrent(contactForm);

                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }

            } else if (c == deleteCommand) {
                try {
                    store.deleteRecord(contactList.getSelectedIndex() + 1);
                    contactList.delete(contactList.getSelectedIndex());
                    for (int i = contactList.getSelectedIndex() + 2; i <= store.getNumRecords(); i++) {
                        store.setRecord(i - 1, store.getRecord(i), 0, store.getRecordSize(i));
                    }
                    store.deleteRecord(store.getNumRecords());
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (d == contactForm) {
            if (c == addCommand) {
                saveContact();
                display.setCurrent(contactList);
            } else if (c == modifyCommand) {
                modifyContact();
                display.setCurrent(contactList);
            }
        }
    }

    private void saveContact() {
        Contact contact = new Contact(name.getString(), mail.getString(), phoneNumber.getString());
        try {
            store.addRecord(contact.toByteArray('&'), 0, contact.toByteArray('&').length);
            contactList.append(contact.getName(), null);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }

    }

    private void modifyContact() {
        Contact contact = new Contact(name.getString(), mail.getString(), phoneNumber.getString());
        try {
            store.setRecord(contactList.getSelectedIndex() + 1, contact.toByteArray('&'), 0, contact.toByteArray('&').length);
            contactList.set(contactList.getSelectedIndex(), contact.getName(), null);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }
}