package com.umbrella.project_umbrella.dto.comment;

import com.umbrella.project_umbrella.domain.Comment.Comment;
import com.umbrella.project_umbrella.domain.Post.Post;
import com.umbrella.project_umbrella.domain.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {

    private Long id;
    private String content;
    private String nickName;
    private int pageNumber;


}
