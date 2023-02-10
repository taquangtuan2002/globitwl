/****** Object:  Table [dbo].[oauth_access_token]    Script Date: 08/06/2018 15:17:12 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[oauth_access_token](
	[token_id] [varchar](255) NULL,
	[token] [varbinary](max) NULL,
	[authentication_id] [varchar](255) NULL,
	[user_name] [varchar](255) NULL,
	[client_id] [varchar](255) NULL,
	[authentication] [varbinary](max) NULL,
	[refresh_token] [varchar](255) NULL,
	[defaultOAuth2AccessToken] [varbinary](1) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


/****** Object:  Table [dbo].[oauth_approvals]    Script Date: 08/06/2018 15:18:14 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[oauth_approvals](
	[userId] [varchar](256) NULL,
	[clientId] [varchar](256) NULL,
	[scope] [varchar](256) NULL,
	[status] [varchar](10) NULL,
	[expiresAt] [datetime] NULL,
	[lastModifiedAt] [datetime] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO



/****** Object:  Table [dbo].[oauth_client_details]    Script Date: 08/06/2018 15:18:30 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[oauth_client_details](
	[client_id] [varchar](256) NOT NULL,
	[resource_ids] [varchar](256) NULL,
	[client_secret] [varchar](256) NULL,
	[scope] [varchar](256) NULL,
	[authorized_grant_types] [varchar](256) NULL,
	[web_server_redirect_uri] [varchar](256) NULL,
	[authorities] [varchar](256) NULL,
	[access_token_validity] [int] NULL,
	[refresh_token_validity] [int] NULL,
	[additional_information] [varchar](4096) NULL,
	[autoapprove] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[client_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [additional_information]
GO

ALTER TABLE [dbo].[oauth_client_details] ADD  DEFAULT (NULL) FOR [autoapprove]
GO



/****** Object:  Table [dbo].[oauth_code]    Script Date: 08/06/2018 15:18:39 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[oauth_code](
	[code] [varchar](255) NULL,
	[authentication] [varbinary](max) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[oauth_refresh_token]    Script Date: 08/06/2018 15:18:46 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[oauth_refresh_token](
	[token_id] [varchar](256) NULL,
	[token] [varbinary](max) NULL,
	[authentication] [varbinary](max) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


INSERT INTO oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
 
VALUES('wl_client', NULL, 'password', 'read,write,delete', 'password,authorization_code,refresh_token', NULL, NULL, 36000, 36000, NULL, 1);
