INSERT INTO [tbl_animal_report_data]
           ([create_date]
           ,[created_by]
           ,[modified_by]
           ,[modify_date]
           ,[uuid_key]


           ,[female]
           ,[male]

           ,[purpose]

           ,[registration_date]
           ,[source]
           ,[total]

           ,[un_gender]
           ,[year]
           ,[animal_id]
           ,[farm_id],type)
     SELECT 
     GETDATE()
,'admin'
,'admin'
,GETDATE()
,NEWID()


      ,[Female]
      ,[Male]

      ,[Purpose]

      ,[Registration_date]
      ,[Source]
      ,[Total_head]

      ,[Gender_un]
		,2017
      ,[tbl_animal_id]
      ,[tbl_farm_id],0

  FROM [FAO_Farm_Animal$]
GO


