package de.jectrum.VideoDownload;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javautils.DownloadManager.DownloadListener;
import javautils.DownloadManager.JavaDownloader;

/**
 *
 * @author Max
 */
public class VideoInfo {

    String title, downloadLink, thumbnailLink;
    /**
     * Length in Seconds
     */
    long length;
    /**
     * Size in MB
     */
    long size;
    Hoster hoster;

    private long downloadedBytes = 0;

    /**
     * @return the thumbnailLink
     */
    public String getThumbnailLink() {
        return thumbnailLink;
    }

    /**
     * @param thumbnailLink
     *            the thumbnailLink to set
     */
    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the downloadLink
     */
    public String getDownloadLink() {
        return downloadLink;
    }

    /**
     * @param downloadLink
     *            the downloadLink to set
     */
    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    /**
     * @return the length
     */
    public long getLength() {
        return length;
    }

    /**
     * @param length
     *            the length to set
     */
    public void setLength(long length) {
        this.length = length;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @return the hoster
     */
    public Hoster getHoster() {
        return hoster;
    }

    /**
     * @param hoster
     *            the hoster to set
     */
    public void setHoster(Hoster hoster) {
        this.hoster = hoster;
    }

    public void downloadVideo() throws MalformedURLException{
        downloadVideo(new File(title + ".mp4"));
    }

    public void downloadVideo(File f) throws MalformedURLException{
        downloadVideo(f,null);
    }

    public void downloadVideo(File f, DownloadListener dl) throws MalformedURLException{
        DownloadListener[] dls = new DownloadListener[dl==null ? 1 : 2];
        dls[0] = new DownloadListener(){

            @Override
            public void downloadTileFile(String name, long l) {
                downloadedBytes = downloadedBytes + l;
            }


            @Override
            public void startedDownloading(String name, long length) {
                size = length / 1024 / 1024;
            }

            @Override
            public void downloadedFile(File f) {
                System.out.println("Finished: " + downloadedBytes);
                downloadedBytes = size * 1024;
            }

        };
        if(dl!=null){
            dls[1] = dl;
        }
        JavaDownloader.downloadFileAsynchronously(new URL(downloadLink), f, dls);
    }

    public long getDownloadedBytes(){
        return downloadedBytes;
    }

    public double getDownloadProgress(){
        return (((double)downloadedBytes) / ((double)(size * 1024.0 * 1024.0))) * 100.0;
    }

}
