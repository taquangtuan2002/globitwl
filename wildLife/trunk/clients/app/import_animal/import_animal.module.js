(function () {
    'use strict';

    Education.ImportAnimal = angular.module('Education.ImportAnimal', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.ImportAnimal.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.import_animal', {
                url: '/import/animal',
                templateUrl: 'import_animal/views/import_animal.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'import animal',
                    pageSubTitle: ''
                },
                controller: 'ImportAnimalController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Farm',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'import_animal/controllers/ImportAnimalController.js?v='+Education.version,
                                'import_animal/business/ImportAnimalService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.import_animal_detail', {
                url: '/detail/import/animal/:id',
                templateUrl: 'import_animal/views/detail.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'import animal detail',
                    pageSubTitle: ''
                },
                controller: 'ImportAnimalDetailController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ImportAnimal',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'import_animal/controllers/ImportAnimalDetailController.js?v='+Education.version,
                                'import_animal/business/ImportAnimalService.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
                                'animal/business/AnimalService.js?v='+Education.version,
                                'original/business/OriginalService.js?v='+Education.version,
                                'product_target/business/ProductTargetService.js?v='+Education.version,
                                'injectionTime/business/InjectionTimeService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.import_animal_detail_view', {
                url: '/detailview/import/animal/:id',
                templateUrl: 'import_animal/views/detail_view.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'import animal detail',
                    pageSubTitle: ''
                },
                controller: 'ImportAnimalDetailViewController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ImportAnimal',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'import_animal/controllers/ImportAnimalDetailViewController.js?v='+Education.version,
                                'import_animal/business/ImportAnimalService.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
                                'animal/business/AnimalService.js?v='+Education.version,
                                'original/business/OriginalService.js?v='+Education.version,
                                'product_target/business/ProductTargetService.js?v='+Education.version,
                                'injectionTime/business/InjectionTimeService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            });
    }]);

})();