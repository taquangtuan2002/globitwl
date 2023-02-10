select fa.Animal_ID 
,an.code,an.old_code
from [FAO_Farm_Animal$] fa inner join tbl_farm f on
fa.tbl_farm_id=f.id
inner join tbl_animal an on an.old_code=fa.Animal_ID
--where fa.Animal_ID is null

update WL.dbo.[FAO_Farm_Animal$] set tbl_farm_id= f.id
from [FAO_Farm_Animal$] fa inner join tbl_farm f on
f.map_code=fa.Farm_ID

--select * from tbl_farm where administrative_unit_id is null

--select old_code,code,name from tbl_animal where code='B131' or old_code='B131'
--select * from CodeAnimal2019 where AID_19='B131' or AID_15='B131'

--select * from [FAO_Farm_Animal$] f inner join tbl_animal an on f.Animal_ID=an.old_code
--where 
--f.Animal_ID is null 
--or 
--an.old_code is null
update WL.dbo.[FAO_Farm_Animal$] set tbl_animal_id=an.id
from [FAO_Farm_Animal$] f inner join tbl_animal an on f.Animal_ID=an.old_code

