(function () {
    'use strict';

    Education.ImportExportLiveStockProduct = angular.module('Education.ImportExportLiveStockProduct', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.ImportExportLiveStockProduct.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.import_live_stock_product', {
                url: '/import_live_stock_product',
                templateUrl: 'import_export_live_stock_product/views/import.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Import live stock product',
                    pageSubTitle: ''
                },
                controller: 'ImportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ImportExportLiveStockProduct',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'import_export_live_stock_product/controllers/ImportController.js?v='+Education.version,
                                'import_export_live_stock_product/business/ImportService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })

            .state('application.export_live_stock_product', {
                url: '/export_live_stock_product',
                templateUrl: 'import_export_live_stock_product/views/export.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Export live stock product',
                    pageSubTitle: ''
                },
                controller: 'ExportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ImportExportLiveStockProduct',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'import_export_live_stock_product/controllers/ExportController.js?v='+Education.version,
                                'import_export_live_stock_product/business/ExportService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            });
    }]);

})();