(function () {
    'use strict';

    Education.InjectionTime = angular.module('Education.InjectionTime', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.InjectionTime.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.injectionTime', {
                url: '/typeofinjectiontime',
                templateUrl: 'injectionTime/views/injectionTime.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Type of injectionTime',
                    pageSubTitle: ''
                },
                controller: 'InjectionTimeController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.InjectionTime',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'injectionTime/controllers/InjectionTimeController.js?v='+Education.version,
                                'injectionTime/business/InjectionTimeService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();