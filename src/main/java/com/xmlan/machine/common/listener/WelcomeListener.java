package com.xmlan.machine.common.listener;

import com.xmlan.machine.common.util.ColorPrintUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class WelcomeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String logo = "                                 .,;LzjCEUOSbUbK2nyr;..          \n" +
                "                         .;c1ESGN88888G8N8GGGGOWUSKSKSK2nz;.           \n" +
                "                  :;zjUEUCZTr;,..     . ..,,rLZUG8g0$88G88D0$NSL.      \n" +
                "             .:rLzrr:.                             ,TKGg08WWbWG0DW;    \n" +
                "          .;Lr;.                          ,rZz          rC8DGSSEOG$W.  \n" +
                "        ;c,.                            ;WWKOi             ,iNGSESUB0. \n" +
                "      jWL;.     ;   .       .    ..    ;@EUK;;       .       :KNSKUS$j \n" +
                "    c@G  1@@  y@8   @@.    B@1  .@8    ,..  n@@.     @@y    @n nNbKb0j \n" +
                "  :G@G     0@D@.    @@@;  @@@T   @G        r@ G@     @@@@;  @K  bWbG$  \n" +
                " :$88n     .@@@     @.;@K@b @z   @K       ;@O ;@@    @T O@@.@K  7B8D.  \n" +
                ":bGUWW    D@r $@O   @; ;@U  @j   @@:;;Lr ;@$W8GO@@   @O   $@@E  $$S    \n" +
                "GbSUU8U  Z@:   j@U  @;     .@r   g$@@@@@ BG     ;@L  @y    .8; $8,     \n" +
                "WbOUUU8G;                                                    rO;       \n" +
                " 2BOUUUW8Wr.                                             .,zL:         \n" +
                "  UgBbbUOWBDW7,                                      .,;cr,            \n" +
                "   :Z80BWWbWGDDDW2c;.                          .:rL7Tr,.               \n" +
                "      ,1W80DDNNG88gDg8NOUiyrr;,:,:,:,;rr7iEUWObZy;:                    \n" +
                "            :;rTyii2CESWWGG8NB88GNWWSEj7r;.                            ";
        ColorPrintUtils.Println(ColorPrintUtils.BLUE, logo);
        initialization();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ColorPrintUtils.Println(ColorPrintUtils.GREEN, "See you next time.");
    }

    private void initialization() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("requirements.txt");
        if (null != url) {
            File file = new File(url.getFile());
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Runtime.getRuntime().exec("pip3 install " + line);
                }
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
