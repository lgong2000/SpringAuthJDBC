package com.example.springauthjdbc.service;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.util.Assert;

import javax.sql.DataSource;

public class MyJdbcUserDetailsManager extends JdbcUserDetailsManager {
    public static final String DEF_CREATE_USER_SQL = "insert into users (username, password, firstname, lastname, email, enabled, accExpired, credsExpired, accLocked) values (?,?,?,?,?,?,?,?,?)";

    public static final String DEF_FIND_USERS_IN_GROUP_SQL = "select username from group_members gm, groups g, users u "
            + "where gm.user_id = u.id and gm.group_id = g.id and g.group_name = ?";

    public static final String DEF_FIND_USER_ID_SQL = "select id from users where username = ?";

    public static final String DEF_FIND_GROUP_ID_SQL = "select id from groups where group_name = ?";

    public static final String DEF_INSERT_GROUP_MEMBER_SQL = "insert into group_members (group_id, user_id) values (?,?)";

    public static final String DEF_DELETE_GROUP_MEMBER_SQL = "delete from group_members where group_id = ? and user_id = ?";

    private String createUserSql = DEF_CREATE_USER_SQL;

    private String insertGroupMemberSql = DEF_INSERT_GROUP_MEMBER_SQL;

    private String deleteGroupMemberSql = DEF_DELETE_GROUP_MEMBER_SQL;

    private String findUserIdSql = DEF_FIND_USER_ID_SQL;

    private String findGroupIdSql = DEF_FIND_GROUP_ID_SQL;

    public MyJdbcUserDetailsManager() {
        setEnableGroups(true);
        setEnableAuthorities(false);
        setFindUsersInGroupSql(DEF_FIND_USERS_IN_GROUP_SQL);
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

    @Override
    public void addUserToGroup(final String username, final String groupName) {
        this.logger.debug("Adding user '" + username + "' to group '" + groupName + "'");
        Assert.hasText(username, "username should have text");
        Assert.hasText(groupName, "groupName should have text");
        int user_id = findUserId(username);
        int group_id = findGroupId(groupName);
        getJdbcTemplate().update(this.insertGroupMemberSql, (ps) -> {
            ps.setInt(1, group_id);
            ps.setInt(2, user_id);
        });
        //this.userCache.removeUserFromCache(username);
    }

    @Override
    public void removeUserFromGroup(final String username, final String groupName) {
        this.logger.debug("Removing user '" + username + "' to group '" + groupName + "'");
        Assert.hasText(username, "username should have text");
        Assert.hasText(groupName, "groupName should have text");
        int user_id = findUserId(username);
        int group_id = findGroupId(groupName);
        getJdbcTemplate().update(this.deleteGroupMemberSql, (ps) -> {
            ps.setInt(1, group_id);
            ps.setInt(2, user_id);
        });
        //this.userCache.removeUserFromCache(username);
    }

    private int findGroupId(String group) {
        return getJdbcTemplate().queryForObject(this.findGroupIdSql, Integer.class, group);
    }

    private int findUserId(String username) {
        return getJdbcTemplate().queryForObject(this.findUserIdSql, Integer.class, username);
    }
}
