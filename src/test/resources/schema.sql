/* 1. START DELETE TABLE  ---------------- */
drop table if exists change_request;

drop table if exists change_request_product;

drop table if exists comment;

drop table if exists image;

drop table if exists lend;

drop table if exists member;

drop table if exists message;

drop table if exists message_origin;

drop table if exists my_product;

drop table if exists recycle;

drop table if exists recycle_like;

drop table if exists report;

drop table if exists save;
/* 1. END DELETE TABLE  ---------------- */


/* 2. START CREATE TABLE  ---------------- */
create table change_request
(
    id                   bigint auto_increment
        primary key,
    product_id           bigint               not null comment '상대물건 fk',
    requester_id         bigint               not null comment '요청자 fk',
    requestee_id         bigint               null,
    request_message      varchar(30)          not null comment '요청자의 메세지',
    reject_message       varchar(30)          null comment '거절 메세지',
    status               varchar(15)          null comment '요청 상태',
    modified_at          datetime(6)          null,
    created_at           datetime(6)          not null,
    removed_by_requester tinyint(1) default 0 not null comment '요청자가 지웠는지 여부',
    removed_by_requestee tinyint(1) default 0 not null comment '요청받는이가 지웠는지 여부'
)
    comment '바꿔주세요';

create table change_request_product
(
    id                bigint auto_increment
        primary key,
    change_request_id bigint   not null comment '교환 요청 fk',
    product_id        bigint   null,
    created_at        datetime null,
    modified_at       datetime null
)
    comment '바꿔주세요 요청 상세 물건';

create table comment
(
    id                bigint auto_increment
        primary key,
    post_type         varchar(15)          not null comment '댓글이 달릴 도메인 타입 (ex. 같이사요 .... 같이 알아요 ....)',
    post_id           bigint               not null comment '게시글 fk',
    content           varchar(500)         not null comment '댓글 내용',
    writer_id         bigint               not null comment '댓글 작성자 fk',
    parent_comment_id bigint               null comment '대댓글일시.... 부모 댓글의 id값',
    is_removed        tinyint(1) default 0 not null,
    created_at        datetime             null,
    modified_at       datetime             null
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
    id              bigint auto_increment
        primary key,
    message_type    varchar(30) not null comment '메세지가 어떤 도메인으로 부터 시작되었는지 확인하는 타입 스트링',
    message_post_id bigint      not null comment '메세지의 타입 엔티티에서 id 값',
    sender_id       bigint      null comment '메세지 보내는 사람 id',
    receiver_id     bigint      null comment '메세지 받는 사람의 id',
    created_at      datetime(6) not null,
    modified_at     datetime(6) null
)
    comment '메세지의 원천';

create table message
(
    id                  bigint auto_increment
        primary key,
    content             varchar(500)         not null comment '쪽지 내용',
    message_type        smallint             not null comment '메세지 타입 ( 0 = 알림, 1 = sender가 보냄, 11 = receiver가 보냄)',
    deleted_by_sender   tinyint(1) default 0 null,
    deleted_by_receiver tinyint(1) default 0 null,
    message_origin_id   bigint               not null comment '메세지의 원천',
    created_at          datetime(6)          not null,
    modified_at         datetime(6)          null,
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
    comment_cnt        bigint     default 0       null comment '댓글 갯수 카운트',
    created_at         datetime(6)                not null,
    modified_at        datetime(6)                null,
    is_removed         tinyint(1) default (false) not null comment '삭제 여부'
)
    comment '아껴쓰기 테이블 (같이사요 + 같이 알아요 )';