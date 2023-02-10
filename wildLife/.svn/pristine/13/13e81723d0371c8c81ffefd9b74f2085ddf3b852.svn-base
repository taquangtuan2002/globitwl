(function () {
    'use strict';

    angular.module('Education.Bran').service('BranService', BranService);

    BranService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function BranService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'bran/';

        self.getTableDefinition = getTableDefinition;
        self.getAllBran = getAllBran;
        self.getBranById = getBranById;
        self.createBran = createBran;
        self.updateBran = updateBran;
        self.deleteBran = deleteBran;
        self.deleteBrans = deleteBrans;
        self.getAllBranDto = getAllBranDto;
		self.getPageSearchBran=getPageSearchBran;
		self.checkDuplicateCode=checkDuplicateCode;

		function checkDuplicateCode(code) {
            var url = baseUrl + 'checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAllBranDto(){
            var url = baseUrl + 'getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function deleteBrans(ids, successCallback, errorCallback) {
            var url = baseUrl + 'delete';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteBran(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function updateBran(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function createBran(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getBranById(id) {
            var url = baseUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllBran(pageIndex, pageSize) {
            var url = baseUrl + 'page/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getPageSearchBran(dto,pageIndex, pageSize, successCallback, errorCallback) {
            if(dto != null){

                var url = baseUrl + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

       
        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editBran(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"animalType.edit" | translate}}</a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteBranRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"animalType.delete" | translate}}</a>';
            };
          

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
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
                    title: ' {{"bran.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
            

                {
                    field: 'code',
                    title:'{{"bran.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'name',
                    title: '{{"bran.name" | translate}}' ,
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }, {
                    field: 'description',
                    title:'{{"bran.description" | translate}}',
                    sortable: true,
                    cellStyle: _operationColStyle,
                    switchable: true,

                }
            ];
        }
    }
})();