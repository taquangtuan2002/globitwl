/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ImportEgg').controller('ImportEggController', ImportEggController);

    ImportEggController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ImportEggService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate',
        'Upload',
        'FarmService',
        'ImportAnimalService'
    ];

    angular.module('Education.ImportEgg').directive('numberInput', function ($filter) {
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
    function ImportEggController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate, Upload, FarmService, importAnimalService) {
        $scope.$on('$viewContentLoaded', function () {
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
        vm.importEggs = []
        //vm.importEgg={};
        vm.modalFarm = {}
        vm.farm = { isNew: false };
        vm.modalConfirmDelete = {};
        vm.selectedExportEggs = [];
        vm.count = 0;
        vm.listDistrictId = null;
        vm.farmSelected = null;
        vm.farm = null;
        vm.farmName = null;
        vm.searchDto = {};
        vm.firstQuantity = 0;
        /** generate */
        //vm.getPageImportAnimal();
        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
		vm.isSdahView = false;
        vm.eggTypes = [{
            id: 0,
            name:"Thương phẩm"
        },
        {
            id: 1,
            name:"Trứng giống"
        }]

        //------Start--Phân quyền theo user đăng nhập-----------
        blockUI.start();
        service.getCurrentUser().then(function (data) {
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
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah_view' ){
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
            if (vm.isFamer) {//trường hợp tài khoản nông dân

                vm.textSearch = vm.user.username;
                vm.findBy();

            } else{
                vm.findByAdmin();
            }
            getAdministrativeUnitDtoByLoginUser();

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

        //get farm by userLogin
        function getFarmByUserLogin() {
        	if(vm.isFamer){
        		service.getFarmByUserLogin().then(function (data) {
                    vm.listFarm = data;
                    if (vm.listFarm != null && vm.listFarm.length == 1) {
                        if (vm.importEgg == null)
                            vm.importEgg = {};

                        vm.importEgg.farm = vm.listFarm[0];
                    }
                    console.log('vm.listFarm');
                    console.log(vm.listFarm);
                });
        	}            
        }

        //--------End ----Phân quyền-------------

        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.importEggs,
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
                        vm.selectedExportEggs.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedExportEggs = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedExportEggs);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedExportEggs.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedExportEggs = [];
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

        vm.enterSearch = function () {
            // console.log(event.keyCode);
            if (event.keyCode == 13) {//Phím Enter

                vm.searchByCode();
            }
        };
        vm.searchByCode = function () {
            if (vm.isFamer == true || vm.isWard == true) {
                vm.pageIndex = 1;
                if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                    vm.bsTableControl.state.pageNumber = 1;
                }
                vm.findBy();
            } else {
                vm.pageIndexNote = 1;
                if (vm.bsTableControlAdmin != null && vm.bsTableControlAdmin.state != null) {
                    vm.bsTableControlAdmin.state.pageNumber = 1;
                }
                vm.findByAdmin();
            }

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

            if (vm.fromDate != null) {
                vm.searchDto.fromDate = vm.fromDate;
            } else {
                vm.searchDto.fromDate = null;
            }
            if (vm.toDate != null) {
                vm.searchDto.toDate = vm.toDate;
            } else {
                vm.searchDto.toDate = null;
            }

            console.log(vm.searchDto);
            if (changeDate()) {
                vm.searchDto.type = 1;
                service.getPageSearchExportEgg(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                    vm.importEggs = data.content;
                    vm.bsTableControl.options.data = vm.importEggs;
                    vm.bsTableControl.options.totalRows = data.totalElements;
                });
            }

        }
        function changeDate() {
            if (vm.fromDate != null && vm.toDate != null && vm.fromDate > vm.toDate) {
                toastr.warning($translate.instant("importAnimal.toDateBeforeFrom"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }

        $scope.editExportEgg = function (id) {
            if (id) {
                service.getExportEgg(id).then(function (data) {
                    vm.importEgg = data;
                    vm.importEgg.isNew = false;
                    vm.firstQuantity = vm.importEgg.quantity;
                    if (vm.importEgg != null && vm.importEgg.farm != null) {
                        vm.farm = vm.importEgg.farm;
                        vm.farmName = vm.importEgg.farm.name;
                    }
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

        vm.createExportEgg = function () {
            
            vm.farmSelected = null;
            vm.farm = null;
            vm.farmName = null;
            vm.importEgg = { isNew: true, type: 1 };
            vm.importEgg.dateExport = new Date();
            vm.firstQuantity = 0;
            getFarmByUserLogin();
            vm.modalAnimal = modal.open({
                animation: true,
                templateUrl: 'detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        };

        vm.save = function () {
            if (vm.importEgg == null) {
                toastr.warning($translate.instant("exportEgg.emptyExport"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importEgg.farm == null) {
                toastr.warning($translate.instant("exportEgg.emptyFarm"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importEgg.dateExport == null) {
                toastr.warning($translate.instant("exportEgg.emptyDateImport"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importEgg.dateExport != null && vm.importEgg.dateExport > new Date()) {
                toastr.warning($translate.instant("exportEgg.requireDateImport"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importEgg.quantity == null || vm.importEgg.quantity <= 0) {
                toastr.warning($translate.instant("exportEgg.emptyQuantity"), $translate.instant("toastr.warning"));
                return;
            }
            if (!Number.isInteger(vm.importEgg.quantity) || (Number.isInteger(vm.importEgg.quantity) && vm.importEgg.quantity <= 0)) {
                toastr.warning($translate.instant("exportEgg.quantityIsInteger"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importEgg.eggType == null) {
                toastr.warning($translate.instant("exportEgg.emptyEggType"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importEgg.importExportAnimal == null || vm.importEgg.importExportAnimal.id == null) {
                toastr.warning($translate.instant("exportEgg.emptyImportEcportAnimal"), $translate.instant("toastr.warning"));
                return;
            }
            if(moment(vm.importEgg.dateExport).startOf('day').toDate() < moment(vm.importEgg.importExportAnimal.dateImport).startOf('day').toDate()){
                toastr.warning($translate.instant("exportEgg.validDateImportAniamal"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importEgg) {

                if (vm.importEgg.id) {
                    service.updateExportEgg(vm.importEgg.id, vm.importEgg, function success(data) {
                       
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    }).then(function(data){
                        
                        if(data.code == "-7"){
                            toastr.warning($translate.instant("exportEgg.validDateImportAniamal"), $translate.instant("toastr.warning"));
                        }else if(data.code =="-3"){//Số lượng sửa không được nhỏ hơn số lượng đã dùng để xuất
                            toastr.warning($translate.instant("exportEgg.quantityEditLessThan"), $translate.instant("toastr.warning"));
                        }else if(data.code == "-2"){// Ngày nhập trứng không thể lớn hơn ngày xuất xuất trứng từ dữ liệu xuất trứng đã có
                            toastr.warning($translate.instant("exportEgg.validDateImport"), $translate.instant("toastr.warning"));
                        } else{
                            toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                            vm.modalAnimal.close();
                            if (vm.isFamer == true) {
                                vm.pageIndex = 1;
                                if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                                    vm.bsTableControl.state.pageNumber = 1;
                                }
                                vm.findBy();
                            } else {
                                vm.pageIndexNote = 1;
                                if (vm.bsTableControlAdmin != null && vm.bsTableControlAdmin.state != null) {
                                    vm.bsTableControlAdmin.state.pageNumber = 1;
                                }
                                vm.findByAdmin();
                            }
                        }
                    });
                } else {
                    service.createExportEgg(vm.importEgg, function success(data) {
                        
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    }).then(function(data){
                        if(data.code == "-7"){
                            toastr.warning($translate.instant("exportEgg.validDateImportAniamal"), $translate.instant("toastr.warning"));
                        }else{
                            toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                            if (vm.isFamer == true) {
                                vm.pageIndex = 1;
                                if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                                    vm.bsTableControl.state.pageNumber = 1;
                                }
                                vm.findBy();
                            } else {
                                vm.pageIndexNote = 1;
                                if (vm.bsTableControlAdmin != null && vm.bsTableControlAdmin.state != null) {
                                    vm.bsTableControlAdmin.state.pageNumber = 1;
                                }
                                vm.findByAdmin();
                            }
                            vm.modalAnimal.close();
                        }
                    });
                }
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

        vm.deletefarms = function () {
            if (vm.selectedFarms.length == 0) {
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

        vm.confirmDelete = function () {
            vm.pageIndex = 1;
            var ids = vm.selectedFarms.map(function (element) {
                return element.id;
            });
            service.deleteServiceByIds(ids, function success(data) {
                // vm.getPageImportAnimal();
                vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedFarms = [];
            }, function failure(error) {
                console.log(error);
            });
        }
        $scope.deleteExportEgg = function (id) {
            if (id) {
                vm.recordId = id;
                let isChange = true;
                service.getExportEgg(id).then(function (data) {
                    isChange = data.quantity != data.remainQuantity
                }).then(function(){
                    if(isChange == true){
                        toastr.warning($translate.instant("exportEgg.recordIsUsed"), $translate.instant("toastr.warning"));
                    }else{
                        vm.modalConfirmDelete = modal.open({
                            animation: true,
                            templateUrl: 'confirm_delete_record.html',
                            scope: $scope,
                            backdrop: 'static',
                            keyboard: false,
                            size: 'md'
                        });
                    }
                });
            } else {
                vm.recordId = null;
            }
            
        }
        vm.confirmDeleteRecord = function () {
            if (vm.recordId) {
                service.deleteExportEgg(vm.recordId).then(function (data) {
                    if (data != null && data.code == "-1") {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    } else if (data != null && data.code == "-2") {
                        toastr.warning($translate.instant("importAnimal.deleteImport"), $translate.instant("toastr.warning"));
                    }
                    else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }

                    if (vm.isFamer == true || vm.isWard == true) {
                        vm.pageIndex = 1;
                        if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                            vm.bsTableControl.state.pageNumber = 1;
                        }
                    } else {
                        vm.pageIndexNote = 1;
                        if (vm.bsTableControlAdmin != null && vm.bsTableControlAdmin.state != null) {
                            vm.bsTableControlAdmin.state.pageNumber = 1;
                        }
                    }
                    vm.searchByCode();
                    vm.modalConfirmDelete.close();
                    vm.selectedFarms = [];
                });
                /*,function failure(error){
                    toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    vm.modalConfirmDelete.close(); 
                });*/
            }
        }

        vm.findByAdmin = function () {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchDto.nameOrCode = vm.textSearch;
            }

            else {
                vm.searchDto.nameOrCode = null;
            }

            if (vm.fromDate != null) {
                vm.searchDto.fromDate = vm.fromDate;
            } else {
                vm.searchDto.fromDate = null;
            }
            if (vm.toDate != null) {
                vm.searchDto.toDate = vm.toDate;
            } else {
                vm.searchDto.toDate = null;
            }

            console.log(vm.searchDto);
            if (changeDate()) {
                vm.searchDto.type = 1;
                service.getPageSearchExportEgg(vm.searchDto, vm.pageIndexNot, vm.pageSizeNot).then(function (data) {
                    vm.importEggNots = data.content;
                    vm.bsTableControlAdmin.options.data = vm.importEggNots;
                    vm.bsTableControlAdmin.options.totalRows = data.totalElements;
                });
            }

        }
        vm.bsTableControlAdmin = {
            options: {
                data: vm.importEggNots,
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



        //--------------Popup Farm----------//

        //load user administrativeunit
        function getAdministrativeUnitDtoByLoginUserOnSearchFarm() {
            service.getAdministrativeUnitDtoByLoginUser().then(function (data) {
                vm.adminstrativeUnits = data;
                if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
                    getDataByComboboxOnSearchFarm(vm.adminstrativeUnits[0]);

                }
            });
        }
        function getDataByComboboxOnSearchFarm(administrativeUnit) {
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
            }

            //vm.findBy();
            vm.findByFarm();
        }

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
                    getAdministrativeUnitDtoByLoginUserOnSearchFarm();
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
                vm.farmName = vm.farm.name;
                vm.importEgg.farm = vm.farm;

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

        //--------------END Popup Farm----------//

        //--------------POPUP SELECT ImportExportAnimal------//
        vm.modalImportExportAnimal = null;
        vm.selectedIEAnimals = [];
        vm.pageIndexAnimal = 1;
        vm.pageSizeAnimal = 10;
        vm.showPopupAnimal = function(){
            if(!vm.importEgg || !vm.importEgg.farm || !vm.importEgg.farm.id ){
                toastr.warning($translate.instant("exportEgg.emptyFarm"), $translate.instant("toastr.warning"));
                return;
            }else{
                vm.selectedIEAnimals = [];
                vm.searchByImportExportAnimal();
                vm.modalImportExportAnimal = modal.open({
                    animation: true,
                    templateUrl: 'choose_importExporAnimal_modal.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
            }
        }
        vm.bsTableControlImportExportAnimal = {
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
                pageSize: vm.pageSizeAnimal,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableImportExportAnimal(),
                onCheck: function (row, $element) {
                    if (vm.selectedIEAnimals != null) {
                        vm.selectedIEAnimals = [];
                    }
                    $scope.$apply(function () {
                        vm.selectedIEAnimals.push(row);
                    });
                },

                onUncheck: function (row, $element) {

                    if (vm.selectedIEAnimals != null) {
                        vm.selectedIEAnimals = [];
                    }
                },

                onPageChange: function (index, pageSize) {
                    vm.pageSizeAnimal = pageSize;
                    vm.pageIndexAnimal = index;
                    //vm.searchTeachers();
                    vm.searchByImportExportAnimal();
                }
            }
        };
        vm.searchByImportExportAnimal = function(){
            if (vm.searchDtoAnimal == null) {
                vm.searchDtoAnimal = {};
            }
            if (vm.importExportAnimalCode != null && vm.importExportAnimalCode != "") {
                vm.searchDtoAnimal.nameOrCode = vm.importExportAnimalCode;
            }
            else {
                vm.searchDtoAnimal.nameOrCode = null;
            }


            console.log("vm.searchDtoFarm");
            console.log(vm.searchDtoFarm);
            vm.searchDtoAnimal.farmId = vm.importEgg.farm.id;
            vm.searchDtoAnimal.type = 1;
            vm.searchDtoAnimal.remainQuantityDown = 0;// Số lượng còn lại lớn hơn 0
            vm.searchDtoAnimal.productTargetCode = "EGG";
            importAnimalService.getPageSearchImportAnimalSeed(vm.searchDtoAnimal, vm.pageIndexAnimal, vm.pageSizeAnimal).then(function (data) {
                // vm.farms = data.content;
                vm.bsTableControlImportExportAnimal.options.data = data.content;
                vm.bsTableControlImportExportAnimal.options.totalRows = data.totalElements;
            });
        }
        vm.agreeImportExportAnimal = function(){
            if(vm.selectedIEAnimals.length == 0){
                toastr.warning($translate.instant("exportEgg.emptyImportEcportAnimal"), $translate.instant("toastr.warning"));
                return;
            }else{
                vm.importEgg.importExportAnimal = vm.selectedIEAnimals[0];
                vm.modalImportExportAnimal.close();
            }
        }
        vm.enterSearchAnimal = function(event){
            if(event.keyCode == 13){
                vm.searchByImportExportAnimal();
            }
        }


        
    }

})();
