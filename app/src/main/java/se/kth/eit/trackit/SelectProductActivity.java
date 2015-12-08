package se.kth.eit.trackit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import se.kth.eit.trackit.view.ProductsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectProductActivity extends AppCompatActivity {

    private static final String USDA_API_KEY = "ARAvbfMmDAgomdTL0YkuVNzNRCbZehXyjkHZrTNL";
    public static final String CATEGORY_ID_EXTRA = "CategoryId";
    public static final String CATEGORY_NAME_EXTRA = "CategoryName";

    private List<String> productNames;
    private ProductsListAdapter adapter;
    private RecyclerView listView;
    private String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(CATEGORY_NAME_EXTRA));
        categoryId = getIntent().getStringExtra(CATEGORY_ID_EXTRA);
        setUpListView();
    }

    private void setUpListView() {
        listView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        productNames = new ArrayList<>();
        adapter = new ProductsListAdapter(this, productNames);
        listView.setAdapter(adapter);
        new GetProductsAsyncTask().execute();
    }

    private class GetProductsAsyncTask extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SelectProductActivity.this);
            progressDialog.setMessage("wait..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String url = "http://api.nal.usda.gov/ndb/search/?fg=" + categoryId + "&offset=" +
                    String.valueOf(productNames.size()) + "&api_key=" + USDA_API_KEY;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            return restTemplate.getForObject(url, String.class, "Android");
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            progressDialog.hide();
            try {
                JSONObject main = new JSONObject(json);
                JSONArray list = main.getJSONObject("list").getJSONArray("item");
                for (int i = 0; i < list.length(); i++) {
                    productNames.add(list.getJSONObject(i).getString("name"));
                }
                adapter.setProductNames(productNames);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
