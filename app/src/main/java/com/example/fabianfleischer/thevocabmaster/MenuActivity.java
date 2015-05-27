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
    Button btnUeben;
    Button btnErstellen;
    Button btnHighscore;
    //Das ist ein Test, die zweite
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoginStatus();
    }
    public void einbindenOberflaeche() {
        btnUeben = (Button) findViewById(R.id.btnUeben);
        btnStatistik = (Button) findViewById(R.id.btnStatistik);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnErstellen = (Button) findViewById(R.id.btnListeErstellen);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnHighscore = (Button) findViewById(R.id.btnHighscore);
        btnUeben.setOnClickListener(this);
        btnStatistik.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnErstellen.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnHighscore.setOnClickListener(this);
    }
    public void checkLoginStatus() {
/**
 * Dashboard Screen for the application Automatisches vom Fleischer
 * */
// Check login status in database
        userFunctions = new UserFunctions();
        if (userFunctions.isUserLoggedIn(getApplicationContext())) {
            setContentView(R.layout.menu);
            einbindenOberflaeche();
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
            case R.id.btnUeben:
                buttonUebenclicked();
                break;
            case R.id.btnLogout:
                buttonLogout();
                break;
            case R.id.btnHighscore:
                buttonHighscoreclicked();
                break;
        }
    }

    public void buttonLogout(){
        userFunctions.logoutUser(getApplicationContext());
        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        finish();
    }

    public void buttonUebenclicked(){
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

    public void buttonHighscoreclicked(){
        Intent i = new Intent(getApplicationContext(),
                HighscoreActivity.class);
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

