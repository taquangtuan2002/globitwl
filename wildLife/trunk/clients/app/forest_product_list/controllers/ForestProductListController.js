/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ForestProductList').controller('ForestProductListController', ForestProductListController);

    ForestProductListController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ForestProductListService',
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
        'Utilities',
        'ReportPeriodService',
        'FmsAdministrativeService',
    ];

    angular.module('Education.ForestProductList').directive('numberInput', function ($filter) {
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

    function ForestProductListController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, util, Uploader, FileSaver, farmService, animalService, $translate, utils, reportPeriodService, fmsAdministrativeService) {
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
        vm.animals2 = [];
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
        vm.searchDtoForestProductList = {};
        vm.confirmName = "";
        vm.confirmDate = new Date();
        vm.fromDate = moment().startOf('month').toDate();
        vm.toDate = moment().endOf('month').toDate();
        vm.isEdit = false;
        vm.isViewDetail = false;
        vm.forestProductListSelected = [];
        vm.searchDto = {};
        vm.searchDtoFarm = {};
        vm.idUserAttachments = null;
        vm.idFPLNew = null;
        //------Start--Phân quyền theo user đăng nhập-----------
        blockUI.start();
        service.getCurrentUser().then(function (data) {
            vm.user = data;
            vm.confirmName = data.displayName;
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
            if (vm.isRole) { //trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
                vm.getAllCity();
                //vm.getPageForestProductList();
            } else { // trường hợp này thì phân quyền theo user
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUser();
                }

            }


            blockUI.stop();
        });

        vm.getAllCity = function () {
            fmsAdministrativeService.getAllCity().then(function (data) {
                vm.adminstrativeUnitsCityFrom = data;
            });
        }
        //load user administrativeunit
        function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function (data) {
                vm.adminstrativeUnits = data;
                console.log("A", data)
                if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
                    getDataByCombobox(vm.adminstrativeUnits[0]);

                }
            });
        }

        // function getDataByCombobox(administrativeUnit) {
        //     if (vm.searchDtoFarm == null) {
        //         vm.searchDtoFarm = {};
        //     }
        //     if (administrativeUnit.parentDto == null) {
        //         vm.searchDtoFarm.province = administrativeUnit.id;

        //     } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
        //         vm.searchDtoFarm.district = administrativeUnit.id;
        //         vm.searchDtoFarm.province = administrativeUnit.parentDto.id;

        //     } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
        //         vm.searchDtoFarm.ward = administrativeUnit.id;
        //         vm.searchDtoFarm.district = administrativeUnit.parentDto.id;
        //         vm.searchDtoFarm.province = administrativeUnit.parentDto.parentDto.id;
        //     }
        //     if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') { //trường hợp tài khoản nông dân
        //         //gán tên cơ sở chăn nuôi
        //         vm.farmCode = vm.user.username;
        //         vm.searchDtoFarm.nameOrCode = vm.user.username;
        //     }
        //     vm.findByFarm();
        // }
        function getDataByCombobox(administrativeUnit) {
            if (vm.searchDtoFarm == null) {
                vm.searchDtoFarm = {};
            }
            if (administrativeUnit.parentDto == null) {
                debugger
                vm.searchDtoFarm.province = administrativeUnit.id;
                vm.adminstrativeUnitsCityFrom = [];
                vm.adminstrativeUnitsCityFrom.push(administrativeUnit);
                service.getAllByParentId(vm.searchDtoFarm.province).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_DistFrom = data;
                    }

                });



            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                vm.searchDtoFarm.district = administrativeUnit.id;
                vm.searchDtoFarm.province = administrativeUnit.parentDto.id;
                vm.adminstrativeUnitsCityFrom = [];
                vm.adminstrativeUnitsCityFrom.push(administrativeUnit.parentDto);
                vm.adminstrativeUnits_DistFrom = [];
                if (vm.adminstrativeUnits_DistFrom == null)
                    vm.adminstrativeUnits_DistFrom = [];
                vm.adminstrativeUnits_DistFrom.push(administrativeUnit);
                service.getAllByParentId(vm.searchDtoFarm.province).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_DistFrom = data;
                    }
                });
                service.getAllByParentId(vm.searchDtoFarm.district).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_WardFrom = data;
                    }
                });

            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                vm.searchDtoFarm.ward = administrativeUnit.id;
                vm.searchDtoFarm.district = administrativeUnit.parentDto.id;
                vm.searchDtoFarm.province = administrativeUnit.parentDto.parentDto.id;
                if (vm.adminstrativeUnitsCityFrom == null) vm.adminstrativeUnitsCityFrom = [];
                vm.adminstrativeUnitsCityFrom.push(administrativeUnit.parentDto.parentDto);
                if (vm.adminstrativeUnits_DistFrom == null) vm.adminstrativeUnits_DistFrom = [];
                vm.adminstrativeUnits_DistFrom.push(administrativeUnit.parentDto);
                if (vm.adminstrativeUnits_WardFrom == null)
                    vm.adminstrativeUnits_WardFrom = [];
                vm.adminstrativeUnits_WardFrom.push(administrativeUnit);
            }
            if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') { //trường hợp tài khoản nông dân
                //gán tên cơ sở chăn nuôi
                vm.farmCode = vm.user.username;
                vm.searchDtoFarm.nameOrCode = vm.user.username;
            }
            vm.findByFarm();
        }
        // function getDataByCombobox(administrativeUnit) {
        //     if (vm.searchDtoFarm == null) {
        //         vm.searchDtoFarm = {};
        //     }
        //     // debugger
        //     if (administrativeUnit.parentDto == null) {
        //         vm.searchDtoFarm.province = administrativeUnit.id;
        //         vm.selectedCityFrom = administrativeUnit.id;
        //         if (vm.adminstrativeUnitsCityFrom == null)
        //         vm.adminstrativeUnitsCityFrom = [];
        //         vm.adminstrativeUnitsCityFrom.push(administrativeUnit);
        //         service.getAllByParentId(vm.searchDtoFarm.province).then(function (data) {
        //             if (data != null && data.length > 0) {
        //                 vm.adminstrativeUnits_DistFrom = data;
        //             }

        //         });

        //     } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
        //         vm.searchDtoFarm.district = administrativeUnit.id;
        //         vm.searchDtoFarm.province = administrativeUnit.parentDto.id;
        //         vm.selectedCityFrom = administrativeUnit.parentDto.id;
        //         vm.forestProduct.administrativeUnit = administrativeUnit.id;
        //         vm.adminstrativeUnitsCityFrom = [];
        //         vm.adminstrativeUnitsCityFrom.push(administrativeUnit.parentDto);
        //         vm.adminstrativeUnits_DistFrom = [];
        //         if (vm.adminstrativeUnits_DistFrom == null)
        //             vm.adminstrativeUnits_DistFrom = [];
        //         vm.adminstrativeUnits_DistFrom.push(administrativeUnit);

        //         service.getAllByParentId(vm.searchDtoFarm.province).then(function (data) {
        //             if (data != null && data.length > 0) {
        //                 vm.adminstrativeUnits_DistFrom = data;
        //             }
        //         });
        //         service.getAllByParentId(vm.searchDtoFarm.district).then(function (data) {
        //             if (data != null && data.length > 0) {
        //                 vm.adminstrativeUnits_WardFrom = data;
        //             }
        //         });

        //     } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
        //         vm.searchDtoFarm.ward = administrativeUnit.id;
        //         vm.searchDtoFarm.district = administrativeUnit.parentDto.id;
        //         vm.searchDtoFarm.province = administrativeUnit.parentDto.parentDto.id;
        //         vm.selectedCityFrom = administrativeUnit.parentDto.parentDto.id;
        //         vm.forestProduct.administrativeUnit = administrativeUnit.parentDto.id;
        //         vm.forestProduct.communeFrom = administrativeUnit.id;
        //         if (vm.adminstrativeUnitsCityFrom == null) vm.adminstrativeUnitsCityFrom = [];
        //         vm.adminstrativeUnitsCityFrom.push(administrativeUnit.parentDto.parentDto);
        //         if (vm.adminstrativeUnits_DistFrom == null) vm.adminstrativeUnits_DistFrom = [];
        //         vm.adminstrativeUnits_DistFrom.push(administrativeUnit.parentDto);
        //         if (vm.adminstrativeUnits_WardFrom == null)
        //             vm.adminstrativeUnits_WardFrom = [];
        //         vm.adminstrativeUnits_WardFrom.push(administrativeUnit);
        //     }
        //     if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') { //trường hợp tài khoản nông dân
        //         //gán tên cơ sở chăn nuôi
        //         vm.farmCode = vm.user.username;
        //         vm.searchDtoFarm.nameOrCode = vm.user.username;
        //     }
        //     vm.findByFarm();
        // }


        vm.generate = function () {
            if (vm.currentId) { } else {
                let date = new Date();

                vm.importAnimal.dateImport = { year: date.getFullYear(), month: date.getMonth(), day: date.getDate() };
                //vm.getBatchCode();
            }
        }
        vm.generate();

        animalService.getAll().then(function (data) {
            vm.animals = data;
        }).catch(function (error) {
            vm.animals = [];
        });

        vm.editInjectionPlants = function (index) {
            vm.indexInjectionPlant = index;
            vm.injectionPlant = JSON.parse(JSON.stringify(vm.injectionPlants[vm.indexInjectionPlant]));
            vm.injectionPlant.isNew = false;
            injectionTimeService.getAllInjectionTimeDto().then(function (data) {
                vm.injectionTimes = data;
            });
            vm.modalInjectionPlant = modal.open({
                animation: true,
                templateUrl: 'injection_plant_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.confirmDeleteImportAnimal = function () {
            vm.injectionPlants.splice(vm.indexInjectionPlant, 1);
            vm.modalInjectionPlant.close();
        }
        vm.injectionTimes = [];
        vm.addInjectionPlant = function () {
            vm.indexInjectionPlant = -1;
            let date = new Date();
            vm.injectionPlant = { isNew: true, injectionDate: { year: date.getFullYear(), month: date.getMonth(), day: date.getDate() } };

            vm.modalInjectionPlant = modal.open({
                animation: true,
                templateUrl: 'injection_plant_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        vm.saveInjectionPlant = function () {
            if (vm.validateAddInjectionPlant()) {
                vm.injectionPlant.injectionRound = vm.injectionPlant.injectionTime.name;
                if (vm.indexInjectionPlant == -1) {
                    vm.injectionPlants.push(vm.injectionPlant);
                } else {
                    vm.injectionPlants[vm.indexInjectionPlant] = vm.injectionPlant;
                }
                vm.modalInjectionPlant.close();
                console.log(vm.injectionPlants);
            }
        }
        vm.validateAddInjectionPlant = function () {
            return true;
        }

        vm.onFarmSelected = function (item) {
            vm.importAnimal.farm = vm.farmSelected;
        };

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
            if (vm.isRole) { //trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
                vm.findByFarm();
            } else { // trường hợp này thì phân quyền cơ sở chăn nuôi theo user
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
            if (angular.isUndefined(vm.selectedFarms) || vm.selectedFarms.length == 0) {
                toastr.warning($translate.instant("exportAnimal.notChooseFarm"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.handleAddressCreatedFpl = function (ward) {
            if (ward.includes("Thị trấn")) {
                vm.forestProduct.addressCreatedFpl = ward.substring(9, ward.length);
            } else if (ward.includes("Phường")) {
                vm.forestProduct.addressCreatedFpl = ward.substring(7, ward.length);
            } else {
                vm.forestProduct.addressCreatedFpl = ward.substring(3, ward.length);
            }
        }
        vm.handleAddressConfirm = function (city) {
            if (city.includes("Thành phố")) {
                vm.forestProduct.addressConfirm = city.substring(10, city.length);
            } else {
                vm.forestProduct.addressConfirm = city.substring(5, city.length);
            }
        }
        vm.agreeFarm = function () {
            if (checkAgreeFarm()) {
                vm.farmSelected = vm.selectedFarms[0];
                vm.farm = vm.selectedFarms[0];
                vm.farmName = vm.farm.name;
                vm.forestProduct.farm = vm.farm;
                vm.forestProduct.provinceName = "Sở nông nghiệp và PTNTT " + vm.farm.administrativeUnit.parentDto.parentDto.name;
                // if (vm.forestProduct.organizationName == null) {
                //     service.getCurrentUser().then(function (data) {
                //         vm.idUserAttachments = data.id;
                //         service.getUserLogined(vm.idUserAttachments).then(function (data) {
                //             vm.forestProduct.organizationName = data[0].organizationName
                //         })
                //     })
                // }
                if (vm.forestProduct.farm.administrativeUnit != null) {
                    if (vm.forestProduct.farm.administrativeUnit != null) {
                        vm.forestProduct.communeFrom = vm.forestProduct.farm.administrativeUnit;
                        vm.handleAddressCreatedFpl(vm.forestProduct.farm.administrativeUnit.name);
                    }
                    if (vm.forestProduct.farm.administrativeUnit.parentDto != null) {
                        vm.forestProduct.administrativeUnitFrom = vm.forestProduct.farm.administrativeUnit.parentDto;
                    }
                    if (vm.forestProduct.farm.administrativeUnit.parentDto.parentDto != null) {
                        vm.selectedCityFrom = vm.forestProduct.farm.administrativeUnit.parentDto.parentDto;
                        vm.handleAddressConfirm(vm.forestProduct.farm.administrativeUnit.parentDto.parentDto.name);
                    }
                    if (vm.forestProduct.farm.ownerName != null) {
                        vm.forestProduct.organizationCreatedFplName = vm.forestProduct.farm.ownerName;
                    }

                    service.getChildrenByParentId(vm.selectedCityFrom.id).then(function (data) {
                        if (data != null && data.length > 0) {
                            vm.adminstrativeUnits_DistFrom = data;
                        }
                    })
                        ;
                }
                if (vm.forestProduct.farm.village != null) {
                    vm.forestProduct.villageFrom = vm.forestProduct.farm.village;
                }
                if (vm.forestProduct.farm.phoneNumber != null) {
                    vm.forestProduct.farm.phoneNumber = vm.forestProduct.farm.phoneNumber;
                }
                //
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

        vm.bsTableControl = {
            options: {
                data: vm.forestProductList,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: true,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSize,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinition(),
                onCheck: function (row, $element) {
                    if (vm.forestProductListSelected != null) {
                        vm.forestProductListSelected = [];
                    }
                    $scope.$apply(function () {
                        vm.forestProductListSelected.push(row);
                    });
                },

                onUncheck: function (row, $element) {
                    if (vm.forestProductListSelected != null) {
                        vm.forestProductListSelected = [];
                    }
                },

                onPageChange: function (index, pageSize) {
                    vm.pageSizeFarm = pageSize;
                    vm.pageIndexFarm = index;
                    vm.pageIndex = index;
                    vm.pageSize = pageSize;
                    vm.getPageForestProductList();
                }
            }
        };



        vm.enterSearchFarm = function () {
            if (event.keyCode == 13) { //Phím Enter

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
            } else {
                vm.searchDtoFarm.nameOrCode = null;
            }
            service.getPageSearchFarm(vm.searchDtoFarm, vm.pageIndexFarm, vm.pageSizeFarm).then(function (data) {
                vm.farms = data.content;
                vm.bsTableControlFarm.options.data = vm.farms;
                vm.bsTableControlFarm.options.totalRows = data.totalElements;
                if (vm.isFamer == true && vm.farms != null && vm.farms.length > 0) {
                    if (vm.importAnimal == null) vm.importAnimal = {};
                    vm.farmName = vm.farms[0].name;
                    vm.farmCode = vm.farms[0].code;
                    vm.importAnimal.farm = vm.farms[0];
                }
            });
        }

        vm.forestProduct = {};
        vm.modalPopupAddEditForestProductList = null;
        vm.modalConfirmDelete = null;
        vm.CityFrom = null;
        vm.CityTo = null;
        vm.viewForestProductList = function (id) {
            if (id) {
                service.getForestProductListById(id).then(function (data) {
                    vm.forestProduct = data;
                    vm.forestProduct.isNew = false;
                    vm.forestProduct.isCheckFarm = true;
                    vm.listForesProductDetail = data.details;
                    vm.modalPopupAddEditForestProductList = modal.open({
                        animation: true,
                        templateUrl: 'detail_forest.html',
                        scope: $scope,
                        backdrop: 'static',
                        keyboard: false,
                        size: 'lg'
                    });
                });
            }
        }
        //tran huu dat them ham them bang ke lam san start

        vm.addForestProductList = function (id, index) {
            //debugger
            vm.allOriginal = [];
            vm.adminstrativeUnitsCity = [];

            service.getAdminstrativeUnitsCity().then(function (data) {
                vm.adminstrativeUnitsCity = data;
            });
            service.getAllOriginal().then(function (data) {
                vm.allOriginal = data;
                console.log(data);
            });
            // if (vm.forestProduct.organizationName == null) {
            //     service.getCurrentUser().then(function (data) {
            //         vm.idUserAttachments = data.id;
            //         service.getUserLogined(vm.idUserAttachments).then(function (data) {
            //             if(data[0].organizationName){
            //                 vm.forestProduct.organizationName = data[0].organizationName
            //             }
            //         })
            //     })
            // }
            if (index == 2) {
                vm.isViewDetail = true;
            } else {
                vm.isViewDetail = false;
            }
            if (id) {
                service.getForestProductListById(id).then(function (data) {
                    vm.forestProduct = data;
                    vm.forestProduct.isNew = false;
                    vm.forestProduct.isCheckFarm = true;
                    vm.isEdit = true;
                    if (data.administrativeUnitTo) {
                        vm.CityTo = data.administrativeUnitTo.parentDto.name;
                    }
                    if (data.administrativeUnitFrom) {
                        vm.CityFrom = data.administrativeUnitFrom.parentDto.name;
                    }

                    vm.selectedCityFrom = data.farm.administrativeUnit.parentDto.parentDto.name;
                    vm.forestProduct.administrativeUnitFrom = data.farm.administrativeUnit.parentDto;
                    vm.forestProduct.communeFrom = data.farm.administrativeUnit;
                    getDataByComboboxFrom(vm.forestProduct.administrativeUnitFrom);
                    getDataByComboboxTo(vm.forestProduct.administrativeUnitTo);
                    console.log(data);
                    if (data.details) {
                        vm.listForesProductDetail = data.details;
                    } else {
                        vm.listForesProductDetail = [];
                    }
                    if (!vm.forestProduct.exports) {
                        vm.forestProduct.exports = [];
                    }
                });
            } else {
                vm.isEdit = false;
                vm.selectedCityFrom = null;
                vm.selectedCityTo = null;
                vm.forestProduct = {};
                vm.forestProduct.isNew = true;
                vm.forestProduct.isCheckFarm = true;
                vm.forestProduct.dateIssue = new Date();
                vm.forestProduct.exports = [];
                vm.listForesProductDetail = [];
                vm.idFPLNew = null;
                //getAllCity();//tran huu dat lay full city
            }
            vm.modalPopupAddEditForestProductList = modal.open({
                animation: true,
                templateUrl: 'detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                windowClass: "customer-modal-lg",
                size: 'lg'
            });
        }
        const counts = {};
        vm.addForestProductDetail = function (detail) {
            //debugger
            var listFPDetail = vm.listForesProductDetail;
            vm.forestProductDetail = [];
            if (!vm.forestProduct.farm) {
                toastr.warning("Vui lòng chọn cơ sở nuôi ", "Thông báo");
                return;
            }
            counts.farmId = vm.forestProduct.farm.id;
            service.getListAnimalFromReportForm16BySearch(counts).then(function (data) {
                vm.animals2 = data;
            }).catch(function (error) {
                vm.animals2 = [];
            });
            if (detail) {
                vm.forestProductDetail = angular.copy(detail);
                reportPeriodService.getLastRecordReportPeriodByFarmAndAnimal({ animalId: vm.forestProductDetail.animal.id, farmId: vm.forestProduct.farm.id }).then((data) => {
                    if (data && data.reportItems) {
                        if (data.reportItems.length > 1) {
                            data.reportItems = data.reportItems.filter((e) => e.animal.id == animal.id);
                        }
                        vm.forestProductDetail.reportForm16Old = data.reportItems[0];
                        service.getDetailsByStatusFP({ skipFPLD: vm.forestProductDetail.id, animalId: vm.forestProductDetail.animal.id, farmId: vm.forestProduct.farm.id }).then((data) => {
                            let fpld = data;
                            if (fpld.length > 0) {
                                for (let i = 0; i < fpld.length; i++) {
                                    if (vm.forestProductDetail.reportForm16Old.total != null) {
                                        if (fpld[i].total != null) {
                                            vm.forestProductDetail.reportForm16Old.total -= fpld[i].total;
                                        }

                                    }
                                    if (vm.forestProductDetail.reportForm16Old.male != null) {
                                        if (fpld[i].male != null) {
                                            vm.forestProductDetail.reportForm16Old.male -= fpld[i].male;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.female != null) {
                                        if (fpld[i].female != null) {
                                            vm.forestProductDetail.reportForm16Old.female -= fpld[i].female;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.unGender != null) {
                                        if (fpld[i].unGender != null) {
                                            vm.forestProductDetail.reportForm16Old.unGender -= fpld[i].unGender;
                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.maleParent != null) {
                                        if (fpld[i].maleParent != null) {
                                            vm.forestProductDetail.reportForm16Old.maleParent -= fpld[i].maleParent;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.femaleParent != null) {
                                        if (fpld[i].femaleParent != null) {
                                            vm.forestProductDetail.reportForm16Old.femaleParent -= fpld[i].femaleParent;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.unGenderParent != null) {
                                        if (fpld[i].unGenderParent != null) {
                                            vm.forestProductDetail.reportForm16Old.unGenderParent -= fpld[i].unGenderParent;

                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.maleGilts != null) {
                                        if (fpld[i].maleGilts != null) {
                                            vm.forestProductDetail.reportForm16Old.maleGilts -= fpld[i].maleGilts;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.femaleGilts != null) {
                                        if (fpld[i].femaleGilts != null) {
                                            vm.forestProductDetail.reportForm16Old.femaleGilts -= fpld[i].femaleGilts;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.unGenderGilts != null) {
                                        if (fpld[i].unGenderGilts != null) {
                                            vm.forestProductDetail.reportForm16Old.unGenderGilts -= fpld[i].unGenderGilts;
                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.maleChildUnder1YearOld != null) {
                                        if (fpld[i].maleChildUnder1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.maleChildUnder1YearOld -= fpld[i].maleChildUnder1YearOld;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.femaleChildUnder1YearOld != null) {
                                        if (fpld[i].femaleChildUnder1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.femaleChildUnder1YearOld -= fpld[i].femaleChildUnder1YearOld;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.childUnder1YearOld != null) {
                                        if (fpld[i].childUnder1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.childUnder1YearOld -= fpld[i].childUnder1YearOld;
                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.maleChildOver1YearOld != null) {
                                        if (fpld[i].maleChildOver1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.maleChildOver1YearOld -= fpld[i].maleChildOver1YearOld;
                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.femaleChildOver1YearOld != null) {
                                        if (fpld[i].femaleChildOver1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.femaleChildOver1YearOld -= fpld[i].femaleChildOver1YearOld;
                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.unGenderChildOver1YearOld != null) {
                                        if (fpld[i].unGenderChildOver1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.unGenderChildOver1YearOld -= fpld[i].unGenderChildOver1YearOld;
                                        }
                                    }
                                }
                            }
                        });
                    }
                });
                vm.forestProductDetail.isNew = false;
            } else {
                vm.forestProductDetail = {};
                vm.forestProductDetail.isNew = true;
            }
            vm.modalPopupAddEditForestDetail = modal.open({
                animation: true,
                templateUrl: 'forest_product_list_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'lg'
            });
            vm.forestProductDetail.unit = "Cá thể";
        }

        vm.saveForestProductListDetail = function () {
            if (!vm.forestProductDetail.animal) {
                toastr.warning("Chưa chọn loài", "Cảnh báo");
                return;
            }
            if (vm.forestProductDetail.isNew) {
                //debugger
                vm.listForesProductDetail.push(vm.forestProductDetail);
                vm.modalPopupAddEditForestDetail.close();
            } else if (!vm.forestProductDetail.isNew) {
                for (let i = 0; i < vm.listForesProductDetail.length; i++) {
                    var fpd1 = parseInt(vm.listForesProductDetail[i].animal.id);
                    var fpd2 = parseInt(vm.forestProductDetail.animal.id);
                    if (fpd1 == fpd2) {
                        //debugger
                        vm.listForesProductDetail[i] = vm.forestProductDetail;
                    }

                }
                vm.modalPopupAddEditForestDetail.close();
            }
        }

        vm.saveForestProductList = function () {
            //debugger;
            if (!vm.forestProduct.farm) {
                toastr.warning("Chưa chọn cơ sở nuôi", "Cảnh báo");
                return;
            } else if (!vm.forestProduct.code) {
                toastr.warning("Chưa nhập Số bản kê lâm sản", "Cảnh báo");
                return;
            } else if (vm.listForesProductDetail.length <= 0) {
                toastr.warning("Chưa kê khai động vật", "Cảnh báo");
                return;
            } else if (vm.forestProduct.transportDuration < 0) {
                toastr.warning("Thời gian vận chuyển chưa hợp lý", "Cảnh báo");
                return;
            } else {
                if (vm.selectedCityFrom != null) {
                    vm.forestProduct.provinceIdFrom = vm.selectedCityFrom.id;
                    vm.forestProduct.provinceCodeFrom = vm.selectedCityFrom.code;
                }
                if (vm.selectedCityTo != null) {
                    vm.forestProduct.provinceIdTo = vm.selectedCityTo.id;
                    vm.forestProduct.provinceCodeTo = vm.selectedCityTo.code;
                }
                vm.forestProduct.canceled = 2;
                vm.forestProduct.details = vm.listForesProductDetail;
                if (vm.idFPLNew) {
                    vm.forestProduct.id = vm.idFPLNew;
                }
                service.saveForestProductList(vm.forestProduct).then(function (data) {
                    // vm.modalPopupAddEditForestProductList.close();
                    toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                    if (data) {
                        vm.forestProduct = data;
                    }
                    vm.selectedCityFrom = data.farm.administrativeUnit.parentDto.parentDto;
                    vm.forestProduct.administrativeUnitFrom = data.farm.administrativeUnit.parentDto;
                    vm.forestProduct.communeFrom = data.farm.administrativeUnit;
                    if (vm.forestProduct.isCheckFarm == false) {
                        vm.forestProduct.farm.village = data.farm.village;
                        vm.forestProduct.farm.adressDetail = data.farm.adressDetail;
                        vm.forestProduct.farm.phoneNumber = data.farm.phoneNumber;
                        vm.forestProduct.farm.businessRegistrationNumber = data.farm.businessRegistrationNumber;
                        vm.forestProduct.farm.newRegistrationCode = data.farm.newRegistrationCode;
                    }
                    vm.getPageForestProductList();
                    // if(data.id){
                    //     vm.forestProduct.id = data.id;
                    //     vm.idFPLNew = data.id;
                    // }
                });
            }
        }
        vm.confirmDeleteForestProductList = function (id) {
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDelete.result.then(function (confirm) {
                if (confirm == 'yes') {
                    service.deletePageForestProductList(id).then(function () {
                        toastr.info("Xóa bản ghi thành công", "Thông báo");
                        vm.getPageForestProductList();
                    });
                }
            });
        }

        // vm.saveForestProductList = function(){
        //     if(!vm.forestProduct.farm){
        //         toastr.warning("Chưa chọn cơ sở nuôi", "Cảnh báo");
        //         return;
        //     }else if(!vm.forestProduct.code){
        //         toastr.warning("Chưa nhập số hiệu bản kê", "Cảnh báo");
        //         return;
        //     } else{
        //         vm.forestProduct.canceled=2;
        //         vm.forestProduct.details = vm.listForesProductDetail;
        //         service.saveForestProductList(vm.forestProduct).then(function(data){
        //             vm.modalPopupAddEditForestProductList.close();
        //             vm.getPageForestProductList();
        //         });
        //     }
        // }

        vm.getPageForestProductList = function () {
            vm.searchDtoForestProductList = {};
            vm.searchDtoForestProductList.fromDate = vm.fromDate ? vm.fromDate : null;
            vm.searchDtoForestProductList.toDate = vm.toDate ? vm.toDate : null;
            vm.searchDtoForestProductList.nameOrCode = vm.nameOrCode ? vm.nameOrCode : null;
            service.getPageForestProductList(vm.searchDtoForestProductList, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.forestProductList = data.content;
                vm.bsTableControl.options.data = vm.forestProductList;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        vm.getPageForestProductList();

        vm.modalPopupAddEditForestDetail = null;
        vm.addEditForestDetailIndex = -1;
        vm.forestProductDetail = {};
        vm.forestProductList = [];
        vm.listForesProductDetail = [];
        vm.modalConfirmDeleteInjectionPlants = null;
        vm.confirmDeleteForestDetail = function (index) {
            vm.modalConfirmDeleteInjectionPlants = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_forest_detail.html',
                scope: $scope,
                // backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDeleteInjectionPlants.result.then(function (confirm) {
                if (confirm == 'yes') {
                    vm.listForesProductDetail.splice(index, 1);
                }
            })
        }


        //--------------Popup Batch Code----------//
        vm.pageIndexBatchCode = 1, vm.pageSizeBatchCode = 10;
        vm.subModalInstance = {};

        vm.selectedBatchCodes = [];
        vm.batchCodeSelected = {};
        vm.listExportAnimalSelected = [];
        vm.showPopupBatchCode = function () {

            if (!vm.forestProduct.farm) {
                toastr.warning($translate.instant("exportAnimal.mustSelectFarm"), $translate.instant("toastr.warning"));
                return;
            }
            vm.subModalInstance = modal.open({
                animation: true,
                templateUrl: 'choose_batch_code_modal.html',
                scope: $scope,
                size: 'lg'
            });
            vm.code = null;
            vm.listExportAnimalSelected = [];
            vm.selectedBatchCodes = [];
            vm.batchCodeSelected = {};
            vm.findBy();

            vm.subModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {

                }
            }, function () {
                vm.batchCode = null;
            });
        }

        var closeModal = function () {

            vm.subModalInstance.close();

        };

        function checkAgree() {
            if (!vm.listExportAnimalSelected || vm.listExportAnimalSelected.length == 0) {
                toastr.warning($translate.instant("exportAnimal.notChooseBatchCode"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.agree = function () {
            if (checkAgree()) {
                let listId = [];
                if (vm.forestProduct.exports && vm.forestProduct.exports.length > 0) {
                    listId = vm.forestProduct.exports.map(item => item.id);
                }
                vm.listExportAnimalSelected.forEach(function (item) {
                    if (listId.indexOf(item.id) == -1) {
                        vm.forestProduct.exports.push(item);
                    }
                });
                vm.listExportAnimalSelected = [];
                closeModal();
            }
        }

        vm.enterSearchTeacher = function (e) {
            if (event.keyCode == 13) { //Phím Enter
                vm.searchTeachers();
            }
        }
        vm.bsTableControlCode = {
            options: {
                data: [],
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: false,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeBatchCode,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionCode(),

                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.listExportAnimalSelected.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        angular.forEach(rows, function (row) {
                            vm.listExportAnimalSelected.push(row);
                        });
                        // vm.listExportAnimalSelected = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.listExportAnimalSelected);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.listExportAnimalSelected.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.listExportAnimalSelected = [];
                    });
                },

                onPageChange: function (index, pageSize) {
                    vm.pageSizeBatchCode = pageSize;
                    vm.pageIndexBatchCode = index;
                    //vm.searchTeachers();
                    vm.findBy();
                }
            }
        };



        vm.enterSearchCode = function () {
            if (event.keyCode == 13) { //Phím Enter

                vm.searchByCode();
            }
        };
        vm.searchByCode = function () {
            vm.pageIndexBatchCode = 1;
            vm.bsTableControlCode.state.pageNumber = 1;
            vm.findBy();
        };
        vm.findBy = function () {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (vm.batchCode != null && vm.batchCode != "") {
                vm.searchDto.nameOrCode = vm.batchCode;
            } else {
                vm.searchDto.nameOrCode = null;
            }
            if (vm.forestProduct != null && vm.forestProduct.farm != null && vm.forestProduct.farm.id != null) {
                vm.searchDto.farmId = vm.forestProduct.farm.id;
            } else
                vm.searchDto.farmId = null;
            vm.searchDto.type = -1;
            service.getPageSearchImportAnimal(vm.searchDto, vm.pageIndexBatchCode, vm.pageSizeBatchCode).then(function (data) {
                vm.importAnimals = data.content;
                vm.bsTableControlCode.options.data = vm.importAnimals;
                vm.bsTableControlCode.options.totalRows = data.totalElements;
            });
        }

        vm.confirmDeleteListExportAnimal = function (index) {
            vm.modalConfirmDeleteInjectionPlants = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_forest_detail.html',
                scope: $scope,
                // backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDeleteInjectionPlants.result.then(function (confirm) {
                if (confirm == 'yes') {
                    if (vm.forestProduct.exports && vm.forestProduct.exports.length > 0) {
                        if (vm.forestProduct.exports[index] && vm.forestProduct.exports[index].forestProductsList && vm.forestProduct.exports[index].forestProductsList.id) {
                            service.deleteLinkedByExportAnimalId(vm.forestProduct.exports[index].forestProductsList.id).then(function (data) {
                                console.log(data);
                            });
                        }
                        vm.forestProduct.exports.splice(index, 1);
                    }
                }
            })
        }

        vm.confirmDeleteRecordsExport = function (index) {
            vm.modalConfirmDeleteInjectionPlants = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_forest_detail.html',
                scope: $scope,
                // backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDeleteInjectionPlants.result.then(function (confirm) {
                if (confirm == 'yes') {
                    vm.forestProduct.exports.splice(index, 1);
                }
            })
        }

        vm.searchByCodeForestProductList = function () {
            vm.pageIndex = 1;
            vm.getPageForestProductList();
        }

        vm.enterSearchForestProductList = function (event) {
            if (event.keyCode == 13) {
                vm.pageIndex = 1;
                vm.getPageForestProductList();
            }
        }
        vm.onChangFromDateToDate = function () {
            vm.pageIndex = 1;
            vm.getPageForestProductList();
        }

        vm.print = function () {
            var innerContents = document.getElementById('divPrint').innerHTML;
            var popupWinindow = window.open('', '_blank', 'width=1200,height=900,scrollbars=yes,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
            popupWinindow.document.open();
            popupWinindow.document.write('<html><head><link href="assets/css/custom.css" rel="stylesheet" type="text/css"><link href="assets/css/external/select.min.css" rel="stylesheet" type="text/css"><link href="assets/css/external/bootstrap.min.css" rel="stylesheet" type="text/css"></head><body onload="window.print()">' + innerContents + '</body></html>');
            popupWinindow.document.close();
        }

        //tran huu dat ham xuat file word start
        vm.myBlobObjectWord = undefined;
        vm.getFileWord = function (id) {
            service.getForestProductListById(id).then(function (data) {
                if (data) {
                    vm.farmName = data.farm.name;
                    vm.FPLCode = data.code;
                }
            })
            var object = {};
            object.id = id;
            console.log('download started, you can show a wating animation');
            service.getStreamDocument(object)
                .then(function (data) { //is important that the data was returned as Aray Buffer
                    console.log('Stream download complete, stop animation!');
                    //vm.myBlobObjectWord = new Blob([data], { type: 'application/msword' });
                    var file = new Blob([data], { type: 'application/msword' });
                    var fileName = "BKLS_" + (vm.farmName ? (vm.farmName + "_") : "") + (vm.FPLCode ? vm.FPLCode : "") + ".doc";
                    FileSaver.saveAs(file, fileName);
                    // FileSaver.saveAs(file, "BangKeLamSan.doc");
                }, function (fail) {
                    console.log('Download Error, stop animation and show error message');
                    vm.myBlobObjectWord = [];
                });

        };

        //tran huu dat ham xuat file word end

        //tran huu dat start thêm hàm lưu hình ảnh
        vm.uploadFile = null;
        vm.forestProductfile = null;
        vm.uploadFilesForest = function (file, errFile) {
            // debugger
            $scope.uploadFile = file;
            if ($scope.uploadFile != null) {
                Uploader.upload({
                    url: settings.api.baseUrl + 'api/file/uploadArticleImg',
                    method: 'POST',
                    data: { uploadfile: $scope.uploadFile }
                }).then(function (successResponse) {
                    var file = successResponse.data;
                    if (file) {
                        //vm.forestProductfile=file;
                        vm.forestProduct.file = file
                    }

                }, function (errorResponse) {
                    toastr.error('Error submitting data...', 'Error');
                });
            }
        }
        //tran huu dat end thêm hàm lưu hình ảnh

        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            vm.adminstrativeUnitsCityFrom = [];
            if (city) {
                vm.searchDtoFarm.province = city.id;
            } else {
                vm.searchDtoFarm.province = null;
            }
            vm.searchDto.district = null;
            vm.searchDto.ward = null;
            vm.searchDtoFarm.district = null;
            vm.searchDtoFarm.ward = null;
            vm.bsTableControlFarm.state.pageNumber = 1;

            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    else {
                        vm.adminstrativeUnits_Dist = [];
                        vm.adminstrativeUnits_Wards = [];
                    }
                });
            }
            else {
                vm.searchDto.district = null;
                vm.searchDto.ward = null;
                vm.selectedDist = null;
                vm.selectWards = null;
                vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Wards = [];
            }
            vm.findByFarm();
        }
        vm.onFmsadminstrativeUnitDistSelected = function (dist) {
            if (dist) {
                vm.searchDtoFarm.district = dist.id;
            } else {
                vm.searchDtoFarm.district = null;
            }
            vm.searchDto.ward = null;
            vm.searchDtoFarm.ward = null;
            vm.bsTableControlFarm.state.pageNumber = 1;
            if (dist != null && dist.id != null) {
                service.getAllByParentId(dist.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                    else {
                        vm.searchDtoFarm.ward = null;
                        vm.adminstrativeUnits_Wards = [];
                    }
                });
            }
            else {
                vm.adminstrativeUnits_Wards = [];
            }
            vm.findByFarm();
        }
        vm.onFmsadminstrativeUnitWardsSelected = function (item) {
            if (item) {
                vm.searchDtoFarm.ward = item.id;
            } else {
                vm.searchDtoFarm.ward = null;
            }
            // vm.pageIndex = 1;
            vm.bsTableControlFarm.state.pageNumber = 1;
            vm.findByFarm();
        }

        // vm.onFmsadminstrativeUnitCitySelectedFrom = function (city) {
        //     vm.adminstrativeUnitsCityFrom = [];
        //     if (city != null && city.id != null) {
        //         service.getChildrenByParentId(city.id).then(function (data) {
        //             if (data != null && data.length > 0) {
        //                 vm.adminstrativeUnits_DistFrom = data;
        //                 vm.forestProduct.administrativeUnitFrom = null;
        //                 vm.forestProduct.communeFrom = null;
        //             } else {
        //                 vm.forestProduct.administrativeUnitFrom = null;
        //                 vm.adminstrativeUnits_DistFrom = [];
        //                 vm.forestProduct.communeFrom = null;
        //             }
        //         });
        //     } else {
        //         vm.forestProduct.administrativeUnitFrom = null;
        //         vm.forestProduct.communeFrom = null;
        //         vm.adminstrativeUnits_DistFrom = [];
        //     }
        // }
        vm.onFmsadminstrativeUnitCitySelectedFrom = function (city) {
            // debugger
            // vm.adminstrativeUnitsCityFrom = [];
            if (city) {
                vm.selectedCityFrom = city;
            } else {
                vm.selectedCityFrom = null;
            }
            vm.forestProduct.administrativeUnitFrom = null;
            vm.forestProduct.communeFrom = null;
            // vm.bsTableControlFarm.state.pageNumber = 1;

            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_DistFrom = data;
                    }
                    else {
                        vm.adminstrativeUnits_DistFrom = [];
                        vm.adminstrativeUnits_WardFrom = [];
                    }
                });
            }
            else {
                // vm.searchDto.district = null;
                // vm.searchDto.ward = null;
                // vm.selectedDist = null;
                // vm.selectWards = null;
                vm.adminstrativeUnits_DistFrom = [];
                vm.adminstrativeUnits_WardFrom = [];
            }
            vm.findByFarm();
        }

        // vm.onFmsadminstrativeUnitDistrictSelectedFrom = function (district) {
        //     if (district != null && district.id != null) {
        //         service.getChildrenByParentId(district.id).then(function (data) {
        //             if (data != null && data.length > 0) {
        //                 vm.adminstrativeUnits_WardFrom = data;
        //                 vm.forestProduct.communeFrom = null;
        //             } else {
        //                 vm.forestProduct.communeFrom = null;
        //                 vm.adminstrativeUnits_WardFrom = [];
        //             }
        //         });
        //     } else {
        //         vm.forestProduct.communeFrom = null;
        //         vm.adminstrativeUnits_WardFrom = [];
        //     }
        // }


        vm.onFmsadminstrativeUnitDistrictSelectedFrom = function (district) {
            if (district) {
                // vm.searchDtoFarm.district = district.id;
                vm.forestProduct.administrativeUnitFrom = district;
            } else {
                vm.forestProduct.administrativeUnitFrom = null;
            }
            vm.forestProduct.communeFrom = null;
            // vm.bsTableControlFarm.state.pageNumber = 1;
            if (district != null && district.id != null) {
                service.getAllByParentId(district.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_WardFrom = data;
                    }
                    else {
                        vm.forestProduct.communeFrom = null;
                        vm.adminstrativeUnits_WardFrom = [];
                    }
                });
            }
            else {
                vm.adminstrativeUnits_WardFrom = [];
            }
            vm.findByFarm();
        }


        // vm.onFmsadminstrativeUnitCitySelectedTo = function (item) {
        //     if (item) {
        //         vm.searchDtoFarm.ward = item.id;
        //     } else {
        //         vm.searchDtoFarm.ward = null;
        //     }
        //     // vm.pageIndex = 1;
        //     vm.bsTableControlFarm.state.pageNumber = 1;
        //     vm.findByFarm();
        // }

        vm.onFmsadminstrativeUnitCitySelectedTo = function (city) {
            if (city != null && city.id != null) {
                service.getChildrenByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_DistTo = data;
                        vm.forestProduct.administrativeUnitTo = null;
                        vm.forestProduct.communeTo = null;
                    } else {
                        vm.forestProduct.administrativeUnitTo = null;
                        vm.adminstrativeUnits_DistTo = [];
                        vm.forestProduct.communeTo = null;
                    }
                });
            } else {
                vm.forestProduct.administrativeUnitTo = null;
                vm.forestProduct.communeTo = null;
                vm.adminstrativeUnits_DistTo = [];
            }
        }

        vm.onFmsadminstrativeUnitDistrictSelectedTo = function (district) {
            if (district != null && district.id != null) {
                service.getChildrenByParentId(district.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_WardTo = data;
                        vm.forestProduct.communeTo = null;
                    } else {
                        vm.forestProduct.communeTo = null;
                        vm.adminstrativeUnits_WardTo = [];
                    }
                });
            } else {
                vm.forestProduct.communeTo = null;
                vm.adminstrativeUnits_WardTo = [];
            }
        }

        function getDataByComboboxFrom(Wards) {
            //debugger
            if (Wards != null) {
                if (Wards.parentDto != null) {
                    vm.selectedCityFrom = Wards.parentDto;
                    vm.forestProduct.administrativeUnitFrom = Wards;
                } else {
                    vm.selectedCityFrom = Wards;
                }
                service.getChildrenByParentId(vm.forestProduct.administrativeUnitFrom.id).then(function (data) {
                    //debugger
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_WardFrom = data;
                    }
                });
            }
        }

        function getDataByComboboxTo(Wards) {
            if (Wards != null) {
                if (Wards.parentDto != null) {
                    vm.selectedCityTo = Wards.parentDto;
                } else {
                    vm.selectedCityTo = Wards;
                }
                service.getChildrenByParentId(vm.selectedCityTo.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_DistTo = data;
                    }
                });
            }
        }
        vm.totalAnimal;
        vm.forestProductDetail = [];
        vm.reportForm16A = [];
        vm.onSelectedAnimal = function (animal) {
            if (animal != null && vm.forestProduct.farm) {
                let check = vm.listForesProductDetail.filter(e => e.animal.id === animal.id);
                if (check && check.length > 0 && vm.forestProductDetail.isNew) {
                    toastr.warning("Loài này đã được khai báo ở bản kê lâm sản này", "Thông báo");
                    return;
                }
                // tìm bản ghi mẫu 16 gần nhất của loài
                reportPeriodService.getLastRecordReportPeriodByFarmAndAnimal({ animalId: animal.id, farmId: vm.forestProduct.farm.id }).then((data) => {
                    if (data && data.reportItems) {
                        if (data.reportItems.length > 1) {
                            data.reportItems = data.reportItems.filter((e) => e.animal.id == animal.id);
                        }
                        vm.forestProductDetail.reportForm16Old = data.reportItems[0];
                        service.getDetailsByStatusFP({ animalId: animal.id, farmId: vm.forestProduct.farm.id }).then((data) => {
                            let fpld = data;
                            if (fpld.length > 0) {
                                for (let i = 0; i < fpld.length; i++) {
                                    if (vm.forestProductDetail.reportForm16Old.total != null) {
                                        if (fpld[i].total != null) {
                                            vm.forestProductDetail.reportForm16Old.total -= fpld[i].total;
                                        }

                                    }
                                    if (vm.forestProductDetail.reportForm16Old.male != null) {
                                        if (fpld[i].male != null) {
                                            vm.forestProductDetail.reportForm16Old.male -= fpld[i].male;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.female != null) {
                                        if (fpld[i].female != null) {
                                            vm.forestProductDetail.reportForm16Old.female -= fpld[i].female;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.unGender != null) {
                                        if (fpld[i].unGender != null) {
                                            vm.forestProductDetail.reportForm16Old.unGender -= fpld[i].unGender;
                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.maleParent != null) {
                                        if (fpld[i].maleParent != null) {
                                            vm.forestProductDetail.reportForm16Old.maleParent -= fpld[i].maleParent;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.femaleParent != null) {
                                        if (fpld[i].femaleParent != null) {
                                            vm.forestProductDetail.reportForm16Old.femaleParent -= fpld[i].femaleParent;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.unGenderParent != null) {
                                        if (fpld[i].unGenderParent != null) {
                                            vm.forestProductDetail.reportForm16Old.unGenderParent -= fpld[i].unGenderParent;

                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.maleGilts != null) {
                                        if (fpld[i].maleGilts != null) {
                                            vm.forestProductDetail.reportForm16Old.maleGilts -= fpld[i].maleGilts;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.femaleGilts != null) {
                                        if (fpld[i].femaleGilts != null) {
                                            vm.forestProductDetail.reportForm16Old.femaleGilts -= fpld[i].femaleGilts;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.unGenderGilts != null) {
                                        if (fpld[i].unGenderGilts != null) {
                                            vm.forestProductDetail.reportForm16Old.unGenderGilts -= fpld[i].unGenderGilts;
                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.maleChildUnder1YearOld != null) {
                                        if (fpld[i].maleChildUnder1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.maleChildUnder1YearOld -= fpld[i].maleChildUnder1YearOld;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.femaleChildUnder1YearOld != null) {
                                        if (fpld[i].femaleChildUnder1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.femaleChildUnder1YearOld -= fpld[i].femaleChildUnder1YearOld;
                                        }
                                    }
                                    if (vm.forestProductDetail.reportForm16Old.childUnder1YearOld != null) {
                                        if (fpld[i].childUnder1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.childUnder1YearOld -= fpld[i].childUnder1YearOld;
                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.maleChildOver1YearOld != null) {
                                        if (fpld[i].maleChildOver1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.maleChildOver1YearOld -= fpld[i].maleChildOver1YearOld;
                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.femaleChildOver1YearOld != null) {
                                        if (fpld[i].femaleChildOver1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.femaleChildOver1YearOld -= fpld[i].femaleChildOver1YearOld;
                                        }
                                    }

                                    if (vm.forestProductDetail.reportForm16Old.unGenderChildOver1YearOld != null) {
                                        if (fpld[i].unGenderChildOver1YearOld != null) {
                                            vm.forestProductDetail.reportForm16Old.unGenderChildOver1YearOld -= fpld[i].unGenderChildOver1YearOld;
                                        }
                                    }
                                }
                            }
                        });
                    } else {
                        vm.forestProductDetail.reportForm16Old = [];
                    }
                })
            } else {
                if (vm.forestProductDetail.isNew) {
                    vm.forestProductDetail = [];
                    vm.forestProductDetail.isNew = true;
                } else {
                    vm.forestProductDetail = [];
                }
            }
        }

        vm.calcToalReportForm16 = function (...params) {
            let total = 0;
            if (params && params.length > 0) {
                params.forEach(function (item) {
                    if (item) {
                        total += parseInt(item);
                    }
                });
            }
            return total;
        };

        vm.updateTotalForm16 = function (obj) {
            if (vm.forestProductDetail.reportForm16Old) {
                if (obj.maleParent && obj.maleParent > vm.forestProductDetail.reportForm16Old.maleParent) obj.maleParent = vm.forestProductDetail.reportForm16Old.maleParent;
                if (obj.femaleParent && obj.femaleParent > vm.forestProductDetail.reportForm16Old.femaleParent) obj.femaleParent = vm.forestProductDetail.reportForm16Old.femaleParent;
                if (obj.unGenderParent && obj.unGenderParent > vm.forestProductDetail.reportForm16Old.unGenderParent) obj.unGenderParent = vm.forestProductDetail.reportForm16Old.unGenderParent;

                if (obj.maleGilts && obj.maleGilts > vm.forestProductDetail.reportForm16Old.maleGilts) obj.maleGilts = vm.forestProductDetail.reportForm16Old.maleGilts;
                if (obj.femaleGilts && obj.femaleGilts > vm.forestProductDetail.reportForm16Old.femaleGilts) obj.femaleGilts = vm.forestProductDetail.reportForm16Old.femaleGilts;
                if (obj.unGenderGilts && obj.unGenderGilts > vm.forestProductDetail.reportForm16Old.unGenderGilts) obj.unGenderGilts = vm.forestProductDetail.reportForm16Old.unGenderGilts;

                if (obj.maleChildOver1YearOld && obj.maleChildOver1YearOld > vm.forestProductDetail.reportForm16Old.maleChildOver1YearOld) obj.maleChildOver1YearOld = vm.forestProductDetail.reportForm16Old.maleChildOver1YearOld;
                if (obj.femaleChildOver1YearOld && obj.femaleChildOver1YearOld > vm.forestProductDetail.reportForm16Old.femaleChildOver1YearOld) obj.femaleChildOver1YearOld = vm.forestProductDetail.reportForm16Old.femaleChildOver1YearOld;
                if (obj.unGenderChildOver1YearOld && obj.unGenderChildOver1YearOld > vm.forestProductDetail.reportForm16Old.unGenderChildOver1YearOld) obj.unGenderChildOver1YearOld = vm.forestProductDetail.reportForm16Old.unGenderChildOver1YearOld;

                if (obj.maleChildUnder1YearOld && obj.maleChildUnder1YearOld > vm.forestProductDetail.reportForm16Old.maleChildUnder1YearOld) obj.maleChildUnder1YearOld = vm.forestProductDetail.reportForm16Old.maleChildUnder1YearOld;
                if (obj.femaleChildUnder1YearOld && obj.femaleChildUnder1YearOld > vm.forestProductDetail.reportForm16Old.femaleChildUnder1YearOld) obj.femaleChildUnder1YearOld = vm.forestProductDetail.reportForm16Old.femaleChildUnder1YearOld;
                if (obj.childUnder1YearOld && obj.childUnder1YearOld > vm.forestProductDetail.reportForm16Old.childUnder1YearOld) obj.childUnder1YearOld = vm.forestProductDetail.reportForm16Old.childUnder1YearOld;
            }
            if (obj.maleParent)
                obj.total = 0;
            obj.male = 0;
            obj.female = 0;
            obj.unGender = 0;

            obj.male = vm.calcToalReportForm16(
                obj.maleParent,
                obj.maleGilts,
                obj.maleChildOver1YearOld,
                obj.maleChildUnder1YearOld
            );
            obj.female = vm.calcToalReportForm16(
                obj.femaleParent,
                obj.femaleGilts,
                obj.femaleChildOver1YearOld,
                obj.femaleChildUnder1YearOld
            );
            obj.unGender = vm.calcToalReportForm16(
                obj.unGenderParent,
                obj.unGenderGilts,
                obj.childUnder1YearOld,
                obj.unGenderChildOver1YearOld
            );
            obj.total = obj.male + obj.female + obj.unGender;
        };

        vm.updateTotalForm16Back = function (obj) {

            if (obj.maleParentBack && obj.maleParentBack > obj.maleParent) obj.maleParentBack = obj.maleParent;
            if (obj.femaleParentBack && obj.femaleParentBack > obj.femaleParent) obj.femaleParentBack = obj.femaleParent;
            if (obj.unGenderParentBack && obj.unGenderParentBack > obj.unGenderParent) obj.unGenderParentBack = obj.unGenderParent;

            if (obj.maleGiltsBack && obj.maleGiltsBack > obj.maleGilts) obj.maleGiltsBack = obj.maleGilts;
            if (obj.femaleGiltsBack && obj.femaleGiltsBack > obj.femaleGilts) obj.femaleGiltsBack = obj.femaleGilts;
            if (obj.unGenderGiltsBack && obj.unGenderGiltsBack > obj.unGenderGilts) obj.unGenderGiltsBack = obj.unGenderGilts;

            if (obj.maleChildOver1YearOldBack && obj.maleChildOver1YearOldBack > obj.maleChildOver1YearOld) obj.maleChildOver1YearOldBack = obj.maleChildOver1YearOld;
            if (obj.femaleChildOver1YearOldBack && obj.femaleChildOver1YearOldBack > obj.femaleChildOver1YearOld) obj.femaleChildOver1YearOldBack = obj.femaleChildOver1YearOld;
            if (obj.unGenderChildOver1YearOldBack && obj.unGenderChildOver1YearOldBack > obj.unGenderChildOver1YearOld) obj.unGenderChildOver1YearOldBack = obj.unGenderChildOver1YearOld;

            if (obj.maleChildUnder1YearOldBack && obj.maleChildUnder1YearOldBack > obj.maleChildUnder1YearOld) obj.maleChildUnder1YearOldBack = obj.maleChildUnder1YearOld;
            if (obj.femaleChildUnder1YearOldBack && obj.femaleChildUnder1YearOldBack > obj.femaleChildUnder1YearOld) obj.femaleChildUnder1YearOldBack = obj.femaleChildUnder1YearOld;
            if (obj.childUnder1YearOldBack && obj.childUnder1YearOldBack > obj.childUnder1YearOld) obj.childUnder1YearOldBack = obj.childUnder1YearOld;

            //if(obj.maleParentBack)
            obj.totalBack = 0;
            obj.maleBack = 0;
            obj.femaleBack = 0;
            obj.unGenderBack = 0;

            obj.maleBack = vm.calcToalReportForm16(
                obj.maleParentBack,
                obj.maleGiltsBack,
                obj.maleChildOver1YearOldBack,
                obj.maleChildUnder1YearOldBack
            );
            obj.femaleBack = vm.calcToalReportForm16(
                obj.femaleParentBack,
                obj.femaleGiltsBack,
                obj.femaleChildOver1YearOldBack,
                obj.femaleChildUnder1YearOldBack
            );
            obj.unGenderBack = vm.calcToalReportForm16(
                obj.unGenderParentBack,
                obj.unGenderGiltsBack,
                obj.childUnder1YearOldBack,
                obj.unGenderChildOver1YearOldBack
            );
            obj.totalBack = obj.maleBack + obj.femaleBack + obj.unGenderBack;
        };

        vm.printFileWord = function () {
            if (!vm.forestProduct.farm) {
                toastr.warning("Chưa chọn cơ sở nuôi", "Cảnh báo");
                return;
            } else if (!vm.forestProduct.code) {
                toastr.warning("Chưa nhập Số bản kê lâm sản", "Cảnh báo");
                return;
            } else if (vm.listForesProductDetail.length <= 0) {
                toastr.warning("Chưa kê khai động vật", "Cảnh báo");
                return;
            } else if (vm.forestProduct.transportDuration < 0) {
                toastr.warning("Thời gian vận chuyển chưa hợp lý", "Cảnh báo");
                return;
            } else {

                if (vm.selectedCityFrom != null) {
                    vm.forestProduct.provinceIdFrom = vm.selectedCityFrom.id;
                    vm.forestProduct.provinceCodeFrom = vm.selectedCityFrom.code;
                }
                if (vm.selectedCityTo != null) {
                    vm.forestProduct.provinceIdTo = vm.selectedCityTo.id;
                    vm.forestProduct.provinceCodeTo = vm.selectedCityTo.code;
                }
                vm.forestProduct.canceled = 2;
                vm.forestProduct.details = vm.listForesProductDetail;
                service.saveForestProductList(vm.forestProduct).then(function (data) {
                    if (data && data.id) {
                        console.log(data);
                        vm.forestProduct.id = data.id;
                        vm.idFPLNew = data.id;
                        vm.getFileWord(data.id);
                        // vm.modalPopupAddEditForestProductList.close();
                        vm.getPageForestProductList();
                    }
                });


            }
        }
        vm.addEmailToSend = function () {
            var mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
            if (vm.emailToSend.match(mailformat)) {
                toastr.success("Thêm email thành công", "Thông báo");
                vm.listEmail.push(vm.emailToSend);
                vm.emailToSend = "";
                return true;
            } else {
                toastr.warning("Email sai định dạng", "Cảnh báo");
                vm.emailToSend = "";
                return false;
            }

        }
        vm.removeEmail = function (item) {
            for (var i = 0; i < vm.listEmail.length; i++) {
                if (vm.listEmail[i] == item) {
                    vm.listEmail.splice(i, 1);
                }
            }
        }
        vm.listEmail = [];
        vm.sendEmailInfo = function () {
            service.getEmailAddressToSend(vm.forestProduct).then(function (data) {
                if (data) {
                    vm.listEmail = data;
                }
                vm.modalPopupCancelForestProductList = modal.open({
                    animation: true,
                    templateUrl: 'send_email_info_modal.html',
                    scope: $scope,
                    size: 'md'
                });
            })
        }
        vm.sendEmail = function () {
            vm.forestProduct.listEmail = vm.listEmail;
            service.sendEmail(vm.forestProduct).then(function (data) {
                if (data) {
                    vm.listEmail = [];
                    vm.forestProduct = data;
                    vm.modalPopupCancelForestProductList.close();
                    toastr.success("Bạn đã gửi email thành công", "Thông báo");
                    vm.getPageForestProductList();
                    return;
                } else {
                    toastr.warning("Bạn không có quyền sửa đổi BK này", "Thông báo");
                    return;
                }

            });
        }

        vm.downloadeForestProductsList = function () {
            $scope.myBlobObjectDownloadAnimal = undefined;
            service.downloadeForestProductsList(vm.searchDtoForestProductList).then(
                function (data) {
                    var file = new Blob([data], {
                        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    });
                    FileSaver.saveAs(file, "DanhSachBangKeLamSan.xlsx");
                },
                function (fail) {
                    console.log("Download Error, stop animation and show error message");
                    $scope.myBlobObjectDownloadAnimal = [];
                }
            );
        };
        vm.cancelForestProductList = function (id) {
            if (id) {
                service.getForestProductListById(id).then(function (data) {
                    vm.forestProduct = data;
                    if (data.details) {
                        vm.listForesProductDetail = data.details;
                    }
                    vm.modalPopupCancelForestProductList = modal.open({
                        animation: true,
                        templateUrl: 'cancel.html',
                        scope: $scope,
                        backdrop: 'static',
                        keyboard: false,
                        windowClass: "customer-modal-lg",
                        size: 'lg'
                    });
                });
            }

        };

        vm.cancelForestProductList1 = function (id) {
            if (id) {
                service.getForestProductListById(id).then(function (data) {
                    //debugger
                    vm.forestProduct = data;
                    if (data.administrativeUnitFrom) {
                        vm.getCityFrom = data.administrativeUnitFrom.parentDto;
                    }
                    if (data.administrativeUnitTo) {
                        vm.getCityTo = data.administrativeUnitTo.parentDto;
                    }
                    vm.forestProduct.canceled = 0;
                    vm.sendEmailInfo();
                });
            }

        };
        vm.validateDateConfirm = (date) => {
            var dateRegex = /^\d{2}[./-]\d{2}[./-]\d{4}$/
            if (date.match(dateRegex)) {
                return true;
            } else {
                return false;
            }
        }
        vm.cancelForestProductList2 = function (id) {
            if (id) {
                vm.modalConfirmFPL = modal.open({
                    animation: true,
                    templateUrl: 'confirm_FPL.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
                vm.modalConfirmFPL.result.then(function (confirm) {
                    //debugger
                    if (confirm == 'yes') {
                        if (vm.confirmName == "" || vm.confirmDate == null || vm.confirmDate == "" || typeof vm.confirmDate == "undefined") {
                            vm.confirmName = "";
                            toastr.warning("Thông tin xác nhận chưa hợp lệ", "Thông báo");
                            return;
                        } else {
                            //debugger
                            service.getForestProductListById(id).then(function (data) {
                                //debugger
                                vm.forestProduct = data;
                                vm.forestProduct.statusConfirmName = vm.confirmName;
                                // vm.forestProduct.statusConfirmDate = new Date(vm.confirmDate);
                                vm.forestProduct.canceled = 4;
                                vm.forestProduct.noteConfirm = vm.noteConfirm;
                                service.saveForestProductList(vm.forestProduct).then(function (data) {
                                    if (data.isCheckFarm == false) {
                                        vm.forestProduct.farm.village = data.farm.village;
                                        vm.forestProduct.farm.adressDetail = data.farm.adressDetail;
                                        vm.forestProduct.farm.phoneNumber = data.farm.phoneNumber;
                                        vm.forestProduct.farm.businessRegistrationNumber = data.farm.businessRegistrationNumber;
                                        vm.forestProduct.farm.newRegistrationCode = data.farm.newRegistrationCode;
                                    }
                                    if (data) {
                                        vm.getPageForestProductList();
                                        toastr.success("Xác nhận thành công", "Thông báo");
                                    } else {
                                        toastr.warning("Bạn không có quyền sửa đổi BK này", "Thông báo");
                                        return;
                                    }

                                });

                            });
                        }

                    } else if (confirm == 'no') {
                        if (vm.confirmName == "" || vm.confirmDate == null || vm.confirmDate == "" || typeof vm.confirmDate == "undefined") {
                            toastr.warning("Thông tin xác nhận chưa hợp lệ", "Thông báo");
                            vm.confirmName = "";
                            return;
                        } else {
                            service.getForestProductListById(id).then(function (data) {
                                vm.forestProduct = data;
                                vm.forestProduct.statusConfirmName = vm.confirmName;
                                vm.forestProduct.statusConfirmDate = new Date(vm.confirmDate);
                                vm.forestProduct.noteConfirm = vm.noteConfirm;
                                vm.forestProduct.canceled = 5;
                                service.saveForestProductList(vm.forestProduct).then(function (data) {
                                    if (data) {
                                        toastr.success("Xác nhận thành công", "Thông báo");
                                        vm.getPageForestProductList();
                                    } else {
                                        toastr.warning("Bạn không có quyền sửa đổi BK này", "Thông báo");
                                        return;
                                    }

                                });

                            });
                        }
                    }
                });

            }

        };
        vm.successFPL = function (id) {
            if (id) {
                service.getForestProductListById(id).then(function (data) {
                    vm.forestProduct = data;
                    vm.modalPopupSuccessForestProductList = modal.open({
                        animation: true,
                        templateUrl: 'success.html',
                        scope: $scope,
                        backdrop: 'static',
                        keyboard: false,
                        windowClass: "customer-modal-lg",
                        size: 'md'
                    });
                    vm.modalPopupSuccessForestProductList.result.then(function (confirm) {
                        if (confirm == 'yes') {
                            vm.forestProduct.canceled = 3;
                            service.saveForestProductList(vm.forestProduct).then(function (data) {
                                if (data) {
                                    toastr.success("Hoàn thành bảng kê", "Thông báo");
                                    vm.getPageForestProductList();
                                } else {
                                    toastr.warning("Bạn không có quyền sửa đổi BK này", "Thông báo");
                                    return;
                                }
                            });
                        } else if (confirm == 'no') {

                        }
                    });


                });
            }

        };
        vm.saveForestProductListBack = function () {
            vm.forestProduct.details = vm.listForesProductDetail;
            vm.forestProduct.canceled = 1;
            console.log(vm.forestProduct);
            service.saveForestProductList(vm.forestProduct).then(function (data) {
                if (data) {
                    vm.modalPopupSaveForestProductList.close();
                    vm.modalPopupCancelForestProductList.close();

                    vm.getPageForestProductList();
                } else {
                    toastr.warning("Bạn không có quyền sửa đổi BK này", "Thông báo");
                    return;
                }

            });
        };

        vm.saveForestBack = function () {
            vm.modalPopupSaveForestProductList = modal.open({
                animation: true,
                templateUrl: 'notice.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                windowClass: "customer-modal-lg",
                size: 'lg'
            });
        }

        vm.changeDateTo1 = function () {
            if (vm.forestProduct.transportDuration) {
                if (vm.forestProduct.transportStart) {
                    vm.forestProduct.transportEnd = new Date();
                    vm.forestProduct.transportEnd.setDate(vm.forestProduct.transportStart.getDate() + vm.forestProduct.transportDuration);
                } else {
                    if (vm.forestProduct.transportEnd) {
                        vm.forestProduct.transportStart = new Date();
                        vm.forestProduct.transportStart.setDate(vm.forestProduct.transportEnd.getDate() - vm.forestProduct.transportDuration);
                    }
                }
            }
            // else {
            //     if (vm.forestProduct.transportStart && vm.forestProduct.transportEnd) {
            //         var sumDate = new Date();
            //         sumDate = (vm.forestProduct.transportEnd.getTime() - vm.forestProduct.transportStart.getTime()) / (1000 * 60 * 60 * 24);
            //         vm.forestProduct.transportDuration = sumDate
            //     }
            // }

        }

        vm.changeDateTo2 = function () { // end thay đổi
            if (vm.forestProduct.transportEnd) {
                if (vm.forestProduct.transportStart) { // set sumdate
                    var sumDate = new Date();
                    vm.forestProduct.transportEnd.setHours(0);
                    vm.forestProduct.transportEnd.setMinutes(0, 0, 0);
                    vm.forestProduct.transportStart.setHours(0);
                    vm.forestProduct.transportStart.setMinutes(0, 0, 0);
                    sumDate = (vm.forestProduct.transportEnd.getTime() - vm.forestProduct.transportStart.getTime()) / (1000 * 60 * 60 * 24);
                    console.log("a", sumDate)
                    vm.forestProduct.transportDuration = sumDate
                } else { // set start 
                    if (vm.forestProduct.transportDuration) {
                        vm.forestProduct.transportStart = new Date();
                        vm.forestProduct.transportStart.setDate(vm.forestProduct.transportEnd.getDate() - vm.forestProduct.transportDuration);
                    }

                }
            }
        }

        vm.changeDateTo = function () { // khi chon startDate
            if (vm.forestProduct.transportStart) {
                if (vm.forestProduct.transportDuration) {
                    vm.forestProduct.transportEnd = new Date();
                    vm.forestProduct.transportEnd.setHours(0);
                    vm.forestProduct.transportEnd.setMinutes(0, 0, 0);
                    vm.forestProduct.transportStart.setHours(0);
                    vm.forestProduct.transportStart.setMinutes(0, 0, 0);
                    vm.forestProduct.transportEnd.setDate(vm.forestProduct.transportStart.getDate() + vm.forestProduct.transportDuration);
                } else {
                    if (vm.forestProduct.transportEnd) {
                        var sumDate = new Date();
                        vm.forestProduct.transportEnd.setHours(0);
                        vm.forestProduct.transportEnd.setMinutes(0, 0, 0);
                        vm.forestProduct.transportStart.setHours(0);
                        vm.forestProduct.transportStart.setMinutes(0, 0, 0);
                        sumDate = (vm.forestProduct.transportEnd.getTime() - vm.forestProduct.transportStart.getTime()) / (8.64e7);
                        vm.forestProduct.transportDuration = sumDate
                    }

                }
            }

        }


        vm.onCheckChangeFarm = function () {
            vm.forestProduct.farm.administrativeUnit = vm.forestProduct.communeFrom;
        }

    }

})();