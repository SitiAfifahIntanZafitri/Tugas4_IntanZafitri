package com.example.tugas4_intanzafitri;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    Button btnBubbleSort, btnSelectionSort, btnInsertionSort, btnQuickSort, btnMergeSort;
    TextView txtNama,txtDeskripsi, txtDetail;
    ImageView imgDetail;
    FloatingActionButton btnRefresh;
    View lyCurrency;
    ProgressBar loadingIndicator;
    private String namaAlgoritma = "Bubble Sort";
    JSONObject dataTerbaru;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inisialisasiView();
        ambilDataAlgoritma();
    }

    private void inisialisasiView() {
        txtNama = findViewById(R.id.txtNama);
        txtDetail = findViewById(R.id.txtDetail);
        txtDeskripsi = findViewById(R.id.txtDeskripsi);
        imgDetail = (ImageView)findViewById(R.id.imgDetail);
        lyCurrency = findViewById(R.id.lyCurrency);
        loadingIndicator = findViewById(R.id.loadingIndicator);

        btnBubbleSort = findViewById(R.id.btnBubbleSort);
        btnBubbleSort.setOnClickListener(view -> tampilkanDataAlgoritma("Bubble Sort"));

        btnSelectionSort = findViewById(R.id.btnSelectionSort);
        btnSelectionSort.setOnClickListener(view -> tampilkanDataAlgoritma("Selection Sort"));

        btnInsertionSort = findViewById(R.id.btnInsertionSort);
        btnInsertionSort.setOnClickListener(view -> tampilkanDataAlgoritma("Insertion Sort"));

        btnQuickSort = findViewById(R.id.btnQuickSort);
        btnQuickSort.setOnClickListener(view -> tampilkanDataAlgoritma("Quick Sort"));

        btnMergeSort = findViewById(R.id.btnMergeSort);
        btnMergeSort.setOnClickListener(view -> tampilkanDataAlgoritma("Merge Sort"));

        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(view -> ambilDataAlgoritma());
    }

    private void ambilDataAlgoritma() {
        loadingIndicator.setVisibility(View.VISIBLE);
        String baseURL = "https://ewinsutriandi.github.io/mockapi/algoritma_pengurutan.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MAIN",response.toString());
                        dataTerbaru = response;
                        tampilkanDataAlgoritma(namaAlgoritma);
                        loadingIndicator.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingIndicator.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Gagal mengambil data",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void tampilkanDataAlgoritma(String namaAlgoritma) {
        this.namaAlgoritma = namaAlgoritma;
        txtNama.setText(namaAlgoritma);
        try { // try catch untuk antisipasi error saat parsing JSON
            JSONObject data = dataTerbaru.getJSONObject(namaAlgoritma);
            txtDetail.setText(data.getString("baca_lebih_lanjut"));
            txtDeskripsi.setText(data.getString("deskripsi"));
            Glide.with(MainActivity.this)
                    .load(data.getString("gambar"))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}