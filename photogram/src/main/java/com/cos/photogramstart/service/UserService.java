package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${file.path}") 
	private String uploadFolder; 
	
	// 회원프로필 사진변경 -----------------------------------
	@Transactional
	public User updateProfilePhoto(int principalId, MultipartFile profileImageFile) {
		UUID uuid = UUID.randomUUID(); // uuid
		String imageFileName = uuid+"_"+profileImageFile.getOriginalFilename(); // 1.jpg 
		System.out.println("이미지 파일이름 :  "+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		// 통신, I/O -> 예외가 발생할 수 있다. 
		try {
			Files.write(imageFilePath, profileImageFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalId).orElseThrow(()->{
			throw new CustomApiException("유저를 찾을 수 없습니다.");
		});
		userEntity.setProfileImageUrl(imageFileName);
		
		return userEntity;
	} // 더티체킹으로 업데이트 됨.
	
	@Transactional(readOnly = true)
	public UserProfileDto memberProfile(int pageUserId, int principalId) { // 회원 프로필
		UserProfileDto dto = new UserProfileDto(); // 데이타 트랜스 오브젝트
		
		// SELECT * FROM image WHERE userId = :userId;
		// JPA 방식
		User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> { // Image는 안가져옴. from User만 진행.
			throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
		});
		
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId); // 1은 페이지 주인, -1은 주인이 아님
		dto.setImageCount(userEntity.getImages().size());
		
		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		
		dto.setSubscribeState(subscribeState == 1);
		dto.setSubscribeCount(subscribeCount);
		
		// 좋아요 카운트 추가하기 
		userEntity.getImages().forEach((image)->{
			image.setLikeCount(image.getLikes().size());
		});
		
		return dto;
	}
	
	@Transactional
	public User modifyMember(int id, User user) { // 회원수정
		// 1. 영속화
		// Optional Wrapping 1. 무조건 찾았다 get() 2. 못찾았어 Exception 발동 시킬께 orElseThrow() 
		User userEntity = userRepository.findById(id).orElseThrow(() -> {	return new CustomValidationApiException("찾을 수 없는 id입니다.");});
		
		// 2. 영속화된 오브젝트를 수정 - 더티 체킹(업데이트 완료)
		userEntity.setName(user.getName());
		
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
				 
		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
		return userEntity;
	} // 더티 체킹이 일어나서 업데이트가 완료됨.
}
