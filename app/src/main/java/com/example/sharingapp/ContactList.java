package com.example.sharingapp;

import java.util.ArrayList;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

public class ContactList {
    private ArrayList<Contact> contacts;
    private String FILENAME;

    public ContactList() {
        contacts = new ArrayList<Contact>();
    }

    public ContactList( ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public ArrayList<String> getAllUsernames(){
        ArrayList<String> result = new  ArrayList<String>();

        for(int i = 0; i < this.contacts.size(); i ++){
            result.add(this.contacts.get(i).getUsername());
        }

        return result;
    }

    public void addContact(Contact contact){
        this.contacts.add(contact);
    }

    public void deleteContact(Contact contact){
        this.contacts.remove(contact);
    }

    public Contact getContact(int index){
        return this.contacts.get(index);
    }

    public int getSize(){
        return this.contacts.size();
    }

    public int getIndex(Contact contact){
        return this.contacts.indexOf(contact);
    }

    public boolean hasContact(Contact contact){
        boolean result = false;
        if(this.contacts.contains(contact)){
            result = true;
        }

        return result;
    }

    public Contact getContactByUsername(String username){
        for (int i = 0; i < this.contacts.size(); i++){
            if(this.contacts.get(i).getUsername() == username){
                return this.contacts.get(i);
            }
        }
        return null;
    }

    public boolean isUsernameAvailable(String username){
        for (Contact c : contacts) {
            if (c.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public void loadContacts(Context context) {

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            contacts = new Gson().fromJson(isr, listType);
            fis.close();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<Contact>();
        } catch (IOException e) {
            contacts = new ArrayList<Contact>();
        }
    }

    public boolean saveContacts(Context context) {
        boolean ret = false;
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(contacts, osw);
            osw.flush();
            fos.close();
            ret = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
