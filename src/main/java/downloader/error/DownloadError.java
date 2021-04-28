package downloader.error;
public enum DownloadError {

	Connection_Error(400, "Connection failure."),
	HTTP_Error(      0, "");

	private  int code;
	private  String message;

	DownloadError(int code, String message){
		this.code = code;
		this.message = message;
	}

	public static DownloadError httpError(int code, String message){
		var error = DownloadError.HTTP_Error;
		error.setCode(code);
		error.setMessage(message);
		return error;
	}

	private void setCode(int code) {
		this.code = code;
	}

	private void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
