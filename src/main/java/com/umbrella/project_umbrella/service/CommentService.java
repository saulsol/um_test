package com.umbrella.project_umbrella.service;

import com.umbrella.project_umbrella.domain.Comment.Comment;
import com.umbrella.project_umbrella.domain.Comment.CommentRepository;
import com.umbrella.project_umbrella.domain.Post.Post;
import com.umbrella.project_umbrella.domain.Post.PostRepository;
import com.umbrella.project_umbrella.domain.User.User;
import com.umbrella.project_umbrella.domain.User.UserRepository;
import com.umbrella.project_umbrella.dto.comment.CommentResponseDto;
import com.umbrella.project_umbrella.dto.comment.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    public List<CommentResponseDto> create(String nickName, Long postId, CommentRequestDto commentRequestDto){


        User user = userRepository.findByNickName(nickName); // Long id 로 변경

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다."+ postId)
        );


        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .user(user)
                .post(post)
                .build();

        Comment savedComment = commentRepository.save(comment);

        // pageRequest 만들어서 처리
        PageRequest pageRequest = makePageRequest(commentRequestDto);

        List<Comment> commentList = commentRepository.findAllByPost_Id(pageRequest, savedComment.getPost().getId());
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        // DTO 변환
        for (Comment commentEntity : commentList) {

            CommentResponseDto commentResponseDto = new CommentResponseDto(
                    commentEntity.getId(),
                    commentEntity.getContent(),
                    commentEntity.getCreateDate(),
                    commentEntity.getUser().getNickName()
            );

            responseDtoList.add(commentResponseDto);

        }

        return responseDtoList;
    }


    // 댓글 수정
    public List<CommentResponseDto> update(Long commentId, CommentRequestDto commentRequestDto){

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                        ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."+ commentId)
                );
        comment.update(commentRequestDto.getContent());

        Comment savedComment = commentRepository.save(comment);
        PageRequest pageRequest = makePageRequest(commentRequestDto);
        return  returnResponseDtoList(pageRequest, savedComment);
    }


    // 댓글 삭제
    public void delete(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."+ id)
        );
        commentRepository.delete(comment);
    }



    // PageRequest 객체 만들어서 처리하는 방식
    // requestDto 로 pageNumber 를  받아서 처리하는 방식
    // 생성 날짜로 정렬 후 10개씩 잘라서 리턴
    private PageRequest makePageRequest(CommentRequestDto commentRequestDto){
        return PageRequest
               .of(commentRequestDto.getPageNumber(), 10, Sort.by(Sort.Direction.ASC, "createDate"));
    }



    private List<CommentResponseDto> returnResponseDtoList(PageRequest pageRequest, Comment comment){
        List<Comment> commentList = commentRepository.findAllByPost_Id(pageRequest, comment.getPost().getId());

        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        // DTO 변환
        for (Comment commentEntity : commentList) {

            CommentResponseDto commentResponseDto = new CommentResponseDto(
                    commentEntity.getId(),
                    commentEntity.getContent(),
                    commentEntity.getCreateDate(),
                    commentEntity.getUser().getNickName()
            );

            responseDtoList.add(commentResponseDto);

        }

        return  responseDtoList;
    }


}
