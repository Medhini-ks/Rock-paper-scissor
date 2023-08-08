package com.example.stonepaperscissor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifImageView;

public class FinishComp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_comp);

        Button btnHome = findViewById(R.id.btnHome);

        String winner = getIntent().getStringExtra("winner");
        setView(winner);
        btnHome.setOnClickListener(view -> {
            finish();
        });
    }

    private void setView(String winner) {
        GifImageView imageView = findViewById(R.id.statusDisplay);
        TextView tvStatus = findViewById(R.id.tv_Status);
        if (winner.equals("comp")) {
            imageView.setImageResource(R.drawable.lose_gif);
            tvStatus.setText("Better luck next time\nYou lose!");
        } else {
            imageView.setImageResource(R.drawable.win_gif);
            tvStatus.setText("Congratulations\nYou won!");
        }
    }
}
