(function () {
    'use strict';

    Education.BiologicalClass = angular.module('Education.BiologicalClass', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.BiologicalClass.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.biological_class', {
                url: '/biologicalClass',
                templateUrl: 'biological_class/views/biological_class.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Type of animal',
                    pageSubTitle: ''
                },
                controller: 'BiologicalClassController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.BiologicalClass',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'biological_class/controllers/BiologicalClassController.js',
                                'biological_class/business/BiologicalClassService.js',
                            ]
                        });
                    }]
                }
            });
    }]);

})();