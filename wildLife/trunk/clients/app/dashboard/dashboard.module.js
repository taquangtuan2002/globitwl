(function () {
    'use strict';

    Education.Dashboard = angular.module('Education.Dashboard', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',

        // Start: For tree table...
        'treeGrid',
        // End: For tree table

        'Education.Common'
    ]);

    Education.Dashboard.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            // Dashboard
            .state('application.dashboard', {
                url: '/dashboard',
                templateUrl: 'dashboard/views/general-alt.html?v='+Education.version,
                data: {icon: 'fa fa-desktop', pageTitle: 'Trang chủ', pageSubTitle: ''},
                controller: 'DashboardController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Dashboard',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                // 'assets/css/external/book.css',
                                'dashboard/controllers/DashboardController.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version,
                                'dashboard/business/DashboardService.js?v=' + Education.version,
                                'report/controllers/FarmPositionVGMapController.js?v=' + Education.version,
                                'report/business/QuantityReportService.js?v=' + Education.version,
                                'fms_region/business/FmsRegionService.js?v=' + Education.version,
                                'animal/business/AnimalService.js?v=' + Education.version,
                                'report/business/QuantityReportService.js?v=' + Education.version,
                                'report/business/SumQuantityReportDataService.js?v=' + Education.version		
                            ]
                        });
                    }]
                }
            })

            // Dashboard
            .state('application.privacy_policy', {
                url: '/privacy_policy',
                templateUrl: 'dashboard/views/privacy_policy.html?v='+Education.version,
                data: {icon: 'fa fa-desktop', pageTitle: 'Chính sách về quyền riêng tư', pageSubTitle: ''},
                controller: 'DashboardController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Dashboard',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                // 'assets/css/external/book.css',
                                'dashboard/controllers/DashboardController.js?v='+Education.version,
                                'farm/business/FarmService.js?v='+Education.version,
								'fms_administrative_unit/business/FmsAdministrativeService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })

            .state('application.dashboard-edu', {
                url: '/dashboard/edu',
                templateUrl: 'dashboard/views/comlet-edu.html?v='+Education.version,
                data: {icon: 'fa fa-desktop', pageTitle: 'Trang chủ', pageSubTitle: 'Quản lý'},
                controller: 'DashboardController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Dashboard',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                // 'assets/css/external/book.css',
                                'dashboard/controllers/DashboardController.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })

            .state('application.dashboard-students', {
                url: '/dashboard/students',
                templateUrl: 'dashboard/views/comlet-students.html?v='+Education.version,
                data: {icon: 'fa fa-desktop', pageTitle: 'Trang chủ', pageSubTitle: 'Quản lý sinh viên'},
                controller: 'DashboardController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Dashboard',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                // 'assets/css/external/book.css',
                                'dashboard/controllers/DashboardController.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })

            .state('application.dashboard-exam', {
                url: '/dashboard/exam',
                templateUrl: 'dashboard/views/comlet-exam.html?v='+Education.version,
                data: {icon: 'fa fa-desktop', pageTitle: 'Trang chủ', pageSubTitle: 'Quản lý khảo thí'},
                controller: 'DashboardController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Dashboard',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                // 'assets/css/external/book.css',
                                'dashboard/controllers/DashboardController.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })

            .state('application.dashboard-hr', {
                url: '/dashboard/hr',
                templateUrl: 'dashboard/views/comlet-hr.html?v='+Education.version,
                data: {icon: 'fa fa-desktop', pageTitle: 'Trang chủ', pageSubTitle: 'Quản lý nhân sự'},
                controller: 'DashboardController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Dashboard',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                // 'assets/css/external/book.css',
                                'dashboard/controllers/DashboardController.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })

            .state('application.dashboard-fee', {
                url: '/dashboard/fee',
                templateUrl: 'dashboard/views/comlet-fee.html?v='+Education.version,
                data: {icon: 'fa fa-desktop', pageTitle: 'Trang chủ', pageSubTitle: 'Quản lý học phí'},
                controller: 'DashboardController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Dashboard',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                // 'assets/css/external/book.css',
                                'dashboard/controllers/DashboardController.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })

            .state('application.dashboard-settings', {
                url: '/dashboard/settings',
                templateUrl: 'dashboard/views/comlet-settings.html?v='+Education.version,
                data: {icon: 'fa fa-desktop', pageTitle: 'Trang chủ', pageSubTitle: 'Thiết lập hệ thống'},
                controller: 'DashboardController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Dashboard',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                // 'assets/css/external/book.css',
                                'dashboard/controllers/DashboardController.js?v='+Education.version
                            ]
                        });
                    }]
                }
            })
    }]);

})();