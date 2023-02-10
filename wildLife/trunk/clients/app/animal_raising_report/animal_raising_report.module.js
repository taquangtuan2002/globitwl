(function () {
    'use strict';

    Education.InformationAnimal = angular.module('Education.InformationAnimal', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.InformationAnimal.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.information_animal', {
                url: '/information_animal_total',
                templateUrl: 'animal_raising_report/views/animal_raising_total.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'InformationAnimal',
                    pageSubTitle: ''
                },
                controller: 'InformationAnimalTotalController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.InformationAnimal',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'animal_raising_report/controllers/InformationAnimalTotalController.js',
                                'animal_raising_report/business/InformationAnimalTotalService.js',
                                'animal/business/AnimalService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            })
            .state('application.fluctuation_herd_by_month', {
                url: '/fluctuation_herd_by_month',
                templateUrl: 'animal_raising_report/views/fluctuation_herd_by_month.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'FluctuationHerdByMonth',
                    pageSubTitle: ''
                },
                controller: 'FluctuationHerdByMonthController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.InformationAnimal',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'animal_raising_report/controllers/FluctuationHerdByMonthController.js',
                                'animal_raising_report/business/FluctuationHerdByMonthService.js',
                                'animal/business/AnimalService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            })
            .state('application.fluctuation_grownup_by_month', {
                url: '/fluctuation_grownup_by_month',
                templateUrl: 'animal_raising_report/views/fluctuation_grownup_by_month.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'FluctuationHerdByMonth',
                    pageSubTitle: ''
                },
                controller: 'FluctuationGrownupByMonthController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.InformationAnimal',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'animal_raising_report/controllers/FluctuationGrownupByMonthController.js',
                                'animal_raising_report/business/FluctuationHerdByMonthService.js',
                                'animal/business/AnimalService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            })
            ;
    }]);

})();