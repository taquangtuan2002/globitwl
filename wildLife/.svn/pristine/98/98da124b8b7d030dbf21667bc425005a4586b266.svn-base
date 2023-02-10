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
      ,f.id,2015,0
      
from Farm_2015$ fa inner join FAO_Farm$ fb on fa.Ow_name=fb.Ow_name
and fa.Com_code_02=fb.Commune_ID
inner join Farm_Animal_2015$ fan on fa.Farm_ID=fan.Farm_ID
inner join tbl_farm f on f.map_code=fb.[Farm_ID *]
where [tbl_animal_id] is not null and tbl_farm_id is null
GO


