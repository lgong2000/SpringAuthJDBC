-- H2 2.1.214;
SET DB_CLOSE_DELAY -1;        
;             
CREATE USER IF NOT EXISTS "SA" SALT '1c746c2a1075d612' HASH '94f57181c23c941ae9ae113d749db591a17b2dc3547e05f2cd8dc52d85595456' ADMIN;         
CREATE MEMORY TABLE "PUBLIC"."USERS"(
    "USERNAME" VARCHAR_IGNORECASE(50) NOT NULL,
    "PASSWORD" VARCHAR_IGNORECASE(500) NOT NULL,
    "ENABLED" BOOLEAN NOT NULL
);  
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4" PRIMARY KEY("USERNAME");  
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.USERS;   
INSERT INTO "PUBLIC"."USERS" VALUES
('admin', '$2a$10$oA3DH1MdcaBQYzFGI7rcHeOYsWw2RE9Zp3II/llS1G714xqjfZT8K', TRUE),
('user', '$2a$10$QQoi5jEzATNTBmHbSfZRQejrzGc9rXT.qASVJDyzSZDaC4j328Yam', TRUE);        
CREATE MEMORY TABLE "PUBLIC"."AUTHORITIES"(
    "USERNAME" VARCHAR_IGNORECASE(50) NOT NULL,
    "AUTHORITY" VARCHAR_IGNORECASE(50) NOT NULL
);             
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.AUTHORITIES;             
CREATE UNIQUE INDEX "PUBLIC"."IX_AUTH_USERNAME" ON "PUBLIC"."AUTHORITIES"("USERNAME" NULLS FIRST, "AUTHORITY" NULLS FIRST);   
CREATE MEMORY TABLE "PUBLIC"."GROUPS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 0 RESTART WITH 2 MINVALUE 0) NOT NULL,
    "GROUP_NAME" VARCHAR_IGNORECASE(50) NOT NULL
);              
ALTER TABLE "PUBLIC"."GROUPS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_7" PRIMARY KEY("ID");       
-- 2 +/- SELECT COUNT(*) FROM PUBLIC."GROUPS";
INSERT INTO "PUBLIC"."GROUPS" VALUES
(0, 'Admins'),
(1, 'Users');           
CREATE MEMORY TABLE "PUBLIC"."GROUP_AUTHORITIES"(
    "GROUP_ID" BIGINT NOT NULL,
    "AUTHORITY" CHARACTER VARYING(50) NOT NULL
);        
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.GROUP_AUTHORITIES;       
INSERT INTO "PUBLIC"."GROUP_AUTHORITIES" VALUES
(0, 'ROLE_ADMIN'),
(1, 'ROLE_USER');        
CREATE MEMORY TABLE "PUBLIC"."GROUP_MEMBERS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 0 RESTART WITH 2 MINVALUE 0) NOT NULL,
    "USERNAME" CHARACTER VARYING(50) NOT NULL,
    "GROUP_ID" BIGINT NOT NULL
);         
ALTER TABLE "PUBLIC"."GROUP_MEMBERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_F" PRIMARY KEY("ID");
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.GROUP_MEMBERS;           
INSERT INTO "PUBLIC"."GROUP_MEMBERS" VALUES
(0, 'user', 1),
(1, 'admin', 0);
ALTER TABLE "PUBLIC"."GROUP_MEMBERS" ADD CONSTRAINT "PUBLIC"."FK_GROUP_MEMBERS_GROUP" FOREIGN KEY("GROUP_ID") REFERENCES "PUBLIC"."GROUPS"("ID") NOCHECK;     
ALTER TABLE "PUBLIC"."GROUP_AUTHORITIES" ADD CONSTRAINT "PUBLIC"."FK_GROUP_AUTHORITIES_GROUP" FOREIGN KEY("GROUP_ID") REFERENCES "PUBLIC"."GROUPS"("ID") NOCHECK;             
ALTER TABLE "PUBLIC"."AUTHORITIES" ADD CONSTRAINT "PUBLIC"."FK_AUTHORITIES_USERS" FOREIGN KEY("USERNAME") REFERENCES "PUBLIC"."USERS"("USERNAME") NOCHECK;    
