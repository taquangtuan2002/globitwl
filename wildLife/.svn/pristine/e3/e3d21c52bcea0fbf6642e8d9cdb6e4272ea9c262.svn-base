(function () {
    'use strict';

    Education.Animal = angular.module('Education.Animal', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.Animal.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.animal', {
                url: '/animal',
                templateUrl: 'animal/views/animal.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Animal',
                    pageSubTitle: ''
                },
                controller: 'AnimalController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Animal',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'animal/controllers/AnimalController.js',
                                'animal/business/AnimalService.js',
                                'animal_type/business/AnimalTypeService.js',
                                'original/business/OriginalService.js',
                                'product_target/business/ProductTargetService.js',
                            ]
                        });
                    }]
                }
            })
            .state('application.listanimal', {
                url: '/listanimal',
                templateUrl: 'animal/views/listanimal.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Animal',
                    pageSubTitle: ''
                },
                controller: 'ListAnimalController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Animal',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'animal/controllers/ListAnimalController.js',
                                'animal/business/AnimalService.js',
                                'animal_type/business/AnimalTypeService.js',
                                'original/business/OriginalService.js',
                                'product_target/business/ProductTargetService.js',
                            ]
                        });
                    }]
                }
            });

            
            
    }]);

})();