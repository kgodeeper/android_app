package com.example.kapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kapp.api.ApiService;
import com.example.kapp.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText usname,password;
    private Button login;
    private TextView reg_redirect;
    private final static int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        usname = findViewById(R.id.login_usname);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.btn_login);
        reg_redirect = findViewById(R.id.btn_register_redirect);

        reg_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernamestr = usname.getText().toString();
                String passwordstr = password.getText().toString();
                User tmp = new User(usernamestr,passwordstr,"","");
                if(!usernamestr.equals("") && !passwordstr.equals("")){
                ApiService.apiService.user_login(tmp)
                        .enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User user = response.body();
                                if(!user.getToken().equals("")){
                                    Toast.makeText(view.getContext(),"Logged in",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });
                }else{
                    Toast.makeText(view.getContext(),"Các trường không được để rỗng",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                usname.setText(data.getStringExtra("username"));
                password.setText(data.getStringExtra("password"));
            }
        }
    }
}