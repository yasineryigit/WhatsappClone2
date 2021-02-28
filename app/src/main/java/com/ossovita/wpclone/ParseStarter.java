package com.ossovita.wpclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

public class ParseStarter extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("uXVdXVqiw9Uunz91ULvZfhewAJqDdmRTbe18u2vZ")
                // if desired
                .clientKey("YezmbDv2ku71hXIlwJQlw8mubVN9HpIkvyilAEHa")
                .server("https://parseapi.back4app.com/")
                .build()
        );

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL,true);
    }
}
