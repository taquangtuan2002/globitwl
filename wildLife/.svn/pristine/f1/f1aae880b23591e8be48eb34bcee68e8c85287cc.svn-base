(function () {
    'use strict';

    angular.module('Education.TechnicalStaff').service('TechnicalStaffService', TechnicalStaffService);

    TechnicalStaffService.$inject = [
        '$http',
        '$q',
        'settings',
        'Utilities',
        '$translate'
    ];

    function TechnicalStaffService($http, $q, settings, utils, $translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;

        //TechnicalStaff
        self.createTechnicalStaff = createTechnicalStaff;
        self.deleteTechnicalStaffById = deleteTechnicalStaffById;
        self.getPageTechnicalStaff = getPageTechnicalStaff;
        // self.updateTechnicalStaff = updateTechnicalStaff;
        self.getTechnicalStaffById = getTechnicalStaffById;
        // self.getListTechnicalStaffByName = getListTechnicalStaffByName;
        self.getPageSearchTechnicalStaff = getPageSearchTechnicalStaff;
        self.deleteServiceByIds = deleteServiceByIds;
        self.getAllTechnicalStaff = getAllTechnicalStaff;
        self.getAlTechnicalStaffByProvince = getAlTechnicalStaffByProvince;
        self.checkDuplicateCode = checkDuplicateCode;
        self.saveTechnicalStaff = saveTechnicalStaff;
        self.getAllByParentId = getAllByParentId;
        self.getAdminstrativeUnitsCity = getAdminstrativeUnitsCity;
        self.getChildrenByParentId = getChildrenByParentId;
        self.getCurrentUser = getCurrentUser;
        self.getAdministrativeUnitDtoByLoginUser = getAdministrativeUnitDtoByLoginUser;



        self.getTableDefinition = getTableDefinition;


        function createTechnicalStaff(technicalStaff, successCallback, errorCallback) {
            var url = baseUrl + 'technicaldtaff/create';
            return utils.resolveAlt(url, 'POST', null, technicalStaff, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteTechnicalStaffById(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'technicaldtaff/delete/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop)

        }

        function getCurrentUser() {
            var url = baseUrl + 'fms_users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAdministrativeUnitDtoByLoginUser() {
            var url = baseUrl + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getPageTechnicalStaff(pageIndex, pageSize) {
            var url = baseUrl + 'technicaldtaff/page/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function saveTechnicalStaff(id, technicalStaff, successCallback, errorCallback) {
            var url = baseUrl + 'technicaldtaff/update/' + id;
            return utils.resolveAlt(url, 'PUT', null, technicalStaff, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }


        function deleteServiceByIds(ids, successCallback, errorCallback) {
            var url = baseUrl + 'technicaldtaff/delete/ids';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }


        function getTechnicalStaffById(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'technicaldtaff/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        // function getListTechnicalStaffByName(name) {
        //     if (!name) {
        //         return $q.when(null);
        //     }

        //     var url = baseUrl + 'technicaldtaff/' + name;
        //     return utils.resolve(url, 'GET', angular.noop, angular.noop);
        // }

        function getPageSearchTechnicalStaff(dto, pageIndex, pageSize) {
            if (dto != null) {

                var url = baseUrl + 'technicaldtaff/searchDto/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, angular.noop, angular.noop);

            }
        }

        // tinh huyen 

        function getAllByParentId(parentId) {
            var url = baseUrl + 'administrative/getAllByParentId/' + parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAdminstrativeUnitsCity() {
            var url = baseUrl + 'administrative/getall/city';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getChildrenByParentId(parentId) {
            var url = Url + 'administrative/getChildrenByParentId/' + parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }



        function getAllTechnicalStaff() {
            var url = baseUrl + 'technicaldtaff/getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAlTechnicalStaffByProvince(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'technicaldtaff/getAlTechnicalStaffByProvince/' + id;
            return utils.resolveAlt(url, 'GET', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop)

        }


        function checkDuplicateCode(code) {
            var url = baseUrl + 'technicaldtaff/checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }



        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editTechnicalStaff(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> Sửa</a>' +
                    '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteTechnicalStaffRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash margin-right-5"></i>Xóa</a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _regionFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.name;
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
                    classes: 'text-center'
                };

            };

            return [{
                field: '',
                title: 'TT',
                switchable: true,
                visible: true,
                formatter: _tableIndex,
                cellStyle: _tableTextRight
            },
            {
                field: 'name',
                title: 'Tên Nhân Viên',
                switchable: false,
                sortable: true,
                visible: true,
                // formatter: _tableOperation,
                cellStyle: _cellNowrap
            }, {
                field: 'code',
                title: 'Mã nhân viên',
                switchable: false,
                sortable: true,
                visible: true,
                // formatter: _tableOperation,
                cellStyle: _cellNowrap
            }, {
                field: 'position',
                title: 'Chức vụ nhân viên',
                switchable: false,
                sortable: true,
                visible: true,
                // formatter: _tableOperation,
                cellStyle: _cellNowrap
            },
            {
                field: 'province',
                title: 'Tỉnh',
                switchable: false,
                sortable: true,
                visible: true,
                formatter: _regionFormatter,
                cellStyle: _cellNowrap
            },
            {

                field: '',
                title: 'Thao Tác',
                switchable: true,
                visible: true,
                formatter: _tableOperation,
                cellStyle: _operationColStyle

            },
            {
                field: 'state',
                checkbox: true
            }
            ]

        }
    }
})();