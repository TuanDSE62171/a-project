package portal_xml.portal_xml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import portal_xml.portal_xml.filter.CrawlerTokenRequestFilter;

@SpringBootApplication
@EnableConfigurationProperties
public class PortalXmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalXmlApplication.class, args);
	}

	@Bean
	@DependsOn(value = "contextBean")
	public FilterRegistrationBean<CrawlerTokenRequestFilter> authenticateFilter() {
		FilterRegistrationBean<CrawlerTokenRequestFilter> registrationBean =
				new FilterRegistrationBean<>();
		registrationBean.setFilter(new CrawlerTokenRequestFilter());
		registrationBean.addUrlPatterns("/crawler/*");
		return registrationBean;
	}
}
