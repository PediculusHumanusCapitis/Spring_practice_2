package ru.appline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CompassModel implements Serializable {
    private static final CompassModel instance = new CompassModel();
    private final Map<Integer, SideWorld> model;
    public CompassModel(){
        model=new HashMap<Integer, SideWorld>();
    }

    public static CompassModel getInstance(){
        return instance;
    }

    public void addSide(int id, SideWorld side){
        model.put(id,side);
    }

    public Map<Integer, SideWorld> getCompass(){
        return model;
    }
    public String getSide(int degree) {
        for (SideWorld side : model.values()) {
            int start = side.getStart();
            int end = side.getEnd();
            int newDegree = degree;
            if(start>end){
                end = end +360;
                newDegree = degree + 360;
            }
            if (newDegree >= start && newDegree <= end){
                return side.getName();
            }
        }
        return "Нет стороны света в градусе "+ degree;
    }

    public void reset() {
        model.clear();
    }
}
