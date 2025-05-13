package com.example.findinglogs.model.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import com.example.findinglogs.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static Drawable getDrawable(String name, Context context) {
        if (context == null) {
            if (Logger.ISLOGABLE) Logger.e(TAG, "getDrawable: context is null");
            return null;
        }
        Resources resources = context.getResources();
        Drawable drawable;
        String nameDrawable = "weather_icon_" + name;
        Log.d("draw", nameDrawable);
        try {

            int idDrawable = resources.getIdentifier(nameDrawable, Constants.KEY_COD_DRAWABLE,
                    context.getPackageName());
            drawable = ResourcesCompat.getDrawable(resources, idDrawable, null);
        } catch (Exception e) {
            if (Logger.ISLOGABLE)
                Logger.w(TAG, "can't retrieve drawable resource: getting default");
            drawable = ResourcesCompat
                    .getDrawable(resources, R.drawable.ic_launcher_foreground, null);
        }
        return drawable;
    }

    public static String getCelsiusTemperatureFromKevin(float temp) {
        float tempKelvinRef = 275.15f;
        float convertToCelsius = temp - tempKelvinRef;
        NumberFormat fmt = DecimalFormat.getNumberInstance(new Locale("pt", "BR"));
        fmt.setMaximumFractionDigits(1);
        fmt.setRoundingMode(RoundingMode.DOWN);
        String celsius = fmt.format(convertToCelsius);
        return celsius + "°C";
    }
}
