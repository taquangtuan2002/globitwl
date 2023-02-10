(function () {
    'use strict';

    Education.News = angular.module('Education.News', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'Education.Common'
    ]);

    Education.News.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

	        .state('application.news', {
	            url: '/news/:cateId',
	            templateUrl: 'news/views/news.html?v='+Education.version,
	            data: {pageTitle: 'Thông tin News'},
	            controller: 'NewsController as vm',
	            params: {
                    cateId: {
                      value: '',
                      squash: true // or enable this instead to squash `cateId` when empty
                    } 
                },
	            resolve: {
	                deps: ['$ocLazyLoad', function ($ocLazyLoad) {
	                    return $ocLazyLoad.load({
	                        name: 'Education.News',
	                        insertBefore: '#ng_load_plugins_before',
	                        files: [
	                            'news/controllers/NewsController.js?v='+Education.version,
	                            'news/business/NewsService.js?v='+Education.version,
	                        ]
	                    });
	                }]
	            }
	        })
	        
	        .state('application.news_entry', {
                url: '/news/entry/:newsId',
                templateUrl: 'news/views/news_entry.html?v='+Education.version,
                data: {pageTitle: 'Thông tin News'},
                controller: 'NewsEntryController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.News',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'news/controllers/NewsEntryController.js?v='+Education.version,
                                'news/business/NewsService.js?v='+Education.version,
                            ]
                        });
                    }]
                }
            })
    }]);

})();