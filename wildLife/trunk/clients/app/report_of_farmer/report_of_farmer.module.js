(function () {
    'use strict';

    Education.ReportOfFarmer = angular.module('Education.ReportOfFarmer', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.ReportOfFarmer.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.report_of_farmer', {
                url: '/report_of_farmer',
                templateUrl: 'report_of_farmer/views/report_of_farmer.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Thống kê theo cơ sở nuôi',
                    pageSubTitle: ''
                },
                controller: 'ReportOfFarmerController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportOfFarmer',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report_of_farmer/controllers/ReportOfFarmerController.js?v='+Education.version,
                                'report_of_farmer/business/ReportOfFarmerService.js?v='+Education.version,
                                'report/controllers/ImportQuantityReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            });
    }]);

})();