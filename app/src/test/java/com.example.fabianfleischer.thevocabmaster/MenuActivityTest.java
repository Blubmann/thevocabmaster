package com.example.fabianfleischer.thevocabmaster;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import android.content.Intent;
import android.widget.Button;

import com.example.R;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class MenuActivityTest{

    private MenuActivity activity;
    private LoginActivity activityL;
    @Before
    public void setup() {
        activityL = Robolectric.buildActivity(LoginActivity.class).create().get();
        Button btnLogin = (Button) activityL.findViewById(R.id.btnLogin);
        btnLogin.performClick();
        activity = Robolectric.buildActivity(MenuActivity.class).create().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void clickingLoginButton_shouldChangeActivityToUeben() throws Exception {

        Button btnU = (Button) activity.findViewById(R.id.btnUeben);
        btnU.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(UebenActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void clickingLoginButton_shouldChangeActivityToErstellen() throws Exception {

        Button btnErstellen = (Button) activity.findViewById(R.id.btnListeErstellen);
        btnErstellen.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(ListeActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }
}
