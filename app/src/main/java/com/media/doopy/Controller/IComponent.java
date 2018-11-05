package com.media.doopy.Controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface IComponent {
    List<Component> JSONList(JSONArray jsonObject) throws JSONException;
    Component JSONValues(JSONObject jsonObject) throws JSONException;
}
