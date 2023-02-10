/**
 * Created by bizic on 29/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Common').service('LoginService', LoginService);
    LoginService.$inject = [
        'OAuth',
        'Utilities',
        'settings'
    ];

    function LoginService(OAuth, utils, settings) {
        var self = this;

        self.performLogin = performLogin;
        self.forgotPassword = forgotPassword;
        /**
         * Perform login
         * @param user
         */
        function performLogin(user) {
            return OAuth.getAccessToken(user, null);
        }
        function forgotPassword(dto) {
            var url = settings.api.baseUrl+ 'public/publicAPI/forgotPasswordSendEmail';
            //console.log(url);
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
    }

})();