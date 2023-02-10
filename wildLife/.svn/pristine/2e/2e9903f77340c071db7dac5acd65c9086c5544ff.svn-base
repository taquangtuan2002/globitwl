/**
 * Created by nguyen the dat on 22/3/2018.
 */
(function () {
    'use strict';

    angular.module('Education.CmsArticle').controller('CmsArticleController', CmsArticleController);

    CmsArticleController.$inject = [
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
        '$window',
		'$translate',
		'blockUI'
    ];
   

    function CmsArticleController($rootScope, $scope, toastr, $timeout, settings, utils, modal, service, Upload, cmsCategoryService, cmsArticleTypeService,$state,$window,$translate,blockUI) {
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
		vm.pageIndexNot = 0;
        vm.pageSizeNot = 25;
		vm.isRole=false;
		vm.title = "";
		//------Start--Phân quyền theo user đăng nhập-----------
		blockUI.start();
        service.getCurrentUser().then(function (data) {
            console.log(data);
            vm.user=data;
            var roles = data.roles;
           
            if(roles!=null && roles.length>0){
                for(var i=0; i<roles.length;i++){
                    if (roles[i]!=null &&  roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' || roles[i].authority.toLowerCase() == 'role_dlp'|| roles[i].authority.toLowerCase() == 'role_sdah'|| roles[i].authority.toLowerCase() == 'role_sdah_view')) {
                        vm.isRole = true;						
                    }
					break;
                }
            }else{
                vm.isRole=false;				
            }
            vm.getSearchArticleByDto();
//			if(vm.isRole){
//				vm.getArticles();
//			}else{
//				vm.getArticleNotAdmins();
//			}
			
			
            blockUI.stop();
        });
        
		
		
		
		//--------End ----Phân quyền-------------
        vm.searchDto={}
        vm.maxSize=5;
		vm.getSearchArticleByDto = function(){
			if(vm.selectedCategory!=null)
				vm.searchDto.categoryId=vm.selectedCategory.id;
			else
				vm.searchDto.categoryId = null;
			vm.searchDto.title = vm.title;
			vm.searchDto.pageSize= vm.pageSize;
			vm.searchDto.pageIndex = vm.pageIndex;
			
			service.getSearchArticleByDto(vm.searchDto, function (data) {
            }, function (error) {

            }).then(function (data) {
                if (data) {
                    vm.articles = data.content;
                    vm.totalItem = data.totalElements
                    console.log(vm.articles);
                    vm.articles.forEach(article => {
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
                    });
                    if(vm.isRole==false){
//                    	vm.bsTableControlNotAdmin.state.pageNumber = vm.pageIndex;
                        vm.bsTableControlNotAdmin.options.data = vm.articleNots;
                        vm.bsTableControlNotAdmin.options.totalRows = data.totalElements;
                    }
                    else if(vm.isRole==true){
//                    	vm.bsTableControl.state.pageNumber = vm.pageIndex;
                        vm.bsTableControl.options.data = vm.articles;
                        vm.bsTableControl.options.totalRows = data.totalElements;
                    }
                    
                }
            });
		}
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
//		
//		vm.getArticleNotAdmins = function () {
//            service.getArticles(vm.pageIndex, vm.pageSize).then(function (data) {
//                vm.articleNots = data.content;
//                console.log(vm.articleNots);
//                vm.articleNots.forEach(article => {
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
//                vm.bsTableControlNotAdmin.options.data = vm.articleNots;
//                vm.bsTableControlNotAdmin.options.totalRows = data.totalElements;
//            });
//        };

        
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
                    vm.pageIndex = index;
                    vm.getSearchArticleByDto();
//                    vm.search();
                }
            }
        };
		
		vm.bsTableControlNotAdmin = {
            options: {
                data: vm.articleNots,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                // showColumns: true,
                // showToggle: true,
                pagination: true,
                pageSize: vm.pageSizeNot,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionNotAdmin(),
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
                    vm.pageSizeNot = pageSize;
                    vm.pageIndexNot = index;
//                    vm.searchByNot();
                    vm.getSearchArticleByDto();
                }
            }
        };

        /**
         * New event account
         */
        vm.newArticle = function () {
            console.log($window.location.origin+window.location.pathname+'#/CmsArticleEdit');
            var url = $window.location.origin + $window.location.pathname+'#/CmsArticleEdit/';
            $window.open(url,'_blank');
            return;
            vm.article.titleImageUrl = null;
            vm.article.isNew = true;

            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_article_modal.html',
                scope: $scope,
                backdrop: 'static',
                size: 'md'
            });
            $scope.onSelected(null);
            modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {

                    $scope.onSelected(null);
                    service.saveArticle(vm.article, function success() {

                        // Refresh list
                        //vm.getArticles();
                        vm.getSearchArticleByDto();
                        // Notify
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));

                        // clear object
                        vm.article = {};
                    }, function failure() {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    });
                }
            }, function () {
                vm.article = {};
                console.log('Modal dismissed at: ' + new Date());
            });
        };


        /**
         * Edit a account
         * @param accountId
         */
        $scope.editArticle = function (articleId) {
            console.log($window.location.origin+window.location.pathname+'#/CmsArticleEdit');
            var url = $window.location.origin + $window.location.pathname+'#/CmsArticleEdit/'+articleId;
            $window.open(url,'_blank');
            return;
            service.getArticle(articleId).then(function (data) {

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

                var modalInstance = modal.open({
                    animation: true,
                    templateUrl: 'edit_article_modal.html',
                    scope: $scope,
                    backdrop: 'static',
                    size: 'lg'
                });

                modalInstance.result.then(function (confirm) {
                    if (confirm == 'yes') {

                        // if (!vm.article.code || vm.article.code.trim() == '') {
                        //     toastr.error('Vui lòng nhập mã phòng ban.', 'Lỗi');
                        //     return;
                        // }

                        // if (!vm.article.name || vm.article.name.trim() == '') {
                        //     toastr.error('Vui lòng nhập tên phòng ban.', 'Lỗi');
                        //     return;
                        // }
                        $scope.onSelected(null);
                        if (vm.articles) {
                            vm.articles.forEach(article => {
                                if (vm.article.listCategory) {
                                    for (let index = 0; index < vm.article.listCategory.length; index++) {
                                        const category = vm.article.listCategory[index];
                                        if (!article.categories[index]) {
                                            article.categories[index] = {};
                                        }
                                        article.categories[index].category = category;
                                        if (article.id) {
                                            article.categories[index].article = { id: article.id };
                                        }
                                    }
                                }
                            });
                        };
                        service.updateArticle(vm.article, function success() {

                            // Refresh list
                            //vm.getArticles();
                            vm.getSearchArticleByDto();
                            // Notify
                            toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                            // clear object
                            vm.article = {};
                        }, function failure() {
                            toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        });
                    }
                }, function () {
                    vm.article = {};
                    console.log('Modal dismissed at: ' + new Date());
                });
            });
        };
		
		
        /**
         * View a account
         * @param accountId
         */
        $scope.viewArticle = function (articleId) {
            console.log($window.location.origin+window.location.pathname+'#/CmsArticleView');
            var url = $window.location.origin + $window.location.pathname+'#/CmsArticleView/'+articleId;
            $window.open(url,'_blank');
            return;
            service.getArticle(articleId).then(function (data) {

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

                var modalInstance = modal.open({
                    animation: true,
                    templateUrl: 'edit_article_modal.html',
                    scope: $scope,
                    backdrop: 'static',
                    size: 'lg'
                });

                modalInstance.result.then(function (confirm) {
                    if (confirm == 'yes') {

                        // if (!vm.article.code || vm.article.code.trim() == '') {
                        //     toastr.error('Vui lòng nhập mã phòng ban.', 'Lỗi');
                        //     return;
                        // }

                        // if (!vm.article.name || vm.article.name.trim() == '') {
                        //     toastr.error('Vui lòng nhập tên phòng ban.', 'Lỗi');
                        //     return;
                        // }
                        $scope.onSelected(null);
                        if (vm.articles) {
                            vm.articles.forEach(article => {
                                if (vm.article.listCategory) {
                                    for (let index = 0; index < vm.article.listCategory.length; index++) {
                                        const category = vm.article.listCategory[index];
                                        if (!article.categories[index]) {
                                            article.categories[index] = {};
                                        }
                                        article.categories[index].category = category;
                                        if (article.id) {
                                            article.categories[index].article = { id: article.id };
                                        }
                                    }
                                }
                            });
                        };
                        service.updateArticle(vm.article, function success() {

                            // Refresh list
                            //vm.getArticles();
                            vm.getSearchArticleByDto();
                            // Notify
                            toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                            // clear object
                            vm.article = {};
                        }, function failure() {
                            toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        });
                    }
                }, function () {
                    vm.article = {};
                    console.log('Modal dismissed at: ' + new Date());
                });
            });
        };

        /**
         * Delete accounts
         */
        $scope.deleteArticle = function (id) {
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_modal.html',
                scope: $scope,
                size: 'lg'
            });

            modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                    service.deleteArticleById(id, function success() {

                        // Refresh list
//                        vm.getArticles();
                        vm.getSearchArticleByDto();
                        // Notify
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));


                    }, function failure() {
                        toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    });
                }
            }, function () {
                console.log('Modal dismissed at: ' + new Date());
            });
        };


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
            else if(!cmsCategory && !vm.selectedCategory){
            	return 'active';
            }
            return '';
        }
        vm.selectCategory = function (el) {
            vm.selectedCategory = 0;
            vm.selectedCategory = el;
            vm.pageIndex = 1;
//			if(vm.isRole){
//                vm.pageIndex = 1;
//				vm.search();
//			}else{
//                vm.pageIndex = 1;
//				vm.searchByNot();
//			}
            vm.getSearchArticleByDto();
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
            console.log(vm.articles);
        };
        
        
        
