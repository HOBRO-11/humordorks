package com.mal.humordorks.search;

import java.util.ArrayList;
import java.util.List;

public class Trie {

    private Node root;

    public Trie() {
        this.root = new Node();
    }
    
    public Trie(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public List<String> searchRecommend(String text, int size) {

        List<String> result = new ArrayList<>();

        // create term
        Term term = new Term(text, 0);

        // check root child
        if (root.getChildren() == null) {
            return null;
        }

        // find node of text path
        Node findNode = findNodeByText(term, root);

        // text is textable
        if (findNode.isTextable) {
            result.add(text);
        }

        List<Node> findChildren = findNode.getChildren();

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
        for (Node n : findChildren) {
            result.add(text + catchText(n));
        }

        return result;
    }
    
    public void makeTrie(List<Term> tree) {
        for (Term term : tree) {
            Node temp = root;
            while (true) {
                temp = insert_node(term, temp);
                if (temp == null) {
                    break;
                }
            }
        }
    }

    private String catchText(Node root) {
        String result = "";
        result += root.getC();

        if (root.isTextable) {
            return result;
        }
        List<Node> tempChildren = root.getChildren();
        if (tempChildren == null) {
            return result;
        }
        Node tempChild = tempChildren.get(0);
        result += catchText(tempChild);
        return result;
    }

    private Node findNodeByText(Term term, Node root) {
        List<Node> temp = root.getChildren();

        // find children of term
        while (true) {
            Node findNode = findNodeByChar(term, temp);
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

    private Node insert_node(Term term, Node root) {
        List<Node> children = root.getChildren();
        if (children == null) {
            return insert_child_null_Case(term, root);
        } else {
            return insert_child_NotNull_case(term, children);
        }
    }

    private Node insert_child_null_Case(Term term, Node root) {
        // add arr
        root.addChildren();

        // new node
        Node ntt = new Node(term.cntChar(), term.getScore());

        // add node
        root.addChild(ntt);

        // isEnd
        return EndCaseHandler(term, ntt);
    }

    private Node insert_child_NotNull_case(Term term, List<Node> children) {

        // find node by char
        Node findNode = findNodeByChar(term, children);

        // check node
        Node wannaAddNode = ejectNode(term, children, findNode);

        // find and insert node order by score desc and chat asc
        insertNode_OrderByScoreDESCAndCharASC(children, wannaAddNode);

        return EndCaseHandler(term, wannaAddNode);

    }

    private void insertNode_OrderByScoreDESCAndCharASC(List<Node> children, Node wannaAddNode) {
        int size = children.size();
        boolean isAdd = false;
        for (int i = 0; i < size; i++) {
            Node cntNode = children.get(i);
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

    private Node ejectNode(Term term, List<Node> children, Node targetNode) {
        Node wannaAddNode = null;
        if (targetNode == null) {
            wannaAddNode = new Node(term.cntChar(), term.getScore());
        } else {
            wannaAddNode = targetNode;
            wannaAddNode.addScore(term.getScore());
            children.remove(targetNode);
        }
        return wannaAddNode;
    }

    private Node findNodeByChar(Term term, List<Node> children) {
        for (Node n : children) {
            if (n.getC() == term.cntChar()) {
                return n;
            }
        }
        return null;
    }

    private Node EndCaseHandler(Term term, Node ntt) {
        if (term.isEnd()) {
            ntt.ableText();
            return null;
        } else {
            term.shiftCntIdx();
            return ntt;
        }
    }

    private class Node {
        private char c;
        private long score;
        private boolean isTextable = false;
        private List<Node> children;

        public Node() {
        }

        public Node(char c, int score) {
            this.c = c;
            this.score = score;
        }

        public char getC() {
            return c;
        }

        public long getScore() {
            return score;
        }

        public List<Node> getChildren() {
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

        public void addChild(Node ntt) {
            this.children.add(ntt);
        }

        public void removeChild(Node ntt) {
            this.children.remove(ntt);
        }
    }
}
