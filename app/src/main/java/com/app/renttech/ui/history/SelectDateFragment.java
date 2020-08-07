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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    //EditText tgl_mulai;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yyyy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, yyyy, mm, dd);

    }

    public void onDateSet(DatePicker view, int yyyy, int mm, int dd) {
        populateSetDate(yyyy, mm, dd);
    }
    public void populateSetDate(int year, int month, int day) {
        //System.out.println(day+"-"+month+"-"+year);
        EditText tgl_mulai =(EditText)getActivity().findViewById(R.id.tgl_mulai);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, day);
        tgl_mulai.setText(dateFormatter.format(newDate.getTime()));

    }


}