package com.mal.humordorks.search;

import lombok.Data;

@Data
public class Term {

    private char[] charArr;
    private int score;
    private int cntIdx = 0;

    public Term() {
    }

    public Term(String term, int score) {
        this.charArr = term.toCharArray();
        this.score = score;
    }

    public String getKey(){
        return String.valueOf(charArr);
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
