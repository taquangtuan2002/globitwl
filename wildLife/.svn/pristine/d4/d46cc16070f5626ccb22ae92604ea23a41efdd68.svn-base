/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.LiveStockProduct').controller('LiveStockProductController', LiveStockProductController);

    LiveStockProductController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'LiveStockProductService',
		'AnimalService',
		'UnitService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate'
    ];
    
    function LiveStockProductController ($rootScope, $scope, $http, $timeout,settings,service,animalService,unitService,modal,toastr,$state,blockUI,$stateParams,utils,$translate) {
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
        vm.liveStockProducts = []
        vm.modalLiveStockProduct={}
        vm.liveStockProduct = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedLiveStockProducts = [];
        vm.count = 0;
		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';

		/*
         * Loại động vật
         */
        vm.getListParentAnimal = function(){
        	animalService.getListParent().then(function (data) {
        		vm.parentAnimals = data;
                console.log(vm.parentAnimals)
            });
        }
        vm.getListParentAnimal();
		
		/*
         * Đơn vị tính
         */
        vm.getListUnit = function(){
        	unitService.getAllUnitDto().then(function (data) {
        		vm.units = data;
                console.log(vm.units)
            });
        }
        vm.getListUnit();
        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.liveStockProducts,
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
                        vm.selectedLiveStockProducts.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedLiveStockProducts = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedLiveStockProducts);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedLiveStockProducts.splice(index, 1);
                        });
                        
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedLiveStockProducts = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
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
            service.getPageSearch(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
                vm.liveStockProducts = data.content;
                vm.bsTableControl.options.data = vm.liveStockProducts;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        vm.findBy();

        vm.createLiveStockProduct = function (){
            vm.liveStockProduct = {isNew : true};

            vm.modalLiveStockProduct = modal.open({
                animation: true,
                templateUrl: 'live_stock_product_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });



        };

        $scope.editLiveStockProduct=function (id){
            if(id){
                service.getById(id).then(function(data){
                    vm.liveStockProduct = data;
                    vm.liveStockProduct.isNew =false;
					vm.tempCode = vm.liveStockProduct.code;
                    console.log(vm.liveStockProduct);
                });
                vm.modalLiveStockProduct = modal.open({
                    animation: true,
                    templateUrl: 'live_stock_product_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
            }
        }

        $scope.deleteLiveStockProduct =function deleteUnit(id){
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.deleteLiveStockProducts =function (){
            if(vm.selectedLiveStockProducts.length == 0){
                //toastr.warning("Bạn chưa chọn!")
				toastr.warning($translate.instant("liveStockProduct.emptySelected"), $translate.instant("toastr.warning"));
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

        vm.saveLiveStockProduct  =function (){
            if(vm.liveStockProduct){
                
                /*if(!vm.unit.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/
				if(vm.liveStockProduct.isNew==false){
					vm.liveStockProduct.code=vm.tempCode;
				}
                service.saveLiveStockProduct(vm.liveStockProduct,function success(data){
					toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
					vm.findBy();
                    vm.modalLiveStockProduct.close();
                },function failure(error){
					toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    console.log(error);
                });
            }
        }

        vm.confirmDelete =function (){
            vm.pageIndex = 1;
            var ids = vm.selectedLiveStockProducts.map(function(element){
                return element.id;
            });
            service.deleteServiceByIds(ids,function success(data){
                //vm.getAllOriginal();
				vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedLiveStockProducts = [];
            },function failure(error){
                console.log(error);
            });
       }
       $scope.deleteLiveStockProductRecord = function(id){
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
            service.deleteById(vm.recordId).then(function (data){
                if(data!=null && data.code=="-1"){
				 toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                }
				else{
                    toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                }
                //vm.getAllOriginal();
				vm.findBy();
                vm.modalConfirmDelete.close(); 
                vm.selectedLiveStockProducts = [];
            });
			/*,function failure(error){
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                vm.modalConfirmDelete.close(); 
            });*/
           }
       }
	   function validate() {
		    console.log(vm.liveStockProduct);
			if(!vm.liveStockProduct.code){
				 toastr.warning($translate.instant("liveStockProduct.emptyCode"), $translate.instant("toastr.warning"));
				return;
			}
			if(!vm.liveStockProduct.name){
				toastr.warning($translate.instant("liveStockProduct.emptyName"), $translate.instant("toastr.warning"));
				return;
			}
			if(!vm.liveStockProduct.animal){
				toastr.warning($translate.instant("liveStockProduct.emptyAnimal"), $translate.instant("toastr.warning"));
				return;
			}
			if(!vm.liveStockProduct.unitQuantity && !vm.liveStockProduct.unitAmount){
				toastr.warning($translate.instant("liveStockProduct.emptyUnit"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.liveStockProduct.isNew==false && (vm.tempCode==null || vm.tempCode=="")){
				toastr.warning($translate.instant("liveStockProduct.emptyCode"), $translate.instant("toastr.warning"));
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
                            
							vm.saveLiveStockProduct();
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
                                    vm.liveStockProduct.code = vm.tempCode.trim();
                                    
									vm.saveLiveStockProduct();
                                }
                            });
                        }else{
                            vm.liveStockProduct.code = vm.tempCode.trim();
                            
							vm.saveLiveStockProduct();
                        }
                    }
                }
                console.log(data);

            });
        }
		vm.checkDuplicateCode = function (type,action) {
            if(validate()){
                checkDuplicateCode(vm.liveStockProduct.code,type,action);
            }
        }

    }

})();
