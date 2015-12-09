package se.kth.eit.trackit.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import se.kth.eit.trackit.R;
import se.kth.eit.trackit.SelectProductActivity;

import java.util.List;

/**
 * Created by laure on 08.12.15.
 */
public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {

    private List<String> productNames;
    private Context context;

    public ProductsListAdapter(Context context, List<String> productNames) {
        this.context = context;
        this.productNames = productNames;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String productName = productNames.get(position);
        holder.textView.setText(productName);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SelectProductActivity) context).returnProduct(productName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productNames.size();
    }

    public void setProductNames(List<String> names) {
        this.productNames = names;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }
}
