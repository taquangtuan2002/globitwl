(function () {
    'use strict';

    Education.AdministrativeUnitEditable = angular.module('Education.AdministrativeUnitEditable', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'ngFileSaver',
        'Education.Common'
    ]);

    Education.AdministrativeUnitEditable.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

        // User Listing
            .state('application.administrative_unit_editable', {
                url: '/administrativeuniteditable',
                templateUrl: 'administrative_unit_editable/views/administrative_unit_editable.html?v='+Education.version,
                data: {
                    icon: 'icon-equalizer',
                    pageTitle: 'Manager administrative units',
                    pageSubTitle: 'Quản lý đơn vị hành chính'
                },
                controller: 'AdministrativeUnitEditableController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.AdministrativeUnitEditable',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'administrative_unit_editable/controllers/AdministrativeUnitEditableController.js?v='+Education.version,
                                'administrative_unit_editable/business/AdministrativeUnitEditableService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            })

            // // User Listing
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