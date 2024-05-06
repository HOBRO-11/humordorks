package com.mal.humordorks.search;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mal.humordorks.exception.ResourceNotFound;

@Component
public class TotalTermRepositoryImpl implements TotalTermRepository {

    private static ObjectMapper mapper = new ObjectMapper();
    private static final Set<Index> LIST_IDX = Collections.synchronizedSet(new HashSet<Index>());
    private static final String BASE_URL = "http://localhost:9201/";
    private static final String SUFFIX_URL = "/_search/";
    private static final int UNABLE_SEARCH_SIZE = 0;
    private static final long DEFAULT_SEARCH_SIZE = 10000L;

    @Override
    public List<Term> findTermByIndexReturnList(String indexName, long searchSize) throws IOException {
        if (searchSize == UNABLE_SEARCH_SIZE) {
            searchSize = DEFAULT_SEARCH_SIZE;
        }
        checkIndexName(indexName);
        String baseUrl = getSearchUrl(indexName);
        BodyInserter<String, ?> searchTermJsonBody = getSearchTermJsonBody(indexName);

        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();

        String response = webClient.post()
                .uri(SUFFIX_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(searchTermJsonBody))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ReadJsonField readJsonField = new ReadJsonField(mapper);
        readJsonField.setJsonNode(response);

        return readJsonField.readJsonNodeWithGet();
    }

    @Override
    public void addIndex(String indexName) {
        LIST_IDX.add(new Index(indexName));
    }

    private String getSearchUrl(String indexName) {
        StringBuilder sb = new StringBuilder();
        sb.append(BASE_URL);
        sb.append(indexName);
        return sb.toString();
    }

    private void checkIndexName(String idxName) {
        if (LIST_IDX.contains(new Index(idxName)) == Boolean.FALSE) {
            throw new ResourceNotFound(" this index not register");
        }
    }

    private BodyInserter<String, ?> getSearchTermJsonBody(String index_name) {
        String jsonBodyFormat = "{ " +
                "\"size\": 0, " +
                "\"aggs\": { " +
                "   \"%s\": { " +
                "       \"terms\": { " +
                "           \"field\": \"description\", " +
                "           \"size\": 10000, " +
                "           \"order\": { \"_key\": \"asc\" } " +
                "       } " +
                "   } " +
                "} " +
                "} ";

        String json = String.format(jsonBodyFormat, index_name);
        return BodyInserters.fromValue(json);
    }

}
