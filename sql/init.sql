-- 新建数据库admachine, 如果存在同样的库则不会创建, 使用utf8字符集
create schema if not exists `admachine`
  char set utf8;

-- 选中数据库
use `admachine`;

-- 以下所有创建需要严格遵守本文件中的表创建顺序, 不能打乱
-- 创建角色表, 如果存在则抛弃旧表, 重新创建
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

-- 创建用户表, 如果存在则抛弃旧表, 重新创建. 本表依赖于角色表
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
  founder varchar(20)   not null
  comment '创建人',
  LED     varchar(30)    comment'led屏幕',
  primary key (id)
)
  char set utf8
  comment '用户表';

-- 创建用户和设备关联表,实现多对多查询等操作
drop table if exists `machine_user`;
create table `machine_user`(
machine_id         int auto_increment not null
comment '广告机id',
user_id         int auto_increment not null
comment '用户ID'
)
  char set utf8
  comment '设备用户关联表';

  -- 创建设备分组表 machine_group
drop table if exists `machine_group`;
create table `machine_group`(
id int auto_increment not null,
machineID varchar(255)
comment '广告机id',
userID
comment '用户ID',
light int comment '路灯亮度',
groupName varchar(255) comment '组名'
)
  char set utf8
  comment '设备分组表';


-- 创建广告机表, 如果存在则抛弃旧表重新创建
drop table if exists `advertisement_machine`;
create table `advertisement_machine` (
  id         int auto_increment not null
  comment '广告机ID',
  userID     int comment '用户ID',
  name       varchar(128) comment '广告机名称',
  address    varchar(8192) comment '广告机地址',
  longitude  double comment '经度',
  cameraIP    varchar(100) comment '摄像头IP',
  cameraSequence varchar(33) comment '摄像头序列号',
  cameraVerificationCode varchar(33) comment '摄像头设备验证码',
  accessToken varchar (100) comment 'accessToken一周一更新',
  latitude   double comment '纬度',
  addTime    datetime comment '添加时间',
  codeNumber varchar(512)       not null unique
  comment '机器标识码(注册码)',
  control int comment '新的灯开关',
  light      int comment '灯开关, 1 开灯, 0 关灯',
  charge     int comment '充电状态, 1 充电, 0 闲置',
  checked    int comment '选中',
  remark     text comment '备注',
  check (light in (0, 1)),
  check (charge in (0, 1)),
  check (checked in (0, 1)),
  LED  varchar comment 'led屏幕',
  city  varchar comment '城市',
  updateTime  varchar comment '定位更新时间',
  cityCode  varchar comment '城市编码',
  addrStr  varchar comment '定位地址',
  singLampID     int comment '单灯控制器ID',
  primary key (id)
)
  char set utf8
  comment '广告机表';

-- 创建广告表, 如果存在则抛弃旧表重新创建
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

  -- 创建led屏幕表, 如果存在则抛弃旧表重新创建
drop table if exists `led_machine`;
create table `led_machine` (
  id        int auto_increment not null
  comment '广告ID',
  name      varchar(255)
  comment '广告名称',
  led        varchar(255) comment 'led屏幕编号',
  machine_id    int comment '所属广告机',
  user_id int    comment '用户ID',
  play_list varchar(255) comment '播放列表',
  primary key (id)
)
  char set utf8
  comment 'led广告表';

-- 创建温湿度传感器数据表, 如果存在则抛弃旧表重新创建
drop table if exists `machine_sensor`;
create table `machine_sensor` (
  id          int auto_increment not null
  comment '传感记录ID',
  eCH2O varchar(64) comment '甲醛',
  temperature varchar(64) comment '温度',
  humidity    varchar(64) comment '湿度',
  pm25        varchar(64) comment 'PM2.5',
  brightness  varchar(64) comment '亮度',
  pm10 varchar(64) comment 'pm10',
  eCo2    varchar(64) comment 'eCo2',
  tVoc varchar(64) comment 'tVoc',
  machineID   int                not null unique
  comment '机器标识码(注册码)',
  primary key (id)
)
  char set utf8
  comment '传感器参数';


  SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for singlelampdata
