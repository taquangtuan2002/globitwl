(function () {
    'use strict';

    Education.CmsCategory = angular.module('Education.CmsCategory', [
        'ui.router',
        'oc.lazyLoad',
        'bsTable',
        'toastr',
        'ui.select',
        'Education.Common'
    ]);

    	Education.CmsCategory.config(['$stateProvider', function ($stateProvider) {

        $stateProvider

            // Event priority
            .state('application.cmscategory', {
                url: '/cmscategory',
                templateUrl: 'cmscategory/views/cmscategory.html',
                data: {pageTitle: 'Danh mục chủ đề'},
                controller: 'CmsCategoryController as vm',
                resolve: {
                    deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'Education.CmsCategory',
                            insertBefore: '#ng_load_plugins_before',
                            files: [
                                'cmscategory/controllers/CmsCategoryController.js',
                                'cmscategory/business/CmsCategoryService.js'
                            ]
                        });
                    }]
                }
            });
    }]);

})();