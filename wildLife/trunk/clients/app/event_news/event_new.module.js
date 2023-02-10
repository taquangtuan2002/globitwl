(function () {
    'use strict';

    Education.EventNew = angular.module('Education.EventNew', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.EventNew.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            .state('application.event_news', {
                url: '/eventnews',
                templateUrl: 'event_news/views/event_news.html',
			   //templateUrl: 'common/views/error.html',
                data: {
                    icon: 'fa fa-paw',
                    pageTitle: 'EventNew',
                    pageSubTitle: 'Chưa được thiết lập hệ thống'
                },
                controller: 'EventNewController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.EventNew',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'event_news/controllers/EventNewController.js',
                                'event_news/business/EventNewService.js',
                               
                            ]
                        });
                    }]
                }
            });
    }]);

})();