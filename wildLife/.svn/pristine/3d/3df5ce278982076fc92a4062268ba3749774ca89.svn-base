(function () {
    'use strict';

    angular.module('Education.BiologicalClass').service('BiologicalClassService', BiologicalClassService);

    BiologicalClassService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function BiologicalClassService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'biologicalClass';

        var listType = [
            {id: 1, name: "Lớp"},
            {id: 2, name: "Bộ"},
            {id: 3, name: "Họ"}
        ]
          
        self.getTableDefinition = getTableDefinition;
        self.deleteById = deleteById;
        self.saveOrUpdate = saveOrUpdate;
        self.getById = getById;
        self.searchByPage = searchByPage;
        self.getTableSelectDefinition = getTableSelectDefinition;

		// function checkDuplicateCode(code) {
        //     var url = baseUrl + 'checkCode/' + code;
        //     return utils.resolve(url, 'GET', angular.noop, angular.noop);
        // }

        function deleteById(id, successCallback, errorCallback) {
            var url = baseUrl + "/" + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function saveOrUpdate(entity, successCallback, errorCallback) {
            var url = baseUrl;

            return utils.resolveAlt(url, 'POST', null, entity, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getById(id) {
            var url = baseUrl + "/" + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

		function searchByPage(dto, successCallback, errorCallback) {
            if(dto != null){
                var url = baseUrl + '/searchByPage';
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

        function getTableDefinition() {
            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.openEditForm(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> Sửa</a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteById(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> Xoá</a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
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
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '90px', 'text-align': 'center' }
                };
            };
            var _cellState = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '1%' }
                };
            };

            let _fomatType = (value) => {
                let type = listType.filter(e => e.id == value);
                return type[0]? type[0].name : '';
            }

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
                    field: '',
                    title: 'Thao tác',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
                {
                    field: 'name',
                    title: 'Tên tiếng việt',
                    switchable: true,
                    visible: true,
                    cellStyle: _cellNowrap,
                },
                {
                    field: 'sci',
                    title:'Tên latin',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }, 
                {
                    field: 'code',
                    title: 'Chữ cái đầu' ,
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'type',
                    title:'Loại',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _fomatType,
                    switchable: true,
                }
            ];
        }

        function getTableSelectDefinition() {
            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.openEditForm(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> Sửa</a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteById(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> Xoá</a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
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
            let _fomatType = (value) => {
                let type = listType.filter(e => e.id == value);
                return type[0]? type[0].name : '';
            }
            var _cellState = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '1%' }
                };
            };

            return [
                {
                    field: 'state',
                    checkbox: true,
                    cellStyle: _cellState
                },
                {
                    field: 'name',
                    title: 'Tên tiếng việt',
                    switchable: true,
                    visible: true,
                    cellStyle: _cellNowrap,
                },
                {
                    field: 'sci',
                    title:'Tên latin',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }, 
                {
                    field: 'code',
                    title: 'Chữ cái đầu' ,
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'type',
                    title:'Loại',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _fomatType,
                    switchable: true,
                }
            ];
        }
    }
})();