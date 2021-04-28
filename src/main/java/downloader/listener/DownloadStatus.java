package downloader.listener;

public class DownloadStatus {

	private final double progress;
	private final String downloadedSize, totalSize, fileName, path;

	public DownloadStatus(double progress, String downloadedSize, String totalSize, String fileName, String path) {
		this.progress = progress;
		this.downloadedSize = downloadedSize;
		this.totalSize = totalSize;
		this.fileName = fileName;
		this.path = path;
	}


	public String getPath() {
		return path;
	}

	public double getProgress() {
		return progress;
	}

	public String getDownloadedSize() {
		return downloadedSize;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public String getFileName() {
		return fileName;
	}
}
