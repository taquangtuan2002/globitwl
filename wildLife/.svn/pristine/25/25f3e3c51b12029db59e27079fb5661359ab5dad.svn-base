(function () {
    'use strict';

    Education.ReportFarmerManagerment = angular.module('Education.ReportFarmerManagerment', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.ReportFarmerManagerment.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.report_farm_manager', {
                url: '/fmreport',
                templateUrl: 'report_farm_manager/views/report_drug.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Thống kê lần nhập thuốc',
                    pageSubTitle: ''
                },
                controller: 'ReportDrugController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportFarmerManagerment',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report_farm_manager/controllers/ReportDrugController.js?v='+Education.version,
                                'report_farm_manager/business/ReportDrugService.js?v='+Education.version,                               
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'import_drug/business/ImportDrugService.js?v='+Education.version,
                                'drug/business/DrugService.js?v='+Education.version
								
                            ]
                        });
                    }]
                }
            })
			.state('application.report_bran', {
                url: '/b_report',
                templateUrl: 'report_farm_manager/views/report_bran.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Thống kê lần nhập cám',
                    pageSubTitle: ''
                },
                controller: 'ReportAnimalFeedController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportFarmerManagerment',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report_farm_manager/controllers/ReportAnimalFeedController.js?v='+Education.version,
                                'report_farm_manager/business/ReportAnimalFeedService.js?v='+Education.version,                               
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'import_drug/business/ImportDrugService.js?v='+Education.version,
                                'bran/business/BranService.js?v='+Education.version
								
                            ]
                        });
                    }]
                }
            })
			.state('application.report_egg', {
                url: '/eggreport',
                templateUrl: 'report_farm_manager/views/report_egg.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Thống kê lần xuất trứng',
                    pageSubTitle: ''
                },
                controller: 'ReportEggController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportFarmerManagerment',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report_farm_manager/controllers/ReportEggController.js?v='+Education.version,
                                'report_farm_manager/business/ReportEggService.js?v='+Education.version,                               
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'import_drug/business/ImportDrugService.js?v='+Education.version,
                                'importEgg/business/ImportEggService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            });
    }]);

})();