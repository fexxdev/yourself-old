package com.creativity.yourself;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;




public class LoginActivity extends AppBaseCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private ConstraintLayout loginLayout;

    private Button signInButton;

    private TextView registerLink;

    private EditText loginEmail;
    private EditText loginPassword;

    private ImageView imageLogo;

    private AnimationDrawable backgroundGradientAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final TypeWriter tw = (TypeWriter) findViewById(R.id.tv);
        tw.setText("");
        tw.setCharacterDelay(250);
        tw.animateText("YourSelf");
        final MediaPlayer sound;
        sound = (MediaPlayer) MediaPlayer.create(this, R.raw.typewriter);
        sound.start();


        this.loginLayout = findViewById(R.id.loginLayout);

        this.backgroundGradientAnim = (AnimationDrawable) this.loginLayout.getBackground();
        this.backgroundGradientAnim.setEnterFadeDuration(3000);
        this.backgroundGradientAnim.setExitFadeDuration(3000);

        //this.imageLogo = findViewById(R.id.imageLogo);
        this.signInButton = findViewById(R.id.signInButton);
        this.registerLink = findViewById(R.id.registerLink);
        this.loginEmail = findViewById(R.id.loginEmail);
        this.loginPassword = findViewById(R.id.loginPassword);

        this.signInButton.setOnClickListener(this);
        this.registerLink.setOnClickListener(this);

        this.signInButton.setAlpha(0);
        this.registerLink.setAlpha(0);
        this.loginEmail.setAlpha(0);
        this.loginPassword.setAlpha(0);



        new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            tw.animate().setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    ConstraintSet set = new ConstraintSet();
                    //set.clone(loginLayout);
                    set.connect(R.id.tv, ConstraintSet.BOTTOM, R.id.loginLinearLayout, ConstraintSet.TOP);
                    //set.applyTo(loginLayout);

                    Transition transition = new ChangeBounds();
                    transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
                    transition.setDuration(2000);

                    TransitionManager.beginDelayedTransition(loginLayout, transition);

                    signInButton.animate().alpha(1.0f).setDuration(800).start();
                    registerLink.animate().alpha(1.0f).setDuration(800).start();
                    loginEmail.animate().alpha(1.0f).setDuration(800).start();
                    loginPassword.animate().alpha(1.0f).setDuration(800).start();
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    },500);
}


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInButton:
                this.signIn(this.loginEmail.getText().toString(), this.loginPassword.getText().toString());

                break;


            case R.id.registerLink:
                Intent registration = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registration);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.backgroundGradientAnim != null && !this.backgroundGradientAnim.isRunning())
            this.backgroundGradientAnim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.backgroundGradientAnim != null && this.backgroundGradientAnim.isRunning())
            this.backgroundGradientAnim.stop();
    }

    @Override
    public void onNetworkStatusChange(boolean isOnline) {
        //Ricezione connessione assente
    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            this.loginEmail.setError("Required.");
            valid = false;
        } else {
            this.loginEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            this.loginPassword.setError("Required.");
            valid = false;
        } else {
            this.loginPassword.setError(null);
        }
        if (password.length() <6 ) {
            this.loginPassword.setError("Password troppo corta!");
            valid = false;
        }else {
            this.loginPassword.setError(null);
        }

        return valid;
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email + " password:" + password);
        if (!validateForm(email, password)) {
            return;
        }

        // showProgressDialog();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail: onComplete:" + task.isSuccessful());
                        Intent main = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(main);
                        finish();

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // hideProgressDialog();
                    }
                });
    }
}
