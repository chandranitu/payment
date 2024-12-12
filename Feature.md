
# Top up of credit card


 
/**
 * Endpoint to set a spending limit for a credit card.
 * 
 * @param cardNumber The card number to set the limit for.
 * @param limit The spending limit.
 * @return ResponseEntity confirming the limit setting.
 */
@PostMapping("/set-limit/{cardNumber}")
public ResponseEntity<String> setSpendingLimit(@PathVariable String cardNumber, @RequestBody BigDecimal limit) {
    paymentService.setSpendingLimit(cardNumber, limit); // Set the spending limit
    return ResponseEntity.ok("Spending limit set successfully");
}



API---------------
i have spring boot application has lot of api. wanted to categorize like Core api ,Domain api ,Experience api so that core api is never exposed to public domain


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CoreApi {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DomainApi {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExperienceApi {}



import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/core")
public class CoreApiController {

    @CoreApi
    @PostMapping("/users")
    public String createUser() {
        return "User created";
    }
}

@RestController
@RequestMapping("/api/domain")
public class DomainApiController {

    @DomainApi
    @GetMapping("/products")
    public String getProducts() {
        return "List of products";
    }
}

@RestController
@RequestMapping("/api/experience")
public class ExperienceApiController {

    @ExperienceApi
    @GetMapping("/offers")
    public String getOffers() {
        return "List of offers";
    }
}


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiAccessAspect {

    @Before("@annotation(coreApi)")
    public void checkCoreApiAccess(CoreApi coreApi) {
        // Check if user has ADMIN role
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Access denied for Core API");
        }
    }

    @Before("@annotation(domainApi)")
    public void checkDomainApiAccess(DomainApi domainApi) {
        // Check if user is authenticated
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new AccessDeniedException("Access denied for Domain API");
        }
    }

    // Experience APIs can be public by default, no need for additional checks
}




@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/api/experience/**").permitAll() // Public
            .antMatchers("/api/domain/**").authenticated() // Requires authentication
            .antMatchers("/api/core/**").hasRole("ADMIN") // Admin only
            .anyRequest().authenticated() // Other requests
            .and()
            .httpBasic(); // Use your preferred authentication method
    }
}



Environment-Specific Configuration---------------------------
You can use environment variables or configuration files to toggle access based on the environment (e.g., dev, test, prod). For example, in your application.yml:

yaml
Copy code
security:
  core-api:
    enabled: true # or false based on the environment
Then use this property in your security configuration:

java
Copy code
@Value("${security.core-api.enabled}")
private boolean isCoreApiEnabled;

@Override
protected void configure(HttpSecurity http) throws Exception {
    if (isCoreApiEnabled) {
        http.authorizeRequests()
            .antMatchers("/api/core/**").hasRole("ADMIN")
            .anyRequest().authenticated();
    } else {
        http.authorizeRequests()
            .antMatchers("/api/core/**").permitAll()
            .anyRequest().authenticated();
    }
}