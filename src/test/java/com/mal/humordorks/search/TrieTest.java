package com.mal.humordorks.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TrieTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void test() throws IOException {

        List<Term> tree = new ArrayList<>();
        tree = readTree();

        Trie trie = new Trie();

        trie.makeTrie(tree);

        String targetText = "감자";
        List<String> searchRecommend = trie.searchRecommend(targetText, 0);

        for (String string : searchRecommend) {
            System.out.println(string);
        }
    }

    List<Term> readTree() throws IOException {
        ReadJsonField readJsonField = new ReadJsonField(mapper);
        readJsonField.setJsonNode();
        // List<Term> jsonNodeWithGet = readJsonField.readJsonNodeWithGet();
        // return jsonNodeWithGet.stream().map(term -> new TermToTest(term.getKey(),
        // term.getScore()))
        // .collect(Collectors.toList());
        return readJsonField.readJsonNodeWithGet();
    }
}
