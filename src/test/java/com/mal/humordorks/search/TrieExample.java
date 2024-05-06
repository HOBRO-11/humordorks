package com.mal.humordorks.search;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TrieExample {
    ObjectMapper mapper = new ObjectMapper();
    ReadJsonField readField = new ReadJsonField(mapper);
    Trie trie = new Trie();

    @Test
    void testName() throws IOException {

        readField.setJsonNode(new File("/Users/hobro/vscode_workspace/init_data/es_terms.json"));
        List<Term> terms = readField.readJsonNodeWithGet();
        trie.makeTrie(terms);
        List<String> searchRecommend = trie.searchRecommend("감자", 0);

        for (String string : searchRecommend) {
            System.out.println(string);
        }       
    }
}
