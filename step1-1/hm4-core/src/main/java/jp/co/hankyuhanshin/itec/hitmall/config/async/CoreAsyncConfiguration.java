/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.config.async;

import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 非同期処理設定クラス
 *
 * @author yt23807
 * @version $Revision: 1.0 $
 */
@Configuration
public class CoreAsyncConfiguration extends AsyncConfigurerSupport {

    //    @Bean
    public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor delegate) {
        return new DelegatingSecurityContextAsyncTaskExecutor(delegate);
    }

    /**
     * ペイメントクライアントの定義
     *
     * @return PaymentClient
     */
    @Override
    @Bean
    public Executor getAsyncExecutor() {
        ContextAwarePoolExecutor executor = new ContextAwarePoolExecutor();

        // スレッドプール設定
        // 
        // ※スレッド設定の流れはcoreSystem.properties参照
        executor.setCorePoolSize(PropertiesUtil.getSystemPropertiesValueToInt("async.executor.core.pool.size"));
        executor.setQueueCapacity(PropertiesUtil.getSystemPropertiesValueToInt("async.executor.queue.capacity"));
        executor.setMaxPoolSize(PropertiesUtil.getSystemPropertiesValueToInt("async.executor.max.pool.size"));
        executor.setThreadNamePrefix(PropertiesUtil.getSystemPropertiesValue("async.executor.thread.name.prefix"));

        // 2023-renew No14 from here
        executor.setRejectedExecutionHandler(new RejectedExecutionHandlerImpl());
        // 2023-renew No14 to here

        executor.initialize();
        return executor;
    }

    /**
     * 非同期処理内でリクエストスコープ／セッションスコープのBeanを扱えるようにするためのExecutor
     *
     * @author yt23807
     */
    public class ContextAwarePoolExecutor extends ThreadPoolTaskExecutor {
        private static final long serialVersionUID = 1L;

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            // Request/Sessionスコープ参照可能Callable
            ContextAwareCallable contextAwareCallable =
                            new ContextAwareCallable(task, RequestContextHolder.currentRequestAttributes());
            // DelegatingSecurityContextCallableでラップすることで、SpringSecurity管理情報も参照可能とする
            return super.submit(new DelegatingSecurityContextCallable(contextAwareCallable));
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
            // Request/Sessionスコープ参照可能Callable
            ContextAwareCallable contextAwareCallable =
                            new ContextAwareCallable(task, RequestContextHolder.currentRequestAttributes());
            // DelegatingSecurityContextCallableでラップすることで、SpringSecurity管理情報も参照可能とする
            return super.submitListenable(new DelegatingSecurityContextCallable(contextAwareCallable));
        }
    }

    /**
     * Request参照を可能なCallableクラス
     *
     * @param <T>
     * @author yt23807
     */
    public class ContextAwareCallable<T> implements Callable<T> {
        private Callable<T> task;
        private RequestAttributes context;

        /**
         * コンストラクタ
         *
         * @param task
         * @param context
         */
        public ContextAwareCallable(Callable<T> task, RequestAttributes context) {
            this.task = task;
            this.context = context;
        }

        @Override
        public T call() throws Exception {
            if (context != null) {
                RequestContextHolder.setRequestAttributes(context);
            }
            try {
                return task.call();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }

    // 2023-renew No14 from here

    /**
     * TaskRejectedException対策を施したExecutionHandler
     * ※クイックオーダーやセールde予約による一括カート追加処理で、
     *   アクセス情報登録を非同期呼び出しする際、TaskRejectedException が多々発生する。
     *   呼び出しスレッドがブロッキングキューに空きができるまで待機するように修正
     */
    public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                throw new RejectedExecutionException(
                                "There was an unexpected InterruptedException whilst waiting to add a Runnable in the executor's blocking queue",
                                e
                );
            }
        }
    }

    // 2023-renew No14 to here

}
