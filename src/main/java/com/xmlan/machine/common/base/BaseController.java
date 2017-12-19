package com.xmlan.machine.common.base;

import com.xmlan.machine.common.util.BeanValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;

/**
 * Created by ayakurayuki on 2017/12/11-14:41.
 * Package: com.xmlan.machine.common.base
 */
public abstract class BaseController {

    protected Logger logger = LogManager.getLogger(getClass());

    /**
     * 新的记录标记
     */
    protected final int NEW_INSERT_ID = -2;

    /**
     * 配置的管理页面根路径
     */
    @Value("${adminPath}")
    protected String adminPath;

    @Autowired
    protected Validator validator;

    /**
     * 添加临时消息
     *
     * @param redirectAttributes Redirect跳转属性封装对象
     * @param messages           消息（任意个数的对象）
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String message : messages) {
            stringBuilder.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute("message", stringBuilder.toString());
        logger.debug("Message added.");
    }

    /**
     * 添加临时消息
     *
     * @param model    model
     * @param messages 消息（任意个数的对象）
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String message : messages) {
            stringBuilder.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute("message", stringBuilder.toString());
        logger.debug("Message added.");
    }

    protected boolean beanValidator(Object attributesObject, Object object, Class<?>... groups) {
        try {
            BeanValidator.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidator.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            if (attributesObject instanceof RedirectAttributes) {
                addMessage((RedirectAttributes) attributesObject, list.toArray(new String[]{}));
            } else if (attributesObject instanceof Model) {
                addMessage((Model) attributesObject, list.toArray(new String[]{}));
            }
            return false;
        }
        return true;
    }

}
