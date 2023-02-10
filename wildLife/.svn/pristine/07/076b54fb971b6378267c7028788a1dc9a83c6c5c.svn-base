(function () {
    'use strict';

    Education.AffiliatedUnits = angular.module('Education.AffiliatedUnits', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.AffiliatedUnits.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.AffiliatedUnits', {
                url: '/AffiliatedUnits',
                templateUrl: 'common/views/error.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Chưa được thiết lập hệ thống',
                    pageSubTitle: ''
                },
                controller: 'AffiliatedUnitsController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.AffiliatedUnits',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'affiliated_units/controllers/AffiliatedUnitsController.js',
                            ]
                        });
                    }]
                }
            });
    }]);

})();