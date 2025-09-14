package HTTP.Server.Details;

import java.util.Arrays;
import java.util.Optional;

public enum Method {
    GET,
    POST,
    DELETE,
    PUT,
    PATCH,
    OPTIONS;


    public static Method fromString(String methodString) {
        if (methodString == null) {
            return null;
        }
        Optional<Method> method = Arrays.stream(Method.values())
                .filter(m -> m.name().equalsIgnoreCase(methodString))
                .findFirst();

        return method.orElse(null);
    }
}
