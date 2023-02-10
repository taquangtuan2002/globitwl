(function() {
    'use strict';

    Education.FmsAdministrative = angular.module('Education.FmsAdministrative', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'ngJsTree',
        'Education.Common'
    ]);

    Education.FmsAdministrative.config(['$stateProvider', function($stateProvider) {

        $stateProvider

        // FmsAdministrative Listing
            .state('application.fms_administrative', {
            url: '/fms/administrative',
            templateUrl: 'fms_administrative_unit/views/fms_administrative.html?v='+Education.version,
            data: {
                icon: 'icon-equalizer',
                pageTitle: 'Administrative Unit',
                pageSubTitle: 'Quản lý người dùng'
            },
            controller: 'FmsAdministrativeController as vm',
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'Education.FmsAdministrative',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            'fms_administrative_unit/controllers/FmsAdministrativeController.js?v='+Education.version,
                            'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
                            'fms_region/business/FmsRegionService.js?v='+Education.version
                        ]
                    });
                }]
            }
        })

        // User Listing
        // .state('application.user_groups', {
        //     url: '/user/groups',
        //     templateUrl: 'users/views/user_groups.html?v='+Education.version,
        //     data: {
        //         icon: 'icon-equalizer',
        //         pageTitle: 'Hệ thống',
        //         pageSubTitle: 'Nhóm người dùng'
        //     },
        //     controller: 'UserGroupController as vm',
        //     resolve: {
        //         deps: ['$ocLazyLoad', function ($ocLazyLoad) {
        //             return $ocLazyLoad.load({
        //                 name: 'Education.User',
        //                 insertBefore: '#ng_load_plugins_before',
        //                 files: [
        //                     'users/controllers/UserGroupController.js?v='+Education.version,
        //                     'users/business/UserGroupService.js?v='+Education.version
        //                 ]
        //             });
        //         }]
        //     }
        // })

        // User Listing
        // .state('application.user_roles', {
        //     url: '/user/roles',
        //     templateUrl: 'users/views/roles.html?v='+Education.version,
        //     data: {
        //         icon: 'icon-equalizer',
        //         pageTitle: 'Hệ thống',
        //         pageSubTitle: 'Vai trò người dùng'
        //     },
        //     controller: 'UserRoleController as vm',
        //     resolve: {
        //         deps: ['$ocLazyLoad', function ($ocLazyLoad) {
        //             return $ocLazyLoad.load({
        //                 name: 'Education.User',
        //                 insertBefore: '#ng_load_plugins_before',
        //                 files: [
        //                     'users/controllers/UserRoleController.js?v='+Education.version,
        //                     'users/business/UserRoleService.js?v='+Education.version
        //                 ]
        //             });
        //         }]
        //     }
        // })
    }]);

})();