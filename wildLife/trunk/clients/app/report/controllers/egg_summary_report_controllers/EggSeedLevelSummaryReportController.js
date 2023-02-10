/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('EggSeedLevelSummaryReportController', EggSeedLevelSummaryReportController);

    EggSeedLevelSummaryReportController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'QuantityReportService',
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
        'MeatProductionReportService',
        'AnimalService'
    ];

    function EggSeedLevelSummaryReportController($rootScope, $scope, $http, $timeout, settings, service, regionService, modal, toastr, $state, blockUI, $stateParams, utils, $translate, farmService, auService, meatService, animalService) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
        var today = new Date();
        vm.currentYear = today.getFullYear();

        // blockUI.stop();
        // blockUI.start();
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.farmSizes = []
        vm.modalFarmSize = {};
        vm.farmSize = { isNew: false };
        vm.modalConfirmDelete = {};
        vm.selectedFarmSize = [];
        vm.count = 0;
        vm.searchDto = {};
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.results = [];
        vm.viewType = "chart";
        vm.title = null;
        vm.regionName = null;
        vm.animalName = null;
        var isDup = false;
        vm.datas = [];
        vm.dataDetail = [];
        vm.paramDtoDetail = {};
        vm.paramDtoPreClick = {};
        var isDupDetail = false;
        vm.isShow = true;
        $scope.years = [];
        for (var i = 2000; i <= vm.currentYear; i++) {
            $scope.years.push(i);
        }
        vm.months = [
            {
                name: "Tháng 1",
                value: 1
            },
            {
                name: "Tháng 2",
                value: 2
            },
            {
                name: "Tháng 3",
                value: 3
            },
            {
                name: "Tháng 4",
                value: 4
            },
            {
                name: "Tháng 5",
                value: 5
            },
            {
                name: "Tháng 6",
                value: 6
            },
            {
                name: "Tháng 7",
                value: 7
            },
            {
                name: "Tháng 8",
                value: 8
            },
            {
                name: "Tháng 9",
                value: 9
            },
            {
                name: "Tháng 10",
                value: 10
            },
            {
                name: "Tháng 11",
                value: 11
            },
            {
                name: "Tháng 12",
                value: 12
            }
        ];

        /** generate */


        vm.paramDto = {};
        vm.paramDto.periodType = "1";
        vm.paramDto.currentYear = today.getFullYear().toString();
        vm.paramDto.fromMonth = today.getMonth();
        vm.paramDto.toMonth = today.getMonth();
        //vm.paramDto.groupByItems = ["region","animalParent","province","productTarget"];
        //vm.paramDto.productTargetId = 1;    //loại thịt
        vm.paramDto.fromMonth = 1;
        vm.paramDto.toMonth = 12;
        vm.paramDto.fromYear = vm.currentYear;
        vm.paramDto.toYear = vm.currentYear;
		vm.paramDto.levels=[];
		vm.paramDto.levels.push(1);//giống bố mẹ
		vm.paramDto.levels.push(2);//giống ông bà
        vm.groupByItems = [
            {
                name: "Động vật",
                value: "animalId"
            },
            {
                name: "Loại động vật",
                value: "animalParent"
            },
            {
                name: "Loại động vật",
                value: "animalType"
            },
            {
                name: "Cơ sở",
                value: "farm"
            },
            {
                name: "Xã",
                value: "ward"
            },
            {
                name: "Huyện",
                value: "district"
            },
            {
                name: "Tỉnh",
                value: "province"
            },
            {
                name: "Vùng sinh thái",
                value: "region"
            }
        ];
        vm.isRole = false;
		vm.listEgg=[];
		vm.listEggType=[];
		vm.index=0;
		vm.indexEgg=0;
        vm.getCombo = function () {
            regionService.getAll().then(function (data) {
                console.log()
                if (data && data.length > 0) {
                    vm.regions = data;
                } else {
                    vm.regions = [];
                }
            });

        };
        vm.getCombo();
        function validate() {
            return true;
        };       

        vm.getQuantityReport = function () {         
            vm.paramDto.type=1;
            
			vm.paramDto.groupByItems=[];
			vm.paramDto.fromMonth = vm.paramDto.toMonth;
            vm.paramDto.fromYear = vm.paramDto.toYear;
            vm.paramDto.startTimeType = 1;
					
			vm.paramDto.groupByItems.push('region');	
			vm.paramDto.groupByItems.push('province');
			vm.paramDto.groupByItems.push('report');
			vm.paramDto.groupByItems.push('eggType');	
			
            meatService.getEggSummaryReport(vm.paramDto).then(function (data) {
                vm.results = data;
				console.log(data);
				if(vm.results!=null && vm.results.length>0){
					vm.listEgg=vm.results [0].reportCoes;
					vm.indexEgg=vm.listEgg.length;
					vm.listEggType=vm.results [0].eggTypes;
					vm.index=vm.listEggType.length;
				}else{
					vm.results =[];
					vm.listEgg=[];
					vm.indexEgg=0;
					vm.listEggType=[];
					vm.index=0;
				}
				console.log(vm.index);
				console.log(vm.listEgg);
				console.log(vm.listEggType);
            });
        }

        vm.showCalcaulation = function () {

            vm.modal = modal.open({
                animation: true,
                templateUrl: 'calculation.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });

        };
        vm.onFmsAnimalSelected = function (item) {
            if (item != null && item.id != null) {
                vm.animalName = item.name;
            } else {
                vm.animalName = null;
            }
        }

        vm.onFmsRegionSelected = function (region) {

            if (region != null && region.id != null) {
                vm.regionName = region.name;
                auService.getAllCityByRegion(region.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_City = data;
                        vm.selectedCity = null;
                        vm.selectedDist = null;
                        vm.selectWards = null;
                        vm.paramDto.provinceId = null;
                        vm.paramDto.districtId = null;
                        vm.paramDto.wardId = null;
                        vm.provinceName = null;
                        vm.districtName = null;
                        vm.wardsName = null;
                    }
                    else {
                        vm.paramDto.provinceId = null;
                        vm.paramDto.districtId = null;
                        vm.paramDto.wardId = null;
                        vm.selectedCity = null;
                        vm.selectedDist = null;
                        vm.selectWards = null;
                        vm.adminstrativeUnits_City = [];
                        vm.adminstrativeUnits_Dist = [];
                        vm.adminstrativeUnits_Wards = [];
                        vm.provinceName = null;
                        vm.districtName = null;
                        vm.wardsName = null;
                    }

                });
            } else {
                vm.paramDto.provinceId = null;
                vm.paramDto.districtId = null;
                vm.paramDto.wardId = null;
                vm.selectedCity = null;
                vm.selectedDist = null;
                vm.selectWards = null;
                vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Wards = [];
                vm.regionName = null;
                vm.provinceName = null;
                vm.districtName = null;
                vm.wardsName = null;
                vm.ward = null;
                vm.district = null;
                vm.city = null;
                vm.regionName = null;
            }
        }

        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            if (city != null && city.id != null) {
                farmService.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                        vm.selectedDist = null;
                        vm.selectWards = null;
                    }
                    else {
                        vm.selectedDist = null;
                        vm.selectWards = null;
                        vm.adminstrativeUnits_Dist = [];
                        vm.adminstrativeUnits_Wards = [];
                    }
                });
            }
            else {

                vm.paramDto.districtId = null;
                vm.paramDto.wardId = null;
                vm.selectedDist = null;
                vm.selectWards = null;
                vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Wards = [];
                vm.districtName = null;
                vm.wardsName = null;
                vm.provinceName = null;
                vm.ward = null;
                vm.district = null;
                vm.city = null;
            }
            vm.city = city;
            console.log(vm.city);
        }

        vm.onFmsadminstrativeUnitDistSelected = function (dist) {
            if (dist != null && dist.id != null) {
                farmService.getAllByParentId(dist.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                    else {
                        vm.selectWards = null;
                        vm.adminstrativeUnits_Wards = [];
                    }
                    vm.selectWards = null;
                });
            }
            else {
                vm.paramDto.wardId = null;
                vm.selectWards = null;
                vm.adminstrativeUnits_Wards = [];
                vm.wardsName = null;
                vm.districtName = null;
                vm.ward = null;
                vm.district = null;
            }
            vm.district = dist;
            console.log(vm.district);
        }
        vm.onFmsadminstrativeUnitWardsSelected = function (item) {
            vm.ward = item;
            console.log(vm.ward);
        }
    }

})();
