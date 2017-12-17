package com.webbisswift.cfcnepal.ui.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import com.webbisswift.cfcnepal.R;
import com.webbisswift.cfcnepal.utils.FontManager;

/**
 * Created by apple on 12/5/17.
 */

public class CButton extends AppCompatButton {


    public CButton(Context context) {
        this(context, null);
    }

    public CButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);

        if (ta != null) {
            String fontAsset = ta.getString(R.styleable.CustomFont_font_name);

            if (fontAsset!=null && !fontAsset.isEmpty()) {
                Typeface tf = FontManager.getInstance(context).getFont(fontAsset);

                if (tf != null)
                    setTypeface(tf);
                else
                    Log.d("CButton", String.format("Could not create a font from asset: %s", fontAsset));
            }
        }
    }

}
