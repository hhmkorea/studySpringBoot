package shop.mtcoding.bank.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import shop.mtcoding.bank.dto.user.UserReqDto.*;
import shop.mtcoding.bank.dto.user.UserRespDto.*;

// Spring 관련 Bean들이 하나도 없는 환경.
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Spy // 진짜 psssword를 만들어서 @InjectMocks UserService에 넣음.
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void joinMember_test() throws Exception {
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("쌀");

        // stub1 : 가정법, 가설
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.empty());
        //Mockito.when(userRepository.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.of(new User()));

        // stub2
        User ssar = User.builder()
                .id(1L)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .fullname("쌀")
                .role(UserEnum.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(ssar);

        // when
        JoinRespDto joinRespDto = userService.joinMember(joinReqDto);
        System.out.println("테스트: "+joinRespDto);

        // then
        Assertions.assertThat(joinRespDto.getId()).isEqualTo(1L);
        Assertions.assertThat(joinRespDto.getUsername()).isEqualTo("ssar");
    }
}
