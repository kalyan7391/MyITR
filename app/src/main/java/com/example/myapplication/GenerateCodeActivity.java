package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.util.Random;

public class GenerateCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);

        ImageView ivQRCode = findViewById(R.id.ivQRCode);
        TextView tvCode = findViewById(R.id.tvCode);

        String teacherUsername = getIntent().getStringExtra("teacher_username");
        String subject = getIntent().getStringExtra("subject");
        String type = getIntent().getStringExtra("type");

        if ("qr".equals(type)) {
            try {
                String data = teacherUsername + ":" + subject;
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 300, 300);
                ivQRCode.setImageBitmap(bitmap);
                ivQRCode.setVisibility(ImageView.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String code = String.format("%06d", new Random().nextInt(999999));
            tvCode.setText(code);
            tvCode.setVisibility(TextView.VISIBLE);
        }
    }
}