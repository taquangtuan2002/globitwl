(function() {
    'use strict';

    Education.HusbandryMethod = angular.module('Education.HusbandryMethod', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',

        'Education.Common'
    ]);

    Education.HusbandryMethod.config(['$stateProvider', function($stateProvider) {

        $stateProvider

        // HusbandryMethod Listing
            .state('application.husbandry_method', {
            url: '/husbandrymethod',
            templateUrl: 'husbandry_method/views/husbandry_method.html?v='+Education.version,
            data: {
                icon: 'icon-equalizer',
                pageTitle: 'Husbandry method',
                pageSubTitle: 'Quản lý người dùng'
            },
            controller: 'HusbandryMethodController as vm',
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'Education.HusbandryMethod',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            'husbandry_method/controllers/HusbandryMethodController.js?v='+Education.version,
                            'husbandry_method/business/HusbandryMethodService.js?v='+Education.version
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