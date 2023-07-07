package kr.anabada.anabadaserver.domain;

import kr.anabada.anabadaserver.common.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class ServiceTestWithoutImageUpload {
    @MockBean
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        System.out.println("imageService.attach()는 무시하고 테스트를 진행합니다.");
        doNothing().when(imageService).attach(anyLong(), anyList(), anyLong());
    }
}
