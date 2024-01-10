/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.config;

import mockproject.backend.config.filter.JWTAuthenticationFilter;
import mockproject.backend.domain.entity.enums.EPermission;
import mockproject.backend.domain.entity.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static mockproject.backend.utils.constant.ClassUrlAPI.*;
import static mockproject.backend.utils.constant.SyllabusUrlAPI.*;
import static mockproject.backend.utils.constant.TrainingUrlAPI.*;
import static mockproject.backend.utils.constant.UserUrlAPI.*;
import static org.springframework.security.config.Elements.LOGOUT;

//author: DuongAM
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private Environment environment;
    //    role
    private final String SUPER = ERole.SUPERADMIN.name();
    private final String ADMIN = ERole.ADMIN.name();
    private final String TRAINER = ERole.TRAINER.name();
    //permission USER
    private final String CREATE_USER = EPermission.CREATE_USER.name();
    private final String READ_USER = EPermission.READ_USER.name();
    private final String UPDATE_USER = EPermission.UPDATE_USER.name();
    private final String DELETE_USER = EPermission.DELETE_USER.name();
    private final String IMPORT_USER = EPermission.IMPORT_USER.name();

//    permission syllabus

    private final String CREATE_SYLLABUS = EPermission.CREATE_SYLLABUS.name();
    private final String READ_SYLLABUS = EPermission.READ_SYLLABUS.name();
    private final String UPDATE_SYLLABUS = EPermission.CREATE_USER.name();
    private final String DELETE_SYLLABUS = EPermission.DELETE_SYLLABUS.name();
    private final String IMPORT_SYLLABUS = EPermission.IMPORT_SYLLABUS.name();

//    permission training

    private final String CREATE_TRAINING = EPermission.CREATE_TRAINING.name();
    private final String READ_TRAINING = EPermission.READ_TRAINING.name();
    private final String UPDATE_TRAINING = EPermission.UPDATE_TRAINING.name();
    private final String DELETE_TRAINING = EPermission.DELETE_TRAINING.name();
    private final String IMPORT_TRAINING = EPermission.IMPORT_TRAINING.name();
    //    permission class
    private final String CREATE_CLASS = EPermission.CREATE_CLASS.name();
    private final String READ_CLASS = EPermission.READ_CLASS.name();
    private final String UPDATE_CLASS = EPermission.UPDATE_CLASS.name();
    private final String DELETE_CLASS = EPermission.DELETE_CLASS.name();
    private final String IMPORT_CLASS = EPermission.IMPORT_CLASS.name();


    private final String[] swaggerAntPatterns = {"/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
            "/configuration/security", "/swagger-ui.html", "/webjars/**"};

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(swaggerAntPatterns);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
// login
                .antMatchers(USER_LOGIN, USER_CREATE_PASSWORD, USER_RENEW_PASSWORD).permitAll()
                //permission
                .antMatchers(USER_PERMISSION).hasAnyRole(SUPER, ADMIN, TRAINER)
                .antMatchers(USER_PERMISSION_EDIT).hasAnyRole(SUPER, ADMIN)
// user
                .antMatchers(USER_MANAGER, USER_GET_BY_ID_EMAIL).hasAnyAuthority(READ_USER, UPDATE_USER, CREATE_USER, IMPORT_USER, DELETE_USER)
                .antMatchers(USER_GET_BY_ID_EMAIL).hasAnyAuthority(CREATE_SYLLABUS, CREATE_CLASS, CREATE_SYLLABUS, CREATE_TRAINING)
                .antMatchers(USER_CREATE).hasAnyAuthority(CREATE_USER)
                .antMatchers(USER_UPDATE).hasAuthority(UPDATE_USER)
                .antMatchers(USER_DELETE).hasAuthority(DELETE_USER)
//syllabus
                .antMatchers(SYLLABUS_VIEW, SYLLABUS_ALL_LIST, SYLLABUS_DUPLICATE).hasAnyAuthority(READ_SYLLABUS, CREATE_SYLLABUS, UPDATE_SYLLABUS, CREATE_TRAINING, UPDATE_TRAINING, CREATE_CLASS, UPDATE_CLASS)
                .antMatchers(SYLLABUS_CREATE, SYLLABUS_DUPLICATE).hasAuthority(CREATE_SYLLABUS)
                .antMatchers(SYLLABUS_DELETE).hasAuthority(DELETE_SYLLABUS)
                .antMatchers(SYLLABUS_UPDATE, SYLLABUS_GET_UPDATE).hasAuthority(UPDATE_SYLLABUS)

//training program
                .antMatchers(TRAINING_PROGRAM_ALL_LIST, TRAINING_PROGRAM_BY_TRAININGCODE, TRAINING_PROGRAM_BY_TRAININGCODE_SYLLABUS, TRAINING_PROGRAM_VIEW).hasAnyAuthority(READ_TRAINING, UPDATE_TRAINING, DELETE_TRAINING, CREATE_CLASS)
                .antMatchers(TRAINING_PROGRAM_CREATE, TRAINING_PROGRAM_DUPLICATE).hasAuthority(CREATE_TRAINING)
                .antMatchers(TRAINING_PROGRAM_DELETE).hasAnyAuthority(DELETE_TRAINING)
                .antMatchers(TRAINING_PROGRAM_UPDATE, TRAINING_PROGRAM_STATUS).hasAnyAuthority(UPDATE_TRAINING)
//Class
                .antMatchers(CLASS_VIEW, CLASS_FILTER, CLASS_GET_ALL, CLASS_GET_DETAIL, CLASS_GET_BY_ID).hasAnyAuthority(READ_CLASS, CREATE_CLASS, DELETE_CLASS)
                .antMatchers(CLASS_CREATE).hasAnyAuthority(CREATE_CLASS)
                .antMatchers(CLASS_UPDATE, CLASS_CHANGE_TRAINING, CLASS_CHANGE_SYLLABUS).hasAnyAuthority(UPDATE_CLASS)
                .antMatchers(CLASS_DELETE).hasAnyAuthority(DELETE_CLASS)

                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT)).permitAll()

                .and()
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //complete authorize latter
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
