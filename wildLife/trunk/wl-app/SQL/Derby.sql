CREATE TABLE  clientdetails (
  appId varchar(255) PRIMARY KEY NOT NULL,
  resourceIds varchar(255) DEFAULT NULL,
  appSecret varchar(255) DEFAULT NULL,
  scope varchar(255) DEFAULT NULL,
  grantTypes varchar(255) DEFAULT NULL,
  redirectUrl varchar(255) DEFAULT NULL,
  authorities varchar(255) DEFAULT NULL,
  access_token_validity int DEFAULT NULL,
  refresh_token_validity int DEFAULT NULL,
  additionalInformation varchar(4096) DEFAULT NULL,
  autoApproveScopes varchar(255) DEFAULT NULL
  );
  
CREATE TABLE oauth_access_token
(
	token_id varchar(255) ,
	token blob ,
	authentication_id varchar(255) PRIMARY KEY NOT NULL,
	user_name varchar(255) ,
	client_id varchar(255) ,
	authentication blob ,
	refresh_token varchar(255) ,
	defaultOAuth2AccessToken BOOLEAN 
);

CREATE TABLE  oauth_client_details (
  client_id varchar(255)  PRIMARY KEY NOT NULL ,
  resource_ids varchar(255) DEFAULT NULL,
  client_secret varchar(255) DEFAULT NULL,
  scope varchar(255) DEFAULT NULL,
  authorized_grant_types varchar(255) DEFAULT NULL,
  web_server_redirect_uri varchar(255) DEFAULT NULL,
  authorities varchar(255) DEFAULT NULL,
  access_token_validity int DEFAULT NULL,
  refresh_token_validity int DEFAULT NULL,
  additional_information varchar(4096) DEFAULT NULL,
  autoapprove varchar(255) DEFAULT NULL
);

CREATE TABLE  oauth_client_token (
  token_id varchar(255) DEFAULT NULL,
  token blob,
  authentication_id varchar(255) PRIMARY KEY NOT NULL,
  user_name varchar(255) DEFAULT NULL,
  client_id varchar(255) DEFAULT NULL
);

CREATE TABLE oauth_code (
  code varchar(255) DEFAULT NULL,
  authentication blob
);

CREATE TABLE oauth_refresh_token (
  token_id varchar(255) DEFAULT NULL,
  token blob,
  authentication blob
);


INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES
	('education_client', NULL, 'password', 'read,write,delete', 'password,authorization_code,refresh_token', NULL, NULL, 36000, 36000, NULL, '1');
