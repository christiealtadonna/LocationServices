package com.example.locationservice;

import android.app.Activity;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import com.example.locationservice.Functions.GetMac;


public class LoginActivity extends AppCompatActivity {

    private TextView user_name_textview, user_fullname, user_interests, user_frequency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        user_name_textview = (TextView) findViewById(R.id.user_name);
        user_fullname = (TextView) findViewById(R.id.user_full_name);
        user_interests = (TextView) findViewById(R.id.user_interests);
        user_frequency = (TextView) findViewById(R.id.user_frequency);

        //TextViews that will display on the main screen


        Button login_button = (Button) findViewById(R.id.login);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass in these values to set them on the main page

                //When user logs in- put into the database where the user is
//                GetMac get_mac = new GetMac(LoginActivity.this);
//                final String local_mac = get_mac.getMacAddr();


                EditText username = (EditText) findViewById(R.id.username);
                String user_name = username.getText().toString();
                Log.d("Username \n", user_name);
                EditText pswd = (EditText) findViewById(R.id.password);
                String password = pswd.getText().toString();
                Log.d("Password \n", password);

                Connect con = new Connect(LoginActivity.this, user_name_textview, user_name, password);
                //must use instance of class that connects to database
                Log.d("entered button click", "button click");
                con.execute("login", user_name, password);


            }
        });


    }


}

//    Intent result_intent = new Intent();
//    LocationUpdateActivity update_location = new LocationUpdateActivity();
//    //start thread
//    if (update_location.update_thread.getState() == Thread.State.NEW) {
//        update_location.update_thread.start();
//    }
//    setResult(RESULT_OK, result_intent);
//    finish();
//
//
//
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        if(requestCode == 0){
//            if(resultCode != Activity.RESULT_OK){
//
//            }
//        }












