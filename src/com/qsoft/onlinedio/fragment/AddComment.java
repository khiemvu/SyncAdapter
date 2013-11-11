package com.qsoft.onlinedio.fragment;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.onlinedio.R;

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

    @Click({R.id.comment_btCancel, R.id.comment_btPost})
    protected void someButtonClick(View view){
        switch (view.getId())
        {
            case R.id.comment_btCancel:
                finish();
                break;
            case R.id.comment_btPost:
                postComment();
                break;
        }
    }

    private void postComment()
    {
        Intent intentProgram_Extra = new Intent(this, Comment_.class);
        String comment = comment_etComment.getText().toString();
        intentProgram_Extra.putExtra("COMMENT", comment);
        startActivity(intentProgram_Extra);
    }

}
