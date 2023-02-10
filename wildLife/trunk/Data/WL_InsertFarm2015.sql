INSERT INTO [WL_NEW].[dbo].[tbl_farm]
           ([create_date]
           ,[created_by]
           ,[modified_by]
           ,[modify_date]
           ,[uuid_key]
           ,[adress_detail]
,[old_code]
           ,[latitude]
           ,[longitude]
           ,[map_code]
           ,[media_link]
           ,[name]
           ,[owner_Adress]
           ,[owner_name]
           ,[owner_phone_number]
           ,[phone_number]
           ,[village]
           ,[administrative_unit_id]
,[business_registration_number]
,[business_registration_datee],type
)
     SELECT GETDATE(),
     'admin',
     'admin',
     GETDATE(),
     NEWID(),
[Address]
      ,[FID_Farm_2015]
      ,[Y]
      ,[X]
      ,[Farm_ID]
      ,[Website]
      ,[Ow_name]
,[Address]
      ,[Ow_name]
      ,[Telephone]
      ,[Telephone]
      ,[Name_village]
,[adminis_id]
      ,[Regis_]
      ,[Date_regis],0

  FROM [WL_NEW].[dbo].[Farm_2015$]
  where Farm_ID not in (
  select f2.Farm_ID from FAO_Farm$ f1 inner join Farm_2015$ f2
	on f1.Ow_name=f2.Ow_name and f1.adminis_id=f2.adminis_id)
GO


