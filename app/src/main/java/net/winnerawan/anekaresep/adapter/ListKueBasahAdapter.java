package net.winnerawan.anekaresep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.winnerawan.anekaresep.R;
import net.winnerawan.anekaresep.config.AppController;
import net.winnerawan.anekaresep.fragment.FragmentKueBasah;
import net.winnerawan.anekaresep.helper.MyTextView;
import net.winnerawan.anekaresep.model.KueBasah;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 8/15/16.
 */
public class ListKueBasahAdapter extends BaseAdapter {

    FragmentKueBasah fragment;
    private LayoutInflater inflater;
    List<KueBasah> listResepKueBasah = new ArrayList<KueBasah>();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ListKueBasahAdapter(FragmentKueBasah fragment, List<KueBasah> listResepKueBasah){
        this.fragment=fragment;
        this.listResepKueBasah=listResepKueBasah;
    }

    @Override
    public int getCount() {
        return listResepKueBasah.size();
    }

    @Override
    public Object getItem(int location) {
        return listResepKueBasah.get(location);
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
            convertView = inflater.inflate(R.layout.list, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView imageHeader = (NetworkImageView) convertView.findViewById(R.id.image);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratings);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        MyTextView cat = (MyTextView) convertView.findViewById(R.id.kategori);
        MyTextView time = (MyTextView) convertView.findViewById(R.id.timetext);

        KueBasah kueBasah = listResepKueBasah.get(position);

        imageHeader.setImageUrl(kueBasah.getGambar(), imageLoader);
        title.setText(kueBasah.getJudul());
        ratingBar.setRating(Integer.parseInt(kueBasah.getRate()));
        cat.setText(kueBasah.getKategori());
        time.setText(kueBasah.getTime());

        return convertView;
    }

}
