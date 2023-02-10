(function () {
    'use strict';

    angular.module('Education.ReportFarmerManagerment').service('ReportAnimalFeedService', ReportAnimalFeedService);

    ReportAnimalFeedService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities'
    ];

    function ReportAnimalFeedService($http, $q, $filter, settings, utils) {
        var self = this;
		var Url = settings.api.baseUrl + settings.api.apiPrefix;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'importanimalfeed/';
        var type = 1;//Kiểu phiếu: 1= Nhập đàn
        self.getTableDefinition = getTableDefinition;
        self.getTableDefinitionExport = getTableDefinitionExport;
        
        self.getPageImportAnimalFeed = getPageImportAnimalFeed;
        self.updateImportAnimalFeed = updateImportAnimalFeed;
        self.createImportAnimalFeed = createImportAnimalFeed;
        self.deleteImportAnimalFeed = deleteImportAnimalFeed;
		self.getImportAnimalFeed=getImportAnimalFeed;
        self.getPageSearchAnimalFeed=getPageSearchAnimalFeed;
  
		self.getAdministrativeUnitDtoByLoginUser=getAdministrativeUnitDtoByLoginUser;
		self.getCurrentUser=getCurrentUser;
		self.getFarmByUserLogin=getFarmByUserLogin;
		self.reportInventory=reportInventory;
		
		function getCurrentUser() {
            var url = Url + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getAdministrativeUnitDtoByLoginUser() {
            var url = Url + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getFarmByUserLogin() {
            var url = Url + 'farm/getFarmByUserLogin';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
       

        function deleteImportAnimalFeed(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }


        function getImportAnimalFeed(id) {
            var url = baseUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function createImportAnimalFeed(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function updateImportAnimalFeed(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getPageImportAnimalFeed(pageIndex, pageSize) {
            var url = baseUrl + 'page/'+type+'/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


		function getPageSearchAnimalFeed(dto,pageIndex, pageSize, successCallback, errorCallback) {
            if(dto != null){

                var url = baseUrl + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }
		function reportInventory(dto, successCallback, errorCallback) {
            if(dto != null){

                var url = baseUrl + 'reportInventory';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }
		
        // function getSpeciality() {
        //     var url = baseUrl + 'speciality/1/1000';
        //     return utils.resolve(url, 'GET', angular.noop, angular.noop);
        // }
        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editImportAnimalFeed(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteImportAnimalFeed(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>';
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
			var _formatterCode = function (value, row, index, field){
                if(value && value.code){
                    return value.code;
                }
                return '';
            }
            var _numberFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return $filter('numberNoDecimalsIfZeroFilter')(value, 2);
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
            var _formatterUnit = function (value, row, index) {
                if (!value || !value.name) {
                    return '';
                }
                return value.name;
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
                {
                    field: 'code',
                    title: '{{"genarel.batchCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }
                ,{
                    field: 'dateIssue',
                    title: '{{"importAnimalFeed.dateImport" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _dateFormatter

                }
				, {
                    field: 'bran',
                    title: '{{"importAnimalFeed.branCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
					formatter: _formatterCode

                }
				, {
                    field: 'bran',
                    title: '{{"importAnimalFeed.branName" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
				, {
                    field: 'unit',
                    title: '{{"application.menu.sku"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _formatterUnit
                }
				, {
                    field: 'quantity',
                    title: '{{"importAnimalFeed.quantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }
				, {
                    field: 'remainQuantity',
                    title: '{{"importAnimalFeed.remain_quantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }
                , {
                    field: 'price',
                    title: '{{"importAnimalFeed.price"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }
				, {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
                , {
                    field: 'supplier',
                    title: '{{"importAnimalFeed.supplierName" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }
				, {
                    field: 'description',
                    title: '{{"importAnimal.description" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,

                } 
            ];
        };
        
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
        
        function getTableDefinitionExport() {
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
			var _formatterCode = function (value, row, index, field){
                if(value && value.code){
                    return value.code;
                }
                return '';
            }
            var _numberFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return $filter('numberNoDecimalsIfZeroFilter')(value, 2);
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
			var _formatterBranCode = function (value, row, index, field){
                if(value && value.bran && value.bran.code){
                    return value.bran.code;
                }
                return '';
            }
			var _formatterBranName = function (value, row, index, field){
                if(value && value.bran && value.bran.name){
                    return value.bran.name;
                }
                return '';
            }
            var _formatterPrice = function (value, row, index) {
                if (!value || !value.price) {
                    return '';
                }
                return $filter('number')(value.price, 0);
            };
            var _formatterUnit = function (value, row, index) {
                if (!value || !value.name) {
                    return '';
                }
                return value.name;
            };

            return [
                {
                    field: 'code',
                    title: '{{"genarel.batchCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }
                ,{
                    field: 'dateIssue',
                    title: '{{"importAnimalFeed.dateExport" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _dateFormatter

                }
				, {
                    field: 'importAnimalFeed',
                    title: '{{"importAnimalFeed.branCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
					formatter: _formatterBranCode

                }
				, {
                    field: 'importAnimalFeed',
                    title: '{{"importAnimalFeed.branName" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterBranName,
                    switchable: true,
                }
				, {
                    field: 'unit',
                    title: '{{"application.menu.sku"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _formatterUnit
                }
				, {
                    field: 'quantity',
                    title: '{{"importAnimalFeed.quantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }
                , {
                    field: 'importAnimalFeed',
                    title: '{{"importAnimalFeed.price"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _formatterPrice
                }
				, {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
				, {
                    field: 'buyerName',
                    title: '{{"importAnimalFeed.buyerName" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                }
				, {
                    field: 'buyerAdress',
                    title: '{{"importAnimalFeed.buyerAdress" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                }
				, {
                    field: 'description',
                    title: '{{"importAnimal.description" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,

                } 
            ];
        };

    }
})();