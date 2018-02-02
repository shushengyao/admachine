package com.xmlan.machine.common.listener

import com.xmlan.machine.common.util.ColorPrintUtils

import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

/**
 * WebServer启动初始化
 */
class WelcomeListener implements ServletContextListener {

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
        initialization()
    }

    @Override
    void contextDestroyed(ServletContextEvent servletContextEvent) {
        ColorPrintUtils.Println(ColorPrintUtils.GREEN, "See you next time.")
    }

    private static void initialization() {
        def requirements = ["pillow"]
        requirements.each { requirement ->
            def pip3 = Runtime.runtime.exec "pip3 install ${requirement}"
            pip3.inputStream.eachLine { response ->
                println response
            }
        }
    }

}
