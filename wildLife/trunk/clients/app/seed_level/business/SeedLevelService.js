/**
 * Created by bizic on 30/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.FmsSeedLevel').service('SeedLevelService', SeedLevelService);

    SeedLevelService.$inject = [
        '$http',
        '$q',
        'settings',
        'Utilities',
        '$translate'
    ];

    function SeedLevelService($http, $q, settings, utils, $translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;


        //Region
        self.getSeedLevels = getSeedLevels;
        self.saveSeedLevel = saveSeedLevel;
        self.getSeedLevel = getSeedLevel;
        self.deleteSeedLevelId = deleteSeedLevelId;
        self.confirmDelete = confirmDelete;
        self.getAll = getAll;
		self.getPageSearchSeedLevel=getPageSearchSeedLevel;

        self.getTableDefinition = getTableDefinition;
        self.getUser = getUser;
        self.saveUser = saveUser;
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
		self.checkDuplicateCode=checkDuplicateCode;
		

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
        // Region
		function checkDuplicateCode(code) {
            var url = baseUrl + 'seed_level/checkCode';
            return utils.resolveAlt(url, 'POST', null, code, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop)
        }
        function getAll() {
            var url = baseUrl + 'seed_level/getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function confirmDelete(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'seed_level/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop)

        }

        function deleteSeedLevelId(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'seed_level/delete/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop)
        }

        function getSeedLevel(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'seed_level/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function getSeedLevels(pageIndex, pageSize) {

            var url = baseUrl + 'seed_level';
            url += '/' + ((pageIndex > 0) ? pageIndex : 1);
            url += '/' + ((pageSize > 0) ? pageSize : 10);

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function saveSeedLevel(dto, successCallback, errorCallback) {
            var url = baseUrl + 'seed_level/create';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		function getPageSearchSeedLevel(dto,pageIndex, pageSize, successCallback, errorCallback) {
            if(dto != null){

                var url = baseUrl + 'seed_level/searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }


        function getUser(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'users/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function saveUser(user, successCallback, errorCallback) {
            var url = baseUrl + 'users';

            user.active = user.active == null ? 0 : user.active;

            return utils.resolveAlt(url, 'POST', null, user, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
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
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editSeedLevel(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"region.edit" | translate}}</a>' +
                    '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteSeedLevel(' + "'" + row.id + "'" + ')"><i class="fa fa-trash margin-right-5"></i> {{"region.delete" | translate}}</a>';
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
            var _cellNowrap3 = function(value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'pre-line', 'max-width': '120px' }
                };
            };

            var _dateFormatter = function(value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };

            var _getIndex = function(value, row, index) {
                if (typeof index == "number") {
                    return index+1;
                }
                return '';
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

            return [
                // {
                //     field: 'state',
                //     checkbox: true,
                //     cellStyle: _cellState
                // },
                {
                    field: 'state',
                    formatter: _getIndex,
                    cellStyle: {css: {"text-align":"center"}}
                },
                {
                    field: '',
                    title: '{{"region.action" | translate}}',
                    sortable: false,
                    visible: true,
                    switchable: true,
                    formatter: _tableOperation,
                    cellStyle: _cellNowrap2
                }, {
                    field: 'code',
                    title: '{{"region.codeSeedLevel" | translate}}',
                    sortable: false,
                    switchable: false,
                    cellStyle: _cellNowrap2
                }, {
                    field: 'name',
                    title: '{{"region.nameSeedLevel" | translate}}',
                    switchable: true,
                    visible: true,
                    cellStyle: _cellNowrap3
                }, {
                    field: 'level',
                    title: '{{"region.level" | translate}}',
                    sortable: false,
                    switchable: true,
                    cellStyle: {css: {"text-align":"center"}}
                } // {
                // field: 'acreage',
                // title: 'Di???n t??ch',
                // sortable: true,  
                // switchable: true,
                // visible: true,
                // cellStyle: _cellNowrap2
                //}, 
                
            ]
        }
    }
})();