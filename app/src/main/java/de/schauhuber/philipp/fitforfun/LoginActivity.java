package de.schauhuber.philipp.fitforfun;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private String MessageOnLogin = "Erfolgreich Angemeldet";
    private String MessageOnLoginFail = "Email und/oder Passwort falsch";
    private String MessageOnRegistrierenFail = "Email wird bereits verwendet oder Email/Passwort erfüllt nicht den Ansprüchen. Bitte gültige Email Addresse und Passwort von mindestens 6 Zeichen verwenden";
    private String MessageOnNoEmail = "Bitte email hier eingeben";
    private String MessageOnNoPasswort = "Bitte Passwort hier eingeben";

    private FirebaseAuth firebaseAuth;

    private EditText editEmail , editPasswort;
    private Button buttonLogin, buttonRegistrieren;

    private DatabaseAdapter databaseAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Ui();
        buttons();
    }




    private void Ui() {
        editEmail = (EditText) findViewById(R.id.eingabe_email);
        editPasswort = (EditText) findViewById(R.id.eingabe_passwort);
        buttonLogin= (Button) findViewById(R.id.button_login);
        buttonRegistrieren = (Button) findViewById(R.id.button_registrieren);
        databaseAdapter = new DatabaseAdapter();
        firebaseAuth = FirebaseAuth.getInstance();


    }

    private void buttons() {

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            DoLogIn();
            }
        });

        buttonRegistrieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoRegistration();
            }});

    }



    private boolean LoginNotNull() {
        String Login = editEmail.getText().toString();
        if(TextUtils.isEmpty(Login)) {
            editEmail.setError(MessageOnNoEmail);
            return false;
        } else {return true;}
    }

    private boolean PasswortNotNull(){
        String Passwort = editPasswort.getText().toString();
        if(TextUtils.isEmpty(Passwort)) {
            editPasswort.setError(MessageOnNoPasswort);
            return false;
        } else {return true;}
    }

    private void DoLogIn(){
        if (LoginNotNull() && PasswortNotNull()) {
           String login = editEmail.getText().toString();
            String passwort = editPasswort.getText().toString();
            firebaseAuth.signInWithEmailAndPassword(login, passwort).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(LoginActivity.this,MessageOnLogin, Toast.LENGTH_SHORT).show();
                        changeClass();


                    } else {
                        Toast.makeText(LoginActivity.this,MessageOnLoginFail, Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

    }
    private void DoRegistration(){
        if (LoginNotNull() && PasswortNotNull()) {
            String login = editEmail.getText().toString();
            String passwort = editPasswort.getText().toString();
            firebaseAuth.createUserWithEmailAndPassword(login, passwort).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        databaseAdapter.addNewChild();
                        DoLogIn();


                    } else {
                        Toast.makeText(LoginActivity.this, MessageOnRegistrierenFail, Toast.LENGTH_LONG).show();
                    }
                }

            });
        }

    }

    private void changeClass(){
        Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
        startActivity(intent);

    }


}
