package com.project.healthapp.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.healthapp.MainActivity;
import com.project.healthapp.R;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    private static final String TAG = "VerifyOTP";

    private PinView pinView;
    private TextView textView;
    private ImageView imageView;
    private Button button;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String codebySystem;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    String phone, full_name, gender, username, email, password, birth_date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_otp);

        mAuth = FirebaseAuth.getInstance();

        pinView = findViewById(R.id.pin_view);
        textView = findViewById(R.id.number);
        imageView = findViewById(R.id.close);
        button = findViewById(R.id.resend_code);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d(TAG, "Retrieving data from Signup_3 class");
            phone = bundle.getString("PHONE");
            full_name = bundle.getString("FULL NAME");
            username = bundle.getString("USERNAME");
            email = bundle.getString("EMAIL");
            password = bundle.getString("PASSWORD");
            gender = bundle.getString("GENDER");
            birth_date = bundle.getString("DATE");

            textView.setText(phone);
            if(phone == null){
                Log.d(TAG, "Data retrieved is null");
            }

            else{
                Log.d(TAG, "Retrieved data is not null");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPhoneNumberVerification(phone);
                        // Restore instance state
                        if (codebySystem == null && savedInstanceState != null) {
                            onRestoreInstanceState(savedInstanceState);
                        }
                    }
                });
            }
        }
        else{
            Log.d(TAG, "Bundle is null");
        }
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
        // [END start_phone_auth]
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_VERIFICATION_ID,codebySystem);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        codebySystem = savedInstanceState.getString(KEY_VERIFICATION_ID);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    Log.d(TAG, "onCodeSent: " + s);
                    super.onCodeSent(s, token);
                    codebySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);

                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinView.setText(code);
                        verifyPhoneNumberWithCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyPhoneNumberWithCode(String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codebySystem, code);
        signInWithPhoneAuthCredential(credential);
        // [END verify_with_code]
    }
    public void callNextScreenFromOTP(View view) {
        String code = pinView.getText().toString();
        if(!code.isEmpty()){
            Log.d(TAG, "Code is not null");
            verifyPhoneNumberWithCode(code);
        }
        else{
            Log.d(TAG, "Code is not null");
            pinView.setError("Verification code is required to progress.");
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");

                            NewUserData();
                            Intent intent = new Intent(VerifyOTP.this, MainActivity.class);
                            startActivity(intent);
                            FirebaseUser user = task.getResult().getUser();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void NewUserData() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(user.getUid());

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("full_name", full_name);
        hashMap.put("username", username);
        hashMap.put("email", email);
        hashMap.put("phone_number", phone);
        hashMap.put("birth_date", birth_date);
        hashMap.put("gender", gender);
        ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(VerifyOTP.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}






