package com.example.onlinejobfinder.employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.EmailActivity;
import com.example.onlinejobfinder.MainActivity;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.SendMail;
import com.example.onlinejobfinder.SendRequestMail;
import com.example.onlinejobfinder.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class RequestMaintenanceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    Button btn_sendrequest;
    ProgressDialog progressDialog;
    EditText edittext_subject, edittext_message,edittext_email;
    String name2, user_id,token,permaid, address, email, contactno, background;
    String val_contactno = "";
    String val_specialization = "";
    SessionManager sessionManager;
    SharedPreferences userPref2;
    ImageView imageprofile;
    TextView textnameprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_maintenance);
        btn_sendrequest = findViewById(R.id.btnsendrequestmaintenance);
        edittext_message = findViewById(R.id.edittext_requestmaintenance_message);
        edittext_subject = findViewById(R.id.edittext_requestmaintenance_subject);
        edittext_email = findViewById(R.id.edittext_requestmaintenance_sender);
        userPref2 = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref2.edit();
        drawerLayout = findViewById(R.id.drawerLayout2);
        navigationView = findViewById(R.id.navigation_view2);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbarmaintitle);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        name2 = userPref2.getString("name","name");
        email = userPref2.getString("email","email");
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        edittext_email.setText(email);
        sessionManager = new SessionManager(getApplicationContext());

        String intentname = getIntent().getExtras().getString("name");
        imageprofile = navigationView.getHeaderView(0).findViewById(R.id.drawerimageviewprofile);
        textnameprofile = navigationView.getHeaderView(0).findViewById(R.id.drawerprofilename);
        textnameprofile.setText(intentname);


        try {
            String checknull = getIntent().getExtras().getString("profile_pic");
            if (checknull.equals(Constant.URL+"/storage/profiles/null")) {
                imageprofile.setImageResource(R.drawable.img);
            } else {
                Picasso.get().load(getIntent().getStringExtra("profile_pic")).into( imageprofile);
            }
        } catch (Exception e) {
            imageprofile.setImageResource(R.drawable.img);
        }

        navigationView.setNavigationItemSelectedListener(this);

        btn_sendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendRequestMail sm = new SendRequestMail(RequestMaintenanceActivity.this, "admin@pesobalayan-ojfs.online","pesostaff@pesobalayan-ojfs.online", edittext_subject.getText().toString(),
                        "Employer Email: " + edittext_email.getText().toString()+ "\n" +
                                edittext_message.getText().toString(),
                        "peso-notification@pesobalayan-ojfs.online");
                sm.execute();
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.navigation_home_employer:
                Intent ia1 = new Intent(RequestMaintenanceActivity.this, EmployerActivity.class);
                ia1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ia1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(ia1);
                break;
            case R.id.navigation_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to logout?");
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        SharedPreferences.Editor editor = userPref.edit();
                        //                       editor.clear();
                        //                      editor.apply();
                        sessionManager.setEmployerLogin(false);
                        Intent ia = new Intent(RequestMaintenanceActivity.this, MainActivity.class);
                        startActivity(ia);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;
            case R.id.navigation_requestmaintenance:
                drawerLayout.closeDrawers();
                break;
            case R.id.navigation_applicanthired:
                Intent ia11 = new Intent(RequestMaintenanceActivity.this, ApplicantHiredActivity.class);
                ia11.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ia11.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ia11.putExtra("name",textnameprofile.getText().toString());
                ia11.putExtra("profile_pic", getIntent().getStringExtra("profile_pic"));
                startActivity(ia11);
                break;
        }
        return true;
    }

    public void onResume() {
        super.onResume();
        drawerLayout.closeDrawers();
    }
}