package vee.apps.gitscommerce;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vee.apps.gitscommerce.api.request.GitsRequest;
import vee.apps.gitscommerce.api.response.Gits;

public class MainActivity extends AppCompatActivity implements GitsRequest.OnGitsRequestListener{

    @BindView(R.id.textView)
    TextView tvHello;

    @BindView(R.id.btnSort)
    Button btnSort;

    @BindView(R.id.btnFilter)
    Button btnFilter;

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private GitsRequest mGitsRequest;
    final static String SORT_STR = "sort";
    final static String FILTER_STR = "filter";
    final static String POPULARITY_STR = "   Popularity";
    final static String LOWtoHIGH_STR = "   Low Price - High Price";
    final static String HIGHtoLOW_STR = "   High Price - Low Price";
    String mSort="";
    String mFilter = "";

    private Gits mGitResponse;

    private GridLayoutManager staggeredGridLayoutManagerVertical;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    private List<String> itemsValueNow;
    private List<String> itemsValueTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gits Commerce");
        setTitleColor(Color.parseColor("#ffffff"));

        tvHello.setVisibility(View.GONE);

        staggeredGridLayoutManagerVertical = new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManagerVertical);

        itemsValueTemp = new ArrayList<>();
        itemsValueNow = new ArrayList<>();
        mGitsRequest = new GitsRequest(this);

        mSwipeRefreshLayout.setRefreshing(true);
        mGitsRequest.callApi("");

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(SORT_STR);

            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(FILTER_STR);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                itemsValueTemp.clear();
                myRecyclerViewAdapter.clearData();
                mSwipeRefreshLayout.setRefreshing(true);
                mGitsRequest.callApi("");
            }
        });
    }

    @Override
    public void onRequestGitsSuccess(Gits gitsResponse) {

        mGitResponse = gitsResponse;

        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this);
        recyclerView.setAdapter(myRecyclerViewAdapter);

        itemsValueTemp.clear();

        mSort = POPULARITY_STR;
        mFilter = "Semua";

        filterData(mFilter);
        sortData(mSort);

        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onRequestGitsFailure(String message) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public class IntegerComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    }

    public void sortData(String req){

        ArrayList<Integer> gitsInt = new ArrayList<Integer>();

        int h =0;

        switch (req){
            case "   Popularity":
                for(int i = 0; i<mGitResponse.getGitsdata().getGitsProductses().size();i++){

                    for(int z=0; z<itemsValueTemp.size();z++){

                        if(mGitResponse.getGitsdata().getGitsProductses().get(i).getId().equals(itemsValueTemp.get(z))){
                            gitsInt.add(h,Integer.parseInt(mGitResponse.getGitsdata().getGitsProductses().get(i).getRating()));
                            h++;
                            break;
                        }
                    }
                }
                Collections.sort(gitsInt,new IntegerComparator());
                break;

            case "   Low Price - High Price":
                for(int i = 0; i<mGitResponse.getGitsdata().getGitsProductses().size();i++){

                    for(int z=0; z<itemsValueTemp.size();z++){

                        if(mGitResponse.getGitsdata().getGitsProductses().get(i).getId().equals(itemsValueTemp.get(z))){
                            gitsInt.add(h,Integer.parseInt(mGitResponse.getGitsdata().getGitsProductses().get(i).getHarga()));
                            h++;
                            break;
                        }
                    }
                }
                Collections.sort(gitsInt,Collections.<Integer>reverseOrder(new IntegerComparator()));
                break;

            case "   High Price - Low Price":
                for(int i = 0; i<mGitResponse.getGitsdata().getGitsProductses().size();i++){

                    for(int z=0; z<itemsValueTemp.size();z++){

                        if(mGitResponse.getGitsdata().getGitsProductses().get(i).getId().equals(itemsValueTemp.get(z))){
                            gitsInt.add(h,Integer.parseInt(mGitResponse.getGitsdata().getGitsProductses().get(i).getHarga()));
                            h++;
                            break;
                        }
                    }
                }
                Collections.sort(gitsInt,new IntegerComparator());
                break;

            default:
                break;
        }

        myRecyclerViewAdapter.clearData();

        int k =0;

        if(req.equals(POPULARITY_STR)){

            for(int j = 0; j<gitsInt.size();j++){

                for(int i=0; i<mGitResponse.getGitsdata().getGitsProductses().size();i++){

                    if(mGitResponse.getGitsdata().getGitsProductses().get(i).getRating().equals(String.valueOf(gitsInt.get(j)))){

                        myRecyclerViewAdapter.add(
                                myRecyclerViewAdapter.getItemCount(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getNama(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getDiskon(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getHarga(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getRating(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getStok(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getId(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getUrlFoto(),
                                k);
                        k++;

                    }
                }
            }
        }else{

            for(int j = 0; j<gitsInt.size();j++){

                for(int i=0; i<mGitResponse.getGitsdata().getGitsProductses().size();i++){

                    if(mGitResponse.getGitsdata().getGitsProductses().get(i).getHarga().equals(String.valueOf(gitsInt.get(j)))){

                        myRecyclerViewAdapter.add(
                                myRecyclerViewAdapter.getItemCount(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getNama(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getDiskon(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getHarga(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getRating(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getStok(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getId(),
                                mGitResponse.getGitsdata().getGitsProductses().get(i).getUrlFoto(),
                                k);
                        k++;
                    }
                }
            }
        }
    }

    public void filterData(String req){

        req = req.replace(" ","");

        myRecyclerViewAdapter.clearData();
        itemsValueNow.clear();

        int j = 0;
        for(int i=0; i<mGitResponse.getGitsdata().getGitsProductses().size();i++){

            if(req.equals("Semua")){
                itemsValueNow.add(mGitResponse.getGitsdata().getGitsProductses().get(i).getId());
            }else{

                if(mGitResponse.getGitsdata().getGitsProductses().get(i).getJenisProduct().toString().equals(req)){
                    itemsValueNow.add(mGitResponse.getGitsdata().getGitsProductses().get(i).getId());
                }
            }
        }

        itemsValueTemp.clear();
        for(int i=0;i<itemsValueNow.size();i++){
            itemsValueTemp.add(itemsValueNow.get(i));
        }
    }

    public void showDialog(final String dlg){
        try{

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.boxdialog);
            dialog.setCancelable(true);

            RadioButton rd1 = (RadioButton)dialog.findViewById(R.id.radioButton);
            RadioButton rd2 = (RadioButton)dialog.findViewById(R.id.radioButton2);
            RadioButton rd3 = (RadioButton)dialog.findViewById(R.id.radioButton3);
            RadioButton rd4 = (RadioButton)dialog.findViewById(R.id.radioButton4);
            final RadioGroup rdgroup = (RadioGroup)dialog.findViewById(R.id.rdGroup);
            Button btnBack = (Button)dialog.findViewById(R.id.button5);
            Button btnOk = (Button)dialog.findViewById(R.id.button4);

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    RadioButton rdGet = (RadioButton)dialog.findViewById(rdgroup.getCheckedRadioButtonId());
                    String req = rdGet.getText().toString();

                    if(dlg.equals(SORT_STR)){
                        mSort = req;
                        dialog.dismiss();

                    }else if(dlg.equals(FILTER_STR)){
                        mFilter = req;
                        dialog.dismiss();
                    }

                    filterData(mFilter);
                    sortData(mSort);
                }
            });

            switch (dlg){
                case SORT_STR:
                    dialog.setTitle("Sort by");
                    rd1.setText("   Popularity");
                    rd2.setText("   Low Price - High Price");
                    rd3.setText("   High Price - Low Price");
                    rd4.setVisibility(View.GONE);

                    break;

                case FILTER_STR:
                    dialog.setTitle("Filter by");
                    rd1.setText("   Semua");
                    rd2.setText("   " + mGitResponse.getGitsdata().getGitsFilters().get(0).getJenis());
                    rd3.setText("   " +  mGitResponse.getGitsdata().getGitsFilters().get(1).getJenis());
                    rd4.setText("   " + mGitResponse.getGitsdata().getGitsFilters().get(2).getJenis());
                    rd4.setVisibility(View.VISIBLE);

                    break;

                default:
                    break;
            }
            dialog.show();
        }catch(Exception e){}
    }

}
