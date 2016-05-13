package com.gin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEdit = (Button) findViewById(R.id.customEdit);
        btnEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //        Intent intent;
        //
        //        switch (v.getId()){
        //            case R.id.customEdit:
        //                intent = CustomEditActivity.getIntent(MainActivity.this);
        //                break;
        //            case R.id.gpuImge:
        //                intent = GPUImageAcitivity.getIntent(MainActivity.this);
        //                break;
        //            default:
        //                intent = new Intent(MainActivity.this,MainActivity.class);
        //                break;
        //        }
        //        startActivity(intent);
    }
}
