(function () {
    'use strict';

    Education.Members = angular.module('Education.Members', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.Members.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.members', {
                url: '/members',
                templateUrl: 'common/views/error.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Chưa được thiết lập hệ thống',
                    pageSubTitle: ''
                },
                controller: 'MembersController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Members',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'members/controllers/MembersController.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();