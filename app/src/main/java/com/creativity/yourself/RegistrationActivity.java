package com.creativity.yourself;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppBaseCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegistrationActivity";

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText registrationEmail;
    private EditText registrationPassword;

    private Button registrationButton;

    private TextView registrationGotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        this.registrationEmail = findViewById(R.id.registrationEmail);
        this.registrationPassword = findViewById(R.id.registrationPassword);

        this.registrationButton = findViewById(R.id.registrationButton);
        this.registrationButton.setOnClickListener(this);

        this.registrationGotoLogin = findViewById(R.id.registrationGotoLogin);
        this.registrationGotoLogin.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "Utente: " + user.getUid() + " (signed_in)");
                } else {
                    // User is signed out
                    Log.d(TAG, "Utente (signed_out)");
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount -> Email: " + email + " Password: " + password);
        if (!validateForm(email, password)) {
            return;
        }

        //showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "Registrazione Completata! -> " + task.isSuccessful());
                        Intent intro = new Intent(RegistrationActivity.this, IntroActivity.class);
                        startActivity(intro);
                        finish();

                        if (!task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //hideProgressDialog();
                    }
                });
    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            this.registrationEmail.setError("Required.");
            valid = false;
        } else {
            this.registrationEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            this.registrationPassword.setError("Required.");
            valid = false;
        } else {
            this.registrationPassword.setError(null);
        }

        if (password.length() <6 ) {
            this.registrationPassword.setError("Password troppo corta!");
            valid = false;
        }else {
                this.registrationPassword.setError(null);
            }

        return valid;
    }


    @Override
    public void onNetworkStatusChange(boolean isOnline) {
        //Ricezione connessione assente
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registrationButton:
                createAccount(this.registrationEmail.getText().toString(), this.registrationPassword.getText().toString());
                break;

            case R.id.registrationGotoLogin:
                Intent login = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
                break;
        }
    }
}
