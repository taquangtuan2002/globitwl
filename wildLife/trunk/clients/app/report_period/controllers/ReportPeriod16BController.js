/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ReportPeriod').controller('ReportPeriod16BController', ReportPeriod16BController);

    ReportPeriod16BController.$inject = [
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
        '$translate',
        'OriginalService',
        'ProductTargetService',
        'InjectionTimeService'
    ];
    angular.module('Education.ReportPeriod').directive('fileDownload',function(){
        return{
            restrict:'A',
            scope:{
                fileDownload:'=',
                fileName:'=',
            },

            link:function(scope,elem,atrs){


                scope.$watch('fileDownload',function(newValue, oldValue){

                    if(newValue!=undefined && newValue!=null){
                        console.debug('Downloading a new file');
                        var isFirefox = typeof InstallTrigger !== 'undefined';
                        var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
                        var isIE = /*@cc_on!@*/false || !!document.documentMode;
                        var isEdge = !isIE && !!window.StyleMedia;
                        var isChrome = !!window.chrome && !!window.chrome.webstore || window.chrome!=null;;
                        var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
                        var isBlink = (isChrome || isOpera) && !!window.CSS;

                        if(isFirefox || isIE || isChrome){
                            if(isChrome){
                                console.log('Manage Google Chrome download');
                                var url = window.URL || window.webkitURL;
                                var fileURL = url.createObjectURL(scope.fileDownload);
                                var downloadLink = angular.element('<a></a>');//create a new  <a> tag element
                                downloadLink.attr('href',fileURL);
                                downloadLink.attr('download',scope.fileName);
                                downloadLink.attr('target','_self');
                                downloadLink[0].click();//call click function
                                url.revokeObjectURL(fileURL);//revoke the object from URL
                            }
                            if(isIE){
                                console.log('Manage IE download>10');
                                window.navigator.msSaveOrOpenBlob(scope.fileDownload,scope.fileName);
                            }
                            if(isFirefox){
                                console.log('Manage Mozilla Firefox download');
                                var url = window.URL || window.webkitURL;
                                var fileURL = url.createObjectURL(scope.fileDownload);
                                var a=elem[0];//recover the <a> tag from directive
                                a.href=fileURL;
                                a.download=scope.fileName;
                                a.target='_self';
                                a.click();//we call click function
                            }


                        }else{
                            alert('SORRY YOUR BROWSER IS NOT COMPATIBLE');
                        }
                    }
                });

            }
        }
    });
    angular.module('Education.ReportPeriod').filter('animalPropsFilter', function () {
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
    angular.module('Education.ReportPeriod').directive('numberInput', function ($filter) {
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

                    valStr = val ? val.toString() : '';
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

    function ReportPeriod16BController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, util, Uploader, FileSaver, farmService, animalService, $translate, originalService, productTargetService, injectionTimeService) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;
        vm.type = 2;
        vm.ImportAnimal = {};
        vm.currentId = $state.params.id ? $state.params.id : null;
        vm.farmSelected = null;
        vm.animalSelected = null;
        vm.pageSize = 10;
        vm.pageIndex = 1;
        vm.sizes=[5,10,25,50,100];
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
        vm.animalGenders = settings.animalGenders;
        vm.numberOne = 1;
        vm.individual = null;
        vm.animalStatuses = settings.IndividualAnimalStatuses;
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

        $scope.years = [];
        var today = new Date();
        vm.currentYear=today.getFullYear();
		for(var i=vm.currentYear; i >= 2000; i--) {
			$scope.years.push({value: i, name: i+""});
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
                vm.search();
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
            }
            vm.search();
            vm.findByFarm();
        }
        //--------End ----Phân quyền-------------
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
                        vm.farmName = vm.farmSelected.name;
                        vm.animalSelected = data.animal.parent;
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
                if (typeof vm.individual.status != typeof 0 ||vm.individual.status < 0) {
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
                vm.farmName = vm.farm.name;
                vm.importAnimal.farm = vm.farm;

                //vm.findBy();
                if(vm.reportPeriod.isNew == true){
                    service.getAllAnimalReported(vm.farm.id).then(function(data){
                        if(data && data.length > 0){
                            vm.listReportForm16A = [];
                            for(let index = 0; index < data.length; index++){
                                vm.listReportForm16A.push({
                                    animal: data[index]
                                });
                            }
                        }
                    });
                }

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
        /****----------------------------- FORM 16 */
        vm.years = [];
        vm.currentYear = new Date().getFullYear();
        for (let year = vm.currentYear; year >=2000; year--) {
            vm.years.push({ value: year, name: year + "" });
        }
        vm.months = [];
        vm.currentMonth = new Date().getMonth()+1;
        for (let m = 1; m <= 12; m++) {
            vm.months.push({ value: m, name: m + "" });
        }

        vm.modalFormPopup16A = null;
        vm.reportPeriod = null;
        vm.listReportForm16A = [];
        vm.reportPeriod = {};

        vm.addEditForm16Popup = function(id, type){
            vm.disabledEditFarm=true;
            vm.listReportForm16A = [];
            vm.farm = null;
            vm.reportForm16A = {};
            if(id){
                vm.disabledEditFarm=false;
                service.getReportPeriod(id).then(function(data){
                    vm.reportPeriod = {};
                    vm.reportPeriod = data;
                    vm.reportPeriod.isNew = false;
                    vm.form16Year = vm.reportPeriod.year; 
                    vm.form16Month = vm.reportPeriod.month; 
                    vm.form16Date = vm.reportPeriod.date; 
                    vm.listReportForm16A = vm.reportPeriod.reportItems;
                    vm.farm = vm.reportPeriod.farm;
                    if(vm.form16Year && vm.form16Month && vm.form16Date){
                        vm.reportForm16A.dateReport = new Date(vm.form16Year, vm.form16Month -1, vm.form16Date);
                    }else{
                        vm.reportForm16A.dateReport = new Date();
                    }
                });
                    
                if( vm.form16Year && vm.form16Month && vm.form16Date){
                    vm.reportForm16A.dateReport = new Date(vm.form16Year, vm.form16Month-1, vm.form16Date);
                }else{
                    vm.reportForm16A.dateReport = new Date();
                }
            }else {
                vm.reportPeriod = {};
                vm.reportPeriod.isNew = true;
                
                vm.form16Year = new Date().getFullYear(); 
                vm.form16Month = new Date().getMonth() + 1; 
                vm.form16Date = new Date().getDate(); 
                if(vm.form16Year && vm.form16Month && vm.form16Date){
                    vm.reportForm16A.dateReport = new Date(vm.form16Year, vm.form16Month -1, vm.form16Date);
                }else{
                    vm.reportForm16A.dateReport = new Date();
                }
            }
            if(type == 1){// FROM A
                vm.modalFormPopup16A = modal.open({
                    animation: true,
                    templateUrl: 'report_form16_detail.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    windowClass:"customer-modal-lg",
                    size: 'lg'
                });
            }else if(type == 2){// FROM B
                vm.modalFormPopup16A = modal.open({
                    animation: true,
                    templateUrl: 'report_form16_detail.html',
                    scope: $scope,
                    backdrop: 'static',
                    windowClass:"customer-modal-lg",
                    keyboard: false,
                    size: 'lg'
                });
            }
        }

        vm.calcToalReportForm16 = function(...params){
            let total = 0;
            if(params && params.length > 0){
                params.forEach(function(item){
                    if(item && typeof item == typeof 0){
                        total += item;
                    }
                });
            }
            return total;
        }
        vm.updateTotalForm16 = function(obj){
            obj.total = 0;
            obj.male = 0;
            obj.female = 0;
            obj.unGender = 0;

            obj.male = vm.calcToalReportForm16(obj.maleChildOver1YearOld, obj.maleImport, ((-1)*obj.maleExport));
            obj.female = vm.calcToalReportForm16(obj.femaleChildOver1YearOld, obj.femaleImport, ((-1)*obj.femaleExport));
            obj.unGender = vm.calcToalReportForm16(obj.childUnder1YearOld, obj.unGenderChildOver1YearOld, obj.unGenderImport, ((-1)*obj.unGenderExport));
            obj.total = obj.male + obj.female + obj.unGender;
        }
        vm.form16Year = vm.currentYear;
        vm.form16Month = vm.currentMonth;
        vm.onSelectMonthForm16 = function(){
            vm.days = [];
            const lastDayOfMonth = moment(new Date(vm.form16Year, vm.form16Month - 1, 1)).endOf('month').toDate().getDate();
            for(let dayIndex = 1; dayIndex <= lastDayOfMonth; dayIndex++){
                vm.days.push({value: dayIndex, name: dayIndex+""});
            }
        }
        vm.onSelectMonth = function(item){
            if(item){
                vm.days = [];
                vm.date = null;
                const lastDayOfMonth = moment(new Date(vm.year, vm.month - 1, 1)).endOf('month').toDate().getDate();
                for(let dayIndex = 1; dayIndex <= lastDayOfMonth; dayIndex++){
                    vm.days.push({value: dayIndex, name: dayIndex+""});
                }
            }
        }
        vm.onSelectMonthForm16();
        vm.onSelectYear = function(){
            vm.days = [];
            vm.date = null;
            const lastDayOfMonth = moment(new Date(vm.year, vm.month - 1, 1)).endOf('month').toDate().getDate();
            for(let dayIndex = 1; dayIndex <= lastDayOfMonth; dayIndex++){
                vm.days.push({value: dayIndex, name: dayIndex+""});
            }
        }

        vm.addForm16ToList = function(){
			if(!vm.reportForm16A.dateReport){
                toastr.warning('Chưa chọn ngày', 'Cảnh báo');
				return;
            }
            if(vm.reportForm16A.animal){
                const item = angular.copy(vm.reportForm16A);
                vm.listReportForm16A.push(item);
                console.log(vm.listReportForm16A);
                vm.reportForm16A = {};
                if(vm.form16Year && vm.form16Month && vm.form16Date){
                    vm.reportForm16A.dateReport = new Date(vm.form16Year, vm.form16Month -1, vm.form16Date);
                }else{
                    vm.reportForm16A.dateReport = new Date();
                }
            }else{
                toastr.warning('Chưa chọn loài', 'Cảnh báo');
            }
        }
        vm.saveReportPeriod = function(){
			if(!vm.form16Year){
                toastr.warning('Chưa chọn năm của kỳ báo cáo', 'Cảnh báo');
				return;
            }
			if(!vm.form16Month){
                toastr.warning('Chưa chọn tháng của kỳ báo cáo', 'Cảnh báo');
				return;
            }
            if(vm.listReportForm16A && vm.listReportForm16A.length ==0){
                if(!vm.reportForm16A.animal){
                    toastr.warning('Chưa chọn loài nuôi', 'Cảnh báo');
                    return;
                }
                if(!vm.reportForm16A.dateReport){
                    toastr.warning('Chưa chọn ngày', 'Cảnh báo');
                    return;
                }
            }
            if(!vm.form16Date){
                toastr.warning('Chưa chọn ngày của kỳ báo cáo', 'Cảnh báo');
            }else if(vm.farm){
                vm.reportPeriod.year = vm.form16Year;
                vm.reportPeriod.month = vm.form16Month;
                vm.reportPeriod.date = vm.form16Date;

                vm.reportPeriod.farm = vm.farm;
                vm.reportPeriod.type = 2;
                vm.reportPeriod.farmId = vm.farm.id;
                service.checkDuplicateYearMonthDate(vm.reportPeriod).then(function(res){
                    if(res){
                        toastr.warning("Ngày tháng năm của cơ sở đã được tồn tại", "Cảnh báo");
                    }else{
                        if(vm.reportForm16A && vm.reportForm16A.animal && vm.reportForm16A.dateReport){
                            vm.listReportForm16A.push(vm.reportForm16A);
                            vm.reportForm16A = {};
                        }
        
                        vm.reportPeriod.reportItems = vm.listReportForm16A;
                        service.saveReportPeriod(vm.reportPeriod).then(function(data){
                            console.log(data);
                            vm.modalFormPopup16A.close();
                            vm.pageIndex = 0;
                            vm.searchByPage();
                        });
                    }
                });
            }else{
                toastr.warning('Chưa chọn cơ sở nuôi', 'Cảnh báo');
            }
        }

        vm.removeForm16ToList = function(index){
            vm.listReportForm16A.splice(index, 1);
        }

        vm.searchByPage = function () {
            var searchObject = {
                type: vm.type,
                text: vm.textSearch,
                pageIndex: vm.pageIndex,
                pageSize: vm.pageSize,
                provinceId: vm.province ? vm.province.id : null,
                districtId: vm.district ? vm.district.id : null,
                wardId: vm.ward ? vm.ward.id : null
            }
            service.searchByPage(searchObject).then(function(data){
                vm.reportPeriods = data.content;
                vm.totalItems=data.totalElements;
            });
        }

        vm.search = function () {
            var searchObject = {
                type: vm.type,
                text: vm.textSearch,
                pageIndex: 0,
                pageSize: vm.pageSize,
                provinceId: vm.province ? vm.province.id : null,
                districtId: vm.district ? vm.district.id : null,
                wardId: vm.ward ? vm.ward.id : null,
                year: vm.year? vm.year: null,
                month: vm.month? vm.month: null,
                date: vm.date? vm.date: null
            }
            service.searchByPage(searchObject).then(function(data){
                vm.reportPeriods = data.content;
            });
        }

        vm.searchByPage();

        vm.modalConfirmDelete = null;
        vm.deleteById = function (id) {
            vm.reportPeriod.type = 2;
            if(id){
                service.getReportPeriod(id).then(function(data){
                    vm.reportPeriod = {};
                    if(data){
                        vm.reportPeriod = data;
                        if(vm.reportPeriod.farm && vm.reportPeriod.farm.name != null){
                            vm.farmName = vm.reportPeriod.farm.name;
                            vm.dateReport = vm.reportPeriod.date + "/" + vm.reportPeriod.month + "/" + vm.reportPeriod.year;
                        }
                    }else{
                        toastr.error('Có lỗi xảy ra', 'Vui lòng thử lại');
                    }
                });
            }
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDelete.result.then(function(confirm){
                if(confirm=='yes'){
                    service.deleteById(id).then(function(data){
                        if (data) {
                            toastr.info('Xóa thành công dữ liệu thành công mẫu 16B của cơ sở' + data.farm.name + " , năm " + data.year, 'Thông báo');
                            vm.pageIndex = 0;
                            vm.searchByPage();
                        }else{
                            toastr.error('Có lỗi xảy ra khi xóa', 'Lỗi');
                        }
                    });
                }
            });
            
        }

        function getAllProvince(){
            service.getAdminstrativeUnitsCity().then(function(data){
                vm.adminstrativeUnits_City = data||[];
            });
        }
        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                        vm.selectedDist = null;
                        vm.selectedWard = null;
                        vm.district=null;
						vm.ward=null;
						vm.district=null;
                    } else {
                        vm.district=null;
						vm.ward=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
						vm.district=null;
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards=[];
                    }
                });
            } else {
                vm.district=null;
				vm.ward=null;
	        	vm.selectedDist = null;
	        	vm.selectWards = null;
				vm.district=null;
                vm.adminstrativeUnits_Dist = [];	
				vm.adminstrativeUnits_Wards=[];
            }

            vm.pageIndex = 0;
            vm.searchByPage();
        }

        vm.onFmsadminstrativeUnitDistSelected = function (dist) {
            if (dist != null && dist.id != null) {
				service.getAllByParentId(dist.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Wards = data;
						vm.ward=null;						
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
				vm.ward=null;
				vm.adminstrativeUnits_Wards=[];
			}

            vm.pageIndex = 0;
            vm.searchByPage();
        }
        vm.onFmsadminstrativeUnitWardsSelected=function(item){
            vm.pageIndex = 0;
            vm.searchByPage();
        }
        
        vm.enterSearch = function () {
            // console.log(event.keyCode);
            if (event.keyCode == 13) {//Phím Enter
                vm.pageIndex = 0;
                vm.searchByPage();
            }
        };

        vm.coppyReportPeriod = function () {
            if (vm.reportPeriod && vm.reportPeriod.id) {
                vm.copyForm16Popup(vm.reportPeriod.id);
            }
        }

        vm.copyForm16Popup = function(id, type){
            vm.listReportForm16A = [];
            vm.farm = null;
            vm.reportForm16A = {};
            if(id){
                service.getReportPeriod(id).then(function(data){
                    vm.reportPeriod = {};
                    vm.reportPeriod = data;
                    vm.reportPeriod.isCopy = true;
                    vm.form16Year = new Date().getFullYear(); 
                    vm.form16Month = new Date().getMonth() + 1; 
                    vm.form16Date = new Date().getDate(); 
                    vm.listReportForm16A = vm.reportPeriod.reportItems;
                    vm.farm = vm.reportPeriod.farm;
                    vm.reportPeriod.id = null;
                    vm.listReportForm16A.forEach(function(item){
                        item.id = null;
                        if(vm.form16Year && vm.form16Month && vm.form16Date){
                            item.dateReport = new Date(vm.form16Year, vm.form16Month -1, vm.form16Date);
                        }else{
                            item.dateReport = new Date();
                        }
                    });
                    if(vm.form16Year && vm.form16Month && vm.form16Date){
                        vm.reportForm16A.dateReport = new Date(vm.form16Year, vm.form16Month -1, vm.form16Date);
                    }else{
                        vm.reportForm16A.dateReport = new Date();
                    }
                });
            }
            vm.modalFormPopup16A = modal.open({
                animation: true,
                templateUrl: 'report_form16_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                windowClass:"customer-modal-lg",
                size: 'lg'
            });
        }

        $scope.myBlobObject=undefined;
        $scope.getFileExcelForm16A=function(){
            $scope.myBlobObject=undefined;
            const searchDto = {};
            if(vm.province){
                searchDto.provinceId = vm.province.id;
            }else{
                searchDto.provinceId = null;
            }
            if(vm.district){
                searchDto.districtId = vm.district.id;
            }else{
                searchDto.districtId = null;
            }
            if(vm.ward){
                searchDto.wardId = vm.ward.id;
            }else{
                searchDto.wardId = null;
            }
            if(vm.animal){
                searchDto.animalId = vm.animal.id;
            }else{
                searchDto.animalId = null;
            }
            searchDto.year = vm.year?vm.year:null;
            searchDto.month = vm.month?vm.month:null;
            searchDto.date = vm.date?vm.date:null;
            searchDto.type = vm.type;

            console.log('download started, you can show a wating animation');
            service.getFileExcelForm16A(searchDto)
                .then(function(data){//is important that the data was returned as Aray Buffer
                    console.log('Stream download complete, stop animation!');
                    $scope.myBlobObject=new Blob([data],{ type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
                },function(fail){
                    console.log('Download Error, stop animation and show error message');
                    $scope.myBlobObject=[];
                });
        };

    }

})();
