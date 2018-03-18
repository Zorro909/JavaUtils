package de.jectrum.VideoDownload;

import de.jectrum.VideoDownload.Actions.Action;

public abstract class CHoster extends Action {

    private Hoster h;

    protected CHoster(Hoster h) {
        this.h = h;
    }

    public Hoster getHoster() {
        return h;
    }
    
    public abstract CHoster fromURL(String url);

}
