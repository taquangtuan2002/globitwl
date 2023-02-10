(function () {
    'use strict';

    angular.module('Education.InjectionPlant').service('InjectionPlantService', InjectionPlantService);

    InjectionPlantService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function InjectionPlantService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'injectionplant/';

        self.getTableDefinition = getTableDefinition;
        self.getAllInjectionPlant = getAllInjectionPlant;
        self.getPage = getPage;
        self.getInjectionPlantById = getInjectionPlantById;
        self.createInjectionPlant = createInjectionPlant;
        self.updateInjectionPlant = updateInjectionPlant;
        self.deleteInjectionPlant = deleteInjectionPlant;
        self.deleteInjectionPlants = deleteInjectionPlants;


	
        // function getAllInjectionPlantDto(){
        //     var url = baseUrl + 'getall';
        //     return utils.resolve(url, 'GET', angular.noop, angular.noop);
        // }
        function getPage(pageIndex, pageSize){
            var url = baseUrl + 'page/'+ pageIndex +'/'+ pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteInjectionPlant(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteInjectionPlants(ids, successCallback, errorCallback) {
            var url = baseUrl + 'delete';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function updateInjectionPlant(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function createInjectionPlant(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getInjectionPlantById(id) {
            var url = baseUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllInjectionPlant(pageIndex, pageSize) {
            var url = baseUrl + 'getall/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		

        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editInjectionPlant(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"injectionPlant.edit" | translate}}</a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteInjectionPlantRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"injectionPlant.delete" | translate}}</a>';
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

           

            return [
                {
                    field: 'state',
                    checkbox: true
                },
                {
                    field: '',
                    title: '{{"injectionPlant.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
               
                {
                    field: 'injectionDate',
                    title:'{{"injectionPlant.injectionDate" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _dateFormatter
                },
                {
                    field: 'drug',
                    title: ' {{"injectionPlant.drug" | translate}}',
                    switchable: true,
                    visible: true,
                    //formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
               
                 {
                    field: 'injectionRound',
                    title: '{{"injectionPlant.injectionRound" | translate}}' ,
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'code',
                    title:'{{"injectionPlant.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
            ];
        }
    }
})();