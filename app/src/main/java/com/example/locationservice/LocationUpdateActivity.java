package com.example.locationservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationservice.Functions.GetMac;

public class LocationUpdateActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_update);

        Button search_button = (Button) findViewById(R.id.search_friends_button);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText find_name = (EditText) findViewById(R.id.find_friends_name);
                TextView ret_location = (TextView) findViewById(R.id.display_location);
                Connect conn = new Connect(LocationUpdateActivity.this, ret_location);

                String friend_name = find_name.getText().toString();
                //conn.execute("get_connected_mac", friend_name);

                conn.execute("get_location", friend_name);

            }
        });


    }

}

