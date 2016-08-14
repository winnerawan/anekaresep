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
import net.winnerawan.anekaresep.adapter.ListAyamAdapter;
import net.winnerawan.anekaresep.admobadapter.AdmobAdapterWrapper;
import net.winnerawan.anekaresep.config.AppConfig;
import net.winnerawan.anekaresep.config.AppController;
import net.winnerawan.anekaresep.helper.ExpandableHeightListView;
import net.winnerawan.anekaresep.model.Ayam;

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
public class FragmentAyam extends ListFragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private JSONObject obj;
    private ProgressDialog pDialog;
    private List<Ayam> listResepAyam = new ArrayList<Ayam>();
    private ListAyamAdapter adapter;
    ListView listViewResep;
    AdmobAdapterWrapper adapterWrapper;
    Timer updateAdsTimer;

    public FragmentAyam() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View viewAyam = inflater.inflate(R.layout.fragment_list, container, false);
        listViewResep = (ListView) viewAyam.findViewById(android.R.id.list);
        adapter = new ListAyamAdapter(this, listResepAyam);
        listViewResep.setAdapter(adapter);
        MobileAds.initialize(getActivity().getApplicationContext(), getString(R.string.appID));
        //********upcoming in the v1.2 !
        //test devices' ids
        String[] testDevicesIds = new String[]{getString(R.string.testDeviceID), AdRequest.DEVICE_ID_EMULATOR};
        //when you'll be ready for release please use anoth
        adapterWrapper = new AdmobAdapterWrapper(this.getContext(), getString(R.string.adUnitID1));
        adapterWrapper.setAdapter(adapter);
        adapterWrapper.setLimitOfAds(3);
        adapterWrapper.setNoOfDataBetweenAds(10);
        adapterWrapper.setFirstAdIndex(2);
        listViewResep.setAdapter(adapterWrapper);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest resepReq = new JsonArrayRequest(AppConfig.URL_GET_RESEP_AYAM, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());
                hidePDialog();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        obj = response.getJSONObject(i);
                        Ayam ayam = new Ayam();
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
                        listResepAyam.add(ayam);
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
        initUpdateAdsTimer();
        return viewAyam;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String title = ((TextView)v.findViewById(R.id.title)).getText().toString().trim();

        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra("title", title);
        startActivity(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
        if(updateAdsTimer!=null)
            updateAdsTimer.cancel();
        adapterWrapper.destroyAds();
    }
    /*
        * Could be omitted. It's only for updating an ad blocks in each 60 seconds without refreshing the list
         */
    private void initUpdateAdsTimer() {
        updateAdsTimer = new Timer();
        updateAdsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterWrapper.requestUpdateAd();
                    }
                });
            }
        }, 60*1000, 60*1000);
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
