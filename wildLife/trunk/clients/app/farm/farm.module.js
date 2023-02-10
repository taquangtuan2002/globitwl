(function () {
    'use strict';

    Education.Farm = angular.module('Education.Farm', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.Farm.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.farm', {
                url: '/farm',
                templateUrl: 'farm/views/farm.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Danh sách cơ sở',
                    pageSubTitle: ''
                },
                controller: 'FarmController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Farm',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'farm/controllers/FarmController.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'water_source/business/WaterSourceService.js?v='+Education.version,
                                'husbandry_type/business/HusbandryTypeService.js?v='+Education.version,
                                'husbandry_method/business/HusbandryMethodService.js?v='+Education.version,
                                'product_target/business/ProductTargetService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
                                'animal_type/business/AnimalTypeService.js?v='+Education.version,
                                'certificate/business/CertificateService.js?v='+Education.version,
                                'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.farm_detail', {
                url: '/detail_farm/:id',
                params: {
                    idScroll: null
                },
                templateUrl: 'farm/views/detail.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Thông tin cơ sở',
                    pageSubTitle: ''
                },
                controller: 'FarmDetailController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Farm',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'farm/controllers/FarmDetailController.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
                                'water_source/business/WaterSourceService.js?v='+Education.version,
                                'husbandry_type/business/HusbandryTypeService.js?v='+Education.version,
                                'husbandry_method/business/HusbandryMethodService.js?v='+Education.version,
                                'product_target/business/ProductTargetService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'animal_type/business/AnimalTypeService.js?v='+Education.version,
                                'farm_size/business/FarmSizeService.js?v='+Education.version,
                                'certificate/business/CertificateService.js?v='+Education.version,
                                'ownership/business/OwnershipService.js?v='+Education.version,
                                'animal_report_data/business/AnimalReportDataService.js?v='+Education.version,
                                'report_period/business/ReportPeriodService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            })
        
    }]);

})();