package com.example.springauthjdbc.controller;

import com.example.springauthjdbc.model.GroupDto;
import com.example.springauthjdbc.model.UserDto;
import com.example.springauthjdbc.service.MyJdbcUserDetailsManager;
import com.example.springauthjdbc.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private MyJdbcUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/user")
    public void createUser(@RequestBody UserDto userDto) {
        MyUserDetails user = new MyUserDetails(userDto.username(),
                encoder.encode(userDto.password()),
                userDto.firstname(),
                userDto.lastname(),
                userDto.email(),
                AuthorityUtils.NO_AUTHORITIES);
        userDetailsManager.createUser(user);
        userDetailsManager.addUserToGroup(userDto.username(), "Users");
    }

    @GetMapping("/admin/groups")
    public List<String> getGroups() {
        return userDetailsManager.findAllGroups();
    }

    @GetMapping("/admin/groupusers")
    public List<String> getGroupUsers(@RequestBody GroupDto groupDto) {
        return userDetailsManager.findUsersInGroup(groupDto.groupName());
    }

    @PostMapping("/admin/group")
    public void createGroup(@RequestBody GroupDto groupDto) {
        List<GrantedAuthority> authorities;
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + groupDto.authority()));
        userDetailsManager.createGroup(groupDto.groupName(), authorities);
    }

    @PutMapping("/admin/group")
    public void renameGroup(@RequestBody GroupDto groupDto) {
        userDetailsManager.renameGroup(groupDto.groupName(), groupDto.newGroupName());
    }

    @DeleteMapping("/admin/group")
    public void deleteGroup(@RequestBody GroupDto groupDto) {
        userDetailsManager.deleteGroup(groupDto.groupName());
    }

    @PostMapping("/admin/usergroup")
    public void addUserToGroup(@RequestBody UserDto userDto) {
        userDetailsManager.addUserToGroup(userDto.username(), userDto.groupName());
    }

    @DeleteMapping("/admin/usergroup")
    public void removeUserFromGroup(@RequestBody UserDto userDto) {
        userDetailsManager.removeUserFromGroup(userDto.username(), userDto.groupName());
    }
}
