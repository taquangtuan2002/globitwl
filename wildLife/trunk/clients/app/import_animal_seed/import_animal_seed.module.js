(function () {
    'use strict';

    Education.ImportAnimalSeed = angular.module('Education.ImportAnimalSeed', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.ImportAnimalSeed.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.import_animal_seed', {
                url: '/import_animal_seed',
                templateUrl: 'import_animal_seed/views/import_animal_seed.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'import animal seed',
                    pageSubTitle: ''
                },
                controller: 'ImportAnimalSeedController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Farm',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'import_animal_seed/controllers/ImportAnimalSeedController.js?v='+Education.version,
                                'import_animal_seed/business/ImportAnimalSeedService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
                                'original/business/OriginalService.js?v='+Education.version,
                                'product_target/business/ProductTargetService.js?v='+Education.version,
                                'animal/business/AnimalService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            })
            .state('application.import_animal_seed_detail', {
                url: '/detail/import_animal_seed/:id',
                templateUrl: 'import_animal_seed/views/detail.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'import animal seed detail',
                    pageSubTitle: ''
                },
                controller: 'ImportAnimalSeedDetailController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ImportAnimalSeed',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'import_animal_seed/controllers/ImportAnimalSeedDetailController.js?v='+Education.version,
                                'import_animal_seed/business/ImportAnimalSeedService.js?v='+Education.version,
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