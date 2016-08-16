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
import net.winnerawan.anekaresep.adapter.ListKueKeringAdapter;
import net.winnerawan.anekaresep.config.AppConfig;
import net.winnerawan.anekaresep.config.AppController;
import net.winnerawan.anekaresep.model.KueKering;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 8/15/16.
 */
public class FragmentKueKering extends ListFragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    ListKueKeringAdapter adapter;
    ListView listView;
    private JSONObject obj;
    private ProgressDialog pDialog;
    private List<KueKering> listResepKue = new ArrayList<KueKering>();

    public FragmentKueKering(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View viewKueKering = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) viewKueKering.findViewById(android.R.id.list);
        adapter = new ListKueKeringAdapter(this, listResepKue);
        listView.setAdapter(adapter);
        listResepKue.clear();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest kueReq = new JsonArrayRequest(AppConfig.URL_GET_RESEP_KUEKERING, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());
                hidePDialog();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        obj = response.getJSONObject(i);
                        KueKering kueKering = new KueKering();
                        kueKering.setId(obj.getString("id"));
                        kueKering.setAwal(obj.getString("awal"));
                        kueKering.setGambar(obj.getString("gambar"));
                        kueKering.setJudul(obj.getString("judul"));
                        kueKering.setBahan(obj.getString("bahan"));
                        kueKering.setCara(obj.getString("cara"));
                        kueKering.setKategori(obj.getString("kategori"));
                        kueKering.setAkhir(obj.getString("akhir"));
                        kueKering.setKeterangan(obj.getString("keterangan"));
                        kueKering.setRate(obj.getString("rate"));
                        kueKering.setTime(obj.getString("time"));
                        listResepKue.add(kueKering);
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
        return viewKueKering;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String title = ((TextView)v.findViewById(R.id.title)).getText().toString().trim();
        KueKering kueKering = listResepKue.get(position);
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra("title", title);

        i.putExtra("gambar", kueKering.getGambar());
        i.putExtra("awal", kueKering.getAwal().replaceAll("</li>", "\n")
                .replaceAll("<(.*?)\\>"," "));
        i.putExtra("bahan", kueKering.getBahan().replaceAll("</li>", "\n")
                .replaceAll("<(.*?)\\>"," "));
        i.putExtra("cara", kueKering.getCara().replaceAll("</li>", "\n")
                .replaceAll("<(.*?)\\>"," "));
        i.putExtra("rate", kueKering.getRate());
        i.putExtra("akhir", kueKering.getAkhir().replaceAll("</li>", "\n")
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
