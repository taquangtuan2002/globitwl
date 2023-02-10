(function () {
    'use strict';

    Education.ProductTarget = angular.module('Education.ProductTarget', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.ProductTarget.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.product_target', {
                url: '/producttarget',
                templateUrl: 'product_target/views/product_target.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Mục đích nuôi',
                    pageSubTitle: ''
                },
                controller: 'ProductTargetController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ProductTarget',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'product_target/controllers/ProductTargetController.js?v='+Education.version,
                                'product_target/business/ProductTargetService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();