package com.peirra.tvdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {


    private static final int DIALOG_REQUEST_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTvDialog();
            }
        });

        findViewById(R.id.default_d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDefaultDialog();
            }
        });

    }




    public void showTvDialog() {
        Intent intent = new Intent(this, TVDialogActivity.class);
        intent.putExtra(TVDialogActivity.ARG_TITLE_RES,R.string.dialog_title);
        intent.putExtra(TVDialogActivity.ARG_ICON_RES,R.mipmap.ic_launcher);
        intent.putExtra(TVDialogActivity.ARG_NEGATIVE_RES,R.string.dialog_no);
        intent.putExtra(TVDialogActivity.ARG_POSITIVE_RES, R.string.dialog_yes);
        intent.putExtra(TVDialogActivity.ARG_DESC_RES, R.string.dialog_desc);
        startActivityForResult(intent, DIALOG_REQUEST_CODE);
    }



    public void showDefaultDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_desc)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Choose Native POSITIVE",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Choose Native NEGATIVE",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == DIALOG_REQUEST_CODE){
            if(resultCode == RESULT_OK){ //retry
                Toast.makeText(this,"Choose TV POSITIVE",Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this,"Choose TV NEGATIVE",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
