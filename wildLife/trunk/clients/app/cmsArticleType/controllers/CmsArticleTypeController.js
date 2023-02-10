/**
 * Created by nguyen the dat on 22/3/2018.
 */
(function () {
    'use strict';

    angular.module('Education.CmsArticleType').controller('CmsArticleTypeController', CmsArticleTypeController);

    CmsArticleTypeController.$inject = [
        '$rootScope',
        '$scope',
        'toastr',
        '$timeout',
        'settings',
        'Utilities',
        '$uibModal',
        'CmsArticleTypeService',
        'Upload',
		'$translate',
		'blockUI'
    ];

    function CmsArticleTypeController($rootScope, $scope, toastr, $timeout, settings, utils, modal, service,Upload,$translate,blockUI) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        
        var vm = this;

        vm.articleType = {};
        vm.articleTypes = [];
        vm.selecteddepartments = [];
        vm.modalInstance=null;
        vm.viewCheckDuplicateCode={};

        vm.pageIndex = 0;
        vm.pageSize = 25;
		vm.pageIndexNot = 0;
        vm.pageSizeNot = 25;
        vm.isRole=false;
        vm.tempCode = '';
		
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
				vm.getArticleTypes();
			}else{
				vm.getArticleTypeNotAdmins();
			}
			
			
            blockUI.stop();
        });
		
		
		
		
		
		//--------End ----Phân quyền-------------
        
        vm.getArticleTypes = function () {
            service.getArticleTypes(vm.pageIndex, vm.pageSize).then(function (data) {
                vm.articleTypes = data.content;
                console.log( vm.articleTypes );
                vm.bsTableControl.options.data = vm.articleTypes;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        };
        vm.getArticleTypeNotAdmins = function () {
            service.getArticleTypes(vm.pageIndexNot, vm.pageSizeNot).then(function (data) {
                vm.articleTypeNots = data.content;
                console.log( vm.articleTypeNots );
                vm.bsTableControlNotAdmin.options.data = vm.articleTypeNots;
                vm.bsTableControlNotAdmin.options.totalRows = data.totalElements;
            });
        };
        //vm.getArticleTypes();
        vm.bsTableControl = {
            options: {
                data: vm.articleTypes,
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
                    vm.getArticleTypes();
                }
            }
        };
		
		vm.bsTableControlNotAdmin = {
            options: {
                data: vm.articleTypeNots,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                //showColumns: true,
                //showToggle: true,
                pagination: true,
                pageSize: vm.pageSizeNot,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionNotAdmin(),
                
                onPageChange: function (index, pageSize) {
                    vm.pageSizeNot = pageSize;
                    vm.pageIndexNot = index - 1;
                    vm.getArticleTypeNotAdmins();
                }
            }
        };

        /**
         * New event account
         */
        vm.newArticleType = function () {
			vm.articleType={};
            vm.articleType.isNew = true;

            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_article_type_modal.html',
                scope: $scope,
                backdrop:'static',
                size: 'md'
            });
        };

        /**
         * Edit a account
         * @param accountId
         */
        $scope.editArticleType = function (id) {
            service.getArticleType(id).then(function (data) {

                vm.articleType = data;
                vm.articleType.isNew = false;
				vm.tempCode = vm.articleType.code;
                vm.modalInstance = modal.open({
                    animation: true,
                    templateUrl: 'edit_article_type_modal.html',
                    scope: $scope,
                    backdrop:'static',
                    size: 'md'
                });

             
            });
        };



        /**
         * Delete accounts
         */
        vm.saveArticle=function(){
            if(vm.articleType.isNew){
                

                service.createArticleType(vm.articleType, function success() {

                    // Refresh list
                    vm.getArticleTypes();

                    // Notify
                    toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                    
                    vm.modalInstance.close();
                    // clear object
                    vm.articleType = {};
                }, function failure() {
                    toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                });

            }
            else{
					
                 vm.articleType.code=vm.tempCode;
                 service.createArticleType(vm.articleType, function success() {

                     // Refresh list
                     vm.getArticleTypes();

                     // Notify
                     toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));                  
                     // clear object
                    vm.modalInstance.close();
                     vm.articleType = {};
                 }, function failure() {
                     toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                 });
            }
		}

////////////////////////////

		function validate() {
			console.log("vm.articleType");
			console.log(vm.articleType);
				if (vm.articleType.code==null) {
					toastr.warning($translate.instant("cmsArticleType.emptyCode"), $translate.instant("toastr.warning"));
					return false;
				}
				if (vm.articleType.name==null) {
				   toastr.warning($translate.instant("cmsArticleType.emptyName"), $translate.instant("toastr.warning"));
					return false;
				}
				if(vm.articleType.isNew==false && (vm.tempCode==null|| vm.tempCode=="")){
					toastr.warning($translate.instant("cmsArticleType.emptyCode"), $translate.instant("toastr.warning"));
					return false;
				}
			return true;
		}
		function checkDuplicateCode(code,type,action){ //type: 1 -> save; 2 -> edit;   action: 1 -> just check code; 2 -> save or edit
			service.checkDuplicateCode(code).then(function(data) {
				vm.viewCheckDuplicateCode = data;
				console.log(action,type);
				if(action == 1){
					if(type == 1){
						if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true){
						   // toastr.warning("Mã bị trùng");
							toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
						}
						if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false){
							//toastr.success("Mã không bị trùng");
							toastr.success($translate.instant("checkCode.codeDuplicateNot"), $translate.instant("toastr.info"));
						}
					}
					if(type == 2){
						if(vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()){
							checkDuplicateCode(vm.tempCode,1,1);
						}else{
						   // toastr.info("Mã chưa thay đổi");
							toastr.info($translate.instant("checkCode.codeNotChange"), $translate.instant("toastr.info"));
						}
					}
				}
				if(action == 2){
					if(type == 1){
						if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true){
							//toastr.warning("Mã bị trùng");
							toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
						}
						if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false){
							
							vm.saveArticle();
						}
					}
					if(type == 2){
						if(vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()){
							service.checkDuplicateCode(vm.tempCode).then(function(data) {
								vm.viewCheckDuplicateCode = data;
								if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true){
									//toastr.warning("Mã bị trùng");
									toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
								}
								if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false){
									vm.articleType.code = vm.tempCode.trim();
									
									vm.saveArticle();
								}
							});
						}else{
							vm.articleType.code = vm.tempCode.trim();
							
							vm.saveArticle();
						}
					}
				}
				console.log(data);

			});
		}
		
		vm.checkDuplicateCode = function (type,action) {
			if(validate()){
				console.log("Aaaaa");
				checkDuplicateCode(vm.articleType.code,type,action);
			}
		}
///////////////////////////
        
        $scope.deleteArticleType = function (id) {
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_modal.html',
                scope: $scope,
                size: 'md'
            });

            modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                    service.deleteArticleById(id, function success() {

                        // Refresh list
                        vm.getArticleTypes();

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
      
    }

})();