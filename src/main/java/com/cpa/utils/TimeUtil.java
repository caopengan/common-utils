package com.cpa.utils;

import java.util.concurrent.TimeUnit;

/**
 *毫秒级时间获取
 *高并发场景下，直接调用 System.currentTimeMillis() 性能下降很明显会阻塞主线程
 */
public class TimeUtil {

    private static volatile long cuurentTimeMillis;

    static {
        cuurentTimeMillis = System.currentTimeMillis();
        Thread daemon = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    cuurentTimeMillis = System.currentTimeMillis();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //设置当前线程为守护线程，保证其他线程退出之后，自动退出
        daemon.setDaemon(true);
        daemon.setName("time-tick-thread");
        daemon.start();
    }

    public static long currentTimeMillis(){
        return cuurentTimeMillis;
    }

}
