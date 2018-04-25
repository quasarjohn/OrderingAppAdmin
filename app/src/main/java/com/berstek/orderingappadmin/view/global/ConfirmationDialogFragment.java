package com.berstek.orderingappadmin.view.global;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.berstek.orderingappadmin.R;
import com.berstek.orderingappadmin.custom_classes.CustomDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationDialogFragment extends CustomDialogFragment implements View.OnClickListener {

  public TextView titleTxt;
  public Button cancelBtn, continueBtn;

  public ConfirmationDialogFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    super.onCreateView(inflater, container, savedInstanceState);
    view = inflater.inflate(R.layout.fragment_confirmation_dialog, container, false);

    titleTxt = view.findViewById(R.id.titleTxt);
    cancelBtn = view.findViewById(R.id.cancelBtn);
    continueBtn = view.findViewById(R.id.continueBtn);

    cancelBtn.setOnClickListener(this);
    continueBtn.setOnClickListener(this);

    return view;
  }

  public ConfirmationDialogCallback confirmationDialogCallback;

  @Override
  public void onClick(View view) {
    int id = view.getId();

    if (id == R.id.continueBtn) {
      confirmationDialogCallback.onContinue();
    } else {
      confirmationDialogCallback.onCancel();
    }

    dismiss();
  }

  public interface ConfirmationDialogCallback {
    void onContinue();

    void onCancel();
  }

  public void setConfirmationDialogCallback(ConfirmationDialogCallback confirmationDialogCallback) {
    this.confirmationDialogCallback = confirmationDialogCallback;
  }
}
