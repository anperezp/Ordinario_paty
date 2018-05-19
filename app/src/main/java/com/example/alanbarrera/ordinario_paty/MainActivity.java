package com.example.alanbarrera.ordinario_paty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alanbarrera.ordinario_paty.logic.DATA;

public class MainActivity extends AppCompatActivity
{
    Button buttonLogin;
    Button buttonForgotPassword;
    EditText txtUsername;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);

        //Prepar datos
        DATA.Prepare();

        // Get our button from the layout resource,
        // and attach an event to it
        buttonLogin = findViewById(R.id.button_login);
        buttonForgotPassword = findViewById(R.id.button_forgot_password);
        txtUsername = findViewById(R.id.editText_login_user);
        txtPassword = findViewById(R.id.editText_login_password);
    }

    public void login(View v) {
        Intent intent = new Intent(this, ActivityMyOrders.class);
        startActivity(intent);
    }
}
