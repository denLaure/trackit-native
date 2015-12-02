package se.kth.eit.trackit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMealActivity extends AppCompatActivity {

    private final static String YEAR = "year";
    private final static String MONTH = "month";
    private final static String DAY = "day";

    private EditText dateInput;
    private Calendar calendar = Calendar.getInstance();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy",
            Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        setupDateInput();
    }

    private void setupDateInput() {
        dateInput = (EditText) findViewById(R.id.date_input);
        dateInput.setText(dateFormat.format(calendar.getTime()));
        findViewById(R.id.calendar_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        dateInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)
                    showDateDialog();
            }
        });
        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
    }

    private void setDate(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        dateInput.setText(dateFormat.format(calendar.getTime()));
    }

    private void showDateDialog() {
        DatePickerFragment datePicker = DatePickerFragment
                .newInstance(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        static DatePickerFragment newInstance(int year, int month, int day) {
            DatePickerFragment datePicker = new DatePickerFragment();
            Bundle args = new Bundle();
            args.putInt(YEAR, year);
            args.putInt(MONTH, month);
            args.putInt(DAY, day);
            datePicker.setArguments(args);

            return datePicker;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int year = getArguments().getInt(YEAR);
            int month = getArguments().getInt(MONTH);
            int day = getArguments().getInt(DAY);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            ((AddMealActivity) getActivity()).setDate(year, month, day);
        }
    }
}
