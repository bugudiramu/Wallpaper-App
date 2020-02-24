package com.example.wallapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.wallapp.models.Constants;
import com.example.wallapp.models.WallPaper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration dividerItemDecoration;
    private List<WallPaper> wallPapers;
    private RequestQueue requestQueue;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Use Toolbar or Appbar (Action Bar is deprecated)

        // TODO Get all the fields from xml files
        wallPapers = new ArrayList<WallPaper>();
        requestQueue = Volley.newRequestQueue(this);
//        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        getWallData();
        // Onclick for recyclerView
    }


    public void getWallData() {
        // TODO OR
        /*
        final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Loading...");
    progressDialog.show();
        */
        progressBar = new ProgressBar(this);
        progressBar.setVisibility(View.VISIBLE);
        String wallUrl = Constants.BASE_URL + "client_id=" + Constants.API_KEY;
        final String url = "";
        final JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, wallUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Log.v("Response", response.toString());
                // hide the progress dialog
//                progressBar.setVisibility(View.GONE);

                //  TODO GET all the fields
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONObject urlObj = jsonObject.getJSONObject("urls");
                        JSONObject userObj = jsonObject.getJSONObject("user");
                        String smallUrl = urlObj.getString("small");
                        String regularUrl = urlObj.getString("regular");
                        String avatarName = userObj.getString("name");
                        JSONObject profileImgObject = userObj.getJSONObject("profile_image");
                        String avatarImg = profileImgObject.getString("large");

                        // TODO Change the smallUrl to regularUrl inorder to get the real image
                        wallPapers.add(new WallPaper(regularUrl, avatarImg, avatarName));
                        adapter = new WallPaperAdapter(getApplicationContext(), wallPapers);
                        recyclerView.setAdapter(adapter);
//                        progressBar.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Response", error.toString());
                // hide the progress dialog
//                progressBar.setVisibility(View.GONE);
            }
        });
        requestQueue.add(arrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.favorite_setting) {
            Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}