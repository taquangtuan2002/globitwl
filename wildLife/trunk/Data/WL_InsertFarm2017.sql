INSERT INTO [WL_NEW].[dbo].[tbl_farm]
           ([create_date]
           ,[created_by]
           ,[modified_by]
           ,[modify_date]
           ,[uuid_key]
           ,[adress_detail]           
           ,[latitude]
           ,[longitude]
           ,[name]
           ,[new_registration_code]
			,owner_email
			,media_link
           ,[owner_Adress]
           ,[owner_name]
           ,[phone_number]
			,map_code
           ,[village]
           ,[administrative_unit_id])

     SELECT 
     GETDATE(),
     'admin',
     'admin',
     GETDATE(),
     NEWID(),
      [Address]
      ,CAST([X] AS nvarchar(255))
      ,CAST([Y] AS nvarchar(255))
      ,[Ow_name]
      ,[Regis_ID]
      ,[Email]
      ,[Website]
      ,[Address]
      ,[Ow_name]
      ,[Telephone]
      ,[Farm_ID *]
      ,[Name_village]      
      ,[adminis_id]

  FROM [WL_NEW].[dbo].[FAO_Farm$]
GO


