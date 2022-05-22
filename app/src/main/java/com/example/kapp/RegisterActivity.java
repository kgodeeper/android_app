package com.example.kapp;

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

public class RegisterActivity extends AppCompatActivity {
    private Button reg;
    private EditText usname,pass,full,re;
    private TextView login_redirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private boolean IsNotNull(String a){
        return !a.equals("");
    }

    private void init(){
        reg = findViewById(R.id.reg_btn);
        usname = findViewById(R.id.reg_username);
        pass = findViewById(R.id.reg_password);
        full = findViewById(R.id.reg_fullname);
        re = findViewById(R.id.reg_confirm);
        login_redirect = findViewById(R.id.btn_login_redirect);
        Intent intent = getIntent();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usname.getText().toString();
                String password = pass.getText().toString();
                String fullname = full.getText().toString();
                String repass   = re.getText().toString();
                if(IsNotNull(username) && IsNotNull(password) && IsNotNull(fullname) && IsNotNull(repass)){
                    if(password.matches("[a-zA-Z0-9]{8,}")){
                        if(password.equals(repass)){
                            User user = new User(username,password,fullname,"");
                            ApiService.apiService.user_register(user)
                                    .enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                            User user = response.body();
                                            if(!user.getUsername().equals("")){
                                                Toast.makeText(view.getContext(),"Signned up",Toast.LENGTH_LONG).show();
                                                intent.putExtra("username",username);
                                                intent.putExtra("password",password);
                                                setResult(RESULT_OK,intent);
                                                finish();
                                            }else{
                                                Toast.makeText(view.getContext(),"Tài khoản đã tồn tại",Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {

                                        }
                                    });
                        }else{
                            Toast.makeText(view.getContext(),"Mật khẩu không khớp",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(view.getContext(),"Mật khẩu tối thiểu 8 kí tự",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(view.getContext(),"Các trường không được để trống",Toast.LENGTH_LONG).show();
                }
            }
        });

        login_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}