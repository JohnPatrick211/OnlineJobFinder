package com.example.onlinejobfinder;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context)
    {
        sharedPreferences = context.getSharedPreferences("AppKey",0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void setApplicantLogin(boolean applicantlogin)
    {
        editor.putBoolean("KEY_APPLICANTLOGIN",applicantlogin);
        editor.commit();
    }
    public boolean getApplicantLogin()
    {
        return sharedPreferences.getBoolean("KEY_APPLICANTLOGIN",false);
    }
    public void setEmployerLogin(boolean employerlogin)
    {
        editor.putBoolean("KEY_EMPLOYERLOGIN",employerlogin);
        editor.commit();
    }
    public boolean getEmployerLogin()
    {
        return sharedPreferences.getBoolean("KEY_EMPLOYERLOGIN",false);
    }
    public void setSessionId(String id)
    {
        editor.putString("KEY_ID",id);
        editor.commit();
    }
    public String getSessionId()
    {
        return  sharedPreferences.getString("KEY_ID","");
    }
    public void setSessionToken(String token)
    {
        editor.putString("KEY_TOKEN",token);
        editor.commit();
    }
    public String getSessionToken()
    {
        return  sharedPreferences.getString("KEY_TOKEN","");
    }
    public void setSessionName(String name)
    {
        editor.putString("KEY_NAME",name);
        editor.commit();
    }
    public String getSessionName()
    {
        return  sharedPreferences.getString("KEY_NAME","");
    }
    public void setSessionEmail(String email)
    {
        editor.putString("KEY_EMAIL",email);
        editor.commit();
    }
    public String getSessionEmail()
    {
        return  sharedPreferences.getString("KEY_EMAIL","");
    }

}
