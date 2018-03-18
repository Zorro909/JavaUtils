package de.jectrum.VideoDownload.Actions.Results;

import java.util.HashMap;

import com.google.gson.JsonObject;

import de.jectrum.VideoDownload.VideoInfo;

public class FetchVideoResult extends Result {

    private VideoInfo vi;

    /**
     * 
     * @param variables
     *            Variables as seen at VideoInfo
     * @see VideoInfo#title
     * @see VideoInfo#downloadLink
     * @see VideoInfo#thumbnailLink
     * @see VideoInfo#length
     * @see VideoInfo#size
     */
    public FetchVideoResult(HashMap<String, String> variables) {
        super(new JsonObject());
        vi = new VideoInfo();
        vi.setTitle(variables.get("title"));
        vi.setDownloadLink(variables.get("downloadLink"));
        vi.setThumbnailLink(variables.get("thumbnailLink"));
        if (variables.containsKey("length")) {
            vi.setLength(Long.valueOf(variables.get("length")));
        }
        if (variables.containsKey("size")) {
            vi.setSize(Long.valueOf(variables.get("size")));
        }
    }

    public VideoInfo getVideoInfo() {
        return vi;
    }

}
