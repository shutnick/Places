package com.example.moreno.places.components.details;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.moreno.places.R;

/**
 * Created on 12.10.2015.
 */
public class PriceLevelTextView extends TextView{
    public PriceLevelTextView(Context context) {
        this(context, null);
    }

    public PriceLevelTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PriceLevelTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(int priceLevel, Object junkParam) {
        int textColor;
        String text;
        switch (priceLevel) {
            case 0:
                textColor = getResources().getColor(R.color.green_800);
                text = "free";
                break;
            case 1:
                textColor = getResources().getColor(R.color.light_green_500);
                text = "Inexpensive";
                break;
            case 2:
                textColor = getResources().getColor(R.color.yellow_500);
                text = "Moderate";
                break;
            case 3:
                textColor = getResources().getColor(R.color.orange_600);
                text = "Expensive";
                break;
            case 4:
                textColor = getResources().getColor(R.color.red_900);
                text = "Very expensive";
                break;
            default:
                textColor = getResources().getColor(R.color.grey_500);
                text = "Undefined";
                break;
        }
        setText(text);
        setTextColor(textColor);
    }

}
