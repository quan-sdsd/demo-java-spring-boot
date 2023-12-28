package fpt.project.datn.utility;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public class Webclient {
    private final static WebClient client = WebClient.create();

    private Webclient(){}

    public static <T> T get(String uri, Class<T> clazz, Map<String, String> params) {
        String uriWithParams = getUriWithParams(uri, params);
        Mono<T> rs = client.get()
                .uri(uriWithParams)
                .retrieve()
                .bodyToMono(clazz);
        return rs.block();
    }

//    public static String post()

    private static String getUriWithParams(String uri, Map<String, String> params) {
        StringBuilder uriWithParams = new StringBuilder(uri);
        if(params.isEmpty()) {
            return uriWithParams.toString();
        }
        for(String name : params.keySet()) {
            uriWithParams.append(name)
                    .append("=")
                    .append(params.get(name))
                    .append("&");
        }
        return uriWithParams.substring(uriWithParams.length() - 1);
    }
}
