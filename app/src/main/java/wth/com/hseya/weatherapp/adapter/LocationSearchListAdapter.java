package wth.com.hseya.weatherapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wth.com.hseya.weatherapp.R;
import wth.com.hseya.weatherapp.bean.LocationDataBean;


public class LocationSearchListAdapter extends BaseAdapter {

    private Activity activity;
    private List<LocationDataBean> dataList;
    private LocationDataBean dataBean;

    public LocationSearchListAdapter(Activity act, List<LocationDataBean> arrayList) {

        this.activity = act;
        this.dataList = arrayList;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try {

            View view = convertView;

            ViewHolder holder;

            if (view == null) {

                LayoutInflater inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listview_locations, null);

                holder = new ViewHolder();
                view.setTag(holder);

            } else {
                holder = (ViewHolder) view.getTag();
            }

            if ((dataList == null) || ((position + 1) > dataList.size()))
                return view;

            dataBean = dataList.get(position);


            holder.addressTextView = (TextView) view.findViewById(R.id.addressTextView);
            holder.latTextView = (TextView) view.findViewById(R.id.latTextView);
            holder.longTextView = (TextView) view.findViewById(R.id.longTextView);

            if (holder.addressTextView != null) {

                holder.addressTextView.setText(dataBean.getName());
            }

            if (holder.longTextView != null) {

                holder.longTextView.setText("LONG : " + dataBean.getLon());
            }

            if (holder.latTextView != null) {

                holder.latTextView.setText("LAT : " + dataBean.getLat());
            }


            return view;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getCount() {

        if (dataList != null) {
            return dataList.size();
        } else {
            return 1;
        }

    }

    @Override
    public Object getItem(int index) {

        if (dataList != null) {
            return dataList.get(index);
        } else {
            return null;
        }

    }

    @Override
    public long getItemId(int index) {
        return index;
    }



    public class ViewHolder {

        public TextView addressTextView, latTextView, longTextView;

    }
}