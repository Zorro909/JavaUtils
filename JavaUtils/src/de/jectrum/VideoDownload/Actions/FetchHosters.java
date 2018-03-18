package de.jectrum.VideoDownload.Actions;


import de.jectrum.VideoDownload.VideoDownload;
import de.jectrum.VideoDownload.Actions.Results.FetchHostersResult;
import de.jectrum.VideoDownload.Actions.Results.Result;

public class FetchHosters extends Action {

    VideoDownload vd;

    public FetchHosters(VideoDownload vd) {
        this.vd = vd;
    }

    @Override
    protected Request buildRequest() {
        Request r = new Request("https://api.video-download.online/v1/list");
        r.addVariable("key", vd.getKey());
        return r;
    }

    @Override
    protected Result buildResult(Request r) {
        Result result = new FetchHostersResult(r.getResult());
        return result;
    }

}