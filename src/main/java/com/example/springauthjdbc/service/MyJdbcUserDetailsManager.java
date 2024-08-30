package com.example.springauthjdbc.service;

import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

public class MyJdbcUserDetailsManager extends JdbcUserDetailsManager {
    public MyJdbcUserDetailsManager() {
        setEnableGroups(true);
        setEnableAuthorities(false);
    }

    public MyJdbcUserDetailsManager(DataSource dataSource) {
        this();
        setDataSource(dataSource);
    }
}
