(function () {
    'use strict';

    angular.module('Education.InjectionTime').service('InjectionTimeService', InjectionTimeService);

    InjectionTimeService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function InjectionTimeService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'injectiontime/';

        self.getTableDefinition = getTableDefinition;
        self.getAllInjectionTime = getAllInjectionTime;
        self.getInjectionTimeById = getInjectionTimeById;
        self.createInjectionTime = createInjectionTime;
        self.updateInjectionTime = updateInjectionTime;
        self.deleteInjectionTime = deleteInjectionTime;
        self.deleteInjectionTimes = deleteInjectionTimes;
        self.getAllInjectionTimeDto = getAllInjectionTimeDto;
		self.getPageSearchInjectionTime=getPageSearchInjectionTime;
		self.checkDuplicateCode=checkDuplicateCode;

		function checkDuplicateCode(code) {
            var url = baseUrl + 'checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAllInjectionTimeDto(){
            var url = baseUrl + 'getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function deleteInjectionTimes(ids, successCallback, errorCallback) {
            var url = baseUrl + 'delete';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteInjectionTime(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function updateInjectionTime(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function createInjectionTime(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getInjectionTimeById(id) {
            var url = baseUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllInjectionTime(pageIndex, pageSize) {
            var url = baseUrl + 'page/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getPageSearchInjectionTime(dto,pageIndex, pageSize, successCallback, errorCallback) {
            if(dto != null){

                var url = baseUrl + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

       
        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editInjectionTime(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"animalType.edit" | translate}}</a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteInjectionTimeRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"animalType.delete" | translate}}</a>';
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
                var _typeInjectTimeFormatter = function(value, row, index, field) {
                    let types = [
                        {
                            value: 0,
                            name: 'Tiêm vắc xin phòng bệnh'
                        },
            
                        {
                            value: 1,
                            name: 'Tiêm kháng sinh chữa bệnh'
                        },
                        {
                            value: 2,
                            name: 'Khác'
                        }
                    ];
                   if(value>-1 && value<=2)
                        return types[value].name;
                  
                  
                }

            return [
                {
                    field: 'state',
                    checkbox: true
                },
                {
                    field: '',
                    title: ' {{"injectionTime.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
            

                {
                    field: 'code',
                    title:'{{"injectionTime.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'name',
                    title: '{{"injectionTime.name" | translate}}' ,
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }, {
                    field: 'type',
                    title:'{{"injectionTime.type" | translate}}',
                    sortable: true,
                    formatter: _typeInjectTimeFormatter,
                    cellStyle: _operationColStyle,
                    switchable: true,

                }
            ];
        }
    }
})();