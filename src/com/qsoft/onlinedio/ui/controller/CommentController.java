package com.qsoft.onlinedio.ui.controller;

import android.app.Activity;
import android.content.Intent;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.adapter.CommentList;
import com.qsoft.onlinedio.database.entity.CommentModel;
import com.qsoft.onlinedio.fragment.AddComment_;
import com.qsoft.onlinedio.fragment.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * User: khiemvx
 * Date: 11/12/13
 */
@EBean
public class CommentController
{
    public static final String[] names = new String[]{
            "Khiemvx", "Khiemvx", "Khiemvx", "Khiemvx", "Khiemvx", "Khiemvx"};
    public static final String[] comments = new String[]{
            "Báo chí Trung Quốc cho biết cuộc tập trận quy mô Parmenion 2013 của quân đội Hy Lạp được tiến hành cả trên bộ lẫn trên biển, sau màn phô diễn sức mạnh trên bộ vào đầu tháng 10 giờ đây tới lượt lực lượng hải quân của nước này được lệnh điều động thao diễn.",
            "Báo chí Trung Quốc cho biết cuộc tập trận quy mô Parmenion 2013 của quân đội Hy Lạp được tiến hành cả trên bộ lẫn trên biển, sau màn phô diễn sức mạnh trên bộ vào đầu tháng 10 giờ đây tới lượt lực lượng hải quân của nước này được lệnh điều động thao diễn.",
            "Báo chí Trung Quốc cho biết cuộc tập trận quy mô Parmenion 2013 của quân đội Hy Lạp được tiến hành cả trên bộ lẫn trên biển, sau màn phô diễn sức mạnh trên bộ vào đầu tháng 10 giờ đây tới lượt lực lượng hải quân của nước này được lệnh điều động thao diễn.",
            "Báo chí Trung Quốc cho biết cuộc tập trận quy mô Parmenion 2013 của quân đội Hy Lạp được tiến hành cả trên bộ lẫn trên biển, sau màn phô diễn sức mạnh trên bộ vào đầu tháng 10 giờ đây tới lượt lực lượng hải quân của nước này được lệnh điều động thao diễn.",
            "Báo chí Trung Quốc cho biết cuộc tập trận quy mô Parmenion 2013 của quân đội Hy Lạp được tiến hành cả trên bộ lẫn trên biển, sau màn phô diễn sức mạnh trên bộ vào đầu tháng 10 giờ đây tới lượt lực lượng hải quân của nước này được lệnh điều động thao diễn.",
            "Báo chí Trung Quốc cho biết cuộc tập trận quy mô Parmenion 2013 của quân đội Hy Lạp được tiến hành cả trên bộ lẫn trên biển, sau màn phô diễn sức mạnh trên bộ vào đầu tháng 10 giờ đây tới lượt lực lượng hải quân của nước này được lệnh điều động thao diễn.",};
    public static final Integer[] images = new Integer[]{R.drawable.content_comment_image, R.drawable.content_comment_image,
            R.drawable.content_comment_image, R.drawable.content_comment_image,
            R.drawable.content_comment_image, R.drawable.content_comment_image,};
    public static final String[] times = new String[]{
            "a day ago", "a day ago", "a day ago", "a day ago", "a day ago", "a day ago"};

    @RootContext
    Activity context;

    Comment comment = new Comment();
    private List<CommentModel> rowItems;

    @Click(R.id.comment_tvComment)
    protected void tvCommentClick(){
        Intent intent = new Intent(context.getBaseContext(), AddComment_.class);
        context.startActivity(intent);
    }

    public CommentList prepareDataShow()
    {
        //add data into list
        rowItems = new ArrayList<CommentModel>();
        for (int i = 0; i < names.length; i++)
        {
            CommentModel item = new CommentModel(images[i], names[i], comments[i], times[i]);
            rowItems.add(item);
        }
        CommentList adapter = new CommentList(context,
                R.layout.program_comment_item_layout, rowItems);

        //TODO add data of addCommentFragmentActivity into Listview
        return adapter;
    }
}
