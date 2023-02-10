/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('ReportAnimalCurrentByFamilyController', ReportAnimalCurrentByFamilyController);

    ReportAnimalCurrentByFamilyController.$inject = [
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
        'AnimalService',
        'FileSaver'
    ];
    
    function ReportAnimalCurrentByFamilyController ($rootScope, $scope, $http, $timeout,settings,service,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService,animalService,FileSaver) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;
        vm.paramDto={};
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
                vm.paramDto.provinceId = administrativeUnit.id;
                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit);
                service.getAllByParentId(vm.paramDto.provinceId).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }

                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                vm.paramDto.districtId = administrativeUnit.id;
                vm.paramDto.provinceId = administrativeUnit.parentDto.id;
                vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
                vm.adminstrativeUnits_Dist = [];
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit);
                service.getAllByParentId(vm.paramDto.provinceId).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }

                });
                service.getAllByParentId(vm.paramDto.districtId).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }

                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                vm.paramDto.wardId = administrativeUnit.id;
                vm.paramDto.districtId = administrativeUnit.parentDto.id;
                vm.paramDto.provinceId = administrativeUnit.parentDto.parentDto.id;

                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
                if (vm.adminstrativeUnits_Wards == null) vm.adminstrativeUnits_Wards = [];
                vm.adminstrativeUnits_Wards.push(administrativeUnit);
            }
        }
		
		
		//--------End ----Phân quyền-------------

        /** declare */
        animalService.getAll().then(function (data) {
            vm.animals = data;
        }).catch(function (error) {
            vm.animals = [];
        });
        
        var today = new Date();
        vm.currentYear=today.getFullYear();
        vm.isCallReport = false;
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
        vm.searchDto = {};
		for(var i=2000; i <= vm.currentYear; i++) {
			$scope.years.push({value: i, name: i+""});
        }
        for(var month = 1; month <= 12; month++){
            $scope.months.push({value: month, name: 'Tháng ' + month});
        }
        /** generate */
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
        vm.listAnimalFamily = [];
        vm.family = null;

        animalService.getListAnimalFamily().then(function(data){
            if(data){
                vm.listAnimalFamily = data.filter(function(item){
                    return item;
                });
            }else{
                vm.listAnimalFamily = data;
            }
        });
        vm.familySelected  = function(){
            vm.reportCurrentStatusByFamily();
        }
        vm.onSelectYear = function(){
            vm.reportCurrentStatusByFamily();
        }

		vm.reportCurrentStatusByFamily = function(){
            // if(!vm.family){
            //     toastr.warning("Chưa chọn họ động vật", "Cảnh bảo");
            //     return;
            // }
            if(vm.family){
                vm.titleHeader = "Báo cáo hiện trạng nuôi ĐVHD "+vm.family+" năm "+vm.selectYear;
            }else{
                vm.titleHeader = "";
            }
            const searchDto = {
                listAnimalFamily: vm.listFamilyClassParam ? vm.listFamilyClassParam: null,
                listAnimalOrdo: vm.listOrdoClassParam ? vm.listOrdoClassParam: null,
                listAnimalClass: vm.listAnimalClassParam? vm.listAnimalClassParam: null,
                provinceId: vm.paramDto.provinceId ?vm.paramDto.provinceId: null,
                districtId: vm.paramDto.districtId ?vm.paramDto.districtId: null,
                communeId: vm.paramDto.wardId? vm.paramDto.wardId: null,
                year: vm.selectYear,
                dateReport: vm.dateReport
                , listAnimalCites : vm.listCites ? vm.listCites :null
                , listAnimalVnlist: vm.vnlist ? vm.vnlist: null
                , listAnimalVnlist06: vm.vnlist06 ? vm.vnlist06: null
                ,animalIds: vm.listAnimal ? vm.listAnimal : null,
                listAnimalGroup: vm.animalGroup?vm.animalGroup: null
            }
            service.reportCurrentStatusByFamily(searchDto).then(function(data){
                vm.results = data;
            });
		}

		
        function getAllProvince(){
            service.getAdminstrativeUnitsCity().then(function(data){
                vm.adminstrativeUnits_City = data;
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
                vm.paramDto.districtId=null;
                vm.paramDto.wardId=null;
            }

            vm.pageIndex = 0;
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
		                vm.paramDto.wardId=null;
						vm.adminstrativeUnits_Wards=[];
					}
	            });
			}
			else {
                vm.paramDto.wardId=null;
				vm.adminstrativeUnits_Wards=[];
			}
            vm.pageIndex = 0;
        }
        vm.onFmsadminstrativeUnitWardsSelected=function(item){
            vm.pageIndex = 0;
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

        vm.getListAnimalOrdo = function (callSearchFunction) {
            animalService.getListAnimalOrdo(vm.searchDto.listAnimalClassParam).then(function (data) {
                vm.listAnimalOrdo = [];
                vm.listAnimalFamily = [];
                vm.listOrdoClassParam = null;
                vm.listFamilyClassParam = null;
                data.forEach(item => {
                    var title = '-';
                    if (item && item.trim().length > 0) {
                        title = item;
                    }
                    vm.listAnimalOrdo.push({ name: title });
                });

                vm.getListAnimalFamilyParam(true);
            });
        }
        vm.animalClassSelected = function () {
            if (vm.animalClass && vm.animalClass == '-') {
                vm.searchDto.animalClass = null;
                vm.searchDto.listAnimalClassParam = null;
                vm.searchDto.listOrdoClassParam = null;
                vm.searchDto.listFamilyClassParam = null;
            }
            else{
                vm.searchDto.listAnimalClass = vm.listAnimalClassParam;
                vm.searchDto.listOrdoClassParam = null;
                vm.searchDto.listFamilyClassParam = null;
            }
            vm.getListAnimalOrdoParam(true);
        }

        vm.animalOrdoSelected = function () {
            if (vm.ordo && vm.ordo == '-') {
                //vm.searchDto.ordo = null;
                vm.searchDto.listOrdoClassParam = null;
                //vm.searchDto.family = null;
            }
            else{
                vm.searchDto.listAnimalOrdo = vm.listOrdoClassParam;
                //vm.searchDto.ordo = vm.ordo;
                //vm.searchDto.family = null;
            }
            vm.getListAnimalFamilyParam(true);
        }

        vm.familySelected = function () {
            if (vm.family && vm.family == '-') {
                //vm.searchDto.family = null;
                vm.searchDto.listAnimalFamilyParam = null;
            }
            else{
                //vm.searchDto.family = vm.family;
                vm.searchDto.listAnimalFamily = vm.listFamilyClassParam;
            }
            vm.findBy();
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
            });

            if (callSearchFunction) {
                vm.findBy();
            }
        }
        vm.listAnimalClass = [];
        vm.listAnimalFamily = [];
        vm.listAnimalOrdo = [];
        vm.getListAnimalClass = function () {
            animalService.getListAnimalClass().then(function (data) {
                vm.listAnimalClass = [];
                vm.listAnimalClassParam = null;
                vm.listOrdoClassParam = null;
                vm.listFamilyClassParam = null;
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
        vm.getListCites = function () {
            animalService.getListCites().then(function (data) {
                vm.listcites = [];
                data.forEach(item => {
                    var cites;
                    if (item && item.trim().length > 0) {
                        cites = item;
                        vm.listcites.push({ name: cites });
                    }
                    
                });
            });
        }
        vm.getVnLists = function () {
            animalService.getVnLists().then(function (data) {
                vm.vnLists = [];
                data.forEach(item => {
                    var vnList = '-';
                    if (item && item.trim().length > 0) {
                        vnList = item;
                        vm.vnLists.push({ name: vnList });
                    }
                    
                });
            });
        }
        vm.getVnList06s = function () {
            animalService.getVnList06s().then(function (data) {
                vm.vnList06s = [];
                data.forEach(item => {
                    var vnList06 ;
                    if (item && item.trim().length > 0) {
                        vnList06 = item;
                        vm.vnList06s.push({ name: vnList06 });
                    }
                    
                });
            });
        }
        vm.listAnimalGroup=[];
        vm.getAnimalGroup = function () {
            animalService.getListAnimalGroup().then(function (data) {
                vm.listAnimalGroup = [];
                data.forEach((item) => {
                    var animalGroup;
                    if (item && item.trim().length > 0) {
                        animalGroup = item;
                        vm.listAnimalGroup.push({ name: animalGroup });
                    }
                });
            });
        };
        vm.getAnimalGroup();
        vm.getVnLists();
        vm.getVnList06s();
        vm.getListCites();
        vm.getListAnimalClass();
        vm.getListAnimalOrdo();
        vm.getListAnimalFamily();

        vm.findBy = function () {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchDto.nameOrCode = vm.textSearch;
            }
            else {
                vm.searchDto.nameOrCode = null;
            }
            animalService.getPageSearchAnimal(vm.searchDto, 1, 99999999).then(function (data) {
                vm.animals = data.content;
            });
        }

		vm.exportDataReportToExcel = function(){
            vm.myBlobObject = undefined;
            const searchDto = {
                // animalFamily: vm.family ? vm.family: null,
                // animalOrdo: vm.ordo ? vm.ordo: null,
                // animalClass: vm.animalClass? vm.animalClass: null,
                // provinceId: vm.paramDto.provinceId ?vm.paramDto.provinceId: null,
                // districtId: vm.paramDto.districtId ?vm.paramDto.districtId: null,
                // communeId: vm.paramDto.wardId? vm.paramDto.wardId: null,
                //  animalCites : vm.cites ? vm.cites :null
                // , animalVnlist: vm.vnlist ? vm.vnlist: null
                // , animalVnlist06: vm.vnlist06 ? vm.vnlist06: null,
                // animalId: vm.animal ? vm.animal : null,
                // animalGroup: vm.animalGroup?vm.animalGroup: null,
                // year: vm.selectYear,
                // dateReport: vm.dateReport
                listAnimalFamily: vm.listFamilyClassParam ? vm.listFamilyClassParam: null,
                listAnimalOrdo: vm.listOrdoClassParam ? vm.listOrdoClassParam: null,
                listAnimalClass: vm.listAnimalClassParam? vm.listAnimalClassParam: null,
                provinceId: vm.paramDto.provinceId ?vm.paramDto.provinceId: null,
                districtId: vm.paramDto.districtId ?vm.paramDto.districtId: null,
                communeId: vm.paramDto.wardId? vm.paramDto.wardId: null,
                year: vm.selectYear,
                dateReport: vm.dateReport
                , listAnimalCites : vm.listCites ? vm.listCites :null
                , listAnimalVnlist: vm.vnlist ? vm.vnlist: null
                , listAnimalVnlist06: vm.vnlist06 ? vm.vnlist06: null
                ,animalIds: vm.listAnimal ? vm.listAnimal : null,
                listAnimalGroup: vm.animalGroup?vm.animalGroup: null
            }
            service.getAnimalCurrentByFamilyDataToExcelFile(searchDto).then(function (data) {//is important that the data was returned as Aray Buffer
                var file = new Blob([data], {
                    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                });
                FileSaver.saveAs(file, "DuLieuBaoCaoHoatDongNuoiDVHDTheoHo.xls");
            }, function (fail) {
                console.log('Download Error, stop animation and show error message');
                vm.myBlobObject = [];
            });
        }
        //List 

        vm.getListAnimalClass = function () {
            animalService.getListAnimalClass().then(function (data) {
              vm.listAnimalClass = [];
              vm.listAnimalClassParam = null;
              vm.listOrdoClassParam = null;
              vm.listFamilyClassParam = null;
              data.forEach((item) => {
                var title = "-";
                if (item && item.trim().length > 0) {
                  title = item;
                }
                vm.listAnimalClass.push({ name: title });
              });
            });
      
            // vm.getListAnimalOrdo();
            vm.getListAnimalOrdoParam();
          };
      
      
      // Search list ordo by class AnimalSearchDto
        vm.getListAnimalOrdoParam = function(callSearchFunction) {
            animalService
              .getListAnimalOrdoParam(vm.searchDto)
              .then(function (data) {
                vm.listAnimalOrdo = [];
                vm.listOrdoClassParam    = null;
                vm.listFamilyClassParam = null;
                data.forEach((item) => {
                  var title = "-";
                  if (item && item.trim().length > 0) {
                    title = item;
                  }
                  vm.listAnimalOrdo.push({ name: title });
                });
                vm.getListAnimalFamilyParam(true);
              });
        };
      
      
      
      //List Animal Family Param
        vm.getListAnimalFamilyParam = function (callSearchFunction) {
            animalService
              .getListAnimalFamilyParam(vm.searchDto)
              .then(function (data) {
                vm.listAnimalFamily = [];
                vm.listFamilyClassParam = null;
                data.forEach((item) => {
                  var title = "-";
                  if (item && item.trim().length > 0) {
                    title = item;
                  }
                  vm.listAnimalFamily.push({ name: title });
                });
                if(callSearchFunction) {
                  vm.findBy();
                }
              })
        }
        
    }

})();
