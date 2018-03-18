package de.jectrum.VideoDownload.Actions;

import java.util.HashMap;

import com.google.gson.JsonObject;

import de.jectrum.VideoDownload.VideoDownload;
import de.jectrum.VideoDownload.Actions.Results.FetchVideoResult;
import de.jectrum.VideoDownload.Actions.Results.Result;

public class FetchVideo extends Action {

    VideoDownload vd;
    String url;

    public FetchVideo(VideoDownload videoDownload, String url) {
        this.vd = videoDownload;
        this.url = url;
    }

    @Override
    protected Request buildRequest() {
        Request r = new Request("https://api.video-download.online/v1/download/create");
        r.addVariable("key", vd.getKey());
        r.addVariable("url", url);
        r.addVariable("force", "download");
        return r;
    }

    @Override
    protected Result buildResult(Request r) {
        HashMap<String, String> variables = new HashMap<String, String>();
        JsonObject jo = r.getResult().getAsJsonObject();
        if(jo.has("title")){
        variables.put("title", jo.get("title").getAsString());
        }
        variables.put("downloadLink", jo.get("download").getAsString());
        if (jo.has("thumbnail")) {
            variables.put("thumbnailLink", jo.get("thumbnail").getAsString());
        }
        FetchVideoResult fvr = new FetchVideoResult(variables);
        return fvr;
    }

}
