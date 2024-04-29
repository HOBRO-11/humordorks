package com.mal.humordorks.facade.like_posts;

import lombok.Data;

@Data
public class LikePostsPrintForm {
    private String img;
    private String content;

    public static LikePostsPrintForm toDto(String img, String content) {
        LikePostsPrintForm lppf = new LikePostsPrintForm();
        lppf.img = img;
        lppf.content = content;
        return lppf;
    }


}
