package com.xmlan.machine.common.base

import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2018/1/3-09:33.
 * Package: com.xmlan.machine.common.base
 */
@Transactional(readOnly = true)
class BaseBean {

    public static final int DONE = 0
    public static final int DATABASE_DO_NOTHING = -1
    public static final int NEW_INSERT_ID = -2
    public static final int FAILURE = -3

    public static final int ADMIN_ROLE_ID = 1

    public static final int ADMIN_DONE = 10
    public static final int INCORRECT_REPEAT_PASSWD = 11
    public static final int INCORRECT_OLD_PASSWD = 12

}
