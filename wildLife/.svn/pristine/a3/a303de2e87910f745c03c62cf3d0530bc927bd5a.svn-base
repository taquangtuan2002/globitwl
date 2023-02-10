(function () {
    'use strict';

    Education.Settings = angular.module('Education.Settings', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',

        'Education.Common'
    ]);

    Education.Settings.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            // General Settings
            .state('application.settings', {
                url: '/settings',
                templateUrl: 'settings/views/general.html?v='+Education.version,
                data: {pageTitle: 'Settings'},
                controller: 'SettingsController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Settings',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'settings/controllers/SettingsController.js?v='+Education.version,
                                'settings/business/SettingsService.js?v='+Education.version
                            ]
                        });
                    }]
                }
            });
    }]);

})();