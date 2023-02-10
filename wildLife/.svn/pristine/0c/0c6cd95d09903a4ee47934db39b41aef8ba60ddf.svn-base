(function () {
    'use strict';

    Education.ExportEgg = angular.module('Education.ExportEgg', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.ExportEgg.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.export_egg', {
                url: '/export_egg',
                templateUrl: 'exportEgg/views/export_egg.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'export egg',
                    pageSubTitle: ''
                },
                controller: 'ExportEggController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.ExportEgg',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'exportEgg/controllers/ExportEggController.js?v='+Education.version,
                                'exportEgg/business/ExportEggService.js?v='+Education.version,
                                'importEgg/business/ImportEggService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            });
    }]);

})();