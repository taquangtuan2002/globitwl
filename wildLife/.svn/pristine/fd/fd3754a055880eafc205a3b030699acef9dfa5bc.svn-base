(function () {
    'use strict';

    Education.FarmDuplicateDoubts = angular.module('Education.FarmDuplicateDoubts', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common',
        'ngFileSaver'
    ]);

    Education.FarmDuplicateDoubts.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.farm_duplicate_doubts', {
                url: '/farm_duplicate_doubts',
                templateUrl: 'farm_duplicate_doubts/views/farm_duplicate_doubts.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Farm Doubts',
                    pageSubTitle: ''
                },
                controller: 'FarmDuplicateDoubtsController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.FarmDuplicateDoubts',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'farm_duplicate_doubts/controllers/FarmDuplicateDoubtsController.js?v='+Education.version,
                                'farm_duplicate_doubts/business/FarmDuplicateDoubtsService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            })
            ;
    }]);

})();