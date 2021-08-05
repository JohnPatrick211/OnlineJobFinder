package com.example.onlinejobfinder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

/**
 * Designed and Developed by Mohammad suhail ahmed on 27/02/2020
 */
public class CheckInternet {
    public boolean checkInternet(final Context context) {
        boolean res;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.i("internet", "connected to internet");
            res = true;
        } else {
            res = false;
        }
        return res;
    }
}
