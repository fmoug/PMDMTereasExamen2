package dam.pmdm.pmdm03.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dam.pmdm.pmdm03.R;

public class OrderListCustomerAdapter extends RecyclerView.Adapter<OrderListCustomerAdapter.MyViewHolder> {
    private ArrayList<Order>  mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtOrderInfo;
        public TextView txtOrderIdInfo;
        public TextView txtStatusInfo;

        public MyViewHolder(View v) {
            super(v);
            txtOrderInfo=(TextView) itemView.findViewById(R.id.txtOrderInfo);
            txtOrderIdInfo=(TextView) itemView.findViewById(R.id.txtOrderIdInfo);
            txtStatusInfo=(TextView) itemView.findViewById(R.id.txtStatusInfo);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderListCustomerAdapter(ArrayList<Order> myDataset) {
        this.mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderListCustomerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_customer,parent,false);
        return new MyViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Order order = mDataset.get(position);

        String OrderInfo = "Cantidad:";

        String orderInfo = "" +
                "cantidad: " + order.getCount() + "\n" +
                "producto: " + order.getProduct().getName() + "\n" +
                "categoría: " + order.getCategory().getName();

        holder.txtOrderInfo.setText(orderInfo);
        holder.txtOrderIdInfo.setText("Num. " +order.getId());
        holder.txtStatusInfo.setText(order.getOrderStatus().toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
