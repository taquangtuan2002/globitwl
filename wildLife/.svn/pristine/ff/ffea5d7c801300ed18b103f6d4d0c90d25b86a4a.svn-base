(function() {
    'use strict';

    Education.WildLifeAbouts = angular.module('Education.WildLifeAbouts', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',

        'Education.Common'
    ]);

    Education.WildLifeAbouts.config(['$stateProvider', function($stateProvider) {

        $stateProvider

        // wildLife Listing
            .state('application.wl_abouts', {
            url: '/wlabouts',
            templateUrl: 'wl_abouts/views/wl_abouts.html?v='+Education.version,
            data: {
                icon: 'icon-equalizer',
                pageTitle: 'Giới thiệu chung dự án',
                pageSubTitle: 'Giới thiệu chung về dự án'
            },
            controller: 'WildLifeAboutsController as vm',
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'Education.wildLife',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            'wl_abouts/controllers/WildLifeAboutsController.js?v='+Education.version,
                            'wl_abouts/business/WildLifeAboutsService.js?v='+Education.version
                        ]
                    });
                }]
            }
        })
    }]);

})();