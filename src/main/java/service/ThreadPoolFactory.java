package service;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 */
public class ThreadPoolFactory implements ThreadFactory {

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    private final String threadNamePrefix;

    public ThreadPoolFactory(String prefix) {
        this.threadNamePrefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadNamePrefix + "-ThreadJob-" + atomicInteger.getAndIncrement());
        // 设置为守护线程：当main线程退出后，线程池中的线程会被中断而退出
        thread.setDaemon(true);
        return thread;
    }
}
