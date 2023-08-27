package kr.anabada.anabadaserver.domain.change.respository;

import kr.anabada.anabadaserver.domain.change.entity.ChangeRequest;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChangeRequestRepository extends JpaRepository<ChangeRequest, Long>, ChangeRequestRepositoryCustom {
    @Query("select c from ChangeRequest c where c.requester = :user or c.requestee = :user")
    List<ChangeRequest> findAllByTargetUser(@Param("user") User user);
}
