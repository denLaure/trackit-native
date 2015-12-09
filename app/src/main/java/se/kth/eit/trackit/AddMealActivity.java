package se.kth.eit.trackit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import se.kth.eit.trackit.model.DiaryEntry;
import se.kth.eit.trackit.model.DiaryEntryType;
import se.kth.eit.trackit.persistence.HelperFactory;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddMealActivity extends AppCompatActivity {

    private final static String YEAR = "year";
    private final static String MONTH = "month";
    private final static String DAY = "day";
    private final static String HOUR = "hour";
    private final static String MINUTE = "minute";
    public static final int REQUEST_ADD_FOOD = 1;
    public static final String ADDED_PRODUCT_EXTRA = "addedProduct";

    private EditText dateInput;
    private EditText timeInput;
    private Calendar calendar = Calendar.getInstance();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy",
            Locale.US);
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm",
            Locale.US);
    private List<String> foods;
    private ArrayAdapter<String> addedFoodAdapter;
    private ListView addedListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        foods = new ArrayList<>();
        setupAddedFoodListView();
        setupDateInput();
        setupTimeInput();
        setupButtons();
    }

    private void setupAddedFoodListView() {
        addedFoodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods);
        addedListView = (ListView) findViewById(R.id.added_food_list);
        addedListView.setAdapter(addedFoodAdapter);
    }

    private void setupButtons() {
        findViewById(R.id.add_food_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFoodActivity();
            }
        });
        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!foods.isEmpty()) {
                    StringBuilder s = new StringBuilder(300);
                    for (String food : foods) {
                        s.append(food);
                        s.append(";");
                    }
                    s.deleteCharAt(s.length()-1);
                    try {
                        HelperFactory.getHelper().getDiaryDAO().create(new DiaryEntry(
                                DiaryEntryType.FOOD, s.toString(), calendar.getTime()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        finish();
                    }
                }
            }
        });
    }

    private void startFoodActivity() {
        Intent intent = new Intent(this, FoodActivity.class);
        startActivityForResult(intent, REQUEST_ADD_FOOD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_FOOD && data != null &&
                data.hasExtra(ADDED_PRODUCT_EXTRA)) {
            foods.add(data.getStringExtra(ADDED_PRODUCT_EXTRA));
            addedFoodAdapter.notifyDataSetChanged();
        }
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
        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
    }

    private void setupTimeInput() {
        timeInput = (EditText) findViewById(R.id.time_input);
        timeInput.setText(timeFormat.format(calendar.getTime()));
        timeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
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
                .newInstance(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    private void setTime(int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        timeInput.setText(timeFormat.format(calendar.getTime()));
    }

    private void showTimeDialog() {
        TimePickerFragment timePicker = TimePickerFragment.newInstance(
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        timePicker.show(getSupportFragmentManager(), "timePicker");
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

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        static TimePickerFragment newInstance(int hour, int minute) {
            TimePickerFragment timePicker = new TimePickerFragment();
            Bundle args = new Bundle();
            args.putInt(HOUR, hour);
            args.putInt(MINUTE, minute);
            timePicker.setArguments(args);

            return timePicker;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int hour = getArguments().getInt(HOUR);
            int minute = getArguments().getInt(MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            ((AddMealActivity) getActivity()).setTime(hour, minute);
        }
    }
}
