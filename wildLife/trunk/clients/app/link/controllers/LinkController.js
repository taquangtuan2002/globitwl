/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Link').controller('LinkController', LinkController);

    LinkController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'LinkService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate'
    ];
    
    function LinkController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr,$state,blockUI,$stateParams,utils,$translate) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
        // blockUI.stop();
        // blockUI.start();
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.Links = []
        vm.modalLink={}
        vm.Link = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedLinks = [];
        vm.count = 0;
		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.getAllLink=function (){
            service.getAllLink(vm.pageIndex,vm.pageSize).then(function(data){
                vm.Links = data.content;
                vm.bsTableControl.options.data = vm.Links;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getAllLink();


        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.Links,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSize,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinition(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selectedLinks.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedLinks = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedLinks);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedLinks.splice(index, 1);
                        });
                        
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedLinks = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;

                    //vm.getAllOriginal();
					 vm.findBy();
                }
            }
        };

		vm.enterSearch = function(){
            // console.log(event.keyCode);
            if(event.keyCode == 13){//Phím Enter
                
                vm.searchByCode();
            }
        };
		 vm.searchByCode = function () {
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.findBy();
        };
		vm.findBy=function () {
            if( vm.searchDto==null){
                vm.searchDto={};
            }
            if(vm.textSearch!=null&& vm.textSearch!=""){
                vm.searchDto.nameOrCode=vm.textSearch;
            }
            else{
                vm.searchDto.nameOrCode=null;
            }
            
            console.log(vm.searchDto);
            service.getPageSearchLink(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
                vm.Links = data.content;
                vm.bsTableControl.options.data = vm.Links;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        

        vm.createLink = function (){
            vm.Link = {isNew : true};
            vm.modalLink = modal.open({
                animation: true,
                templateUrl: 'link_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });



        };

        $scope.editLink=function (id){
            if(id){
                service.getLinkById(id).then(function(data){
                    vm.Link = data;
                    vm.Link.isNew =false;
					vm.tempCode = vm.Link.code;
                });
                vm.modalLink = modal.open({
                    animation: true,
                    templateUrl: 'link_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
            }
        }

        $scope.deleteLink =function deleteLink(id){
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.deleteLinks =function (){
            if(vm.selectedLinks.length == 0){
                //toastr.warning("Bạn chưa chọn!")
				toastr.warning($translate.instant("link.emptySelected"), $translate.instant("toastr.warning"));
                return;
            }

            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });

           
        }

        vm.saveLink  =function (){
            if(vm.Link){
                service.saveLink(vm.Link,function success(data){
					toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
					vm.findBy();
                    vm.modalLink.close();
                },function failure(error){
					toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    console.log(error);
                });
            }
        }

        vm.confirmDelete =function (){
            vm.pageIndex = 1;
            var ids = vm.selectedLinks.map(function(element){
                return element.id;
            });
            service.deleteServiceByIds(ids,function success(data){
                toastr.info("Xóa thành công " + vm.selectedLinks.length + " bản ghi đã lựa chọn", "Thông báo");
				vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedLinks = [];
            },function failure(error){
                toastr.error("Có lỗi xảy ra khi xóa các bạn ghi đã chọn", "Cảnh báo");
            });
       }
       $scope.deleteLinkRecord = function(id){
        if(id){
            vm.recordId = id;
        }else {
            vm.recordId = null;
        }
        vm.modalConfirmDelete = modal.open({
            animation: true,
            templateUrl: 'confirm_delete_record.html',
            scope: $scope,
            backdrop: 'static',
            keyboard: false,
            size: 'md'
        });
       }
       vm.confirmDeleteRecord = function(){
           if(vm.recordId){
            service.deleteLinkById(vm.recordId).then(function (data){
                if(data!=null && data.code=="-1"){
				 toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                }
				else{
                    toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                }
                //vm.getAllOriginal();
				vm.findBy();
                vm.modalConfirmDelete.close(); 
                vm.selectedLinks = [];
            });
			/*,function failure(error){
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                vm.modalConfirmDelete.close(); 
            });*/
           }
       }
	   function validate() {
		    console.log(vm.Link);
			if(!vm.Link.code){
				 toastr.warning($translate.instant("Link.emptyCode"), $translate.instant("toastr.warning"));
				return;
			}
			if(!vm.Link.name){
				toastr.warning($translate.instant("Link.emptyName"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.Link.isNew==false && (vm.tempCode==null || vm.tempCode=="")){
				toastr.warning($translate.instant("Link.emptyCode"), $translate.instant("toastr.warning"));
				return false;
			}
			return true;
	   }
	   
	   function checkDuplicateCode(code,type,action){ //type: 1 -> save; 2 -> edit;   action: 1 -> just check code; 2 -> save or edit
            service.checkDuplicateCode(code).then(function(data) {
                vm.viewCheckDuplicateCode = data;
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
                            
							vm.saveLink();
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
                                    vm.Link.code = vm.tempCode.trim();
                                    
									vm.saveLink();
                                }
                            });
                        }else{
                            vm.Link.code = vm.tempCode.trim();
                            
							vm.saveLink();
                        }
                    }
                }
                console.log(data);

            });
        }
		vm.checkDuplicateCode = function (type,action) {
            if(validate()){
                checkDuplicateCode(vm.Link.code,type,action);
            }
        }

    }

})();
