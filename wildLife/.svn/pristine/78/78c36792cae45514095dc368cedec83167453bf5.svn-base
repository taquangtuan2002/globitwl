/**
 * Created by nguyen the dat on 22/3/2018.
 */
(function () {
    'use strict';

    angular.module('Education.CmsCategory').controller('CmsCategoryController', CmsCategoryController);

    CmsCategoryController.$inject = [
        '$rootScope',
        '$scope',
        'toastr',
        '$timeout',
        'settings',
        'Utilities',
        '$uibModal',
        'CmsCategoryService',
        'Upload',
		'$translate',
		'blockUI'
    ];
    

    function CmsCategoryController($rootScope, $scope, toastr, $timeout, settings, utils, modal, service,Upload,$translate,blockUI) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        
        var vm = this;

        vm.category = {};
        vm.categorys = [];
        vm.selectedCategories = [];

        vm.pageIndex = 0;
        vm.pageSize = 25;
		vm.pageIndexNot = 0;
        vm.pageSizeNot = 25;
		vm.isRole=false;
		 vm.modalInstance ={};
		
		//------Start--Phân quyền theo user đăng nhập-----------
		blockUI.start();
        service.getCurrentUser().then(function (data) {
            console.log(data);
            vm.user=data;
            var roles = data.roles;
           
            if(roles!=null && roles.length>0){
                for(var i=0; i<roles.length;i++){
                    if (roles[i]!=null &&  roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' ||roles[i].authority.toLowerCase() == 'role_dlp')) {
                        vm.isRole = true;						
                    }
					break;
                }
            }else{
                vm.isRole=false;
				
            }
			if(vm.isRole){
				vm.getPageCategory();
			}else{
				vm.getPageCategoryNotAdmin();
			}
			
			
            blockUI.stop();
        });
		
		
		
		
		
		//--------End ----Phân quyền-------------
        
        vm.getPageCategory = function () {
            service.getPageCategory(vm.pageIndex, vm.pageSize).then(function (data) {
                vm.categorys = data.content;
                console.log( vm.categorys );
                vm.bsTableControl.options.data = vm.categorys;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        };
		vm.getPageCategoryNotAdmin = function () {
            service.getPageCategory(vm.pageIndexNot, vm.pageSizeNot).then(function (data) {
                
				vm.categoryNotAdmins = data.content;
                console.log( vm.categoryNotAdmins );
                vm.bsTableControlNotAdmin.options.data = vm.categoryNotAdmins;
                vm.bsTableControlNotAdmin.options.totalRows = data.totalElements;
            });
        };
        
        
        vm.bsTableControl = {
            options: {
                data: vm.categorys,
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
                        vm.selectedCategories.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedCategories = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedCategories);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedCategories.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedCategories = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    vm.getPageCategory();
                }
            }
        };
		
		vm.bsTableControlNotAdmin = {
            options: {
                data: vm.categoryNotAdmins,
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
                
                onPageChange: function (index, pageSize) {
                    vm.pageSizeNot = pageSize;
                    vm.pageIndexNot = index;
                    vm.getPageCategoryNotAdmin();
                }
            }
        };

        /**
         * New event account
         */
        vm.newCategory = function () {

            vm.category.isNew = true;

            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_category_modal.html',
                scope: $scope,
                backdrop:'static',
                size: 'md'
            });

            vm.modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {

                    if (!vm.category.title || vm.category.title.trim() == '') {
						toastr.warning($translate.instant("cmscategory.emptyTitle"), $translate.instant("toastr.warning"));
                        //toastr.error('Vui lòng nhập tiêu đề.', 'Lỗi');
                        return;
                    }

                    if (!vm.category.description || vm.category.description.trim() == '') {
                       toastr.warning($translate.instant("cmscategory.emptyDescription"), $translate.instant("toastr.warning"));
                        return;
                    }
           
                    service.createCategory(vm.category, function success() {

                        // Refresh list
                        vm.getPageCategory();

                        // Notify
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));

                        // clear object
                        vm.category = {};
                    }, function failure() {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    });
                }
            }, function () {
                vm.category = {};
                console.log('Modal dismissed at: ' + new Date());
            });
        };

        /**
         * Edit a account
         * @param accountId
         */
        $scope.editCategory = function (id) {
            service.getCategoryById(id).then(function (data) {

                vm.category = data;
                vm.category.isNew = false;

                vm.modalInstance = modal.open({
                    animation: true,
                    templateUrl: 'edit_category_modal.html',
                    scope: $scope,
                    backdrop: 'static',
                    size: 'md'
                });

                vm.modalInstance.result.then(function (confirm) {
                    if (confirm == 'yes') {

                        if (!vm.category.title || vm.category.title.trim() == '') {
                            toastr.warning($translate.instant("cmscategory.emptyTitle"), $translate.instant("toastr.warning"));
                            return;
                        }

                        if (!vm.category.description || vm.category.description.trim() == '') {
                            toastr.warning($translate.instant("cmscategory.emptyDescription"), $translate.instant("toastr.warning"));
                            return;
                        }

                        service.updateCategory(vm.category, function success() {

                            // Refresh list
                            vm.getPageCategory();

                            // Notify
                            toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));

                            // clear object
                            vm.category = {};
                        }, function failure() {
                           toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        });
                    }
                }, function () {
                    vm.category = {};
                    console.log('Modal dismissed at: ' + new Date());
                });
            });
        };
		
		function validate(){
			if (!vm.category.title || vm.category.title.trim() == '') {
				toastr.warning($translate.instant("cmscategory.emptyTitle"), $translate.instant("toastr.warning"));
				return false;
			}

			if (!vm.category.description || vm.category.description.trim() == '') {
				toastr.warning($translate.instant("cmscategory.emptyDescription"), $translate.instant("toastr.warning"));
				return false;
			}
			return true;
		}
		vm.save=function(){
			if(validate()){
				if(vm.category.isNew == true){
					service.createCategory(vm.category, function success() {

                        // Refresh list
                        vm.getPageCategory();
						vm.modalInstance.close();

                        // Notify
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));

                        // clear object
                        vm.category = {};
                    }, function failure() {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    });
				}else{
					service.updateCategory(vm.category, function success() {

						// Refresh list
						vm.getPageCategory();
						vm.modalInstance.close();
						// Notify
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));

						// clear object
						vm.category = {};
					}, function failure() {
					   toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
					});
				}
				
			}
		}

        
        $scope.deleteCategory = function (id) {
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_modal.html',
                scope: $scope,
                size: 'md'
            });

            modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                    service.deleteCategoryById(id, function success() {

                        // Refresh list
                        vm.getPageCategory();

                        // Notify
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));

                        // Clear selected accounts
                        vm.selectedCategories = [];
                    }, function failure() {
                        toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    });
                }
            }, function () {
                console.log('Modal dismissed at: ' + new Date());
            });
        };
        
    }

})();