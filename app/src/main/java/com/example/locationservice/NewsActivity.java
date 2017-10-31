package com.example.locationservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);




        Intent intent = getIntent();
        String result = intent.getStringExtra("return");
        String [] r = result.split(",");
        String articles= "";
//        Log.d("at array i = 1 \n", r[1]);
//        Log.d("at i =2 \n", r[2]);
//        Log.d("at i =3 \n ", r[3]);
        for(int i = 1; i< r.length; i++){
            articles += r[i] + "\n \n";

        }
//        String number_of_elements = intent.getStringExtra("NUMBER_OF_ELEMENTS");
//        int numbElements=  Integer.parseInt(number_of_elements);
//        String articles ="";
//        //get all of the articles into a single string
//        for(int i = 1; i <= numbElements; i++){
//            articles += intent.getStringExtra("article"+i);
//            if(i < numbElements){
//                articles += "\n";
//            }
//        }

        TextView url_display = (TextView) findViewById(R.id.new_input);
        url_display.setText(articles);



    }
}