-- ----------------------------
DROP TABLE IF EXISTS `singlelampdata`;
CREATE TABLE `singlelampdata` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `machineID` int(13) DEFAULT NULL,
  `userID` int(13) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `currError` double(64,0) DEFAULT NULL COMMENT '故障位',
  `gridVolt` double(64,0) DEFAULT NULL COMMENT '电压',
  `gridCurr` double(64,0) DEFAULT NULL COMMENT '电流',
  `gridFreq` double(64,0) DEFAULT NULL COMMENT '频率',
  `gridPF` double(64,0) DEFAULT NULL COMMENT '电网PF',
  `gridAP` double(64,0) DEFAULT NULL COMMENT '电网有功',
  `gridRP` double(64,0) DEFAULT NULL COMMENT '电网无功',
  `temperature` double(64,0) DEFAULT NULL COMMENT '温度',
  `ledDim` double(64,0) DEFAULT NULL COMMENT '亮灯',
  `workTime` double(64,0) DEFAULT NULL COMMENT '亮灯时间',
  `workTimeT` double(64,0) DEFAULT NULL COMMENT '累计时间',
  `energyTonight` double(64,0) DEFAULT NULL COMMENT '本次用电量',
  `energyTotal` double(64,0) DEFAULT NULL COMMENT '累计用电量',
  `ledLux` double(64,0) DEFAULT NULL COMMENT '灯头照度值',
  `ppkToday` double(64,0) DEFAULT NULL COMMENT '当天负载峰值功率',
  `ppkHistory` double(64,0) DEFAULT NULL COMMENT '历史负载峰值功率',
  `energyToday` double(64,0) DEFAULT NULL COMMENT '当日用电',
  `saveMoneyT` double(64,0) DEFAULT NULL COMMENT '经济收益',
  `cO2T` double(64,0) DEFAULT NULL COMMENT 'CO2减排量',
  `energyMonth` double(64,0) DEFAULT NULL COMMENT '月用电',
  `saveMoneyMonth` double(64,0) DEFAULT NULL COMMENT '当月经济收益',
  `cO2Month` double(64,0) DEFAULT NULL COMMENT '当月CO2排放量',
  `gridEnergyYear` double(64,0) DEFAULT NULL COMMENT '年电量',
  `saveMoneyYear` double(64,0) DEFAULT NULL COMMENT '当年经济收益',
  `cO2Year` double(64,0) DEFAULT NULL COMMENT '当年CO2减排量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `singlelampdata`;
CREATE TABLE `singlelampdata_new` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `machineID` int(13) DEFAULT NULL,
   `userID` int(13) DEFAULT NULL,
   `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
   `deviceCode` double(64,0) DEFAULT NULL COMMENT '设备码',
   `temperature` double(64,0) DEFAULT NULL COMMENT '电源温度',
    `inputVoltage` double(64,0) DEFAULT NULL COMMENT '输入电压',
   `inputCurrent` double(64,0) DEFAULT NULL COMMENT '输入电流',
   `outputVol` double(64,0) DEFAULT NULL COMMENT '输出电压',
   `outputCurr` double(64,0) DEFAULT NULL COMMENT '输出电流',
   `gridAP` double(64,0) DEFAULT NULL COMMENT '电源有功功率',
   `gridAPD` double(64,0) DEFAULT NULL COMMENT '电源有功功率因素',
   `ledBright` double(64,0) DEFAULT NULL COMMENT 'led亮度',
   powerStatus     varchar(64) comment '电源状态',
   PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建操作记录表, 本表用于持久化所有用户以及广告机发出的操作记录, 同理如果存在则抛弃旧表重新创建
drop table if exists `sys_log`;
create table `sys_log` (
  id             int auto_increment not null
  comment 'ID',
  type           varchar(64) comment '类别/模块',
  operate        varchar(64) comment '操作类型',
  operator       int comment '操作者ID',
  operatorObject varchar(64) comment '操作者对象类别',
  description    text comment '描述',
  logDate        datetime comment '记录时间',
  primary key (id)
)
  char set utf8
  comment '系统日志';

-- 初始化角色数据
insert into role values (id, '管理员', '管理员'), (id, '用户', '用户');

-- 初始化用户数据
insert into user values (
  id,
  'admin',
  'admin',
  '4dae3c427732469187d806fa83470313a21e6a827b35d831a6b70f420cff97fd',
  1,
  '',
  now(),
  '',
  ''
);
-- 4dae3c427732469187d806fa83470313a21e6a827b35d831a6b70f420cff97fd -> zhxm2512209

-- 防止删除管理员角色的触发器
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
      insert into role (id, name, remark)
      values (OLD.id, OLD.name, OLD.remark);
    end if;
  end;

-- 防止删除非空角色的触发器（该角色有所属用户时不删除）
create trigger `NULL_ROLE_CHECK_TRIGGER`
  after delete
  on role
  for each row
  begin
    declare count int default 0;
    select count(*)
    into count
    from user
    where roleID = OLD.id;
    if count != 0
    then
      insert into role (id, name, remark)
      values (OLD.id, OLD.name, OLD.remark);
    end if;
  end;

-- 防止删除ROOT管理员的触发器
create trigger `ROOT_ADMIN_PROTECTION`
  after delete
  on user
  for each row
  begin
    declare count int default 0;
    select count(*)
    into count
    from user
    where id = 1;
    if count != 1
    then
      insert into user (id, username, authname, password, roleID, address, addTime, phone, remark)
      values (
        OLD.id,
        OLD.username,
        OLD.authname,
        OLD.password,
        OLD.roleID,
        OLD.address,
        OLD.addTime,
        OLD.phone,
        OLD.remark
      );
    end if;
  end;