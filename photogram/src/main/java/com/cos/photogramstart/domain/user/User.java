package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA - Javer Persistance API ( 자바로 데이터를 영구적으로 저장할 수 있는 API 제공)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data // Getter, Setter, toString() 자동 생성됨
@Entity // 디비에 테이블을 생성
public class User {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
	private int id;
	
	@Column(length = 100, unique =  true) // 제약조건, OAuth2 로그인을 위해 칼럼 늘리기 
	private String username;
	@Column(nullable = false)
	private String password;	
	@Column(nullable = false)
	private String name;
	private String website; // 웹 사이트
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl; // 사진
	private String role; // 권한 

	// 1.  나는 연관관계의 주인이 아니다. 그러므로 테이블에 Colum을 만들지마.
	// 2. User를 Select 할 때 해당 User id로 등록된 image들을 모두 가져와.
	
	/* fetch 옵션 *************************************************************************
		 LAZY = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마 ---> from user
		                대신 getImages() 함수의 image들이 호출될 때 가져와! ---> from image 
		 EAGER = User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와!! ---> from user left outer join image 
	*/
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)  // 연관관계, Image = One, User = Many
	// FetchType.LAZY : 해당 Entity(테이블) 조인해서 "필요하면" 데이타 가져올게!
	// user 하나 선택할때마다 이미지가 여러개 불러오므로 LAZY 전략을 씀.
	@JsonIgnoreProperties({"user"}) // Images 내부 User는 파싱하지 않음 -- 무한참조 예방.
	private List<Image> images; // 양방향 매핑
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role +", createDate="
				+ createDate + "]"; // image 부분 삭제해서 무한참조 없앰.
	}

}
