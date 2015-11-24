package wth.com.hseya.weatherapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import wth.com.hseya.weatherapp.R;
import wth.com.hseya.weatherapp.bean.LocationDataBean;
import wth.com.hseya.weatherapp.listener.LocationDeleteListener;

/**
 * Created by ayesh on 11/23/15.
 */
public class SelectedLocationListAdapter extends BaseAdapter {

    private Activity activity;
    private List<LocationDataBean> dataList;
    private LocationDataBean dataBean;
    private LocationDeleteListener deleteListener;

    public SelectedLocationListAdapter(Activity act, List<LocationDataBean> arrayList,LocationDeleteListener deleteListener) {

        this.activity       = act;
        this.dataList       = arrayList;
        this.deleteListener = deleteListener;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try {

            View view = convertView;

            ViewHolder holder;

            if (view == null) {

                LayoutInflater inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listview_selected_location, null);

                holder = new ViewHolder();
                view.setTag(holder);

            } else {
                holder = (ViewHolder) view.getTag();
            }

            if ((dataList == null) || ((position + 1) > dataList.size()))
                return view;

            dataBean = dataList.get(position);


            holder.selectedLocationTextView = (TextView) view.findViewById(R.id.selectedLocationTextView);
            holder.selectedLocLatTextView   = (TextView) view.findViewById(R.id.selectedLocLatTextView);
            holder.selectedLocLongTextView  = (TextView) view.findViewById(R.id.selectedLocLongTextView);
            holder.locationDeleteImageView  = (ImageView) view.findViewById(R.id.locationDeleteImageView);

            holder.locationDeleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteListener.OnDeleteLocation(position);

                }
            });

            if (holder.selectedLocationTextView != null) {

                holder.selectedLocationTextView.setText(dataBean.getName());
            }

            if (holder.selectedLocLongTextView != null) {

                holder.selectedLocLongTextView.setText("LONG : " + dataBean.getLon());
            }

            if (holder.selectedLocLatTextView != null) {

                holder.selectedLocLatTextView.setText("LAT : " + dataBean.getLat());
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

        public TextView selectedLocationTextView, selectedLocLatTextView, selectedLocLongTextView;
        private ImageView locationDeleteImageView;

    }
}