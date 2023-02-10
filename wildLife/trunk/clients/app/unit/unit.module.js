(function () {
    'use strict';

    Education.Unit = angular.module('Education.Unit', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.Unit.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.unit', {
                url: '/unit',
                templateUrl: 'unit/views/unit.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Unit',
                    pageSubTitle: ''
                },
                controller: 'UnitController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Unit',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'unit/controllers/UnitController.js?v='+Education.version,
                                'unit/business/UnitService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();