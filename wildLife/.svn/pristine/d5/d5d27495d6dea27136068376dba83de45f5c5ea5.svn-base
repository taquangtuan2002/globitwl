(function() {
    'use strict';

    angular.module('Education.Farm').service('FarmService', FarmService);

    FarmService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$rootScope'
    ];

    function FarmService($http, $q, $filter, settings, utils, $rootScope) {
        var self = this;
        var Url = settings.api.baseUrl + settings.api.apiPrefix;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'farm/';
        self.getFarm = getFarm;
        self.getPageFarm = getPageFarm;
        self.saveFarm = saveFarm;
        self.createFarm = createFarm;
        self.deleteFarmById = deleteFarmById;
        self.getTableDefinition4Files = getTableDefinition4Files;
        self.getFileById = getFileById;
        self.getAdminstrativeUnitsCity = getAdminstrativeUnitsCity;
        self.getAllByParentId = getAllByParentId;

        self.getAll = getAll;
        self.removeListByParent = removeListByParent;
        self.getPageSearchFarm = getPageSearchFarm;
        self.getPageSearchFarmNonePage = getPageSearchFarmNonePage;
        self.autoGenericCode = autoGenericCode;
        self.getStream = getStream;
        //tran huu dat start bien
        self.getStreamDocument = getStreamDocument;
        //tran huu dat end bien
        self.countFarmByAdministrativeUnit = countFarmByAdministrativeUnit;
        self.countFarmByListAdministrativeUnit = countFarmByListAdministrativeUnit;
        self.countFarmListAdministrativeUnitByMapCode = countFarmListAdministrativeUnitByMapCode;
        self.countFarmListAdministrativeUnitById = countFarmListAdministrativeUnitById;
        self.getAdministrativeUnitDtoByLoginUser = getAdministrativeUnitDtoByLoginUser;
        self.getCurrentUser = getCurrentUser;
        self.countDensityMapCodeAdministrativeUnit = countDensityMapCodeAdministrativeUnit;
        self.countDensityListRegion = countDensityListRegion;
        self.getAllCertificate = getAllCertificate;
        self.getStreamExportDashboard = getStreamExportDashboard;
        self.getAnimalReport = getAnimalReport;
        self.mergeFarm = mergeFarm;
        self.saveListAnimalReport = saveListAnimalReport;
        self.saveListBearReport = saveListBearReport;
        self.saveNormalAnimal = saveNormalAnimal;
        self.saveAnimalReportData = saveAnimalReportData;
        self.farmReportPeriodSearchList = farmReportPeriodSearchList;
        self.getFarmPeriodByIdAndType = getFarmPeriodByIdAndType;
        self.updateAnimalReportData = updateAnimalReportData;
        self.getListReport16FormDto = getListReport16FormDto;
        self.saveReportFormAnimalEgg = saveReportFormAnimalEgg;
        self.saveReportFormAnimalEggList = saveReportFormAnimalEggList;
        self.saveReportFormAnimalGiveBirthList = saveReportFormAnimalGiveBirthList;
        self.getAllReportFormAnimalEgg = getAllReportFormAnimalEgg;
        self.getPageReportFormAnimalEgg = getPageReportFormAnimalEgg;
        self.deleteReportFormAnimalEgg = deleteReportFormAnimalEgg;
        self.getReportFormAnimalEggById = getReportFormAnimalEggById;
        self.getPageReportFormAnimalGiveBirth = getPageReportFormAnimalGiveBirth;
        self.deletePageReportFormAnimalGiveBirthById = deletePageReportFormAnimalGiveBirthById;
        self.saveReportFormAnimalGiveBirth = saveReportFormAnimalGiveBirth;
        self.getReportFormAnimalGiveBirthById = getReportFormAnimalGiveBirthById;
        self.getNumberOfChildrenInTheCommunityOverTime = getNumberOfChildrenInTheCommunityOverTime;
        self.getTableDefinitionForestProductList = getTableDefinitionForestProductList;
        self.getTableDefinitionCodeForestProductList = getTableDefinitionCodeForestProductList;
        self.getForestProductListById = getForestProductListById;
        self.saveForestProductList = saveForestProductList;
        self.getPageForestProductList = getPageForestProductList;
        self.deletePageForestProductList = deletePageForestProductList;
        self.getPageSearchImportAnimal = getPageSearchImportAnimal;
        self.getFarmSummary = getFarmSummary;
        self.quantityChildIncrement = quantityChildIncrement;
        self.giveBirthQuantityChildIncrement = giveBirthQuantityChildIncrement;

        self.reportFormAnimalEggGetByPage = reportFormAnimalEggGetByPage;
        self.reportFormAnimalEggGetById = reportFormAnimalEggGetById;
        self.reportFormAnimalEggGetSaveOrUpdate = reportFormAnimalEggGetSaveOrUpdate;
        self.reportFormAnimalEggPeriodDeleteById = reportFormAnimalEggPeriodDeleteById;
        self.getPageByFarmId = getPageByFarmId;

        self.reportFormAnimalGiveBirthPeriodGetByPage = reportFormAnimalGiveBirthPeriodGetByPage;
        self.reportFormAnimalGiveBirthPeriodSaveOrUpdate = reportFormAnimalGiveBirthPeriodSaveOrUpdate;
        self.reportFormAnimalGiveBirthPeriodDeleteById = reportFormAnimalGiveBirthPeriodDeleteById;
        self.reportFormAnimalGiveBirthPeriodGetById = reportFormAnimalGiveBirthPeriodGetById;
        self.checkDuplicateYearMonthDateReport16C = checkDuplicateYearMonthDateReport16C;
        self.checkDuplicateYearMonthDateReport16D = checkDuplicateYearMonthDateReport16D;
        self.getReportPeriod = getReportPeriod;
        self.updateAllFarmtoMap = updateAllFarmtoMap;
        self.getOneTemplate16BysearchDto = getOneTemplate16BysearchDto;
        self.deleteDataMapByAdministrativeUnit = deleteDataMapByAdministrativeUnit;
        self.getAllForm16ByFarm = getAllForm16ByFarm;
        self.mergeReport16 = mergeReport16;
        self.mergeReportPeriod = mergeReportPeriod;
        self.getUserAdminstrativeEditTableUnit = getUserAdminstrativeEditTableUnit;

        //tran huu dat them ham lay danh sach nguon goc cua bang ke lam san
        self.getAllOriginal = getAllOriginal;
        self.mergeFarmNew = mergeFarmNew;
        self.getAllDistrict = getAllDistrict;
        self.sendEmail = sendEmail;
        self.getChildrenByParentId = getChildrenByParentId;
        self.getAllAnimalLastReported = getAllAnimalLastReported;
        //tran huu dat them ham lay danh sach nguon goc cua bang ke lam san

        // tran sinh thanh
        self.getListAnimalFromReportForm16BySearch = getListAnimalFromReportForm16BySearch;

        function getListAnimalFromReportForm16BySearch(dto, successCallback, errorCallback) {
            var url = Url + 'reportForm16/getListAnimalFromReportForm16BySearch';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        // tran sinh thanh

        //getForm16ById
        function getAllForm16ByFarm(farmID) {
            var url = Url + 'reportForm16//getbyfarm/' + farmID;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteDataMapByAdministrativeUnit(dto) {
            if (dto != null) {
                var url = Url + 'animal_report/deleteDataMapByAdministrativeUnit';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);
            }
        }

        function getOneTemplate16BysearchDto(dto) {
            if (dto != null) {
                dto.pageIndex = 1;
                dto.pageSize = 1;
                var url = Url + 'reportForm16GetParent/getReport16BySearchDto';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }

        function updateAllFarmtoMap(pageIndex, pageSize, year, dto) {
            if (dto != null) {
                if (dto.yearRegistration != null) {
                    dto.yearRegistration = null;
                }
                var url = Url + 'animal_report/updateAllFarmtoMap' + '/' + pageIndex + '/' + pageSize + '/' + year;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }

        function getReportPeriod(id) {
            var url = Url + '/reportPeriod/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function checkDuplicateYearMonthDateReport16C(dto) {
            if (dto != null) {
                var url = Url + 'reportformanimaleggperiod/checkDuplicateYearMonthDateReport16C';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }

        function checkDuplicateYearMonthDateReport16D(dto) {
            if (dto != null) {
                var url = Url + 'reportformanimalgivebirthperiod/checkDuplicateYearMonthDateReport16D';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }

        function reportFormAnimalGiveBirthPeriodGetByPage(dto, pageIndex, pageSize) {
            if (dto != null) {
                var url = Url + 'reportformanimalgivebirthperiod/getpage/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }

        function reportFormAnimalGiveBirthPeriodSaveOrUpdate(dto) {
            if (dto != null) {
                var url = Url + 'reportformanimalgivebirthperiod';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }

        function reportFormAnimalGiveBirthPeriodDeleteById(id) {
            var url = Url + 'reportformanimalgivebirthperiod/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function reportFormAnimalGiveBirthPeriodGetById(id) {
            var url = Url + 'reportformanimalgivebirthperiod/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function reportFormAnimalEggGetByPage(dto, pageIndex, pageSize) {
            if (dto != null) {
                var url = Url + 'reportformanimaleggperiod/getpage/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }

        function reportFormAnimalEggGetSaveOrUpdate(dto) {
            if (dto != null) {
                var url = Url + 'reportformanimaleggperiod';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }

        function reportFormAnimalEggPeriodDeleteById(id) {
            var url = Url + 'reportformanimaleggperiod/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function reportFormAnimalEggGetById(id) {
            var url = Url + 'reportformanimaleggperiod/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getPageSearchImportAnimal(dto, pageIndex, pageSize) {
            if (dto != null) {
                // dto.isImportExportAnimalSeed = true;
                var url = settings.api.baseUrl + settings.api.apiPrefix + 'import_export/animal/' + 'searchDto/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }

        function getPageForestProductList(dto, pageIndex, pageSize) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist/searchByPage/' + pageIndex + '/' + pageSize;

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getForestProductListById(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function saveForestProductList(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function deletePageForestProductList(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist/' + id;

            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getPageReportFormAnimalGiveBirth(dto, pageIndex, pageSize) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportanimalgivebirth/getpage/' + pageIndex + '/' + pageSize;
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function deletePageReportFormAnimalGiveBirthById(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportanimalgivebirth/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function saveReportFormAnimalGiveBirth(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportanimalgivebirth';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getReportFormAnimalGiveBirthById(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportanimalgivebirth/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getNumberOfChildrenInTheCommunityOverTime(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportanimalgivebirth/getNumberOfChildrenInTheCommunityOverTime/';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getListReport16FormDto(farmId) {
            var url = Url + 'import_export/animal/getListReport16FormDto/0/0/' + farmId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAnimalReport(farmId) {
            var url = Url + 'animal_report/getByFarmId/' + farmId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getPageByFarmId(farmId, pageIndex, pageSize) {
            var url = Url + 'animal_report/getPageByFarmId/' + farmId + '/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getReportFormAnimalEggById(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportformanimalegg/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getStreamExportDashboard(dto) {
            console.log("RUNNING");
            var deferred = $q.defer();

            $http({
                    url: settings.api.baseUrl + settings.api.apiPrefix + 'file/exportDashboardView',
                    method: "POST", //you can use also GET or POST
                    data: dto,
                    headers: { 'Content-type': 'application/json' },
                    responseType: 'arraybuffer', //THIS IS IMPORTANT
                })
                .success(function(data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function(data) {
                    console.error("ERROR");
                    deferred.reject(data);
                });

            return deferred.promise;
        };

        function getAllAnimalLastReported(farmId) {
            var url = Url + 'animal_report/getAllAnimalLastReported/' + farmId;
            return utils.resolveAlt(url, 'POST', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }


        function getAllCertificate(pageIndex, pageSize) {
            var url = Url + 'certificate/page/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getCurrentUser() {
            var url = Url + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAdministrativeUnitDtoByLoginUser() {
            var url = Url + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAdminstrativeUnitsCity() {
            var url = Url + 'administrative/getall/city';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllByParentId(parentId) {
            var url = Url + 'administrative/getAllByParentId/' + parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getChildrenByParentId(parentId) {
            var url = Url + 'administrative/getChildrenByParentId/' + parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAll() {
            var url = baseUrl + 'getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getFileById(id) {
            console.log("RUNNING");
            var deferred = $q.defer();

            $http({
                    url: baseUrl + 'file/' + id,
                    method: "GET", //you can use also GET or POST
                    // data:list,
                    headers: { 'Content-type': 'application/json' },
                    responseType: 'arraybuffer', //THIS IS IMPORTANT
                })
                .success(function(data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function(data) {
                    console.error("ERROR");
                    deferred.reject(data);
                });
            return deferred.promise;
            //            var url = baseUrl +'file/'+ id;
            //            return $http.get(url, { responseType: 'arraybuffer' });
        }

        function mergeFarm(farmId, duplicateFarmId) {
            var url = baseUrl + 'mergeFarm/' + farmId + '/' + duplicateFarmId;

            return utils.resolveAlt(url, 'POST', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function mergeReport16(listForm16Delete) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportForm16/delete/list';
            return utils.resolveAlt(url, 'POST', null, listForm16Delete, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function mergeReportPeriod(list16, farmIdMain, duplicateFarmId) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportForm16/mergePeriod/' + farmIdMain + '/' + duplicateFarmId;
            return utils.resolveAlt(url, 'POST', null, list16, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        //tran huu dat them hàm mergefarm new start
        function mergeFarmNew(farmId, duplicateFarmId, farmMerge) {
            var url = baseUrl + 'mergeFarmNew/' + farmId + '/' + duplicateFarmId;

            return utils.resolveAlt(url, 'POST', null, farmMerge, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        //tran huu dat them hàm mergefarm new end

        function getAllReportFormAnimalEgg(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportformanimalegg/getall';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getPageReportFormAnimalEgg(dto, pageIndex, pageSize) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportformanimalegg/getpage/' + pageIndex + '/' + pageSize;
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function deleteReportFormAnimalEgg(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportformanimalegg/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function deleteFarmById(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function saveReportFormAnimalEgg(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportformanimalegg';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function saveReportFormAnimalEggList(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportformanimalegg/saveList';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function saveReportFormAnimalGiveBirthList(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportanimalgivebirth/saveList';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }


        function getFarm(id) {
            var url = baseUrl + 'get/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getFarmSummary(id) {
            var url = baseUrl + 'getSummary/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function createFarm(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function saveFarm(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getPageFarm(pageIndex, pageSize) {
            var url = baseUrl + 'page/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function getAllOriginal() {
            var url = Url + 'original/getalldtos';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function updateAnimalReportData(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'farmReportPeriod/updateAnimalReportData';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function saveListAnimalReport(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'animal_report/saveListAnimalReport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function saveListBearReport(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'animal_report/saveListBearReport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getFarmPeriodByIdAndType(id, type) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'farmReportPeriod/getById/' + id + '/' + type;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function farmReportPeriodSearchList(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'farmReportPeriod/searchList';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function saveAnimalReportData(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'farmReportPeriod/saveOrUpdate';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function saveNormalAnimal(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'animal_report/saveNormalAnimal';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function removeListByParent(id, list, successCallback, errorCallback) {
            var url = baseUrl + 'removeListByParent/' + id;

            return utils.resolveAlt(url, 'POST', null, list, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getPageSearchFarm(searchFarmDto, pageIndex, pageSize, successCallback, errorCallback) {
            if (searchFarmDto != null) {

                var url = baseUrl + 'searchDto/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, searchFarmDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

        function getPageSearchFarmNonePage(searchFarmDto, successCallback, errorCallback) {
            if (searchFarmDto != null) {

                var url = baseUrl + 'searchDto';
                return utils.resolveAlt(url, 'POST', null, searchFarmDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

        function autoGenericCode(codeDistrict, id, codeCity) {
            var url = baseUrl + 'autoGenericCode/' + codeDistrict + '/' + id + '/' + codeCity;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function countFarmByAdministrativeUnit(id, level) {
            var url = baseUrl + 'countFarmByAu/' + id + '/' + level;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function countFarmByListAdministrativeUnit(ids, level, successCallback, errorCallback) {
            var url = baseUrl + 'countFarmByListAu/' + level;

            return utils.resolveAlt(url, 'POST', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function countFarmListAdministrativeUnitByMapCode(mapCode, level) {
            var url = baseUrl + 'countFarmAuByMapCode/' + mapCode + '/' + level;

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function countFarmListAdministrativeUnitById(id, level) {
            var url = baseUrl + 'countFarmByAuId/' + id + '/' + level;

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function countDensityMapCodeAdministrativeUnit(mapCode) {
            var url = baseUrl + 'countDensityMapCodeAdministrativeUnit/' + mapCode;

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function countDensityListRegion() {
            var url = baseUrl + 'countDensityListRegion';

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getUserAdminstrativeEditTableUnit(adminUnitId) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'AdministrativeUnitEditable/getAdministrativeUnitEditableByAdminUnit/' + adminUnitId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getStream(searchFarmDto) {
            console.log("RUNNING");
            var deferred = $q.defer();

            $http({
                    url: settings.api.baseUrl + settings.api.apiPrefix + 'file/downloadFarm',
                    method: "POST", //you can use also GET or POST
                    data: searchFarmDto,
                    headers: { 'Content-type': 'application/json' },
                    responseType: 'arraybuffer', //THIS IS IMPORTANT
                })
                .success(function(data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function(data) {
                    console.error("ERROR");
                    deferred.reject(data);
                });

            return deferred.promise;
        };

        //tran huu dat tạo hàm xuất file word start
        function getStreamDocument(object) {
            console.log("RUNNING");
            var deferred = $q.defer();
            $http({
                    url: settings.api.baseUrl + settings.api.apiPrefix + 'file/downloadForestList',
                    method: "POST", //you can use also GET or POST
                    data: object,
                    headers: { 'Content-type': 'application/json' },
                    responseType: 'arraybuffer', //THIS IS IMPORTANT
                })
                .success(function(data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function(data) {
                    console.error("ERROR");
                    deferred.reject(data);
                });

            return deferred.promise;
        };
        //tran huu dat tạo hàm xuất file word end
        function quantityChildIncrement(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportformanimalegg/quantityChildIncrement';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function giveBirthQuantityChildIncrement(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportanimalgivebirth/giveBirthQuantityChildIncrement';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getAllDistrict(level) {
            var url = Url + 'administrative/getallbylevel/' + level;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function sendEmail(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist/sendEmail';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        // function getSpeciality() {
        //     var url = baseUrl + 'speciality/1/1000';
        //     return utils.resolve(url, 'GET', angular.noop, angular.noop);
        // }


        function getTableDefinition4Files() {
            var _tableOperation = function(value, row, index) {
                var ret = '';
                ret += '<a class="text-danger margin-right-10" uib-tooltip="Xóa tệp tin" tooltip-trigger="mouseenter" ng-show="!$parent.isView" data-ng-click="$parent.deleteDocument(' + "'" + index + "'" + ')"><i class="icon-trash"></i></a>';
                ret += '<a class="green-dark" uib-tooltip="Tải về" tooltip-trigger="mouseenter" ng-show="!$parent.isView" data-ng-click="$parent.downloadDocument(' + "'" + index + "'" + ')"><i class="icon-cloud-download"></i></a>';

                return ret;
            };

            var _fileSize = function(value, row, index) {
                return $filter('fileSize')(value);
            };

            var _fileType = function(value, row, index) {
                return $filter('contentType')(value);
            };

            var _cellNowrap = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _operationColStyle = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '90px', 'text-align': 'center' }
                };
            };

            return [{
                field: '',
                title: '{{"farm.action" | translate}}',
                switchable: true,
                visible: true,
                formatter: _tableOperation,
                cellStyle: _operationColStyle
            }, {
                field: 'file.name',
                title: '{{"farm.nameFile" | translate}}',
                sortable: true,
                switchable: false,
                cellStyle: _cellNowrap
            }, {
                field: 'file.contentSize',
                title: '{{"farm.contentSize" | translate}}',
                sortable: true,
                switchable: false,
                cellStyle: _cellNowrap,
                formatter: _fileSize
            }]
        }

        function getTableDefinitionForestProductList() {

            var _tableOperation = function(value, row, index) {
                return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.addForestProductList(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    +
                    '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5 text-danger" href="#" data-ng-click="$parent.vm.confirmDeleteForestProductList(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>' +
                    '<a href="#" class="btn btn-default margin-left-10" file-name="' + "'BangKeLamSan.doc'" + '" ng-click="$parent.vm.getFileWord(' + "'" + row.id + "'" + ')" file-download="$parent.vm.myBlobObjectWord"><i class="fa fa-file-excel-o"> Xuất dữ liệu word</i></a>';
            };
            var _tableInput = function(value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            //tran huu dat start
            var _imageFormat = function(value, row, index, field) {
                    if (value && value.id) {
                        return "<img ng-src='" + settings.api.baseUrl + "public/publicAPI/downloadArticleImg/" + value.id + "' ngf-accept=''image/*'' style='height:100px;'>";
                    }
                    return "";
                }
                //tran huu dat end

            var _tableIndex = function(value, row, index, field) {
                return index + 1;

            };

            var _cellNowrap = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _cellNowrapTextRight = function(value, row, index, field) {
                return {
                    classes: 'text-right',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _formatterName = function(value, row, index, field) {
                if (value && value.name) {
                    return value.name;
                }
                return '';
            }
            var _numberFormatter = function(value, row, index) {
                if (!value) {
                    return '';
                }
                return $filter('number')(value, 0);
            };
            var _dateFormatter = function(value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _operationColStyle = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };
            var _operationColStyleTableIndex = function(value, row, index, field) {
                return {
                    classes: 'text-right'
                };
            };
            var _createDateFormatter = function(value, row, index) {

                if (!value) {
                    return '';
                } else {
                    var year = value[0];
                    var month = value[1];
                    var date = value[2];
                    var hour = value[3];
                    var minutes = value[4];
                    var seconds = value[5];
                    return date + '/' + month + '/' + year + ' | ' + hour + ':' + minutes + ':' + seconds;
                }

            };

            return [
                // {
                //     field: 'state',
                //     checkbox: true
                // }, 
                {
                    field: '',
                    title: 'TT',
                    switchable: true,
                    visible: true,
                    formatter: _tableIndex,
                    cellStyle: _operationColStyleTableIndex
                },
                {
                    field: '',
                    title: '{{"importAnimal.operating" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                }, {
                    field: 'code',
                    title: 'Số hiệu bản kê',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    // formatter:_dateFormatter,
                    switchable: true
                }, {
                    field: 'dateIssue',
                    title: 'Ngày khai báo',
                    sortable: true,
                    cellStyle: _cellNowrapTextRight,
                    formatter: _dateFormatter,
                    switchable: true
                }, {
                    field: 'original',
                    title: 'Nguồn gốc lâm sản',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }, {
                    field: 'invoiceCode',
                    title: 'Mã hóa đơn kèm theo',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }, {
                    field: 'vehicle',
                    title: 'Phương tiện vận chuyển',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }, {
                    field: 'invoiceDate',
                    title: 'Ngày hóa đơn kèm theo(Nếu có)',
                    sortable: true,
                    cellStyle: _cellNowrapTextRight,
                    formatter: _dateFormatter,
                    switchable: true
                }, {
                    field: 'vehicleRegistrationPlate',
                    title: 'Biển số/Số hiệu phương tiện',
                    sortable: true,
                    cellStyle: _cellNowrapTextRight,
                    formatter: _dateFormatter,
                    switchable: true
                }, {
                    field: 'transportDuration',
                    title: 'Thời gian vận chuyển',
                    sortable: true,
                    cellStyle: _cellNowrapTextRight,
                    // formatter:_dateFormatter,
                    switchable: true
                }, {
                    field: 'transportStart',
                    title: 'Ngày bắt đầu vận chuyển',
                    sortable: true,
                    cellStyle: _cellNowrapTextRight,
                    formatter: _dateFormatter,
                    switchable: true
                }, {
                    field: 'transportEnd',
                    title: 'Ngày kết thúc vận chuyển',
                    sortable: true,
                    cellStyle: _cellNowrapTextRight,
                    formatter: _dateFormatter,
                    switchable: true
                }, {
                    //tran huu dat đổi biến cột administrativeUnitFrom
                    //field: 'transportFrom',
                    field: 'administrativeUnitFrom',
                    title: 'Vận chuyển từ',
                    sortable: true,
                    formatter: _formatterName,
                    cellStyle: _cellNowrap,
                    // formatter:_dateFormatter,
                    switchable: true
                }, {
                    //tran huu dat đổi biến cột administrativeUnitTo
                    field: 'administrativeUnitTo',
                    //field: 'transportTo',
                    title: 'Vận chuyển đến',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName,
                    // formatter:_dateFormatter,
                    switchable: true
                }, {
                    field: 'buyerName',
                    title: 'Người mua',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }, {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName,
                    switchable: true,
                }, {
                    field: 'dateImport',
                    title: '{{"importAnimal.dateImport" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrapTextRight,
                    formatter: _dateFormatter,
                    switchable: true
                },
                //tran huu dat start them cot image
                {
                    field: 'file',
                    title: 'ảnh',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _imageFormat,
                    switchable: true
                },
                //tran huu dat end
            ];
        };

        function getTableDefinitionCodeForestProductList() {

            var _tableOperation = function(value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editImportAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    +
                    '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteImportAnimal(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>';
            };
            var _tableInput = function(value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            var _cellNowrap = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _formatterName = function(value, row, index, field) {
                if (value && value.name) {
                    return value.name;
                }
                return '';
            }
            var _dateFormatter = function(value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };


            var _operationColStyle = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };
            var _numberFormatter = function(value, row, index) {
                if (!value) {
                    return '';
                }
                return $filter('number')(value, 0);
            };
            var _createDateFormatter = function(value, row, index) {
                if (!value) {
                    return '';
                } else {
                    var year = value[0];
                    var month = value[1];
                    var date = value[2];
                    var hour = value[3];
                    var minutes = value[4];
                    var seconds = value[5];
                    return date + '/' + month + '/' + year + ' | ' + hour + ':' + minutes + ':' + seconds;
                }

            };
            return [{
                    field: 'state',
                    checkbox: true,
                }

                , {
                    field: 'batchCode',
                    title: '{{"importAnimal.batchCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,

                }, {
                    field: 'animal',
                    title: '{{"importAnimal.animal.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName,
                    switchable: true,
                }, {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName,
                    switchable: true,
                }, {
                    field: 'quantity',
                    title: '{{"importAnimal.quantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }, {
                    field: 'remainQuantity',
                    title: '{{"importAnimal.remainQuantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }, {
                    field: 'dateImport',
                    title: '{{"importAnimal.dateImport" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _dateFormatter

                },
                {
                    field: 'created_by',
                    title: '{{"farm.createBy" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    // formatter: _numberFormat
                },
                {
                    field: 'create_date',
                    title: '{{"farm.create_date" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _createDateFormatter

                },


            ];
        };

        //---------Popup Farm-------//
        self.getTableDefinitionFarm = getTableDefinitionFarm;
        self.getPageSearchFarm = getPageSearchFarm;
        var baseUrlFarm = settings.api.baseUrl + settings.api.apiPrefix + 'farm/';
        //thành - dữ liệu view farm của bstable
        function getPageSearchFarm(searchFarmDto, pageIndex, pageSize, successCallback, errorCallback) {
            if (searchFarmDto != null) {

                var url = baseUrlFarm + 'searchDto/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, searchFarmDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

        function getTableDefinitionFarm() { //cái này là view danh sách cơ sở chăn nuôi

            var _tableOperation = function(value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editImportAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    +
                    '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteImportAnimal(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>';
            };
            var _tableInput = function(value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            var _cellNowrap = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _formatterName = function(value, row, index, field) {
                if (value && value.name) {
                    return value.name;
                }
                return '';
            }
            var _dateFormatter = function(value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _operationColStyle = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };

            return [{
                    field: 'state',
                    checkbox: true,
                },
                {
                    field: 'code',
                    title: '{{"farm.farmCode" | translate}}',
                    sortable: false,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }, {
                    field: 'name',
                    title: '{{"farm.name" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'ownerName',
                    title: '{{"farm.ownerName" | translate}}',
                    sortable: false,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'provinceName',
                    title: '{{"administrativeUnit.province" | translate}}',
                    sortable: false,
                    switchable: false,
                    cellStyle: _cellNowrap
                }

            ];
        }

    }
})();