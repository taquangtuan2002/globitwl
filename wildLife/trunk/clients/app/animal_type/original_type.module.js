(function () {
    'use strict';

    Education.AnimalType = angular.module('Education.AnimalType', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.AnimalType.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.animal_type', {
                url: '/typeofanimal',
                templateUrl: 'animal_type/views/animal_type.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Type of animal',
                    pageSubTitle: ''
                },
                controller: 'AnimalTypeController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.AnimalType',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'animal_type/controllers/AnimalTypeController.js',
                                'animal_type/business/AnimalTypeService.js',
                            ]
                        });
                    }]
                }
            });
    }]);

})();