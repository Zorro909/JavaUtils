package de.jectrum.VideoDownload.Actions;

import de.jectrum.VideoDownload.Actions.Results.Result;

public abstract class Action {

    private Request r;

    protected abstract Request buildRequest();

    public boolean fetch() {
        try {
            r = buildRequest();
            r.fetch();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Result getResult() {
        return buildResult(r);
    }

    protected abstract Result buildResult(Request r);

}
