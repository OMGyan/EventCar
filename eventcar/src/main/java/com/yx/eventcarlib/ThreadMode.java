package com.yx.eventcarlib;

/**
 * Author by YX, Date on 2019/8/6.
 */
public enum ThreadMode {

    POSTING, //与发布者同处线程
    MAIN, // 始终保持主线程
    BACKGROUND //始终保持子线程
}
