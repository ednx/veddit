package com.eovalencia.veddit.source.restApi.deserialize;

import android.util.Log;

import com.eovalencia.veddit.model.SubReddit;
import com.eovalencia.veddit.source.restApi.model.Constants;
import com.eovalencia.veddit.source.restApi.model.SubRedditResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Eovalencia on 23/01/17.
 */

public class RedditDeserializer implements JsonDeserializer<SubRedditResponse> {

    @Override
    public SubRedditResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        SubRedditResponse subRedditResponse = gson.fromJson(json, SubRedditResponse.class);
        JsonObject jsonObjectResponseData = json.getAsJsonObject().getAsJsonObject(Constants.RESPONSE_DATA);
        JsonArray jsonArrayResponseChildren = jsonObjectResponseData.getAsJsonArray(Constants.RESPONSE_ARRAY_CHILDREN);
        subRedditResponse.setSubRedditsResponse(deserializeSubRedditToJson(jsonArrayResponseChildren));
        return subRedditResponse;
    }

    private ArrayList<SubReddit> deserializeSubRedditToJson(JsonArray jsonArrayResponseChildren){
        ArrayList<SubReddit> subReddits = new ArrayList<> ();
        for (int i = 0; i < jsonArrayResponseChildren.size(); i++) {
            JsonObject subRedditResponseDataObject = jsonArrayResponseChildren.get(i).getAsJsonObject();
            JsonObject jsonObject = subRedditResponseDataObject.getAsJsonObject(Constants.RESPONSE_DATA);
            String itemId = jsonObject.get("id").getAsString();
            String header_img = jsonObject.get("header_img").isJsonNull() ? null : jsonObject.get("header_img").getAsString();
            String title = jsonObject.get("title").getAsString() != null ? jsonObject.get("title").getAsString() : "";
            String public_description = jsonObject.get("public_description").getAsString() != null ? jsonObject.get("public_description").getAsString() : "";
            long subscribers = jsonObject.get("subscribers").getAsLong();
            long created = jsonObject.get("created").getAsLong();
            String icon_img = jsonObject.get("icon_img").getAsString() != null ? jsonObject.get("icon_img").getAsString() : "";
            String display_name = jsonObject.get("display_name").getAsString() != null ? jsonObject.get("display_name").getAsString() : "";
            String description = jsonObject.get("description").getAsString() != null ? jsonObject.get("description").getAsString() : "";
            String url = jsonObject.get("url").getAsString() != null ? jsonObject.get("url").getAsString() : "";
            SubReddit aux = new SubReddit(itemId, header_img, title, public_description, subscribers, created, icon_img, display_name, description, url);
            subReddits.add(aux);
        }
        return subReddits;
    }
}
