(function () {
    'use strict';

    angular.module('Education.ImportAnimalSeed').service('ImportAnimalSeedService', ImportAnimalSeedService);

    ImportAnimalSeedService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities'
    ];

    function ImportAnimalSeedService($http, $q, $filter, settings, utils) {
        var self = this;
		var Url = settings.api.baseUrl + settings.api.apiPrefix;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'import_export/animal/';
        var type = 1;//Kiểu phiếu: 1= Nhập đàn
        self.getTableDefinition = getTableDefinition;
        self.getTableDefinitionSeedLevel = getTableDefinitionSeedLevel;
        self.getImportAnimal = getImportAnimal;
        self.getPageImportAnimal = getPageImportAnimal;
        self.updateImportAnimal = updateImportAnimal;
        self.createImportAnimal = createImportAnimal;
        self.deleteImportAnimal = deleteImportAnimal;
        self.getTableDefinitionInjectionPlant = getTableDefinitionInjectionPlant;
        self.getFileById = getFileById;
        self.getPageSearchImportAnimal=getPageSearchImportAnimal;
        self.getListSeedlevel=getListSeedlevel;
        
      //  self.getsearchImportAnimalDto=getsearchImportAnimalDto;
		self.getBatchCode=getBatchCode;
		self.getStream=getStream;
		self.getAdministrativeUnitDtoByLoginUser=getAdministrativeUnitDtoByLoginUser;
		self.getCurrentUser=getCurrentUser;
		//self.getPageSearchFarm=getPageSearchFarm;

        // function getsearchImportAnimalDto(searchFarmDto, successCallback, errorCallback) {
        //     if(searchFarmDto != null){

        //         var url = baseUrl + 'searchDto';
        //         return utils.resolveAlt(url, 'POST', null, searchFarmDto, {
        //             'Content-Type': 'application/json; charset=utf-8'
        //         }, successCallback, errorCallback);

        //     }
        // }
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
            var _tableIndex = function (value, row, index, field) {
                return index + 1;
            };
            var _tableTextRight = function (value, row, index, field) {
                return {
                    classes:'text-center'
                };
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
                    title: 'TT',
                    switchable: true,
                    visible: true,
                    formatter: _tableIndex,
                    cellStyle: _tableTextRight
                }, 
                {
                    field: '',
                    title: '{{"importAnimal.operating" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                }
                // , {
                //     field: 'batchCode',
                //     title: '{{"importAnimal.batchCode" | translate}}',
                //     sortable: true,
                //     cellStyle: _cellNowrap,
                //     switchable: true,

                // }
                , {
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
                }
                // , {
                //     field: 'remainQuantity',
                //     title: '{{"importAnimal.remainQuantity"| translate}}',
                //     sortable: true,
                //     switchable: false,
                //     cellStyle: _cellNowrap,
                //     formatter: _numberFormatter
                // }
                , {
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
            var _formatterOtherName = function (value, row, index, field){
                if(value && value.name){
                    return value.otherName;
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
                    css: { 'white-space': 'break-spaces', 'text-align': 'left', 'min-width': '350px', 'max-width': '350px'}
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
                    title: 'TT',
                    switchable: true,
                    visible: true,
                    formatter: _tableIndex,
                    cellStyle: _tableTextRight
                },
                {
                    field: '',
                    title: '{{"importAnimal.operating" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                }
                // , {
                //     field: 'batchCode',
                //     title: '{{"importAnimal.batchCode" | translate}}',
                //     sortable: true,
                //     cellStyle: _cellNowrap,
                //     switchable: true,

                // }
                ,{
                    field: 'animal',
                    title: '{{"animal.name" | translate}}',
                    sortable: true,
//                    cellStyle: _styleAnimalName,
                    formatter:_formatterName,
                    switchable: true,
                },{
                    field: 'animal',
                    title: 'Tên khác',
                    sortable: true,
//                    cellStyle: _styleAnimalName,
                    formatter:_formatterOtherName,
                    switchable: true,
                }
                ,{
                    field: 'animal.scienceName',
                    title: 'Tên khoa học',
                    sortable: true,
//                    cellStyle: _styleAnimalName,
//                    formatter:_formatterOtherName,
                    switchable: true,
                }, {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _tableTextLeft,
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
                    cellStyle: _tableTextRight,
                    formatter: _numberFormatter
                }
                // , {
                //     field: 'remainQuantity',
                //     title: '{{"importAnimal.remainQuantity"| translate}}',
                //     sortable: true,
                //     switchable: false,
                //     cellStyle: _cellNowrap,
                //     formatter: _numberFormatter
                // }
                , {
                    field: 'dateImport',
                    title: '{{"importAnimal.dateImport" | translate}}',
                    sortable: true,
                    cellStyle: _tableTextRight,
                    switchable: true,
                    formatter: _dateFormatter

                } , {
                    field: 'seedLevel',
                    title: '{{"importAnimal.seedLevel" | translate}}',
                    sortable: true,
                    cellStyle: _tableTextLeft,
                    switchable: true,
                    formatter: _formatterName
                } , {
                    field: 'description',
                    title: '{{"importAnimal.description" | translate}}',
                    sortable: true,
                    cellStyle: _tableTextLeft,
                    switchable: true,

                } ,{
                    field: 'created_by',
                    title: '{{"farm.createBy" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _tableTextLeft,
                   // formatter: _numberFormat
                },
                {
                    field: 'create_date',
                    title: '{{"farm.create_date" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _tableTextRight,
                    formatter: _createDateFormatter
                }
                ,{
                    field: 'farm.ownerPhoneNumber',
                    title: '{{"farm.ownerPhoneNumber" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _tableTextRight,
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
    }
})();