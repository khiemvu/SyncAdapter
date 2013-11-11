//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.onlinedio.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import com.qsoft.onlinedio.R.id;
import com.qsoft.onlinedio.R.layout;

public final class FirstLaunchActivity_
    extends FirstLaunchActivity
{

    private Handler handler_ = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.first_launch_layout);
    }

    private void init_(Bundle savedInstanceState) {
    }

    private void afterSetContentView_() {
        launch_btLogin = ((Button) findViewById(id.launch_btLogin));
        {
            View view = findViewById(id.launch_btLogin);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        FirstLaunchActivity_.this.btLoginClicked();
                    }

                }
                );
            }
        }
        afterViews();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView_();
    }

    public static FirstLaunchActivity_.IntentBuilder_ intent(Context context) {
        return new FirstLaunchActivity_.IntentBuilder_(context);
    }

    @Override
    public void showMessage(final String msg) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    FirstLaunchActivity_.super.showMessage(msg);
                } catch (RuntimeException e) {
                    Log.e("FirstLaunchActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, FirstLaunchActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public FirstLaunchActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (context_ instanceof Activity) {
                ((Activity) context_).startActivityForResult(intent_, requestCode);
            } else {
                context_.startActivity(intent_);
            }
        }

    }

}
