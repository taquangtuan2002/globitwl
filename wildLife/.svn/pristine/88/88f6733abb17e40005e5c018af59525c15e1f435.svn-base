/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ImportExportLiveStockProduct').controller('ExportController', ExportController);

    ExportController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ExportService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate',
        'Upload',
        'FarmService'
    ];

    angular.module('Education.ImportExportLiveStockProduct').directive('numberInput', function ($filter) {
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
    function ExportController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate, Upload, FarmService) {
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
        vm.listData = []
        vm.export={};
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
        vm.firstQuantity = 0;//Số lượng ban đầu, trong trường hợp sửa bản ghi xuất 
		vm.firstAmount = 0;//Khối lượng ban đầu, trong trường hợp sửa bản ghi xuất 
        /** generate */
        //vm.getPageImportAnimal();
        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
		vm.showQuantity=false;
		vm.showAmount=false;
		vm.isSdahView = false;
        

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

            if (vm.isRole) {
                vm.findByAdmin();
            } else {
                if (vm.isFamer) {//trường hợp tài khoản nông dân
                    vm.textSearch = vm.user.username;
                    vm.findBy();
                }else{
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
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (administrativeUnit.parentDto == null) {
                vm.searchDto.cityId = administrativeUnit.id;
                vm.findByAdmin();
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                vm.searchDto.districtId = administrativeUnit.id;
                vm.findByAdmin();
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                vm.searchDto.wardsId = administrativeUnit.id;
                vm.findByAdmin();
            }
        }

        function getAllLiveStockProduct() {
            service.getAllLiveStockProduct().then(function (data) {
                vm.listLiveStockProduct = data;
            });
        }

        //get farm by userLogin
        function getFarmByUserLogin() {
            if (vm.isFamer) {
                service.getFarmByUserLogin().then(function (data) {
                    vm.listFarm = data;
                    if (vm.listFarm != null && vm.listFarm.length == 1) {
                        if (vm.export == null)
                            vm.export = {};

                        vm.export.farm = vm.listFarm[0];
						vm.farm=vm.listFarm[0];
						vm.farmName=vm.listFarm[0].name;
						vm.findByBatchCode();
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
                data: vm.listData,
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

        vm.enterSearchExport = function () {
            // console.log(event.keyCode);
            if (event.keyCode == 13) {//Phím Enter

                vm.searchBy();
            }
        };
        vm.searchBy = function () {
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
                vm.searchDto.type = -1;//xuất sản phẩm chăn nuôi
                service.getPageSearchExport(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                    vm.listData = data.content;
                    vm.bsTableControl.options.data = vm.listData;
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

        $scope.editExport = function (id) {
            if (id) {
                service.getExport(id).then(function (data) {
                    vm.export = data;
                    vm.export.isNew = false;
                    
                    if (vm.export != null && vm.export.farm != null) {
                        vm.farm = vm.export.farm;
                        vm.farmName = vm.export.farm.name;
                    }
					vm.firstQuantity = vm.export.quantity;
					vm.firstAmount = vm.export.amount;
					vm.dateImport=vm.export.importLiveStockPorduct.dateIssue;
					if(vm.export.liveStockProductDto!=null && vm.export.liveStockProductDto.unitQuantity!=null){
						vm.showQuantity=true;
					}
					if(vm.export.liveStockProductDto!=null && vm.export.liveStockProductDto.unitAmount!=null){
						vm.showAmount=true;
					}
					
                });
                vm.modalAnimal = modal.open({
                    animation: true,
                    templateUrl: 'export_detail.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'lg'
                });
				vm.modalAnimal.result.then(function (confirm) {
					
				}, function () {
					console.log("cancel");
					vm.showAmount=false;
					vm.showQuantity=false;
				});
            }
        }

        vm.createExport = function () {
            vm.farmSelected = null;
            vm.farm = null;
            vm.farmName = null;
            vm.export = { isNew: true, type: -1 };
			vm.export.dateIssue = moment(new Date(), 'DD/MM/YYYY').toDate();
            vm.firstQuantity = 0;
            getFarmByUserLogin();
            getAllLiveStockProduct();
            vm.modalAnimal = modal.open({
                animation: true,
                templateUrl: 'export_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'lg'
            });
			vm.modalAnimal.result.then(function (confirm) {
                
            }, function () {
                console.log("cancel");
				vm.showAmount=false;
				vm.showQuantity=false;
            });
        };

        vm.save = function () {
			console.log(vm.export);
            if (!vm.farmName) {
				 toastr.warning($translate.instant("exportAnimal.emptyFarm"), $translate.instant("toastr.warning"));
                return;
            }
			if (vm.export.importLiveStockPorduct==null || vm.export.importLiveStockPorduct.id==null || vm.export.importLiveStockPorduct.id==0) {
				 toastr.warning($translate.instant("exportAnimal.emptyBatchCode"), $translate.instant("toastr.warning"));
                return;
            }
            if (!vm.export.liveStockProductDto) {
                toastr.warning($translate.instant("exportAnimal.emptyLiveStockproduct"), $translate.instant("toastr.warning"));
                return;
            }
            if (!vm.export.dateIssue || vm.export.dateIssue=="") {
                toastr.warning($translate.instant("exportAnimal.emptyDateExport"), $translate.instant("toastr.warning"));
                return;
            }
			if(vm.export.dateIssue!=null && vm.export.dateIssue>new Date()){
                toastr.warning($translate.instant("exportAnimal.warningDate"), $translate.instant("toastr.warning"));
                return ;
            }
			if(vm.export.dateIssue!=null && vm.dateImport!=null && vm.export.dateIssue<vm.dateImport){
				 toastr.warning($translate.instant("exportAnimal.dateImportExport"), $translate.instant("toastr.warning"));
                return;
			}
           
            if ((!vm.export.quantity || vm.export.quantity <=0) && vm.export.importLiveStockPorduct.remainQuantity>0 && vm.showQuantity) {
                toastr.warning($translate.instant("exportAnimal.emptyQuantity"), $translate.instant("toastr.warning"));
                return;
            }			
			
            if (vm.export.quantity > vm.export.importLiveStockPorduct.remainQuantity+ vm.firstQuantity) {
            	toastr.warning($translate.instant("import_export_live_stock_product.errorQuantity"), $translate.instant("toastr.warning"));
                return;
            }
			if ((!vm.export.amount || vm.export.amount <=0) && vm.export.importLiveStockPorduct.remainAmount>0 &&vm.showAmount) {
                toastr.warning($translate.instant("exportAnimal.emptyAmount"), $translate.instant("toastr.warning"));
                return;
            }
			
			if (vm.export.amount > vm.export.importLiveStockPorduct.remainAmount+ vm.firstAmount) {
            	toastr.warning($translate.instant("import_export_live_stock_product.errorAmount"), $translate.instant("toastr.warning"));
                return;
            }
			
			/*if ((!vm.export.quantity || vm.export.quantity <=0) && vm.export.importLiveStockPorduct.remainQuantity>0 && (!vm.export.amount || vm.export.amount <=0) && vm.export.importLiveStockPorduct.remainAmount>0) {
                toastr.warning($translate.instant("import_export_live_stock_product.emptyAmountQuantity"), $translate.instant("toastr.warning"));
                return;
            }*/
            
            vm.export.type = -1; 
            if (vm.id) {
                if (vm.export) {
                    service.updateExport(vm.id, vm.export).then(function(data) {                      
						console.log(data);
						if(data!=null){
							toastr.info($translate.instant("exportAnimal.saveSuccessfully"), $translate.instant("toastr.info"));
							 vm.modalAnimal.close();
							 if (vm.isFamer == true) {								
								vm.findBy();
							 } else {								
								vm.findByAdmin();
							 }
						}else{
							toastr.warning($translate.instant("exportAnimal.errorQuantity"), $translate.instant("toastr.warning"));
							return false;
						}
                    });
					/*, function (respone) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });*/
                }
            } else {
                service.createExport(vm.export).then(function(data) {                   
					console.log(data);
					if(data!=null){						
						 vm.modalAnimal.close();
						toastr.info($translate.instant("exportAnimal.saveSuccessfully"), $translate.instant("toastr.info"));
						if (vm.isFamer == true) {								
							vm.findBy();
						 } else {								
							vm.findByAdmin();
						 }
					}else{
						toastr.warning($translate.instant("exportAnimal.errorQuantity"), $translate.instant("toastr.warning"));
						return false;
					}
                });
				/*, function (respone) {
                    toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    console.log(error);
                });*/
            }
			vm.showQuantity=false;
			vm.showAmount=false;
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
        $scope.deleteExport = function (id) {
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
				
                service.deleteExport(vm.recordId).then(function (data) {
                    if (data != null && data.code == "-1") {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    } else if (data != null && data.code == "-2") {
                        toastr.warning($translate.instant("importAnimal.deleteImport1"), $translate.instant("toastr.warning"));
                    }
                    else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }
					vm.searchBy();
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
                vm.searchDto.type = -1;
                service.getPageSearchExport(vm.searchDto, vm.pageIndexNot, vm.pageSizeNot).then(function (data) {
                    vm.importNots = data.content;
                    vm.bsTableControlAdmin.options.data = vm.importNots;
                    vm.bsTableControlAdmin.options.totalRows = data.totalElements;
                });
            }

        }
        vm.bsTableControlAdmin = {
            options: {
                data: vm.importNots,
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
                vm.export.farm = vm.farm;

                vm.findByBatchCode();

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
                    if (vm.export == null) vm.export = {};
                    vm.farmName = vm.farms[0].name;
                    //vm.farmCode = vm.farms[0].code;
                    vm.export.farm = vm.farms[0];
					vm.farm=vm.farms[0];
					vm.findByBatchCode();
                }
                //console.log(vm.farms);
            });
        }

        //--------------END Popup Farm----------//
		//--------------Popup BatchCode----------//
		vm.pageIndexBatchCode=1, vm.pageSizeBatchCode=10;
        vm.subModalInstance = {};

        vm.selectedBatchCodes=[];
        vm.batchCodeSelected={};
        vm.showPopupBatchCode = function () {
            if (vm.export.farm != null) {
                vm.subModalInstance = modal.open({
                    animation: true,
                    templateUrl: 'choose_batch_code_modal.html',
                    scope: $scope,
                    size: 'lg'
                });
                vm.code=null;
                vm.selectedBatchCodes=[];
                vm.batchCodeSelected={};
                vm.findByBatchCode();
                vm.subModalInstance.result.then(function (confirm) {
                    if (confirm == 'yes') {
                    
                    }
                }, function () {
                    vm.batchCode=null;
                    console.log("cancel");
                });
            }else{
                toastr.warning($translate.instant("exportAnimal.notChooseFarm"), $translate.instant("toastr.warning"));
                return false;
            }
        }

        var closeModal = function () {
			
            vm.subModalInstance.close();

        };
        function checkAgree(){
            console.log("checkAgree");
            console.log(vm.selectedBatchCodes);
            if(angular.isUndefined(vm.selectedBatchCodes)|| vm.selectedBatchCodes.length==0 ){
                toastr.warning($translate.instant("exportAnimal.notChooseBatchCode"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.agree=function () {
            if(checkAgree()){				
                vm.batchCodeSelected=vm.selectedBatchCodes[0];
                vm.batchCode=vm.batchCodeSelected.batchCode;
				if(vm.export==null){
					vm.export={};
				}
				vm.export.batchCode=vm.batchCode;				
				vm.export.liveStockProductDto=vm.batchCodeSelected.liveStockProductDto;				
                vm.farm=vm.batchCodeSelected.farm;
                vm.dateImport=vm.batchCodeSelected.dateIssue;
				vm.export.importLiveStockPorduct=vm.batchCodeSelected;//phiếu nhập
				vm.dateIssue=vm.batchCodeSelected.dateIssue;
				if(vm.export.liveStockProductDto!=null && vm.export.liveStockProductDto.unitQuantity!=null){
					vm.showQuantity=true;
				}else{
                    vm.showQuantity=false;
                }
				if(vm.export.liveStockProductDto!=null && vm.export.liveStockProductDto.unitAmount!=null){
					vm.showAmount=true;
				}else{
                    vm.showAmount=false;
                }				
			   vm.batchCode=null;
                closeModal();
				console.log(vm.batchCodeSelected);
            }
        }
        
        vm.enterSearchTeacher=function (e) {
            if(event.keyCode == 13){//Phím Enter
                vm.searchTeachers();
            }
        }
        vm.bsTableControlCode = {
            options: {
                data: vm.imports,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: true,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeBatchCode,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionCode(),
                onCheck: function (row, $element) {
                    if( vm.selectedBatchCodes!=null){
                        vm.selectedBatchCodes=[];
                    }
                    $scope.$apply(function () {
                        vm.selectedBatchCodes.push(row);
                    });
                },
              
                onUncheck: function (row, $element) {
                    
                    if( vm.selectedBatchCodes!=null){
                        vm.selectedBatchCodes=[];
                    }
                },
                
                onPageChange: function (index, pageSize) {
                    vm.pageSizeBatchCode = pageSize;
                    vm.pageIndexBatchCode = index;
                    //vm.searchTeachers();
					vm.findByBatchCode();
                }
            }
        };
		
		
		
		vm.enterSearchCode = function(){
			// console.log(event.keyCode);
			if(event.keyCode == 13){//Phím Enter
				
				vm.searchByCode();
			}
        };
		 vm.searchByCode = function () {
            vm.pageIndexBatchCode = 1;
            vm.bsTableControlCode.state.pageNumber = 1;
            vm.findByBatchCode();
        };
		vm.findByBatchCode=function () {
            if( vm.searchCodeDto==null){
                vm.searchCodeDto={};
            }
            if(vm.batchCode!=null&& vm.batchCode!=""){
                vm.searchCodeDto.nameOrCode=vm.batchCode;
            }
            else{
                vm.searchCodeDto.nameOrCode=null;
            }
			if(vm.farm!=null && vm.farm.id!=null){
				vm.searchCodeDto.farmId=vm.farm.id;
			}else{
				if(vm.export!=null && vm.export.farm!=null && vm.export.farm.id!=null){
					vm.searchCodeDto.farmId=vm.export.farm.id;
				}else
				vm.searchCodeDto.farmId=null;
			}
			vm.searchCodeDto.type=1;
            console.log("vm.searchCodeDto");
            
            console.log(vm.searchCodeDto);
            service.getPageSearchExport(vm.searchCodeDto,vm.pageIndexBatchCode,vm.pageSizeBatchCode).then(function(data) {
                vm.imports = data.content;
                vm.bsTableControlCode.options.data = vm.imports;
                vm.bsTableControlCode.options.totalRows = data.totalElements;
                console.log(vm.imports);
                 if(vm.imports!=null && vm.imports.length == 1){
                     //vm.batchCode=vm.importAnimals[0].batchCode;
					if(vm.export==null){
						vm.export={};
					}
					vm.batchCodeSelected=vm.imports[0];
					vm.export.batchCode=vm.imports[0].batchCode;				
					vm.export.liveStockProductDto=vm.imports[0].liveStockProductDto;					
					vm.export.importLiveStockPorduct=vm.imports[0];//phiếu nhập
					vm.batchCodeSelected=vm.selectedBatchCodes[0];
					vm.dateImport=vm.imports[0].dateIssue;					
					if(vm.export.liveStockProductDto!=null && vm.export.liveStockProductDto.unitQuantity!=null){
						vm.showQuantity=true;
					}
					if(vm.export.liveStockProductDto!=null && vm.export.liveStockProductDto.unitAmount!=null){
						vm.showAmount=true;
					}
                					
                 }
            });
        }
    }

})();
