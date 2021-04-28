package downloader.listener;

public class DownLoadFinish {

    private final int total;
    private final String totalSize, fileName;
    private final boolean finish, success;

    public DownLoadFinish(String totalSize, String fileName, boolean finish, boolean success) {
        this.totalSize = totalSize;
        this.fileName = fileName;
        this.finish = finish;
        this.success = success;
        total = 0;
    }

    public DownLoadFinish(int totalSize, String fileName, boolean finish, boolean success) {
        this.total = totalSize;
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
}
