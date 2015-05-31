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
import org.robolectric.shadows.ShadowToast;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.example.R;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class RegisterActivityTest {

    private RegisterActivity activity;
    @Before
    public void setup()  {
        activity = Robolectric.buildActivity(RegisterActivity.class).create().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void clickingLoginButton_shouldChangeActivityToLogin() throws Exception {

        Button btnLogin = (Button) activity.findViewById(R.id.btnLinkToLoginScreen);

        btnLogin.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(LoginActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void clickingRegisterButton_getError() throws NullPointerException {

        Button btnRegister = (Button) activity.findViewById(R.id.btnRegister);
        EditText inputName = (EditText) activity.findViewById(R.id.registerName);
        EditText inputEmail = (EditText) activity.findViewById(R.id.registerEmail);
        EditText inputPassword = (EditText) activity.findViewById(R.id.registerPassword);

        inputName.setText("Bernd");
        inputEmail.setText("BlubberBernd@long.de");
        inputPassword.setText("Blubber");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("Register Error"));
    }

    @Test
    public void clickingRegisterButton_shouldChangeActivityToDashboard() throws Exception {

        Button btnRegister = (Button) activity.findViewById(R.id.btnRegister);
        EditText inputName = (EditText) activity.findViewById(R.id.registerName);
        EditText inputEmail = (EditText) activity.findViewById(R.id.registerEmail);
        EditText inputPassword = (EditText) activity.findViewById(R.id.registerPassword);

        inputName.setText("Bernd");
        inputEmail.setText("BlubberBernd@long.de");
        inputPassword.setText("Blubber");
        btnRegister.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(UebenActivity.class.getCanonicalName(), intent.getComponent().getClassName());    }
}
