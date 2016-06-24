package com.kzai.nytimessearch.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by kzai on 6/20/16.
 */
public class DatePickerFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Activity needs to implement this interface
        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getActivity();

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    /*
    // Defines the listener interface
    public interface DatePickerDialogListener {
        void onFinishDatePickerDialog(String inputText);
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        DatePickerDialogListener listener = (DatePickerDialogListener) getTargetFragment();

        listener.onFinishDatePickerDialog();
        //listener.onFinishDatePickerDialog(mEditText.getText().toString());

        dismiss();
        super.onDismiss(dialog);
    } */
}
