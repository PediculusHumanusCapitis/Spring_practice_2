package ru.appline.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.appline.logic.CompassModel;
import ru.appline.logic.SideWorld;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final CompassModel compassModel = CompassModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value = "/createCompass", consumes = "application/json")
    public void createCompass(@RequestBody Map<String,String> regBody){
        if(newId.get()!=1) {
            compassModel.reset();
            newId.set(1);
        }
        for (Map.Entry<String, String> entry : regBody.entrySet()) {
            String sideName = entry.getKey();
            String range = entry.getValue();
            String[] parts = range.split("-");
            int start = Integer.parseInt(parts[0].trim());
            int end = Integer.parseInt(parts[1].trim());


            SideWorld sideWorld = new SideWorld(sideName, start, end);
            compassModel.addSide(newId.getAndIncrement(), sideWorld);
        }

    }

    @GetMapping(value = "/getCompass", produces = "application/json")
    public Map<Integer,SideWorld> getCompass(){
        return compassModel.getCompass();
    }

    @GetMapping(value = "/getSide",consumes = "application/json", produces = "application/json")
    public Map<String,Object> getSide(@RequestBody Map<String,Integer> regBody){
        int degree = regBody.get("Degree");
        Map<String, Object> response = new HashMap<String, Object>();
        String sideName = compassModel.getSide(degree);
        response.put("Side", sideName);
        return response;
    }

}
