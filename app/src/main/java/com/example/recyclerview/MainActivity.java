package com.example.recyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ApiModel> userList = new ArrayList<>();
    RedAdapter adapter;
    String url = "https://jsonplaceholder.typicode.com/";

    int id;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // initData();
       // initRecyclerView();

        SearchView searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApi myApi = retrofit.create(MyApi.class);
        Call<List<ApiModel>> call = myApi.getModels();

        call.enqueue(new Callback<List<ApiModel>>() {
            @Override
            public void onResponse(Call<List<ApiModel>> call, Response<List<ApiModel>> response) {

                userList  = response.body();

                adapter = new RedAdapter(userList);
                recyclerView = findViewById(R.id.recyclerview);
                layoutManager = new LinearLayoutManager(MainActivity.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<List<ApiModel>> call, Throwable t) {

            }
        });
    }

  /*  private void initData() {

        userList = new ArrayList<>();

        userList.add(new Model(R.drawable.a4, "Piku", title, "7:00 am",
                "...................................................................................................."));
        userList.add(new Model(R.drawable.a1, "Ram", "Hey, What's Up ?", "10:00 am",
                "...................................................................................................."));
        userList.add(new Model(R.drawable.a2, "Mohan", "Oky, bye.", "12:00 pm",
                "...................................................................................................."));
        userList.add(new Model(R.drawable.a6, "Anjali", "Hey, Good Afternoon.", "1:00 pm",
                "...................................................................................................."));
        userList.add(new Model(R.drawable.a5, "Nisha", "I am never give up.", "5:24 pm",
                "...................................................................................................."));
        userList.add(new Model(R.drawable.a3, "Hari", "Bahut Ache.", "5:30 pm",
                "...................................................................................................."));
        userList.add(new Model(R.drawable.a7, "Ram", "Hey, Good Evening.", "7:00 pm",
                "...................................................................................................."));
        userList.add(new Model(R.drawable.a8, "Mark", "Hey, Good Night.", "9:00 pm",
                "...................................................................................................."));
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

   */
}