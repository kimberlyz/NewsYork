package com.kzai.nytimessearch;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by kzai on 6/20/16.
 */
public class NewsFilterDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    
    private EditText displayDatePicker;
    private OnCompleteListener mListener;
    private Calendar chosenCalendar;
    private String sort;
    //private HashMap<String, Boolean> categories = new HashMap<>();
    private ArrayList<String> categories;
    
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
        getDialog().setCanceledOnTouchOutside(false);
        categories = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_news_filter, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupDatePicker(view);
        setupSpinner(view);
        Button button = (Button) view.findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                mListener.onComplete(chosenCalendar, sort, categories);
                dismiss();
            }
        });
    }

    private void setupSpinner(View v) {
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
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
        displayDatePicker = (EditText) v.findViewById(R.id.display_datepicker);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        displayDatePicker.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        //displayDatePicker = (EditText) getView().findViewById(R.id.display_datepicker);
        //displayDatePicker = (EditText) getView().findViewById(R.id.display_datepicker);
        displayDatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int year = mcurrentDate.get(Calendar.YEAR);
                int month = mcurrentDate.get(Calendar.MONTH);
                int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                            /*      Your code   to get date and time    */
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
                mDatePicker.show();  }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        sort = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + sort, Toast.LENGTH_LONG).show();
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

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        //Toast.makeText(getContext(), "HAI", Toast.LENGTH_SHORT).show();
        switch (view.getId()) {
            case R.id.checkbox_art:
                if (checked) {
                    //params.put()
                    categories.add("Art");
                }
                // Put some meat on the sandwich
                else {
                    categories.remove("Art");
                }
                // Remove the meat
                break;
            case R.id.checkbox_business:
                if (checked) {
                    categories.add("Business");
                }
                // Cheese me
                else {
                    categories.remove("Business");
                }
                // I'm lactose intolerant
                break;
            case R.id.checkbox_fashion:
                if (checked) {
                    //params.put()
                    categories.add("Fashion");
                }
                // Put some meat on the sandwich
                else {
                    categories.remove("Fashion");
                }
                // Remove the meat
                break;
            case R.id.checkbox_food:
                if (checked) {
                    categories.add("Food");
                }
                // Cheese me
                else {
                    categories.remove("Food");
                }
                // I'm lactose intolerant
                break;
            case R.id.checkbox_health:
                if (checked) {
                    categories.add("Health");
                }
                // Put some meat on the sandwich
                else {
                    categories.remove("Health");
                }
                // Remove the meat
                break;
            case R.id.checkbox_politics:
                if (checked) {
                    categories.add("Politics");
                }
                // Cheese me
                else {
                    categories.remove("Politics");
                }
                // I'm lactose intolerant
                break;
            case R.id.checkbox_sports:
                if (checked) {
                    //params.put()
                    categories.add("Sports");
                }
                // Put some meat on the sandwich
                else {
                    categories.remove("Sports");
                }
                // Remove the meat
                break;
            case R.id.checkbox_travel:
                if (checked) {
                    categories.add("Travel");
                }
                // Cheese me
                else {
                    categories.remove("Travel");
                }
                // I'm lactose intolerant
                break;
        }
    }
}
