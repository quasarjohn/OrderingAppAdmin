package com.berstek.orderingappadmin.callback;

import com.google.firebase.auth.FirebaseUser;

public interface AuthCallback {

  void onAuthSuccess(FirebaseUser user);
}
