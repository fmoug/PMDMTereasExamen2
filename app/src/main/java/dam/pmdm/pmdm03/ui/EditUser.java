package dam.pmdm.pmdm03.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import dam.pmdm.pmdm03.Data.DataBase;
import dam.pmdm.pmdm03.Model.CurrentSession;
import dam.pmdm.pmdm03.Model.OrderStatus;
import dam.pmdm.pmdm03.Model.User;
import  dam.pmdm.pmdm03.R;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditUser extends AppCompatActivity {

    EditText txtUserPassword;
    EditText txtUserPassword2;
    EditText txtName;
    EditText txtSurname;
    EditText txtMail;
    Spinner spinner;
    ImageView imageView;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        txtUserPassword = (EditText) findViewById(R.id.txtUserPassword);
        txtUserPassword2 = (EditText) findViewById(R.id.txtPassword2);
        txtName = (EditText) findViewById(R.id.txtName);
        txtSurname = (EditText) findViewById(R.id.txtSurname);
        txtMail = (EditText) findViewById(R.id.txtMail);
        imageView = (ImageView) findViewById(R.id.imageView);

        User user = CurrentSession.getUser();
        String name = user.getName();
        String surname =user.getSurname();
        String email = user.getEmail().toString();

        txtName.setText(name);
        txtSurname.setText(surname);
        txtMail.setText(email);

        if (ContextCompat.checkSelfPermission(EditUser.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(EditUser.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditUser.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        File imgFile = new File(String.valueOf(CurrentSession.getUser().getImageURL()));

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(myBitmap);
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
                Edit(v);
            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
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

    private void Edit(View view) {

        Intent intent;
        String name = txtName.getText().toString();
        String surname = txtSurname.getText().toString();
        String mail = txtMail.getText().toString();
        String password = txtUserPassword.getText().toString();
        String password2 = txtUserPassword2.getText().toString();
        String imageURL = String.valueOf(currentPhotoPath);

        if (!name.isEmpty() || !surname.isEmpty() || !mail.isEmpty() ||
                !password.isEmpty() || !password2.isEmpty()) {
            if (password.equals(password2)) {

                try {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("Name", name);
                    contentValues.put("Surname", surname);
                    contentValues.put("email", mail);
                    contentValues.put("password", password);
                    contentValues.put("imageURL", imageURL);


                    DataBase dataBase = new DataBase(getApplicationContext());
                    SQLiteDatabase writableDatabase = dataBase.getWritableDatabase();
                    writableDatabase.update("USER", contentValues, "Id = " + CurrentSession.getUser().getId(), null);
                    writableDatabase.close();

                    User user = CurrentSession.getUser();

                    user.setName(name);
                    user.setSurname(surname);
                    user.setEmail(mail);
                    user.setImageURL(imageURL);


                    if (CurrentSession.getUser().isAdmin()) {
                        intent = new Intent(this, dam.pmdm.pmdm03.ui.AdminMain.class);
                    } else {
                        intent = new Intent(this, dam.pmdm.pmdm03.ui.CustomerMain.class);
                    }

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
