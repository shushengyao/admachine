package com.xmlan.machine.module.system.entity

import com.xmlan.machine.common.base.ObjectEnum
import com.xmlan.machine.common.util.StringUtils

/**
 * 单独维护的操作者对象
 *
 * com.xmlan.machine.module.system.entity
 *
 * @author ayakurayuki
 * @date 2018/3/16-15:53
 */
class Operator {

    int operatorID
    ObjectEnum objectType
    String operatorName

    @Override
    boolean equals(Object obj) {
        if (null == obj) {
            return false
        }
        if (obj instanceof Operator) {
            try {
                return obj.operatorID == this.operatorID && obj.objectType == this.objectType && StringUtils.equals(obj.operatorName, this.operatorName)
            }catch (Exception e){
                e.printStackTrace()
            }
        } else {
            return false
        }
    }

}
