/**
 * Created by nguyen the dat on 22/3/2018.
 */
(function () {
    'use strict';

    angular.module('Education.CmsArticle').controller('CmsArticleEditController', CmsArticleEditController);

    CmsArticleEditController.$inject = [
        '$rootScope',
        '$scope',
        'toastr',
        '$timeout',
        'settings',
        'Utilities',
        '$uibModal',
        'CmsArticleService',
        'Upload',
        'CmsCategoryService',
        'CmsArticleTypeService',
        '$state',
		'$translate'
    ];

    

    function CmsArticleEditController($rootScope, $scope, toastr, $timeout, settings, utils, modal, service, Upload, cmsCategoryService, cmsArticleTypeService,$state,$translate) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;


        var vm = this;

        vm.article = {};
        vm.articles = [];
        vm.selecteddepartments = [];
        vm.selectedCategory = null;

        vm.pageIndex = 0;
        vm.pageSize = 25;
        vm.baseUrl = settings.api.baseUrl + settings.api.apiPrefix;

      //date time
        $scope.openDate = function () {
            $scope.popupDate.opened = true;
        };

        $scope.format = 'dd/MM/yyyy';
        $scope.altInputFormats = 'dd/MM/yyyy';

        $scope.popupDate = {
            opened: false,

        };
        
//        vm.getArticles = function () {
//            service.getArticles(vm.pageIndex, vm.pageSize).then(function (data) {
//                vm.articles = data.content;
//                console.log(vm.articles);
//                vm.articles.forEach(article => {
//                    if (article.categories) {
//                        for (let index = 0; index < article.categories.length; index++) {
//                            const category = article.categories[index];
//                            if (!article.listCategory) {
//                                article.listCategory = [];
//                                article.listCategory[index] = (category.category);
//                            } else {
//                                article.listCategory[index] = (category.category);
//                            }
//                        }
//                    }
//                });
//                vm.bsTableControl.options.data = vm.articles;
//                vm.bsTableControl.options.totalRows = data.totalElements;
//            });
//        };

//        vm.getArticles();
        vm.bsTableControl = {
            options: {
                data: vm.articles,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                // showColumns: true,
                // showToggle: true,
                pagination: true,
                pageSize: vm.pageSize,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinition(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selecteddepartments.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selecteddepartments = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selecteddepartments);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selecteddepartments.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selecteddepartments = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index - 1;
//                    vm.getArticles();
                }
            }
        };
        
        /**
         * New event account
         */
        vm.newArticle = function () {
            vm.article.titleImageUrl = null;
            vm.article.isNew = true;
            $scope.onSelected(null);
        };


        /**
         * Edit a account
         * @param accountId
         */
        $scope.editArticle = function (departmentId) {
            service.getArticle(departmentId).then(function (data) {

                vm.article = data;
                const article = vm.article;
                if (article.categories) {
                    for (let index = 0; index < article.categories.length; index++) {
                        const category = article.categories[index];
                        if (!article.listCategory) {
                            article.listCategory = [];
                            article.listCategory[index] = (category.category);
                        } else {
                            article.listCategory[index] = (category.category);
                        }
                    }
                }
                vm.article.isNew = false;

            });
        };
        vm.save = function(){
            if (vm.article == null) {
                vm.article = {};
            }
            if (vm.article.title == null || vm.article.title == '') {
                toastr.warning('Vui lòng nhập tiêu đề.', 'Lỗi');
                return;
            }
            if (vm.article.summary == null || vm.article.summary == '') {
                toastr.warning('Vui lòng nhập tóm tắt nội dung.', 'Lỗi');
                return;
            }
            if (vm.article.summary != null && vm.article.summary.length > 255) {
                toastr.warning('Tóm tắt nội dung quá dài, không thể quá 255 kí tự.', 'Lỗi');
                return;
            }
            if (vm.article.listCategory == null || vm.article.listCategory.length <= 0) {
                toastr.warning('Vui lòng chọn ít nhất 1 chủ đề.', 'Lỗi');
                return;
            }
            if (vm.article.publishDate == null || vm.article.publishDate == '') {
                toastr.warning('Vui lòng chọn ngày đăng tin.', 'Lỗi');
                return;
            }
            if (vm.article.titleImageUrl == null || vm.article.titleImageUrl == '') {
                toastr.warning('Vui lòng chọn ảnh tiêu đề.', 'Lỗi');
                return;
            }
            if (vm.article.content == null || vm.article.content == '') {
                toastr.warning('Vui lòng nhập nội dung của bài.', 'Lỗi');
                return;
            }

            if (vm.article.isNew == true) {
                service.saveArticle(vm.article, function success() {
    
                    // Refresh list
                    // vm.getArticles();
                    vm.goBack();
                    // Notify
                    toastr.info('Bạn đã lưu thành công một bản ghi.', 'Thông báo');
                    // clear object
                    vm.article = {};
                }, function failure() {
                    toastr.error('Có lỗi xảy ra khi lưu thông tin bài báo.', 'Lỗi');
                });
            }
            else{
                service.updateArticle(vm.article, function success() {
    
                    // Refresh list
                    // vm.getArticles();
                    vm.goBack();
                    // Notify
                    toastr.info('Bạn đã lưu thành công một bản ghi.', 'Thông báo');
                    // clear object
                    vm.article = {};
                }, function failure() {
                    toastr.error('Có lỗi xảy ra khi lưu thông tin bài báo.', 'Lỗi');
                });
            }
        }
        vm.goBack = function(){
            $state.go("application.CmsArticle");
        }

        vm.cmsCategories = [];
        vm.getCmsCategory = function () {
            cmsCategoryService.getPageCategory(1, 1000).then(function (data) {
                vm.cmsCategories = data.content;
            }).catch(function () {
                vm.cmsCategories = [];
            });
        };
        vm.getCmsCategory();

        vm.cmsArticleTypes = [];
        vm.getCmsArticleTypes = function () {
            cmsArticleTypeService.getArticleTypes(1, 1000).then(function (data) {
                vm.cmsArticleTypes = data.content;
            });
        }
        vm.getCmsArticleTypes();
        vm.getStyle = function (cmsCategory) {
            if (vm.selectedCategory && vm.selectedCategory.id && cmsCategory && cmsCategory.id && vm.selectedCategory.id == cmsCategory.id) {
                return 'active';
            }
            return '';
        }
        vm.selectCategory = function (el) {
            vm.selectedCategory = el;
            vm.search();
        }

        $scope.onSelected = function (item) {
            if (vm.article) {
                vm.article.categories = [];
                if (vm.article.listCategory) {
                    for (let index = 0; index < vm.article.listCategory.length; index++) {
                        const category = vm.article.listCategory[index];
                        if (!vm.article.categories[index]) {
                            vm.article.categories[index] = {};
                        }
                        vm.article.categories[index].category = category;
                        if (vm.article.id) {
                            vm.article.categories[index].article = { id: vm.article.id };
                        } else {
                            vm.article.categories[index].article = { id: null };
                        }
                    }
                }
            };
        };
        $scope.onRemove = function (item) {
            if (vm.article) {
                vm.article.categories = [];
                if (vm.article.listCategory) {
                    for (let index = 0; index < vm.article.listCategory.length; index++) {
                        const category = vm.article.listCategory[index];
                        if (!vm.article.categories[index]) {
                            vm.article.categories[index] = {};
                        }
                        vm.article.categories[index].category = category;
                        if (vm.article.id) {
                            vm.article.categories[index].article = { id: vm.article.id };
                        } else {
                            vm.article.categories[index].article = { id: null };
                        }
                    }
                }
            };
        };

      

        vm.file = [];
        vm.errFile = [];
        vm.selectFiles = function(files, errFiles) {
            vm.file = files;
            vm.errFile = errFiles;
            console.log(vm.file);
            console.log(vm.errFile);
        };

        $scope.MAX_FILE_SIZE = '2MB';
        $scope.f = null;
        $scope.errFile = null;

        $scope.uploadFiles = function(file, errFiles) {
            $scope.f = file;
            $scope.errFile = errFiles && errFiles[0];
            vm.startUploadFile(file);
        };

        vm.startUploadFile = function(file) {
            console.log(file);
            if (file) {
                file.upload = Upload.upload({
                    url: vm.baseUrl + 'file/uploadArticleImg',
                    data: {uploadfile: file}
                });

                file.upload.then(function (response) {
                    console.log(response);
                    file.result = response.data;
                    vm.file = response.data;
                    vm.article.titleImageUrl = response.data.id;
                    toastr.info($translate.instant("upload.importSuccess"), $translate.instant("toastr.info"));
                },function errorCallback(response) {
                    toastr.error($translate.instant("upload.importError"), $translate.instant("toastr.error"));
                });
            }
        };

        vm.tinymceOptions = {
			height: 300,
			theme: 'modern',
			plugins: ['lists fullscreen' // autoresize
			],
			toolbar: 'bold underline italic | removeformat | bullist numlist outdent indent | fullscreen',
			content_css: [
				'//fonts.googleapis.com/css?family=Poppins:300,400,500,600,700',
				'/assets/css/tinymce_content.css'],
			autoresize_bottom_margin: 0,
			statusbar: false,
			menubar: false,
        };
        
        function generate(){
            if($state.params && $state.params.id){
                $scope.editArticle($state.params.id);
            }else{
                vm.newArticle();
            }
            vm.getCmsCategory();
        };  
        generate();
    }

})();