package com.xmlan.machine.common.base

import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2018/1/3-09:33.
 * Package: com.xmlan.machine.common.base
 */
@Transactional(readOnly = true)
class BaseBean {

    public static final int DONE = 0
    public static final int PASS = 1
    public static final int DATABASE_DO_NOTHING = -1
    public static final int NEW_INSERT_ID = -2
    public static final int FAILURE = -3
    public static final int NO_SUCH_ROW = -4
    public static final int ERROR_REQUEST = -5

    public static final int ADMIN_ROLE_ID = 1
    public static final int ROOT_ADMIN_ID = 1

    public static final int ADMIN_DONE = 100
    public static final int INCORRECT_REPEAT_PASSWD = 101
    public static final int INCORRECT_OLD_PASSWD = 102

    public static final int ROLE_HAVE_SOME_USERS = -200
    public static final int ROLE_IS_NOT_EXISTS = -201
    public static final int ROOT_ADMIN_CAN_NOT_CHANGE_ROLE = -202

    public static final String SHOW = "1"
    public static final String HIDE = "0"

    public static final String YES = "1"
    public static final String NO = "0"

    public static final String TRUE = "true"
    public static final String FALSE = "false"

}
