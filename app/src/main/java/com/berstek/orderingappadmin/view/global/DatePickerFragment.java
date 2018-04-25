package com.berstek.orderingappadmin.view.global;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {



  private DatePickerFragmentCallback datePickerFragmentCallback;

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
    dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
    return dialog;
  }

  public void onDateSet(DatePicker view, int year, int month, int day) {
    datePickerFragmentCallback.onDateSet(year, month, day);
    this.dismiss();
  }

  public interface DatePickerFragmentCallback {
    void onDateSet(int year, int month, int day);
  }

  public void setDatePickerFragmentCallback(DatePickerFragmentCallback datePickerFragmentCallback) {
    this.datePickerFragmentCallback = datePickerFragmentCallback;
  }
}
