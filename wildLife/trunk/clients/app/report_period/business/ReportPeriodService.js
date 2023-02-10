(function () {
    'use strict';

    angular.module('Education.ReportPeriod').service('ReportPeriodService', ReportPeriodService);

    ReportPeriodService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities'
    ];

    function ReportPeriodService($http, $q, $filter, settings, utils) {
        var self = this;
		var Url = settings.api.baseUrl + settings.api.apiPrefix;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'import_export/animal/';
        var type = 1;//Kiểu phiếu: 1= Nhập đàn
        self.getTableDefinition = getTableDefinition;
        self.getTableDefinitionSeedLevel = getTableDefinitionSeedLevel;
        self.getImportAnimal = getImportAnimal;
        self.getFileExcelForm16A = getFileExcelForm16A;
        self.getPageImportAnimal = getPageImportAnimal;
        self.updateImportAnimal = updateImportAnimal;
        self.createImportAnimal = createImportAnimal;
        self.deleteImportAnimal = deleteImportAnimal;
        self.getTableDefinitionInjectionPlant = getTableDefinitionInjectionPlant;
        self.getFileById = getFileById;
        self.getPageSearchImportAnimal=getPageSearchImportAnimal;
        self.getListSeedlevel=getListSeedlevel;
        self.saveReportPeriod=saveReportPeriod;
        self.searchByPage=searchByPage;
        self.getReportPeriod=getReportPeriod;
        self.deleteById=deleteById;
        self.getAllByParentId=getAllByParentId;
        self.getAdminstrativeUnitsCity=getAdminstrativeUnitsCity;
        self.getAdminstrativeUnitById=getAdminstrativeUnitById;
        self.getUserAdminstrativeUnit=getUserAdminstrativeUnit;
        self.getUserAdminstrativeEditTableUnit=getUserAdminstrativeEditTableUnit;
        self.getAllAnimalReported=getAllAnimalReported;

        //tran huu dat theem hamf lay form 16 theo farm start
        self.getAllForm16ByFarm=getAllForm16ByFarm;
        self.saveListReportForm16=saveListReportForm16;
        self.getForm16ById=getForm16ById;
        self.deleteForm16ById=deleteForm16ById;
        //tran huu dat theem hamf lay form 16 theo farm end
        
      //  self.getsearchImportAnimalDto=getsearchImportAnimalDto;
		self.getBatchCode=getBatchCode;
		self.getStream=getStream;
		self.getAdministrativeUnitDtoByLoginUser=getAdministrativeUnitDtoByLoginUser;
		self.getCurrentUser=getCurrentUser;
		self.checkDuplicateYearMonthDate=checkDuplicateYearMonthDate;
        self.getFileImportExcelForm16A = getFileImportExcelForm16A;
        self.searchByPageReportForm16 = searchByPageReportForm16;
        self.getDataDistrictAndWard = getDataDistrictAndWard;
		self.searchByPageReportFormAnimalEgg = searchByPageReportFormAnimalEgg;
		self.exportReportFormAnimalEgg16C =  exportReportFormAnimalEgg16C;
		self.searchByPageReportFormAnimalGiveBirth = searchByPageReportFormAnimalGiveBirth;
		self.exportReportFormAnimalGiveBirth16D = exportReportFormAnimalGiveBirth16D;
        self.downloadTemplateImportFile = downloadTemplateImportFile;
        self.downloadListAnimal = downloadListAnimal;
        self.downloadListAdminstrativeUnit = downloadListAdminstrativeUnit;
        self.getFileTutorialImportDataForm16 = getFileTutorialImportDataForm16;
        self.getFileImportExcelForm16ANew= getFileImportExcelForm16ANew;
        self.getLastRecordReportPeriodByFarmAndAnimal = getLastRecordReportPeriodByFarmAndAnimal;
        self.deletePageReportFormAnimalGiveBirthById = deletePageReportFormAnimalGiveBirthById;
        self.deleteReportFormAnimalEgg = deleteReportFormAnimalEgg;
        self.getReportFormAnimalEggById = getReportFormAnimalEggById;
        self.getReportFormAnimalGiveBirthById = getReportFormAnimalGiveBirthById;

        self.getFileImportExcelForm16AInImportReport = getFileImportExcelForm16AInImportReport;
        self.getFileImportExcelForm16ANewInImportReport= getFileImportExcelForm16ANewInImportReport;
        self.getFileImportExcelForm16ADetail = getFileImportExcelForm16ADetail;
        self.exportData = exportData;

        function exportData(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'reportForm16/export-data' ,
                method:"POST",//you can use also GET or POST
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                deferred.reject(data);
            });
            return deferred.promise;
        };

        // lấy bản ghi cuối cùng 1 loài của farm
        function getLastRecordReportPeriodByFarmAndAnimal(searchObject, successCallback, errorCallback) {
            var url = Url + 'reportPeriod/getLastRecordReportPeriodByFarmAndAnimal';

            return utils.resolveAlt(url, 'POST', null, searchObject, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function deletePageReportFormAnimalGiveBirthById(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportanimalgivebirth/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getReportFormAnimalGiveBirthById(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportanimalgivebirth/' + id;
            return utils.resolveAlt(url, 'GET', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function deleteReportFormAnimalEgg(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportformanimalegg/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getReportFormAnimalEggById(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'reportformanimalegg/' + id;
            return utils.resolveAlt(url, 'GET', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getFileTutorialImportDataForm16 (){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/getFileTutorialImportDataForm16',
                method:"GET",
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                deferred.reject(data);
            });
            return deferred.promise;
        }
      
        
        function downloadListAdminstrativeUnit (dto){
            if(dto != null){
                var deferred = $q.defer();
                $http({
                    url:settings.api.baseUrl + settings.api.apiPrefix  +'file/exportAdministrativeUnit/' + dto.id  ,
                    method:"POST",
                    headers:{'Content-type': 'application/json'},
                    responseType : 'arraybuffer',//THIS IS IMPORTANT
                })
                    .success(function (data) {
                        deferred.resolve(data);
                    }).error(function (data) {
                    deferred.reject(data);
                });
                return deferred.promise;
            }
            
        }

        function downloadListAnimal (searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/exportAnimal' ,
                method:"POST",
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                deferred.reject(data);
            });
            return deferred.promise;
        }

        function downloadTemplateImportFile (){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/downloadTemplateImportForm16' ,
                method:"POST",
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                deferred.reject(data);
            });
            return deferred.promise;
        }
        function getDataDistrictAndWard(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/exportReportForm16DataByDistrictAndWard' ,
                method:"POST",//you can use also GET or POST
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                deferred.reject(data);
            });
            return deferred.promise;
        };
		
		function exportReportFormAnimalEgg16C(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/exportReportFormAnimalEgg16C' ,
                method:"POST",//you can use also GET or POST
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                deferred.reject(data);
            });
            return deferred.promise;
        };
		
		function exportReportFormAnimalGiveBirth16D(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/exportReportFormAnimalGiveBirth16D' ,
                method:"POST",//you can use also GET or POST
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                deferred.reject(data);
            });
            return deferred.promise;
        };

        function searchByPageReportForm16(searchObject, successCallback, errorCallback) {
            var url = Url + 'reportForm16/searchByPage';
            return utils.resolveAlt(url, 'POST', null, searchObject, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        //tran huu dat them ham lay form16 theo farm
        function getAllForm16ByFarm(farmID) {
            var url = Url + 'reportForm16//getbyfarm/'+farmID;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        //tran huu dat them ham lay form16 theo farm

        //tran huu dat them ham lay form 16 theo id start
        function getForm16ById(form16Id){
            var url = Url + 'reportForm16/'+form16Id;
            return utils.resolve(url,'GET',angular.noop, angular.noop)
        }

        function deleteForm16ById(idForm16){
            var url = Url + 'reportForm16/delete/'+idForm16;
            return utils.resolve(url,'POST',angular.noop, angular.noop)
        }

        //tran huu dat them ham lay form 16 theo id end

        //tran huu dat them ham luu form16 theo farm
        function saveListReportForm16(listReportForm16, successCallback, errorCallback){
            var url = Url + 'reportForm16/savelist';
            return utils.resolveAlt(url, 'POST', null, listReportForm16, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        //tran huu dat them ham luu form16 theo farm
        
		function searchByPageReportFormAnimalEgg(searchObject, successCallback, errorCallback) {
            var url = Url + 'reportformanimalegg/searchByPage';
            return utils.resolveAlt(url, 'POST', null, searchObject, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		function searchByPageReportFormAnimalGiveBirth(searchObject, successCallback, errorCallback) {
            var url = Url + 'reportanimalgivebirth/searchByPage';
            return utils.resolveAlt(url, 'POST', null, searchObject, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function getAdminstrativeUnitsCity(){
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'administrative/getall/city';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAdminstrativeUnitById(id){
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'administrative/'+id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getUserAdminstrativeUnit(userId){
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'user_administrative/getByUserId/'+userId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getUserAdminstrativeEditTableUnit(adminUnitId){
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'AdministrativeUnitEditable/getAdministrativeUnitEditableByAdminUnit/'+adminUnitId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAllByParentId(parentId){
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'administrative/getAllByParentId/' + parentId ;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteById(id) {
            var url = Url + 'reportPeriod/' + id;
            return utils.resolve(url, 'DELETE', angular.noop, angular.noop);
        }
        
        function getReportPeriod(id) {
            var url = Url + 'reportPeriod/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function searchByPage(searchObject, successCallback, errorCallback) {
            var url = Url + 'reportPeriod/searchByPage';

            return utils.resolveAlt(url, 'POST', null, searchObject, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function checkDuplicateYearMonthDate(dto) {
            var url = Url + 'reportPeriod/checkDuplicateYearMonthDate';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getAllAnimalReported(farmId) {
            var url = Url + 'animal_report/getAllAnimalReported/'+farmId;

            return utils.resolveAlt(url, 'POST', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

		function getCurrentUser() {
            var url = Url + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getAdministrativeUnitDtoByLoginUser() {
            var url = Url + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		/*function getPageSearchFarm(searchFarmDto, successCallback, errorCallback) {
            if(searchFarmDto != null){

                var url = Url + 'farm/searchDto';
                return utils.resolveAlt(url, 'POST', null, searchFarmDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }*/
		
		function getStream(searchFarmDto){
            console.log("RUNNING");
            var deferred = $q.defer();

            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/downloadImportAnimal' ,
                method:"POST",//you can use also GET or POST
                data:searchFarmDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                console.error("ERROR");
                deferred.reject(data);
            });

            return deferred.promise;
        };
		function getFileExcelForm16A(searchDto){
            console.log("RUNNING");
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/downloadFileExcelForm16A' ,
                method:"POST",//you can use also GET or POST
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                console.error("ERROR");
                deferred.reject(data);
            });

            return deferred.promise;
        };
        function getFileImportExcelForm16A(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/downloadFileImportExcelForm16A' ,
                method:"POST",//you can use also GET or POST
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                console.error("ERROR");
                deferred.reject(data);
            });

            return deferred.promise;
        };
        function getFileImportExcelForm16ADetail(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/downloadFileImportExcelForm16ADetail' ,
                method:"POST",//you can use also GET or POST
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                console.error("ERROR");
                deferred.reject(data);
            });

            return deferred.promise;
        };
        //xuất excel cho báo cáo gần nhất theo động vật
        function getFileImportExcelForm16AInImportReport(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/downloadFileImportExcelForm16AInImportReport' ,
                method:"POST",//you can use also GET or POST
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                console.error("ERROR");
                deferred.reject(data);
            });

            return deferred.promise;
        };

        //tran huu dat them file import new
        function getFileImportExcelForm16ANew(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/downloadFileImportExcelForm16ANew' ,
                method:"POST",//you can use also GET or POST
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                console.error("ERROR");
                deferred.reject(data);
            });

            return deferred.promise;
        };
        //tran huu dat them file import new
        function getFileImportExcelForm16ANewInImportReport(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/downloadFileImportExcelForm16ANewInImportReport' ,
                method:"POST",//you can use also GET or POST
                data:searchDto,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                console.error("ERROR");
                deferred.reject(data);
            });

            return deferred.promise;
        };
        function getFileById(id) {
            var url = baseUrl + 'file/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteImportAnimal(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }


        function getImportAnimal(id) {
            var url = baseUrl + 'get/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getListSeedlevel() {
            var url = Url + 'seed_level/getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function saveReportPeriod(dto, successCallback, errorCallback) {
            var url = Url + 'reportPeriod';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function createImportAnimal(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function updateImportAnimal(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getPageImportAnimal(pageIndex, pageSize) {
            var url = baseUrl + 'page/'+type+'/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function getAllOriginal(pageIndex, pageSize) {
            var url = baseUrl + 'pagination/getall/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getPageSearchImportAnimal(dto,pageIndex, pageSize, successCallback, errorCallback) {
            if(dto != null){
                // dto.isImportExportAnimalSeed = true;
                var url = baseUrl + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }
		function getBatchCode(importDate) {
            var url = baseUrl + 'autoGenericBatchCode/' + importDate;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }		

        // function getSpeciality() {
        //     var url = baseUrl + 'speciality/1/1000';
        //     return utils.resolve(url, 'GET', angular.noop, angular.noop);
        // }
        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.editImportAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteImportAnimal(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>';
            };
            var _tableInput = function (value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap'}
                };
            };
            var _formatterName = function (value, row, index, field){
                if(value && value.name){
                    return value.name;
                }
                return '';
            }
            var _numberFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return $filter('number')(value, 0);
            };
            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };
            var _createDateFormatter = function (value, row, index) {
                
                if (!value) {
                    return '';
                }else {
                    var year = value[0];
                    var month = value[1];
                    var date = value[2];
                    var hour = value[3];
                    var minutes = value[4];
                    var seconds = value[5];
                    return date + '/' + month + '/' + year +' | ' +hour +':' +minutes+':' +seconds;
                }
                
            };


            // var _roomFormatter = function (value, row, index) {
            //     if (!value) {
            //         return '';
            //     }
            //     if(value!= null || value.length > 0){
            //     }
            //     return value.room.name;
            // };

            return [
                // {
                //     field: 'state',
                //     checkbox: true
                // }, 
                {
                    field: '',
                    title: '{{"importAnimal.operating" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                }, {
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
                    formatter:_formatterName,
                    switchable: true,
                }, {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
				/*,{
                    field: 'provinceName',
                    title: '{{"administrativeUnit.province" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }*/
				, {
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

                }, {
                    field: 'description',
                    title: '{{"importAnimal.description" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,

                } ,{
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
                }
                ,{
                    field: 'farm.ownerPhoneNumber',
                    title: '{{"farm.ownerPhoneNumber" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                   // formatter: _numberFormat
                }
            ];
        };
        function getTableDefinitionSeedLevel() {

            var _tableOperation = function (value, row, index) {
                return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.editImportAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteImportAnimal(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>';
            };
            var _tableInput = function (value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap'}
                };
            };
            var _formatterName = function (value, row, index, field){
                if(value && value.name){
                    return value.name;
                }
                return '';
            }
            var _numberFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return $filter('number')(value, 0);
            };
            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };
            var _styleAnimalName = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'break-spaces', 'text-align': 'center', 'min-width': '350px', 'max-width': '350px'}
                };
            };
            var _createDateFormatter = function (value, row, index) {
                
                if (!value) {
                    return '';
                }else {
                    var year = value[0];
                    var month = value[1];
                    var date = value[2];
                    var hour = value[3];
                    var minutes = value[4];
                    var seconds = value[5];
                    return date + '/' + month + '/' + year +' | ' +hour +':' +minutes+':' +seconds;
                }
                
            };


            // var _roomFormatter = function (value, row, index) {
            //     if (!value) {
            //         return '';
            //     }
            //     if(value!= null || value.length > 0){
            //     }
            //     return value.room.name;
            // };

            return [
                // {
                //     field: 'state',
                //     checkbox: true
                // }, 
                {
                    field: '',
                    title: '{{"importAnimal.operating" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                }, {
                    field: 'batchCode',
                    title: '{{"importAnimal.batchCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,

                }, {
                    field: 'animal',
                    title: '{{"importAnimal.animal.name" | translate}}',
                    sortable: true,
                    cellStyle: _styleAnimalName,
                    formatter:_formatterName,
                    switchable: true,
                }, {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
				/*,{
                    field: 'provinceName',
                    title: '{{"administrativeUnit.province" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }*/
				, {
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

                } , {
                    field: 'seedLevel',
                    title: '{{"importAnimal.seedLevel" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _formatterName
                } , {
                    field: 'description',
                    title: '{{"importAnimal.description" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,

                } ,{
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
                }
                ,{
                    field: 'farm.ownerPhoneNumber',
                    title: '{{"farm.ownerPhoneNumber" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                   // formatter: _numberFormat
                }
            ];
        };

        function getTableDefinitionInjectionPlant() {
            var _tableOperation = function (value, row, index) {
                var ret = '';
                ret += '<a class="text-danger margin-right-10" uib-tooltip="{{\'importAnimal.edit\' | translate}}" tooltip-trigger="mouseenter" ng-show="!$parent.isView" data-ng-click="$parent.editInjectionPlant(' + "'" + index + "'" + ')"><i class="fa icon-pencil"></i>{{"importAnimal.edit" | translate}}</a>';
                ret += '<a class="green-dark" uib-tooltip="{{\'importAnimal.delete\' | translate}}" tooltip-trigger="mouseenter" ng-show="!$parent.isView" data-ng-click="$parent.deleteInjectionPlant(' + "'" + index + "'" + ')"><i class="fa icon-trash"></i>{{"importAnimal.delete" | translate}}</a>';

                return ret;
            };

            var _fileSize = function (value, row, index) {
                return $filter('fileSize')(value);
            };

            var _fileType = function (value, row, index) {
                return $filter('contentType')(value);
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };

            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '90px', 'text-align': 'center' }
                };
            };

            return [
                    {
                        field: 'state',
                        checkbox: true
                    },
                {
                    field: '',
                    title: '{{farm.operation | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },{
                    field: 'injectionDate',
                    title: '{{"importAnimal.injectionDate" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _dateFormatter
                }
                , {
                    field: 'drug',
                    title: '{{"importAnimal.drug" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
            ]
        }
		//---------Popup Farm-------//
        self.getTableDefinitionFarm=getTableDefinitionFarm;
        self.getPageSearchFarm=getPageSearchFarm;
		var baseUrlFarm = settings.api.baseUrl + settings.api.apiPrefix + 'farm/';

        function getPageSearchFarm(searchFarmDto,pageIndex, pageSize, successCallback, errorCallback) {
            if(searchFarmDto != null){

                var url = baseUrlFarm + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, searchFarmDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

        function getTableDefinitionFarm() {//cái này là view danh sách cơ sở chăn nuôi

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editImportAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteImportAnimal(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>';
            };
            var _tableInput = function (value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap'}
                };
            };
            var _formatterName = function (value, row, index, field){
                if(value && value.name){
                    return value.name;
                }
                return '';
            }
            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };

            return [
                {
                    field: 'state',
                    checkbox: true,
                }
                ,
                {
                    field: 'code',
                    title: '{{"farm.farmCode" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'name',
                    title: '{{"farm.name" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'ownerName',
                    title: '{{"farm.ownerName" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'provinceName',
                    title: '{{"administrativeUnit.province" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }

            ];
        }
    }
})();