(function () {
    'use strict';

    angular.module('Education.AnimalType').service('AnimalTypeService', AnimalTypeService);

    AnimalTypeService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function AnimalTypeService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'animaltype/';

        self.getTableDefinition = getTableDefinition;
        self.getAllAnimalType = getAllAnimalType;
        self.getAnimalTypeById = getAnimalTypeById;
        self.createAnimal = createAnimal;
        self.updateAnimal = updateAnimal;
        self.deleteAnimal = deleteAnimal;
        self.deleteAnimals = deleteAnimals;
        self.getAllAnimalTypeDto = getAllAnimalTypeDto;
		self.getPageSearchAnimalType=getPageSearchAnimalType;
		self.checkDuplicateCode=checkDuplicateCode;

		function checkDuplicateCode(code) {
            var url = baseUrl + 'checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAllAnimalTypeDto(){
            var url = baseUrl + 'getalldtos';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function deleteAnimals(ids, successCallback, errorCallback) {
            var url = baseUrl + 'delete';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteAnimal(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function updateAnimal(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function createAnimal(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getAnimalTypeById(id) {
            var url = baseUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllAnimalType(pageIndex, pageSize) {
            var url = baseUrl + 'getall/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getPageSearchAnimalType(dto,pageIndex, pageSize, successCallback, errorCallback) {
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
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editAnimalType(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"animalType.edit" | translate}}</a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteAnimalTypeRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"animalType.delete" | translate}}</a>';
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
            var _tableIndex = function (value, row, index, field) {
                return index + 1;
            };
            var _tableTextRight = function (value, row, index, field) {
                return {
                    classes:'text-center'
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
                // {
                //     field: 'state',
                //     checkbox: true
                // },
                {
                    field: '',
                    title: ' {{"animalType.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
                {
                    field: 'code',
                    title:'{{"animalType.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'name',
                    title: '{{"animalType.name" | translate}}' ,
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }, {
                    field: 'description',
                    title:'{{"animalType.description" | translate}}',
                    sortable: true,
                    // formatter: _tableInput,
                    cellStyle: _cellNowrap,
                    switchable: true,

                }
            ];
        }
    }
})();