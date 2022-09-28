package umn.ac.id.maggotproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import umn.ac.id.maggotproject.global.AuthenticatedUser;
import umn.ac.id.maggotproject.model.AuthenticationModel;
import umn.ac.id.maggotproject.retrofit.ApiService;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    TextView email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Login Button has been Clicked!", Toast.LENGTH_LONG).show();
                login(email.getText().toString(), password.getText().toString());
            }
        });
    }

    private void login(String email, String password) {
        ApiService.endpoint().login(email, password).enqueue(new Callback<AuthenticationModel>() {
            @Override
            public void onResponse(Call<AuthenticationModel> call, Response<AuthenticationModel> response) {
                if(response.isSuccessful()) {
                    AuthenticationModel.Result result = response.body().login();

                    if(result.getMessage() != null) {
                        Toast.makeText(LoginActivity.this, "Wrong email or Password!", Toast.LENGTH_LONG).show();
                    } else {
                        AuthenticatedUser.setUser(result.getUser(), result.getToken());
                        Toast.makeText(LoginActivity.this, "Login Success, Hello " + AuthenticatedUser.getUser().getFull_name(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, PengepulActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthenticationModel> call, Throwable t) {
                Log.d("Error response : ", t.toString());
            }
        });
    }
}