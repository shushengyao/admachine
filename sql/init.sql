create schema if not exists `admachine`
  char set utf8;

use `admachine`;

drop table if exists `role`;
create table `role` (
  id     int auto_increment not null
  comment '角色ID',
  name   varchar(64)        not null
  comment '角色名称',
  remark text comment '备注',
  primary key (id)
)
  char set utf8
  comment '角色表';

drop table if exists `user`;
create table `user` (
  id       int auto_increment not null
  comment '用户ID',
  username varchar(64)        not null
  comment '用户名',
  authname varchar(64)        not null
  comment '登录名',
  password varchar(512)       not null
  comment '密码',
  roleID   int                not null
  comment '所属角色ID',
  address  varchar(8192) comment '地址',
  addTime  datetime comment '加入时间',
  phone    varchar(16) comment '联系电话',
  remark   text comment '备注',
  primary key (id)
)
  char set utf8
  comment '用户表';

drop table if exists `advertisement_machine`;
create table `advertisement_machine` (
  id         int auto_increment not null
  comment '广告机ID',
  userID     int                not null
  comment '用户ID',
  name       varchar(128)       not null
  comment '广告机名称',
  address    varchar(8192) comment '广告机地址',
  longitude  double comment '经度',
  latitude   double comment '纬度',
  addTime    datetime comment '添加时间',
  codeNumber varchar(512)       not null
  comment '机器标识码(注册码)',
  remark     text comment '备注',
  primary key (id)
)
  char set utf8
  comment '广告机表';

drop table if exists `advertisement`;
create table `advertisement` (
  id        int auto_increment not null
  comment '广告ID',
  name      varchar(128)       not null
  comment '广告名称',
  url       text comment '图片、视频资源路径',
  time      int comment '播放时长',
  addTime   datetime comment '添加时间',
  machineID int                not null
  comment '机器标识码',
  userID    int                not null
  comment '用户ID',
  remark    text comment '备注',
  primary key (id)
)
  char set utf8
  comment '广告表';

drop table if exists `machine_sensor`;
create table `machine_sensor` (
  id          int auto_increment not null
  comment '传感记录ID',
  temperature varchar(64) comment '温度',
  humidity    varchar(64) comment '湿度',
  pm25        varchar(64) comment 'PM2.5',
  pm10        varchar(64) comment 'PM10',
  codeNumber  varchar(512)       not null
  comment '机器标识码(注册码)',
  primary key (id)
)
  char set utf8
  comment '传感器参数';

insert into role values (id, '管理员', '管理员'), (id, '用户', '用户');
insert into user values (
  id,
  'admin',
  'admin',
  'd6afefbaa8389a98c03e37035bc4cd264776bb784993b877afda72e20d8d5865',
  1,
  '',
  now(),
  '',
  ''
), (
  id,
  '希梦3117',
  'ximon3117',
  '4dae3c427732469187d806fa83470313a21e6a827b35d831a6b70f420cff97fd',
  1,
  '',
  now(),
  '',
  ''
);
-- d6afefbaa8389a98c03e37035bc4cd264776bb784993b877afda72e20d8d5865 -> yuki6261
-- 4dae3c427732469187d806fa83470313a21e6a827b35d831a6b70f420cff97fd -> zhxm2512209

-- 防止删除管理员角色
create trigger `ROLE_DELETE_CHECK_TRIGGER`
  after delete
  on role
  for each row
  begin
    declare count int default 0;
    select count(*)
    into count
    from role
    where id = 1;
    if count != 1
    then
      insert into role (id, name, remark) values (OLD.id, OLD.name, OLD.remark);
    end if;
  end;

-- 防止删除非空角色（该角色有所属用户时不删除）
create trigger `NULL_ROLE_CHECK_TRIGGER`
  after delete
  on role
  for each row
  begin
    declare count int;
    select count(*)
    into count
    from user
    where roleID = OLD.id;
    if count != 0
    then
      insert into role (id, name, remark) values (OLD.id, OLD.name, OLD.remark);
    end if;
  end;
