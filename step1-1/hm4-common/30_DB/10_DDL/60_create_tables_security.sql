-- SpringSecurity
-- RememberMe認証用テーブル ※Springのガイドの通りのDDL
-- 参考：https://spring.pleiades.io/spring-security/site/docs/5.0.19.BUILD-SNAPSHOT/reference/html/remember-me.html#remember-me-persistent-token
CREATE TABLE PERSISTENT_LOGINS
(
	USERNAME	VARCHAR(64) NOT NULL,
	SERIES		VARCHAR(64) PRIMARY KEY,
	TOKEN		VARCHAR(64) NOT NULL,
	LAST_USED	TIMESTAMP NOT NULL
);
