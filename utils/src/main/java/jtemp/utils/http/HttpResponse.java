package jtemp.utils.http;

/**
 * @author ZMS
 */
public class HttpResponse {

    private int responseCode;
    private boolean success;
    private byte[] data;

    public HttpResponse(int responseCode, boolean success, byte[] data) {
        this.responseCode = responseCode;
        this.success = success;
        this.data = data;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return String.format("HttpResponse[responseCode=%s,success=%s,data=%s]", responseCode, success, data == null ? "" : new String(data));
    }
}
