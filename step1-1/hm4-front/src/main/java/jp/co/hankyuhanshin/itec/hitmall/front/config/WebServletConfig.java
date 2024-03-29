package jp.co.hankyuhanshin.itec.hitmall.front.config;

import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.json.category.CategoryDataListServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;

@Configuration
public class WebServletConfig {

    @Bean
    public ServletRegistrationBean<HttpServlet> categoryDataListServlet() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new CategoryDataListServlet());
        servRegBean.addUrlMappings("/categorydatalistServlet");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }
}
