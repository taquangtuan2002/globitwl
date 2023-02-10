/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Common').controller('ChangePasswordController', ChangePasswordController);

    ChangePasswordController.$inject = [
        '$rootScope',
        '$scope',
        '$state',
        '$cookies',
        '$http',
        'settings',
        'constants',
        'ChangePasswordService',
        'toastr',
        'focus',
        'blockUI',
        '$uibModal',
        '$location',
        '$translate'
    ];

    function ChangePasswordController($rootScope, $scope, $state, $cookies, $http, settings, constants, service, toastr, focus, blockUI, modal, $location, $translate) {
        var vm = this;
        
        function getTranslateValue(translationKey) {
            var translation = $translate.instant(translationKey);
            return translation;
        }

        vm.checkValided = null;
        vm.dto = {};

        var paramValue = $location.search().key; 
        blockUI.start();

        function checkValided() {
            vm.dto = {};
            vm.dto.token = paramValue;
            vm.dto.phoneNumber = vm.phoneNumber;
            service.checkKey(vm.dto).then(function (checkResult) {
                if (checkResult != null) {
                    vm.checkValided = checkResult;
                    if (vm.checkValided != null && vm.dto.phoneNumber != null && vm.dto.phoneNumber.length > 0 && !vm.checkValided.phoneNumberSuccess) {
                        toastr.warning(getTranslateValue('forgotPassword.toastr.isvalid.phone_number_not_valided'), getTranslateValue('toastr.warning'));
                        return;
                    } else if (vm.checkValided != null && vm.dto.phoneNumber != null && vm.dto.phoneNumber.length > 0 && vm.checkValided.phoneNumberSuccess) {
                        toastr.success(getTranslateValue('forgotPassword.toastr.isvalid.phone_number_is_valid'), getTranslateValue('toastr.success'));
                        return;
                    }
                }
                else {
                    vm.checkValided = null;
                }
                blockUI.stop();
            });
        };

        vm.checkVerifyPhoneNumber = function () {
            if (vm.phoneNumber == null || vm.phoneNumber == '') {
                toastr.warning(getTranslateValue('forgotPassword.toastr.isvalid.phoneNumber'), getTranslateValue('toastr.warning'));
                return;
            }
            else {
                checkValided();
            }
        }

        if (paramValue != null && paramValue != '') {
            checkValided();
        } else {
            $state.go('login');
        }

        vm.changePassword = function () {
            if (vm.dto.password == null || vm.dto.password == '') {
                toastr.warning(getTranslateValue('forgotPassword.toastr.isvalid.password'), getTranslateValue('toastr.warning'));
                return;
            }
            if (vm.dto.confirmPassword == null || vm.dto.confirmPassword == '') {
                toastr.warning(getTranslateValue('forgotPassword.toastr.isvalid.confirmPassword'), getTranslateValue('toastr.warning'));
                return;
            }
            if (angular.equals(vm.dto.password, vm.dto.confirmPassword)) {
                if (vm.dto != null && vm.dto.token != null) {
                    service.changePassword(vm.dto).then(function (result) {
                        if (result != null) {
                            if (result.success) {
                                toastr.success(getTranslateValue('forgotPassword.toastr.update_success'), getTranslateValue('toastr.success'));
                                $state.go('login');
                            }
                            else {
                                if (result.caseResult == 4) {
                                    toastr.error(getTranslateValue('forgotPassword.link_has_expired'), getTranslateValue('toastr.error'));
                                    return;
                                } else if (result.caseResult == 5) {
                                    toastr.error(getTranslateValue('forgotPassword.link_error_or_has_expired'), getTranslateValue('toastr.error'));
                                    return;
                                } else if (result.caseResult == 7) {
                                    toastr.error(getTranslateValue('forgotPassword.toastr.isvalid.phone_number_is_valid'), getTranslateValue('toastr.error'));
                                    return;
                                }
                                else {
                                    toastr.error(getTranslateValue('forgotPassword.toastr.error'), getTranslateValue('toastr.error'));
                                    return;
                                }
                            }
                        }
                        else {
                            vm.checkValided = null;
                        }
                    });
                }
            }
            else {
                toastr.warning(getTranslateValue('forgotPassword.toastr.isvalid.checkPassword'), getTranslateValue('toastr.warning'));
                return;
            }

            checkValided();
        };

        vm.onEnter = function(e){
            if(e.keyCode === 13){
                vm.changePassword();
            }
        }
    }

})();