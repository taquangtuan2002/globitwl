/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Common').controller('LoginController', LoginController);

    LoginController.$inject = [
        '$rootScope',
        '$scope',
        '$state',
        '$cookies',
        '$http',
        'settings',
        'constants',
        'LoginService',
        'toastr',
        'focus',
        'blockUI',
        '$uibModal',
        '$translate'
    ];

    function LoginController($rootScope, $scope, $state, $cookies, $http, settings, constants, service, toastr, focus, blockUI, modal, $translate) {
        var vm = this;
        vm.login = function () {

            blockUI.start();

            // Username?
            if (!vm.user.username || vm.user.username.trim() == '') {
                blockUI.stop();

                toastr.error('Please enter your username.', 'Error');
                focus('username');
                return;
            }

            // Password?
            if (!vm.user.password || vm.user.password.trim() == '') {
                blockUI.stop();

                toastr.error('Please enter your password.', 'Error');
                focus('password');
                return;
            }

            service.performLogin(vm.user).then(function(response) {

                blockUI.stop();

                if (angular.isDefined(response) && angular.isObject(response.data)) {
                    $state.go('application.dashboard');
                } else {
                    toastr.error('Something wrong happened. Please try again later.', 'Error');
                }

            }).catch(function () {
                blockUI.stop();
            });
        };

        // Focus on username field
        focus('username');
        vm.modalInstance = null;
        vm.openPopupForgotPassword = function(){
            vm.forgotUsername = null;
            vm.forgotEmail = null;
            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'popup_forgot_password.html',
                scope: $scope,
                backdrop: 'static',
                size: 'md'
            });
        }
        vm.backToLogin = function(){
            vm.modalInstance.close();
        }
        vm.forgotUsername = null;
        vm.forgotEmail = null;
        vm.getPassword = function(){
            var forgotPasswordDto = {};
            forgotPasswordDto.email = vm.forgotEmail;
            forgotPasswordDto.username = vm.forgotUsername;
            forgotPasswordDto.location = window.location.href;
            forgotPasswordDto.host = window.location.hostname;
            if(!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(forgotPasswordDto.email))){
                toastr.warning($translate.instant('forgotPassword.notValidEmail'),$translate.instant('toastr.warning'));
                return;
            }
            service.forgotPassword(forgotPasswordDto).then(function(data){
                console.log(data);
                if(data && data.success){
                    toastr.info($translate.instant('forgotPassword.sendEmailSucess'),$translate.instant('toastr.info'));
                    vm.modalInstance.close();
                }else if(data && data.success == false){
                    if(data.text=='Error'){
                        toastr.warning($translate.instant('forgotPassword.notSendEmail'),$translate.instant('toastr.warning'));
                    }else
                    toastr.warning($translate.instant('forgotPassword.emailAndUserNameNotValid'),$translate.instant('toastr.warning'));
                }
            });
        }


    }

})();