package net.winnerawan.anekaresep.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.winnerawan.anekaresep.R;
import net.winnerawan.anekaresep.config.AppController;
import net.winnerawan.anekaresep.helper.MyTextView;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    String title;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        String gambar = bundle.getString("gambar");
        String awal = bundle.getString("awal");
        String bahan = bundle.getString("bahan");
        String cara = bundle.getString("cara");
        String akhir = bundle.getString("akhir");
        String rate = bundle.getString("rate");

        adView = (AdView) findViewById(R.id.adDetail);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        MyTextView judul = (MyTextView) findViewById(R.id.title);
        judul.setText(title);
        NetworkImageView image = (NetworkImageView) findViewById(R.id.image);
        image.setImageUrl(gambar, imageLoader);
        TextView desc = (TextView) findViewById(R.id.description);
        desc.setText(awal);
        MyTextView tvbahan = (MyTextView) findViewById(R.id.bahan);
        tvbahan.setText(bahan);
        MyTextView tvcara = (MyTextView) findViewById(R.id.cara);
        tvcara.setText(cara);
        MyTextView tvakhir = (MyTextView) findViewById(R.id.ket);
        tvakhir.setText(akhir);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratings);
        ratingBar.setRating(Integer.parseInt(rate));




    }
}
