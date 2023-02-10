 
  
  select fa.Animal_ID 
,an.code,an.old_code
from [farm_animal_2015$] fa inner join tbl_farm f on
fa.Farm_ID=f.map_code
inner join tbl_animal an on an.old_code=fa.Animal_ID

--update [farm_animal_2015$] set tbl_farm_id= f.id
--from [farm_animal_2015$] fa inner join tbl_farm f on
--f.map_code=fa.Farm_ID

--update [farm_animal_2015$] set tbl_animal_id=an.id
--from [farm_animal_2015$] f inner join tbl_animal an on f.Animal_ID=an.old_code

select * from [farm_animal_2015$] where Animal_ID not in 
(select old_code from tbl_animal)


