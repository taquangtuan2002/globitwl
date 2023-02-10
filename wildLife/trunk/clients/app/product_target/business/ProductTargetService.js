(function () {
    'use strict';

    angular.module('Education.ProductTarget').service('ProductTargetService', ProductTargetService);

    ProductTargetService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function ProductTargetService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'producttarget/';

        self.getTableDefinition = getTableDefinition;
        self.getPageProductTarget = getPageProductTarget;
        self.createProductTarget = createProductTarget;
        self.saveProductTarget = saveProductTarget;
        self.deleteProductTargetById = deleteProductTargetById;
        self.deleteListId = deleteListId;


        self.getProducTargetById = getProducTargetById;
        self.saveOriginal = saveOriginal;
        self.deleteServiceByIds = deleteServiceByIds;
        self.deleteOriginById = deleteOriginById;
		self.getPageSearchProductTarget=getPageSearchProductTarget;

        self.getAllProductTarget = getAllProductTarget;
        self.getallByParentId = getallByParentId;
		self.checkDuplicateCode=checkDuplicateCode;

		function checkDuplicateCode(code) {
            var url = baseUrl + 'checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAllProductTarget(){
            var url = baseUrl + '/getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getallByParentId(parentId){
            var url = baseUrl + '/getallByParentId/'+parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteListId(ids, successCallback, errorCallback){
            var url = baseUrl + 'delete';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteProductTargetById(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function saveProductTarget(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function createProductTarget(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteOriginById(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteServiceByIds(ids, successCallback, errorCallback) {
            var url = baseUrl + 'delete/ids';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function saveOriginal(dto, successCallback, errorCallback) {
            var url = baseUrl + 'save';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getProducTargetById(id) {
            var url = baseUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getPageProductTarget(pageIndex, pageSize) {
            var url = baseUrl + 'page/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		
		function getPageSearchProductTarget(dto,pageIndex, pageSize, successCallback, errorCallback) {
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
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editProductTarget(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"product.edit" | translate}}</a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteProductTargetRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"product.delete" | translate}}</a>';
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
                {
                    field: '',
                    title: 'TT',
                    switchable: true,
                    visible: true,
                    formatter: _tableIndex,
                    cellStyle: _tableTextRight
                },
                {
                    field: 'state',
                    checkbox: true
                },
                {
                    field: '',
                    title: '{{"product.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
                {
                    field: 'code',
                    title: '{{"productTarget.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'name',
                    title: '{{"productTarget.name" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }, {
                    field: 'description',
                    title: '{{"product.description" | translate}}',
                    sortable: true,
                    // formatter: _tableInput,
                    cellStyle: _cellNowrap,
                    switchable: true,

                }
            ];
        }
    }
})();