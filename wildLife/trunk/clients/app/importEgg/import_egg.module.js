(function () {
    'use strict';

    Education.ImportEgg = angular.module('Education.ImportEgg', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.ImportEgg.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.import_egg', {
                url: '/import_egg',
                templateUrl: 'importEgg/views/import_egg.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'export egg',
                    pageSubTitle: ''
                },
                controller: 'ImportEggController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ImportEgg',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'importEgg/controllers/ImportEggController.js?v='+Education.version,
                                'importEgg/business/ImportEggService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version,
                                'import_animal/business/ImportAnimalService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();