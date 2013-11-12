//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.onlinedio.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.qsoft.onlinedio.R.layout;
import com.qsoft.onlinedio.ui.controller.CommentController_;

public final class Comment_
    extends Comment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
        commentController = CommentController_.getInstance_(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        listView = ((ListView) findViewById(com.qsoft.onlinedio.R.id.comment_lvCommentList));
        tvComment = ((TextView) findViewById(com.qsoft.onlinedio.R.id.comment_tvComment));
        ((CommentController_) commentController).afterSetContentView_();
        afterViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.program_comment_layout, container, false);
        }
        return contentView_;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterSetContentView_();
    }

    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    public static Comment_.FragmentBuilder_ builder() {
        return new Comment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public Comment build() {
            Comment_ fragment_ = new Comment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
