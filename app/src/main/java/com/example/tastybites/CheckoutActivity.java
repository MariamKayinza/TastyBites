package com.example.tastybites;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

//
public class CheckoutActivity extends AppCompatActivity implements PaymentResultListener
{

    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    RadioGroup radioGroup;
    Button confirmButton;
    RadioButton mastercard_radio_button;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout); // replace with your layout file
//        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);


        radioGroup = findViewById(R.id.radio_group);
        confirmButton = findViewById(R.id.button);
        mastercard_radio_button = findViewById(R.id.mastercard_radio_button);

        Toast.makeText(this, "asdfklasdfjaldskf", Toast.LENGTH_SHORT).show();


            confirmButton.setOnClickListener(v -> {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == mastercard_radio_button.getId()){
                    startPayment(10000);
                }else {
                    Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                }
            });
        Checkout.preload(CheckoutActivity.this);

    }
    public void startPayment(int Amount){
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_1DP5mmOlF5G5ag");
//        checkout.setKeyID("rzp_test_FXb5XmLEY2ZBQ6");
//        checkout.setImage(R.drawable.logo);
//        final Activity activity = this;
        try{
            JSONObject jsonObject = new JSONObject();
            // image of the payment  from drawable
            jsonObject.put("image",R.drawable.tasty2);
            jsonObject.put("name","Tasty Bites");
            jsonObject.put("description","Order Payment");

            // currency should be in USD
            jsonObject.put("currency","USD");
            jsonObject.put("amount",Amount);
            //color of the payment button
            jsonObject.put("theme.color","#F9813A");

            JSONObject retryObject = new JSONObject();
            retryObject.put("enabled",true);
            retryObject.put("max_count",4);
            jsonObject.put("retry",retryObject);
            checkout.open(CheckoutActivity.this,jsonObject);
    }catch (JSONException e){
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
//        checkout.open(CheckoutActivity.this,jsonObject);
    }


    @Override
    public void onPaymentSuccess(String s) {
//        txtPaymentStatus.setText("Payment Successful");

        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
//        txtPaymentStatus.setText("Payment Failed");
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();

    }
}




