package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
	// 댓글쓰기
	@Transactional
	public Comment saveComment(String content, int imageId, int userId) {		
		
		// Tip (객체를 만들 때 id값만 담아서 insert 할 수 있다.)
		// 대신 return 할 때 image객체와 user 객체는 id값만 가지고 있는 빈 객체를 리턴받는다.
		Image image = new Image();
		image.setId(imageId);
		
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			return new CustomApiException("유저 아이디를 찾을 수 없습니다.");
		});
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(userEntity);
		
		return commentRepository.save(comment);
	}
	
	// 댓글삭제
	@Transactional
	public void deleteComment(int id) { 
		try {
			commentRepository.deleteById(id);					
		} catch (Exception e) {
			throw new CustomApiException(e.getMessage()); // data를 리턴하는 컨트롤러 에러 처리
		}
	}
}
