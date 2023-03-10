/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Farm').controller('FarmController', FarmController);

    FarmController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'FarmService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate',
        'Upload',
        'FmsAdministrativeService',
        'HusbandryTypeService',
        'HusbandryMethodService',
        'WaterSourceService',
        'ProductTargetService',
        'AnimalService',
        'AnimalTypeService',
        'OwnershipService',
        'FileSaver'

    ];

//    angular.module('Education.Farm').directive('fileDownload', function () {
//        return {
//            restrict: 'A',
//            scope: {
//                fileDownload: '=',
//                fileName: '=',
//            },
//
//            link: function (scope, elem, atrs) {
//
//                scope.$watch('fileDownload', function (newValue, oldValue) {
//                    if (newValue != undefined && newValue != null) {
//                        console.debug('Downloading a new file');
//                        var isFirefox = typeof InstallTrigger !== 'undefined';
//                        var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
//                        var isIE = /*@cc_on!@*/false || !!document.documentMode;
//                        var isEdge = !isIE && !!window.StyleMedia;
//                        var isChrome = !!window.chrome && !!window.chrome.webstore || window.chrome != null;;
//                        var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
//                        var isBlink = (isChrome || isOpera) && !!window.CSS;
//
//                        if (isFirefox || isIE || isChrome) {
//                            if (isChrome) {
//                                console.log('Manage Google Chrome download');
//                                var url = window.URL || window.webkitURL;
//                                var fileURL = url.createObjectURL(scope.fileDownload);
//                                var downloadLink = angular.element('<a></a>');//create a new  <a> tag element
//                                downloadLink.attr('href', fileURL);
//                                downloadLink.attr('download', scope.fileName);
//                                downloadLink.attr('target', '_self');
//                                downloadLink[0].click();//call click function
//                                url.revokeObjectURL(fileURL);//revoke the object from URL
//                            }
//                            if (isIE) {
//                                console.log('Manage IE download>10');
//                                window.navigator.msSaveOrOpenBlob(scope.fileDownload, scope.fileName);
//                            }
//                            if (isFirefox) {
//                                console.log('Manage Mozilla Firefox download');
//                                var url = window.URL || window.webkitURL;
//                                var fileURL = url.createObjectURL(scope.fileDownload);
//                                var a = elem[0];//recover the <a> tag from directive
//                                a.href = fileURL;
//                                a.download = scope.fileName;
//                                a.target = '_self';
//                                a.click();//we call click function
//                            }
//
//
//                        } else {
//                            alert('SORRY YOUR BROWSER IS NOT COMPATIBLE');
//                        }
//                    }
//                });
//
//            }
//        }
//    });
    angular.module('Education.Farm').directive('numberInput', function ($filter) {
        return {
            require: 'ngModel',
            link: function (scope, elem, attrs, ngModelCtrl) {

                ngModelCtrl.$formatters.push(function (modelValue) {
                    return setDisplayNumber(modelValue, true);
                });

                // it's best to change the displayed text using elem.val()
                // rather than
                // ngModelCtrl.$setViewValue because the latter will re-trigger
                // the parser
                // and not necessarily in the correct order with the changed
                // value last.
                // see
                // http://radify.io/blog/understanding-ngmodelcontroller-by-example-part-1/
                // for an explanation of how ngModelCtrl works.
                ngModelCtrl.$parsers.push(function (viewValue) {
                    setDisplayNumber(viewValue);
                    return setModelNumber(viewValue);
                });

                // occasionally the parser chain doesn't run (when the user
                // repeatedly
                // types the same non-numeric character)
                // for these cases, clean up again half a second later using
                // "keyup"
                // (the parser runs much sooner than keyup, so it's better UX to
                // also do it within parser
                // to give the feeling that the comma is added as they type)
                elem.bind('keyup focus', function () {
                    setDisplayNumber(elem.val());
                });

                function setDisplayNumber(val, formatter) {
                    var valStr, displayValue;

                    if (typeof val === 'undefined' || val==null) {
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

    function FarmController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate, Upload, fmsAdministrativeService, husbandryTypeService, husbandryMethodService, waterSourceService, productTargetService, animalService, animalTypeService, ownershipService,FileSaver) {
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
        vm.pageSize = 10;
        vm.farms = [];
        vm.ownerships = [];
        vm.modalFarm = {};
        vm.farm = { isNew: false };
        vm.modalConfirmDelete = {};
        vm.selectedFarms = [];
        vm.count = 0;
        vm.searchDto = {
        };
        vm.statuses = [

            {
                value: 2,
                name: 'Ng??ng ho???t ?????ng'
            },

            {
                value: 3,
                name: '??ang ho???t ?????ng'
            }

        ];
        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
        vm.isSearchExtend = false;
        vm.isSdahView = false;//c???p t???nh nh??ng ch??? ???????c xem

        vm.isSelectProvince = false;
        vm.isSelectDistrict = false;
        vm.isSelectWard = false;
        $scope.years = [];
        var today = new Date();
        vm.currentYear=today.getFullYear();
		for(var i=vm.currentYear; i >= 2000; i--) {
			$scope.years.push({value: i, name: i+""});
        }
        
        //------Start--Ph??n quy???n theo user ????ng nh???p-----------
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
            if (vm.isRole) {//tr?????ng h???p admin v?? dlp th?? ???????c xem t???t c??? c??c c?? s??? ch??n nu??i
                vm.getAllCity();
                vm.findBy();
            } else {// tr?????ng h???p n??y th?? ph??n quy???n theo user
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUser();
                }

            }
            blockUI.stop();
        });

        //load user administrativeunit
        function getAdministrativeUnitDtoByLoginUser() {
            if(vm.isSdahView) {
                vm.getDataBySdahViewRole();
            } else {
                service.getAdministrativeUnitDtoByLoginUser().then(function (data) {
                    vm.adminstrativeUnits = data;
                    if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
                        getDataByCombobox(vm.adminstrativeUnits[0]);
                    }
                });
            }
        }
        vm.listStatus = [
        {
            id:1,
            name:"kh??ng ho???t ?????ng"
        },
        // },{
        //     id:1,
        //     name:"T???m Ng??ng"
        // },
        {
            id:0,
            name:"??ang ho???t ?????ng"
        }
        ]
        vm.onStatus = function (){
            console.log(vm.searchDto.statusfarm);
            vm.findBy();
        }
        function getDataByCombobox(administrativeUnit) {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (administrativeUnit.parentDto == null) {

                vm.searchDto.province = administrativeUnit.id;
                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit);
                service.getAllByParentId(vm.searchDto.province).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }

                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                vm.searchDto.district = administrativeUnit.id;
                vm.searchDto.province = administrativeUnit.parentDto.id;

                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
                vm.adminstrativeUnits_Dist = [];
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                // vm.adminstrativeUnits_Dist.push(administrativeUnit);
                service.getAllByParentId(vm.searchDto.province).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }

                });
                service.getAllByParentId(vm.searchDto.district).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }

                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                vm.searchDto.ward = administrativeUnit.id;
                vm.searchDto.district = administrativeUnit.parentDto.id;
                vm.searchDto.province = administrativeUnit.parentDto.parentDto.id;

                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
                if (vm.adminstrativeUnits_Wards == null) vm.adminstrativeUnits_Wards = [];
                vm.adminstrativeUnits_Wards.push(administrativeUnit);
            }
            if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') {//tr?????ng h???p t??i kho???n n??ng d??n
                //g??n t??n c?? s??? ch??n nu??i
                vm.textSearch = vm.user.username;
                // console.log("username");
                //console.log(vm.user.username);
            }
            vm.findBy();

        }

        vm.getDataBySdahViewRole = () => {
            vm.findBy();
        }

        //--------End ----Ph??n quy???n-------------
        //load data farm
        vm.getPageFarm = function () {
            service.getPageFarm(vm.pageIndex, vm.pageSize).then(function (data) {
                vm.farms = data.content;
                vm.bsTableControl.options.data = vm.farms;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        
        vm.searchDto.checkSort = false;
        vm.sapXep = function() {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            } else {
                if (vm.searchDto.province != null) {
                    vm.isSelectProvince = true;
                }
                if (vm.searchDto.district != null) {
                    vm.isSelectDistrict = true;
                }
                if (vm.searchDto.ward != null) {
                    vm.isSelectWard = true;
                }
            }
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchDto.nameOrCode = vm.textSearch;
            }
            else {
                vm.searchDto.checkSort = !vm.searchDto.checkSort;
                
                vm.searchDto.nameOrCode = null;
            }
            if (validate()) {
                service.getPageSearchFarm(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                    vm.farms = data.content;
                    vm.bsTableControl.options.data = vm.farms;
                    vm.bsTableControl.options.totalRows = data.totalElements;
                });
            }
        }

        vm.findBy = function () {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            } else {
                if (vm.searchDto.province != null) {
                    vm.isSelectProvince = true;
                }
                if (vm.searchDto.district != null) {
                    vm.isSelectDistrict = true;
                }
                if (vm.searchDto.ward != null) {
                    vm.isSelectWard = true;
                }

            }
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchDto.nameOrCode = vm.textSearch;
            }
            else {
                vm.searchDto.nameOrCode = null;
            }
            if (validate()) {
                service.getPageSearchFarm(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                    vm.farms = data.content;
                    vm.bsTableControl.options.data = vm.farms;
                    vm.bsTableControl.options.totalRows = data.totalElements;
                });
            }
        } 
        
        vm.modaldeleteDataMapByAdministrativeUnit = null;
        vm.showPopupdeleteDataMapByAdministrativeUnit = function () {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if ((vm.searchDto.province == null || vm.searchDto.province <= 0)
                && (vm.searchDto.district == null || vm.searchDto.district <= 0)
                && (vm.searchDto.ward == null || vm.searchDto.ward <= 0)) {
                    toastr.warning("B???n ph???i ch???n T???nh/th??nh ho???c qu???n/huy??n ho???c x??/ph?????ng.");
                    return;
            }
            else{
                var dto = {};
                if (vm.searchDto.ward) {
                    dto.area_id = vm.searchDto.ward;
                } else if (vm.searchDto.district) {
                    dto.area_id = vm.searchDto.district;
                } else if (vm.searchDto.province) {
                    dto.area_id = vm.searchDto.province;
                }

                vm.modaldeleteDataMapByAdministrativeUnit = modal.open({
                    animation: true,
                    templateUrl: 'modal_delete_DataMapByAdministrativeUnit.html',
                    scope: $scope,
                    size: 'lg',
                    backdrop: 'static'
                });

            }
        }

        vm.deleteDataMapByAdministrativeUnit = function () {
            service.deleteDataMapByAdministrativeUnit(vm.searchDto).then(function (data) {
                if (data) {
                    alert("Th??nh c??ng.");
                    if (vm.modaldeleteDataMapByAdministrativeUnit) {
                        vm.modaldeleteDataMapByAdministrativeUnit.close();
                    }
                }
                else{
                    alert("Th???t b???i.");
                }
            });
        }

        vm.synchronizedMap = function () {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            vm.searchDto.isConvert = false;
            vm.pushToMap();
        }

        vm.convertDataAndSynchronizedMap = function () {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            vm.searchDto.isConvert = true;
            vm.pushToMap();
        }

        vm.pushToMap = function(){
        	if (vm.searchDto == null) {
                vm.searchDto = {};
            } else {
                if (vm.searchDto.province != null) {
                    vm.isSelectProvince = true;
                }
                if (vm.searchDto.district != null) {
                    vm.isSelectDistrict = true;
                }
                if (vm.searchDto.ward != null) {
                    vm.isSelectWard = true;
                }
            }
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchDto.nameOrCode = vm.textSearch;
            }
            else {
                vm.searchDto.nameOrCode = null;
            }
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchDto.nameOrCode = vm.textSearch;
            }
            else {
                vm.searchDto.nameOrCode = null;
            }
            if(!vm.searchDto.yearRegistration){
            	toastr.warning("B???n ph???i ch???n n??m tr?????c");
            	return;
            }

            vm.modalupdateAllFarmtoMap = modal.open({
                animation: true,
                templateUrl: 'modal_updateAllFarmtoMap.html',
                scope: $scope,
                size: 'lg',
                backdrop: 'static'
            });
        }

        vm.updateAllFarmtoMap = function () {
            service.updateAllFarmtoMap(1, 100000,vm.searchDto.yearRegistration,vm.searchDto).then(function (data) {
                alert('OK');
                if (vm.modalupdateAllFarmtoMap) {
                    vm.modalupdateAllFarmtoMap.close();
                }
            });
        }

        function validate() {
            if (vm.searchDto != null && vm.searchDto.productTargetId != null && vm.searchDto.productTargetId > 0) {
                if (!(vm.searchDto.balanceNumber != null || vm.searchDto.balanceNumber != 0 || vm.searchDto.toBalanceNumber != null || vm.searchDto.toBalanceNumber != 0)) {
                    toastr.warning($translate.instant("farm.balanceNumberEmpty"), $translate.instant("toastr.warning"));
                    return false;
                }
                return true;
            }

            if (vm.searchDto != null && vm.searchDto.animalId != null && vm.searchDto.animalId > 0) {
                if (!(vm.searchDto.balanceNumber != null || vm.searchDto.balanceNumber != 0 || vm.searchDto.toBalanceNumber != null || vm.searchDto.toBalanceNumber != 0)) {
                    toastr.warning($translate.instant("farm.balanceNumberEmpty"), $translate.instant("toastr.warning"));
                    return false;
                }
                if (vm.searchDto.productTargetId == null || vm.searchDto.productTargetId <= 0) {
                    toastr.warning($translate.instant("product.chooseProduct"), $translate.instant("toastr.warning"));
                    return false;
                }


                return true;

            }
            return true;
        }
        
        vm.bsTableControl = {
            options: {
                data: vm.farms,
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
                columns: getTableDefinition(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selectedFarms.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedFarms = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedFarms);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedFarms.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedFarms = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    vm.findBy();
                    //vm.getPageFarm();
                }
            }
        };
        vm.enterSearch = function () {
            // console.log(event.keyCode);
            if (event.keyCode == 13) {//Ph??m Enter

                vm.searchByCode();
            }
        };
        vm.searchByCode = function () {
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.findBy();
        };


        $scope.editFarm = function (id) {
            $state.go("application.farm_detail", { id: id , idScroll: 'testScroll'})
        }

        vm.createFarm = function () {
            vm.farm = { isNew: true };
            $state.go('application.farm_detail');
        };

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
                toastr.warning("B???n ch??a ch???n!")
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
                vm.findBy();
                //vm.getPageFarm();
                vm.modalConfirmDelete.close();
                vm.selectedFarms = [];
            }, function failure(error) {
                console.log(error);
            });
        }
        $scope.deleteOriginalRecord = function (id) {
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
                service.deleteFarmById(vm.recordId).then(function (data) {
                    //alert(data);
                    console.log("data1111");
                    console.log(data);
                    if (data != null && data.code == "-1") {
                        toastr.warning($translate.instant("farm.NotDelete"), $translate.instant("toastr.warning"));
                    }
                    else if (data != null && data.code == "-2") {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    } else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }
                    vm.findBy();
                    //vm.getPageFarm();
                    vm.modalConfirmDelete.close();
                    vm.selectedFarms = [];
                });
                /*,function failure(error){
                   toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    vm.modalConfirmDelete.close(); 
                });*/
            }
        }
        //// Upload file
        $scope.MAX_FILE_SIZE = '5MB';
        $scope.f = null;
        $scope.errFile = null;

        $scope.uploadFiles = function (file, errFiles) {
            $scope.f = file;
            $scope.errFile = errFiles && errFiles[0];
        };

        vm.startUploadFile = function (file) {
            console.log(file);
            if (file) {
                file.upload = Upload.upload({
                    url: vm.baseUrl + 'file/importWLFarm',
                    data: { uploadfile: file }
                });

                file.upload.then(function (response) {
                    console.log(response);
                    file.result = response.data;
                    // getAllStudent(vm.pageIndex,vm.pageSize);
                    vm.findBy();
                    //getTreeData(vm.pageIndex,vm.pageSize);
                    toastr.info($translate.instant("upload.importSuccess"), $translate.instant("toastr.info"));
                }, function errorCallback(response) {
                    toastr.error($translate.instant("upload.importError"), $translate.instant("toastr.error"));
                });
            }
        };


        vm.importFarm = function () {
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'import_modal.html',
                scope: $scope,
                size: 'md'
            });


            $scope.f = null;
            $scope.errFile = null;

            modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                    vm.startUploadFile($scope.f);
                    console.log($scope.f);
                }
            }, function () {

                console.log("cancel");
            });
        }
        vm.getAllCity = function () {
            fmsAdministrativeService.getAllCity().then(function (data) {
                vm.adminstrativeUnits_City = data;

            });
        }
        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                        vm.selectedDist = null;
                        vm.selectWards = null;
                        vm.searchDto.district = null;
                        vm.searchDto.ward = null;
                    }
                    else {
                        vm.searchDto.district = null;
                        vm.searchDto.ward = null;
                        vm.selectedDist = null;
                        vm.selectWards = null;
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
            vm.findBy();
        }
        vm.onFmsadminstrativeUnitDistSelected = function (dist) {
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            if (dist != null && dist.id != null) {
                service.getAllByParentId(dist.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                        vm.searchDto.ward = null;
                        vm.selectWards = null;
                    }
                    else {
                        vm.searchDto.ward = null;
                        vm.selectWards = null;
                        vm.adminstrativeUnits_Wards = [];
                    }

                });
            }
            else {
                vm.searchDto.ward = null;
                vm.selectWards = null;
                vm.adminstrativeUnits_Wards = [];
            }
            vm.findBy();
        }
        vm.onFmsadminstrativeUnitWardsSelected = function (item) {
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.findBy();
        }
        vm.onSelectYear = function () {
            vm.findBy();
        }
        //export
        $scope.myBlobObject = undefined;
        $scope.getFile = function () {
            console.log("check search dto",vm.searchDto)//check search dto
            console.log('download started, you can show a wating animation');
            service.getStream(vm.searchDto)
                .then(function (data) {//is important that the data was returned as Aray Buffer
                    console.log('Stream download complete, stop animation!');
                    $scope.myBlobObject = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                    FileSaver.saveAs($scope.myBlobObject, "farm.xls");
                }, function (fail) {
                    console.log('Download Error, stop animation and show error message');
                    $scope.myBlobObject = [];
                });
        };

        
        //t??m ki???m m??? r???ng
        function clear() {
            vm.searchDto.balanceNumber = null;
            vm.searchDto.toBalanceNumber = null;
            vm.searchDto.animalId = null;
            vm.searchDto.isOutSourcing = null;
            vm.searchDto.productTargetId = null;
            vm.searchDto.createDateFrom = null;
            vm.searchDto.createDateTo = null;
            vm.searchDto.createdBy = null;
            vm.searchDto.modifiedDateFrom = null;
            vm.searchDto.modifiedDateTo = null;
            vm.searchDto.modifiedBy = null;
            vm.searchDto.ownership = null;

        }
        vm.searchExtend = function () {
            if (vm.isSearchExtend == false) {
                vm.isSearchExtend = true;
                clear();
                //load d??? li???u c??c combobox m??? r???ng
                /* waterSourceService.getAll().then(function(data) {
                vm.waterResources = data;
                });
                husbandryMethodService.getAll().then(function(data) {
                    vm.husbandryMethods = data;
                });
                husbandryTypeService.getAll().then(function(data) {
                    vm.husbandryTypes = data;
                    console.log(vm.husbandryTypes)
                });*/
                productTargetService.getAllProductTarget().then(function (data) {
                    vm.productTargets = data;
                    console.log(vm.productTargets)
                });
                /*animalService.getAll().then(function(data) {
                    vm.animals = data;
                    console.log(vm.animals)
                });*/
                animalService.getListParent().then(function (data) {
                    vm.animalParents = data;
                    console.log(vm.animals)
                });
                animalTypeService.getAllAnimalTypeDto().then(function (data) {
                    vm.animalTypes = data;
                    console.log(vm.animalTypes)
                });
                /*service.getAllCertificate(1,10000).then(function(data) {
                    vm.certificates = data.content;
                    console.log(vm.certificates)
                });*/

            } else {
                vm.isSearchExtend = false;
                clear();
                vm.findBy();
            }

        };

        ownershipService.getAll().then(function (data) {
            vm.ownerships = data;
        });

        vm.onSelectOwnership = function (item) {
            vm.findBy();
        }
        vm.checkedFarm = null;
        vm.onClickSelectedA = function () {
            if (vm.checkedFarmA && vm.checkedFarmB) {
                vm.checkedFarmB = !vm.checkedFarmB;
            }
        }
        vm.onClickSelectedB = function () {
            if (vm.checkedFarmA && vm.checkedFarmB) {
                vm.checkedFarmA = !vm.checkedFarmA;
            }
        }
        vm.modalMerge = null;
        vm.form16FarmA=[];
        vm.form16FarmB=[];
        vm.form16Dup=[];
        //vm.listForm16AB=[];
        //Array ?????ng v???t
        vm.listAnimalInForm16FarmAB=[];
        vm.showPopupMerge = function () {
            if (vm.selectedFarms.length == 2) {
                vm.farmA = vm.selectedFarms[0];
                vm.farmB = vm.selectedFarms[1];
                vm.farmMergeDto={};
                
                vm.checkedFarmA = null;
                vm.checkedFarmB = null;
                //get report16
                //L???y d??? li???u form16 c???a farmA v?? farmB
                vm.form16Dup=[];
                vm.listForm16AB=[];
                vm.listAnimalInForm16FarmAB=[];
                vm.listForm16Checked=[];
                // debugger
                service.getAllForm16ByFarm(vm.farmA.id).then(function (data){
                    // debugger
                    vm.form16FarmA = data;
                    service.getAllForm16ByFarm(vm.farmB.id).then(function (data){
                        // debugger
                        vm.form16FarmB = data;
                        //L???y ra danh s??ch ?????ng v???t
                        var rp16AB = vm.form16FarmA.concat(vm.form16FarmB);
                        for(let item of rp16AB){
                            item.isChecking = true;
                            if(vm.listAnimalInForm16FarmAB && vm.listAnimalInForm16FarmAB.length>0){
                                let fABs = vm.listAnimalInForm16FarmAB.filter(x => x.id ==item.animal.id);
                                if(fABs.length==0){
                                    vm.listAnimalInForm16FarmAB.push(item.animal);
                                }
                            }else{
                                vm.listAnimalInForm16FarmAB.push(item.animal);
                            }
                        }
                        vm.listForm16AB = rp16AB;
                        
                    });
                });
               
            //end get report16
                vm.modalMerge = modal.open({
                    animation: true,
                    templateUrl: 'modal_merge_farm.html',
                    scope: $scope,
                    size: 'lg',
                    backdrop: 'static'
                });
            }
        }
        $scope.getValues = function(id, num){
            if(num==1){
                var array = vm.form16FarmA.filter(x => x.animal.id == id);
            }else{
                var array = vm.form16FarmB.filter(x => x.animal.id == id);
            }
            
            return array;
        }
        //start select form16
        vm.listForm16Delete=[];//Danh s??ch form16 c???n b???
        vm.listForm16Checked=[];
        vm.currentForm16Check=[];
        vm.modalMergeForm16 = null;
        $scope.viewReport16 = function(item){
            //console.log(vm.listForm16AB.length);
            vm.currentForm16Check=[];
            vm.currentForm16Check.push(item);
            vm.modalMergeForm16 = modal.open({
                animation: true,
                templateUrl: "view_detail.html",
                scope: $scope,
                backdrop: "static",
                keyboard: false,
                windowClass: "customer-modal-lg",
                size: "lg",
            });
        }
        $scope.selectForm16 = (e,rpForm)=>{
            vm.listForm16UnChecked = [];
            vm.listForm16Checked = [];
            for(var item of vm.listForm16AB){
                if(item.id == rpForm.id){
                    if(e.target.checked==true){
                        item.isChecking = true;
                        for(var item2 of vm.listForm16AB){
                            var timeItem = new Date(item.dateReport).toLocaleDateString();
                            var timeItem2 = new Date(item2.dateReport).toLocaleDateString();
                            if(timeItem == timeItem2 && item.id != item2.id && item.animal.id == item2.animal.id){
                                item2.isChecking = false;
                            }
                        }
                    }else{
                        item.isChecking = false;
                    }
                }
            }
        }
        $scope.checkedForm16 = (rpForm)=>{
            if(rpForm.isChecking == true){
                return true;
            }else{
                return false;
            }
        }
        // $scope.selectForm16 = (e,rpForm)=>{
        //     vm.listForm16UnChecked = [];
        //     vm.listForm16Checked = [];
        //     for(var item of vm.listForm16AB){
        //         if(item.id != rpForm.id){
        //             item.isChecking = false;
        //             if(e.target.checked==true){
        //                 vm.listForm16UnChecked.push(item);
        //             }
        //         }else{
        //             item.isChecking = true;
        //             vm.listForm16Checked.push(item);
        //         }
        //     }
        //    console.log("B???:");
        //    console.log(vm.listForm16Checked);
        //    console.log("Ch???n:");
        //    console.log(rpForm);
        // }
        // $scope.checkedForm16 = (rpForm) =>{
        //     if(rpForm.isChecking == true){
        //         return true;
        //     }else{
        //         return false;
        //     }

        // }
        // vm.saveMergeForm16 = function(){
        //     if(vm.listForm16UnChecked && vm.listForm16UnChecked.length>0){
        //         for(let item of vm.listForm16UnChecked){
        //             var listCheckExist = vm.listForm16Delete.filter(x=>x.id==item.id);
        //             if(listCheckExist.length==0){
        //                 vm.listForm16Delete.push(item);
        //             }
        //         }
        //         vm.form16Dup = [];
        //         for(let item of vm.listForm16Checked){
        //             item.mergeStatus=true;
        //             item.mergeFarmName=item.farm.name;
        //             vm.form16Dup.push(item);
        //         }
        //         toastr.info("???? h???p nh???t c??c b???n ghi", "Th??ng b??o");
        //         console.log(vm.listForm16Delete);
        //         vm.modalMergeForm16.close();
        //     }else{
        //         toastr.warning("B???n ch??a ch???n b???n ghi n??o", "Th??ng b??o");
        //         return;
        //     }
        // }
        //end select form16

        $scope.clickCheck = (e,id) => {
            if(id==vm.farmA.id && e.target.checked){
                
                vm.farmMergeDto[e.target.name] = vm.farmA[e.target.name];
                
            }
            if(id==vm.farmA.id && !e.target.checked){
                
                vm.farmMergeDto[e.target.name] = vm.farmB[e.target.name];
                
            }
            if(id==vm.farmB.id && !e.target.checked){
                
                vm.farmMergeDto[e.target.name] = vm.farmA[e.target.name];
                
            }
            if(id==vm.farmB.id && e.target.checked){
                
                vm.farmMergeDto[e.target.name] = vm.farmB[e.target.name];
                
            }
            console.log("vm.farmMergeDto",vm.farmMergeDto);
            
        }

        $scope.check = (id, name, code) => {
            if(vm.farmMergeDto[code]==name && vm.farmMergeDto[code]==vm.farmA[code]){
                return true;
            }
            if(vm.farmMergeDto[code]==name && vm.farmMergeDto[code]==vm.farmB[code]){
                return true;
            }
            return false;
           
        }

        vm.acceptMergeFarm = function () {
            if (vm.checkedFarmA || vm.checkedFarmB) {
                let farmIdMain = null;
                let duplicateFarmId = null;
                if (vm.checkedFarmA == true) {
                    farmIdMain = vm.farmA.id;
                    duplicateFarmId = vm.farmB.id;
                } else if (vm.checkedFarmB == true) {
                    farmIdMain = vm.farmB.id;
                    duplicateFarmId = vm.farmA.id;
                }
                service.mergeFarm(farmIdMain, duplicateFarmId).then(function () {
                    toastr.info("H???p nh???t d??? li???u th??nh c??ng", "Th??ng b??o");
                    vm.modalMerge.close();
                    vm.selectedFarms = [];
                    vm.searchByCode();
                    if (document.querySelector('input[name="btSelectAll"]').checked == true) {
                        document.querySelector('input[name="btSelectAll"]').click();
                    } else {
                        document.querySelector('input[name="btSelectAll"]').click();
                        document.querySelector('input[name="btSelectAll"]').click();
                    }
                });
            } else {
                toastr.warning("B???n ch??a ch???n c?? s??? nu??i ch??nh", "C???nh b??o");
            }
        }

        //tran huu dat t???o h??m mergerfarm m???i start
        vm.acceptMergeFarmNew = function () {
            if (vm.checkedFarmA || vm.checkedFarmB) {
                // debugger
                let farmIdMain = null;
                let duplicateFarmId = null;
                vm.listForm16FarmDupChecking = [];
                if (vm.checkedFarmA == true) {
                    farmIdMain = vm.farmA.id;
                    duplicateFarmId = vm.farmB.id;
                    vm.listForm16FarmDupChecking = vm.form16FarmA.filter(x=>x.isChecking==true);
                } else if (vm.checkedFarmB == true) {
                    farmIdMain = vm.farmB.id;
                    duplicateFarmId = vm.farmA.id;
                    vm.listForm16FarmDupChecking = vm.form16FarmB.filter(x=>x.isChecking==true);
                }
                // debugger
                vm.listForm16Delete=[];
                for(var item of vm.listForm16AB){
                    if(item.isChecking == false || item.isChecking==='undefined' || item.isChecking==null){
                        vm.listForm16Delete.push(item);
                    }
                }
                service.mergeReport16(vm.listForm16Delete).then(function(){
                    //X??a h???t period c???a farm c?? ??i, ?????i period cho form16 c??
                    service.mergeReportPeriod(vm.listForm16FarmDupChecking, farmIdMain, duplicateFarmId).then(function(){
                        toastr.info("H???p nh???t d??? li???u v???t nu??i th??nh c??ng", "Th??ng b??o");
                        service.mergeFarmNew(farmIdMain, duplicateFarmId,vm.farmMergeDto).then(function () {
                            toastr.info("H???p nh???t d??? li???u th??nh c??ng", "Th??ng b??o");
                                vm.modalMerge.close();
                                vm.selectedFarms = [];
                                vm.searchByCode();
                                if (document.querySelector('input[name="btSelectAll"]').checked == true) {
                                    document.querySelector('input[name="btSelectAll"]').click();
                                } else {
                                    document.querySelector('input[name="btSelectAll"]').click();
                                    document.querySelector('input[name="btSelectAll"]').click();
                                }
                        });   
                    });
                }, function(){
                    toastr.warning("D??? li???u v???t nu??i kh??ng th??? x??a do c?? r??ng bu???c v???i BKLS", "Th??ng b??o");
                });
            } else {
                toastr.warning("B???n ch??a ch???n c?? s??? nu??i ch??nh", "C???nh b??o");
            }
        }
        //tran huu dat t???o h??m mergefarm m???i end

        function getTableDefinition() {
            var _tableOperation = function (value, row, index) {
                //data-ng-click="$parent.editFarm(' + "'" + row.id + "'" + ')"
                return '<a  class="green-dark margin-right-5" href="#/detail_farm/' + row.id + '" target="_blank"><i class="icon-pencil margin-right-5"></i> {{"animal.detail" | translate}}</a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    // +'<a class="green-dark margin-right-5" href="#/view_detail_farm/' + row.id + '" target="_blank"><i class="icon-eye margin-right-5"></i> {{"farm.detailView" | translate}}</a>'
                    + '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteOriginalRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i> {{"animal.delete" | translate}}</a>';
            };
            var _tableOperationViewLinkWithCode = function (value, row, index) {
                if (row && row.code) {
                    return '<a class="green-dark margin-right-5" href="#/view_detail_farm/' + row.id + '" target="_blank">' + row.code + '</a>';
                }
                return '';
            };
            var _tableOperationViewLinkWithName = function (value, row, index) {
                if (row && row.name) {
                    return '<a class="green-dark margin-right-5" href="#/view_detail_farm/' + row.id + '" target="_blank">' + row.name + '</a>';
                }
                return '';
            };
            var _tableInput = function (value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '120px' }
                };
            };


            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _nameFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.name;
            };
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '90px', 'text-align': 'center' }
                };
            };
            var _numberFormat = function (value, row, index) {
                if (value) {
                    return $filter('number')(value);
                }
                else {
                    return "";
                }
            };
            var _createDateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                } else {
                    var year = value[0];
                    var month = value[1];
                    var date = value[2];
                    var hour = value[3];
                    var minutes = value[4];
                    var seconds = value[5];
                    return date + '/' + month + '/' + year + ' | ' + hour + ':' + minutes + ':' + seconds;
                }
            };
            var _tableIndex = function (value, row, index, field) {
                return index + 1;
            };
            var _tableTextRight = function (value, row, index, field) {
                return {
                    classes: 'text-center'
                };
            };

            return [
                {
                    field: '',
                    title: 'TT',
                    switchable: true,
                    visible: true,
                    formatter: _tableIndex,
                    cellStyle: _tableTextRight
                },
                {
                    field: 'state',
                    checkbox: true
                }
                ,
                {
                    field: '',
                    title: '{{"farm.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
                {
                    field: 'code',
                    title: '{{"farm.farmCode" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                },
                {
                    field: 'provinceName',
                    title: 'T???nh/Th??nh ph???',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    visible:(!vm.isSelectProvince)
                },
               
                {
                    field: 'districtName',
                    title: 'Huy???n',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    visible:(!vm.isSelectDistrict)
                },
                {
                    field: 'wardsName',
                    title: 'X??',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    visible:(!vm.isSelectWard)
                }
                , {
                    field: 'name',
                    title: 'C?? s??? nu??i',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'ownerPhoneNumber',
                    title: '{{"farm.ownerPhoneNumber" | translate}}',
                    sortable: true,
                    switchable: true,
                    cellStyle: _cellNowrap,

                },
                {
                    field: 'ownerName',
                    title: '{{"farm.ownerName" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'village',
                    title: 'Th??n x??m',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                },
                {
                    field: 'adressDetail',
                    title: 'S??? nh??',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                },
                {
                    field: 'newRegistrationCode',
                    title: 'M??? s??? ????ng k??',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
            ];
        };



    }
})();
