package com.example.zamlingo.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.zamlingo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrammarExercise extends AppCompatActivity {

    private TextView grammarQuestionTextView, feedbackTextView, progressTextView;
    private RadioGroup optionsRadioGroup;
    private Button submitAnswerButton;

    private String selectedLanguage;
    private String correctAnswer;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private List<Question> questions = new ArrayList<>();
    private DatabaseReference mDatabase;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_exercise);

        // Initialize views
        grammarQuestionTextView = findViewById(R.id.grammarQuestionTextView);
        feedbackTextView = findViewById(R.id.feedbackTextView);
        progressTextView = findViewById(R.id.progressTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        submitAnswerButton = findViewById(R.id.submitAnswerButton);

        // Firebase setup
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get selected language
        selectedLanguage = getIntent().getStringExtra("selected_language");

        // Load and shuffle questions
        loadPredefinedQuestions();
        Collections.shuffle(questions);

        // Display the first question
        displayQuestion();

        // Submit answer listener
        submitAnswerButton.setOnClickListener(v -> checkAnswer());
    }

    private void loadPredefinedQuestions() {
        if ("Bemba".equalsIgnoreCase(selectedLanguage)) {
            questions.add(new Question("What is the Bemba word for 'Hello'?", new String[]{"Muli shani", "Bwanji", "Shani"}, "Muli shani"));
            questions.add(new Question("How do you say 'Thank you' in Bemba?", new String[]{"Natotela", "Zikomo", "Webale"}, "Natotela"));
            questions.add(new Question("What does 'Cibusa' mean?", new String[]{"Shepherd", "Teacher", "Hunter"}, "Shepherd"));
            questions.add(new Question("Which of these means 'food' in Bemba?", new String[]{"Ubwali", "Nsima", "Inshima"}, "Ubwali"));
            questions.add(new Question("What is the plural of 'umwana' in Bemba?", new String[]{"Abana", "Ifyalo", "Imiti"}, "Abana"));
            questions.add(new Question("How do you say 'Goodbye' in Bemba?", new String[]{"Shalenipo", "Tiyeni", "Bwino"}, "Shalenipo"));
            questions.add(new Question("What is the Bemba word for 'House'?", new String[]{"Ingo", "Nyumba", "Nganda"}, "Nganda"));
            questions.add(new Question("What does 'Ifishimu' mean in Bemba?", new String[]{"Fish", "Birds", "Goats"}, "Fish"));
            questions.add(new Question("What is 'Milk' in Bemba?", new String[]{"Amenshi", "Icibemba", "Amata"}, "Amata"));
            questions.add(new Question("How do you say 'I love you' in Bemba?", new String[]{"Nalikutemwa", "Ndakukonda", "Nakutemwa"}, "Nalikutemwa"));

            questions.add(new Question("How do you say 'How much does this cost?' in Bemba?", new String[]{"Icilishamo shinga?", "Cino shani?", "Ulushamo shinga?"}, "Icilishamo shinga?"));
            questions.add(new Question("What is 'I want to buy this' in Bemba?", new String[]{"Ndofwaya ukuishita", "Ndefwaya ukufuma", "Ndefwaya ukupita"}, "Ndofwaya ukuishita"));
            questions.add(new Question("How do you say 'Is there a discount?' in Bemba?", new String[]{"Cikwete inshita?", "Kwaba icikwama?", "Kwaba icishinka?"}, "Kwaba icikwama?"));
            questions.add(new Question("What does 'Fipwile bwino' mean in Bemba?", new String[]{"Well negotiated", "Finished well", "Done cheaply"}, "Finished well"));
            questions.add(new Question("How do you say 'I don’t have enough money' in Bemba?", new String[]{"Nshakwete ifyashita", "Nshakwete amaka", "Nshakwete impiya shakwena"}, "Nshakwete impiya shakwena"));
            questions.add(new Question("What is the Bemba phrase for 'Can you reduce the price?'", new String[]{"Mungatontonkanya umutengo?", "Mungakoselesha umutengo?", "Mungacite cimbi umutengo?"}, "Mungatontonkanya umutengo?"));
            questions.add(new Question("How do you say 'I will think about it' in Bemba?", new String[]{"Nkatontonkanya", "Nkakoselesha", "Nkalekanya"}, "Nkatontonkanya"));
            questions.add(new Question("What does 'Ino nshita' mean in Bemba?", new String[]{"This time", "That time", "Every time"}, "This time"));
            questions.add(new Question("What is the Bemba word for 'Agreement' in negotiations?", new String[]{"Imyeshi", "Icinkondelo", "Icimbulika"}, "Icinkondelo"));
            questions.add(new Question("How do you say 'Let's finalize the deal' in Bemba?", new String[]{"Twipaye ubushiku", "Twipushe umulandu", "Twikatanye"}, "Twikatanye"));

        } else if ("Nyanja".equalsIgnoreCase(selectedLanguage)) {
            questions.add(new Question("What is the Nyanja word for 'Hello'?", new String[]{"Bwanji", "Muli bwanji", "Muli shani"}, "Bwanji"));
            questions.add(new Question("How do you say 'Thank you' in Nyanja?", new String[]{"Zikomo", "Natotela", "Webale"}, "Zikomo"));
            questions.add(new Question("What does 'Mphunzitsi' mean?", new String[]{"Teacher", "Shepherd", "Hunter"}, "Teacher"));
            questions.add(new Question("Which of these means 'food' in Nyanja?", new String[]{"Chakudya", "Ubwali", "Inshima"}, "Chakudya"));
            questions.add(new Question("What is the plural of 'Mwana' in Nyanja?", new String[]{"Ana", "Ifyalo", "Imiti"}, "Ana"));
            questions.add(new Question("How do you say 'Goodbye' in Nyanja?", new String[]{"Tiyeni", "Shalenipo", "Bwino"}, "Tiyeni"));
            questions.add(new Question("What is the Nyanja word for 'House'?", new String[]{"Nyumba", "Ingo", "Nganda"}, "Nyumba"));
            questions.add(new Question("What does 'Nsomba' mean in Nyanja?", new String[]{"Fish", "Birds", "Goats"}, "Fish"));
            questions.add(new Question("What is 'Milk' in Nyanja?", new String[]{"Mkaka", "Icibemba", "Amata"}, "Mkaka"));
            questions.add(new Question("How do you say 'I love you' in Nyanja?", new String[]{"Ndikukonda", "Nalikutemwa", "Nakukonda"}, "Ndikukonda"));

            questions.add(new Question("How do you say 'How much does this cost?' in Nyanja?", new String[]{"Zimenezi zingati?", "Zimenezi zili bwanji?", "Zimenezi zing'ono?"}, "Zimenezi zingati?"));
            questions.add(new Question("What is 'I want to buy this' in Nyanja?", new String[]{"Ndikufuna kugula izi", "Ndikufuna kuyenda izi", "Ndikufuna kubweza izi"}, "Ndikufuna kugula izi"));
            questions.add(new Question("How do you say 'Is there a discount?' in Nyanja?", new String[]{"Kodi pali kuchepa?", "Kodi pali malonda?", "Kodi pali ntchito?"}, "Kodi pali kuchepa?"));
            questions.add(new Question("What does 'Chimaliziro chabwino' mean in Nyanja?", new String[]{"Well negotiated", "Finished well", "Done cheaply"}, "Finished well"));
            questions.add(new Question("How do you say 'I don’t have enough money' in Nyanja?", new String[]{"Ndilibe ndalama zokwanira", "Ndilibe ndalama zambiri", "Ndilibe ndalama zonse"}, "Ndilibe ndalama zokwanira"));
            questions.add(new Question("What is the Nyanja phrase for 'Can you reduce the price?'", new String[]{"Kodi mungachepetse mtengo?", "Kodi mungalitse mtengo?", "Kodi mungasinthe mtengo?"}, "Kodi mungachepetse mtengo?"));
            questions.add(new Question("How do you say 'I will think about it' in Nyanja?", new String[]{"Ndikaganiza kaye", "Ndikapuma kaye", "Ndikakambirana"}, "Ndikaganiza kaye"));
            questions.add(new Question("What does 'Nthawi ino' mean in Nyanja?", new String[]{"This time", "That time", "Every time"}, "This time"));
            questions.add(new Question("What is the Nyanja word for 'Agreement' in negotiations?", new String[]{"Mgwirizano", "Pangano", "Chibvomerezo"}, "Mgwirizano"));
            questions.add(new Question("How do you say 'Let's finalize the deal' in Nyanja?", new String[]{"Tiyeni timalize pangano", "Tiyeni tikambirane bwino", "Tiyeni titseke ntchito"}, "Tiyeni timalize pangano"));
        }
    }

    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        grammarQuestionTextView.setText(currentQuestion.getQuestion());
        optionsRadioGroup.removeAllViews();
        correctAnswer = currentQuestion.getCorrectAnswer();

        for (String option : currentQuestion.getOptions()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            radioButton.setTextColor(Color.BLACK);
            optionsRadioGroup.addView(radioButton);
        }

        feedbackTextView.setVisibility(View.GONE);
        progressTextView.setText("Progress: " + (currentQuestionIndex + 1) + "/10 | Score: " + score);
        submitAnswerButton.setEnabled(true);
    }

    private void checkAnswer() {
        int selectedOptionId = optionsRadioGroup.getCheckedRadioButtonId();
        if (selectedOptionId == -1) {
            Toast.makeText(this, "Please select an answer.", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedOptionId);
        String selectedAnswer = selectedRadioButton.getText().toString();

        if (selectedAnswer.equals(correctAnswer)) {
            feedbackTextView.setText("Correct!");
            feedbackTextView.setTextColor(Color.GREEN);
            score++;
        } else {
            feedbackTextView.setText("Incorrect. Correct answer: " + correctAnswer);
            feedbackTextView.setTextColor(Color.RED);
        }

        feedbackTextView.setVisibility(View.VISIBLE);
        submitAnswerButton.setEnabled(false);

        // Automatically move to the next question after a short delay
        new Handler().postDelayed(this::moveToNextQuestion, 2000);
    }

    private void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < 10 && currentQuestionIndex < questions.size()) {
            displayQuestion();
        } else {
            saveProgressToFirebase();
            Toast.makeText(this, "Quiz Complete! Final Score: " + score + "/10", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void saveProgressToFirebase() {
        mDatabase.child("users").child(userId).child("grammar_exercise_score").setValue(score)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Progress saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to save progress: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private static class Question {
        private final String question;
        private final String[] options;
        private final String correctAnswer;

        public Question(String question, String[] options, String correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public String[] getOptions() {
            return options;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
