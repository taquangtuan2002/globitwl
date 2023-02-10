(function() {
    'use strict';

    Education.FmsRegion = angular.module('Education.FmsRegion', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',

        'Education.Common'
    ])
    .run(['$rootScope', function($rootScope){
        var version = $rootScope.version;
        console.log(version);
    }]);

    Education.FmsRegion.config(['$stateProvider', function($stateProvider) {

        $stateProvider

        // FmsRegion Listing
            .state('application.fms_region', {
            url: '/fms/region',
            templateUrl: 'fms_region/views/fms_region.html?v='+Education.version,
            data: {
                icon: 'icon-equalizer',
                pageTitle: 'Ecoregion',
                pageSubTitle: 'Quản lý người dùng'
            },
            controller: 'FmsRegionController as vm',
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'Education.FmsRegion',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            'fms_region/controllers/FmsRegionController.js?v='+Education.version,
                            'fms_region/business/FmsRegionService.js?v='+Education.version
                        ]
                    });
                }]
            }
        })

        // // User Listing
        // .state('application.fms_region', {
        //     url: '/user/groups',
        //     templateUrl: 'fms_region/views/fms_region.html?v='+Education.version,
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
        //     templateUrl: 'users/views/roles.html?v='+Education.version,
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