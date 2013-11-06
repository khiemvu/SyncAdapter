package com.qsoft.onlinedio.filecache;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.fragment.HomeFragment;

/**
 * User: khiemvx
 * Date: 11/6/13
 */
public class HomeItemView extends Activity
{
    // Declare Variables
    ImageLoader imageLoader = new ImageLoader(this);
    private TextView tvTitle;
    private TextView tvUserName;
    private TextView tvLike;
    private TextView tvComment;
    private TextView tvTime;
    private ImageView imView;

    private String title;
    private String userName;
    private String likes;
    private String comment;
    private String time;
    private String image;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.home_items_layout);

        Intent i = getIntent();
        title = i.getStringExtra(HomeFragment.TITLE);
        comment = i.getStringExtra(HomeFragment.COMMENT);
        likes = i.getStringExtra(HomeFragment.LIKE);
        userName = i.getStringExtra(HomeFragment.NAME);
        time = i.getStringExtra(HomeFragment.TIME);
        image = i.getStringExtra(HomeFragment.AVATAR);


        initComponents();

        setValueOnView();
    }

    private void setValueOnView()
    {
        tvTitle.setText(title);
        tvComment.setText(comment);
        tvLike.setText(likes);
        tvUserName.setText(userName);
        tvTime.setText(time);
        imageLoader.DisplayImage(image, imView);
    }

    private void initComponents()
    {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvUserName = (TextView) findViewById(R.id.tvName);
        tvLike = (TextView) findViewById(R.id.tvLike);
        tvComment = (TextView) findViewById(R.id.tvComment);
        tvTime = (TextView) findViewById(R.id.tvTime);
        imView = (ImageView) findViewById(R.id.ivAvatar);
    }
}