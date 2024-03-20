package shop.mtcoding.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import shop.mtcoding.bank.domain.user.User;

public class UserRespDto {
    @ToString
    @Setter
    @Getter
    public static class JoinRespDto {
        private Long id;
        private String username;
        private String password;
        private String email;
        private String fullname;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.email = user.getEmail();
            this.fullname = user.getFullname();
        }
    }

}
