package com.project.healthapp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.healthapp.R;

import java.util.Calendar;

public class Signup_2 extends AppCompatActivity {

    private static final String TAG = "Signup_2";
    private Button next_btn, login_btn;
    private ImageView back_btn;
    private TextView title_text;
    private RadioGroup radioGroup;
    RadioButton gender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_2);

        back_btn = findViewById(R.id.signup_2_back_btn);
        next_btn = findViewById(R.id.signup_2_next_btn);
        login_btn = findViewById(R.id.signup_2_login_btn);
        title_text = findViewById(R.id.signup_2_title_text);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.date_picker);
    }

    public void callNextSignupScreen(View view) {

        if (!validateAge() | !validateGender()){
            return;
        }

        gender = findViewById(radioGroup.getCheckedRadioButtonId());
        String Gender = gender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String date = day +"/"+ month +"/"+ year;

        Intent signup_2_intent = new Intent(getApplicationContext(), Signup_3.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d(TAG, "Bundle is not null");
            Log.d(TAG, "Retrieving data from previous activity");
            String full_name = bundle.getString("FULL NAME");
            String username = bundle.getString("USERNAME");
            String email = bundle.getString("EMAIL");
            String password = bundle.getString("PASSWORD");

            if(full_name == null || username == null || email == null || password == null){
                Log.d(TAG, "Data received from previous activity are null");
            }
            else{
                Log.d(TAG, "Data received from previous activity are NOT null");
            }

            Log.d(TAG, "Storing data into bundle to pass data to next activity");
            Bundle BUNDLE = new Bundle();
            BUNDLE.putString("FULL NAME", full_name);
            BUNDLE.putString("USERNAME", username);
            BUNDLE.putString("EMAIL", email);
            BUNDLE.putString("PASSWORD", password);
            BUNDLE.putString("GENDER", Gender);
            BUNDLE.putString("DATE", date);

            if(BUNDLE == null){
                Log.d(TAG, "Data saved in the bundle");
            }

            signup_2_intent.putExtras(BUNDLE);
        }
        else{
            Intent signup_intent = new Intent(getApplicationContext(), Signup.class);
            startActivity(signup_intent);
            Toast.makeText(this, "Data has not been transferred properly", Toast.LENGTH_SHORT).show();
        }



        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(back_btn, "transition_back_btn");
        pairs[1] = new Pair<View, String>(next_btn, "transition_title_text");
        pairs[2] = new Pair<View, String>(login_btn, "transition_next_btn");
        pairs[3] = new Pair<View, String>(title_text, "transition_login_btn");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup_2.this, pairs);
            startActivity(signup_2_intent, options.toBundle());
        } else {
            startActivity(signup_2_intent);
        }

    }

    private boolean validateGender(){
        if (radioGroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get((Calendar.YEAR));
        int userage = datePicker.getYear();
        int isAgeValid = currentYear - userage;

        if(isAgeValid < 13){
            Toast.makeText(this, "You have to be at least 13 years old to create an account", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
}