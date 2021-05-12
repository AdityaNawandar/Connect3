package com.example.connectthree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    int intActivePlayer = 1;
    int[] intarrGameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    boolean blnIsGameActive = true;

    public void dropIn(View view) {
        ImageView imgvwCounter = (ImageView) view;
        int intTappedCounter = Integer.parseInt(imgvwCounter.getTag().toString());

        // 0 = empty, 1 = yellow, 2 = red
        if (intarrGameState[intTappedCounter - 1] == 0 && blnIsGameActive) {
            intarrGameState[intTappedCounter - 1] = intActivePlayer;
            imgvwCounter.setTranslationY(-2000);

            if (intActivePlayer == 1) {
                imgvwCounter.setImageResource(R.drawable.yellowcoin);
                imgvwCounter.animate().rotation(1800).translationYBy(2000).setDuration(1000);
                checkWinner();
                intActivePlayer = 2;
            } else if (intActivePlayer == 2) {
                imgvwCounter.setImageResource(R.drawable.redcoin);
                imgvwCounter.animate().rotation(1800).translationYBy(2000).setDuration(1000);
                checkWinner();
                intActivePlayer = 1;
            }
        }
    }

    private void checkWinner() {

        String strWinner = "";
        Button btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        TextView tvWinner = (TextView) findViewById(R.id.tvWinner);
        if (intarrGameState[0] == intActivePlayer && intarrGameState[1] == intActivePlayer && intarrGameState[2] == intActivePlayer || // 1st row
                intarrGameState[3] == intActivePlayer && intarrGameState[4] == intActivePlayer && intarrGameState[5] == intActivePlayer || // 2nd row
                intarrGameState[6] == intActivePlayer && intarrGameState[7] == intActivePlayer && intarrGameState[8] == intActivePlayer || // 3rd row
                intarrGameState[0] == intActivePlayer && intarrGameState[3] == intActivePlayer && intarrGameState[6] == intActivePlayer || // 1st col.
                intarrGameState[1] == intActivePlayer && intarrGameState[4] == intActivePlayer && intarrGameState[7] == intActivePlayer || // 2nd col.
                intarrGameState[2] == intActivePlayer && intarrGameState[5] == intActivePlayer && intarrGameState[8] == intActivePlayer || // 3rd col.
                intarrGameState[0] == intActivePlayer && intarrGameState[4] == intActivePlayer && intarrGameState[8] == intActivePlayer || // Diagonal          \
                intarrGameState[2] == intActivePlayer && intarrGameState[4] == intActivePlayer && intarrGameState[6] == intActivePlayer) //   Diagonal      /
        {
            if (intActivePlayer == 1) {
                strWinner = "Yellow won!";
            } else if (intActivePlayer == 2) {
                strWinner = "Red won!";
            }

            blnIsGameActive = false;
            tvWinner.setText(strWinner);
            btnPlayAgain.postDelayed(new Runnable() {
                public void run() {
                    tvWinner.setVisibility(View.VISIBLE);
                    btnPlayAgain.setVisibility(View.VISIBLE);
                }
            }, 500);
        } else {
            boolean areAllNonZero = true;
            for (int entry : intarrGameState) {
                if (entry == 0) {
                    areAllNonZero = false;
                    break;
                }
            }
            if (areAllNonZero) {
                btnPlayAgain.postDelayed(new Runnable() {
                    public void run() {
                        tvWinner.setText("Draw!");
                        tvWinner.setVisibility(View.VISIBLE);
                        btnPlayAgain.setVisibility(View.VISIBLE);
                    }
                }, 500);
            }
        }
    }

    public void playAgain(View view) {
        Button btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        TextView tvWinner = (TextView) findViewById(R.id.tvWinner);
        tvWinner.setVisibility(View.INVISIBLE);
        btnPlayAgain.setVisibility(View.INVISIBLE);

        androidx.gridlayout.widget.GridLayout glBoard = findViewById(R.id.gridLayout);

        for (int i = 0; i < glBoard.getChildCount(); i++) {
            ImageView ivSlotInBoard = (ImageView) glBoard.getChildAt(i);
            ivSlotInBoard.setImageDrawable(null);
        }

        for (int i = 0; i < intarrGameState.length; i++) {
            intarrGameState[i] = 0;
        }
        intActivePlayer = 1;
        blnIsGameActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}