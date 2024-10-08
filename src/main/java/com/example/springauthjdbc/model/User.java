package com.example.springauthjdbc.model;

public class User {
    private String password;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private boolean enabled;

    private boolean accountexpired;

    private boolean credentialsexpired;

    private boolean accountlocked;

    public User() {}

    public User(String username, String password,
                String firstname, String lastname, String email) {
        this(username, password, firstname, lastname, email, true, false, false,false);
    }

    public User(String username, String password,
                String firstname, String lastname, String email,
                boolean enabled, boolean accountexpired, boolean credentialsexpired, boolean accountlocked) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.enabled = enabled;
        this.accountexpired = accountexpired;
        this.credentialsexpired = credentialsexpired;
        this.accountlocked = accountlocked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountexpired() {
        return accountexpired;
    }

    public void setAccountexpired(boolean accountexpired) {
        this.accountexpired = accountexpired;
    }

    public boolean isCredentialsexpired() {
        return credentialsexpired;
    }

    public void setCredentialsexpired(boolean credentialsexpired) {
        this.credentialsexpired = credentialsexpired;
    }

    public boolean isAccountlocked() {
        return accountlocked;
    }

    public void setAccountlocked(boolean accountlocked) {
        this.accountlocked = accountlocked;
    }

}
