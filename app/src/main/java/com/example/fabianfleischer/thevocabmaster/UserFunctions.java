package com.example.fabianfleischer.thevocabmaster;

/**
 * Created by fabian.fleischer on 22.10.2014.
 */

import android.content.Context;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFunctions {

    private JSONParser jsonParser;
    private DatabaseHandler dbhandler;

    private static String loginURL = "http://193.196.7.23/android_login_api/";
    private static String registerURL = "http://193.196.7.23/android_login_api/";

    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String read_tag = "give";
    private static String question_tag = "question";

    // constructor
    public UserFunctions() {
        jsonParser = new JSONParser();
    }

    //login with user provided email/pass
    public JSONObject loginUser(String email, String password) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        //Log.v("userfunctions", "loginuser");
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        return json;
    }

    //register a new user with name/email/pass
    public JSONObject registerUser(String name, String email, String password) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));


        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }

    public JSONObject readData() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", read_tag));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        return json;
    }

    //determine if the user is logged in
    public boolean isUserLoggedIn(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if (count > 0) {
            // user logged in
            return true;
        }
        return false;
    }

    //logout the user
    public boolean logoutUser(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }

    public void startDBHandler(Context context) {
        dbhandler = new DatabaseHandler(context);
    }

    public void addUser(String user) {
        dbhandler.addUser(user);
    }

    public void addUser(String name, String mail, String uid, String create){
        dbhandler.addUser(name, mail, uid, create);
    }
}