/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ReportPeriod').controller('PageTemplate16DController', PageTemplate16DController);

    PageTemplate16DController.$inject = [
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
    angular.module('Education.ReportPeriod').directive('fileDownload', function () {
        return {
            restrict: 'A',
            scope: {
                fileDownload: '=',
                fileName: '=',
            },
            link: function (scope, elem, atrs) {
                scope.$watch('fileDownload', function (newValue, oldValue) {
                    if (newValue != undefined && newValue != null) {
                        console.debug('Downloading a new file');
                        var isFirefox = typeof InstallTrigger !== 'undefined';
                        var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
                        var isIE = /*@cc_on!@*/false || !!document.documentMode;
                        var isEdge = !isIE && !!window.StyleMedia;
                        var isChrome = !!window.chrome && !!window.chrome.webstore || window.chrome != null;;
                        var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
                        var isBlink = (isChrome || isOpera) && !!window.CSS;

                        if (isFirefox || isIE || isChrome) {
                            if (isChrome) {
                                console.log('Manage Google Chrome download');
                                var url = window.URL || window.webkitURL;
                                var fileURL = url.createObjectURL(scope.fileDownload);
                                var downloadLink = angular.element('<a></a>');//create a new  <a> tag element
                                downloadLink.attr('href', fileURL);
                                downloadLink.attr('download', scope.fileName);
                                downloadLink.attr('target', '_self');
                                downloadLink[0].click();//call click function
                                url.revokeObjectURL(fileURL);//revoke the object from URL
                            }
                            if (isIE) {
                                console.log('Manage IE download>10');
                                window.navigator.msSaveOrOpenBlob(scope.fileDownload, scope.fileName);
                            }
                            if (isFirefox) {
                                console.log('Manage Mozilla Firefox download');
                                var url = window.URL || window.webkitURL;
                                var fileURL = url.createObjectURL(scope.fileDownload);
                                var a = elem[0];//recover the <a> tag from directive
                                a.href = fileURL;
                                a.download = scope.fileName;
                                a.target = '_self';
                                a.click();//we call click function
                            }


                        } else {
                            alert('SORRY YOUR BROWSER IS NOT COMPATIBLE');
                        }
                    }
                });

            }
        }
    });

    function PageTemplate16DController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, util, Uploader, FileSaver, farmService, animalService, $translate) {
        $scope.$on('$viewContentLoaded', function () {
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
        vm.animal = null;
        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
        vm.isSearchExtend = false;
        vm.isSdahView = false;//cấp tỉnh nhưng chỉ được xem

        vm.isSelectProvince = false;
        vm.isSelectDistrict = false;
        vm.isSelectWard = false;
        vm.province = null;
        vm.district = null;
        vm.ward = null;

        vm.isRoleAdmin = false;

        $scope.years = [];
        var today = new Date();
        vm.currentYear=today.getFullYear();
		for(var i=vm.currentYear; i >= 2000; i--) {
			$scope.years.push({value: i, name: i+""});
        }
        vm.pageSizeReportForm = 25;
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
                    if(roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_admin'){
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
            if (vm.isRole) {//trường hợp admin và dlp thì được xem tất cả các cơ sở
                getAllProvince();
                vm.getListReportFormByPage();
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
            if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') {//trường hợp tài khoản nông dân
                //gán tên cơ sở chăn nuôi
                vm.textSearch = vm.user.username;
                // console.log("username");
                //console.log(vm.user.username);
            }
            vm.getListReportFormByPage();
        }
        vm.form16Year = vm.currentYear;
        vm.form16Month = vm.currentMonth;
        vm.onSelectMonthForm16 = function () {
            vm.days = [];
            const lastDayOfMonth = moment(new Date(vm.form16Year, vm.form16Month - 1, 1)).endOf('month').toDate().getDate();
            for (let dayIndex = 1; dayIndex <= lastDayOfMonth; dayIndex++) {
                vm.days.push({ value: dayIndex, name: dayIndex + "" });
            }
        }
        vm.onSelectDateForm16  = function(item){
            vm.getListReportFormByPage();
        }
        vm.onSelectMonth = function (item) {
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
        vm.onSelectYear = function () {
            vm.days = [];
            vm.date = null;
            const lastDayOfMonth = moment(new Date(vm.year, vm.month - 1, 1)).endOf('month').toDate().getDate();
            for (let dayIndex = 1; dayIndex <= lastDayOfMonth; dayIndex++) {
                vm.days.push({ value: dayIndex, name: dayIndex + "" });
            }
            vm.getListReportFormByPage();
        }
        vm.onSelectDateForm = function(){
            vm.getListReportFormByPage();
        }
        function getAllProvince() {
            service.getAdminstrativeUnitsCity().then(function (data) {
                vm.adminstrativeUnits_City = data || [];
            });
        }
        vm.onRemovedDate = function(){
            vm.pageIndex = 1;
            vm.getListReportFormByPage();
        }  
        vm.onRemovedMonth = function(){
            vm.pageIndex = 1;
            vm.getListReportFormByPage();
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

            vm.pageIndex = 1;
            vm.getListReportFormByPage();
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

            vm.pageIndex = 1;
            vm.getListReportFormByPage();
        }
        vm.onFmsadminstrativeUnitWardsSelected = function (item) {
            vm.pageIndex = 1;
            vm.getListReportFormByPage();
        }
        vm.enterSearchReportForm = function(){
            if (event.keyCode == 13) {//Phím Enter
                vm.pageIndex = 1;
                vm.getListReportFormByPage();
            }
        }
        vm.search = function(){
			vm.pageIndex = 1;
			vm.getListReportFormByPage();
            /*if(vm.textSearch && vm.textSearch.trim() != ""){
                vm.getListReportFormByPage();
            }*/
        }
        vm.getListReportFormByPage = function () {
            vm.searchObject = {
                textSearch: vm.textSearch,
                pageIndex: vm.pageIndex ,
                pageSize: vm.pageSizeReportForm,
                provinceId: vm.province ? vm.province : null,
                districtId: vm.district ? vm.district : null,
                wardId: vm.ward ? vm.ward: null,
                year: vm.year ? vm.year : null,
                month: vm.month ? vm.month : null,
                day: vm.date ? vm.date : null,
                animalId: vm.animalId ? vm.animalId : null,
                animalClass: vm.animalClass ? vm.animalClass : null,
                animalOrdo: vm.ordo ? vm.ordo : null,
                animalFamily: vm.family ? vm.family : null,
				fromDate:vm.fromDate?vm.fromDate:null,
				toDate:vm.toDate?vm.toDate:null
            }
			if(changeDate()){
				service.searchByPageReportFormAnimalGiveBirth(vm.searchObject).then(function (data) {
					if (data != null) {
						vm.listReportForm = data.content;
						vm.totalItems = data.totalElements;
					}
				}, function (fail) {
					toastr.error("Đã có lỗi xảy ra, vui lòng bấm Crl + F5 để thử lại", "Lỗi");
				});
			}
            
        }
		function changeDate(){
			 if(vm.fromDate!=null && vm.toDate!=null && vm.fromDate>vm.toDate){
				 toastr.warning($translate.instant("importAnimal.toDateBeforeFrom"), $translate.instant("toastr.warning"));
				return false;
			}
			return true;
		}
        // vm.getListReportFormByPage();

        /** get data */
        vm.searchDto = {};
        vm.listAnimalClass = [];
        vm.listAnimalFamily = [];
        vm.listAnimalOrdo = [];
        vm.getListAnimalClass = function () {
            animalService.getAll().then(function(data){
              //  //debugger
                vm.listAnimal16D=data.filter(x=>x.reproductionForm==1);
                animalService.getListAnimalClass().then(function (data) {
                //   //debugger
                    vm.listAnimalClass = [];
                    vm.animalClass = null;
                    vm.ordo = null;
                    vm.family = null;
                    data.forEach(item => {
                        // //debugger
                        var title = '-';
                        if (item && item.trim().length > 0) {
                            title = item;
                        }
                        var check = vm.listAnimal16D.filter(x=>x.animalClass==item);
                        if(check.length>0||item==""){
                            vm.listAnimalClass.push({ name: title });
                        }
                    });
                });
    
                // vm.getListAnimalOrdo();
                // ==========================================
                animalService.getListAnimalOrdo(vm.searchDto.animalClass).then(function (data) {
                    vm.listAnimalOrdo = [];
                    vm.ordo = null;
                    vm.family = null;
                    data.forEach(item => {
                        var title = '-';
                        if (item && item.trim().length > 0) {
                            title = item;
                        }
                        var check = vm.listAnimal16D.filter(x=>x.ordo==item);
                        if(check.length>0||item==""){
                            vm.listAnimalOrdo.push({ name: title });
                        }
                    });
                    // vm.getListAnimalFamily(data1);
                });
                // ==========================================
                animalService.getListAnimalFamily(vm.searchDto.animalClass, vm.searchDto.ordo).then(function (data) {
                    vm.listAnimalFamily = [];
                    vm.family = null;
                    data.forEach(item => {
                        var title = '-';
                        if (item && item.trim().length > 0) {
                            title = item;
                        }
                        var check = vm.listAnimal16D.filter(x=>x.family==item);
                        if(check.length>0||item==""){
                            vm.listAnimalFamily.push({ name: title });
                        }
                    });
                    // if (callSearchFunction) {
                    //     vm.searchByCode();
                    // }
                });
                // ==========================================
                vm.searchByCode();
            });
        }

        vm.getListAnimalOrdo = function (callSearchFunction) {
            animalService.getAll().then(function(data){
                vm.listAnimal16D=data.filter(x=>x.reproductionForm==1);
                animalService.getListAnimalOrdo(vm.searchDto.animalClass).then(function (data) {
                    vm.listAnimalOrdo = [];
                    vm.ordo = null;
                    vm.family = null;
                    data.forEach(item => {
                        var title = '-';
                        if (item && item.trim().length > 0) {
                            title = item;
                        }
                        var check = vm.listAnimal16D.filter(x=>x.ordo==item);
                        if(check.length>0||item==""){
                            vm.listAnimalOrdo.push({ name: title });
                        }
                    });
                    // vm.getListAnimalFamily(data1);
                });
            });
        }

        vm.getListAnimalFamily = function (callSearchFunction) {
            animalService.getAll().then(function(data1){
                vm.listAnimal16D=data1.filter(x=>x.reproductionForm==1);
                animalService.getListAnimalFamily(vm.searchDto.animalClass, vm.searchDto.ordo).then(function (data) {
                    vm.listAnimalFamily = [];
                    vm.family = null;
                    data.forEach(item => {
                        var title = '-';
                        if (item && item.trim().length > 0) {
                            title = item;
                        }
                        var check = vm.listAnimal16D.filter(x=>x.family==item);
                        if(check.length>0||item==""){
                            vm.listAnimalFamily.push({ name: title });
                        }
                    });
                    // if (callSearchFunction) {
                    //     vm.searchByCode();
                    // }
                });
            });
        }
        vm.getListAnimalClass();
        // vm.getListAnimalOrdo();
        // vm.getListAnimalFamily();

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
                //Vũ Văn Đức thêm 2 model
                vm.ordo = null;
                vm.family = null;
            }
            vm.getListAnimalOrdo(true);
            vm.getListReportFormByPage();
        }

        vm.animalOrdoSelected = function () {
            if (vm.ordo && vm.ordo == '-') {
                vm.searchDto.ordo = null;
                vm.searchDto.family = null;
            }
            else{
                vm.searchDto.ordo = vm.ordo;
                vm.searchDto.family = null;
                vm.family = null;
            }
            vm.getListAnimalFamily(true);
            vm.getListReportFormByPage();
        }

        vm.familySelected = function () {
            if (vm.family && vm.family == '-') {
                vm.searchDto.family = null;
            }
            else{
                vm.searchDto.family = vm.family;
            }
            vm.searchByCode();
            vm.getListReportFormByPage();
        }
        vm.searchByCode = function(){
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            animalService.getPageSearchAnimal(vm.searchDto, 1, 1000000).then(function (data) {
                vm.animals = data.content.filter(x=>x.reproductionForm==1);
            });
        }
		// animalService.getAll().then(function (data) {
		// 	vm.animals = data;
		// })
        
        vm.selecteAnimal = function (){
            vm.getListReportFormByPage();
        }
        $scope.getDataDistrictAndWard = function () {
            $scope.myBlobObjectDataDistrictAndWard = undefined;
            const searchDto = {};
            /*if (!vm.province) {
                toastr.warning("Chưa chọn Tỉnh/Thành phố.", "Cảnh báo");
                return;
            }
            if (!vm.district) {
                toastr.warning("Chưa chọn Huyện/Thị xã.", "Cảnh báo");
                return;
            }
            if (!vm.year) {
                toastr.warning("Chưa chọn năm.", "Cảnh báo");
                return;
            }*/
            searchDto.year = vm.year ? vm.year : null;
            searchDto.month = vm.month ? vm.month : null;
            searchDto.day = vm.date ? vm.date : null;
            searchDto.provinceId = vm.province ? vm.province:null;
            searchDto.districtId = vm.district ? vm.district:null;
            searchDto.wardId = vm.ward ? vm.ward:null;
            searchDto.pageIndex = vm.pageIndex ? vm.pageIndex:null;
            searchDto.pageSize = vm.pageSizeReportForm ? vm.pageSizeReportForm:null;
			searchDto.fromDate= vm.fromDate ? vm.fromDate:null;
			searchDto.toDate= vm.toDate ? vm.toDate:null;
            console.log('download started, you can show a wating animation');
			if(changeDate()){
				service.exportReportFormAnimalGiveBirth16D(searchDto)
                .then(function (data) {//is important that the data was returned as Aray Buffer
                	var file = new Blob([data], {
                		type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                    });
                    FileSaver.saveAs(file, "DuLieuMau16D.xls");
                }, function (fail) {
                    console.log('Download Error, stop animation and show error message');
                    $scope.myBlobObjectFileImportExcel = [];
                });
			}
            
        };
        vm.delete16DById=function(id){
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete16D.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDelete.result.then(function (confirm) {
                if (confirm == 'yes') {
                    // check editable
                    //debugger
                    service.getReportFormAnimalGiveBirthById(id).then(function(res){
                        //debugger
                        var report = res;
                        var dateReport;
                        if(typeof report.dateReport === "undefined"){
                            dateReport = new Date();
                        }else{
                            dateReport = new Date(report.dateReport);
                        }
                        var dateReportMonth = dateReport.getMonth()+1;
                        var dateReportYear = dateReport.getFullYear();
                        service.getAdministrativeUnitDtoByLoginUser().then(function (data){
                            //debugger
                            vm.adminstrativeUnits = data;
                            var editable = false;
                            var today = new Date();
                            // var todayDate = String(today.getDate()).padStart(2, '0');
                            var todayMonth = String(today.getMonth() + 1).padStart(2, '0');
                            var todayYear = today.getFullYear();
                            var todayQuarter = 0;
                            if(todayMonth <= 3) {
                                todayQuarter = 1;
                            } else if(todayMonth > 3 && todayMonth <= 6) {
                                todayQuarter = 2;
                            } else if(todayMonth > 6 && todayMonth <= 9) {
                                todayQuarter = 3;
                            } else {
                                todayQuarter = 4;
                            }
                            var periodQuarter=0;
                            if(dateReportMonth){
                                if(dateReportMonth <= 3) {
                                periodQuarter = 1;
                                } else if(dateReportMonth > 3 && dateReportMonth <= 6) {
                                periodQuarter = 2;
                                } else if(dateReportMonth > 6 && dateReportMonth <= 9) {
                                periodQuarter = 3;
                                } else {
                                periodQuarter = 4;
                                }
                            }
                            if((todayYear==dateReportYear&&(todayQuarter === periodQuarter))||vm.isRoleAdmin == true){
                                service.deletePageReportFormAnimalGiveBirthById(id).then(function (r1) {
                                    if(r1){
                                        toastr.info("Xóa bản ghi thành công", "Thông báo");
                                        vm.search();
                                    }else{
                                        toastr.error("Có lỗi xảy ra, phải xóa dữ liệu vật nuôi để tiếp tục xóa bản ghi này", "Cảnh báo");
                                        vm.search();
                                    }
                                });
                            }else{
                                //debugger
                                if(vm.adminstrativeUnits.length==0){
                                    toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                                    return;
                                }
                                for(let i=0;i<vm.adminstrativeUnits.length;i++){
                                    service.getUserAdminstrativeEditTableUnit(vm.adminstrativeUnits[i].parentId!=null?vm.adminstrativeUnits[i].parentId:vm.adminstrativeUnits[i].id).then(function (data1){
                                        //debugger
                                        vm.userEnableEdit = data1;
                                        if(vm.userEnableEdit && vm.userEnableEdit.length>0){
                                            for(let j=0;j<vm.userEnableEdit.length;j++){
                                                if(vm.userEnableEdit[j].year==dateReportYear && vm.userEnableEdit[j].editable==true){
                                                    var temp=0;
                                                    if(dateReportMonth <= 3) {
                                                            temp = 1;
                                                            } else if(dateReportMonth > 3 && dateReportMonth <= 6) {
                                                            temp = 2;
                                                            } else if(dateReportMonth > 6 && dateReportMonth <= 9) {
                                                            temp = 3;
                                                            } else {
                                                            temp = 4;
                                                            }
                                                    
                                                    if(vm.userEnableEdit[j].quater==temp){
                                                        editable=true;
                                                    }    
                                                    if(vm.userEnableEdit[j].quater==null||vm.userEnableEdit[j].quater==''||typeof vm.userEnableEdit[j].quater == 'undefined'){
                                                        editable=true;
                                                    }                                 
                                                }
                                            }
                                            if(editable==true){
                                                //Check role and delete
                                                service.deletePageReportFormAnimalGiveBirthById(id).then(function (r2) {
                                                    if(r2){
                                                        toastr.info("Xóa bản ghi thành công", "Thông báo");
                                                        vm.search();
                                                    }else{
                                                        toastr.error("Có lỗi xảy ra, phải xóa dữ liệu vật nuôi để tiếp tục xóa bản ghi này", "Cảnh báo");
                                                        vm.search();
                                                    }
                                                });
                                            }else{
                                                toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                                                return;
                                            }
                                        }else{
                                          toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                                          return;
                                        }
                                    });
                                }
                            }

                        });

                    });
                    
                }else{
                    toastr.error('Có lỗi xảy ra khi xóa', 'Lỗi');
                }
            }, function () {
                // cancel
            });
        }
    }

})();
