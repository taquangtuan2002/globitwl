/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.AnimalRequired').controller('AnimalRequiredController', AnimalRequiredController);

    AnimalRequiredController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'AnimalRequiredService',
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

    function AnimalRequiredController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, animalTypeService, originalService, productTargetService, $translate, Upload) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
        vm.currentId = $state.params.id ? $state.params.id : null;
        vm.listType = [
            {id: 1, name: "Lớp"},
            {id: 2, name: "Bộ"},
            {id: 3, name: "Họ"}
        ]
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.animalRequireds = []
        vm.modalAnimal = {}
        vm.animalRequiredStatusCurrent = null;
        vm.animal = { isNew: false, typeDto: null, originalDto: null, productTargetDto: null };
        vm.modalConfirmDelete = {};
        vm.selectedAnimals = [];
        vm.baseUrl = settings.api.baseUrl + settings.api.apiPrefix;
		vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
        vm.isSdahView = false;//cấp tỉnh nhưng chỉ được xem
        vm.isApprovedSpecies=false;

        vm.originals = [];

        vm.reproductionForms = [];
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
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_approved_species') {
                        vm.isApprovedSpecies = true;
                    }
                    
                }
            } else {
                vm.isRole = false;
                vm.isFamer = false;
                vm.isSdah = false;
                vm.isDistrict = false;
                vm.isWard = false;
                vm.isSdahView = false;
                vm.isApprovedSpecies = false;
            }
            
            blockUI.stop();
        });
        for(let key in settings.ReproductionForm){
            vm.reproductionForms.push(settings.ReproductionForm[key]);
        }
        
        vm.listRequiredStatus = [
            settings.AnimalRequiredStatus.PENDING,
            settings.AnimalRequiredStatus.DONE,
            settings.AnimalRequiredStatus.REJECTED,
            settings.AnimalRequiredStatus.NEW
        ];

        vm.animalTypes = [];
        vm.searchDto = {
        };
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.getPageAnimal = function () {
            if(vm.isApprovedSpecies == true) {
                vm.searchDto.animalRequiredStatus = vm.animalRequiredStatusCurrent?vm.animalRequiredStatusCurrent: 1;
            }
            if(vm.isApprovedSpecies == false) {
                vm.searchDto.animalRequiredStatus = vm.animalRequiredStatusCurrent?vm.animalRequiredStatusCurrent: null;
            }
            service.getPageSearchAnimalRequired(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.animalRequireds = data.content;
                vm.bsTableControl.options.data = vm.animalRequireds;
                vm.bsTableControl.options.totalRows = data.totalElements;
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
        /** generate */
        vm.getPageAnimal();
        vm.generateAnimalType();
        vm.generateOriginal();
        vm.generateProductTarget();
        // vm.generateAnimalParent();

        /** get data */
        vm.listAnimalClassSci = [];
        vm.listAnimalFamilySci = [];
        vm.listAnimalOrdoSci = [];
        vm.getListAnimalClass = function () {
            let dto = {};
            dto.pageIndex = 1;
            dto.pageSize = 100000000;
            dto.type = 1;
            service.searchByPageBiologicalClass(dto).then(function (data) {
                vm.listAnimalClassSci = [];
                vm.listAnimalFamilySci = [];
                vm.animalClassSci = null;
                vm.ordoSci = null;
                vm.familySci = null;
                if(data) {
                    vm.listAnimalClassSci = data.content;
                }
            });
            vm.getListAnimalOrdo();
        }

        vm.getListAnimalOrdo = function (callSearchFunction) {
            let dto = {};
            dto.pageIndex = 1;
            dto.pageSize = 100000000;
            dto.type = 2;
            dto.animalClassSci = vm.animal.animalClassSci ? vm.animal.animalClassSci : vm.searchDto.animalClassSci;
            service.searchByPageBiologicalClass(dto).then(function (data) {
                vm.listAnimalOrdoSci = [];
                vm.listAnimalFamilySci = [];
                vm.ordoSci = null;
                vm.familySci = null;
                vm.animal.ordoSci = null;
                vm.animal.familySci = null;
                if(data) {
                    vm.listAnimalOrdoSci = data.content;
                }

                vm.getListAnimalFamily(true);
            });
        }

        vm.getListAnimalFamily = function (callSearchFunction) {
            let dto = {};
            dto.pageIndex = 1;
            dto.pageSize = 100000000;
            dto.type = 3;
            // dto.animalClassSci = vm.animal.animalClassSci ? vm.animal.animalClassSci : vm.searchDto.animalClassSci;
            dto.ordoSci = vm.animal.ordoSci ? vm.animal.ordoSci : vm.searchDto.ordoSci;
            if(dto.ordoSci == null) {
                dto.ordoSci = "-----";
            }
            service.searchByPageBiologicalClass(dto).then(function (data) {
                vm.listAnimalFamilySci = [];
                vm.familySci = null;
                if(data) {
                    vm.listAnimalFamilySci = data.content;
                }

                if (callSearchFunction) {
                    vm.searchByCode();
                }
            });
        }

        vm.load=function(){
            if(vm.currentId){
                $scope.editAnimal(vm.currentId);
            }
        }

        vm.asyncFunction = async () => {
            await vm.getListAnimalClass();
            await  vm.load();
        } 
        vm.asyncFunction();

        // vm.getListAnimalOrdo();
        // vm.getListAnimalFamily();
        vm.getListCites = function () {
            service.getListCites().then(function (data) {
                vm.listcites = [];
                data.forEach(item => {
                    var cites;
                    if (item && item.trim().length > 0) {
                        cites = item;
                        vm.listcites.push({ name: cites });
                    }
                    
                });
            });
        }
        vm.getVnLists = function () {
            service.getVnLists().then(function (data) {
                vm.vnLists = [];
                data.forEach(item => {
                    var vnList = '-';
                    if (item && item.trim().length > 0) {
                        vnList = item;
                        vm.vnLists.push({ name: vnList });
                    }
                    
                });
            });
        }
        vm.getVnList06s = function () {
            service.getVnList06s().then(function (data) {
                vm.vnList06s = [];
                data.forEach(item => {
                    var vnList06 ;
                    if (item && item.trim().length > 0) {
                        vnList06 = item;
                        vm.vnList06s.push({ name: vnList06 });
                    }
                    
                });
            });
        }
        vm.getListCites();
        vm.getVnLists();
        vm.getVnList06s();

        vm.animalClassSelected = function () {
            if (vm.animalClassSci && vm.animalClassSci == '-') {
                vm.searchDto.animalClassCSci = null;
                vm.searchDto.ordoSci = null;
                vm.searchDto.familySci = null;
            }
            else{
                vm.searchDto.animalClassSci = vm.animalClassSci;
                vm.searchDto.ordoSci = null;
                vm.searchDto.familySci = null;
            }
            vm.getListAnimalOrdo(true);
        }

        vm.animalOrdoSelected = function () {
            if (vm.ordoSci && vm.ordoSci == '-') {
                vm.searchDto.ordoSci = null;
                vm.searchDto.familySci = null;
            }
            else{
                vm.searchDto.ordoSci = vm.ordoSci;
                vm.searchDto.familySci = null;
            }
            vm.getListAnimalFamily(true);
        }

        vm.familySelected = function () {
            if (vm.familySci && vm.familySci == '-') {
                vm.searchDto.familySci = null;
            }
            else{
                vm.searchDto.familySci = vm.familySci;
            }
            vm.searchByCode();
        }

        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.animalRequireds,
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
            service.getPageSearchAnimalRequired(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.animalRequireds = data.content;
                vm.bsTableControl.options.data = vm.animalRequireds;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }


        vm.createAnimal = function () {
            // vm.generateAnimalParent();
            vm.aniGroup1=false;
            vm.aniGroup2=false;
            vm.aniGroup3=false;
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
        vm.inforUserRequired="";
        $scope.editAnimal = function (id) {
            if (id) {
                // vm.generateAnimalParent();
                service.getAnimalRequiredById(id).then(function (data) {
                    vm.animal = data;
                    vm.animal.isNew = false;
                    vm.tempCode = vm.animal.code;
                    vm.inforUserRequired="";
                    if(vm.animal.user!=null){
                        if(vm.animal.user.person!=null  && vm.animal.user.person.displayName!=null ){
                            vm.inforUserRequired+=vm.animal.user.person.displayName+",";
                        }
                        if(vm.animal.user.person!=null && vm.animal.user.person.phoneNumber!=null){
                            vm.inforUserRequired+=vm.animal.user.person.phoneNumber+",";
                        }
                        if(vm.animal.user.email!=null){
                            vm.inforUserRequired+=vm.animal.user.email;
                        }
                    }
                    vm.aniGroup1=false;
                    vm.aniGroup2=false;
                    vm.aniGroup3=false;
                    if(vm.animal.aniGroup==="Động vật rừng thông thường"){
                        vm.onSelectaniGroup1();
                    }
                    if(vm.animal.aniGroup==="Động vật rừng Nguy cấp quý hiếm"){
                        vm.onSelectaniGroup2();
                    }
                    if(vm.animal.aniGroup==="Động vật khác"){
                        vm.onSelectaniGroup3();
        
                    }
                }).then(() => { 
                    console.log(vm.animal.familySci)
                    vm.modalAnimal = modal.open({
                        animation: true,
                        templateUrl: 'animal_info.html',
                        scope: $scope,
                        backdrop: 'static',
                        keyboard: false,
                        size: 'lg'
                    });
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
            if (vm.animal) {
                if(vm.animal.isNew == false){
                    vm.animal.code = vm.tempCode;
                }
                service.saveOrUpdateAnimalRequired(vm.animal).then(function success(data) {
                    toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                    //vm.getPageAnimal();
                    vm.aniGroup1=false;
                    vm.aniGroup2=false;
                    vm.aniGroup3=false;
                    vm.findBy();
                    vm.modalAnimal.close();
                }).catch(function failure(error) {
                    toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    console.log(error);
                });
            }
        }

        vm.confirmDelete = function () {
            vm.pageIndex = 1;
            var ids = vm.selectedAnimals.map(function (element) {
                return element.id;
            });
            service.deleteListId(ids, function success(data) {
                //vm.getPageAnimal();
                vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedAnimals = [];
            }, function failure(error) {
                console.log(error);
            });
        }
        $scope.deleteAnimalRecord = function (id) {
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

        // vm.deleteAnimals = function () {
        //     vm.modalConfirmDelete = modal.open({
        //         animation: true,
        //         templateUrl: 'confirm_delete.html',
        //         scope: $scope,
        //         backdrop: 'static',
        //         keyboard: false,
        //         size: 'md'
        //     });
        // }

        function validate() {
            console.log(vm.animal);
            if (!vm.animal.name) {
                toastr.warning($translate.instant("animal.emptyName"), $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.animal.scienceName) {
                toastr.warning($translate.instant("animal.emptyScienceName"), $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.animal.animalClassSci) {
                toastr.warning("Lớp động vật không được để trống", $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.animal.ordoSci) {
                toastr.warning("Bộ động vật không được để trống", $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.animal.familySci) {
                toastr.warning("Họ động vật không được để trống", $translate.instant("toastr.warning"));
                return false;
            }
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
                        if (vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()) {
                            checkDuplicateCode(vm.tempCode, 1, 1);
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
                            if(vm.animal.animalRequiredStatus == settings.AnimalRequiredStatus.PENDING.value){
                                toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                            }
                        }
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {

                            vm.saveAnimal();
                        }
                    }
                    if (type == 2) {
                        if (vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()) {
                            service.checkDuplicateCode(vm.tempCode).then(function (data) {
                                vm.viewCheckDuplicateCode = data;
                                if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                                    //toastr.warning("Mã bị trùng");
                                    toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                                }
                                if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {
                                    vm.animal.code = vm.tempCode.trim();

                                    vm.saveAnimal();
                                }
                            });
                        } else {
                            vm.animal.code = vm.tempCode.trim();

                            vm.saveAnimal();
                        }
                    }
                }
                console.log(data);

            });
        }
        vm.sendAnimalRequired=function(){
            if (validate()) {
                if (vm.animal) {
                    if(vm.animal.isNew == false){
                        vm.animal.code = vm.tempCode;
                    }
                    service.saveOrUpdateAnimalRequired(vm.animal).then(function success(data) {
                        //vm.getPageAnimal();
                        vm.aniGroup1=false;
                        vm.aniGroup2=false;
                        vm.aniGroup3=false;
                        $scope.sendEmail(data.id);
                        vm.findBy();
                        vm.modalAnimal.close();
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                    }).catch(function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }
        vm.checkDuplicateCode = function (type, action) {
            if (validate()) {
                vm.saveAnimal();
                // if(vm.animal.isNew){
                //     checkDuplicateCode(vm.animal.code, type, action);
                // }else{
                //     checkDuplicateCode(vm.tempCode, type, action);
                // }
                // if(vm.tempCode == null || vm.tempCode == ""){
                // }else{
                //     checkDuplicateCode(vm.animal.code, type, action);
                // }
            }
            // vm.saveAnimal();
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
        vm.acceptAnimalRequired=function(animalRequiredId){
            if(animalRequiredId){
                service.animalRequiredApplyToAnimal(vm.animal).then(function(){
                    vm.modalAnimal.close();
                    vm.findBy();
                });
            }
            
        }
        vm.rejectAnimalRequired=function(animalRequiredId){
            if(animalRequiredId){
                service.changeAnimalRequiredStatus({id: animalRequiredId, animalRequiredStatus: settings.AnimalRequiredStatus.REJECTED.value, feedBack: vm.animal.feedBack}).then(function(){
                    vm.modalAnimal.close();
                    vm.findBy();
                });
            }
            
        }

        $scope.changeStatus = function(animalRequiredId){
            if(animalRequiredId && animalRequiredId != null){
                service.getAnimalRequiredById(animalRequiredId).then(function (data) {
                    if(data && data != null){
                        vm.animalChangeStatus = data;
                    }
                });
            }
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'change_status_modal.html',
                scope: $scope,
                backdrop: 'static',
                size: 'md'
            });

            modalInstance.result.then(function (confirm) {
                if (confirm == 'reject') {
                    // service.changeAnimalRequiredStatus({id: animalRequiredId, animalRequiredStatus: settings.AnimalRequiredStatus.REJECTED.value}).then(function(){
                    //     vm.findBy();
                    // });
                    // vm.findBy();
                }else if(confirm == 'done'){
                    service.animalRequiredApplyToAnimal({id: animalRequiredId}).then(function(){
                        vm.findBy();
                    });
                }
            }, function () {

                console.log("cancel");
            });
        }

        $scope.changeStatusReject = function(animalRequiredId){
            if(animalRequiredId && animalRequiredId != null){
                service.getAnimalRequiredById(animalRequiredId).then(function (data) {
                    if(data && data != null){
                        vm.animalChangeStatus = data;
                    }
                });
            }
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'change_status_modal_reject.html',
                scope: $scope,
                backdrop: 'static',
                size: 'md'
            });

            modalInstance.result.then(function (confirm) {
                if (confirm == 'reject') {
                    // service.changeAnimalRequiredStatus({id: animalRequiredId, animalRequiredStatus: settings.AnimalRequiredStatus.REJECTED.value, feedBack: vm.animalChangeStatus.feedBack}).then(function(){
                    //     vm.findBy();
                    // });
                    // vm.findBy();
                }else if(confirm == 'done'){
                    service.changeAnimalRequiredStatus({id: animalRequiredId, animalRequiredStatus: settings.AnimalRequiredStatus.REJECTED.value, feedBack: vm.animalChangeStatus.feedBack}).then(function(){
                        vm.findBy();
                    });
                }
            }, function () {

                console.log("cancel");
            });
        }

        $scope.sendEmail = function(animalRequiredId){
            if(animalRequiredId && animalRequiredId != null){
                service.getAnimalRequiredById(animalRequiredId).then(function (data) {
                    if(data && data != null){
                        vm.animalChangeStatus = data;
                        service.changeAnimalRequiredStatus({id: animalRequiredId, animalRequiredStatus: settings.AnimalRequiredStatus.PENDING.value}).then(function(){
                            vm.findBy();
                        });
                    }
                });
            }
            
        }

        $scope.viewAnimal = function(id){
            service.getAnimalRequiredById(id).then(function (data) {
                vm.animal = data;
                vm.tempCode = vm.animal.code;
                console.log(vm.animal);
            });
            vm.modalAnimal = modal.open({
                animation: true,
                templateUrl: 'animal_required_view.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.onSelectAnimalRequiredStatus = function(){
            vm.findBy();
        }

        vm.aniGroup1=false;
        vm.aniGroup2=false;
        vm.aniGroup3=false;
        
        vm.onSelectaniGroup1=function(){
            vm.aniGroup1=!vm.aniGroup1;
            vm.aniGroup2=false;
            vm.aniGroup3=false;
            if(vm.aniGroup1==true){
                vm.animal.aniGroup="Động vật rừng thông thường";
                vm.animal.cites=null;
                vm.animal.animalGroup=null;
                vm.animal.vnlist06=null;
            }else{
                vm.animal.aniGroup="";
                vm.animal.cites=null;
                vm.animal.animalGroup=null;
                vm.animal.vnlist06=null;
            }
            
        }
        vm.onSelectaniGroup2=function(){
            vm.aniGroup1=false;
            vm.aniGroup2=!vm.aniGroup2;
            vm.aniGroup3=false;
            if(vm.aniGroup2==true){
                vm.animal.aniGroup="Động vật rừng Nguy cấp quý hiếm";
            }else{
                vm.animal.aniGroup="";
                vm.animal.cites=null;
                vm.animal.animalGroup=null;
                vm.animal.vnlist06=null;
            }
            
        }
        vm.onSelectaniGroup3=function(){
            vm.aniGroup1=false;
            vm.aniGroup2=false;
            vm.aniGroup3=!vm.aniGroup3;
            if(vm.aniGroup3==true){
                vm.animal.aniGroup="Động vật khác";
                vm.animal.cites=null;
                vm.animal.animalGroup=null;
                vm.animal.vnlist06=null;
            }else{
                vm.animal.aniGroup="";
                vm.animal.cites=null;
                vm.animal.animalGroup=null;
                vm.animal.vnlist06=null;
            }
            
        }
        vm.listAnimalGroup=[];
        vm.getAnimalGroup = function () {
            service.getListAnimalGroup().then(function (data) {
                vm.listAnimalGroup = [];
                data.forEach((item) => {
                    var animalGroup;
                    if (item && item.trim().length > 0) {
                        animalGroup = item;
                        vm.listAnimalGroup.push({ name: animalGroup });
                    }
                });
            });
        };
        vm.getAnimalGroup();

       

    }

})();
