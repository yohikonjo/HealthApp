package com.project.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class Signup_3 extends AppCompatActivity {

    private static final String TAG = "Signup_3";
    private Button next_btn, login_btn;
    private ImageView back_btn;
    private TextView title_text, data;
    private ScrollView scrollView;
    private TextInputLayout phone_number;
    private CountryCodePicker countryCodePicker;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_3);

        back_btn = findViewById(R.id.signup_3_back_btn);
        next_btn = findViewById(R.id.signup_3_next_btn);
        login_btn = findViewById(R.id.signup_3_login_btn);
        title_text = findViewById(R.id.signup_3_title_text);
        scrollView = findViewById(R.id.scrollview);
        phone_number = findViewById(R.id.PHONE);
        countryCodePicker = findViewById(R.id.country_code);

    }

    public void callNextScreen(View view) {

        if (!validatePhoneNumber()){
            Toast.makeText(this, phone, Toast.LENGTH_SHORT);
            return;
        }

        phone = phone_number.getEditText().getText().toString().trim();
        String number = "+" + countryCodePicker.getSelectedCountryCode() + phone;
        Intent signup_3_intent = new Intent(this, VerifyOTP.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d(TAG, "Bundle is not null");
            Log.d(TAG, "Retrieving data from previous activity");
            String full_name = bundle.getString("FULL NAME");
            String username = bundle.getString("USERNAME");
            String email = bundle.getString("EMAIL");
            String password = bundle.getString("PASSWORD");
            String gender = bundle.getString("GENDER");
            String date = bundle.getString("DATE");

            if(full_name == null || username == null || email == null || password == null || gender == null || date == null){
                Log.d(TAG, "Data received from previous activity are null");
            }
            else{
                Log.d(TAG, "Data received from previous activity are NOT null");
            }

            Log.d(TAG, "Storing data into bundle to pass data to next activity");
            Bundle BUNDLE = new Bundle();
            BUNDLE.putString("PHONE", number);
            BUNDLE.putString("GENDER", gender);
            BUNDLE.putString("DATE", date);
            BUNDLE.putString("FULL NAME", full_name);
            BUNDLE.putString("USERNAME", username);
            BUNDLE.putString("EMAIL", email);
            BUNDLE.putString("PASSWORD", password);

            signup_3_intent.putExtras(BUNDLE);
        }
        else{
            Log.d(TAG, "Bundle is null");
            Toast.makeText(this, number, Toast.LENGTH_SHORT);
        }

                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(back_btn, "transition_back_btn");
                pairs[1] = new Pair<View, String>(next_btn, "transition_title_text");
                pairs[2] = new Pair<View, String>(login_btn, "transition_next_btn");
                pairs[3] = new Pair<View, String>(title_text, "transition_login_btn");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup_3.this, pairs);
                    startActivity(signup_3_intent, options.toBundle());
                } else {
                    startActivity(signup_3_intent);
                }
    }

    private boolean validatePhoneNumber(){
        if (countryCodePicker.isValidFullNumber()){
            Toast.makeText(this, "Entered number is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
}