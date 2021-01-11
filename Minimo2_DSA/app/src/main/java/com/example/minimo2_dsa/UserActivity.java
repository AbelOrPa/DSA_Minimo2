package com.example.minimo2_dsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minimo2_dsa.Models.User;
import com.example.minimo2_dsa.Service.APIService;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {


    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        username = (EditText)findViewById(R.id.userName);

    }

    public void btnUser (View view){

        String user = username.getText().toString();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        APIService apiService =retrofit.create(APIService.class);
        Call<User> call = apiService.getUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){



                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("username",response.body().getLogin());
                    intent.putExtra("followers",String.valueOf(response.body().getFollowers()));
                    intent.putExtra("following",String.valueOf(response.body().getFollowing()));
                    intent.putExtra("url",response.body().getAvatar_url());
                    startActivity(intent);

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(),"ERROR de connexion",Toast.LENGTH_SHORT).show();
            }
        });

    }

}