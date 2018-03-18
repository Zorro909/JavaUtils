package de.jectrum.VideoDownload.Actions.Results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.jectrum.VideoDownload.Hoster;
import de.jectrum.VideoDownload.STATUS;

public class FetchHostersResult extends Result {

    ArrayList<Hoster> hosters = new ArrayList<Hoster>();

    public FetchHostersResult(JsonElement jo) {
        super(jo);
        Iterator<JsonElement> i = jo.getAsJsonObject().get("items").getAsJsonArray().iterator();
        while (i.hasNext()) {
            JsonObject je = i.next().getAsJsonObject();
            String name = je.get("name").getAsString();
            STATUS status = STATUS.valueOf(je.get("status").getAsString());
            ArrayList<String> domains = new ArrayList<String>();
            Iterator<JsonElement> d = je.get("domains").getAsJsonArray().iterator();
            while (d.hasNext()) {
                JsonElement jele = d.next();
                domains.add(jele.getAsString());
            }
            String[] dom = domains.toArray(new String[] {});
            boolean adult = false;
            if (je.has("adult")) {
                adult = je.get("adult").getAsBoolean();
            }
            hosters.add(new Hoster(name, dom, status, adult));
        }
    }

    public ArrayList<Hoster> getHosters() {
        return hosters;
    }

}