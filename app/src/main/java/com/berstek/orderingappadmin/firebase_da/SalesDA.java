package com.berstek.orderingappadmin.firebase_da;


import com.google.firebase.database.Query;

public class SalesDA extends DA {

  public Query queryTotalSales(String date) {
    return rootRef.child("orders_completed").child(date);
  }
}
