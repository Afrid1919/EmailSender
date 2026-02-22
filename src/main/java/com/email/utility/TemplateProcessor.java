package com.email.utility;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TemplateProcessor {
    public String processTemplate(String body, Map<String, Object> variables){
        String processedBody = body;

        for(Map.Entry<String, Object> entry : variables.entrySet()){
            String placeholder = "{{" +entry.getKey() + "}}";
            processedBody = processedBody.replace(placeholder, entry.getValue().toString());
        }
        return processedBody;
    }
}
