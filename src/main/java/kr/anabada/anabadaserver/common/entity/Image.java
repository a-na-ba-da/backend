package kr.anabada.anabadaserver.common.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "image_type", nullable = false, length = 10)
    private String imageType;

    @Column(name = "original_file_name", length = 100)
    private String originalFileName;

    @Column(name = "path", nullable = false, length = 150)
    private String path;

    @Column(name = "uploader", nullable = false)
    private Long uploader;

}