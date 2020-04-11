package com.jvinix.iy4s.Utils;

/**
 *
 * Created by Julio Carvalho on Aug/2019
 * juliov.ca@outlook.com
 *
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.jvinix.iy4s.Models.Report;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Converter {

    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream ByteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ByteStream);
        byte[] b = ByteStream.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static String ByteToString(byte[] b) {
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static byte[] StringToByte(String s) {
        return s.getBytes();
    }

    public static byte[] BitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream ByteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ByteStream);

        return ByteStream.toByteArray();
    }

    public static Bitmap ByteToBitmap(byte[] encodeByte) {
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            //Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, size);//encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap DrawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static String millisecondsToString(long milliseconds) {
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);

        return longToString(hours) + ":" + longToString(minutes) + ":" + longToString(seconds);
    }

    private static String longToString(long val) {
        String result = String.valueOf(val);
        if (val < 10) {
            result = "0" + result;
        }
        return result;
    }

    public static long StringTimeToLong(String time) {

        String h = time.substring(0,2);
        String m = time.substring(3,5);
        String s = time.substring(6,8);

        return (Integer.valueOf(h) * 3600000) + (Integer.valueOf(m) * 60000) + (Integer.valueOf(s) * 1000);
    }

    public static String LongToStringTime(Long longdate)
    {
        try {
            DateFormat formatdate = new SimpleDateFormat("MMM dd, yyyy - HH:mm");
            Date date = new Date(longdate);
            return formatdate.format(date);
        } catch (Exception e) {
            Log.e("LOG_CONVERTER", e.getMessage());
            return null;
        }
    }


    public static void JsonToReport(JSONObject jsonObject) {
        // display response
        Log.d("jsonObject: ", jsonObject.toString());
        Report report = new Report();
        try{
            report.setReportId(jsonObject.getInt("ReportId"));
            report.setDescription(jsonObject.getString("Description"));
            Log.d("jsonObject: ", report.toString());
        }
        catch (Exception ex)
        {
            Log.e("JsonToReport", ex.getMessage());
        }
            //    gson.fromJson(response.toString(), Report.class);
//        Log.d("Report", report.toString());
    }

}
