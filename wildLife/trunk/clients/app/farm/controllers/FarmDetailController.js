/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.Farm').controller('FarmDetailController', FarmDetailController);

    FarmDetailController.$inject = [
        '$window',
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
        'Upload',
        'FileSaver',
        'FmsAdministrativeService',
        'HusbandryTypeService',
        'HusbandryMethodService',
        'WaterSourceService',
        'ProductTargetService',
        'AnimalService',
        'AnimalTypeService',
        'FarmSizeService',
        'CertificateService',
        '$translate',
        'OwnershipService',
        'AnimalReportDataService',
        'ReportPeriodService'
    ];
    //tran huu dat them module download start
    angular.module('Education.Farm').directive('fileDownload', function() {
        return {
            restrict: 'F',
            scope: {
                fileDownload: '=',
                fileName: '=',
            },

            link: function(scope, elem, atrs) {


                scope.$watch('fileDownload', function(newValue, oldValue) {

                    if (newValue != undefined && newValue != null) {

                        var isFirefox = typeof InstallTrigger !== 'undefined';
                        var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
                        var isIE = /*@cc_on!@*/ false || !!document.documentMode;
                        var isEdge = !isIE && !!window.StyleMedia;
                        var isChrome = !!window.chrome && !!window.chrome.webstore || window.chrome != null;;
                        var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
                        var isBlink = (isChrome || isOpera) && !!window.CSS;

                        if (isFirefox || isIE || isChrome) {
                            if (isChrome) {
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
                                window.navigator.msSaveOrOpenBlob(scope.fileDownload, scope.fileName);
                            }
                            if (isFirefox) {
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
    //tran huu dat them module download end
    angular.module('Education').filter('animalPropsFilter', function() {
        return function(items, props) {
            var out = [];

            if (angular.isArray(items)) {
                items.forEach(function(item) {
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
    /*angular.module('Education.Farm').directive('myDatePicker', function() {
        return {
            restrict: 'A',
            require: '?ngModel',
            link: function(scope, element, attrs, ngModelController) {

                // Private variables
                var datepickerFormat = 'dd/mm/yyyy',
                    momentFormat = 'DD/MM/YYYY',
                    datepicker,
                    elPicker;

                // Init date picker and get objects
                // http://bootstrap-datepicker.readthedocs.org/en/release/index.html
                datepicker = element.datepicker({
                    autoclose: true,
                    keyboardNavigation: false,
                    todayHighlight: true,
                    format: datepickerFormat
                });
                elPicker = datepicker.data('datepicker').picker;

                // Adjust offset on show
                datepicker.on('show', function(evt) {
                    elPicker.css('left', parseInt(elPicker.css('left')) + +attrs.offsetX);
                    elPicker.css('top', parseInt(elPicker.css('top')) + +attrs.offsetY);
                });

                // Only watch and format if ng-model is present
                // https://docs.angularjs.org/api/ng/type/ngModel.NgModelController
                if (ngModelController) {
                    // So we can maintain time
                    var lastModelValueMoment;

                    ngModelController.$formatters.push(function(modelValue) {
                        //
                        // Date -> String
                        //

                        // Get view value (String) from model value (Date)
                        var viewValue,
                            m = moment(modelValue);
                        if (modelValue && m.isValid()) {
                            // Valid date obj in model
                            lastModelValueMoment = m.clone(); // Save date (so
                            // we can
                            // restore time
                            // later)
                            viewValue = m.format(momentFormat);
                        } else {
                            // Invalid date obj in model
                            lastModelValueMoment = undefined;
                            viewValue = undefined;
                        }

                        // Update picker
                        element.datepicker('update', viewValue);

                        // Update view
                        return viewValue;
                    });

                    ngModelController.$parsers.push(function(viewValue) {
                        //
                        // String -> Date
                        //

                        // Get model value (Date) from view value (String)
                        var modelValue,
                            m = moment(viewValue, momentFormat, true);
                        if (viewValue && m.isValid()) {
                            // Valid date string in view
                            if (lastModelValueMoment) { // Restore time
                                m.hour(lastModelValueMoment.hour());
                                m.minute(lastModelValueMoment.minute());
                                m.second(lastModelValueMoment.second());
                                m.millisecond(lastModelValueMoment.millisecond());
                            }
                            modelValue = m.toDate();
                        } else {
                            // Invalid date string in view
                            modelValue = undefined;
                        }

                        // Update model
                        return modelValue;
                    });

                    datepicker.on('changeDate', function(evt) {
                        // Only update if it's NOT an <input> (if it's an
                        // <input> the datepicker plugin trys to cast the val to
                        // a Date)
                        if (evt.target.tagName !== 'INPUT') {
                            ngModelController.$setViewValue(moment(evt.date).format(momentFormat)); // $seViewValue
                            // basically
                            // calls
                            // the
                            // $parser
                            // above
                            // so
                            // we
                            // need
                            // to
                            // pass
                            // a
                            // string
                            // date
                            // value
                            // in
                            ngModelController.$render();
                        }
                    });
                }

            }
        };
    });*/
    angular.module('Education.Farm').directive('numberInput', function($filter) {
        return {
            require: 'ngModel',
            link: function(scope, elem, attrs, ngModelCtrl) {
                ngModelCtrl.$formatters.push(function(modelValue) {
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
                ngModelCtrl.$parsers.push(function(viewValue) {
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
                elem.bind('keyup focus', function() {
                    setDisplayNumber(elem.val());
                });

                function setDisplayNumber(val, formatter) {
                    var valStr, displayValue;

                    if (typeof val === 'undefined') {
                        return 0;
                    }

                    valStr = val.toString();
                    displayValue = valStr.replace(/,/g, '').replace(/[A-Za-z]/g, '');
                    //displayValue = parseFloat(displayValue);
                    displayValue = (!isNaN(displayValue)) ? displayValue.toString() : '';

                    // handle leading character -/0
                    if (valStr.length === 1 && valStr[0] === '-') {
                        displayValue = valStr[0];
                    }
                    // else if (valStr.length === 1 && valStr[0] === '0') {
                    //     displayValue = '';
                    // } 
                    // else {
                    //     // displayValue = $filter('number')(displayValue);
                    // }

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

                    // if (attrs.positive && displayValue[0] === '-') {
                    //     displayValue = displayValue.substring(1);
                    // }

                    if (typeof formatter !== 'undefined') {
                        return (displayValue === '') ? 0 : displayValue;
                    }
                    // else {
                    //     elem.val((displayValue === '0') ? '0' : displayValue);
                    // }
                }

                function setModelNumber(val) {
                    var modelNum = val.toString().replace(/,/g, '').replace(/[A-Za-z]/g, '');
                    //modelNum = parseFloat(modelNum);
                    modelNum = (!isNaN(modelNum)) ? modelNum : 0;
                    // if (modelNum.toString().indexOf('.') !== -1) {
                    //     modelNum = Math.round((modelNum + 0.00001) * 100) / 100;
                    // }
                    if (attrs.positive) {
                        modelNum = Math.abs(modelNum);
                    }
                    return modelNum;
                }
            }
        };
    });

    function FarmDetailController($window, $rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, util, Uploader, FileSaver, fmsAdministrativeService, husbandryTypeService, husbandryMethodService, waterSourceService, productTargetService, animalService, animalTypeService, farmSizeService, certificateService, $translate, ownershipService, animalReportDataService, reportPeriodService) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;
        vm.farm = {};
        vm.currentId = $state.params.id ? $state.params.id : null;
        //thanh
        // setTimeout(() => {
        //     $("body").animate({scrollTop: $('#testScroll').offset().top}, "slow");
        // });
        vm.modalStore = {};
        //        vm.stores = [];
        vm.store = {
            isNew: true
        };

        vm.adminstrativeUnits = [];
        vm.adminstrativeUnits_City = [];
        vm.adminstrativeUnits_Dist = [];
        vm.adminstrativeUnits_Wards = [];
        vm.managerList = {};
        vm.farmHusbandryTypes = [];
        vm.husbandryMethods = [];
        vm.husbandryTypesSelected = [];
        vm.waterSources = [];
        vm.productTargets = [];
        vm.productTargetsNullParent = [];
        vm.productTargetsHasParent = [];
        vm.list = [];
        vm.indexStore = -1;
        vm.animal = null;
        vm.ownerships = [];
        vm.modalAnimalReportData = null;
        vm.animalReports = [];
        vm.informationHerdHaveFormIdAndformType = [];
        vm.informationHerd = [];
        vm.animalReportDataIndex = null;
        vm.modalDeleteConfirm = null;
        vm.modalIndividualAnimal = null;
        vm.searchDto16D = {};
        vm.pageIndexAnimalReportData = 1;
        vm.pageSizeAnimalReportData = 100;
        vm.statuses = [

            // {
            //     value: -1,
            //     name: 'Ngưng hoạt động'
            // },

            // {
            //     value: 1,
            //     name: 'Đang hoạt động'
            // },
            {
                value: 2,
                name: 'Chưa đăng ký',
            },

            {
                value: 3,
                name: 'Đã đăng ký'
            }

        ];
        vm.statusFarms = [{
                value: 1,
                name: 'Ngưng hoạt động',
            },

            {
                value: 0,
                name: 'Đang hoạt động'
            }

        ];
        vm.forTradingPuposes = [];
        // vm.forTradingPuposes = [
        //     {
        //         value: 1,
        //         // name:"Breeding"
        //         name: "Nuôi sinh sản"
        //     }
        //     ,{
        //         value: 2,
        //         name:"Nuôi sinh trưởng"
        //     }
        //     ,{
        //         value: 3,
        //         name:"Nuôi sinh sản, Nuôi sinh trưởng"
        //     }
        // ];
        // vm.raisingPupopses = [
        //     {
        //         value: 1,
        //         name: 'T-Nuôi thương mại'
        //     }
        //     ,{
        //         value: 2,
        //         name: 'Z-Vườn thú trưng bày'
        //     }
        //     ,{
        //         value: 3,
        //         name: 'Q-Biểu diễn xiếc'
        //     }
        //     ,{
        //         value: 4,
        //         name: 'R-Cứu hộ bảo tồn'
        //     }
        //     ,{
        //         value: 5,
        //         name: 'S-Nghiên cứu khoa học'
        //     }
        //     ,{
        //         value: 6,
        //         name: 'Khác'
        //     }
        // ]
        vm.salanganeHouseTypes = [{
                value: 0,
                name: "Nhà xây mới"
            },
            {
                value: 1,
                name: "Nhà cơ nới"
            }
        ];
        vm.methodFeeds = [
            { value: 1, name: 'Nuôi sinh trưởng' },
            { value: 2, name: 'Nuôi sinh sản' },
            { value: 3, name: 'Nuôi khác' }
        ];

        vm.animalsGiveBirths = [];
        vm.animalsGiveEggs = [];
        vm.months = [];
        vm.currentMonth = new Date().getMonth() + 1;
        for (let m = 1; m <= 12; m++) {
            vm.months.push({ value: m, name: m + "" });
        }
        vm.listAnimalGiveEggs = [];
        vm.listAnimalGiveBirth = [];
        vm.reportFormAnimalEggSearchDto = {};
        vm.reportFormAnimalGiveBirthSearchDto = {};
        vm.reportFormAnimalGiveBirthPeriod = {};
        vm.bsTableControlForestProductList = {
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
                pageSize: vm.pageSizeForestProductList,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionForestProductList(),
                onCheck: function(row, $element) {
                    if (vm.forestProductListSelected != null) {
                        vm.forestProductListSelected = [];
                    }
                    $scope.$apply(function() {
                        vm.forestProductListSelected.push(row);
                    });
                },

                onUncheck: function(row, $element) {
                    if (vm.forestProductListSelected != null) {
                        vm.forestProductListSelected = [];
                    }
                },

                onPageChange: function(index, pageSize) {
                    vm.pageSizeFarm = pageSize;
                    vm.pageIndexFarm = index;
                    //
                    vm.getPageForestProductList();
                }
            }
        };
        vm.pageSizeForestProductList = 25;
        vm.pageIndexForestProductList = 1;
        vm.searchDtoForestProductList = {};
        vm.fromDateForestProductList = moment().startOf('month').toDate();
        vm.toDateForestProductList = moment().endOf('month').toDate();

        // DS Nhập đàn
        vm.importAnimalsTable = [];
        vm.pageIndexImportAnimal = 1;
        vm.pageSizeImportAnimal = 25;
        vm.pageIndexExportAnimal = 1;
        vm.pageSizeExportAnimal = 25;

        vm.forestProductListSelected = [];
        vm.onSelectedAnimalName = onSelectedAnimalName;
        vm.getPageForestProductList = getPageForestProductList;
        vm.getPageSearchExportAnimalTable = getPageSearchExportAnimalTable;
        vm.getPageSearchImportAnimalTable = getPageSearchImportAnimalTable;
        vm.getPageReportFormAnimalEgg = getPageReportFormAnimalEgg;
        vm.getPageReportFormAnimalGiveBirth = getPageReportFormAnimalGiveBirth;
        vm.getPageAnimalReportDataByFarmId = getPageAnimalReportDataByFarmId;

        vm.modalIndividualConfirmDelete = null;
        vm.individualIndex = null;
        vm.individuals = [];
        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
        vm.farmSize = {};
        vm.isSdahView = false; //cấp tỉnh nhưng chỉ được xem

        vm.isRoleAdmin = false;

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
            if (vm.isRole) { //trường hợp admin và dlp thì lấy tất cả địa chỉ
                getAllCity();

            } else { // trường hợp này thì phân quyền theo user
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUser();

                }

            }
            blockUI.stop();
        });
        vm.ownerDifferentBaseAddress = false;
        vm.checkCity = function() {
            vm.ownerDifferentBaseAddress = !vm.ownerDifferentBaseAddress;

        }
        vm.convertLat = function() {
            var deg = 0;
            var min = 0;
            var sec = 0;
            if (vm.latDeg != null) {
                deg = vm.latDeg
            }
            if (vm.latMin != null) {
                min = vm.latMin;
            }
            if (vm.latSec != null) {
                sec = vm.latSec;
            }
            vm.farm.latitude = deg + min / 600 + sec / 3600;
        }
        vm.convertLng = function() {
                var deg = 0;
                var min = 0;
                var sec = 0;
                if (vm.lngDeg != null) {
                    deg = vm.lngDeg
                }
                if (vm.lngMin != null) {
                    min = vm.lngMin;
                }
                if (vm.lngSec != null) {
                    sec = vm.lngSec;
                }
                vm.farm.longitude = deg + min / 600 + sec / 3600;
            }
            //load user administrativeunit
        function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function(data) {
                vm.adminstrativeUnits = data;
                if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
                    const district = vm.adminstrativeUnits.find(unit => unit.name == vm.farm.districtName);
                    if (district) {
                        getCombobox(district);
                    } else {
                        getCombobox(vm.adminstrativeUnits[0]);
                    }

                }
            });
        }

        function getCombobox(administrativeUnit) {
            if (administrativeUnit.parentDto == null) {
                vm.selectedCity = administrativeUnit;
                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit);
                fmsAdministrativeService.getAllByParentId(vm.selectedCity.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }

                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                vm.selectedDist = administrativeUnit;
                vm.selectedCity = administrativeUnit.parentDto;

                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
                vm.adminstrativeUnits_Dist = [];
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                // vm.adminstrativeUnits_Dist.push(administrativeUnit);
                fmsAdministrativeService.getAllByParentId(vm.selectedCity.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }

                });
                fmsAdministrativeService.getAllByParentId(vm.selectedDist.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }

                });
                /*if(vm.isFamer==false){
                    vm.autoGenerateChange();
                }*/
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                if (vm.farm == null) vm.farm = {};
                vm.farm.administrativeUnit = administrativeUnit;
                vm.selectedDist = administrativeUnit.parentDto;
                vm.selectedCity = administrativeUnit.parentDto.parentDto;

                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
                if (vm.adminstrativeUnits_Wards == null) vm.adminstrativeUnits_Wards = [];
                vm.adminstrativeUnits_Wards.push(administrativeUnit);
                /*if(vm.isFamer==false){
                    vm.autoGenerateChange();
                }*/
            }
            if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') { //trường hợp tài khoản nông dân
                //gán tên cơ sở chăn nuôi
                vm.code = vm.user.username;
                //load ra cơ sở chăn nuôi
                vm.findBy();
            }
        }
        vm.findBy = function() {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (vm.code != null && vm.code != "") {
                vm.searchDto.nameOrCode = vm.code;
            } else {
                vm.searchDto.nameOrCode = null;
            }
            if (vm.selectedCity != null) {
                vm.searchDto.province = vm.selectedCity.id;
            } else {
                vm.searchDto.province = null;
            }
            if (vm.selectedDist != null) {
                vm.searchDto.district = vm.selectedDist.id;
            } else {
                vm.searchDto.district = null;
            }
            if (vm.farm != null && vm.farm.administrativeUnit != null) {
                vm.searchDto.ward = vm.farm.administrativeUnit.id;
            } else {
                vm.searchDto.ward = null;
            }
            service.getPageSearchFarm(vm.searchDto, 1, 10).then(function(data) {
                vm.farms = data.content;
                if (vm.farms != null && vm.farms.length > 0) {
                    vm.currentId = vm.farms[0].id;
                    //vm.generate();
                    $state.go("application.farm_detail", { id: vm.currentId, "#": "testScroll" });
                }

            });
        }

        //--------End ----Phân quyền-------------

        vm.bsTableControl4Files = {
            options: {
                data: [],
                idField: 'id',
                sortable: false,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: true,
                showToggle: false,
                pagination: false,
                locale: settings.locale,
                columns: service.getTableDefinition4Files()
            }
        };

        function getAllCity() {
            service.getAdminstrativeUnitsCity().then(function(data) {
                vm.adminstrativeUnits_City = data;
            });
        }
        //getAllCity();

        vm.onFmsadminstrativeUnitCitySelected = function(city) {
            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                        vm.selectedDist = null;
                        vm.farm.administrativeUnit = null;
                    } else {
                        vm.selectedDist = null;
                        vm.farm.administrativeUnit = null;
                        vm.adminstrativeUnits_Dist = [];
                        vm.adminstrativeUnits_Wards = [];
                    }
                });
            } else {
                vm.selectedDist = null;
                vm.farm.administrativeUnit = null;
                vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Wards = [];
            }
        }

        vm.onFmsadminstrativeUnitDistSelected = function(dist) {
            if (dist != null && dist.id != null) {
                service.getAllByParentId(dist.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    } else {
                        vm.farm.administrativeUnit = null;
                        vm.adminstrativeUnits_Wards = [];
                    }
                    vm.farm.administrativeUnit = null;
                });
                //vm.autoGenerateChange();
            } else {
                vm.farm.administrativeUnit = null;
                vm.adminstrativeUnits_Wards = [];
            }
        }
        vm.onFmsadminstrativeUnitWardsSelected = function(item) {
            if (item) {
                service.getAllByParentId(item.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                        vm.farm.administrativeUnit = null;
                    }
                });
            } else {
                // vm.adminstrativeUnits_Wards = [];
                vm.farm.administrativeUnit = null;
            }
        }

        function getDataByCombobox(Wards) {
            if (Wards != null && Wards.parentDto != null && Wards.parentDto.parentDto != null) {
                console.log("datacomboAddress:ward");
                console.log(Wards);
                vm.selectedCity = Wards.parentDto.parentDto;
                vm.selectedDist = Wards.parentDto;
                console.log("datacomboAddress2:city");
                console.log(vm.selectedCity);
                console.log("datacomboAddress3: district");
                console.log(vm.selectedDist);
                // vm.farm.adressDetail = "";
                // vm.farm.adressDetail = (vm.farm.village != null ? (vm.farm.village + ', ') : '');
                // vm.farm.adressDetail += Wards.name;
                // vm.farm.adressDetail += ", " + vm.selectedDist.name;
                // vm.farm.adressDetail += ", " + vm.selectedCity.name;
                service.getAllByParentId(vm.selectedCity.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    service.getAllByParentId(vm.selectedDist.id).then(function(data) {
                        if (data != null && data.length > 0) {
                            vm.adminstrativeUnits_Wards = data;
                        }
                    });
                });
            }
        }

        function getDataByComboboxOwner(Wards) {
            if (Wards != null && Wards.parentDto != null && Wards.parentDto.parentDto != null) {
                vm.ownerSelectedCity = Wards.parentDto.parentDto;
                vm.ownerSelectedDist = Wards.parentDto;
                service.getAllByParentId(vm.ownerSelectedCity.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_DistOwner = data;
                    }
                    service.getAllByParentId(vm.ownerSelectedDist.id).then(function(data) {
                        if (data != null && data.length > 0) {
                            vm.adminstrativeUnits_WardsOwner = data;
                        }
                    });
                });
            }
        }
        vm.report16Type = 0;
        vm.generate = function() {
            if (vm.currentId) {
                service.getFarm(vm.currentId).then(function(data) {
                    if (data != null) {
                        console.log("Data:" + data);
                        vm.farm = data;
                        if (vm.farm.farmProductTargets != null && vm.farm.farmProductTargets.length > 0) {
                            for (var i = 0; i < vm.farm.farmProductTargets.length; i++) {
                                var tar = vm.farm.farmProductTargets[i];
                                if (tar.code == 'Rearing') {
                                    vm.report16Type = 1;
                                    break;
                                }
                            }
                        }
                        //console.log("Ádasdasdas",vm.farm);
                        if (vm.farm.administrativeUnit != null && vm.farm.ownerAdministrativeUnit != null && vm.farm.administrativeUnit.id != vm.farm.ownerAdministrativeUnit.id) {
                            vm.ownerDifferentBaseAddress = false;
                            vm.checkCity();
                            getDataByComboboxOwner(vm.farm.ownerAdministrativeUnit);
                        }

                        // lấy danh sách tên từng loại động vật nếu có - bỏ đi giờ chỉ lưu loại động vật cha
                        if (vm.farm.administrativeUnit != null && vm.isFamer == false) {
                            getDataByCombobox(vm.farm.administrativeUnit);
                        }
                        //                        vm.stores = data.stores.map(element => element);
                        //                        vm.bsTableControl4Files.options.data = vm.farm.attachments;
                        if (data.farmHusbandryType)
                            vm.husbandryTypesSelected = data.farmHusbandryType.map(el => el.husbandryType);
                        // service.getAnimalReport(vm.currentId).then(function(data){
                        // 	vm.animalReports = data;
                        // });
                        // vm.animalReports = vm.farm.animalReportDatas;
                        vm.informationHerdHaveFormIdAndformType = vm.farm.informationHerdHaveFormIdAndformType;
                        vm.informationHerd = vm.farm.informationHerd;
                        if (vm.animalReports != null && vm.animalReports.length > 0) {

                            vm.animalReports.sort(function(a, b) {
                                return b.year - a.year;
                            });

                        }
                        // if(vm.farm.animalReportDatas!=null && vm.farm.animalReportDatas.length>0){
                        // 	vm.animalReportDatas=[];
                        // 	for (var i = 0; i < vm.animalReports.length; i++) {
                        //     	let animalReport = vm.animalReports[i];
                        //     	if(animalReport.formId==null){
                        //     		vm.animalReportDatas.push(animalReport);
                        //     	}
                        // 	}
                        // }
                        //thanh
                        if (vm.farm.farmHusbandryMethods && vm.farm.farmHusbandryMethods.length > 0) {
                            if (vm.husbandryMethods && vm.husbandryMethods.length > 0) {
                                vm.husbandryMethods.map(hm => {
                                    let check = vm.farm.farmHusbandryMethods.filter(e => e.husbandryMethod.id === hm.id);
                                    if (check && check.length > 0) {
                                        hm.checked = true;
                                    }
                                })
                            } else {
                                husbandryMethodService.getAll().then(function(data) {
                                    //debugger
                                    vm.husbandryMethods = data;
                                    data.map(hm => {
                                        let check = vm.farm.farmHusbandryMethods.filter(e => e.husbandryMethod.id === hm.id);
                                        if (check && check.length > 0) {
                                            hm.checked = true;
                                        }
                                    })
                                });
                            }

                        };

                        vm.pageIndexAnimalReportData = 1;
                        vm.pageSizeAnimalReportData = 100;
                        vm.getPageAnimalReportDataByFarmId();

                        service.getListReport16FormDto(vm.currentId).then(function(listData) {
                            vm.listReport16FormDto = listData;
                            vm.animalForms = [];
                            let listAnimalName = [];
                            if (vm.listReport16FormDto) {
                                vm.listReport16FormDto.forEach(function(item) {
                                    if (listAnimalName.indexOf(item.animalName) == -1) {
                                        listAnimalName.push(item.animalName);
                                        vm.animalForms.push({ animalName: item.animalName });
                                    }
                                });
                                vm.onSelectedAnimalName();
                            }
                            //vm.getAnimalForms(vm.listReport16FormDto);
                        });

                        service.farmReportPeriodSearchList({
                            farmId: vm.farm.id,
                            type: settings.AnimalReportDataFormType.firstType
                        }).then(function(listData) {
                            if (listData && listData.length > 0) {
                                vm.listFarmPeriodType1 = listData;
                            }
                        });
                        service.farmReportPeriodSearchList({
                            farmId: vm.farm.id,
                            type: settings.AnimalReportDataFormType.secondType
                        }).then(function(listData) {
                            if (listData && listData.length > 0) {
                                vm.listFarmPeriodType2 = listData;
                            }
                        });
                        service.farmReportPeriodSearchList({
                            farmId: vm.farm.id,
                            type: settings.AnimalReportDataFormType.thirdType
                        }).then(function(listData) {
                            if (listData && listData.length > 0) {
                                vm.listFarmPeriodType3 = listData;
                            }
                        });
                        if (vm.farm.farmHusbandryMethods) {
                            vm.husbandryMethodSelectList = vm.farm.farmHusbandryMethods.map(item => item.husbandryMethod.id);
                        }
                    }
                }).then(function() {
                    vm.getPageForestProductList();
                    vm.getPageSearchExportAnimalTable();
                    vm.getPageSearchImportAnimalTable();
                    vm.getPageReportFormAnimalEgg();
                    vm.getPageReportFormAnimalGiveBirth();
                    animalTypeService.getAllAnimalTypeDto().then(function(data) {
                        vm.animalTypes = data;
                        if (vm.animalTypes != null && vm.animalTypes.length > 0) {
                            // vm.farm.farmAnimalTypes = [];
                            // // for (var i = 0; i < vm.animalTypes.length; i++) {
                            // //     var item = vm.animalTypes[i];
                            // //     if (item.name = "Gia cầm") {
                            // //         vm.farm.farmAnimalTypes.push(item);
                            // //         break;
                            // //     }
                            // // }
                        }
                        if (vm.farm.farmAnimalTypes && vm.farm.farmAnimalTypes.length > 0) {
                            let listId = vm.farm.farmAnimalTypes.map(type => type.id);
                            if (listId && listId.length > 0) {
                                vm.animalTypes.forEach(el => {
                                    if (listId.indexOf(el.id) > -1) {
                                        el.checked = true;
                                    }
                                });
                            }

                        }
                    });
                    productTargetService.getAllProductTarget().then(function(data) {
                        ////debugger
                        vm.productTargets = data;
                        vm.productTargetsNullParent = data.filter(el => {
                            return el.parent == null;
                        });
                        vm.productTargetsHasParent = data.filter(el => {
                            return el.parent != null;
                        });
                        //Xử lý checkbox mục đích nuôi nếu có mục đích nuôi (con) được chọn sẽ chọn cả cha
                        if (vm.farm.farmProductTargets && vm.farm.farmProductTargets.length > 0) {
                            //let listProductTargetIds = vm.farm.farmProductTargets.map(el => el.id);
                            let listProductTargetIds = [];
                            vm.farm.farmProductTargets.map(el => {
                                if (el.parent != null) {
                                    listProductTargetIds.push(el.parent.id);
                                }
                                listProductTargetIds.push(el.id)
                            });
                            vm.productTargets.forEach(productTarget => {
                                if (listProductTargetIds.indexOf(productTarget.id) > -1) {
                                    productTarget.checked = true;
                                }
                            });
                        }
                    });
                });
            } else {
                productTargetService.getAllProductTarget().then(function(data) {
                    vm.productTargets = data;
                });
                animalTypeService.getAllAnimalTypeDto().then(function(data) {
                    vm.animalTypes = data;
                    if (vm.animalTypes != null && vm.animalTypes.length > 0) {}
                });
                productTargetService.getAllProductTarget().then(function(data) {
                    vm.productTargets = data;
                    vm.productTargetsNullParent = data.filter(el => {
                        return el.parent == null;
                    });
                    vm.productTargetsHasParent = data.filter(el => {
                        return el.parent != null;
                    });
                    if (vm.farm.farmProductTargets && vm.farm.farmProductTargets.length > 0) {
                        let listProductTargetIds = vm.farm.farmProductTargets.map(el => el.id);
                        vm.productTargets.forEach(productTarget => {
                            if (listProductTargetIds.indexOf(productTarget.id) > -1) {
                                productTarget.checked = true;
                            }
                        });
                    }
                });
            }

            waterSourceService.getAll().then(function(data) {
                vm.waterResources = data;
            });
            husbandryMethodService.getAll().then(function(data) {
                //debugger
                vm.husbandryMethods = data;
            });
            husbandryTypeService.getAll().then(function(data) {
                vm.husbandryTypes = data;
            });
            animalService.getListParent().then(function(data) {
                vm.animals = data;

            });
            ownershipService.getAll().then(function(data) {
                vm.ownerships = data;
            });

            certificateService.getAllCertificateDto().then(function(data) {
                vm.certificates = data;
            });
            farmSizeService.getAllFarmSizeDto().then(function(data) {
                vm.farmSizes = data;
            });
            vm.searchByPage();
        }

        vm.pageIndex = 1;
        vm.pageSize = 5;
        vm.years = [];
        var today = new Date();
        vm.currentYear = today.getFullYear();
        for (var i = vm.currentYear; i >= 2000; i--) {
            vm.years.push({ value: i, name: i + "" });
        }
        vm.days = [];
        vm.daysInMonth = (month, year) => {
            return new Date(year, month, 0).getDate();
        }
        vm.currentDate = vm.daysInMonth(vm.currentMonth, vm.currentYear);
        for (var i = 1; i <= vm.currentDate; i++) {
            vm.days.push({ value: i, name: i + "" });
        }
        console.log(vm.days);
        vm.searchByPage = function() {
            var searchObject = {
                type: 1,
                pageIndex: vm.pageIndex,
                pageSize: vm.pageSize,
                farmId: parseInt(vm.currentId) ? vm.currentId : null
            }
            reportPeriodService.searchByPage(searchObject).then(function(data) {
                vm.reportPeriods = data.content;
                vm.totalItems = data.totalElements;
            });
        }
        vm.searchByPage();
        vm.addEditForm16Popup = function(id, type) {
            //debugger
            vm.onSelectMonthForm16();
            vm.listReportForm16A = [];
            vm.farm = null;
            vm.reportForm16A = {};
            vm.allOriginal = [];
            service.getAllOriginal().then(function(data) {
                vm.allOriginal = data;
            });
            if (id) {
                service.getReportPeriod(id).then(function(data) {
                    //debugger
                    vm.reportPeriod = {};
                    vm.reportPeriod = data;
                    vm.reportPeriod.isNew = false;
                    vm.form16Year = vm.reportPeriod.year;
                    vm.form16Month = vm.reportPeriod.month;
                    vm.form16Date = vm.reportPeriod.date;
                    vm.listReportForm16A = vm.reportPeriod.reportItems;
                    vm.farm = vm.reportPeriod.farm;
                    if (vm.form16Year && vm.form16Month && vm.form16Date) {
                        vm.reportForm16A.dateReport = new Date(vm.form16Year, vm.form16Month - 1, vm.form16Date);
                    } else {
                        vm.reportForm16A.dateReport = new Date();
                    }
                });
            } else {
                vm.reportPeriod = {};
                vm.reportPeriod.isNew = true;
                vm.form16Year = new Date().getFullYear();
                vm.form16Month = new Date().getMonth() + 1;
                vm.form16Date = new Date().getDate();
                if (vm.form16Year && vm.form16Month && vm.form16Date) {
                    vm.reportForm16A.dateReport = new Date(vm.form16Year, vm.form16Month - 1, vm.form16Date);
                } else {
                    vm.reportForm16A.dateReport = new Date();
                }
            }
            vm.modalFormPopup16A = modal.open({
                animation: true,
                templateUrl: 'report_form_16A.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                windowClass: "customer-modal-lg",
                size: 'lg'
            });
        }

        vm.modalConfirmDelete = null;
        vm.deleteById = function(id) {
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDelete.result.then(function(confirm) {
                if (confirm == 'yes') {
                    //Get period
                    reportPeriodService.getReportPeriod(id).then(function(res) {
                        service.getAdministrativeUnitDtoByLoginUser().then(function(adminUnit) {
                            //zzz
                            var period = res;
                            vm.adminstrativeUnits1 = adminUnit;
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
                            if (period.month) {
                                if (period.month <= 3) {
                                    periodQuarter = 1;
                                } else if (period.month > 3 && period.month <= 6) {
                                    periodQuarter = 2;
                                } else if (period.month > 6 && period.month <= 9) {
                                    periodQuarter = 3;
                                } else {
                                    periodQuarter = 4;
                                }
                            } else {
                                if (vm.form16Month <= 3) {
                                    periodQuarter = 1;
                                } else if (vm.form16Month > 3 && vm.form16Month <= 6) {
                                    periodQuarter = 2;
                                } else if (vm.form16Month > 6 && vm.form16Month <= 9) {
                                    periodQuarter = 3;
                                } else {
                                    periodQuarter = 4;
                                }
                            }
                            if ((todayYear == (period.year ? period.year : vm.form16Year) && (todayQuarter === periodQuarter)) || vm.isRoleAdmin == true) {
                                //Check role and delete
                                reportPeriodService.deleteById(id).then(function(data) {
                                    if (data) {
                                        toastr.info('Xóa dữ liệu khai báo thành công', 'Thông báo');
                                        vm.pageIndex = 0;
                                        vm.searchByPage();
                                    } else {
                                        toastr.error('Có lỗi xảy ra khi xóa', 'Lỗi');
                                    }
                                });
                            } else {
                                //debugger
                                if (vm.adminstrativeUnits1.length == 0) {
                                    toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                                    return;
                                }
                                for (let i = 0; i < vm.adminstrativeUnits1.length; i++) {
                                    service.getUserAdminstrativeEditTableUnit(vm.adminstrativeUnits1[i].parentId != null ? vm.adminstrativeUnits1[i].parentId : vm.adminstrativeUnits1[i].id).then(function(data1) {
                                        //debugger
                                        vm.userEnableEdit = data1;
                                        if (vm.userEnableEdit && vm.userEnableEdit.length > 0) {
                                            for (let j = 0; j < vm.userEnableEdit.length; j++) {
                                                if (vm.userEnableEdit[j].year == period.year && vm.userEnableEdit[j].editable == true) {
                                                    var temp = 0;
                                                    if (period.month <= 3) {
                                                        temp = 1;
                                                    } else if (period.month > 3 && period.month <= 6) {
                                                        temp = 2;
                                                    } else if (period.month > 6 && period.month <= 9) {
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
                                                //Check role and delete
                                                reportPeriodService.deleteById(id).then(function(data) {
                                                    if (data) {
                                                        toastr.info('Xóa dữ liệu khai báo thành công', 'Thông báo');
                                                        vm.pageIndex = 0;
                                                        vm.searchByPage();
                                                    } else {
                                                        toastr.error('Có lỗi xảy ra khi xóa', 'Lỗi');
                                                    }
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
            });

        }
        vm.validateSDT = (sdt) => {
            var sdtRegex = /^0[0-9]{9,10}$/
            if (sdt.match(sdtRegex)) {
                return true;
            } else {
                return false;
            }
        }
        vm.validateLatitude = (llt) => {
            if (parseFloat(llt) >= 8.3 && parseFloat(llt) <= 23.5) {
                return true;
            } else {
                return false;
            }
        }
        vm.validateLongitude = (llt) => {
            //debugger
            if (parseFloat(llt) >= 101.7 && parseFloat(llt) <= 109.5) {
                return true;
            } else {
                return false;
            }
        }

        function isValidForm() {
            var focusItem = null;
            if (vm.farm == null) {
                toastr.warning($translate.instant("farm.error"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.farm.name == null || vm.farm.name == '') {
                toastr.warning($translate.instant("farm.emptyFarm"), $translate.instant("toastr.warning"));
                focusItem = document.getElementById("name");
                if (focusItem) {
                    focusItem.focus();
                }
                return;
            }
            if (vm.farm.administrativeUnit == null || vm.farm.administrativeUnit.id == null || vm.farm.administrativeUnit.id == '') {
                toastr.warning($translate.instant("farm.emptyAdministrativeUnit"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.farm.latitude == null || vm.farm.latitude == '') {
                toastr.warning($translate.instant("farm.nulllatitude"), $translate.instant("toastr.warning"));
                focusItem = document.getElementById("latitude");
                if (focusItem) {
                    focusItem.focus();
                }
                return;
            }
            if (vm.farm.longitude == null || vm.farm.longitude == '') {
                toastr.warning($translate.instant("farm.nulllongitude"), $translate.instant("toastr.warning"));
                focusItem = document.getElementById("longitude");
                if (focusItem) {
                    focusItem.focus();
                }
                return;
            }

            if (vm.farm.ownerName == null || vm.farm.ownerName == '') {
                toastr.warning($translate.instant("farm.emptyOwnerName"), $translate.instant("toastr.warning"));
                focusItem = document.getElementById("ownerName");
                if (focusItem) {
                    focusItem.focus();
                }
                return;
            }

            if (vm.farm.ownerName == null || vm.farm.ownerName == '') {
                toastr.warning($translate.instant("farm.emptyOwnerName"), $translate.instant("toastr.warning"));
                focusItem = document.getElementById("ownerName");
                if (focusItem) {
                    focusItem.focus();
                }
                return;
            }
            console.log(vm.farm)
            if (vm.farm.farmProductTargets == null || vm.farm.farmProductTargets.length == 0) {
                toastr.warning("Vui lòng chọn mục đích nuôi!", $translate.instant("toastr.warning"));
                return;
            }
            if (vm.farm.farmHusbandryMethods == null || vm.farm.farmHusbandryMethods.length == 0) {
                toastr.warning("Vui lòng chọn hình thức nuôi", $translate.instant("toastr.warning"));
                return;
            }
            if (vm.farm.statusFarm == null) {
                toastr.warning("Vui lòng chọn trạng thái!", $translate.instant("toastr.warning"));
                return;
            }
            if (vm.farm.status == null) {
                toastr.warning("Vui lòng chọn trạng thái đăng ký!", $translate.instant("toastr.warning"));
                return;
            }
            //debugger
            // if (vm.farm.phoneNumber != null && vm.farm.phoneNumber != '' && typeof vm.farm.phoneNumber != "undefined" && vm.validateSDT(vm.farm.phoneNumber) == false) {
            //     toastr.warning("Số điện thoại không hợp lệ!", $translate.instant("toastr.warning"));
            //     return;
            // }
            if (vm.farm.ownerPhoneNumber != null && vm.farm.ownerPhoneNumber != '' && typeof vm.farm.ownerPhoneNumber != "undefined" && vm.validateSDT(vm.farm.ownerPhoneNumber) == false) {
                toastr.warning("Số điện thoại chủ sở hữu không hợp lệ!", $translate.instant("toastr.warning"));
                return;
            }
            if (vm.farm.latitude != null && vm.validateLatitude(vm.farm.latitude) == false) {
                toastr.warning("Vĩ độ không được nhỏ hơn 8.3 và không lớn hơn 23.5", $translate.instant("toastr.warning"));
                return;
            }
            if (vm.farm.longitude != null && vm.validateLongitude(vm.farm.longitude) == false) {
                toastr.warning("Kinh độ không được nhỏ hơn 101.7 và không lớn hơn 109.5", $translate.instant("toastr.warning"));
                return;
            }
            // if (vm.farm.ownerPhoneNumber == null || vm.farm.ownerPhoneNumber == '') {
            //     toastr.warning($translate.instant("farm.emptyOwnerPhoneNumber"), $translate.instant("toastr.warning"));
            //     focusItem = document.getElementById("ownerPhoneNumber");
            //     if (focusItem) {
            //         focusItem.focus();
            //     }
            //     return;
            // }

            if (vm.farm.dateRegistration != null && typeof vm.farm.dateRegistration != "undefined") {
                let now = new Date();
                let farmDateRegistration = new Date(vm.farm.dateRegistration);
                if (farmDateRegistration > now) {
                    toastr.warning("Ngày cấp(theo 06) không hợp lệ!", $translate.instant("toastr.warning"));
                    return;
                }
            }
            if (vm.farm.dateOtherRegistration != null && typeof vm.farm.dateOtherRegistration != "undefined") {
                let now = new Date();
                let farmDateOtherRegistration = new Date(vm.farm.dateOtherRegistration);
                if (farmDateOtherRegistration > now) {
                    toastr.warning("Ngày cấp không hợp lệ!", $translate.instant("toastr.warning"));
                    return;
                }
            }
            if (vm.farm.ownerDobYear != null) {
                let now = new Date();
                let year = now.getFullYear();
                if (vm.farm.ownerDobYear > year || vm.farm.ownerDobYear < 1000) {
                    toastr.warning("Năm sinh không hợp lệ!", "Thông báo");
                    return;
                }

                vm.farm.ownerDob = new Date(vm.farm.ownerDobYear, 0, 1);
            }

            return true;
        }
        vm.saveFarm = function() {
            if (isValidForm()) {
                vm.farm.animalReportDatas = (vm.animalReports && vm.animalReports.length > 0) ? vm.animalReports : null;
                if (vm.currentId) {
                    if (vm.farm) {
                        if (vm.ownerDifferentBaseAddress == true) {
                            if (vm.ownerSelectedCity != null) {
                                vm.farm.ownerProvinceId = vm.ownerSelectedCity.id;
                                vm.farm.ownerProvinceCode = vm.ownerSelectedCity.code;
                            }
                            if (vm.ownerSelectedDist != null) {
                                vm.farm.ownerDistrictId = vm.ownerSelectedDist.id;
                                vm.farm.ownerDistrictCode = vm.ownerSelectedDist.code;
                            }
                        } else {
                            vm.farm.ownerDistrictId = null;
                            vm.farm.ownerDistrictCode = null;
                            vm.farm.ownerProvinceId = null;
                            vm.farm.ownerProvinceCode = null;
                            vm.ownerSelectedCity = null;
                            vm.ownerSelectedDist = null;
                            vm.farm.ownerAdministrativeUnit = null;
                            vm.farm.ownerVillage = null;
                        }

                        service.saveFarm(vm.currentId, vm.farm, function success(data) {
                            toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                            if (vm.isFamer == false) {
                                $state.go('application.farm');
                            }
                        }, function failure(error) {
                            toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        });
                    }
                } else {

                    if (vm.farm != null) {
                        if (vm.selectedCity != null)
                            vm.farm.provinceCode = vm.selectedCity.code;
                        if (vm.selectedDist != null) {
                            vm.farm.districtId = vm.selectedDist.id;
                            vm.farm.districtCode = vm.selectedDist.code;
                        }
                        if (vm.ownerDifferentBaseAddress) {

                            if (vm.ownerSelectedCity != null) {
                                vm.farm.ownerProvinceId = vm.ownerSelectedCity.id;
                                vm.farm.ownerProvinceCode = vm.ownerSelectedCity.code;
                            }
                            if (vm.ownerSelectedDist != null) {
                                vm.farm.ownerDistrictId = vm.ownerSelectedDist.id;
                                vm.farm.ownerDistrictCode = vm.ownerSelectedDist.code;
                            }
                        }

                        console.log("famr", vm.farm);

                    }
                    // vm.farm.ownerDob = vm.farm.formDateNew +  vm.farm.formMonthNew+  vm.farm.formYearNew ; 
                    //     vm.farm.formYearNew = new Date().getFullYear();
                    //     
                    //    console.log(vm.formYearNew);
                    //     vm.farm.formMonthNew = new Date().getMonth() + 1;
                    //     vm.farm.formDateNew = new Date().getDate();

                    service.createFarm(vm.farm).then(function(data) {
                        vm.farm = data;

                        $state.go('application.farm_detail', {
                            id: vm.farm.id,
                            "#": "testScroll"
                        });
                        // if (vm.isFamer == false) {
                        //     $state.go('application.farm');
                        // }
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    });
                }
            }
        }

        vm.goBack = function() {
            if (vm.isFamer == false)
                $state.go('application.farm');
        }
        vm.generate();

        vm.uploadedFile = null;
        vm.errorFile = null;
        vm.uploadFiles = function(file, errFiles) {
            vm.uploadedFile = file;
            if (vm.uploadedFile != null) {
                Uploader
                    .upload({
                        url: settings.api.baseUrl +
                            settings.api.apiPrefix +
                            'farm/uploadattachment',
                        method: 'POST',
                        data: {
                            uploadfile: vm.uploadedFile
                        }
                    })
                    .then(
                        function(successResponse) {

                            var attachment = successResponse.data;
                            if (vm.uploadedFile &&
                                (!vm.errorFile || (vm.errorFile && vm.errorFile.$error == null))) {
                                if (vm.farm != null &&
                                    vm.farm.attachments == null) {
                                    vm.farm.attachments = [];
                                }
                                vm.farm.attachments.push(
                                    // { title: attachment.file.name,
                                    // contentLength:
                                    // attachment.file.contentSize,
                                    // contentType: fileDesc.contentType }
                                    attachment);
                                vm.bsTableControl4Files.options.data = vm.farm.attachments;
                            }
                        },
                        function(errorResponse) {
                            toastr.error('Error submitting data...',
                                'Error');
                        },
                        function(evt) {
                            // console.log('progress: ' +
                            //     parseInt(100.0 * evt.loaded /
                            //         evt.total) + '%');
                        });
            }
        };

        $scope.deleteDocument = function(index) {
            if (vm.farm != null && vm.farm.attachments != null) {
                for (var i = 0; i < vm.farm.attachments.length; i++) {
                    if (i == index) {
                        vm.farm.attachments.splice(i, 1);
                    }
                }
            }
        };

        $scope.downloadDocument = function(index) {
            if (vm.farm != null && vm.farm.attachments != null) {
                for (var i = 0; i < vm.farm.attachments.length; i++) {
                    if (i == index) {
                        var attachment = vm.farm.attachments[i];
                        service.getFileById(attachment.file.id).then(function(data) {
                            var file = new Blob([data], {
                                type: attachment.file.contentType
                            });
                            FileSaver.saveAs(file, attachment.file.name);
                        });
                    }
                }
            }
        };

        //        vm.addStore = function () {
        //            vm.indexStore = vm.stores.length;
        //            vm.store = {
        //                isNew: true
        //            };
        //            vm.modalStore = modal.open({
        //                animation: true,
        //                templateUrl: 'store_detail.html',
        //                scope: $scope,
        //                backdrop: 'static',
        //                keyboard: false,
        //                size: 'md'
        //            });
        //        };

        //        vm.editStore = function (index) {
        //            vm.indexStore = index;
        //            vm.store = JSON.parse(JSON.stringify(vm.stores[index]));
        //            vm.store.isNew = false;
        //            vm.modalStore = modal.open({
        //                animation: true,
        //                templateUrl: 'store_detail.html',
        //                scope: $scope,
        //                backdrop: 'static',
        //                keyboard: false,
        //                size: 'md'
        //            });
        //        };

        vm.deleteFarmStore = function(index) {
            vm.indexStore = index;
            vm.modalStore = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_store.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        //        vm.confirmDeleteStore = function () {
        //            vm.stores.splice(vm.indexStore, 1);
        //            vm.indexStore = -1;
        //            vm.modalStore.close();
        //        }

        //        vm.saveStore = function () {
        //            if (vm.indexStore >= 0) {
        //                if (vm.indexStore === vm.stores.length) {
        //                    vm.stores.push(vm.store);
        //                } else {
        //                    vm.stores[vm.indexStore] = vm.store;
        //                }
        //            }
        //            vm.indexStore = -1;
        //            vm.modalStore.close();
        //        }

        vm.updateChangeHusbandrySelected = function() {
            vm.farm.farmHusbandryType = vm.husbandryTypesSelected.map(element => {
                return {
                    husbandryType: element
                }
            });
        }
        vm.onHusbandryTypeSelected = function(item) {
            vm.updateChangeHusbandrySelected();
        }

        vm.onRemovedHusbandry = function() {
                vm.updateChangeHusbandrySelected();
            }
            //bỏ đoạn này đi vì loại động vật(farmAnimals chỉ lưu động vật cha)
            /*vm.onSelectedAnimal = function(animal) {
                vm.animal = animal;
                if (vm.animal != null && vm.animal.id > 0) {
                    animalService.getListByParentId(vm.animal.id).then(function(data) {
                        vm.list = data;
                        if (vm.list != null && vm.list.length > 0) {
                            if (vm.animalByParents != null && vm.animalByParents.length > 0) {
                                // vm.animalByParents.unshift(vm.list);
                                for (var j = 0; j < vm.list.length; j++) {
                                    vm.animalByParents.unshift(vm.list[j]);
                                }
                            } else {
                                vm.animalByParents = data;
                            }
                        }

                    });
                }
            }
            vm.onRemovedAnimal = function(animal) {
                if (vm.farm.farmAnimals != null && vm.farm.farmAnimals.length > 0) {
                    for (var i = 0; i < vm.farm.farmAnimals.length; i++) {
                        if (animal != null && animal.id > 0 && vm.farm.farmAnimals[i] != null && vm.farm.farmAnimals[i].parent != null && typeof vm.farm.farmAnimals[i].parent !== "undefined" && typeof vm.farm.farmAnimals[i].parent.id !== "undefined" && vm.farm.farmAnimals[i].parent.id == animal.id) {
                            vm.farm.farmAnimals.splice(i, 1);
                            i--;
                        }
                    }
                }
                if (animal.id > 0 && vm.animalByParents != null && vm.animalByParents.length > 0) {

                    for (var j = 0; j < vm.animalByParents.length; j++) {
                        if (animal != null && animal.id > 0 && vm.animalByParents[j] != null && vm.animalByParents[j].parent != null && typeof vm.animalByParents[j].parent !== "undefined" && typeof vm.animalByParents[j].parent.id !== "undefined" && vm.animalByParents[j].parent.id == animal.id) {
                            vm.animalByParents.splice(j, 1);
                            j--;
                        }
                    }
                   
                }
                vm.autoGenerateChange

            }*/
        vm.autoGenerateChange = function() {
            if (vm.selectedCity != null && vm.selectedDist != null && vm.currentId == null) {
                service.autoGenericCode(vm.selectedDist.code, vm.selectedDist.id, vm.selectedCity.code).then(function(data) {
                    if (vm.farm != null && data.code != null)
                        vm.farm.code = data.code;
                });
            }
            /*
             * else if(vm.currentId==null){
             * toastr.warning($translate.instant("farm.emptyAdministrativeUnit"),
             * $translate.instant("toastr.warning"));
             * 
             * return false; }
             */
        }

        vm.changeFarmSize = function(quantity) {
            if (quantity > 0) {
                farmSizeService.getByQuantity(quantity).then(function(data) {
                    if (vm.farm == null)
                        vm.farm = {};
                    vm.farm.farmSize = data;
                });
            }

        }

        vm.position = [];
        // Google MAP
        $scope.initMap = function() {
            var lat = 20.447188;
            var lng = 106.165732;
            var zoom = 10;
            if (vm.farm != null && vm.farm.administrativeUnit != null && vm.farm.administrativeUnit.latitude != null && vm.farm.administrativeUnit.latitude != "" && vm.farm.administrativeUnit.longitude != null && vm.farm.administrativeUnit.longitude != "") {
                lat = parseFloat(vm.farm.administrativeUnit.latitude);
                lng = parseFloat(vm.farm.administrativeUnit.longitude);
                zoom = 15;
            } else if (vm.selectedDist != null && vm.selectedDist.latitude != null && vm.selectedDist.latitude != "" && vm.selectedDist.longitude != null && vm.selectedDist.longitude != "") {
                lat = parseFloat(vm.selectedDist.latitude);
                lng = parseFloat(vm.selectedDist.longitude);
                zoom = 12;
            } else if (vm.selectedCity != null && vm.selectedCity.latitude != null && vm.selectedCity.latitude != "" && vm.selectedCity.longitude != null && vm.selectedCity.longitude != "") {
                lat = parseFloat(vm.selectedCity.latitude);
                lng = parseFloat(vm.selectedCity.longitude);
            }
            var map = new google.maps.Map(document.getElementById('map-pick'), {
                zoom: zoom,
                center: {
                    lat: lat,
                    lng: lng
                }
            });
            var bounds = {
                north: 23.322070,
                south: 8.690170,
                east: 123.926840,
                west: 92.053184
            };
            // Create the search box and link it to the UI element.
            var input = document.getElementById('pac-input');
            var searchBox = new google.maps.places.SearchBox(input);
            map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

            // Bias the SearchBox results towards current map's viewport.
            map.addListener('bounds_changed', function() {
                searchBox.setBounds(map.getBounds());
            });



            var markers = [];
            if (vm.farm.longitude != null && vm.farm.latitude != null) {
                markers.push(new google.maps.Marker({
                    position: {
                        lat: Number(vm.farm.latitude),
                        lng: Number(vm.farm.longitude)
                    },
                    map: map //,
                        //                    name:element.name,
                        //                    ownerName:element.ownerName,
                        //                    ownerPhoneNumber:element.ownerPhoneNumber,
                        //                    adressDetail:element.adressDetail
                }));
            }
            // Listen for the event fired when the user selects a prediction and retrieve
            // more details for that place.
            searchBox.addListener('places_changed', function() {
                var places = searchBox.getPlaces();

                if (places.length == 0) {
                    return;
                }

                // Clear out the old markers.
                markers.forEach(function(marker) {
                    marker.setMap(null);
                });
                markers = [];

                // For each place, get the icon, name and location.
                var bounds = new google.maps.LatLngBounds();
                places.forEach(function(place) {
                    if (!place.geometry) {
                        return;
                    }
                    var icon = {
                        url: place.icon,
                        size: new google.maps.Size(71, 71),
                        origin: new google.maps.Point(0, 0),
                        anchor: new google.maps.Point(17, 34),
                        scaledSize: new google.maps.Size(25, 25)
                    };

                    // Create a marker for each place.
                    markers.push(new google.maps.Marker({
                        map: map,
                        icon: icon,
                        title: place.name,
                        position: place.geometry.location
                    }));

                    if (place.geometry.viewport) {
                        // Only geocodes have viewport.
                        bounds.union(place.geometry.viewport);
                    } else {
                        bounds.extend(place.geometry.location);
                    }
                });
                //map.fitBounds(bounds);
            });


            map.addListener('click', function(e) {
                placeMarkerAndPanTo(e.latLng, map);
            });

            var tickMarker = [];

            function placeMarkerAndPanTo(latLng, map) {
                tickMarker.forEach(function(m) {
                    m.setMap(null);
                });
                var marker = new google.maps.Marker({
                    position: latLng,
                    map: map
                });
                tickMarker.push(marker);
                var coordinates = latLng.toString().replace('(', '').replace(')', '').trim();
                vm.position = coordinates.split(',');
            }
        };

        //        vm.modalMap=null;
        vm.showMap = function() {
            vm.modalMap = modal.open({
                animation: true,
                templateUrl: 'vgmap.html',
                scope: $scope,
                // backdrop: 'static',
                keyboard: false,
                size: 'lg',
                windowClass: "customer-modal-lg",
                opened: {}
                /*
                 * opened:{ initMap(); }
                 */
            });

            /*
             * vm.modalMap.opened.then(function(){ $scope.initMap(); });
             */
            // google.maps.event.addDomListener(window, "load", $scope.initMap());
            vm.modalMap.rendered.then(function() { $timeout(initVGMap, 1000); });

        };
        vm.confirmCoordinate = function() {
            vm.farm.longitude = vm.position[1].trim();
            vm.farm.latitude = vm.position[0].trim();
            //        	vm.farm.gMapX = vm.position[0].trim();
            //            vm.farm.gMapY = vm.position[1].trim();
            if (vm.modalMap) {
                vm.modalMap.close();
            }
        }
        vm.farmNameChange = function() {
            if (vm.farm.name != null) {
                vm.farm.ownerName = vm.farm.name;
            }
        }
        vm.farmPhoneNumberChange = function() {
            const a = document.getElementById('longitude').value;
            if (vm.farm.phoneNumber != null) {
                vm.farm.ownerPhoneNumber = vm.farm.phoneNumber;
            }
        }
        vm.checkMapLatitudeOut = false;
        vm.checkMapLatitude = function() {
            // const a = document.getElementById('latitude').value;
            if (vm.farm.latitude != null) {
                if ((8.3 > parseFloat(vm.farm.latitude) || parseFloat(vm.farm.latitude) > 23.5)) {
                    vm.checkMapLatitudeOut = true;
                } else {
                    vm.checkMapLatitudeOut = false;
                }
                vm.convertLatLonToVN2000();
            }
        }
        vm.checkMapLongitudeOut = false;
        vm.checkMapLongitude = function() {
            // const b = document.getElementById('longitude').value;
            if (vm.farm.longitude != null) {
                if ((101.7 > parseFloat(vm.farm.longitude) || parseFloat(vm.farm.longitude) > 109.5)) {
                    vm.checkMapLongitudeOut = true;
                } else {
                    vm.checkMapLongitudeOut = false;
                }
                vm.convertLatLonToVN2000();
            }
        }


        vm.onSelectAnimalType = function() {
            vm.farm.farmAnimalTypes = vm.animalTypes.filter(el => el.checked == true);
        }

        vm.onSelectProductTarget = function(selected) {
            if (selected.checked == true) {
                // tìm những thằng cha đã đc chon
                let check = vm.productTargets.filter(e => e.parent == null && e.checked == true);
                // có 1 mục đích chính (ko có cha) đã được chọn
                if (check && check.length > 0) {
                    if (selected.parent) {
                        if (check[0].id == selected.parent.id)
                            vm.productTargets.map(e => {
                                if (e.id == selected.id) {
                                    e.checked = true;
                                }
                            });
                    } else {
                        vm.productTargets.map(e => {
                            e.checked = false;
                            if (e.id == selected.id) e.checked = true;
                        });
                    }
                } else {
                    vm.productTargets.map(e => {
                        if (e.id == selected.id) {
                            e.checked = true;
                        }
                    });
                }
            } else {
                vm.productTargets.map(e => {
                    if (e.id == selected.id) {
                        e.checked = false;
                    }
                });
            }
            // hiện tại để 1 trong 2 Nuôi thương mại hoặc Nuôi phi thương mại
            vm.productTargetsNullParent = vm.productTargets.filter(e => e.parent == null)
            vm.farm.farmProductTargets = vm.productTargets.filter(el => el.checked == true);
        }

        vm.getListProductTargetChild = function(productTarget) {
                return vm.productTargets.filter(el => el.parent != null && el.parent.id == productTarget.id);
            }
            //thanh
        vm.onSelectHusbandryTarget = function(husbandryTarget) {
            if (!husbandryTarget.checked) {
                vm.onHusbandryMethodSelect(husbandryTarget).forEach(el => {
                    el.checked = false;
                });
            }
            vm.farm.farmhusbandryTargets = vm.husbandryTargets.filter(el => el.checked == true);
        }

        vm.createAnimalReportData = function() {
            vm.animalReportData = { isNew: true };
            vm.getListAnimal();
            vm.getAllProductTarget();
            vm.getAllOgirinal();
            vm.individuals = [];
            vm.modalAnimalReportData = modal.open({
                animation: true,
                templateUrl: 'animal_report_data_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.editAnimalReportData = function(index) {
            vm.getListAnimal();
            vm.getAllProductTarget();
            vm.getAllOgirinal();
            vm.animalReportDataIndex = index;

            const item = angular.copy(vm.animalReports[index]);
            if (item) {
                vm.animalReportData = item || {};
                vm.animalReportData.isNew = false;
                vm.tempCode = vm.animalReportData.code;
                if (vm.animalReportData) {
                    vm.individuals = angular.copy(item.individualAnimals);
                }
                vm.modalAnimalReportData = modal.open({
                    animation: true,
                    templateUrl: 'animal_report_data_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
            }
        }
        vm.deleteAnimalReportData = function(index) {
            vm.animalReportDataIndex = index;
            vm.modalDeleteConfirm = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_animal_report.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        vm.confirmDeleteAnimalReport = function() {
            vm.animalReports.splice(vm.animalReportDataIndex, 1);
            vm.animalReportDataIndex = null;
            vm.modalDeleteConfirm.close();
        }

        vm.getListAnimal = function() {
            animalReportDataService.getAllAnimal().then(function(data) {
                vm.animals = data;
                vm.animalsGiveBirths = data.filter(function(el) {
                    return el.reproductionForm == settings.ReproductionForm.giveAnimalBirth.value;
                });
                vm.animalsGiveEggs = data.filter(function(el) {
                    return el.reproductionForm == settings.ReproductionForm.giveEggBirth.value;
                });
            });
        }
        vm.getAllOgirinal = function() {
            animalReportDataService.getAllOgirinal().then(function(data) {
                vm.originals = data;
            });
        }
        vm.getAllProductTarget = function() {
            animalReportDataService.getAllProductTarget().then(function(data) {
                vm.productTargets = data;
            });
        }

        vm.checkValidAnimalReportData = function() {
            // if(!vm.animalReportData.farm){
            //     toastr.warning($translate.instant("exportAnimal.emptyFarm"), $translate.instant("toastr.warning"));
            //     return false;
            // }
            if (!vm.animalReportData.animal) {
                toastr.warning($translate.instant("exportAnimal.emptyAnimal"), $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.animalReportData.year) {
                toastr.warning("Bạn phải chọn năm của kỳ báo cáo", $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.animalReportData.type) {
                toastr.warning("Bạn phải chọn loại", $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.animalReportData.original) {
                toastr.warning("Bạn phải nguồn gốc", $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.animalReportData.productTarget) {
                toastr.warning("Bạn phải chọn mục đích nuôi", $translate.instant("toastr.warning"));
                return false;
            }
            if (vm.animalReportData.unGender < 0) {
                toastr.warning("Số lượng vật nuôi không rõ giới tính phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            if (vm.animalReportData.female < 0) {
                toastr.warning("Số lượng vật nuôi cái phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            if (vm.animalReportData.male < 0) {
                toastr.warning("Số lượng vật nuôi đực phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            let total = 0;
            if (vm.animalReportData.male >= 0) {
                total += vm.animalReportData.male
            }
            if (vm.animalReportData.female >= 0) {
                total += vm.animalReportData.female
            }
            if (vm.animalReportData.unGender >= 0) {
                total += vm.animalReportData.unGender
            }
            if (total < 0) {
                toastr.warning("Tổng số vật nuôi phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.saveAnimalReportData = function() {
            if (vm.checkValidAnimalReportData()) {
                if (vm.animalReportData && vm.animalReportData.isNew == true) {
                    const len = vm.animalReports.length;
                    vm.animalReportData.individualAnimals = vm.individuals;
                    vm.animalReports[len] = vm.animalReportData;
                    vm.modalAnimalReportData.close();
                } else if (vm.animalReportData) {
                    vm.animalReportData.individualAnimals = vm.individuals;
                    vm.animalReports[vm.animalReportDataIndex] = vm.animalReportData;
                    vm.modalAnimalReportData.close();
                }
                if (vm.farm && vm.farm.id) {
                    vm.animalReportData.farm = { id: vm.farm.id };

                    animalReportDataService.createAnimalReportData(vm.animalReportData).then(function() {
                        vm.pageIndexAnimalReportData = 1;
                        vm.pageSizeAnimalReportData = 100;
                        vm.getPageAnimalReportDataByFarmId();
                    });
                }
            }
        }

        function getPageAnimalReportDataByFarmId() {
            service.getPageByFarmId(vm.currentId, vm.pageIndexAnimalReportData, vm.pageSizeAnimalReportData).then(function(data) {
                vm.animalReports = data.content;
                vm.animalReportDatas = data.content;
                vm.totalElementsAnimalReportDatas = data.totalElements;
            });
        }

        vm.addIndividual = function() {
            vm.individual = { isNew: true };
            vm.modalIndividualAnimal = modal.open({
                animation: true,
                templateUrl: 'individual_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        vm.editIndividual = function(index) {
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

        vm.saveIndividual = function() {
            if (vm.individual && vm.individual.isNew == true) {
                const len = vm.individuals.length;
                vm.individuals[len] = vm.individual;
                vm.updateTotalView();
                vm.modalIndividualAnimal.close();
            } else if (vm.individual) {
                vm.individuals[vm.individualIndex] = vm.individual;
                vm.updateTotalView();
                vm.modalIndividualAnimal.close();
            }
        }
        vm.updateTotalView = function() {
            if (vm.individuals && vm.individuals.length > 0) {
                vm.animalReportData.male = 0;
                vm.animalReportData.female = 0;
                vm.animalReportData.unGender = 0;
                for (let index = 0; index < vm.individuals.length; index++) {
                    const item = vm.individuals[index];
                    if (item.gender == settings.ENUM_AnimalGenders.male) {
                        vm.animalReportData.male++;
                    } else if (item.gender == settings.ENUM_AnimalGenders.female) {
                        vm.animalReportData.female++;
                    } else if (item.gender == settings.ENUM_AnimalGenders.unGender) {
                        vm.animalReportData.unGender++;
                    }
                }
            }
        }
        vm.deleteIndividual = function(index) {
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
        vm.confirmDeleteIndividual = function() {
            vm.individuals.splice(vm.individualIndex, 1);
            vm.modalIndividualConfirmDelete.close();
            vm.updateTotalView();
        }
        vm.getAnimalGender = function(value) {
            const result = settings.animalGenders.find(item => item.value == value);
            if (result) {
                return result.name;
            } else return "";

        }
        vm.getAnimalStatus = function(value) {
            const result = settings.IndividualAnimalStatuses.find(item => item.value == value);
            if (result) {
                return result.name;
            } else return "";
        }

        vm.listProductTarget = [];
        productTargetService.getAllProductTarget().then(function(data) {
            vm.listProductTarget = data;
        });
        vm.getAllOgirinal();
        vm.modalPopupSource = null;
        vm.modalProductTarget = null;
        vm.listDto = [];
        vm.openPopupSelectTarget = function() {
            vm.modalProductTarget = modal.open({
                animation: true,
                templateUrl: 'modalSelectProductTarget.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        vm.openPopupSelectSource = function() {
            vm.modalPopupSource = modal.open({
                animation: true,
                templateUrl: 'modalSelectOriginal.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.selectProductTarget = function(index) {
            const selectedItem = vm.productTargets[index];
            vm.rawTable.purpose = selectedItem.code;
            vm.rawTable.productTarget = { id: selectedItem.id };
            vm.modalProductTarget.close();
        }
        vm.selectOriginal = function(index) {
            const selectedItem = vm.originals[index];
            vm.rawTable.source = selectedItem.code;
            vm.rawTable.original = { id: selectedItem.id };
            vm.modalPopupSource.close();
        }

        vm.calcTotal = function(...arr) {
            let total = 0;
            for (let index = 0; index < arr.length; index++) {
                let numberSapmle = arr[index];
                if (!numberSapmle) {
                    numberSapmle = 0;
                }
                total += numberSapmle;
            }
            return total;
        }
        vm.rawTable = {
            year: new Date().getFullYear()
        };

        vm.addAnimalReport = function() {
                if (!vm.rawTable.animal) {
                    toastr.warning("Phải chọn loài động vật");
                } else {
                    vm.rawTable.totalParent = vm.calcTotal(vm.rawTable.maleParent, vm.rawTable.femaleParent, vm.rawTable.unGenderParent);
                    vm.rawTable.totalChild = vm.calcTotal(vm.rawTable.maleChild, vm.rawTable.femaleChild, vm.rawTable.unGenderChild);
                    vm.listDto.push(angular.copy(vm.rawTable));
                    vm.rawTable = {
                        year: vm.currentYear
                    };
                }

            }
            //vm.getListAnimal();
        vm.modalPopupSelectAnimal = null;
        vm.openPopupAnimal = function() {
            vm.textSearch = "";
            vm.modalPopupSelectAnimal = modal.open({
                animation: true,
                templateUrl: 'modalPopupSelectAnimal.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        };
        vm.selectAnimalAndClosePopup = function(item) {
            for (let index = 0; vm.animals.length; index++) {
                const animal = vm.animals[index];
                if (item.id == animal.id) {
                    vm.rawTable.animal = angular.copy(animal);
                    break;
                }
            }
            vm.modalPopupSelectAnimal.close();
        }
        vm.removeListSelected = function(index) {
            vm.listDto.splice(index, 1);
        }
        vm.removeListBearDtoSelected = function(index) {
            vm.listBearDto.splice(index, 1);
        }
        vm.removeListNormalSelected = function(index) {
            vm.listNormalAnimal.splice(index, 1);
        }
        vm.saveListAnimalReport = function() {
            let listDtoSave = angular.copy(vm.listDto);
            if (vm.rawTable.animal) {
                listDtoSave.push(vm.rawTable);
            }
            if (listDtoSave && listDtoSave.length > 0 && vm.farm.id) {
                if (!vm.isNew) {
                    vm.farmPeriod.listAnimalReportDataFormDto = [];
                }
                listDtoSave.forEach(function(item) {
                    item.farm = { id: vm.farm.id };
                    if (vm.isNew == true) {
                        item.month = vm.monthAnimalReportType1;
                        item.year = vm.yearAnimalReportType1;
                        item.title = vm.monthAnimalReportType1 + "/" + vm.yearAnimalReportType1;
                    }
                    if (!vm.isNew) {
                        item.year = vm.farmPeriod.year;
                        item.month = vm.farmPeriod.month;
                        item.title = vm.farmPeriod.year;
                        vm.farmPeriod.listAnimalReportDataFormDto.push(item);
                    }
                });
                if (!vm.isNew) {
                    service.updateAnimalReportData(vm.farmPeriod).then(function() {
                        vm.listDto = [];
                        vm.rawTable = { year: vm.currentYear };
                    });
                } else {
                    service.saveAnimalReportData({
                        farmId: vm.farm.id,
                        listAnimalReportDataFormDto: listDtoSave,
                        type: settings.AnimalReportDataFormType.firstType,
                        year: vm.yearAnimalReportType1,
                        month: vm.monthAnimalReportType1,
                        title: vm.monthAnimalReportType1 + "/" + vm.yearAnimalReportType1
                    }).then(function(data) {
                        vm.generate();
                        vm.listDto = [];
                        vm.rawTable = { year: vm.currentYear };
                    });
                }

            }
            vm.modalPopupTableType1.close();
        }
        vm.getAnimalReportDataType = function(value) {
            const res = settings.AnimalReportDataType.find(el => el.value == value);
            if (res) {
                return res.name;
            } else
                return "";
        }
        vm.onSelectedProductTarget = function() {
            if (vm.rawTable.productTarget) {
                vm.rawTable.purpose = vm.rawTable.productTarget.code;
            } else
                vm.rawTable.purpose = null;
        }
        vm.onSelectedOriginal = function() {
            if (vm.rawTable.original) {
                vm.rawTable.source = vm.rawTable.original.code;
            } else
                vm.rawTable.source = null;
        }

        vm.listChipCodes = [];
        vm.rawTableType2 = { year: vm.currentYear };
        vm.modalPopupInputChipCode = null;
        vm.listBearDto = [];
        vm.showPopupInputChipCode = function(rawTable) {
            let len = 0;
            if (rawTable.maleBearHasChip) {
                len += rawTable.maleBearHasChip;
            }
            if (rawTable.femaleBearHasChip) {
                len += rawTable.femaleBearHasChip;
            }
            if (len > 0) {
                vm.listChipCodes = [];
                for (let index = 0; index < len; index++) {
                    vm.listChipCodes.push({ code: "" });
                }
                vm.modalPopupInputChipCode = modal.open({
                    animation: true,
                    templateUrl: 'modalPopupInputChipCode.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
            }
        }
        vm.saveListChipCode = function(list) {
            vm.rawTableType2.listChipCode = list.map(el => el.code);
            vm.modalPopupInputChipCode.close();
        }
        vm.addAnimalBearReport = function() {
            if (!vm.rawTableType2.animal) {
                toastr.warning("Phải chọn loài động vật");
            } else {
                vm.rawTableType2.totalBearHasChip = vm.calcTotal(vm.rawTableType2.maleBearHasChip, vm.rawTableType2.femaleBearHasChip);
                vm.rawTableType2.totalBearNoChip = vm.calcTotal(vm.rawTableType2.maleBearNoChip, vm.rawTableType2.femaleBearNoChip, vm.rawTableType2.reasonBornAtFarm, vm.rawTableType2.reasonOther);
                vm.listBearDto.push(angular.copy(vm.rawTableType2));
                vm.rawTableType2 = {
                    year: vm.currentYear
                };
            }
        }
        vm.saveListBearReport = function() {
            let listBearDtoSave = angular.copy(vm.listBearDto);
            if (vm.rawTableType2.animal) {
                listBearDtoSave.push(vm.rawTableType2);
            }
            if (listBearDtoSave && listBearDtoSave.length > 0 && vm.farm.id) {
                if (!vm.isNew) {
                    vm.farmPeriod.listAnimalReportDataFormDto = [];
                }
                listBearDtoSave.forEach(function(item) {
                    item.farm = { id: vm.farm.id };
                    if (vm.isNew == true) {
                        item.year = vm.yearAnimalReportType2;
                        item.month = vm.monthAnimalReportType2;
                        item.title = vm.monthAnimalReportType2 + '/' + vm.yearAnimalReportType2;
                    }
                    if (!vm.isNew) {
                        item.year = vm.farmPeriod.year;
                        item.month = vm.farmPeriod.month;
                        item.title = vm.farmPeriod.month + '/' + vm.farmPeriod.year;
                        vm.farmPeriod.listAnimalReportDataFormDto.push(item);
                    }
                });

                if (!vm.isNew) {
                    service.updateAnimalReportData(vm.farmPeriod).then(function() {
                        vm.listBearDto = [];
                        vm.rawTableType2 = { year: vm.currentYear };
                    });
                } else {
                    service.saveAnimalReportData({
                        farmId: vm.farm.id,
                        listAnimalReportDataFormDto: listBearDtoSave,
                        type: settings.AnimalReportDataFormType.secondType,
                        year: vm.yearAnimalReportType2,
                        month: vm.monthAnimalReportType2,
                        title: vm.monthAnimalReportType2 + '/' + vm.yearAnimalReportType2
                    }).then(function(data) {
                        vm.generate();
                        vm.listBearDto = [];
                        vm.rawTableType2 = { year: vm.currentYear };
                    });
                }
            }
            vm.modalPopupTableType2.close();
        }

        vm.rawTableType3 = { year: vm.currentYear };
        vm.listNormalAnimal = [];
        vm.addNormalAnimalReport = function() {
            if (!vm.rawTableType3.animal) {
                toastr.warning("Phải chọn loài nuôi");
            } else if (!vm.rawTableType3.totalNormal || vm.rawTableType3.totalNormal <= 0) {
                toastr.warning("Tổng số lượng phải lớn hơn 0");
            } else {
                vm.listNormalAnimal.push(angular.copy(vm.rawTableType3));
                vm.rawTableType3 = {
                    year: vm.currentYear
                };
            }
        }

        vm.saveListNormalReport = function() {
            let listPetsDto = angular.copy(vm.listNormalAnimal);
            if (vm.rawTableType3.animal) {
                listPetsDto.push(vm.rawTableType3);
            }
            if (listPetsDto && listPetsDto.length > 0 && vm.farm.id) {
                if (!vm.isNew) {
                    vm.farmPeriod.listAnimalReportDataFormDto = [];
                }
                listPetsDto.forEach(function(item) {
                    item.farm = { id: vm.farm.id };
                    if (vm.isNew == true) {
                        item.year = vm.yearAnimalReportType3;
                        item.month = vm.monthAnimalReportType3;
                        item.title = vm.monthAnimalReportType3 + '/' + vm.yearAnimalReportType3;
                    }
                    if (!vm.isNew) {
                        item.year = vm.farmPeriod.year;
                        item.title = vm.farmPeriod.year;
                        item.month = vm.farmPeriod.month;
                        vm.farmPeriod.listAnimalReportDataFormDto.push(item);
                    }
                });

                if (!vm.isNew) {
                    service.updateAnimalReportData(vm.farmPeriod).then(function() {
                        vm.listNormalAnimal = [];
                        vm.rawTableType3 = { year: vm.currentYear };
                    });
                } else {
                    service.saveAnimalReportData({
                        farmId: vm.farm.id,
                        listAnimalReportDataFormDto: listPetsDto,
                        type: settings.AnimalReportDataFormType.thirdType,
                        month: vm.monthAnimalReportType3,
                        year: vm.yearAnimalReportType3,
                        title: vm.monthAnimalReportType3 + '/' + vm.yearAnimalReportType3
                    }).then(function(data) {
                        vm.generate();
                        vm.listNormalAnimal = [];
                        vm.rawTableType3 = { year: vm.currentYear };
                    });
                }
            }
            vm.modalPopupTableType3.close();
        }
        vm.yearAnimalReportType1 = vm.currentYear;
        vm.yearAnimalReportType2 = vm.currentYear;
        vm.yearAnimalReportType3 = vm.currentYear;

        vm.monthAnimalReportType1 = vm.currentMonth;
        vm.monthAnimalReportType2 = vm.currentMonth;
        vm.monthAnimalReportType3 = vm.currentMonth;

        vm.isNew = false;
        vm.farmPeriod = null;
        vm.modalPopupTableType1 = null;
        vm.showPopupTableType1 = function(id) {
            if (id) {
                vm.listDto = [];
                vm.rawTable = {};
                vm.isNew = false;
                service.getFarmPeriodByIdAndType(id, settings.AnimalReportDataFormType.firstType).then(function(data) {
                    vm.farmPeriod = data;
                });
            } else {
                vm.yearAnimalReportType1 = vm.currentYear;
                vm.rawTable = { year: vm.currentYear };
                vm.listDto = [];
                vm.isNew = true;
            }
            vm.modalPopupTableType1 = modal.open({
                animation: true,
                templateUrl: 'modalPopupTableType1.html',
                scope: $scope,
                windowClass: "customer-modal-lg",
                backdrop: 'static',
                keyboard: false,
                size: 'lg'
            });
        }
        vm.modalPopupTableType2 = null;
        vm.showPopupTableType2 = function(id) {
            if (id) {
                vm.listBearDto = [];
                vm.rawTableType2 = {};
                vm.isNew = false;
                service.getFarmPeriodByIdAndType(id, settings.AnimalReportDataFormType.secondType).then(function(data) {
                    vm.farmPeriod = data;
                });
            } else {
                vm.yearAnimalReportType2 = vm.currentYear;
                vm.isNew = true;
                vm.listBearDto = [];
                vm.rawTableType2 = { year: vm.currentYear };
            }
            vm.modalPopupTableType2 = modal.open({
                animation: true,
                templateUrl: 'modalPopupTableType2.html',
                scope: $scope,
                windowClass: "customer-modal-lg",
                backdrop: 'static',
                keyboard: false,
                size: 'lg'
            });
        }
        vm.modalPopupTableType3 = null;
        vm.showPopupTableType3 = function(id) {
            if (id) {
                vm.listNormalAnimal = [];
                vm.rawTableType3 = {};
                vm.isNew = false;
                service.getFarmPeriodByIdAndType(id, settings.AnimalReportDataFormType.thirdType).then(function(data) {
                    vm.farmPeriod = data;
                });
            } else {
                vm.yearAnimalReportType3 = vm.currentYear;
                vm.isNew = true;
                vm.listNormalAnimal = [];
                vm.rawTableType3 = { year: vm.currentYear };
            }
            vm.modalPopupTableType3 = modal.open({
                animation: true,
                templateUrl: 'modalPopupTableType3.html',
                scope: $scope,
                windowClass: "customer-modal-lg",
                backdrop: 'static',
                keyboard: false,
                size: 'lg'
            });
        }

        vm.getListBear = function(list) {
            if (list && list.length > 0) {
                return list.filter(el => {
                    if (el.name && el.name.toLowerCase().indexOf('gấu') != -1) {
                        return true;
                    }
                    return false;
                });
            }
        }
        vm.getListOtherBear = function(list) {
            if (list && list.length > 0) {
                return list.filter(el => {
                    if (el.name && el.name.toLowerCase().indexOf('gấu') == -1) {
                        return true;
                    }
                    return false;
                });
            }
        }
        $scope.filterAnimalReport = function(p) {
            if (p.formId == null) {
                return p;
            } else {
                return;
            }
        }

        vm.modalPopupReportFormAnimalEgg = null;
        vm.reportFormAnimalEgg = null;
        vm.reportFormAnimalEggs = [];
        vm.addEditReportAnimalEggs = []; // List năm tháng ngày import trứng
        vm.reportFormAnimalEggSearchDto = {};
        vm.reportFormAnimalEggPageIndex = 1;
        vm.reportFormAnimalEggPageSize = 100;
        vm.reportFormAnimalEggTotalItems = 0;
        vm.addToListAddEditFormAnimalEgg = function() {
            if (vm.reportFormAnimalEgg && !vm.reportFormAnimalEgg.animal) {
                toastr.warning("Chưa chọn loài nuôi", "Cảnh báo");
                return;
            }
            if (!vm.reportFormAnimalEgg.parentMale || vm.reportFormAnimalEgg.parentMale <= 0) {
                toastr.warning("Số đàn bố mẹ (đực) " + vm.reportFormAnimalEgg.animal.name + " không được để trống. Vui lòng thêm dữ liệu tại 'Biểu mẫu số 16'.", "Cảnh báo");
                return;
            }
            if (!vm.reportFormAnimalEgg.parentFemale || vm.reportFormAnimalEgg.parentFemale <= 0) {
                toastr.warning("Số đàn bố mẹ (cái) " + vm.reportFormAnimalEgg.animal.name + " không được để trống. Vui lòng thêm dữ liệu tại 'Biểu mẫu số 16'.", "Cảnh báo");
                return;
            }
            if (vm.reportFormAnimalEgg && vm.reportFormAnimalEgg.animal && (vm.reportFormAnimalEgg.quantityChildIncrement == "" || !vm.reportFormAnimalEgg.quantityChildIncrement)) {
                toastr.warning("Số con " + vm.reportFormAnimalEgg.animal.name + " cộng dồn theo thời gian không được để trống", "Cảnh báo");
                return;
            }
            if (vm.reportFormAnimalEgg && vm.reportFormAnimalEgg.animal && (vm.reportFormAnimalEgg.quantityChildIncrement != "" && vm.reportFormAnimalEgg.quantityChildIncrement <= 0)) {
                toastr.warning("Số con " + vm.reportFormAnimalEgg.animal.name + " cộng dồn theo thời gian không được nhỏ hơn 0", "Cảnh báo");
                return;
            }
            if (vm.reportFormAnimalEgg) {
                vm.reportFormAnimalEgg.quantityChildLive = vm.reportFormAnimalEgg.quantityChildHatch - vm.reportFormAnimalEgg.quantityChildDie;
            }
            if (vm.reportFormAnimalEgg) {
                vm.reportFormAnimalEgg.remainQuantity = vm.reportFormAnimalEgg.quantityChildIncrement - vm.reportFormAnimalEgg.quantityChildSeparateCaptivity;
            }
            if (!vm.addEditReportAnimalEggPeriod) {
                vm.addEditReportAnimalEggPeriod = {};
            }
            if (!vm.addEditReportAnimalEggPeriod.reportFormAnimalEggs) {
                vm.addEditReportAnimalEggPeriod.reportFormAnimalEggs = [];
            }
            if (!vm.listAnimalGiveEggs) {
                vm.listAnimalGiveEggs = [];
            }
            if (vm.reportFormAnimalEgg && vm.reportFormAnimalEgg.animal && vm.reportFormAnimalEgg.dateReport) {
                vm.listAnimalGiveEggs.push(angular.copy(vm.reportFormAnimalEgg));
                vm.reportFormAnimalEgg = { isNew: true, dateReport: new Date() };
            }
        }
        vm.removeToListAddEditFormAnimalEgg = function(index) {
            // vm.addEditReportAnimalEggPeriod.reportFormAnimalEggs.splice(index, 1);
            vm.listAnimalGiveEggs.splice(index, 1);
        }
        vm.addReportFormAnimalEgg = function(id) {
            vm.listAnimalGiveEggs = [];
            vm.getListAnimal();
            if (id) {
                service.getReportFormAnimalEggById(id).then(function(res) {
                    vm.reportFormAnimalEgg = {};
                    vm.reportFormAnimalEgg = res;
                    vm.reportFormAnimalEgg.isNew = false;
                    // vm.addEditReportAnimalEggPeriod = res;
                    // vm.onSelectMonthForm16(vm.addEditReportAnimalEggPeriod);
                    // if(vm.addEditReportAnimalEggPeriod.year && vm.addEditReportAnimalEggPeriod.month && vm.addEditReportAnimalEggPeriod.date){                                                
                    //     vm.reportFormAnimalEgg.dateReport = new Date(vm.addEditReportAnimalEggPeriod.year, vm.addEditReportAnimalEggPeriod.month-1, vm.addEditReportAnimalEggPeriod.date);
                    // }
                    //debugger
                    var report = vm.reportFormAnimalEgg;
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
                            vm.modalPopupReportFormAnimalEgg = modal.open({
                                animation: true,
                                templateUrl: 'report_form_animal_egg_table.html',
                                scope: $scope,
                                windowClass: "customer-modal-lg",
                                backdrop: 'static',
                                keyboard: false,
                                size: 'lg'
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
                                            vm.modalPopupReportFormAnimalEgg = modal.open({
                                                animation: true,
                                                templateUrl: 'report_form_animal_egg_table.html',
                                                scope: $scope,
                                                windowClass: "customer-modal-lg",
                                                backdrop: 'static',
                                                keyboard: false,
                                                size: 'lg'
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
            } else {
                vm.listAnimalGiveEggs = [];
                vm.addEditReportAnimalEggs = [];
                vm.reportFormAnimalEgg = {};
                vm.reportFormAnimalEgg.isNew = true;
                vm.reportFormAnimalEgg.dateReport = new Date();

                // vm.addEditReportAnimalEggPeriod = {};
                // vm.addEditReportAnimalEggPeriod.isNew = true;
                // vm.addEditReportAnimalEggPeriod.farm = vm.farm;
                // vm.addEditReportAnimalEggPeriod.year = new Date().getFullYear();
                // vm.addEditReportAnimalEggPeriod.month = new Date().getMonth() + 1;
                // vm.addEditReportAnimalEggPeriod.date = new Date().getDate();
                // vm.onSelectMonthForm16(vm.addEditReportAnimalEggPeriod);
                // if(vm.addEditReportAnimalEggPeriod.year && vm.addEditReportAnimalEggPeriod.month && vm.addEditReportAnimalEggPeriod.date){                                                
                //     vm.reportFormAnimalEgg.dateReport = new Date(vm.addEditReportAnimalEggPeriod.year, vm.addEditReportAnimalEggPeriod.month-1, vm.addEditReportAnimalEggPeriod.date);
                // }
                //debugger
                var report = vm.reportFormAnimalEgg;
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
                        vm.modalPopupReportFormAnimalEgg = modal.open({
                            animation: true,
                            templateUrl: 'report_form_animal_egg_table.html',
                            scope: $scope,
                            windowClass: "customer-modal-lg",
                            backdrop: 'static',
                            keyboard: false,
                            size: 'lg'
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
                                        vm.modalPopupReportFormAnimalEgg = modal.open({
                                            animation: true,
                                            templateUrl: 'report_form_animal_egg_table.html',
                                            scope: $scope,
                                            windowClass: "customer-modal-lg",
                                            backdrop: 'static',
                                            keyboard: false,
                                            size: 'lg'
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
            }
            vm.isQuantityChildEgg = false;


        }

        function getPageReportFormAnimalEgg() {
            if (vm.farm && vm.farm.id) {
                vm.reportFormAnimalEggSearchDto.farmId = vm.farm.id;
                // service.reportFormAnimalEggGetByPage(vm.reportFormAnimalEggSearchDto, vm.reportFormAnimalEggPageIndex, vm.reportFormAnimalEggPageSize).then(function(res){

                //     vm.reportFormAnimalEggs = res.content;
                //     vm.reportFormAnimalEggTotalItems = res.totalElements;
                // }).catch(function(err){
                //     vm.reportFormAnimalEggs = [];
                // });

                service.getPageReportFormAnimalEgg(vm.reportFormAnimalEggSearchDto, vm.reportFormAnimalEggPageIndex, vm.reportFormAnimalEggPageSize).then(function(res) {

                    vm.reportFormAnimalEggs = res.content;
                    vm.reportFormAnimalEggTotalItems = res.totalElements;
                }).catch(function(err) {
                    vm.reportFormAnimalEggs = [];
                });
            }
        }
        vm.getPageReportFormAnimalEgg();
        $scope.pageFormImportAnimalEggChange = function() {
            vm.getPageReportFormAnimalEgg();
        }
        vm.saveReportFormAnimalEgg = function() {
            if (vm.listAnimalGiveEggs && vm.listAnimalGiveEggs.length > 0) {
                vm.listAnimalGiveEggs.forEach(item => {
                    if (item.animal) {
                        return true;
                    } else {
                        toastr.warning("Chưa chọn loài nuôi", "Cảnh báo");
                        return false;
                    }
                    if (item.animal && (item.quantityChildIncrement == "" || item.quantityChildIncrement < 0)) {
                        toastr.warning("Số con " + item.animal.name + " cộng dồn theo thời gian không được để trống", "Cảnh báo");
                        return;
                    }
                    if (!item.parentMale || item.parentMale <= 0) {
                        toastr.warning("Số đàn bố mẹ (đực) " + item.animal.name + " không được để trống. Vui lòng thêm dữ liệu tại 'Biểu mẫu số 16'.", "Cảnh báo");
                        return;
                    }
                    if (!item.parentFemale || item.parentFemale <= 0) {
                        toastr.warning("Số đàn bố mẹ (cái) " + item.animal.name + " không được để trống. Vui lòng thêm dữ liệu tại 'Biểu mẫu số 16'.", "Cảnh báo");
                        return;
                    }
                });
            }
            if (vm.listAnimalGiveEggs && vm.listAnimalGiveEggs.length == 0) {
                if (vm.reportFormAnimalEgg && !vm.reportFormAnimalEgg.animal) {
                    toastr.warning("Chưa chọn loài nuôi", "Cảnh báo");
                    return;
                }
            }
            // if (!vm.reportFormAnimalEgg.parentMale || vm.reportFormAnimalEgg.parentMale <= 0) {
            //     toastr.warning("Số đàn bố mẹ (đực) " + vm.reportFormAnimalEgg.animal.name + " không được để trống. Vui lòng thêm dữ liệu tại 'Biểu mẫu số 16'.", "Cảnh báo");
            //     return;
            // }
            if (!vm.reportFormAnimalEgg.parentFemale || vm.reportFormAnimalEgg.parentFemale <= 0) {
                toastr.warning("Số con cái " + vm.reportFormAnimalEgg.animal.name + " trong trại không có !", "Cảnh báo");
                return;
            }

            // if (vm.reportFormAnimalEgg.parentFemale == null || vm.reportFormAnimalEgg.parentFemale == 0) {
            //     toastr.warning("Không có " + vm.reportFormAnimalEgg.animal.name + " cái trong trại", "Cảnh báo");
            //     return;
            // }
            if (vm.reportFormAnimalEgg && !vm.reportFormAnimalEgg.dateReport) {
                toastr.warning("Ngày lấy trứng khỏi tổ/ấp trứng/con non nở, chết không được để trống", "Cảnh báo");
                return;
            }

            if (vm.reportFormAnimalEgg && vm.reportFormAnimalEgg.animal && (vm.reportFormAnimalEgg.quantityChildIncrement == "" || !vm.reportFormAnimalEgg.quantityChildIncrement)) {
                toastr.warning("Số con " + vm.reportFormAnimalEgg.animal.name + " cộng dồn theo thời gian không được để trống", "Cảnh báo");
                return;
            }
            if (vm.reportFormAnimalEgg && vm.reportFormAnimalEgg.animal && (vm.reportFormAnimalEgg.quantityChildIncrement != "" && vm.reportFormAnimalEgg.quantityChildIncrement <= 0)) {
                toastr.warning("Số con " + vm.reportFormAnimalEgg.animal.name + " cộng dồn theo thời gian không được nhỏ hơn 0", "Cảnh báo");
                return;
            }
            if (vm.reportFormAnimalEgg.animal && vm.reportFormAnimalEgg) {
                vm.listAnimalGiveEggs.push(angular.copy(vm.reportFormAnimalEgg));
            }
            vm.listAnimalGiveEggs.forEach(function(item) {
                item.farm = vm.farm;
            });
            service.saveReportFormAnimalEggList(vm.listAnimalGiveEggs).then(function(data) {
                vm.modalPopupReportFormAnimalEgg.close();
                vm.getPageReportFormAnimalEgg();
                vm.searchByPage();
                getPageAnimalReportDataByFarmId();
                toastr.info("Lưu bản ghi thành công", "Thông báo");
                vm.isQuantityChildEgg = false;
            }).catch(function(err) {
                toastr.warning("Lưu bản ghi không thành công", "Cảnh báo");
            });
        }

        vm.modalConfirmDelete = null;
        vm.confirmDeleteReportFormAnimalEgg = function(id) {
                vm.modalConfirmDelete = modal.open({
                    animation: true,
                    templateUrl: 'confirm_raw_data.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
                vm.modalConfirmDelete.result.then(function(confirm) {
                    if (confirm == 'yes') {
                        // service.reportFormAnimalEggPeriodDeleteById(id).then(function(){
                        //     vm.getPageReportFormAnimalEgg();
                        //     toastr.info("Xóa bản ghi thành công", "Thông báo");
                        // });
                        // check editable
                        service.getReportFormAnimalEggById(id).then(function(res) {
                            var report = res;
                            var dateReport;
                            if (typeof report.dateReport === "undefined") {
                                dateReport = new Date();
                            } else {
                                dateReport = new Date(report.dateReport);
                            }
                            var dateReportMonth = dateReport.getMonth() + 1;
                            var dateReportYear = dateReport.getFullYear();
                            service.getAdministrativeUnitDtoByLoginUser().then(function(data) {
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
                                    service.deleteReportFormAnimalEgg(id).then(function(res1) {
                                        vm.getPageReportFormAnimalEgg();
                                        if (res1) {
                                            toastr.info("Xóa bản ghi thành công", "Thông báo");
                                        } else {
                                            toastr.error("Có lỗi xảy ra, phải xóa dữ liệu vật nuôi để tiếp tục xóa bản ghi này", "Thông báo");
                                        }

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
                                                    //Check role and delete
                                                    service.deleteReportFormAnimalEgg(id).then(function(res) {
                                                        vm.getPageReportFormAnimalEgg();
                                                        if (res) {
                                                            toastr.info("Xóa bản ghi thành công", "Thông báo");
                                                        } else {
                                                            toastr.error("Có lỗi xảy ra, phải xóa dữ liệu vật nuôi để tiếp tục xóa bản ghi này", "Thông báo");
                                                        }

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
                }, function() {
                    // cancel
                });
            }
            //check số con non cộng dồn theo thời gian
        vm.isQuantityChildEgg = false;
        vm.quantityChildIncrement = function(item) {
            var dto = {};
            if (item.id != null) {
                dto.id = item.id;
            } else {
                dto.id = null;
            }
            dto.animalId = item.animal.id;
            dto.dateReport = item.dateReport;
            dto.farmId = vm.farm.id;
            service.quantityChildIncrement(dto).then(function(res) {
                if (res && (res != "" || res != 0)) {
                    if (vm.reportFormAnimalEgg) {
                        vm.reportFormAnimalEgg.quantityChildIncrement = res;
                    }
                    vm.isQuantityChildEgg = true;
                } else {
                    if (vm.reportFormAnimalEgg) {
                        vm.reportFormAnimalEgg.quantityChildIncrement = "";
                    }
                    vm.isQuantityChildEgg = false;
                }

            });
        }
        vm.quantityChildIncrementForList = function(item) {
            item.isQuantityChildEgg = false;
            var dto = {};
            if (item.id != null) {
                dto.id = item.id;
            } else {
                dto.id = null;
            }
            dto.animalId = item.animal.id;
            dto.dateReport = item.dateReport;
            dto.farmId = vm.farm.id;
            service.quantityChildIncrement(dto).then(function(res) {
                if (res != null && res != "") {
                    item.quantityChildIncrement = res;
                    item.isQuantityChildEgg = true;
                } else {
                    item.isQuantityChildEgg = false;
                }
            });
        }

        vm.getOneTemplate16aBysearchDto = function(item, type) {

            if (type == "16C") {
                if (vm.reportFormAnimalEgg) {
                    vm.reportFormAnimalEgg.parentMale = 0;
                    vm.reportFormAnimalEgg.parentFemale = 0;
                }
            } else if (type == "16D") {
                if (vm.reportFormAnimalGiveBirth) {
                    vm.reportFormAnimalGiveBirth.parentMale = 0;
                    vm.reportFormAnimalGiveBirth.parentFemale = 0;
                }
            }

            if (!item || !item.dateReport || !item.animal || !vm.farm) {
                return;
            }
            var search16aDto = {};
            var form16Year = item.dateReport.getFullYear();
            var form16Month = item.dateReport.getMonth() + 1;
            var form16Date = item.dateReport.getDate();

            search16aDto.animalId = item.animal.id;
            search16aDto.farmId = vm.farm.id;
            search16aDto.year = form16Year;
            search16aDto.month = form16Month;
            search16aDto.date = form16Date;
            service.getOneTemplate16BysearchDto(search16aDto).then(function(res) {
                if (res != null && res != "" && res.length > 0) {
                    var rp16a = res;
                    console.log(rp16a);
                    if (rp16a && rp16a.length > 0) {
                        rp16a.forEach(rpItem => {
                            if (rpItem.animal) {
                                if (type == "16C" && vm.reportFormAnimalEgg && vm.reportFormAnimalEgg.animal &&
                                    rpItem.animal.id == vm.reportFormAnimalEgg.animal.id) {
                                    vm.reportFormAnimalEgg.parentMale = rpItem.maleParent;
                                    vm.reportFormAnimalEgg.parentFemale = rpItem.femaleParent;
                                } else if (type == "16D" && rpItem.animal && vm.reportFormAnimalGiveBirth && vm.reportFormAnimalGiveBirth.animal &&
                                    rpItem.animal.id == vm.reportFormAnimalGiveBirth.animal.id) {
                                    vm.reportFormAnimalGiveBirth.parentMale = rpItem.maleParent;
                                    vm.reportFormAnimalGiveBirth.parentFemale = rpItem.femaleParent;
                                }
                            } else {
                                toastr.warning("Không có bố mẹ trong trại", "Cảnh báo");
                            }

                        });
                    }
                } else {

                }
            });
        }

        vm.onSelectedQuantityChildIncrementEgg = function(item) {
            if (item != null && item.animal != null && item.dateReport != null && vm.farm) {
                //debugger
                var report = item;
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
                        if (dateReport > today) {
                            vm.reportFormAnimalEgg.parentMale = 0;
                            vm.reportFormAnimalEgg.parentFemale = 0;
                            vm.reportFormAnimalEgg.quantityChildIncrement = "";
                            toastr.warning("Ngày khai sinh không được lớn hơn ngày hiện tại", "Cảnh báo");
                            return;
                        }
                        // alo
                        for (var i of vm.reportFormAnimalEggs) {
                            var dateReportInForm = new Date(dateReport).toLocaleDateString();
                            var dateReportInTable = new Date(i.dateReport).toLocaleDateString();

                            var d1 = Date.parse(dateReportInForm);
                            var d2 = Date.parse(dateReportInTable);
                            if (d1 == d2 && vm.reportFormAnimalEgg.isNew == true && (vm.reportFormAnimalEgg.animal != null ? vm.reportFormAnimalEgg.animal.id : null) == i.animal.id) {
                                vm.reportFormAnimalEgg.parentMale = 0;
                                vm.reportFormAnimalEgg.parentFemale = 0;
                                vm.reportFormAnimalEgg.quantityChildIncrement = "";
                                toastr.warning("Ngày khai sinh của loài này đã tồn tại, vui lòng chọn sửa", "Cảnh báo");
                                return;
                            }
                        }
                        vm.quantityChildIncrement(item);
                        vm.getOneTemplate16aBysearchDto(item, "16C");
                    } else {
                        if (vm.adminstrativeUnits.length == 0) {
                            vm.reportFormAnimalEgg.parentMale = 0;
                            vm.reportFormAnimalEgg.parentFemale = 0;
                            vm.reportFormAnimalEgg.quantityChildIncrement = "";
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
                                        if (dateReport > today) {
                                            vm.reportFormAnimalEgg.parentMale = 0;
                                            vm.reportFormAnimalEgg.parentFemale = 0;
                                            vm.reportFormAnimalEgg.quantityChildIncrement = "";
                                            toastr.warning("Ngày khai sinh không được lớn hơn ngày hiện tại", "Cảnh báo");
                                            return;
                                        }
                                        // alo
                                        for (var i of vm.reportFormAnimalEggs) {
                                            var dateReportInForm = new Date(dateReport).toLocaleDateString();
                                            var dateReportInTable = new Date(i.dateReport).toLocaleDateString();

                                            var d1 = Date.parse(dateReportInForm);
                                            var d2 = Date.parse(dateReportInTable);
                                            if (d1 == d2 && vm.reportFormAnimalEgg.isNew == true && (vm.reportFormAnimalEgg.animal != null ? vm.reportFormAnimalEgg.animal.id : null) == i.animal.id) {
                                                vm.reportFormAnimalEgg.parentMale = 0;
                                                vm.reportFormAnimalEgg.parentFemale = 0;
                                                vm.reportFormAnimalEgg.quantityChildIncrement = "";
                                                toastr.warning("Ngày khai sinh của loài này đã tồn tại, vui lòng chọn sửa", "Cảnh báo");
                                                return;
                                            }
                                        }
                                        vm.quantityChildIncrement(item);
                                        vm.getOneTemplate16aBysearchDto(item, "16C");
                                    } else {
                                        vm.reportFormAnimalEgg.parentMale = 0;
                                        vm.reportFormAnimalEgg.parentFemale = 0;
                                        vm.reportFormAnimalEgg.quantityChildIncrement = "";
                                        toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                                        return;
                                    }
                                } else {
                                    vm.reportFormAnimalEgg.parentMale = 0;
                                    vm.reportFormAnimalEgg.parentFemale = 0;
                                    vm.reportFormAnimalEgg.quantityChildIncrement = "";
                                    toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                                    return;
                                }
                            });
                        }
                    }

                });
            }
        }
        vm.onSelectedQuantityChildIncrementEggForList = function(item) {
            if (item != null && item.animal != null && item.dateReport != null) {
                vm.quantityChildIncrementForList(item);
            }
        }

        vm.reportFormAnimalGiveBirth = {};
        vm.modalPopupFormAnimalGiveBirth = null;
        vm.reportFormAnimalGiveBirths = [];
        vm.reportFormAnimalGiveBirthSearchDto = {};
        vm.reportFormAnimalGiveBirthPageIndex = 1;
        vm.reportFormAnimalGiveBirthPageSize = 10;
        vm.reportFormAnimalGiveBirthTotalItems = 0;
        vm.reportFormAnimalGiveBirthPeriod = {};

        vm.removeToListAddEditFormAnimalGiveBirth = function(index) {
            vm.listAnimalGiveBirth.splice(index, 1);
        }
        vm.addToListAddEditFormAnimalGiveBirth = function() {

            if (vm.reportFormAnimalGiveBirth && !vm.reportFormAnimalGiveBirth.animal) {
                toastr.warning("Chưa chọn loài nuôi", "Cảnh báo");
                return;
            }
            if (!vm.reportFormAnimalGiveBirth.parentMale || vm.reportFormAnimalGiveBirth.parentMale <= 0) {
                toastr.warning("Số đàn bố mẹ (đực) " + vm.reportFormAnimalGiveBirth.animal.name + " không được để trống. Vui lòng thêm dữ liệu tại 'Biểu mẫu số 16'.", "Cảnh báo");
                return;
            }
            if (!vm.reportFormAnimalGiveBirth.parentFemale || vm.reportFormAnimalGiveBirth.parentFemale <= 0) {
                toastr.warning("Số đàn bố mẹ (cái) " + vm.reportFormAnimalGiveBirth.animal.name + " không được để trống. Vui lòng thêm dữ liệu tại 'Biểu mẫu số 16'.", "Cảnh báo");
                return;
            }
            if (vm.reportFormAnimalGiveBirth && vm.reportFormAnimalGiveBirth.animal && (vm.reportFormAnimalGiveBirth.quantityChildIncrement == "" || !vm.reportFormAnimalGiveBirth.quantityChildIncrement)) {
                toastr.warning("Số con con cộng dồn theo thời gian " + vm.reportFormAnimalGiveBirth.animal.name + " không được để trống", "Cảnh báo");
                return;
            }
            if (vm.reportFormAnimalGiveBirth && vm.reportFormAnimalGiveBirth.animal && (vm.reportFormAnimalGiveBirth.quantityChildIncrement != "" && vm.reportFormAnimalGiveBirth.quantityChildIncrement <= 0)) {
                toastr.warning("Số con con cộng dồn theo thời gian " + vm.reportFormAnimalGiveBirth.animal.name + " không được nhỏ hơn 0", "Cảnh báo");
                return;
            }
            // if (vm.reportFormAnimalGiveBirth) {
            //     vm.reportFormAnimalGiveBirth.quantityChildLive = vm.reportFormAnimalGiveBirth.quantityChildHatch - vm.reportFormAnimalGiveBirth.quantityChildDie;
            // }
            // if (vm.reportFormAnimalGiveBirth) {
            //     vm.reportFormAnimalGiveBirth.remainQuantity = vm.reportFormAnimalGiveBirth.quantityChildIncrement - vm.reportFormAnimalGiveBirth.quantityChildSeparateCaptivity;
            // }
            // if (!vm.reportFormAnimalGiveBirthPeriod) {
            //     vm.reportFormAnimalGiveBirthPeriod = {};
            // }
            // if (!vm.reportFormAnimalGiveBirthPeriod.reportFormAnimalGiveBirths) {
            //     vm.reportFormAnimalGiveBirthPeriod.reportFormAnimalGiveBirths = [];
            // }

            if (vm.reportFormAnimalGiveBirth && vm.reportFormAnimalGiveBirth.animal && vm.reportFormAnimalGiveBirth.dateReport) {
                var example = angular.copy(vm.reportFormAnimalGiveBirth);
                example.isAddRow = true;
                vm.listAnimalGiveBirth.push(example);
                vm.reportFormAnimalGiveBirth = { isNew: true, dateReport: new Date() };
            } else {
                toastr.warning("Phải chọn loài nuôi", "Thông báo");
            }
        }

        vm.addReportFormAnimalGiveBirth = function(id) {
            vm.getListAnimal();
            vm.listAnimalGiveBirth = [];
            vm.searchDto16D = {};
            if (id) {
                // service.reportFormAnimalGiveBirthPeriodGetById(id).then(function(res){
                service.getReportFormAnimalGiveBirthById(id).then(function(res) {
                    vm.reportFormAnimalGiveBirth = res;
                    vm.reportFormAnimalGiveBirth.isNew = false;
                    vm.reportFormAnimalGiveBirth.hasEditQuantityChildIncrement = false;

                    // vm.reportFormAnimalGiveBirthPeriod = res;
                    // vm.reportFormAnimalGiveBirthPeriod.isNew = false;
                    // vm.onSelectMonthForm16(vm.reportFormAnimalGiveBirthPeriod);
                    //debugger
                    var report = vm.reportFormAnimalGiveBirth;
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
                            vm.modalPopupFormAnimalGiveBirth = modal.open({
                                animation: true,
                                templateUrl: 'report_form_animal_give_birth_table.html',
                                scope: $scope,
                                windowClass: "customer-modal-lg",
                                backdrop: 'static',
                                keyboard: false,
                                size: 'lg'
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
                                            vm.modalPopupFormAnimalGiveBirth = modal.open({
                                                animation: true,
                                                templateUrl: 'report_form_animal_give_birth_table.html',
                                                scope: $scope,
                                                windowClass: "customer-modal-lg",
                                                backdrop: 'static',
                                                keyboard: false,
                                                size: 'lg'
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
            } else {
                vm.reportFormAnimalGiveBirth = {};
                vm.reportFormAnimalGiveBirth.isNew = true;
                vm.reportFormAnimalGiveBirth.dateReport = new Date();
                vm.reportFormAnimalGiveBirth.hasEditQuantityChildIncrement = false;
                // vm.reportFormAnimalGiveBirth.farm = vm.farm;
                // vm.reportFormAnimalGiveBirthPeriod = {};
                // vm.reportFormAnimalGiveBirthPeriod.isNew = true;
                // vm.reportFormAnimalGiveBirthPeriod.farm = vm.farm;

                // vm.reportFormAnimalGiveBirthPeriod.year = new Date().getFullYear();
                // vm.reportFormAnimalGiveBirthPeriod.month = new Date().getMonth()+1;
                // vm.reportFormAnimalGiveBirthPeriod.date = new Date().getDate();
                // vm.onSelectMonthForm16(vm.reportFormAnimalGiveBirthPeriod);
                //debugger
                var report = vm.reportFormAnimalGiveBirth;
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
                        vm.modalPopupFormAnimalGiveBirth = modal.open({
                            animation: true,
                            templateUrl: 'report_form_animal_give_birth_table.html',
                            scope: $scope,
                            windowClass: "customer-modal-lg",
                            backdrop: 'static',
                            keyboard: false,
                            size: 'lg'
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
                                        vm.modalPopupFormAnimalGiveBirth = modal.open({
                                            animation: true,
                                            templateUrl: 'report_form_animal_give_birth_table.html',
                                            scope: $scope,
                                            windowClass: "customer-modal-lg",
                                            backdrop: 'static',
                                            keyboard: false,
                                            size: 'lg'
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
            }

        }

        function getPageReportFormAnimalGiveBirth() {
            if (vm.farm && vm.farm.id) {
                vm.reportFormAnimalGiveBirthSearchDto.farmId = vm.farm.id;
                // service.reportFormAnimalGiveBirthPeriodGetByPage(vm.reportFormAnimalGiveBirthSearchDto, vm.reportFormAnimalGiveBirthPageIndex, vm.reportFormAnimalGiveBirthPageSize).then(function(res){
                service.getPageReportFormAnimalGiveBirth(vm.reportFormAnimalGiveBirthSearchDto, vm.reportFormAnimalGiveBirthPageIndex, vm.reportFormAnimalGiveBirthPageSize).then(function(res) {
                    vm.reportFormAnimalGiveBirths = res.content;
                    vm.reportFormAnimalGiveBirthTotalItems = res.totalElements;
                }).catch(function(err) {
                    vm.reportFormAnimalGiveBirths = [];
                });
            }
        }
        vm.getPageReportFormAnimalGiveBirth();
        $scope.pageFormImportAnimalEggChange = function() {
            vm.getPageReportFormAnimalGiveBirth();
        }
        vm.saveReportFormAnimalGiveBirth = function() {
            if (vm.listAnimalGiveBirth && vm.listAnimalGiveBirth.length > 0) {
                vm.listAnimalGiveBirth.forEach(item => {
                    if (item.animal) {
                        return true;
                    }
                    if (!item.animal || item.animal == null) {
                        toastr.warning("Chưa chọn loài nuôi.", "Cảnh báo");
                        return false;
                    }
                    if (item.animal && (item.quantityChildIncrement == "" || item.quantityChildIncrement < 0)) {
                        toastr.warning("Số con con " + item.animal.name + " cộng dồn theo thời gian không được để trống", "Cảnh báo");
                        return;
                    }
                    if (!item.parentMale || item.parentMale <= 0) {
                        toastr.warning("Số đàn bố mẹ (đực) " + item.animal.name + " không được để trống. Vui lòng thêm dữ liệu tại 'Biểu mẫu số 16'.", "Cảnh báo");
                        return;
                    }
                    if (!item.parentFemale || item.parentFemale <= 0) {
                        toastr.warning("Số đàn bố mẹ (cái) " + item.animal.name + " không được để trống. Vui lòng thêm dữ liệu tại 'Biểu mẫu số 16'.", "Cảnh báo");
                        return;
                    }
                });
            }
            if (vm.listAnimalGiveBirth && vm.listAnimalGiveBirth.length == 0) {
                if (vm.reportFormAnimalGiveBirth && !vm.reportFormAnimalGiveBirth.animal) {
                    toastr.warning("Chưa chọn loài nuôi", "Cảnh báo");
                    return;
                }
            }
            // if (vm.reportFormAnimalGiveBirth.animal && (!vm.reportFormAnimalGiveBirth.parentMale || vm.reportFormAnimalGiveBirth.parentMale <= 0)) {
            //     toastr.warning("Số đàn bố mẹ (đực) " + vm.reportFormAnimalGiveBirth.animal.name + " không được để trống. Vui lòng thêm dữ liệu tại 'Biểu mẫu số 16'.", "Cảnh báo");
            //     return;
            // }
            if (vm.reportFormAnimalGiveBirth.animal && (!vm.reportFormAnimalGiveBirth.parentFemale || vm.reportFormAnimalGiveBirth.parentFemale <= 0)) {
                toastr.warning("Số con cái " + vm.reportFormAnimalGiveBirth.animal.name + " trong trại không có !", "Cảnh báo");
                return;
            }
            if (vm.reportFormAnimalGiveBirth && vm.reportFormAnimalGiveBirth.animal && (vm.reportFormAnimalGiveBirth.quantityChildIncrement == "" || !vm.reportFormAnimalGiveBirth.quantityChildIncrement)) {
                toastr.warning("Số con con " + vm.reportFormAnimalGiveBirth.animal.name + " cộng dồn theo thời gian không được để trống", "Cảnh báo");
                return;
            }
            if (vm.reportFormAnimalGiveBirth && vm.reportFormAnimalGiveBirth.animal && (vm.reportFormAnimalGiveBirth.quantityChildIncrement != "" && vm.reportFormAnimalGiveBirth.quantityChildIncrement <= 0)) {
                toastr.warning("Số con con " + vm.reportFormAnimalGiveBirth.animal.name + " cộng dồn theo thời gian không được nhỏ hơn 0", "Cảnh báo");
                return;
            }
            if (vm.reportFormAnimalGiveBirth.animal && vm.reportFormAnimalGiveBirth) {
                if (!vm.listAnimalGiveBirth) {
                    vm.listAnimalGiveBirth = [];
                }
                vm.listAnimalGiveBirth.push(vm.reportFormAnimalGiveBirth);
            }
            vm.listAnimalGiveBirth.forEach(function(item) {
                item.farm = vm.farm;
            });
            service.saveReportFormAnimalGiveBirthList(vm.listAnimalGiveBirth).then(function(data) {
                vm.modalPopupFormAnimalGiveBirth.close();
                vm.searchByPage();
                getPageAnimalReportDataByFarmId();
                vm.getPageReportFormAnimalGiveBirth();
                toastr.info("Lưu bản ghi thành công", "Thông báo");
            }).catch(function(err) {
                toastr.warning("Lưu bản ghi không thành công", "Cảnh báo");
            });
        }
        vm.modalConfirmDelete = null;
        vm.confirmDeleteReportFormAnimalGiveBirth = function(id) {
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_raw_data.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDelete.result.then(function(confirm) {
                if (confirm == 'yes') {

                    // check editable
                    //debugger
                    service.getReportFormAnimalGiveBirthById(id).then(function(res) {
                        //debugger
                        var report = res;
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
                                service.deletePageReportFormAnimalGiveBirthById(id).then(function(r1) {
                                    if (r1) {
                                        vm.getPageReportFormAnimalGiveBirth();
                                        toastr.info("Xóa bản ghi thành công", "Thông báo");
                                        vm.search();
                                    } else {
                                        toastr.error("Có lỗi xảy ra, phải xóa dữ liệu vật nuôi để tiếp tục xóa bản ghi này", "Cảnh báo");
                                        vm.search();
                                    }
                                });
                            } else {
                                //debugger
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
                                                //Check role and delete
                                                service.deletePageReportFormAnimalGiveBirthById(id).then(function(r) {
                                                    if (r) {
                                                        vm.getPageReportFormAnimalGiveBirth();
                                                        toastr.info("Xóa bản ghi thành công", "Thông báo");
                                                        vm.search();
                                                    } else {
                                                        toastr.error("Có lỗi xảy ra, phải xóa dữ liệu vật nuôi để tiếp tục xóa bản ghi này", "Cảnh báo");
                                                        vm.search();
                                                    }
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
                    // service.reportFormAnimalGiveBirthPeriodDeleteById(id).then(function(){
                }
            }, function() {
                // cancel
            });
        }



        /** ForestProductList --------------------------- */



        /*animalService.getAll().then(function (data) {
            vm.animals = data;
        }).catch(function (error) {
            vm.animals = [];
        });*/

        vm.forestProduct = {};
        vm.modalPopupAddEditForestProductList = null;
        vm.modalConfirmDeleteForestProductList = null;

        vm.addForestProductList = function(id) {
            vm.allOriginal = [];
            vm.adminstrativeUnitsCity = [];
            service.getAdminstrativeUnitsCity().then(function(data) {
                vm.adminstrativeUnitsCity = data;
            });
            service.getAllOriginal().then(function(data) {
                vm.allOriginal = data;
            });

            // service.getAllDistrict(2).then(function (data) {
            //     vm.adminstrativeUnitsCity = data;
            // });
            if (id) {
                service.getForestProductListById(id).then(function(data) {
                    vm.forestProduct = data;
                    vm.forestProduct.isNew = false;
                    getDataByComboboxFrom(vm.forestProduct.administrativeUnitFrom)
                    getDataByComboboxTo(vm.forestProduct.administrativeUnitTo)
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
                vm.selectedCityFrom = null;
                vm.selectedCityTo = null;
                vm.forestProduct = {};
                vm.forestProduct.farm = vm.farm;
                vm.forestProduct.isNew = true;
                vm.forestProduct.dateIssue = new Date();
                vm.forestProduct.exports = [];
                vm.listForesProductDetail = [];
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
        vm.confirmDeleteForestProductList = function(id) {
            vm.modalConfirmDeleteForestProductList = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDeleteForestProductList.result.then(function(confirm) {
                if (confirm == 'yes') {
                    service.deletePageForestProductList(id).then(function() {
                        toastr.info("Xóa bản ghi thành công", "Thông báo");
                        vm.getPageForestProductList();
                    });
                }
            });
        }

        vm.saveForestProductList = function() {
            if (!vm.forestProduct.farm) {
                toastr.warning("Chưa chọn cơ sở nuôi", "Cảnh báo");
                return;
            } else if (!vm.forestProduct.code) {
                toastr.warning("Chưa nhập Số hiệu bản kê", "Cảnh báo");
                return;
            } else {
                vm.forestProduct.details = vm.listForesProductDetail;
                service.saveForestProductList(vm.forestProduct).then(function(data) {
                    vm.modalPopupAddEditForestProductList.close();
                    vm.getPageForestProductList();
                });
            }
        }

        function getPageForestProductList() {
            if (vm.farm && vm.farm.id) {
                vm.searchDtoForestProductList = {};
                vm.searchDtoForestProductList.farmId = vm.farm.id;
                vm.searchDtoForestProductList.fromDate = vm.fromDateForestProductList ? vm.fromDateForestProductList : null;
                vm.searchDtoForestProductList.toDate = vm.toDateForestProductList ? vm.toDateForestProductList : null;
                vm.searchDtoForestProductList.nameOrCode = vm.nameOrCodeForestProductList ? vm.nameOrCodeForestProductList : null;
                service.getPageForestProductList(vm.searchDtoForestProductList, vm.pageIndexForestProductList, vm.pageSizeForestProductList).then(function(data) {
                    vm.forestProductList = data.content;
                    vm.bsTableControlForestProductList.options.data = vm.forestProductList;
                    vm.bsTableControlForestProductList.options.totalRows = data.totalElements;
                });
            }
        }

        vm.modalPopupAddEditForestDetail = null;
        vm.addEditForestDetailIndex = -1;
        vm.forestProductDetail = {};
        vm.forestProductList = [];
        vm.listForesProductDetail = [];

        const counts = {};
        vm.addForestProductDetail = function(detail) {
            vm.forestProductDetail = [];
            if (!vm.forestProduct.farm) {
                toastr.warning("Vui lòng chọn cơ sở nuôi ", "Thông báo");
                return;
            }
            counts.farmId = vm.forestProduct.farm.id;
            service.getListAnimalFromReportForm16BySearch(counts).then(function(data) {
                vm.animals2 = data;
            }).catch(function(error) {
                vm.animals2 = [];
            });
            if (detail) {
                vm.forestProductDetail = angular.copy(detail);
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
        }

        vm.saveForestProductListDetail = function() {
            if (!vm.forestProductDetail.animal) {
                toastr.warning("Chưa chọn loài", "Cảnh báo");
                return;
            }
            if (vm.forestProductDetail.isNew) {
                vm.listForesProductDetail.push(vm.forestProductDetail);
                vm.modalPopupAddEditForestDetail.close();
            } else if (!vm.forestProductDetail.isNew) {
                for (let i = 0; i < vm.listForesProductDetail.length; i++) {
                    if (vm.listForesProductDetail[i].animal.id == vm.forestProductDetail.animal.id)
                        vm.listForesProductDetail[i] = vm.forestProductDetail;
                }
                vm.modalPopupAddEditForestDetail.close();
            }
        }
        vm.modalConfirmDeleteInjectionPlants = null;
        vm.confirmDeleteForestDetail = function(index) {
            vm.modalConfirmDeleteInjectionPlants = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_forest_detail.html',
                scope: $scope,
                // backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDeleteInjectionPlants.result.then(function(confirm) {
                if (confirm == 'yes') {
                    vm.listForesProductDetail.splice(index, 1);
                }
            })
        }


        //--------------Popup Batch Code----------//
        vm.pageIndexBatchCodeForestProductList = 1, vm.pageSizeBatchCodeForestProductList = 10;
        vm.subModalInstanceForestProductList = {};

        vm.selectedBatchCodesForestProductList = [];
        vm.batchCodeSelectedForestProductList = {};
        vm.listExportAnimalSelectedForestProductList = [];
        vm.showPopupBatchCodeForestProductList = function() {

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
            vm.listExportAnimalSelectedForestProductList = [];
            vm.selectedBatchCodesForestProductList = [];
            vm.batchCodeSelectedForestProductList = {};
            vm.findByForestProductList();

            //getListTeacher(vm.pageIndexTeacher,vm.pageSizeTeacher);
            vm.subModalInstance.result.then(function(confirm) {
                if (confirm == 'yes') {

                }
            }, function() {
                vm.batchCode = null;
            });
        }

        var closeModal = function() {

            vm.subModalInstance.close();

        };

        function checkAgreeForestProductList() {
            if (!vm.listExportAnimalSelected || vm.listExportAnimalSelected.length == 0) {
                toastr.warning($translate.instant("exportAnimal.notChooseBatchCode"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.agreeForestProductList = function() {
            if (checkAgreeForestProductList()) {
                let listId = [];
                if (vm.forestProduct.exports && vm.forestProduct.exports.length > 0) {
                    listId = vm.forestProduct.exports.map(item => item.id);
                }
                vm.listExportAnimalSelected.forEach(function(item) {
                    if (listId.indexOf(item.id) == -1) {
                        vm.forestProduct.exports.push(item);
                    }
                });
                vm.listExportAnimalSelected = [];
                closeModal();
            }
        }

        vm.bsTableControlCodeForestProductList = {
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
                pageSize: vm.pageSizeBatchCodeForestProductList,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionCodeForestProductList(),

                onCheck: function(row, $element) {
                    $scope.$apply(function() {
                        vm.listExportAnimalSelectedForestProductList.push(row);
                    });
                },
                onCheckAll: function(rows) {
                    $scope.$apply(function() {
                        angular.forEach(rows, function(row) {
                            vm.listExportAnimalSelectedForestProductList.push(row);
                        });
                        // vm.listExportAnimalSelected = rows;
                    });
                },
                onUncheck: function(row, $element) {
                    var index = utils.indexOf(row, vm.listExportAnimalSelectedForestProductList);
                    if (index >= 0) {
                        $scope.$apply(function() {
                            vm.listExportAnimalSelectedForestProductList.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function(rows) {
                    $scope.$apply(function() {
                        vm.listExportAnimalSelectedForestProductList = [];
                    });
                },

                onPageChange: function(index, pageSize) {
                    vm.pageSizeBatchCodeForestProductList = pageSize;
                    vm.pageIndexBatchCodeForestProductList = index;
                    vm.findByForestProductList();
                }
            }
        };



        vm.enterSearchCodeForestProductList = function() {
            if (event.keyCode == 13) { //Phím Enter

                vm.searchByCodeForestProductList();
            }
        };
        vm.searchByCodeForestProductList = function() {
            vm.pageIndexBatchCode = 1;
            vm.bsTableControlCode.state.pageNumber = 1;
            vm.findByForestProductList();
        };
        vm.findByForestProductList = function() {
            if (vm.searchDtoForestProductList == null) {
                vm.searchDtoForestProductList = {};
            }
            if (vm.batchCodeForestProductList != null && vm.batchCodeForestProductList != "") {
                vm.searchDto.nameOrCode = vm.batchCode;
            } else {
                vm.searchDto.nameOrCode = null;
            }
            if (vm.forestProduct != null && vm.forestProduct.farm != null && vm.forestProduct.farm.id != null) {
                vm.searchDto.farmId = vm.forestProduct.farm.id;
            } else
                vm.searchDto.farmId = null;
            vm.searchDto.type = -1;
            service.getPageSearchImportAnimal(vm.searchDto, vm.pageIndexBatchCodeForestProductList, vm.pageSizeBatchCodeForestProductList).then(function(data) {
                vm.importAnimals = data.content;
                vm.bsTableControlCode.options.data = vm.importAnimals;
                vm.bsTableControlCode.options.totalRows = data.totalElements;
            });
        }
        vm.addReportForm16A = function() {
            //debugger
            vm.listReportForm16A = [];
            vm.reportForm16A = {};
            vm.reportPeriod = {};
            vm.reportPeriod.isNew = true;
            const date = new Date();
            vm.form16Year = new Date().getFullYear();
            vm.form16Month = new Date().getMonth() + 1;
            vm.form16Date = new Date().getDate();
            if (vm.form16Year && vm.form16Month && vm.form16Date) {
                vm.reportForm16A.dateReport = new Date(vm.form16Year, vm.form16Month - 1, vm.form16Date);
            } else {
                vm.reportForm16A.dateReport = new Date();
            }
            vm.allOriginal = [];
            service.getAllOriginal().then(function(data) {
                vm.allOriginal = data;
            });

            vm.modalFormPopup16A = modal.open({
                animation: true,
                templateUrl: 'report_form_16A.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                windowClass: "customer-modal-lg",
                size: 'lg'
            });
        }
        vm.form16Year = vm.currentYear;
        vm.form16Month = vm.currentMonth;
        vm.form16Date = new Date().getDate();
        vm.onSelectMonthForm16 = function() {
            vm.days = [];
            const lastDayOfMonth = moment(new Date(vm.form16Year, vm.form16Month - 1, 1)).endOf('month').toDate().getDate();
            for (let dayIndex = 1; dayIndex <= lastDayOfMonth + 1; dayIndex++) {
                if (vm.days.length <= 30) {
                    vm.days.push({ value: dayIndex, name: dayIndex + "" });
                }
            }
            console.log(vm.days);
        }
        vm.onSelectDateForm16 = function() {
            vm.searchByPage();
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
            vm.searchByPage();
        }
        vm.onSelectYear = function() {
                vm.days = [];
                vm.date = null;
                const lastDayOfMonth = moment(new Date(vm.year, vm.month - 1, 1)).endOf('month').toDate().getDate();
                for (let dayIndex = 1; dayIndex <= lastDayOfMonth; dayIndex++) {
                    vm.days.push({ value: dayIndex, name: dayIndex + "" });
                }
                vm.searchByPage();
            }
            // vm.onSelectMonth = function (item) {
            //     if (item) {
            //         vm.days = [];
            //         vm.date = null;
            //         const lastDayOfMonth = moment(new Date(vm.year, vm.month - 1, 1)).endOf('month').toDate().getDate();
            //         for (let dayIndex = 1; dayIndex <= lastDayOfMonth; dayIndex++) {
            //             vm.days.push({ value: dayIndex, name: dayIndex + "" });
            //         }
            //     }
            //     vm.searchByPage();
            // }
            // vm.onSelectYear = function () {
            //     vm.days = [];
            //     vm.date = null;
            //     const lastDayOfMonth = moment(new Date(vm.year, vm.month - 1, 1)).endOf('month').toDate().getDate();
            //     for (let dayIndex = 1; dayIndex <= lastDayOfMonth; dayIndex++) {
            //         vm.days.push({ value: dayIndex, name: dayIndex + "" });
            //     }
            //     vm.searchByPage();
            // }
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
        vm.updateTotalForm16 = function(obj) {
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

        vm.addForm16ToList = function() {
            // if (vm.reportForm16A.animal && !vm.reportForm16A.total) {
            //     toastr.warning('Số lượng cá thể ' + vm.reportForm16A.animal.name + ' khai báo không được để trống', 'Cảnh báo');
            //     return;
            // }
            // if (vm.reportForm16A.animal && vm.reportForm16A.total && vm.reportForm16A.total < 0) {
            //     toastr.warning('Số lượng cá thể ' + vm.reportForm16A.animal.name + ' khai báo không được nhỏ hơn 0', 'Cảnh báo');
            //     return;
            // }
            if (vm.reportForm16A.original) {
                const item = angular.copy(vm.reportForm16A);
                vm.listReportForm16A.push(item);
                vm.reportForm16A = {};

            }
            if (vm.reportForm16A.animal) {
                const item = angular.copy(vm.reportForm16A);
                vm.listReportForm16A.push(item);
                vm.reportForm16A = {};
                if (vm.form16Year && vm.form16Month && vm.form16Date) {
                    vm.reportForm16A.dateReport = new Date(vm.form16Year, vm.form16Month - 1, vm.form16Date);
                } else {
                    vm.reportForm16A.dateReport = new Date();
                }
            } else {
                toastr.warning('Chưa chọn loài', 'Cảnh báo');
            }
        }
        vm.sizes = [5, 15, 25, 50, 100];
        vm.removeForm16ToList = function(index) {
            vm.listReportForm16A.splice(index, 1);
        }


        vm.saveReportPeriod = function() {
            if (!vm.form16Year) {
                toastr.warning('Chưa chọn năm của kỳ báo cáo', 'Cảnh báo');
                return;
            }
            if (!vm.form16Month) {
                toastr.warning('Chưa chọn tháng của kỳ báo cáo', 'Cảnh báo');
                return;
            }
            if (!vm.reportForm16A.dateReport) {
                toastr.warning('Chưa chọn ngày', 'Cảnh báo');
                return;
            }

            var currentTime = new Date();
            var reportTime = new Date(vm.form16Year, vm.form16Month - 1, vm.form16Date, null, null, null, null);

            var d1 = Date.parse(currentTime);
            var d2 = Date.parse(reportTime);
            if (d2 > d1) {
                toastr.warning('Ngày khai báo không được lớn hơn ngày hiện tại', 'Cảnh báo');
                return;
            }
            if (vm.listReportForm16A && vm.listReportForm16A.length == 0) {
                if (!vm.reportForm16A.animal) {
                    toastr.warning('Chưa chọn loài nuôi', 'Cảnh báo');
                    return;
                }
            }
            if (vm.listReportForm16A && vm.listReportForm16A.length > 0) {
                vm.listReportForm16A.forEach(item => {
                    if (item.animal) {
                        return true;
                    }
                    // if (item.animal && item.total && item.total < 0) {
                    //     toastr.warning('Số lượng cá thể ' + item.animal.name + ' khai báo không được bằng 0', 'Cảnh báo');
                    //     return;
                    // }
                    // if (item.animal && !item.total) {
                    //     toastr.warning('Số lượng cá thể ' + item.animal.name + ' khai báo không được để trống', 'Cảnh báo');
                    //     return;
                    // }
                });
            }
            // if (vm.reportForm16A.animal && vm.reportForm16A.total && (vm.reportForm16A.total < 0)) {
            //     toastr.warning('Số lượng cá thể ' + vm.reportForm16A.animal.name + ' khai báo không được bằng 0', 'Cảnh báo');
            //     return;
            // }

            // if (vm.reportForm16A.animal && !vm.reportForm16A.total) {
            //     toastr.warning('Số lượng cá thể ' + vm.reportForm16A.animal.name + ' khai báo không được để trống', 'Cảnh báo');
            //     return;
            // }

            if (!vm.form16Date) {
                toastr.warning('Chưa chọn ngày của kỳ báo cáo', 'Cảnh báo');
            } else if (vm.farm) {
                vm.reportPeriod.year = vm.form16Year;
                vm.reportPeriod.month = vm.form16Month;
                vm.reportPeriod.date = vm.form16Date;
                vm.reportPeriod.farm = vm.farm;
                vm.reportPeriod.type = 1;
                vm.reportPeriod.farmId = vm.farm.id;
                reportPeriodService.checkDuplicateYearMonthDate(vm.reportPeriod).then(function(res) {
                    if (res) {
                        toastr.warning("Ngày tháng năm khai báo của cơ sở đã tồn tại, vui lòng chọn mốc thời gian khác.", "Cảnh báo");
                    } else {
                        // //debugger
                        service.getAdministrativeUnitDtoByLoginUser().then(function(adminUnit) {
                            // //debugger
                            vm.adminstrativeUnits1 = adminUnit;
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
                            if (vm.reportPeriod.month) {
                                if (vm.reportPeriod.month <= 3) {
                                    periodQuarter = 1;
                                } else if (vm.reportPeriod.month > 3 && vm.reportPeriod.month <= 6) {
                                    periodQuarter = 2;
                                } else if (vm.reportPeriod.month > 6 && vm.reportPeriod.month <= 9) {
                                    periodQuarter = 3;
                                } else {
                                    periodQuarter = 4;
                                }
                            } else {
                                if (vm.form16Month <= 3) {
                                    periodQuarter = 1;
                                } else if (vm.form16Month > 3 && vm.form16Month <= 6) {
                                    periodQuarter = 2;
                                } else if (vm.form16Month > 6 && vm.form16Month <= 9) {
                                    periodQuarter = 3;
                                } else {
                                    periodQuarter = 4;
                                }
                            }

                            if ((todayYear == (vm.reportPeriod.year ? vm.reportPeriod.year : vm.form16Year) && (todayQuarter === periodQuarter)) || vm.isRoleAdmin == true) {
                                if (vm.reportForm16A && vm.reportForm16A.animal && vm.reportForm16A.dateReport) {
                                    vm.listReportForm16A.push(vm.reportForm16A);
                                    vm.reportForm16A = {};
                                }
                                vm.reportPeriod.reportItems = vm.listReportForm16A;
                                reportPeriodService.saveReportPeriod(vm.reportPeriod).then(function(data) {
                                    toastr.success("Lưu thành công", "Thông báo");
                                    vm.modalFormPopup16A.close();
                                    vm.pageIndex = 0;
                                    vm.searchByPage();
                                });
                            } else {
                                if (vm.adminstrativeUnits1.length == 0) {
                                    toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                                    return;
                                }
                                for (let i = 0; i < vm.adminstrativeUnits1.length; i++) {
                                    service.getUserAdminstrativeEditTableUnit(vm.adminstrativeUnits1[i].parentId != null ? vm.adminstrativeUnits1[i].parentId : vm.adminstrativeUnits1[i].id).then(function(data1) {

                                        vm.userEnableEdit = data1;
                                        if (vm.userEnableEdit && vm.userEnableEdit.length > 0) {
                                            for (let j = 0; j < vm.userEnableEdit.length; j++) {
                                                if (vm.userEnableEdit[j].year == vm.form16Year && vm.userEnableEdit[j].editable == true) {
                                                    var temp = 0;
                                                    if (vm.form16Month <= 3) {
                                                        temp = 1;
                                                    } else if (vm.form16Month > 3 && vm.form16Month <= 6) {
                                                        temp = 2;
                                                    } else if (vm.form16Month > 6 && vm.form16Month <= 9) {
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
                                                if (vm.reportForm16A && vm.reportForm16A.animal && vm.reportForm16A.dateReport) {
                                                    vm.listReportForm16A.push(vm.reportForm16A);
                                                    vm.reportForm16A = {};
                                                }
                                                vm.reportPeriod.reportItems = vm.listReportForm16A;
                                                reportPeriodService.saveReportPeriod(vm.reportPeriod).then(function(data) {
                                                    vm.modalFormPopup16A.close();
                                                    toastr.success("Lưu thành công", "Thông báo");
                                                    vm.pageIndex = 0;
                                                    vm.searchByPage();
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
                    }
                });
            } else {
                toastr.warning('Chưa chọn cơ sở nuôi', 'Cảnh báo');
            }
        }

        vm.confirmDeleteListExportAnimal = function(index) {
            vm.modalConfirmDeleteInjectionPlants = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_forest_detail.html',
                scope: $scope,
                // backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDeleteInjectionPlants.result.then(function(confirm) {
                if (confirm == 'yes') {
                    if (vm.forestProduct.exports && vm.forestProduct.exports.length > 0) {
                        if (vm.forestProduct.exports[index] && vm.forestProduct.exports[index].forestProductsList && vm.forestProduct.exports[index].forestProductsList.id) {
                            service.deleteLinkedByExportAnimalId(vm.forestProduct.exports[index].forestProductsList.id).then(function(data) {});
                        }
                        vm.forestProduct.exports.splice(index, 1);
                    }
                }
            })
        }

        vm.confirmDeleteRecordsExport = function(index) {
            vm.modalConfirmDeleteInjectionPlants = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_forest_detail.html',
                scope: $scope,
                // backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
            vm.modalConfirmDeleteInjectionPlants.result.then(function(confirm) {
                if (confirm == 'yes') {
                    vm.forestProduct.exports.splice(index, 1);
                }
            })
        }

        vm.searchByCodeForestProductList = function() {
            vm.pageIndexForestProductList = 1;
            vm.getPageForestProductList();
        }

        vm.enterSearchForestProductList = function(event) {
            if (event.keyCode == 13) {
                vm.pageIndexForestProductList = 1;
                vm.getPageForestProductList();
            }
        }
        vm.onChangFromDateToDate = function() {
            vm.pageIndexForestProductList = 1;
            vm.getPageForestProductList();
        }


        function getPageSearchImportAnimalTable() {
            // ;
            if (vm.farm && vm.farm.id) {
                vm.searchDtoTableImport = {};
                vm.searchDtoTableImport.nameOrCode = vm.farm.code;
                vm.searchDtoTableImport.farmId = vm.farm.id;
                vm.searchDtoTableImport.type = 1;
                service.getPageSearchImportAnimal(vm.searchDtoTableImport, vm.pageIndexImportAnimal, vm.pageSizeImportAnimal, function success(data) {

                }, function failure() {

                }).then(function(data) {
                    vm.importAnimalsTableImport = data.content;
                    vm.totalElementsImportAnimalTableImport = data.totalElements;
                });
            }
        };

        function getPageSearchExportAnimalTable() {
            // ;
            if (vm.farm && vm.farm.id) {
                vm.searchDtoTableExport = {};
                vm.searchDtoTableExport.nameOrCode = vm.farm.code;
                vm.searchDtoTableExport.farmId = vm.farm.id;
                vm.searchDtoTableExport.type = -1;
                service.getPageSearchImportAnimal(vm.searchDtoTableExport, vm.pageIndexExportAnimal, vm.pageSizeExportAnimal, function success(data) {

                }, function failure() {

                }).then(function(data) {
                    vm.importAnimalsTableExport = data.content;
                    vm.totalElementsImportAnimalTableExport = data.totalElements;
                });
            }
        };
        vm.animalForms = [];
        vm.selectAnimalForm = null; //Animal dc chon
        vm.listReport16FormDtoFilter = [];

        function onSelectedAnimalName() {
            if (vm.selectAnimalForm) {
                if (vm.listReport16FormDto && vm.listReport16FormDto.length > 0) {
                    vm.listReport16FormDtoFilter = vm.listReport16FormDto.filter(function(el) {
                        return el.animalName == vm.selectAnimalForm.animalName;
                    });
                }
            } else {
                vm.listReport16FormDtoFilter = vm.listReport16FormDto;
            }
            vm.listReport16FormDtoFilter.sort(function(a, b) {
                if (a.dateReport - b.dateReport) {
                    return a.dateReport - b.dateReport;
                } else {
                    return a.animalName.localeCompare(b.animalName);
                }
            });
        }

        //Biểu mẫu 16D

        vm.getNumberOfChildrenInTheCommunityOverTime = function() {
            if (!vm.searchDto16D) {
                vm.searchDto16D = {};
            }
            vm.reportFormAnimalGiveBirth = {}
            vm.reportFormAnimalGiveBirth.quantityChildIncrement = "";
            vm.searchDto16D.animalId = (vm.reportFormAnimalGiveBirth && vm.reportFormAnimalGiveBirth.animal && vm.reportFormAnimalGiveBirth.animal.id) ? vm.reportFormAnimalGiveBirth.animal.id : null;
            vm.searchDto16D.farmId = (vm.farm && vm.farm.id) ? vm.farm.id : null;
            vm.searchDto16D.dateReport = (vm.reportFormAnimalGiveBirth && vm.reportFormAnimalGiveBirth.dateReport) ? vm.reportFormAnimalGiveBirth.dateReport : null;
            vm.searchDto16D.id = (vm.reportFormAnimalGiveBirth && vm.reportFormAnimalGiveBirth.id) ? vm.reportFormAnimalGiveBirth.id : null;

            if (vm.searchDto16D.animalId && vm.searchDto16D.farmId && vm.searchDto16D.dateReport) {
                service.getNumberOfChildrenInTheCommunityOverTime(vm.searchDto16D).then(function(data) {
                    if (data && data.hasEditQuantityChildIncrement !== null && data.quantityChildIncrement !== null) {
                        vm.reportFormAnimalGiveBirth.hasEditQuantityChildIncrement = data.hasEditQuantityChildIncrement;
                        vm.reportFormAnimalGiveBirth.quantityChildIncrement = data.quantityChildIncrement;
                    }
                });
            }
        }
        vm.giveBirthQuantityChildIncrement = function(item) {
            //check quyen
            //debugger
            var report = item;
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
                    //debugger
                    if (dateReport > today) {
                        vm.reportFormAnimalGiveBirth.parentMale = 0;
                        vm.reportFormAnimalGiveBirth.parentFemale = 0;
                        vm.reportFormAnimalGiveBirth.quantityChildIncrement = "";
                        toastr.warning("Ngày khai sinh không được lớn hơn ngày hiện tại", "Cảnh báo");
                        return;
                    }
                    for (var i of vm.reportFormAnimalGiveBirths) {
                        var dateReportInForm = new Date(dateReport).toLocaleDateString();
                        var dateReportInTable = new Date(i.dateReport).toLocaleDateString();

                        var d1 = Date.parse(dateReportInForm);
                        var d2 = Date.parse(dateReportInTable);
                        if (d1 == d2 && vm.reportFormAnimalGiveBirth.isNew == true && (vm.reportFormAnimalGiveBirth.animal != null ? vm.reportFormAnimalGiveBirth.animal.id : null) == i.animal.id) {
                            vm.reportFormAnimalGiveBirth.parentMale = 0;
                            vm.reportFormAnimalGiveBirth.parentFemale = 0;
                            vm.reportFormAnimalGiveBirth.quantityChildIncrement = "";
                            toastr.warning("Ngày khai sinh của loài này đã tồn tại, vui lòng chọn sửa", "Cảnh báo");
                            return;
                        }
                    }
                    vm.getOneTemplate16aBysearchDto(item, "16D");
                    var dto = {};
                    item.hasEditQuantityChildIncrement = false;
                    if (item.id != null) {
                        dto.id = item.id;
                    } else {
                        dto.id = null;
                    }
                    if (item.animal) {
                        dto.animalId = item.animal.id;
                    }

                    dto.dateReport = item.dateReport;
                    dto.farmId = vm.farm.id;
                    service.giveBirthQuantityChildIncrement(dto).then(function(res) {
                        if (res && (res != "" || res != 0)) {
                            item.quantityChildIncrement = res;
                            item.hasEditQuantityChildIncrement = true;
                        } else {
                            if (!item.isAddRow) {
                                item.quantityChildIncrement = 0;
                            }
                            item.hasEditQuantityChildIncrement = false;
                            item.isAddRow = false;
                        }
                    });
                } else {
                    if (vm.adminstrativeUnits.length == 0) {
                        vm.reportFormAnimalGiveBirth.parentMale = 0;
                        vm.reportFormAnimalGiveBirth.parentFemale = 0;
                        vm.reportFormAnimalGiveBirth.quantityChildIncrement = "";
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
                                    if (dateReport > today) {
                                        vm.reportFormAnimalGiveBirth.parentMale = 0;
                                        vm.reportFormAnimalGiveBirth.parentFemale = 0;
                                        vm.reportFormAnimalGiveBirth.quantityChildIncrement = "";
                                        toastr.warning("Ngày khai sinh không được lớn hơn ngày hiện tại", "Cảnh báo");
                                        return;
                                    }
                                    for (var i of vm.reportFormAnimalGiveBirths) {
                                        var dateReportInForm = new Date(dateReport).toLocaleDateString();
                                        var dateReportInTable = new Date(i.dateReport).toLocaleDateString();

                                        var d1 = Date.parse(dateReportInForm);
                                        var d2 = Date.parse(dateReportInTable);
                                        if (d1 == d2 && vm.reportFormAnimalGiveBirth.isNew == true && (vm.reportFormAnimalGiveBirth.animal != null ? vm.reportFormAnimalGiveBirth.animal.id : null) == i.animal.id) {
                                            vm.reportFormAnimalGiveBirth.parentMale = 0;
                                            vm.reportFormAnimalGiveBirth.parentFemale = 0;
                                            vm.reportFormAnimalGiveBirth.quantityChildIncrement = "";
                                            toastr.warning("Ngày khai sinh của loài này đã tồn tại, vui lòng chọn sửa", "Cảnh báo");
                                            return;
                                        }
                                    }
                                    vm.getOneTemplate16aBysearchDto(item, "16D");
                                    var dto = {};
                                    item.hasEditQuantityChildIncrement = false;
                                    if (item.id != null) {
                                        dto.id = item.id;
                                    } else {
                                        dto.id = null;
                                    }
                                    if (item.animal) {
                                        dto.animalId = item.animal.id;
                                    }

                                    dto.dateReport = item.dateReport;
                                    dto.farmId = vm.farm.id;
                                    service.giveBirthQuantityChildIncrement(dto).then(function(res) {
                                        if (res && (res != "" || res != 0)) {
                                            item.quantityChildIncrement = res;
                                            item.hasEditQuantityChildIncrement = true;
                                        } else {
                                            if (!item.isAddRow) {
                                                item.quantityChildIncrement = 0;
                                            }
                                            item.hasEditQuantityChildIncrement = false;
                                            item.isAddRow = false;
                                        }
                                    });
                                } else {
                                    vm.reportFormAnimalGiveBirth.parentMale = 0;
                                    vm.reportFormAnimalGiveBirth.parentFemale = 0;
                                    vm.reportFormAnimalGiveBirth.quantityChildIncrement = "";
                                    toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                                    return;
                                }
                            } else {
                                vm.reportFormAnimalGiveBirth.parentMale = 0;
                                vm.reportFormAnimalGiveBirth.parentFemale = 0;
                                vm.reportFormAnimalGiveBirth.quantityChildIncrement = "";
                                toastr.warning("Hiện tại bạn không có quyền thao tác này", "Cảnh báo");
                                return;
                            }
                        });
                    }
                }

            });
        }

        vm.onSelectedAnimal16D = function(rawData) {
            // vm.getNumberOfChildrenInTheCommunityOverTime();
            vm.giveBirthQuantityChildIncrement(rawData);
        };

        vm.onChangeDateReport16D = function() {
            // vm.getNumberOfChildrenInTheCommunityOverTime();
        }
        vm.addressChange = function() {
            if (vm.farm != null && vm.farm.ownerAdress != null && vm.farm.ownerAdress != '') {
                vm.farm.adressDetail = vm.farm.ownerAdress;
            }
        }

        vm.husbandryMethodSelectList = [];
        vm.onHusbandryMethodSelect = function() {
                vm.farm.farmHusbandryMethods = [];
                vm.husbandryMethodSelectList.forEach(function(item) {
                    vm.farm.farmHusbandryMethods.push({ husbandryMethod: { id: item } });
                });
            }
            //thanh
        vm.checkMySelectChoice = function(choice) {
            vm.farm.farmHusbandryMethods = [];
            // husbandryMethod
            const getCheckboxState = () => {
                let states = [];
                states.length = 0;
                for (let i of choice) {
                    if ((document.getElementById(`husbandryMethod${i.id}`).checked)) {
                        states.push({ husbandryMethod: { id: i.id } })
                    }
                }
                return states;
            }
            vm.farm.farmHusbandryMethods = getCheckboxState();


            // vm.husbandryMethodSelectList.forEach(function (item) {
            //     if(choice.checked) {
            //         vm.farm.farmHusbandryMethods.push({ husbandryMethod: { id: item } });
            //     }
            // });
        }



        function initVGMap() {
            var mapCore = new VGMap.VGMapCore('map', {
                center: new VGMap.VGLatLng(16.031031269429292, 108.05932603776455),
                zoom: 6,
                minZoom: 6,
                maxZoom: 18,
                draggable: true,
                zoomControl: true,
                contextMenuEnabled: true,
                contextMenus: [{
                    title: 'Chọn',
                    onClick: function(e) {
                        // alert(e.lat + " - " + e.lng);
                        vm.farm.latitude = e.lat;
                        vm.farm.longitude = e.lat;
                        vm.modalMap.close();
                    }
                }]
            });
            new VGMap.VGTileLayer('https://fao.gisgo.vn/tiles/{z}/{x}/{y}.png', {
                enableCache: false,
                enableBuffer: true,
                visible: true,
            }).addTo(mapCore);
            vm.vgMapPos = {};

            function initPositionMap() {
                vm.vgMapPos.latitude = vm.farm.latitude;
                vm.vgMapPos.longitude = vm.farm.longitude;
                if (vm.vgMapPos.latitude != null && vm.vgMapPos.longitude != null) {
                    var feature = new VGMap.VGFeature({
                        geometry: new VGMap.VGPoint(new VGMap.VGLatLng(vm.vgMapPos.latitude, vm.vgMapPos.longitude)),
                        style: new VGMap.VGPointStyle({
                            imageUrl: 'assets/images/mapPickerIcon.png',
                            size: [36, 72]
                        })
                    });
                    if (featureTemp) {
                        featureTemp.remove(mapCore);
                    }
                    featureTemp = feature;
                    feature.addTo(mapCore);
                }
            }
            $timeout(initPositionMap, 750);
            vm.getListPronvice = function() {
                $.get('https://fao.gisgo.vn/region/listProvinces').done(function(xhr) {
                    if (xhr.status === 'OK') {
                        vm.pronvices = [];
                        $.each(xhr.data, function(idx, item) {
                            vm.pronvices.push(item);
                        });
                    }
                });
            }
            vm.onSelectedPronvice = function(value) {
                if (mapCore) {
                    mapCore.clearPermanentFeatures();
                }
                if (value) {
                    vm.districts = [];
                    vm.districtSelected = null;
                    vm.communeSelected = null;
                    vm.communes = [];
                    $.get('https://fao.gisgo.vn/region/listDistricts', {
                        parent_id: value.area_id
                    }).done(function(xhr) {
                        if (xhr.status === 'OK') {

                            $.each(xhr.data, function(idx, item) {
                                vm.districts.push(item);
                            });
                            getFarms(value.area_id, true);
                        }
                    });
                } else {
                    vm.districtSelected = null;
                    vm.districts = [];
                    vm.communeSelected = null;
                    vm.communes = [];
                    getFarms();
                }
            }
            vm.onSelectDistrict = function(value) {
                if (mapCore) {
                    mapCore.clearPermanentFeatures();
                }
                if (value) {
                    vm.communes = [];
                    vm.communeSelected = null;
                    $.get('https://fao.gisgo.vn/region/listCommunes', {
                        parent_id: value.area_id
                    }).done(function(xhr) {
                        if (xhr.status === 'OK') {

                            $.each(xhr.data, function(idx, item) {
                                vm.communes.push(item);
                            });
                            getFarms(value.area_id, true);
                        }
                    });
                } else {
                    vm.communeSelected = null;
                    vm.communes = [];
                    getFarms(vm.pronvinceSelected.area_id, true);
                }
            }
            vm.onSelectCommune = function(value) {
                if (mapCore) {
                    mapCore.clearPermanentFeatures();
                }
                if (value) {
                    getFarms(value.area_id, true);
                } else {
                    getFarms(vm.districtSelected.area_id, true);
                }
            }

            function getFarms(area_id, loadBounds) {
                var maxFeature = 200;
                var is_cluster = $('#is_cluster').is(':checked');
                mapCore.clearFeatures();
                mapCore.clearClusterFeatures();
                // 

                //
                if (area_id && loadBounds) {
                    $.get('https://fao.gisgo.vn/region/shape', {
                        area_id: area_id,
                        // zoom: mapCore.getZoom(),
                        // layer: vm.farmLayer,
                    }).done(function(xhr) {
                        if (xhr.status === 'OK') {
                            mapCore.addGeoJSON(xhr.data, {
                                permanent: true
                            });
                            mapCore.fitGeoJSON(xhr.data);
                        }
                    });
                    if (mapCore && vm.vgMapPos.latitude && vm.vgMapPos.latitude) {
                        var feature = new VGMap.VGFeature({
                            geometry: new VGMap.VGPoint(new VGMap.VGLatLng(vm.vgMapPos.latitude, vm.vgMapPos.latitude)),
                            style: new VGMap.VGPointStyle({
                                imageUrl: 'assets/images/mapPickerIcon.png',
                                size: [36, 72]
                            })
                        });
                        if (featureTemp) {
                            featureTemp.remove(mapCore);
                        }
                        featureTemp = feature;
                        feature.addTo(mapCore);
                    }
                }
            }


            function filterByArea(loadBounds) {
                if (vm.communeSelected) {
                    getFarms(vm.communeSelected.area_id, loadBounds);
                } else if (vm.districtSelected) {
                    getFarms(vm.districtSelected.area_id, loadBounds);
                } else if (vm.pronvinceSelected) {
                    getFarms(vm.pronvinceSelected.area_id, loadBounds);
                } else {
                    getFarms(undefined, loadBounds);
                }
            }
            // vm.pronvinceSelected = vm.selectedCity?vm.selectedCity.code: null;
            // vm.districtSelected = vm.selectedDist?vm.selectedDist.code: null;
            // vm.communeSelected = vm.farm.administrativeUnit ?vm.farm.administrativeUnit.code: null;
            vm.pronvinceSelected = null;
            vm.districtSelected = null;
            vm.communeSelected = null;
            vm.getListPronvice();
            if (vm.selectedCity) {
                vm.pronvinceSelected = { area_id: vm.selectedCity.code, name_vn: vm.selectedCity.name };
                vm.onSelectedPronvice(vm.pronvinceSelected.area_id);
            }
            if (vm.selectedDist) {
                vm.districtSelected = { area_id: vm.selectedDist.code, name_vn: vm.selectedDist.name };
                vm.onSelectDistrict(vm.districtSelected.area_id)
            }
            if (vm.farm.administrativeUnit) {
                vm.communeSelected = { area_id: vm.farm.administrativeUnit.code, name_vn: vm.farm.administrativeUnit.name };
                vm.onSelectCommune(vm.communeSelected.area_id)
            }
            var features = [];

            var lastLevel = 1;
            mapCore.on('zoom_changed', function(e) {
                if (vm.pronvinceSelected || vm.districtSelected || vm.communeSelected) {
                    filterByArea(false);
                } else {
                    if (e.getZoom() >= 0 && e.getZoom() < 11 && lastLevel !== 1) {
                        lastLevel = 1;
                        getFarms();
                    } else if (e.getZoom() >= 10 && e.getZoom() < 13 && lastLevel !== 2) {
                        lastLevel = 2;
                        getFarms();
                    } else if (e.getZoom() >= 13 && lastLevel !== 3) {
                        lastLevel = 3;
                        getFarms();
                    }
                }
                if (mapCore && vm.vgMapPos.latitude != null && vm.vgMapPos.longitude != null) {
                    var feature = new VGMap.VGFeature({
                        geometry: new VGMap.VGPoint(new VGMap.VGLatLng(vm.vgMapPos.latitude, vm.vgMapPos.longitude)),
                        style: new VGMap.VGPointStyle({
                            imageUrl: 'assets/images/mapPickerIcon.png',
                            size: [36, 72]
                        })
                    });
                    if (featureTemp) {
                        featureTemp.remove(mapCore);
                    }
                    featureTemp = feature;
                    feature.addTo(mapCore);
                }
            });
            var featureTemp;
            mapCore.on('click', function(e) {
                // mapCore.remove();
                var feature = new VGMap.VGFeature({
                    geometry: new VGMap.VGPoint(new VGMap.VGLatLng(e.latlng.lat, e.latlng.lng)),
                    style: new VGMap.VGPointStyle({
                        imageUrl: 'assets/images/mapPickerIcon.png',
                        size: [36, 72]
                    })
                });
                if (featureTemp) {
                    featureTemp.remove(mapCore);
                }
                featureTemp = feature;
                feature.addTo(mapCore);

                vm.vgMapPos.latitude = e.latlng.lat;
                vm.vgMapPos.longitude = e.latlng.lng;
            });
            // function testThanh() {

            //     if(vm.farm.longitude != null && vm.farm.latitude != null ){
            //         if((8 > parseFloat(vm.farm.latitude)|| parseFloat(vm.farm.latitude) > 24 )
            //         ) {

            //         toastr.warning("Toạ độ này nằm ngoài phạm vi lãnh thổ Việt Nam, mời bạn nhập lại.", "Thông báo");
            //         focusItem = document.getElementById("latitude");
            //             if (focusItem) {
            //                 focusItem.focus();
            //             }
            //         return;
            //         }

            //     }
            //     if(vm.farm.longitude != null && vm.farm.latitude != null ){
            //         if(( 102 >parseFloat(vm.farm.longitude) || parseFloat(vm.farm.longitude) > 110 )
            //         ) {

            //         toastr.warning("Toạ độ này nằm ngoài phạm vi lãnh thổ Việt Nam, mời bạn nhập lại.", "Thông báo");
            //         focusItem = document.getElementById("latitude");
            //             if (focusItem) {
            //                 focusItem.focus();
            //             }
            //         return;
            //         }

            //     }

            // }
            // vm.farmPhoneNumberChange = function () {
            //     if (vm.farm.phoneNumber != null) {
            //         vm.farm.ownerPhoneNumber = vm.farm.phoneNumber;
            //     }
            // }

            vm.confirmCoordinateVgMap = function() {
                    if (vm.vgMapPos.latitude == undefined && vm.vgMapPos.longitude == undefined) {
                        toastr.warning('Chưa chọn tọa độ')
                        return;
                    }
                    if (vm.vgMapPos.latitude && vm.vgMapPos.longitude) {
                        vm.farm.latitude = vm.vgMapPos.latitude;
                        vm.farm.longitude = vm.vgMapPos.longitude;
                        // vm.modalMap.close();

                        if (vm.farm.longitude != null && vm.farm.latitude != null) {
                            if ((8 > parseFloat(vm.farm.latitude) || parseFloat(vm.farm.latitude) > 24)) {
                                toastr.warning("Toạ độ này nằm ngoài phạm vi lãnh thổ Việt Nam, mời bạn nhập lại.", "Thông báo");
                                return;
                            }
                        }
                        if (vm.farm.longitude != null && vm.farm.latitude != null) {
                            if ((102 > parseFloat(vm.farm.longitude) || parseFloat(vm.farm.longitude) > 110)) {
                                toastr.warning("Toạ độ này nằm ngoài phạm vi lãnh thổ Việt Nam, mời bạn nhập lại.", "Thông báo");
                                return;
                            }
                        }
                        vm.checkMapLatitudeOut = false;
                        vm.checkMapLongitudeOut = false;
                        vm.convertLatLonToVN2000();
                        vm.modalMap.close();

                    }
                    // if (vm.vgMapPos.latitude && vm.vgMapPos.longitude) {
                    //     vm.modalMap.close();
                    // }
                    // else 
                    // {
                    //     
                    //     toastr.warning('Chưa chọn tọa độ')
                    // }
                }
                // getFarms(); 
            filterByArea(true);
        }

        vm.convertLatLonToVN2000 = function (){
            if(vm.selectedCity && vm.selectedCity.vn2000 && vm.farm.longitude && vm.farm.latitude ){
                var wgs84 = "+title=WGS 84 (long/lat) +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees"
                var vn2000 = vm.selectedCity.vn2000;
                var convertedXY = proj4(wgs84, vn2000, [vm.farm.longitude, vm.farm.latitude]);
                if(convertedXY && convertedXY.length>1){
                   vm.farm.xM = convertedXY[0];
                    vm.farm.yM = convertedXY[1];
                }
            }
        }

        vm.convertVN2000ToLatTo = function (){
            if( vm.selectedCity && vm.selectedCity.vn2000 && vm.farm.xM && vm.farm.yM ){
                // var wgs84 = "+title=WGS 84 (long/lat) +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees"
                var vn2000 = vm.selectedCity.vn2000;
                // var convertedXY = proj4(wgs84, wgs84, [vm.farm.xM, vm.farm.yM]);
                var convertedXY = proj4(vn2000).inverse([parseFloat(vm.farm.xM),parseFloat( vm.farm.yM)]);
                if(convertedXY && convertedXY.length>1){
                   vm.farm.longitude = convertedXY[0];
                    vm.farm.latitude = convertedXY[1];
                }
            }
        }

        //tran huu dat ham xuat file word start
        vm.myBlobObjectWord = undefined;
        vm.getFileWord = function(id) {
            var object = {};
            object.id = id;
            service.getStreamDocument(object)
                .then(function(data) { //is important that the data was returned as Aray Buffer
                    //vm.myBlobObjectWord = new Blob([data], { type: 'application/msword' });
                    var file = new Blob([data], { type: 'application/msword' });
                    FileSaver.saveAs(file, "BangKeLamSan.doc");
                }, function(fail) {
                    vm.myBlobObjectWord = [];
                });

        };

        //tran huu dat ham xuat file word end

        //tran huu dat start thêm hàm lưu hình ảnh
        vm.uploadFile = null;
        vm.forestProductfile = null;
        vm.uploadFilesForest = function(file, errFile) {
                $scope.uploadFile = file;
                if ($scope.uploadFile != null) {
                    Uploader.upload({
                        url: settings.api.baseUrl + 'api/file/uploadArticleImg',
                        method: 'POST',
                        data: { uploadfile: $scope.uploadFile }
                    }).then(function(successResponse) {
                        var file = successResponse.data;
                        if (file) {
                            //vm.forestProductfile=file;
                            vm.forestProduct.file = file
                        }

                    }, function(errorResponse) {
                        toastr.error('Error submitting data...', 'Error');
                    });
                }
            }
            //tran huu dat end thêm hàm lưu hình ảnh

        vm.adminstrativeUnits_DistOwner;
        vm.adminstrativeUnits_WardsOwner;
        vm.onFmsadminstrativeUnitCitySelectedOwner = function(city) {
            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_DistOwner = data;
                        vm.ownerSelectedDist = null;
                        vm.farm.ownerAdministrativeUnit = null;
                    } else {
                        vm.ownerSelectedDist = null;
                        vm.farm.ownerAdministrativeUnit = null;
                        vm.adminstrativeUnits_DistOwner = [];
                        vm.adminstrativeUnits_WardsOwner = [];
                    }
                });
            } else {
                vm.ownerSelectedDist = null;
                vm.farm.ownerAdministrativeUnit = null;
                vm.adminstrativeUnits_DistOwner = [];
                vm.adminstrativeUnits_WardsOwner = [];
            }
        }

        vm.onFmsadminstrativeUnitDistSelectedOwner = function(dist) {
            if (dist != null && dist.id != null) {
                service.getAllByParentId(dist.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_WardsOwner = data;
                    } else {
                        vm.farm.ownerAdministrativeUnit = null;
                        vm.adminstrativeUnits_WardsOwner = [];
                    }
                    vm.farm.ownerAdministrativeUnit = null;
                });
                //vm.autoGenerateChange();
            } else {
                vm.farm.ownerAdministrativeUnit = null;
                vm.adminstrativeUnits_WardsOwner = [];
            }
        }
        vm.onFmsadminstrativeUnitWardsSelectedOwner = function(item) {
            if (item) {
                service.getAllByParentId(item.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_WardsOwner = data;
                        vm.farm.ownerAdministrativeUnit = null;
                    }
                });
            } else {
                // vm.adminstrativeUnits_Wards = [];
                vm.farm.ownerAdministrativeUnit = null;
            }
        }

        vm.onFmsadminstrativeUnitCitySelectedFrom = function(city) {
            if (city != null && city.id != null) {
                service.getChildrenByParentId(city.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_DistFrom = data;
                        vm.forestProduct.administrativeUnitFrom = null;
                    } else {
                        vm.forestProduct.administrativeUnitFrom = null;
                        vm.adminstrativeUnits_DistFrom = [];
                    }
                });
            } else {
                vm.forestProduct.administrativeUnitFrom = null;
                vm.adminstrativeUnits_DistFrom = [];
            }
        }

        vm.onFmsadminstrativeUnitCitySelectedTo = function(city) {
            if (city != null && city.id != null) {
                service.getChildrenByParentId(city.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_DistTo = data;
                        vm.forestProduct.administrativeUnitTo = null;
                    } else {
                        vm.forestProduct.administrativeUnitTo = null;
                        vm.adminstrativeUnits_DistTo = [];
                    }
                });
            } else {
                vm.forestProduct.administrativeUnitTo = null;
                vm.adminstrativeUnits_DistTo = [];
            }
        }

        function getDataByComboboxFrom(Wards) {
            if (Wards != null) {
                if (Wards.parentDto != null) {
                    vm.selectedCityFrom = Wards.parentDto;
                } else {
                    vm.selectedCityFrom = Wards;
                }
                service.getChildrenByParentId(vm.selectedCityFrom.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_DistFrom = data;
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
                service.getChildrenByParentId(vm.selectedCityTo.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_DistFrom = data;
                    }
                });
            }
        }

        vm.printFileWord = function() {
            if (!vm.forestProduct.farm) {
                toastr.warning("Chưa chọn cơ sở nuôi", "Cảnh báo");
                return;
            } else if (!vm.forestProduct.code) {
                toastr.warning("Chưa nhập Số hiệu bản kê", "Cảnh báo");
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
                vm.forestProduct.details = vm.listForesProductDetail;
                service.saveForestProductList(vm.forestProduct).then(function(data) {
                    vm.getFileWord(data.id);
                    vm.modalPopupAddEditForestProductList.close();
                    vm.getPageForestProductList();
                });
            }
        }

        vm.sendEmail = function() {
            if (!vm.forestProduct.farm) {
                toastr.warning("Chưa chọn cơ sở nuôi", "Cảnh báo");
                return;
            } else if (!vm.forestProduct.code) {
                toastr.warning("Chưa nhập Số hiệu bản kê", "Cảnh báo");
                return;
            }
            if (!vm.forestProduct.administrativeUnitTo && !vm.selectedCityTo) {
                toastr.warning("Chưa chọn địa chỉ gửi đến", "Cảnh báo");
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
                vm.forestProduct.details = vm.listForesProductDetail;
                service.sendEmail(vm.forestProduct).then(function(data) {
                    vm.modalPopupAddEditForestProductList.close();
                    // vm.getPageForestProductList();
                    toastr.success("Bạn đã gưi email thành công", "Thông báo");
                    return;
                });
            }
        }

        vm.totalAnimal;
        vm.onSelectedAnimal = function(animal) {
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

        vm.showPositionFarmVgmap = (farm) => {
            if (farm != null && farm.id != null) {
                let { id, latitude, longitude, name } = farm;
                $state.go('application.farm_position_vgmap', { id: id, lat: latitude, long: longitude, name: name });
            }
        }

    }

})();