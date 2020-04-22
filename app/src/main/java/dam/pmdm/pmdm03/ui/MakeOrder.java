package dam.pmdm.pmdm03.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import dam.pmdm.pmdm03.Data.CategoryRepository;
import dam.pmdm.pmdm03.Data.ProductRespository;
import dam.pmdm.pmdm03.Data.UserRepository;
import dam.pmdm.pmdm03.Model.CurrentSession;
import dam.pmdm.pmdm03.Model.Order;
import dam.pmdm.pmdm03.Model.OrderStatus;
import dam.pmdm.pmdm03.Model.Product;
import dam.pmdm.pmdm03.Model.SpinAdapter;
import dam.pmdm.pmdm03.Model.User;
import dam.pmdm.pmdm03.Model.Category;
import dam.pmdm.pmdm03.R;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MakeOrder extends AppCompatActivity {
    private Spinner spCount;
    private Spinner spCategory;
    private Spinner spProduct;
    private SpinAdapter productAdapter;
    private SpinAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        spCount = (Spinner) findViewById(R.id.spCount);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spProduct = (Spinner) findViewById(R.id.spProduct);

        //Rellenamos spCount
        String[] spinnerValues = {"1", "2", "3", "4", "5"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValues);
        spCount.setAdapter(arrayAdapter);

        ShowCategories();

        ManageEvents();
    }

    private void ManageEvents() {

        findViewById(R.id.btnMakeOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeOrder(v);
            }
        });

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Category category = (Category) categoryAdapter.getItem(position);

                ShowProducts(category.getId());
                // Here you can do the action you want to...
                //Toast.makeText(getApplicationContext(), "ID: " + category.getId() + "\nName: " + category.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
    }

    private void ShowCategories() {
        Category[] categories;
        CategoryRepository categoryRepository = new CategoryRepository(getApplicationContext());
        categories = categoryRepository.GetList().toArray(new Category[0]);
        categoryAdapter = new SpinAdapter(this,
                android.R.layout.simple_spinner_item,
                categories);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spCategory.setAdapter(categoryAdapter);

        Category category = (Category) categoryAdapter.getItem(0);

        ShowProducts(category.getId());
    }

    private void ShowProducts(int categoryId) {
        Product[] products;

        ProductRespository productRespository = new ProductRespository(getApplicationContext());

        products = productRespository.GetList(categoryId).toArray(new Product[0]);

        productAdapter = new SpinAdapter(this,
                android.R.layout.simple_spinner_item,
                products);
        spProduct = (Spinner) findViewById(R.id.spProduct);
        spProduct.setAdapter(productAdapter);
    }

    public void MakeOrder(View view) {

        Order order = new Order();

        Product product = (Product)spProduct.getSelectedItem();
        Category category = (Category)spCategory.getSelectedItem();

        order.setUserId(CurrentSession.getUser().getId());
        order.setCount(Integer.parseInt(spCount.getSelectedItem().toString()));
        order.setOrderStatus(OrderStatus.Pending);
        order.setProductId(product.getId());
        order.setProduct(product);
        order.setCategory(category);

        CurrentSession.setOrder(order);

        Intent intent;
        intent = new Intent(this, dam.pmdm.pmdm03.ui.FinishOrder.class);
        startActivity(intent);

    }
}
