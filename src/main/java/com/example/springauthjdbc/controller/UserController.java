package com.example.springauthjdbc.controller;

import com.example.springauthjdbc.model.GroupDto;
import com.example.springauthjdbc.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/user")
    public void createUser(@RequestBody UserDto userDto) {
        UserDetails user = User.builder()
                .username(userDto.username())
                .password(encoder.encode(userDto.password()))
                .roles("USER")
                .build();
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.addUserToGroup(userDto.username(), "Users");
    }

    @GetMapping("/admin/groups")
    public List<String> getGroups() {
        return jdbcUserDetailsManager.findAllGroups();
    }

    @GetMapping("/admin/groupusers")
    public List<String> getGroupUsers(@RequestBody GroupDto groupDto) {
        return jdbcUserDetailsManager.findUsersInGroup(groupDto.groupName());
    }

    @PostMapping("/admin/group")
    public void createGroup(@RequestBody GroupDto groupDto) {
        List<GrantedAuthority> authorities;
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + groupDto.authority()));
        jdbcUserDetailsManager.createGroup(groupDto.groupName(), authorities);
    }

    @PutMapping("/admin/group")
    public void renameGroup(@RequestBody GroupDto groupDto) {
        jdbcUserDetailsManager.renameGroup(groupDto.groupName(), groupDto.newGroupName());
    }

    @DeleteMapping("/admin/group")
    public void deleteGroup(@RequestBody GroupDto groupDto) {
        jdbcUserDetailsManager.deleteGroup(groupDto.groupName());
    }

    @PostMapping("/admin/usergroup")
    public void addUserToGroup(@RequestBody UserDto userDto) {
        jdbcUserDetailsManager.addUserToGroup(userDto.username(), userDto.groupName());
    }

    @DeleteMapping("/admin/usergroup")
    public void removeUserFromGroup(@RequestBody UserDto userDto) {
        jdbcUserDetailsManager.removeUserFromGroup(userDto.username(), userDto.groupName());
    }
}
