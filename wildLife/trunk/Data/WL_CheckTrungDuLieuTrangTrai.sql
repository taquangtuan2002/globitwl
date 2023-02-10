select tb1.id,tb2.id,tb1.name,tb2.name, tb1.adress_detail,tb2.adress_detail,tb1.ward,tb2.ward,tb1.district,tb2.district from
(
select f1.id,f1.name,fu.name as ward,fu1.name as district,--fu2.name as province ,
fu1.id as districtId,fu.id as wardId,f1.adress_detail
from tbl_farm f1 inner join tbl_fms_administrative_unit fu
on f1.administrative_unit_id=fu.id 
inner join tbl_fms_administrative_unit fu1 on fu1.id=fu.parent_id
inner join tbl_fms_administrative_unit fu2 on fu2.id = fu1.parent_id
where f1.year_registration=2015
) as tb1 inner join
(
select f1.id,f1.name,fu.name as ward,fu1.name as district,--fu2.name as province ,
fu1.id as districtId,fu.id as wardId,f1.adress_detail
from tbl_farm f1 inner join tbl_fms_administrative_unit fu
on f1.administrative_unit_id=fu.id 
inner join tbl_fms_administrative_unit fu1 on fu1.id=fu.parent_id
inner join tbl_fms_administrative_unit fu2 on fu2.id = fu1.parent_id
where f1.year_registration=2017
) as tb2 on tb1.name=tb2.name and tb1.districtId=tb2.districtId and tb1.wardId<>tb2.wardId and tb1.id<>tb2.id

--inner join tbl_animal_report_data an on an.farm_id=tb2.id

order by tb1.district,tb2.district,tb1.name,tb2.name

--update tbl_animal_report_data set farm_id =tb1.id

--from
--(
--select f1.id,f1.name,fu.name as ward,fu1.name as district,--fu2.name as province ,
--fu1.id as districtId,fu.id as wardId,f1.adress_detail
--from tbl_farm f1 inner join tbl_fms_administrative_unit fu
--on f1.administrative_unit_id=fu.id 
--inner join tbl_fms_administrative_unit fu1 on fu1.id=fu.parent_id
--inner join tbl_fms_administrative_unit fu2 on fu2.id = fu1.parent_id
--where f1.year_registration=2015
--) as tb1 inner join
--(
--select f1.id,f1.name,fu.name as ward,fu1.name as district,--fu2.name as province ,
--fu1.id as districtId,fu.id as wardId,f1.adress_detail
--from tbl_farm f1 inner join tbl_fms_administrative_unit fu
--on f1.administrative_unit_id=fu.id 
--inner join tbl_fms_administrative_unit fu1 on fu1.id=fu.parent_id
--inner join tbl_fms_administrative_unit fu2 on fu2.id = fu1.parent_id
--where f1.year_registration=2017
--) as tb2 on tb1.name=tb2.name and tb1.districtId=tb2.districtId and tb1.wardId<>tb2.wardId and tb1.id<>tb2.id

--inner join tbl_animal_report_data an on an.farm_id=tb2.id
