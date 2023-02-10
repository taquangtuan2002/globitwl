(function() {
    'use strict';
    angular.module('Education.AdministrativeOrganization').service('AdministrativeOrganizationService', AdministrativeOrganizationService);

    AdministrativeOrganizationService.$inject = [
        '$http',
        '$q',
        'settings',
        'Utilities',
        '$translate'
    ];

    function AdministrativeOrganizationService($http, $q, settings, utils, $translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;
        self.getChildrenByParentId = getChildrenByParentId;
        self.getCurrentUser = getCurrentUser;
        self.saveAdministrativeOrganization = saveAdministrativeOrganization;
        self.updateAdministrativeOrganization = updateAdministrativeOrganization;
        self.getAdministrativeUnitDtoByLoginUser = getAdministrativeUnitDtoByLoginUser;
        self.getAllByParentId = getAllByParentId;
        self.getParent = getParent;
        self.getById = getById;
        self.deleteAdministrativeOrganization = deleteAdministrativeOrganization;
        self.getListTree = getListTree;
        self.getOrganization = getOrganization;

        function getOrganization(dto, successCallback, errorCallback) {
            var url = baseUrl + 'administrativeOrganization/get-administrative-organization';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        // Lấy administrative theo user đăng nhập
        function getAdministrativeUnitDtoByLoginUser() {
            var url = baseUrl + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllByParentId(parentId) {
            var url = baseUrl + 'administrative/getAllByParentId/' + parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getCurrentUser() {
            var url = baseUrl + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        
        function getChildrenByParentId(id) {
            if(!id) {
                return $q.when(null);
            }
            var url = baseUrl + 'administrativeOrganization/getChildrenByParentId/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function saveAdministrativeOrganization(dto, successCallback, errorCallback) {
            var url = baseUrl + 'administrativeOrganization/create';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function updateAdministrativeOrganization(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'administrativeOrganization/update/' + id;
            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getParent(dto, successCallback, errorCallback) {
            var url = baseUrl + 'administrativeOrganization/getParent';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getById(id) {
            if(!id) {
                return $q.when(null);
            }
            var url = baseUrl + 'administrativeOrganization/getById/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteAdministrativeOrganization(id, successCallback, errorCallback) {
            var url = baseUrl + 'administrativeOrganization/delete/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getListTree(dto, successCallback, errorCallback) {
            var url = baseUrl + 'administrativeOrganization/getListTree';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
    }
})();