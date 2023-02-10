(function () {
    'use strict';

    Education.InternalStatistics = angular.module('Education.InternalStatistics', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.InternalStatistics.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.internal_statistics', {
                url: '/internal_statistics',
                templateUrl: 'common/views/error.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Chưa được thiết lập hệ thống',
                    pageSubTitle: ''
                },
                controller: 'InternalStatisticsController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Original',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'internal_statistics/controllers/InternalStatisticsController.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();