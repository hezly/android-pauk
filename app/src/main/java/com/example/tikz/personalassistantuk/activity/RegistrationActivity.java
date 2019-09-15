package com.example.tikz.personalassistantuk.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import com.example.tikz.personalassistantuk.data.MahasiswaDBOpenHelper;

public class RegistrationActivity extends AppCompatActivity {

    private Button create;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    private EditText txtFName, txtLName, txtNIM, txtPass;
    private String fname, lname, nim, pass;
    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        //create the instance of database access class and open database connection
        final MahasiswaDBAccess databaseAccess = MahasiswaDBAccess.getInstance(getApplicationContext());

        openHelper = new MahasiswaDBOpenHelper(this);
        db = openHelper.getReadableDatabase();
        txtFName = (EditText)findViewById(R.id.etFirstName);
        txtLName = (EditText)findViewById(R.id.etLastName);
        txtNIM = (EditText)findViewById(R.id.etNIM);
        txtPass = (EditText) findViewById(R.id.etPassword);
        txtLogin = (TextView) findViewById(R.id.btnLoginReg);
        create = (Button) findViewById(R.id.btnCreate);

        //Initial view
        initView();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get string value from EditText interface
                String fname=txtFName.getText().toString();
                String lname=txtLName.getText().toString();
                String nim=txtNIM.getText().toString();
                String pass=txtPass.getText().toString();

                //Check string fname, lname, nim != null
                if (fname.equals("")){
                    Toast.makeText(getApplicationContext(), "First name cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (lname.equals("")){
                    Toast.makeText(getApplicationContext(), "Last name cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (nim.equals("")){
                    Toast.makeText(getApplicationContext(), "NIM cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (pass.equals("")){
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (fname!=null || !lname.equals("") || !nim.equals("") || !pass.equals("")) {
                    databaseAccess.openDB2();
                    //Delete data mahasiswa sebelumnya
                    databaseAccess.deleteMahasiswa();
                    //Insert data ke dalam database registrasi, tabel mahasiswa
                    databaseAccess.insertData(fname,lname,nim,pass);

                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                    redirectLogin();
                    databaseAccess.closeDB2();
                    finish();
                }
                //Check string end
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectLogin();
            }
        });

    }

    private void initView() {
        MahasiswaDBAccess databaseAccess = MahasiswaDBAccess.getInstance(getApplicationContext());
        databaseAccess.getDataMahasiswa();
        fname = Constant.getFirstName();
        lname = Constant.getLastName();
        nim = Constant.getNIM();
        if (fname != null && lname != null && nim != null){
            txtFName.setText(fname);
            txtLName.setText(lname);
            txtNIM.setText(nim);
        }
    }

    public void home(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void redirectLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
