package com.qsoft.onlinedio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.filecache.ImageLoader;
import com.qsoft.onlinedio.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: khiemvx
 * Date: 10/15/13
 */
public class HomeFeeds extends BaseAdapter
{
    private TextView tvTitle;
    private TextView tvUserName;
    private TextView tvLike;
    private TextView tvComment;
    private TextView tvTime;
    private ImageView imView;

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public HomeFeeds(Context context,
                     ArrayList<HashMap<String, String>> arraylist)
    {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.home_items_layout, parent, false);
        // Get the position
        resultp = data.get(position);

        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvUserName = (TextView) itemView.findViewById(R.id.tvName);
        tvLike = (TextView) itemView.findViewById(R.id.tvLike);
        tvComment = (TextView) itemView.findViewById(R.id.tvComment);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        imView = (ImageView) itemView.findViewById(R.id.ivAvatar);

        tvComment.setText("Comments: " + resultp.get(HomeFragment.COMMENT));
        tvLike.setText("Likes: " + resultp.get(HomeFragment.LIKE));
        tvTime.setText(""+resultp.get(HomeFragment.TIME)+ " day a go");
        tvTitle.setText(resultp.get(HomeFragment.TITLE));
        tvUserName.setText(resultp.get(HomeFragment.NAME));
        imageLoader.DisplayImage(resultp.get(HomeFragment.AVATAR), imView);
//        // Capture ListView item click
//        itemView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // Get the position
//                resultp = data.get(position);
//                Intent intent = new Intent(context, HomeItemView.class);
//                intent.putExtra(HomeFragment.COMMENT, resultp.get(HomeFragment.COMMENT));
//                intent.putExtra(HomeFragment.NAME, resultp.get(HomeFragment.NAME));
//                intent.putExtra(HomeFragment.TITLE,resultp.get(HomeFragment.TITLE));
//                intent.putExtra(HomeFragment.NAME,resultp.get(HomeFragment.NAME));
//                intent.putExtra(HomeFragment.TIME,resultp.get(HomeFragment.TIME));
//                intent.putExtra(HomeFragment.AVATAR,resultp.get(HomeFragment.AVATAR));
//                context.startActivity(intent);
//            }
//        });
        return itemView;
    }
}
