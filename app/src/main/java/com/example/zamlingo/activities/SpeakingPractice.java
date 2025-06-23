package com.example.zamlingo.activities;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zamlingo.R;

import java.util.Locale;

public class SpeakingPractice extends AppCompatActivity implements OnInitListener {

    private TextToSpeech textToSpeech;
    private EditText userInputEditText;
    private TextView pronunciationFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaking_practice);

        // Initialize the TextToSpeech instance
        textToSpeech = new TextToSpeech(this, this);

        userInputEditText = findViewById(R.id.userInputEditText);
        pronunciationFeedback = findViewById(R.id.pronunciationFeedback);
        Button btnSpeak = findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(v -> onSpeakButtonClick());
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language for speech synthesis
            int langResult = textToSpeech.setLanguage(Locale.ENGLISH);
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                pronunciationFeedback.setText("Language is not supported.");
            }
        } else {
            pronunciationFeedback.setText("Text-to-speech initialization failed.");
        }
    }

    public void onSpeakButtonClick() {
        String textToPronounce = userInputEditText.getText().toString();
        if (!textToPronounce.isEmpty()) {
            pronunciationFeedback.setText("Pronouncing: " + textToPronounce);
            textToSpeech.speak(textToPronounce, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            pronunciationFeedback.setText("Please type something.");
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
