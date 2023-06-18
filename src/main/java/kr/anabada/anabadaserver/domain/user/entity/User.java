package kr.anabada.anabadaserver.domain.user.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nickname", nullable = false, length = 30)
    private String nickname;
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Column(name = "activated", nullable = false)
    private Boolean activated;
    @Column(name = "role", nullable = false)
    private String role;

    public boolean isBan() {
        if (role.equals("ROLE_ADMIN"))
            return false;

        return !activated;
    }
    
    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .nickname(nickname)
                .activated(activated)
                .build();
    }

    public void changeNickname(String nickname) {
        if (nickname == null || nickname.length() > 30) {
            throw new IllegalArgumentException("닉네임은 30자 이내로 입력해주세요.");
        }

        if (Objects.equals(this.nickname, nickname)){
            throw new IllegalArgumentException("이전 닉네임과 동일합니다.");
        }

        this.nickname = nickname;
    }
}