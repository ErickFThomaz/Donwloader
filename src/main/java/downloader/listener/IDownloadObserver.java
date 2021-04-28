package downloader.listener;

public interface IDownloadObserver {

	void onUpdate(DownloadStatus downloadStatus);


	void onFinish(DownLoadFinish downLoadFinish);
}
