(function() {
    'use strict';

    Education.ExportAnimal = angular.module('Education.ExportAnimal', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'ngFileUpload',
        'Education.Common'
    ]);

    Education.ExportAnimal.config(['$stateProvider', function($stateProvider) {

        $stateProvider

        // ExportAnimal Listing
            .state('application.export_animal', {
            url: '/exportanimal',
            templateUrl: 'export_animal/views/export_animal.html?v='+Education.version,
            data: {
                icon: 'icon-equalizer',
                pageTitle: 'Export animal',
                pageSubTitle: 'Quản lý người dùng'
            },
            controller: 'ExportAnimalController as vm',
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'Education.ExportAnimal',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            'export_animal/controllers/ExportAnimalController.js?v='+Education.version,
                            'export_animal/business/ExportAnimalService.js?v='+Education.version,
                            'animal/business/AnimalService.js?v='+Education.version,
                            'farm/business/farmService.js?v='+Education.version,
                            'original/business/OriginalService.js?v='+Education.version,
                            'product_target/business/ProductTargetService.js?v='+Education.version,
                            'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                        ]
                    });
                }]
            }
        })

        // User Listing
        .state('application.exportAnimalDetail', {
            url: '/detail/exportanimal/:id',
            templateUrl: 'export_animal/views/exportAnimalDetail.html?v='+Education.version,
            data: {
                icon: 'icon-equalizer',
                pageTitle: 'Hệ thống',
                pageSubTitle: '{{"exportanimalDetail" | translate}}'
            },
            controller: 'ExportAnimalDetailController as vm',
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'Education.ExportAnimalDetail',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            'export_animal/controllers/ExportAnimalDetailController.js?v='+Education.version,
                            'export_animal/business/ExportAnimalService.js?v='+Education.version,
                            'animal/business/AnimalService.js?v='+Education.version,
                            'farm/business/farmService.js?v='+Education.version,
                            'original/business/OriginalService.js?v='+Education.version,
                            'product_target/business/ProductTargetService.js?v='+Education.version,
                            'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                        ]
                    });
                }]
            }
        })
        .state('application.exportAnimalDetailView', {
            url: '/detailview/exportanimal/:id',
            templateUrl: 'export_animal/views/exportAnimalDetailView.html?v='+Education.version,
            data: {
                icon: 'icon-equalizer',
                pageTitle: 'Hệ thống',
                pageSubTitle: '{{"exportanimalDetail" | translate}}'
            },
            controller: 'ExportAnimalDetailViewController as vm',
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'Education.ExportAnimalDetail',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            'export_animal/controllers/ExportAnimalDetailViewController.js?v='+Education.version,
                            'export_animal/business/ExportAnimalService.js?v='+Education.version,
                            'animal/business/AnimalService.js?v='+Education.version,
                            'farm/business/farmService.js?v='+Education.version,
                            'original/business/OriginalService.js?v='+Education.version,
                            'product_target/business/ProductTargetService.js?v='+Education.version,
                            'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                        ]
                    });
                }]
            }
        })

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