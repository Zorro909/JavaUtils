package de.jectrum.VideoDownload;

public class Hoster {

    String name;
    String[] domains;
    STATUS state;
    boolean adult = false;

    public Hoster(String name, String[] domain, STATUS status) {
        this.name = name;
        this.domains = domain;
        this.state = status;
    }

    public Hoster(String name, String[] domain, STATUS status, boolean adult) {
        this(name, domain, status);
        this.adult = adult;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the domains
     */
    public String[] getDomains() {
        return domains;
    }

    /**
     * @return the state
     */
    public STATUS getState() {
        return state;
    }

    /**
     * @return the adult
     */
    public boolean isAdult() {
        return adult;
    }

}
