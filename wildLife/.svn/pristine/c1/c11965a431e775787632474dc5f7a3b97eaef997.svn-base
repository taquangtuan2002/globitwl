(function () {
    'use strict';

    Education.ReportPeriod = angular.module('Education.ReportPeriod', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.ReportPeriod.config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('application.16A_report_period', {
                url: '/report_period',
                templateUrl: 'report_period/views/listing.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Mẫu 16',
                    pageSubTitle: ''
                },
                controller: 'ReportPeriodController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportPeriod',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report_period/controllers/ReportPeriodController.js?v='+Education.version,
                                'report_period/controllers/ImportDataForm16Controller.js?v='+Education.version,
                                'report_period/business/ReportPeriodService.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
                                'animal/business/AnimalService.js?v='+Education.version,
                                'original/business/OriginalService.js?v='+Education.version,
                                'product_target/business/ProductTargetService.js?v='+Education.version,
                                'injectionTime/business/InjectionTimeService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.paperTemplate', {
                url: '/paper_template',
                templateUrl: 'report_period/views/paper_template.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Mẫu sổ giấy',
                    pageSubTitle: ''
                },
                controller: 'PageTemplateController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportPeriod',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report_period/controllers/PageTemplateController.js?v='+Education.version,
                                'report_period/business/ReportPeriodService.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
                                'animal/business/AnimalService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            
            .state('application.16B_report_period', {
                url: '/16B_report_period',
                templateUrl: 'report_period/views/16B_report_period.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Mẫu 16B',
                    pageSubTitle: ''
                },
                controller: 'ReportPeriod16BController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportPeriod16B',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report_period/controllers/ReportPeriod16BController.js?v='+Education.version,
                                'report_period/business/ReportPeriodService.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
                                'animal/business/AnimalService.js?v='+Education.version,
                                'original/business/OriginalService.js?v='+Education.version,
                                'product_target/business/ProductTargetService.js?v='+Education.version,
                                'injectionTime/business/InjectionTimeService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.import_data_form16', {
                url: '/import_data_form16',
                templateUrl: 'report_period/views/import_data.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Import dữ liệu mẫu 16',
                    pageSubTitle: ''
                },
                controller: 'ImportDataForm16Controller as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ImportDataForm16Controller',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report_period/controllers/ImportDataForm16Controller.js?v='+Education.version,
                                'report_period/business/ReportPeriodService.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
                                'animal/business/AnimalService.js?v='+Education.version,
                                'original/business/OriginalService.js?v='+Education.version,
                                'product_target/business/ProductTargetService.js?v='+Education.version,
                                'injectionTime/business/InjectionTimeService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
			.state('application.paperTemplate16C', {
                url: '/paper_template_16C',
                templateUrl: 'report_period/views/paper_template_16c.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Sổ 16C',
                    pageSubTitle: ''
                },
                controller: 'PageTemplate16CController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportPeriod',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report_period/controllers/PageTemplate16CController.js?v='+Education.version,
                                'report_period/business/ReportPeriodService.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
                                'animal/business/AnimalService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
			.state('application.paperTemplate16D', {
                url: '/paper_template_16D',
                templateUrl: 'report_period/views/paper_template_16d.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Sổ 16D',
                    pageSubTitle: ''
                },
                controller: 'PageTemplate16DController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportPeriod',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report_period/controllers/PageTemplate16DController.js?v='+Education.version,
                                'report_period/business/ReportPeriodService.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
                                'animal/business/AnimalService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            ;
    }]);

})();