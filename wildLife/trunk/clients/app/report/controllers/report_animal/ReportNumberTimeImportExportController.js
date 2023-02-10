/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('ReportNumberTimeImportExportController', ReportNumberTimeImportExportController);

    ReportNumberTimeImportExportController.$inject = [
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
    
    function ReportNumberTimeImportExportController ($rootScope, $scope, $http, $timeout,settings,service,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService,animalService) {
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
        vm.fromYear = vm.currentYear;
        vm.fromMonth = new Date().getMonth()+1;
        vm.toYear = vm.currentYear;
        vm.toMonth = new Date().getMonth()+1;
        $scope.years = [];
        $scope.months = [];
        vm.farms = [];
		for(var i=2000; i <= vm.currentYear; i++) {
			$scope.years.push({value: i, name: i+""});
        }
        for(var month = 1; month <= 12; month++){
            $scope.months.push({value: month, name: 'Tháng ' + month});
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
        vm.pageIndexFarm = 1;
        vm.pageSizeFarm = 10;

		animalService.getAll().then(function (data) {
			vm.animals = data;
		}).catch(function (error) {
			vm.animals = [];
		});

		vm.getReportNumberTimeImportExport = function(){
            
            const fromDate = moment(new Date(vm.fromYear, vm.fromMonth-1, 1)).toDate();
            const toDate = moment(new Date(vm.toYear, vm.toMonth-1, 1)).endOf('month').toDate();
            if(fromDate.valueOf(fromDate) < toDate.valueOf(toDate)){
                vm.titleHeader = "<h4 class='text-center'>Báo cáo nhập xuất động vật hoang dã ("+ ((vm.fromMonth+"").padStart(2,'0')) +"/"+vm.fromYear+"-"+((vm.toMonth+"").padStart(2,"0"))+"/"+vm.toYear+") "+( (vm.farmSelected && vm.farmSelected.name)? ("Cơ sở "+vm.farmSelected.name): "")+"</h4>";
                
            }else{
                toastr.warning('Tháng năm chọn không hợp lệ')
            }
            const searchDto = {
                animalId: vm.animal? vm.animal.id: null
                , farmId: vm.farmSelected? vm.farmSelected.id: null
                , year: vm.selectYear
                , province: vm.province ?vm.province.id: null
                , district: vm.district ?vm.district.id: null
                , ward: vm.ward? vm.ward.id: null
                , fromDate: fromDate
                , toDate: toDate
            }

            if(vm.province) searchDto.auId = vm.province.id;
            if(vm.district) searchDto.auId = vm.district.id;
            if(vm.ward) searchDto.auId = vm.ward.id;

            service.getReportNumberTimeImportExport(searchDto).then(function(data){
                vm.results = data;
            });
		}

		vm.selecteAnimal = function(){
		}
        vm.getReport = function(){
        }
        function getAllProvince(){
            service.getAdminstrativeUnitsCity().then(function(data){
                vm.adminstrativeUnits_City = data||[];
            });
        }
        getAllProvince();
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

        vm.selectedFarms = [];
        vm.farmSelected = null;
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
            vm.findByFarm();
            //lấy danh sách cơ sở chăn nuôi phân theo quyền
            // if (vm.isRole) {//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
            //     vm.findByFarm();
            // } else {// trường hợp này thì phân quyền cơ sở chăn nuôi theo user
            //     if (vm.isRole) {
            //         getAdministrativeUnitDtoByLoginUser();

            //     }

            // }


            vm.subModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {

                }
            }, function () {
                vm.farmCode = null;
                console.log("cancel");
            });
        }

        var closeModal = function () {

            vm.subModalInstance.close();

        };
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
                vm.farmName = "";
                if(vm.farm.code){
                    vm.farmName += vm.farm.code+ " - ";
                }
                vm.farmName += vm.farm.name;

                //vm.findBy();

                vm.farmCode = null;
                closeModal();
            }
        }


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

        vm.clearFarmSelected = function(){
            vm.farmSelected = null;
            vm.farmName = null;
        }

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
            const searchDtoFarmTemp2 = angular.copy(vm.searchDtoFarm);
            searchDtoFarmTemp2.showListFarmSelect = true;
            service.getPageSearchFarm(searchDtoFarmTemp2, vm.pageIndexFarm, vm.pageSizeFarm).then(function (data) {
                vm.farms = data.content;
                vm.bsTableControlFarm.options.data = vm.farms;
                vm.bsTableControlFarm.options.totalRows = data.totalElements;
                console.log("vm.farms");
                console.log(vm.farms);
                if (vm.isFamer == true && vm.farms != null && vm.farms.length > 0) {
                    vm.farmName = vm.farms[0].name;
                    vm.farmCode = vm.farms[0].code;
                }
                //console.log(vm.farms);
            });
        }

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
            if (vm.searchDtoFarm == null) {
                vm.searchDtoFarm = {};
            }
            if (administrativeUnit.parentDto == null) {
                vm.searchDtoFarm.province = administrativeUnit.id;

            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                vm.searchDtoFarm.district = administrativeUnit.id;
                vm.searchDtoFarm.province = administrativeUnit.parentDto.id;

            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                vm.searchDtoFarm.ward = administrativeUnit.id;
                vm.searchDtoFarm.district = administrativeUnit.parentDto.id;
                vm.searchDtoFarm.province = administrativeUnit.parentDto.parentDto.id;

            }
            if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') {//trường hợp tài khoản nông dân
                //gán tên cơ sở chăn nuôi
                vm.farmCode = vm.user.username;
                vm.searchDtoFarm.nameOrCode = vm.user.username;
                // console.log("username");
                //console.log(vm.user.username);
            }

            //vm.findBy();
            vm.findByFarm();


        }
		
    }

})();
