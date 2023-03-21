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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    
    // 댓글 조회
    public List<CommentResponseDto> findComments(CommentRequestDto commentRequestDto, Long postId){
        PageRequest pageRequest = makePageRequest(commentRequestDto);
        return returnResponseDtoList(pageRequest, postId);
    }
    
    
    
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

       return findComments(commentRequestDto, savedComment.getPost().getId());
    }


    // 댓글 수정
    public List<CommentResponseDto> update(CommentRequestDto commentRequestDto){

        Comment comment = commentRepository.findById(commentRequestDto.getId()).orElseThrow(
                        ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."+ commentRequestDto.getId())
                );

        comment.update(commentRequestDto.getContent());

        Comment savedComment = commentRepository.save(comment);

        return findComments(commentRequestDto, savedComment.getPost().getId());
    }


    // 댓글 삭제
    public List<CommentResponseDto> delete(CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(commentRequestDto.getId()).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."+ commentRequestDto.getId())
        );
        commentRepository.delete(comment);
        return findComments(commentRequestDto, comment.getPost().getId());
    }



    // PageRequest 객체 만들어서 처리하는 방식
    // requestDto 로 pageNumber 를  받아서 처리하는 방식
    // 생성 날짜로 정렬 후 10개씩 잘라서 리턴
    private PageRequest makePageRequest(CommentRequestDto commentRequestDto){
        return PageRequest
               .of(commentRequestDto.getPageNumber(), 10, Sort.by(Sort.Direction.ASC, "createDate"));
    }


    @Transactional(readOnly = true)
    public List<CommentResponseDto> returnResponseDtoList(PageRequest pageRequest, Long postId){
        List<Comment> commentList = commentRepository.findAllByPost_Id(pageRequest, postId);

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
