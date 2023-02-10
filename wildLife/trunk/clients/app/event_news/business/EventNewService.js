(function () {
    'use strict';

    angular.module('Education.EventNew').service('EventNewService', EventNewService);

    EventNewService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function EventNewService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'animal/';

        self.getTableDefinition = getTableDefinition;
       

        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteAnimalRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i></a>';
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
            var _typeFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.name;
            };
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '90px', 'text-align': 'center' }
                };
            };

            var _originalFormatter = function(value,row,index){
                if (!value) {
                    return '';
                }
                return value.name;
            }
            var _productTargetFormatter = function(value,row,index){
                if (!value) {
                    return '';
                }
                return value.name;
            }
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
                    field: 'state',
                    checkbox: true
                },
                {
                    field: 'code',
                    title: $translate.instant('animal.code'),
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'name',
                    title: $translate.instant('animal.name'),
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }, {
                    field: 'description',
                    title: $translate.instant('animal.description'),
                    sortable: true,
                    // formatter: _tableInput,
                    cellStyle: _cellNowrap,
                    switchable: true,

                },{
                    field: 'typeDto',
                    title: $translate.instant('animalType.name'),
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _typeFormatter,
                },{
                    field: 'originalDto',
                    title: $translate.instant('original.name'),
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _originalFormatter,
                },{
                    field: 'productTargetDto',
                    title: $translate.instant('product.name'),
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _productTargetFormatter,
                },
                {
                    field: '',
                    title: $translate.instant('product.action'),
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                }
            ];
        }
    }
})();