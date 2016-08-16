package net.winnerawan.anekaresep.fragment;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import net.winnerawan.anekaresep.R;
import net.winnerawan.anekaresep.activity.DetailActivity;
import net.winnerawan.anekaresep.activity.MainActivity;
import net.winnerawan.anekaresep.adapter.ListPudingAdapter;
import net.winnerawan.anekaresep.config.AppConfig;
import net.winnerawan.anekaresep.config.AppController;
import net.winnerawan.anekaresep.helper.ExpandableHeightListView;
import net.winnerawan.anekaresep.model.Puding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by winnerawan on 8/13/16.
 */
public class FragmentPuding extends ListFragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private JSONObject obj;
    private ProgressDialog pDialog;
    private List<Puding> listResepPuding = new ArrayList<Puding>();
    private ListPudingAdapter adapter;
    ListView listViewResep;

    public FragmentPuding() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View viewPuding = inflater.inflate(R.layout.fragment_list, container, false);
        listViewResep = (ListView) viewPuding.findViewById(android.R.id.list);
        adapter = new ListPudingAdapter(this, listResepPuding);
        listViewResep.setAdapter(adapter);
        listResepPuding.clear();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonArrayRequest resepReq = new JsonArrayRequest(AppConfig.URL_GET_RESEP_PUDING, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());
                hidePDialog();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        obj = response.getJSONObject(i);
                        Puding ayam = new Puding();
                        ayam.setId(obj.getString("id"));
                        ayam.setGambar(obj.getString("gambar"));
                        ayam.setJudul(obj.getString("judul"));
                        ayam.setAwal(obj.getString("awal"));
                        ayam.setBahan(obj.getString("bahan"));
                        ayam.setCara(obj.getString("cara"));
                        ayam.setKeterangan(obj.getString("keterangan"));
                        ayam.setAkhir(obj.getString("akhir"));
                        ayam.setTime(obj.getString("time"));
                        ayam.setRate(obj.getString("rate"));
                        ayam.setKategori(obj.getString("kategori"));
                        //String image = ayam.getGambar();
                        listResepPuding.add(ayam);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                VolleyLog.e("Error: ", error.toString());
                hidePDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(resepReq);
        return viewPuding;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String title = ((TextView)v.findViewById(R.id.title)).getText().toString().trim();
        Puding ay = listResepPuding.get(position);
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra("title", title);

        i.putExtra("gambar", ay.getGambar());
        i.putExtra("awal", ay.getAwal().replaceAll("</li>", "\n")
                .replaceAll("<(.*?)\\>"," "));
        i.putExtra("bahan", ay.getBahan().replaceAll("</li>", "\n")
                .replaceAll("<(.*?)\\>"," "));
        i.putExtra("cara", ay.getCara().replaceAll("</li>", "\n")
                .replaceAll("<(.*?)\\>"," "));
        i.putExtra("rate", ay.getRate());
        i.putExtra("akhir", ay.getAkhir().replaceAll("</li>", "\n")
                .replaceAll("<(.*?)\\>"," "));
        startActivity(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
