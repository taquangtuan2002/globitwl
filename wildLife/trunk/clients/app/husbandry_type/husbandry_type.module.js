(function() {
    'use strict';

    Education.HusbandryType = angular.module('Education.HusbandryType', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',

        'Education.Common'
    ]);

    Education.HusbandryType.config(['$stateProvider', function($stateProvider) {

        $stateProvider

        // HusbandryType Listing
            .state('application.husbandry_type', {
            url: '/husbandrytype',
            templateUrl: 'husbandry_type/views/husbandry_type.html?v='+Education.version,
            data: {
                icon: 'icon-equalizer',
                pageTitle: 'Hệ thống',
                pageSubTitle: 'Quản lý người dùng'
            },
            controller: 'HusbandryTypeController as vm',
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'Education.HusbandryType',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            'husbandry_type/controllers/HusbandryTypeController.js?v='+Education.version,
                            'husbandry_type/business/HusbandryTypeService.js?v='+Education.version
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