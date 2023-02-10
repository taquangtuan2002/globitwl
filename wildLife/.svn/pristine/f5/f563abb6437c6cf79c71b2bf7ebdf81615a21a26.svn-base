(function () {
    'use strict';

    Education.AnimalRequired = angular.module('Education.AnimalRequired', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.AnimalRequired.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.animal_required', {
                url: '/animal_required/:id',
                //url: '/animal_required',
                templateUrl: 'animal_required/views/animal_required.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'AnimalRequired',
                    pageSubTitle: ''
                },
                controller: 'AnimalRequiredController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.AnimalRequired',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'animal_required/controllers/AnimalRequiredController.js',
                                'animal_required/business/AnimalRequiredService.js',
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