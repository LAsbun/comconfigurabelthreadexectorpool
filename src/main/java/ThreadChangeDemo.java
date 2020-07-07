import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by sws
 * <p>
 * demo from https://mp.weixin.qq.com/s/EhBt44Rj0c5E-UVf742aGw")
 */

public class ThreadChangeDemo {

    public static void main(String[] args) throws InterruptedException {
        dynamicModeifuExector();
    }

    private static ThreadPoolExecutor buildTHreadPoolExecutor() {
        return new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("ThreadChangeDemo").build());
    }

    private static void dynamicModeifuExector() throws InterruptedException {
        ThreadPoolExecutor executor = buildTHreadPoolExecutor();
        for (int i = 0; i < 15; i++) {
            executor.submit(() -> {
                threadPoolStatus(executor, "创建任务");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolStatus(executor, "改变之前");
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
        threadPoolStatus(executor, "改变之后");
        Thread.currentThread().join();

    }

    private static void threadPoolStatus(ThreadPoolExecutor executor, String name) {
        LinkedBlockingQueue queue = (LinkedBlockingQueue) executor.getQueue();
        System.out.println(Thread.currentThread().getName() + "-" + name + "-:" +
                "核心线程数:" + executor.getCorePoolSize() +
                " 活动线程数:" + executor.getActiveCount() +
                " 最大线程数:" + executor.getMaximumPoolSize() +
                " 线程池活跃度:" + diveide(executor.getActiveCount(), executor.getMaximumPoolSize()) +
                " 任务完成数:" + executor.getCompletedTaskCount() +
                " 队列大小:" + (queue.size() + queue.remainingCapacity()) +
                " 当前排队线程数:" + queue.size() +
                " 队列剩余大小:" + queue.remainingCapacity()

        );
    }

    private static String diveide(int num1, int num2) {
        return String.format("%1.2f%%", Double.parseDouble(num1 + "")
                / Double.parseDouble(num2 + ""));
    }
}
