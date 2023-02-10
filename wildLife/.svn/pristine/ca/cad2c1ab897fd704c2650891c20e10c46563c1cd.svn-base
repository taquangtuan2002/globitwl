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
           ,[animal_id]
           ,[farm_id],year,type)
     select
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
      ,[tbl_animal_id]
      ,[tbl_farm_id],2015,0
      
from Farm_Animal_2015$ fa inner join tbl_farm f on fa.tbl_farm_id=f.id
GO


