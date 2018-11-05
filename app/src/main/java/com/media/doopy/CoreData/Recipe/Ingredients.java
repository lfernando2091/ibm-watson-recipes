package com.media.doopy.CoreData.Recipe;

import com.media.doopy.Controller.Component;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ingredients extends Component {
    public String text;
    public double weight;

    public Ingredients() {
    }

    public Ingredients(String text, double weight) {
        this.text = text;
        this.weight = weight;
    }

    @Override
    public List<Component> JSONList(JSONArray jsonObject) throws JSONException {
        List<Component> ingredients = new ArrayList<Component>();
        for (int i = 0; i < jsonObject.length(); i++){
            ingredients.add(new Ingredients(
                    ((JSONObject)jsonObject.get(i)).optString("text"),
                    ((JSONObject)jsonObject.get(i)).optDouble("weight")
            ));
        }
        return ingredients;
    }

    @Override
    public Component JSONValues(JSONObject jsonObject) throws JSONException {
        return null;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "text='" + text + '\'' +
                ", weight=" + weight +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
