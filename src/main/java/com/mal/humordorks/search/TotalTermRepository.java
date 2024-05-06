package com.mal.humordorks.search;

import java.io.IOException;
import java.util.List;

public interface TotalTermRepository {

    List<Term> findTermByIndexReturnList(String indexName, long searchSize) throws IOException;

    void addIndex(String indexName);

}