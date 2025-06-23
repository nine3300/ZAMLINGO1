package com.example.zamlingo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zamlingo.R;
import com.example.zamlingo.utils.DataUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class ContextualLearningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contextual_learning);

        EditText userQueryInput = findViewById(R.id.userQueryInput);
        TextView searchResults = findViewById(R.id.searchResults);
        Button searchButton = findViewById(R.id.searchButton);

        JSONObject jsonData = DataUtils.loadJsonFromRaw(this, R.raw.bemba_nyanja_data);

        searchButton.setOnClickListener(v -> {
            String query = userQueryInput.getText().toString().trim().toLowerCase();
            searchResults.setText(searchForInformation(jsonData, query));
        });
    }

    private String searchForInformation(JSONObject data, String query) {
        try {
            JSONArray bembaArray = data.getJSONArray("bemba");
            JSONArray nyanjaArray = data.getJSONArray("nyanja");

            StringBuilder result = new StringBuilder();

            result.append(findMatchingTopics(bembaArray, "Bemba", query));
            result.append(findMatchingTopics(nyanjaArray, "Nyanja", query));

            return result.length() > 0 ? result.toString() : "No information found for: " + query;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving information.";
        }
    }

    private String findMatchingTopics(JSONArray array, String language, String query) throws Exception {
        StringBuilder matches = new StringBuilder();
        for (int i = 0; i < array.length(); i++) {
            JSONObject topic = array.getJSONObject(i);
            if (topic.getString("topic").toLowerCase().contains(query)) {
                matches.append(language).append(" - ").append(topic.getString("topic")).append(":\n");
                matches.append(topic.getString("details")).append("\n\n");
            }
        }
        return matches.toString();
    }
}
