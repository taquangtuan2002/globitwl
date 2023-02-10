/**
 * Created by bizic on 29/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Common').service('ChangePasswordService', ChangePasswordService);
    ChangePasswordService.$inject = [
        'Utilities',
        'settings'
    ];

    function ChangePasswordService(utils,settings) {
        var self = this;

        self.checkKey = checkKey;
        self.changePassword = changePassword;

        function checkKey(dto) {
            var url = settings.api.baseUrl+ 'public/publicAPI/checkToken';
            //console.log(url);
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function changePassword(dto) {
            var url = settings.api.baseUrl+ 'public/publicAPI/changePassword';
            //console.log(url);
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

    }

})();