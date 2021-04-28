package downloader;

import downloader.error.DownloadError;
import downloader.listener.IDownloadObserver;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DownloadBuilder {

    private String url, path, fileName, userAgent, auth;
    private Proxy proxy;
    private int readTimeOut = 10000;
    private Consumer<DownloadError> errorConsumer;
    private final List<IDownloadObserver> downloadObserver = new ArrayList<>();


    public DownloadBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public DownloadBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public DownloadBuilder setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public DownloadBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public DownloadBuilder setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public DownloadBuilder setAuth(String auth) {
        this.auth = auth;
        return this;
    }

    public DownloadBuilder setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public DownloadBuilder setErrorConsumer(Consumer<DownloadError> errorConsumer) {
        this.errorConsumer = errorConsumer;
        return this;
    }

    public DownloadBuilder addListener(IDownloadObserver downloadObserver) {
        this.downloadObserver.add(downloadObserver);
        return this;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAuth() {
        return auth;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Consumer<DownloadError> getErrorConsumer() {
        return errorConsumer;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public String getUrl() {
        return url;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public List<IDownloadObserver> getDownloadObserver() {
        return downloadObserver;
    }
}
