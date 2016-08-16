package net.winnerawan.anekaresep.activity;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.winnerawan.anekaresep.R;
import net.winnerawan.anekaresep.config.AppController;
import net.winnerawan.anekaresep.helper.MyTextView;

public class DetailTipsActivity extends AppCompatActivity {

    AdView adView;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tips);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String gambar = bundle.getString("gambar");
        String tips = bundle.getString("tips");
        String rate = bundle.getString("rate");
        String kat = bundle.getString("kategori");
        String keterangan = bundle.getString("keterangan");

        adView = (AdView) findViewById(R.id.adTips);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        NetworkImageView image = (NetworkImageView) findViewById(R.id.image);
        MyTextView tvtitle = (MyTextView) findViewById(R.id.title);
        MyTextView tvket = (MyTextView) findViewById(R.id.ket);
        TextView tvintro = (TextView) findViewById(R.id.description);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratings);

        image.setImageUrl(gambar, imageLoader);
        tvtitle.setText(title);
        tvket.setText(tips);
        ratingBar.setRating(Integer.parseInt(rate));
        tvintro.setText(keterangan);

    }
}
