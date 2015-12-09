package se.kth.eit.trackit.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import se.kth.eit.trackit.R;
import se.kth.eit.trackit.SelectProductActivity;
import se.kth.eit.trackit.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment with the list of product categories.
 */
public class ProductCategoriesFragment extends Fragment {

    private List<ProductCategory> categories;
    private ProductCategoryAdapter adapter;

    public ProductCategoriesFragment() {
        categories = new ArrayList<>();
//        categories.add(new ProductCategory("3500", "American Indian/Alaska Native Foods",
//                R.drawable.bakery));
//        categories.add(new ProductCategory("1800", "Baked Products", R.drawable.bakery));
//        categories.add(new ProductCategory("1300", "Beef Products", R.drawable.bakery));
//        categories.add(new ProductCategory("0800", "Breakfast Cereals", R.drawable.bakery));
//        categories.add(new ProductCategory("2000", "Cereal Grains and Pasta",
//                R.drawable.bakery));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_categories, container, false);
        adapter = new ProductCategoryAdapter(getActivity(), categories);
        ((GridView) view.findViewById(R.id.categories_list_view)).setAdapter(adapter);
        new GetProductCategoriesAsyncTask().execute();
        return view;
    }

    private class GetProductCategoriesAsyncTask extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("wait..");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String url = "http://api.nal.usda"
                    + ".gov/ndb/list?format=json&lt=g&sort=ip&api_key=" +
                    SelectProductActivity.USDA_API_KEY;
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
                    JSONObject item = list.getJSONObject(i);
                    String name = item.getString("name");
                    int drawableId = getResources().getIdentifier("se.kth.eit"
                            + ".trackit:drawable/" + name.toLowerCase().replaceAll("\\/",
                                    "_").replaceAll(",", "").replaceAll(" ", "_"), null, null);
                    categories.add(new ProductCategory(item.getString("id"), name, drawableId));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
