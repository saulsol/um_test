package com.umbrella.project_umbrella.controller;

import com.umbrella.project_umbrella.dto.comment.CommentRequestDto;
import com.umbrella.project_umbrella.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Getter
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 생성
//    @PostMapping("/post/{id}/comments")
//    public ResponseEntity<?> commentSave(@PathVariable(name = "id") Long postId, @RequestBody CommentRequestDto commentRequestDto){
//
//
//
//
//
//
//    }






}
