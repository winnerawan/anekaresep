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
import net.winnerawan.anekaresep.fragment.FragmentAyam;
import net.winnerawan.anekaresep.helper.MyTextView;
import net.winnerawan.anekaresep.model.Ayam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 8/13/16.
 */
public class ListAyamAdapter extends BaseAdapter {

    FragmentAyam fragment;
    private LayoutInflater inflater;
    List<Ayam> listResepAyam = new ArrayList<Ayam>();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ListAyamAdapter(FragmentAyam fragment, List<Ayam> listResepAyam) {
        this.fragment=fragment;
        this.listResepAyam=listResepAyam;
    }

    @Override
    public int getCount() {
        return listResepAyam.size();
    }

    @Override
    public Object getItem(int location) {
        return listResepAyam.get(location);
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

        Ayam ayam = listResepAyam.get(position);

        imageHeader.setImageUrl(ayam.getGambar(), imageLoader);
        ratingBar.setRating(Integer.parseInt(ayam.getRate()));
        title.setText(ayam.getJudul());
        cat.setText(ayam.getKategori());
        time.setText(ayam.getTime());

        return convertView;
    }


}
