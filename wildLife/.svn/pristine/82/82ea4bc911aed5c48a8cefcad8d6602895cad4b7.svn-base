/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('ReportAnimalDangerousCitesController', ReportAnimalDangerousCitesController);

    ReportAnimalDangerousCitesController.$inject = [
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

    function ReportAnimalDangerousCitesController($rootScope, $scope, $http, $timeout, settings, service, regionService, modal, toastr, $state, blockUI, $stateParams, utils, $translate, farmService, auService, animalService,FileSaver) {
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
        vm.fromMonth = new Date().getMonth() + 1;
        vm.toYear = vm.currentYear;
        vm.toMonth = new Date().getMonth() + 1;
        $scope.years = [];
        $scope.months = [];
        vm.farms = [];
        vm.isShowProvince = true;
        vm.isShowDistrict = true;
        vm.isShowWard = true;
        vm.searchDto = {};
        for (var i = vm.currentYear; i >= 2000; i--) {
            $scope.years.push({ value: i, name: i + "" });
        }
        for (var month = 1; month <= 12; month++) {
            $scope.months.push({ value: month, name: 'Tháng ' + month });
        }

        /** generate */


        vm.paramDto = {};
        vm.paramDto.periodType = "1";
        vm.paramDto.currentYear = today.getFullYear().toString();
        vm.paramDto.fromMonth = today.getMonth();
        vm.paramDto.toMonth = today.getMonth();

        vm.paramDto.fromMonth = 1;
        vm.paramDto.toMonth = 12;
        vm.paramDto.fromYear = vm.currentYear;
        vm.paramDto.toYear = vm.currentYear;
        vm.animalId = null;
        vm.results = [];
        vm.titleHeader = "";
        vm.isRole = false;
        vm.pageIndexFarm = 1;
        vm.pageSizeFarm = 10;
		vm.pageIndexAnimal = 1;
        vm.pageSizeAnimal = 10;

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
        }
        //--------End ----Phân quyền-------------
        animalService.getAll().then(function (data) {
            vm.animals = data;
        }).catch(function (error) {
            vm.animals = [];
        });

        
        vm.reportAnimalEndangeredPreciousRareNormarlAndCites = function () {
            if(vm.province && vm.province != null){
                vm.isShowProvince = false;
                vm.isShowDistrict = true;
                vm.isShowWard = true;
            }
            if(vm.district && vm.district != null){
                vm.isShowDistrict = false;
                vm.isShowWard = true;
            }
            if(vm.ward && vm.ward != null){
                vm.isShowWard = true;
            }

            if(!vm.selectYear || vm.selectYear == null){
                toastr.warning('Chưa chọn năm', "Cảnh báo");
                return;
            }
            if(!vm.dateReport || vm.dateReport == null){
                toastr.warning('Chưa chọn ngày.', "Cảnh báo");
                return;
            }

            const searchDto = {
                animalId: vm.animal ? vm.animal.id : null
                , animalIds: vm.searchDto.animalIds ? vm.searchDto.animalIds : null
                , farmId: vm.farmSelected ? vm.farmSelected.id : null
                , year: vm.selectYear
                , dateReport: vm.dateReport
                , provinceId: vm.province ? vm.province : null
                , districtId: vm.district ? vm.district : null
                , communeId: vm.ward ? vm.ward : null
                , animalClass : vm.searchDto.animalClass ? vm.searchDto.animalClass:null
                , listAnimalClass: vm.searchDto.listAnimalClass ? vm.searchDto.listAnimalClass : null
                , animalOrdo : vm.searchDto.ordo ? vm.searchDto.ordo:null
                , listAnimalOrdo: vm.searchDto.listAnimalOrdo ? vm.searchDto.listAnimalOrdo : null
                , animalFamily : vm.searchDto.family ? vm.searchDto.family:null
                , listAnimalFamily: vm.searchDto.listAnimalFamily ? vm.searchDto.listAnimalFamily : null
                , animalCites : vm.cites ? vm.cites :null
                , listAnimalCites: vm.listCitesParam ? vm.listCitesParam : null
                , animalVnlist: vm.vnlist ? vm.vnlist: null
                , listAnimalVnlist: vm.vnlistParam ? vm.vnlistParam : null
                , animalVnlist06: vm.vnlist06 ? vm.vnlist06: null
                , listAnimalVnlist06: vm.vnList06Param ? vm.vnList06Param : null
                , animalVnlist06ByForm: false
            }
            service.reportAnimalEndangeredPreciousRareNormarlAndCites(searchDto).then(function (data) {
                vm.results = data;
            });
        }

      
        function getAllProvince() {
            service.getAdminstrativeUnitsCity().then(function (data) {
                vm.adminstrativeUnits_City = data || [];
            });
        }
        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                        vm.selectedDist = null;
                        vm.selectedWard = null;
                        vm.district = null;
                        vm.ward = null;
                        vm.district = null;
                    } else {
                        vm.district = null;
                        vm.ward = null;
                        vm.selectedDist = null;
                        vm.selectWards = null;
                        vm.district = null;
                        vm.adminstrativeUnits_Dist = [];
                        vm.adminstrativeUnits_Wards = [];
                    }
                });
            } else {
                vm.district = null;
                vm.ward = null;
                vm.selectedDist = null;
                vm.selectWards = null;
                vm.district = null;
                vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Wards = [];
            }
            vm.pageIndex = 0;
        }

        vm.onFmsadminstrativeUnitDistSelected = function (dist) {
            if (dist != null && dist.id != null) {
                service.getAllByParentId(dist.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                        vm.ward = null;
                        vm.selectWards = null;
                        vm.ward = null;
                    }
                    else {
                        vm.selectWards = null;
                        vm.ward = null;
                        vm.adminstrativeUnits_Wards = [];
                    }
                });
            }
            else {
                vm.selectWards = null;
                vm.ward = null;
                vm.adminstrativeUnits_Wards = [];
            }

            vm.pageIndex = 0;
        }
        vm.onFmsadminstrativeUnitWardsSelected = function (item) {
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
                if (vm.farm.code) {
                    vm.farmName += vm.farm.code + " - ";
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

        vm.clearFarmSelected = function () {
            vm.farmSelected = null;
            vm.farmName = null;
        }

        vm.enterSearchFarm = function () {
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
		//Popup loài nuôi (animal)
		vm.selectedAnimals = [];
        vm.animalSelected = null;
        vm.showPopupAnimal = function () {
            vm.subModalInstance = modal.open({
                animation: true,
                templateUrl: 'choose_animal_modal.html',
                scope: $scope,
                size: 'lg'
            });
            vm.code = null;
            vm.selectedAnimals = [];
            vm.anmialSelected = {};
            vm.findByAnimal();

            vm.subModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {

                }
            }, function () {
                vm.animalCode = null;
                console.log("cancel");
            });
        }

        var closeModal = function () {

            vm.subModalInstance.close();

        };
        function checkAgreeAnimal() {
            console.log("checkAgree");
            console.log(vm.selectedAnimals);
            if (angular.isUndefined(vm.selectedAnimals) || vm.selectedAnimals.length == 0) {
                toastr.warning($translate.instant("exportAnimal.notChooseAnimal"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.agreeAnimal = function () {
            if (checkAgreeAnimal()) {
                vm.anmialSelected = vm.selectedAnimals[0];
                vm.animal = vm.selectedAnimals[0];
                vm.animalName = "";
                if (vm.animal.code) {
                    vm.animalName += vm.animal.code + " - ";
                }
                vm.animalName += vm.animal.name;

                //vm.findBy();

                vm.animalCode = null;
                closeModal();
            }
        }


        vm.bsTableControlAnimal = {
            options: {
                data: vm.animals,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: true,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeAnimal,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionAnimal(),
                onCheck: function (row, $element) {
                    if (vm.selectedAnimals != null) {
                        vm.selectedAnimals = [];
                    }
                    $scope.$apply(function () {
                        vm.selectedAnimals.push(row);
                    });
                },

                onUncheck: function (row, $element) {

                    if (vm.selectedAnimals != null) {
                        vm.selectedAnimals = [];
                    }
                },

                onPageChange: function (index, pageSize) {
                    vm.pageSizeAnimal = pageSize;
                    vm.pageIndexAnimal = index;
                    //vm.searchTeachers();
                    vm.findByAnimal();
                }
            }
        };

        vm.clearAnimalSelected = function () {
            vm.animalSelected = null;
            vm.animalName = null;
			vm.animal = null;
        }

        vm.enterSearchAnimal = function () {
            if (event.keyCode == 13) {//Phím Enter
                vm.searchByAnimal();
            }
        };
        vm.searchByAnimal = function () {
            vm.pageIndexAnimal = 1;
            vm.bsTableControlAnimal.state.pageNumber = 1;
            vm.findByAnimal();
        };
        vm.findByAnimal = function () {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (vm.animalCode != null && vm.animalCode != "") {
                vm.searchDto.nameOrCode = vm.animalCode;
            }
            else {
                vm.searchDto.nameOrCode = null;
            }
            service.getPageSearchAnimal(vm.searchDto, vm.pageIndexAnimal, vm.pageSizeAnimal).then(function (data) {
                vm.animals = data.content;
                vm.bsTableControlAnimal.options.data = vm.animals;
                vm.bsTableControlAnimal.options.totalRows = data.totalElements;
            });
        }
        vm.findByAnimalSelected = function() {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            service.getPageSearchAnimal(vm.searchDto, 1, 100000).then(function (data) {
                vm.animals = data.content;
            });
        }
		
        vm.getDataReportAnimalDangerousCitesToExcel = function () {
            vm.myBlobObject = undefined;
            const searchDto = {};
            if (!vm.selectYear) {
                toastr.warning("Chưa chọn năm.", "Cảnh báo");
                return;
            }
            if (!vm.dateReport) {
                toastr.warning("Chưa chọn ngày.", "Cảnh báo");
                return;
            }

            searchDto.year = vm.selectYear ? vm.selectYear : null;
            searchDto.dateReport = vm.dateReport ? vm.dateReport : null;
            searchDto.provinceId = vm.province ? vm.province:null;
            searchDto.districtId = vm.district ? vm.district:null;
            searchDto.communeId = vm.ward ? vm.ward:null;
			searchDto.animalId = vm.animal ? vm.animal.id : null;
            searchDto.animalIds = vm.searchDto.animalIds ? vm.searchDto.animalIds : null;
            searchDto.farmId =vm.farmSelected ? vm.farmSelected.id : null;
            searchDto.animalClass = vm.searchDto.animalClass ? vm.searchDto.animalClass:null;
            searchDto.listAnimalClass = vm.searchDto.listAnimalClass ? vm.searchDto.listAnimalClass:null;
            searchDto.animalOrdo = vm.searchDto.ordo ? vm.searchDto.ordo:null;
            searchDto.listAnimalOrdo = vm.searchDto.listAnimalOrdo ? vm.searchDto.listAnimalOrdo:null;
            searchDto.animalFamily = vm.searchDto.family ? vm.searchDto.family:null;
            searchDto.listAnimalFamily = vm.searchDto.listAnimalFamily ? vm.searchDto.listAnimalFamily:null;
            searchDto.animalCites = vm.cites ? vm.cites :null;
            searchDto.listAnimalCites = vm.listCitesParam ? vm.listCitesParam : null;
            searchDto.animalVnlist= vm.vnlist ? vm.vnlist: null;
            searchDto.listAnimalVnlist = vm.vnlistParam ? vm.vnlistParam : null;
            searchDto.animalVnlist06= vm.vnlist06 ? vm.vnlist06: null;
            searchDto.listAnimalVnlist06 = vm.vnList06Param ? vm.vnList06Param : null;
            searchDto.animalVnlist06ByForm= false;

            console.log('download started, you can show a wating animation');
            service.getDataExportReportAnimalDangerousCitesToExcel(searchDto)
                .then(function (data) {//is important that the data was returned as Aray Buffer
                	var file = new Blob([data], {
                		type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                    });
                    FileSaver.saveAs(file, "DuLieuBaoCaoHoatDongNuoiDVHD.xls");
                }, function (fail) {
                    console.log('Download Error, stop animation and show error message');
                    $scope.myBlobObjectFileImportExcel = [];
                });
        };
		
		vm.getDataReportAnimalDangerousCitesToExcelByForm = function () {
            vm.myBlobObjectByForm = undefined;
            const searchDto = {};
            if (!vm.selectYear) {
                toastr.warning("Chưa chọn năm.", "Cảnh báo");
                return;
            }
            if (!vm.dateReport) {
                toastr.warning("Chưa chọn ngày.", "Cảnh báo");
                return;
            }

            searchDto.year = vm.selectYear ? vm.selectYear : null;
            searchDto.dateReport = vm.dateReport ? vm.dateReport : null;
            searchDto.provinceId = vm.province ? vm.province:null;
            searchDto.districtId = vm.district ? vm.district:null;
            searchDto.communeId = vm.ward ? vm.ward:null;
			searchDto.animalId = vm.animal ? vm.animal.id : null;
            searchDto.animalIds = vm.searchDto.animalIds ? vm.searchDto.animalIds : null;
            searchDto.farmId =vm.farmSelected ? vm.farmSelected.id : null;
            searchDto.animalClass = vm.searchDto.animalClass ? vm.searchDto.animalClass:null;
            searchDto.listAnimalClass = vm.searchDto.listAnimalClass ? vm.searchDto.listAnimalClass:null;
            searchDto.animalOrdo = vm.searchDto.ordo ? vm.searchDto.ordo:null;
            searchDto.listAnimalOrdo = vm.searchDto.listAnimalOrdo ? vm.searchDto.listAnimalOrdo:null;
            searchDto.animalFamily = vm.searchDto.family ? vm.searchDto.family:null;
            searchDto.listAnimalFamily = vm.searchDto.listAnimalFamily ? vm.searchDto.listAnimalFamily:null;
            searchDto.animalCites = vm.cites ? vm.cites :null;
            searchDto.listAnimalCites = vm.listCitesParam ? vm.listCitesParam : null;
            searchDto.animalVnlist= vm.vnlist ? vm.vnlist: null;
            searchDto.listAnimalVnlist = vm.vnlistParam ? vm.vnlistParam : null;
            searchDto.animalVnlist06= vm.vnlist06 ? vm.vnlist06: null;
            searchDto.listAnimalVnlist06 = vm.vnList06Param ? vm.vnList06Param : null;
            searchDto.animalVnlist06ByForm = true;
            console.log('download started, you can show a wating animation');
            service.getDataExportReportAnimalDangerousCitesToExcelByForm(searchDto)
                .then(function (data) {//is important that the data was returned as Aray Buffer
                	var file = new Blob([data], {
                		type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                    });
                    FileSaver.saveAs(file, "BaocaoDVgaynuoitheoNd06.xlsx");
                }, function (fail) {
                    console.log('Download Error, stop animation and show error message');
                    $scope.myBlobObjectByForm = [];
                });
        };
        vm.getListAnimalOrdo = function (callSearchFunction) {
            animalService.getListAnimalOrdo(vm.searchDto.animalClass).then(function (data) {
                vm.listAnimalOrdo = [];
                vm.listAnimalFamily = [];
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

        vm.getListAnimalOrdoParam = function (callSearchFunction) {
            animalService.getListAnimalOrdoParam(vm.searchDto).then(function (data) {
                vm.listAnimalOrdo = [];
                vm.listAnimalFamily = [];
                vm.listAnimalOrdoParam = null;
                vm.listAnimalFamilyParam = null;
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

        vm.searchDto.listAnimalClass = [];
        vm.searchDto.listAnimalOrdo = [];
        vm.searchDto.listAnimalFamily = [];
        vm.listAnimalClassSelected = function() {
            if(vm.listAnimalClassParam && vm.listAnimalClassParam == "-") {
            vm.searchDto.listAnimalClass = null;
            vm.searchDto.listAnimalOrdo = null;
            vm.searchDto.listAnimalFamily = null;
            } else {
                vm.searchDto.listAnimalClass = vm.listAnimalClassParam;
                vm.searchDto.listAnimalOrdo = null;
                vm.searchDto.listAnimalFamily = null;
            }
            vm.getListAnimalOrdoParam(true);
        }

        // vm.animalOrdoSelected = function () {
        //     if (vm.ordo && vm.ordo == '-') {
        //         vm.searchDto.ordo = null;
        //         vm.searchDto.family = null;
        //     }
        //     else{
        //         vm.searchDto.ordo = vm.ordo;
        //         vm.searchDto.family = null;
        //     }
        //     vm.getListAnimalFamily(true);
        // }

        vm.listAnimalOrdoSelected = function() {
            if(vm.listAnimalOrdoParam && vm.listAnimalOrdoParam == '-') {
                vm.searchDto.listAnimalOrdo = null;
                vm.searchDto.listAnimalFamily = null;
            }
            else {
                vm.searchDto.listAnimalOrdo = vm.listAnimalOrdoParam;
                vm.searchDto.listAnimalFamily = null;
            }
            vm.getListAnimalFamilyParam(true);
        }


        // vm.familySelected = function () {
        //     if (vm.family && vm.family == '-') {
        //         vm.searchDto.family = null;
        //     }
        //     else{
        //         vm.searchDto.family = vm.family;
        //     }
        //     vm.findByAnimal();
        // }

        vm.listAnimalFamilySelected = function() {
            if(vm.listAnimalFamilyParam && vm.listAnimalFamilyParam == '-') {
                vm.searchDto.listAnimalFamily = null;
            } else {
                vm.searchDto.listAnimalFamily = vm.listAnimalFamilyParam;
            }
            // vm.findByAnimal();
            vm.findByAnimalSelected();
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
        }

        vm.getListAnimalFamilyParam = function (callSearchFunction) {
            animalService.getListAnimalFamilyParam(vm.searchDto).then(function (data) {
                vm.listAnimalFamily = [];
                vm.listAnimalFamilyParam = null;
                data.forEach(item => {
                    var title = '-';
                    if (item && item.trim().length > 0) {
                        title = item;
                    }
                    vm.listAnimalFamily.push({ name: title });
                });
            });
            if(callSearchFunction) {
                // vm.findByAnimal();
                vm.findByAnimalSelected();
            }
        }
        vm.listAnimalClass = [];
        vm.listAnimalFamily = [];
        vm.listAnimalOrdo = [];
        // vm.getListAnimalClass = function () {
        //     animalService.getListAnimalClass().then(function (data) {
        //         vm.listAnimalClass = [];
        //         vm.animalClass = null;
        //         vm.ordo = null;
        //         vm.family = null;
        //         data.forEach(item => {
        //             var title = '-';
        //             if (item && item.trim().length > 0) {
        //                 title = item;
        //             }
        //             vm.listAnimalClass.push({ name: title });
        //         });
        //     });

        //     vm.getListAnimalOrdo();
        // }
        vm.getListAnimalClass = function () {
            animalService.getListAnimalClass().then(function (data) {
                vm.listAnimalClass = [];
                vm.listAnimalClassParam = null;
                vm.listAnimalOrdoParam = null;
                vm.listAnimalFamilyParam = null;
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
        vm.listAnimalSelected = function() {
            if(vm.listAnimals && vm.listAnimals.length > 0) {
                vm.searchDto.animalIds = [];
                vm.listAnimals.forEach((animal) => {
                    vm.searchDto.animalIds.push(animal.id);
                });
            } else {
                vm.searchDto.animalIds = [];
            }
        }
        vm.getVnLists();
        vm.getVnList06s();
        vm.getListCites();
        vm.getListAnimalClass();
        vm.getListAnimalOrdo();
        vm.getListAnimalFamily();

    }

})();
