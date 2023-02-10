(function () {
    'use strict';

    angular.module('Education.UserFileUpload').service('UserFileUploadService', UserFileUploadService);

    UserFileUploadService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function UserFileUploadService($http, $q, $filter, settings, utils, $translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'user_file_upload/';
        var URL = settings.api.baseUrl + settings.api.apiPrefix;
        self.getTableDefinition = getTableDefinition;
        self.getPageUserFileUpload = getPageUserFileUpload;
        self.getUserFileUploadById = getUserFileUploadById;
        self.createUserFileUpload = createUserFileUpload;
        self.updateUserFileUpload = updateUserFileUpload;
        self.deleteUserFileUpload = deleteUserFileUpload;
        self.getCurrentUser = getCurrentUser;
        self.getAdministrativeUnitDtoByLoginUser = getAdministrativeUnitDtoByLoginUser;
        self.getAdminstrativeUnitsCity = getAdminstrativeUnitsCity;
        self.getAllByParentId = getAllByParentId;
        self.getFileAttachmentUploadById = getFileAttachmentUploadById;
        self.deleteFileAttchmentUploadById = deleteFileAttchmentUploadById;

        function getAdminstrativeUnitsCity() {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'administrative/getall/city';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAllByParentId(parentId) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'administrative/getAllByParentId/' + parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getCurrentUser() {
            var url = URL + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAdministrativeUnitDtoByLoginUser() {
            var url = URL + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function deleteUserFileUpload(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function updateUserFileUpload(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function createUserFileUpload(dto, successCallback, errorCallback) {
            var url = baseUrl + 'save';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function getPageUserFileUpload(searchDto, successCallback, errorCallback) {
            var url = baseUrl + 'searchByPage';
            return utils.resolveAlt(url, 'POST', null, searchDto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function getUserFileUploadById(id) {
            var url = baseUrl + 'getUserFileUploadById/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getFileAttachmentUploadById(id) {
            var url = baseUrl + 'getFileAttchmentUploadById/' + id;
            return $http.get(url, { responseType: 'arraybuffer' });
        }
        function deleteFileAttchmentUploadById(id) {
            var url = baseUrl + 'deleteFileAttchmentUploadById/' + id;
            return utils.resolve(url, 'DELETE', angular.noop, angular.noop);
        }

        
        function getTableDefinition() {
            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editUserFileUpload(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"UserFileUpload.edit" | translate}}</a>'
                + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.viewUserFileUploadRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i> {{"UserFileUpload.view" | translate}}</a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteUserFileUploadRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"UserFileUpload.delete" | translate}}</a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _cellNowrapBreak = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'break-spaces', 'min-width': '120px', 'max-width': '150px' }
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
            var _tableIndex = function (value, row, index, field) {
                return index + 1;
            };
            var _tableTextRight = function (value, row, index, field) {
                return {
                    classes: 'text-center'
                };
            };
            var administrativeUnitFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.name;
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
                    field: 'province',
                    title: 'Tỉnh/Thành phố',
                    sortable: true,
                    switchable: false,
                    formatter: administrativeUnitFormatter,
                    cellStyle: _cellNowrap,
                }
                ,
                {
                    field: 'district',
                    title: 'Quận/Huyện',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: administrativeUnitFormatter,
                }
                ,
                {
                    field: 'ward',
                    title: 'Phường/Xã',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: administrativeUnitFormatter,
                }
                ,
                {
                    field: 'title',
                    title: 'Tiêu đề',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'description',
                    title: 'Mô tả',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                },
                {
                    field: 'organizationName',
                    title: 'Tên cơ quan/tổ chức',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrapBreak
                }
            ];
        }
    }
})();