package com.example.zamlingo.models;

public class QuestionResponse {
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public static class Question {
        private String text;
        private String correctAnswer;
        private String[] options;

        public String getText() {
            return text;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public String[] getOptions() {
            return options;
        }
    }
}
