package com.qsoft.onlinedio.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.database.DbHelper;
import com.qsoft.onlinedio.filecache.ImageLoader;
import com.qsoft.onlinedio.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: khiemvx
 * Date: 10/15/13
 */
public class HomeFeeds extends CursorAdapter
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

//    public HomeFeeds(Context context,
//                     ArrayList<HashMap<String, String>> arraylist)
//    {
//        this.context = context;
//        data = arraylist;
//        imageLoader = new ImageLoader(context);
//    }

    public HomeFeeds(Context context, Cursor c, ArrayList<HashMap<String, String>> arrayList)
    {
        super(context, c);
        this.context = context;
        data = arrayList;
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
        resultp = data.get(position);

        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvUserName = (TextView) itemView.findViewById(R.id.tvName);
        tvLike = (TextView) itemView.findViewById(R.id.tvLike);
        tvComment = (TextView) itemView.findViewById(R.id.tvComment);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        imView = (ImageView) itemView.findViewById(R.id.ivAvatar);

        tvComment.setText("Comments: " + resultp.get(HomeFragment.COMMENT));
        tvLike.setText("Likes: " + resultp.get(HomeFragment.LIKE));
        tvTime.setText("" + resultp.get(HomeFragment.TIME) + " day a go");
        tvTitle.setText(resultp.get(HomeFragment.TITLE));
        tvUserName.setText(resultp.get(HomeFragment.NAME));
        imageLoader.DisplayImage(resultp.get(HomeFragment.AVATAR), imView);
        return itemView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.home_items_layout,parent, false);
        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(cursor.getString(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_TITLE)));
        tvUserName = (TextView) view.findViewById(R.id.tvName);
        tvUserName.setText(cursor.getString(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_USERNAME)));
        tvLike = (TextView) view.findViewById(R.id.tvLike);
        tvLike.setText(cursor.getInt(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_LIKES)));
        tvComment = (TextView) view.findViewById(R.id.tvComment);
        tvComment.setText(cursor.getInt(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_COMMENTS)));
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvTime.setText(cursor.getString(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_UPDATED_AT)));
        imView = (ImageView) view.findViewById(R.id.ivAvatar);
        String urlImage = cursor.getString(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_AVATAR));
        imageLoader.DisplayImage(urlImage,imView);
    }
}
