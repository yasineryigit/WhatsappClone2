package com.ossovita.wpclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    Boolean loginModeActive=false;
    EditText usernameText,passwordText;
    TextView loginTextView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameText = findViewById(R.id.usernameEditText);
        passwordText = findViewById(R.id.passwordEditText);
        loginTextView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        setTitle("Whatsapp Login");

    }

    public void signupLogin(View v){
        ParseUser user = new ParseUser();

        if(usernameText.getText().toString().equals("")||passwordText.getText().toString().matches("")){
            Toast.makeText(this, "Please enter valid username and password", Toast.LENGTH_SHORT).show();
        }else{
            user.setUsername(usernameText.getText().toString());
            user.setPassword(passwordText.getText().toString());

            if(loginModeActive){
                ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e==null){//hata yoksa
                            Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                            goNext();
                        }else{//hata varsa
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){//hata yoksa
                            goNext();
                            Toast.makeText(MainActivity.this, "Signed Up!", Toast.LENGTH_SHORT).show();
                        }else{//hata varsa
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }



    }

    public void toggleLoginMode(View v){
        if (loginModeActive) {
            loginTextView.setText("or, Sign up");
            button.setText("Login");
            loginModeActive=false;
        }else{
            loginTextView.setText("or, Login");
            button.setText("Sign up");
            loginModeActive=true;

        }
    }

    private void goNext() {

    }
}