select tb1.Ow_name,tb2.Ow_name, tb1.Address,tb2.Address,tb1.Commune_ID,tb2.Com_code_02,tb1.ward,tb2.ward from
(
select f1.Ow_name,fu.name as ward,fu1.name as district,--fu2.name as province ,
fu1.id as districtId,fu.id as wardId,f1.Address,f1.Commune_ID
from FAO_Farm$ f1 inner join tbl_fms_administrative_unit fu
on f1.adminis_id=fu.id 
inner join tbl_fms_administrative_unit fu1 on fu1.id=fu.parent_id
inner join tbl_fms_administrative_unit fu2 on fu2.id = fu1.parent_id
) as tb1 inner join
(
select f1.Ow_name,fu.name as ward,fu1.name as district,--fu2.name as province ,
fu1.id as districtId,fu.id as wardId,f1.Address,f1.Com_code_02
from Farm_2015$ f1 inner join tbl_fms_administrative_unit fu
on f1.adminis_id=fu.id 
inner join tbl_fms_administrative_unit fu1 on fu1.id=fu.parent_id
inner join tbl_fms_administrative_unit fu2 on fu2.id = fu1.parent_id
) as tb2 on tb1.Ow_name=tb2.Ow_name and tb1.districtId=tb2.districtId and tb1.wardId<>tb2.wardId


--select f1.Ow_name,f2.Ow_name,f1.Address,f2.Address from FAO_Farm$ f1 
--inner join Farm_2015$ f2 on f1.Ow_name=f2.Ow_name and f1.adminis_id<>f2.adminis_id
--inner join tbl_fms_administrative_unit fu on fu.id=f2.adminis_id or fu.id=f1.adminis_id