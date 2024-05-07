package com.mal.humordorks.search;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

@WebFluxTest
public class ElasticTerm {

    TotalTermRepository repository = new TotalTermRepositoryImpl();
    Trie trie = new Trie();

    @Test
    void testName() throws IOException {
        repository.addIndex("test_food_item");
        List<Term> results = repository.findTermByIndexReturnList("test_food_item", 0);

        trie.makeTrie(results);
        List<String> searchRecommend = trie.searchRecommend("감자", 0);

        for (String string : searchRecommend) {
            System.out.println(string);
        }       
    }

}
