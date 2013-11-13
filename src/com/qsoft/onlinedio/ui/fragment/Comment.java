package com.qsoft.onlinedio.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.database.entity.CommentModel;
import com.qsoft.onlinedio.ui.controller.CommentController;

import java.util.List;

/**
 * User: khiemvx
 * Date: 10/17/13
 */
@EFragment (R.layout.program_comment_layout)
public class Comment extends Fragment
{
    @ViewById(R.id.comment_tvComment)
    protected TextView tvComment;
    @ViewById(R.id.comment_lvCommentList)
    protected ListView listView;
    @Bean
    CommentController commentController;

    private List<CommentModel> rowItems;

    @AfterViews
    protected void afterViews()
    {
        listView.setAdapter(commentController.prepareDataShow());
    }
}
