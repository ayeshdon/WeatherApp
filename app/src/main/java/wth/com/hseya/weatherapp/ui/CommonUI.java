package wth.com.hseya.weatherapp.ui;


import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import wth.com.hseya.weatherapp.R;


public class CommonUI {


    private static CommonUI instance;

    public static synchronized CommonUI getInstance(Context context) {


        if (instance == null) {

            instance = new CommonUI();
        }

        return instance;
    }


    /**
     * show custom action bar
     *
     * @param customActionBar - actionbar object
     * @param context
     * @param isHomeNavEnable
     * @param title           - actionbar title
     * @param isHomeUp
     * @throws Exception
     */
    public void setCommonActionBar(ActionBar customActionBar, Context context, boolean isHomeNavEnable,
                                   String title, boolean isHomeUp) throws Exception {
        try {

            customActionBar.setDisplayHomeAsUpEnabled(false);
            customActionBar.setDisplayShowCustomEnabled(true);
            customActionBar.setDisplayUseLogoEnabled(true);
            customActionBar.setDisplayShowTitleEnabled(true);

            if (isHomeNavEnable) {

                if (isHomeUp) {

                    customActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM |
                            ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);


                } else {
                    customActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM |
                            ActionBar.DISPLAY_SHOW_HOME);
                }
            } else {
                customActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            }


//			customActionBar.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.actionbar));


            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(R.layout.actionbar_layout, null);

            TextView actionBarTextView = (TextView) v.findViewById(R.id.actionBarTextView);

            actionBarTextView.setText(Html.fromHtml(title));


            customActionBar.setCustomView(v);

        } catch (Exception e) {
            throw e;
        }
    }





}
