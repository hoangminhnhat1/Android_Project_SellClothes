package com.example.androidproject_sellclothes.Controller;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.shopping.Api.ApiService;
import com.app.shopping.Model.Orders;
import com.app.shopping.Model.Shipments;
import com.app.shopping.Prevalent.Prevalent;
import com.app.shopping.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmFinalOrderController extends AppCompatActivity {
    private EditText nameEditText,phoneEditText,addressEditText,cityEditText;
    private Button confirmOrderBtn;
    private String totalAmount = "";
    private List<Orders> ordersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = "+totalAmount+" VND",Toast.LENGTH_SHORT).show();
        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText =(EditText) findViewById(R.id.shippment_name);
        phoneEditText =(EditText) findViewById(R.id.shippment_phone_number);
        addressEditText =(EditText) findViewById(R.id.shippment_address);
        cityEditText =(EditText) findViewById(R.id.shippment_city);
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
    }

    private void Check() {
        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Full Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Valid Address.",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your City Name",Toast.LENGTH_SHORT).show();
        }
        else {

            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {

//        Prevalent.currentOnlineUser.getPhone())
        ApiService.apiService.getOrdersByUID(Prevalent.currentOnlineUser.getPhone()).enqueue(new Callback<List<Orders>>() {

            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                ordersRef = response.body();
                UpdateOrder();
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                Toast.makeText(ConfirmFinalOrderController.this,"fail 0",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateOrder() {
        ordersRef.get(0).setTotalAmount(totalAmount);
        ordersRef.get(0).setState("Confirmed");
        ApiService.apiService.updateOrders(ordersRef.get(0)).enqueue(new Callback<Orders>() {

            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                Toast.makeText(ConfirmFinalOrderController.this,"Update orders success.",Toast.LENGTH_SHORT).show();
                AddShipment();
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                Toast.makeText(ConfirmFinalOrderController.this,"fail 2.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AddShipment() {
        Shipments ordersShipment = new Shipments();
        final String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd. yyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());
        ordersShipment.setName(nameEditText.getText().toString());
        ordersShipment.setPhone(phoneEditText.getText().toString());
        ordersShipment.setAddress(addressEditText.getText().toString());
        ordersShipment.setCity(cityEditText.getText().toString());
        ordersShipment.setDate(saveCurrentDate);
        ordersShipment.setTime(saveCurrentTime);
        ordersShipment.setIdOrder(ordersRef.get(0).getIdOrder());
        ApiService.apiService.addShipment(ordersShipment).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(ConfirmFinalOrderController.this,"Your final Order has been placed successfully.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConfirmFinalOrderController.this, HomeController.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ConfirmFinalOrderController.this,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
