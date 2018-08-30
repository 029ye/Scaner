package com.ye.scaner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private TextView tx;
    private EditText etx;
    private ImageView one_barcode;
    private ImageView two_barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        tx = findViewById(R.id.tx);
        etx = findViewById(R.id.etx);
        one_barcode = findViewById(R.id.one_barcode);
        two_barcode = findViewById(R.id.two_barcode);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = etx.getText().toString();
                if ("" != str){
                    one_barcode.setImageBitmap(genOneBarcode(str));
                    two_barcode.setImageBitmap(genTwoBarcode(str));
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        获取扫描结果
        if (resultCode == RESULT_OK){
            String result = data.getExtras().get("result").toString();
            tx.setText(result);
        }
    }

    Bitmap genOneBarcode(String str){
        return EncodingUtils.createOneCode(str, 600, 200);
    }

    Bitmap genTwoBarcode(String str){
        return EncodingUtils.createQRCode(str, 600, 600, null);
    }
}
