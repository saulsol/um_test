package com.umbrella.project_umbrella.service;

import com.umbrella.project_umbrella.constant.Role;
import com.umbrella.project_umbrella.domain.Comment.Comment;
import com.umbrella.project_umbrella.domain.Post.Post;
import com.umbrella.project_umbrella.domain.User.User;
import com.umbrella.project_umbrella.dto.comment.CommentRequestDto;
import com.umbrella.project_umbrella.dto.comment.CommentResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class CommentServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CommentService commentService;

    @Test
    public void saveTest(){

        User user = User.builder()
                .email("s@sss")
                .age(10)
                .mName("saul")
                .nickName("Nick")
                .password("password")
                .role(Role.USER)
                .build();

        Post post1 = Post.builder()
                .content("content")
                .title("title")
                .writer("writer")
                .build();

        Post post2 = Post.builder()
                .content("content2")
                .title("title2")
                .writer("writer2")
                .build();


        entityManager.persist(user);
        entityManager.persist(post1);
        entityManager.persist(post2);

        CommentRequestDto commentRequestDto = CommentRequestDto
                .builder()
                .id(1L)
                .content("s")
                .pageNumber(0)
                .build();

        CommentRequestDto commentRequestDto2 = CommentRequestDto
                .builder()
                .id(2L)
                .content("ss")
                .pageNumber(0)
                .build();


        commentService.create(user.getNickName(), post1.getId(), commentRequestDto);
        commentService.create(user.getNickName(), post1.getId(), commentRequestDto2);

        List<CommentResponseDto> responseDtos = commentService.delete(commentRequestDto);

        for (CommentResponseDto responseDto : responseDtos) {
            System.out.println("---------------------------------------");
            System.out.println(responseDto);
        }


        
        
        }

    }



