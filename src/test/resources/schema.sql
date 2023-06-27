create table change_request
(
    id                bigint auto_increment
        primary key,
    product_id        bigint                     not null,
    created_at        datetime(6)                not null,
    place             varchar(30)                not null,
    requester_id      bigint                     not null,
    requester_product bigint                     not null,
    reject_message    varchar(30)                null comment '거절 사유',
    status            tinyint(1)                 null comment '무반응(null), 거절,  승인',
    is_removed        tinyint(1) default (false) not null
)
    comment '바꿔주세요';

create table comment
(
    id           bigint auto_increment
        primary key,
    content      varchar(100)    not null comment '댓글 내용',
    writer       bigint          not null,
    border_type  varchar(10)     null comment '댓글이 달릴 도메인 타입 (ex. 같이사요 .... 같이 알아요 ....)',
    class        bit default (0) not null comment '댓글과 대댓글을 구성하기 위한 계층
0 = 부모, 1 = 자식 (대댓글 )',
    ordering     smallint        not null comment '댓글과 대댓글의 순서',
    group_number smallint        not null comment '댓글 그룹'
);

create table image
(
    id                 binary(16)   not null
        primary key,
    image_type         varchar(15)  not null,
    ext                varchar(5)   null comment '확장자명',
    post_id            mediumtext   null,
    original_file_name varchar(100) null,
    uploader           bigint       not null,
    created_at         datetime     null
);

create table lend
(
    id            bigint auto_increment
        primary key,
    writer        bigint                     not null,
    title         varchar(50)                null,
    start_at      datetime(6)                not null,
    end_at        datetime(6)                null,
    content       varchar(300)               not null comment '상품 설명',
    price_per_day int                        not null,
    lat           double                     null,
    lng           double                     not null,
    created_at    datetime(6)                not null,
    modified_at   datetime(6)                null,
    is_removed    tinyint(1) default (false) not null comment '삭제 여부',
    status        tinyint(1) default (false) not null comment '빌렸는지 여부'
)
    comment '빌려주세요';

create table member
(
    id        bigint auto_increment
        primary key,
    role      varchar(20) not null,
    nickname  varchar(30) null,
    email     varchar(50) not null,
    activated bit         null,
    constraint user_pk
        unique (nickname),
    constraint user_pk2
        unique (email)
);

create table message_origin
(
    id                bigint      not null
        primary key,
    message_type      varchar(10) not null comment '메세지가 어떤 도메인으로 부터 시작되었는지 확인하는 타입 스트링',
    message_detail_id bigint      not null comment '메세지의 타입 엔티티에서 id 값',
    created_at        datetime(6) not null
)
    comment '메세지의 원천';

create table message
(
    id                  bigint auto_increment
        primary key,
    title               varchar(50)                not null,
    content             varchar(500)               not null,
    deleted_by_sender   tinyint(1) default (false) not null,
    deleted_by_receiver tinyint(1) default (false) not null,
    sender_id           bigint                     not null,
    receiver_id         bigint                     not null,
    created_at          datetime(6)                not null,
    message_type        varchar(10)                not null comment '메세지가 어떤 도메인으로 부터 시작되었는지 확인하는 타입 스트링',
    message_detail_id   bigint                     not null comment 'message_type 엔티티의 id',
    message_origin_id   bigint                     not null comment '메세지의 원천',
    constraint fk_message_message_origin
        foreign key (message_origin_id) references message_origin (id)
);

create table my_product
(
    id             bigint auto_increment
        primary key,
    owner          bigint                     not null,
    name           varchar(100)               null,
    original_price int unsigned               not null,
    content        varchar(700)               not null comment '설명',
    status         varchar(20)                not null,
    category       varchar(40)                null,
    is_removed     tinyint(1) default (false) not null comment '삭제 여부'
);

create table recycle
(
    id          bigint auto_increment
        primary key,
    title       varchar(50)                not null,
    writer      bigint                     not null,
    content     varchar(300)               null,
    created_at  datetime(6)                not null,
    modified_at datetime(6)                null,
    is_removed  tinyint(1) default (false) not null comment '삭제 여부'
)
    comment '다시쓰기';

create table recycle_like
(
    id         bigint auto_increment
        primary key,
    recycle_id bigint not null,
    member_id  bigint not null
)
    comment '다시쓰기 좋아요';

create table report
(
    id                 bigint auto_increment
        primary key,
    report_type        varchar(15)          not null,
    report_target_id   bigint               not null,
    reporter_member_id bigint               not null,
    reported_member_id bigint               not null,
    content            varchar(500)         null comment '신고내용',
    confirmed          tinyint(1) default 0 null comment '관리자 확인 여부',
    created_at         datetime(6)          not null
);

create table requester_product
(
    id         bigint auto_increment
        primary key,
    product_id bigint not null
);

create table save
(
    id                 bigint auto_increment
        primary key,
    writer             bigint                     not null,
    title              varchar(50)                not null,
    content            varchar(700)               not null,
    is_online_delivery tinyint(1)                 null comment '(같이사요 ) 전달방법 ( 대, 비대면 )',
    buy_date           date                       null comment '(같이사요 ) 구매일',
    product_url        varchar(500)               null comment '(같이사요,  같이 알아요 ) 상품 주소',
    pay                int                        null comment '(같이사요 ) 너가 내야되는 돈',
    is_online          tinyint(1)                 null comment '(같이 알아요) 구매처 온오프라인 여부',
    buy_place_lat      double                     null,
    buy_place_lng      double                     null,
    buy_place_detail   varchar(15)                null comment '오프라인 구매처 위경도에 대한 자세한 텍스트 정보 (상호명 등)',
    save_type          varchar(15)                not null comment '같이사요, 같이 알아요 type',
    created_at         datetime(6)                not null,
    modified_at        datetime(6)                null,
    is_removed         tinyint(1) default (false) not null comment '삭제 여부'
)
    comment '아껴쓰기 테이블 (같이사요 + 같이 알아요 )';