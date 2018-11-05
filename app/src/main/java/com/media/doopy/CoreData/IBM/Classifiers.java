package com.media.doopy.CoreData.IBM;

import com.media.doopy.Controller.Component;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Classifiers extends Component {
    private List<Component> classes;
    private String classifierId;
    private String name;

    public Classifiers(){

    }

    public Classifiers(List<Component> classes, String classifierId, String name) {
        JSON(classes, classifierId, name);
    }

    private void JSON(List<Component> classes, String classifierId, String name) {
        this.classes = classes;
        this.classifierId = classifierId;
        this.name = name;
    }

    @Override
    public List<Component> JSONList(JSONArray jsonObject) throws JSONException {
        List<Component> classifiers = new ArrayList<Component>();
        for (int i = 0; i < jsonObject.length(); i++){
            classifiers.add(new Classifiers(
                    new Classes().JSONList(((JSONObject)jsonObject.get(i)).optJSONArray("classes")),
                    ((JSONObject)jsonObject.get(i)).optString("classifier_id"),
                    ((JSONObject)jsonObject.get(i)).optString("name")
            ));
        }
        return classifiers;
    }

    @Override
    public Component JSONValues(JSONObject jsonObject) throws JSONException {
        return null;
    }

    @Override
    public String toString() {
        return "Classifiers{" +
                "classes=" + classes +
                ", classifierId='" + classifierId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public List<Component> getClasses() {
        return classes;
    }

    public void setClasses(List<Component> classes) {
        this.classes = classes;
    }

    public String getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(String classifierId) {
        this.classifierId = classifierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
