package com.example.locationservice;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.data;

/**
 * Created by christiealtadonna on 1/30/17.
 * can use for login, registration and configuration
 */




public class Connect extends AsyncTask<String,Void,String>  {

    TextView user_name_connect, location;
    private Context context;
    private String full_name;

    private String username_class;
    private String password_class;
//    private String user_frequency_class;
//    private String fullname_class;
//    private String interests_class;


    //Take these in constructor to display on main page
    public Connect(Context context, TextView user_name, String username, String password) {
        this.context = context;
        this.user_name_connect = user_name;
        this.username_class = username;
        this.password_class = password;

    }

    //Connect constructor for registration
    public Connect(Context context, String username_class, String password_class) {
        this.context = context;
        this.username_class = username_class;
        this.password_class = password_class;

    }

    //Connect constructor for configuration
    public Connect(Context context, String username_class) {
        this.context = context;
        this.username_class = username_class;
    }

    //Connect constructor for get_location
    public Connect(Context context, TextView location){
        this.context = context;
        this.location = location;
    }

    //Constructor for MainActivity
    public Connect(Context context){
        this.context = context;
    }

    protected void onPreExecute(){}


    @Override
    protected String doInBackground(String... params) {

        Log.d("entered doInBackground","eneter");
        //POST method
        try {
            String method = (String) params[0];

            //String method = "login";
            String username = (String)params[1];
            Log.d("username in Connect :",username);

            String data = "";
            if(method.equals("login")) {
                String password = (String)params[2];
                data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            }
            else if(method.equals("register")){
                String password = (String)params[2];
                data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            }
            else if(method.equals("configure")){
                String fullname = (String) params[2];
                String interests = (String) params[3];
                String update_frequency = (String) params[4];
                data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
               // data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                data += "&" + URLEncoder.encode("fullname", "UTF-8") + "=" + URLEncoder.encode(fullname, "UTF-8");
                data += "&" + URLEncoder.encode("interests", "UTF-8") + "=" + URLEncoder.encode(interests, "UTF-8");
                data += "&" + URLEncoder.encode("update_frequency", "UTF-8") + "=" + URLEncoder.encode(update_frequency, "UTF-8");


            }
            else if(method.equals("get_mac")){
                String device_mac =params[2];
                String connected_mac = params[3];
                //String around_wifi_list = params[4];
                Log.d("device mac",device_mac);
                data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data += "&" + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("device_mac", "UTF-8") + "=" + URLEncoder.encode(device_mac, "UTF-8");
                data += "&" + URLEncoder.encode("connected_mac", "UTF-8") + "=" + URLEncoder.encode(connected_mac, "UTF-8");
                //data += "&" + URLEncoder.encode("around_wifi_list", "UTF-8") + "=" + URLEncoder.encode(around_wifi_list, "UTF-8");
            }

            else if(method.equals("get_location")){
                String fullname =params[1];
                full_name = fullname;
                data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data += "&" + URLEncoder.encode("fullname", "UTF-8") + "=" + URLEncoder.encode(fullname, "UTF-8");
            }

            else if(method.equals("get_news")){
                String interest = params[1];
                data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8");
                data += "&" + URLEncoder.encode("interest", "UTF-8") + "=" + URLEncoder.encode(interest, "UTF-8");
            }

            //fill in your_computer_id ******
            //MAKE SURE TO CHANGE TO hw1sublime.php
            //String link = "http://localhost:8888/hw1sublime.php";
            String link = "http://192.168.1.126:8888/demo/hw1sublime.php";
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            Log.d("Exc conn.setDoOutput",data);
            //write this data to the link
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            Log.d("Exc outstrm",data);
            wr.write(data);
            wr.flush();
            Log.d("Excflush:",data);
            //Open stream to receice the responded message
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Log.d("exc buffread:", data);
            StringBuilder sb = new StringBuilder();
            String line = null;
            Log.d("Exc after strg builder", "sting");
            //Read Server Response
            while ((line = reader.readLine()) != null) {
                Log.d("enter while ","enter");
                sb.append(line);
            }
            Log.d("My Result:", sb.toString());

            return sb.toString();




        } catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
            return new String("Exception: " + e.getMessage());
        }
    }



    protected void onPostExecute(String result){
       // String res = result;
       // String res = result.substring(0,5);

        String [] r = result.split(";");
        if(r[0].startsWith("Login")){
            this.context.startActivity(new Intent(context, MainActivity.class).putExtra("username",r[1]).putExtra("fullname",r[2]).putExtra("interest",r[3]).putExtra("update_frequency",r[4]));
        }
        else if(result.equals("Success Registration")){
            this.context.startActivity((new Intent(context, ConfigurationActivity.class)).putExtra("EXTRA_USERNAME",this.username_class).putExtra("EXTRA_PASSWORD",this.password_class));

        }
        else if(r[0].equals("Configured")){
            this.context.startActivity(new Intent(context, MainActivity.class).putExtra("username",r[1]).putExtra("fullname",r[2]).putExtra("interest",r[3]).putExtra("update_frequency",r[4]));


        }
        else if(result.equals("Success Device Mac Update")){
            Log.d("success","in onPostExecute");
        }
        else if(r[0].startsWith("Location")){
            location.setVisibility(View.VISIBLE);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateObject = new Date();
            df.format(dateObject);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date inputDate = formatter.parse(r[1]);
                Log.d("Parse exception", "error");
                int dif = (int) (dateObject.getTime() - inputDate.getTime());
                int hour = dif / 3600;
                location.setText(full_name + ": \n " + hour + " hour(s) \n " + r[2] + "\n " + r[3]);
            }
            catch(ParseException e) {
            }
        }
        else if(result.startsWith("News")){
            this.context.startActivity(new Intent(this.context, NewsActivity.class).putExtra("return",result));
        }


    }


}




