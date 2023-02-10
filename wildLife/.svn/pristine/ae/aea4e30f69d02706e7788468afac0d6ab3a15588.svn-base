(function () {
    'use strict';

    Education.ExportDrug = angular.module('Education.ExportDrug', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.ExportDrug.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.export_drug', {
                url: '/export_drug',
                templateUrl: 'export_drug/views/export_drug.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'export drug',
                    pageSubTitle: ''
                },
                controller: 'ExportDrugController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ExportDrug',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'export_drug/controllers/ExportDrugController.js?v='+Education.version,
                                'export_drug/business/ExportDrugService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version,
                                'drug/business/DrugService.js?v='+Education.version,
                                'unit/business/UnitService.js?v='+Education.version,
                                'import_animal/business/ImportAnimalService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            });
    }]);

})();