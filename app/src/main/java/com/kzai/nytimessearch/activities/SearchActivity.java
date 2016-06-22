package com.kzai.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.kzai.nytimessearch.Article;
import com.kzai.nytimessearch.ArticleArrayAdapter;
import com.kzai.nytimessearch.EndlessScrollListener;
import com.kzai.nytimessearch.NewsFilterDialogFragment;
import com.kzai.nytimessearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements NewsFilterDialogFragment.OnCompleteListener {

    EditText etQuery;
    GridView gvResults;
    Button btnSearch;

    Calendar calendar;
    String sort;

    String searchQuery;
    int pageNum;

    FragmentManager fm;
    NewsFilterDialogFragment newsFilterDialogFragment;
    public static String beginDate;

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();
    }

    public void setupViews() {
        gvResults = (GridView) findViewById(R.id.gvResults);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);

        pageNum = 0;
        setupGridViewListeners();

        fm = getSupportFragmentManager();
        newsFilterDialogFragment = NewsFilterDialogFragment.newInstance("Advanced Search");

    }

    private void setupGridViewListeners() {
        // hook up listener for grid click
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create an intent to display the article
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                // get the article to display
                Article article = articles.get(position);
                // pass in that article to intent
                i.putExtra("article", article);
                // launch activity
                startActivity(i);
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                //pageNum++;
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }

            @Override
            public int getFooterViewType() {
                return -1;
            }
        });
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "e273cb22aac54a71820c5acac368a6bf");
        params.put("page", offset);
        params.put("q", searchQuery);

        if (calendar != null) {
            // (2) create a date "formatter" (the date format we want)
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

            // (3) create a new String using the date format we want
            String chosenDateText = formatter.format(calendar.getTime());
            params.put("begin_date", chosenDateText);
        }

        if (sort != null) {
            params.put("sort", sort);
        }

        if (categories != null) {
            String combo = "";
            for (String news_desk_value: categories) {
                combo += news_desk_value + ",";
            }
            params.put("news_desk", combo.substring(combo.length() - 1));
        }

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    //adapter.clear();
                    adapter.addAll(Article.fromJSONArray(articleJsonResults));
                    Log.d("DEBUG", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                pageNum = 0;
                searchQuery = query;
                AsyncHttpClient client = new AsyncHttpClient();
                String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

                RequestParams params = new RequestParams();
                params.put("api-key", "e273cb22aac54a71820c5acac368a6bf");
                params.put("page", 0);

                params.put("q", query);

                if (calendar != null) {
                    // (2) create a date "formatter" (the date format we want)
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

                    // (3) create a new String using the date format we want
                    String chosenDateText = formatter.format(calendar.getTime());
                    params.put("begin_date", chosenDateText);
                }

                if (sort != null) {
                    params.put("sort", sort);
                }

                if (categories != null) {
                    String combo = "";
                    for (String news_desk_value: categories) {
                        combo += news_desk_value + ",";
                    }
                    combo = combo.substring(0, combo.length() - 1);
                    params.put("news_desk", combo);
                }

                client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("DEBUG", response.toString());
                        JSONArray articleJsonResults = null;

                        try {
                            articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                            adapter.clear();
                            adapter.addAll(Article.fromJSONArray(articleJsonResults));
                            Log.d("DEBUG", articles.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //searchView.setOnSuggestionListener();

        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            newsFilterDialogFragment.show(fm, "fragment_news_filter");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onComplete(Calendar calendar, String sort, ArrayList<String> categories) {
        // After the dialog fra
        // gment completes, it calls this callback.
        // use the string here
        if (calendar != null) {
            this.calendar = calendar;
        }

        if (!sort.isEmpty()) {
            this.sort = sort;
        }

        if (!categories.isEmpty()) {
            this.categories = categories;
        }
    }

    public void onCheckboxClicked(View view) {
        newsFilterDialogFragment.onCheckboxClicked(view);
        // Is the view now checked?
        /*
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked

        } */
    }
}
