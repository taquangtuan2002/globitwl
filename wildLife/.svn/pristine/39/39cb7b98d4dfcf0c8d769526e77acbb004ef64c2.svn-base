(function() {
    'use strict';

    Education.WaterSource = angular.module('Education.WaterSource', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',

        'Education.Common'
    ]);

    Education.WaterSource.config(['$stateProvider', function($stateProvider) {

        $stateProvider

        // WaterSource Listing
            .state('application.water_source', {
            url: '/watersource',
            templateUrl: 'water_source/views/water_source.html?v='+Education.version,
            data: {
                icon: 'icon-equalizer',
                pageTitle: 'Water source',
                pageSubTitle: 'Quản lý người dùng'
            },
            controller: 'WaterSourceController as vm',
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'Education.WaterSource',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            'water_source/controllers/WaterSourceController.js?v='+Education.version,
                            'water_source/business/WaterSourceService.js?v='+Education.version
                        ]
                    });
                }]
            }
        })

        // // User Listing
        // .state('application.fms_region', {
        //     url: '/user/groups',
        //     templateUrl: 'fms_region/views/fms_region.html',
        //     data: {
        //         icon: 'icon-equalizer',
        //         pageTitle: 'Hệ thống',
        //         pageSubTitle: 'Nhóm người dùng'
        //     },
        //     controller: 'UserGroupController as vm',
        //     resolve: {
        //         deps: ['$ocLazyLoad', function($ocLazyLoad) {
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

        // // User Listing
        // .state('application.user_roles', {
        //     url: '/user/roles',
        //     templateUrl: 'users/views/roles.html',
        //     data: {
        //         icon: 'icon-equalizer',
        //         pageTitle: 'Hệ thống',
        //         pageSubTitle: 'Vai trò người dùng'
        //     },
        //     controller: 'UserRoleController as vm',
        //     resolve: {
        //         deps: ['$ocLazyLoad', function($ocLazyLoad) {
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