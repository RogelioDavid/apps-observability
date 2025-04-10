package com.app.model.transfer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Post {

    private Long userId;
    private Long id;
    private String title;
    private String body;

}
