package downloader.utils;

import java.util.concurrent.ThreadFactory;

public class DownloadThread implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "Downloader");
    }
}
