import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.iashin.poetry.constants.CommonConstant;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @date: 2024/7/12
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
public class FutureTest {

    @Test
    public void testFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));

        assertEquals("Hello World", completableFuture.get());
    }

    @Test
    public void testExecutorService() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

        Runnable task = () -> System.out.println(Thread.currentThread().getName() + "执行任务: " + System.currentTimeMillis() / 1000);

        // 延迟1秒执行
//        scheduler.schedule(task, 1, TimeUnit.SECONDS);
//
        // 初始延迟0秒，之后每2秒执行一次
//        scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);

        // 初始延迟0秒，每次执行后等待1秒再执行下一次
        scheduler.scheduleWithFixedDelay(task, 0, 5, TimeUnit.SECONDS);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduler.shutdown();
    }

    @Test
    public void testAES() {
        String s = "123";
        String encryption = SecureUtil.aes(CommonConstant.CRYPTOJS_KEY.getBytes()).encryptHex(s);
        System.out.println(StrUtil.toString(encryption));
        String decryptStr = SecureUtil.aes(CommonConstant.CRYPTOJS_KEY.getBytes()).decryptStr(encryption);
        System.out.println(decryptStr);
    }
}