package com.qsoft.onlinedio.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.onlinedio.R;

public class Sidebar extends ArrayAdapter<String>
{
    private String[] slideBarOptions;
    private Context context;
    private TextView tvOption, tvSpec;
    private ImageView ivOption;

    public Sidebar(Context context, int textViewResourceId, String[] slideBarOptions)
    {
        super(context, textViewResourceId, slideBarOptions);
        this.slideBarOptions = slideBarOptions;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.slidebar_listoptions, null);
        }
        setUpViewFindByID(v);
        String option = slideBarOptions[position];
        if (option.equals("Home"))
        {
            ivOption.setImageResource(R.drawable.sidebar_image_icon_home);
        }
        else if (option.equals("Favorite"))
        {
            ivOption.setImageResource(R.drawable.sidebar_image_icon_favorite);
        }
        else if (option.equals("Following"))
        {
            ivOption.setImageResource(R.drawable.sidebar_image_icon_favorite);
        }
        else if (option.equals("Audience"))
        {
            tvOption.setText(option);
            ivOption.setImageResource(R.drawable.sidebar_image_icon_audience);
        }
        else if (option.equals("Genres"))
        {
            ivOption.setImageResource(R.drawable.sidebar_image_icon_genres);
        }
        else if (option.equals("Setting"))
        {
            ivOption.setImageResource(R.drawable.sidebar_image_icon_setting);
        }
        else if (option.equals("Help Center"))
        {
            tvSpec.setText("Support");
            ivOption.setImageResource(R.drawable.sidebar_image_icon_helpcenter);
        }
        else
        {
            ivOption.setImageResource(R.drawable.sidebar_image_icon_logout);
        }
        if (!option.equals("Help Center"))
        {
            tvSpec.setText("");
        }
        tvOption.setText(option);
        tvOption.setTypeface(null, Typeface.BOLD);
        tvOption.setTextColor(R.color.Black);
        return v;
    }

    private void setUpViewFindByID(View v)
    {
        tvOption = (TextView) v.findViewById(R.id.slidebar_tvOption);
        ivOption = (ImageView) v.findViewById(R.id.slidebar_ivOption);
        tvSpec = (TextView) v.findViewById(R.id.slidebar_tvSpecOption);
    }
}
