/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ReportFarmerManagerment').controller('ReportAnimalFeedController', ReportAnimalFeedController);

    ReportAnimalFeedController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ReportAnimalFeedService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate',
        'Upload',
        'FarmService',
        'BranService'
    ];

    angular.module('Education.ReportFarmerManagerment').directive('numberInput', function ($filter) {
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
    function ReportAnimalFeedController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate, Upload, FarmService, branService) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;


        $scope.currentTab = 1;
        $scope.changeTab = function (index) {
            $scope.currentTab = index;
        };
        $scope.isActiveTab = function (index) {
            return index === $scope.currentTab;
        }

        /** declare */
        var vm = this;
        vm.baseUrl = settings.api.baseUrl + settings.api.apiPrefix;
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.pageIndexNot = 1;
        vm.pageSizeNot = 25;
        vm.pageIndexExport = 1;
        vm.pageSizeExport = 25;
        vm.importAnimalFeeds = []
        vm.modalFarm = {}
        vm.farm = { isNew: false };
        vm.modalConfirmDelete = {};
        vm.selectedImportAnimalFeeds = [];
        vm.count = 0;
        vm.listDistrictId = null;

        /** generate */
        //vm.getPageImportAnimal();
        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
		vm.isSdahView = false;
        vm.getCombo = function () {
            branService.getAllBranDto().then(function (data) {
                vm.brans = data;
            });
        }
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
            if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') {//trường hợp tài khoản nông dân

                vm.textSearch = vm.user.username;
                vm.getCombo();
                vm.findBy();
                vm.findByExport();

            } else if (vm.isRole == true) {
                vm.getCombo();
                vm.findBy();
                vm.findByExport();
            } else {
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
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (administrativeUnit.parentDto == null) {
                vm.searchDto.province = administrativeUnit.id;

            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                vm.searchDto.district = administrativeUnit.id;

            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                vm.searchDto.ward = administrativeUnit.id;

            }
            vm.pageIndex = 1;
            if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                vm.bsTableControl.state.pageNumber = 1;
            }
            vm.pageIndexExport = 1;
            if (vm.bsTableControlExport != null && vm.bsTableControlExport.state != null) {
                vm.bsTableControlExport.state.pageNumber = 1;
            }
            vm.findBy();
            vm.findByExport();

        }
        //get farm by userLogin
        function getFarmByUserLogin() {
            service.getFarmByUserLogin().then(function (data) {
                vm.listFarm = data;
                if (vm.listFarm != null && vm.listFarm.length == 1) {
                    if (vm.importAnimalFeed == null)
                        vm.importAnimalFeed = {};

                    vm.importAnimalFeed.farm = vm.listFarm[0];
                }
                console.log('vm.listFarm');
                console.log(vm.listFarm);
            });
        }

        //--------End ----Phân quyền-------------

        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.importAnimalFeeds,
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
                        vm.selectedImportAnimalFeeds.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedImportAnimalFeeds = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedImportAnimalFeeds);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedImportAnimalFeeds.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedImportAnimalFeeds = [];
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
        vm.bsTableControlExport = {
            options: {
                data: vm.listExport,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeExport,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionExport(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selectedExport.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedExport = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedExport);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedExport.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedExport = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSizeExport = pageSize;
                    vm.pageIndexExport = index;

                    //vm.getPageImportAnimal();
                    vm.findByExport();
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
            vm.pageIndex = 1;
            if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                vm.bsTableControl.state.pageNumber = 1;
            }
            vm.pageIndexExport = 1;
            if (vm.bsTableControlExport != null && vm.bsTableControlExport.state != null) {
                vm.bsTableControlExport.state.pageNumber = 1;
            }
            vm.findBy();
            vm.findByExport();
        };

        vm.onSelectedBran = function (bran) {
            vm.pageIndex = 1;
            if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                vm.bsTableControl.state.pageNumber = 1;
            }
            vm.pageIndexExport = 1;
            if (vm.bsTableControlExport != null && vm.bsTableControlExport.state != null) {
                vm.bsTableControlExport.state.pageNumber = 1;
            }
            vm.findBy();
            vm.findByExport();
        }

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

            if (vm.bran != null && vm.bran.id != null) {
                vm.searchDto.branId = vm.bran.id;
            } else {
                vm.searchDto.branId = null;
            }

            vm.searchDto.type = 1;

            console.log(vm.searchDto);
            if (changeDate()) {
                service.getPageSearchAnimalFeed(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                    vm.importAnimalFeeds = data.content;
                    vm.bsTableControl.options.data = vm.importAnimalFeeds;
                    vm.bsTableControl.options.totalRows = data.totalElements;
                });
                service.reportInventory(vm.searchDto).then(function (data) {
                    vm.report = data;

                });
            }

        }

        vm.findByExport = function () {
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

            if (vm.bran != null && vm.bran.id != null) {
                vm.searchDto.branId = vm.bran.id;
            } else {
                vm.searchDto.branId = null;
            }

            console.log(vm.searchDto);
            if (changeDate()) {
                vm.searchDtoCopy = angular.copy(vm.searchDto);
                vm.searchDtoCopy.type = -1
                service.getPageSearchAnimalFeed(vm.searchDtoCopy, vm.pageIndexExport, vm.pageSizeExport).then(function (data) {
                    vm.listExport = data.content;
                    vm.bsTableControlExport.options.data = data.content;
                    vm.bsTableControlExport.options.totalRows = data.totalElements;
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

        $scope.editImportAnimalFeed = function (id) {
            if (id) {

                service.getImportAnimalFeed(id).then(function (data) {
                    vm.importAnimalFeed = data;
                    vm.importAnimalFeed.isNew = false;

                    console.log(vm.importAnimalFeed);
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

        vm.createImportAnimalFeed = function () {
            getFarmByUserLogin();
            //let date = new Date();
            //vm.importAnimalFeed.dateImport = {year: date.getFullYear(), month: date.getMonth(), day: date.getDate()};
            //vm.importAnimalFeed.dateImport =new Date();
            vm.importAnimalFeed = { isNew: true };

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
            if (vm.importAnimalFeed == null) {
                toastr.warning($translate.instant("importAnimalFeed.emptyExport"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importAnimalFeed.farm == null) {
                toastr.warning($translate.instant("importAnimalFeed.emptyFarm"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importAnimalFeed.dateImport == null) {
                toastr.warning($translate.instant("importAnimalFeed.emptyDate"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importAnimalFeed.bran == null) {
                toastr.warning($translate.instant("importAnimalFeed.emptyBran"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importAnimalFeed.quantity == null) {
                toastr.warning($translate.instant("importAnimalFeed.emptyQuantity"), $translate.instant("toastr.warning"));
                return;
            }

            if (vm.importAnimalFeed) {
                if (vm.importAnimalFeed.id) {
                    service.updateImportAnimalFeed(vm.importAnimalFeed.id, vm.importAnimalFeed, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        vm.findBy();
                        vm.findByExport();
                        vm.modalAnimal.close();
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                } else {
                    service.createImportAnimalFeed(vm.importAnimalFeed, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        vm.findBy();
                        vm.findByExport();
                        vm.modalAnimal.close();
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
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
                vm.pageIndex = 1;
                if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                    vm.bsTableControl.state.pageNumber = 1;
                }
                vm.pageIndexExport = 1;
                if (vm.bsTableControlExport != null && vm.bsTableControlExport.state != null) {
                    vm.bsTableControlExport.state.pageNumber = 1;
                }
                vm.findBy();
                vm.findByExport();
                vm.modalConfirmDelete.close();
                vm.selectedFarms = [];
            }, function failure(error) {
                console.log(error);
            });
        }
        $scope.deleteImportAnimalFeed = function (id) {
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
                service.deleteImportAnimalFeed(vm.recordId).then(function (data) {
                    if (data != null && data.code == "-1") {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    } else if (data != null && data.code == "-2") {
                        toastr.warning($translate.instant("importAnimal.deleteImport"), $translate.instant("toastr.warning"));
                    }
                    else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }
                    vm.pageIndex = 1;
                    if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                        vm.bsTableControl.state.pageNumber = 1;
                    }
                    vm.pageIndexExport = 1;
                    if (vm.bsTableControlExport != null && vm.bsTableControlExport.state != null) {
                        vm.bsTableControlExport.state.pageNumber = 1;
                    }
                    vm.findBy();
                    vm.findByExport();
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

            vm.searchDto.type = 0;//loại thức ăn

            console.log(vm.searchDto);
            if (changeDate()) {
                service.getPageSearchImportAnimalFeed(vm.searchDto, vm.pageIndexNot, vm.pageSizeNot).then(function (data) {
                    vm.importAnimalFeedNots = data.content;
                    vm.bsTableControlAdmin.options.data = vm.importAnimalFeedNots;
                    vm.bsTableControlAdmin.options.totalRows = data.totalElements;
                });
            }

        }

    }

})();
