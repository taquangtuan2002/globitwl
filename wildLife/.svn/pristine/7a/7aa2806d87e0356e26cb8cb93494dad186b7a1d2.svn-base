(function () {
    'use strict';

    Education.FarmSize = angular.module('Education.FarmSize', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.FarmSize.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.farm_size', {
                url: '/size_farm',
                templateUrl: 'farm_size/views/farm_size.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Farm size',
                    pageSubTitle: ''
                },
                controller: 'FarmSizeController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.FarmSize',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'farm_size/controllers/FarmSizeController.js',
                                'farm_size/business/FarmSizeService.js',
                            ]
                        });
                    }]
                }
            });
    }]);

})();