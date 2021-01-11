package com.example.minimo2_dsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minimo2_dsa.Models.Repositorio;
import com.example.minimo2_dsa.Models.User;
import com.example.minimo2_dsa.Service.APIService;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private LoadingDialog loadingDialog;

    private TextView userNam,followersU,followingU;
    private ImageView profile;
    private List<Repositorio> listaRepos;
    private String username, follo,followi,urlAvatar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        userNam = (TextView)findViewById(R.id.userN);
        followersU = (TextView)findViewById(R.id.followers);
        followingU = (TextView)findViewById(R.id.following);
        profile = (ImageView) findViewById(R.id.profileImage);

        recycler = (RecyclerView)findViewById(R.id.recycler);

        //mensaje loading
        loadingDialog = new LoadingDialog(MainActivity.this);
        loadingDialog.startLoadingDialog();


        username = getIntent().getExtras().getString("username");
        follo = getIntent().getExtras().getString("followers");
        followi = getIntent().getExtras().getString("following");
        urlAvatar = getIntent().getExtras().getString("url");
        userNam.setText(username);
        followingU.setText("Following: "+ followi);
        followersU.setText("Followers: " + follo);
        Picasso.get().load(urlAvatar).into(profile);




        //llamamos a la funcion para obtener los datos y añadirlos al recycler
        getRepositoriosU(username);
    }


    public void getRepositoriosU (String username){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        APIService apiService =retrofit.create(APIService.class);
        Call<List<Repositorio>> call = apiService.getRepos(username);
        call.enqueue(new Callback<List<Repositorio>>() {
            @Override
            public void onResponse(Call<List<Repositorio>> call, Response<List<Repositorio>> response) {

                if (response.isSuccessful()){

                    listaRepos = response.body();
                    recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
                    DataAdapter dataAdapter = new DataAdapter(listaRepos);
                    recycler.setAdapter(dataAdapter);
                    loadingDialog.dismissDialog();

                }
            }

            @Override
            public void onFailure(Call<List<Repositorio>> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(),"ERROR de connexion",Toast.LENGTH_SHORT).show();

            }
        });

    }
}