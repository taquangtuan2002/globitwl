/**
 * Created by bizic on 30/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.WaterSource').service('WaterSourceService', WaterSourceService);

    WaterSourceService.$inject = [
        '$http',
        '$q',
        'settings',
        'Utilities',
        '$translate'
    ];


    function WaterSourceService($http, $q, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;


        //Region
        self.getWaterSources = getWaterSources;
        self.saveWaterSource = saveWaterSource;
        self.getWaterSourceById = getWaterSourceById;
        self.deleteWaterSourceById = deleteWaterSourceById;
        self.confirmDelete = confirmDelete;
        self.getAll = getAll;
		self.checkDuplicateCode=checkDuplicateCode;

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
		self.getPageSearchWaterSource=getPageSearchWaterSource;
		

		
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
            var url = baseUrl + 'watersource/checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getAll() {
            var url = baseUrl + 'watersource/getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function confirmDelete(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'watersource/delete/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop)

        }

        function deleteWaterSourceById(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'watersource/delete/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop)
        }

        function getWaterSourceById(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'watersource/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function getWaterSources(pageIndex, pageSize) {

            var url = baseUrl + 'watersource';
            url += '/' + ((pageIndex > 0) ? pageIndex : 1);
            url += '/' + ((pageSize > 0) ? pageSize : 10);

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function saveWaterSource(waterSource, successCallback, errorCallback) {
            var url = baseUrl + 'watersource/create';
            return utils.resolveAlt(url, 'POST', null, waterSource, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
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
		
		function getPageSearchWaterSource(dto,pageIndex, pageSize, successCallback, errorCallback) {
            if(dto != null){

                var url = baseUrl + 'watersource/searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

        function getTableDefinition() {

            var _tableOperation = function(value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editWaterSource(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i> {{"waterSource.edit" | translate}}</a>' +
                    '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteWaterSource(' + "'" + row.id + "'" + ')"><i class="fa fa-trash margin-right-5"></i> {{"waterSource.delete" | translate}}</a>';
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
                },
				{
                    field: '',
                    title:  '{{"waterSource.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _cellActions
                },{
                    field: 'code',
                    title: '{{"waterSource.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap2
                }, {
                    field: 'name',
                    title:  '{{"waterSource.name" | translate}}',
                    switchable: true,
                    visible: true,
                    cellStyle: _cellNowrap2
                }
                // {
                //     field: 'description',    
                //     title: 'Mô tả',
                //     sortable: true,
                //     switchable: true,
                //     cellStyle: _cellNowrap2
                // }, // {
                // field: 'acreage',
                // title: 'Diện tích',
                // sortable: true,  
                // switchable: true,
                // visible: true,
                // cellStyle: _cellNowrap2
                //}, 
                
            ]
        }
    }
})();