package com.example.locationservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationservice.Functions.GetMac;

import java.util.List;

//use a thread-set periodic thread

public class MainActivity extends AppCompatActivity implements Runnable {

    int Current_user_update_frequency;// = 5; //per 5 seconds
    Thread update_thread = new Thread(MainActivity.this); //Thread
    boolean show_info_flag = true;
    String username;
//    WifiManager wifiManager;
//    List<String> around_wifi_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        String fullname = intent.getStringExtra("fullname");
        final String interest = intent.getStringExtra("interest");
        String update_freq = intent.getStringExtra("update_frequency");


       // Log.d("update frequency",update_freq);

        //get user frequency to update duraction
        //String freq;//= update_freq.substring(0,2);
//       int freq_number=0;
//        //Current_user_update_frequency= Integer.parseInt(freq);
//        if(update_freq.equals("10")){
//            //freq = update_freq.substring(0,2);
//            freq_number =Integer.parseInt(update_freq);
//        }
//        else if(update_freq.equals("15") || update_freq.equals("30")||update_freq.equals("60")){
//            freq_number =Integer.parseInt(update_freq);
//            freq_number = freq_number*60;
//        }
//        else{
//            int one = update_freq.charAt(0);
//            freq_number = one*60;
//        }

//        update_freq.trim();
//        Current_user_update_frequency = Integer.parseInt(update_freq);



        if(update_freq.equals("1 Minute")){
            Current_user_update_frequency = 60;
        }
        else if(update_freq.equals("15 Minutes")){
            Current_user_update_frequency = 15*60;
        }
        else if(update_freq.equals("30 Minutes")){
            Current_user_update_frequency= 30*60;
        }
        else if(update_freq.equals("60 Minutes")){
            Current_user_update_frequency = 60*60;
        }
        else{
            Current_user_update_frequency = 10;
        }

        String error = ""+Current_user_update_frequency;
        Log.d("frequency number",error);


        TextView unu = (TextView)findViewById(R.id.user_name_update);
        TextView fnu = (TextView)findViewById(R.id.user_full_name_update);
        TextView iu = (TextView)findViewById(R.id.user_interests_update);
        TextView fu = (TextView)findViewById(R.id.user_frequency_update);

        unu.setText(username);
        fnu.setText(fullname);
        iu.setText(interest);
        fu.setText(update_freq);



//        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        startActivityForResult(intent, MY_REQUEST_ID);

        Button configure_button = (Button) findViewById(R.id.configure_main_button);
        configure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = getIntent().getExtras().getString("EXTRA_PASSWORD");
                Intent intent = new Intent(MainActivity.this, ConfigurationActivity.class);
                intent.putExtra("EXTRA_PASSWORD", password);
                startActivity(intent);
            }
        });

        Button find_friends = (Button) findViewById(R.id.location_button);
        find_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationUpdateActivity.class);
                startActivity(intent);
            }
        });


        Button news_button = (Button) findViewById(R.id.news_button);
        news_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connect conn = new Connect(MainActivity.this);
                //get interest from post execute of login or configuration
                //use to generate news articles user is interested in
                conn.execute("get_news",interest);
            }
        });



        //start thread
        if (this.update_thread.getState() == Thread.State.NEW) {
            this.update_thread.start();
        }




    }



//    //Code to get list of local wifi
//    final BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
//                List<ScanResult> scanResults = wifiManager.getScanResults();
//                for(int i = 0; i < scanResults.size(); i++){
//                    around_wifi_list.add(scanResults.get(i).BSSID);
//                }
//            }
//        }
//    };
//
//

    @Override
    protected void onPause() {
        super.onPause();
        this.show_info_flag = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.show_info_flag = true;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (show_info_flag) {
                    // Get Mac Address of this local device.
                    final GetMac get_mac = new GetMac(MainActivity.this);
                    final String local_mac = get_mac.getMacAddr();//get_mac.get_Local_Mac();
                    //get connected Mac Address to find location
                    final String connected_mac = get_mac.get_connect();
//                    String wifi_list ="";
//                    for(int i = 0; i < around_wifi_list.size(); i++){
//                        wifi_list += around_wifi_list.get(i);
//                        if(i < around_wifi_list.size() - 1){
//                            wifi_list += "_";
//                        }
//                    }


                    // Print information in Logcat.
                    Log.d("xincoder_thread", local_mac);

                    // Toast information
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "Mac Address of this device:\n" + local_mac, Toast.LENGTH_SHORT).show();
                            //get username to identify which user's device mac
                            //pass device mac to update databast

//                            //generate wifi list
//                            String wifi_list ="";
//                            for(int i = 0; i < around_wifi_list.size(); i++){
//                                wifi_list += around_wifi_list.get(i);
//                                if(i < around_wifi_list.size() - 1){
//                                    wifi_list += "_";
//                                }
//                            }

//                            Log.d("Connect mac is: ", connected_mac); FIX THIS
                            Connect conn = new Connect(MainActivity.this);
                            conn.execute("get_mac",username,local_mac, connected_mac);// wifi_list);

                        }
                    });
                }

                // Sleep 5 (Current_user_update_frequency*1000) seconds.
                Thread.sleep(this.Current_user_update_frequency * 1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
