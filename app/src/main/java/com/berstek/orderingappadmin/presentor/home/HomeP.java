package com.berstek.orderingappadmin.presentor.home;

import com.berstek.orderingappadmin.firebase_da.SalesDA;
import com.berstek.orderingappadmin.model.Order;
import com.berstek.orderingappadmin.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.text.ParseException;

public class HomeP {

  private SalesDA salesDA;

  public HomeP() {
    salesDA = new SalesDA();
  }

  public void queryTotalSales(String date) throws ParseException {
    salesDA.queryTotalSales(date).addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        salesPCallback.onOrderCompleted(dataSnapshot.getValue(Order.class));
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {

      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  private SalesPCallback salesPCallback;

  public interface SalesPCallback {
    void onOrderCompleted(Order oder);
  }

  public void setSalesPCallback(SalesPCallback salesPCallback) {
    this.salesPCallback = salesPCallback;
  }
}
