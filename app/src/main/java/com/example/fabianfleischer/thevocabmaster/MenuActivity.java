package com.example.fabianfleischer.thevocabmaster;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.R;
import com.example.fabianfleischer.thevocabmaster.library.UserFunctions;

/**
 * Created by fabian.fleischer on 01.11.2014.
 */
public class MenuActivity extends Activity implements View.OnClickListener {
    UserFunctions userFunctions;
    Button btnLogout;
    Button btnSettings;
    Button btnStatistik;
    Button btnÜben;
    Button btnListeErstellen;
    //Das ist ein Test, die zweite
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoginStatus();
        init();
    }
    public void init() {
        einbindenOberflaeche();
    }
    public void einbindenOberflaeche() {
        btnÜben = (Button) findViewById(R.id.btnÜben);
        btnStatistik = (Button) findViewById(R.id.btnStatistik);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnListeErstellen = (Button) findViewById(R.id.btnListeErstellen);
        btnÜben.setOnClickListener(this);
        btnStatistik.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnListeErstellen.setOnClickListener(this);
    }
    public void checkLoginStatus() {
/**
 * Dashboard Screen for the application Automatisches vom Fleischer
 * */
// Check login status in database
        userFunctions = new UserFunctions();
        if (userFunctions.isUserLoggedIn(getApplicationContext())) {
            setContentView(R.layout.menu);
            btnLogout = (Button) findViewById(R.id.btnLogout);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
// TODO Auto-generated method stub
                    userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
// Closing dashboard screen
                    finish();
                }
            });
        } else {
// user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
// Closing dashboard screen
            finish();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) { //Checkt welches Element geklickt wurde.
            case R.id.btnSettings:
                buttonSettingsclicked();
                break;
            case R.id.btnListeErstellen:
                buttonErstellenClicked();
                break;
            case R.id.btnStatistik:
                buttonStatistikMenueclicked();
                break;
            case R.id.btnÜben:
                buttonÜbenclicked();
                break;
        }
    }
    public void buttonÜbenclicked(){
        Intent i = new Intent(getApplicationContext(),
                DashboardActivity.class);
        startActivity(i);
        finish();
    }

    public void buttonErstellenClicked(){
        Intent i = new Intent(getApplicationContext(),
                ListeActivity.class);
        startActivity(i);
        finish();
    }
    public void buttonStatistikMenueclicked(){
//TODO
    }
    public void buttonSettingsclicked(){
//TODO
    }
}

