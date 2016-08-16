package net.winnerawan.anekaresep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import net.winnerawan.anekaresep.R;
import net.winnerawan.anekaresep.config.AppController;
import net.winnerawan.anekaresep.fragment.FragmentPuding;
import net.winnerawan.anekaresep.helper.MyTextView;
import net.winnerawan.anekaresep.model.Puding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 8/13/16.
 */
public class ListPudingAdapter extends BaseAdapter {

    FragmentPuding fragment;
    private LayoutInflater inflater;
    List<Puding> listResepPuding = new ArrayList<Puding>();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ListPudingAdapter(FragmentPuding fragment, List<Puding> listResepPuding) {
        this.fragment=fragment;
        this.listResepPuding=listResepPuding;
    }

    @Override
    public int getCount() {
        return listResepPuding.size();
    }

    @Override
    public Object getItem(int location) {
        return listResepPuding.get(location);
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

        Puding ayam = listResepPuding.get(position);

        imageHeader.setImageUrl(ayam.getGambar(), imageLoader);
        ratingBar.setRating(Integer.parseInt(ayam.getRate()));
        title.setText(ayam.getJudul());
        cat.setText(ayam.getKategori());
        time.setText(ayam.getTime());

        return convertView;
    }


}
