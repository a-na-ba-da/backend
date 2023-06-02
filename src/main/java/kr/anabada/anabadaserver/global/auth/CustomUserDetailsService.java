package kr.anabada.anabadaserver.global.auth;

import jakarta.persistence.EntityNotFoundException;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("called loadUserByUsername. email: {}", email);
        User account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("계정을 찾을 수 없습니다."));

        if (account.isBan()) {
            throw new EntityNotFoundException("계정이 정지되었습니다.");
        }

        return new SecurityUser(account);
    }
}
