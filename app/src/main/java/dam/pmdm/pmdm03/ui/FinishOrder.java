package dam.pmdm.pmdm03.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import dam.pmdm.pmdm03.Data.OrderRepository;
import dam.pmdm.pmdm03.Model.CurrentSession;
import dam.pmdm.pmdm03.Model.Order;
import dam.pmdm.pmdm03.Model.User;
import dam.pmdm.pmdm03.R;

public class FinishOrder extends AppCompatActivity {
TextView txtOrderSummary;
    TextView txtAddress;
    TextView txtLocality;
    TextView txtPostalCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_order);

        txtOrderSummary = (TextView) findViewById(R.id.txtOrderSummary);
        txtOrderSummary.setText(GetSummary());

        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtLocality = (TextView) findViewById(R.id.txtLocality);
        txtPostalCode = (TextView) findViewById(R.id.txtPostalCode);


        ManageEvents();
    }

    private String GetSummary()
    {
        Order order = CurrentSession.getOrder();

        String summary = "Pedido: \n" +
                "categoría: " + order.getCategory().getName() + "\n" +
                "producto: " + order.getProduct().getName() + "\n" +
                "cantidad: " + order.getCount() ;

        return  summary;
    }

    private void ManageEvents() {

        findViewById(R.id.btnFinishOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save(v);
            }
        }); }

    private void Save(View view) {

        Order order = CurrentSession.getOrder();
        User user = CurrentSession.getUser();

        order.setAddress(txtAddress.getText().toString());
        order.setLocality(txtLocality.getText().toString());
        order.setPostalCode(txtPostalCode.getText().toString());

        String summary = "Pedido realizado: \n" +
                "categoría: " + order.getCategory().getName() + "\n" +
                "producto: " + order.getProduct().getName() + "\n" +
                "cantidad: " + order.getCount() + "\n" +
                "dirección: " + order.getAddress() + "\n" +
                "Localidad: " + order.getLocality() + "\n" +
                "Código postal:" + order.getPostalCode() + "\n" +
                "Mail de envío: " + user.getEmail();

        OrderRepository orderRepository = new OrderRepository(getApplicationContext());

        try {
            orderRepository.Insert(order);

            Toast.makeText(this, summary, Toast.LENGTH_LONG).show();

            CurrentSession.setOrder(null);

            Intent intent;
            intent = new Intent(this, dam.pmdm.pmdm03.ui.CustomerMain.class);
            startActivity(intent);

        } catch (Exception ex) {
            Toast.makeText(this, "Se ha pdroducido un error durante el guardado del pedido", Toast.LENGTH_SHORT).show();
        }
    }
}
