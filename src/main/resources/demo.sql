create table zdl_admin_category
(
    category_id   int auto_increment comment '分类id'
        primary key,
    category_name varchar(100)                       not null comment '分类名称',
    category_type smallint                           not null comment '分类类型',
    parent_id     int                                not null comment '父级id',
    sort          int      default 0                 not null comment '排序',
    disabled_flag tinyint  default 0                 not null comment '是否禁用',
    deleted_flag  tinyint  default 0                 not null comment '是否删除',
    remark        varchar(255)                       null,
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time   datetime default CURRENT_TIMESTAMP not null
)
    comment '分类表，主要用于商品分类' collate = utf8mb4_general_ci
                                      row_format = DYNAMIC;

create index idx_parent_id
    on zdl_admin_category (parent_id);

create table zdl_admin_change_log
(
    change_log_id  bigint auto_increment comment '更新日志id'
        primary key,
    version        varchar(255)                       not null comment '版本',
    type           int                                not null comment '更新类型:[1:特大版本功能更新;2:功能更新;3:bug修复]',
    publish_author varchar(255)                       not null comment '发布人',
    public_date    date                               not null comment '发布日期',
    content        text                               not null comment '更新内容',
    link           text                               null comment '跳转链接',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint version_unique
        unique (version)
)
    comment '系统更新日志' collate = utf8mb4_general_ci
                           row_format = DYNAMIC;

