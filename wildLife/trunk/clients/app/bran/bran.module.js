(function () {
    'use strict';

    Education.Bran = angular.module('Education.Bran', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.Bran.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.bran', {
                url: '/bran',
                templateUrl: 'bran/views/bran.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Type of bran',
                    pageSubTitle: ''
                },
                controller: 'BranController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Bran',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'bran/controllers/BranController.js',
                                'bran/business/BranService.js',
                            ]
                        });
                    }]
                }
            });
    }]);

})();