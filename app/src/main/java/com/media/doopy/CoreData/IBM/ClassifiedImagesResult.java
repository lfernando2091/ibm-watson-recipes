package com.media.doopy.CoreData.IBM;

import com.media.doopy.Controller.Component;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ClassifiedImagesResult extends Component {
    private int customClasses;
    private List<Component> images;
    private int imagesProcessed;

    public ClassifiedImagesResult(){

    }

    private ClassifiedImagesResult(int customClasses, List<Component> images, int imagesProcessed) {
        JSON(customClasses, images, imagesProcessed);
    }

    private void JSON(int customClasses, List<Component> images, int imagesProcessed) {
        this.customClasses = customClasses;
        this.images = images;
        this.imagesProcessed = imagesProcessed;
    }

    @Override
    public List<Component> JSONList(JSONArray jsonObject) throws JSONException {
        return null;
    }

    @Override
    public Component JSONValues(JSONObject jsonObject) throws JSONException {
        return new ClassifiedImagesResult(
                jsonObject.optInt("custom_classes"),
                new Images().JSONList(jsonObject.optJSONArray("images")),
                jsonObject.optInt("images_processed")
        );
    }

    @Override
    public String toString() {
        return "ClassifiedImagesResult{" +
                "customClasses=" + customClasses +
                ", images=" + images +
                ", imagesProcessed=" + imagesProcessed +
                '}';
    }

    public int getCustomClasses() {
        return customClasses;
    }

    public void setCustomClasses(int customClasses) {
        this.customClasses = customClasses;
    }

    public List<Component> getImages() {
        return images;
    }

    public void setImages(List<Component> images) {
        this.images = images;
    }

    public int getImagesProcessed() {
        return imagesProcessed;
    }

    public void setImagesProcessed(int imagesProcessed) {
        this.imagesProcessed = imagesProcessed;
    }
}
