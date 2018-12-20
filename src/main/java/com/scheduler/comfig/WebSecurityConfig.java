package com.scheduler.comfig;

import com.scheduler.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

/**
 * This class make configuration when application getting start
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//надає можливість перевіряти права користувачі і адмінів (UserController)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserSevice userSevice;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //authorization turns on
                    .antMatchers("/", "/registration", "/static/**").permitAll() //all users have access to this page
                    .anyRequest().authenticated()//for all other pages user shod be authorized
                .and()
                    .formLogin()//вказуємо форм логін
                    .loginPage("/login")//вказуємо логін пайдж
                    .permitAll()//дозволяємо всім ним користуватися
                .and()
                    .logout()//дозволяємо всім користуватися логаутом
                    .permitAll();//
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSevice)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)//ходить в базу даних буру звідти юзерів та їх ролі
                .passwordEncoder(NoOpPasswordEncoder.getInstance())//шифрує паролі, щоб вони не зберігалися у якному вигляді
                .usersByUsernameQuery("select username, password, active from usr where username=?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.username=?");//допомогає спрінгу отримати список юзерів з їх ролями
    }*/

    /*   @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("u")
                        .password("p")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }*/
}