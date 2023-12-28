package fpt.project.datn.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.*;
import java.util.Map;

public class Utility {
    @Getter
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();;

    private Utility() {
        // should not be created
    }

    public static String getAttributeFromJson(String requestBody, String name) {
        try {
            JsonNode node = objectMapper.readTree(requestBody);
            return node.get(name).asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Only work correctly in the main thread(SecurityContextHolder is local thread and be deleted after main request thread end)
     * @return current username
     */
    public static String getCurrentUserName() {
        return getAuthentication().getName();
    }

    public static UserDetails getCurrentUser() {
        return (UserDetails) getAuthentication().getPrincipal();
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String htmlTemplateReader(String path) {
        StringBuilder html = new StringBuilder();
        try{
            File template = getFile(path);
            try(FileReader reader = new FileReader(template)){
                try(BufferedReader bufferedReader = new BufferedReader(reader)) {
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        html.append(line);
                    }
                }
            }
        }
        catch (Exception ignored) {

        }
        return html.toString();
    }

    public static File getFile(String path) throws IOException {
        return resourceLoader.getResource("classpath:" + path).getFile();
    }
}
