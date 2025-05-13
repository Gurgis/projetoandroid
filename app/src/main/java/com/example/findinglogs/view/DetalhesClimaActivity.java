package com.example.findinglogs.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
//imports pra eu ajustar o tempo do nascer e por do sol
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import com.example.findinglogs.R;
import com.example.findinglogs.model.util.Utils;

public class DetalhesClimaActivity extends AppCompatActivity {

    private TextView Cidade, Temperatura, Umidade, Pressao, NascerSol, PorSol, Descricao, Sensacao, TempMin, TempMax;


    //tradução simples, buscar depois se há uma maneira automatizada
    public String traduzirDescricao(String descricaoIngles) {
        switch (descricaoIngles) {
            case "clear sky": return "céu limpo";
            case "few clouds": return "poucas nuvens";
            case "scattered clouds": return "nuvens dispersas";
            case "overcast clouds": return "Nuvens carregadas";
            case "shower rain": return "chuva de pancadas";
            case "rain": return "chuva";
            case "thunderstorm": return "trovoada";
            case "snow": return "neve";
            case "mist": return "névoa";
            default: return descricaoIngles; // retorna original se não tiver tradução
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_clima); //

        // textviews
        Cidade = findViewById(R.id.Cidade);
        Temperatura = findViewById(R.id.Temperatura);
        Umidade = findViewById(R.id.Umidade);
        Pressao = findViewById(R.id.Pressao);
        NascerSol = findViewById(R.id.NascerSol);
        PorSol = findViewById(R.id.PorSol);
        Descricao = findViewById(R.id.Descricao);
        Sensacao = findViewById(R.id.Sensacao);
        TempMin = findViewById(R.id.TempMin);
        TempMax = findViewById(R.id.TempMax);
        ImageView imageIcon = findViewById(R.id.ImagemIcon);
        ScrollView layoutDetalhes = findViewById(R.id.TelaScroll);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);

        // pega os dados da Intent
        String cidade = getIntent().getStringExtra("cidade");
        float temperatura = getIntent().getFloatExtra("temperatura", 0f);
        float umidade = getIntent().getFloatExtra("umidade", 0f);
        float pressao = getIntent().getFloatExtra("pressao", 0f);
        long nascerSol = getIntent().getLongExtra("nascer_do_sol", 0L);
        long porSol = getIntent().getLongExtra("por_do_sol", 0L);
        String descricao = getIntent().getStringExtra("descricao");
        float sensacaoTermica = getIntent().getFloatExtra("sensacao_termica", 0f);
        float tempMin = getIntent().getFloatExtra("temp_min", 0f);
        float tempMax = getIntent().getFloatExtra("temp_max", 0f);
        String horaNascer = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(nascerSol * 1000));
        String horaPor = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(porSol * 1000));
        String iconeClima = getIntent().getStringExtra("icone_clima");



        // mostra na tela
        Cidade.setText(cidade);
        Temperatura.setText(Utils.getCelsiusTemperatureFromKevin(temperatura));
        Umidade.setText(umidade + "%");
        Pressao.setText(pressao + " hPa");
        // aqui eu estou transformado o tempo do nascer e por do sol para horas
        NascerSol.setText( horaNascer);
        PorSol.setText(horaPor);
        Descricao.setText(traduzirDescricao(descricao));
        Sensacao.setText("Sensação térmica é de " + Utils.getCelsiusTemperatureFromKevin(sensacaoTermica));
        TempMin.setText( " / " + Utils.getCelsiusTemperatureFromKevin(tempMin) + " ");
        TempMax.setText(Utils.getCelsiusTemperatureFromKevin(tempMax) );


        //voltando pra tela anterior para não precisar clicar só no botão de voltar do celular
        btnVoltar.setOnClickListener(v -> finish());

        if (iconeClima != null) {
            Drawable drawable = Utils.getDrawable(iconeClima, this);
            imageIcon.setImageDrawable(drawable);
        }

            //utilizando o switch para mudar o background com base no codigo do icone
        switch (iconeClima) {
            case "02d":
                layoutDetalhes.setBackgroundColor(getColor(R.color.weather_few_clouds));
                break;
            case "02n":
                layoutDetalhes.setBackgroundColor(getColor(R.color.weather_few_clouds_dark));
                break;
            case "03d":
                layoutDetalhes.setBackgroundColor(getColor(R.color.weather_cloudy));
                break;
            case "03n":
                layoutDetalhes.setBackgroundColor(getColor(R.color.weather_cloudy_dark));
                break;
            case "04d":
                layoutDetalhes.setBackgroundColor(getColor(R.color.weather_scattered_clouds));
                break;
            case "04n":
                layoutDetalhes.setBackgroundColor(getColor(R.color.weather_scattered_clouds_dark));
                break;
            case "09d":
                layoutDetalhes.setBackgroundColor(getColor(R.color.weather_fog));
                break;
            case "09n":
                layoutDetalhes.setBackgroundColor(getColor(R.color.weather_fog_dark));
                break;
            default:
                layoutDetalhes.setBackgroundColor(getColor(R.color.weather_few_clouds));
                break;
        }





    }
}


