package downloader;

import downloader.error.DownloadError;
import downloader.listener.DownLoadFinish;
import downloader.listener.DownloadStatus;
import downloader.utils.DownloadThread;
import downloader.utils.ProgressObservableInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Downloader {

    private final DownloadBuilder downloadBuilder;
    private boolean followRedirects = false;

    private HttpURLConnection connection = null;

    private double cache;

    public Downloader(DownloadBuilder downloadBuilder) {
        this.downloadBuilder = downloadBuilder;
    }

    public Downloader useFollowRedirects() {
        followRedirects = true;
        return this;
    }


    public void asyncDownload() {
        ExecutorService threadPool = Executors.newFixedThreadPool(2, new DownloadThread());
        Future<?> ignored = threadPool.submit(this::download);

        threadPool.shutdown();
    }

    public void download() {

        try {
            if (downloadBuilder.getProxy() != null) {
                connection = (HttpURLConnection) new URL(downloadBuilder.getUrl()).openConnection(downloadBuilder.getProxy());
            } else {
                connection = (HttpURLConnection) new URL(downloadBuilder.getUrl()).openConnection();
            }
        } catch (Exception ex) {

            downloadBuilder.getDownloadObserver().forEach(c -> c.onFinish(new DownLoadFinish(-1, downloadBuilder.getPath(), downloadBuilder.getFileName(), true, true)));

            if (ex.getClass().equals(MalformedURLException.class)) {
                downloadBuilder.getErrorConsumer().accept(DownloadError.httpError(150, "The URL is not valid or is incorrect."));
            }
            if (ex.getClass().equals(UnknownHostException.class)) {
                downloadBuilder.getErrorConsumer().accept(DownloadError.httpError(100, "Check your internet connection."));
            }
            return;
        }

        try {

            if (connection != null) {

                if (downloadBuilder.getUserAgent() == null || downloadBuilder.getUserAgent().isEmpty())
                    connection.setRequestProperty("User-Agent", "Downloader");
                else
                    connection.setRequestProperty("User-Agent", downloadBuilder.getUserAgent());

                if (downloadBuilder.getAuth() != null) {
                    connection.setRequestProperty("Authorization ", downloadBuilder.getAuth());
                }

                connection.setInstanceFollowRedirects(followRedirects);
                connection.setReadTimeout(downloadBuilder.getReadTimeOut());

                InputStream in = connection.getInputStream();

                ProgressObservableInputStream stream = new ProgressObservableInputStream(in, connection.getContentLength());

                FileOutputStream out = new FileOutputStream(downloadBuilder.getPath() + System.getProperty("file.separator") + downloadBuilder.getFileName());

                byte[] buffer = new byte[4096];
                int read;

                while ((read = stream.read(buffer)) != -1) {
                    out.write(buffer, 0, read);

                    double now = stream.getPercentage();
                    if (now > cache) {

                        downloadBuilder.getDownloadObserver().forEach(c -> {
                            try {
                                c.onUpdate(new DownloadStatus(stream.getPercentage(),
                                        stream.getSize((out.getChannel().size())),
                                        stream.getSize(),
                                        downloadBuilder.getFileName(), downloadBuilder.getPath()));
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        });
                    }
                    cache = now;
                }
                downloadBuilder.getDownloadObserver().forEach(c ->
                        c.onFinish(new DownLoadFinish(stream.getSize(), downloadBuilder.getPath(),downloadBuilder.getFileName(), true, true)));


                out.flush();

                if (connection != null)
                    connection.disconnect();


                if (out != null)
                    out.close();


                if (stream != null)
                    stream.close();
            }
        } catch (MalformedURLException exception) {
            downloadBuilder.getDownloadObserver().forEach(c ->
                    c.onFinish(new DownLoadFinish(-1, downloadBuilder.getPath() ,downloadBuilder.getFileName(), true, true)));

            downloadBuilder.getErrorConsumer().accept(DownloadError.httpError(101, "The URL is not valid or is incorrect."));
            downloadBuilder.getErrorConsumer().accept(DownloadError.Connection_Error);

        } catch (Exception exception) {
            downloadBuilder.getDownloadObserver().forEach(c ->
                    c.onFinish(new DownLoadFinish(-1, downloadBuilder.getPath(), downloadBuilder.getFileName(), true, true)));

            if (exception.getClass().equals(UnknownHostException.class)) {
                downloadBuilder.getErrorConsumer().accept(DownloadError.httpError(100, "Check your internet connection."));
                return;
            }
            exception.printStackTrace();
        }
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public DownloadBuilder getDownloadBuilder() {
        return downloadBuilder;
    }
}
