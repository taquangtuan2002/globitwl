(function () {
    'use strict';

    Education.LiveStockProduct = angular.module('Education.LiveStockProduct', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.LiveStockProduct.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.live_stock_product', {
                url: '/live_stock_product',
                templateUrl: 'live_stock_product/views/live_stock_product.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'LiveStockProduct',
                    pageSubTitle: ''
                },
                controller: 'LiveStockProductController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.LiveStockProduct',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'live_stock_product/controllers/LiveStockProductController.js?v='+Education.version,
                                'live_stock_product/business/LiveStockProductService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'unit/business/UnitService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            });
    }]);

})();