(function () {
    'use strict';

    angular.module('Education.ImportDrug').service('ImportDrugService', ImportDrugService);

    ImportDrugService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities'
    ];

    function ImportDrugService($http, $q, $filter, settings, utils) {
        var self = this;
		var Url = settings.api.baseUrl + settings.api.apiPrefix;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'importdrug/';
       
        self.getTableDefinition = getTableDefinition;
        self.validDelete = validDelete;
        
        self.getPageImportDrug = getPageImportDrug;
        self.updateImportDrug = updateImportDrug;
        self.createImportDrug = createImportDrug;
        self.deleteImportDrug = deleteImportDrug;
		self.getImportDrug=getImportDrug;
        self.getPageSearchImportDrug=getPageSearchImportDrug;
  
		self.getAdministrativeUnitDtoByLoginUser=getAdministrativeUnitDtoByLoginUser;
		self.getCurrentUser=getCurrentUser;
		self.getFarmByUserLogin=getFarmByUserLogin;
		self.getTableDefinitionAdmin=getTableDefinitionAdmin;
		
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
       

        function deleteImportDrug(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }


        function getImportDrug(id) {
            var url = baseUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function validDelete(id) {
            var url = baseUrl + 'validDelete/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function createImportDrug(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function updateImportDrug(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getPageImportDrug(pageIndex, pageSize) {
            var url = baseUrl +'/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


		function getPageSearchImportDrug(dto,pageIndex, pageSize, successCallback, errorCallback) {
            if(dto != null){

                var url = baseUrl + 'searchDto/'+ pageIndex +'/'+pageSize;
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
                return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.editImportDrug(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importDrug.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteImportDrug(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importDrug.delete" | translate}}</a>';
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
                if (!value && value != 0) {
                    return '';
                }
                // return $filter('number')(value, 1);
                return value;
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
                    title: '{{"importDrug.operating" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                }, {
                    field: 'code',
                    title: '{{"exportDrug.batchCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }, {
                    field: 'dateImport',
                    title: '{{"importDrug.dateImport" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _dateFormatter

                }
				, {
                    field: 'drug',
                    title: '{{"importDrug.drugCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
					formatter: _formatterCode

                }
				, {
                    field: 'drug',
                    title: '{{"importDrug.drugName" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
				, {
                    field: 'quantity',
                    title: '{{"importDrug.quantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }, {
                    field: 'remainQuantity',
                    title: '{{"exportDrug.remainQuantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }, {
                    field: 'price',
                    title: '{{"importDrug.price"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }, {
                    field: 'unit',
                    title: '{{"importDrug.unit"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName
                }
				, {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
                ,{
                    field: 'supplier',
                    title: '{{"importDrug.supplierName" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'preservation',
                    title: '{{"importDrug.preservation" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'dateOfManufacture',
                    title: '{{"importDrug.dateOfManufacture" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'expiryDate',
                    title: '{{"importDrug.expiryDate" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'producer',
                    title: '{{"importDrug.producer" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'symbolCode',
                    title: '{{"importDrug.symbolCode" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'tradenames',
                    title: '{{"importDrug.tradenames" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'batchCodeManufacture',
                    title: '{{"importDrug.batchCodeManufacture" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
				
				
            ];
        };
		
		function getTableDefinitionAdmin() {

            var _tableOperation = function (value, row, index) {
                return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.editImportDrug(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importDrug.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteImportDrug(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importDrug.delete" | translate}}</a>';
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
                if (!value && value != 0) {
                    return '';
                }
                // return $filter('number')(value, 1);
                return value;
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

            

            return [
                {
                    field: '',
                    title: '{{"importDrug.operating" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                }, {
                    field: 'code',
                    title: '{{"exportDrug.batchCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }, {
                    field: 'dateImport',
                    title: '{{"importDrug.dateImport" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _dateFormatter

                }
				, {
                    field: 'drug',
                    title: '{{"importDrug.drugCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
					formatter: _formatterCode

                }
				, {
                    field: 'drug',
                    title: '{{"importDrug.drugName" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
				, {
                    field: 'quantity',
                    title: '{{"importDrug.quantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }, {
                    field: 'remainQuantity',
                    title: '{{"exportDrug.remainQuantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }, {
                    field: 'price',
                    title: '{{"importDrug.price"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }
				, {
                    field: 'unit',
                    title: '{{"importDrug.unit"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName
                }
				, {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
                ,{
                    field: 'supplier',
                    title: '{{"importDrug.supplierName" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'preservation',
                    title: '{{"importDrug.preservation" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'dateOfManufacture',
                    title: '{{"importDrug.dateOfManufacture" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'expiryDate',
                    title: '{{"importDrug.expiryDate" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'producer',
                    title: '{{"importDrug.producer" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'symbolCode',
                    title: '{{"importDrug.symbolCode" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'tradenames',
                    title: '{{"importDrug.tradenames" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                ,{
                    field: 'batchCodeManufacture',
                    title: '{{"importDrug.batchCodeManufacture" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
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