package kr.anabada.anabadaserver.domain.change.service;

import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.common.service.NaverProductService;
import kr.anabada.anabadaserver.domain.change.dto.MyProductRequest;
import kr.anabada.anabadaserver.domain.change.dto.MyProductResponse;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.change.respository.ProductRepository;
import kr.anabada.anabadaserver.domain.change.respository.ProductRepositoryImpl.SearchProductRecord;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.anabada.anabadaserver.domain.change.dto.ProductStatus.AVAILABLE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final NaverProductService naverProductService;
    private final ImageService imageService;
    private final ProductRepository myProductRepository;

    @Transactional
    public void createMyProduct(User owner, MyProductRequest request) {
        var productInfo = naverProductService.parseNaverProduct(request.getNaverProductId());

        MyProduct myProduct =
                myProductRepository.save(
                        MyProduct.builder()
                                .owner(owner)
                                .originalPrice(productInfo.price())
                                .name(productInfo.productName())
                                .content(request.getContent())
                                .status(AVAILABLE)
                                .build());

        imageService.attach(owner.getId(), request.getImages(), myProduct.getId());
    }

    public Page<MyProductResponse> getMyProducts(User owner, String searchWord, Pageable pageable, Boolean searchAllStatus) {
        return myProductRepository.findUserProductList(new SearchProductRecord(owner, searchWord, pageable, searchAllStatus));
    }

    public Page<MyProductResponse> getProducts(String searchWord, Pageable pageable) {
        return myProductRepository.findUserProductList(new SearchProductRecord(null, searchWord, pageable, false));
    }
}
