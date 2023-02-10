(function() {
    'use strict';

    Education.FmsSeedLevel = angular.module('Education.FmsSeedLevel', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',

        'Education.Common'
    ])
    .run(['$rootScope', function($rootScope){
        var version = $rootScope.version;
        console.log(version);
    }]);

    Education.FmsSeedLevel.config(['$stateProvider', function($stateProvider) {

        $stateProvider

        // FmsRegion Listing
            .state('application.fms_seed_level', {
            url: '/seed_level',
            templateUrl: 'seed_level/views/seed_level.html?v='+Education.version,
            data: {
                icon: 'icon-equalizer',
                pageTitle: 'seedlevel',
                pageSubTitle: 'Danh mục cấp giống'
            },
            controller: 'SeedLevelController as vm',
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'Education.FmsSeedLevel',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            'seed_level/controllers/SeedLevelController.js?v='+Education.version,
                            'seed_level/business/SeedLevelService.js?v='+Education.version
                        ]
                    });
                }]
            }
        })
    }]);

})();