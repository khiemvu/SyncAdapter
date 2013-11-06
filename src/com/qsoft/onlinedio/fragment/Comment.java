package com.qsoft.onlinedio.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.qsoft.onlinedio.adapter.CommentList;
import com.qsoft.onlinedio.model.CommentModel;
import com.qsoft.onlinedio.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: khiemvx
 * Date: 10/17/13
 */
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


    private TextView tvComment;
    private ListView listView;
    private List<CommentModel> rowItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View viewer = (View) inflater.inflate(R.layout.program_comment_layout, container, false);

        initComponent(viewer);

        tvComment.setOnClickListener(onclickListener);

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

        return viewer;
    }

    private void initComponent(View viewer)
    {
        listView = (ListView) viewer.findViewById(R.id.comment_lvCommentList);
        tvComment = (TextView) viewer.findViewById(R.id.comment_tvComment);
    }

    private final View.OnClickListener onclickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.comment_tvComment:
                    Intent intent = new Intent(getActivity().getBaseContext(), AddComment.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
