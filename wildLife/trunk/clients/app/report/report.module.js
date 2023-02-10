(function () {
    'use strict';

    Education.Report = angular.module('Education.Report', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        //'$translate'
    ]);

    Education.Report.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.quantity_report', {
                url: '/quantity_report',
                templateUrl: 'report/views/quantity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo số lượng đầu con động vật',
                    pageSubTitle: ''
                },
                controller: 'QuantityReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.QuantityReport',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/QuantityReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.region_quantity_report', {
                url: '/region_quantity_report',
                templateUrl: 'report/views/region_quantity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo số lượng đầu con động vật theo vùng',
                    pageSubTitle: ''
                },
                controller: 'RegionQuantityReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.RegionQuantityReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/RegionQuantityReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.region_detail_quantity_report', {
                url: '/region_detail_quantity_report',
                templateUrl: 'report/views/region_detail_quantity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo Số lượng động vật qua các năm tại vùng',
                    pageSubTitle: ''
                },
                controller: 'RegionDetailQuantityReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.RegionDetailQuantityReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/RegionDetailQuantityReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            }).state('application.r_animal_ratio_by_region_report', {
                url: '/r_animal_ratio_by_region_report',
                templateUrl: 'report/views/animal_ratio_by_region_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Cơ cấu đàn động vật',
                    pageSubTitle: ''
                },
                controller: 'AnimalRatioByRegionReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.AnimalRatioByRegionReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/AnimalRatioByRegionReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
			.state('application.meat_yield_report', {
                url: '/meat_yield_report',
                templateUrl: 'report/views/meat_yield_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo sản lượng thịt',
                    pageSubTitle: ''
                },
                controller: 'MeatYieldReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.MeatYieldReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/business/MeatProductionReportService.js?v='+Education.version,
                                'report/controllers/MeatYieldReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
			.state('application.produc_target_report_seed_level', {
                url: '/produc_target_report_seed_level',
                templateUrl: 'report/views/seed_level_produc_target_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo về số con thương phẩm',
                    pageSubTitle: ''
                },
                controller: 'SeedLevelProducTargetReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.SeedLevelProducTargetReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/SeedLevelProducTargetReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
			.state('application.ratio_by_animal_report', {
                url: '/ratio_by_animal_report',
                templateUrl: 'report/views/ratio_by_animal_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Cơ cấu đàn động vật của từng vùng',
                    pageSubTitle: ''
                },
                controller: 'RatioByAnimalReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.RatioByAnimalReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/RatioByAnimalReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.farm_position', {
                url: '/farm_position',
                templateUrl: 'report/views/farm_position.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Vị trí cơ sở chăn nuôi',
                    pageSubTitle: ''
                },
                controller: 'FarmPositionController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.FarmPositionController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/FarmPositionController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'report/business/QuantityReportService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.farm_position_vgmap', {
                url: '/farm_position_vgmap?id&lat&long&name',
                templateUrl: 'report/views/farm_position_vgmap2.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Vị trí cơ sở chăn nuôi',
                    pageSubTitle: ''
                },
                controller: 'FarmPositionVGMapController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.FarmPositionVGMapController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/FarmPositionVGMapController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'report/business/QuantityReportService.js?v='+Education.version,
								'report/business/SumQuantityReportDataService.js?v='+Education.version								
                            ]
                        });
                    }]
                }
            })
            .state('application.farm_position_vgmap_search', {
                url: '/farm_position_vgmap_search',
                templateUrl: 'report/views/farm_position_vgmap_search.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Tra cứu thông tin cơ sở nuôi',
                    pageSubTitle: ''
                },
                controller: 'FarmPositionVGMapSearchController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.FarmPositionVGMapSearchController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/FarmPositionVGMapSearchController.js?v='+Education.version				
                            ]
                        });
                    }]
                }
            })
            //tran huu dat start
            .state('application.atlas_grow_priman', {
                url: '/atlas_grow_priman',
                templateUrl: 'report/views/atlas_grow_priman.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Atlas các cơ sở gây nuôi Linh trưởng',
                    pageSubTitle: ''
                },
                controller: 'AtlasGrowPrimanController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.AtlasGrowPrimanController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/views/atlas_grow_priman.html',
                                'report/controllers/AtlasGrowPrimanController.js'
                            ]
                        });
                    }]
                }
            })
            //tran huu dat end
            .state('application.province_report', {
                url: '/sum_quantity_report',
                templateUrl: 'report/views/sum_quantity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo tỉnh',
                    pageSubTitle: ''
                },
                controller: 'SumQuantityReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.SumQuantityReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/SumQuantityReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.sum_quantity_report_data', {
                url: '/sum_quantity_report_data',
                templateUrl: 'report/views/sum_quantity_report_data.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo 03',
                    pageSubTitle: ''
                },
                controller: 'SumQuantityReportDataController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.SumQuantityReportDataController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/SumQuantityReportDataController.js?v='+Education.version,
                                'report/business/SumQuantityReportDataService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.compare_sum_quantity_report', {
                url: '/compare_sum_quantity_report',
                templateUrl: 'report/views/compare_sum_quantity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo so sánh tổng đàn',
                    pageSubTitle: ''
                },
                controller: 'CompareSumQuantityReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.CompareSumQuantityReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/CompareSumQuantityReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.province_import_report', {
                url: '/province_import_report',
                templateUrl: 'report/views/province_import_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo so sánh tăng đàn cấp tỉnh',
                    pageSubTitle: ''
                },
                controller: 'ProvinceImportReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ProvinceImportReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/ProvinceImportReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.density_map', {
                url: '/density_map',
                templateUrl: 'report/views/density_map.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Bản đồ mật độ cơ sở chăn nuôi',
                    pageSubTitle: ''
                },
                controller: 'DensityMapController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.DensityMapController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/DensityMapController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.import_quantity_report', {
                url: '/import_quantity_report',
                templateUrl: 'report/views/import_quantity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo tăng đàn',
                    pageSubTitle: ''
                },
                controller: 'ImportQuantityReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ImportQuantityReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
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
            })
            .state('application.export_meat', {
                url: '/export_meat',
                templateUrl: 'report/views/export_meat.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo sản lượng thịt',
                    pageSubTitle: ''
                },
                controller: 'ExportMeatController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ExportMeatController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/ExportMeatController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.export_quantity_report', {
                url: '/export_quantity_report',
                templateUrl: 'report/views/export_quantity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo giảm đàn',
                    pageSubTitle: ''
                },
                controller: 'ExportQuantityReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ExportQuantityReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/ExportQuantityReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.compare_import_quantity_report', {
                url: '/compare_import_quantity_report',
                templateUrl: 'report/views/compare_import_quantity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo so sánh tăng đàn',
                    pageSubTitle: ''
                },
                controller: 'CompareImportQuantityReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.CompareImportQuantityReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/CompareImportQuantityReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.compare_export_meat', {
                url: '/compare_export_meat',
                templateUrl: 'report/views/compare_export_meat.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo so sánh sản lượng thịt',
                    pageSubTitle: ''
                },
                controller: 'CompareExportMeatController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.CompareExportMeatController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/CompareExportMeatController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.compare_export_quantity_report', {
                url: '/compare_export_quantity_report',
                templateUrl: 'report/views/compare_export_quantity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo so sánh giảm đàn',
                    pageSubTitle: ''
                },
                controller: 'CompareExportQuantityReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.CompareExportQuantityReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/CompareExportQuantityReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.growth_import_quantity_report', {
                url: '/growth_import_quantity_report',
                templateUrl: 'report/views/growth_import_quantity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo tăng trưởng tổng đàn',
                    pageSubTitle: ''
                },
                controller: 'GrowthImportQuantityReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.GrowthImportQuantityReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/GrowthImportQuantityReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.farm_summary_report', {
                url: '/farm_summary_report',
                templateUrl: 'report/views/farm_summary_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Bảng tổng hợp cơ sở chăn nuôi',
                    pageSubTitle: ''
                },
                controller: 'FarmSummaryReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.FarmSummaryReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/FarmSummaryReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })  
            .state('application.meat_production_report', {
                url: '/meat_production_report',
                templateUrl: 'report/views/meat_production_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo về sản lượng thịt',
                    pageSubTitle: ''
                },
                controller: 'MeatProductionReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.MeatProductionReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/MeatProductionReportController.js?v='+Education.version,
                                'report/business/MeatProductionReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
			.state('application.quantity_report_seed_level', {
                url: '/quantity_report_seed_level',
                templateUrl: 'report/views/seed_level_quantity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo số lượng động vật giống ông bà, bố mẹ',
                    pageSubTitle: ''
                },
                controller: 'SeedLevelQuantityReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.SeedLevelQuantityReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/SeedLevelQuantityReportController.js?v='+Education.version,
                                'report/business/SeedLevelQuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.egg_report', {
                url: '/egg_report',
                templateUrl: 'report/views/egg_report_view/egg_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo sản lượng trứng',
                    pageSubTitle: ''
                },
                controller: 'EggReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.EggReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/egg_report_controllers/EggReportController.js?v='+Education.version,                                
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'report/business/QuantityReportService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.compare_egg_report', {
                url: '/compare_egg_report',
                templateUrl: 'report/views/egg_report_view/compare_egg_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo so sánh sản lượng trứng',
                    pageSubTitle: ''
                },
                controller: 'CompareEggReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.CompareEggReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/egg_report_controllers/CompareEggReportController.js?v='+Education.version,                                
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'report/business/QuantityReportService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.livestock_capacity', {
                url: '/livestock_capacity',
                templateUrl: 'report/views/livestock_capacity_report_view/livestock_capacity_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo qui mô chăn nuôi theo vùng',
                    pageSubTitle: ''
                },
                controller: 'LivestockCapacityController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.LivestockCapacityController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/livestock_capacity_report_controllers/LivestockCapacityController.js?v='+Education.version,                                
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'report/business/QuantityReportService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.live_stock_product_report', {
                url: '/s_live_stock_product_report',
                templateUrl: 'report/views/live_stock_product_report_view/report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo sản lượng sản phẩm chăn nuôi động vật',
                    pageSubTitle: ''
                },
                controller: 'LiveStockProductReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.LiveStockProductReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/live_stock_product_report_controllers/LiveStockProductReportController.js?v='+Education.version,                                
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'report/business/QuantityReportService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.compare_live_stock_product_report', {
                url: '/cp_compare_live_stock_product_report',
                templateUrl: 'report/views/live_stock_product_report_view/compare_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo so sánh sản lượng sản phẩm chăn nuôi động vật',
                    pageSubTitle: ''
                },
                controller: 'CompareLiveStockProductReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.CompareLiveStockProductReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/live_stock_product_report_controllers/CompareLiveStockProductReportController.js?v='+Education.version,                                
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'report/business/QuantityReportService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
			.state('application.percent_of_growth_report', {
                url: '/percent_of_growth_report',
                templateUrl: 'report/views/percent_of_growth/percent_of_growth_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo phần trăm tăng trưởng theo đầu con',
                    pageSubTitle: ''
                },
                controller: 'PercentOfGrowthReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.PercentOfGrowthReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/percent_of_growth_report_controller/PercentOfGrowthReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,								
								 'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
			.state('application.meat_percent_of_growth_report', {
                url: '/meat_percent_of_growth_report',
                templateUrl: 'report/views/percent_of_growth/percent_of_growth_meat.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo phần trăm tăng trưởng sản lượng thịt',
                    pageSubTitle: ''
                },
                controller: 'MeatPercentOfGrowthReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.MeatPercentOfGrowthReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/percent_of_growth_report_controller/MeatPercentOfGrowthReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,								
								 'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
			.state('application.egg_percent_of_growth_report', {
                url: '/egg_percent_of_growth_report',
                templateUrl: 'report/views/percent_of_growth/percent_of_growth_egg.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo phần trăm tăng trưởng sản lượng trứng',
                    pageSubTitle: ''
                },
                controller: 'EggPercentOfGrowthReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.EggPercentOfGrowthReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/percent_of_growth_report_controller/EggPercentOfGrowthReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,								
								 'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
			.state('application.egg_summary_report', {
                url: '/summary_egg_report',
                templateUrl: 'report/views/egg_summary_report_view/egg_summary_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Bảng tổng hợp sản lượng trứng',
                    pageSubTitle: ''
                },
                controller: 'EggSummaryReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.EggSummaryReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/egg_summary_report_controllers/EggSummaryReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'report/business/MeatProductionReportService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            }) 
			.state('application.seed_level_egg_summary_report', {
                url: '/sl_egg_summary_report',
                templateUrl: 'report/views/egg_summary_report_view/egg_seed_level_summary_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Bảng tổng hợp trứng từ đàn ông bà, bố mẹ',
                    pageSubTitle: ''
                },
                controller: 'EggSeedLevelSummaryReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.EggSeedLevelSummaryReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/egg_summary_report_controllers/EggSeedLevelSummaryReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'report/business/MeatProductionReportService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            }) 
			.state('application.meat_productivity_forecast_report', {
                url: '/meat_productivity_forecast_report',
                templateUrl: 'report/views/productivity_forecast_report_view/meat_productivity_forecast_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Dự báo năng suất sản lượng thịt',
                    pageSubTitle: ''
                },
                controller: 'MeatProductivityForecastReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.MeatProductivityForecastReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/productivity_forecast_controllers/MeatProductivityForecastReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'report/business/MeatProductionReportService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            
			.state('application.predict_the_number_of_live_meat_slightly', {
                url: '/predict_the_number_of_live_meat_slightly',
                templateUrl: 'report/views/productivity_forecast_report_view/predict_the_number_of_live_meat_slightly.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Dự báo số con thịt hơi xuất chuồng',
                    pageSubTitle: ''
                },
                controller: 'PredictTheNumberOfLiveMeatSlightlyController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.PredictTheNumberOfLiveMeatSlightlyController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/productivity_forecast_controllers/PredictTheNumberOfLiveMeatSlightlyController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'report/business/MeatProductionReportService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.compare_predict_the_number_of_live_meat_slightly', {
                url: '/compare_predict_the_number_of_live_meat_slightly',
                templateUrl: 'report/views/productivity_forecast_report_view/compare_predict_the_number_of_live_meat_slightly.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'So sánh số con thịt hơi xuất chuồng',
                    pageSubTitle: ''
                },
                controller: 'ComparePredictTheNumberOfLiveMeatSlightlyController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ComparePredictTheNumberOfLiveMeatSlightlyController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/productivity_forecast_controllers/ComparePredictTheNumberOfLiveMeatSlightlyController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'report/business/MeatProductionReportService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
			.state('application.eggs_productivity_forecast_report', {
                url: '/eggs_productivity_forecast_report',
                templateUrl: 'report/views/productivity_forecast_report_view/eggs_productivity_forecast_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Dự báo năng suất trứng',
                    pageSubTitle: ''
                },
                controller: 'EggProductivityForecastReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.EggProductivityForecastReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/productivity_forecast_controllers/EggProductivityForecastReportController.js?v='+Education.version,
                                'report/business/QuantityReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'report/business/MeatProductionReportService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.export_egg_follow_the_herd', {
                url: '/export_egg_follow_the_herd',
                templateUrl: 'report/views/egg_report_view/export_egg_follow_the_herd.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo sản lượng trứng theo đàn',
                    pageSubTitle: ''
                },
                controller: 'ExportEggFollowTheHerdController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ExportEggFollowTheHerdController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/egg_report_controllers/ExportEggFollowTheHerdController.js?v='+Education.version,                                
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'report/business/QuantityReportService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.raising_animal_report', {
                url: '/raising_animal_report',
                templateUrl: 'report/views/animal_raising_report.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo động vật nuôi',
                    pageSubTitle: ''
                },
                controller: 'AnimalRaisingReportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.AnimalRaisingReportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/AnimalRaisingReportController.js?v='+Education.version,                                
								'report/business/AnimalRaisingReportService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.current_status_report_by_animal', {
                url: '/current_status_report_by_animal',
                templateUrl: 'report/views/report_animal/report_current_status_by_animal.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo động vật nuôi',
                    pageSubTitle: ''
                },
                controller: 'ReportCurrentStatusByAnimalController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportCurrentStatusByAnimalController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/report_animal/ReportCurrentStatusByAnimalController.js?v='+Education.version,                                
								'report/business/ReportCurrentStatusByAnimalService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.current_status_report_by_unit', {
                url: '/current_status_report_by_unit',
                templateUrl: 'report/views/report_animal/report_current_status_by_unit.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo động vật nuôi',
                    pageSubTitle: ''
                },
                controller: 'ReportCurrentStatusByUnitController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportCurrentStatusByUnitController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/report_animal/ReportCurrentStatusByUnitController.js?v='+Education.version,                                
								'report/business/ReportCurrentStatusByAnimalService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.report_number_time_import_export', {
                url: '/report_number_time_import_export',
                templateUrl: 'report/views/report_animal/report_number_time_import_export.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo nhập - xuất động vật hoang dã',
                    pageSubTitle: ''
                },
                controller: 'ReportNumberTimeImportExportController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportNumberTimeImportExportController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/report_animal/ReportNumberTimeImportExportController.js?v='+Education.version,                                
								'report/business/ReportCurrentStatusByAnimalService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.report_import_export_form_16', {
                url: '/report_import_export_form_16',
                templateUrl: 'report/views/report_animal/report_import_export_form_16.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo nhập - xuất dựa theo mẫu 16',
                    pageSubTitle: ''
                },
                controller: 'ReportImportExportForm16Controller as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportImportExportForm16Controller',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/report_animal/ReportImportExportForm16Controller.js?v='+Education.version,                                
								'report/business/ReportCurrentStatusByAnimalService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.report_animal_dangerous_cites', {
                url: '/report_animal_dangerous_cites',
                templateUrl: 'report/views/report_animal/report_animal_dangerous_cites.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'BÁO CÁO HOẠT ĐỘNG NUÔI ĐỘNG VẬT RỪNG NGUY CẤP, QUÝ, HIẾM, ĐỘNG VẬT RỪNG THÔNG THƯỜNG VÀ ĐỘNG VẬT THUỘC CÁC PHỤ LỤC CITES',
                    pageSubTitle: ''
                },
                controller: 'ReportAnimalDangerousCitesController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportAnimalDangerousCitesController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/report_animal/ReportAnimalDangerousCitesController.js?v='+Education.version,                                
								'report/business/ReportCurrentStatusByAnimalService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.report_animal_current_by_family', {
                url: '/report_animal_current_by_family',
                templateUrl: 'report/views/report_animal/report_animal_current_by_family.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo tình trạng nuôi động vật hoang dã theo họ',
                    pageSubTitle: ''
                },
                controller: 'ReportAnimalCurrentByFamilyController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportAnimalCurrentByFamilyController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/report_animal/ReportAnimalCurrentByFamilyController.js?v='+Education.version,                                
								'report/business/ReportCurrentStatusByAnimalService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.report_import_animal', {
                url: '/report_import_animal',
                templateUrl: 'report/views/report_animal/report_import_animal.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo tình trạng tăng số lượng động vật hoang dã ',
                    pageSubTitle: ''
                },
                controller: 'ReportImportAnimalController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportImportAnimalController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/report_animal/ReportImportAnimalController.js?v='+Education.version,                                
								'report/business/ReportCurrentStatusByAnimalService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.report_export_animal', {
                url: '/report_export_animal',
                templateUrl: 'report/views/report_animal/report_export_animal.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo tình trạng giảm số lượng động vật hoang dã ',
                    pageSubTitle: ''
                },
                controller: 'ReportExportAnimalController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportExportAnimalController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/report_animal/ReportExportAnimalController.js?v='+Education.version,                                
								'report/business/ReportCurrentStatusByAnimalService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            .state('application.report_forest_product_list', {
                url: '/report_forest_product_list',
                templateUrl: 'report/views/report_animal/report_forest_product_list.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Báo cáo sổ theo dõi xác nhận bảng kê lâm sản ',
                    pageSubTitle: ''
                },
                controller: 'ReportForestProductListController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ReportForestProductListController',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'report/controllers/report_animal/ReportForestProductListController.js?v='+Education.version,                                
								'report/business/ReportCurrentStatusByAnimalService.js?v='+Education.version,
								'fms_region/business/FmsRegionService.js?v='+Education.version,
								'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
								'animal/business/AnimalService.js?v='+Education.version,
								'ownership/business/OwnershipService.js?v='+Education.version,
                                'forest_product_list/business/ForestProductListService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
            ;
        
        
    }]);

})();