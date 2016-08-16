package net.winnerawan.anekaresep.fragment;

import android.support.v4.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
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

import net.winnerawan.anekaresep.R;
import net.winnerawan.anekaresep.activity.DetailActivity;
import net.winnerawan.anekaresep.activity.MainActivity;
import net.winnerawan.anekaresep.adapter.ListKueBasahAdapter;
import net.winnerawan.anekaresep.config.AppConfig;
import net.winnerawan.anekaresep.config.AppController;
import net.winnerawan.anekaresep.model.KueBasah;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 8/15/16.
 */
public class FragmentKueBasah extends ListFragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    ListKueBasahAdapter adapter;
    ListView listView;
    private JSONObject obj;
    private ProgressDialog pDialog;
    private List<KueBasah> listResepKue = new ArrayList<KueBasah>();

    public FragmentKueBasah(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View viewKueBasah = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) viewKueBasah.findViewById(android.R.id.list);
        adapter = new ListKueBasahAdapter(this, listResepKue);
        listView.setAdapter(adapter);
        listResepKue.clear();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest kueReq = new JsonArrayRequest(AppConfig.URL_GET_RESEP_KUEBASAH, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());
                hidePDialog();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        obj = response.getJSONObject(i);
                        KueBasah kueBasah = new KueBasah();
                        kueBasah.setId(obj.getString("id"));
                        kueBasah.setAwal(obj.getString("awal"));
                        kueBasah.setGambar(obj.getString("gambar"));
                        kueBasah.setJudul(obj.getString("judul"));
                        kueBasah.setBahan(obj.getString("bahan"));
                        kueBasah.setCara(obj.getString("cara"));
                        kueBasah.setKategori(obj.getString("kategori"));
                        kueBasah.setAkhir(obj.getString("akhir"));
                        kueBasah.setKeterangan(obj.getString("keterangan"));
                        kueBasah.setRate(obj.getString("rate"));
                        kueBasah.setTime(obj.getString("time"));
                        listResepKue.add(kueBasah);
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

        AppController.getInstance().addToRequestQueue(kueReq);
        return viewKueBasah;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String title = ((TextView)v.findViewById(R.id.title)).getText().toString().trim();
        KueBasah kueBasah = listResepKue.get(position);
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra("title", title);

        i.putExtra("gambar", kueBasah.getGambar());
        i.putExtra("awal", kueBasah.getAwal().replaceAll("</li>", "\n")
                .replaceAll("<(.*?)\\>"," "));
        i.putExtra("bahan", kueBasah.getBahan().replaceAll("</li>", "\n")
                .replaceAll("<(.*?)\\>"," "));
        i.putExtra("cara", kueBasah.getCara().replaceAll("</li>", "\n")
                .replaceAll("<(.*?)\\>"," "));
        i.putExtra("rate", kueBasah.getRate());
        i.putExtra("akhir", kueBasah.getAkhir().replaceAll("</li>", "\n")
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
