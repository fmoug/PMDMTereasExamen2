package dam.pmdm.pmdm03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import dam.pmdm.pmdm03.*;
import dam.pmdm.pmdm03.Data.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBase dataBase;
        dataBase = new DataBase(getApplicationContext());
        Toast.makeText(this, "Base de datos iniciada", Toast.LENGTH_SHORT).show();
    }

    public void Login(View view)
    {
        Intent intent;
        intent = new Intent(this, dam.pmdm.pmdm03.ui.Login.class);
        startActivity(intent);
    }
}

