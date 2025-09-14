package HTTP.Server.Details;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Response {
    private String HttpVersion;
    private int statusCode;
    private String statusMessage;
    private String contentType;
    private Date date;
    private long contentLength;
    private String content;


    public Response(String httpVersion, int statusCode, String responseMessage, String contentType, long contentLength, String response) {
        this.HttpVersion = httpVersion;
        this.statusMessage = responseMessage;
        this.contentType = contentType;
        this.date = new Date();
        this.contentLength = contentLength;
        this.content = response;
        this.statusCode = statusCode;
    }

    public String getResponseString() {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(getHttpVersion()).append(" ").append(statusCode).append(" ").append(statusMessage).append("\r\n");
        responseBuilder.append("Content-Type: ").append(contentType).append("\r\n");
        responseBuilder.append("Content-Length: ").append(contentLength).append("\r\n");
        responseBuilder.append("Date: ").append(date).append("\r\n");
        responseBuilder.append("Server: Simple HTTP Server\r\n");
        responseBuilder.append("Connection: close\r\n");
        responseBuilder.append("\r\n");
        if (content != null) {
            responseBuilder.append(content);
        }
        return responseBuilder.toString();
    }
}
