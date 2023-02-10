(function () {
    'use strict';

    Education.TotalReport = angular.module('Education.TotalReport', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.TotalReport.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.total_report', {
                url: '/total_report',
                templateUrl: 'total_report/views/total_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'import animal',
                    pageSubTitle: ''
                },
                controller: 'TotalReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Farm',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'total_report/controllers/TotalReportController.js?v='+Education.version,
                                'total_report/business/TotalReportService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            });
    }]);

})();