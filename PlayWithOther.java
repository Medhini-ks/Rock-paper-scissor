package com.example.stonepaperscissor;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.stonepaperscissor.databinding.ActivityPlayWithOtherBinding;

public class PlayWithOther extends AppCompatActivity {

    private ActivityPlayWithOtherBinding binding;

    private AnimationDrawable animation1;
    private AnimationDrawable animation2;
    private CountDownTimer setTime;

    private String playerName1 = "Player 1";
    private String playerName2 = "Player 2";

    private boolean player1Ready = false;
    private boolean player2Ready = false;
    private boolean allowPlaying = true;

    private String selectionP1;
    private String selectionP2;

    private int scoreP1 = 0;
    private int scoreP2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayWithOtherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getPlayersName();

        binding.btnP2rock.setOnClickListener(view -> onPlayP2("rock"));
        binding.btnP2paper.setOnClickListener(view -> onPlayP2("paper"));
        binding.btnP2scissor.setOnClickListener(view -> onPlayP2("scissor"));
        binding.btnP1rock.setOnClickListener(view -> onPlayP1("rock"));
        binding.btnP1paper.setOnClickListener(view -> onPlayP1("paper"));
        binding.btnP1scissor.setOnClickListener(view -> onPlayP1("scissor"));
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
        public void onTick(long p0) {
            animation1.start();
            animation2.start();
        }

        @Override
        public void onFinish() {
            animation1.stop();
            animation2.stop();
            allowPlaying = true;
            player1Ready = false;
            player2Ready = false;
            binding.ivIconP1.setBackgroundResource(0);
            binding.ivIconP2.setBackgroundResource(0);
            setSelectedIcon();
            setScore();
            endGame();
        }
    }.start();
    }

    private void onPlayP1(String selection) {
        if (allowPlaying) {
            binding.ivIconP1.setImageResource(R.drawable.check);
            binding.tvP1Status.setText("Ready");
            selectionP1 = selection;
            player1Ready = true;
            if (player2Ready) {
                allowPlaying = false;
                playAnimation();
            }
        }
    }

    private void onPlayP2(String selection) {
        if (allowPlaying) {
            binding.ivIconP2.setImageResource(R.drawable.check);
            binding.tvP2Status.setText("Ready");
            selectionP2 = selection;
            player2Ready = true;
            if (player1Ready) {
                allowPlaying = false;
                playAnimation();
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

    private String getResult() {
        if (selectionP1.equals(selectionP2))
            return "tie";
        else if (selectionP1.equals("rock") && selectionP2.equals("scissor") ||
            selectionP1.equals("paper") && selectionP2.equals("rock") ||
            selectionP1.equals("scissor") && selectionP2.equals("paper"))
            return "P1";
        else
            return "P2";
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

    private void getPlayersName() {
        Dialog nameDialog = new Dialog(this);
        nameDialog.setContentView(R.layout.player_name_dialog);
        nameDialog.findViewById(R.id.btnOk).setOnClickListener(view -> {
        EditText etName1 = nameDialog.findViewById(R.id.et_name1);
        EditText etName2 = nameDialog.findViewById(R.id.et_name2);
        String name1 = etName1.getText().toString();
        String name2 = etName2.getText().toString();

        if (!name1.isEmpty() && !name2.isEmpty()) {
            playerName1 = name1;
            playerName2 = name2;
            binding.playerName1.setText(playerName1);
            binding.playerName2.setText(playerName2);
            nameDialog.cancel();
        } else {
            Toast.makeText(this, "Enter both players' names.", Toast.LENGTH_SHORT).show();
        }
    });
        nameDialog.show();
    }

    private void endGame() {
        if (scoreP1 == 3 || scoreP2 == 3) {
            String winner = (scoreP1 == 3) ? playerName1 : playerName2;
            Intent intent = new Intent(this, FinishActivity.class);
            intent.putExtra("name", winner);
            startActivity(intent);
            finish();
        }
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
