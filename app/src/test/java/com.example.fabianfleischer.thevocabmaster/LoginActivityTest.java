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
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.R;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class LoginActivityTest{

    private LoginActivity activity;
    @Before
    public void setup()  {
        activity = Robolectric.buildActivity(LoginActivity.class).create().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void clickingLoginButton_shouldChangeActivityToMenu() throws Exception {

        Button btnLogin = (Button) activity.findViewById(R.id.btnLogin);

        btnLogin.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(MenuActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void clickingRegisterButton_shouldChangeActivityToRegister() throws Exception {

        Button btnLogin = (Button) activity.findViewById(R.id.btnLinkToRegisterScreen);

        btnLogin.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(RegisterActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void clickingLoginButton_getError() throws NullPointerException {

        Button btnLogin = (Button) activity.findViewById(R.id.btnLogin);
        EditText inputEmail = (EditText) activity.findViewById(R.id.loginEmail);
        EditText inputPassword = (EditText) activity.findViewById(R.id.loginPassword);

        inputEmail.setText("BlubberBernd@long.de");
        inputPassword.setText("Blubber");
        btnLogin.performClick();
        assertThat(ShadowToast.getTextOfLatestToast().toString(), equalTo("Login Error"));
    }
}

