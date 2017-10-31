package com.example.locationservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class ConfigurationActivity extends AppCompatActivity {


    ArrayList<String> global_interests = new ArrayList<>();
    //Set global so can access it outside the onClick method
    String duration = "";
    int toTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        final String interests = null;
        TextView select_interests = (TextView) findViewById(R.id.interests_checkbox);
        select_interests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interest_choices();
                //what to do with interest choices
            }
        });


        //Spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        addItemsOnSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                duration = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //final String final_interests_string = interests_string;

        Button config_button = (Button) findViewById(R.id.confirg_button);
        config_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Variables used in OnClick must be global or final
                //String password = getIntent().getExtras().getString("EXTRA_PASSWORD");
                //Log.d("password \n", password);
                EditText usr_name = (EditText) findViewById(R.id.user_login_entry);
                EditText fullname = (EditText) findViewById(R.id.user_full_name_entry);
                String flnm = fullname.getText().toString();
                String username = usr_name.getText().toString();

                //Get the interests to add to the database when CONFIGURE button in clicked
                String interests_string = "";
                for (int i = 0; i < global_interests.size(); i++) {
                    interests_string += global_interests.get(i);
                    if (i < global_interests.size() - 1) {
                        interests_string += '_';
                    }
                }

                //set the data in the configuration page
//                TextView unu = (TextView)findViewById(R.id.user_name_update);
//                TextView fnu = (TextView)findViewById(R.id.user_full_name_update);
//                TextView iu = (TextView)findViewById(R.id.user_interests_update);
//                TextView fu = (TextView)findViewById(R.id.user_frequency_update);

//                unu.setText(username);
//                fnu.setText(flnm);
//                iu.setText(interests_string);
//                fu.setText(duration);

                Connect con = new Connect(ConfigurationActivity.this, username);
                con.execute("configure", username, flnm, interests_string, duration);
            }
        });


    }


    private void interest_choices() {
        final String[] interests = {"Sciences", "Business", "Computers", "Education"};
        final ArrayList<String> selectedInterests = new ArrayList<>();
//is this correct for the alert dialog builder ???
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                //set dialog title
                .setTitle("Please Select Your Interests")
                //Specify the list array, the items to be selected by default (null or any)
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(interests, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            //if the user checked the interest, add it to the selected interests
                            selectedInterests.add(interests[which]);
                            // global_interests.add(interests[which]);
                        } else if (selectedInterests.contains(interests[which])) {
                            selectedInterests.remove(interests[which]);
                            //  global_interests.remove(interests[which]);
                        }

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //save the selected interests to the global Array
                        for (int i = 0; i < selectedInterests.size(); i++) {
                            global_interests.add(selectedInterests.get(i));
                        }
                        String interests_string = "";
                        for (int i = 0; i < global_interests.size(); i++) {
                            interests_string += global_interests.get(i);
                            if (i < global_interests.size() - 1) {
                                interests_string += '_';
                            }
                        }
                        CheckedTextView checkedEditText = (CheckedTextView) findViewById(R.id.interests_checkbox);
                        checkedEditText.setText(interests_string);

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.create();
        builder.show();
    }

    public void addItemsOnSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> list = new ArrayList<>();
        list.add("10 Seconds");
        list.add("1 Minute");
        list.add("15 Minutes");
        list.add("30 Minutes");
        list.add("60 Minutes");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(dataAdapter);


    }



}

