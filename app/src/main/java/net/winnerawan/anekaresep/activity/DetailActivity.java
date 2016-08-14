package net.winnerawan.anekaresep.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.winnerawan.anekaresep.R;
import net.winnerawan.anekaresep.helper.MyTextView;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");

        MyTextView judul = (MyTextView) findViewById(R.id.title);
        judul.setText(title);


    }
}
