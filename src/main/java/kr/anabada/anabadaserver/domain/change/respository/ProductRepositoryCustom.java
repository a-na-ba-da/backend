package kr.anabada.anabadaserver.domain.change.respository;

import kr.anabada.anabadaserver.domain.change.dto.MyProductResponse;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.respository.ProductRepositoryImpl.SearchProductRecord;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepositoryCustom {
    Page<MyProductResponse> findUserProductList(SearchProductRecord searchProduct);

    void createChangeRequest(Long targetId, List<Long> myProductIds);

    void updateStatus(List<Long> productIds, ProductStatus status);
}
