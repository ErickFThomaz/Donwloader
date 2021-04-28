package downloader;

import downloader.listener.DownLoadFinish;
import downloader.listener.DownloadStatus;
import downloader.listener.IDownloadObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Download {

	private static Logger logger = LoggerFactory.getLogger(Download.class);

	public static void main(String... args) {

		DownloadBuilder builder = new DownloadBuilder();
		builder.setUrl("")
				.setPath(System.getProperty("user.home") + "\\Desktop\\teste")
				.setFileName("img.png")
				.setErrorConsumer(downloadError -> logger.info(downloadError.getCode() + " " + downloadError.getMessage()))
				.addListener(new IDownloadObserver() {
					@Override
					public void onUpdate(DownloadStatus downloadStatus) {
						logger.info(downloadStatus.getProgress() + "%");
					}

					@Override
					public void onFinish(DownLoadFinish downLoadFinish) {
						logger.info("Terminou");
					}
				});

		Downloader downloader = new Downloader(builder);
		downloader.useFollowRedirects().asyncDownload();

	}
}
