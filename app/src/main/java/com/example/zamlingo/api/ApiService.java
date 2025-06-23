package com.example.zamlingo.api;

import com.example.zamlingo.models.QuestionResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("gemini-1.5-flash-latest:generateContent")
    Call<QuestionResponse> getGrammarQuestion(@Query("selected_language") String selectedLanguage, @Query("key") String apiKey);
}
