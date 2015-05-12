package com.example.fabianfleischer.thevocabmaster;

/**
 * Created by fabian.fleischer on 22.10.2014.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;

import com.example.R;
import com.example.fabianfleischer.thevocabmaster.library.DatabaseHandler;
import com.example.fabianfleischer.thevocabmaster.library.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginTask extends AsyncTask<String, Void, Integer> {

    private ProgressDialog progressDialog;
    private LoginActivity activity;
    private UserFunctions userFunction;
    private int id = -1;
    private static String loginURL = "http://193.196.7.23/android_login_api/";
    private static String registerURL = "http://193.196.7.23/android_login_api/";
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    private int responseCode = 0;

    public LoginTask(LoginActivity activity, ProgressDialog progressDialog)
    {
        this.activity = activity;
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onPreExecute()
    {
        progressDialog.show();
    }

    protected Integer doInBackground(String... arg0) {
        EditText userName = (EditText)activity.findViewById(R.id.loginEmail);
        EditText passwordEdit = (EditText)activity.findViewById(R.id.loginPassword);
        String email = userName.getText().toString();
        String password = passwordEdit.getText().toString();
        userFunction = new UserFunctions();
        JSONObject json = userFunction.loginUser(email, password);

        // check for login response
        try {
            if (json.getString(KEY_SUCCESS) != null) {
                String res = json.getString(KEY_SUCCESS);

                if(Integer.parseInt(res) == 1){
                    //user successfully logged in
                    // Store user details in SQLite Database
                    userFunction.startDBHandler(activity.getApplicationContext());
                    JSONObject json_user = json.getJSONObject("user");
                    //Log.v("name", json_user.getString(KEY_NAME));
                    // Clear all previous data in database
                    userFunction.logoutUser(activity.getApplicationContext());
                    userFunction.addUser(json_user.getString(KEY_EMAIL));

                    responseCode = 1;
                    // Close Login Screen
                    //finish();

                }else{
                    responseCode = 0;
                    // Error in login
                }
            }

        } catch (NullPointerException e) {
            e.printStackTrace();

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return responseCode;
    }

    @Override
    protected void onPostExecute(Integer responseCode)
    {
        EditText userName = (EditText)activity.findViewById(R.id.loginEmail);
        EditText passwordEdit = (EditText)activity.findViewById(R.id.loginPassword);


        if (responseCode == 1) {
            progressDialog.dismiss();
            Intent i = new Intent();
            i.setClass(activity.getApplicationContext(), MenuActivity.class);
            activity.startActivity(i);
            //activity.loginReport(responseCode);
        }
        else {
            progressDialog.dismiss();
            activity.showLoginError(responseCode);

        }

    }
}