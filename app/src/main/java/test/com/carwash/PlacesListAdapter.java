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
public class PlacesListAdapter extends BaseAdapter {

    Context context;
    ArrayList<ServiceCentreLocation> locations;

    public PlacesListAdapter(Context c, ArrayList<ServiceCentreLocation> locations) {
        this.context = c;
        this.locations = locations;
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Object getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return locations.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder ;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
            holder = new ViewHolder();
            holder.tvPlaceName = (TextView) convertView.findViewById(R.id.tvPlaceName);
            holder.tvDuration = (TextView) convertView.findViewById(R.id.tvlDuration);
            holder.tvDistance = (TextView) convertView.findViewById(R.id.tvlDistance);
            holder.tvPhone = (TextView) convertView.findViewById(R.id.tvlPhone);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tvlAddress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvPlaceName.setText(locations.get(position).getPlaceName());
        holder.tvDistance.setText(locations.get(position).getDistance());
        holder.tvDuration.setText(locations.get(position).getDuration());
        holder.tvPhone.setText(locations.get(position).getPhone());
        holder.tvAddress.setText(locations.get(position).getAddress());

        return convertView;
    }


    class ViewHolder {
        TextView tvPlaceName, tvDuration, tvDistance, tvPhone, tvAddress;
    }
}
