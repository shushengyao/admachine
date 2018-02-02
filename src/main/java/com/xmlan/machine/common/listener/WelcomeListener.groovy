package com.xmlan.machine.common.listener

import com.xmlan.machine.common.config.Global
import com.xmlan.machine.common.config.TinyImage
import com.xmlan.machine.common.util.ColorPrintUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

/**
 * WebServer启动初始化
 */
class WelcomeListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(WelcomeListener.class)

    @Override
    void contextInitialized(ServletContextEvent servletContextEvent) {
        String logo = """
                                                 .,;LzjCEUOSbUbK2nyr;..          
                                         .;c1ESGN88888G8N8GGGGOWUSKSKSK2nz;.           
                                  :;zjUEUCZTr;,..     . ..,,rLZUG8g08888G88D08NSL.      
                             .:rLzrr:.                             ,TKGg08WWbWG0DW;    
                          .;Lr;.                          ,rZz          rC8DGSSEOG8W.  
                        ;c,.                            ;WWKOi             ,iNGSESUB0. 
                      jWL;.     ;   .       .    ..    ;@EUK;;       .       :KNSKUS8j 
                    c@G  1@@  y@8   @@.    B@1  .@8    ,..  n@@.     @@y    @n nNbKb0j 
                  :G@G     0@D@.    @@@;  @@@T   @G        r@ G@     @@@@;  @K  bWbG88  
                 :888n     .@@@     @.;@K@b @z   @K       ;@O ;@@    @T O@@.@K  7B8D.  
                :bGUWW    D@r 8@O   @; ;@U  @j   @@:;;Lr ;@W8GO @@   @O   8@@E  88/    
                GbSUU8U  Z@:   j@U  @;     .@r   g8@@@@@ BG     ;@L  @y    .8; 88,     
                WbOUUU8G;                                                    rO;       
                 2BOUUUW8Wr.                                             .,zL:         
                  UgBbbUOWBDW7,                                      .,;cr,            
                   :Z80BWWbWGDDDW2c;.                          .:rL7Tr,.               
                      ,1W80DDNNG88gDg8NOUiyrr;,:,:,:,;rr7iEUWObZy;:                    
                            :;rTyii2CESWWGG8NB88GNWWSEj7r;.                            
        """
        ColorPrintUtils.Println(ColorPrintUtils.BLUE, logo)

        // 本程序不涉及实时Python调用，所以可以异步安装requirements
        Thread.start {
            try {
                initialization('pip3') // 优先尝试使用pip3安装
                TinyImage.getInstance().setEnable(true)
            } catch (Exception e) {
                logger.info("pip3安装失败，尝试使用pip安装。消息：${e.message}")
                try {
                    initialization('pip') // Runtime.exec使用pip3被拒绝时尝试使用pip安装
                    TinyImage.getInstance().setEnable(true)
                } catch (Exception ex) {
                    TinyImage.getInstance().setEnable(false)
                    logger.error("Python依赖库未安装成功，不能使用缩图功能。消息：${ex.message}") // 所有安装均失败
                }
            }
        }
    }

    @Override
    void contextDestroyed(ServletContextEvent servletContextEvent) {
        ColorPrintUtils.Println(ColorPrintUtils.GREEN, "See you next time.")
    }

    private static void initialization(String bin) {
        def requirementsText = Global.getConfig('python.requirements')
        def requirements = requirementsText.split(',')
        requirements.each { requirement ->
            def pip3 = Runtime.runtime.exec "${bin} install ${requirement}"
            pip3.inputStream.eachLine { response ->
                logger.info(response)
            }
        }
    }

}
