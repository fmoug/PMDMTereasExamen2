package dam.pmdm.pmdm03.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dam.pmdm.pmdm03.Data.OrderRepository;
import dam.pmdm.pmdm03.Model.CurrentSession;
import dam.pmdm.pmdm03.Model.Order;
import dam.pmdm.pmdm03.Model.OrderListAdminAdapter;
import dam.pmdm.pmdm03.Model.OrderListCustomerAdapter;
import dam.pmdm.pmdm03.Model.OrderStatus;
import dam.pmdm.pmdm03.Model.User;
import dam.pmdm.pmdm03.R;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class OrdersAdmin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static final String ORDERSTATUS = "ORDERSTATUS";
    public static final String ADMIN = "ADMIN";
    OrderStatus orderStatus;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_admin);

        Intent intent = getIntent();

        int spm= intent.getIntExtra(ORDERSTATUS, 0);

        orderStatus = OrderStatus.values()[intent.getIntExtra(ORDERSTATUS, 0)];
        user = CurrentSession.getUser();

        ArrayList<Order> dataset;


        recyclerView = (RecyclerView) findViewById(R.id.rvOrders);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        OrderRepository orderRepository = new OrderRepository(getApplicationContext());
        if (user.isAdmin()) {
            dataset = orderRepository.GetList(orderStatus);
            mAdapter = new OrderListAdminAdapter(dataset);
        } else {
            dataset = orderRepository.GetList(orderStatus, user.getId());
            mAdapter = new OrderListCustomerAdapter(dataset);
        }

        recyclerView.setAdapter(mAdapter);
    }
}
