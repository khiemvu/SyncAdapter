package com.qsoft.onlinedio.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.adapter.CommentList;
import com.qsoft.onlinedio.model.CommentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * User: khiemvx
 * Date: 10/17/13
 */
@EFragment (R.layout.program_comment_layout)
public class Comment extends Fragment
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

    @ViewById(R.id.comment_tvComment)
    protected TextView tvComment;
    @ViewById(R.id.comment_lvCommentList)
    protected ListView listView;

    private List<CommentModel> rowItems;

    @AfterViews
    protected void afterViews()
    {
        prepareDataShow();
    }
    private void prepareDataShow()
    {
        //add data into list
        rowItems = new ArrayList<CommentModel>();
        for (int i = 0; i < names.length; i++)
        {
            CommentModel item = new CommentModel(images[i], names[i], comments[i], times[i]);
            rowItems.add(item);
        }
        CommentList adapter = new CommentList(getActivity(),
                R.layout.program_comment_item_layout, rowItems);

        //TODO add data of addCommentFragmentActivity into Listview

        listView.setAdapter(adapter);
    }

    @Click (R.id.comment_tvComment)
    protected void tvCommentClick(){
        Intent intent = new Intent(getActivity().getBaseContext(), AddComment_.class);
        startActivity(intent);
    }
}
