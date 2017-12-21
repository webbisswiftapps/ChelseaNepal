package com.webbisswift.cfcn.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 11/30/17.
 */

public class FontManager {

    private static FontManager instance;

    private AssetManager mgr;

    private Map<String, Typeface> fonts;

    private FontManager(AssetManager _mgr) {
        mgr = _mgr;
        fonts = new HashMap<String, Typeface>();
    }

    public static void init(AssetManager mgr) {
        instance = new FontManager(mgr);
    }

    public static FontManager getInstance(Context c) {
        if(instance == null) {
            init(c.getAssets());
        }

        return instance;
    }

    public Typeface getFont(String asset) {
        if (fonts.containsKey(asset))
            return fonts.get(asset);

        Typeface font = null;

        try {
            String path = "fonts/"+asset;
            font = Typeface.createFromAsset(mgr, path);
            fonts.put(asset, font);
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            return font;
        }

    }

    public boolean cacheAllFonts(){
        String[] fontNames = {"Montserrat-Bold.otf", "Montserrat-Light.otf", "Montserrat-Regular.otf", "Montserrat-SemiBold.otf"};
        int count = 0;
        for(String name:fontNames){
            try {
                Typeface font = Typeface.createFromAsset(mgr, "fonts/" + name);
                fonts.put(name, font);
                count++;
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return count == fontNames.length;
    }



}
