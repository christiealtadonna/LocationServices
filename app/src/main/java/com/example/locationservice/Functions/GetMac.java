package com.example.locationservice.Functions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by christiealtadonna on 2/6/17.
 */

public class GetMac extends Thread {
    //use wifi manager
    Context current_context;
    WifiManager wifiManager;

    String Local_Mac; //mobile device mac address
    String Connected_Mac; //mac adress of wifi connected to

    //contructor
    public GetMac(Context context) {
        this.current_context = context;
        init(); //initialization behavior
    }

    public void init() {
        wifiManager = (WifiManager) current_context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        wifiManager.startScan();

    }

    public String get_Local_Mac() {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        //getting connecting mac
        this.Local_Mac = wifiInfo.getMacAddress();
        return this.Local_Mac;
    }



    //create a method called get connected mac
    public String get_connect(){
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        this.Connected_Mac = wifiInfo.getBSSID();
        return this.Connected_Mac;

    }


    //Code to get list of local wifi
    private final BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
                List<ScanResult> scanResults = wifiManager.getScanResults();
            }
        }
    };




    public String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0") && !nif.getName().equalsIgnoreCase("eth0"))
                    continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {

        }
        return "02:00:00:00:00:00";
    }
}








