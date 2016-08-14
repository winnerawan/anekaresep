package net.winnerawan.anekaresep.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.winnerawan.anekaresep.R;

/**
 * Created by winnerawan on 8/14/16.
 */
public class FragmentDetail extends Fragment {

    public FragmentDetail(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View viewDetail = inflater.inflate(R.layout.fragment_detail, container, false);

        return viewDetail;
    }
}
