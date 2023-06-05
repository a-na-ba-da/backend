package kr.anabada.anabadaserver.domain.recycle;

import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecycleServiceTest {
    @Autowired
    RecycleRepository recycleRepository;

    @Test
    @DisplayName("recyle 게시글 작성 테스트")
    void testSave(){


    }

}
