package umn.ac.id.project.maggot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import umn.ac.id.project.maggot.adapter.DetailWarungAdapter;
import umn.ac.id.project.maggot.adapter.ListWarungBinaanAdapter;
import umn.ac.id.project.maggot.model.WarungModel;
import umn.ac.id.project.maggot.retrofit.ApiService;

public class ListWarungActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_warung);
        getDataWarung();
        ImageView back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListWarungActivity.this, HomePagePengelolaBankSampah.class);
                startActivity(intent);
            }
        });
    }

    private void getDataWarung() {
        ApiService.endpoint().getWarung().enqueue(new Callback<WarungModel>() {
            @Override
            public void onResponse(@NonNull Call<WarungModel> call, @NonNull Response<WarungModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    FloatingActionButton fab = findViewById(R.id.fab);
                    List<WarungModel.Warung> results = response.body().getWarung();
                    Log.d("Success", results.toString());
                    DetailWarungAdapter detailWarungAdapter = new DetailWarungAdapter(ListWarungActivity.this, results);
                    RecyclerView recyclerView2 = findViewById(R.id.listWarungRecyclerView);
                    recyclerView2.setAdapter(detailWarungAdapter);
                    recyclerView2.setLayoutManager(new LinearLayoutManager(ListWarungActivity.this));

                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(view, "INI BUAT FAB TAMBAH DI WARUNG!!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<WarungModel> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }
}
