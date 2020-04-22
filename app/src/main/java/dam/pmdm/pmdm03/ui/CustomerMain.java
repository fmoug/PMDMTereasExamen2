package dam.pmdm.pmdm03.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import dam.pmdm.pmdm03.Model.CurrentSession;
import dam.pmdm.pmdm03.Model.OrderStatus;
import  dam.pmdm.pmdm03.R;

public class CustomerMain extends AppCompatActivity {

    TextView txtUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

       txtUserInfo = (TextView) findViewById(R.id.txtUserInfo);

        String userInfo = "Usuario: " + CurrentSession.getUser().getName() + " " + CurrentSession.getUser().getSurname();

        txtUserInfo.setText(userInfo);

        ManageEvents();
    }

    private void ManageEvents() {

        findViewById(R.id.btnMakeOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MakeOrder(v);
            }
        });

        findViewById(R.id.btnViewPendingOrders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewOrders(v,OrderStatus.Pending);
            }
        });

        findViewById(R.id.btnViewAceptedOrders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewOrders(v,OrderStatus.Accepted);
            }
        });

        findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUser(v);
            }
        });

        findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    public void MakeOrder(View view) {
        Intent intent;
        intent = new Intent(this, dam.pmdm.pmdm03.ui.MakeOrder.class);
        startActivity(intent);
    }

    public void ViewOrders(View view, OrderStatus orderStatus) {
        Intent intent;
        intent = new Intent(this, dam.pmdm.pmdm03.ui.OrdersAdmin.class);
        intent.putExtra(OrdersAdmin.ORDERSTATUS, orderStatus.ordinal());
        startActivity(intent);
    }

    public void EditUser(View view) {
        Intent intent;
        intent = new Intent(this, dam.pmdm.pmdm03.ui.EditUser.class);

        startActivity(intent);
    }
}
