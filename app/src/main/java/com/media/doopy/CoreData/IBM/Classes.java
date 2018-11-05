package com.media.doopy.CoreData.IBM;

import com.media.doopy.Controller.Component;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Classes extends Component {
    private String tClass;
    private double score;
    private String typeHierarchy;

    public Classes(){

    }

    public Classes(String tClass, double score, String typeHierarchy) {
        JSON(tClass, score, typeHierarchy);
    }

    private void JSON(String tClass, double score, String typeHierarchy) {
        this.tClass = tClass;
        this.score = score;
        this.typeHierarchy = typeHierarchy;
    }

    @Override
    public List<Component> JSONList(JSONArray jsonObject) throws JSONException {
        List<Component> classes = new ArrayList<Component>();
        for (int i = 0; i < jsonObject.length(); i++){
            classes.add(new Classes(
                    ((JSONObject)jsonObject.get(i)).optString("class"),
                    ((JSONObject)jsonObject.get(i)).optDouble("score"),
                    ((JSONObject)jsonObject.get(i)).optString("type_hierarchy")
            ));
        }
        return classes;
    }

    @Override
    public Component JSONValues(JSONObject jsonObject) throws JSONException {
        return null;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "tClass='" + tClass + '\'' +
                ", score=" + score +
                ", typeHierarchy='" + typeHierarchy + '\'' +
                '}';
    }

    public String gettClass() {
        return tClass;
    }

    public void settClass(String tClass) {
        this.tClass = tClass;
    }

    public double getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getTypeHierarchy() {
        return typeHierarchy;
    }

    public void setTypeHierarchy(String typeHierarchy) {
        this.typeHierarchy = typeHierarchy;
    }
}
