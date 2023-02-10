select * from [Farm$] f inner join
(
select f.*,fu.code as com_code from tbl_farm f inner join tbl_fms_administrative_unit fu on f.administrative_unit_id=fu.id
) as tb1
on f.[Ow_name]=tb1.name and f.[Commune_ID]=tb1.com_code

/*update tbl_farm set map_code=f.Farm_id
from 
[Farm$] f 
inner join
(
select f.*,fu.code as com_code from tbl_farm f inner join tbl_fms_administrative_unit fu on f.administrative_unit_id=fu.id
) as tb1
on f.[Ow_name]=tb1.name and f.[Commune_ID]=tb1.com_code
where tbl_farm.id=tb1.id
*/