/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.AnimalReportData').controller('AnimalReportDataExportExcellController', AnimalReportDataExportExcellController);

    AnimalReportDataExportExcellController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'AnimalReportDataService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate'
    ];
    angular.module('Education.AnimalReportData').directive('fileDownload',function(){
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
    function AnimalReportDataExportExcellController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
        // blockUI.stop();
        // blockUI.start();
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.animalReportDatas = []
        vm.modalFarmSize = {};
        vm.animalReportData = { isNew: false };
        vm.modalConfirmDelete = {};
        vm.selectedFarmSize = [];
        vm.count = 0;
        vm.searchDto = {};
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.subModalInstance = null;
        vm.pageIndexFarm = 1;
        vm.pageSizeFarm = 25;
        vm.farmCode = null;
        vm.originals = [];
        vm.productTargets = [];
        vm.individuals = [];
        vm.searchAnimalReportDataDto = {};
        vm.bsTableControl = {
            options: {
                data: vm.animalReportDatas,
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
                        vm.selectedFarmSize.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedFarmSize = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedFarmSize);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedFarmSize.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedFarmSize = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    vm.getListAnimalReportData();
                    //vm.getAllAnimalType();
                }
            }
        };
        vm.listCites = [
            {value:"I",name:"I" },
            {value:"II",name:"II" },
            {value:"III",name:"III" }
        ];
        vm.getListAnimalReportData = function () {
            service.getListAnimalReportData(vm.pageIndex, vm.pageSize).then(function (data) {
                vm.animalReportDatas = data.content;
                vm.bsTableControl.options.data = vm.animalReportDatas;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        // vm.getListAnimalReportData();
        vm.getPageBySearchAnimalReportDataDto = function(){
            if(vm.searchDto.province){
                vm.searchDto.provinceId = vm.searchDto.province.id;
            }else{
                vm.searchDto.provinceId = null;
            }
            if(vm.searchDto.district){
                vm.searchDto.districtId = vm.searchDto.district.id;
            }else{
                vm.searchDto.districtId = null;
            }
            if(vm.searchDto.ward){
                vm.searchDto.communeId = vm.searchDto.ward.id;
            }else{
                vm.searchDto.communeId = null;
            }
            if(vm.searchDto.animal){
                vm.searchDto.animalId = vm.searchDto.animal.id;
            }else{
                vm.searchDto.animalId = null;
            }
            if(!vm.searchDto.year){
                toastr.warning("Phải chọn năm của kỳ báo cáo");
                return;
            }else{
                service.getViewExcelExportAnimalReportData(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                    vm.animalReportDatas = data.content;
                    vm.totalItem = data.totalElements;
                });
            }
        }
        // vm.getPageBySearchAnimalReportDataDto();
        vm.years = [];
        vm.currentYear = new Date().getFullYear();
        for(let year = vm.currentYear ;year >= vm.currentYear - 20;year--){
            vm.years.push({value:year, name: year+""});
        }

        vm.enterSearch = function () {
            // console.log(event.keyCode);
            if (event.keyCode == 13) {//Phím Enter

                vm.searchByCode();
            }
        };
        vm.searchByCode = function () {
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.findBy();
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


            console.log(vm.searchDto);
            service.getPageSearchFarmSize(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.animalReportDatas = data.content;
                vm.bsTableControl.options.data = vm.animalReportDatas;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }


        vm.createFarmSize = function () {
            vm.animalReportData = { isNew: true };
            vm.individuals = [];
            vm.getListAnimal();
            vm.getAllProductTarget();
            vm.getAllOgirinal();
            vm.modalFarmSize = modal.open({
                animation: true,
                templateUrl: 'animal_report_data_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });

        };

        $scope.editAnimalReportData = function (id) {
            if (id) {
                vm.getListAnimal();
                vm.getAllProductTarget();
                vm.getAllOgirinal();
                service.getAnimalById(id).then(function (data) {
                    vm.animalReportData = data;
                    vm.animalReportData.isNew = false;
                    vm.tempCode = vm.animalReportData.code;
                    console.log(vm.animalReportData);
                    if(vm.animalReportData.individualAnimals && vm.animalReportData.individualAnimals.length > 0){
                        vm.individuals = angular.copy(vm.animalReportData.individualAnimals);
                    }else{
                        vm.individuals = [];
                    }
                });
                vm.modalFarmSize = modal.open({
                    animation: true,
                    templateUrl: 'animal_report_data_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
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

        vm.deleteFarmSizes = function () {
            if (vm.selectedFarmSize.length == 0) {
                //toastr.warning("Bạn chưa chọn!")
                toastr.warning($translate.instant("farmSize.emptySelected"), $translate.instant("toastr.warning"));
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

        vm.save = function () {
            if (vm.animalReportData && vm.checkValid()) {
                vm.animalReportData.individualAnimals = vm.individuals;
                if (vm.animalReportData.isNew) {
                    service.createAnimalReportData(vm.animalReportData).then(function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllFarmSize();
                        vm.findBy();
                        vm.modalFarmSize.close();
                        vm.selectedFarmSize = [];
                        vm.getListAnimalReportData();
                    }).catch(function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                } else {
                    vm.animalReportData.code = vm.tempCode;
                    service.saveAnimalReportData(vm.animalReportData.id, vm.animalReportData).then(function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllFarmSize();
                        vm.findBy();
                        vm.modalFarmSize.close();
                        vm.selectedFarmSize = [];
                        vm.getListAnimalReportData();
                    }).catch(function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        vm.confirmDelete = function () {
            vm.pageIndex = 1;
            var ids = vm.selectedFarmSize.map(function (element) {
                return element.id;
            });
            service.deleteFarmSizes(ids, function success(data) {
                //toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                //vm.getAllFarmSize();
                vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedFarmSize = [];
            }, function failure(error) {
                //toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                console.log(error);
            });
        }
        $scope.deleteAnimalReportData = function (id) {
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
                service.deleteById(vm.recordId).then(function (data) {
                    if (data != null && data.code == "-1") {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    }
                    else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }
                    // vm.getAllFarmSize();
                    vm.getListAnimalReportData();
                    vm.modalConfirmDelete.close();
                    vm.selectedFarmSize = [];
                });
            }
        }


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
            vm.getPageSearchFarm();
            vm.subModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {

                }
            }, function () {
                vm.farmCode = null;
                console.log("cancel");
            });
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
                    vm.getPageSearchFarm();
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
            vm.getPageSearchFarm();
        };
        vm.getPageSearchFarm = function () {
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
                console.log(vm.farms);
                if (vm.isFamer == true && vm.farms != null && vm.farms.length > 0) {
                    if (vm.exportAnimal == null) vm.exportAnimal = {};
                    vm.farmName = vm.farms[0].name;
                    vm.farmCode = vm.farms[0].code;
                    vm.exportAnimal.farm = vm.farms[0];
                }
            });
        }
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
                vm.animalReportData.farm = vm.farmSelected;

                vm.subModalInstance.close();
            }
        }

        vm.getListAnimal = function(){
        	service.getAllAnimal().then(function (data) {
        		vm.animals = data;
                console.log(vm.getAllAnimal)
            });
        }
        vm.getAllOgirinal = function(){
        	service.getAllOgirinal().then(function (data) {
        		vm.originals = data;
                console.log(vm.getAllAnimal)
            });
        }
        vm.getAllProductTarget = function(){
        	service.getAllProductTarget().then(function (data) {
        		vm.productTargets = data;
                console.log(vm.getAllAnimal)
            });
        }

        vm.checkValid = function(){
            if(!vm.animalReportData.farm){
                toastr.warning($translate.instant("exportAnimal.emptyFarm"), $translate.instant("toastr.warning"));
                return false;
            }
            if(!vm.animalReportData.animal){
                toastr.warning($translate.instant("exportAnimal.emptyAnimal"), $translate.instant("toastr.warning"));
                return false;
            }
            if(!vm.animalReportData.year){
                toastr.warning("Bạn phải chọn năm của kỳ báo cáo", $translate.instant("toastr.warning"));
                return false;
            }
            if(!vm.animalReportData.type){
                toastr.warning("Bạn phải chọn loại", $translate.instant("toastr.warning"));
                return false;
            }
            if(!vm.animalReportData.original){
                toastr.warning("Bạn phải nguồn gốc", $translate.instant("toastr.warning"));
                return false;
            }
            if(!vm.animalReportData.productTarget){
                toastr.warning("Bạn phải chọn mục đích nuôi", $translate.instant("toastr.warning"));
                return false;
            }
            if(vm.animalReportData.unGender < 0){
                toastr.warning("Số lượng vật nuôi không rõ giới tính phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            if(vm.animalReportData.female < 0){
                toastr.warning("Số lượng vật nuôi cái phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            if(vm.animalReportData.male < 0){
                toastr.warning("Số lượng vật nuôi đực phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            let total = 0;
            if(vm.animalReportData.male >= 0){
                total += vm.animalReportData.male
            }
            if(vm.animalReportData.female >= 0){
                total += vm.animalReportData.female
            }
            if(vm.animalReportData.unGender >= 0){
                total += vm.animalReportData.unGender
            }
            if(total <= 0){
                toastr.warning("Tổng số vật nuôi phải lớn hơn 0", $translate.instant("toastr.warning"));
                return false;
            }
            

            return true;
        }
        vm.getListAnimal();


        vm.addIndividual = function(){
            vm.individual = {isNew: true};
            vm.modalIndividualAnimal = modal.open({
                animation: true,
                templateUrl: 'individual_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        vm.editIndividual = function(index){
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

        vm.saveIndividual = function(){
            if(vm.individual && vm.individual.isNew == true){
                const len = vm.individuals.length;
                vm.individuals[len] = vm.individual;
                vm.updateTotalView();
                vm.modalIndividualAnimal.close();
            }else if(vm.individual){
                vm.individuals[vm.individualIndex] = vm.individual;
                vm.updateTotalView();
                vm.modalIndividualAnimal.close();
            }
        }
        vm.updateTotalView = function(){
            if(vm.individuals && vm.individuals.length > 0){
                vm.animalReportData.male = 0;
                vm.animalReportData.female = 0;
                vm.animalReportData.unGender = 0;
                for(let index = 0;index < vm.individuals.length;index++){
                    const item = vm.individuals[index];
                    if(item.gender == settings.ENUM_AnimalGenders.male){
                        vm.animalReportData.male++;
                    }else if(item.gender == settings.ENUM_AnimalGenders.female){
                        vm.animalReportData.female++;
                    }else if(item.gender == settings.ENUM_AnimalGenders.unGender){
                        vm.animalReportData.unGender++;
                    }
                }
            }
        }
        vm.deleteIndividual = function(index){
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
        vm.confirmDeleteIndividual = function(){
            vm.individuals.splice(vm.individualIndex, 1);
            vm.modalIndividualConfirmDelete.close();
            vm.updateTotalView();
        }
        vm.getAnimalGender = function(value){
            const result = settings.animalGenders.find(item => item.value == value);
            if(result){
                return result.name;
            }else return "";
            
        }
        vm.getAnimalStatus = function(value){
            const result = settings.IndividualAnimalStatuses.find(item => item.value == value);
            if(result){
                return result.name;
            }else return "";
        }
        vm.adminstrativeUnits = [];
        function getAllProvince(){
            service.getAdminstrativeUnitsCity().then(function(data){
                vm.adminstrativeUnits_City = data||[];
            });
        }
        getAllProvince();
        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                        vm.selectedDist = null;
                        vm.selectedWard = null;
                        vm.searchDto.district=null;
						vm.searchDto.ward=null;
						vm.ward=null;
						vm.district=null;
                    } else {
                        vm.searchDto.district=null;
						vm.searchDto.ward=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
						vm.district=null;
		                vm.adminstrativeUnits_Dist = [];
						vm.ward=null;
						vm.adminstrativeUnits_Wards=[];
                    }
                });
            } else {
                vm.searchDto.district=null;
				vm.searchDto.ward=null;
	        	vm.selectedDist = null;
	        	vm.selectWards = null;
				vm.district=null;
                vm.adminstrativeUnits_Dist = [];				
				vm.ward=null;
				vm.adminstrativeUnits_Wards=[];
            }
        }

        vm.onFmsadminstrativeUnitDistSelected = function (dist) {
            if (dist != null && dist.id != null) {
				service.getAllByParentId(dist.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Wards = data;
						vm.searchDto.ward=null;						
						vm.selectWards=null;
						vm.ward=null;
						
					}
					else {
						vm.searchDto.ward=null;
			        	vm.selectWards = null;
		                vm.ward=null;
						vm.adminstrativeUnits_Wards=[];
					}
					
	            });
			}
			else {
				vm.searchDto.ward=null;
	        	vm.selectWards = null;
				vm.ward=null;
				vm.adminstrativeUnits_Wards=[];
			}
        }
        vm.onFmsadminstrativeUnitWardsSelected=function(item){
        }
        
        $scope.myBlobObject=undefined;
        $scope.myBlobObjectTemplate=undefined;
        $scope.getFile=function(){
            $scope.myBlobObject=undefined;
            $scope.myBlobObjectTemplate=undefined;
            if(vm.searchDto.province){
                vm.searchDto.provinceId = vm.searchDto.province.id;
            }else{
                vm.searchDto.provinceId = null;
            }
            if(vm.searchDto.district){
                vm.searchDto.districtId = vm.searchDto.district.id;
            }else{
                vm.searchDto.districtId = null;
            }
            if(vm.searchDto.ward){
                vm.searchDto.communeId = vm.searchDto.ward.id;
            }else{
                vm.searchDto.communeId = null;
            }
            if(vm.searchDto.animal){
                vm.searchDto.animalId = vm.searchDto.animal.id;
            }else{
                vm.searchDto.animalId = null;
            }

            console.log('download started, you can show a wating animation');
            service.getStream(vm.searchDto)
                .then(function(data){//is important that the data was returned as Aray Buffer
                    console.log('Stream download complete, stop animation!');
                    $scope.myBlobObject=new Blob([data],{ type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
                },function(fail){
                    console.log('Download Error, stop animation and show error message');
                    $scope.myBlobObject=[];
                });
        };
        $scope.getFileTemplate=function(){
            $scope.myBlobObject=undefined;
            $scope.myBlobObjectTemplate=undefined;
            if(vm.searchDto.province){
                vm.searchDto.provinceId = vm.searchDto.province.id;
            }else{
                vm.searchDto.provinceId = null;
            }
            if(vm.searchDto.district){
                vm.searchDto.districtId = vm.searchDto.district.id;
            }else{
                vm.searchDto.districtId = null;
            }
            if(vm.searchDto.ward){
                vm.searchDto.communeId = vm.searchDto.ward.id;
            }else{
                vm.searchDto.communeId = null;
            }
            if(vm.searchDto.animal){
                vm.searchDto.animalId = vm.searchDto.animal.id;
            }else{
                vm.searchDto.animalId = null;
            }

            console.log('download started, you can show a wating animation');
            service.getStreamTemplate(vm.searchDto)
                .then(function(data){//is important that the data was returned as Aray Buffer
                    console.log('Stream download complete, stop animation!');
                    $scope.myBlobObjectTemplate=new Blob([data],{ type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
                },function(fail){
                    console.log('Download Error, stop animation and show error message');
                    $scope.myBlobObjectTemplate=[];
                });
        };

    }

})();
