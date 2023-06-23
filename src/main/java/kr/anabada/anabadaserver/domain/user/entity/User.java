package kr.anabada.anabadaserver.domain.user.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
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
}