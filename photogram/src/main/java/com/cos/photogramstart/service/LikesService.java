package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
	private final LikesRepository likesRepository;
	
	// 좋아요
	@Transactional
	public void doLike(int mageId, int principalId) {
		likesRepository.mLikes(mageId, principalId);
	}
	
	// 좋아요 취소 
	@Transactional
	public void undoLike(int mageId, int principalId) {
		likesRepository.mUnLikes(mageId, principalId);
	}
}
