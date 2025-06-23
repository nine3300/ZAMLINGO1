package com.example.zamlingo.utils;

public class GrammarUtils {

    public static void getGrammarQuestion(String language, GrammarQuestionCallback callback) {
        // Simulate a dynamic question-fetching mechanism
        String question = "What is the correct form of ...?";
        String correctAnswer = "Correct Answer";
        String[] options = {"Option 1", "Correct Answer", "Option 3", "Option 4"};
        callback.onSuccess(question, correctAnswer, options);
    }

    public interface GrammarQuestionCallback {
        void onSuccess(String question, String correctAnswer, String[] options);
        void onFailure(Exception e);
    }

}
