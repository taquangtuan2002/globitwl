select fa.Ow_name,fa.Address,fb.Ow_name
, fa.Com_code_02,fb.Commune_ID from Farm_2015$ fa inner join FAO_Farm$ fb on fa.Ow_name=fb.Ow_name
and fa.Com_code_02=fb.Commune_ID
inner join Farm_Animal_2015$ fan on fa.Farm_ID=fan.Farm_ID
inner join tbl_farm f on f.map_code=fb.[Farm_ID *]


select * from Farm_2015$ fa inner join Farm_Animal_2015$ fan on fa.Farm_ID=fan.Farm_ID
where fa.Ow_name=N'Bùi Anh Dũng'