(function () {
    'use strict';

    Education.ExportAnimalFeed = angular.module('Education.ExportAnimalFeed', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.ExportAnimalFeed.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.export_animal_feed', {
                url: '/feed_animal_export',
                templateUrl: 'export_animal_feed/views/export_animal_feed.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'export animal feed',
                    pageSubTitle: ''
                },
                controller: 'ExportAnimalFeedController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Farm',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'export_animal_feed/controllers/ExportAnimalFeedController.js?v='+Education.version,
                                'export_animal_feed/business/ExportAnimalFeedService.js?v='+Education.version,
                                'farm/business/farmService.js?v='+Education.version,
                                'unit/business/UnitService.js?v='+Education.version,
                                'bran/business/BranService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            });
    }]);

})();