package javautils.DownloadManager;

import java.io.File;

/**
 * The Interface of an DownloadListener for a Download from {@link JavaDownloader}s
 * @see JavaUtils
 * @see JavaDownloader
 * @author Max
 *
 */
public interface DownloadListener {

	/**
	 * Occures when a Part were downloaded (Mostly 1024 Bytes)
	 * @param name The Name of the File
	 * @param length The Length of the Downloaded Part
	 */
	public void downloadTileFile(String name,long length);

	/**
	 * Occures when a Download of a File is finished
	 * @param name The Name of the Downloaded File
	 */
	public void downloadedFile(File f);

	/**
	 * Occures when a File starts to be downloaded
	 * @param name The Name of the File
	 * @param length The Length of the File
	 */
	public void startedDownloading(String name, long length);

}
