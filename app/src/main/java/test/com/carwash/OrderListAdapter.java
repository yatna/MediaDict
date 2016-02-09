package test.com.carwash;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ritesh_kumar on 14-Jun-15.
 */
public class OrderListAdapter extends BaseAdapter {

    Context context;
    ArrayList<OrderList> orderLists;

    public OrderListAdapter(Context c, ArrayList<OrderList> orderLists) {
        this.context = c;
        this.orderLists = orderLists;
    }

    @Override
    public int getCount() {
        return orderLists.size();
    }

    @Override
    public Object getItem(int position) {
        return orderLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderLists.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder ;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
            holder = new ViewHolder();
            holder.orderid = (TextView) convertView.findViewById(R.id.id);
            holder.ordertype = (TextView) convertView.findViewById(R.id.type);
            holder. amount = (TextView) convertView.findViewById(R.id.amt);
            holder.timestamp= (TextView) convertView.findViewById(R.id.date);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.orderid.setText(orderLists.get(position).getOrder_id());
        holder.ordertype.setText(orderLists.get(position).getOrder_type());
        holder.amount.setText(orderLists.get(position).getAmount());
        holder.timestamp.setText(orderLists.get(position).getTimestamp());
        holder.status.setText(orderLists.get(position).getStatus());

        return convertView;
    }


    class ViewHolder {
        TextView orderid, ordertype, amount, timestamp, status;
    }
}
