package com.webbisswift.cfcn.domain.model.v2;

import java.util.HashMap;

/**
 * Created by biswas on 07/02/2018.
 */

public class SMTeamShort {

    private static final HashMap<String, String> short_names = new HashMap<>();

    public static String getTeamShort(String name){
        String res = short_names.get(name);
        if(res == null || res.isEmpty())
            res = name;

        return res;
    }
    static {
        short_names.put("West Ham United", "West Ham");
        short_names.put("Tottenham Hotspur","Tottenham");
        short_names.put("Manchester City","Man City");
        short_names.put("West Bromwich Albion","West Brom");
        short_names.put("Manchester United","Man Utd");
        short_names.put("Newcastle United","Newcastle");
        short_names.put("Stoke City", "Stoke");
        short_names.put("Swansea City", "Swansea");
        short_names.put("Leicester City","Leicester");
        short_names.put("Crystal Palace","Palace");
        short_names.put("AFC Bournemouth","Bournemouth");
        short_names.put("Brighton & Hove Albion", "Brighton");
        short_names.put("Huddersfield Town","Huddersfield");
    }
}
