package com.xmlan.machine.common.base

import com.xmlan.machine.common.util.BeanValidator
import com.xmlan.machine.common.util.SessionUtils
import com.xmlan.machine.common.util.StringUtils
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
 * Created by ayakurayuki on 2017/12/11-14:41. <br/>
 * Package: com.xmlan.machine.common.base <br/>
 * Controller基类 <br/>
 */
abstract class BaseController extends BaseBean {

    protected Logger logger = LogManager.getLogger(getClass())

    /**
     * Bean校验器
     */
    @Autowired
    protected Validator validator
    /**
     * 通用的request
     */
    @Autowired
    protected HttpServletRequest request
    /**
     * 通用的response
     */
    @Autowired
    protected HttpServletResponse response

    /**
     * 配置的管理页面根路径
     */
    @Value('${adminPath}')
    public String adminPath
    /**
     * 配置的广告机平台访问根路径
     */
    @Value('${servicePath}')
    public String servicePath
    /**
     * 配置的手机端平台访问根路径
     */
    @Value('${mobilePath}')
    public String mobilePath

    @ModelAttribute('adminPath')
    String adminPath() { return adminPath }

    @ModelAttribute('servicePath')
    String servicePath() { return servicePath }

    @ModelAttribute('mobilePath')
    String mobilePath() { return mobilePath }

    @ModelAttribute('adminRoleID')
    int adminRoleID() { return ADMIN_ROLE_ID }

    @ModelAttribute('newEntityID')
    int newEntityID() { return NEW_INSERT_ID }

    @ModelAttribute('loginUser')
    User loginUser(HttpServletRequest request) { return SessionUtils.getAdmin(request) }

    /**
     * 添加临时消息
     *
     * @param redirectAttributes Redirect跳转属性封装对象
     * @param messages 消息（任意个数的对象）
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        def stringBuilder = new StringBuilder()
        for (message in messages) {
            stringBuilder.append(message).append(messages.length > 1 ? '<br/>' : StringUtils.EMPTY)
        }
        redirectAttributes.addFlashAttribute("message", stringBuilder.toString())
        logger.debug "Message added."
    }

    /**
     * 添加消息
     *
     * @param model model
     * @param messages 消息（任意个数的对象）
     */
    protected void addMessage(Model model, String... messages) {
        def stringBuilder = new StringBuilder()
        for (message in messages) {
            stringBuilder.append(message).append(messages.length > 1 ? '<br/>' : StringUtils.EMPTY)
        }
        model.addAttribute("message", stringBuilder.toString())
        logger.debug "Message added."
    }

    protected boolean beanValidator(Object modelOrAttribute, Object object, Class<?>... groups) {
        try {
            BeanValidator.validateWithException(validator, object, groups)
        } catch (ConstraintViolationException ex) {
            def list = BeanValidator.extractPropertyAndMessageAsList(ex, ": ")
            list.add(0, "Bean validation failed: ")
            if (modelOrAttribute instanceof RedirectAttributes) {
                addMessage((RedirectAttributes) modelOrAttribute, list.toArray([] as String[]))
            } else if (modelOrAttribute instanceof Model) {
                addMessage((Model) modelOrAttribute, list.toArray([] as String[]))
            }
            return false
        }
        return true
    }

}
