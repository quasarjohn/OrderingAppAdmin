package com.berstek.orderingappadmin.utils;

import android.content.Context;
import android.widget.ImageView;

import com.berstek.orderingappadmin.firebase_da.DA;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

  private Context context;

  public Utils(Context context) {
    this.context = context;
  }

  public void loadImage(String url, ImageView img) {
    Glide.with(context).load(url).skipMemoryCache(true).into(img);
  }

  public void loadImage(String url, ImageView img, int size) {
    Glide.with(context).load(url).skipMemoryCache(true).override(size, size).into(img);
  }

  public void loadImage(String url, ImageView img, int size, boolean centerCrop) {
    if (centerCrop)
      Glide.with(context).load(url).skipMemoryCache(true).override(size, size).centerCrop().into(img);
    else
      Glide.with(context).load(url).skipMemoryCache(true).override(size, size).into(img);
  }

  public static String getTimestapDateOnly() throws ParseException {
    SimpleDateFormat d = new SimpleDateFormat("dd:MMMM:yyyy");
    String s = d.format(new Date());

    return s;
  }

  public static String getTimestapDateOnly(Date date) throws ParseException {
    SimpleDateFormat d = new SimpleDateFormat("d:MMMM:yyyy");
    String s = d.format(date);

    return s;
  }
}
