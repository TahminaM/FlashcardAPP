package com.example.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flashcard.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView answerTextView = findViewById(R.id.answer);
        TextView questionTextView = findViewById(R.id.question);
        TextView choiceOne = findViewById(R.id.choiceOne);
        TextView choiceTwo = findViewById(R.id.choiceTwo);
        TextView choiceThree = findViewById(R.id.choiceThree);
        ImageView toggle = findViewById(R.id.toggle);
        Button buttonR = findViewById(R.id.buttonR);
        ImageView add = (ImageView) findViewById(R.id.add);
        //for the button.
        final boolean[] showAnswers = {true};
        final boolean[] flipped = {false};
        // change the background color of the multiple choice answers when
        // clicked to indicate whether the question was answered correctly
        choiceOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceOne.setBackgroundColor(getResources().getColor(R.color.purple_200, null));
            }
        });
        choiceTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceOne.setBackgroundColor(getResources().getColor(R.color.purple_200, null));
                choiceTwo.setBackgroundColor(getResources().getColor(R.color.black, null));
            }
        });
        choiceThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceOne.setBackgroundColor(getResources().getColor(R.color.purple_200, null));
                choiceThree.setBackgroundColor(getResources().getColor(R.color.black, null));
            }
        });
        //question and answer toggle
        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionTextView.setVisibility(View.INVISIBLE);
                answerTextView.setVisibility(View.VISIBLE);
            }
        });
        answerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionTextView.setVisibility(View.VISIBLE);
                answerTextView.setVisibility(View.INVISIBLE);
            }
        });
        // added a button so users can easily reset the correct and incorrect choices.
        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceOne.setBackgroundColor(getResources().getColor(R.color.teal_200, null));
                choiceTwo.setBackgroundColor(getResources().getColor(R.color.teal_200, null));
                choiceThree.setBackgroundColor(getResources().getColor(R.color.teal_200, null));
                //questionTextView.setText("When was the first version of Android released?");
                flipped[0] = false;
            }
        });
        // to show the choices and hide the choices
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showAnswers[0]) {
                    showAnswers[0] = false;
                    choiceOne.setVisibility(View.INVISIBLE);
                    choiceTwo.setVisibility(View.INVISIBLE);
                    choiceThree.setVisibility(View.INVISIBLE);
                    toggle.setImageResource(R.drawable.ic_eye_hidden);
                } else {
                    showAnswers[0] = true;
                    choiceOne.setVisibility(View.VISIBLE);
                    choiceTwo.setVisibility(View.VISIBLE);
                    choiceThree.setVisibility(View.VISIBLE);
                    toggle.setImageResource(R.drawable.ic_open_eye);
                }
            }
        });
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);

                MainActivity.this.startActivityForResult(intent, 100);
            }

        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode==RESULT_OK) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question = data.getExtras().getString("question"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("answer");

            ((TextView) findViewById(R.id.question)).setText(question);
            ((TextView) findViewById(R.id.answer)).setText(answer);
        }
    }
}




