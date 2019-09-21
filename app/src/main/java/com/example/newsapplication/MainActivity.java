package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManger;
    ArrayList<HashMap<String, String>> arrayListNews;
    RecyclerView.Adapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        mRecyclerView=findViewById(R.id.mRecyclerView);
        mLayoutManger=new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManger);

        
        callAPI();
    }

    private void callAPI() {


// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.myjson.com/bins/k40e5";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        parseAPIResponse(response);
                        Toast.makeText(MainActivity.this, "Response"+response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void parseAPIResponse(String response) {

        try {
            JSONObject objResponse = new JSONObject(response);
            JSONArray arrayHeadlines = objResponse.getJSONArray("headlines");
            arrayListNews = new ArrayList<>();

            for(int i=0; i < arrayHeadlines.length(); i++){
                JSONObject objItem = arrayHeadlines.getJSONObject(i);
                String title = objItem.getString("title");
                String imgUrl = objItem.getString("imgUrl");
                String description = objItem.getString("description");

                HashMap<String, String> map = new HashMap<>();
                map.put("title", title);
                map.put("url", imgUrl);
                map.put("detail", description);
                arrayListNews.add(map);


                mAdapter = new HomeListAdapter(MainActivity.this, arrayListNews);
                mRecyclerView.setAdapter(mAdapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
