package com.qsoft.onlinedio.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.qsoft.onlinedio.R;

/**
 * User: khiemvx
 * Date: 10/18/13
 */
public class AddComment extends FragmentActivity
{
    private Button comment_btCancel;
    private Button comment_btPost;
    private EditText comment_etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_layout);

        initComponents();

        comment_btCancel.setOnClickListener(onclickListener);
        comment_btPost.setOnClickListener(onclickListener);

    }

    private void initComponents()
    {
        comment_btCancel = (Button) findViewById(R.id.comment_btCancel);
        comment_btPost = (Button) findViewById(R.id.comment_btPost);
        comment_etComment = (EditText) findViewById(R.id.comment_tvComment);
    }

    private final View.OnClickListener onclickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.comment_btCancel:
                    finish();
                    break;
                case R.id.comment_btPost:
                    Intent intentProgram_Extra = new Intent(AddComment.this, Comment.class);
                    String comment = comment_etComment.getText().toString();
                    intentProgram_Extra.putExtra("COMMENT", comment);
                    startActivity(intentProgram_Extra);
                    break;
            }
        }
    };
}
