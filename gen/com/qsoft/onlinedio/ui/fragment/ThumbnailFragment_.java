//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.onlinedio.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qsoft.onlinedio.R.layout;

public final class ThumbnailFragment_
    extends ThumbnailFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.program_thumbnail_fragment, container, false);
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

    public static ThumbnailFragment_.FragmentBuilder_ builder() {
        return new ThumbnailFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public ThumbnailFragment build() {
            ThumbnailFragment_ fragment_ = new ThumbnailFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
