package com.example.pervasivepositioning;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ClassifierClass {

    private Context context;
    private StorageHandler storage;

    public ClassifierClass(Context context){
        this.context = context;
        storage = new StorageHandler(context);
    }

    public Map<String, List<Integer>> getContents(){
        Map<String, List<Integer>> contentMap = new HashMap<>();
        String contents = storage.read();
        for(String entry: contents.split("\n")){
            String[] s = entry.split(";");
            String position = s[0];
            List<Integer> list = new ArrayList();
            for(String str: s[1].split(",-")){
                list.add(Integer.parseInt(str));
            }
            contentMap.put(position,list);
        }
        return contentMap;
    }

    public String knn(List<Integer> test, int k){
        SortedMap<Double, String> euclideanDistanceMap = new TreeMap<>();
        for(Map.Entry<String, List<Integer>> entry : getContents().entrySet()) {
            if(test.size() != 3){
                return "";
            }
            euclideanDistanceMap.put(calcEuclidean(entry.getValue(), test), entry.getKey());
        }
        List<String> positionStrengthAverage = new ArrayList<>();
        for(Map.Entry<Double, String> entry : euclideanDistanceMap.entrySet()){
            if(positionStrengthAverage.size()<k){
                positionStrengthAverage.add(entry.getValue());
            }
        }
        int x = 0;
        int y = 0;
        int z = 0;

        for(String positions: positionStrengthAverage){
            String[] coords = positions.split(",");
            x += Integer.parseInt(coords[0]);
            y += Integer.parseInt(coords[1]);
            z += Integer.parseInt(coords[2]);
        }
        x = x/positionStrengthAverage.size();
        y = y/positionStrengthAverage.size();
        z = z/positionStrengthAverage.size();

        return x + "," + y + "," + z;
    }

    public Double calcEuclidean(List<Integer> training, List<Integer> test){
        if(training.size() != test.size()){
            throw new Error("List size mismatch in calculate euclidean distance. Training size was "+ training.size()+", test size was " + test.size());
        }
        Double calculatorValue = 0d;
        for(int i = 0; i<training.size();i++){
            calculatorValue += Math.pow(training.get(i),2) - Math.pow(test.get(i),2);
        }
        return Math.sqrt(calculatorValue);
    }

}