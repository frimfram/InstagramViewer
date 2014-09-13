package com.codepath.frimfram.instagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


public class PhotosActivity extends Activity {
	
	public static final String CLIENT_ID = "ea10d28b7efd45b08f4b2eee0375ba6f";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter photoAdapter;
	private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        
        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {			
			@Override
			public void onRefresh() {
				fetchPopularPhotos();				
			}
		});

        fetchPopularPhotos();
    }
    
    private void fetchPopularPhotos() {
    	photos = new ArrayList<InstagramPhoto>();
    	photoAdapter = new InstagramPhotosAdapter(this, photos);
    	
    	ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
    	lvPhotos.setAdapter(photoAdapter);
    	
    	String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
    	
    	//create the network client
    	AsyncHttpClient client = new AsyncHttpClient();
    	
    	//trigger the network request
    	client.get(popularUrl, new JsonHttpResponseHandler(){
    		//define success and failure call-backs
    		@Override
    		public void onSuccess(int statusCode, Header[] headers,
    				JSONObject response) {
    			JSONArray photosJSON = null;
    			try {
    				photos.clear();
    				photosJSON = response.getJSONArray("data");
    				ArrayList<InstagramPhoto> newPhotos = InstagramPhoto.fromJson(photosJSON);
    				photoAdapter.addAll(newPhotos);
    				photoAdapter.notifyDataSetChanged();
    			}catch(JSONException e) {
    				e.printStackTrace();
    			}
    			swipeContainer.setRefreshing(false);
    		}
    		@Override
    		public void onFailure(int statusCode, Header[] headers,
    				String responseString, Throwable throwable) {
    			// TODO Auto-generated method stub
    			super.onFailure(statusCode, headers, responseString, throwable);
    		}
    	});
    	
    	//handle the successful response (popular photo json)
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
