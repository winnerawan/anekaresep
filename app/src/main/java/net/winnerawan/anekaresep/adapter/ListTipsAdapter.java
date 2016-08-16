package net.winnerawan.anekaresep.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.winnerawan.anekaresep.R;
import net.winnerawan.anekaresep.activity.TipsActivity;
import net.winnerawan.anekaresep.config.AppController;
import net.winnerawan.anekaresep.fragment.FragmentTips;
import net.winnerawan.anekaresep.helper.MyTextView;
import net.winnerawan.anekaresep.model.Ayam;
import net.winnerawan.anekaresep.model.Tips;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 8/15/16.
 */
public class ListTipsAdapter extends BaseAdapter {

    FragmentTips fragment;
    private LayoutInflater inflater;
    List<Tips> listTips = new ArrayList<Tips>();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ListTipsAdapter(FragmentTips fragment, List<Tips> listTips) {
        this.fragment=fragment;
        this.listTips=listTips;
    }

    @Override
    public int getCount() {
        return listTips.size();
    }

    @Override
    public Object getItem(int location) {
        return listTips.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.list_tips, null);
            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
            NetworkImageView imageHeader = (NetworkImageView) convertView.findViewById(R.id.image);
            RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratings);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            MyTextView cat = (MyTextView) convertView.findViewById(R.id.kategori);
            MyTextView time = (MyTextView) convertView.findViewById(R.id.timetext);
            ImageView clock = (ImageView) convertView.findViewById(R.id.timeimage);
            clock.setVisibility(View.GONE);
            time.setVisibility(View.GONE);

        Tips tips = listTips.get(position);

        imageHeader.setImageUrl(tips.getGambar(), imageLoader);
        title.setText(tips.getJudul());
        ratingBar.setRating(Integer.parseInt(tips.getRate()));
        cat.setText(tips.getKategori());

        return convertView;
    }
}
