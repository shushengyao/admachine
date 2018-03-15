package com.xmlan.machine.module.system.service

import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.base.ModuleEnum
import com.xmlan.machine.common.base.ObjectEnum
import com.xmlan.machine.common.base.OperateEnum
import com.xmlan.machine.common.util.DateUtils
import com.xmlan.machine.module.system.dao.SysLogDAO
import com.xmlan.machine.module.system.entity.SysLog
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统日志服务类
 * <p>
 * com.xmlan.machine.module.system.service
 *
 * @author ayakurayuki
 * @date 2018/3/13-16:38
 */
@Service("SysLogService")
@Transactional(readOnly = true)
class SysLogService extends BaseService<SysLog, SysLogDAO> {

    /**
     * 禁止修改日志记录
     *
     * @param entity 日志记录对象
     * @return DATABASE_DO_NOTHING: -1
     */
    @Override
    int update(SysLog entity) {
        return DATABASE_DO_NOTHING
    }

    /**
     * 禁止删除日志记录
     *
     * @param entity 日志记录对象
     * @return DATABASE_DO_NOTHING: -1
     */
    @Override
    int delete(SysLog entity) {
        return DATABASE_DO_NOTHING
    }

    /**
     * 创建新的日志记录
     *
     * @param module 操作对象的
     * @param operate 操作类型
     * @param operatorID 操作对象ID（用户/广告机 的ID）
     * @param operatorObject 操作对象类型（用户/广告机）
     * @param description 描述
     * @return 返回值
     */
    int log(ModuleEnum module, OperateEnum operate, int operatorID, ObjectEnum operatorObject, String description) {
        def log = new SysLog()
        log.type = module.toString()
        log.operate = operate.toString()
        log.operator = operatorID
        log.operatorObject = operatorObject.toString()
        log.description = description
        log.logDate = DateUtils.dateTime
        super.insert(log)
    }

}
