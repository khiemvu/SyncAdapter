package com.qsoft.onlinedio.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qsoft.onlinedio.R;

/**
 * User: khiemvx
 * Date: 10/17/13
 */
public class ThumbnailFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.program_thumbnail_fragment, null);
    }
}
