package kr.anabada.anabadaserver.common.repository;

import kr.anabada.anabadaserver.common.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
