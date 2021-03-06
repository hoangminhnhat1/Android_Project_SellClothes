package com.example.androidproject_sellclothes.Controller;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.shopping.Api.ApiService;
import com.app.shopping.Model.Users;
import com.app.shopping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterController extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        loadingBar = new ProgressDialog(this);
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            CreateAccountDB(name, phone, password);
        }

    }

    private void CreateAccountDB(final String name, final String phone, final String password) {
        Users userdata = new Users();
        userdata.setPhone(phone);
        userdata.setPassword(password);
        userdata.setName(name);
        userdata.setLevel("1");
        ApiService.apiService.addUser(userdata).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Toast.makeText(RegisterController.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                Intent intent = new Intent(RegisterController.this, LoginController.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(RegisterController.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                Log.e("abc",t.getMessage());
                Toast.makeText(RegisterController.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterController.this, MainController.class);
                startActivity(intent);
            }
        });
    }
}
