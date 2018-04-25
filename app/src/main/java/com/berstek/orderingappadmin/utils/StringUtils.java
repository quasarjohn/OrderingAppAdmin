package com.berstek.orderingappadmin.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

  public static String formatDf00(double d) {
    DecimalFormat df = new DecimalFormat("0.00");
    return df.format(d);
  }

  public static String getPesoSign() {
    return "₱";
  }

  public static String removeDecimalIfZero(String s) {
    String[] parts = s.split("\\.");
    if (parts[1].equals("00"))
      return parts[0];
    return s;
  }

  public static String formatWithPesoSign(double p) {
    return getPesoSign() + " " + removeDecimalIfZero(formatDf00(p));
  }

  public static String intToDateStringMMMMddyyyy(int d, int m, int y) throws ParseException {
    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
    Date date = sd.parse((d) + "-" + (m + 1) + "-" + y);
    SimpleDateFormat sd2 = new SimpleDateFormat("MMMM dd, yyyy");
    return sd2.format(date);
  }

  public static Date intToDate(int d, int m, int y) throws ParseException {
    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");

    return sd.parse((d) + "-" + (m + 1) + "-" + y);
  }

  public static String getCurrentDateString() {
    SimpleDateFormat d = new SimpleDateFormat("MMMM dd, yyyy");
    return d.format(new Date());
  }
}
