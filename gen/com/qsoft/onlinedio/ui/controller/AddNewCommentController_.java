//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.onlinedio.ui.controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.qsoft.onlinedio.ui.fragment.AddComment;

public final class AddNewCommentController_
    extends AddNewCommentController
{

    private Context context_;

    private AddNewCommentController_(Context context) {
        context_ = context;
        init_();
    }

    public void afterSetContentView_() {
        if (!(context_ instanceof Activity)) {
            return ;
        }
        {
            View view = findViewById(com.qsoft.onlinedio.R.id.comment_btCancel);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AddNewCommentController_.this.someButtonClick(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.onlinedio.R.id.comment_btPost);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AddNewCommentController_.this.someButtonClick(view);
                    }

                }
                );
            }
        }
    }

    /**
     * You should check that context is an activity before calling this method
     * 
     */
    public View findViewById(int id) {
        Activity activity_ = ((Activity) context_);
        return activity_.findViewById(id);
    }

    @SuppressWarnings("all")
    private void init_() {
        if (context_ instanceof Activity) {
            Activity activity = ((Activity) context_);
        }
        if (context_ instanceof AddComment) {
            context = ((AddComment) context_);
        }
    }

    public static AddNewCommentController_ getInstance_(Context context) {
        return new AddNewCommentController_(context);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
