(function () {
    'use strict';

    Education.User = angular.module('Education.User', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'ngFileSaver',
        'Education.Common'
    ]);

    Education.User.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

        // User Listing
            .state('application.user_accounts', {
                url: '/user/accounts',
                templateUrl: 'users/views/users.html?v='+Education.version,
                data: {
                    icon: 'icon-equalizer',
                    pageTitle: 'Manager users',
                    pageSubTitle: 'Quản lý người dùng'
                },
                controller: 'UserController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.User',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'users/controllers/UserController.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            })

            // User Listing
            .state('application.user_groups', {
                url: '/user/groups',
                templateUrl: 'users/views/user_groups.html?v='+Education.version,
                data: {
                    icon: 'icon-equalizer',
                    pageTitle: 'Hệ thống',
                    pageSubTitle: 'Nhóm người dùng'
                },
                controller: 'UserGroupController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.User',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'users/controllers/UserGroupController.js?v='+Education.version,
                                'users/business/UserGroupService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })

            // User Listing
            .state('application.user_roles', {
                url: '/user/roles',
                templateUrl: 'users/views/roles.html?v='+Education.version,
                data: {
                    icon: 'icon-equalizer',
                    pageTitle: 'Hệ thống',
                    pageSubTitle: 'Vai trò người dùng'
                },
                controller: 'UserRoleController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.User',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'users/controllers/UserRoleController.js?v='+Education.version,
                                'users/business/UserRoleService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
    }]);

})();