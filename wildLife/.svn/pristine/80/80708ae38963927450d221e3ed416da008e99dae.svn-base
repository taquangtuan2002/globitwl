/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.Ownership').controller('OwnershipController', OwnershipController);

    OwnershipController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        'OwnershipService',
		'$translate'
    ];

    function OwnershipController($rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, service,$translate) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;

        vm.user = {}; // Doi tượng
        vm.users = []; // Mảng
        vm.ownership = {}; // mảng Region
        vm.ownerships = [];
        vm.id = null;

        vm.selectedOwnership = [];

        // UI
        vm.modalInstance = null;

        // pagination
        vm.pageIndex = 0;
        vm.pageSize = 10;

		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        
        vm.saveOwnership = function() {
			if(vm.ownership.isNew==false){
                vm.ownership.code=vm.tempCode;
                service.save(vm.ownership,vm.ownership.id).then(function(data) {
                    toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                     // Reload Ownership
                     //vm.getOwnerships();
                     vm.findBy();
                     vm.modalInstance.close();
                 }, function errorCallback(response) {
                     toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                 });
			}
            if(vm.ownership.isNew == true){
                service.save(vm.ownership).then(function(data) {
                    toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                     // Reload Ownership
                     //vm.getOwnerships();
                     vm.findBy();
                     vm.modalInstance.close();
                 }, function errorCallback(response) {
                     toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                 });
            }
        };

        /**
         * Create a new user
         */
        vm.newOwnership = function() {
            vm.ownership = { isNew: true };

            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_modal.html',
                backdrop: 'static',
                scope: $scope,
                size: 'lg'
            });
        };


        // search user by username
        vm.textSearch = '';
        vm.searchByCode = function() {
            vm.textSearch = String(vm.textSearch).trim();
            vm.getOwnerShips();
        }

        vm.bsTableControl = {
            options: {
                data: vm.ownership,
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
                onCheck: function(row, $element) {
                    $scope.$apply(function() {
                        if (row.username && row.username != 'admin') {
                            vm.selectedOwnership.push(row);
                        } else {
                            bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                        }
                        // vm.selectedOwnership.push(row);
                    });
                },
                onCheckAll: function(rows) {
                    $scope.$apply(function() {
                        angular.forEach(rows, function(row) {
                            if (row.username && row.username != 'admin') {
                                vm.selectedOwnership.push(row);
                            } else {
                                bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                            }
                        });
                        // vm.selectedOwnership = rows;
                    });
                },
                onUncheck: function(row, $element) {
                    var index = utils.indexOf(row, vm.selectedOwnership);
                    if (index >= 0) {
                        $scope.$apply(function() {
                            vm.selectedOwnership.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function(rows) {
                    $scope.$apply(function() {
                        vm.selectedOwnership = [];
                    });
                },
                onPageChange: function(index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;

                    //vm.getOwnerships();
					vm.findBy();
                }
            }
        };
		vm.enterSearch = function(){
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
            service.getOwnerShips(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
                vm.ownership = data.content;
                vm.bsTableControl.options.data = vm.ownership;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
		
        $scope.editOwnership = function(id) {
            service.getOwnershipDtoById(id).then(function(data) {
                if (data && data.id) {
                    vm.ownership = data;
                    vm.ownership.isNew = false;
					vm.tempCode = vm.ownership.code;
                    vm.modalInstance = modal.open({
                        animation: true,
                        templateUrl: 'edit_modal.html',
                        backdrop: 'static',
                        scope: $scope,
                        size: 'lg'
                    });
                }
            });
        }

        $scope.deleteOwnership = function(id) {
            vm.id = id;
            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'delete_modal.html',
                backdrop: 'static',
                scope: $scope,
                size: 'md'
            });


        }
        vm.confirmDelete = function() {			
            // alert(vm.id)
            service.removeById(vm.id).then(function(data) {
				 vm.ownership = data;
				 if(vm.ownership!=null && vm.ownership.code=="-1"){
                    toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                }
				else{
                    toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                }

                vm.ownership.isNew = false;
                //vm.getOwnerships();
				vm.findBy();
                vm.modalInstance.close();
            });
        }
		
		function validate() {
		    console.log(vm.ownership);
            if (!vm.ownership.code) {
                toastr.warning($translate.instant("region.emptyCode"), $translate.instant("toastr.warning"));
                return false;
            }
			
            if (!vm.ownership.name) {
                toastr.warning($translate.instant("region.emptyName"), $translate.instant("toastr.warning"));
                return false;
            }
			if(vm.ownership.isNew==false && (vm.tempCode==null || vm.tempCode=="")){
				toastr.warning($translate.instant("region.emptyCode"), $translate.instant("toastr.warning"));
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
                            
							vm.saveOwnership();
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
                                    vm.ownership.code = vm.tempCode.trim();
                                    
									vm.saveOwnership();
                                }
                            });
                        }else{
                            vm.ownership.code = vm.tempCode.trim();
                            
							vm.saveOwnership();
                        }
                    }
                }
                console.log(data);

            });
        }
		vm.checkDuplicateCode = function (type,action) {
            if(validate()){
                checkDuplicateCode(vm.ownership.code,type,action);
            }
        }
        vm.findBy();

    }

})();