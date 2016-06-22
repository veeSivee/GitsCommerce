package vee.apps.gitscommerce;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by binaryvi on 12/05/2016.
 */
public class MyRecyclerViewAdapter extends
        RecyclerView.Adapter<MyRecyclerViewAdapter.ItemHolder>  {

    private List<String> itemsName;
    private List<String> itemsDiskon;
    private List<String> itemsHarga;
    private List<String> itemsUrl;
    private List<Integer> itemsValue;
    private List<String> itemsRating;
    private List<String> itemsStok;
    private List<String> itemsID;
    private LayoutInflater layoutInflater;
    private Context context;
    public int dataKe;

    OnItemClickListener mItemClickListener;

    public MyRecyclerViewAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        itemsName = new ArrayList<String>();
        itemsDiskon = new ArrayList<>();
        itemsHarga = new ArrayList<>();
        itemsUrl = new ArrayList<>();
        itemsValue = new ArrayList<Integer>();
        itemsRating = new ArrayList<>();
        itemsStok = new ArrayList<>();
        itemsID = new ArrayList<>();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView itemCardView = (CardView)layoutInflater.inflate(R.layout.cardview, parent, false);
        return new ItemHolder(itemCardView, this);
        //return null;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int i) {
        holder.setItemName(itemsName.get(i));
        int value = itemsValue.get(i);
        holder.setItemValue(String.valueOf(value));

        holder.setItemDiskon(itemsDiskon.get(i));
        holder.setItemHarga(itemsHarga.get(i));
        holder.setImageView(itemsUrl.get(i));

    }

    @Override
    public int getItemCount() {
        return itemsName.size();
    }

    public int getDataKe(){
        return  dataKe;
    }

    public void add(int location, String iName,String diskon,String harga,String rating,String stok,String id, String url, int iValue){
        itemsName.add(location, iName);
        itemsValue.add(location, iValue);
        itemsDiskon.add(location,diskon);
        itemsHarga.add(location,harga);
        itemsRating.add(location,rating);
        itemsStok.add(location,stok);
        itemsID.add(location,id);
        itemsUrl.add(location,url);
        notifyItemInserted(location);
    }

    public void remove(int location){
        if(location >= itemsName.size())
            return;

        itemsName.remove(location);
        itemsDiskon.remove(location);
        itemsHarga.remove(location);
        itemsValue.remove(location);
        itemsRating.remove(location);
        itemsID.remove(location);
        itemsStok.remove(location);
        itemsUrl.remove(location);
        notifyItemRemoved(location);
    }

    public void clearData() {

        itemsName.clear();
        itemsDiskon.clear();
        itemsHarga.clear();
        itemsValue.clear();
        itemsRating.clear();
        itemsID.clear();
        itemsStok.clear();
        itemsUrl.clear();
        notifyDataSetChanged();
    }


    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private MyRecyclerViewAdapter parent;
        private CardView cardView;
        TextView textItemName;
        TextView textItemDiskon;
        TextView textItemHarga;
        TextView textItemValue;
        ImageView imageView;

        public ItemHolder(CardView cView, MyRecyclerViewAdapter parent) {
            super(cView);
            cardView = cView;
            this.parent = parent;
            textItemName = (TextView) cardView.findViewById(R.id.textView2);
            textItemValue = (TextView) cardView.findViewById(R.id.textView4);
            textItemDiskon = (TextView) cardView.findViewById(R.id.textView3);
            textItemHarga = (TextView) cardView.findViewById(R.id.card_title);
            imageView = (ImageView) cardView.findViewById(R.id.imageView);

            cView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataKe = Integer.valueOf(getItemValue());

                    Intent intent = new Intent(context, DetailProduct.class);
                    intent.putExtra("id", itemsID.get(dataKe).toString());
                    intent.putExtra("rating",itemsRating.get(dataKe).toString());
                    intent.putExtra("stok",itemsStok.get(dataKe).toString());
                    intent.putExtra("url",itemsUrl.get(dataKe).toString());
                    intent.putExtra("nama",itemsName.get(dataKe).toString());
                    context.startActivity(intent);

                }
            });
        }

        public void setItemName(CharSequence name){
            textItemName.setText(name);
        }

        public CharSequence getItemName(){
            return textItemName.getText();
        }

        public void setItemValue(CharSequence val){
            textItemValue.setText(val);
        }

        public String getItemValue(){
            return textItemValue.getText().toString();
        }

        public TextView getTextItemDiskon() {
            return textItemDiskon;
        }

        public void setItemDiskon(CharSequence val) {
            textItemDiskon.setText( "Disc " +val + "%");
        }

        public TextView getTextItemHarga() {
            return textItemHarga;
        }

        public void setItemHarga(CharSequence val) {
            textItemHarga.setText("Rp " + toRupiahFormat(val.toString()));
        }

        public void setImageView(String drawable){
            Picasso.with(context).load(drawable)
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                dataKe = Integer.valueOf(getItemValue());
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    public String toRupiahFormat(String nominal) {
        NumberFormat rupiahFormat = null;
        String rupiah = "";

        rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        rupiah = rupiahFormat.format(Double.parseDouble(nominal)) +",00";


        return rupiah;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
