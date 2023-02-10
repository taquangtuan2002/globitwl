/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ImportAnimalFeed').controller('ImportAnimalFeedController', ImportAnimalFeedController);

    ImportAnimalFeedController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ImportAnimalFeedService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate',
        'Upload',
        'FarmService',
        'UnitService',
        'BranService'
    ];
   
    angular.module('Education.ImportAnimalFeed').directive('numberInput', function($filter) {
    	  return {
    	    require: 'ngModel',
    	    link: function(scope, elem, attrs, ngModelCtrl) {

    	      ngModelCtrl.$formatters.push(function(modelValue) {
    	        return setDisplayNumber(modelValue, true);
    	      });

    	      // it's best to change the displayed text using elem.val() rather than
    	      // ngModelCtrl.$setViewValue because the latter will re-trigger the parser
    	      // and not necessarily in the correct order with the changed value last.
    	      // see http://radify.io/blog/understanding-ngmodelcontroller-by-example-part-1/
    	      // for an explanation of how ngModelCtrl works.
    	      ngModelCtrl.$parsers.push(function(viewValue) {
    	        setDisplayNumber(viewValue);
    	        return setModelNumber(viewValue);
    	      });

    	      // occasionally the parser chain doesn't run (when the user repeatedly 
    	      // types the same non-numeric character)
    	      // for these cases, clean up again half a second later using "keyup"
    	      // (the parser runs much sooner than keyup, so it's better UX to also do it within parser
    	      // to give the feeling that the comma is added as they type)
    	      elem.bind('keyup focus', function() {
    	        setDisplayNumber(elem.val());
    	      });

    	      function setDisplayNumber(val, formatter) {
    	        var valStr, displayValue;

    	        if (typeof val === 'undefined') {
    	          return 0;
    	        }

    	        valStr = val.toString();
    	        displayValue = valStr.replace(/,/g, '').replace(/[A-Za-z]/g, '');
    	        displayValue = parseFloat(displayValue);
    	        displayValue = (!isNaN(displayValue)) ? displayValue.toString() : '';

    	        // handle leading character -/0
    	        if (valStr.length === 1 && valStr[0] === '-') {
    	          displayValue = valStr[0];
    	        } else if (valStr.length === 1 && valStr[0] === '0') {
    	          displayValue = '';
    	        } else {
    	          displayValue = $filter('number')(displayValue);
    	        }

    	        // handle decimal
    	        if (!attrs.integer) {
    	          if (displayValue.indexOf('.') === -1) {
    	            if (valStr.slice(-1) === '.') {
    	              displayValue += '.';
    	            } else if (valStr.slice(-2) === '.0') {
    	              displayValue += '.0';
    	            } else if (valStr.slice(-3) === '.00') {
    	              displayValue += '.00';
    	            }
    	          } // handle last character 0 after decimal and another number
    	          else {
    	            if (valStr.slice(-1) === '0') {
    	              displayValue += '0';
    	            }
    	          }
    	        }

    	        if (attrs.positive && displayValue[0] === '-') {
    	          displayValue = displayValue.substring(1);
    	        }

    	        if (typeof formatter !== 'undefined') {
    	          return (displayValue === '') ? 0 : displayValue;
    	        } else {
    	          elem.val((displayValue === '0') ? '' : displayValue);
    	        }
    	      }

    	      function setModelNumber(val) {
    	        var modelNum = val.toString().replace(/,/g, '').replace(/[A-Za-z]/g, '');
    	        modelNum = parseFloat(modelNum);
    	        modelNum = (!isNaN(modelNum)) ? modelNum : 0;
    	        if (modelNum.toString().indexOf('.') !== -1) {
    	          modelNum = Math.round((modelNum + 0.00001) * 100) / 100;
    	        }
    	        if (attrs.positive) {
    	          modelNum = Math.abs(modelNum);
    	        }
    	        return modelNum;
    	      }
    	    }
    	  };
    	});
    function ImportAnimalFeedController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr,$state,blockUI,$stateParams,utils,$translate,Upload,FarmService, 
        unitService, branService) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
		vm.baseUrl = settings.api.baseUrl + settings.api.apiPrefix;
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
		vm.pageIndexNot = 1;
        vm.pageSizeNot = 25;
        vm.importAnimalFeeds = []
        vm.modalFarm={}
        vm.farm = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedImportAnimalFeeds = [];
        vm.count = 0;
        vm.listDistrictId=null;
        
        /** generate */
        //vm.getPageImportAnimal();
		vm.isRole=false;
		vm.isFamer=false;
		vm.isSdah=false;
		vm.isDistrict=false;
		vm.isWard=false;
		vm.isSdahView = false;
		vm.getCombo=function(){
			branService.getAllBranDto().then(function (data) {
                vm.brans = data;
              
            });
		}
		vm.getUnit=function(){
			unitService.getAllUnitDto().then(function (data) {
                vm.units = data;
            });
		}
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
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah' ) {
                        vm.isSdah = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_district' ) {
                        vm.isDistrict = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_ward' ) {
                        vm.isWard = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_famer' ) {
                        vm.isFamer = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah_view' ){
                        vm.isSdahView = true;
						vm.isSdah = true;							
                    }
                }
            }else{
                vm.isRole=false;
				vm.isFamer=false;
				vm.isSdah=false;
				vm.isDistrict=false;
				vm.isWard=false;
				vm.isSdahView = false;
            }
            vm.getCombo();
            vm.getUnit();
			if(vm.isRole == false && vm.isFamer==true &&  vm.user!==null && vm.user.username!==null && vm.user.username!==''){//trường hợp tài khoản nông dân               
				vm.textSearch=vm.user.username;
				vm.findBy();

            }else if(vm.isRole == true){
				vm.findByAdmin();
			}else{
				getAdministrativeUnitDtoByLoginUser();
			}
			

            blockUI.stop();
        });
		//load user administrativeunit
		function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function(data) {
                vm.adminstrativeUnits = data;
				if(vm.adminstrativeUnits!=null && vm.adminstrativeUnits.length>0){
					getDataByCombobox(vm.adminstrativeUnits[0]);
					
				}
            });
        }
    		
		function getDataByCombobox(administrativeUnit) {
			if( vm.searchDto==null){
                vm.searchDto={};
            }
			if(administrativeUnit.parentDto==null){
				vm.searchDto.province=administrativeUnit.id;
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				vm.searchDto.district=administrativeUnit.id;
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){
				vm.searchDto.ward=administrativeUnit.id;				
               
			}
			
			vm.findByAdmin();
            
        }
		//get farm by userLogin
        function getFarmByUserLogin(){
        	if(vm.isFamer){
        		service.getFarmByUserLogin().then(function(data) {
                    vm.listFarm = data;
                    if(vm.listFarm != null && vm.listFarm.length == 1){
    					if(vm.importAnimalFeed==null)
    						vm.importAnimalFeed={};
    					
                        vm.importAnimalFeed.farm = vm.listFarm[0];
                    }
                    console.log('vm.listFarm');
                    console.log(vm.listFarm);
                });
        	}            
        }
        vm.showPopupFarm = function () {
            vm.subModalInstance = modal.open({
                animation: true,
                templateUrl: 'choose_farm_modal.html',
                scope: $scope,
                size: 'lg'
            });
            vm.code = null;
            vm.selectedFarms = [];
            vm.farmSelected = {};
            //lấy danh sách cơ sở chăn nuôi phân theo quyền
            if (!vm.isFarmer) {//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
                vm.findByFarm();
            } 
            else 
            {
            	// trường hợp này thì phân quyền cơ sở chăn nuôi theo user
            	getAdministrativeUnitDtoByLoginUser();
            }

            vm.subModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {

                }
            }, function () {
                vm.farmCode = null;
                console.log("cancel");
            });
        }
        vm.pageIndexFarm=1;
        vm.pageSizeFarm=10;

        vm.enterSearchFarm = function () {
            // console.log(event.keyCode);
            if (event.keyCode == 13) {//Phím Enter

                vm.searchByFarm();
            }
        };
        vm.searchByFarm = function () {
            vm.pageIndexFarm = 1;
            vm.bsTableControlFarm.state.pageNumber = 1;
            vm.findByFarm();
        };

        vm.findByFarm = function () {
            if (vm.searchDtoFarm == null) {
                vm.searchDtoFarm = {};
            }
            if (vm.farmCode != null && vm.farmCode != "") {
                vm.searchDtoFarm.nameOrCode = vm.farmCode;
            }
            else {
                vm.searchDtoFarm.nameOrCode = null;
            }


            console.log("vm.searchDtoFarm");
            console.log(vm.searchDtoFarm);
            service.getPageSearchFarm(vm.searchDtoFarm, vm.pageIndexFarm, vm.pageSizeFarm).then(function (data) {
                vm.farms = data.content;
                vm.bsTableControlFarm.options.data = vm.farms;
                vm.bsTableControlFarm.options.totalRows = data.totalElements;
                console.log("vm.farms");
                console.log(vm.farms);
                if (vm.isFamer == true && vm.farms != null && vm.farms.length > 0) {
                    if (vm.importAnimal == null) vm.importAnimal = {};
                    vm.farmName = vm.farms[0].name;
                    vm.farmCode = vm.farms[0].code;
                    vm.importAnimal.farm = vm.farms[0];
                }
                //console.log(vm.farms);
            });
        }
        
        function checkAgreeFarm() {
            console.log("checkAgree");
            console.log(vm.selectedFarms);
            if (angular.isUndefined(vm.selectedFarms) || vm.selectedFarms.length == 0) {
                toastr.warning($translate.instant("exportAnimal.notChooseFarm"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.agreeFarm = function () {
            if (checkAgreeFarm()) {
                vm.farmSelected = vm.selectedFarms[0];
                vm.farm = vm.selectedFarms[0];
                vm.farmName = vm.farm.name;
                vm.importAnimalFeed.farm = vm.farm;

                //vm.findBy();

                vm.farmCode = null;
                vm.subModalInstance.close();
            }
        }
		//--------End ----Phân quyền-------------

        /** bussiness */
        vm.bsTableControlFarm = {
                options: {
                    data: vm.farms,
                    idField: 'id',
                    sortable: true,
                    striped: true,
                    maintainSelected: true,
                    clickToSelect: false,
                    showColumns: false,
                    singleSelect: true,
                    showToggle: false,
                    pagination: true,
                    pageSize: vm.pageSizeFarm,
                    pageList: [5, 10, 25, 50, 100],
                    locale: settings.locale,
                    sidePagination: 'server',
                    columns: service.getTableDefinitionFarm(),
                    onCheck: function (row, $element) {
                        if (vm.selectedFarms != null) {
                            vm.selectedFarms = [];
                        }
                        $scope.$apply(function () {
                            vm.selectedFarms.push(row);
                        });
                    },

                    onUncheck: function (row, $element) {

                        if (vm.selectedFarms != null) {
                            vm.selectedFarms = [];
                        }
                    },

                    onPageChange: function (index, pageSize) {
                        vm.pageSizeFarm = pageSize;
                        vm.pageIndexFarm = index;
                        //vm.searchTeachers();
                        vm.findByFarm();
                    }
                }
            };
        vm.bsTableControl = {
            options: {
                data: vm.importAnimalFeeds,
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
                        vm.selectedImportAnimalFeeds.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedImportAnimalFeeds = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedImportAnimalFeeds);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedImportAnimalFeeds.splice(index, 1);
                        });
                        
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedImportAnimalFeeds = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;

                    //vm.getPageImportAnimal();
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
           if(vm.isFamer==true){
					vm.pageIndex = 1;
					vm.bsTableControl.state.pageNumber = 1;
					vm.findBy();
			 }else{
					vm.pageIndexNot = 1;
					vm.bsTableControlAdmin.state.pageNumber = 1;
					vm.findByAdmin();
			 }
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
            
			if(vm.fromDate!=null){
				vm.searchDto.fromDate=vm.fromDate;
			}else{
				vm.searchDto.fromDate=null;
			}
			if(vm.toDate!=null){
				vm.searchDto.toDate=vm.toDate;
			}else{
				vm.searchDto.toDate=null;
			}
          
			vm.searchDto.type=1;//nhập
            
            console.log(vm.searchDto);
           if(changeDate()){
			   service.getPageSearchImportAnimalFeed(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
					vm.importAnimalFeeds = data.content;
					vm.bsTableControl.options.data = vm.importAnimalFeeds;
					vm.bsTableControl.options.totalRows = data.totalElements;
				});
		   }
            
        }
		function changeDate(){
			 if(vm.fromDate!=null && vm.toDate!=null && vm.fromDate>vm.toDate){
				 toastr.warning($translate.instant("importAnimal.toDateBeforeFrom"), $translate.instant("toastr.warning"));
				return false;
			}
			return true;
		}

        $scope.editImportAnimalFeed = function(id){
           if(id){
           	
                service.getImportAnimalFeed(id).then(function(data){
                    vm.importAnimalFeed = data;
                    vm.importAnimalFeed.isNew =false;
					
                    console.log(vm.importAnimalFeed);
                });
                vm.modalAnimal = modal.open({
                    animation: true,
                    templateUrl: 'detail.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
            }
        }

        vm.createImportAnimalFeed = function (){
        	//trường hợp là nông dân thì lấy các trang cơ sở theo user
			getFarmByUserLogin();
			//let date = new Date();
			//vm.importAnimalFeed.dateIssue = {year: date.getFullYear(), month: date.getMonth(), day: date.getDate()};
			//vm.importAnimalFeed.dateIssue =new Date();
            vm.importAnimalFeed = {isNew : true, dateIssue: new Date()};
          
            vm.modalAnimal = modal.open({
                animation: true,
                templateUrl: 'detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        };
		
		vm.save  =function (){
			if(vm.importAnimalFeed==null){
				 toastr.warning($translate.instant("importAnimalFeed.emptyExport"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.importAnimalFeed.farm==null){
				 toastr.warning($translate.instant("importAnimalFeed.emptyFarm"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.importAnimalFeed.dateIssue==null){
				 toastr.warning($translate.instant("importAnimalFeed.emptyDate"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.importAnimalFeed.dateIssue!=null && vm.importAnimalFeed.dateIssue>new Date()){
                toastr.warning($translate.instant("importAnimal.warningDate"), $translate.instant("toastr.warning"));
                return ;
            }
			if(vm.importAnimalFeed.bran==null){
				 toastr.warning($translate.instant("importAnimalFeed.emptyBran"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.importAnimalFeed.unit==null){
				 toastr.warning($translate.instant("importAnimalFeed.emptyUnit"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.importAnimalFeed.quantity==null){
				 toastr.warning($translate.instant("importAnimalFeed.emptyQuantity"), $translate.instant("toastr.warning"));
				return;
			}
			
            if(vm.importAnimalFeed){
                
                if(vm.importAnimalFeed.id){
					
                    service.updateImportAnimalFeed(vm.importAnimalFeed.id,vm.importAnimalFeed,function success(){
                    },function failure(error){
						 toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    }).then(function(data){
                        if(data.code == "-5"){
                            toastr.warning($translate.instant("importAnimalFeed.validImportQuantity"), $translate.instant("toastr.warning"));
                        }else if(data.code == "-4"){
                            toastr.warning($translate.instant("importAnimalFeed.validImportDate"), $translate.instant("toastr.warning"));
                        }else{
                            toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                           
                            if(vm.isFamer==true){
                                   vm.pageIndex = 1;
                                   vm.bsTableControl.state.pageNumber = 1;
                                   vm.findBy();
                            }else{
                                   vm.pageIndexNot = 1;
                                   vm.bsTableControlAdmin.state.pageNumber = 1;
                                   vm.findByAdmin();
                            }
                           vm.modalAnimal.close();
                        }
                    });
                }else {
                    service.createImportAnimalFeed(vm.importAnimalFeed,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                       
                        if (vm.isFamer == true) {
                            vm.pageIndex = 1;
                            if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                                vm.bsTableControl.state.pageNumber = 1;
                            }
                            vm.findBy();
                        } else {
                            vm.pageIndexNot = 1;
                            if (vm.bsTableControlAdmin != null && vm.bsTableControlAdmin.state != null) {
                                vm.bsTableControlAdmin.state.pageNumber = 1;
                            }
                            vm.findByAdmin();
                        }
                        vm.modalAnimal.close();
                    },function failure(error){
						 toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        $scope.deleteOriginal =function deleteOriginal(id){
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.deletefarms =function (){
            if(vm.selectedFarms.length == 0){
                toastr.warning("Bạn chưa chọn!")
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

        vm.confirmDelete =function (){
            vm.pageIndex = 1;
            var ids = vm.selectedFarms.map(function(element){
                return element.id;
            });
            service.deleteServiceByIds(ids,function success(data){
               // vm.getPageImportAnimal();
			   vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedFarms = [];
            },function failure(error){
                console.log(error);
            });
       }
       $scope.deleteImportAnimalFeed = function(id){
        
        if(id){
            vm.recordId = id;
            service.validDateIssueDelete(id).then(function(valid){
                if(valid){
                    vm.modalConfirmDelete = modal.open({
                        animation: true,
                        templateUrl: 'confirm_delete_record.html',
                        scope: $scope,
                        backdrop: 'static',
                        keyboard: false,
                        size: 'md'
                    });
                }else{
                    toastr.warning($translate.instant("importAnimalFeed.cannotDelete"), $translate.instant("toastr.warning"));
                }
            });
        }else {
            vm.recordId = null;
        }
        
       }
       vm.confirmDeleteRecord = function(){
           if(vm.recordId){
            service.deleteImportAnimalFeed(vm.recordId).then(function (data){
                if(data!=null && data.code=="-1"){
                    toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                }else if(data!=null && data.code=="-2"){
                    toastr.warning($translate.instant("importAnimal.deleteImport"), $translate.instant("toastr.warning"));
                }
                else{
                    toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                }
                
                if (vm.isFamer == true) {
                    vm.pageIndex = 1;
                    if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                        vm.bsTableControl.state.pageNumber = 1;
                    }
                    vm.findBy();
                } else {
                    vm.pageIndexNot = 1;
                    if (vm.bsTableControlAdmin != null && vm.bsTableControlAdmin.state != null) {
                        vm.bsTableControlAdmin.state.pageNumber = 1;
                    }
                    vm.findByAdmin();
                }

                vm.modalConfirmDelete.close(); 
                vm.selectedFarms = [];
            });
           }
       }
	   
	   vm.findByAdmin=function () {
            if( vm.searchDto==null){
                vm.searchDto={};
            }
            if(vm.textSearch!=null&& vm.textSearch!=""){
                vm.searchDto.nameOrCode=vm.textSearch;
            }

            else{
                vm.searchDto.nameOrCode=null;
            }          
            
			if(vm.fromDate!=null){
				vm.searchDto.fromDate=vm.fromDate;
			}else{
				vm.searchDto.fromDate=null;
			}
			if(vm.toDate!=null){
				vm.searchDto.toDate=vm.toDate;
			}else{
				vm.searchDto.toDate=null;
			}
          
			vm.searchDto.type=1;//nhập
            
            console.log(vm.searchDto);
			if(changeDate()){
			   service.getPageSearchImportAnimalFeed(vm.searchDto,vm.pageIndexNot,vm.pageSizeNot).then(function(data) {
					vm.importAnimalFeedNots = data.content;
					vm.bsTableControlAdmin.options.data = vm.importAnimalFeedNots;
					vm.bsTableControlAdmin.options.totalRows = data.totalElements;
				});
			}
            
        }
		vm.bsTableControlAdmin = {
            options: {
                data: vm.importAnimalFeedNots,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeNot,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionAdmin(),
                
                onPageChange: function (index, pageSize) {
                    vm.pageSizeNot = pageSize;
                    vm.pageIndexNot = index;

                    //vm.getPageImportAnimal();
					vm.findByAdmin();
                }
            }
        };
		//thêm cám 
		vm.createBran=function(){
			 vm.bran = {isNew : true};
          
            vm.modalBran= modal.open({
                animation: true,
                templateUrl: 'bran_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
		}
		
        vm.saveBran = function () {
            if (vm.bran) {

                /*if(!vm.bran.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/
                console.log(1);
                if (vm.bran.isNew) {
                    console.log(2);
                    branService.createBran(vm.bran, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        
                        vm.modalBran.close();
                        vm.getCombo();
                        vm.getUnit();
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                } else {
                    vm.bran.code = vm.tempCode;
                    branService.updateBran(vm.bran.id, vm.bran, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        
                        vm.modalBran.close();
                        vm.getCombo();
                        vm.getUnit();
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }
		
		function validate() {
            console.log(vm.bran);
            if (!vm.bran.code) {
                toastr.warning($translate.instant("bran.emptyCode"), $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.bran.name) {
                toastr.warning($translate.instant("bran.emptyName"), $translate.instant("toastr.warning"));
                return false;
            }
            
			if(vm.bran.isNew==false && (vm.tempCode==null || vm.tempCode=="")){
				toastr.warning($translate.instant("bran.emptyCode"), $translate.instant("toastr.warning"));
				return false;
			}
            return true;
        }

        function checkDuplicateCode(code, type, action) { //type: 1 -> save; 2 -> edit;   action: 1 -> just check code; 2 -> save or edit
            branService.checkDuplicateCode(code).then(function (data) {
                vm.viewCheckDuplicateCode = data;
                console.log(action);
                console.log(type);
                if (action == 1) {
                    if (type == 1) {
                        console.log(11);
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                            // toastr.warning("Mã bị trùng");
                            toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                        }
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {
                            //toastr.success("Mã không bị trùng");
                            toastr.success($translate.instant("checkCode.codeDuplicateNot"), $translate.instant("toastr.info"));
                        }
                    }
                    if (type == 2) {
                        console.log(12);
                        if (vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()) {
                            checkDuplicateCode(vm.tempCode, 1, 1);
                        } else {
                            // toastr.info("Mã chưa thay đổi");
                            toastr.info($translate.instant("checkCode.codeNotChange"), $translate.instant("toastr.info"));
                        }
                    }
                }
                if (action == 2) {
                    if (type == 1) {
                        console.log(21);
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                            //toastr.warning("Mã bị trùng");
                            toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                        }
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {

                            vm.saveBran();
                            console.log(21);
                        }
                    }
                    if (type == 2) {
                        if (vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()) {
                            branService.checkDuplicateCode(vm.tempCode).then(function (data) {
                                vm.viewCheckDuplicateCode = data;
                                if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                                    //toastr.warning("Mã bị trùng");
                                    toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                                }
                                if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {
                                    vm.bran.code = vm.tempCode.trim();

                                    vm.saveBran();
                                    console.log(22);
                                }
                            });
                        } else {
                            vm.bran.code = vm.tempCode.trim();

                            vm.saveBran();
                            console.log(3);
                        }
                    }
                }
                console.log(data);

            });
        }
        vm.checkDuplicateCode = function (type, action) {
            if (validate()) {
                console.log('123');
                checkDuplicateCode(vm.bran.code, type, action);

            }
        }
	   
    }

})();
