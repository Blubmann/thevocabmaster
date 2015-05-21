package com.example.fabianfleischer.thevocabmaster;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;

import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.R;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class MenuActivityTest {

    private MenuActivity activity;
    @Before
    public void setup(){
        activity = Robolectric.buildActivity(MenuActivity.class).create().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void clickingLoginButton_shouldChangeActivityToUeben() throws NullPointerException {

        Button btnUeben = (Button) activity.findViewById(R.id.btnUeben);
        btnUeben.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(DashboardActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void clickingLoginButton_shouldChangeActivityToErstellen() throws NullPointerException {

        Button btnErstellen = (Button) activity.findViewById(R.id.btnListeErstellen);
        btnErstellen.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(ListeActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }
}
