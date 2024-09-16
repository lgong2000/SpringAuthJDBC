package com.example.springauthjdbc.service;

import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

public class MyJdbcUserDetailsManager extends JdbcUserDetailsManager {
    public static final String DEF_CREATE_USER_SQL = "insert into users (username, password, firstname, lastname, email, enabled, accExpired, credsExpired, accLocked) values (?,?,?,?,?,?,?,?,?)";

    private String createUserSql = DEF_CREATE_USER_SQL;

    public MyJdbcUserDetailsManager() {
        setEnableGroups(true);
        setEnableAuthorities(false);
    }

    public MyJdbcUserDetailsManager(DataSource dataSource) {
        this();
        setDataSource(dataSource);
    }

    public void createUser(final MyUserDetails user) {
        //validateUserDetails(user);
        getJdbcTemplate().update(this.createUserSql, (ps) -> {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstname());
            ps.setString(4, user.getLastname());
            ps.setString(5, user.getEmail());
            ps.setBoolean(6, user.isEnabled());
            ps.setBoolean(7, !user.isAccountNonLocked());
            ps.setBoolean(8, !user.isAccountNonExpired());
            ps.setBoolean(9, !user.isCredentialsNonExpired());
        });
//        if (getEnableAuthorities()) {
//            insertUserAuthorities(user);
//        }
    }
}
