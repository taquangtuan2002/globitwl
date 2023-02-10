/**
 * Created by bizic on 30/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.User').service('UserGroupService', UserGroupService);

    UserGroupService.$inject = [
        '$http',
        '$q',
        'settings',
        'Utilities'
    ];

    function UserGroupService($http, $q, settings, utils) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;

        self.getGroup = getGroup;
        self.getGroups = getGroups;
        self.saveGroup = saveGroup;
        self.deleteGroups = deleteGroups;

        self.getTableDefinition = getTableDefinition;

        function getGroup(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'usergroup/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getGroups(pageIndex, pageSize) {
            var url = baseUrl + "usergroup/list";
            url += '/' + ((pageIndex >= 0) ? pageIndex : 0);
            url += '/' + ((pageSize > 0) ? pageSize : 25);

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function saveGroup(group, successCallback, errorCallback) {
            var url = baseUrl + "usergroup";
            var method = 'POST';

            // if (group.id != null && group.id > 0) {
            //     method = 'PUT';
            // }

            return utils.resolveAlt(url, method, null, group, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteGroups(ids, successCallback, errorCallback) {
            if (!ids || ids.length <= 0) {
                return $q.when(null);
            }

            var url = baseUrl + 'usergroup';
            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark" href="#" data-ng-click="$parent.editGroup(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-10"></i>Sửa</a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space': 'nowrap'}
                };
            };

            var _cellActions = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space' : 'nowrap', 'width' : '10%'}
                }
            };

            return [
                {
                    field: 'state',
                    checkbox: true
                },
                {
                    field: '',
                    title: 'Thao tác',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _cellActions
                },
                {
                    field: 'name',
                    title: 'Tên nhóm người dùng',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'description',
                    title: 'Mô tả',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
            ]
        }
    }
})();