create table zdl_admin_config
(
    config_id    bigint auto_increment comment '主键'
        primary key,
    config_name  varchar(255)                       not null comment '参数名字',
    config_key   varchar(255)                       not null comment '参数key',
    config_value text                               not null,
    remark       varchar(255)                       null,
    update_time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '上次修改时间',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '系统配置' collate = utf8mb4_unicode_ci
                       row_format = DYNAMIC;

create table zdl_admin_data_tracer
(
    data_tracer_id bigint auto_increment
        primary key,
    data_id        bigint                             not null comment '各种单据的id',
    type           int                                not null comment '单据类型',
    content        text                               null comment '操作内容',
    diff_old       text                               null comment '差异：旧的数据',
    diff_new       text                               null comment '差异：新的数据',
    extra_data     text                               null comment '额外信息',
    user_id        bigint                             not null comment '用户id',
    user_type      int                                not null comment '用户类型：1 后管用户 ',
    user_name      varchar(50)                        not null comment '用户名称',
    ip             varchar(50)                        null comment 'ip',
    ip_region      varchar(1000)                      null comment 'ip地区',
    user_agent     varchar(2000)                      null comment '用户ua',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '各种单据操作记录' collate = utf8mb4_unicode_ci
                               row_format = DYNAMIC;

create index order_id_order_type
    on zdl_admin_data_tracer (data_id, type);

create table zdl_admin_department
(
    department_id bigint auto_increment comment '部门主键id'
        primary key,
    name          varchar(50)                        not null comment '部门名称',
    manager_id    bigint                             null comment '部门负责人id',
    parent_id     bigint   default 0                 not null comment '部门的父级id',
    sort          int                                not null comment '部门排序',
    update_time   datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '部门' collate = utf8mb4_unicode_ci
                   row_format = DYNAMIC;

create index parent_id
    on zdl_admin_department (parent_id);

create table zdl_admin_employee
(
    employee_id        bigint auto_increment comment '主键'
        primary key,
    login_name         varchar(30) charset utf8mb3          not null comment '登录帐号',
    login_pwd          varchar(50) charset utf8mb3          not null comment '登录密码',
    actual_name        varchar(30) charset utf8mb3          not null comment '员工名称',
    avatar             varchar(200)                         null,
    gender             tinyint(1) default 0                 not null comment '性别',
    phone              varchar(15) charset utf8mb3          null comment '手机号码',
    department_id      int                                  not null comment '部门id',
    position_id        bigint                               null comment '职务ID',
    email              varchar(100)                         null comment '邮箱',
    disabled_flag      tinyint unsigned                     not null comment '是否被禁用 0否1是',
    deleted_flag       tinyint unsigned                     not null comment '是否删除0否 1是',
    administrator_flag tinyint    default 0                 not null comment '是否为超级管理员: 0 不是，1是',
    remark             varchar(200)                         null comment '备注',
    update_time        datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time        datetime   default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '员工表' collate = utf8mb4_unicode_ci
                     row_format = DYNAMIC;

create table zdl_admin_login_fail
(
    login_fail_id         bigint auto_increment comment '自增id'
        primary key,
    user_id               bigint                             not null comment '用户id',
    user_type             int                                not null comment '用户类型',
    login_name            varchar(1000)                      null comment '登录名',
    login_fail_count      int                                null comment '连续登录失败次数',
    lock_flag             tinyint  default 0                 null comment '锁定状态:1锁定，0未锁定',
    login_lock_begin_time datetime                           null comment '连续登录失败锁定开始时间',
    create_time           datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time           datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uid_and_utype
        unique (user_id, user_type)
)
    comment '登录失败次数记录表' collate = utf8mb4_general_ci
                                 row_format = DYNAMIC;

create table zdl_admin_login_log
(
    login_log_id    bigint auto_increment comment '主键'
        primary key,
    user_id         int                                not null comment '用户id',
    user_type       int                                not null comment '用户类型',
    user_name       varchar(1000)                      not null comment '用户名',
    login_ip        varchar(1000)                      null comment '用户ip',
    login_ip_region varchar(1000)                      null comment '用户ip地区',
    user_agent      text                               null comment 'user-agent信息',
    login_result    int                                not null comment '登录结果：0成功 1失败 2 退出',
    remark          varchar(2000)                      null comment '备注',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户登录日志' collate = utf8mb4_unicode_ci
                           row_format = DYNAMIC;

create index customer_id
    on zdl_admin_login_log (user_id);

create table zdl_admin_menu
(
    menu_id         bigint auto_increment comment '菜单ID'
        primary key,
    menu_name       varchar(200)                         not null comment '菜单名称',
    menu_type       int                                  not null comment '类型',
    parent_id       bigint                               not null comment '父菜单ID',
    sort            int                                  null comment '显示顺序',
    path            varchar(255)                         null comment '路由地址',
    component       varchar(255)                         null comment '组件路径',
    perms_type      int                                  null comment '权限类型',
    api_perms       text                                 null comment '后端权限字符串',
    web_perms       text                                 null comment '前端权限字符串',
    icon            varchar(100)                         null comment '菜单图标',
    context_menu_id bigint                               null comment '功能点关联菜单ID',
    frame_flag      tinyint(1) default 0                 not null comment '是否为外链',
    frame_url       text                                 null comment '外链地址',
    cache_flag      tinyint(1) default 0                 not null comment '是否缓存',
    visible_flag    tinyint(1) default 1                 not null comment '显示状态',
    disabled_flag   tinyint(1) default 0                 not null comment '禁用状态',
    deleted_flag    tinyint(1) default 0                 not null comment '删除状态',
    create_user_id  bigint                               not null comment '创建人',
    create_time     datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user_id  bigint                               null comment '更新人',
    update_time     datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '菜单表' collate = utf8mb4_general_ci
                     row_format = DYNAMIC;

create table zdl_admin_oa_bank
(
    bank_id          bigint auto_increment comment '银行信息ID'
        primary key,
    bank_name        varchar(255)                         not null comment '开户银行',
    account_name     varchar(255)                         not null comment '账户名称',
    account_number   varchar(255)                         not null comment '账号',
    remark           varchar(255)                         null comment '备注',
    business_flag    tinyint(1)                           not null comment '是否对公',
    enterprise_id    bigint                               not null comment '企业ID',
    disabled_flag    tinyint(1) default 0                 not null comment '禁用状态',
    deleted_flag     tinyint(1) default 0                 not null comment '删除状态',
    create_user_id   bigint                               not null comment '创建人ID',
    create_user_name varchar(50)                          not null comment '创建人',
    create_time      datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment 'OA银行信息
' collate = utf8mb4_general_ci
  row_format = DYNAMIC;

create index idx_enterprise_id
    on zdl_admin_oa_bank (enterprise_id);

create table zdl_admin_oa_enterprise
(
    enterprise_id              bigint auto_increment comment '企业ID'
        primary key,
    enterprise_name            varchar(255)                         not null comment '企业名称',
    enterprise_logo            varchar(255)                         null comment '企业logo',
    type                       int        default 1                 not null comment '类型（1:有限公司;2:合伙公司）',
    unified_social_credit_code varchar(255)                         not null comment '统一社会信用代码',
    contact                    varchar(100)                         not null comment '联系人',
    contact_phone              varchar(100)                         not null comment '联系人电话',
    email                      varchar(100)                         null comment '邮箱',
    province                   varchar(100)                         null comment '省份',
    province_name              varchar(100)                         null comment '省份名称',
    city                       varchar(100)                         null comment '市',
    city_name                  varchar(100)                         null comment '城市名称',
    district                   varchar(100)                         null comment '区县',
    district_name              varchar(100)                         null comment '区县名称',
    address                    varchar(255)                         null comment '详细地址',
    business_license           varchar(255)                         null comment '营业执照',
    disabled_flag              tinyint(1) default 0                 not null comment '禁用状态',
    deleted_flag               tinyint(1) default 0                 not null comment '删除状态',
    create_user_id             bigint                               not null comment '创建人ID',
    create_user_name           varchar(50)                          not null comment '创建人',
    create_time                datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time                datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment 'OA企业模块
' collate = utf8mb4_general_ci
  row_format = DYNAMIC;

create table zdl_admin_oa_enterprise_employee
(
    enterprise_employee_id bigint auto_increment comment '主键ID'
        primary key,
    enterprise_id          varchar(100)                       not null comment '订单ID',
    employee_id            varchar(100)                       not null comment '货物名称',
    update_time            datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time            datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint uk_enterprise_employee
        unique (enterprise_id, employee_id)
)
    comment '企业关联的员工' collate = utf8mb4_general_ci
                             row_format = DYNAMIC;

create index idx_employee_id
    on zdl_admin_oa_enterprise_employee (employee_id);

create index idx_enterprise_id
    on zdl_admin_oa_enterprise_employee (enterprise_id);

create table zdl_admin_oa_invoice
(
    invoice_id                     bigint auto_increment comment '发票信息ID'
        primary key,
    invoice_heads                  varchar(255)                         not null comment '开票抬头',
    taxpayer_identification_number varchar(255)                         not null comment '纳税人识别号',
    account_number                 varchar(255)                         not null comment '银行账户',
    bank_name                      varchar(255)                         not null comment '开户行',
    remark                         varchar(255)                         null comment '备注',
    enterprise_id                  bigint                               not null comment '企业ID',
    disabled_flag                  tinyint(1) default 0                 not null comment '禁用状态',
    deleted_flag                   tinyint(1) default 0                 not null comment '删除状态',
    create_user_id                 bigint                               not null comment '创建人ID',
    create_user_name               varchar(50)                          not null comment '创建人',
    create_time                    datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time                    datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment 'OA发票信息
' collate = utf8mb4_general_ci
  row_format = DYNAMIC;

create index idx_enterprise_id
    on zdl_admin_oa_invoice (enterprise_id);

create table zdl_admin_operate_log
(
    operate_log_id    bigint auto_increment comment '主键'
        primary key,
    operate_user_id   bigint                             not null comment '用户id',
    operate_user_type int                                not null comment '用户类型',
    operate_user_name varchar(50)                        not null comment '用户名称',
    module            varchar(50)                        null comment '操作模块',
    content           varchar(500)                       null comment '操作内容',
    url               varchar(100)                       null comment '请求路径',
    method            varchar(100)                       null comment '请求方法',
    param             text                               null comment '请求参数',
    ip                varchar(255)                       null comment '请求ip',
    ip_region         varchar(1000)                      null comment '请求ip地区',
    user_agent        text                               null comment '请求user-agent',
    success_flag      tinyint                            null comment '请求结果 0失败 1成功',
    fail_reason       longtext                           null comment '失败原因',
    update_time       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '操作记录' collate = utf8mb4_unicode_ci
                       row_format = DYNAMIC;

create table zdl_admin_password_log
(
    id           bigint auto_increment comment '主键'
        primary key,
    user_id      bigint                             not null comment '用户id',
    user_type    tinyint                            not null comment '用户类型',
    old_password varchar(255)                       not null comment '旧密码',
    new_password varchar(255)                       null comment '新密码',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '密码修改记录' collate = utf8mb4_general_ci
                           row_format = DYNAMIC;

create index user_and_type_index
    on zdl_admin_password_log (user_id, user_type);

create table zdl_admin_position
(
    position_id   bigint auto_increment comment '职务ID'
        primary key,
    position_name varchar(200)                         not null comment '职务名称',
    level         varchar(200)                         null comment '职级',
    sort          int        default 0                 null comment '排序',
    remark        varchar(200)                         null comment '备注',
    deleted_flag  tinyint(1) default 0                 null,
    create_time   datetime   default CURRENT_TIMESTAMP not null,
    update_time   datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
)
    comment '职务表' collate = utf8mb4_general_ci
                     row_format = DYNAMIC;

create table zdl_admin_role
(
    role_id     bigint auto_increment comment '主键'
        primary key,
    role_name   varchar(20)                        not null comment '角色名称',
    role_code   varchar(500)                       null comment '角色编码',
    remark      varchar(255)                       null comment '角色描述',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint role_code_uni
        unique (role_code)
)
    comment '角色表' charset = utf8mb3
                     row_format = DYNAMIC;

create table zdl_admin_role_data_scope
(
    id              bigint auto_increment
        primary key,
    data_scope_type int                                not null comment '数据范围id',
    view_type       int                                not null comment '数据范围类型',
    role_id         bigint                             not null comment '角色id',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '角色的数据范围' collate = utf8mb4_unicode_ci
                             row_format = DYNAMIC;

create table zdl_admin_role_employee
(
    id          bigint auto_increment
        primary key,
    role_id     bigint                             not null comment '角色id',
    employee_id bigint                             not null comment '员工id',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint uk_role_employee
        unique (role_id, employee_id)
)
    comment '角色员工功能表' collate = utf8mb4_unicode_ci
                             row_format = DYNAMIC;

create table zdl_admin_role_menu
(
    role_menu_id bigint auto_increment comment '主键id'
        primary key,
    role_id      bigint                             not null comment '角色id',
    menu_id      bigint                             not null comment '菜单id',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '角色-菜单
' collate = utf8mb4_unicode_ci
  row_format = DYNAMIC;

create index idx_menu_id
    on zdl_admin_role_menu (menu_id);

create index idx_role_id
    on zdl_admin_role_menu (role_id);

create table zdl_admin_table_column
(
    table_column_id bigint auto_increment
        primary key,
    user_id         bigint                             not null comment '用户id',
    user_type       int                                not null comment '用户类型',
    table_id        int                                not null comment '表格id',
    columns         text                               null comment '具体的表格列，存入的json',
    create_time     datetime default CURRENT_TIMESTAMP not null,
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint uni_employee_table
        unique (user_id, table_id)
)
    comment '表格的自定义列存储' collate = utf8mb4_general_ci
                                 row_format = DYNAMIC;



INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (236, 1, 138, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (237, 1, 132, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (238, 1, 142, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (239, 1, 149, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (240, 1, 150, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (241, 1, 185, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (242, 1, 186, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (243, 1, 187, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (244, 1, 188, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (245, 1, 145, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (246, 1, 196, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (247, 1, 144, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (248, 1, 181, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (249, 1, 183, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (250, 1, 184, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (251, 1, 165, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (252, 1, 47, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (253, 1, 48, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (254, 1, 137, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (255, 1, 166, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (256, 1, 194, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (257, 1, 78, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (258, 1, 173, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (259, 1, 174, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (260, 1, 175, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (261, 1, 176, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (262, 1, 50, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (263, 1, 26, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (264, 1, 40, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (265, 1, 105, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (266, 1, 106, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (267, 1, 109, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (268, 1, 163, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (269, 1, 164, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (270, 1, 199, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (271, 1, 110, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (272, 1, 159, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (273, 1, 160, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (274, 1, 161, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (275, 1, 162, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (276, 1, 130, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (277, 1, 157, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (278, 1, 158, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (279, 1, 133, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (280, 1, 117, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (281, 1, 156, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (282, 1, 193, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (283, 1, 200, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (284, 1, 220, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (285, 1, 45, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (286, 1, 219, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (287, 1, 46, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (288, 1, 91, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (289, 1, 92, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (290, 1, 93, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (291, 1, 94, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (292, 1, 95, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (293, 1, 96, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (294, 1, 86, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (295, 1, 87, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (296, 1, 88, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (297, 1, 76, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (298, 1, 97, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (299, 1, 98, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (300, 1, 99, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (301, 1, 100, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (302, 1, 101, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (303, 1, 102, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (304, 1, 103, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (305, 1, 104, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (306, 1, 213, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (307, 1, 214, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (308, 1, 143, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (309, 1, 203, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (310, 1, 215, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (311, 1, 218, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (312, 1, 147, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (313, 1, 170, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (314, 1, 171, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (315, 1, 168, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (316, 1, 169, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (317, 1, 202, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (318, 1, 201, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (319, 1, 148, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (320, 1, 152, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (321, 1, 190, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (322, 1, 191, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (323, 1, 192, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (324, 1, 198, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (325, 1, 207, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (326, 1, 111, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (327, 1, 206, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (328, 1, 81, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (329, 1, 204, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (330, 1, 205, '2024-06-30 23:21:37', '2024-06-30 23:21:37');
INSERT INTO hooke.zdl_admin_role_menu (role_menu_id, role_id, menu_id, update_time, create_time) VALUES (331, 1, 122, '2024-06-30 23:21:37', '2024-06-30 23:21:37');

INSERT INTO hooke.zdl_admin_role_employee (id, role_id, employee_id, update_time, create_time) VALUES (325, 36, 63, '2022-10-19 20:25:26', '2022-10-19 20:25:26');
INSERT INTO hooke.zdl_admin_role_employee (id, role_id, employee_id, update_time, create_time) VALUES (329, 34, 72, '2022-11-05 10:56:54', '2022-11-05 10:56:54');
INSERT INTO hooke.zdl_admin_role_employee (id, role_id, employee_id, update_time, create_time) VALUES (330, 36, 72, '2022-11-05 10:56:54', '2022-11-05 10:56:54');
INSERT INTO hooke.zdl_admin_role_employee (id, role_id, employee_id, update_time, create_time) VALUES (333, 1, 44, '2023-10-07 18:53:29', '2023-10-07 18:53:29');
INSERT INTO hooke.zdl_admin_role_employee (id, role_id, employee_id, update_time, create_time) VALUES (334, 1, 47, '2023-10-07 18:55:00', '2023-10-07 18:55:00');
INSERT INTO hooke.zdl_admin_role_employee (id, role_id, employee_id, update_time, create_time) VALUES (341, 1, 48, '2024-09-02 23:03:28', '2024-09-02 23:03:28');

INSERT INTO hooke.zdl_admin_role_data_scope (id, data_scope_type, view_type, role_id, update_time, create_time) VALUES (67, 1, 2, 1, '2024-03-18 20:41:00', '2024-03-18 20:41:00');

INSERT INTO hooke.zdl_admin_role (role_id, role_name, role_code, remark, update_time, create_time) VALUES (1, '技术总监', null, '', '2022-10-19 20:24:09', '2019-06-21 12:09:34');
INSERT INTO hooke.zdl_admin_role (role_id, role_name, role_code, remark, update_time, create_time) VALUES (34, '销售总监', 'cto', '', '2023-09-06 19:10:34', '2019-08-30 09:30:50');
INSERT INTO hooke.zdl_admin_role (role_id, role_name, role_code, remark, update_time, create_time) VALUES (35, '总经理', null, '', '2019-08-30 09:31:05', '2019-08-30 09:31:05');
INSERT INTO hooke.zdl_admin_role (role_id, role_name, role_code, remark, update_time, create_time) VALUES (36, '董事长', null, '', '2019-08-30 09:31:11', '2019-08-30 09:31:11');
INSERT INTO hooke.zdl_admin_role (role_id, role_name, role_code, remark, update_time, create_time) VALUES (37, '财务', null, '', '2019-08-30 09:31:16', '2019-08-30 09:31:16');

INSERT INTO hooke.zdl_admin_position (position_id, position_name, level, sort, remark, deleted_flag, create_time, update_time) VALUES (7, 'P4', '1', 0, null, 0, '2024-12-26 04:02:06', '2024-12-26 04:02:06');

INSERT INTO hooke.zdl_admin_oa_invoice (invoice_id, invoice_heads, taxpayer_identification_number, account_number, bank_name, remark, enterprise_id, disabled_flag, deleted_flag, create_user_id, create_user_name, create_time, update_time) VALUES (15, '1024创新实验室', '1024lab', '1024lab', '中国银行', '123', 2, 0, 0, 1, '管理员', '2022-10-22 17:59:35', '2023-09-27 16:26:07');

INSERT INTO hooke.zdl_admin_oa_enterprise_employee (enterprise_employee_id, enterprise_id, employee_id, update_time, create_time) VALUES (154, '2', '2', '2022-10-22 17:57:50', '2022-10-22 17:57:50');
INSERT INTO hooke.zdl_admin_oa_enterprise_employee (enterprise_employee_id, enterprise_id, employee_id, update_time, create_time) VALUES (155, '2', '44', '2022-10-22 17:57:50', '2022-10-22 17:57:50');

INSERT INTO hooke.zdl_admin_oa_enterprise (enterprise_id, enterprise_name, enterprise_logo, type, unified_social_credit_code, contact, contact_phone, email, province, province_name, city, city_name, district, district_name, address, business_license, disabled_flag, deleted_flag, create_user_id, create_user_name, create_time, update_time) VALUES (1, '1024创新区块链实验室', 'public/common/34f5ac0fc097402294aea75352c128f0_20240306112435.png', 1, '1024lab_block', '开云', '18637925892', null, '410000', '河南省', '410300', '洛阳市', '410311', '洛龙区', '区块链大楼', 'public/common/1d89055e5680426280446aff1e7e627c_20240306112451.jpeg', 0, 0, 1, '管理员', '2021-10-22 17:03:35', '2022-10-22 17:04:18');
INSERT INTO hooke.zdl_admin_oa_enterprise (enterprise_id, enterprise_name, enterprise_logo, type, unified_social_credit_code, contact, contact_phone, email, province, province_name, city, city_name, district, district_name, address, business_license, disabled_flag, deleted_flag, create_user_id, create_user_name, create_time, update_time) VALUES (2, '1024创新实验室', '', 2, '1024lab', '卓大', '18637925892', 'lab1024@163.com', '410000', '河南省', '410300', '洛阳市', '410311', '洛龙区', '1024大楼', 'public/common/59b1ca99b7fe45d78678e6295798a699_20231201200459.jpg', 0, 0, 44, '卓大', '2022-10-22 14:57:36', '2022-10-22 17:03:57');

INSERT INTO hooke.zdl_admin_oa_bank (bank_id, bank_name, account_name, account_number, remark, business_flag, enterprise_id, disabled_flag, deleted_flag, create_user_id, create_user_name, create_time, update_time) VALUES (26, '工商银行', '1024创新实验室', '1024', '基本户', 1, 2, 0, 0, 1, '管理员', '2022-10-22 17:58:43', '2022-10-22 17:58:43');
INSERT INTO hooke.zdl_admin_oa_bank (bank_id, bank_name, account_name, account_number, remark, business_flag, enterprise_id, disabled_flag, deleted_flag, create_user_id, create_user_name, create_time, update_time) VALUES (27, '建设银行', '1024创新实验室', '10241', '其他户', 0, 2, 0, 0, 1, '管理员', '2022-10-22 17:59:19', '2022-10-22 17:59:19');

INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (26, '菜单管理', 2, 50, 1, '/menu/list', '/system/menu/menu-list.vue', null, null, null, 'CopyOutlined', null, 0, null, 1, 1, 0, 0, 2, '2021-08-09 15:04:35', 1, '2023-12-01 19:39:03');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (40, '删除', 3, 26, null, null, null, 1, 'system:menu:batchDelete', 'system:menu:batchDelete', null, 26, 0, null, 0, 1, 0, 0, 1, '2021-08-12 09:45:56', 1, '2023-10-07 18:15:50');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (45, '组织架构', 1, 0, 3, '/organization', null, null, null, null, 'UserSwitchOutlined', null, 0, null, 0, 1, 0, 0, 1, '2021-08-12 16:13:27', 1, '2024-07-02 19:27:44');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (46, '员工管理', 2, 45, 3, '/organization/employee', '/system/employee/index.vue', null, null, null, 'AuditOutlined', null, 0, null, 0, 1, 0, 0, 1, '2021-08-12 16:21:50', 1, '2024-07-02 20:15:23');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (47, '商品管理', 2, 48, 1, '/erp/goods/list', '/business/erp/goods/goods-list.vue', null, null, null, 'AliwangwangOutlined', null, 0, null, 1, 1, 0, 1, 1, '2021-08-12 17:58:39', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (48, '商品管理', 1, 138, 3, '/goods', null, null, null, null, 'BarcodeOutlined', null, 0, null, 0, 1, 0, 1, 1, '2021-08-12 18:02:59', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (50, '系统设置', 1, 0, 6, '/setting', null, null, null, null, 'SettingOutlined', null, 0, null, 0, 1, 0, 0, 1, '2021-08-13 16:41:33', 1, '2023-12-01 19:38:03');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (76, '角色管理', 2, 45, 4, '/organization/role', '/system/role/index.vue', null, null, null, 'SlidersOutlined', null, 0, null, 0, 1, 0, 0, 1, '2021-08-26 10:31:00', 1, '2024-07-02 20:15:28');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (78, '商品分类', 2, 48, 2, '/erp/catalog/goods', '/business/erp/catalog/goods-catalog.vue', null, null, null, 'ApartmentOutlined', null, 0, null, 1, 1, 0, 1, 1, '2022-05-18 23:34:14', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (79, '自定义分组', 2, 48, 3, '/erp/catalog/custom', '/business/erp/catalog/custom-catalog.vue', null, null, null, 'AppstoreAddOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-05-18 23:37:53', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (81, '用户操作记录', 2, 213, 6, '/support/operate-log/operate-log-list', '/support/operate-log/operate-log-list.vue', null, null, null, 'VideoCameraOutlined', null, 0, null, 0, 1, 0, 0, 1, '2022-05-20 12:37:24', 44, '2024-08-13 14:34:10');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (85, '组件演示', 2, 84, null, '/demonstration/index', '/support/demonstration/index.vue', null, null, null, 'ClearOutlined', null, 0, null, 0, 1, 0, 0, 1, '2022-05-20 23:16:46', null, '2022-05-20 23:16:46');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (86, '添加部门', 3, 46, 1, null, null, 1, 'system:department:add', 'system:department:add', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-26 23:33:37', 1, '2023-10-07 18:26:35');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (87, '修改部门', 3, 46, 2, null, null, 1, 'system:department:update', 'system:department:update', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-26 23:34:11', 1, '2023-10-07 18:26:44');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (88, '删除部门', 3, 46, 3, null, null, 1, 'system:department:delete', 'system:department:delete', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-26 23:34:49', 1, '2023-10-07 18:26:49');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (91, '添加员工', 3, 46, null, null, null, 1, 'system:employee:add', 'system:employee:add', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:11:38', 1, '2023-10-07 18:27:46');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (92, '编辑员工', 3, 46, null, null, null, 1, 'system:employee:update', 'system:employee:update', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:12:10', 1, '2023-10-07 18:27:49');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (93, '禁用启用员工', 3, 46, null, null, null, 1, 'system:employee:disabled', 'system:employee:disabled', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:12:37', 1, '2023-10-07 18:27:53');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (94, '调整员工部门', 3, 46, null, null, null, 1, 'system:employee:department:update', 'system:employee:department:update', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:12:59', 1, '2023-10-07 18:27:34');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (95, '重置密码', 3, 46, null, null, null, 1, 'system:employee:password:reset', 'system:employee:password:reset', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:13:30', 1, '2023-10-07 18:27:57');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (96, '删除员工', 3, 46, null, null, null, 1, 'system:employee:delete', 'system:employee:delete', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:14:08', 1, '2023-10-07 18:28:01');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (97, '添加角色', 3, 76, null, null, null, 1, 'system:role:add', 'system:role:add', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:34:00', 1, '2023-10-07 18:42:31');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (98, '删除角色', 3, 76, null, null, null, 1, 'system:role:delete', 'system:role:delete', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:34:19', 1, '2023-10-07 18:42:35');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (99, '编辑角色', 3, 76, null, null, null, 1, 'system:role:update', 'system:role:update', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:34:55', 1, '2023-10-07 18:42:44');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (100, '更新数据范围', 3, 76, null, null, null, 1, 'system:role:dataScope:update', 'system:role:dataScope:update', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:37:03', 1, '2023-10-07 18:41:49');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (101, '批量移除员工', 3, 76, null, null, null, 1, 'system:role:employee:batch:delete', 'system:role:employee:batch:delete', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:39:05', 1, '2023-10-07 18:43:32');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (102, '移除员工', 3, 76, null, null, null, 1, 'system:role:employee:delete', 'system:role:employee:delete', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:39:21', 1, '2023-10-07 18:43:37');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (103, '添加员工', 3, 76, null, null, null, 1, 'system:role:employee:add', 'system:role:employee:add', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:39:38', 1, '2023-10-07 18:44:05');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (104, '修改权限', 3, 76, null, null, null, 1, 'system:role:menu:update', 'system:role:menu:update', null, null, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:41:55', 1, '2023-10-07 18:44:11');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (105, '添加', 3, 26, null, null, null, 1, 'system:menu:add', 'system:menu:add', null, 26, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:44:37', 1, '2023-10-07 17:35:35');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (106, '编辑', 3, 26, null, null, null, 1, 'system:menu:update', 'system:menu:update', null, 26, 0, null, 0, 1, 0, 0, 1, '2022-05-27 00:44:59', 1, '2023-10-07 17:35:48');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (109, '参数配置', 2, 50, 3, '/config/config-list', '/support/config/config-list.vue', null, null, null, 'AntDesignOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-05-27 13:34:41', 1, '2024-12-26 05:21:37');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (110, '数据字典', 2, 50, 4, '/setting/dict', '/support/dict/index.vue', null, null, null, 'BarcodeOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-05-27 17:53:00', 1, '2024-12-26 05:21:39');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (111, '监控服务', 1, 0, 100, '/monitor', null, null, null, null, 'BarChartOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-06-17 11:13:23', 1, '2024-12-26 05:21:33');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (113, '查询', 3, 112, null, null, null, null, null, 'ad', null, null, 0, null, 0, 1, 0, 0, 1, '2022-06-17 11:31:36', null, '2022-06-17 11:31:36');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (114, '运维工具', 1, 0, 200, null, null, null, null, null, 'NodeCollapseOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-06-20 10:09:16', 1, '2023-12-01 19:36:18');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (117, 'Reload', 2, 50, 12, '/hook', '/support/reload/reload-list.vue', null, null, null, 'ReloadOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-06-20 10:16:49', 1, '2024-12-26 05:21:42');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (122, '数据库监控', 2, 111, 4, '/support/druid/index', null, null, null, null, 'ConsoleSqlOutlined', null, 1, 'http://localhost:1024/druid', 1, 1, 0, 1, 1, '2022-06-20 14:49:33', 1, '2024-12-26 05:21:33');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (130, '单号管理', 2, 50, 6, '/support/serial-number/serial-number-list', '/support/serial-number/serial-number-list.vue', null, null, null, 'NumberOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-06-24 14:45:22', 1, '2024-12-26 05:21:40');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (132, '公告管理', 2, 138, 2, '/oa/notice/notice-list', '/business/oa/notice/notice-list.vue', null, null, null, 'SoundOutlined', null, 0, null, 1, 1, 0, 1, 1, '2022-06-24 18:23:09', 1, '2024-12-26 06:26:09');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (133, '缓存管理', 2, 50, 11, '/support/cache/cache-list', '/support/cache/cache-list.vue', null, null, null, 'BorderInnerOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-06-24 18:52:25', 1, '2024-12-26 05:21:41');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (138, '数据管理', 1, 0, 1, null, null, null, null, null, 'BankOutlined', null, 0, null, 0, 1, 0, 0, 1, '2022-06-24 20:09:18', 1, '2024-12-26 05:27:15');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (142, '公告详情', 2, 132, null, '/oa/notice/notice-detail', '/business/oa/notice/notice-detail.vue', null, null, null, null, null, 0, null, 0, 0, 0, 1, 1, '2022-06-25 16:38:47', 1, '2024-12-26 06:26:09');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (143, '登录登出记录', 2, 213, 5, '/support/login-log/login-log-list', '/support/login-log/login-log-list.vue', null, null, null, 'LoginOutlined', null, 0, null, 0, 1, 0, 0, 1, '2022-06-28 15:01:38', 44, '2024-08-13 14:33:49');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (144, '企业管理', 2, 138, 1, '/oa/enterprise/enterprise-list', '/business/oa/enterprise/enterprise-list.vue', null, null, null, 'ShopOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-09-14 17:00:07', 1, '2024-12-26 06:26:06');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (145, '企业详情', 2, 138, null, '/oa/enterprise/enterprise-detail', '/business/oa/enterprise/enterprise-detail.vue', null, null, null, null, null, 0, null, 0, 0, 0, 1, 1, '2022-09-14 18:52:52', 1, '2024-12-26 06:26:03');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (147, '帮助文档', 2, 218, 1, '/help-doc/help-doc-manage-list', '/support/help-doc/management/help-doc-manage-list.vue', null, null, null, 'FolderViewOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-09-14 19:59:01', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (148, '意见反馈', 2, 218, 2, '/feedback/feedback-list', '/support/feedback/feedback-list.vue', null, null, null, 'CoffeeOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-09-14 19:59:52', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (149, '我的通知', 2, 132, null, '/oa/notice/notice-employee-list', '/business/oa/notice/notice-employee-list.vue', null, null, null, null, null, 0, null, 0, 0, 0, 1, 1, '2022-09-14 20:29:41', 1, '2024-12-26 06:26:09');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (150, '我的通知公告详情', 2, 132, null, '/oa/notice/notice-employee-detail', '/business/oa/notice/notice-employee-detail.vue', null, null, null, null, null, 0, null, 0, 0, 0, 1, 1, '2022-09-14 20:30:25', 1, '2024-12-26 06:26:09');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (151, '代码生成', 2, 0, 600, '/support/code-generator', '/support/code-generator/code-generator-list.vue', null, null, null, 'CoffeeOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-09-21 18:25:05', 1, '2024-12-26 05:21:29');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (152, '更新日志', 2, 218, 3, '/support/change-log/change-log-list', '/support/change-log/change-log-list.vue', null, null, null, 'HeartOutlined', null, 0, null, 0, 1, 0, 1, 44, '2022-10-10 10:31:20', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (153, '清除缓存', 3, 133, null, null, null, 1, 'support:cache:delete', 'support:cache:delete', null, 133, 0, null, 0, 1, 1, 1, 1, '2022-10-15 22:45:13', 1, '2024-12-26 05:21:41');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (154, '获取缓存key', 3, 133, null, null, null, 1, 'support:cache:keys', 'support:cache:keys', null, 133, 0, null, 0, 1, 1, 1, 1, '2022-10-15 22:45:48', 1, '2024-12-26 05:21:41');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (156, '查看结果', 3, 117, null, null, null, 1, 'support:reload:result', 'support:reload:result', null, 117, 0, null, 0, 1, 0, 1, 1, '2022-10-15 23:17:23', 1, '2024-12-26 05:21:42');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (157, '单号生成', 3, 130, null, null, null, 1, 'support:serialNumber:generate', 'support:serialNumber:generate', null, 130, 0, null, 0, 1, 0, 1, 1, '2022-10-15 23:21:06', 1, '2024-12-26 05:21:40');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (158, '生成记录', 3, 130, null, null, null, 1, 'support:serialNumber:record', 'support:serialNumber:record', null, 130, 0, null, 0, 1, 0, 1, 1, '2022-10-15 23:21:34', 1, '2024-12-26 05:21:40');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (159, '新建', 3, 110, null, null, null, 1, 'support:dict:add', 'support:dict:add', null, 110, 0, null, 0, 1, 0, 1, 1, '2022-10-15 23:23:51', 1, '2024-12-26 05:21:39');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (160, '编辑', 3, 110, null, null, null, 1, 'support:dict:edit', 'support:dict:edit', null, 110, 0, null, 0, 1, 0, 1, 1, '2022-10-15 23:24:05', 1, '2024-12-26 05:21:39');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (161, '批量删除', 3, 110, null, null, null, 1, 'support:dict:delete', 'support:dict:delete', null, 110, 0, null, 0, 1, 0, 1, 1, '2022-10-15 23:24:34', 1, '2024-12-26 05:21:39');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (162, '刷新缓存', 3, 110, null, null, null, 1, 'support:dict:refresh', 'support:dict:refresh', null, 110, 0, null, 0, 1, 0, 1, 1, '2022-10-15 23:24:55', 1, '2024-12-26 05:21:39');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (163, '新建', 3, 109, null, null, null, 1, 'support:config:add', 'support:config:add', null, 109, 0, null, 0, 1, 0, 1, 1, '2022-10-15 23:26:56', 1, '2024-12-26 05:21:37');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (164, '编辑', 3, 109, null, null, null, 1, 'support:config:update', 'support:config:update', null, 109, 0, null, 0, 1, 0, 1, 1, '2022-10-15 23:27:07', 1, '2024-12-26 05:21:37');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (165, '查询', 3, 47, null, null, null, 1, 'goods:query', 'goods:query', null, 47, 0, null, 0, 1, 0, 1, 1, '2022-10-16 19:55:39', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (166, '新建', 3, 47, null, null, null, 1, 'goods:add', 'goods:add', null, 47, 0, null, 0, 1, 0, 1, 1, '2022-10-16 19:56:00', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (167, '批量删除', 3, 47, null, null, null, 1, 'goods:batchDelete', 'goods:batchDelete', null, 47, 0, null, 0, 1, 0, 1, 1, '2022-10-16 19:56:15', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (168, '查询', 3, 147, 11, null, null, 1, 'support:helpDoc:query', 'support:helpDoc:query', null, 147, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:12:13', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (169, '新建', 3, 147, 12, null, null, 1, 'support:helpDoc:add', 'support:helpDoc:add', null, 147, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:12:37', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (170, '新建目录', 3, 147, 1, null, null, 1, 'support:helpDocCatalog:addCategory', 'support:helpDocCatalog:addCategory', null, 147, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:12:57', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (171, '修改目录', 3, 147, 2, null, null, 1, 'support:helpDocCatalog:update', 'support:helpDocCatalog:update', null, 147, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:13:46', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (173, '新建', 3, 78, null, null, null, 1, 'category:add', 'category:add', null, 78, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:17:02', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (174, '查询', 3, 78, null, null, null, 1, 'category:tree', 'category:tree', null, 78, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:17:22', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (175, '编辑', 3, 78, null, null, null, 1, 'category:update', 'category:update', null, 78, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:17:38', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (176, '删除', 3, 78, null, null, null, 1, 'category:delete', 'category:delete', null, 78, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:17:50', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (177, '新建', 3, 79, null, null, null, 1, 'category:add', 'custom:category:add', null, 78, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:17:02', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (178, '查询', 3, 79, null, null, null, 1, 'category:tree', 'custom:category:tree', null, 78, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:17:22', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (179, '编辑', 3, 79, null, null, null, 1, 'category:update', 'custom:category:update', null, 78, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:17:38', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (180, '删除', 3, 79, null, null, null, 1, 'category:delete', 'custom:category:delete', null, 78, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:17:50', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (181, '查询', 3, 144, null, null, null, 1, 'oa:enterprise:query', 'oa:enterprise:query', null, 144, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:25:14', 1, '2024-12-26 06:26:06');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (182, '新建', 3, 144, null, null, null, 1, 'oa:enterprise:add', 'oa:enterprise:add', null, 144, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:25:25', 1, '2024-12-26 06:26:06');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (183, '编辑', 3, 144, null, null, null, 1, 'oa:enterprise:update', 'oa:enterprise:update', null, 144, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:25:36', 1, '2024-12-26 06:26:06');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (184, '删除', 3, 144, null, null, null, 1, 'oa:enterprise:delete', 'oa:enterprise:delete', null, 144, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:25:53', 1, '2024-12-26 06:26:06');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (185, '查询', 3, 132, null, null, null, 1, 'oa:notice:query', 'oa:notice:query', null, 132, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:26:38', 1, '2024-12-26 06:26:09');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (186, '新建', 3, 132, null, null, null, 1, 'oa:notice:add', 'oa:notice:add', null, 132, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:27:04', 1, '2024-12-26 06:26:09');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (187, '编辑', 3, 132, null, null, null, 1, 'oa:notice:update', 'oa:notice:update', null, 132, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:27:15', 1, '2024-12-26 06:26:09');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (188, '删除', 3, 132, null, null, null, 1, 'oa:notice:delete', 'oa:notice:delete', null, 132, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:27:23', 1, '2024-12-26 06:26:09');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (190, '查询', 3, 152, null, null, null, 1, '', 'support:changeLog:query', null, 152, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:28:33', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (191, '新建', 3, 152, null, null, null, 1, 'support:changeLog:add', 'support:changeLog:add', null, 152, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:28:46', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (192, '批量删除', 3, 152, null, null, null, 1, 'support:changeLog:batchDelete', 'support:changeLog:batchDelete', null, 152, 0, null, 0, 1, 0, 1, 1, '2022-10-16 20:29:10', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (193, '文件管理', 2, 50, 20, '/support/file/file-list', '/support/file/file-list.vue', null, null, null, 'FolderOpenOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-10-21 11:26:11', 1, '2024-12-26 05:21:43');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (194, '删除', 3, 47, null, null, null, 1, 'goods:delete', 'goods:delete', null, 47, 0, null, 0, 1, 0, 1, 1, '2022-10-21 20:00:12', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (195, '修改', 3, 47, null, null, null, 1, 'goods:update', 'goods:update', null, null, 0, null, 0, 1, 0, 1, 1, '2022-10-21 20:05:23', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (196, '查看详情', 3, 145, null, null, null, 1, 'oa:enterprise:detail', 'oa:enterprise:detail', null, null, 0, null, 0, 1, 0, 1, 1, '2022-10-21 20:16:47', 1, '2024-12-26 06:26:03');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (198, '删除', 3, 152, null, null, null, 1, 'support:changeLog:delete', 'support:changeLog:delete', null, null, 0, null, 0, 1, 0, 1, 1, '2022-10-21 20:42:34', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (199, '查询', 3, 109, null, null, null, 1, 'support:config:query', 'support:config:query', null, null, 0, null, 0, 1, 0, 1, 1, '2022-10-21 20:45:14', 1, '2024-12-26 05:21:37');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (200, '查询', 3, 193, null, null, null, 1, 'support:file:query', 'support:file:query', null, null, 0, null, 0, 1, 0, 1, 1, '2022-10-21 20:47:23', 1, '2024-12-26 05:21:43');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (201, '删除', 3, 147, 14, null, null, 1, 'support:helpDoc:delete', 'support:helpDoc:delete', null, null, 0, null, 0, 1, 0, 1, 1, '2022-10-21 21:03:20', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (202, '更新', 3, 147, 13, null, null, 1, 'support:helpDoc:update', 'support:helpDoc:update', null, null, 0, null, 0, 1, 0, 1, 1, '2022-10-21 21:03:32', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (203, '查询', 3, 143, null, null, null, 1, 'support:loginLog:query', 'support:loginLog:query', null, null, 0, null, 0, 1, 0, 0, 1, '2022-10-21 21:05:11', 1, '2023-10-07 14:27:23');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (204, '查询', 3, 81, null, null, null, 1, 'support:operateLog:query', 'support:operateLog:query', null, null, 0, null, 0, 1, 0, 0, 1, '2022-10-22 10:33:31', 1, '2023-10-07 14:27:56');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (205, '详情', 3, 81, null, null, null, 1, 'support:operateLog:detail', 'support:operateLog:detail', null, null, 0, null, 0, 1, 0, 0, 1, '2022-10-22 10:33:49', 1, '2023-10-07 14:28:04');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (206, '心跳监控', 2, 111, 1, '/support/heart-beat/heart-beat-list', '/support/heart-beat/heart-beat-list.vue', 1, null, null, 'FallOutlined', null, 0, null, 0, 1, 0, 1, 1, '2022-10-22 10:47:03', 1, '2024-12-26 05:21:33');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (207, '更新', 3, 152, null, null, null, 1, 'support:changeLog:update', 'support:changeLog:update', null, null, 0, null, 0, 1, 0, 1, 1, '2022-10-22 11:51:32', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (212, '查询', 3, 117, null, null, null, 1, 'support:reload:query', 'support:reload:query', null, null, 0, null, 1, 1, 1, 1, 1, '2023-10-07 14:31:36', 1, '2024-12-26 05:21:42');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (213, '网络安全', 1, 0, 5, null, null, 1, null, null, 'SafetyCertificateOutlined', null, 0, null, 1, 1, 0, 0, 1, '2023-10-17 19:03:08', 1, '2023-12-01 19:38:00');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (214, '登录失败锁定', 2, 213, 4, '/support/login-fail', '/support/login-fail/login-fail-list.vue', 1, null, null, 'LockOutlined', null, 0, null, 1, 1, 0, 0, 1, '2023-10-17 19:04:24', 44, '2024-08-13 14:16:26');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (215, '接口加解密', 2, 213, 2, '/support/api-encrypt', '/support/api-encrypt/api-encrypt-index.vue', 1, null, null, 'CodepenCircleOutlined', null, 0, null, 1, 1, 0, 1, 1, '2023-10-24 11:49:28', 1, '2024-12-26 05:23:10');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (216, '导出', 3, 47, null, null, null, 1, 'goods:exportGoods', 'goods:exportGoods', null, null, 0, null, 1, 1, 0, 1, 1, '2023-12-01 19:34:03', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (217, '导入', 3, 47, 3, null, null, 1, 'goods:importGoods', 'goods:importGoods', null, null, 0, null, 1, 1, 0, 1, 1, '2023-12-01 19:34:22', 1, '2024-12-26 06:26:13');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (218, '文档中心', 1, 0, 4, null, null, 1, null, null, 'FileSearchOutlined', null, 0, null, 1, 1, 0, 1, 1, '2023-12-01 19:37:28', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (219, '部门管理', 2, 45, 1, '/organization/department', '/system/department/department-list.vue', 1, null, null, 'ApartmentOutlined', null, 0, null, 0, 1, 0, 0, 1, '2024-06-22 16:40:21', 1, '2024-07-02 20:15:17');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (221, '定时任务', 2, 50, 25, '/job/list', '/support/job/job-list.vue', 1, null, null, 'AppstoreOutlined', null, 0, null, 1, 1, 0, 1, 2, '2024-06-25 17:57:40', 1, '2024-12-26 05:21:47');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (228, '职务管理', 2, 45, 2, '/organization/position', '/system/position/position-list.vue', 1, null, null, 'ApartmentOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-06-29 11:11:09', 1, '2024-07-02 20:15:11');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (229, '查询任务', 3, 221, null, null, null, 1, 'support:job:query', 'support:job:query', null, 221, 0, null, 1, 1, 0, 1, 2, '2024-06-29 11:14:15', 1, '2024-12-26 05:21:47');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (230, '更新任务', 3, 221, null, null, null, 1, 'support:job:update', 'support:job:update', null, 221, 0, null, 1, 1, 0, 1, 2, '2024-06-29 11:15:40', 1, '2024-12-26 05:21:47');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (231, '执行任务', 3, 221, null, null, null, 1, 'support:job:execute', 'support:job:execute', null, 221, 0, null, 1, 1, 0, 1, 2, '2024-06-29 11:16:03', 1, '2024-12-26 05:21:47');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (232, '查询记录', 3, 221, null, null, null, 1, 'support:job:log:query', 'support:job:log:query', null, 221, 0, null, 1, 1, 0, 1, 2, '2024-06-29 11:16:37', 1, '2024-12-26 05:21:47');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (233, 'knife4j文档', 2, 218, 4, '/knife4j', null, 1, null, null, 'FileWordOutlined', null, 1, 'http://localhost:1024/doc.html', 1, 1, 0, 1, 1, '2024-07-02 20:23:50', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (234, 'swagger文档', 2, 218, 5, '/swagger', 'http://localhost:1024/swagger-ui/index.html', 1, null, null, 'ApiOutlined', null, 1, 'http://localhost:1024/swagger-ui/index.html', 1, 1, 0, 1, 1, '2024-07-02 20:35:43', 1, '2024-12-26 05:23:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (250, '三级等保设置', 2, 213, 1, '/support/level3protect/level3-protect-config-index', '/support/level3protect/level3-protect-config-index.vue', 1, null, null, 'SafetyOutlined', null, 0, null, 1, 1, 0, 0, 44, '2024-08-13 11:41:02', 44, '2024-08-13 11:58:12');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (251, '敏感数据脱敏', 2, 213, 3, '/support/level3protect/data-masking-list', '/support/level3protect/data-masking-list.vue', 1, null, null, 'FileProtectOutlined', null, 0, null, 1, 1, 0, 1, 44, '2024-08-13 11:58:00', 1, '2024-12-26 05:23:15');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (252, '数据查看', 1, 138, 1, null, null, 1, null, null, 'AccountBookOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:30:21', null, '2024-12-26 06:30:21');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (253, '数据配置', 1, 138, 2, null, null, 1, null, null, 'AimOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:30:51', null, '2024-12-26 06:30:51');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (254, '数据审核', 1, 138, 3, null, null, 1, null, null, 'AlertOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:31:10', 1, '2024-12-26 06:31:19');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (255, '用户数据', 2, 252, 1, '/business/show/user', '/business/show/user-index.vue', 1, null, null, 'AlibabaOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:33:32', null, '2024-12-26 06:33:32');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (256, '小盈通持仓', 2, 252, 2, '/business/show/position-xyt', '/business/show/position-xyt-index.vue', 1, null, null, 'AlignCenterOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:35:57', 1, '2024-12-26 06:37:22');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (257, '房产持仓', 2, 252, 3, '/business/show/position-house', '/business/show/position-house-index.vue', 1, null, null, 'AccountBookOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:36:15', 1, '2024-12-26 06:39:48');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (258, 'Banner配置', 2, 253, 2, '/business/config/banner', '/business/config/banner-index.vue', 1, null, null, 'AuditOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:38:26', 1, '2024-12-26 06:39:57');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (259, '文章配置', 2, 253, 1, '/business/config/article', '/business/config/article-index.vue', 1, null, null, 'AccountBookOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:39:03', null, '2024-12-26 06:39:03');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (260, '任务配置', 2, 253, 3, '/business/config/task', '/business/config/task-index.vue', 1, null, null, 'AliwangwangOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:40:37', null, '2024-12-26 06:40:37');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (261, '小盈通配置', 2, 253, 4, '/business/config/xyt', '/business/config/xyt-index.vue', 1, null, null, 'AccountBookOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:41:14', null, '2024-12-26 06:41:14');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (262, '房产配置', 2, 253, 5, '/business/config/house', '/business/config/house-index.vue', 1, null, null, 'AccountBookOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:41:37', 1, '2024-12-26 06:42:44');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (263, '实名认证', 2, 254, 1, '/business/review/real-name-verification', '/business/review/real-name-verification-index.vue', 1, null, null, 'AccountBookOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:44:35', 1, '2024-12-26 06:46:59');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (264, '充值审核', 2, 254, 2, '/business/review/recharge', '/business/review/recharge-index.vue', 1, null, null, 'ApiOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:45:19', 1, '2024-12-26 06:45:57');
INSERT INTO hooke.zdl_admin_menu (menu_id, menu_name, menu_type, parent_id, sort, path, component, perms_type, api_perms, web_perms, icon, context_menu_id, frame_flag, frame_url, cache_flag, visible_flag, disabled_flag, deleted_flag, create_user_id, create_time, update_user_id, update_time) VALUES (265, '提现审核', 2, 254, 3, '/business/review/withdrawal', '/business/review/withdrawal-index.vue', 1, null, null, 'BarChartOutlined', null, 0, null, 1, 1, 0, 0, 1, '2024-12-26 06:45:49', null, '2024-12-26 06:45:49');

INSERT INTO hooke.zdl_admin_login_log (login_log_id, user_id, user_type, user_name, login_ip, login_ip_region, user_agent, login_result, remark, update_time, create_time) VALUES (1767, 1, 1, '管理员', '127.0.0.1', '', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', 2, null, '2024-12-26 06:49:46', '2024-12-26 14:49:47');
INSERT INTO hooke.zdl_admin_login_log (login_log_id, user_id, user_type, user_name, login_ip, login_ip_region, user_agent, login_result, remark, update_time, create_time) VALUES (1768, 1, 1, '管理员', '127.0.0.1', '', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', 0, '电脑端', '2024-12-26 06:49:55', '2024-12-26 14:49:55');
INSERT INTO hooke.zdl_admin_login_log (login_log_id, user_id, user_type, user_name, login_ip, login_ip_region, user_agent, login_result, remark, update_time, create_time) VALUES (1769, 1, 1, '管理员', '127.0.0.1', '', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', 0, '电脑端', '2024-12-26 09:45:27', '2024-12-26 17:45:27');

INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (1, 'admin', '40cc20b8891cd3fd1f008ea7f4ac17c3', '管理员', 'public/common/1eea469452484ffea4a42570c4072466_20240702220447.jpg', 0, '13500000000', 1, 3, null, 0, 0, 1, null, '2024-09-03 21:39:17', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (2, 'huke', '9628c5df5132cfe1e1baa6125a3ef8b1', '胡克', null, 0, '13123123121', 1, 4, null, 0, 0, 0, null, '2024-12-26 04:02:52', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (44, 'zhuoda', 'bf63cb6431d613acdee104f692845b22', '卓大', null, 1, '18637925892', 1, 6, null, 0, 0, 0, null, '2024-09-03 21:36:10', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (47, 'shanyi', 'ca405fddcb90ac2a71b33fe7126ed2a8', '善逸', 'public/common/f823b00873684f0a9d31f0d62316cc8e_20240630015141.jpg', 1, '17630506613', 2, 5, null, 0, 0, 0, '这个是备注', '2024-09-03 21:36:11', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (48, 'qinjiu', 'b1cfb0ed0080306199fa76c872d6a32e', '琴酒', null, 2, '14112343212', 2, 6, null, 0, 1, 0, null, '2024-12-26 04:01:48', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (63, 'kaiyun', '0e5ec5746bf955f253fa747ab76cfa67', '开云', null, 0, '13112312346', 2, 5, null, 0, 1, 0, null, '2024-12-26 04:01:48', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (64, 'qingye', '40cc20b8891cd3fd1f008ea7f4ac17c3', '清野', null, 1, '13123123111', 2, 4, null, 0, 1, 0, null, '2024-12-26 04:01:48', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (65, 'feiye', '40cc20b8891cd3fd1f008ea7f4ac17c3', '飞叶', null, 1, '13123123112', 4, 3, null, 0, 1, 0, null, '2024-12-26 04:01:40', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (66, 'luoyi', '40cc20b8891cd3fd1f008ea7f4ac17c3', '罗伊', null, 1, '13123123142', 4, 2, null, 1, 1, 0, null, '2024-12-26 04:01:40', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (67, 'chuxiao', '7287168489ed5598741362cbec2b0741', '初晓', null, 1, '13123123123', 1, 2, null, 1, 0, 0, null, '2024-09-03 21:36:18', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (68, 'xuanpeng', '40cc20b8891cd3fd1f008ea7f4ac17c3', '玄朋', null, 1, '13123123124', 1, 3, null, 0, 0, 0, null, '2024-09-03 21:36:18', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (69, 'peixian', '40cc20b8891cd3fd1f008ea7f4ac17c3', '玄朋', null, 1, '18377482773', 1, 4, null, 0, 0, 0, null, '2024-09-03 21:36:19', '2022-10-04 21:33:50');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (73, 'limbo', '50ea4174e4ad0970bcf6423f99c0cbcd', '陈琳博', null, 1, '18906662339', 2, 4, null, 0, 1, 0, null, '2024-12-26 04:01:48', '2024-07-17 10:36:16');
INSERT INTO hooke.zdl_admin_employee (employee_id, login_name, login_pwd, actual_name, avatar, gender, phone, department_id, position_id, email, disabled_flag, deleted_flag, administrator_flag, remark, update_time, create_time) VALUES (74, 'xzh', 'f5ca8e50d26e6070ed2198e136ee967d', 'admin1', null, 1, '13654567897', 5, 6, null, 0, 1, 0, null, '2024-12-26 04:01:34', '2024-08-09 09:49:56');

INSERT INTO hooke.zdl_admin_department (department_id, name, manager_id, parent_id, sort, update_time, create_time) VALUES (1, '1024创新实验室', 1, 0, 1, '2022-10-19 20:17:09', '2022-10-19 20:17:09');
INSERT INTO hooke.zdl_admin_department (department_id, name, manager_id, parent_id, sort, update_time, create_time) VALUES (2, '开发部', 44, 1, 1000, '2022-10-19 20:22:23', '2022-10-19 20:22:23');

INSERT INTO hooke.zdl_admin_data_tracer (data_tracer_id, data_id, type, content, diff_old, diff_new, extra_data, user_id, user_type, user_name, ip, ip_region, user_agent, update_time, create_time) VALUES (35, 10, 1, '新增', null, null, null, 47, 1, '善逸', '127.0.0.1', '0|0|0|内网IP|内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36 Edg/109.0.1518.61', '2023-10-07 19:02:24', '2023-10-07 19:02:24');
INSERT INTO hooke.zdl_admin_data_tracer (data_tracer_id, data_id, type, content, diff_old, diff_new, extra_data, user_id, user_type, user_name, ip, ip_region, user_agent, update_time, create_time) VALUES (36, 11, 1, '新增', null, null, null, 1, 1, '管理员', '127.0.0.1', '0|0|0|内网IP|内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', '2023-12-01 19:55:53', '2023-12-01 19:55:53');
INSERT INTO hooke.zdl_admin_data_tracer (data_tracer_id, data_id, type, content, diff_old, diff_new, extra_data, user_id, user_type, user_name, ip, ip_region, user_agent, update_time, create_time) VALUES (37, 12, 1, '新增', null, null, null, 1, 1, '管理员', '127.0.0.1', '0|0|0|内网IP|内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', '2023-12-01 19:57:26', '2023-12-01 19:57:26');
INSERT INTO hooke.zdl_admin_data_tracer (data_tracer_id, data_id, type, content, diff_old, diff_new, extra_data, user_id, user_type, user_name, ip, ip_region, user_agent, update_time, create_time) VALUES (38, 11, 1, '', null, null, null, 1, 1, '管理员', '127.0.0.1', '0|0|0|内网IP|内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', '2023-12-01 19:58:09', '2023-12-01 19:58:09');
INSERT INTO hooke.zdl_admin_data_tracer (data_tracer_id, data_id, type, content, diff_old, diff_new, extra_data, user_id, user_type, user_name, ip, ip_region, user_agent, update_time, create_time) VALUES (39, 2, 3, '修改企业信息', '统一社会信用代码:"1024lab"<br/>详细地址:"1024大楼"<br/>区县名称:"洛龙区"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:"洛阳市"<br/>删除状态:false<br/>联系人:"卓大"<br/>省份名称:"河南省"<br/>企业logo:"public/common/fb827d63dda74a60ab8b4f70cc7c7d0a_20221022145641_jpg"<br/>联系人电话:"18637925892"<br/>企业名称:"1024创新实验室"<br/>邮箱:"lab1024@163.com"', '营业执照:"public/common/59b1ca99b7fe45d78678e6295798a699_20231201200459.jpg"<br/>统一社会信用代码:"1024lab1"<br/>详细地址:"1024大楼"<br/>区县名称:"洛龙区"<br/>禁用状态:false<br/>类型:外资企业<br/>城市名称:"洛阳市"<br/>删除状态:false<br/>联系人:"卓大1"<br/>省份名称:"河南省"<br/>企业logo:""<br/>联系人电话:"18637925892"<br/>企业名称:"1024创新实验室1"<br/>邮箱:"lab1024@163.com"', null, 1, 1, '管理员', '127.0.0.1', '0|0|0|内网IP|内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', '2023-12-01 20:05:05', '2023-12-01 20:05:05');
INSERT INTO hooke.zdl_admin_data_tracer (data_tracer_id, data_id, type, content, diff_old, diff_new, extra_data, user_id, user_type, user_name, ip, ip_region, user_agent, update_time, create_time) VALUES (40, 2, 3, '修改企业信息', '营业执照:"public/common/59b1ca99b7fe45d78678e6295798a699_20231201200459.jpg"<br/>统一社会信用代码:"1024lab1"<br/>详细地址:"1024大楼"<br/>区县名称:"洛龙区"<br/>禁用状态:false<br/>类型:外资企业<br/>城市名称:"洛阳市"<br/>删除状态:false<br/>联系人:"卓大1"<br/>省份名称:"河南省"<br/>企业logo:""<br/>联系人电话:"18637925892"<br/>企业名称:"1024创新实验室1"<br/>邮箱:"lab1024@163.com"', '营业执照:"public/common/59b1ca99b7fe45d78678e6295798a699_20231201200459.jpg"<br/>统一社会信用代码:"1024lab"<br/>详细地址:"1024大楼"<br/>区县名称:"洛龙区"<br/>禁用状态:false<br/>类型:外资企业<br/>城市名称:"洛阳市"<br/>删除状态:false<br/>联系人:"卓大"<br/>省份名称:"河南省"<br/>企业logo:""<br/>联系人电话:"18637925892"<br/>企业名称:"1024创新实验室"<br/>邮箱:"lab1024@163.com"', null, 1, 1, '管理员', '127.0.0.1', '0|0|0|内网IP|内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', '2023-12-01 20:05:54', '2023-12-01 20:05:54');
INSERT INTO hooke.zdl_admin_data_tracer (data_tracer_id, data_id, type, content, diff_old, diff_new, extra_data, user_id, user_type, user_name, ip, ip_region, user_agent, update_time, create_time) VALUES (41, 2, 3, '更新银行:<br/>', null, null, null, 1, 1, '管理员', '127.0.0.1', '0|0|0|内网IP|内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', '2023-12-01 20:09:17', '2023-12-01 20:09:17');
INSERT INTO hooke.zdl_admin_data_tracer (data_tracer_id, data_id, type, content, diff_old, diff_new, extra_data, user_id, user_type, user_name, ip, ip_region, user_agent, update_time, create_time) VALUES (42, 2, 3, '更新发票：<br/>删除状态:由【false】变更为【】', null, null, null, 1, 1, '管理员', '127.0.0.1', '0|0|0|内网IP|内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', '2023-12-01 20:09:20', '2023-12-01 20:09:20');
INSERT INTO hooke.zdl_admin_data_tracer (data_tracer_id, data_id, type, content, diff_old, diff_new, extra_data, user_id, user_type, user_name, ip, ip_region, user_agent, update_time, create_time) VALUES (49, 1, 3, '修改企业信息', '营业执照:"public/common/852b7e19bef94af39c1a6156edf47cfb_20221022170332_jpg"<br/>统一社会信用代码:"1024lab_block"<br/>详细地址:"区块链大楼"<br/>区县名称:"洛龙区"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:"洛阳市"<br/>删除状态:false<br/>联系人:"开云"<br/>省份名称:"河南省"<br/>企业logo:"public/common/f4a76fa720814949a610f05f6f9545bf_20221022170256_jpg"<br/>联系人电话:"18637925892"<br/>企业名称:"1024创新区块链实验室"', '营业执照:"public/common/1d89055e5680426280446aff1e7e627c_20240306112451.jpeg"<br/>统一社会信用代码:"1024lab_block"<br/>详细地址:"区块链大楼"<br/>区县名称:"洛龙区"<br/>禁用状态:false<br/>类型:有限企业<br/>城市名称:"洛阳市"<br/>删除状态:false<br/>联系人:"开云"<br/>省份名称:"河南省"<br/>企业logo:"public/common/34f5ac0fc097402294aea75352c128f0_20240306112435.png"<br/>联系人电话:"18637925892"<br/>企业名称:"1024创新区块链实验室"', null, 1, 1, '管理员', '127.0.0.1', '0|0|0|内网IP|内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36', '2024-03-06 11:24:55', '2024-03-06 11:24:55');
INSERT INTO hooke.zdl_admin_data_tracer (data_tracer_id, data_id, type, content, diff_old, diff_new, extra_data, user_id, user_type, user_name, ip, ip_region, user_agent, update_time, create_time) VALUES (99, 12, 1, '', null, null, null, 1, 1, '管理员', '127.0.0.1', '0|0|0|内网IP|内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36', '2024-09-03 21:06:32', '2024-09-03 21:06:32');
INSERT INTO hooke.zdl_admin_data_tracer (data_tracer_id, data_id, type, content, diff_old, diff_new, extra_data, user_id, user_type, user_name, ip, ip_region, user_agent, update_time, create_time) VALUES (100, 12, 1, '', null, null, null, 1, 1, '管理员', '127.0.0.1', '', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', '2024-12-26 04:36:14', '2024-12-26 04:36:14');

INSERT INTO hooke.zdl_admin_config (config_id, config_name, config_key, config_value, remark, update_time, create_time) VALUES (1, '万能密码', 'super_password', '1024ok', '一路春光啊一路荆棘呀惊鸿一般短暂如夏花一样绚烂这是一个不能停留太久的世界，一路春光啊一路荆棘呀惊鸿一般短暂如夏花一样绚烂这是一个不能停留太久的世界啊', '2024-09-03 21:27:03', '2021-12-16 23:32:46');
INSERT INTO hooke.zdl_admin_config (config_id, config_name, config_key, config_value, remark, update_time, create_time) VALUES (2, '三级等保', 'level3_protect_config', '{
	"fileDetectFlag":true,
	"loginActiveTimeoutMinutes":30,
	"loginFailLockMinutes":30,
	"loginFailMaxTimes":3,
	"maxUploadFileSizeMb":30,
	"passwordComplexityEnabled":true,
	"regularChangePasswordMonths":3,
	"regularChangePasswordNotAllowRepeatTimes":3,
	"twoFactorLoginEnabled":false
}', 'SmartJob Sample2 update', '2024-09-03 21:49:23', '2024-08-13 11:44:49');

INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (1, '手机', 1, 0, 0, 0, 0, null, '2022-10-10 22:27:24', '2022-07-14 20:55:15');
INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (2, '键盘', 1, 0, 0, 0, 0, null, '2022-09-14 21:39:00', '2022-07-14 20:55:48');
INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (3, '自定义1', 2, 0, 0, 0, 0, null, '2022-09-14 22:01:06', '2022-07-14 20:56:03');
INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (4, '自定义2', 2, 0, 0, 0, 0, null, '2022-09-14 22:01:10', '2022-07-14 20:56:09');
INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (351, '鼠标', 1, 0, 0, 0, 0, null, '2022-09-14 21:39:06', '2022-09-14 21:39:06');
INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (352, '苹果', 1, 1, 0, 0, 0, null, '2022-09-14 21:39:25', '2022-09-14 21:39:25');
INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (353, '华为', 1, 1, 0, 0, 0, null, '2022-09-14 21:39:32', '2022-09-14 21:39:32');
INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (354, 'IKBC', 1, 2, 0, 0, 0, null, '2022-09-14 21:39:38', '2022-09-14 21:39:38');
INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (355, '双飞燕', 1, 2, 0, 0, 0, null, '2022-09-14 21:39:47', '2022-09-14 21:39:47');
INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (356, '罗技', 1, 351, 0, 0, 0, null, '2022-09-14 21:39:57', '2022-09-14 21:39:57');
INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (357, '小米', 1, 1, 0, 0, 0, null, '2022-10-10 22:27:39', '2022-10-10 22:27:39');
INSERT INTO hooke.zdl_admin_category (category_id, category_name, category_type, parent_id, sort, disabled_flag, deleted_flag, remark, update_time, create_time) VALUES (360, 'iphone', 1, 352, 0, 0, 0, null, '2023-12-04 21:26:55', '2023-12-01 19:54:22');
