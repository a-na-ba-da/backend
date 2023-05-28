package kr.anabada.anabadaserver.common.controller;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "writer", nullable = false)
    private Long writer;

    @Column(name = "border_type", length = 10)
    private String borderType;

    @Column(name = "class", nullable = false)
    private Boolean classField = false;

    // TODO : order는 DB에서 예약어이므로 다른 이름으로 변경해야함 - 성훈
    @Column(name = "`order`", nullable = false)
    private Short order;

    @Column(name = "group_number", nullable = false)
    private Short groupNumber;

}