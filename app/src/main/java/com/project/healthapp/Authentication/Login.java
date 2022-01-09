package com.project.healthapp.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.healthapp.MainActivity;
import com.project.healthapp.R;
import com.project.healthapp.Welcome;

public class Login extends AppCompatActivity {

    private ImageView back_btn;
    private Button forgot_password, login, create_account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        back_btn = findViewById(R.id.login_back_button);
        forgot_password = findViewById(R.id.forgot_password);
        login = findViewById(R.id.login);
        create_account = findViewById(R.id.create);

    }

    public void callPreviousScreen(View view) {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }

    public void Authenticate(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void callSignupScreen(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    public void callForgotMyPasswordScreen(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        finish();
        startActivity(intent);
    }
}
