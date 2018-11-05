package com.media.doopy.CoreData.IBM;

import com.media.doopy.Controller.Component;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Images extends Component {
    private List<Component> classifiers;
    private String image;

    @Override
    public List<Component> JSONList(JSONArray jsonObject) throws JSONException {
        List<Component> images = new ArrayList<Component>();
        for (int i = 0; i < jsonObject.length(); i++){
            images.add(new Images(
                    new Classifiers().JSONList(((JSONObject)jsonObject.get(i)).optJSONArray("classifiers")),
                    ((JSONObject)jsonObject.get(i)).optString("image")
            ));
        }
        return images;
    }

    @Override
    public Component JSONValues(JSONObject jsonObject) throws JSONException {
        return null;
    }

    public Images(){

    }

    @Override
    public String toString() {
        return "Images{" +
                "classifiers=" + classifiers +
                ", image='" + image + '\'' +
                '}';
    }

    private Images(List<Component> classifiers, String image) {
        JSON(classifiers, image);
    }

    private void JSON(List<Component> classifiers, String image) {
        this.classifiers = classifiers;
        this.image = image;
    }


    public List<Component> getClassifiers() {
        return classifiers;
    }

    public void setClassifiers(List<Component> classifiers) {
        this.classifiers = classifiers;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
