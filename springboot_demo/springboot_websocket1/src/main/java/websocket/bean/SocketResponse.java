package websocket.bean;

/**
 * Author: Johnny
 * Date: 2017/7/25
 * Time: 0:16
 */
public class SocketResponse {
    private String responseMessage;

    public SocketResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
