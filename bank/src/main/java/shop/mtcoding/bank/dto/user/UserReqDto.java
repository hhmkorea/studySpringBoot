package shop.mtcoding.bank.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

public class UserReqDto {
    @Setter
    @Getter
    public static class LoginReqDto { // Controller가기 전에 동작해서 유효성 검사 못함.
        private String username;
        private String password;
    }

    @Setter
    @Getter
    public static class JoinReqDto {
        // 영문, 숫자는 되고, 길이는 최소 2~20자 내외
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2~20자 이내로 작성해주세요.") // 정규표현식
        @NotEmpty // null이거나, 공백일 수 없다.
        private String username;

        // 길이 4~20
        @NotEmpty
        @Size(min = 4, max = 20) // @Size : String에서만 사용가능
        private String password;

        // 이메일 형식
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA]{2,3}$", message = "이메일 형식으로 작성해주세요.") // 정규표현식
        private String email;

        // 영어, 한글, 1~20
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~20자 이내로 작성해주세요.") // 정규표현식
        private String fullname;

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }
}
