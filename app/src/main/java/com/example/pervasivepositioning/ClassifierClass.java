package com.example.pervasivepositioning;

import android.content.Context;

import java.util.Map;

public class ClassifierClass {

    private Context context;
    private StorageHandler storage;

    public ClassifierClass(Context context){
        this.context = context;
        storage = new StorageHandler(context);
    }

    public Map<String, Map<String, Integer>> getContents(){
        String contents = storage.read("file.name");
        String[] entries = contents.split("\n");
        for(String entry: entries){
            entry.split(";");
        }
    }
}
