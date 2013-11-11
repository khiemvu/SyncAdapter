package com.qsoft.onlinedio.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.onlinedio.R;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View viewer = (View) inflater.inflate(R.layout.program_layout, container, false);
        initServicePlayMusic();
        initComponent(viewer);
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

        getTimeOfRecordAndShow();

        rbtThumnail.setOnClickListener(onclickListener);
        rbtDetail.setOnClickListener(onclickListener);
        rbtComment.setOnClickListener(onclickListener);
        btPlayOrStop.setOnClickListener(onclickListener);
        btBack.setOnClickListener(onclickListener);

        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
        sbTime.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                seekChange(v);
                return false;
            }
        });

        return viewer;
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

    private final View.OnClickListener onclickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
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
//                    if (mediaPlayer.isPlaying()){
//                        mediaPlayer.pause();
//                    }
                    getFragmentManager().popBackStack();
                    break;
            }
        }
    };

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

        tvTimeCur.setText(getTimeString(mediaPosition));
        tvTimePlay.setText(getTimeString(mediaDuration));
    }

    private String getTimeString(long millis)
    {
        StringBuffer buf = new StringBuffer();

        //int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                //.append(String.format("%02d", hours))
                //.append(":")
                .append(String.format("%01d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    private void initComponent(View viewer)
    {
        sbVolume = (SeekBar) viewer.findViewById(R.id.program_sbVolume);
        sbTime = (SeekBar) viewer.findViewById(R.id.program_sbTime);
        btPlayOrStop = (Button) viewer.findViewById(R.id.program_btPlayOrStop);
        tvTimeCur = (TextView) viewer.findViewById(R.id.program_tvTimeCur);
        tvTimePlay = (TextView) viewer.findViewById(R.id.program_tvTimePlay);
        rbtDetail = (RadioButton) viewer.findViewById(R.id.program_rbtDetail);
        rbtThumnail = (RadioButton) viewer.findViewById(R.id.program_rbtThumnail);
        btStar = (Button) viewer.findViewById(R.id.program_btFavorite);
        btLike = (Button) viewer.findViewById(R.id.program_btLike);
        btList = (Button) viewer.findViewById(R.id.program_btList);
        rbtComment = (RadioButton) viewer.findViewById(R.id.program_rbtComment);
        btBack = (Button) viewer.findViewById(R.id.program_btBack);
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
