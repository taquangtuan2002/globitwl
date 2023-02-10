(function () {
    'use strict';

    angular.module('Education.LiveStockProduct').service('LiveStockProductService', LiveStockProductService);

    LiveStockProductService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function LiveStockProductService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'liveStockProduct/';

        self.getTableDefinition = getTableDefinition;
        self.getAllLiveStockProduct = getAllLiveStockProduct;
        self.getById = getById;
        self.saveLiveStockProduct = saveLiveStockProduct;
        self.deleteServiceByIds = deleteServiceByIds;
        self.deleteById = deleteById;      
		self.getPageSearch=getPageSearch;
		self.checkDuplicateCode=checkDuplicateCode;

		function checkDuplicateCode(code) {
            var url = baseUrl + 'checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
       
        function deleteById(id,successCallback,errorCallback){
            var url = baseUrl + 'delete/'+id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteServiceByIds(ids,successCallback, errorCallback){
            var url = baseUrl + 'delete/ids';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function saveLiveStockProduct(dto,successCallback,errorCallback){
            var url = baseUrl + 'save';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getById(id){
            var url = baseUrl +id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllLiveStockProduct(pageIndex, pageSize) {
            var url = baseUrl + 'pagination/getall/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		
		function getPageSearch(dto,pageIndex, pageSize, successCallback, errorCallback) {
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
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editLiveStockProduct(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"original.edit" | translate}}</a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteLiveStockProductRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"original.delete" | translate}}</a>';
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
                    css: { 'white-space': 'nowrap', 'width': '120px' }
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

             var _nameFormatter = function (value, row, index) {
                 if (!value) {
                     return '';
                 }
                 if(value!= null || value.length > 0){
                 }
                 return value.name;
             };

            return [
                {
                    field: 'state',
                    checkbox: true
                },
                {
                    field: '',
                    title: '{{"liveStockProduct.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
                {
                    field: 'code',
                    title: '{{"liveStockProduct.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'name',
                    title: '{{"liveStockProduct.name" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }, {
                    field: 'animal',
                    title: '{{"liveStockProduct.animal" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
					formatter:_nameFormatter
                }
				, {
                    field: 'unitQuantity',
                    title: '{{"liveStockProduct.unitQuantity" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
					formatter:_nameFormatter
                }
				, {
                    field: 'unitAmount',
                    title: '{{"liveStockProduct.unitAmount" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
					formatter:_nameFormatter
                }
            ];
        }
    }
})();