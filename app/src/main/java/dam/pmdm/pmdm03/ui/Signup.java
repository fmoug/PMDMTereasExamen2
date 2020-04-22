package dam.pmdm.pmdm03.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import  android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dam.pmdm.pmdm03.Data.DataBase;
import dam.pmdm.pmdm03.R;

public class Signup extends AppCompatActivity {

    EditText txtUserName;
    EditText txtUserPassword;
    EditText txtUserPassword2;
    EditText txtName;
    EditText txtSurname;
    EditText txtMail;
    private Spinner spinner;
    ImageView imageView;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtUserPassword = (EditText) findViewById(R.id.txtUserPassword);
        txtUserPassword2 = (EditText) findViewById(R.id.txtPassword2);
        txtName = (EditText) findViewById(R.id.txtName);
        txtSurname = (EditText) findViewById(R.id.txtSurname);
        txtMail = (EditText) findViewById(R.id.txtName);
        spinner = (Spinner) findViewById(R.id.spinner);
        imageView = (ImageView) findViewById(R.id.imageView);

        String[] spinnerValues = {"Normal", "Administrador"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValues);
        spinner.setAdapter(arrayAdapter);


        if (ContextCompat.checkSelfPermission(Signup.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Signup.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Signup.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }


        ManageEvents();
    }

    private void ManageEvents() {

        findViewById(R.id.btnTakePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhoto();
            }
        });

        findViewById(R.id.btnAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup(v);
            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void TakePhoto() {
  //      Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
   //     if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
    //        startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
   //     }
      Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                try {
                    //Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                    //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                } catch (Exception ex) {
                    // Error occurred while creating the File
                    throw ex;
                }


            }
        }
    }

    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE)
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }
    }

    public void Signup(View view) {

        Intent intent;
        String name = txtName.getText().toString();
        String surname = txtSurname.getText().toString();
        String userName = txtUserName.getText().toString();
        String mail = txtMail.getText().toString();
        String password = txtUserPassword.getText().toString();
        String password2 = txtUserPassword2.getText().toString();
        String spinnerSelectItem = spinner.getSelectedItem().toString();
String imageURL = String.valueOf(currentPhotoPath);

        if (!name.isEmpty() || !surname.isEmpty() || !userName.isEmpty() || !mail.isEmpty() ||
                !password.isEmpty() || !password2.isEmpty()) {
            if (password.equals(password2)) {

                ContentValues contentValues = new ContentValues();
                contentValues.put("Name", name);
                contentValues.put("Surname", surname);
                contentValues.put("email", mail);
                contentValues.put("userName", userName);
                contentValues.put("password", password);
                contentValues.put("imageURL", imageURL);
                if (spinnerSelectItem.equals("Administrador")) {
                    contentValues.put("isAdmin", 1);
                } else {
                    contentValues.put("isAdmin", 0);
                }

                try {
                    DataBase dataBase = new DataBase(getApplicationContext());
                    SQLiteDatabase writableDatabase = dataBase.getWritableDatabase();
                    writableDatabase.insert("USER", null, contentValues);
                    writableDatabase.close();
                    txtName.setText("");
                    txtSurname.setText("");
                    txtUserName.setText("");
                    txtMail.setText("");
                    txtUserPassword.setText("");
                    txtUserPassword2.setText("");

                    intent = new Intent(this, dam.pmdm.pmdm03.ui.Login.class);

                    startActivity(intent);

                } catch (Exception ex) {
                    Toast.makeText(this, "Error en la inserción de datos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Las contraeñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
