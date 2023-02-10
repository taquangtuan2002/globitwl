(function () {
    'use strict';

    Education.Original = angular.module('Education.Original', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.Original.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.original', {
                url: '/original',
                templateUrl: 'original/views/original.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Original',
                    pageSubTitle: ''
                },
                controller: 'OriginalController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Original',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'original/controllers/OriginalController.js?v='+Education.version,
                                'original/business/OriginalService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();