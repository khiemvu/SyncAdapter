package com.qsoft.onlinedio.ui.controller;

import android.content.Intent;
import android.view.View;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.ui.fragment.AddComment;
import com.qsoft.onlinedio.ui.fragment.Comment_;

/**
 * User: khiemvx
 * Date: 11/13/13
 */
@EBean
public class AddNewCommentController
{
    @RootContext
    AddComment context;

    @Click({R.id.comment_btCancel, R.id.comment_btPost})
    protected void someButtonClick(View view){
        switch (view.getId())
        {
            case R.id.comment_btCancel:
                context.finish();
                break;
            case R.id.comment_btPost:
                postComment();
                break;
        }
    }

    private void postComment()
    {
        Intent intentProgram_Extra = new Intent(context, Comment_.class);
        String comment = context.getComment_etComment().getText().toString();
        intentProgram_Extra.putExtra("COMMENT", comment);
        context.startActivity(intentProgram_Extra);
    }
}
