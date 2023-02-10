/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.AnimalReportData').controller('AnimalReportDataController', AnimalReportDataController);

    AnimalReportDataController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'AnimalReportDataService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate',
        'FmsAdministrativeService'
    ];
    angular.module('Education').filter('animalPropsFilter', function () {
        return function (items, props) {
            var out = [];
    
            if (angular.isArray(items)) {
                items.forEach(function (item) {
                    var itemMatches = false;
    
                    var keys = Object.keys(props);
                    for (var i = 0; i < keys.length; i++) {
                        var prop = keys[i];
                        var text = props[prop].toLowerCase();
                        if (item[prop] && item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                            itemMatches = true;
                            break;
                        }
                    }
    
                    if (itemMatches) {
                        out.push(item);
                    }
                });
            } else {
                // Let the output be the input untouched
                out = items;
            }
    
            return out;
        }
    });

    function AnimalReportDataController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate, fmsAdministrativeService) {
        $scope.$on('$viewContentLoaded', function () {
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
        vm.animalReportDatas = []
        vm.modalFarmSize = {};
        vm.animalReportData = { isNew: false };
        vm.modalConfirmDelete = {};
        vm.selectedFarmSize = [];
        vm.count = 0;
        vm.searchDto = {};
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.subModalInstance = null;
        vm.pageIndexFarm = 1;
        vm.pageSizeFarm = 25;
        vm.farmCode = null;
        vm.originals = [];
        vm.productTargets = [];
        vm.individuals = [];
        vm.farmSelected = null;
        vm.bsTableControl = {
            options: {
                data: vm.animalReportDatas,
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
                        vm.selectedFarmSize.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedFarmSize = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedFarmSize);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedFarmSize.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedFarmSize = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    vm.getListAnimalReportData();
                    //vm.getAllAnimalType();
                }
            }
        };
        vm.isRole=false;
		vm.isFamer=false;
		vm.isSdah=false;
		vm.isDistrict=false;
		vm.isWard=false;
		vm.isSearchExtend=false;
		vm.isSdahView=false;//cấp tỉnh nhưng chỉ được xem
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
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah_view' ) {
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
			if(vm.isRole){//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
				vm.getAllCity();
				vm.getListAnimalReportData();
			}else{// trường hợp này thì phân quyền theo user
				if(vm.isRole==false){
					getAdministrativeUnitDtoByLoginUser();
					
				}
				
			}
            


            blockUI.stop();
        });
		vm.getAllCity = function() {
            fmsAdministrativeService.getAllCity().then(function(data) {
                vm.adminstrativeUnits_City = data;
                
            });
        }
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
			if(administrativeUnit.parentDto==null){
				vm.province=administrativeUnit;
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				vm.adminstrativeUnits_City.push(administrativeUnit);
				service.getAllByParentId(vm.province.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    
                });
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				vm.district=administrativeUnit;
				vm.province = administrativeUnit.parentDto;
				
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
				if(vm.adminstrativeUnits_Dist==null) vm.adminstrativeUnits_Dist=[];
				service.getAllByParentId(vm.province.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    
                });
				service.getAllByParentId(vm.district.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                    
                });
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){
				vm.ward=administrativeUnit;				
                vm.district = administrativeUnit.parentDto;
				vm.province = administrativeUnit.parentDto.parentDto;
				
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
				if(vm.adminstrativeUnits_Dist==null) vm.adminstrativeUnits_Dist=[];
				vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
				if(vm.adminstrativeUnits_Wards==null) vm.adminstrativeUnits_Wards=[];
				vm.adminstrativeUnits_Wards.push(administrativeUnit);
			}
			if(vm.isRole == false && vm.isFamer==true &&  vm.user!==null && vm.user.username!==null && vm.user.username!==''){//trường hợp tài khoản nông dân
                //gán tên cơ sở chăn nuôi
                vm.textSearch=vm.user.username;
                vm.nameOrCode=vm.user.username;
               // console.log("username");
				//console.log(vm.user.username);
            }
			vm.getListAnimalReportData();
            
        }
        vm.getListAnimalReportData = function () {
            // service.getListAnimalReportData(vm.pageIndex, vm.pageSize).then(function (data) {
            //     vm.animalReportDatas = data.content;
            //     vm.bsTableControl.options.data = vm.animalReportDatas;
            //     vm.bsTableControl.options.totalRows = data.totalElements;
            // });

            var searchObject = {
                provinceId: vm.province ? vm.province.id : null,
                districtId: vm.district ? vm.district.id : null,
                communeId: vm.ward ? vm.ward.id : null,
                year: vm.year ? vm.year : null,
                animalId: vm.animalSelected ? vm.animalSelected.id: null,
                nameOrCode: vm.nameOrCode && vm.nameOrCode.trim().length > 0?vm.nameOrCode.trim(): null
            }
            service.getPageBySearch(searchObject, vm.pageIndex, vm.pageSize).then(function(data){
                vm.animalReportDatas = data.content;
                vm.bsTableControl.options.data = vm.animalReportDatas;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getListAnimalReportData();
        vm.years = [];
        vm.currentYear = new Date().getFullYear();
        for(let year = vm.currentYear - 20;year <= vm.currentYear;year++){
            vm.years.push({value:year, name: year+""});
        }

        vm.enterSearch = function () {
            // console.log(event.keyCode);
            if (event.keyCode == 13) {//Phím Enter

                vm.searchByCode();
            }
        };
        vm.searchByCode = function () {
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.findBy();
        };
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


            console.log(vm.searchDto);
            service.getPageSearchFarmSize(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.animalReportDatas = data.content;
                vm.bsTableControl.options.data = vm.animalReportDatas;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }


        vm.createFarmSize = function () {
            vm.animalReportData = { isNew: true };
            vm.individuals = [];
            vm.farmSelected = null;
            vm.getListAnimal();
            vm.getAllProductTarget();
            vm.getAllOgirinal();
            vm.modalFarmSize = modal.open({
                animation: true,
                templateUrl: 'animal_report_data_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'lg'
            });

        };

        $scope.editAnimalReportData = function (id) {
            if (id) {
                vm.getListAnimal();
                vm.getAllProductTarget();
                vm.getAllOgirinal();
                service.getAnimalById(id).then(function (data) {
                    vm.animalReportData = data;
                    vm.animalReportData.isNew = false;
                    vm.tempCode = vm.animalReportData.code;
                    console.log(vm.animalReportData);
                    if(vm.animalReportData.individualAnimals && vm.animalReportData.individualAnimals.length > 0){
                        vm.individuals = angular.copy(vm.animalReportData.individualAnimals);
                    }else{
                        vm.individuals = [];
                    }
                });
                vm.modalFarmSize = modal.open({
                    animation: true,
                    templateUrl: 'animal_report_data_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'lg'
                });
            }
        }
        $scope.previewAnimalReportData = function (id) {
            if (id) {
                vm.getListAnimal();
                vm.getAllProductTarget();
                vm.getAllOgirinal();
                service.getAnimalById(id).then(function (data) {
                    vm.animalReportData = data;
                    vm.animalReportData.isNew = false;
                    vm.tempCode = vm.animalReportData.code;
                    console.log(vm.animalReportData);
                    if(vm.animalReportData.individualAnimals && vm.animalReportData.individualAnimals.length > 0){
                        vm.individuals = angular.copy(vm.animalReportData.individualAnimals);
                    }else{
                        vm.individuals = [];
                    }
                });
                vm.modalFarmSize = modal.open({
                    animation: true,
                    templateUrl: 'animal_report_data_info_view.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'lg'
                });
            }
        }

        $scope.deleteOriginal = function deleteOriginal(id) {
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.deleteFarmSizes = function () {
            if (vm.selectedFarmSize.length == 0) {
                //toastr.warning("Bạn chưa chọn!")
                toastr.warning($translate.instant("farmSize.emptySelected"), $translate.instant("toastr.warning"));
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

        vm.save = function () {
            if (vm.animalReportData && vm.checkValid()) {
                vm.animalReportData.individualAnimals = vm.individuals;
                if (vm.animalReportData.isNew) {
                    service.createAnimalReportData(vm.animalReportData).then(function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllFarmSize();
                        vm.findBy();
                        vm.modalFarmSize.close();
                        vm.selectedFarmSize = [];
                        vm.getListAnimalReportData();
                        vm.saveListAnimalReport();
                    }).catch(function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                } else {
                    vm.animalReportData.code = vm.tempCode;
                    service.saveAnimalReportData(vm.animalReportData.id, vm.animalReportData).then(function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllFarmSize();
                        vm.findBy();
                        vm.modalFarmSize.close();
                        vm.selectedFarmSize = [];
                        vm.getListAnimalReportData();
                        vm.saveListAnimalReport();
                    }).catch(function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        vm.confirmDelete = function () {
            vm.pageIndex = 1;
            var ids = vm.selectedFarmSize.map(function (element) {
                return element.id;
            });
            service.deleteFarmSizes(ids, function success(data) {
                //toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                //vm.getAllFarmSize();
                vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedFarmSize = [];
            }, function failure(error) {
                //toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                console.log(error);
            });
        }
        $scope.deleteAnimalReportData = function (id) {
            if (id) {
                vm.recordId = id;
            } else {
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
        vm.confirmDeleteRecord = function () {
            if (vm.recordId) {
                service.deleteById(vm.recordId).then(function (data) {
                    if (data != null && data.code == "-1") {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    }
                    else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }
                    // vm.getAllFarmSize();
                    vm.getListAnimalReportData();
                    vm.modalConfirmDelete.close();
                    vm.selectedFarmSize = [];
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
            vm.getPageSearchFarm();
            vm.subModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {

                }
            }, function () {
                vm.farmCode = null;
                console.log("cancel");
            });
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
                    vm.getPageSearchFarm();
                }
            }
        };
        vm.enterSearchFarm = function () {
            // console.log(event.keyCode);
            if (event.keyCode == 13) {//Phím Enter
                vm.searchByFarm();
            }
        };
        vm.searchByFarm = function () {
            vm.pageIndexFarm = 1;
            vm.bsTableControlFarm.state.pageNumber = 1;
            vm.getPageSearchFarm();
        };
        vm.getPageSearchFarm = function () {
            if (vm.searchDtoFarm == null) {
                vm.searchDtoFarm = {};
            }
            if (vm.farmCode != null && vm.farmCode != "") {
                vm.searchDtoFarm.nameOrCode = vm.farmCode;
            } else {
                vm.searchDtoFarm.nameOrCode = null;
            }
            service.getPageSearchFarm(vm.searchDtoFarm, vm.pageIndexFarm, vm.pageSizeFarm).then(function (data) {
                vm.farms = data.content;
                vm.bsTableControlFarm.options.data = vm.farms;
                vm.bsTableControlFarm.options.totalRows = data.totalElements;
                console.log(vm.farms);
                if (vm.isFamer == true && vm.farms != null && vm.farms.length > 0) {
                    if (vm.exportAnimal == null) vm.exportAnimal = {};
                    vm.farmName = vm.farms[0].name;
                    vm.farmCode = vm.farms[0].code;
                    vm.exportAnimal.farm = vm.farms[0];
                }
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
                vm.animalReportData.farm = vm.farmSelected;

                vm.subModalInstance.close();
            }
        }

        vm.getListAnimal = function(){
        	service.getAllAnimal().then(function (data) {
        		vm.animals = data;
            });
        }
        vm.getListAnimal();
        vm.getAllOgirinal = function(){
        	service.getAllOgirinal().then(function (data) {
        		vm.originals = data;
                console.log(vm.getAllAnimal)
            });
        }
        vm.getAllProductTarget = function(){
        	service.getAllProductTarget().then(function (data) {
        		vm.productTargets = data;
                console.log(vm.getAllAnimal)
            });
        }

        vm.checkValid = function(){
            if(!vm.animalReportData.farm){
                toastr.warning($translate.instant("exportAnimal.emptyFarm"), $translate.instant("toastr.warning"));
                return false;
            }
            if(!vm.animalReportData.animal){
                toastr.warning($translate.instant("exportAnimal.emptyAnimal"), $translate.instant("toastr.warning"));
                return false;
            }
            if(!vm.animalReportData.year){
                toastr.warning("Bạn phải chọn năm của kỳ báo cáo", $translate.instant("toastr.warning"));
                return false;
            }
            if(!vm.animalReportData.type){
                toastr.warning("Bạn phải chọn loại", $translate.instant("toastr.warning"));
                return false;
            }
            if(!vm.animalReportData.original){
                toastr.warning("Bạn phải nguồn gốc", $translate.instant("toastr.warning"));
                return false;
            }
            if(!vm.animalReportData.productTarget){
                toastr.warning("Bạn phải chọn mục đích nuôi", $translate.instant("toastr.warning"));
                return false;
            }
            if(vm.animalReportData.unGender < 0){
                toastr.warning("Số lượng vật nuôi không rõ giới tính phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            if(vm.animalReportData.female < 0){
                toastr.warning("Số lượng vật nuôi cái phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            if(vm.animalReportData.male < 0){
                toastr.warning("Số lượng vật nuôi đực phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            let total = 0;
            if(vm.animalReportData.male >= 0){
                total += vm.animalReportData.male
            }
            if(vm.animalReportData.female >= 0){
                total += vm.animalReportData.female
            }
            if(vm.animalReportData.unGender >= 0){
                total += vm.animalReportData.unGender
            }
            if(total <= 0){
                toastr.warning("Tổng số vật nuôi phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            

            return true;
        }



        vm.addIndividual = function(){
            vm.individual = {isNew: true};
            vm.modalIndividualAnimal = modal.open({
                animation: true,
                templateUrl: 'individual_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'lg'
            });
        }
        vm.editIndividual = function(index){
            vm.individualIndex = index;
            vm.individual = angular.copy(vm.individuals[index]);
            vm.individual.isNew = false;
            vm.modalIndividualAnimal = modal.open({
                animation: true,
                templateUrl: 'individual_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.saveIndividual = function(){
            if(vm.individual && vm.individual.isNew == true){
                const len = vm.individuals.length;
                vm.individuals[len] = vm.individual;
                vm.updateTotalView();
                vm.modalIndividualAnimal.close();
            }else if(vm.individual){
                vm.individuals[vm.individualIndex] = vm.individual;
                vm.updateTotalView();
                vm.modalIndividualAnimal.close();
            }
        }
        vm.updateTotalView = function(){
            if(vm.individuals && vm.individuals.length > 0){
                vm.animalReportData.male = 0;
                vm.animalReportData.female = 0;
                vm.animalReportData.unGender = 0;
                for(let index = 0;index < vm.individuals.length;index++){
                    const item = vm.individuals[index];
                    if(item.gender == settings.ENUM_AnimalGenders.male){
                        vm.animalReportData.male++;
                    }else if(item.gender == settings.ENUM_AnimalGenders.female){
                        vm.animalReportData.female++;
                    }else if(item.gender == settings.ENUM_AnimalGenders.unGender){
                        vm.animalReportData.unGender++;
                    }
                }
            }
        }
        vm.deleteIndividual = function(index){
            vm.individualIndex = index;
            vm.modalIndividualConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_individual.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        vm.confirmDeleteIndividual = function(){
            vm.individuals.splice(vm.individualIndex, 1);
            vm.modalIndividualConfirmDelete.close();
            vm.updateTotalView();
        }
        vm.getAnimalGender = function(value){
            const result = settings.animalGenders.find(item => item.value == value);
            if(result){
                return result.name;
            }else return "";
            
        }
        vm.getAnimalStatus = function(value){
            const result = settings.IndividualAnimalStatuses.find(item => item.value == value);
            if(result){
                return result.name;
            }else return "";
        }

        vm.productTargets = [];
        vm.getAllOgirinal();
        vm.modalPopupSource = null;
        vm.modalProductTarget = null;
        vm.listDto = [];
        vm.openPopupSelectTarget = function(){
            vm.modalProductTarget = modal.open({
                animation: true,
                templateUrl: 'modalSelectProductTarget.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        vm.openPopupSelectSource = function(){
            vm.modalPopupSource = modal.open({
                animation: true,
                templateUrl: 'modalSelectOriginal.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        
        vm.selectProductTarget = function(index){
            const selectedItem = vm.productTargets[index];
            vm.rawTable.purpose = selectedItem.code;
            vm.rawTable.productTarget = {id: selectedItem.id};
            vm.modalProductTarget.close();
        }
        vm.selectOriginal = function(index){
            const selectedItem = vm.originals[index];
            vm.rawTable.source = selectedItem.code;
            vm.rawTable.original = {id: selectedItem.id};
            vm.modalPopupSource.close();
        }
        
        vm.calcTotal = function(...arr){
            let total = 0;
            for(let index = 0; index < arr.length;index++){
                let numberSapmle = arr[index];
                if(!numberSapmle){
                    numberSapmle = 0;
                }
                total += numberSapmle;
            }
            return total;
        }
        vm.rawTable = {
            year: vm.currentYear
        };

        vm.addAnimalReport = function(){
            if(!vm.rawTable.animal){
                toastr.warning("Phải chọn loài động vật");
            }else{
                vm.rawTable.totalParent = vm.calcTotal(vm.rawTable.maleParent, vm.rawTable.femaleParent, vm.rawTable.unGenderParent);
                vm.rawTable.totalChild = vm.calcTotal(vm.rawTable.maleChild, vm.rawTable.femaleChild, vm.rawTable.unGenderChild);
                vm.listDto.push(angular.copy(vm.rawTable));
                vm.rawTable = {
                    year: vm.currentYear
                };
                vm.saveListAnimalReport();
                console.log(vm.listDto);
            }
            
        }
        vm.modalPopupSelectAnimal = null;
        vm.openPopupAnimal = function(){
            vm.getListAnimal();
            vm.textSearch="";
            vm.modalPopupSelectAnimal = modal.open({
                animation: true,
                templateUrl: 'modalPopupSelectAnimal.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        };
        vm.selectAnimalAndClosePopup = function(item){
            for(let index = 0; vm.animals.length;index++){
                const animal = vm.animals[index];
                if(item.id == animal.id){
                    vm.rawTable.animal = angular.copy(animal);
                    break;
                }
            }
            vm.modalPopupSelectAnimal.close();
        }
        vm.removeListSelected = function(index){
            vm.listDto.splice(index, 1);
        }
        vm.saveListAnimalReport = function(){
            let listDtoSave = angular.copy(vm.listDto);
            if(vm.rawTable.animal){
                listDtoSave.push(vm.rawTable);
            }
            // if(!vm.animalReportData.farm){
            //     // toastr.warning("Phải chọn cơ sở gây nuôi");
            //     return;
            // }
            if(listDtoSave && listDtoSave.length > 0 && vm.animalReportData.farm.id){
                listDtoSave.forEach(function(item){
                    item.farm = {id: vm.animalReportData.farm.id};
                });
                service.saveListAnimalReport(listDtoSave).then(function(data){
                    vm.listDto = [];
                    vm.rawTable = {year: vm.currentYear};
                });
            }else{
                // toastr.warning("Phải chọn thông tin quần thể loài nuôi");
            }
        }
        vm.getAnimalReportDataType = function(value){
            const res = settings.AnimalReportDataType.find(el => el.value == value);
            if(res){
                return res.name;
            }else
            return "";
        }

        // function getAllProvince(){
        //     service.getAdminstrativeUnitsCity().then(function(data){
        //         vm.adminstrativeUnits_City = data||[];
        //     });
        // }
        // getAllProvince();
        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                        vm.selectedDist = null;
                        vm.selectedWard = null;
                        vm.searchDto.district=null;
						vm.searchDto.ward=null;
						vm.ward=null;
						vm.district=null;
                    } else {
                        vm.searchDto.district=null;
						vm.searchDto.ward=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
						vm.district=null;
		                vm.adminstrativeUnits_Dist = [];
						vm.ward=null;
						vm.adminstrativeUnits_Wards=[];
                    }
                });
            } else {
	        	vm.selectedDist = null;
	        	vm.selectWards = null;
				vm.district=null;
                vm.adminstrativeUnits_Dist = [];				
				vm.ward=null;
				vm.adminstrativeUnits_Wards=[];
            }
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.getListAnimalReportData();
        }

        vm.onFmsadminstrativeUnitDistSelected = function (dist) {
            if (dist != null && dist.id != null) {
				service.getAllByParentId(dist.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Wards = data;				
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
				vm.adminstrativeUnits_Wards=[];
            }
            vm.getListAnimalReportData();
        }
        vm.onFmsadminstrativeUnitWardsSelected=function(item){
            vm.getListAnimalReportData();
        }

        vm.onSelectYear = function(year){
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.getListAnimalReportData();
        }
        vm.enterSearchNameOrCode = function(event){
            if(event.keyCode == 13){
                vm.pageIndex = 1;
                vm.bsTableControl.state.pageNumber = 1;
                vm.getListAnimalReportData();
            }
        }
        vm.searchByNameOrCode = function(){
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.getListAnimalReportData();
        }
        vm.onSelectedAnimal = function(){
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.getListAnimalReportData();
        }

    }

})();
