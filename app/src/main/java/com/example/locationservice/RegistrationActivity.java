package com.example.locationservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.button;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText confirm_password= (EditText) findViewById(R.id.confirm_password);


        //create the button to use later
        Button button = (Button) findViewById(R.id.register_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.register_username);
                EditText password = (EditText) findViewById(R.id.register_password);
                String usrnm = username.getText().toString();
                String pswd = password.getText().toString();
                String confirm_pswd = confirm_password.getText().toString();

                if(pswd.equals(confirm_pswd)) {
                    Connect conn = new Connect(RegistrationActivity.this, usrnm, pswd);
                    conn.execute("register", usrnm, pswd);
                }
                else{
                    TextView no_match = (TextView)findViewById(R.id.no_match_password);
                    no_match.setVisibility(View.VISIBLE);
                }


            }
        });

    }





}
