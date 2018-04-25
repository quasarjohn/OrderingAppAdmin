package com.berstek.orderingappadmin.firebase_da;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DA {

  public DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().getRoot();

  public void log(Object object) {
    rootRef.child("logs").push().setValue(object);
  }

}
