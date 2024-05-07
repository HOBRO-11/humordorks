package com.mal.humordorks.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadJsonField {

    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonNode jsonNode;

    public ReadJsonField(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setJsonNode(File file) throws IOException {
        jsonNode = objectMapper.readTree(file);
    }

    public void setJsonNode(String json) throws IOException {
        jsonNode = objectMapper.readTree(new File("/Users/hobro/vscode_workspace/init_data/es_terms.json"));
    }

    public Term readJsonNodeWithPath(int i) {
        JsonNode path = jsonNode.get("aggregations").get("test_food_terms").get("buckets").path(i);
        if (path.isEmpty()) {
            return null;
        }
        String term = path.path("key").asText();
        int doc_count = path.path("doc_count").asInt();

        return new Term(term, doc_count);
    }

    public List<Term> readJsonNodeWithGet() {

        List<Term> tempList = new ArrayList<>();

        int i = 0;

        while (true) {

            JsonNode path = jsonNode.get("aggregations").get("test_food_terms").get("buckets").path(i++);
            if (path.isEmpty()) {
                break;
            }
            String key = path.path("key").asText();
            int doc_count = path.path("doc_count").asInt();
            Term term = new Term(key, doc_count);
            tempList.add(term);
        }

        return tempList;
    }

}
