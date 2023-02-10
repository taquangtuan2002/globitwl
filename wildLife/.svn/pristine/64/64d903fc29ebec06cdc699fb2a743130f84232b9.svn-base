(function () {
    'use strict';

    angular.module('Education.Report').service('ReportCurrentStatusByAnimalService', ReportCurrentStatusByAnimalService);

    ReportCurrentStatusByAnimalService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function ReportCurrentStatusByAnimalService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'report/';
        var Url = settings.api.baseUrl + settings.api.apiPrefix ;
        var baseUrlFarm = settings.api.baseUrl + settings.api.apiPrefix + 'farm/';
		var baseUrlAnimal = settings.api.baseUrl + settings.api.apiPrefix + 'animal/';
        
		self.reportByAnimalsCurrent=reportByAnimalsCurrent;
		self.getAdminstrativeUnitsCity=getAdminstrativeUnitsCity;
		self.getAllByParentId=getAllByParentId;
        self.reportCurrentStatusByAnimalAdministrative=reportCurrentStatusByAnimalAdministrative;
        self.getReportNumberTimeImportExport=getReportNumberTimeImportExport;
        self.getAllFarmSimple=getAllFarmSimple;
        self.getTableDefinitionFarm=getTableDefinitionFarm;
        self.getAdministrativeUnitDtoByLoginUser=getAdministrativeUnitDtoByLoginUser;
        self.getPageSearchFarm=getPageSearchFarm;
        self.reportAnimalEndangeredPreciousRareNormarlAndCites=reportAnimalEndangeredPreciousRareNormarlAndCites;
        self.reportCurrentStatusByFamily=reportCurrentStatusByFamily;
        self.getCurrentUser=getCurrentUser;
        self.getDataExportReportAnimalDangerousCitesToExcel = getDataExportReportAnimalDangerousCitesToExcel;
		self.getDataExportReportAnimalDangerousCitesToExcelByForm = getDataExportReportAnimalDangerousCitesToExcelByForm;
        self.getAnimalCurrentByFamilyDataToExcelFile = getAnimalCurrentByFamilyDataToExcelFile;
		self.getPageSearchAnimal = getPageSearchAnimal;
		self.getTableDefinitionAnimal  = getTableDefinitionAnimal;
		self.getReportImportExportForm16  = getReportImportExportForm16;
        self.getReportImportAnimal = getReportImportAnimal;
        self.getReportExportAnimal = getReportExportAnimal;
        self.getReportForestProductList = getReportForestProductList;
        self.getForestProductById = getForestProductById;
        self.getStreamDocument = getStreamDocument;
        self.getAllForm16ByFarm = getAllForm16ByFarm;
        //get forestProduct
        function getForestProductById(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist/'+id;
            return utils.resolveAlt(url, 'GET', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getReportImportExportForm16(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'report/getReportImportExportForm16';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getStreamDocument(object) {
            console.log("RUNNING");
            var deferred = $q.defer();
            $http({
                url: settings.api.baseUrl + settings.api.apiPrefix + 'file/downloadForestList',
                method: "POST",//you can use also GET or POST
                data: object,
                headers: { 'Content-type': 'application/json' },
                responseType: 'arraybuffer',//THIS IS IMPORTANT
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
        function getReportForestProductList(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'report/report26';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getReportImportAnimal(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'report/report24';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getReportExportAnimal(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'report/report25';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getAllForm16ByFarm(farmID) {
            var url = Url + 'reportForm16//getbyfarm/'+farmID;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        
        function getAnimalCurrentByFamilyDataToExcelFile(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/exportReportAnimalCurrentByFamilyToExcel' ,
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

        function getDataExportReportAnimalDangerousCitesToExcel(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/exportReportAnimalDangerousCitesToExcelNativeQuery' ,
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
		function getDataExportReportAnimalDangerousCitesToExcelByForm(searchDto){
            var deferred = $q.defer();
            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/exportReportAnimalDangerousCitesToExcelByFormNativeQuery' ,
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
        
        function getCurrentUser() {
            var url = Url + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function reportAnimalEndangeredPreciousRareNormarlAndCites(dto) {
            if(dto != null){
                var url = settings.api.baseUrl + settings.api.apiPrefix  + 'report/reportAnimalEndangeredPreciousRareNormarlAndCites';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }
        function reportCurrentStatusByFamily(dto) {
            if(dto != null){
                var url = settings.api.baseUrl + settings.api.apiPrefix  + 'report/reportCurrentStatusByFamily';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }
        function getPageSearchFarm(searchFarmDto,pageIndex, pageSize, successCallback, errorCallback) {
            if(searchFarmDto != null){

                var url = baseUrlFarm + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, searchFarmDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }
        
        function reportByAnimalsCurrent(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'report/reportByAnimalsCurrent';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function reportCurrentStatusByAnimalAdministrative(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'report/reportCurrentStatusByAnimalAdministrative';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getReportNumberTimeImportExport(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'report/reportNumberTimeImportExport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }


        function getAllFarmSimple(){
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'farm/getAllFarmSimple';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAdminstrativeUnitsCity(){
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'administrative/getall/city';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAllByParentId(parentId){
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'administrative/getAllByParentId/' + parentId ;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAdministrativeUnitDtoByLoginUser() {
            var url = Url + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
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
            var _tableIndex = function (value, row, index, field) {
                return index + 1;
            };
            var _tableTextCenter = function (value, row, index, field) {
                return {
                    classes:'text-center',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _tableTextRight = function (value, row, index, field) {
                return {
                    classes:'text-right',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _tableTextLeft = function (value, row, index, field) {
                return {
                    classes:'text-left',
                    css: { 'white-space': 'nowrap' }
                };
            };

            return [
                {
                    field: '',
                    title: 'TT',
                    switchable: true,
                    visible: true,
                    formatter: _tableIndex,
                    cellStyle: _tableTextCenter
                },
                {
                    field: 'state',
                    checkbox: true,
                },
                {
                    field: 'code',
                    title: '{{"farm.farmCode" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _tableTextLeft,
                }
                , {
                    field: 'name',
                    title: '{{"farm.name" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _tableTextLeft
                },
                {
                    field: 'ownerName',
                    title: '{{"farm.ownerName" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _tableTextLeft
                },
                {
                    field: 'provinceName',
                    title: '{{"administrativeUnit.province" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _tableTextLeft
                }

            ];
        }
		
		
		function getPageSearchAnimal(searchAnimalDto,pageIndex, pageSize, successCallback, errorCallback) {
            if(searchAnimalDto != null){

                var url = baseUrlAnimal + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, searchAnimalDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }
		function getTableDefinitionAnimal() {//cái này là view loài nuôi

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
            var _tableIndex = function (value, row, index, field) {
                return index + 1;
            };
            var _tableTextCenter = function (value, row, index, field) {
                return {
                    classes:'text-center',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _tableTextRight = function (value, row, index, field) {
                return {
                    classes:'text-right',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _tableTextLeft = function (value, row, index, field) {
                return {
                    classes:'text-left',
                    css: { 'white-space': 'nowrap' }
                };
            };
			var _cellNowrapBreak = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'break-spaces', 'min-width':'120px','max-width':'150px' }
                };
            };

            return [
                {
                    field: '',
                    title: 'TT',
                    switchable: true,
                    visible: true,
                    formatter: _tableIndex,
                    cellStyle: _tableTextCenter
                },
                {
                    field: 'state',
                    checkbox: true,
                },
                {
                    field: 'code',
                    title: '{{"animal.code" | translate}}' ,
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                // ,{
                //     field: 'oldCode',
                //     title: 'Mã 2015' ,
                //     sortable: true,
                //     switchable: false,
                //     cellStyle: _cellNowrap,
                // }
                , {
                    field: 'name',
                    title: 'Tên loài',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                },
                {
                    field: 'otherName',
                    title: 'Tên khác',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                },
                {
                    field: 'scienceName',
                    title: 'Tên khoa học',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                },{
                    field: 'enName',
                    title: 'Tên tiếng Anh',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                }, {
                    field: 'animalClass',
                    title:'Lớp',
                    sortable: true,
                    // formatter: _tableInput,
                    cellStyle: _cellNowrap,
                    switchable: true,

                },{
                    field: 'ordo',
                    title: 'Bộ' ,
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }

            ];
        }

    }
})();