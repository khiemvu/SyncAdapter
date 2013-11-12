package com.qsoft.onlinedio.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.onlinedio.R;
import com.qsoft.onlinedio.util.DateTime;

/**
 * User: khiemvx
 * Date: 10/16/13
 */
@EFragment (R.layout.program_layout)
public class ProgramFragment extends Fragment
{
    private int mediaDuration;
    private int mediaPosition;
    private MediaPlayer mediaPlayer;
    private final Handler handler = new Handler();
    private AudioManager audioManager = null;

    @ViewById(R.id.program_rbtThumnail)
    protected RadioButton rbtThumnail;

    @ViewById(R.id.program_rbtDetail)
    protected RadioButton rbtDetail;

    @ViewById(R.id.program_rbtComment)
    protected RadioButton rbtComment;

    @ViewById(R.id.program_sbVolume)
    protected SeekBar sbVolume;

    @ViewById(R.id.program_sbTime)
    protected SeekBar sbTime;

    @ViewById(R.id.program_btFavorite)
    protected Button btStar;

    @ViewById(R.id.program_btLike)
    protected Button btLike;

    @ViewById(R.id.program_btList)
    protected Button btList;

    @ViewById(R.id.program_btPlayOrStop)
    protected Button btPlayOrStop;

    @ViewById(R.id.program_tvTimeCur)
    protected TextView tvTimeCur;

    @ViewById(R.id.program_tvTimePlay)
    protected TextView tvTimePlay;

    @ViewById(R.id.program_btBack)
    protected Button btBack;

    @AfterViews()
    protected void afterViews(){
        initServicePlayMusic();
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

        getTimeOfRecordAndShow();

        sbTime.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                seekChange(v);
                return false;
            }
        });
    }
    @SeekBarProgressChange(R.id.program_sbVolume)
    protected void onProgressChangeOnSeekBar (SeekBar seekBar, int progress, boolean fromUser){
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
    }

    // This is event handler thumb moving event
    private void seekChange(View v)
    {
        if (mediaPlayer.isPlaying())
        {
            SeekBar sb = (SeekBar) v;
            mediaPlayer.seekTo(sb.getProgress());
        }
    }

    @Click({R.id.program_rbtThumnail,R.id.program_rbtDetail, R.id.program_rbtComment,
            R.id.program_btPlayOrStop, R.id.program_btBack})
    protected void someButtonClick(View view)
    {
        switch (view.getId())
        {
            case R.id.program_rbtThumnail:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ThumbnailFragment thumnailFragmentActivity = new ThumbnailFragment_();
                fragmentTransaction.replace(R.id.program_fl_generic, thumnailFragmentActivity);
                fragmentTransaction.commit();
                break;
            case R.id.program_rbtDetail:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                DetailFragment detailFragmentActivity = new DetailFragment_();
                transaction.replace(R.id.program_fl_generic, detailFragmentActivity);
                transaction.commit();
                break;
            case R.id.program_rbtComment:
                FragmentTransaction transactionComment = getFragmentManager().beginTransaction();
                Comment comment = new Comment_();
                transactionComment.replace(R.id.program_fl_generic, comment);
                transactionComment.commit();
                break;
            case R.id.program_btPlayOrStop:
                if (!mediaPlayer.isPlaying())
                {
                    btPlayOrStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.content_bt_play));
                    try
                    {
                        mediaPlayer.start();
                        startPlayProgressUpdater();
                    }
                    catch (IllegalStateException e)
                    {
                        mediaPlayer.pause();
                    }
                }
                else
                {
                    mediaPlayer.pause();
                    btPlayOrStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.content_bt_pause));
                }
                break;
            case R.id.program_btBack:
                getFragmentManager().popBackStack();
                break;
        }
    }
    public void startPlayProgressUpdater()
    {
        getTimeOfRecordAndShow();
        sbTime.setProgress(mediaPosition);
        sbTime.setMax(mediaDuration);
        if (mediaPlayer.isPlaying())
        {
            Runnable notification = new Runnable()
            {
                public void run()
                {
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
        else
        {
            mediaPlayer.pause();
            btPlayOrStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.content_bt_pause));
            if (mediaPosition == mediaDuration)
            {
                sbTime.setProgress(0);
            }
            else
            {
                sbTime.setProgress(mediaPosition);
            }
        }
    }

    private void getTimeOfRecordAndShow()
    {
        mediaDuration = mediaPlayer.getDuration();
        mediaPosition = mediaPlayer.getCurrentPosition();

        tvTimeCur.setText(DateTime.getTimeString(mediaPosition));
        tvTimePlay.setText(DateTime.getTimeString(mediaDuration));
    }

    private void initServicePlayMusic()
    {
        //Return the handle to a system-level service - 'AUDIO'.
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        sbVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        //Set the progress with current Media Volume
        sbVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.testsong_20_sec);
    }

}
