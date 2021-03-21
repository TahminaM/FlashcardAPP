package com.example.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flashcard.R;

//added for lab 3
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

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

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (flashcardDatabase.getAllCards().size() > 0){
            ((TextView) findViewById(R.id.question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.answer)).setText(allFlashcards.get(0).getAnswer());

        }

        currentCardDisplayedIndex = 0;
        //for the button.
        final boolean[] showAnswers = {true};
        final boolean[] flipped = {false};
        // change the background color of the multiple choice answers when
        // clicked to indicate whether the question was answered correctly
        choiceOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceOne.setBackgroundColor(getResources().getColor(R.color.red, null));
                choiceTwo.setBackgroundColor(getResources().getColor(R.color.red, null));
                choiceThree.setBackgroundColor(getResources().getColor(R.color.green, null));
            }
        });
        choiceTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceOne.setBackgroundColor(getResources().getColor(R.color.red, null));
                choiceTwo.setBackgroundColor(getResources().getColor(R.color.red, null));
                choiceThree.setBackgroundColor(getResources().getColor(R.color.green, null));
            }
        });
        choiceThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceOne.setBackgroundColor(getResources().getColor(R.color.red, null));
                choiceTwo.setBackgroundColor(getResources().getColor(R.color.red, null));
                choiceThree.setBackgroundColor(getResources().getColor(R.color.green, null));
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

        // Added for lab 3
        findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // don't try to go to next card if you have no cards to begin with
                if (allFlashcards.size() == 0)
                    return;
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if(currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(questionTextView,
                            "You've reached the end of the cards, going back to start.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                allFlashcards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                ((TextView) findViewById(R.id.question)).setText(flashcard.getAnswer());
                ((TextView) findViewById(R.id.answer)).setText(flashcard.getQuestion());
            }
        });
        findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                allFlashcards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                ((TextView) findViewById(R.id.question)).setText(flashcard.getAnswer());
                ((TextView) findViewById(R.id.answer)).setText(flashcard.getQuestion());
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

        //added for lab 3
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            String question = data.getExtras().getString("question");
            String answer = data.getExtras().getString("answer");


            flashcardDatabase.insertCard(new Flashcard(question, answer));
            allFlashcards = flashcardDatabase.getAllCards();

        }
    }
}