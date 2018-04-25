package com.berstek.orderingappadmin.view.menus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.orderingappadmin.view.global.ConfirmationDialogFragment;

public class DeleteMenuConfirmation extends ConfirmationDialogFragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater,container,savedInstanceState);

    titleTxt.setText("Are you sure you want to delete this menu?");


    return view;
  }
}
