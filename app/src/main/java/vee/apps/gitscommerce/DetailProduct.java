package vee.apps.gitscommerce;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import vee.apps.gitscommerce.api.request.GitsDetailRequest;
import vee.apps.gitscommerce.api.response.Detail;

public class DetailProduct extends AppCompatActivity implements GitsDetailRequest.OnGitsDetailRequestListener{

    @BindView(R.id.imageView2)
    ImageView DetailImage;

    @BindView(R.id.textView6)
    TextView nama;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.textView7)
    TextView hargaBaru;

    @BindView(R.id.textView8)
    TextView hargaLama;

    @BindView(R.id.textView9)
    TextView stok;

    @BindView(R.id.textView5)
    TextView diskon;

    @BindView(R.id.button)
    Button deskripsi;

    @BindView(R.id.textView10)
    TextView isiDeskripsi;

    @BindView(R.id.button2)
    Button spesifikasi;

    @BindView(R.id.textView11)
    TextView isiSpesifikasi;

    @BindView(R.id.button3)
    Button sizeAvail;

    @BindView(R.id.textView12)
    TextView isiSize;

    String getID;
    String getRating;
    String getStok;
    String getUrl;
    String getNama;

    private GitsDetailRequest mGitsRequest;

    //private Detail mGitResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);

        AddBackToolbar();

        runAwal();

        mGitsRequest = new GitsDetailRequest(this);

        mGitsRequest.callApi(getID);

        deskripsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isiDeskripsi.isShown()){
                    isiDeskripsi.setVisibility(View.GONE);
                    changeArrowDownButton(deskripsi);
                }else{
                    isiDeskripsi.setVisibility(View.VISIBLE);
                    changeArrowUpButton(deskripsi);
                }
            }
        });

        spesifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isiSpesifikasi.isShown()){
                    isiSpesifikasi.setVisibility(View.GONE);
                    changeArrowDownButton(spesifikasi);
                }else{
                    isiSpesifikasi.setVisibility(View.VISIBLE);
                    changeArrowUpButton(spesifikasi);
                }
            }
        });

        sizeAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isiSize.isShown()){
                    isiSize.setVisibility(View.GONE);
                    changeArrowDownButton(sizeAvail);
                }else{
                    isiSize.setVisibility(View.VISIBLE);
                    changeArrowUpButton(sizeAvail);
                }
            }
        });
    }

    @Override
    public void onRequestGitsDetailSuccess(Detail gitsResponse) {

        nama.setText(gitsResponse.getDetailData().getNama());
        ratingBar.setRating(Float.parseFloat(getRating));
        hargaBaru.setText("Rp " +priceAfterDisc(gitsResponse.getDetailData().getHarga(),gitsResponse.getDetailData().getDiskon()));

        if(!gitsResponse.getDetailData().getDiskon().equals("0")){
            hargaLama.setText("Rp " + toRupiahFormat(gitsResponse.getDetailData().getHarga()));
        }else{
            hargaLama.setVisibility(View.GONE);
        }

        stok.setText("Stock:" + getStok);
        diskon.setText("Discount " + gitsResponse.getDetailData().getDiskon() + "%");
        isiDeskripsi.setText(gitsResponse.getDetailData().getDeskripsi());

        Picasso.with(this).load(getUrl)
                .fit()
                .centerCrop()
                .into(DetailImage);

        if(gitsResponse.getDetailData().getSpesifikasi()!=null){
            spesifikasi.setVisibility(View.VISIBLE);
            isiSpesifikasi.setText(gitsResponse.getDetailData().getSpesifikasi());
        }

        try{
            if(gitsResponse.getDetailData().getListUkuran().size()>0){
                sizeAvail.setVisibility(View.VISIBLE);
                String ukuran = "";

                for(int i=0;i<gitsResponse.getDetailData().getListUkuran().size();i++){
                    if(i==0){
                        ukuran = gitsResponse.getDetailData().getListUkuran().get(i).getUkuranAvailable();
                    }else{
                        ukuran +=  ", " +gitsResponse.getDetailData().getListUkuran().get(i).getUkuranAvailable();
                    }
                }
                isiSize.setText(ukuran);
            }

        }catch (Exception e){
        }

    }

    @Override
    public void onRequestGitsDetailFailure(String message) {
        isiDeskripsi.setText(message);
    }

    private void runAwal(){

        getID = getIntent().getStringExtra("id");
        getRating = getIntent().getStringExtra("rating");
        getStok = getIntent().getStringExtra("stok");
        getUrl = getIntent().getStringExtra("url");
        getNama = getIntent().getStringExtra("nama");

        getSupportActionBar().setTitle(getNama);

        hargaLama.setPaintFlags(hargaLama.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        spesifikasi.setVisibility(View.GONE);
        isiSpesifikasi.setVisibility(View.GONE);
        sizeAvail.setVisibility(View.GONE);
        isiSize.setVisibility(View.GONE);

        changeArrowUpButton(deskripsi);
        changeArrowDownButton(spesifikasi);
        changeArrowDownButton(sizeAvail);

        nama.setText(getNama);
        ratingBar.setRating(Float.parseFloat(getRating));
        stok.setText("Stock:" + getStok);
        Picasso.with(this).load(getUrl)
                .fit()
                .centerCrop()
                .into(DetailImage);

    }

    private String toRupiahFormat(String nominal) {
        NumberFormat rupiahFormat = null;
        String rupiah = "";

        rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        rupiah = rupiahFormat.format(Double.parseDouble(nominal)) +",00";

        return rupiah;
    }

    private String priceAfterDisc(String price, String disc){
        String result = "";

        int harga = Integer.parseInt(price);
        int diskon = Integer.parseInt(disc);
        double abisDisc = harga - (harga * diskon/100);

        result = toRupiahFormat(String.valueOf(abisDisc));

        return result;
    }

    private void AddBackToolbar() {
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back, null);

        upArrow.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void changeArrowUpButton(Button btn){
        btn.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.ic_keyboard_arrow_up_white_24dp, 0);
    }
    private void changeArrowDownButton(Button btn){
        btn.setCompoundDrawablesWithIntrinsicBounds( 0, 0,R.drawable.ic_keyboard_arrow_down_white_24dp, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
