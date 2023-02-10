/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.ReportPeriod').controller('PageTemplateController', PageTemplateController);

    PageTemplateController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ReportPeriodService',
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
        '$translate'
    ];
    angular.module('Education.ReportPeriod').directive('fileDownload', function() {
        return {
            restrict: 'A',
            scope: {
                fileDownload: '=',
                fileName: '=',
            },
            link: function(scope, elem, atrs) {
                scope.$watch('fileDownload', function(newValue, oldValue) {
                    if (newValue != undefined && newValue != null) {
                        console.debug('Downloading a new file');
                        var isFirefox = typeof InstallTrigger !== 'undefined';
                        var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
                        var isIE = /*@cc_on!@*/ false || !!document.documentMode;
                        var isEdge = !isIE && !!window.StyleMedia;
                        var isChrome = !!window.chrome && !!window.chrome.webstore || window.chrome != null;;
                        var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
                        var isBlink = (isChrome || isOpera) && !!window.CSS;

                        if (isFirefox || isIE || isChrome) {
                            if (isChrome) {
                                console.log('Manage Google Chrome download');
                                var url = window.URL || window.webkitURL;
                                var fileURL = url.createObjectURL(scope.fileDownload);
                                var downloadLink = angular.element('<a></a>'); //create a new  <a> tag element
                                downloadLink.attr('href', fileURL);
                                downloadLink.attr('download', scope.fileName);
                                downloadLink.attr('target', '_self');
                                downloadLink[0].click(); //call click function
                                url.revokeObjectURL(fileURL); //revoke the object from URL
                            }
                            if (isIE) {
                                console.log('Manage IE download>10');
                                window.navigator.msSaveOrOpenBlob(scope.fileDownload, scope.fileName);
                            }
                            if (isFirefox) {
                                console.log('Manage Mozilla Firefox download');
                                var url = window.URL || window.webkitURL;
                                var fileURL = url.createObjectURL(scope.fileDownload);
                                var a = elem[0]; //recover the <a> tag from directive
                                a.href = fileURL;
                                a.download = scope.fileName;
                                a.target = '_self';
                                a.click(); //we call click function
                            }


                        } else {
                            alert('SORRY YOUR BROWSER IS NOT COMPATIBLE');
                        }
                    }
                });

            }
        }
    });

    function PageTemplateController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, util, Uploader, FileSaver, farmService, animalService, $translate) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;
        vm.type = 1;
        vm.ImportAnimal = {};
        vm.currentId = $state.params.id ? $state.params.id : null;
        vm.farmSelected = null;
        vm.animalSelected = null;
        vm.sizes = [5, 10, 25, 50, 100];
        vm.pageSize = 10;
        vm.pageSizeReportForm = 10;
        vm.pageIndex = 1;
        vm.farms = [];
        vm.farmsForm16 = [];
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
        vm.animal = null;
        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
        vm.isSearchExtend = false;
        vm.isSdahView = false; //cấp tỉnh nhưng chỉ được xem

        vm.isSelectProvince = false;
        vm.isSelectDistrict = false;
        vm.isSelectWard = false;
        vm.province = null;
        vm.district = null;
        vm.ward = null;

        vm.isRoleAdmin = false;

        $scope.years = [];
        var today = new Date();
        vm.currentYear = today.getFullYear();
        for (var i = vm.currentYear; i >= 2000; i--) {
            $scope.years.push({ value: i, name: i + "" });
        }

        //------Start--Phân quyền theo user đăng nhập-----------
        blockUI.start();
        service.getCurrentUser().then(function(data) {
            vm.user = data;
            var roles = data.roles;
            if (roles != null && roles.length > 0) {
                for (var i = 0; i < roles.length; i++) {
                    if (roles[i] != null && roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' || roles[i].authority.toLowerCase() == 'role_dlp')) {
                        vm.isRole = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_admin') {
                        vm.isRoleAdmin = true;
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
            if (vm.isRole) { //trường hợp admin và dlp thì được xem tất cả các cơ sở
                getAllProvince();
                vm.getListReportFormByPage();
            } else { // trường hợp này thì phân quyền theo user
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUser();
                }
            }
            blockUI.stop();
        });

        //load user administrativeunit
        function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function(data) {
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
                service.getAllByParentId(vm.province).then(function(data) {
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
                service.getAllByParentId(vm.province).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }

                });
                service.getAllByParentId(vm.district).then(function(data) {
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
            if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') { //trường hợp tài khoản nông dân
                //gán tên cơ sở chăn nuôi
                vm.textSearch = vm.user.username;
            }
            vm.getListReportFormByPage();
        }
        vm.form16Year = vm.currentYear;
        vm.form16Month = vm.currentMonth;
        vm.onSelectMonthForm16 = function() {
            vm.days = [];
            const lastDayOfMonth = moment(new Date(vm.form16Year, vm.form16Month - 1, 1)).endOf('month').toDate().getDate();
            for (let dayIndex = 1; dayIndex <= lastDayOfMonth; dayIndex++) {
                vm.days.push({ value: dayIndex, name: dayIndex + "" });
            }
        }
        vm.onSelectDateForm16 = function(item) {
            vm.getListReportFormByPage();
        }
        vm.onSelectMonth = function(item) {
            if (item) {
                vm.days = [];
                vm.date = null;
                const lastDayOfMonth = moment(new Date(vm.year, vm.month - 1, 1)).endOf('month').toDate().getDate();
                for (let dayIndex = 1; dayIndex <= lastDayOfMonth; dayIndex++) {
                    vm.days.push({ value: dayIndex, name: dayIndex + "" });
                }
            }
            vm.getListReportFormByPage();
        }
        vm.years = [];
        vm.currentYear = new Date().getFullYear();
        for (let year = vm.currentYear; year >= 2000; year--) {
            vm.years.push({ value: year, name: year + "" });
        }
        vm.months = [];
        vm.currentMonth = new Date().getMonth() + 1;
        for (let m = 1; m <= 12; m++) {
            vm.months.push({ value: m, name: m + "" });
        }
        vm.onSelectMonthForm16();
        vm.onSelectYear = function() {
            vm.days = [];
            vm.date = null;
            const lastDayOfMonth = moment(new Date(vm.year, vm.month - 1, 1)).endOf('month').toDate().getDate();
            for (let dayIndex = 1; dayIndex <= lastDayOfMonth; dayIndex++) {
                vm.days.push({ value: dayIndex, name: dayIndex + "" });
            }
            vm.getListReportFormByPage();
        }
        vm.onSelectDateForm = function() {
            vm.getListReportFormByPage();
        }

        function getAllProvince() {
            service.getAdminstrativeUnitsCity().then(function(data) {
                vm.adminstrativeUnits_City = data || [];
            });
        }
        vm.onRemovedDate = function() {
            vm.pageIndex = 1;
            vm.getListReportFormByPage();
        }
        vm.onRemovedMonth = function() {
            vm.pageIndex = 1;
            vm.getListReportFormByPage();
        }
        vm.onFmsadminstrativeUnitCitySelected = function(city) {
            //console.log("city",city);
            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function(data) {
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

            vm.pageIndex = 1;
            vm.getListReportFormByPage();
        }

        vm.onFmsadminstrativeUnitDistSelected = function(dist) {
            if (dist != null && dist.id != null) {
                service.getAllByParentId(dist.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                        vm.ward = null;
                        vm.selectWards = null;
                        // vm.ward = null;

                    } else {
                        vm.selectWards = null;
                        vm.ward = null;
                        vm.adminstrativeUnits_Wards = [];
                    }

                });
            } else {
                vm.selectWards = null;
                vm.ward = null;
                vm.adminstrativeUnits_Wards = [];
            }

            vm.pageIndex = 1;
            vm.getListReportFormByPage();
        }
        vm.onFmsadminstrativeUnitWardsSelected = function(item) {
            vm.pageIndex = 1;
            vm.getListReportFormByPage();
        }
        vm.enterSearchReportForm = function() {
            if (event.keyCode == 13) { //Phím Enter
                vm.pageIndex = 1;
                vm.getListReportFormByPage();
            }
        }
        vm.search = function() {
            vm.getListReportFormByPage();
        }
        vm.getListReportFormByPage = function() {
            vm.searchObject = {
                textSearch: vm.textSearch,
                pageIndex: vm.pageIndex,
                pageSize: vm.pageSizeReportForm,
                provinceId: vm.province ? vm.province : null,
                districtId: vm.district ? vm.district : null,
                wardId: vm.ward ? vm.ward : null,
                year: vm.year ? vm.year : null,
                month: vm.month ? vm.month : null,
                day: vm.date ? vm.date : null,
                animalId: vm.animalId ? vm.animalId : null,
                animalClass: vm.animalClass ? vm.animalClass : null,
                animalOrdo: vm.ordo ? vm.ordo : null,
                animalFamily: vm.family ? vm.family : null,
            }
            service.searchByPageReportForm16(vm.searchObject).then(function(data) {
                if (data != null) {
                    vm.listReportForm = data.content;
                    // for(var item of vm.listReportForm){
                    //     //Hien thi lai chinh xac total, total import, exprt
                    //     vm.updateForm16ExistStart(item, item.dateReport);
                    // }
                    vm.totalItems = data.totalElements;
                    console.log("form 16", data); //log form 16
                }
            }, function(fail) {
                toastr.error("Đã có lỗi xảy ra, vui lòng bấm Crl + F5 để thử lại", "Lỗi");
            });
        }
        vm.getListReportFormByPage();

        /** get data */
        vm.searchDto = {};
        vm.listAnimalClass = [];
        vm.listAnimalFamily = [];
        vm.listAnimalOrdo = [];
        vm.getListAnimalClass = function() {
            animalService.getListAnimalClass().then(function(data) {
                vm.listAnimalClass = [];
                vm.animalClass = null;
                vm.ordo = null;
                vm.family = null;
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

        vm.getListAnimalOrdo = function(callSearchFunction) {
            animalService.getListAnimalOrdo(vm.searchDto.animalClass).then(function(data) {
                vm.listAnimalOrdo = [];
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

        vm.getListAnimalFamily = function(callSearchFunction) {
            animalService.getListAnimalFamily(vm.searchDto.animalClass, vm.searchDto.ordo).then(function(data) {
                vm.listAnimalFamily = [];
                vm.family = null;
                data.forEach(item => {
                    var title = '-';
                    if (item && item.trim().length > 0) {
                        title = item;
                    }
                    vm.listAnimalFamily.push({ name: title });
                });
                if (callSearchFunction) {
                    vm.searchByCode();
                }
            });
        }
        vm.getListAnimalClass();
        vm.getListAnimalOrdo();
        vm.getListAnimalFamily();

        vm.animalClassSelected = function() {
            if (vm.animalClass && vm.animalClass == '-') {
                vm.searchDto.animalClass = null;
                vm.searchDto.ordo = null;
                vm.searchDto.family = null;
            } else {
                vm.searchDto.animalClass = vm.animalClass;
                vm.searchDto.ordo = null;
                vm.searchDto.family = null;
            }
            vm.getListAnimalOrdo(true);
            vm.getListReportFormByPage();
        }

        vm.animalOrdoSelected = function() {
            if (vm.ordo && vm.ordo == '-') {
                vm.searchDto.ordo = null;
                vm.searchDto.family = null;
            } else {
                vm.searchDto.ordo = vm.ordo;
                vm.searchDto.family = null;
            }
            vm.getListAnimalFamily(true);
            vm.getListReportFormByPage();
        }

        vm.familySelected = function() {
            if (vm.family && vm.family == '-') {
                vm.searchDto.family = null;
            } else {
                vm.searchDto.family = vm.family;
            }
            vm.searchByCode();
            vm.getListReportFormByPage();
        }
        vm.searchByCode = function() {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            animalService.getPageSearchAnimal(vm.searchDto, 1, 1000000).then(function(data) {
                vm.animals = data.content;
            });
        }
        animalService.getAll().then(function(data) {
            vm.animals = data;
        })

        vm.selecteAnimal = function() {
            vm.getListReportFormByPage();
        }


        //tran huu dat hàm chọn farm start
        //--------------Popup Farm----------//
        vm.pageIndexFarm = 1, vm.pageSizeFarm = 10;
        vm.subModalInstance = {};
        vm.selectedFarms = [];
        vm.farmSelected = {};
        vm.enterSearchFarm = function() {
            if (event.keyCode == 13) { //Phím Enter
                vm.searchByFarm();
            }
        };
        vm.searchByFarm = function() {
            vm.pageIndexFarm = 1;
            vm.bsTableControlFarm.state.pageNumber = 1;
            vm.findByFarm();
        };
        vm.findByFarm = function() {
            if (vm.searchDtoFarm == null) {
                vm.searchDtoFarm = {};
            }
            if (vm.farmCode != null && vm.farmCode != "") {
                vm.searchDtoFarm.nameOrCode = vm.farmCode;
            } else {
                vm.searchDtoFarm.nameOrCode = null;
            }
            const searchDtoFarmTemp2 = angular.copy(vm.searchDtoFarm);
            searchDtoFarmTemp2.showListFarmSelect = true;
            service.getPageSearchFarm(searchDtoFarmTemp2, vm.pageIndexFarm, vm.pageSizeFarm).then(function(data) {
                vm.farmsForm16 = data.content;
                vm.bsTableControlFarm.options.data = vm.farmsForm16;
                vm.bsTableControlFarm.options.totalRows = data.totalElements;
                if (vm.isFamer == true && vm.farmsForm16 != null && vm.farmsForm16.length > 0) {
                    if (vm.importAnimal == null) vm.importAnimal = {};
                    vm.farmName = vm.farmsForm16[0].name;
                    vm.farmCode = vm.farmsForm16[0].code;
                    vm.importAnimal.farm = vm.farmsForm16[0];
                }
            });
        }
        var closeModal = function() {

            vm.subModalInstance.close();

        };

        function checkAgreeFarm() {
            if (angular.isUndefined(vm.selectedFarms) || vm.selectedFarms.length == 0) {
                toastr.warning($translate.instant("exportAnimal.notChooseFarm"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.agreeFarm = function() {
            if (checkAgreeFarm()) {
                vm.farmSelected = vm.selectedFarms[0];
                vm.farm = vm.selectedFarms[0];
                vm.farmName = vm.farm.name;
                vm.importAnimal.farm = vm.farm;
                vm.reportForm16 = {};
                if (vm.reportPeriod.isNew == true) {
                    service.getAllForm16ByFarm(vm.farm.id).then(function(data) {
                        if (data && data.length > 0) {
                            vm.listReportForm16A = data;
                        }
                    });
                }
                vm.farmCode = null;
                closeModal();
            }
        }
        vm.listForm16Temp = []; //bien de check update du lieu khi ng dung sua form 16 da ton tai
        vm.animalSelect = null;
        vm.listReportForm16Animal = [];
        vm.onSelectedAnimal = function(animalChoice) {
            vm.listForm16Temp = [];
            vm.listTotalForm16A = [];
            vm.listReportForm16Animal = [];
            vm.listForm16BeforeSave = [];
            vm.animalSelect = animalChoice;
            for (let i = 0; i < vm.listReportForm16A.length; i++) {
                if (animalChoice.id == vm.listReportForm16A[i].animal.id) {
                    vm.listReportForm16Animal.push(vm.listReportForm16A[i]);
                    vm.listForm16Temp.push(vm.listReportForm16A[i]);
                    vm.listTotalForm16A.push(vm.listReportForm16A[i]);
                    vm.indexRecord = null;
                }
            }
            //debugger
            if (vm.listReportForm16Animal != null && vm.listReportForm16Animal.length > 0) {
                vm.listReportForm16Animal.sort((a, b) => a.dateReport - b.dateReport);
                vm.listForm16Temp.sort((a, b) => a.dateReport - b.dateReport);
                vm.listTotalForm16A.sort((a, b) => a.dateReport - b.dateReport);
            }
            // vm.list16UpdateStart=[];
            // //debugger
            // for(var item of vm.listReportForm16Animal){
            //     //Hien thi lai chinh xac total, total import, exprt
            //     vm.updateForm16ExistStart(item, item.dateReport);
            // }
            //Update total import-export neu hien thi sai: tam thoi chua dung
            // if(vm.list16UpdateStart.length>0){
            //     service.saveListReportForm16(vm.list16UpdateStart).then(function(){
            //         //debugger
            //         toastr.info("Update total import-export!","Thông báo");
            //     }, function(){
            //         toastr.info("Update failed!","Thông báo");
            //     });
            // }
        }

        // animal dduowcj chon de them vao form 16
        vm.listForm16BeforeSave = [];
        vm.listForm16BeforeSaveTemp = []; //bien check update
        vm.currentDate2 = new Date();
        vm.currentDate = new Date().toISOString().split('T')[0];
        //tran huu dat them hàm save start
        vm.addForm16ToList = function() {
            for (let i = 0; i < vm.listTotalForm16A.length; i++) {
                if (vm.reportForm16.dateReport.valueOf() === vm.listTotalForm16A[i].dateReport.valueOf()) {
                    toastr.warning('Đã có ngày trong báo cáo vui lòng chọn ngày khác', 'Cảnh báo');
                    return;
                }
            }
            if (!vm.reportForm16.dateReport) {
                toastr.warning('Chưa chọn ngày', 'Cảnh báo');
                return;
            }
            if (vm.animalSelect) {
                vm.reportForm16.farm = vm.farm;
                vm.reportForm16.animal = vm.animalSelect;
                const item = angular.copy(vm.reportForm16);
                vm.listForm16BeforeSave.push(vm.reportForm16);
                vm.listTotalForm16A.push(vm.reportForm16);
                vm.reportForm16 = {};
                vm.reportForm16.farm = vm.farm;
                vm.reportForm16.animal = vm.animalSelect;
            } else {
                toastr.warning('Chưa chọn loài', 'Cảnh báo');
                return;
            }
        }

        //ham lay ngay de lay du lieu form 16 gan nhat
        vm.form16ATMP;
        vm.indexRecord = null;
        vm.pickDateForm16 = function() {
                let now = new Date();
                if (vm.reportForm16.dateReport) {
                    let monthOfDateReort = new Date(vm.reportForm16.dateReport.getTime()).getMonth() + 1;
                    let yearOfDateReport = new Date(vm.reportForm16.dateReport.getTime()).getFullYear();

                    // switch(Math.floor((now.getMonth()+1)/4)){
                    //     case 0: //quy 1
                    // 		if(monthOfDateReort>=1 && monthOfDateReort<=3 && yearOfDateReport==now.getFullYear()) {
                    // 		}else {
                    // 			toastr.warning('Ngày báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được thêm mới', 'Cảnh báo');
                    //             return;
                    // 		}
                    // 		break;
                    // 	case 1: //quy 2
                    // 		if(monthOfDateReort>=4 && monthOfDateReort<=6 && yearOfDateReport==now.getFullYear()) {
                    // 		}else {
                    // 			toastr.warning('Ngày báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được thêm mới', 'Cảnh báo');
                    //             return;
                    // 		}
                    // 		break;
                    // 	case 2: //quy 3
                    // 		if(monthOfDateReort>=7 && monthOfDateReort<=9 && yearOfDateReport==now.getFullYear()) {
                    // 		}else {
                    // 			toastr.warning('Ngày báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được thêm mới', 'Cảnh báo');
                    //             return;
                    // 		}
                    // 		break;
                    // 	case 3: //quy 4
                    // 		if(monthOfDateReort>=10 && monthOfDateReort<=12 && yearOfDateReport==now.getFullYear()) {
                    // 		}else {
                    // 			toastr.warning('Ngày báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được thêm mới', 'Cảnh báo');
                    //             return;
                    // 		}
                    // 		break;
                    // }
                    let diffDate = null;
                    if (vm.listTotalForm16A.length > 0) {
                        diffDate = -1;
                        for (let i = 0; i < vm.listTotalForm16A.length; i++) {
                            let date1 = new Date(new Date(vm.reportForm16.dateReport.getTime()).toISOString().split('T')[0]);
                            let date2 = new Date(new Date(new Date(vm.listTotalForm16A[i].dateReport).getTime()).toISOString().split('T')[0]);
                            let diffDate1 = (date1.getTime() - date2.getTime()) / (1000 * 3600 * 24);
                            if (diffDate < 0 && diffDate1 > 0) {
                                diffDate = diffDate1;
                                vm.indexRecord = i;
                            }
                            if (diffDate > diffDate1 && diffDate1 > 0) {
                                diffDate = diffDate1;
                                vm.indexRecord = i;
                            }
                        }
                    }
                    if (diffDate > 0) {
                        vm.form16ATMP = vm.listTotalForm16A[vm.indexRecord];
                        vm.reportForm16.maleParent = vm.listTotalForm16A[vm.indexRecord].maleParent;
                        vm.reportForm16.femaleParent = vm.listTotalForm16A[vm.indexRecord].femaleParent;
                        vm.reportForm16.unGenderParent = vm.listTotalForm16A[vm.indexRecord].unGenderParent;

                        vm.reportForm16.maleGilts = vm.listTotalForm16A[vm.indexRecord].maleGilts;
                        vm.reportForm16.femaleGilts = vm.listTotalForm16A[vm.indexRecord].femaleGilts;
                        vm.reportForm16.unGenderGilts = vm.listTotalForm16A[vm.indexRecord].unGenderGilts;

                        vm.reportForm16.maleChildUnder1YearOld = vm.listTotalForm16A[vm.indexRecord].maleChildUnder1YearOld;
                        vm.reportForm16.femaleChildUnder1YearOld = vm.listTotalForm16A[vm.indexRecord].femaleChildUnder1YearOld;
                        vm.reportForm16.childUnder1YearOld = vm.listTotalForm16A[vm.indexRecord].childUnder1YearOld;

                        vm.reportForm16.maleChildOver1YearOld = vm.listTotalForm16A[vm.indexRecord].maleChildOver1YearOld;
                        vm.reportForm16.femaleChildOver1YearOld = vm.listTotalForm16A[vm.indexRecord].femaleChildOver1YearOld;
                        vm.reportForm16.unGenderChildOver1YearOld = vm.listTotalForm16A[vm.indexRecord].unGenderChildOver1YearOld;
                        vm.updateTotalForm16(vm.reportForm16, vm.reportForm16.dateReport);
                    } else {
                        vm.form16ATMP = {}
                        vm.reportForm16.maleParent = null;
                        vm.reportForm16.femaleParent = null;
                        vm.reportForm16.unGenderParent = null;

                        vm.reportForm16.maleGilts = null;
                        vm.reportForm16.femaleGilts = null;
                        vm.reportForm16.unGenderGilts = null;

                        vm.reportForm16.maleChildUnder1YearOld = null;
                        vm.reportForm16.femaleChildUnder1YearOld = null;
                        vm.reportForm16.childUnder1YearOld = null;

                        vm.reportForm16.maleChildOver1YearOld = null;
                        vm.reportForm16.femaleChildOver1YearOld = null;
                        vm.reportForm16.unGenderChildOver1YearOld = null;

                        vm.updateTotalForm16(vm.reportForm16, vm.reportForm16.dateReport);
                    }
                }
            }
            //ham lay ngay de lay du lieu form 16 gan nhat
            //ham cap nhat lai du lieu form 16 khi ng dung sua start
        vm.updateForm16Exist = function(object, date) {
                let diffDate = -1;
                let now = new Date();
                let monthOfDateReort = new Date(date).getMonth() + 1;
                let yearOfDateReport = new Date(date).getFullYear();
                // switch(Math.floor((now.getMonth()+1)/4)){
                //     case 0: //quy 1
                // 		if(monthOfDateReort>=1 && monthOfDateReort<=3 && yearOfDateReport==now.getFullYear()) {
                // 		}else {
                // 			toastr.warning('Báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được cập nhật', 'Cảnh báo');
                //             return;
                // 		}
                // 		break;
                // 	case 1: //quy 2
                // 		if(monthOfDateReort>=4 && monthOfDateReort<=6 && yearOfDateReport==now.getFullYear()) {
                // 		}else {
                // 			toastr.warning('Báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được cập nhật', 'Cảnh báo');
                //             return;
                // 		}
                // 		break;
                // 	case 2: //quy 3
                // 		if(monthOfDateReort>=7 && monthOfDateReort<=9 && yearOfDateReport==now.getFullYear()) {
                // 		}else {
                // 			toastr.warning('Báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được cập nhật', 'Cảnh báo');
                //             return;
                // 		}
                // 		break;
                // 	case 3: //quy 4
                // 		if(monthOfDateReort>=10 && monthOfDateReort<=12 && yearOfDateReport==now.getFullYear()) {
                // 		}else {
                // 			toastr.warning('Báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được cập nhật', 'Cảnh báo');
                //             return;
                // 		}
                // 		break;
                // }
                for (let i = 0; i < vm.listTotalForm16A.length; i++) {
                    let date1 = new Date(new Date(new Date(date).getTime()).toISOString().split('T')[0]);
                    let date2 = new Date(new Date(new Date(vm.listTotalForm16A[i].dateReport).getTime()).toISOString().split('T')[0]);
                    let diffDate1 = (date1.getTime() - date2.getTime()) / (1000 * 3600 * 24);
                    if (diffDate < 0 && diffDate1 > 0) {
                        diffDate = diffDate1;
                        vm.indexRecord = i;
                    }
                    if (diffDate > diffDate1 && diffDate1 > 0) {
                        diffDate = diffDate1;
                        vm.indexRecord = i;
                    }
                }
                if (diffDate > 0) {
                    vm.form16ATMP = vm.listTotalForm16A[vm.indexRecord];

                    object.maleParent = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].maleParent, object.importMaleParent, -object.exportMaleParent)
                    object.femaleParent = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].femaleParent, object.importFemaleParent, -object.exportFemaleParent)
                    object.maleGilts = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].maleGilts, object.importMaleGilts, -object.exportMaleGilts)
                    object.femaleGilts = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].femaleGilts, object.importFemaleGilts, -object.exportFemaleGilts)
                    object.childUnder1YearOld = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].childUnder1YearOld, object.importChildUnder1YearOld, -object.exportChildUnder1YearOld)
                    object.maleChildOver1YearOld = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].maleChildOver1YearOld, object.importMaleChildOver1YearOld, -object.exportMaleChildOver1YearOld)
                    object.femaleChildOver1YearOld = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].femaleChildOver1YearOld, object.importFemaleChildOver1YearOld, -object.exportFemaleChildOver1YearOld)
                    object.unGenderChildOver1YearOld = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].unGenderChildOver1YearOld, object.importUnGenderChildOver1YearOld, -object.exportUnGenderChildOver1YearOld)
                    vm.updateTotalForm16(object, date);
                }

            }
            //ham cap nha lai du lieu form 16 khi ng dung sua end
            //tran huu dat ham cap nhat so luong nhap start
        vm.updateImportExport = function(object) {
                if (!vm.form16ATMP) {
                    vm.form16ATMP = {};
                }
                object.maleParent = vm.calcToalReportForm16(vm.form16ATMP.maleParent, object.importMaleParent, -object.exportMaleParent)
                object.femaleParent = vm.calcToalReportForm16(vm.form16ATMP.femaleParent, object.importFemaleParent, -object.exportFemaleParent)
                object.unGenderParent = vm.calcToalReportForm16(vm.form16ATMP.unGenderParent, object.importUnGenderParent, -object.exportUnGenderParent)

                object.maleGilts = vm.calcToalReportForm16(vm.form16ATMP.maleGilts, object.importMaleGilts, -object.exportMaleGilts)
                object.femaleGilts = vm.calcToalReportForm16(vm.form16ATMP.femaleGilts, object.importFemaleGilts, -object.exportFemaleGilts)
                object.unGenderGilts = vm.calcToalReportForm16(vm.form16ATMP.unGenderGilts, object.importUnGenderGilts, -object.exportUnGenderGilts)

                object.maleChildUnder1YearOld = vm.calcToalReportForm16(vm.form16ATMP.maleChildUnder1YearOld, object.importMaleChildUnder1YearOld, -object.exportMaleChildUnder1YearOld)
                object.femaleChildUnder1YearOld = vm.calcToalReportForm16(vm.form16ATMP.femaleChildUnder1YearOld, object.importFemaleChildUnder1YearOld, -object.exportFemaleChildUnder1YearOld)
                object.childUnder1YearOld = vm.calcToalReportForm16(vm.form16ATMP.childUnder1YearOld, object.importChildUnder1YearOld, -object.exportChildUnder1YearOld)

                object.maleChildOver1YearOld = vm.calcToalReportForm16(vm.form16ATMP.maleChildOver1YearOld, object.importMaleChildOver1YearOld, -object.exportMaleChildOver1YearOld)
                object.femaleChildOver1YearOld = vm.calcToalReportForm16(vm.form16ATMP.femaleChildOver1YearOld, object.importFemaleChildOver1YearOld, -object.exportFemaleChildOver1YearOld)
                object.unGenderChildOver1YearOld = vm.calcToalReportForm16(vm.form16ATMP.unGenderChildOver1YearOld, object.importUnGenderChildOver1YearOld, -object.exportUnGenderChildOver1YearOld)

                object.totalImport = vm.calcToalReportForm16(object.importMaleParent, object.importFemaleParent, object.importUnGenderParent, object.importMaleGilts, object.importFemaleGilts, object.importUnGenderGilts,
                    object.importMaleChildUnder1YearOld, object.importFemaleChildUnder1YearOld, object.importChildUnder1YearOld, object.importMaleChildOver1YearOld, object.importFemaleChildOver1YearOld, object.importUnGenderChildOver1YearOld);
                object.totalExport = vm.calcToalReportForm16(object.exportMaleParent, object.exportFemaleParent, object.exportUnGenderParent, object.exportMaleGilts, object.exportFemaleGilts,
                    object.exportUnGenderGilts, object.exportMaleChildUnder1YearOld, object.exportFemaleChildUnder1YearOld, object.exportChildUnder1YearOld, object.exportMaleChildOver1YearOld, object.exportFemaleChildOver1YearOld, object.exportUnGenderChildOver1YearOld);
                vm.updateTotalForm16(vm.reportForm16, vm.reportForm16.dateReport);
            }
            //tran huu dat ham cap nhat so luong nhap end
        vm.updateForm16ExistStart = function(object, date) {
            let diffDate = -1;
            let now = new Date();
            let monthOfDateReort = new Date(date).getMonth() + 1;
            let yearOfDateReport = new Date(date).getFullYear();

            for (let i = 0; i < vm.listTotalForm16A.length; i++) {
                let date1 = new Date(new Date(new Date(date).getTime()).toISOString().split('T')[0]);
                let date2 = new Date(new Date(new Date(vm.listTotalForm16A[i].dateReport).getTime()).toISOString().split('T')[0]);
                let diffDate1 = (date1.getTime() - date2.getTime()) / (1000 * 3600 * 24);
                if (diffDate < 0 && diffDate1 > 0) {
                    diffDate = diffDate1;
                    vm.indexRecord = i;
                }
                if (diffDate > diffDate1 && diffDate1 > 0) {
                    diffDate = diffDate1;
                    vm.indexRecord = i;
                }
            }
            if (diffDate > 0) {
                vm.form16ATMP = vm.listTotalForm16A[vm.indexRecord];

                object.maleParent = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].maleParent, object.importMaleParent, -object.exportMaleParent)
                object.femaleParent = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].femaleParent, object.importFemaleParent, -object.exportFemaleParent)
                object.maleGilts = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].maleGilts, object.importMaleGilts, -object.exportMaleGilts)
                object.femaleGilts = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].femaleGilts, object.importFemaleGilts, -object.exportFemaleGilts)
                object.childUnder1YearOld = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].childUnder1YearOld, object.importChildUnder1YearOld, -object.exportChildUnder1YearOld)
                object.maleChildOver1YearOld = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].maleChildOver1YearOld, object.importMaleChildOver1YearOld, -object.exportMaleChildOver1YearOld)
                object.femaleChildOver1YearOld = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].femaleChildOver1YearOld, object.importFemaleChildOver1YearOld, -object.exportFemaleChildOver1YearOld)
                object.unGenderChildOver1YearOld = vm.calcToalReportForm16(vm.listTotalForm16A[vm.indexRecord].unGenderChildOver1YearOld, object.importUnGenderChildOver1YearOld, -object.exportUnGenderChildOver1YearOld)
                    //for Update total import-export---
                var ti = vm.calcToalReportForm16(object.importMaleParent, object.importFemaleParent, object.importUnGenderParent, object.importMaleGilts, object.importFemaleGilts, object.importUnGenderGilts,
                    object.importMaleChildUnder1YearOld, object.importFemaleChildUnder1YearOld, object.importChildUnder1YearOld, object.importMaleChildOver1YearOld, object.importFemaleChildOver1YearOld, object.importUnGenderChildOver1YearOld);
                var te = vm.calcToalReportForm16(object.exportMaleParent, object.exportFemaleParent, object.exportUnGenderParent, object.exportMaleGilts, object.exportFemaleGilts,
                    object.exportUnGenderGilts, object.exportMaleChildUnder1YearOld, object.exportFemaleChildUnder1YearOld, object.exportChildUnder1YearOld, object.exportMaleChildOver1YearOld, object.exportFemaleChildOver1YearOld, object.exportUnGenderChildOver1YearOld);
                //vm.list16UpdateStart = [];
                if (object.totalImport != ti || object.totalExport != te) {
                    object.totalImport = ti;
                    object.totalExport = te;
                    vm.list16UpdateStart.push(object);
                }
                //---
                object.totalImport = vm.calcToalReportForm16(object.importMaleParent, object.importFemaleParent, object.importUnGenderParent, object.importMaleGilts, object.importFemaleGilts, object.importUnGenderGilts,
                    object.importMaleChildUnder1YearOld, object.importFemaleChildUnder1YearOld, object.importChildUnder1YearOld, object.importMaleChildOver1YearOld, object.importFemaleChildOver1YearOld, object.importUnGenderChildOver1YearOld);
                object.totalExport = vm.calcToalReportForm16(object.exportMaleParent, object.exportFemaleParent, object.exportUnGenderParent, object.exportMaleGilts, object.exportFemaleGilts,
                    object.exportUnGenderGilts, object.exportMaleChildUnder1YearOld, object.exportFemaleChildUnder1YearOld, object.exportChildUnder1YearOld, object.exportMaleChildOver1YearOld, object.exportFemaleChildOver1YearOld, object.exportUnGenderChildOver1YearOld);
                vm.updateTotalForm16(object, date);
            }

        }
        vm.saveReportForm16 = function() {
            console.log(vm.reportForm16);
            //debugger
            service.getAdministrativeUnitDtoByLoginUser().then(function(data) {
                vm.adminstrativeUnits = data;
                if (vm.adminstrativeUnits.length == 0) {
                    service.getCurrentUser().then(function(data) {
                        vm.user = data;
                        var roles = data.roles;
                        if (roles != null && roles.length > 0) {
                            for (var i = 0; i < roles.length; i++) {
                                if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == "role_admin") {
                                    if (vm.reportForm16.dateReport) {
                                        for (let i = 0; i < vm.listTotalForm16A.length; i++) {
                                            if (vm.reportForm16.dateReport.valueOf() === vm.listTotalForm16A[i].dateReport.valueOf()) {
                                                toastr.warning('Đã có ngày trong báo cáo vui lòng chọn ngày khác', 'Cảnh báo');
                                                return;
                                            }
                                        }
                                    }

                                    if (vm.animalSelect) {
                                        if (!vm.reportForm16.dateReport && vm.reportForm16.total) {

                                            toastr.warning('Ngày khai báo không hợp lệ', 'Cảnh báo');
                                            return;
                                        } else {
                                            if (!vm.reportForm16.dateReport && !vm.reportForm16.total ) {
                                            } else {
                                                vm.reportForm16.farm = vm.farm;
                                                vm.reportForm16.animal = vm.animalSelect;
                                                const item = angular.copy(vm.reportForm16);
                                                vm.listForm16BeforeSave.push(vm.reportForm16);
                                                vm.listTotalForm16A.push(vm.reportForm16);
                                                vm.reportForm16 = {};
                                            }
                                        }
                                    } else {
                                        toastr.warning('Chưa chọn loài', 'Cảnh báo');
                                        return;
                                    }
                                    service.saveListReportForm16(vm.listForm16BeforeSave.concat(vm.listReportForm16Animal)).then(function(data) {
                                        vm.modalFormPopup16A.close();
                                        vm.search();
                                    })
                                } else {
                                    toastr.warning("Bạn không có quyền thực hiện thao tác này.", "Cảnh báo");
                                    return;
                                }
                            }
                        }
                    })
                } else {
                    service.getUserAdminstrativeEditTableUnit(vm.adminstrativeUnits[0].id).then(function(data) {
                        vm.userEnableEdit = data;
                        console.log("vu van duc");
                        console.log(vm.userEnableEdit);
                        if (data) {
                            if (vm.reportForm16.dateReport) {
                                for (let i = 0; i < vm.listTotalForm16A.length; i++) {
                                    if (vm.reportForm16.dateReport.valueOf() === vm.listTotalForm16A[i].dateReport.valueOf()) {
                                        toastr.warning('Đã có ngày trong báo cáo vui lòng chọn ngày khác', 'Cảnh báo');
                                        return;
                                    }
                                }
                            }

                            if (vm.animalSelect) {
                                if (!vm.reportForm16.dateReport && vm.reportForm16.total) {
                                    toastr.warning('Ngày khai báo không hợp lệ', 'Cảnh báo');
                                    return;
                                } else {
                                    if (vm.reportForm16.dateReport) {
                                        vm.reportForm16.farm = vm.farm;
                                        vm.reportForm16.animal = vm.animalSelect;
                                        const item = angular.copy(vm.reportForm16);
                                        vm.listForm16BeforeSave.push(vm.reportForm16);
                                        vm.listTotalForm16A.push(vm.reportForm16);
                                        vm.reportForm16 = {};
                                    }
                                }
                            } else {
                                toastr.warning('Chưa chọn loài', 'Cảnh báo');
                                return;
                            }
                            service.saveListReportForm16(vm.listForm16BeforeSave.concat(vm.listReportForm16Animal)).then(function(data) {
                                vm.modalFormPopup16A.close();
                                vm.search();
                            })
                        } else {
                            toastr.warning("Bạn không có quyền thực hiện thao tác này.", "Cảnh báo");
                            return;
                        }
                    })
                }
            })

        }


        vm.removeForm16 = function(reportForm16Id) {
            if (reportForm16Id != null) {
                alert(reportForm16Id);
            }
        }

        //tran huu dat them ham save end


        vm.bsTableControlFarm = {
            options: {
                data: vm.farmsForm16,
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
                onCheck: function(row, $element) {
                    if (vm.selectedFarms != null) {
                        vm.selectedFarms = [];
                    }
                    $scope.$apply(function() {
                        vm.selectedFarms.push(row);
                    });
                },

                onUncheck: function(row, $element) {

                    if (vm.selectedFarms != null) {
                        vm.selectedFarms = [];
                    }
                },

                onPageChange: function(index, pageSize) {
                    vm.pageSizeFarm = pageSize;
                    vm.pageIndexFarm = index;
                    //vm.searchTeachers();
                    vm.findByFarm();
                }
            }
        };

        // tran huu dat ham lay danh sach cach farm trong co so.
        vm.showPopupFarm = function() {
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
                    vm.findByFarm();
                }
                vm.subModalInstance.result.then(function(confirm) {
                    if (confirm == 'yes') {

                    }
                }, function() {
                    vm.farmCode = null;
                });
            }
            //load user administrativeunit

        // function getAdministrativeUnitDtoByLoginUser() {
        //     service.getAdministrativeUnitDtoByLoginUser().then(function (data) {
        //         vm.adminstrativeUnits = data;
        //         if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
        //             getDataByCombobox(vm.adminstrativeUnits[0]);
        //         }
        //     });
        // }

        //tran huu dat hàm chọn farm end
        vm.calcToalReportForm16 = function(...params) {
            let total = 0;
            if (params && params.length > 0) {
                params.forEach(function(item) {
                    if (item) {
                        total += parseInt(item);
                    }
                });
            }
            return total;
        }
        vm.updateTotalForm16 = function(obj, date) {
                let now = new Date();
                let monthOfDateReort = new Date(date).getMonth() + 1;
                let yearOfDateReport = new Date(date).getFullYear();
                // switch(Math.floor((now.getMonth()+1)/4)){
                //     case 0: //quy 1
                // 		if(monthOfDateReort>=1 && monthOfDateReort<=3 && yearOfDateReport==now.getFullYear()) {
                // 		}else {
                // 			toastr.warning('Báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được cập nhật', 'Cảnh báo');
                //             return;
                // 		}
                // 		break;
                // 	case 1: //quy 2
                // 		if(monthOfDateReort>=4 && monthOfDateReort<=6 && yearOfDateReport==now.getFullYear()) {
                // 		}else {
                // 			toastr.warning('Báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được cập nhật', 'Cảnh báo');
                //             return;
                // 		}
                // 		break;
                // 	case 2: //quy 3
                // 		if(monthOfDateReort>=7 && monthOfDateReort<=9 && yearOfDateReport==now.getFullYear()) {
                // 		}else {
                // 			toastr.warning('Báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được cập nhật', 'Cảnh báo');
                //             return;
                // 		}
                // 		break;
                // 	case 3: //quy 4
                // 		if(monthOfDateReort>=10 && monthOfDateReort<=12 && yearOfDateReport==now.getFullYear()) {
                // 		}else {
                // 			toastr.warning('Báo cáo không nằm trong quý hiện tại, dữ liệu bạn sửa sẽ không được cập nhật', 'Cảnh báo');
                //             return;
                // 		}
                // 		break;
                // }
                // if(!obj.check) {
                // }
                obj.total = 0;
                obj.male = 0;
                obj.female = 0;
                obj.unGender = 0;
                obj.male = vm.calcToalReportForm16(obj.maleParent, obj.maleGilts, obj.maleChildUnder1YearOld, obj.maleChildOver1YearOld);
                obj.female = vm.calcToalReportForm16(obj.femaleParent, obj.femaleGilts, obj.femaleChildUnder1YearOld, obj.femaleChildOver1YearOld);
                obj.unGender = vm.calcToalReportForm16(obj.unGenderParent, obj.unGenderGilts, obj.childUnder1YearOld, obj.unGenderChildOver1YearOld);
                obj.total = obj.male + obj.female + obj.unGender;
            }
            //tran huu dat thêm hàm thêm mới form 16 trong mẫu sổ giấy start
        vm.importReason = [];
        for (let i = 0; i < settings.ImportReason.length; i++) {
            vm.importReason.push(settings.ImportReason[i]);
        }
        vm.exportReason = [];
        for (let i = 0; i < settings.ExportReason.length; i++) {
            vm.exportReason.push(settings.ExportReason[i]);
        }
        vm.removeForm16ToList = function(index) {
                vm.listForm16BeforeSave.splice(index, 1);
                vm.listTotalForm16A = vm.listReportForm16Animal.concat(vm.listForm16BeforeSave);
            }
            //bien tong 
        vm.addEditForm16Popup = function(form16, type) {
                vm.disabledEditFarm = true;
                vm.onSelectMonthForm16();
                vm.listReportForm16A = [];
                vm.farm = null;
                vm.reportForm16 = {};
                vm.listForm16BeforeSave = [];
                vm.listTotalForm16A = [];
                vm.listReportForm16Animal = [];
                vm.listForm16Temp = [];
                if (form16) {
                    vm.disabledEditFarm = false;
                    vm.farm = form16.farm;
                    vm.reportForm16.animal = form16.animal;
                    service.getAllForm16ByFarm(form16.farm.id).then(function(data) {
                        if (data && data.length > 0) {
                            vm.listReportForm16A = data;
                            vm.onSelectedAnimal(form16.animal);
                        }
                    });

                } else {
                    vm.reportPeriod = {};
                    vm.reportPeriod.isNew = true;
                    vm.form16Year = new Date().getFullYear();
                    vm.form16Month = new Date().getMonth() + 1;
                    vm.form16Date = new Date().getDate();
                }

                if (type == 1) { // FORM A
                    vm.modalFormPopup16A = modal.open({
                        animation: true,
                        templateUrl: 'add_form16_in_paper_template.html',
                        scope: $scope,
                        backdrop: 'static',
                        keyboard: false,
                        windowClass: "customer-modal-lg",
                        size: 'lg'
                    });
                } else if (type == 2) { // FORM B
                    vm.modalFormPopup16A = modal.open({
                        animation: true,
                        templateUrl: 'add_form16_in_paper_template.html',
                        scope: $scope,
                        backdrop: 'static',
                        windowClass: "customer-modal-lg",
                        keyboard: false,
                        size: 'lg'
                    });
                }
            }
            //tran huu dat thêm hàm thêm mới form 16 trong mẫu sổ giấy start

        //tran huu dat them ham xoa form 16
        vm.recordId = null;
        vm.indexRemove = null;
        vm.deleteForm16Record = function(index, id) {
            if (id) {
                vm.recordId = id;
                vm.indexRemove = index;
            } else {
                vm.recordId = null;
                vm.indexRemove = null;
            }
            service.getForm16ById(id).then(function(res16) {
                //debugger
                var report = res16;
                var dateReport;
                if (typeof report.dateReport === "undefined") {
                    dateReport = new Date();
                } else {
                    dateReport = new Date(report.dateReport);
                }
                var dateReportMonth = dateReport.getMonth() + 1;
                var dateReportYear = dateReport.getFullYear();
                service.getAdministrativeUnitDtoByLoginUser().then(function(data) {
                    //debugger
                    vm.adminstrativeUnits = data;
                    var editable = false;
                    var today = new Date();
                    // var todayDate = String(today.getDate()).padStart(2, '0');
                    var todayMonth = String(today.getMonth() + 1).padStart(2, '0');
                    var todayYear = today.getFullYear();
                    var todayQuarter = 0;
                    if (todayMonth <= 3) {
                        todayQuarter = 1;
                    } else if (todayMonth > 3 && todayMonth <= 6) {
                        todayQuarter = 2;
                    } else if (todayMonth > 6 && todayMonth <= 9) {
                        todayQuarter = 3;
                    } else {
                        todayQuarter = 4;
                    }
                    var periodQuarter = 0;
                    if (dateReportMonth) {
                        if (dateReportMonth <= 3) {
                            periodQuarter = 1;
                        } else if (dateReportMonth > 3 && dateReportMonth <= 6) {
                            periodQuarter = 2;
                        } else if (dateReportMonth > 6 && dateReportMonth <= 9) {
                            periodQuarter = 3;
                        } else {
                            periodQuarter = 4;
                        }
                    }
                    if ((todayYear == dateReportYear && (todayQuarter === periodQuarter)) || vm.isRoleAdmin == true) {
                        vm.modalConfirmDelete = modal.open({
                            animation: true,
                            templateUrl: 'confirm_delete_record.html',
                            scope: $scope,
                            backdrop: 'static',
                            keyboard: false,
                            size: 'md'
                        });
                    } else {
                        if (vm.adminstrativeUnits.length == 0) {
                            toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                            return;
                        }
                        for (let i = 0; i < vm.adminstrativeUnits.length; i++) {
                            service.getUserAdminstrativeEditTableUnit(vm.adminstrativeUnits[i].parentId != null ? vm.adminstrativeUnits[i].parentId : vm.adminstrativeUnits[i].id).then(function(data1) {
                                //debugger
                                vm.userEnableEdit = data1;
                                if (vm.userEnableEdit && vm.userEnableEdit.length > 0) {
                                    for (let j = 0; j < vm.userEnableEdit.length; j++) {
                                        if (vm.userEnableEdit[j].year == dateReportYear && vm.userEnableEdit[j].editable == true) {
                                            var temp = 0;
                                            if (dateReportMonth <= 3) {
                                                temp = 1;
                                            } else if (dateReportMonth > 3 && dateReportMonth <= 6) {
                                                temp = 2;
                                            } else if (dateReportMonth > 6 && dateReportMonth <= 9) {
                                                temp = 3;
                                            } else {
                                                temp = 4;
                                            }

                                            if (vm.userEnableEdit[j].quater == temp) {
                                                editable = true;
                                            }
                                            if (vm.userEnableEdit[j].quater == null || vm.userEnableEdit[j].quater == '' || typeof vm.userEnableEdit[j].quater == 'undefined') {
                                                editable = true;
                                            }
                                        }
                                    }
                                    if (editable == true) {
                                        vm.modalConfirmDelete = modal.open({
                                            animation: true,
                                            templateUrl: 'confirm_delete_record.html',
                                            scope: $scope,
                                            backdrop: 'static',
                                            keyboard: false,
                                            size: 'md'
                                        });
                                    } else {
                                        toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                                        return;
                                    }
                                } else {
                                    toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                                    return;
                                }
                            });
                        }
                    }

                });
            });
        }
        vm.confirmDeleteRecord = function() {
            if (vm.recordId) {
                service.deleteForm16ById(vm.recordId).then(function(data) {
                    if (data != null) {
                        toastr.warning("Xóa thành công", "Cảnh báo");
                    }
                    vm.listReportForm16Animal.splice(vm.indexRemove, 1);
                    vm.listTotalForm16A.splice(vm.indexRemove, 1);
                    vm.modalConfirmDelete.close();
                });

            }
        }
        $scope.getDataDistrictAndWard = function() {
            $scope.myBlobObjectDataDistrictAndWard = undefined;
            const searchDto = {};
            if (!vm.textSearch){
                if (!vm.province) {
                    toastr.warning("Chưa chọn Tỉnh/Thành phố.", "Cảnh báo");
                    return;
                }
                if (!vm.district) {
                    toastr.warning("Chưa chọn Huyện/Thị xã.", "Cảnh báo");
                    return;
                }
            }
            // if (!vm.year) {
            //     toastr.warning("Chưa chọn năm.", "Cảnh báo");
            //     return;
            // }
            searchDto.textSearch = vm.textSearch,
            searchDto.year = vm.year ? vm.year : null;
            searchDto.month = vm.month ? vm.month : null;
            searchDto.day = vm.date ? vm.date : null;
            searchDto.provinceId = vm.province ? vm.province : null
            searchDto.districtId = vm.district ? vm.district : null
            searchDto.wardId = vm.ward ? vm.ward : null
            searchDto.pageIndex = vm.pageIndex ? vm.pageIndex : null
            searchDto.pageSize = vm.pageSizeReportForm ? vm.pageSizeReportForm : null
            // service.getDataDistrictAndWard(searchDto)
            //     .then(function(data) { //is important that the data was returned as Aray Buffer
            //         var file = new Blob([data], {
            //             type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
            //         });
            //         FileSaver.saveAs(file, "DuLieuHuyenXa.xlsx");
            //     }, function(fail) {
            //         console.log('Download Error, stop animation and show error message');
            //         $scope.myBlobObjectFileImportExcel = [];
            //     });
            service.exportData(searchDto)
                .then(function(data) { //is important that the data was returned as Aray Buffer
                    var file = new Blob([data], {
                        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                    });
                    FileSaver.saveAs(file, "DuLieuHuyenXa.xlsx");
                }, function(fail) {
                    console.log('Download Error, stop animation and show error message');
                    $scope.myBlobObjectFileImportExcel = [];
                });
        };
    }

})();