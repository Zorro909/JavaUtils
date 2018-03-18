package de.jectrum.VideoDownload;

import java.util.ArrayList;

import de.jectrum.VideoDownload.Actions.FetchHosters;
import de.jectrum.VideoDownload.Actions.FetchVideo;
import de.jectrum.VideoDownload.Actions.Results.FetchHostersResult;
import de.jectrum.VideoDownload.Actions.Results.FetchVideoResult;
import de.jectrum.VideoDownload.Exceptions.HosterNotFoundException;

public class VideoDownload {

    private ArrayList<CHoster> preferedHosters = new ArrayList<CHoster>();
    private static ArrayList<Hoster> hoster = new ArrayList<Hoster>();
    private String key;
    private boolean fetchedHosters = false;

    public VideoDownload(String key) {
        this(key, true);
    }

    public VideoDownload(String key, boolean fetchHosters) {
        this.key = key;
        if (fetchHosters) {
            fetchHosters();
        }
    }

    public void addPreferredHoster(CHoster customHoster) {
        preferedHosters.add(customHoster);
    }

    public boolean fetchHosters() {
        try {
            FetchHosters fh = new FetchHosters(this);
            fh.fetch();
            FetchHostersResult r = (FetchHostersResult) fh.getResult();
            hoster = r.getHosters();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public VideoInfo fetchVideoInfo(String url) throws HosterNotFoundException {
        /*
        String c = url;
        if (c.startsWith("http://")) {
            c = c.substring(7);
        } else if (c.startsWith("https://")) {
            c = c.substring(8);
        }
        String hoster = c.split("/", 2)[0];
        if (hoster.contains(":")) {
            hoster = hoster.split(":")[0];
        }
        boolean found = false;
        CHoster prefered = null;

        // Only For Easyness
        testForHost: while (!found) {
            for (CHoster ch : this.preferedHosters) {
                for (String domain : ch.getHoster().getDomains()) {
                    if (domain.equalsIgnoreCase(hoster)) {
                        found = true;
                        prefered = ch;
                        break testForHost;
                    }
                }
            }
            for (Hoster h : this.hoster) {
                for (String domain : h.getDomains()) {
                    if (domain.equalsIgnoreCase(hoster)) {
                        found = true;
                        break testForHost;
                    }
                }
            }
        }
        if (!found) { throw new HosterNotFoundException(url); }
        */
        VideoInfo info = null;
        /*if (prefered != null) {
            CHoster ho = prefered.fromURL(url);
            ho.fetch();
            FetchVideoResult result = (FetchVideoResult) ho.getResult();
            info = result.getVideoInfo();
        } else {*/
            FetchVideo fv = new FetchVideo(this, url);
            fv.fetch();
            FetchVideoResult result = (FetchVideoResult) fv.getResult();
            info = result.getVideoInfo();
        //}
        return info;
    }

    public String getKey() {
        return key;
    }

    public static Hoster getHosterByDomain(String string) {
        Hoster ho = null;
        searchHost: for (Hoster h : hoster) {
            for (String domain : h.getDomains()) {
                if (domain.equalsIgnoreCase(string)) {
                    ho = h;
                    break searchHost;
                }
            }
        }
        return ho;
    }

}
