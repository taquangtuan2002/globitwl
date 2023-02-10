(function () {
    'use strict';

    Education.InjectionPlant = angular.module('Education.InjectionPlant', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.InjectionPlant.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.injectionPlant', {
                url: '/typeofinjectionPlant',
                templateUrl: 'injectionPlant/views/injectionPlant.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Type of InjectionPlant',
                    pageSubTitle: ''
                },
                controller: 'InjectionPlantController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.InjectionPlant',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'injectionPlant/controllers/InjectionPlantController.js?v='+Education.version,
                                'injectionPlant/business/InjectionPlantService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();