//package com.qsoft.onlinedio.ui.controller;
//
//import android.app.Activity;
//import android.media.AudioManager;
//import android.os.Handler;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.SeekBar;
//import com.googlecode.androidannotations.annotations.*;
//import com.qsoft.onlinedio.R;
//import com.qsoft.onlinedio.fragment.*;
//import com.qsoft.onlinedio.util.DateTime;
//
///**
// * User: khiemvx
// * Date: 11/12/13
// */
//
//@EBean
//public class ProgramController
//{
//    private int mediaDuration;
//    private int mediaPosition;
//    private final Handler handler = new Handler();
//
//    ProgramFragment context = new ProgramFragment_();
//
//    @SeekBarProgressChange(R.id.program_sbVolume)
//    protected void onProgressChangeOnSeekBar (SeekBar seekBar, int progress, boolean fromUser){
//        context.getAudioManager().setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
//    }
//
//    // This is event handler thumb moving event
//    public void seekChange(View v)
//    {
//        if (context.getMediaPlayer().isPlaying())
//        {
//            SeekBar sb = (SeekBar) v;
//            context.getMediaPlayer().seekTo(sb.getProgress());
//        }
//    }
//
//    @Click({R.id.program_rbtThumnail,R.id.program_rbtDetail, R.id.program_rbtComment,
//            R.id.program_btPlayOrStop, R.id.program_btBack})
//    protected void someButtonClick(View view)
//    {
//        switch (view.getId())
//        {
//            case R.id.program_rbtThumnail:
//                FragmentTransaction fragmentTransaction = context.getFragmentManager().beginTransaction();
//                ThumbnailFragment thumnailFragmentActivity = new ThumbnailFragment_();
//                fragmentTransaction.replace(R.id.program_fl_generic, thumnailFragmentActivity);
//                fragmentTransaction.commit();
//                break;
//            case R.id.program_rbtDetail:
//                FragmentTransaction transaction = context.getFragmentManager().beginTransaction();
//                DetailFragment detailFragmentActivity = new DetailFragment_();
//                transaction.replace(R.id.program_fl_generic, detailFragmentActivity);
//                transaction.commit();
//                break;
//            case R.id.program_rbtComment:
//                FragmentTransaction transactionComment = context.getFragmentManager().beginTransaction();
//                Comment comment = new Comment_();
//                transactionComment.replace(R.id.program_fl_generic, comment);
//                transactionComment.commit();
//                break;
//            case R.id.program_btPlayOrStop:
//                mediaPlayOrStop();
//                break;
//            case R.id.program_btBack:
//                context.getFragmentManager().popBackStack();
//                break;
//        }
//    }
//    private void mediaPlayOrStop()
//    {
//        if (!context.getMediaPlayer().isPlaying())
//        {
//            context.getBtPlayOrStop().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.content_bt_play));
//            try
//            {
//                context.getMediaPlayer().start();
//                startPlayProgressUpdater();
//            }
//            catch (IllegalStateException e)
//            {
//                context.getMediaPlayer().pause();
//            }
//        }
//        else
//        {
//            context.getMediaPlayer().pause();
//            context.getBtPlayOrStop().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.content_bt_pause));
//        }
//    }
//
//    public void startPlayProgressUpdater()
//    {
//        getTimeOfRecordAndShow();
//        context.getSbTime().setProgress(mediaPosition);
//        context.getSbTime().setMax(mediaDuration);
//        if (context.getMediaPlayer().isPlaying())
//        {
//            Runnable notification = new Runnable()
//            {
//                public void run()
//                {
//                    startPlayProgressUpdater();
//                }
//            };
//            handler.postDelayed(notification, 1000);
//        }
//        else
//        {
//            context.getMediaPlayer().pause();
//            context.getBtPlayOrStop().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.content_bt_pause));
//            if (mediaPosition == mediaDuration)
//            {
//                context.getSbTime().setProgress(0);
//            }
//            else
//            {
//                context.getSbTime().setProgress(mediaPosition);
//            }
//        }
//    }
//
//    public void getTimeOfRecordAndShow()
//    {
//        mediaDuration = context.getMediaPlayer().getDuration();
//        mediaPosition = context.getMediaPlayer().getCurrentPosition();
//
//        context.getTvTimeCur().setText(DateTime.getTimeString(mediaPosition));
//        context.getTvTimePlay().setText(DateTime.getTimeString(mediaDuration));
//    }
//
//
//
//}
