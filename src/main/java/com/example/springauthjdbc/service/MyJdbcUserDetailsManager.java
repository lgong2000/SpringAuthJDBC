package com.example.springauthjdbc.service;

import com.example.springauthjdbc.model.User;
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

    public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY = "select g.id, g.group_name, ga.authority "
            + "from groups g, group_members gm, group_authorities ga, users u "
            + "where u.username = ? " + "and g.id = ga.group_id " + "and g.id = gm.group_id " + "and gm.user_id = u.id";

    private String createUserSql = DEF_CREATE_USER_SQL;

    private String insertGroupMemberSql = DEF_INSERT_GROUP_MEMBER_SQL;

    private String deleteGroupMemberSql = DEF_DELETE_GROUP_MEMBER_SQL;

    private String findUserIdSql = DEF_FIND_USER_ID_SQL;

    private String findGroupIdSql = DEF_FIND_GROUP_ID_SQL;

    public MyJdbcUserDetailsManager() {
        setEnableGroups(true);
        setEnableAuthorities(false);
        setFindUsersInGroupSql(DEF_FIND_USERS_IN_GROUP_SQL);
        setGroupAuthoritiesByUsernameQuery(DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY);
    }

    public MyJdbcUserDetailsManager(DataSource dataSource) {
        this();
        setDataSource(dataSource);
    }

    public void createUser(final User user) {
        //validateUserDetails(user);
        getJdbcTemplate().update(this.createUserSql, (ps) -> {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstname());
            ps.setString(4, user.getLastname());
            ps.setString(5, user.getEmail());
            ps.setBoolean(6, user.isEnabled());
            ps.setBoolean(7, user.isAccountexpired());
            ps.setBoolean(8, user.isCredentialsexpired());
            ps.setBoolean(9, user.isAccountlocked());
        });
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
