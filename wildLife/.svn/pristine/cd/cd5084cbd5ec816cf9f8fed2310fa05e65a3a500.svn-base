(function() {
    'use strict';

    Education.Ownership = angular.module('Education.Ownership', [
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

    Education.Ownership.config(['$stateProvider', function($stateProvider) {

        $stateProvider

        // Ownership Listing
            .state('application.ownership', {
            url: '/ownership',
            templateUrl: 'ownership/views/ownership.html?v='+Education.version,
            data: {
                icon: 'icon-equalizer',
                pageTitle: 'Ecoregion',
                pageSubTitle: 'Hình thức sở hữu'
            },
            controller: 'OwnershipController as vm',
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'Education.Ownership',
                        insertBefore: '#ng_load_plugins_before',
                        files: [
                            'ownership/controllers/OwnershipController.js?v='+Education.version,
                            'ownership/business/OwnershipService.js?v='+Education.version
                        ]
                    });
                }]
            }
        })
    }]);

})();