package com.example.findinglogs.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findinglogs.R;
import com.example.findinglogs.model.model.Weather;
import com.example.findinglogs.view.recyclerview.adapter.WeatherListAdapter;
import com.example.findinglogs.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WeatherListAdapter adapter;
    private final List<Weather> weathers = new ArrayList<>();
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_weather);
        FloatingActionButton fetchButton = findViewById(R.id.fetchButton);

        adapter = new WeatherListAdapter(this, weathers);
        recyclerView.setAdapter(adapter);

        // Observador da lista de dados
        viewModel.getWeatherList().observe(this, weathers -> {
            adapter.updateWeathers(weathers);
        });

        // Botão de atualização (agora chama refreshData)
        fetchButton.setOnClickListener(v -> {
            viewModel.refreshData();
            Toast.makeText(MainActivity.this, "Atualizando...", Toast.LENGTH_SHORT).show();
        });
    }
}