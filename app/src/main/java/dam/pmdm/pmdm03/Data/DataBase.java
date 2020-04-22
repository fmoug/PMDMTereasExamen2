package dam.pmdm.pmdm03.Data;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    public final static String DB_NAME = "UD03Task";
    public final static int DB_VERSION = 9;
    private SQLiteDatabase dbOperations;

    private String CreateTable_User = "CREATE TABLE USER ( " +
            "id  INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR( 50 )  NOT NULL," +
            "surname VARCHAR( 50 ) ," +
            "email VARCHAR( 50 )  NOT NULL," +
            "userName VARCHAR( 50 )  NOT NULL," +
            "password VARCHAR( 50 )  NOT NULL," +
            "isAdmin INTEGER  NOT NULL," +
            "imageURL VARCHAR( 255 ))";

    private String CreateTable_Category = "CREATE TABLE CATEGORY ( " +
            "id  INTEGER PRIMARY KEY," +
            "name VARCHAR( 50 )  NOT NULL)";

    private String CreateTable_Product = "CREATE TABLE PRODUCT ( " +
            "id  INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR( 50 )  NOT NULL," +
            "categoryId INTEGER NOT NULL)";

    private String CreateTable_Order = "CREATE TABLE 'ORDERS' ( " +
            "Id  INTEGER PRIMARY KEY AUTOINCREMENT," +
            "UserId INTEGER NOT NULL," +
            "ProductId INTEGER NOT NULL," +
            "Count INTEGER NOT NULL," +
            "Address VARCHAR( 50 ) NOT NULL," +
            "Locality VARCHAR( 50 ) NOT NULL," +
            "PostalCode VARCHAR( 50 ) NOT NULL," +
            "Status VARCHAR( 50 )  NOT NULL)";

    private String AddUsers = "INSERT INTO USER " +
            "(name, surname, email, userName, password, isAdmin) VALUES " +
            "('Administrador','', 'fmoug@hotmail.com', 'admin', 'abc123.', 1)," +
            "('cliente1','', 'fmoug@hotmail.com', 'cliente1', 'abc123.', 0)";

    private String AddCategories = "INSERT INTO CATEGORY " +
            "(id, name) VALUES " +
            "(1,'Informática')," +
            "(2,'Electrónica')," +
            "(3,'Móbiles')";

    private String AddProducts = "INSERT INTO PRODUCT " +
            "(id, categoryId,name) VALUES " +
            "(1,1,'PC de sobremesa')," +
            "(2,1,'Potátil')," +
            "(3,1,'Monitor')," +
            "(4,2,'Televisión')," +
            "(5,2,'DVD')," +
            "(6,3,'Pixel')," +
            "(7,3,'Galaxy')," +
            "(8,3,'Iphone')," +
            "(9,3,'Xiaomi')";

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // TODO Auto-generated constructor stub
    }

    public void AssignSQLiteDatabase(SQLiteDatabase dbOperations){
        this.dbOperations = dbOperations;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CreateTable_User);
        db.execSQL(AddUsers);

        db.execSQL(CreateTable_Category);
        db.execSQL(AddCategories);

        db.execSQL(CreateTable_Product);
        db.execSQL(AddProducts);

        db.execSQL(CreateTable_Order);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS 'ORDERS'");
        db.execSQL("DROP TABLE IF EXISTS 'ORDER'");

        db.execSQL("DROP TABLE IF EXISTS 'USER'");
        db.execSQL("DROP TABLE IF EXISTS 'CATEGORY'");
        db.execSQL("DROP TABLE IF EXISTS 'PRODUCT'");

        onCreate(db);
    }
}

