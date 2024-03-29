package jp.co.hankyuhanshin.itec.hitmall.front.config.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * Controller/Service/LogicのBean定義をPrototype化
 * 作成日：2021/04/12
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Configuration
public class PrototypeScopedBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private static final String BEAN_SCOPE_SINGLETON = "singleton";
    private static final String BEAN_SCOPE_PROTOTYPE = "prototype";
    private static final String LOGIC_BEAN_SUFFIX = "LogicImpl";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDef;

        // @Controllerが付与されたControllerクラスは、全て Scope='Prototype' を設定
        String[] controllerBeanNameList = beanFactory.getBeanNamesForAnnotation(Controller.class);
        for (String controllerBeanName : controllerBeanNameList) {
            beanDef = beanFactory.getBeanDefinition(controllerBeanName);
            if (BEAN_SCOPE_SINGLETON.equals(beanDef.getScope())) {
                beanDef.setScope(BEAN_SCOPE_PROTOTYPE);
            }
        }

        // @Serviceが付与されたServiceクラスは、全て Scope='Prototype' を設定
        String[] serviceBeanNameList = beanFactory.getBeanNamesForAnnotation(Service.class);
        for (String serviceBeanName : serviceBeanNameList) {
            beanDef = beanFactory.getBeanDefinition(serviceBeanName);
            if (BEAN_SCOPE_SINGLETON.equals(beanDef.getScope())) {
                beanDef.setScope(BEAN_SCOPE_PROTOTYPE);
            }
        }

        // @Componentが付与されたLogicクラスは、全て Scope='Prototype' を設定
        String[] logicBeanNameList = beanFactory.getBeanNamesForAnnotation(Component.class);
        for (String logicBeanName : logicBeanNameList) {
            if (logicBeanName.contains(LOGIC_BEAN_SUFFIX)) {
                beanDef = beanFactory.getBeanDefinition(logicBeanName);
                if (BEAN_SCOPE_SINGLETON.equals(beanDef.getScope())) {
                    beanDef.setScope(BEAN_SCOPE_PROTOTYPE);
                }
            }
        }
    }

}
