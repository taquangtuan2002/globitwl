(function () {
    'use strict';

    Education.Common = angular.module('Education.Common', [
        'ui.router',
        'oc.lazyLoad',
        'toastr',
        'ngCookies',
        'blockUI'
    ]);

    Education.Common.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            // Login page
            .state('login', {
                url: '/login',
                templateUrl: 'common/views/login/login.html?v='+Education.version,
                data: {pageTitle: 'System login'},
                controller: 'LoginController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Common',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'common/controllers/LoginController.js?v='+Education.version,
                                'common/business/LoginService.js?v='+Education.version,
                                'assets/css/login.min.css'
                            ]
                        });
                    }]
                }
            })
            .state('forgotPassword', {
                url: '/change_password/path',
                templateUrl: 'common/views/change_password.html?v='+Education.version,
                data: {pageTitle: 'Change password'},
                controller: 'ChangePasswordController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Common',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'common/controllers/ChangePasswordController.js?v='+Education.version,
                                'common/business/ChangePasswordService.js?v='+Education.version,
                                'assets/css/login.min.css'
                            ]
                        });
                    }]
                }
            })
            .state('application.error-page', {
                url: '/error',
                templateUrl: 'common/views/error.html?v='+Education.version,
                data: {icon: 'fa fa-desktop', pageTitle: 'Tính năng chưa thiết lập'},
                controller: 'DashboardController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Dashboard',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                // 'assets/css/external/book.css',
                                'dashboard/controllers/DashboardController.js?v='+Education.version
                            ]
                        });
                    }]
                }
            });
    }]);

})();