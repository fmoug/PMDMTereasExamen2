package dam.pmdm.pmdm03.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dam.pmdm.pmdm03.Model.Category;

public class CategoryRepository {

   private DataBase dataBase;

    public CategoryRepository(Context context) {
        this.dataBase = new DataBase(context);
    }

    public ArrayList<Category> GetList() {

        SQLiteDatabase writableDatabase = dataBase.getWritableDatabase();

        Cursor cursor = writableDatabase.rawQuery("SELECT id, name FROM CATEGORY"
                , null);

        return Adapt(cursor);
    }

    private ArrayList<Category> Adapt(Cursor cursor)
    {
        ArrayList<Category> result = new ArrayList<Category>();
        Category category;

        try {
            while (cursor.moveToNext()) {

                category = new Category();
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));

                result.add(category);
            }

            return result;

        } finally {
            cursor.close();
        }
    }
}
