/**
 * Created by bizic on 28/8/2016.
 */
 (function () {
    'use strict';

    angular.module('Education.Animal').controller('ListAnimalController', ListAnimalController);

    ListAnimalController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'AnimalService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        'AnimalTypeService',
        'OriginalService',
        'ProductTargetService',
        '$translate',
        'Upload'
    ];

    function ListAnimalController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, animalTypeService, originalService, productTargetService, $translate, Upload) {
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
        vm.animals = []
        vm.animals1 = []
        vm.modalAnimal = {}
        vm.animal = { isNew: false, typeDto: null, originalDto: null, productTargetDto: null };
        vm.modalConfirmDelete = {};
        vm.selectedAnimals = [];
        vm.selectedAnimals1 = [];
        vm.baseUrl = settings.api.baseUrl + settings.api.apiPrefix;

        vm.originals = [];

        vm.reproductionForms = [];
        for (let key in settings.ReproductionForm) {
            vm.reproductionForms.push(settings.ReproductionForm[key]);
        }

        vm.animalTypes = [];
        vm.searchDto = {};
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.list06=[];
        vm.listcites = [];
        vm.Lists = [];
        vm.getListCites = function (callSearchFunction) {
            service.getListCites().then(function (data) {
                vm.listcites = [];
                vm.cites=null;
                vm.vnlist=null;
                vm.vnlist06=null;
                data.forEach(item => {
                    var cites = '-';
                    if (item && item.trim().length > 0) {
                        cites = item;
                    }
                    vm.listcites.push({ name: cites });
                });
            });
            vm.getVnLists(true);
        }
        vm.getVnLists = function (callSearchFunction) {
            service.getVnLists(vm.searchDto.animalClass, vm.searchDto.ordo, vm.searchDto.family).then(function (data) {
                vm.Lists = [];
                vm.list06 = [];
                vm.vnlist=null;
                vm.vnlist06=null;
                data.forEach(item => {
                    var list = '-';
                    if (item && item.trim().length > 0) {
                        list = item;
                    }
                    vm.Lists.push({ name: list });
                });
            });
            vm.getVnList06s(true);
        }
        
        vm.getVnList06s = function (callSearchFunction) {
            service.getVnList06s().then(function (data) {
                vm.list06 = [];
                vm.vnlist06=null;
                data.forEach(item => {
                    var title = '-';
                    if (item && item.trim().length > 0) {
                        title = item;
                    }
                    vm.list06.push({ name: title });
                });
                if (callSearchFunction) {
                    vm.searchByCode();
                }
            });
        }
        vm.getListCites();
        vm.getVnLists();
        vm.getVnList06s();

        vm.getPageAnimal = function () {
            service.getPageAnimal(vm.pageIndex, vm.pageSize).then(function (data) {
                vm.animals = data.content;
                vm.animals1 = data.content;
                vm.bsTableControl.options.data = vm.animals;
                vm.bsTableControl.options.totalRows = data.totalElements;
                vm.bsTableControl1.options.data = vm.animals1;
                vm.bsTableControl1.options.totalRows = data.totalElements;
            });
            vm.generateAnimalType();
            vm.generateOriginal();
            vm.generateProductTarget();
        }
        vm.generateAnimalType = function () {
            animalTypeService.getAllAnimalTypeDto().then(function (data) {
                vm.animalTypes = data;
            });
        }
        vm.generateOriginal = function () {
            originalService.getAllOriginalDto().then(function (data) {
                vm.originals = data;
            });
        }
        vm.generateProductTarget = function () {
            productTargetService.getAllProductTarget().then(function (data) {
                vm.productTargets = data;
            });
        }
        vm.generateAnimalParent = function () {
            service.getListParent().then(function (data) {
                vm.animalParents = data;
            });
        }
        /** generate */
        vm.getPageAnimal();
        vm.generateAnimalType();
        vm.generateOriginal();
        vm.generateProductTarget();
        vm.generateAnimalParent();

        /** get data */
        vm.listAnimalClass = [];
        vm.listAnimalFamily = [];
        vm.listAnimalOrdo = [];
      
        vm.getListAnimalClass = function () {
            service.getListAnimalClass().then(function (data) {
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

            vm.getListAnimalOrdo(true);
        }

        vm.getListAnimalOrdo = function (callSearchFunction) {
            service.getListAnimalOrdo(vm.searchDto.animalClass).then(function (data) {
                vm.listAnimalOrdo = [];
                vm.listAnimalFamily = [];
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

        vm.getListAnimalFamily = function (callSearchFunction) {
            service.getListAnimalFamily(vm.searchDto.animalClass, vm.searchDto.ordo).then(function (data) {
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
        vm.animalClassSelected = function () {
            if (vm.animalClass && vm.animalClass == '-') {
                vm.searchDto.animalClass = null;
                vm.searchDto.ordo = null;
                vm.searchDto.family = null;
            }
            else {
                vm.searchDto.animalClass = vm.animalClass;
                vm.searchDto.ordo = null;
                vm.searchDto.family = null;
            }
            vm.getListAnimalOrdo(true);
        }

        vm.animalOrdoSelected = function () {
            if (vm.ordo && vm.ordo == '-') {
                vm.searchDto.ordo = null;
                vm.searchDto.family = null;
            }
            else {
                vm.searchDto.ordo = vm.ordo;
                vm.searchDto.family = null;
            }
            vm.getListAnimalFamily(true);
        }
        vm.animalCitySelected = function () {
            if (vm.cites && vm.cites == '-') {
                vm.searchDto.cites=null;
                vm.searchDto.vnlist = null;
                vm.searchDto.vnlist06 = null;
            }
            else {
                vm.searchDto.cites = vm.cites;
                vm.searchDto.vnlist = null;
                vm.searchDto.vnlist06 = null;
            }
            vm.getVnLists(true);
        }
        vm.animalListSelected = function () {
            if (vm.vnlist && vm.vnlist == '-') {
                vm.searchDto.vnlist = null;
                vm.searchDto.vnlist06 = null;
            }
            else {
                vm.searchDto.vnlist = vm.vnlist;
                vm.searchDto.vnlist06 = null;
            }
            vm.getVnList06s(true);
           
        }
        vm.animalList06Selected = function () {
            if (vm.vnlist06 && vm.vnlist06 == '-') {
                vm.searchDto.vnlist06 = null;
            }
            else {
                vm.searchDto.vnlist06 = vm.vnlist06;
            }
            vm.searchByCode();
            
        }
        vm.familySelected = function () {
            if (vm.family && vm.family == '-') {
                vm.searchDto.family = null;
            }
            else {
                vm.searchDto.family = vm.family;
            }
            vm.searchByCode();
        }
       
     
     
        /** bussiness */
        
        vm.bsTableControl = {
            options: {
                data: vm.animals,
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
                        vm.selectedAnimals.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedAnimals = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedAnimals);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedAnimals.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedAnimals = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    vm.findBy();
                    // vm.getPageAnimal();
                }
            }
        };
        vm.bsTableControl1 = {
            options: {
                data: vm.animals,
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
                columns: service.getTableDefinition1(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selectedAnimals1.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedAnimals1 = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedAnimals1);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedAnimals1.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedAnimals1 = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    vm.findBy1();
                    // vm.getPageAnimal();
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
            // vm.bsTableControl.state.pageNumber = 1;
            vm.bsTableControl1.state.pageNumber = 1;
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
            service.getPageSearchAnimal(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.animals = data.content;
                vm.animals1 = data.content;
                vm.bsTableControl1.options.data = vm.animals1;
                vm.bsTableControl1.options.totalRows = data.totalElements;
            });
        }
        vm.findBy1 = function () {
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
            service.getPageSearchAnimal(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.animals1 = data.content;
                vm.bsTableControl1.options.data = vm.animals1;
                vm.bsTableControl1.options.totalRows = data.totalElements;
            });
        }


        vm.createAnimal = function () {
            vm.generateAnimalParent();
            vm.animal = { isNew: true, typeDto: null, originalDto: null, productTargetDto: null };
            vm.modalAnimal = modal.open({
                animation: true,
                templateUrl: 'animal_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'lg'
            });
        };

        $scope.editAnimal = function (id) {
            if (id) {
                vm.generateAnimalParent();
                service.getAnimal(id).then(function (data) {
                    vm.animal = data;
                    vm.animal.isNew = false;
                    vm.tempCode = vm.animal.code;
                    console.log(vm.animal);
                });
                vm.modalAnimal = modal.open({
                    animation: true,
                    templateUrl: 'animal_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'lg'
                });
            }
        }
        $scope.selectAnimal = function (id,type) {
            if (id) {
                vm.generateAnimalParent();
                service.getAnimal(id).then(function (data) {
                    vm.animal = data;
                    vm.animal.isNew = false;
                    vm.tempCode = vm.animal.code;
                    console.log(vm.animal);
                });
                    vm.modalAnimal = modal.open({
                        animation: true,
                        templateUrl: 'listanimal_info.html',
                        scope: $scope,
                        backdrop: 'static',
                        keyboard: false,
                        size: 'lg'
                    });
                
            }
        }

        $scope.deleteAnimal = function (id) {
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.saveAnimal = function () {
            if (validate() && vm.animal) {
                if (vm.animal.id) {
                    service.updateAnimal(vm.animal.id, vm.animal, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        vm.findBy();
                        vm.modalAnimal.close();
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                } else {
                    service.createAnimal(vm.animal, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        vm.findBy();
                        vm.modalAnimal.close();
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        vm.confirmDelete = function () {
            vm.pageIndex = 1;
            var ids = vm.selectedAnimals.map(function (element) {
                return element.id;
            });
            service.deleteListId(ids, function success(data) {
                toastr.info("Bạn đã xóa thành công các bản ghi đã chọn.", $translate.instant("toastr.info"));
                vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedAnimals = [];
            }, function failure(error) {
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                console.log(error);
            });
        }
        $scope.deleteAnimalRecord = function (id) {
            if (id) {
                vm.recordId = id;
                service.getAnimal(id).then(function (data) {
                    if (data && data != null)
                        vm.animalDelete = data;
                });
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
                service.deleteAnimal(vm.recordId).then(function (data) {
                    if (data != null && data.code == "-1") {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    }
                    else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }

                    //vm.getPageAnimal();
                    vm.findBy();
                    vm.modalConfirmDelete.close();
                    vm.selectedAnimals = [];
                });
                /*,function failure(error){
                     toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    vm.modalConfirmDelete.close(); 
                });*/
            }
        }

        vm.deleteAnimals = function () {
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        function validate() {
            // if (!vm.animal.code) {
            //     toastr.warning($translate.instant("animal.emptyCode"), $translate.instant("toastr.warning"));
            //     return false;
            // }
            if (!vm.animal.name) {
                toastr.warning($translate.instant("animal.emptyName"), $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.animal.scienceName) {
                toastr.warning($translate.instant("animal.emptyScienceName"), $translate.instant("toastr.warning"));
                return false;
            }
            if (vm.animal && (!vm.animal.animalClass || vm.animal.animalClass == null)) {
                toastr.warning("Lớp động vật không được để trống", "Cảnh báo");
                return false;
            }
            /*if(!vm.animal.description){
                toastr.warning("Bạn chưa nhập mô tả!");
                return;
            }
            if(!vm.animal.parent){
                toastr.warning($translate.instant("animal.emptyAnimalParent"), $translate.instant("toastr.warning"));
                return;
            }*/
            // if(!vm.animal.originalDto){
            // 	toastr.warning($translate.instant("animal.emptyOrg"), $translate.instant("toastr.warning"));
            // 	return false;
            // }

            // if(!vm.animal.typeDto){
            // 	toastr.warning($translate.instant("animal.emptyType"), $translate.instant("toastr.warning"));
            // 	return false;
            // }
            // if(vm.animal.animalProductTargets==null ||(vm.animal.animalProductTargets!=null && vm.animal.animalProductTargets.length==0)){
            // 	toastr.warning($translate.instant("animal.emptyProduction"), $translate.instant("toastr.warning"));
            // 	return false;
            // }
            // if (vm.animal.isNew == false && (vm.tempCode == null || vm.tempCode == "")) {
            //     toastr.warning($translate.instant("animal.emptyCode"), $translate.instant("toastr.warning"));
            //     return false;
            // }
            return true;
        }

        function checkDuplicateCode(code, type, action) { //type: 1 -> save; 2 -> edit;   action: 1 -> just check code; 2 -> save or edit
            service.checkDuplicateCode(code).then(function (data) {
                vm.viewCheckDuplicateCode = data;
                if (action == 1) {
                    if (type == 1) {
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                            // toastr.warning("Mã bị trùng");
                            toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                        }
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {
                            //toastr.success("Mã không bị trùng");
                            toastr.success($translate.instant("checkCode.codeDuplicateNot"), $translate.instant("toastr.info"));
                        }
                    }
                    if (type == 2) {
                        if (vm.animal.code.toLowerCase().trim() != code.toLowerCase().trim()) {
                            checkDuplicateCode(vm.animal.code, 1, 1);
                        } else {
                            // toastr.info("Mã chưa thay đổi");
                            toastr.info($translate.instant("checkCode.codeNotChange"), $translate.instant("toastr.info"));
                        }
                    }
                }
                if (action == 2) {
                    if (type == 1) {
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                            //toastr.warning("Mã bị trùng");
                            toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                        }
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {
                            vm.saveAnimal();
                        }
                    }
                    vm.saveAnimal();
                    // if (type == 2) {
                    //     if (vm.animal.code != null && vm.animal.code.toLowerCase().trim() != '') {
                    //         service.checkDuplicateCode(vm.animal.code).then(function (data) {
                    //             vm.viewCheckDuplicateCode = data;
                    //             if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                    //                 //toastr.warning("Mã bị trùng");
                    //                 toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                    //             }
                    //             if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {
                    //                 vm.animal.code = vm.tempCode.trim();
                    //                 vm.saveAnimal();
                    //             }
                    //         });
                    //     } else {
                    //         vm.saveAnimal();
                    //     }
                    // }
                }
                console.log(data);

            });
        }
        vm.checkDuplicateCode = function (type, action) {
            if (validate()) {
                checkDuplicateCode(vm.animal.code, type, action);
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
                    url: vm.baseUrl + 'file/importAnimal',
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


        vm.importAnimal = function () {
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

    }

})();
