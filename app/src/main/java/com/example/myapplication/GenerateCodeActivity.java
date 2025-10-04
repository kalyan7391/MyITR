package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.util.Random;

public class GenerateCodeActivity extends AppCompatActivity {

    private ImageView ivQRCode;
    private TextView tvCode;
    private String teacherUsername, subject, type;

    // Handler to manage the 30-second timer
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable codeRefreshRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);

        ivQRCode = findViewById(R.id.ivQRCode);
        tvCode = findViewById(R.id.tvCode);

        teacherUsername = getIntent().getStringExtra("teacher_username");
        subject = getIntent().getStringExtra("subject");
        type = getIntent().getStringExtra("type");

        // Set up the recurring task that will call our generator function
        setupCodeGenerator();
    }

    private void setupCodeGenerator() {
        codeRefreshRunnable = new Runnable() {
            @Override
            public void run() {
                // This function is called every 30 seconds
                generateNewCode();
                // Schedule this to run again in 30 seconds
                handler.postDelayed(this, 30000); // 30,000 milliseconds
            }
        };
    }

    /**
     * This function checks the 'type' and generates either a
     * new QR code or a new numeric code.
     */
    private void generateNewCode() {
        if ("qr".equals(type)) {
            try {
                // Include a timestamp to make every QR code unique
                String uniqueData = teacherUsername + ":" + subject + ":" + System.currentTimeMillis();
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.encodeBitmap(uniqueData, BarcodeFormat.QR_CODE, 400, 400);
                ivQRCode.setImageBitmap(bitmap);

                // Show the QR code and hide the text view
                ivQRCode.setVisibility(ImageView.VISIBLE);
                tvCode.setVisibility(TextView.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { // This handles the "code" type
            String numericCode = String.format("%06d", new Random().nextInt(999999));
            tvCode.setText(numericCode);

            // Show the text view and hide the QR code image view
            tvCode.setVisibility(TextView.VISIBLE);
            ivQRCode.setVisibility(ImageView.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start the refresh cycle when the screen is visible
        handler.post(codeRefreshRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the refresh cycle when the screen is hidden to save resources
        handler.removeCallbacks(codeRefreshRunnable);
    }
}