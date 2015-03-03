package de.sk.cursor;

import android.app.Activity;
import android.os.Bundle;

public class Contact {

    // private variables
    public int _id;
    public String _name;
    public String _dec;
    public String _extra;

    public Contact() {
    }

    // constructor
    public Contact(int id, String name, String _dec, String _extra) {
        this._id = id;
        this._name = name;
        this._dec = _dec;
        this._extra = _extra;

    }

    // constructor
    public Contact(String name, String _dec, String _extra) {
        this._name = name;
        this._dec = _dec;
        this._extra = _extra;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting dec
    public String getDEC() {
        return this._dec;
    }

    // setting dec
    public void setDEC(String dec) {
        this._dec = dec;
    }

    // getting extra
    public String getExtra() {
        return this._extra;
    }

    // setting extra
    public void setExtra(String extra) {
        this._extra = extra;
    }

    public class ServicesViewActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // etc...
          //  getActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }


}


