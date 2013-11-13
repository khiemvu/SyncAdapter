package com.qsoft.onlinedio.ui.fragment;

import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.ui.controller.AddNewCommentController;

/**
 * User: khiemvx
 * Date: 10/18/13
 */
@EActivity(R.layout.comment_layout)
public class AddComment extends FragmentActivity
{
    @ViewById(R.id.comment_btCancel)
    protected Button comment_btCancel;
    @ViewById(R.id.comment_btPost)
    protected Button comment_btPost;
    @ViewById(R.id.comment_tvComment)
    protected EditText comment_etComment;

    @Bean
    AddNewCommentController addNewCommentController;

    public EditText getComment_etComment()
    {
        return comment_etComment;
    }

    public void setComment_etComment(EditText comment_etComment)
    {
        this.comment_etComment = comment_etComment;
    }
}
