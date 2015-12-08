package se.kth.eit.trackit.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import se.kth.eit.trackit.R;
import se.kth.eit.trackit.model.ProductCategory;

import java.util.List;

/**
 * List adapter for product categories.
 */
public class ProductCategoryAdapter extends BaseAdapter {

    private Context context;

    private List<ProductCategory> categories;

    public ProductCategoryAdapter(Context context, List<ProductCategory> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_list_item, viewGroup, false);
        }
        ((ImageView) view.findViewById(R.id.category_image))
                .setImageResource(categories.get(i).getImageId());
        ((TextView) view.findViewById(R.id.category_name_label)).setText(categories.get(i).getName());
        return view;
    }
}
