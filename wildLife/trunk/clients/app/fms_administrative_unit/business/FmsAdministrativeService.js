/**
 * Created by bizic on 30/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.FmsAdministrative').service('FmsAdministrativeService', FmsAdministrativeService);

    FmsAdministrativeService.$inject = [
        '$http',
        '$q',
        'settings',
        'Utilities',
        '$translate'
    ];

    function FmsAdministrativeService($http, $q, settings, utils, $translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;

        //FmsAdministrative
        self.getListAdministratives = getListAdministratives;
        self.getAdministrativeUnitById = getAdministrativeUnitById;
        //self.updateFmsAdministrative = updateFmsAdministrative;
        self.updateFmsAdministrative = updateFmsAdministrative;
        self.getAll = getAll;
        self.getTreeData = getTreeData;
        self.createFmsAdministrative = createFmsAdministrative;
        self.getAllCity = getAllCity;

        self.confirmDelete = confirmDelete;


        self.getTableDefinition = getTableDefinition;
        // self.getUsers = getUsers;
        // self.getUser = getUser;
        //self.saveUser = saveUser;
        self.deleteUsers = deleteUsers;
        self.getAllRoles = getAllRoles;
        self.getAllGroups = getAllGroups;
        self.usernameDuplicates = usernameDuplicates;
        self.emailDuplicates = emailDuplicates;
        self.changePassword = changePassword;
        self.changePasswordSelf = changePasswordSelf;
        self.passwordValid = passwordValid;
        self.cropProfilePhoto = cropProfilePhoto;
        self.getUserByUsername = getUserByUsername;
        self.getUserByEmail = getUserByEmail;
        self.findUserByUserName = findUserByUserName;
        self.getPageSearchFmsAdministrative = getPageSearchFmsAdministrative;
        self.checkDuplicateCode = checkDuplicateCode;
        self.getTreeData = getTreeData;
        self.getChildrenByParentId = getChildrenByParentId;
        self.getAllCityByRegion = getAllCityByRegion;
        self.getAllByParentId = getAllByParentId;
        self.getListAdministrativeByCodeOrName = getListAdministrativeByCodeOrName;

        function getAllByParentId(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'administrative/getAllByParentId/' + id;
            return utils.resolveAlt(url, 'GET', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop)

        }
        /////////
        function checkDuplicateCode(code) {
            var url = baseUrl + 'administrative/checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        //AdminstrativeUnit
        function getTreeData(pageIndex, pageSize) {
            var url = baseUrl + 'administrative/tree/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function confirmDelete(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'administrative/delete/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop)

        }



        function getAll() {
            var url = baseUrl + 'administrative/getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllCity() {
            var url = baseUrl + 'administrative/getall/city';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getListAdministratives(Id, pageIndex, pageSize) {
            console.log(pageIndex, pageSize);
            var url = baseUrl + 'administrative/' + Id;
            url += '/' + ((pageIndex > 0) ? pageIndex : 1);
            url += '/' + ((pageSize > 0) ? pageSize : 10);

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getListAdministrativeByCodeOrName(Id, code, pageIndex, pageSize) {
            console.log("service" + pageIndex, pageSize);
            var url = baseUrl + 'administrative/nameCode/' + Id + '/' + code;
            url += '/' + ((pageIndex > 0) ? pageIndex : 1);
            url += '/' + ((pageSize > 0) ? pageSize : 10);

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAdministrativeUnitById(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'administrative/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function createFmsAdministrative(fmsAdministrative, successCallback, errorCallback) {
            var url = baseUrl + 'administrative/create';
            return utils.resolveAlt(url, 'POST', null, fmsAdministrative, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function updateFmsAdministrative(id, fmsAdministrative, successCallback, errorCallback) {
            var url = baseUrl + 'administrative/update/' + id;
            return utils.resolveAlt(url, 'PUT', null, fmsAdministrative, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getPageSearchFmsAdministrative(dto, pageIndex, pageSize, successCallback, errorCallback) {
            if (dto != null) {

                var url = baseUrl + 'administrative/searchDto/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

        function getTreeData(pageIndex, pageSize) {
            var url = baseUrl + 'administrative/tree/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getChildrenByParentId(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'administrative/getChildrenByParentId/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllCityByRegion(regionId) {
            if (!regionId) {
                return $q.when(null);
            }

            var url = baseUrl + 'administrative/getCityByRegion/' + regionId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function emailDuplicates(user) {

            if (!user.id) {
                user.id = 0;
            }

            var url = baseUrl + 'user/duplicate/email';

            return utils.resolveAlt(url, 'POST', null, user, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function usernameDuplicates(user) {

            if (!user.id) {
                user.id = 0;
            }

            var url = baseUrl + 'user/duplicate/username';

            return utils.resolveAlt(url, 'POST', null, user, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }



        function getAllRoles() {
            var url = baseUrl + 'roles/all';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllGroups() {
            var url = baseUrl + 'usergroup/all';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }




        function deleteUsers(dtos, successCallback, errorCallback) {
            if (!dtos || dtos.length <= 0) {
                return $q.when(null);
            }

            var url = baseUrl + 'user';
            return utils.resolveAlt(url, 'DELETE', null, dtos, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function changePassword(user, successCallback, errorCallback) {
            var url = baseUrl + 'users/password';

            return utils.resolveAlt(url, 'PUT', null, user, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function changePasswordSelf(user, successCallback, errorCallback) {
            var url = baseUrl + 'users/password/self';

            return utils.resolveAlt(url, 'PUT', null, user, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function passwordValid(passwordObj) {
            var url = baseUrl + 'users/password/valid';

            return utils.resolveAlt(url, 'POST', null, passwordObj, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function cropProfilePhoto(cropper) {
            var url = baseUrl + 'users/photo/crop';

            return utils.resolveAlt(url, 'POST', null, cropper, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getUserByUsername(username) {
            if (!username) {
                return $q.when(null);
            }

            var url = baseUrl + 'users?username=' + username;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getUserByEmail(email) {
            if (!email) {
                return $q.when(null);
            }

            var url = baseUrl + 'users/e/' + email;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function findUserByUserName(username, pageIndex, pageSize) {
            var url = baseUrl + 'users/username/' + username + '/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getTableDefinition() {

            var _tableOperation = function(value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editFmsAdministrative(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"administrativeUnit.edit" | translate}}</a>' +
                    '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteFmsAdministrative(' + "'" + row.id + "'" + ')"><i class="fa fa-trash margin-right-5"></i>  {{"administrativeUnit.delete" | translate}}</a>';
            };

            var _cellNowrap = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'max-width': '120px' }
                };
            };
            var _regionFormatter = function(value, row, index) {
                if (!value) {
                    return '';
                }
                return value.name;
            };
            var _dateFormatter = function(value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };

            var _cellState = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '1%' }
                };
            };

            var _cellActions = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '10%' }
                }
            };

            return [{
                    field: 'state',
                    checkbox: true,
                    cellStyle: _cellState
                }
                /*, {
                    field: '',
                    title: '{{"administrativeUnit.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _cellActions
                }*/
                , {
                    field: 'code',
                    title: '{{"administrativeUnit.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap2
                }, {
                    field: 'name',
                    title: '{{"administrativeUnit.name" | translate}}',
                    switchable: false,
                    visible: true,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'parentDto',
                    title: '{{"administrativeUnit.parent" | translate}}',
                    switchable: false,
                    visible: true,
                    cellStyle: _cellNowrap,
                    formatter: _regionFormatter
                },
                /*,
                {
                    field: 'latitude',
                    title: '{{"administrativeUnit.latitude" | translate}}',
                    switchable: false,
                    visible: true,
                    cellStyle: _cellNowrap2
                },
                {
                    field: 'mapCode',
                    title: '{{"administrativeUnit.mapCode" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap2
                },
                {
                    field: 'gMapX',
                    title: '{{"administrativeUnit.gMapX" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap2
                },
                {
                    field: 'gMapY',
                    title: '{{"administrativeUnit.gMapY" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap2
                },
                {
                    field: 'totalAcreage',
                    title: '{{"administrativeUnit.totalAcreage" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap2
                },*/
                // {
                //     field: 'level',
                //     title: 'Level',
                //     sortable: true,
                //     switchable: true,
                //     cellStyle: _cellNowrap2
                // }, 
                {
                    field: 'regionDto',
                    title: '{{"region.name" | translate}}',
                    sortable: true,
                    switchable: false,
                    visible: true,
                    cellStyle: _cellNowrap,
                    formatter: _regionFormatter
                }
            ]

        }
    }
})();