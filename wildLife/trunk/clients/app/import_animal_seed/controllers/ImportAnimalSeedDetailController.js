/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ImportAnimalSeed').controller('ImportAnimalSeedDetailController', ImportAnimalSeedDetailController);

    ImportAnimalSeedDetailController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ImportAnimalSeedService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        'Upload',
        'FileSaver',
        'FarmService',
        'AnimalService',
        '$translate',
        'OriginalService',
        'ProductTargetService',
        'InjectionTimeService'
    ];

    angular.module('Education.ImportAnimalSeed').directive('numberInput', function ($filter) {
        return {
            require: 'ngModel',
            link: function (scope, elem, attrs, ngModelCtrl) {

                ngModelCtrl.$formatters.push(function (modelValue) {
                    return setDisplayNumber(modelValue, true);
                });

                // it's best to change the displayed text using elem.val() rather than
                // ngModelCtrl.$setViewValue because the latter will re-trigger the parser
                // and not necessarily in the correct order with the changed value last.
                // see http://radify.io/blog/understanding-ngmodelcontroller-by-example-part-1/
                // for an explanation of how ngModelCtrl works.
                ngModelCtrl.$parsers.push(function (viewValue) {
                    setDisplayNumber(viewValue);
                    return setModelNumber(viewValue);
                });

                // occasionally the parser chain doesn't run (when the user repeatedly 
                // types the same non-numeric character)
                // for these cases, clean up again half a second later using "keyup"
                // (the parser runs much sooner than keyup, so it's better UX to also do it within parser
                // to give the feeling that the comma is added as they type)
                elem.bind('keyup focus', function () {
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

    function ImportAnimalSeedDetailController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, util, Uploader, FileSaver, farmService, animalService, $translate, originalService, productTargetService, injectionTimeService) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;
        vm.ImportAnimal = {};
        vm.currentId = $state.params.id ? $state.params.id : null;
        vm.farmSelected = null;
        vm.animalSelected = null;
        vm.pageSize = 25;
        vm.pageIndex = 1;
        vm.farms = [];
        vm.animals = [];
        vm.importAnimal = {};
        vm.injectionPlants = [];
        vm.injectionPlant = {};
        vm.modalInjectionPlant = {};
        vm.indexInjectionPlant = -1;
        vm.originals = [];
        vm.original = {};
        vm.productTargets = [];
        vm.injectionTimes = [];
        vm.parentAnimals = [];
        vm.animalByParents = [];
        vm.animal = {};
        vm.isRole = false;
        vm.isFamer = false;
        vm.animalGenders = settings.animalGenders;
        vm.numberOne = 1;
        vm.individual = null;
        vm.animalStatuses = settings.IndividualAnimalStatuses;
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
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_famer') {
                        vm.isFamer = true;
                    }
                }
            } else {
                vm.isRole = false;
                vm.isFamer = false;
            }
			/*if(vm.isRole){//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
				farmService.getAll().then(function (data) {
					vm.farms = data;
				}).catch(function (error) {
					vm.farms = [];
				});
			}else{// trường hợp này thì phân quyền cơ sở chăn nuôi theo user
				if(vm.isRole==false){
					getAdministrativeUnitDtoByLoginUser();
					
				}
			*/
            if (vm.isFamer == true) {
                getAdministrativeUnitDtoByLoginUser();
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
		/*vm.findBy=function () {
            if( vm.searchDto==null){
                vm.searchDto={};
            }
 
            console.log(vm.searchDto);
            service.getPageSearchFarm(vm.searchDto).then(function(data) {
                 vm.farms = data;
                
            });
        }*/

        //--------End ----Phân quyền-------------

		/*
		* Tự động tạo mã lô
    */

		/*vm.getBatchCode=function(){
			console.log(vm.importAnimal.dateImport);
			service.getBatchCode(new Date()).then(function (data) {
				console.log(data);
				if (data) {
					vm.importAnimal.batchCode = data.batchCode;                       						
				}
			});
						 
		 }*/
        vm.generate = function () {
            if (vm.currentId) {
                service.getImportAnimal(vm.currentId).then(function (data) {
                    console.log(data);
                    if (data) {
                        vm.importAnimal = data;
                        vm.injectionPlants = data.injectionPlants;
                        vm.individuals = data.individualAnimals;
                        vm.individuals.sort((a, b)=> {
                            if(a > b)return 1;
                            if(a < b)return -1;
                            if(a == b) return 0;
                        });
                        console.log(vm.injectionPlants);
                        vm.farmSelected = data.farm;
                        // debugger
                        vm.farmName = "";
                        if(vm.farmSelected.code){
                            vm.farmName += vm.farmSelected.code+ " - ";
                        }
                        vm.farmName += vm.farmSelected.name;
                        // vm.farmName = vm.farmSelected.name;
                        vm.animalSelected = data.animal.parent;
                        // animalService.getListByParentId(vm.animalSelected.id).then(function (data) {
                        //     vm.animalByParents = data;
                        //     console.log(vm.animalByParents);
                        // });
                        // if (vm.importAnimal.animal != null) {
                        //     animalService.getAnimal(vm.importAnimal.animal.id).then(function (data) {
                        //         vm.animal = data;
                        //         console.log(vm.animal);
                        //         vm.productTargets = vm.animal.animalProductTargets;
                        //     });
                        // }

                    }
                });
            } else {
                let date = new Date();

                vm.importAnimal.dateImport = { year: date.getFullYear(), month: date.getMonth(), day: date.getDate() };
                //vm.getBatchCode();
            }

            animalService.getAll().then(function (data) {
                vm.animals = data;
            }).catch(function (error) {
                vm.animals = [];
            });
            
        }
        service.getPageSearchFarm({showListFarmSelect: true}, 1, 10).then(function(data){
            // debugger
            if(data.content && data.content.length == 1){
                const farmTemp = data.content[0];
                vm.importAnimal.farm = farmTemp;
                vm.farmName = "";
                if(farmTemp.code){
                    vm.farmName += farmTemp.code+ " - ";
                }
                vm.farmName += farmTemp.name;
            }
        });
        vm.changedBatchCode = function (date) {
            if (date != null && ($state.params.id == null || $state.params.id == 0)) {
                service.getBatchCode(date).then(function (data) {
                    console.log(data);
                    if (data) {
                        vm.importAnimal.batchCode = data.batchCode;
                    }
                });
            }

        }
        

        vm.saveImportAnimal = function () {
            /*if(!vm.importAnimal.batchCode){
                toastr.warning($translate.instant("importAnimal.requiredBatchCode"), $translate.instant("toastr.warning"));
                return ;
            }*/
            if (!vm.farmName) {
                toastr.warning($translate.instant("importAnimal.farmName"), $translate.instant("toastr.warning"));
                return;
            }
            if (!vm.importAnimal.dateImport) {
                toastr.warning($translate.instant("importAnimal.requiredImportDate"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importAnimal.dateImport != null && vm.importAnimal.dateImport > new Date()) {
                toastr.warning($translate.instant("importAnimal.warningDate"), $translate.instant("toastr.warning"));
                return;
            }
            if (!vm.farmSelected) {
                toastr.warning($translate.instant("importAnimal.requiredFarm"), $translate.instant("toastr.warning"));
                return;
            }
            if (!vm.importAnimal.animal) {
                toastr.warning($translate.instant("importAnimal.requiredAnimal"), $translate.instant("toastr.warning"));
                return;
            }
            if (typeof vm.importAnimal.dayOld == typeof 0 && vm.importAnimal.dayOld < 0) {
                toastr.warning($translate.instant("Ngày tuổi không được nhỏ hơn 0"), $translate.instant("toastr.warning"));
                return;
            }
            if (!vm.importAnimal.animalReportDataType) {
                toastr.warning($translate.instant("Chưa chọn mục thuộc loại"), $translate.instant("toastr.warning"));
                return;
            }
            // if (!vm.importAnimal.lifeCycle || vm.importAnimal.lifeCycle < 1) {
            //     toastr.warning($translate.instant("importAnimal.requiredLifeCycle"), $translate.instant("toastr.warning"));
            //     return;
            // }
            // if (!vm.importAnimal.seedLevel || vm.importAnimal.seedLevel == null) {
            //     toastr.warning($translate.instant("importAnimal.requiredSeedLevel"), $translate.instant("toastr.warning"));
            //     return;
            // }
            // if ( vm.importAnimal.gender < -1 || vm.importAnimal.gender == null || vm.importAnimal.gender == undefined) {
            //     toastr.warning($translate.instant("importAnimal.genderRequired"), $translate.instant("toastr.warning"));
            //     return;
            // }
            // if (!vm.importAnimal.productTarget || vm.importAnimal.productTarget == null) {
            //     toastr.warning($translate.instant("importAnimal.requiredProductTarget"), $translate.instant("toastr.warning"));
            //     return;
            // }
            vm.importAnimal.type = 1;
            vm.importAnimal.injectionPlants = vm.injectionPlants;
            vm.importAnimal.individualAnimals = vm.individuals;
            // if(vm.importAnimal.code == settings.BEAR_CODE){
            //     vm.importAnimal.quantity = vm.numberOne;
            // }
            if (vm.currentId) {
                if (vm.importAnimal) {
                    service.updateImportAnimal(vm.currentId, vm.importAnimal, function success(data) {
                        // if(settings.DUPLICATE_CHIP_CODE == data.chipCode){
                        //     toastr.warning($translate.instant("importAnimal.duplicateChipCode"), $translate.instant("toastr.warning"));
                        // }else{
                            
                        // }
                        
                        
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    }).then(function(data){
                        if(data.dupCodeIndividualAnimals){
                            console.log(data.dupCodeIndividualAnimals);
                            vm.individualAnimalDupCode = data.individualAnimalDupCode;
                            vm.modalDuplicateCode = modal.open({
                                animation: true,
                                templateUrl: 'popup_code_dup_detail.html',
                                scope: $scope,
                                backdrop: 'static',
                                keyboard: false,
                                size: 'md'
                            });
                        }else {
                            toastr.info($translate.instant("importAnimal.saveSuccessfully"), $translate.instant("toastr.info"));
                            $state.go('application.import_animal_seed');
                        }
                    });
                }
            } else {
                service.createImportAnimal(vm.importAnimal, function success(data) {
                    // if(settings.DUPLICATE_CHIP_CODE == data.chipCode){
                    //     toastr.warning($translate.instant("importAnimal.duplicateChipCode"), $translate.instant("toastr.warning"));
                    // }else{
                    // }
                    // $state.go('application.import_animal_seed');
                    // toastr.info($translate.instant("importAnimal.saveSuccessfully"), $translate.instant("toastr.info"));
                   
                }, function failure(error) {
                    toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    console.log(error);
                }).then(function(data){
                    if(data.dupCodeIndividualAnimals){
                        console.log(data.dupCodeIndividualAnimals);
                        vm.individualAnimalDupCode = data.individualAnimalDupCode;
                        vm.modalDuplicateCode = modal.open({
                            animation: true,
                            templateUrl: 'popup_code_dup_detail.html',
                            scope: $scope,
                            backdrop: 'static',
                            keyboard: false,
                            size: 'md'
                        });
                    }else {
                        toastr.info($translate.instant("importAnimal.saveSuccessfully"), $translate.instant("toastr.info"));
                        $state.go('application.import_animal_seed');
                    }
                });
            }
        }

        vm.goBack = function () {
            $state.go('application.import_animal_seed');
        }
        vm.generate();


        vm.editIndividual = function (index) {
            vm.indexIndividual = index;
            vm.individual = JSON.parse(JSON.stringify(vm.individuals[vm.indexIndividual]));
            vm.individual.isNew = false;
            vm.modalInjectionPlant = modal.open({
                animation: true,
                templateUrl: 'individual_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        vm.createInjectionPlants = function (index) {
            vm.indexIndividual = -1;
            vm.individual = { isNew: true };
            vm.modalInjectionPlant = modal.open({
                animation: true,
                templateUrl: 'individual_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.deleteInjectionPlants = function (index) {
            vm.indexIndividual = index;
            vm.modalInjectionPlant = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_injection_plant.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }


        vm.confirmDeleteImportAnimal = function () {
            vm.individuals.splice(vm.indexIndividual, 1);
            vm.modalInjectionPlant.close();
            if(vm.individuals.length > 0){
                vm.importAnimal.quantity = vm.individuals.length;
            }
        }
        vm.individuals = [];
        vm.addIndividual = function () {
            vm.indexIndividual = -1;
            let date = new Date();
            vm.individual = { isNew: true, birthDate: { year: date.getFullYear(), month: date.getMonth(), day: date.getDate() } };
           
            vm.modalInjectionPlant = modal.open({
                animation: true,
                templateUrl: 'individual_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.saveIndividual = function () {
            if (vm.validateAddIndividual()) {
                if (vm.indexIndividual == -1) {
                    vm.individuals.push(vm.individual);
                } else {
                    vm.individuals[vm.indexIndividual] = vm.individual;
                }
                vm.modalInjectionPlant.close();
                if(vm.individuals.length > 0){
                    // vm.importAnimal.quantity = vm.individuals.length;
                }
            }
        }

        vm.validateAddIndividual = function () {
            if(vm.individual){
                // if (!vm.individual.name || vm.individual.name.trim().length <= 0) {
                //     toastr.warning("Phải nhập tên động vật hoang dã");
                //     return false;
                // }
                if (!vm.individual.code || vm.individual.code.trim().length <=0) {
                    toastr.warning("Phải nhập mã số chip");
                    return false;
                }
                if (vm.individual.code && vm.checkDuplicateIndividualAnimalCode(vm.individual.code)) {
                    toastr.warning("Mã số chíp bị trùng");
                    return false;
                }
                // if (typeof vm.individual.individualAnimalStatus != typeof 0 ||vm.individual.individualAnimalStatus < 0) {
                //     toastr.warning("Phải nhập trạng thái");
                //     return false;
                // }
                if (!vm.individual.individualAnimalStatus || vm.individual.individualAnimalStatus.trim().length == 0) {
                    toastr.warning("Phải nhập trạng thái");
                    return false;
                }
                if (typeof vm.individual.gender!= typeof 0 || vm.individual.gender < 0) {
                    toastr.warning("Phải nhập giới tính");
                    return false;
                }
                if (!vm.individual.birthDate) {
                    toastr.warning("Phải nhập ngày sinh");
                    return false;
                }
                if (!vm.individual.dayOld || vm.individual.dayOld < 0) {
                    toastr.warning("Phải nhập ngày tuổi lớn hơn không");
                    return false;
                }
            }
            return true;
        }
        vm.checkDuplicateIndividualAnimalCode = function(newCode){
            for(let index = 0;index < vm.individuals.length; index++){
                if(newCode == vm.individuals[index].code && vm.indexIndividual != index){
                    return true;
                }
            }
            return false;
        }
        vm.onFarmSelected = function (item) {
            vm.importAnimal.farm = vm.farmSelected;
        };
        vm.preMale = 0;
        vm.preFemale = 0;
        vm.preUnGender= 0;
        vm.onAnimalSelected = function (item) {
            /*vm.importAnimal.animal = vm.animalSelected;*/
            if(vm.importAnimal.animalReportDataType == settings.AnimalReportDataTypeENUM.childUnder1YearOld ){
                if(vm.importAnimal.male){
                    vm.preMale = vm.importAnimal.male;
                }
                if(vm.importAnimal.female){
                    vm.preFemale = vm.importAnimal.female;
                }
                vm.importAnimal.male = 0;
                vm.importAnimal.female = 0;
            }else{
                if(vm.preMale){
                    vm.importAnimal.male = vm.preMale;
                }
                if(vm.preFemale){
                    vm.importAnimal.female = vm.preFemale;
                }
                
            }
            if(vm.importAnimal.animalReportDataType == settings.AnimalReportDataTypeENUM.animalParent || vm.importAnimal.animalReportDataType == settings.AnimalReportDataTypeENUM.gilts){
                if(vm.importAnimal.unGender){
                    vm.preUnGender = vm.importAnimal.unGender;
                }
                vm.importAnimal.unGender = 0;
            }else{
                if(vm.preUnGender){
                    vm.importAnimal.unGender = vm.preUnGender;
                }
            }

            
            vm.updateQuantity();
        };
        /*
         * Nguồn gốc động vật
         */
        vm.getOriginals = function () {
            originalService.getAllOriginalDto().then(function (data) {
                vm.originals = data;
            });
        }
        vm.getOriginals();
        /*
         * Loại hình sản xuất
         */
        vm.getProductTargets = function(){
        	productTargetService.getAllProductTarget().then(function (data) {
                vm.productTargets = data;
                console.log(vm.productTargets)
            });
        }
        vm.getProductTargets();
        /*
         * Loại động vật
         */
        vm.getListParentAnimal = function () {
            animalService.getAll().then(function (data) {
                vm.parentAnimals = data;
                console.log(vm.parentAnimals)
            });
        }
        vm.getListParentAnimal();
        /*
         * Sự kiện Chọn Loại động vật
         */
        vm.onSelectedAnimal = function (animal) {
            if (animal != null && animal.id > 0) {
                vm.importAnimal.animal = null;
                vm.importAnimal.productTarget = null;
                vm.importAnimal.seedLevel = null;
                // animalService.getListByParentId(animal.id).then(function (data) {
                //     vm.animalByParents = data;
                //     console.log(vm.animalByParents);

                // });
            }

        }
		/*
         * Sự kiện Chọn Loại động vật con để lấy ra list hướng sản phẩm
         */
        // vm.onSelectedAnimalByParent = function (item) {
        //     vm.importAnimal.productTarget = null;
        //     if (item != null && item.animalProductTargets != null && item.animalProductTargets.length > 0) {
        //         vm.productTargets = item.animalProductTargets;
        //     } else {
        //         vm.productTargets = [];
        //     }
        // }

        /*
         * Cấp giống
         */
        vm.getListSeedlevel = function () {
            service.getListSeedlevel().then(function (data) {
                vm.seedLevels = data;
            });
        }
        vm.getListSeedlevel();
        
        vm.getSeedLevelByOption = function(){
            // const listLevel = [settings.BEAR_WITH_CHIP_LEVEL, settings.BEAR_WITHOUT_CHIP_LEVEL];
            // if(vm.animalSelected && vm.animalSelected.code == settings.BEAR_CODE){
            //     if(vm.seedLevels){
            //         return vm.seedLevels.filter(el=>{
            //             return listLevel.indexOf(el.level) > -1;
            //         });
            //     }
            // }else{
            //     if(vm.seedLevels){
            //         return vm.seedLevels.filter(el=>{
            //             return listLevel.indexOf(el.level) == -1;
            //         });
            //     }else{
            //         return [];
            //     }
            // }

            return vm.seedLevels;
        }

        //--------------Popup Farm----------//
        vm.pageIndexFarm = 1, vm.pageSizeFarm = 10;
        vm.subModalInstance = {};

        vm.selectedFarms = [];
        vm.farmSelected = {};
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
            if (vm.isRole) {//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
                vm.findByFarm();
            } else {// trường hợp này thì phân quyền cơ sở chăn nuôi theo user
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUser();

                }

            }


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
                vm.importAnimal.farm = vm.farm;

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
                    if (vm.importAnimal == null) vm.importAnimal = {};
                    vm.farmName = vm.farms[0].name;
                    vm.farmCode = vm.farms[0].code;
                    vm.importAnimal.farm = vm.farms[0];
                }
                //console.log(vm.farms);
            });
        }

        vm.onSelectSeedLevel = function(){
            if(vm.importAnimal.seedLevel && vm.importAnimal.seedLevel.level == settings.animalParent && vm.importAnimal.gender == settings.NumberZero){
                vm.importAnimal.gender = null;
            }
        }

        vm.getGenderWithAnimalParent = function(){
            if( (vm.importAnimal.seedLevel && (vm.importAnimal.seedLevel.level == settings.animalParent||vm.importAnimal.seedLevel.level == settings.GILTS) ) || (vm.animalSelected&& vm.animalSelected.code == settings.BEAR_CODE) ){
                return vm.animalGenders.filter(el => el.value != settings.NumberZero);
            }else{
                return vm.animalGenders;
            }
        }
        vm.getAnimalGender = function(value){
            const result = vm.animalGenders.find(item => item.value == value);
            if(result){
                return result.name;
            }else return "";
            
        }
        vm.getAnimalStatus = function(value){
            const result = vm.animalStatuses.find(item => item.value == value);
            if(result){
                return result.name;
            }else return "";
            
        }

        vm.updateQuantity = function(){
            vm.importAnimal.quantity = 0;
            if(vm.importAnimal.male && vm.importAnimal.male > 0){
                vm.importAnimal.quantity += vm.importAnimal.male;
            }
            if(vm.importAnimal.female && vm.importAnimal.female > 0){
                vm.importAnimal.quantity += vm.importAnimal.female;
            }
            if(vm.importAnimal.unGender && vm.importAnimal.unGender > 0){
                vm.importAnimal.quantity += vm.importAnimal.unGender;
            }
        }

    }

})();
