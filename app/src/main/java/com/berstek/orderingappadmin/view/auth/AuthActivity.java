package com.berstek.orderingappadmin.view.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.berstek.orderingappadmin.R;
import com.berstek.orderingappadmin.callback.AuthCallback;
import com.berstek.orderingappadmin.presentor.auth.GoogleAuthP;
import com.berstek.orderingappadmin.utils.RequestCodes;
import com.berstek.orderingappadmin.view.home.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class AuthActivity extends AppCompatActivity implements AuthCallback {

  private Button loginBtn;
  private GoogleAuthP googleAuthP;
  private FirebaseAuth auth = FirebaseAuth.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth);
    getSupportActionBar().hide();

    googleAuthP = new GoogleAuthP(this, auth);
    googleAuthP.setGoogleAuthCallback(this);

    loginBtn = findViewById(R.id.loginBtn);
    loginBtn.setOnClickListener(googleAuthP);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RequestCodes.SIGN_IN) {
      googleAuthP.loginToFirebase(data);
    }
  }

  @Override
  public void onAuthSuccess(FirebaseUser user) {
    openMainActivity();
  }

  @Override
  protected void onStart() {
    super.onStart();

    FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    if (auth.getCurrentUser() != null) {
      openMainActivity();
    }
  }

  private void openMainActivity() {
    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }
}
