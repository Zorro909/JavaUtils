package de.jectrum.VideoDownload.Exceptions;

public class HosterNotFoundException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6827266311324984385L;

    public HosterNotFoundException(String url) {
        super("The Hoster for the URL " + url
                        + " could not be defined or is not supported by Video-Download.online!");
    }

}
