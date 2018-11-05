package com.media.doopy.CoreData.Recipe;

import com.media.doopy.Controller.Component;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Recipe extends Component{
    private String uri;
    private String label;
    private String image;
    private String source;
    private String url;
    private String shareAs;
    private int yield;
    private List<String> dietLabels;
    private List<String> healthLabels;
    private List<String> cautions;
    private List<String> ingredientLines;
    private List<Component> ingredients;
    private double calories;
    private double totalWeight;
    private List<Component> totalNutrients;
    private List<Component> totalDaily;
    private List<Component> digest;

    public Recipe(String uri, String label, String image, String source, String url, String shareAs, int yield, List<String> dietLabels, List<String> healthLabels, List<String> cautions, List<String> ingredientLines, List<Component> ingredients, double calories, double totalWeight, List<Component> totalNutrients, List<Component> totalDaily, List<Component> digest) {
        this.uri = uri;
        this.label = label;
        this.image = image;
        this.source = source;
        this.url = url;
        this.shareAs = shareAs;
        this.yield = yield;
        this.dietLabels = dietLabels;
        this.healthLabels = healthLabels;
        this.cautions = cautions;
        this.ingredientLines = ingredientLines;
        this.ingredients = ingredients;
        this.calories = calories;
        this.totalWeight = totalWeight;
        this.totalNutrients = totalNutrients;
        this.totalDaily = totalDaily;
        this.digest = digest;
    }

    public Recipe() {
    }

    @Override
    public List<Component> JSONList(JSONArray jsonObject) throws JSONException {
        return null;
    }

    @Override
    public Component JSONValues(JSONObject jsonObject) throws JSONException {
        return new Recipe(
                jsonObject.optString("uri"),
                jsonObject.optString("label"),
                jsonObject.optString("image"),
                jsonObject.optString("source"),
                jsonObject.optString("url"),
                jsonObject.optString("shareAs"),
                jsonObject.optInt("yield"),
                null,
                null,
                null,
                null,
                new Ingredients().JSONList(jsonObject.optJSONArray("ingredients")),
                jsonObject.optDouble("calories"),
                jsonObject.optDouble("totalWeight"),
                null,
                null,
                null
        );
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "uri='" + uri + '\'' +
                ", label='" + label + '\'' +
                ", image='" + image + '\'' +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                ", shareAs='" + shareAs + '\'' +
                ", yield=" + yield +
                ", dietLabels=" + dietLabels +
                ", healthLabels=" + healthLabels +
                ", cautions=" + cautions +
                ", ingredientLines=" + ingredientLines +
                ", ingredients=" + ingredients +
                ", calories=" + calories +
                ", totalWeight=" + totalWeight +
                ", totalNutrients=" + totalNutrients +
                ", totalDaily=" + totalDaily +
                ", digest=" + digest +
                '}';
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareAs() {
        return shareAs;
    }

    public void setShareAs(String shareAs) {
        this.shareAs = shareAs;
    }

    public int getYield() {
        return yield;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    public List<String> getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    public List<String> getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(List<String> healthLabels) {
        this.healthLabels = healthLabels;
    }

    public List<String> getCautions() {
        return cautions;
    }

    public void setCautions(List<String> cautions) {
        this.cautions = cautions;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public List<Component> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Component> ingredients) {
        this.ingredients = ingredients;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public List<Component> getTotalNutrients() {
        return totalNutrients;
    }

    public void setTotalNutrients(List<Component> totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    public List<Component> getTotalDaily() {
        return totalDaily;
    }

    public void setTotalDaily(List<Component> totalDaily) {
        this.totalDaily = totalDaily;
    }

    public List<Component> getDigest() {
        return digest;
    }

    public void setDigest(List<Component> digest) {
        this.digest = digest;
    }
}
