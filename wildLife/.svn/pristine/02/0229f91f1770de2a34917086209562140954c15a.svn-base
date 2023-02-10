(function () {
    'use strict';

    Education.Link = angular.module('Education.Link', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.Link.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.Link', {
                url: '/link',
                templateUrl: 'link/views/link.html?v='+Education.version,
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'Chia sẻ liên kết',
                },
                controller: 'LinkController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.Link',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'Link/controllers/LinkController.js?v='+Education.version,
                                'Link/business/LinkService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            });
    }]);

})();