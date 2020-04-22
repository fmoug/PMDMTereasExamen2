package dam.pmdm.pmdm03.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dam.pmdm.pmdm03.Model.Category;
import dam.pmdm.pmdm03.Model.Order;
import dam.pmdm.pmdm03.Model.OrderStatus;
import dam.pmdm.pmdm03.Model.Product;

public class OrderRepository {

    private DataBase dataBase;

    public OrderRepository(Context context) {
        this.dataBase = new DataBase(context);
    }

    public ArrayList<Order> GetList(OrderStatus orderStatus, int userId) {

        SQLiteDatabase writableDatabase = dataBase.getWritableDatabase();

        Cursor cursor = writableDatabase.rawQuery("SELECT " +
                        "'ORDERS'.id, userId, productId, count, status, Address," +
                        "Locality, PostalCode, PRODUCT.Name as ProductName, CATEGORY.Name as CategoryName, CATEGORY.Id as CategoryId " +
                        "FROM 'ORDERS' " +
                        "INNER JOIN PRODUCT on 'ORDERS'.ProductId = PRODUCT.Id " +
                        "INNER JOIN CATEGORY on PRODUCT.CategoryId = CATEGORY.Id " +
                        " WHERE " +
                        " status = '" + orderStatus + "' AND " +
                        " userId = " + userId
                , null);

        return Adapt(cursor);
    }

    public ArrayList<Order> GetList(OrderStatus orderStatus) {

        SQLiteDatabase writableDatabase = dataBase.getWritableDatabase();

        Cursor cursor = writableDatabase.rawQuery("SELECT " +
                        "'ORDERS'.id, userId, productId, count, status, Address," +
                        "Locality, PostalCode, PRODUCT.Name as ProductName, CATEGORY.Name as CategoryName, CATEGORY.Id as CategoryId " +
                        "FROM 'ORDERS' " +
                        "INNER JOIN PRODUCT on 'ORDERS'.ProductId = PRODUCT.Id " +
                        "INNER JOIN CATEGORY on PRODUCT.CategoryId = CATEGORY.Id " +
                        " WHERE " +
                        " status = '" + orderStatus + "'"
                , null);

        return Adapt(cursor);
    }

    public void Insert(Order order) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("Count", order.getCount());
        contentValues.put("ProductId", order.getProductId());
        contentValues.put("UserId", order.getUserId());
        contentValues.put("Status", order.getOrderStatus().toString());
        contentValues.put("Address", order.getAddress().toString());
        contentValues.put("Locality", order.getLocality().toString());
        contentValues.put("PostalCode", order.getPostalCode().toString());

        SQLiteDatabase writableDatabase = dataBase.getWritableDatabase();

        writableDatabase.insert("ORDERS", null, contentValues);
        writableDatabase.close();

    }

    public  void ChangeOrderStatus(int orderId, OrderStatus orderStatus)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Status", String.valueOf(orderStatus).toString());

        SQLiteDatabase writableDatabase = dataBase.getWritableDatabase();

        writableDatabase.update("ORDERS",contentValues,"Id = " + orderId, null);
        writableDatabase.close();
    }

    private ArrayList<Order> Adapt(Cursor cursor) {
        ArrayList<Order> result = new ArrayList<Order>();
        Order order;
        Product product;
        Category category;

        try {
            while (cursor.moveToNext()) {

                order = new Order();
                order.setId(cursor.getInt(0));
                order.setUserId(cursor.getInt(1));
                order.setProductId(cursor.getInt(2));
                order.setCount(cursor.getInt(3));
                order.setOrderStatus(OrderStatus.valueOf(cursor.getString(4)));
                order.setAddress(cursor.getString(5).toString());
                order.setLocality(cursor.getString(6).toString());
                order.setPostalCode(cursor.getString(7).toString());

                product = new Product();
                product.setId(order.getProductId());
                product.setName(cursor.getString(6).toString());
                order.setProduct(product);

                category = new Category();
                category.setId(cursor.getInt(8));
                category.setName(cursor.getString(7).toString());
                order.setCategory(category);

                result.add(order);
            }

            return result;

        } finally {
            cursor.close();
        }
    }
}
