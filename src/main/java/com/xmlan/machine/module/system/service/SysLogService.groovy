package com.xmlan.machine.module.system.service

import com.github.pagehelper.PageHelper
import com.google.common.collect.Lists
import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.base.ModuleEnum
import com.xmlan.machine.common.base.ObjectEnum
import com.xmlan.machine.common.base.OperateEnum
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.common.cache.UserCache
import com.xmlan.machine.common.util.DateUtils
import com.xmlan.machine.module.system.dao.SysLogDAO
import com.xmlan.machine.module.system.entity.Operator
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

    List<SysLog> findAll(int pageNo) {
        PageHelper.startPage pageNo, pageSize
        def list = super.findAll()
        return setFriendlyInfoToList(list)
    }

    @Override
    List<SysLog> findList(SysLog entity, int pageNo) {
        PageHelper.startPage pageNo, pageSize
        def list = super.findList(entity, pageNo)
        return setFriendlyInfoToList(list)
    }

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

    private static List<SysLog> setFriendlyInfoToList(List<SysLog> list) {
        list.each {
            it.type = ModuleEnum.getDescription(it.type)
            it.operate = OperateEnum.getDescription(it.operate)
            it.operatorObject = ObjectEnum.getDescription(it.operatorObject)
        }
        return list
    }

    List<Operator> getOperators(List<SysLog> logs) {
        List<Operator> operators = Lists.newArrayList()
        logs.each {
            def operator = new Operator()
            operator.operatorID = it.operator
            operator.objectType = ObjectEnum.getObjectType(it.operatorObject)
            switch (operator.objectType) {
                case ObjectEnum.User:
                    operator.operatorName = UserCache.get(operator.operatorID).username
                    break
                case ObjectEnum.Machine:
                    operator.operatorName = AdvertisementMachineCache.get(operator.operatorID).name
                    break
            }
            if (!operators.contains(operator)) {
                operators.add(operator)
            }
        }
        return operators
    }

}
