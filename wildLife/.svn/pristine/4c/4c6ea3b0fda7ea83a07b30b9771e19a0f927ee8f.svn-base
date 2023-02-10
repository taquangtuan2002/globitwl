(function () {
    'use strict';

    angular.module('Education.User').service('UserRoleService', UserRoleService);

    UserRoleService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities'
    ];

    function UserRoleService($http, $q, $filter, settings, utils) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;

        self.getTableDefinition = getTableDefinition;
        self.getListRole = getListRole;
        self.newRole = newRole;
        self.getRoleById = getRoleById;
        self.editRole = editRole;
        self.deleteRole = deleteRole;

        function getListRole(pageIndex, pageSize) {
            var url = baseUrl + 'roles/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function newRole(role, successCallback, errorCallback) {
            var url = baseUrl + 'roles/';
            return utils.resolveAlt(url, 'POST', null, role, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getRoleById(roleId) {
            var url = baseUrl + 'roles/' + roleId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function editRole(role, successCallback, errorCallback) {
            if(role != null){
                if(!angular.isUndefined(role.id)){
                    var url = baseUrl + 'roles/' + role.id;
                    return utils.resolveAlt(url, 'PUT', null, role, {
                        'Content-Type': 'application/json; charset=utf-8'
                    }, successCallback, errorCallback);
                }
            }
        }

        function deleteRole(roleId, successCallback, errorCallback) {
            var url = baseUrl + 'roles/' + roleId;
            return utils.resolveAlt(url, 'DELETE', null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editRole(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i></a>'
                    +  '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.viewRole(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    +  '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteRole(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i></a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space': 'nowrap'}
                };
            };

            var _cellNowrap2 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space': 'nowrap', 'width' : '120px'}
                };
            };

            var _objectFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                if (value == null) {
                    return '';
                }
                return value.name;
            };

            return [
                {
                    field: 'state',
                    checkbox: true,
                }
                ,{
                    field:'',
                    title: 'Thao tác',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _cellNowrap2
                }
                , {
                    field: 'name',
                    title: 'Tên quyền',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'description',
                    title: 'Mô tả',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
            ];
        }
    }
})();