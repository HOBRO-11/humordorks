package com.mal.humordorks.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TrieExample {

    ObjectMapper mapper = new ObjectMapper();
    NodeToTest root = null;
    // int depth = 0;
    // int cnt = 0;

    @BeforeEach
    void beforeEach() throws IOException {
        List<TermToTest> tree = new ArrayList<>();
        tree = readTree();

        root = new NodeToTest();

        makeTrie(tree, root);
    }

    @Test
    void testName() {
        String targetText = "들";
        List<String> searchRecommend = searchRecommend(targetText, 0, root);

        for (String string : searchRecommend) {
            System.out.println(string);
        }

    }

    private List<String> searchRecommend(String text, int size, NodeToTest root) {

        List<String> result = new ArrayList<>();

        // create term
        TermToTest term = new TermToTest(text, 0);

        // check root child
        if (root.getChildren() == null) {
            return null;
        }

        // find node of text path
        NodeToTest findNode = findNodeByText(term, root);

        // text is textable
        if(findNode.isTextable){
            result.add(text);
        }

        List<NodeToTest> findChildren = findNode.getChildren();

        // TODO 1. 추후 재귀 방식으로 gain을 적용한 search를 지원하도록 한다.
        // allocate gain
        // int findSize = findChildren.size();
        // int first_gain = 0;
        // int gain = 0;
        // if (findSize < size) {
        // gain = size / findSize;
        // first_gain = size - (size - 1) * gain;
        // }

        // 2. 우선 현재 받은 children에서 각각 하나씩 재귀를 통해 받아와 결과에 넣는다.
        for (NodeToTest n : findChildren) {
            result.add( text + catchText(n));
        }

        return result;
    }

    public String catchText(NodeToTest root){
        String result = "";
        result += root.getC();

        if(root.isTextable){
            return result;
        }
        List<NodeToTest> tempChildren = root.getChildren();
        if(tempChildren == null){
            return result;
        }
        NodeToTest tempChild = tempChildren.get(0);
        result += catchText(tempChild);
        return result;
    }

    private NodeToTest findNodeByText(TermToTest term, NodeToTest root) {
        List<NodeToTest> temp = root.getChildren();

        // find children of term
        while (true) {
            NodeToTest findNode = findNodeByChar(term, temp);
            if (findNode == null) {
                return null;
            }
            if (term.isEnd()) {
                return findNode;
            }
            temp = findNode.getChildren();
            term.shiftCntIdx();
        }
    }

    private void makeTrie(List<TermToTest> tree, NodeToTest root) {
        for (TermToTest term : tree) {
            NodeToTest temp = root;
            while (true) {
                temp = insert_node(term, temp);
                if (temp == null) {
                    break;
                }
            }
        }
    }

    private NodeToTest insert_node(TermToTest term, NodeToTest root) {
        List<NodeToTest> children = root.getChildren();
        if (children == null) {
            return insert_child_null_Case(term, root);
        } else {
            return insert_child_NotNull_case(term, children);
        }
    }

    private NodeToTest insert_child_null_Case(TermToTest term, NodeToTest root) {
        // add arr
        root.addChildren();

        // new node
        NodeToTest ntt = new NodeToTest(term.cntChar(), term.getScore());

        // add node
        root.addChild(ntt);

        // isEnd
        return EndCaseHandler(term, ntt);
    }

    private NodeToTest insert_child_NotNull_case(TermToTest term, List<NodeToTest> children) {

        // find node by char
        NodeToTest findNode = findNodeByChar(term, children);

        // check node
        NodeToTest wannaAddNode = ejectNode(term, children, findNode);

        // find and insert node order by score desc and chat asc
        insertNode_OrderByScoreDESCAndCharASC(children, wannaAddNode);

        return EndCaseHandler(term, wannaAddNode);

    }

    private void insertNode_OrderByScoreDESCAndCharASC(List<NodeToTest> children, NodeToTest wannaAddNode) {
        int size = children.size();
        boolean isAdd = false;
        for (int i = 0; i < size; i++) {
            NodeToTest cntNode = children.get(i);
            if (cntNode.getScore() < wannaAddNode.getScore()) {
                children.add(i, wannaAddNode);
                isAdd = true;
                break;
            }
            if (cntNode.getScore() == wannaAddNode.getScore()) {
                if (cntNode.getC() > wannaAddNode.getC()) {
                    children.add(i, wannaAddNode);
                    isAdd = true;
                    break;
                }
            }
        }
        if (isAdd == Boolean.FALSE) {
            children.add(wannaAddNode);
        }
    }

    private NodeToTest ejectNode(TermToTest term, List<NodeToTest> children, NodeToTest targetNode) {
        NodeToTest wannaAddNode = null;
        if (targetNode == null) {
            wannaAddNode = new NodeToTest(term.cntChar(), term.getScore());
        } else {
            wannaAddNode = targetNode;
            wannaAddNode.addScore(term.getScore());
            children.remove(targetNode);
        }
        return wannaAddNode;
    }

    private NodeToTest findNodeByChar(TermToTest term, List<NodeToTest> children) {
        for (NodeToTest n : children) {
            if (n.getC() == term.cntChar()) {
                return n;
            }
        }
        return null;
    }

    private NodeToTest EndCaseHandler(TermToTest term, NodeToTest ntt) {
        if (term.isEnd()) {
            ntt.ableText();
            return null;
        } else {
            term.shiftCntIdx();
            return ntt;
        }
    }

    // inner class

    public class NodeToTest {
        private char c;
        private long score;
        private boolean isTextable = false;
        private List<NodeToTest> children;

        public NodeToTest() {
        }

        public NodeToTest(char c, int score) {
            this.c = c;
            this.score = score;
        }

        public char getC() {
            return c;
        }

        public long getScore() {
            return score;
        }

        public List<NodeToTest> getChildren() {
            return children;
        }

        public boolean isTextable() {
            return isTextable;
        }

        public void addScore(int score) {
            this.score += score;
        }

        public void ableText() {
            this.isTextable = true;
        }

        public void addChildren() {
            this.children = new ArrayList<>();
        }

        public void addChild(NodeToTest ntt) {
            this.children.add(ntt);
        }

        public void removeChild(NodeToTest ntt) {
            this.children.remove(ntt);
        }
    }

    public class TermToTest {
        private char[] charArr;
        private int score;
        private int cntIdx = 0;

        public TermToTest() {
        }

        public TermToTest(String term, int score) {
            this.charArr = term.toCharArray();
            this.score = score;
        }

        public boolean isEnd() {
            return charArr.length == (cntIdx + 1);
        }

        public char cntChar() {
            return charArr[cntIdx];
        }

        public void shiftCntIdx() {
            cntIdx++;
        }

        public int getScore() {
            return score;
        }

    }

    List<TermToTest> readTree() throws IOException {
        ReadJsonField readJsonField = new ReadJsonField(mapper);
        readJsonField.setJsonNode();
        List<Term> jsonNodeWithGet = readJsonField.readJsonNodeWithGet();
        return jsonNodeWithGet.stream().map(term -> new TermToTest(term.getKey(), term.getScore()))
                .collect(Collectors.toList());
    }
}
