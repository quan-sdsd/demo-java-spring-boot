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
import java.util.Random;
import java.util.Set;

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

    public static String htmlTemplateReader(String path, Map<String, String> attributes) {
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
        return insertHtmlAttribute(html.toString(), attributes);
    }

    private static String insertHtmlAttribute(String html, Map<String, String> attributes) {
        Set<String> keys = attributes.keySet();
        StringBuilder attribute = new StringBuilder();
        for(String key : keys) {
            html = html.replace(attribute.append("{{")
                            .append(key)
                            .append("}}")
                    , attributes.get(key)
            );
            attribute.setLength(0);
        }
        return html;
    }

    public static File getFile(String path) throws IOException {
        return resourceLoader.getResource("classpath:" + path).getFile();
    }

    public static String getRanCode(long maxNum) {
        Random random = new Random();
        long codeNum = random.nextLong(maxNum);
        StringBuilder code = new StringBuilder(String.valueOf(codeNum));
        int minLength = String.valueOf(maxNum).length();
        while (true) {
            int length = code.length();
            if(minLength > length) {
                code.insert(0, "0");
                continue;
            }
            break;
        }
        return code.toString();
    }
}
