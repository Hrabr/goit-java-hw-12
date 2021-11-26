package task2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] strings) {
        MyThreadPoolExecutor myPoolExecutor =
                new MyThreadPoolExecutor(10);
        myPoolExecutor.execute(new MyRunnable());
        myPoolExecutor.shutdown();
    }
}

@Iteration(3)
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello!");
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface Iteration {
    int value() default 1;
}

class MyThreadPoolExecutor extends ThreadPoolExecutor {
    public MyThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    @Override
    public void execute(Runnable command) {
        int repeat=command.getClass().getAnnotation(Iteration.class).value();
        for (int i = 0; i < repeat; i++) {
            super.execute(command);
        }
    }
}