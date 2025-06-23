package com.example.zamlingo.rotrofi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GeminiApi {

    @Headers("Content-Type: application/json")
    @POST("https://api.gemini.com/translate")  // Replace with the actual Gemini API endpoint
    Call<TranslationResponse> translateText(@Body TranslationRequest request);
}
