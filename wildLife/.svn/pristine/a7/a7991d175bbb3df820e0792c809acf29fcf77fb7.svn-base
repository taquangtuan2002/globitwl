(function () {
    'use strict';

    /**
     * Chi tiet mot ban tin...
     */
    angular.module('Education.News').controller('NewsEntryController', NewsEntryController);

    NewsEntryController.$inject = [
        '$rootScope',
        '$scope',
        'toastr',
        '$state',
        '$stateParams',
        'NewsService'
    ];

    function NewsEntryController($rootScope, $scope, toastr, $state, $stateParams, service) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
        });

        let vm = this;
        vm.news = {};
        vm.getListCategory = function () {
            service.getListCategory(1, 1000).then(function (data) {
                vm.listCategory = data.content;
                console.log(vm.listCategory);
            });
        };
        vm.getListCategory();
        
        vm.getPageCmsArticleOrderByViewDESC = function () {
            service.getPageCmsArticleOrderByViewDESC(1, 3).then(function (data) {
                vm.listCmsArticleOrderByViewDESC = data.content;
            });
        };
        vm.getPageCmsArticleOrderByViewDESC();

        vm.getNewsById = function () {
            if (vm.newsId != null && vm.newsId != '') {
                service.getNewsById(vm.newsId).then(function (data) {
                    if (data != null && data.id != null) {
                        vm.news = data;
                    }
                });
            }
        };

        if ($stateParams.newsId != null && $stateParams.newsId != '') {
            vm.newsId = $stateParams.newsId;
            //lượt xem + 1
            service.seeNews(vm.newsId);
            vm.getNewsById();
        }

        
        vm.getListNewsMenuRight = function () {
            service.getListNews(1, 3).then(function (data) {
                vm.listNewsMenuRight = data.content;
            });
        };
        vm.getListNewsMenuRight();

        vm.getListNewsMuchRead = function () {
            service.getListNews(1, 3).then(function (data) {
                vm.listNewsMuchRead = data.content;
            });
        };
        vm.getListNewsMuchRead();

        vm.linkToNewsByCategory = function (cateId) {
            $state.go('application.news', { cateId: cateId });
        }

        vm.linkToDetail = function (newsId) {
            $state.go('application.news_entry', { newsId: newsId });
        }

    }

})();