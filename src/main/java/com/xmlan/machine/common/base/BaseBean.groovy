package com.xmlan.machine.common.base

import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2018/1/3-09:33. <br/>
 * Package: com.xmlan.machine.common.base <br/>
 * 基本Bean <br/>
 * 包括通用配置参数 <br/>
 */
@Transactional(readOnly = true)
class BaseBean {

    // ========================= 引导类 ========================= //
    /**
     * 操作完成
     */
    public static final int DONE = 0
    /**
     * 通过
     */
    public static final int PASS = 1
    /**
     * 数据库没有做任何操作
     */
    public static final int DATABASE_DO_NOTHING = -1
    /**
     * 新插入的ID
     */
    public static final int NEW_INSERT_ID = -2
    /**
     * 失败
     */
    public static final int FAILURE = -3
    /**
     * 数据库没有条目
     */
    public static final int NO_SUCH_ROW = -4
    /**
     * 错误请求
     */
    public static final int ERROR_REQUEST = -5
    /**
     * 默认
     */
    public static final int DEFAULT = -6

    // ========================= 标记类 ========================= //
    /**
     * 管理员角色ID
     */
    public static final int ADMIN_ROLE_ID = 1
    /**
     * ROOT管理员ID
     */
    public static final int ROOT_ADMIN_ID = 1

    // ========================= 模块类 ========================= //
    /**
     * 管理员相关操作成功
     */
    public static final int ADMIN_DONE = 100
    /**
     * 错误的确认密码
     */
    public static final int INCORRECT_REPEAT_PASSWD = 101
    /**
     * 错误的原密码
     */
    public static final int INCORRECT_OLD_PASSWD = 102

    /**
     * 角色下还有所属用户
     */
    public static final int ROLE_HAVE_SOME_USERS = -200
    /**
     * 角色不存在
     */
    public static final int ROLE_IS_NOT_EXISTS = -201
    /**
     * ROOT管理员不能被修改到其他角色
     */
    public static final int ROOT_ADMIN_CAN_NOT_CHANGE_ROLE = -202
    /**
     * 极光APIConnectionException
     */
    public static final int ERROR_API_CONNECTION_EXCEPTION = -301
    /**
     * 极光APIRequestException
     */
    public static final int ERROR_API_REQUEST_EXCEPTION = -302

    // ========================= 其他类 ========================= //
    /**
     * 显示
     */
    public static final String SHOW = "1"
    /**
     * 隐藏
     */
    public static final String HIDE = "0"

    /**
     * 是
     */
    public static final int YES = 1
    /**
     * 否
     */
    public static final int NO = 0

    /**
     * 真
     */
    public static final boolean TRUE = true
    /**
     * 假
     */
    public static final boolean FALSE = false

    /**
     * 类型：灯
     */
    public static final int TYPE_LIGHT = 54448
    /**
     * 类型：充电
     */
    public static final int TYPE_CHARGE = 242743
    /**
     * 类型：广告媒体更新
     */
    public static final int TYPE_MEDIA_UPDATE = 63342

}
