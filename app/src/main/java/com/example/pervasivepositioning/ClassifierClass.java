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
        for(String entry: contents.split("\n")){
            String[] s = entry.split(";");
            String position = s[0];
            for(String s :entry.split(";")){
            }

        }
    }
}
