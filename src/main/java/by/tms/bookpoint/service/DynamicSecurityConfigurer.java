//package by.tms.bookpoint.service;
//
//import by.tms.bookpoint.configuration.SecurityRule;
//import by.tms.bookpoint.utils.SecurityRulesProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class DynamicSecurityConfigurer {
//
//    @Autowired
//    private SecurityRulesProperties securityRulesProperties;
//
//    public void configure(HttpSecurity http) throws Exception {
//        List<SecurityRule> rules = securityRulesProperties.getRules();
//
//        for (SecurityRule rule : rules) {
//            String method = rule.getMethod();
//
//            for (String path : rule.getPaths()) {
//                if ("permitAll".equalsIgnoreCase(rule.getRoles())) {
//                    if ("ALL".equalsIgnoreCase(method)) {
//                        // Если метод ALL, маппим только путь
//                        http.authorizeHttpRequests()
//                                .requestMatchers(path.trim()).permitAll();
//                    } else {
//                        // Если указан конкретный метод
//                        http.authorizeHttpRequests()
//                                .requestMatchers(HttpMethod.valueOf(method), path.trim()).permitAll();
//                    }
//                } else {
//                    if ("ALL".equalsIgnoreCase(method)) {
//                        // Если метод ALL, маппим только путь
//                        http.authorizeHttpRequests()
//                                .requestMatchers(path.trim()).hasAnyRole(rule.getRoles().split(","));
//                    } else {
//                        // Если указан конкретный метод
//                        http.authorizeHttpRequests()
//                                .requestMatchers(HttpMethod.valueOf(method), path.trim())
//                                .hasAnyRole(rule.getRoles().split(","));
//                    }
//                }
//            }
//        }
//
//        // Любые другие запросы требуют аутентификации
//        http.authorizeHttpRequests().anyRequest().authenticated();
//    }
//}
