INSERT INTO [WL_NEW].[dbo].[tbl_animal]
           ([create_date]
           ,[created_by]
           ,[modified_by]
           ,[modify_date]
           ,[uuid_key]
           
           ,[ani_group]
           ,[animal_class]
           ,[animal_class_sci]
           ,[cites]
           ,[code]
           
           ,[en_name]
           ,[family]
           ,[name]
           ,[old_code]
           ,[ordo]
           ,[ordo_sci]
           ,[science_name]
           ,[sub_ordo]
           ,[vnlist]
           ,[vnlist06])
     SELECT GETDATE(),'admin','admin',GETDATE(),NEWID(),[Ani_group],[F12],[F13],[Cites],[AID_19],[Name_EN],[Family] 
     ,[Name_VN]
     ,[AID_15]
     ,[F14]
     ,[F15]
     ,[Name_Scien]
      ,[Oder]
      ,[Vnlist]
     
      
      ,[VNlist-06]
      
      
      
  FROM [WL_NEW].[dbo].[CodeAnimal2019]
GO


