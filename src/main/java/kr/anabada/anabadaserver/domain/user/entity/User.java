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
@Table(name = "member")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!email.equals(user.email)) return false;
        if (!activated.equals(user.activated)) return false;
        return role.equals(user.role);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + nickname.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + activated.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}