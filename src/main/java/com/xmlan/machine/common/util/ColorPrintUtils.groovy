package com.xmlan.machine.common.util

/**
 * Created by AyakuraYuki on 12/11/2017
 */
class ColorPrintUtils {

    public static final String BLACK = "\033[0;30m"
    public static final String RED = "\033[0;31m"
    public static final String GREEN = "\033[0;32m"
    public static final String YELLOW = "\033[0;33m"
    public static final String BLUE = "\033[0;34m"
    public static final String PURPLE = "\033[0;35m"
    public static final String DEEP_GREEN = "\033[0;36m"
    public static final String WHITE = "\033[0;37m"

    /**
     * Function: To format message with color, end with WHITE color.
     * Tip: To be compatible with Linux or Unix os, or Linux-like terminal.
     *
     * @param color color which defined in this class
     * @param message original message with placeholder
     * @param objects as String.format()'s objects
     * @return Message with color!
     */
    public static String Format(String color, String message, Object... objects) {
        return color + (String.format(message, objects)) + WHITE
    }

    /**
     * Directly print colored message with new line.
     *
     * @param color color which defined in this class
     * @param message original message with placeholder
     * @param objects as String.format()'s objects
     */
    static void Println(String color, String message, Object... objects) {
        System.out.println(Format(color, message, objects))
    }

    /**
     * Directly print colored message in-line.
     *
     * @param color color which defined in this class
     * @param message original message with placeholder
     * @param objects as String.format()'s objects
     */
    static void Print(String color, String message, Object... objects) {
        System.out.print(Format(color, message, objects))
    }

}
