package downloader.listener;

public class DownLoadFinish {

    private final int total;
    private final String totalSize, fileName, path;
    private final boolean finish, success;

    public DownLoadFinish(String totalSize, String path, String fileName, boolean finish, boolean success) {
        this.totalSize = totalSize;
        this.path = path;
        this.fileName = fileName;
        this.finish = finish;
        this.success = success;
        total = 0;
    }

    public DownLoadFinish(int totalSize, String path, String fileName, boolean finish, boolean success) {
        this.total = totalSize;
        this.path = path;
        this.fileName = fileName;
        this.finish = finish;
        this.success = success;
        this.totalSize = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFinish() {
        return finish;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }
}
