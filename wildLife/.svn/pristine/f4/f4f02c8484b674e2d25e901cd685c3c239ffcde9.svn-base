(function () {
    'use strict';

    Education.Drug = angular.module('Education.Drug', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.Drug.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.drug', {
                url: '/drug',
                templateUrl: 'drug/views/drug.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Drug',
                    pageSubTitle: ''
                },
                controller: 'DrugController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Drug',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'drug/controllers/DrugController.js',
                                'drug/business/DrugService.js',
                            ]
                        });
                    }]
                }
            });
    }]);

})();