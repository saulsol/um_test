package com.umbrella.project_umbrella.dto.comment;

import com.umbrella.project_umbrella.domain.Comment.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Data
@AllArgsConstructor
@Builder
@ToString
public class CommentResponseDto {

    private Long id;
    private String content;
    private String createdDate; //= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String nickname; // 작성자

    @Builder
    public CommentResponseDto(Long id, String content, String createdDate, String modifiedDate, String nickname, Long postsId) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.nickname = nickname;
    }
}

