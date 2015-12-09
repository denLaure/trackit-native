package se.kth.eit.trackit.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import se.kth.eit.trackit.AddMealActivity;
import se.kth.eit.trackit.R;
import se.kth.eit.trackit.SelectProductActivity;
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
        final ProductCategory currentCategory = categories.get(i);
        ((ImageView) view.findViewById(R.id.category_image))
                .setImageResource(currentCategory.getImageId());
        ((TextView) view.findViewById(R.id.category_name_label))
                .setText(currentCategory.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectProductActivity.class);
                intent.putExtra(SelectProductActivity.CATEGORY_ID_EXTRA,
                        currentCategory.getId());
                intent.putExtra(SelectProductActivity.CATEGORY_NAME_EXTRA,
                        currentCategory.getName());
                ((Activity) context).startActivityForResult(intent,
                        AddMealActivity.REQUEST_ADD_FOOD);
            }
        });
        return view;
    }
}
