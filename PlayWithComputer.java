package com.example.stonepaperscissor;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.example.stonepaperscissor.databinding.ActivityPlayWithComputerBinding;

import java.util.Random;

public class PlayWithComputer extends AppCompatActivity {
    private ActivityPlayWithComputerBinding binding;

    private AnimationDrawable animation1;
    private AnimationDrawable animation2;
    private CountDownTimer setTime;

    private boolean allowPlaying = true;

    private String selectionP1;
    private String selectionP2;

    private int scoreP1 = 0;
    private int scoreP2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayWithComputerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnP2rock.setOnClickListener(view -> onPlay("rock"));
        binding.btnP2paper.setOnClickListener(view -> onPlay("paper"));
        binding.btnP2scissor.setOnClickListener(view -> onPlay("scissor"));
    }

    private void playAnimation() {
        binding.ivIconP1.setImageResource(0);
        binding.ivIconP2.setImageResource(0);
        binding.tvP1Status.setText("");
        binding.tvP2Status.setText("");
        binding.ivIconP1.setBackgroundResource(R.drawable.animation_rpc);
        animation1 = (AnimationDrawable) binding.ivIconP1.getBackground();
        binding.ivIconP2.setBackgroundResource(R.drawable.animation_rpc);
        animation2 = (AnimationDrawable) binding.ivIconP2.getBackground();

        setTime = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
                animation1.start();
                animation2.start();
            }

            @Override
            public void onFinish() {
                animation1.stop();
                animation2.stop();
                allowPlaying = true;
                binding.ivIconP1.setBackgroundResource(0);
                binding.ivIconP2.setBackgroundResource(0);
                setSelectedIcon();
                setScore();
                endGame();
            }
        }.start();
    }

    private void endGame() {
        if (scoreP1 == 3 || scoreP2 == 3) {
            String winner = (scoreP1 == 3) ? "comp" : "player";
            Intent intent = new Intent(this, FinishComp.class);
            intent.putExtra("winner", winner);
            startActivity(intent);
            finish();
        }
    }

    private String getResult() {
        if (selectionP1.equals(selectionP2)) {
            return "tie";
        } else if ((selectionP1.equals("rock") && selectionP2.equals("scissor")) ||
                (selectionP1.equals("paper") && selectionP2.equals("rock")) ||
                (selectionP1.equals("scissor") && selectionP2.equals("paper"))) {
            return "P1";
        } else {
            return "P2";
        }
    }

    private void setScore() {
        if (getResult().equals("tie")) {
            binding.tvP1Status.setText("Tie");
            binding.tvP2Status.setText("Tie");
        } else if (getResult().equals("P1")) {
            binding.tvP1Status.setText("Win");
            binding.tvP2Status.setText("Lose");
            scoreP1++;
            switch (scoreP1) {
                case 1:
                    binding.ivP1Star1.setImageResource(R.drawable.star);
                    break;
                case 2:
                    binding.ivP1Star2.setImageResource(R.drawable.star);
                    break;
                case 3:
                    binding.ivP1Star3.setImageResource(R.drawable.star);
                    break;
            }
        } else {
            binding.tvP1Status.setText("Lose");
            binding.tvP2Status.setText("Win");
            scoreP2++;
            switch (scoreP2) {
                case 1:
                    binding.ivP2Star1.setImageResource(R.drawable.star);
                    break;
                case 2:
                    binding.ivP2Star2.setImageResource(R.drawable.star);
                    break;
                case 3:
                    binding.ivP2Star3.setImageResource(R.drawable.star);
                    break;
            }
        }
    }

    private void setSelectedIcon() {
        switch (selectionP1) {
            case "rock":
                binding.ivIconP1.setImageResource(R.drawable.rock);
                break;
            case "paper":
                binding.ivIconP1.setImageResource(R.drawable.paper);
                break;
            case "scissor":
                binding.ivIconP1.setImageResource(R.drawable.scissor);
                break;
        }

        switch (selectionP2) {
            case "rock":
                binding.ivIconP2.setImageResource(R.drawable.rock);
                break;
            case "paper":
                binding.ivIconP2.setImageResource(R.drawable.paper);
                break;
            case "scissor":
                binding.ivIconP2.setImageResource(R.drawable.scissor);
                break;
        }
    }

    private void onPlay(String selection) {
        if (allowPlaying) {
            selectionP1 = getRandomSelection();
            selectionP2 = selection;
            allowPlaying = false;
            playAnimation();
        }
    }

    private String getRandomSelection() {
        String[] selections = {"rock", "paper", "scissor"};
        Random random = new Random();
        int index = random.nextInt(selections.length);
        return selections[index];
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        setTime = null;
    }
}
