/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec;

import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Properties;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"jp.co.hankyuhanshin.itec.hitmall", "jp.co.hankyuhanshin.itec.hmbase",
                "jp.co.hankyuhanshin.itec.hitmall.batch"})
@EnableAsync
public class PdrFrontSiteServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws IOException {
        Properties props = System.getProperties();
        URL url = Thread.currentThread().getContextClassLoader().getResource("config/spring-log.properties");
        if (url != null) {
            InputStream inputStream = url.openStream();
            props.load(inputStream);
            System.setProperties(props);
            inputStream.close();
        }
        SpringApplication app = new SpringApplication(PdrFrontSiteServiceApplication.class);
        app.addInitializers(context -> {
            context.addBeanFactoryPostProcessor(beanFactory -> {
                final DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory) beanFactory;
                dlbf.setAutowireCandidateResolver(new AutowireCandidateResolver(dlbf));
            });
        });
        app.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PdrFrontSiteServiceApplication.class);
    }

    // Lazy-loadはアノテーションにしか対応していないためResolverクラスをOrverrideする
    private static class AutowireCandidateResolver extends ContextAnnotationAutowireCandidateResolver {

        private final DefaultListableBeanFactory beanFactory;

        private AutowireCandidateResolver(DefaultListableBeanFactory beanFactory) {
            this.beanFactory = beanFactory;
        }

        @Override
        protected boolean isLazy(DependencyDescriptor descriptor) {

            MethodParameter methodParam = descriptor.getMethodParameter();
            if (methodParam != null) {
                // ControllerでAutowiredしているクラスをLazy-loadする
                for (Annotation ann : methodParam.getContainingClass().getAnnotations()) {
                    RestController restController = AnnotationUtils.getAnnotation(ann, RestController.class);
                    if (restController != null) {
                        return true;
                    }
                }
            }

            return super.isLazy(descriptor);
        }
    }

}
