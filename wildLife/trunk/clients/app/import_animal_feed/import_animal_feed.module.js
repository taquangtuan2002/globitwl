(function () {
    'use strict';

    Education.ImportAnimalFeed = angular.module('Education.ImportAnimalFeed', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.ImportAnimalFeed.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.import_animal_feed', {
                url: '/feed_animal_import',
                templateUrl: 'import_animal_feed/views/import_animal_feed.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'import animal feed',
                    pageSubTitle: ''
                },
                controller: 'ImportAnimalFeedController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.AnimalFeed',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'import_animal_feed/controllers/ImportAnimalFeedController.js?v='+Education.version,
                                'import_animal_feed/business/ImportAnimalFeedService.js?v='+Education.version,
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