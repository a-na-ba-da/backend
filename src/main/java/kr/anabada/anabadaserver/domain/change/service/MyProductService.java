package kr.anabada.anabadaserver.domain.change.service;

import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.common.service.NaverProductService;
import kr.anabada.anabadaserver.domain.change.dto.MyProductRequest;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.change.respository.MyProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.anabada.anabadaserver.domain.change.dto.ProductStatus.AVAILABLE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyProductService {
    private final NaverProductService naverProductService;
    private final ImageService imageService;
    private final MyProductRepository myProductRepository;

    @Transactional
    public void createMyProduct(Long userId, MyProductRequest request) {
        var productInfo = naverProductService.parseNaverProduct(request.getNaverProductId());

        MyProduct myProduct =
                myProductRepository.save(
                        MyProduct.builder()
                                .owner(userId)
                                .originalPrice(productInfo.price())
                                .name(productInfo.productName())
                                .content(request.getContent())
                                .status(AVAILABLE)
                                .build());

        imageService.attach(userId, request.getImages(), myProduct.getId());
    }
}
