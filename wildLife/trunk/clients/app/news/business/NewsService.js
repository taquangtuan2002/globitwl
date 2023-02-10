(function () {
    'use strict';

    angular.module('Education.News').service('NewsService', NewsService);

    NewsService.$inject = [
    	'$http',
        '$q',
        'settings',
        'Utilities',
        '$uibModal'
    ];

    function NewsService($http, $q, settings, utils, modal) {
    	var self = this;

        var baseUrl = settings.api.baseUrl;
        var baseUrlNews = baseUrl + 'api/news/cmsArticle';
        var baseUrlCategory = baseUrl + 'api/news/cmsCategory';

        self.getListNews  = getListNews;
        self.getNewsById  = getNewsById;
        self.getListCategory  = getListCategory;
        self.getListNewsByCatetegoryId  = getListNewsByCatetegoryId;
        self.seeNews  = seeNews;
        self.getCategoryById  = getCategoryById;
        self.getPageCmsArticleOrderByViewDESC  = getPageCmsArticleOrderByViewDESC;


        function getCategoryById(id) {
            if (!id) {
                return $q.when(null);
            }
            var url = baseUrlCategory + '/getCategoryById/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getListNewsByCatetegoryId(cateId, pageIndex, pageSize, successCallback, errorCallback) {
            var url = baseUrlNews + '/searchListArticleByPage';
            
            var searchDto = {};
            searchDto.categoryId =  Number(cateId);
            searchDto.pageIndex = pageIndex;
            searchDto.pageSize = pageSize;

            return utils.resolveAlt(url, 'POST', null, searchDto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getPageCmsArticleOrderByViewDESC(pageIndex, pageSize) {

            var url = baseUrlNews + '/getPageCmsArticleOrderByViewDESC';
            url += '/' + pageIndex;
            url += '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getListCategory(pageIndex, pageSize) {

            var url = baseUrlNews + '/getAllArticleCategory';
            url += '/' + pageIndex;
            url += '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getListNews(pageIndex, pageSize) {

            var url = baseUrlNews + '/getListArticleByPage';
            url += '/' + pageIndex;
            url += '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function seeNews(id) {
            if (!id) {
                return $q.when(null);
            }
            var url = baseUrlNews + '/seeNews/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getNewsById(id) {
            if (!id) {
                return $q.when(null);
            }
            var url = baseUrlNews + '/getNewsById/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        
    }

})();