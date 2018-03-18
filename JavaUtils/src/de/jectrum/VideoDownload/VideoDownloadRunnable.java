package de.jectrum.VideoDownload;

import java.io.File;
import java.net.MalformedURLException;

import de.jectrum.VideoDownload.Exceptions.HosterNotFoundException;

public class VideoDownloadRunnable {

    public static void main(String[] args) throws HosterNotFoundException, MalformedURLException, InterruptedException {
        System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        if(args.length<2){
            System.out.println("Usage: ");
            System.out.println(" java -jar " + VideoDownloadRunnable.class.getProtectionDomain().getCodeSource().getLocation().getFile().substring(1) + " [KEY] [LINK] {FilePath}");
            System.out.println("    [] = Necessary");
            System.out.println("    {} = Optional");
        }else{
            VideoDownload vd = new VideoDownload(args[0], false);
            VideoInfo vi = vd.fetchVideoInfo(args[1]);
            System.out.println("Starting download of: " + vi.getTitle());
            vi.downloadVideo(new File(args.length>2 ? args[2] : vi.getTitle() + ".mp4"));
            while(vi.getDownloadProgress()!=100.0){
                Thread.sleep(1000L);
                System.out.println("Progress: " + vi.getDownloadProgress() + "% (" + vi.getDownloadedBytes() / 1024 / 1024 +"MB of " + vi.getSize() + "MB)");
            }
        }
    }

}
