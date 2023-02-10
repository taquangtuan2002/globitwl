(function () {
    'use strict';

    Education.ForestProductList = angular.module('Education.ForestProductList', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.ForestProductList.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.forestproductlist', {
                url: '/forestproductlist',
                templateUrl: 'forest_product_list/views/table.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Forest product list',
                    pageSubTitle: ''
                },
                controller: 'ForestProductListController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Farm',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'forest_product_list/controllers/ForestProductListController.js?v='+Education.version,
                                'forest_product_list/business/ForestProductListService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
                                'animal/business/AnimalService.js',
                                'report_period/business/ReportPeriodService.js'
                            ]
                        });
                    }]
                }
            });
    }]);

})();