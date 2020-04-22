package dam.pmdm.pmdm03.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dam.pmdm.pmdm03.Model.Category;
import dam.pmdm.pmdm03.Model.Product;

public class ProductRespository {
    private DataBase dataBase;

    public ProductRespository(Context context) {
        this.dataBase = new DataBase(context);
    }

    public ArrayList<Product> GetList(int categoryId) {

        SQLiteDatabase writableDatabase = dataBase.getWritableDatabase();

        Cursor cursor = writableDatabase.rawQuery("SELECT id, name FROM PRODUCT WHERE " +
                        "categoryId = " + categoryId
                , null);

        return Adapt(cursor);
    }

    private ArrayList<Product> Adapt(Cursor cursor)
    {
        ArrayList<Product> result = new ArrayList<Product>();
        Product product;

        try {
            while (cursor.moveToNext()) {

                product = new Product();
                product.setId(cursor.getInt(0));
                product.setName(cursor.getString(1));

                result.add(product);
            }

            return result;

        } finally {
            cursor.close();
        }
    }
}
