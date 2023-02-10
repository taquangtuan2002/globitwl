/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.FmsSeedLevel').controller('SeedLevelController', SeedLevelController);

    SeedLevelController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        'SeedLevelService',
		'$translate'
    ];

    function SeedLevelController($rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, service,$translate) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;

        vm.user = {}; //
        vm.users = []; // Mảng
        vm.seedLevel = {}; // mảng 
        vm.seedLevels = [];
        vm.id = null;

        vm.selectedseedLevel = [];

        vm.roles = [];
        vm.groups = [];

        // UI
        vm.modalInstance = null;

        // pagination
        vm.pageIndex = 0;
        vm.pageSize = 10;

        vm.changePass = false;
		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';

        /**
         * Get a list of seedLevel
         */
        vm.getSeedLevels = function() {
            service.getSeedLevels(vm.pageIndex, vm.pageSize).then(function(data) {
                vm.seedLevels = data.content;
                vm.bsTableControl.options.data = vm.seedLevels;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        };

        vm.getSeedLevels();

        /**
         * Get all roles
         */
        service.getAllRoles().then(function(data) {
            if (data && data.length > 0) {
                vm.roles = data;
            } else {
                vm.roles = [];
            }
        });

        /**
         * Get all user groups
         */
        service.getAllGroups().then(function(data) {
            if (data && data.length > 0) {
                vm.groups = data;
            } else {
                vm.groups = [];
            }
        });

        vm.saveSeedLevel = function() {
			if(vm.seedLevel.isNew){
                service.saveSeedLevel(vm.seedLevel).then(function(data) {
                    toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                     // Reload seedLevel
                     //vm.getSeedLevels();
                     vm.getSeedLevels();
                     vm.modalInstance.close();
                     vm.findBy();
                 }, function errorCallback(response) {
                     toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                 });
			}else{
                vm.seedLevel.code=vm.tempCode;
                service.saveSeedLevel(vm.seedLevel).then(function(data) {
                    toastr.info($translate.instant("toastr.editSuccess"), $translate.instant("toastr.info"));
                     // Reload seedLevel
                     //vm.getSeedLevels();
                     vm.getSeedLevels();
                     vm.modalInstance.close();
                     vm.findBy();
                 }, function errorCallback(response) {
                     toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                 });
            }
            

        };

        /**
         * Create a new user
         */
        vm.newSeedLevel = function() {
            vm.seedLevel = { isNew: true };

            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_modal.html',
                backdrop: 'static',
                scope: $scope,
                size: 'lg'
            });
        };

       
        $scope.$watch('vm.user.person.displayName', function(newVal, oldVal) {

            if (!newVal) {
                return;
            }

            var fullname = String(newVal).trim();
            if (fullname.length <= 0) {
                return;
            }

            var spaceIndex = fullname.indexOf(' ');

            if (spaceIndex > 0) {
                vm.user.person.firstName = fullname.substr(0, spaceIndex);
                vm.user.person.lastName = fullname.substr(spaceIndex + 1);
            }
        });


        // search user by username
        vm.textSearch = '';

        function findUserByUserName(username, pageIndex, pageSize) {
            service.findUserByUserName(username, pageIndex, pageSize).then(function(data) {
                vm.seedLevels = data.content;
                vm.bsTableControl.options.data = vm.seedLevels;
                vm.bsTableControl.options.totalRows = data.totalElements;
                console.log(data);
            });
        }

        vm.searchByCode = function() {
            vm.textSearch = String(vm.textSearch).trim();
            vm.findBy();
        }

        vm.bsTableControl = {
            options: {
                data: vm.seedLevel,
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
                        // if (row.username && row.username != 'admin') {
                        //     vm.selectedseedLevel.push(row);
                        // } else {
                        //     bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                        // }
                        vm.selectedseedLevel.push(row);
                    });
                },
                onCheckAll: function(rows) {
                    $scope.$apply(function() {
                        // angular.forEach(rows, function(row) {
                        //     if (row.username && row.username != 'admin') {
                        //         vm.selectedseedLevel.push(row);
                        //     } else {
                        //         bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                        //     }
                        // });
                        vm.selectedseedLevel = rows;
                    });
                },
                onUncheck: function(row, $element) {
                    var index = utils.indexOf(row, vm.selectedseedLevel);
                    if (index >= 0) {
                        $scope.$apply(function() {
                            vm.selectedseedLevel.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function(rows) {
                    $scope.$apply(function() {
                        vm.selectedseedLevel = [];
                    });
                },
                onPageChange: function(index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    // vm.getSeedLevels();
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
            service.getPageSearchSeedLevel(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
                vm.seedLevels = data.content;
                vm.bsTableControl.options.data = vm.seedLevels;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
		
        $scope.editSeedLevel = function(id) {
            service.getSeedLevel(id).then(function(data) {
                if (data && data.id) {
                    vm.seedLevel = data;
                    vm.seedLevel.isNew = false;
					vm.tempCode = vm.seedLevel.code;
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

        $scope.deleteSeedLevel = function(id) {
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
            service.deleteSeedLevelId(vm.id).then(function(data) {
				 vm.seedLevel = data;
				//  if(vm.seedLevel!=null && vm.seedLevel.code=="-1"){
                //     toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                // }
				// else{
                //     toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                // }
                toastr.info($translate.instant("toastr.del1"), $translate.instant("toastr.info"));
                vm.seedLevel.isNew = false;
                vm.getSeedLevels();
                vm.modalInstance.close();
				vm.findBy();
            });
        }
		
		function validate() {
		    console.log(vm.seedLevel);
            if (typeof vm.seedLevel.level != "number") {
                toastr.warning($translate.instant("seedLevel.requiredLevel"), $translate.instant("toastr.warning"));
                return false;
            }
			
            if (!vm.seedLevel.name && vm.seedLevel.name.trim() == '') {
                toastr.warning($translate.instant("seedLevel.requiredName"), $translate.instant("toastr.warning"));
                return false;
            }
			if(vm.seedLevel.isNew==false && (vm.tempCode==null || vm.tempCode=="")){
				toastr.warning($translate.instant("seedLevel.requiredCode"), $translate.instant("toastr.warning"));
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
                        
                        vm.saveSeedLevel();
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
                                vm.seedLevel.code = vm.tempCode.trim();
                                
                                vm.saveSeedLevel();
                            }
                        });
                    }else{
                        vm.saveSeedLevel();
                    }
                }
            }
            console.log(data);

        });
    }
		vm.checkDuplicateCode = function (type,action) {
            if(validate()){
                checkDuplicateCode(vm.seedLevel.code,type,action);
            }
        }

        vm.save = function(){
            if(validate()){
                vm.saveSeedLevel();
            }
        }

    }

})();