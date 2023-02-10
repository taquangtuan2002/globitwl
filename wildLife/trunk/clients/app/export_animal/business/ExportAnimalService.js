/**
 * Created by bizic on 30/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ExportAnimal').service('ExportAnimalService', ExportAnimalService);
    ExportAnimalService.$inject = [
        '$http',
        '$q',
        'settings',
        'Utilities',
        '$translate',
        '$filter'
    ];


    function ExportAnimalService($http, $q, settings, utils, $translate, $filter) {
        var self = this;
        var Url = settings.api.baseUrl + settings.api.apiPrefix;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'import_export/animal/';

        var type = -1;//kiểu phiếu: -1= xuất đàn

        //Region
        self.getExportAnimals = getExportAnimals;
        self.saveExportAnimal = saveExportAnimal;
        self.getExportAnimalById = getExportAnimalById;
        self.deleteExportAnimalById = deleteExportAnimalById;
        //self.confirmDelete = confirmDelete;
        self.getAll = getAll;
        self.createImportAnimal = createImportAnimal;
        self.updateImportAnimal = updateImportAnimal;
        self.getTableDefinition = getTableDefinition;
        self.getPageSearchExportAnimal = getPageSearchExportAnimal;
        self.save = save;
        self.getStream = getStream;
        self.getAdministrativeUnitDtoByLoginUser = getAdministrativeUnitDtoByLoginUser;
        self.getCurrentUser = getCurrentUser;
        self.getInventoryReportRemainQuantity = getInventoryReportRemainQuantity;

        function getInventoryReportRemainQuantity(searchDto) {
            var url = baseUrl + 'getInventoryReportRemainQuantity';

            return utils.resolveAlt(url, 'POST', null, searchDto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        // Region
        function getCurrentUser() {
            var url = Url + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAdministrativeUnitDtoByLoginUser() {
            var url = Url + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getStream(searchFarmDto) {
            console.log("RUNNING");
            var deferred = $q.defer();

            $http({
                url: settings.api.baseUrl + settings.api.apiPrefix + 'file/downloadExportAnimal',
                method: "POST",//you can use also GET or POST
                data: searchFarmDto,
                headers: { 'Content-type': 'application/json' },
                responseType: 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                    console.error("ERROR");
                    deferred.reject(data);
                });

            return deferred.promise;
        };
        function getAll() {
            var url = baseUrl + '/getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        //function confirmDelete(id) {
        //    if (!id) {
        //        return $q.when(null);
        //    }

        //    var url = baseUrl + 'exportanimal/delete/' + id;
        //    return utils.resolveAlt(url, 'DELETE', null, null, {
        //        'Content-Type': 'application/json; charset=utf-8'
        //    }, angular.noop, angular.noop)

        //}

        function deleteExportAnimalById(id) {
            if (!id) {
                return $q.when(null);
            }
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getExportAnimalById(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'get/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function getExportAnimals(pageIndex, pageSize) {

            var url = baseUrl + 'page/' + type;
            url += '/' + ((pageIndex > 0) ? pageIndex : 1);
            url += '/' + ((pageSize > 0) ? pageSize : 10);

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function createImportAnimal(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function updateImportAnimal(id, dto) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function saveExportAnimal(exportAnimal, successCallback, errorCallback) {
            var url = baseUrl + 'exportanimal/create';
            return utils.resolveAlt(url, 'POST', null, exportAnimal, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function save(exportAnimal) {
            var url = baseUrl + 'create';
            return utils.resolveAlt(url, 'POST', null, exportAnimal, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getPageSearchExportAnimal(dto, pageIndex, pageSize, successCallback, errorCallback) {
            if (dto != null) {

                var url = baseUrl + 'searchDto/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }


        function getTableDefinition() {//cái này là view danh sách xuất đàn

            var _tableOperation = function (value, row, index) {
                return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#/exportAnimalDetail/' + "'" + row.id + "'" + '" data-ng-click="$parent.editExportAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"exportAnimal.edit" | translate}}</a>' +
                    '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteExportAnimal(' + "'" + row.id + "'" + ')"><i class="fa fa-trash margin-right-5"></i> {{"exportAnimal.delete" | translate}}</a>';
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
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _exportAnimalFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.name;
            }

            var _cellState = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '1%' }
                };
            };

            var _cellActions = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '10%' }
                }
            };
            var _numberFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return $filter('number')(value, 0);
            };
            var _typeFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                else if (value == 1) {
                    return 'Xuất bán';
                } else if (value == 3) {
                    return 'Điều chuyển loại';
                }
                else if (value == 2) {
                    return 'Xuất khác';
                } else
                    return '';
            };
            var _importQuantityFormatter = function (value, row, index) {
                if (value) {
                    return $filter('number')(value.quantity, 0);
                }
                return '';
            };
            var _importAnimalFormatter = function (value, row, index) {
                if (value) {
                    return $filter('number')(value.remainQuantity, 0);
                }
                return '';
            };
            var _batchCodeFormatter = function (value, row, index) {
                if (value) {
                    return value.batchCode;
                }
                return '';
            };
            var _tableIndex = function (value, row, index, field) {
                return index + 1;
            };
            var _tableTextCenter = function (value, row, index, field) {
                return {
                    classes:'text-center'
                };
            };
            var _tableTextRight = function (value, row, index, field) {
                return {
                    classes:'text-right'
                };
            };

            var _createDateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                } else {
                    var year = value[0];
                    var month = value[1];
                    var date = value[2];
                    var hour = value[3];
                    var minutes = value[4];
                    var seconds = value[5];
                    return date + '/' + month + '/' + year + ' | ' + hour + ':' + minutes + ':' + seconds;
                }

            };
            return [
                // {
                //     field: 'state',
                //     checkbox: true,
                //     cellStyle: _cellState
                // }, 
                {
                    field: '',
                    title: 'TT',
                    switchable: true,
                    visible: true,
                    formatter: _tableIndex,
                    cellStyle: _tableTextCenter
                },
                {
                    field: '',
                    title: '{{"exportAnimal.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _cellActions
                    // },{
                    //     field: 'voucherCode',
                    //     title: '{{"exportAnimal.voucherCode" | translate}}',
                    //     sortable: true,
                    //     switchable: false,
                    //     cellStyle: _cellNowrap2
                },{
                    field: 'animal.name',
                    title: 'Loài nuôi',
                    sortable: true,
//                    cellStyle: _styleAnimalName,
//                    formatter:_formatterOtherName,
                    switchable: true,
                }
                ,{
                    field: 'animal.otherName',
                    title: 'Tên khác',
                    sortable: true,
//                    cellStyle: _styleAnimalName,
//                    formatter:_formatterOtherName,
                    switchable: true,
                }
                ,{
                    field: 'animal.scienceName',
                    title: 'Tên khoa học',
                    sortable: true,
//                    cellStyle: _styleAnimalName,
//                    formatter:_formatterOtherName,
                    switchable: true,
                }, {
                    field: 'batchCode',
                    title: '{{"exportAnimal.batchCode" | translate}}',
                    switchable: true,
                    visible: true,
                    cellStyle: _cellNowrap2,
                    // formatter: _batchCodeFormatter
                },
                //, {
                //    field: 'dateImport',
                //    title:  '{{"exportAnimal.dateImport" | translate}}',
                //    switchable: true,
                //    visible: true,
                //    cellStyle: _cellNowrap2,
                //    formatter: _dateFormatter
                //},
                {
                    field: 'quantity',
                    title: '{{"exportAnimal.quantity" | translate}}',
                    switchable: true,
                    visible: true,
                    cellStyle: _tableTextRight,
                    formatter: _numberFormatter
                }
                // , {
                //     field: 'amount',
                //     title: '{{"exportAnimal.amount" | translate}}',
                //     switchable: true,
                //     visible: true,
                //     cellStyle: _cellNowrap2,
                //     formatter: _numberFormatter
                // }
                // , {
                //     field: 'importAnimal',
                //     title: '{{"exportAnimal.importQuantity"| translate}}',
                //     sortable: true,
                //     switchable: false,
                //     cellStyle: _cellNowrap,
                //     formatter: _importQuantityFormatter
                // }
                ,
                // {
                //     field: 'importAnimal',
                //     title: '{{"importAnimal.remainQuantity"| translate}}',
                //     sortable: true,
                //     switchable: false,
                //     cellStyle: _cellNowrap,
                //     formatter: _importAnimalFormatter
                // },
                {
                    field: 'dateExport',
                    title: '{{"exportAnimal.dateExport" | translate}}',
                    switchable: true,
                    visible: true,
                    cellStyle: _cellNowrap2,
                    formatter: _dateFormatter
                }
                // , {
                //     field: 'description',
                //     title: '{{"exportAnimal.reasonExport" | translate}}',
                //     switchable: true,
                //     visible: true,
                //     cellStyle: _cellNowrap2
                // }
                // , {
                //     field: 'exportType',
                //     title: '{{"exportAnimal.type" | translate}}',
                //     switchable: true,
                //     visible: true,
                //     cellStyle: _cellNowrap2,
                //     formatter: _typeFormatter
                // }
                // , {
                //     field: 'exportReason',
                //     title: '{{"exportAnimal.exportReason" | translate}}',
                //     switchable: true,
                //     visible: true,
                //     cellStyle: _cellNowrap2
                // }, {
                //     field: 'buyerName',
                //     title: '{{"exportAnimal.buyerName" | translate}}',
                //     switchable: true,
                //     visible: true,
                //     cellStyle: _cellNowrap2
                // }
                //, {
                //    field: 'buyerAdress',
                //    title:  '{{"exportAnimal.buyerAdress" | translate}}',
                //    switchable: true,
                //    visible: true,
                //    cellStyle: _cellNowrap2
                //}
                , {
                    field: 'farm',
                    title: '{{"farm.name" | translate}}',
                    switchable: true,
                    visible: true,
                    cellStyle: _cellNowrap2,
                    formatter: _exportAnimalFormatter
                }, {
                    field: 'animal',
                    title: '{{"animal.name" | translate}}',
                    switchable: true,
                    visible: true,
                    cellStyle: _cellNowrap2,
                    formatter: _exportAnimalFormatter
                }, {
                    field: 'created_by',
                    title: '{{"farm.createBy" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'create_date',
                    title: '{{"farm.create_date" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _createDateFormatter

                },
                {
                    field: 'farm.ownerPhoneNumber',
                    title: '{{"farm.ownerPhoneNumber" | translate}}',
                    sortable: true,
                    switchable: true,
                    cellStyle: _cellNowrap,

                },
            ]
        }

        //---------Popup Farm-------//
        self.getTableDefinitionFarm = getTableDefinitionFarm;
        self.getPageSearchFarm = getPageSearchFarm;
        var baseUrlFarm = settings.api.baseUrl + settings.api.apiPrefix + 'farm/';

        function getPageSearchFarm(searchFarmDto, pageIndex, pageSize, successCallback, errorCallback) {
            if (searchFarmDto != null) {

                var url = baseUrlFarm + 'searchDto/' + pageIndex + '/' + pageSize;
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
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _formatterName = function (value, row, index, field) {
                if (value && value.name) {
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


        //---------Popup Batch Code-------//cái này là lúc view danh sách chỗ chọn số lô
        self.getTableDefinitionCode = getTableDefinitionCode;
        self.getPageSearchImportAnimal = getPageSearchImportAnimal;
        var baseUrlImport = settings.api.baseUrl + settings.api.apiPrefix + 'import_export/animal/';

        function getPageSearchImportAnimal(dto, pageIndex, pageSize, successCallback, errorCallback) {
            if (dto != null) {

                var url = baseUrlImport + 'searchDto/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

        function getTableDefinitionCode() {

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
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _formatterName = function (value, row, index, field) {
                if (value && value.name) {
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
            var _numberFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return $filter('number')(value, 0);
            };
            var _createDateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                } else {
                    var year = value[0];
                    var month = value[1];
                    var date = value[2];
                    var hour = value[3];
                    var minutes = value[4];
                    var seconds = value[5];
                    return date + '/' + month + '/' + year + ' | ' + hour + ':' + minutes + ':' + seconds;
                }

            };
            return [
                {
                    field: 'state',
                    checkbox: true,
                }

                , {
                    field: 'batchCode',
                    title: '{{"importAnimal.batchCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,

                }
                , {
                    field: 'animal',
                    title: '{{"importAnimal.animal.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName,
                    switchable: true,
                }, {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName,
                    switchable: true,
                }, {
                    field: 'quantity',
                    title: '{{"importAnimal.quantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }
                // , {
                //     field: 'remainQuantity',
                //     title: '{{"importAnimal.remainQuantity"| translate}}',
                //     sortable: true,
                //     switchable: false,
                //     cellStyle: _cellNowrap,
                //     formatter: _numberFormatter
                // }
                , {
                    field: 'dateImport',
                    title: '{{"importAnimal.dateImport" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _dateFormatter

                }
                ,
                {
                    field: 'created_by',
                    title: '{{"farm.createBy" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    // formatter: _numberFormat
                },
                {
                    field: 'create_date',
                    title: '{{"farm.create_date" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _createDateFormatter

                },


            ];
        }
    }
})();