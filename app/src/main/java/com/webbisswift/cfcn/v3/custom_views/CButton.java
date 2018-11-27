package com.webbisswift.cfcn.v3.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;

import com.webbisswift.cfcn.R;
import com.webbisswift.cfcn.utils.FontManager;

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
            Drawable drawableLeft = null;
            Drawable drawableRight = null;
            Drawable drawableBottom = null;
            Drawable drawableTop = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableLeft = ta.getDrawable(R.styleable.CustomFont_drawableLeftCompat);
                drawableRight = ta.getDrawable(R.styleable.CustomFont_drawableRightCompat);
                drawableBottom = ta.getDrawable(R.styleable.CustomFont_drawableBottomCompat);
                drawableTop = ta.getDrawable(R.styleable.CustomFont_drawableTopCompat);
            } else {
                final int drawableLeftId = ta.getResourceId(R.styleable.CustomFont_drawableLeftCompat, -1);
                final int drawableRightId = ta.getResourceId(R.styleable.CustomFont_drawableRightCompat, -1);
                final int drawableBottomId = ta.getResourceId(R.styleable.CustomFont_drawableBottomCompat, -1);
                final int drawableTopId = ta.getResourceId(R.styleable.CustomFont_drawableTopCompat, -1);

                if (drawableLeftId != -1)
                    drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
                if (drawableRightId != -1)
                    drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
                if (drawableBottomId != -1)
                    drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
                if (drawableTopId != -1)
                    drawableTop = AppCompatResources.getDrawable(context, drawableTopId);
            }
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
            String fontAsset = ta.getString(R.styleable.CustomFont_font_name);

            if (fontAsset!=null && !fontAsset.isEmpty()) {
                Typeface tf = FontManager.getInstance(context).getFont(fontAsset);

                if (tf != null)
                    setTypeface(tf);
                else
                    Log.d("CTextView", String.format("Could not create a font from asset: %s", fontAsset));
            }

            ta.recycle();
        }
    }

}
