/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('ReportCurrentStatusByUnitController', ReportCurrentStatusByUnitController);

    ReportCurrentStatusByUnitController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ReportCurrentStatusByAnimalService',
		'FmsRegionService',		
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate',
		'FarmService',
		'FmsAdministrativeService',
		'AnimalService'
    ];
    
    function ReportCurrentStatusByUnitController ($rootScope, $scope, $http, $timeout,settings,service,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService,animalService) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
        var today = new Date();
        vm.currentYear=today.getFullYear();
        vm.isCallReport = false;
		// $rootScope.$on('$translateChangeSuccess', function(event, current, previous) {
		// 	if(vm.isCallReport){
		// 		vm.getQuantityReport();
		// 	}
		// });
        // blockUI.stop();
        // blockUI.start();
        vm.recordId = null;
        vm.animal = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.farmSizes = []
        vm.selectYear = vm.currentYear;
		$scope.years = [];
		for(var i=vm.currentYear; i >= 2000; i--) {
			$scope.years.push({value: i, name: i+""});
        }
        
        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
        vm.isSearchExtend = false;
        vm.isSdahView = false;//cấp tỉnh nhưng chỉ được xem

        vm.isSelectProvince = false;
        vm.isSelectDistrict = false;
        vm.isSelectWard = false;
        vm.province = null;
        vm.district = null;
        vm.ward = null;
        //------Start--Phân quyền theo user đăng nhập-----------
        blockUI.start();
        service.getCurrentUser().then(function (data) {
            console.log(data);
            vm.user = data;
            var roles = data.roles;
            if (roles != null && roles.length > 0) {
                for (var i = 0; i < roles.length; i++) {
                    if (roles[i] != null && roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' || roles[i].authority.toLowerCase() == 'role_dlp')) {
                        vm.isRole = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah') {
                        vm.isSdah = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_district') {
                        vm.isDistrict = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_ward') {
                        vm.isWard = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_famer') {
                        vm.isFamer = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah_view') {
                        vm.isSdahView = true;
                        vm.isSdah = true;
                    }
                }
            } else {
                vm.isRole = false;
                vm.isFamer = false;
                vm.isSdah = false;
                vm.isDistrict = false;
                vm.isWard = false;
                vm.isSdahView = false;
            }
            if (vm.isRole) {//trường hợp admin và dlp thì được xem tất cả các cơ sở
                getAllProvince();
                vm.reportCurrentStatusByAnimalAdministrative();
            } else {// trường hợp này thì phân quyền theo user
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUser();
                }
            }
            blockUI.stop();
        });

        //load user administrativeunit
        function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function (data) {
                vm.adminstrativeUnits = data;
                if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
                    getDataByCombobox(vm.adminstrativeUnits[0]);
                }
            });
        }
        function getDataByCombobox(administrativeUnit) {
            if (administrativeUnit.parentDto == null) {
                vm.province = administrativeUnit.id;
                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit);
                service.getAllByParentId(vm.province).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }

                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                vm.district = administrativeUnit.id;
                vm.province = administrativeUnit.parentDto.id;
                vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
                vm.adminstrativeUnits_Dist = [];
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit);
                service.getAllByParentId(vm.province).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }

                });
                service.getAllByParentId(vm.district).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }

                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                vm.ward = administrativeUnit.id;
                vm.district = administrativeUnit.parentDto.id;
                vm.province = administrativeUnit.parentDto.parentDto.id;

                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
                if (vm.adminstrativeUnits_Wards == null) vm.adminstrativeUnits_Wards = [];
                vm.adminstrativeUnits_Wards.push(administrativeUnit);
            }
            if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') {//trường hợp tài khoản nông dân
                //gán tên cơ sở chăn nuôi
                vm.textSearch = vm.user.username;
                // console.log("username");
                //console.log(vm.user.username);
            }
            vm.reportCurrentStatusByAnimalAdministrative();
        }

        /** generate */
        vm.paramDto={};
        vm.paramDto.periodType="1";
        vm.paramDto.currentYear=today.getFullYear().toString();
        vm.paramDto.fromMonth = today.getMonth();
        vm.paramDto.toMonth = today.getMonth();
        
        vm.paramDto.fromMonth=1;
        vm.paramDto.toMonth=12;
        vm.paramDto.fromYear=vm.currentYear;
		vm.paramDto.toYear=vm.currentYear;
		vm.animalId = null;
		vm.results = [];
        vm.titleHeader = "";
		vm.isRole=false;

        /** get data */
        vm.searchDto = {};
        vm.listAnimalClass = [];
        vm.listAnimalFamily = [];
        vm.listAnimalOrdo = [];
        vm.getListAnimalClass = function () {
            animalService.getListAnimalClass().then(function (data) {
                vm.listAnimalClass = [];
                vm.animalClass = null;
                vm.ordo = null;
                vm.family = null;
                data.forEach(item => {
                    var title = '-';
                    if (item && item.trim().length > 0) {
                        title = item;
                    }
                    vm.listAnimalClass.push({ name: title });
                });
            });

            vm.getListAnimalOrdo();
        }

        vm.getListAnimalOrdo = function (callSearchFunction) {
            animalService.getListAnimalOrdo(vm.searchDto.animalClass).then(function (data) {
                vm.listAnimalOrdo = [];
                vm.ordo = null;
                vm.family = null;
                data.forEach(item => {
                    var title = '-';
                    if (item && item.trim().length > 0) {
                        title = item;
                    }
                    vm.listAnimalOrdo.push({ name: title });
                });
                vm.getListAnimalFamily(true);
            });
        }

        vm.getListAnimalFamily = function (callSearchFunction) {
            animalService.getListAnimalFamily(vm.searchDto.animalClass, vm.searchDto.ordo).then(function (data) {
                vm.listAnimalFamily = [];
                vm.family = null;
                data.forEach(item => {
                    var title = '-';
                    if (item && item.trim().length > 0) {
                        title = item;
                    }
                    vm.listAnimalFamily.push({ name: title });
                });
                if (callSearchFunction) {
                    vm.searchByCode();
                }
            });
        }
        vm.getListAnimalClass();
        vm.getListAnimalOrdo();
        vm.getListAnimalFamily();

        vm.animalClassSelected = function () {
            if (vm.animalClass && vm.animalClass == '-') {
                vm.searchDto.animalClass = null;
                vm.searchDto.ordo = null;
                vm.searchDto.family = null;
            }
            else{
                vm.searchDto.animalClass = vm.animalClass;
                vm.searchDto.ordo = null;
                vm.searchDto.family = null;
            }
            vm.getListAnimalOrdo(true);
        }

        vm.animalOrdoSelected = function () {
            if (vm.ordo && vm.ordo == '-') {
                vm.searchDto.ordo = null;
                vm.searchDto.family = null;
            }
            else{
                vm.searchDto.ordo = vm.ordo;
                vm.searchDto.family = null;
            }
            vm.getListAnimalFamily(true);
        }

        vm.familySelected = function () {
            if (vm.family && vm.family == '-') {
                vm.searchDto.family = null;
            }
            else{
                vm.searchDto.family = vm.family;
            }
            vm.searchByCode();
        }

        vm.searchByCode = function(){
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            animalService.getPageSearchAnimal(vm.searchDto, 1, 1000000).then(function (data) {
                vm.animals = data.content;
            });
        }

		animalService.getAll().then(function (data) {
			vm.animals = data;
		}).catch(function (error) {
			vm.animals = [];
		});

		vm.reportCurrentStatusByAnimalAdministrative = function(){
			// if(vm.animal == null){
			// 	toastr.warning("Bạn chưa chọn loài nuôi", "Thông báo");
			// 	return;
            // }
            if(vm.selectYear == null){
				toastr.warning("Bạn chưa chọn năm", "Thông báo");
				return;
            }
            
            if(vm.animal){
                vm.titleHeader = "<h4 class='text-center' ng-bind-html='vm.titleHeader'>Báo cáo hiện trạng nuôi loài <strong>"+vm.animal.name+"</strong> tại <strong>"+(vm.ward? vm.ward.name : (vm.district? vm.district.name: (vm.province ? vm.province.name: 'Việt Nam')))+"</strong> </h4>";
            }else{
            	vm.titleHeader = "";
            }
            
			if(vm.selectYear){
                const searchDto = {
                    animalId: vm.animalId? vm.animalId: null
                  , year: vm.selectYear
                  , province: vm.province ?vm.province: null
                  , district: vm.district ?vm.district: null
                  , ward: vm.ward? vm.ward: null
        		  , animalClass:vm.animalClass? vm.animalClass:null
				  , ordo:vm.ordo? vm.ordo:null
				  , family:vm.family? vm.family:null
                }
				service.reportCurrentStatusByAnimalAdministrative(searchDto).then(function(data){
					vm.results = data;
				});
			}
		}

		vm.selecteAnimal = function(){
			if(vm.animalId && vm.selectYear){
				vm.reportCurrentStatusByAnimalAdministrative();
            }
        }

        vm.reportCurrentStatusByAnimalAdministrative();

        vm.getReport = function(){
            // if(vm.animalId && vm.selectYear){
            //     const searchDto = {
            //           animalId: vm.animal?vm.animal.id: null
            //         , year: vm.selectYear
            //         , province: vm.province ?vm.province.id: null
            //         , district: vm.district ?vm.district.id: null
            //         , ward: vm.ward? vm.ward.id: null
            //     }
			// 	service.reportCurrentStatusByAnimalAdministrative(searchDto).then(function(data){
			// 		vm.results = data;
			// 	});
			// }
        }
        function getAllProvince(){
            service.getAdminstrativeUnitsCity().then(function(data){
                vm.adminstrativeUnits_City = data||[];
            });
        }
        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                        vm.selectedDist = null;
                        vm.selectedWard = null;
                        vm.district=null;
						vm.ward=null;
						vm.district=null;
                    } else {
                        vm.district=null;
						vm.ward=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
						vm.district=null;
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards=[];
                    }
                });
            } else {
                vm.district=null;
				vm.ward=null;
	        	vm.selectedDist = null;
	        	vm.selectWards = null;
				vm.district=null;
                vm.adminstrativeUnits_Dist = [];	
				vm.adminstrativeUnits_Wards=[];
            }

            vm.pageIndex = 0;
            vm.getReport();
        }

        vm.onFmsadminstrativeUnitDistSelected = function (dist) {
            if (dist != null && dist.id != null) {
				service.getAllByParentId(dist.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Wards = data;
						vm.ward=null;						
						vm.selectWards=null;
						vm.ward=null;
						
					}
					else {
			        	vm.selectWards = null;
		                vm.ward=null;
						vm.adminstrativeUnits_Wards=[];
					}
					
	            });
			}
			else {
	        	vm.selectWards = null;
				vm.ward=null;
				vm.adminstrativeUnits_Wards=[];
			}

            vm.pageIndex = 0;
            vm.getReport();
        }
        vm.onFmsadminstrativeUnitWardsSelected=function(item){
            vm.pageIndex = 0;
            vm.getReport();
        }
		
    }

})();
