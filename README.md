# SpringAuthJDBC

## Version 1
RestController
SecurityFilterChain
formLogin
InMemoryUserDetailsManager

## Version 2
InMemoryUserDetailsManager - interface UserDetailsManager - JdbcUserDetailsManager - JdbcDaoImpl
    - public static final String DEFAULT_USER_SCHEMA_DDL_LOCATION = "org/springframework/security/core/userdetails/jdbc/users.ddl";
users.ddl - select users.dll(text) - Ctrl+Shift+N - Enter - See the create scripts
pom.xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jdbc</artifactId>
    </dependency>
    <dependency>    
        <groupId>com.h2database</ Id>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
application.properties
    spring.datasource.name=dashboard
    spring.datasource.generate-unique-name=false
    spring.h2.console.enabled=true

H2
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:dashboard
EmbeddedDatabase - run script JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION - Create Tables to H2 Database
Add user
PasswordEncoder - Bcrypt

## Version 3
User group authorities

## Version 4
Custom Login Form

## Version 5
Use JdbcUserDetailsManager to Store the user and group inforamtion
Use Record Class as model
H2 Database Dump:
SCRIPT TO 'dump.sql';
SCRIPT SIMPLE TO 'dump.txt';

# Version 6 
Remove MyUserDetails, no customize is necessary.

Group JSON
{
    "groupName": "Readers",
    "authority": "Read",
    "newGroupName": "ReadersII"
}
User JSON
{
    "username": "test0009",
    "password": "password",
    "firstname": "FirstT9",
    "lastname": "LastT9",
    "email": "test0009@bbb.ccc",
    "groupName": "Readers",
    "newUsername": "test0009II",
}
