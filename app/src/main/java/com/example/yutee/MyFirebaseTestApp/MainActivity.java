package com.example.yutee.MyFirebaseTestApp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEdit, passwordEdit;
    private FirebaseAuth firebaseAuth;
    private Button registerButton, loginButton;
    String email, password;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        pd = new ProgressDialog(this);
        pd.setCancelable(false);


        /**
         * firebase Auth
         */
        firebaseAuth = FirebaseAuth.getInstance();

        emailEdit = findViewById(R.id.editTextEmail);
        passwordEdit = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }
    /**
     * register user with email and password
     */

    private void signUpUser(){
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();

        if (!dataValidate(email, password)){
            return;
        }

        pd.setMessage("Registering user...");
        pd.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Sign Up Succesful", Toast.LENGTH_SHORT).show();
                    pd.hide();
                }else{
                    Toast.makeText(MainActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                    pd.hide();
                }
            }
        });

    }
    /**
     * login user with email and password
     */
        private void loginUser(){
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();

               if (!dataValidate(email,password)){
                   return;
               }
                pd.setMessage("Sign in user..");
        pd.show();

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Sign Up Succesful", Toast.LENGTH_SHORT).show();
                        pd.hide();
                    }else{
                        Toast.makeText(MainActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        pd.hide();
                    }
                }
            });


    }



    private boolean dataValidate(String email, String password){

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailEdit.setError("Enter a valid email address");
                return false;
            }

            if (password.isEmpty() || password.length()<2){
                passwordEdit.setError("Password is invalid");
                return false;
            }
            return true;
    }


    @Override
    public void onClick(View view) {

            switch (view.getId()){
                case R.id.registerButton:
                    signUpUser();
                    break;
                case R.id.loginButton:
                    loginUser();
                    break;
            }
    }
}