//        vm.search = function () {
//            service.getSearchArticleByDto({
//                categoryId: vm.selectedCategory ? vm.selectedCategory.id : null,
//                title: vm.title,
//                pageSize: vm.pageSize,
//                pageIndex: vm.pageIndex
//            }, function (data) {
//
//            }, function (error) {
//
//            }).then(function (data) {
//                if (data) {
//                    vm.articles = data.content;
//                    console.log(vm.articles);
//                    vm.articles.forEach(article => {
//                        if (article.categories) {
//                            for (let index = 0; index < article.categories.length; index++) {
//                                const category = article.categories[index];
//                                if (!article.listCategory) {
//                                    article.listCategory = [];
//                                    article.listCategory[index] = (category.category);
//                                } else {
//                                    article.listCategory[index] = (category.category);
//                                }
//                            }
//                        }
//                    });
//                    vm.bsTableControl.state.pageNumber = vm.pageIndex;
//                    vm.bsTableControl.options.data = vm.articles;
//                    vm.bsTableControl.options.totalRows = data.totalElements;
//                }
//            });
//        };
//		vm.searchByNot = function () {
//            service.getSearchArticleByDto({
//                categoryId: vm.selectedCategory ? vm.selectedCategory.id : null,
//                title: vm.title,
//                pageSize: vm.pageSizeNot,
//                pageIndex: vm.pageIndexNot
//            }, function (data) {
//
//            }, function (error) {
//
//            }).then(function (data) {
//                if (data) {
//                    vm.articleNots = data.content;
//                    console.log(vm.articleNots);
//                    vm.articleNots.forEach(article => {
//                        if (article.categories) {
//                            for (let index = 0; index < article.categories.length; index++) {
//                                const category = article.categories[index];
//                                if (!article.listCategory) {
//                                    article.listCategory = [];
//                                    article.listCategory[index] = (category.category);
//                                } else {
//                                    article.listCategory[index] = (category.category);
//                                }
//                            }
//                        }
//                    });
//                    vm.bsTableControlNotAdmin.state.pageNumber = vm.pageIndex;
//                    vm.bsTableControlNotAdmin.options.data = vm.articleNots;
//                    vm.bsTableControlNotAdmin.options.totalRows = data.totalElements;
//                }
//            });
//        };

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
                    url: vm.baseUrl + 'cms/article/uploadattachment',
                    data: {uploadfile: file}
                });

                file.upload.then(function (response) {
                    console.log(response);
                    file.result = response.data;
                    vm.file = response.data;
                    vm.article.titleImageUrl = response.data.name;
                    toastr.info('Import file thành công.', 'Thông báo');
                },function errorCallback(response) {
                    toastr.error('Import file lỗi.', 'Lỗi');
                });
            }
        };

        vm.tinymceOptions = {
			height: 130,
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
    }

})();