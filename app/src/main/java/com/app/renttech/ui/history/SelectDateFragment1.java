package com.app.renttech.ui.history;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.app.renttech.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SelectDateFragment1 extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    //EditText tgl_mulai;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);

    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm, dd);
    }

    public void populateSetDate(int year, int month, int day) {
        EditText tgl_sampai =(EditText)getActivity().findViewById(R.id.tgl_sampai);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, day);
        tgl_sampai.setText(dateFormatter.format(newDate.getTime()));
    }

}