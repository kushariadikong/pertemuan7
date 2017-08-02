package com.example.optimus.pertemuan7;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.optimus.pertemuan7.api.TestingAPI;
import com.example.optimus.pertemuan7.api.model.Repo;
import com.example.optimus.pertemuan7.api.services.TestingService;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private LoadMoreListView lvItem;
    private SwipeRefreshLayout swipeMain;
    private LinkedList<String> list;
    private ArrayAdapter<String> adapter;
    private List<Repo> lstRepo;
    private int MaxPage = 10;
    private int currentPage = 0;

    private static int nRefreshAdded = 6;
    private static int nLoadAdded = 3;
    private static int nDefaultPage = 18;

    private int nItemCount = 0;

    ArrayList<String> arrList = new ArrayList<>();

    String[] androidVersion = new String[]{
            "Not Apple", "Not Blackberry",
            "Cupcake", "Donut",
            "Eclair", "Froyo", "Gingerbread",
            "Honeycomb", "Ice cream sandwich",
            "Jelly Bean", "Kitkat", "Lollipop",
            "Marshmellow", "Nano Nano", "Oreo",
            "Paprika", "Queue", "Radio"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItem = (LoadMoreListView)findViewById(R.id.lv_item);
        swipeMain = (SwipeRefreshLayout)findViewById(R.id.swipe_main);
        list = new LinkedList<>();

        populateDefaultData();
//
//listener untuk loadmore
        lvItem.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (currentPage < MaxPage) {
                    new FakeLoadmoreAsync().execute();
                } else {
                    lvItem.onLoadMoreComplete();
                }
            }
        });

        TestingService testingService = TestingAPI.getClient().create(TestingService.class);
        Call<List<Repo>> call = testingService.listRepos("kushariadikong");
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repos = response.body();
// for (Repo repo:
// repos) {
// list.add(repo.getName());
// }
// nItemCount += repos.size();
// adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,
// android.R.id.text1, list);
// lvItem.setAdapter(adapter);
// System.out.println("success " + repos.size());
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                System.out.println("failure");
            }
        });


        swipeMain.setOnRefreshListener(this);
    }

    private void populateDefaultData(){
        for (int i = 0; i < androidVersion.length; i++){
            list.add(androidVersion[i]);
        }
        nItemCount += androidVersion.length;

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,
                android.R.id.text1, list);
        lvItem.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        new FakePullRefreshAsync().execute();
    }

    //Background proses palsu untuk melakukan proses delay dan append data di bagian bawah list
    private class FakeLoadmoreAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(2000);
            }catch (Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            populateLoadmoreData();
            currentPage +=1;
        }
    }

    private void populateLoadmoreData() {
        int position;
        for (int i = 0; i < nLoadAdded; i++) {
            position = i + nItemCount;
            if (position < arrList.size()) {
                list.addLast(arrList.get(i + nItemCount));
            } else if (position < (arrList.size() + androidVersion.length)) {
                list.addLast(androidVersion[position - arrList.size()]);
            } else {
                list.addLast(list.size()+"");
            }
        }
        nItemCount += nLoadAdded;
        adapter.notifyDataSetChanged();
        lvItem.onLoadMoreComplete();
        lvItem.setSelection(list.size() - 17);
    }

    //Background proses palsu untuk melakukan proses delay dan append data di bagian atas list
    private class FakePullRefreshAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(2000);
            }catch (Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            populatePullRefreshData();
        }
    }

    private void populatePullRefreshData() {
        for (int i = 0; i < nRefreshAdded; i++){
            arrList.add(arrList.size() + "");
        }
        list.removeAll(list);
        for (int i = 0; i < nDefaultPage; i++) {
            if (i < arrList.size()) {
                list.add(arrList.get(i));
            } else {
                list.add(androidVersion[i - arrList.size()]);
            }
        }
        currentPage = 1;
        nItemCount = nDefaultPage;
        swipeMain.setRefreshing(false);
        adapter.notifyDataSetChanged();
        lvItem.setSelection(0);

    }
}

