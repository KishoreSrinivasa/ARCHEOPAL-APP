package com.example.archeopal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText editTextRegisterName, editTextRegisterEmail, editTextRegisterDob, editTextRegisterMobile, editTextRegisterPassword, editTextRegisterConfirmPassword;
    private ProgressBar progressbar;
    private RadioGroup radioRegisterGender;
    private RadioButton radioGivenGender;
    private static final String TAG= "Register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //getSupportActionBar().setTitle("Register");

        Toast.makeText(Register.this,"You can register now", Toast.LENGTH_LONG).show();

        progressbar = findViewById(R.id.progress_bar);
        editTextRegisterName = findViewById(R.id.editText_register_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterDob = findViewById(R.id.editText_register_dob);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);
        editTextRegisterPassword = findViewById(R.id.editText_register_password);
        editTextRegisterConfirmPassword = findViewById(R.id.editText_register_confirm_password);

        radioRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioRegisterGender.clearCheck();

        Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectGender = radioRegisterGender.getCheckedRadioButtonId();
                radioGivenGender = findViewById(selectGender);

                String textName = editTextRegisterName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textDob = editTextRegisterDob.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textPassword = editTextRegisterPassword.getText().toString();
                String textConfirmPassword = editTextRegisterConfirmPassword.getText().toString();
                String textGender;

                if(TextUtils.isEmpty(textName)) {
                    Toast.makeText(Register.this, "Please enter your name.", Toast.LENGTH_LONG).show();
                    editTextRegisterName.setError("Name is required");
                    editTextRegisterName.requestFocus();
                } else if(TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(Register.this, "Please enter your email.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(Register.this, "Please re-enter your email.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Valid email is required");
                    editTextRegisterEmail.requestFocus();
                } else if(TextUtils.isEmpty(textDob)) {
                    Toast.makeText(Register.this, "Please your date of birth.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("date of birth is required");
                    editTextRegisterEmail.requestFocus();
                } else if(radioRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(Register.this, "Please select your gender.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Gender is required");
                    editTextRegisterEmail.requestFocus();
                } else if(TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(Register.this, "Please enter your mobile number.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Mobile number is required");
                    editTextRegisterEmail.requestFocus();
                } else if(textMobile.length() != 10) {
                    Toast.makeText(Register.this, "Please re-enter your mobile number.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Mobile number should be 10 digits");
                    editTextRegisterEmail.requestFocus();
                } else if(TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(Register.this, "Please enter your password.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Password is required");
                    editTextRegisterEmail.requestFocus();
                } else if(textPassword.length() < 6) {
                    Toast.makeText(Register.this, "Password should be at least 6 digits.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Password is too weak");
                    editTextRegisterEmail.requestFocus();
                } else if(TextUtils.isEmpty(textConfirmPassword)) {
                    Toast.makeText(Register.this, "Please confirm your password.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Password confirmation is required");
                    editTextRegisterEmail.requestFocus();
                } else if(!textPassword.equals(textConfirmPassword)) {
                    Toast.makeText(Register.this, "Passwords are mis-matching.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Password Confirmation is required");
                    editTextRegisterEmail.requestFocus();
                    editTextRegisterPassword.clearComposingText();
                    editTextRegisterConfirmPassword.clearComposingText();
                } else {
                    textGender = radioGivenGender.getText().toString();
                    progressbar.setVisibility(View.VISIBLE);
                    registerUser(textName, textEmail, textDob, textGender, textMobile, textPassword);
                }
            }
        });
    }



    //firebase codes sections
    private void registerUser(String textName, String textEmail, String textDob, String textGender, String textMobile, String textPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //create user profile

        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(Register.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_LONG).show();

                            FirebaseUser fireCurrentUser = auth.getCurrentUser();

                            //update Display Name of User
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textName).build();
                            fireCurrentUser.updateProfile(profileChangeRequest);

                            //Entering the data to the database.
                            ReadWriteUserDetails readWriteUserDetails = new ReadWriteUserDetails(textDob, textGender, textMobile);

                            //Enter real time database
                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered User");
                            referenceProfile.child(fireCurrentUser.getUid()).setValue(readWriteUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        fireCurrentUser.sendEmailVerification();

                                        Toast.makeText(Register.this, "User registered successfully.", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Register.this, loginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Register.this, "User registration is failed", Toast.LENGTH_LONG).show();
                                    }
                                    progressbar.setVisibility(View.GONE);
                                }
                            });

                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                editTextRegisterPassword.setError("Your password is too weak, kindly use mix of alphabets, numbers, and special characters");
                                editTextRegisterPassword.requestFocus();
                                progressbar.setVisibility(View.GONE);
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                editTextRegisterName.setError("your email is invalid or already in use");
                                editTextRegisterName.requestFocus();
                                progressbar.setVisibility(View.GONE);
                            } catch (FirebaseAuthUserCollisionException e) {
                                editTextRegisterName.setError("User is already registered");
                                editTextRegisterName.requestFocus();
                                progressbar.setVisibility(View.GONE);
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(Register.this, e.getMessage(),Toast.LENGTH_LONG).show();
                                progressbar.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }
}