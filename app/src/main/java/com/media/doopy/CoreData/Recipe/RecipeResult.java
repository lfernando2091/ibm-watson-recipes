package com.media.doopy.CoreData.Recipe;

import com.media.doopy.Controller.Component;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecipeResult extends Component {
    private String question;
    private int from;
    private int to;
    private List<Component> hits;

    public RecipeResult() {
    }

    public RecipeResult(String question, int from, int to, List<Component> hits) {
        this.question = question;
        this.from = from;
        this.to = to;
        this.hits = hits;
    }

    @Override
    public List<Component> JSONList(JSONArray jsonObject) throws JSONException {
        return null;
    }

    @Override
    public Component JSONValues(JSONObject jsonObject) throws JSONException {
        return new RecipeResult(
                jsonObject.optString("q"),
                jsonObject.optInt("from"),
                jsonObject.optInt("to"),
                new Hits().JSONList(jsonObject.optJSONArray("hits"))
        );
    }

    @Override
    public String toString() {
        return "RecipeResult{" +
                "question='" + question + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", hits=" + hits +
                '}';
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public List<Component> getHits() {
        return hits;
    }

    public void setHits(List<Component> hits) {
        this.hits = hits;
    }
}
