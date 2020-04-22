package dam.pmdm.pmdm03.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import dam.pmdm.pmdm03.Model.User;

public class UserRepository {

   private DataBase dataBase;

    public UserRepository(Context context) {
        this.dataBase = new DataBase(context);
    }

    public User Get(String userName, String password) {
        User user;
        SQLiteDatabase writableDatabase = dataBase.getWritableDatabase();

        Cursor row = writableDatabase.rawQuery("SELECT id, name, surname, isAdmin, imageURL,email FROM user WHERE " +
                        " userName = '" + userName + "' AND " +
                        " password = '" + password + "'"
                , null);

        if (row.moveToFirst()) {

            user = new User();

            user.setId(row.getInt(0));
            user.setName(row.getString(1));
            user.setSurname(row.getString(2));
            user.setAdmin(row.getInt(3) == 1);
            user.setImageURL(row.getString(4));
            user.setEmail(row.getString(5));

            return user;

        } else {
            return null;
        }

    }

}
