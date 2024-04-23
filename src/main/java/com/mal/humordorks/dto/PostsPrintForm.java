package com.mal.humordorks.dto;

import lombok.Data;

@Data
public class PostsPrintForm {

    private String nickname;
    private String img;
    private String content;


    public static PostsPrintForm toDto(String nickname, String img, String content){
        PostsPrintForm postsPrintForm = new PostsPrintForm();
        postsPrintForm.nickname = nickname;
        postsPrintForm.img = img;
        postsPrintForm.content = content;
        return postsPrintForm;
    }

    
}
