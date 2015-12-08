package se.kth.eit.trackit.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import se.kth.eit.trackit.R;
import se.kth.eit.trackit.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment with the list of product categories.
 */
public class ProductCategoriesFragment extends Fragment {

    private List<ProductCategory> categories;

    public ProductCategoriesFragment() {
        categories = new ArrayList<>();
        categories.add(new ProductCategory("3500", "American Indian/Alaska Native Foods",
                R.drawable.bakery));
        categories.add(new ProductCategory("1800", "Baked Products", R.drawable.bakery));
        categories.add(new ProductCategory("1300", "Beef Products", R.drawable.bakery));
        categories.add(new ProductCategory("0800", "Breakfast Cereals", R.drawable.bakery));
        categories.add(new ProductCategory("2000", "Cereal Grains and Pasta",
                R.drawable.bakery));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_categories, container, false);
        ((GridView) view.findViewById(R.id.categories_list_view))
                .setAdapter(new ProductCategoryAdapter(getActivity(), categories));
        return view;

    }
}
