package com.xmlan.machine.common.base

import com.xmlan.machine.common.util.BeanValidator
import com.xmlan.machine.common.util.SessionUtils
import com.xmlan.machine.module.user.entity.User
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.servlet.mvc.support.RedirectAttributes

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException
import javax.validation.Validator

/**
 * Created by ayakurayuki on 2017/12/11-14:41.
 * Package: com.xmlan.machine.common.base
 */
abstract class BaseController extends BaseBean {

    protected Logger logger = LogManager.getLogger(getClass())

    @Autowired
    protected Validator validator
    @Autowired
    protected HttpServletRequest request
    @Autowired
    protected HttpServletResponse response

    /**
     * 配置的管理页面根路径
     */
    @Value('${adminPath}')
    public String adminPath

    @ModelAttribute('adminPath')
    String adminPath() {
        return adminPath
    }

    @ModelAttribute('adminRoleID')
    int adminRoleID() {
        return ADMIN_ROLE_ID
    }

    @ModelAttribute('newEntityID')
    int newEntityID() {
        return NEW_INSERT_ID
    }

    @ModelAttribute('loginUser')
    User loginUser(HttpServletRequest request) {
        return SessionUtils.GetAdmin(request)
    }

    /**
     * 添加临时消息
     *
     * @param redirectAttributes Redirect跳转属性封装对象
     * @param messages 消息（任意个数的对象）
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder stringBuilder = new StringBuilder()
        for (String message : messages) {
            stringBuilder.append(message).append(messages.length > 1 ? "<br/>" : "")
        }
        redirectAttributes.addFlashAttribute("message", stringBuilder.toString())
        logger.debug "Message added."
    }

    /**
     * 添加临时消息
     *
     * @param model model
     * @param messages 消息（任意个数的对象）
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder stringBuilder = new StringBuilder()
        for (String message : messages) {
            stringBuilder.append(message).append(messages.length > 1 ? "<br/>" : "")
        }
        model.addAttribute("message", stringBuilder.toString())
        logger.debug "Message added."
    }

    protected boolean beanValidator(Object attributesObject, Object object, Class<?>... groups) {
        try {
            BeanValidator.validateWithException(validator, object, groups)
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidator.extractPropertyAndMessageAsList(ex, ": ")
            list.add(0, "数据验证失败：")
            if (attributesObject instanceof RedirectAttributes) {
                addMessage((RedirectAttributes) attributesObject, list.toArray([] as String[]))
            } else if (attributesObject instanceof Model) {
                addMessage((Model) attributesObject, list.toArray([] as String[]))
            }
            return false
        }
        return true
    }

}
