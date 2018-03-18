package de.jectrum.VideoDownload.Actions.Results;

import com.google.gson.JsonElement;

public abstract class Result {

    private JsonElement jo;

    Result(JsonElement jo) {
        this.jo = jo;
    }

    public JsonElement getResult() {
        return jo;
    }

}