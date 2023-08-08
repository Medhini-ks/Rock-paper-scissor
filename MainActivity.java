package com.example.stonepaperscissor;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlayWithOther = findViewById(R.id.btnPlayWithOther);
        btnPlayWithOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayWithOther.class);
                startActivity(intent);
            }
        });

        Button btnPlayWithComp = findViewById(R.id.btnPlayWithComputer);
        btnPlayWithComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayWithComputer.class);
                startActivity(intent);
            }
        });

        TextView tvInstruction = findViewById(R.id.tvInstruction);
        tvInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInstructions();
            }
        });
    }

    private void showInstructions() {
        Dialog instDialog = new Dialog(this);
        instDialog.setContentView(R.layout.instruction_dialog);
        Button btnOk = instDialog.findViewById(R.id.btnok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instDialog.cancel();
            }
        });
        instDialog.show();
    }
}
