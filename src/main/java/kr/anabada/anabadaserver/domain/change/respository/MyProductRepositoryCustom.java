package kr.anabada.anabadaserver.domain.change.respository;

import kr.anabada.anabadaserver.domain.change.dto.MyProductResponse;
import kr.anabada.anabadaserver.domain.change.respository.MyProductRepositoryImpl.SearchProductRecord;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface MyProductRepositoryCustom {
    Page<MyProductResponse> findUserProductList(SearchProductRecord searchProduct);
}
