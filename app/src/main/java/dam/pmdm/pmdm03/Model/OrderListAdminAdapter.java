package dam.pmdm.pmdm03.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dam.pmdm.pmdm03.Data.OrderRepository;
import dam.pmdm.pmdm03.R;

public class OrderListAdminAdapter extends RecyclerView.Adapter<OrderListAdminAdapter.MyViewHolder> {

    private ArrayList<Order> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtOrderInfo;
        public TextView txtOrderIdInfo;
        public TextView txtStatusInfo;
        public TextView txtOrderId;
        public Button btnAccept;
        public Button btnReject;

        public MyViewHolder(View v) {
            super(v);
            txtOrderInfo = (TextView) itemView.findViewById(R.id.txtOrderInfo);
            txtOrderIdInfo = (TextView) itemView.findViewById(R.id.txtOrderIdInfo);
            txtStatusInfo = (TextView) itemView.findViewById(R.id.txtStatusInfo);
            txtOrderId = (TextView) itemView.findViewById(R.id.txtOrderId);
            btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
            btnReject = (Button) itemView.findViewById(R.id.btnReject);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderListAdminAdapter(ArrayList<Order> myDataset) {
        this.mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderListAdminAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_admin, parent, false);
        return new MyViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Order order = mDataset.get(position);

        String OrderInfo = "Cantidad:";

        String orderInfo = "" +
                "cantidad: " + order.getCount() + "\n" +
                "producto: " + order.getProduct().getName() + "\n" +
                "categoría: " + order.getCategory().getName();

        holder.txtOrderInfo.setText(orderInfo);
        holder.txtOrderIdInfo.setText("Num. " + order.getId());
        holder.txtOrderId.setText(String.valueOf(order.getId()));
        if (order.getOrderStatus() == OrderStatus.Accepted) {
            holder.btnAccept.setVisibility(View.INVISIBLE);
        }
        if (order.getOrderStatus() == OrderStatus.rejected) {
            holder.btnReject.setVisibility(View.INVISIBLE);
        }
        holder.txtStatusInfo.setText(order.getOrderStatus().toString());

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orderId = Integer.parseInt(holder.txtOrderId.getText().toString());
                OrderRepository orderRepository = new OrderRepository(v.getContext());
                orderRepository.ChangeOrderStatus(orderId, OrderStatus.Accepted);
                holder.txtStatusInfo.setText(String.valueOf(OrderStatus.Accepted));
                holder.btnAccept.setVisibility(View.INVISIBLE);
            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orderId = Integer.parseInt(holder.txtOrderId.getText().toString());
                OrderRepository orderRepository = new OrderRepository(v.getContext());
                orderRepository.ChangeOrderStatus(orderId, OrderStatus.rejected);
                holder.txtStatusInfo.setText(String.valueOf(OrderStatus.rejected));
                holder.btnReject.setVisibility(View.INVISIBLE);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
