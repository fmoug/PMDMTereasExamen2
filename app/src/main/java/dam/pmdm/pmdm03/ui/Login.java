package dam.pmdm.pmdm03.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import dam.pmdm.pmdm03.Data.DataBase;
import dam.pmdm.pmdm03.Data.UserRepository;
import dam.pmdm.pmdm03.Model.CurrentSession;
import dam.pmdm.pmdm03.Model.User;
import dam.pmdm.pmdm03.R;

public class Login extends AppCompatActivity {

    EditText txtUserName;
    EditText txtUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtUserPassword = (EditText) findViewById(R.id.txtUserPassword);

        ManageEvents();
    }

    private void ManageEvents() {

        findViewById(R.id.btnAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(v);
            }
        });

        findViewById(R.id.btnSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup(v);
            }
        });
    }

    public void Login(View view) {

        Intent intent;
        boolean loginOk;
        String userNameString = txtUserName.getText().toString();
        String userPasswordString = txtUserPassword.getText().toString();
        boolean isAdmin;

        try {

            UserRepository UserRepository = new UserRepository(getApplicationContext());

            User user = UserRepository.Get(userNameString, userPasswordString);

            loginOk = user != null;

            if (loginOk) {

                CurrentSession.RegisterUser(user);

                isAdmin = user.isAdmin();

                if (isAdmin) {
                    intent = new Intent(this, dam.pmdm.pmdm03.ui.AdminMain.class);
                } else {
                    intent = new Intent(this, dam.pmdm.pmdm03.ui.CustomerMain.class);
                }
                startActivity(intent);
            } else {
                Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error en la consutla", Toast.LENGTH_SHORT).show();
        }
    }

    public void Signup(View view) {
        Intent intent;
        intent = new Intent(this, dam.pmdm.pmdm03.ui.Signup.class);
        startActivity(intent);
    }
}
