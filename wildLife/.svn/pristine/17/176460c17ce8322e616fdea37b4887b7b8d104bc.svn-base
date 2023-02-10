(function () {
    'use strict';

    Education.Statistic = angular.module('Education.Statistic', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.Statistic.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.statistic', {
                url: '/statistic',
                templateUrl: 'common/views/error.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Chưa được thiết lập hệ thống',
                    pageSubTitle: ''
                },
                controller: 'StatisticController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Statistic',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'statistic/controllers/StatisticController.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();