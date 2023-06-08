package kr.anabada.anabadaserver.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "image_type", nullable = false, length = 15)
    private String imageType;

    @Column(name = "ext", nullable = false, length = 5)
    private String extension;

    @Column(name = "original_file_name", length = 100)
    private String originalFileName;

//    @Column(name = "path", nullable = false, length = 150)
//    private String path;

    @Column(name = "uploader", nullable = false)
    private Long uploader;
}