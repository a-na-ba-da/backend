package kr.anabada.anabadaserver.domain.change.service;

import kr.anabada.anabadaserver.domain.change.dto.ChangeRequestResponse;
import kr.anabada.anabadaserver.domain.change.dto.ChangeRequestStatus;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.entity.ChangeRequest;
import kr.anabada.anabadaserver.domain.change.entity.ChangeRequestProduct;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.change.respository.ChangeRequestProductRepository;
import kr.anabada.anabadaserver.domain.change.respository.ChangeRequestRepository;
import kr.anabada.anabadaserver.domain.change.respository.ProductRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductChangeService {
    private final ProductRepository productRepository;
    private final ChangeRequestRepository changeRequestRepository;
    private final ChangeRequestProductRepository requestProductRepository;

    public ChangeRequestResponse getAllChangeRequest(User user) {
        return new ChangeRequestResponse(
                changeRequestRepository.getAllRequestedByMe(user),
                changeRequestRepository.getAllRequestingForMe(user));
    }

    @Transactional
    public void changeRequest(User requestUser, long targetProductId, List<Long> toChangeProductIds, String message) {
        if (toChangeProductIds == null || toChangeProductIds.isEmpty())
            throw new IllegalArgumentException("교환 신청할 물건은 최소 1개 이상이어야 합니다.");

        // check toChange products are available and in my products
        Set<MyProduct> myProductsToChange = productRepository.findAllByOwnerAndStatus(requestUser, ProductStatus.AVAILABLE)
                .stream()
                .filter(product -> toChangeProductIds.contains(product.getId()))
                .collect(Collectors.toSet());

        if (myProductsToChange.size() != toChangeProductIds.size())
            throw new IllegalArgumentException("교환 신청에 사용된 나의 물건 중 잘못된 물건이 포함되어 있습니다.");

        // check target product is available
        MyProduct targetProduct = productRepository.findById(targetProductId).orElseThrow(() -> new IllegalArgumentException("상대 물건이 존재하지 않습니다."));
        if (targetProduct.getStatus() != ProductStatus.AVAILABLE)
            throw new IllegalArgumentException(String.format("%d는 변경 신청 가능한 물건이 아닙니다.", targetProductId));

        // update product status
        productRepository.updateStatus(toChangeProductIds, ProductStatus.REQUESTING);

        // create change request
        ChangeRequest request = ChangeRequest.builder()
                .targetProduct(targetProduct)
                .status(ChangeRequestStatus.REQUESTING)
                .message(message)
                .requester(requestUser)
                .requestee(targetProduct.getOwner())
                .build();
        changeRequestRepository.save(request);

        // create change request detail
        List<ChangeRequestProduct> changeProducts = myProductsToChange.stream()
                .map(myProduct -> {
                    ChangeRequestProduct changeRequestProduct = ChangeRequestProduct.builder()
                            .product(myProduct)
                            .build();
                    changeRequestProduct.setChangeRequest(request);
                    return changeRequestProduct;
                })
                .toList();

        requestProductRepository.saveAll(changeProducts);
    }
}