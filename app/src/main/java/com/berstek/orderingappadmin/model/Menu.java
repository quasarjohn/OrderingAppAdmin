package com.berstek.orderingappadmin.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Comparator;

public class Menu implements Comparable<Menu>, Serializable{

  private String title, details, img_url, code;
  private double price;
  private int priority;

  private boolean available;

  private String key;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getImg_url() {
    return img_url;
  }

  public void setImg_url(String img_url) {
    this.img_url = img_url;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  @Exclude
  public Comparator<Menu> getTitleComparator() {
    return titleComparator;
  }

  public void setTitleComparator(Comparator<Menu> titleComparator) {
    this.titleComparator = titleComparator;
  }

  @Exclude
  @Override
  public int compareTo(@NonNull Menu menu) {
    return (int) (this.price - menu.getPrice());
  }

  @Exclude
  public static Comparator<Menu> titleComparator = new Comparator<Menu>() {
    @Override
    public int compare(Menu menu, Menu t1) {
      return menu.getTitle().compareTo(t1.getTitle());
    }
  };

  @Exclude
  public static Comparator<Menu> priorityComparator = new Comparator<Menu>() {
    @Override
    public int compare(Menu menu, Menu t1) {
      return menu.getPriority() - t1.getPriority();
    }
  };

  @Exclude
  public static Comparator<Menu> priceComparator = new Comparator<Menu>() {
    @Override
    public int compare(Menu menu, Menu t1) {
      return (int) (menu.getPrice() - t1.getPrice());
    }
  };

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public static Comparator<Menu> getPriorityComparator() {
    return priorityComparator;
  }

  public static void setPriorityComparator(Comparator<Menu> priorityComparator) {
    Menu.priorityComparator = priorityComparator;
  }

  public static Comparator<Menu> getPriceComparator() {
    return priceComparator;
  }

  public static void setPriceComparator(Comparator<Menu> priceComparator) {
    Menu.priceComparator = priceComparator;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }
}
