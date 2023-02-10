(function () {
    'use strict';

    Education.TechnicalStaff = angular.module('Education.TechnicalStaff', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.Settings.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            //TechnicalStaff Listing
            .state('application.technicalStaff', {
                url: '/technicalStaff',
                templateUrl: 'technicalStaff/views/technicalStaff.html?v=' + Education.version,
                data: { 
                    pageTitle: 'TechnicalStaff' ,
                    pageSubTitle: 'Nhân viên kỹ thuật'

            },
                controller: 'TechnicalStaffController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.TechnicalStaff',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'technicalStaff/controllers/TechnicalStaffController.js?v=' + Education.version,
                                'technicalStaff/business/TechnicalStaffService.js?v=' + Education.version,
                                'fms_administrative_unit/business/FmsAdministrativeService.js?v=' + Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();