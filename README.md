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

## Version
User group authorities