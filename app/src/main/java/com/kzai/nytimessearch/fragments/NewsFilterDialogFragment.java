package com.kzai.nytimessearch.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.kzai.nytimessearch.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kzai on 6/20/16.
 */
public class NewsFilterDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    
    @BindView(R.id.display_datepicker) EditText displayDatePicker;
    private OnCompleteListener mListener;
    private Calendar chosenCalendar;
    private String sort;
    @BindView(R.id.spinner) Spinner spinner;

    //private HashMap<String, Boolean> categories = new HashMap<>();
    private ArrayList<String> categories;
    private ArrayList<CheckBox> categoryReferences;
    
    public NewsFilterDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static NewsFilterDialogFragment newInstance(String title) {
        NewsFilterDialogFragment frag = new NewsFilterDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setTitle("Advanced Search");
        View view = inflater.inflate(R.layout.fragment_news_filter, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categories = new ArrayList<>();
        categoryReferences = new ArrayList<>();

        setupDatePicker(view);
        setupSpinner(view);
        setupCheckboxes(view);

        Button button = (Button) view.findViewById(R.id.clear_button);
    }

    @OnClick(R.id.clear_button)
    public void clearFilters() {
        // do something
        chosenCalendar = null;
        sort = "";
        categories = new ArrayList<>();

        for (CheckBox checkBox: categoryReferences) {
            if (checkBox.isChecked()) {
                Log.d("DEBUG", String.valueOf(checkBox.isChecked()));
                checkBox.toggle();
            } else {
                // asdfkjlsd
                Log.d("DEBUG", String.valueOf(checkBox.isChecked()));
            }
        }

        displayDatePicker.setText("");
        spinner.setSelection(0);
    }

    private void setupCheckboxes(View v) {
        categoryReferences.add((CheckBox) v.findViewById(R.id.checkbox_art));
        categoryReferences.add((CheckBox) v.findViewById(R.id.checkbox_business));
        categoryReferences.add((CheckBox) v.findViewById(R.id.checkbox_fashion));
        categoryReferences.add((CheckBox) v.findViewById(R.id.checkbox_food));
        categoryReferences.add((CheckBox) v.findViewById(R.id.checkbox_health));
        categoryReferences.add((CheckBox) v.findViewById(R.id.checkbox_politics));
        categoryReferences.add((CheckBox) v.findViewById(R.id.checkbox_sports));
        categoryReferences.add((CheckBox) v.findViewById(R.id.checkbox_travel));
    }

    private void setupSpinner(View v) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //multiSpinner.add(list);
    }

    private void setupDatePicker(View v) {
        // Show soft keyboard automatically and request focus to field
        displayDatePicker.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @OnClick(R.id.display_datepicker)
    public void displayDate() {
        //button.setText("Hello!");
        Calendar mcurrentDate = Calendar.getInstance();
        int year = mcurrentDate.get(Calendar.YEAR);
        int month = mcurrentDate.get(Calendar.MONTH);
        int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                            /*      Your code   to get date and time */
                chosenCalendar = Calendar.getInstance();
                chosenCalendar.set(selectedyear, selectedmonth, selectedday);

                // (2) create a date "formatter" (the date format we want)
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

                // (3) create a new String using the date format we want
                String chosenDateText = formatter.format(chosenCalendar.getTime());
                displayDatePicker.setText(chosenDateText);
            }
        },year, month, day);
        mDatePicker.getDatePicker().setCalendarViewShown(false);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        sort = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public interface OnCompleteListener {
        void onComplete(Calendar calendar, String sort, ArrayList<String> categories);
    }

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener) activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        mListener.onComplete(chosenCalendar, sort, categories);
        super.onDismiss(dialog);
    }

    // DatePickerDialog Methods
    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setTargetFragment(NewsFilterDialogFragment.this, 300);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        CheckBox checkBox = (CheckBox) view;
        boolean checked = checkBox.isChecked();

        //Toast.makeText(getContext(), "HAI", Toast.LENGTH_SHORT).show();
        switch (view.getId()) {
            case R.id.checkbox_art:
                if (checked) {
                    categories.add("Art");
                }
                else {
                    categories.remove("Art");
                }
                break;
            case R.id.checkbox_business:
                if (checked) {
                    categories.add("Business");
                }
                else {
                    categories.remove("Business");
                }
                break;
            case R.id.checkbox_fashion:
                if (checked) {
                    categories.add("Fashion");
                }
                else {
                    categories.remove("Fashion");
                }
                break;
            case R.id.checkbox_food:
                if (checked) {
                    categories.add("Food");
                }
                else {
                    categories.remove("Food");
                }
                break;
            case R.id.checkbox_health:
                if (checked) {
                    categories.add("Health");
                }
                else {
                    categories.remove("Health");
                }
                break;
            case R.id.checkbox_politics:
                if (checked) {
                    categories.add("Politics");
                }
                else {
                    categories.remove("Politics");
                }
                break;
            case R.id.checkbox_sports:
                if (checked) {
                    categories.add("Sports");
                }
                else {
                    categories.remove("Sports");
                }
                break;
            case R.id.checkbox_travel:
                if (checked) {
                    categories.add("Travel");
                }
                else {
                    categories.remove("Travel");
                }
                break;
        }
    }
}
