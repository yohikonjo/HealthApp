package com.project.healthapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {

    private static final String TAG = "Signup";
    private ImageView back_btn;
    private TextView title_text;
    private Button next_btn, login_btn;

    private TextInputLayout FullName, UserName, Email, Password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        back_btn = findViewById(R.id.signup_back_btn);
        next_btn = findViewById(R.id.signup_next_btn);
        login_btn = findViewById(R.id.signup_login_btn);
        title_text = findViewById(R.id.signup_title_text);

        FullName= findViewById(R.id.full_name);
        UserName = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);

    }

    public void callNextSignupScreen(View view) {

        if(!validateFullName() | !validateUserName() | !validateEmail() | !validatePassword()){
            return;
        }
        Log.d(TAG, "Retrieving data from activity and storing in string");
        String full_name = FullName.getEditText().getText().toString().trim();
        String username = UserName.getEditText().getText().toString().trim();
        String email = Email.getEditText().getText().toString().trim();
        String password = Password.getEditText().getText().toString().trim();

        if(full_name == null || username == null || email == null || password == null){
            Log.d(TAG, "Data received from activity are null");
        }
        else{
            Log.d(TAG, "Data received from previous activity are NOT null");
        }

        Log.d(TAG, "Storing data into bundle to pass data to next activity");
        Bundle bundle = new Bundle();
        bundle.putString("FULL NAME", full_name);
        bundle.putString("USERNAME", username);
        bundle.putString("EMAIL", email);
        bundle.putString("PASSWORD", password);

        Intent signup_intent = new Intent(getApplicationContext(), Signup_2.class);
        signup_intent.putExtras(bundle);

        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(back_btn, "transition_back_btn");
        pairs[1] = new Pair<View, String>(next_btn, "transition_title_text");
        pairs[2] = new Pair<View, String>(login_btn, "transition_next_btn");
        pairs[3] = new Pair<View, String>(title_text, "transition_login_btn");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup.this, pairs);
            startActivity(signup_intent, options.toBundle());
        } else {
            startActivity(signup_intent);
        }

    }

    private boolean validateFullName(){
        String val = FullName.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            FullName.setError("Field can not be empty");
            return false;
        }
        else {
            FullName.setError(null);
            FullName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateUserName(){
        String val = UserName.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        if(val.isEmpty()){
            UserName.setError("Field can not be empty");
            return false;
        }
        else if(val.length()>20){
            UserName.setError("Username is too long");
            return false;
        }
        else if(!val.matches(checkspaces)){
            UserName.setError("White spaces are not allowed");
            return false;
        }
        else {
            UserName.setError(null);
            UserName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateEmail(){
        String val = Email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            Email.setError("Field can not be empty");
            return false;
        }
        else if(!val.matches(checkEmail)){
            Email.setError("Invalid Email!");
            return false;
        }
        else {
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword(){
        String val = Password.getEditText().getText().toString().trim();
        String checkPassword = "^" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";

        if(val.isEmpty()){
            Password.setError("Field can not be empty");
            return false;
        }
        else if(!val.matches(checkPassword)){
            Password.setError("Password format is incorrect please review entry");
            return false;
        }
        else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }

    }

}
