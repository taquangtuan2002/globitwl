(function () {
    'use strict';

    angular.module('Education.ExportEgg').service('ExportEggService', ExportEggService);

    ExportEggService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities'
    ];

    function ExportEggService($http, $q, $filter, settings, utils) {
        var self = this;
		var Url = settings.api.baseUrl + settings.api.apiPrefix;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'export_egg/';
       
        self.getTableDefinition = getTableDefinition;
        
        self.getPageExportEgg = getPageExportEgg;
        self.updateExportEgg = updateExportEgg;
        self.createExportEgg = createExportEgg;
        self.deleteExportEgg = deleteExportEgg;
		self.getExportEgg=getExportEgg;
        self.getPageSearchExportEgg=getPageSearchExportEgg;
  
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
       

        function deleteExportEgg(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }


        function getExportEgg(id) {
            var url = baseUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function createExportEgg(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function updateExportEgg(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getPageExportEgg(pageIndex, pageSize) {
            var url = baseUrl + 'page/'+type+'/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


		function getPageSearchExportEgg(dto,pageIndex, pageSize, successCallback, errorCallback) {
            if(dto != null){

                var url = baseUrl + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }
		
     
        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.editExportEgg(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"exportEgg.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteExportEgg(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"exportEgg.delete" | translate}}</a>';
            };
          

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
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
            var _formatterEggType = function (value, row, index, field){
                const eggTypes = [{
                    id: 0,
                    name:"Thương phẩm"
                },
                {
                    id: 1,
                    name:"Con giống"
                }]
                if(value == 0){
                    return eggTypes[0].name;
                }else{
                    if(value == 1){
                        return eggTypes[1].name;
                    }
                }
                return '';
            };

            

            return [
                {
                    field: '',
                    title: '{{"importAnimal.operating" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
               {
                    field: 'batchCode',
                    title: '{{"exportEgg.batchCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }
				,
                {
                    field: 'dateExport',
                    title: '{{"exportEgg.dateExport" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _dateFormatter
                }
				, {
                    field: 'farm',
                    title: '{{"exportEgg.farmCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterCode,
                    switchable: true,
                }
				, {
                    field: 'farm',
                    title: '{{"exportEgg.farmName" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
                , {
                    field: 'eggType',
                    title: '{{"exportEgg.eggType" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterEggType,
                    switchable: true,
                }
				, {
                    field: 'quantity',
                    title: '{{"exportEgg.quantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }
				, {
                    field: 'buyerName',
                    title: '{{"exportEgg.buyerName" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,                   
                    switchable: true
                }
				, {
                    field: 'buyerAdress',
                    title: '{{"exportEgg.buyerAdress"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
				
				
				
            ];
        };
		
		function getTableDefinitionAdmin() {

			 var _tableOperation = function (value, row, index) {
	                return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.editExportEgg(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"exportEgg.edit" | translate}}</i></a>'
	                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
	                    + '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteExportEgg(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"exportEgg.delete" | translate}}</a>';
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
            var _formatterEggType = function (value, row, index, field){
                const eggTypes = [{
                    id: 0,
                    name:"Thương phẩm"
                },
                {
                    id: 1,
                    name:"Con giống"
                }]
                if(value == 0){
                    return eggTypes[0].name;
                }else{
                    if(value == 1){
                        return eggTypes[1].name;
                    }
                }
                return '';
            };

            

            return [
            	{
                    field: '',
                    title: '{{"importAnimal.operating" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
				{
                    field: 'batchCode',
                    title: '{{"exportEgg.batchCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }
				,
                {
                    field: 'dateExport',
                    title: '{{"exportEgg.dateExport" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _dateFormatter
                }
				, {
                    field: 'farm',
                    title: '{{"exportEgg.farmCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterCode,
                    switchable: true,
                }
				, {
                    field: 'farm',
                    title: '{{"exportEgg.farmName" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
                , {
                    field: 'eggType',
                    title: '{{"exportEgg.eggType" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterEggType,
                    switchable: true,
                }
				, {
                    field: 'quantity',
                    title: '{{"exportEgg.quantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }
				, {
                    field: 'buyerName',
                    title: '{{"exportEgg.buyerName" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,                   
                    switchable: true
                }
				, {
                    field: 'buyerAdress',
                    title: '{{"exportEgg.buyerAdress"| translate}}',
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

    }
})();