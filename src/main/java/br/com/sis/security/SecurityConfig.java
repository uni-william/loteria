package br.com.sis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.sis.util.cdi.CDIServiceLocator;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public AppUserDetailsService userDetailsService() {
		return new AppUserDetailsService();
	}	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		AppUserDetailsService appUserDetailsService = CDIServiceLocator.getBean(AppUserDetailsService.class);
		auth.userDetailsService(appUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntry.setLoginFormUrl("/Login.xhtml");
		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());
		
		JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
		jsfDeniedEntry.setLoginPath("/AcessoNegado.xhtml");
		jsfDeniedEntry.setContextRelative(true);
		
		http
			.csrf().disable()
			.headers().frameOptions().sameOrigin()
			.and()
			
		.authorizeRequests()
			.antMatchers("/").denyAll()
			.antMatchers("/Login.xhtml", "/Erro.xhtml", "/javax.faces.resources/**").permitAll()
			.antMatchers("/Home.xhtml", "/AcessoNegado.xhtml").authenticated()
			.antMatchers("/usuarios/**").hasAnyRole("ADMINISTRADOR")
			.and()
		.formLogin()
			.loginPage("/Login.xhtml")
			.failureUrl("/Login.xhtml?invalid=true")
			.defaultSuccessUrl("/Home.xhtml", true)
			.and()
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.and()
			
		.exceptionHandling()
			.accessDeniedPage("/AcessoNegado.xhtml")
			.authenticationEntryPoint(jsfLoginEntry)
			.accessDeniedHandler(jsfDeniedEntry);
	}	
}

