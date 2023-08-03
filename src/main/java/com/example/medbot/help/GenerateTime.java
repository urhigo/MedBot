package com.example.medbot.help;

import java.util.ArrayList;
import java.util.List;

public class GenerateTime {

    public List<String> generateTime(){
        List<String> timeList = new ArrayList<>();
        for (int i = 8; i < 18; i++){
            timeList.add(i + ":00");
        }
        return timeList;
    }
}
