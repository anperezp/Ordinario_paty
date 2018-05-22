package com.example.alanbarrera.ordinario_paty;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alanbarrera.ordinario_paty.logic.Constants;
import com.example.alanbarrera.ordinario_paty.logic.DATA;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private SignInButton signInButton;
    private Button logOutButton;
    private Button nextScreenButton;
    private GoogleSignInClient mGSignInClient;
    public GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);

        //Prepar datos
        DATA.Prepare();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGSignInClient = GoogleSignIn.getClient(this, gSignInOptions);

        logOutButton = findViewById(R.id.logout);
        nextScreenButton = findViewById(R.id.nextScreen);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null)
        {
            signInButton.setVisibility(View.GONE);
            logOutButton.setVisibility(View.VISIBLE);
            nextScreenButton.setVisibility(View.VISIBLE);
        }
        //updateUI(currentUser);
    }
    private void signIn() {
        Intent signInIntent = mGSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    public void nextScreen(View v)
    {
        startActivity(new Intent(this, ActivityMyOrders.class));
    }

    public void logOut(View v)
    {
        mGSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        signInButton.setVisibility(View.VISIBLE);
                        logOutButton.setVisibility(View.GONE);
                        nextScreenButton.setVisibility(View.GONE);
                        Toast.makeText(getBaseContext(), "Logout succesful", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == Constants.RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            Constants.DELIVERYMAN.FirstName = account.getDisplayName();
            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(this, ActivityMyOrders.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SignInFail", "signInResult:failed code=" + e.getStatusCode());
           // updateUI(null);
        }
    }

    @Override
    public void onClick(View v) {
        signIn();
    }
}
