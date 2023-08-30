package kr.anabada.anabadaserver.domain.share.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.BaseTimeEntity;
import kr.anabada.anabadaserver.common.entity.Image;
import kr.anabada.anabadaserver.domain.share.dto.request.LendPostRequest;
import kr.anabada.anabadaserver.domain.share.dto.response.LendResponse;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "lend")
@Where(clause = "is_removed = false")
@SQLDelete(sql = "UPDATE lend SET is_removed = true WHERE id = ?")
public class Lend extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "writer", nullable = false)
    private User writer;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "start_at", nullable = false)
    private LocalDate start;

    @Column(name = "end_at")
    private LocalDate end;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "price_per_day", columnDefinition = "int not null")
    private Long pricePerDay;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng", nullable = false)
    private Double lng;

    @Builder.Default
    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;

    @Builder.Default
    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @BatchSize(size = 100)
    @JoinColumn(name = "post_id")
    @OneToMany(fetch = FetchType.LAZY)
    @Where(clause = "image_type = 'LEND'")
    private List<Image> images;


    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void editPost(LendPostRequest lendPostRequest) {
        if (StringUtils.hasText(lendPostRequest.getTitle()))
            this.title = lendPostRequest.getTitle();

        if (StringUtils.hasText(lendPostRequest.getContent()))
            this.content = lendPostRequest.getContent();

        if (lendPostRequest.getLat() != null)
            this.lat = lendPostRequest.getLat();

        if (lendPostRequest.getLng() != null)
            this.lng = lendPostRequest.getLng();

        if (lendPostRequest.getPricePerDay() != null)
            this.pricePerDay = lendPostRequest.getPricePerDay();

        if (lendPostRequest.getStart() != null)
            this.start = lendPostRequest.getStart();

        if (lendPostRequest.getEnd() != null)
            this.end = lendPostRequest.getEnd();

        if (lendPostRequest.getImages() != null)
            this.images = lendPostRequest.getImages().stream()
                    .map(imageId -> {
                        Image newImage = new Image();
                        newImage.setId(UUID.fromString(imageId));
                        return newImage;
                    })
                    .toList();
    }


    public LendResponse toResponse() {
        return LendResponse.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .createdAt(getCreatedAt())
                .modifiedAt(getModifiedAt())
                .writer(this.writer.toDto())
                .start(this.start)
                .end(this.end)
                .lat(this.lat)
                .lng(this.lng)
                .images(this.images.stream().map(Image::getId).map(UUID::toString).toList())
                .build();
    }
}