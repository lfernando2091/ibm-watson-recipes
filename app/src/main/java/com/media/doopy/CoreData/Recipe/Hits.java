package com.media.doopy.CoreData.Recipe;

import com.media.doopy.Controller.Component;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Hits extends Component {
    private Component recipe;
    private boolean bookmarked;
    private  boolean bought;

    public Hits() {
    }

    public Hits(Component recipe, boolean bookmarked, boolean bought) {
        this.recipe = recipe;
        this.bookmarked = bookmarked;
        this.bought = bought;
    }

    @Override
    public List<Component> JSONList(JSONArray jsonObject) throws JSONException {
        List<Component> hits = new ArrayList<Component>();
        for (int i = 0; i < jsonObject.length(); i++){
            hits.add(new Hits(
                    new Recipe().JSONValues(((JSONObject)jsonObject.get(i)).optJSONObject("recipe")),
                    ((JSONObject)jsonObject.get(i)).optBoolean("bookmarked"),
                    ((JSONObject)jsonObject.get(i)).optBoolean("bought")
            ));
        }
        return hits;
    }

    @Override
    public Component JSONValues(JSONObject jsonObject) throws JSONException {
        return null;
    }

    @Override
    public String toString() {
        return "Hits{" +
                "recipe=" + recipe +
                ", bookmarked=" + bookmarked +
                ", bought=" + bought +
                '}';
    }

    public Component getRecipe() {
        return recipe;
    }

    public void setRecipe(Component recipe) {
        this.recipe = recipe;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
