(function () {
    'use strict';

    Education.CmsArticleType = angular.module('Education.CmsArticleType', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'ui.select',
        'Education.Common'
    ]);

    	Education.CmsArticleType.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            // Event priority
            .state('application.CmsArticleType', {
                url: '/type/cmsArticleType',
                templateUrl: 'cmsArticleType/views/CmsArticleType.html',
                data: {pageTitle: 'Loại bài báo'},
                controller: 'CmsArticleTypeController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.CmsArticleType',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'cmsArticleType/controllers/CmsArticleTypeController.js',
                                'cmsArticleType/business/CmsArticleTypeService.js'
                            ]
                        });
                    }]
                }
            });
    }]);

})();