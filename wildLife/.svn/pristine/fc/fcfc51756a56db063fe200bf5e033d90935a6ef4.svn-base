/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.FmsAdministrative').controller('FmsAdministrativeController', FmsAdministrativeController);

    FmsAdministrativeController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        'FmsAdministrativeService',
        'FmsRegionService',
        '$translate',
        'Upload'
    ];

    function FmsAdministrativeController($rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, service, regionService, $translate, Upload) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;

        vm.user = {};
        vm.users = [];
        vm.selectedUsers = [];
        vm.treeData = [];
        vm.roles = [];
        vm.groups = [];

        // FmsAdiministrative
        vm.fmsAdministratives = [];
        vm.fmsAdministrative = {};
        vm.modalInstance = {};

        vm.region = {};
        vm.parent = {};
        vm.regions = [];
        vm.parents = [];
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.baseUrl = settings.api.baseUrl + settings.api.apiPrefix;


        vm.getRegionAndParent = function() {

            /*service.getAll().then(function (data) {
                if (data && data.length > 0) {
                    vm.parents = data;
                } else {
                    vm.parents = [];
                }
            })*/

            regionService.getAll().then(function(data) {
                console.log()
                if (data && data.length > 0) {
                    vm.regions = data;
                } else {
                    vm.regions = [];
                }
            })

        }

        // vm.getRegionAndParent();

        // UI
        vm.modalInstance = null;

        // pagination
        vm.pageIndex = 0;
        vm.pageSize = 10;

        vm.changePass = false;


        vm.editFmsAdministrative = function(item) {
            vm.fmsAdministrative = item;
            console.log(item);
            if (vm.fmsAdministrative != null && vm.fmsAdministrative.id > 0) {
                service.getAdministrativeUnitById(vm.fmsAdministrative.id).then(function(data) {
                    console.log(data);

                    vm.fmsAdministrative = data;
                    // vm.parent = data.parentDto;
                    //vm.region = data.regionDto;
                    vm.fmsAdministrative.isNew = false;
                    vm.tempCode = vm.fmsAdministrative.code;
                    console.log(vm.fmsAdministrative);
                    vm.modalInstance = modal.open({
                        animation: true,
                        templateUrl: 'edit_modal.html',
                        scope: $scope,
                        backdrop: 'static',
                        keyboard: false,
                        size: 'lg'
                    });
                    vm.modalInstance.close();
                });


                for (let i = 0; i < vm.parents.length; i++) {
                    if (vm.parents[i].id == vm.fmsAdministrative.id) {
                        vm.parents.splice(i, 1);
                        return;
                    }
                }
                vm.getRegionAndParent();
            }


        }


        vm.deleteFmsAdministrative = function(item) {
            vm.id = item.id;
            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'delete_modal.html',
                backdrop: 'static',
                scope: $scope,
                size: 'md'
            });
        }



        //save
        vm.saveFmsAdministrative = function() {
            if (vm.fmsAdministrative) {

                // if (!vm.fmsAdministrative.level) {
                //     toastr.warning('Vui lòng nhập level!', 'Thông báo');
                //     return;
                // }
                //vm.fmsAdministrative.parentDto = vm.parent;
                //vm.fmsAdministrative.regionDto = vm.region;
                if (!vm.fmsAdministrative.id) {

                    service.createFmsAdministrative(vm.fmsAdministrative, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getListAdministratives();
                        vm.findBy();
                        //getTreeData(vm.pageIndex,vm.pageSize);
                        vm.modalInstance.close();

                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    })
                } else {
                    vm.fmsAdministrative.code = vm.tempCode;
                    service.updateFmsAdministrative(vm.fmsAdministrative.id, vm.fmsAdministrative, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getListAdministratives();
                        //vm.findBy();
                        //getTreeData(vm.pageIndex,vm.pageSize);


                        vm.modalInstance.close();
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    })
                }
                //console.log(getAll());

            }
            // Check for duplicate username & email

            vm.changePass = false;
            // console.log(getAll());
        };



        /**
         * Create a new Administrative
         */
        vm.newFmsAdministrative = function() {
            vm.fmsAdministrative = { isNew: true };

            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_modal.html',
                backdrop: 'static',
                scope: $scope,
                size: 'lg'
            });
            vm.getRegionAndParent();

        };




        /* vm.bsTableControl = {
            options: {
                data: vm.fmsAdministrative,
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
                        if (row.username && row.username != 'admin') {
                            vm.selectedfmsAdministrative.push(row);
                        } else {
                            bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                        }
                        // vm.selectedUsers.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        angular.forEach(rows, function (row) {
                            if (row.username && row.username != 'admin') {
                                vm.selectedfmsAdministrative.push(row);
                            } else {
                                bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                            }
                        });
                        // vm.selectedUsers = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedUsers);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedfmsAdministrative.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedfmsAdministrative = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;

                    //vm.getListAdmin=istratives();
                    //vm.getListAdministratives();
					vm.findBy();
                }
            }
        };*/

        vm.enterSearch = function() {
            // console.log(event.keyCode);
            if (event.keyCode == 13) { //Phím Enter

                vm.searchByCode();
            }
        };
        vm.searchByCode = function() {
            vm.pageIndex = 1;
            // vm.bsTableControl.state.pageNumber = 1;
            vm.findBy();
        };
        vm.findBy = function() {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchDto.nameOrCode = vm.textSearch;
            } else {
                vm.searchDto.nameOrCode = null;
            }
            service.getPageSearchFmsAdministrative(vm.searchDto, vm.pageIndex, vm.pageSize).then(function(data) {
                vm.totalItems = data.totalElements;
                vm.treeData = data.content;
            });
        }

        /**
         * Get a list of FmsAdiministrative
         */

        vm.getListAdministratives = function() {
            service.getListAdministratives(vm.fmsAdministrative.id, vm.pageIndex, vm.pageSize).then(function(data) {
                vm.fmsAdministratives = data.content;
                vm.bsTableControl.options.data = vm.fmsAdministratives;
                vm.bsTableControl.options.totalRows = data.totalElements;

            });
        };
        // vm.getListAdministratives();
        //console.log(getAll());

        vm.confirmDelete = function() {
            // alert(vm.id)
            service.confirmDelete(vm.id).then(function(data) {
                vm.fmsAdministrative = data;
                if (vm.fmsAdministrative != null && vm.fmsAdministrative.code == "-1") {
                    toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    vm.findBy();
                } else if (vm.fmsAdministrative != null && vm.fmsAdministrative.code == "-2") {
                    toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                } else {
                    toastr.warning($translate.instant("administrativeUnit.adminiChil") + vm.fmsAdministrative.name, $translate.instant("toastr.warning"));
                }

                //getTreeData(vm.pageIndex,vm.pageSize);
                //vm.getListAdministratives();
                vm.modalInstance.close();
            });
        }



        //getAll();



        // $scope.readyCB = function () {
        //     // getOrganizationTree();
        // }
        // $scope.selectNode = function (node, selected, event) {
        //     //console.log(selected.node.id);
        //     //console.log(selected.node.text);
        //     vm.getOrganizationDetailById(selected.node.id);
        // }

        //// Upload file
        $scope.MAX_FILE_SIZE = '5MB';
        $scope.f = null;
        $scope.errFile = null;

        $scope.uploadFiles = function(file, errFiles) {
            $scope.f = file;
            $scope.errFile = errFiles && errFiles[0];
        };

        vm.startUploadFile = function(file) {
            console.log(file);
            if (file) {
                file.upload = Upload.upload({
                    url: vm.baseUrl + 'file/importAdministrativeUnit',
                    data: { uploadfile: file }
                });

                file.upload.then(function(response) {
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


        vm.importFmsAdministrative = function() {
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'import_modal.html',
                scope: $scope,
                size: 'md'
            });


            $scope.f = null;
            $scope.errFile = null;

            modalInstance.result.then(function(confirm) {
                if (confirm == 'yes') {
                    vm.startUploadFile($scope.f);
                    console.log($scope.f);
                }
            }, function() {

                console.log("cancel");
            });
        }

        function validate() {
            console.log(vm.fmsAdministrative);
            if (!vm.fmsAdministrative.code) {
                toastr.warning($translate.instant("administrativeUnit.emptyCode"), $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.fmsAdministrative.name) {
                toastr.warning($translate.instant("administrativeUnit.emptyName"), $translate.instant("toastr.warning"));
                return false;
            }
            if (vm.fmsAdministrative.isNew == false && (vm.tempCode == null || vm.tempCode == "")) {
                toastr.warning($translate.instant("administrativeUnit.emptyCode"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }

        function checkDuplicateCode(code, type, action) { //type: 1 -> save; 2 -> edit;   action: 1 -> just check code; 2 -> save or edit
            service.checkDuplicateCode(code).then(function(data) {
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
                            toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                        }
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {

                            vm.saveFmsAdministrative();
                        }
                    }
                    if (type == 2) {
                        if (vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()) {
                            service.checkDuplicateCode(vm.tempCode).then(function(data) {
                                vm.viewCheckDuplicateCode = data;
                                if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                                    //toastr.warning("Mã bị trùng");
                                    toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                                }
                                if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {
                                    vm.fmsAdministrative.code = vm.tempCode.trim();

                                    vm.saveFmsAdministrative();
                                }
                            });
                        } else {
                            vm.fmsAdministrative.code = vm.tempCode.trim();

                            vm.saveFmsAdministrative();
                        }
                    }
                }
                console.log(data);

            });
        }
        vm.checkDuplicateCode = function(type, action) {
                if (validate()) {
                    checkDuplicateCode(vm.fmsAdministrative.code, type, action);
                }
            }
            // Get list tree
            // Start: Tree table
        vm.treeColumnDefinitions = [
            // {field: "timetables",displayName: "timetables",sortable: true,cellTemplate: "<div>{{row.entity.timetables[0].teacher.displayName}}</div>"},

            { field: "code", displayName: $translate.instant("administrativeUnit.code"), sortable: true, cellTemplate: "<div>{{row.branch[col.field]}}</div>" },
            { field: "level", displayName: $translate.instant("administrativeUnit.level"), sortable: true, cellTemplate: "<div>{{row.branch[col.field]}}</div>" },
            {
                field: "Action",
                displayName: $translate.instant("administrativeUnit.action"),
                cellTemplate: '<a ng-click="cellTemplateScope.click(row.branch,1)"><i class="icon-pencil margin-right-15"></i>{{"administrativeUnit.edit" | translate}}</a>' +
                    '<a ng-click="cellTemplateScope.click(row.branch,2)"><i class="fa fa-trash margin-right-15"></i>{{"administrativeUnit.delete" | translate}}</a>',

                cellTemplateScope: {
                    click: function(data, type) { // this works too: $scope.someMethod;
                        if (type == 1) {
                            if (data != null || angular.isDefined(data)) {
                                vm.editFmsAdministrative(data);
                                console.log('this is 1');
                            }
                        }

                        if (type == 2) {
                            if (data != null || angular.isDefined(data)) {
                                vm.deleteFmsAdministrative(data);
                                console.log('this is 2');
                            }
                        }
                    }
                }
            }
        ];

        $scope.myFunction = function(data) {
            alert(data.name);
        }
        vm.expandingProperty = { field: "name", displayName: $translate.instant("administrativeUnit.name"), sortable: true };

        $scope.my_tree_handler = function(branch) {
            console.log('you clicked on', branch)
        }

        vm.onSelectNode = function(node) {
            console.log('node gốc');
            console.log(node);
            getChildrenByParentId(node.id);

        };

        vm.onClickNode = function(node) {
            console.log('node lá');

            getChildrenByParentId(node.id);
        };

        $scope.pageChanged = function() {
            getTreeData(vm.pageIndex, vm.pageSize);
        };

        vm.treeData = [];
        getTreeData(vm.pageIndex, vm.pageSize);

        function getTreeData(pageIndex, pageSize) {
            service.getTreeData(pageIndex, pageSize).then(function(data) {
                vm.totalItems = data.totalElements;
                vm.treeData = data.content;

                console.log(vm.treeData);
            });
        }

        function getChildrenByParentId(parentId) {
            service.getChildrenByParentId(parentId).then(function(data) {
                vm.childs = data;
                console.log(data);
                if (vm.treeData != null && vm.treeData.length > 0) {
                    console.log("treedata");
                    console.log(vm.treeData);
                    for (var i = 0; i < vm.treeData.length; i++) {
                        if (vm.treeData[i].id == parentId) {
                            vm.treeData[i].children = vm.childs;
                            vm.treeData[i].expanded = true;

                        } else if (vm.treeData[i].parentDto == null && vm.treeData[i].expanded == false) {
                            vm.treeData[i].children = [];
                        }
                    }

                }

            });
        }
        //start---popup administrativeUnit parent
        ///--------Popup administrativeUnit--------//
        vm.subModalInstance = {};
        vm.pageIndexSub = 1;
        vm.pageSizeSub = 10;
        vm.selectedSubjects = [];
        vm.subjectSelected = {};
        vm.showPopupAdministrativeParent = function() {
            vm.subModalInstance = modal.open({
                animation: true,
                templateUrl: 'choose_auparent_modal.html',
                scope: $scope,
                size: 'md'
            });
            vm.textSearch = null;
            vm.selectedSubjects = [];
            vm.subjectSelected = {};
            if (vm.fmsAdministrative.id) {
                getListAdministratives(vm.fmsAdministrative.id, vm.pageIndexSub, vm.pageSizeSub);
            }

            vm.subModalInstance.result.then(function(confirm) {
                if (confirm == 'yes') {
                    // if(checkagree){
                    //     vm.subjectSelected=vm.selectedSubjects[0];
                    //     closeModal();
                    // }
                }
            }, function() {

                console.log("cancel");
            });
        }

        var closeModal = function() {
            vm.subModalInstance.close();

        };

        function checkAgree() {
            console.log("checkAgree");
            console.log(vm.selectedSubjects);
            if (angular.isUndefined(vm.selectedSubjects) || vm.selectedSubjects.length == 0) {
                toastr.warning("Bạn chưa chọn ĐƠN VỊ", "Cảnh báo");
                return false;
            }
            return true;
        }
        vm.agree = function() {
            if (checkAgree()) {
                vm.subjectSelected = vm.selectedSubjects[0];
                if (vm.fmsAdministrative == null) {
                    vm.fmsAdministrative = {};
                }
                vm.fmsAdministrative.parentDto = vm.subjectSelected;

                closeModal();
            }
        }

        function getListAdministratives(id, pageIndex, pageSize) {

            service.getListAdministratives(id, pageIndex, pageSize).then(function(data) {
                vm.listAuParents = data.content;
                if (vm.listAuParents.length <= 0 && data.totalElements != 0) {
                    $state.reload();
                }
                vm.bsTableControl.options.data = vm.listAuParents;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }



        vm.bsTableControl = {
            options: {
                data: vm.listAuParents,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: true,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeSub,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinition(),
                onCheck: function(row, $element) {
                    if (vm.selectedSubjects != null) {
                        vm.selectedSubjects = [];
                    }
                    $scope.$apply(function() {
                        vm.selectedSubjects.push(row);
                    });
                },
                // onCheckAll: function (rows) {
                //     $scope.$apply(function () {
                //         vm.selectedSubjects = rows;
                //     });
                // },
                onUncheck: function(row, $element) {
                    // var index = utils.indexOf(row, vm.selectedSubjects);
                    // if (index >= 0) {
                    //     $scope.$apply(function () {
                    //         vm.selectedSubjects.splice(index, 1);
                    //     });
                    // }
                    if (vm.selectedSubjects != null) {
                        vm.selectedSubjects = [];
                    }
                },
                // onUncheckAll: function (rows) {
                //     $scope.$apply(function () {
                //         vm.selectedSubjects = [];
                //     });
                // },
                onPageChange: function(index, pageSize) {
                    vm.pageSizeSub = pageSize;
                    vm.pageIndexSub = index;
                    if (vm.textSearch != '' && vm.textSearch != null) {
                        if (vm.fmsAdministrative.id != null) {
                            getListAdministrativeByCodeOrName(vm.fmsAdministrative.id, vm.textSearch, vm.pageIndexSub, vm.pageSizeSub);
                        }

                    }
                    if (vm.textSearch == '' || vm.textSearch == null) {
                        if (vm.fmsAdministrative.id != null) {
                            getListAdministratives(vm.fmsAdministrative.id, vm.pageIndexSub, vm.pageSizeSub);
                        }

                    }
                }
            }
        };

        function getListAdministrativeByCodeOrName(Id, subjectName, pageIndex, pageSize) {
            service.getListAdministrativeByCodeOrName(Id, subjectName, pageIndex, pageSize).then(function(data) {
                vm.listAuParents = data.content;
                vm.bsTableControl.options.data = vm.listAuParents;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }

        vm.searchByName = function() {
            if (vm.fmsAdministrative.id) {
                vm.pageIndexSub = 1;
                vm.bsTableControl.state.pageNumber = 1;
                vm.textSearch = String(vm.textSearch);
                if (vm.textSearch != '' && vm.textSearch != null) {
                    console.log(vm.pageIndexSub, vm.pageSizeSub);
                    getListAdministrativeByCodeOrName(vm.fmsAdministrative.id, vm.textSearch, vm.pageIndexSub, vm.pageSizeSub);
                }
                if (vm.textSearch == '' || vm.textSearch == null) {
                    console.log(vm.pageIndexSub, vm.pageSizeSub);
                    getListAdministratives(vm.fmsAdministrative.id, vm.pageIndexSub, vm.pageSizeSub);
                }
            }
        }

        vm.enterSearchCode = function() {
            console.log(event.keyCode);
            if (event.keyCode == 13) { //Phím Enter
                vm.searchByName();
            }
        }
        vm.remove = function() {
                vm.fmsAdministrative.parentDto = null;
            }
            //end--------popup administrativeUnit parent

    }
})();