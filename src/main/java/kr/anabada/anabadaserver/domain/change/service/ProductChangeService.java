package kr.anabada.anabadaserver.domain.change.service;

import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.change.respository.ProductRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductChangeService {
    private final ProductRepository productRepository;

    @Transactional
    public void changeRequest(User user, long targetProductId, List<Long> toChangeProductIds) {
        if (toChangeProductIds == null || toChangeProductIds.isEmpty())
            throw new IllegalArgumentException("교환 신청할 물건은 최소 1개 이상이어야 합니다.");

        List<MyProduct> myProducts = productRepository.findAllByOwnerAndStatus(user, ProductStatus.AVAILABLE);

        // check toChange products are available and in my products
        for (Long toChangeProductId : toChangeProductIds) {
            if (myProducts.stream().noneMatch(myProduct -> Objects.equals(myProduct.getId(), toChangeProductId)))
                throw new IllegalArgumentException(String.format("%d는 변경 신청 가능한 물건이 아닙니다.", toChangeProductId));
        }

        // check target product is available
        MyProduct targetProduct = productRepository.findById(targetProductId).orElseThrow(() -> new IllegalArgumentException("상대 물건이 존재하지 않습니다."));
        if (targetProduct.getStatus() != ProductStatus.AVAILABLE)
            throw new IllegalArgumentException(String.format("%d는 변경 신청 가능한 물건이 아닙니다.", targetProductId));

        // update product status
        productRepository.updateStatus(toChangeProductIds, ProductStatus.REQUESTING);

        // create change request
        productRepository.createChangeRequest(targetProductId, toChangeProductIds);
    }
}