package com.mal.humordorks.dto;

import lombok.Data;

@Data
public class CommentPrintForm {
    private String nickname;
    private String content;

    

    public CommentPrintForm() {
    }



    public static CommentPrintForm toDto(String nickname, String content) {
        CommentPrintForm commentPrintForm = new CommentPrintForm();
        commentPrintForm.nickname = nickname;
        commentPrintForm.content = content;
        return commentPrintForm;
    }


}
