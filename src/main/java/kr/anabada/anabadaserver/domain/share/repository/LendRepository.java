package kr.anabada.anabadaserver.domain.share.repository;

import kr.anabada.anabadaserver.domain.share.entity.Lend;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LendRepository extends JpaRepository<Lend, Long>, LendRepositoryCustom {
    Optional<Lend> findByIdAndWriter(Long lendId, User userId);

    List<Lend> findAllByWriter(User user);
}
