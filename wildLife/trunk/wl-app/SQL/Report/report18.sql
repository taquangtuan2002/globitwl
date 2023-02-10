SELECT *
FROM
  (SELECT ROW_NUMBER() OVER (PARTITION BY f.farm_id,
                                          f.animal_id
                             ORDER BY f.date_report DESC) AS rowNumber,
                            f.date_report AS dateReport,
                            p.year,
                            p.month,
                            p.date,
                            prov.id AS provId,
                            prov.name AS provName,
                            prov.code AS provCode,
                            dis.id AS disId,
                            dis.name AS disName,
                            dis.code AS disCode,
                            w.id AS wardId,
                            w.name AS wardName,
                            w.code AS wardCode,
                            a.id AS animalId,
                            a.name AS animalName,
                            a.science_name AS animalSciName,
                            a.other_name AS animalOtherName,
                            a.code AS animalCode,
                            a.ordo AS animalOrdo,
                            a.animal_class AS animalClass,
                            a.family AS animalFamily,
                            a.cites,
                            a.vnlist,
                            a.vnlist06,
                            fa.id AS farmId,
                            fa.name AS farmName,
                            fa.code AS farmCode,
                            fa.new_registration_code AS farmNewRegistrationCode,
                            fa.old_registration_code AS farmOldRegistrationCode,
                            fa.date_registration AS farmDateRegistration,
                            fa.longitude AS farmLongitude,
                            fa.latitude AS farmLatitude,
                            fa.village AS farmVillage,
                            f.total AS total,
                            f.male_parent AS maleParent,
                            f.female_parent AS femaleParent,
                            f.male_gilts AS maleGilts,
                            f.female_gilts AS femaleGilts,
                            f.male_child_under_1year_old + f.female_child_under_1year_old + f.child_under_1year_old AS totalChildUnder1YO,
                            f.male_child_over_1year_old AS maleChildOver1YearOld,
                            f.female_child_over_1year_old AS femaleChildOver1YearOld,
                            f.un_gender_child_over_1year_old AS unGenderChildOver1YearOld,
                            fa.founding_date AS farmFoundingDate
   FROM tbl_report_form16 f
   INNER JOIN tbl_report_period p ON f.report_period_id = p.id
   INNER JOIN tbl_animal a ON a.id = f.animal_id
   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id
   AND prov.parent_id IS NULL
   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id
   AND dis.parent_id IS NOT NULL
   INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id
   AND w.parent_id IS NOT NULL
   INNER JOIN tbl_farm fa ON fa.id=f.farm_id and (fa.isDuplicate is null or fa.isDuplicate =0) )AS tb1
WHERE (1=1)
  AND tb1.rowNumber=1
  AND tb1.total<>0