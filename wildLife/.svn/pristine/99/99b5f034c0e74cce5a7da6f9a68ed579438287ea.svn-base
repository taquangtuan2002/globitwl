(function () {
    'use strict';

    angular.module('Education.News').controller('NewsController', NewsController);

    NewsController.$inject = [
        '$rootScope',
        '$scope',
        'toastr',
        '$state',
        '$stateParams',
        'NewsService'
    ];

    function NewsController($rootScope, $scope, toastr, $state, $stateParams, service) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
        });

        let vm = this;

        vm.pageIndex = 1;
        vm.pageSize = 5;
        vm.totalPage = 1;
        vm.cateId = null;
        vm.category = {};

        vm.getListCategory = function () {
            service.getListCategory(1, 1000).then(function (data) {
                vm.listCategory = data.content;
                console.log(data);
            });
        };
        vm.getListCategory();

        vm.getPageCmsArticleOrderByViewDESC = function () {
            service.getPageCmsArticleOrderByViewDESC(1, 3).then(function (data) {
                vm.listCmsArticleOrderByViewDESC = data.content;
            });
        };
        vm.getPageCmsArticleOrderByViewDESC();
        
        vm.getListNewsByCatetegoryId = function () {
        	vm.pageIndex = 1;
            service.getListNewsByCatetegoryId(vm.cateId, vm.pageIndex, vm.pageSize, function success() { }, function failure() {
                toastr.error('Có lỗi xảy ra khi lấy tin bài theo chuyên mục vui lòng thử lại.', 'Thông báo');
            }).then(function (data) {
                vm.listNews = data.content;
                vm.totalPage = data.totalPages;
            });
        };

        if ($stateParams.cateId != null && $stateParams.cateId != '' && $stateParams.cateId > 0) {
            vm.cateId = parseInt($stateParams.cateId);
            vm.getListNewsByCatetegoryId();
            vm.category = {};
            service.getCategoryById(vm.cateId).then(function (data) {
                vm.category = data;
            });
        }

        vm.getListNews = function () {
            if (vm.cateId != null && vm.cateId != '' && vm.cateId > 0) {
                vm.getListNewsByCatetegoryId();
            }
            else {
                service.getListNews(vm.pageIndex, vm.pageSize).then(function (data) {
                    vm.listNews = data.content;
                    console.log(data);
                    vm.totalPage = data.totalPages;
                });
            }
        };

        if (vm.cateId != null && vm.cateId != '' && vm.cateId > 0) {
            vm.getListNewsByCatetegoryId();
        } else {
            vm.getListNews();
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

        vm.linkToDetail = function (newsId) {
            $state.go('application.news_entry', { newsId: newsId });
        }

        vm.linkToNewsByCategory = function (cateId) {
            $state.go('application.news', { cateId: cateId });
        }

        vm.prePage = function () {
            if (vm.pageIndex > 1) {
                vm.pageIndex--;
                vm.getListNews();
            }
        }

        vm.nextPage = function () {
            if (vm.pageIndex < vm.totalPage) {
                vm.pageIndex++;
                vm.getListNews();
            }
        }

    }

})();