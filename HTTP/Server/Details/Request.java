package HTTP.Server.Details;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
    private Method method;
    private String path;
    private String HttpVersion;


    public Request(String httpVersion, Method method, String path) {
        this.HttpVersion = httpVersion;
        this.path = path;
        this.method = method;
    }

    public String getRequestString() {
        return method + " " + path + " " + getHttpVersion() + "\r\n" +
                "\r\n";
    }
}
