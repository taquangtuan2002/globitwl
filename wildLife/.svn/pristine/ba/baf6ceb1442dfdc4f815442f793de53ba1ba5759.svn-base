(function () {
    'use strict';

    Education.ImportDrug = angular.module('Education.ImportDrug', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.ImportDrug.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.import_drug', {
                url: '/idrug_import',
                templateUrl: 'import_drug/views/import_drug.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'import drug',
                    pageSubTitle: ''
                },
                controller: 'ImportDrugController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ImportDrug',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'import_drug/controllers/ImportDrugController.js?v='+Education.version,
                                'import_drug/business/ImportDrugService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version,
                                'drug/business/DrugService.js?v='+Education.version,
                                'unit/business/UnitService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();