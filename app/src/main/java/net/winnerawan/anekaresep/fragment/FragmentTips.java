package net.winnerawan.anekaresep.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
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
import net.winnerawan.anekaresep.activity.DetailTipsActivity;
import net.winnerawan.anekaresep.activity.TipsActivity;
import net.winnerawan.anekaresep.adapter.ListAyamAdapter;
import net.winnerawan.anekaresep.adapter.ListTipsAdapter;
import net.winnerawan.anekaresep.config.AppConfig;
import net.winnerawan.anekaresep.config.AppController;
import net.winnerawan.anekaresep.model.Ayam;
import net.winnerawan.anekaresep.model.Tips;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerawan on 8/15/16.
 */
public class FragmentTips extends ListFragment {

    private static final String TAG = TipsActivity.class.getSimpleName();
    private JSONObject obj;
    private ProgressDialog pDialog;
    private List<Tips> listTips = new ArrayList<Tips>();
    private ListTipsAdapter adapter;
    ListView listView;

    public FragmentTips() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tips, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);
        adapter = new ListTipsAdapter(this, listTips);
        listTips.clear();
        if(adapter!=null) {
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest tipsReq = new JsonArrayRequest(AppConfig.URL_GET_TIPS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());
                hidePDialog();

                for (int i = 0; i < response.length(); i++) {
                    try{
                        obj = response.getJSONObject(i);
                        Tips tips = new Tips();
                        tips.setId(obj.getString("id"));
                        tips.setGambar(obj.getString("gambar"));
                        tips.setJudul(obj.getString("judul"));
                        tips.setKategori(obj.getString("kategori"));
                        tips.setRate(obj.getString("rate"));
                        tips.setTips(obj.getString("tips"));
                        tips.setKeterangan(obj.getString("keterangan"));
                        listTips.add(tips);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                VolleyLog.e("Error: ", error.toString());
                hidePDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(tipsReq);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String title = ((TextView)v.findViewById(R.id.title)).getText().toString().trim();
        Tips tips = listTips.get(position);
        Intent i = new Intent(getActivity(), DetailTipsActivity.class);
        i.putExtra("title", title);

        i.putExtra("gambar", tips.getGambar());
        i.putExtra("kategori", tips.getKategori());
        i.putExtra("tips", tips.getTips().replaceAll("</li>", "\n")
                .replaceAll("<(.*?)\\>"," "));
        i.putExtra("rate", tips.getRate());
        i.putExtra("keterangan", tips.getKeterangan().replaceAll("</li>", "\n")
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
