package HTTP.Server.Details;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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

    public Response(String httpVersion, int statusCode, String statusMessage, String contentType, long contentLength, String content) {
        this.HttpVersion = httpVersion;
        this.statusMessage = statusMessage;
        this.contentType = contentType;
        this.date = new Date();
        this.contentLength = contentLength;
        this.content = content;
        this.statusCode = statusCode;
    }

    public String getResponseString() {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(getHttpVersion()).append(" ").append(statusCode).append(" ").append(statusMessage).append("\r\n");
        responseBuilder.append("Content-Type: ").append(contentType).append("\r\n");
        responseBuilder.append("Content-Length: ").append(contentLength).append("\r\n");

        // Format date in GMT as per HTTP specification
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        responseBuilder.append("Date: ").append(dateFormat.format(date)).append("\r\n");

        responseBuilder.append("Server: Simple HTTP Server\r\n");
        responseBuilder.append("Connection: close\r\n");
        responseBuilder.append("\r\n");
        if (content != null) {
            responseBuilder.append(content);
        }
        return responseBuilder.toString();
    }
}