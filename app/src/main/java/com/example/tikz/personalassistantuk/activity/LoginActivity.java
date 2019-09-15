package com.example.tikz.personalassistantuk.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tikz.personalassistantuk.Constant;
import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.data.MahasiswaDBAccess;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etNIM, etPass;
    private TextView btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final MahasiswaDBAccess databaseAccess = MahasiswaDBAccess.getInstance(getApplicationContext());
        final Constant constant = new Constant(null,null,null,null);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (TextView) findViewById(R.id.btnRegister);
        etNIM = (EditText) findViewById(R.id.etNIM);
        etPass = (EditText) findViewById(R.id.etPassword);

        /**>>>>>>>initial view start**/

        //Check if user already register
        int count = databaseAccess.checkMahasiswa();
        if (count==0){ //If no registered user then redirect to Registration
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(getApplicationContext(), "Welcome to Main Menu", Toast.LENGTH_LONG).show();
        }

        //get data mahasiswa to save on Constant class
        databaseAccess.getDataMahasiswa();
        //databaseAccess.closeDB2();

        //Get student name
        String name = constant.getFirstName();
        String lname = constant.getLastName();

        /**>>>>>>initial view end **/

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get String from EditText
                String nim = etNIM.getText().toString();
                String pass = etPass.getText().toString();

                //Check if etNIM and etPass null
                if (nim.equals("")){
                    Toast.makeText(getApplicationContext(), "NIM cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (pass.equals("")){
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (!nim.equals("") && !pass.equals("")){
                    if (nim.equals(constant.getNIM()) && pass.equals(constant.getPASSWORD())){
                        home();
                    }else {
                        Toast.makeText(getApplicationContext(), "NIM or Password not match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectRegister();
            }
        });
    }

    private void home(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectRegister(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
        finish();
    }
}
