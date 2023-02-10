
(function () {
    'use strict';

    angular.module('Education.Calendar').controller('CalendarController', CalendarController);

    CalendarController.$inject = [
    	'$http',
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        'focus',
        'Upload',
        'FileSaver',
        'Blob',
        'CalendarService'
    ];

    function CalendarController($http,$rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, focus,Uploader,FileSaver,Blob, service) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });
        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;
        vm.isViewCalendar=true;
        vm.eventDates = [];
        vm.eventDatesView = [];
        vm.selectedStaffs = [];
        vm.selectedDepartments = [];
        vm.selectedChairPerson = {};
        vm.selectedRoom = {};
        vm.event = {};
        vm.eventAttachments = [];
        vm.isNew = false;
        vm.dateStart = new Date();//Lấy ngày hiện thời
        vm.listDepartment = [];
        vm.listPerson = [];
        vm.listChairPerson = [];
        vm.startTime = null;
        vm.endTime = null;
        vm.isAdmin = true;
        $scope.toDay = new Date();
        vm.isfindByCode = false;
        $scope.search = '';
        vm.staffSearchDto = {};
        vm.departmentSearchDto = {};
        vm.listRoom = [];
        vm.RoomSearch = '';
        vm.isApprove=null;
        vm.chairPersonSearch = {};
        vm.priorities = [];
        
        
        //permission
        vm.showMenuRegister = false;
        vm.showButtonApprove = false;
        vm.showButtonPublish = false;
        vm.showButtonEditEvent = false;
        

        vm.hasApproverPermission = false;
        vm.hasPublisherPermission = false;
        vm.hasEditorPermission = false;
        
        vm.hasAdminPermission = false;
        vm.calendarPermission ={};
        
        function getPermission(){
        	service.getPermission().then(function (data) {
        		if(data!=null){
//        			vm.hasCalendarAdminPermission = data.hasCalendarAdminPermission;
//        			vm.hasCalendarApproverPermission = data.hasCalendarApproverPermission;
//        			vm.hasCalendarEditorPermission = data.hasCalendarEditorPermission;
//        			vm.hasCalendarPublisherPermission=data.hasCalendarPublisherPermission;
        			vm.calendarPermission = data;
        			vm.showMenuRegister = vm.calendarPermission.hasCalendarEditorPermission || vm.calendarPermission.hasCalendarAdminPermission;
        			vm.showMenuRegister = vm.showMenuRegister || vm.calendarPermission.hasCalendarPublisherPermission;
        			vm.showMenuRegister = vm.showMenuRegister || vm.calendarPermission.hasCalendarApproverPermission;
        			
        			if (vm.calendarPermission.hasCalendarApproverPermission || vm.calendarPermission.hasCalendarAdminPermission) {
        				vm.showButtonApprove = true;
					}
        			
        			if (vm.calendarPermission.hasCalendarPublisherPermission || vm.calendarPermission.hasCalendarAdminPermission) {
        				vm.showButtonPublish = true;
					}

        			vm.showButtonEditEvent = vm.calendarPermission.hasCalendarEditorPermission || vm.calendarPermission.hasCalendarAdminPermission;
        			vm.showButtonEditEvent = vm.showButtonEditEvent || vm.calendarPermission.hasCalendarApproverPermission || vm.calendarPermission.hasCalendarPublisherPermission;
        			
        			vm.hasAdminPermission = vm.calendarPermission.hasCalendarAdminPermission;
        				
        			console.log(vm.calendarPermission);
        		}
            });
        	vm.showMenuRegister = false;
        }
        
        getPermission();
        
        vm.checkPermissionByStatus = function(status) {
			if (status != null) {
				if (status == 1 || status == 0) { // quyền Approver có quyền sửa
					if (vm.calendarPermission != null && vm.calendarPermission.hasCalendarApproverPermission != null && vm.calendarPermission.hasCalendarApproverPermission == true) {
						return true;
					}
					else {
						return false;
					}
				}
				else if (status <= 2) { //quyền Publish có quyền sửa
					if (vm.calendarPermission != null && vm.calendarPermission.hasCalendarPublisherPermission != null && vm.calendarPermission.hasCalendarPublisherPermission == true) {
						return true;
					}
					else {
						return false;
					}
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}

        vm.deleteListsAttendees = null;
        vm.deleteListdAttendees = null;
        
        vm.eventScope = 0;//Mặc định sẽ là lịch phòng ban
        
        vm.eventPermission = 3;//0 = quyền đăng ký lịch cá nhân/phòng ban, 1 = có quyền đăng ký lịch đơn vị, 2 = có quyền phê duyệt lịch, 3 =  có quyền xuất bản lịch
        
        // UI
        vm.modalInstance = null;

        vm.statuses = [
            { id: 0, name: 'Mới đăng kí' },
            { id: 1, name: 'Đã được phê duyệt' },
            { id: 2, name: 'Đã xuất bản' }
            /*{ id: 3, name: 'Đã kết thúc' },
            { id: 4, name: 'Đã hủy' }*/
        ];

        vm.eventTypes = [
            { id: 0, name: 'Lịch làm việc' },
            { id: 1, name: 'Thông báo' }
        ];

        vm.status = null;
        vm.eventType = null;
        
        // pagination
        vm.pageIndex = 1;
        vm.pageSize = 5;
        vm.DepartmentSearch = {
        };
        vm.staffSearch = {};
        
        vm.treeData = [];
        $scope.treeInstance = null;
        $scope.treeConfig = {
            core: {
                error: function (error) {
                    $log.error('treeCtrl: error from js tree - ' + angular.toJson(error));
                },
                check_callback: true
            },
            plugins: ['types', 'state', 'search']
        };

        
        vm.searchByDtoAttendee = function () {
            vm.pageIndex = 1;
            if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                vm.bsTableControl.state.pageNumber = 1;
			}
            service.searchDto(vm.staffSearchDto,vm.pageIndex,vm.pageSize).then(function (data) {
                vm.listPerson = data.content;
                vm.bsTableControl.options.data = vm.listPerson;
                vm.bsTableControl.options.totalRows = data.totalElements;
                /*
                vm.listChairPerson = data.content;
                vm.bsTableControlChairPerson.options.data = vm.listChairPerson;
                vm.bsTableControlChairPerson.options.totalRows = data.totalElements;*/
            });
        };
        
        vm.searchChairman = function () {
            vm.pageIndex = 1;
            if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                vm.bsTableControl.state.pageNumber = 1;
			}
            service.searchDto(vm.chairPersonSearch,vm.pageIndex,vm.pageSize).then(function (data) {
                vm.listPerson = data.content;
                vm.bsTableControlChairPerson.options.data = vm.listPerson;
                vm.bsTableControlChairPerson.options.totalRows = data.totalElements;
            });
        };

        $scope.readyCB = function () {
        }
        
        $scope.selectNode = function (node, selected, event) {
            if(vm.staffSearchDto.department == null){
                vm.staffSearchDto.department = {};
            }
            vm.staffSearchDto.department.id = selected.node.id;
            if (vm.staffSearchDto == null) {
            	vm.staffSearchDto = {department:{}}
			}
            vm.staffSearchDto.department.id = selected.node.id;
            
            vm.searchByDtoAttendee();
        }
        $scope.selectChairmanNode = function (node, selected, event) {
            if(vm.chairPersonSearch.department == null){
                vm.chairPersonSearch.department = {};
            }
            vm.chairPersonSearch.department.id = selected.node.id;
            if (vm.chairPersonSearch == null) {
            	vm.chairPersonSearch = {department:{}}
			}
            vm.chairPersonSearch.department.id = selected.node.id;
            
            vm.searchChairman();
        }
        $scope.selectNodeDepartment = function (node, selected, event) {
        	vm.DepartmentSearch.parentId=selected.node.id;
        	vm.searchdepartment();
        }

        $scope.applySearch = function (){
            var to = false;
            if(to) {
                clearTimeout(to);
            }
            to = setTimeout(function () {
                if($scope.treeInstance) {
                    $scope.treeInstance.jstree(true).search($scope.search);
                }
            }, 250);
        };

        function getDepartment() {
            service.getDepartmentTree().then(function (data) {
                vm.treeData = data;
                $scope.treeConfig.version++;
            });
        }

        getDepartment();
        
        //search staff
        function getStaffByCode(textSearch, pageIndex, pageSize) {
            service.getStaffByCode(textSearch, pageIndex, pageSize).then(function (data) {
                vm.listPerson = data.content;
                vm.bsTableControl.options.data = vm.listPerson;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }

        
        vm.textSearch = '';
        vm.searchByCode = function () {
            vm.textSearch = String(vm.textSearch).trim();
            if (vm.textSearch != '') {
                getStaffByCode(vm.textSearch, vm.pageIndex, vm.pageSize);
            }
            if (vm.textSearch == '') {
                vm.getStaffs();
            }
        }

        vm.isStatus =function(id){
            if (event.status==id){
                return true;
            }
            else return false;
        }
        
        
        /* TINYMCE */
        vm.tinymceOptions = {
            height: 130,
            theme: 'modern',
            plugins: [
                'lists fullscreen' //autoresize
            ],
            toolbar1: 'bold underline italic | removeformat | bullist numlist outdent indent | fullscreen',
            content_css: [
                '//fonts.googleapis.com/css?family=Poppins:300,400,500,600,700',
                '/assets/css/tinymce_content.css'
            ],
            autoresize_bottom_margin: 0,
            statusbar: false,
            menubar: false
        };


        $scope.inlineOptions = {
            customClass: getDayClass,
            minDate: new Date(),
            showWeeks: false
        };

        $scope.dateOptions = {
            formatYear: 'yy',
            timeFormat: 'hh mm',
            maxDate: new Date(2020, 5, 22),
            minDate: new Date(),
            startingDay: 1
        };


        $scope.toggleMin = function () {
            $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
            $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
        };

        $scope.toggleMin();

        $scope.open1 = function () {
            $scope.popup1.opened = true;
        };

        $scope.open2 = function () {
            $scope.popup2.opened = true;
        };

        $scope.format = 'dd/MM/yyyy';
        $scope.altInputFormats = 'dd/MM/yyyy';

        $scope.popup1 = {
            opened: false
        };

        $scope.popup2 = {
            opened: false
        };
        $scope.changeWeek = function () {
        	console.log($scope.toDay);
            vm.dateStart = $scope.toDay;
            vm.getAllEnventDate();
            vm.getAllEnventDateView();
            vm.event = {};
        }
        function getDayClass(data) {
            var date = data.date,
              mode = data.mode;
            if (mode === 'day') {
                var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

                for (var i = 0; i < $scope.events.length; i++) {
                    var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                    if (dayToCheck === currentDay) {
                        return $scope.events[i].status;
                    }
                }
            }

            return '';
        }

        vm.isToDay = function (dateInput) {
            if (dateInput != null) {
                var date = new Date(dateInput);
                var dateNow = new Date();
                if (date.getDate() == dateNow.getDate() && date.getMonth() == dateNow.getMonth() && date.getFullYear() == dateNow.getFullYear()) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }

        vm.showDate = function (startDateInput, endDateInput) {
            if (startDateInput != null && endDateInput != null) {
                var startDate = new Date(startDateInput);
                var endDate = new Date(endDateInput);
                if (startDate.getDate() == endDate.getDate() && startDate.getMonth() == endDate.getMonth() && startDate.getFullYear() == endDate.getFullYear()) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        
        vm.changeView = function(value) {
			if (value != null) {
				vm.isViewCalendar = value;
			}
		}

        vm.searchdepartment = function () {
        	
        	if (vm.isfindByCode) {
        		vm.DepartmentSearch.name = '';
			}
        	else {
        		vm.DepartmentSearch.code = '';
			}
        	
            if (vm.DepartmentSearch == null || (vm.DepartmentSearch.name == '' && vm.DepartmentSearch.code == '')) {
                return;
            }
            service.searchdepartment(vm.pageSize, vm.pageIndex, vm.DepartmentSearch).then(function (data) {
            	console.log(vm.DepartmentSearch);
                if (data != null) {
                    vm.listDepartment = data.content;
                    for (var i = 0; i < vm.listDepartment.length; i++) {
                        var item = vm.listDepartment[i];
                        if (vm.event != null && vm.event.dAttendees != null) {
                            for (var j = 0; j < vm.event.dAttendees.length; j++) {
                                if (vm.event.dAttendees[j].department.id == item.id) {
                                    item.state = true;
                                }
                            }
                        }
                    }
                    vm.bsTableControlListDepartment.options.data = vm.listDepartment;
                    vm.bsTableControlListDepartment.options.totalRows = data.totalElements;
                }
            });
        };

        vm.searchstaff = function () {
            if (vm.staffSearch == null) {
                return;
            }
            service.searchstaff(vm.pageSize, vm.pageIndex, vm.staffSearch).then(function (data) {
                if (data != null) {
                    vm.listPerson = data.content;
                    for (var i = 0; i < vm.listPerson.length; i++) {
                        var item = vm.listPerson[i];
                        if (vm.event != null && vm.event.sAttendees != null) {
                            for (var j = 0; j < vm.event.sAttendees.length; j++) {
                                if (vm.event.sAttendees[j].staff.id == item.id) {
                                    item.state = true;
                                }
                            }
                        }
                    }
                    vm.bsTableControl.options.data = vm.listPerson;
                    vm.bsTableControl.options.totalRows = data.totalElements;
                }
            });
        };

        vm.searchChairPerson = function () {
            if (vm.chairPersonSearch == null) {
                return;
            }
            service.searchChairPerson(vm.pageSize, vm.pageIndex, vm.chairPersonSearch).then(function (data) {
                if (data != null) {
                    vm.listChairPerson = data.content;
                    for (var i = 0; i < vm.listChairPerson.length; i++) {
                        var item = vm.listChairPerson[i];
                        if (vm.event != null && vm.event.chairPerson != null) {
                            if (vm.event.chairPerson.staff.id == item.id) {
                                item.state = true;
                            }
                        }
                    }
                    vm.bsTableControlChairPerson.options.data = vm.listChairPerson;
                    vm.bsTableControlChairPerson.options.totalRows = data.totalElements;
                }
            });
        };

        vm.bsTableControlChairPerson = {
            options: {
                data: vm.listChairPerson,
                idField: "id",
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: true,
                showToggle: true,
                pagination: true,
                pageSize: vm.pageSize,
                pageList: [5, 10, 25, 50, 100, 200],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionChairPerson(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selectedChairPerson = row;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.listChairPerson);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedChairPerson = {};
                        });
                    }
                },
                onPageChange: function (pageIndex, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = pageIndex;
                    vm.searchChairPerson();
                }
            }
        };
        
        vm.bsTableControlRoom = {
                options: {
                    data: vm.listRoom,
                    idField: "id",
                    sortable: true,
                    striped: true,
                    maintainSelected: true,
                    clickToSelect: false,
                    showColumns: true,
                    showToggle: true,
                    pagination: true,
                    pageSize: vm.pageSize,
                    pageList: [5, 10, 25, 50, 100, 200],
                    locale: settings.locale,
                    sidePagination: 'server',
                    columns: service.getTableDefinitionRoom(),
                    onCheck: function (row, $element) {
                        $scope.$apply(function () {
                            vm.selectedRoom = row;
                        });
                    },
                    onUncheck: function (row, $element) {
                        var index = utils.indexOf(row, vm.listRoom);
                        if (index >= 0) {
                            $scope.$apply(function () {
                                vm.selectedRoom = {};
                            });
                        }
                    },
                    onPageChange: function (pageIndex, pageSize) {
                        vm.pageSize = pageSize;
                        vm.pageIndex = pageIndex;
                        vm.searchRoom();
                    }
                }
            };


        vm.bsTableControl = {
            options: {
                data: vm.listPerson,
                idField: "id",
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: true,
                showToggle: true,
                pagination: true,
                pageSize: vm.pageSize,
                pageList: [5, 10, 25, 50, 100, 200],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinition(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selectedStaffs.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedStaffs = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.listPerson);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            if (vm.deleteListsAttendees == null) {
                                vm.deleteListsAttendees = [];
                            }
                            if (vm.event != null && vm.event.sAttendees != null) {
                                for (var i = 0; i < vm.event.sAttendees.length; i++) {
                                    if (vm.event.sAttendees[i].staff.id == row.id) {
                                        vm.deleteListsAttendees.push(vm.event.sAttendees[i]);
                                    }
                                }
                            }
                        });
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedStaffs = [];
                    });
                },
                onPageChange: function (pageIndex, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = pageIndex;
                    vm.searchstaff();
                }
            }
        };

        vm.bsTableControlListDepartment = {
            options: {
                data: vm.listDepartment,
                idField: "id",
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: true,
                showToggle: true,
                pagination: true,
                pageSize: vm.pageSize,
                pageList: [5, 10, 25, 50, 100, 200],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionListDepartment(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selectedDepartments.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedDepartments = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.listDepartment);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            if (vm.deleteListdAttendees == null) {
                                vm.deleteListdAttendees = [];
                            }
                            if (vm.event != null && vm.event.dAttendees != null) {
                                for (var i = 0; i < vm.event.dAttendees.length; i++) {
                                    if (vm.event.dAttendees[i].department.id == row.id) {
                                        vm.deleteListdAttendees.push(vm.event.dAttendees[i]);
                                    }
                                }
                            }
                        });
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedDepartments = [];
                    });
                },
                onPageChange: function (pageIndex, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = pageIndex;
                    vm.searchdepartment();
                }
            }
        };
        
        vm.getAllEnventDateView = function () {
            service.getAllEnventDateView(vm.dateStart).then(function (data) {
                if (data != null) {
                    var logEvent = data[0].events[0];
                    vm.eventDatesView = data;
                }
            });
        };

        vm.getAllEnventDateView();

        vm.getAllEnventDate = function () {
            service.getAllEnventDate(vm.dateStart).then(function (data) {
                if (data != null) {
                    var logEvent = data[0].events[0];
                    vm.eventDates = data;
                    console.log(vm.eventDates);
                }
            });
        };

        vm.getAllEnventDate();
        
        vm.getAllEventPriorities = function () {
            service.getAllEventPriorities().then(function (data) {
                vm.priorities = data;
            });
        };

        vm.getAllEventPriorities();
        
        vm.searchRoom = function () {
        	
            if (vm.bsTableControlRoom != null && vm.bsTableControlRoom.state != null) {
                vm.bsTableControlRoom.state.pageNumber = 1;
			}
            service.searchRoom(vm.RoomSearch, vm.pageSize, vm.pageIndex).then(function (data) {
                vm.listRoom = data.content;
                vm.bsTableControlRoom.options.data = vm.listRoom;
                vm.bsTableControlRoom.options.totalRows = data.totalElements;
            });
        };
        
        vm.searchRoom();

        vm.addEventDate = function (eventDate) {
            vm.event = {};
            vm.status = utils.findById(vm.statuses, 0);
            vm.eventType = utils.findById(vm.eventTypes, 0);
            vm.isNew = true;
            vm.isView = false;
            if (eventDate != null && eventDate.date != null) {
                vm.event.startTime = new Date(eventDate.date);
                vm.event.endTime = new Date(eventDate.date);
            } else {
                vm.event.startTime = $scope.toDay;
                vm.event.endTime = $scope.toDay;
            }
            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'calendar/views/edit_event.html',
                scope: $scope,
                size: 'lg'
            });

        };

        vm.close = function () {
            vm.modalInstance.close();
        }

        vm.editEventDate = function (eventId) {
            if (!eventId || eventId < 0) {
                toastr.error('Có lỗi xảy ra, vui lòng thử lại.', 'Lỗi');
                return;
            }

            service.getEventById(eventId).then(function (data) {
                if (data != null) {
                    vm.event = data;
                    vm.isNew = false;
                    vm.isView = false;
                    vm.event = {};
                    vm.event = data;
                    vm.status = utils.findById(vm.statuses, data.status);
                    vm.eventType = utils.findById(vm.eventTypes, data.eventType);
                    
                    vm.bsTableControl4Files.options.data = vm.event.attachments;

                    if (vm.event.startTime) {
                        vm.event.startTime = new Date(vm.event.startTime);
                    }

                    if (vm.event.endTime) {
                        vm.event.endTime = new Date(vm.event.endTime);
                    }

                    vm.modalInstance = modal.open({
                        animation: true,
                        templateUrl: 'edit_modal.html',
                        scope: $scope,
                        size: 'lg'
                    });
                }
            });
        };
        vm.removeAttachment = function (filename) {
            if (!filename || !vm.eventAttachments || vm.eventAttachments.length <= 0) {
                return;
            }

            var pos = -1;
            for (var i = 0; i < vm.eventAttachments.length; i ++) {
                if (vm.eventAttachments[i].name == filename) {
                    pos = i;
                    break;
                }
            }

            if (pos >= 0) {
                vm.eventAttachments.splice(pos, 1);
            }
        };
        vm.save = function () {
            if (vm.isNew) {
                if (vm.status != null) {
                    vm.event.status = vm.status.id;
                }
                if (vm.eventType != null) {
                    vm.event.eventType = vm.eventType.id;
                }
                if (vm.event.title==null) {
                    toastr.error('Vui lòng nhập tiêu đề.', 'Lỗi');
                    return;
                }
                if (vm.event.startTime>=vm.event.endTime) {
                    toastr.error('Thời gian sự kiện lỗi, vui lòng nhập lại.', 'Lỗi');
                    return;
                }
                
                service.saveEvent(vm.event, function successCallback(data) {
                    toastr.info('Đã thêm mới sự kiện thành công!', 'Thông báo');
                    // Reload users
                    vm.getAllEnventDate();
                    vm.getAllEnventDateView();

                    vm.event = {};
                }, function errorCallback(response) {
                    toastr.error('Có lỗi xảy ra khi thêm mới sự kiện.', 'Thông báo');
                }).then(function () {
                    // Close the modal
                    if (vm.modalInstance) {
                        vm.modalInstance.close();
                        vm.event = {};
                    }
                });

            } else if (!vm.isNew) {

                if (vm.status != null) {
                    vm.event.status = vm.status.id;
                }
                if (vm.eventType != null) {
                    vm.event.eventType = vm.eventType.id;
                }
                if (vm.event.title==null) {
                    toastr.error('Vui lòng nhập tiêu đề.', 'Lỗi');
                    return;
                }
                if (vm.event.startTime>=vm.event.endTime) {
                    toastr.error('Thời gian sự kiện lỗi, vui lòng nhập lại.', 'Lỗi');
                    return;
                }

                service.saveEvent(vm.event, function successCallback(data) {
                    toastr.info('Đã cập nhật sự kiện thành công!', 'Thông báo');
                    // Reload users
                    vm.getAllEnventDate();
                    vm.getAllEnventDateView();
                    
                    vm.event = {};
                }, function errorCallback(response) {
                    toastr.error('Có lỗi xảy ra khi thêm mới sự kiện.', 'Thông báo');
                }).then(function () {
                    // Close the modal
                    if (vm.modalInstance) {
                        vm.modalInstance.close();
                        vm.event = {};
                    }
                });

            }

            vm.selectedStaffs = [];
            vm.selectedDepartments = [];
            vm.modalInstance.close();
        };

        vm.eventDelete = {};

        vm.deleteEvent = function (eventDateId, eventId) {
            if (!eventDateId || eventDateId < 0 || !eventId || eventId < 0) {
                toastr.error('Có lỗi xảy ra, vui lòng thử lại.', 'Lỗi');
                return;
            }

            var eventDate = utils.findById(vm.eventDates, eventDateId);
            if (eventDate != null) {
                var event = utils.findById(eventDate.events, eventId);
                if (event != null) {
                    vm.eventDelete = event;
                }
            }

            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'delete_modal.html',
                scope: $scope,
                size: 'md'
            });

            vm.modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                    var lstEventDelete = [];
                    lstEventDelete.push(vm.eventDelete);
                    blockUI.start();
                    service.deleteEvent(lstEventDelete, function success() {
                        // Refresh list
                        vm.getAllEnventDate();
                        vm.getAllEnventDateView();

                        // Block UI
                        blockUI.stop();

                        // Notify
                        toastr.info('Bạn đã xoá thành công sự kiện: ' + vm.eventDelete.title, 'Thông báo');

                        // Clear selected tags
                        vm.eventDelete = {};
                    }, function failure() {
                        // Block UI
                        blockUI.stop();

                        toastr.error('Có lỗi xảy ra khi xóa sự kiện.', 'Thông báo');
                    });
                }
            }, function () {
                console.log('Modal dismissed at: ' + new Date());
            });

        };


        vm.showModalChairPerson = function () {
            vm.pageIndex = 1;
            vm.listChairPerson = [];
            vm.selectedChairPerson = {};
            vm.chairPersonSearch = { keyword: '' };

            vm.bsTableControlChairPerson.options.data = vm.listChairPerson;
            vm.bsTableControlChairPerson.options.totalRows = 0;

            vm.modalListChairPerson = modal.open({
                animation: true,
                templateUrl: 'calendar/views/chairmanselect.html',
                scope: $scope,
                size: 'lg'
            });

            vm.modalListChairPerson.result.then(function (confirm) {
                if (confirm == 'yes') {
                    if (vm.event.chairPerson == null) {
                        vm.event.chairPerson = {};
                    }

                    if (vm.selectedChairPerson != null) {
                        var att = {};
                        att.displayName = vm.selectedChairPerson.displayName;
                        att.staff = vm.selectedChairPerson;
                        att.isChairPerson = true;
                        att.attendeeType = 0;

                        vm.event.chairPerson = att;
                    }
                }
                else {
                    vm.selectedChairPerson = {};
                }
            });
            vm.modalListChairPerson.close();
        };
        

        vm.showModalRoom = function () {
            vm.pageIndex = 1;
            vm.listRoom = [];
            vm.isfindByCode = false;
            vm.RoomSearch = '';
            vm.searchRoom();

            vm.modalListRoom = modal.open({
                animation: true,
                templateUrl: 'calendar/views/roomselect.html',
                scope: $scope,
                size: 'md'
            });

            vm.modalListRoom.result.then(function (confirm) {
                if (confirm == 'yes') {
                    if (vm.event.room == null) {
                        vm.event.room = {};
                    }

                    if (vm.selectedRoom != null) {

                        vm.event.room = vm.selectedRoom;
                    }
                }
                else {
                    vm.selectedRoom = {};
                }
            });
            vm.modalListRoom.close();
        };

        vm.showModallistPerson = function () {
            vm.pageIndex = 1;
            vm.selectedStaffs = [];
            vm.listPerson = [];
            vm.staffSearch = { keyword: '' };
            vm.bsTableControl.options.data = vm.listPerson;
            vm.bsTableControl.options.totalRows = 0;

            vm.modalListEventAttendee = modal.open({
                animation: true,
                templateUrl: 'calendar/views/listeventattendee.html',
                scope: $scope,
                size: 'lg'
            });

            vm.modalListEventAttendee.result.then(function (confirm) {
                if (confirm == 'yes') {
                    if (vm.event.sAttendees == null) {
                        vm.event.sAttendees = [];
                    }
                    if (vm.deleteListsAttendees != null) {
                        for (var i = 0; i < vm.deleteListsAttendees.length; i++) {
                            for (var j = 0; j < vm.event.sAttendees.length; j++) {
                                if (vm.deleteListsAttendees[i].id == vm.event.sAttendees[j].id) {
                                    vm.event.sAttendees.splice(j, 1);
                                }
                            }
                        }
                    }
                    for (var i = 0; i < vm.selectedStaffs.length; i++) {
                        var att = {};
                        att.displayName = vm.selectedStaffs[i].displayName;
                        att.staff = vm.selectedStaffs[i];
                        att.attendeeType = 0;
                        vm.event.sAttendees.push(att);
                    }
                }
                else {
                    vm.selectedStaffs = [];
                    vm.deleteListsAttendees = [];
                }
            });
        };


        vm.showModalListDepartment = function () {
            vm.pageIndex = 1;
            vm.listDepartment = [];
            vm.selectedDepartments = [];
            vm.DepartmentSearch = {};
            vm.isfindByCode = false;

            vm.bsTableControlListDepartment.options.data = vm.listDepartment;
            vm.bsTableControlListDepartment.options.totalRows = 0;

            vm.modalListDepartment = modal.open({
                animation: true,
                templateUrl: 'calendar/views/listdepartment.html',
                scope: $scope,
                size: 'lg'
            });

            vm.modalListDepartment.result.then(function (confirm) {
                if (confirm == 'yes') {
                    if (vm.event.dAttendees == null) {
                        vm.event.dAttendees = [];
                    }
                    if (vm.deleteListdAttendees != null) {
                        for (var i = 0; i < vm.deleteListdAttendees.length; i++) {
                            for (var j = 0; j < vm.event.dAttendees.length; j++) {
                                if (vm.deleteListdAttendees[i].id == vm.event.dAttendees[j].id) {
                                    vm.event.dAttendees.splice(j, 1);
                                }
                            }
                        }
                    }
                    for (var i = 0; i < vm.selectedDepartments.length; i++) {
                        var att = {};
                        att.displayName = vm.selectedDepartments[i].name;
                        att.department = vm.selectedDepartments[i];
                        att.attendeeType = 1;
                        vm.event.dAttendees.push(att);
                    }
                }
                else {
                    vm.selectedDepartments = [];
                    vm.deleteListdAttendees = [];
                }
            });

        };


        vm.sAttendeesRemove = function (index) {
            if (vm.event.sAttendees != null) {
                for (var i = 0; i < vm.event.sAttendees.length; i++) {
                    if (index == i) {
                        vm.event.sAttendees.splice(i, 1);
                    }
                }
            }
        }

        vm.chairPersonRemove = function () {
            if (vm.event.chairPerson != null) {
                vm.event.chairPerson = null;
            }
        }
        
        vm.roomRemove = function () {
            if (vm.event.room != null) {
                vm.event.room = null;
            }
        }


        vm.dAttendeesRemove = function (index) {
            if (vm.event.dAttendees != null) {
                for (var i = 0; i < vm.event.dAttendees.length; i++) {
                    if (index == i) {
                        vm.event.dAttendees.splice(i, 1);
                    }
                }
            }
        }

        vm.bsTableControl4Files = {
            options: {
                data: vm.event.attachments,
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

        //// Upload file
        vm.uploadedFile = null;
        vm.errorFile = null;
        vm.uploadFiles = function (file, errFiles) {
            vm.uploadedFile = file;
            if(vm.uploadedFile!=null){
	            Uploader.upload({
	                url: settings.api.baseUrl+ settings.api.apiPrefix + 'calendar/event/uploadattachment',
	                method: 'POST',
	                data: {uploadfile: vm.uploadedFile}
	            }).then(function (successResponse) {
	            	
	            	var attachment = successResponse.data;
	            	console.log(attachment);
	                if (vm.uploadedFile && (!vm.errorFile || (vm.errorFile && vm.errorFile.$error == null))) {
	                    if (vm.event != null && vm.event.attachments == null) {
	                        vm.event.attachments = [];
	                    }
                        vm.event.attachments.push(
                                //{ title: attachment.file.name, contentLength: attachment.file.contentSize, contentType: fileDesc.contentType }
                        		attachment
                            );
	                    vm.bsTableControl4Files.options.data = vm.event.attachments;
	                }
	            }, function (errorResponse) {
	                toastr.error('Error submitting data...', 'Error');
	            }, function (evt) {
	                console.log('progress: ' + parseInt(100.0 * evt.loaded / evt.total) + '%');
	            });
            }
        };


        $scope.deleteDocument = function (index) {
            if (vm.event != null && vm.event.attachments != null) {
                for (var i = 0; i < vm.event.attachments.length; i++) {
                    if (i == index) {
                        vm.event.attachments.splice(i, 1);
                    }
                }
            }
        }
        $scope.downloadFile = function (url, type, name) {
            	$http.get(url, {responseType:'arraybuffer'})
                    .success(function (response) {
                        var file = new Blob([response], {type: type});

                        var isChrome = !!window.chrome && !!window.chrome.webstore || window.chrome!=null;
                        var isIE = /*@cc_on!@*/false || !!document.documentMode;
                        var isEdge = !isIE && !!window.StyleMedia;


                        if (isChrome){
                            var url = window.URL || window.webkitURL;

                            var downloadLink = angular.element('<a></a>');
                            downloadLink.attr('href',url.createObjectURL(file));
                            downloadLink.attr('target','_self');
                            downloadLink.attr('download', name);
                            downloadLink[0].click();
                        }
                        else if(isEdge || isIE){
                            window.navigator.msSaveOrOpenBlob(file,name);

                        }
                        else {
                            var fileURL = URL.createObjectURL(file);
                            window.open(fileURL);
                        }

                    })
        };
        $scope.downloadDocument = function (index) {
            if (vm.event != null && vm.event.attachments != null) {
                for (var i = 0; i < vm.event.attachments.length; i++) {
                    if (i == index) {
                        var  attachment= vm.event.attachments[i];
                        service.getFileById(attachment.file.id).success(function (data) {
                        	 var file = new Blob([data], {type: attachment.file.contentType});
                        	 FileSaver.saveAs(file, attachment.file.name);
                        });;
                    }
                }
            }
        }
        
        vm.isCheckAll = false;
        
        vm.CheckAll = function() {
			if (vm.isCheckAll) {
				for (var i = 0; i < vm.eventDates.length; i++) {
					if (vm.eventDates[i].events != null) {
						for (var j = 0; j < vm.eventDates[i].events.length; j++) {
							if (vm.eventDates[i].events[j].isChecked != true) {
								vm.eventDates[i].events[j].isChecked = true;
							}
						}
					}
				}
			}
			else {
				for (var i = 0; i < vm.eventDates.length; i++) {
					if (vm.eventDates[i].events != null) {
					for (var j = 0; j < vm.eventDates[i].events.length; j++) {
						if (vm.eventDates[i].events[j].isChecked != false) {
							vm.eventDates[i].events[j].isChecked = false;
						}
					}
					}
				}
			}
		}
        

    	vm.hasCheck = false;
    	vm.publishSelected = function(){
    		alert('publish events');
        }
    	
        vm.Publish=function(){
            vm.isApprove=false;
            vm.hasCheck = false;
            var listApprove = [];

            if (vm.eventDates != null) {
        		for (var i = 0; i < vm.eventDates.length; i++) {
    				if (vm.eventDates[i].events != null) {
    					for (var j = 0; j < vm.eventDates[i].events.length; j++) {
    						if (vm.eventDates[i].events[j].isChecked != null && vm.eventDates[i].events[j].isChecked == true) {
                                vm.hasCheck = true;
    							if (vm.eventDates[i].events[j].status == 2 ) {
                                    
                                    toastr.warning('Sự kiện: "'+vm.eventDates[i].events[j].title+'" đã được xuất bản.', 'Thông báo');
    				

        							// listApprove.push(vm.eventDates[i].events[j]);
								}
    							else {
                                    listApprove.push(vm.eventDates[i].events[j]);

								}
    						}
    					}
    				}
    			}
			}
        	
        	if (!vm.hasCheck) {
                toastr.warning('Bạn chưa chọn sự kiện nào để xuất bản.', 'Thông báo');
				return;
			}
            
            vm.modalApproveAll = modal.open({
                animation: true,
                templateUrl: 'modalApproveAll.html',
                scope: $scope,
                size: 'md'
            });

            vm.modalApproveAll.result.then(function (confirm) {
                if (confirm == 'yes') {
                	
                	
                	if (listApprove.length > 0) {
                		for(var i=0;i<listApprove.length;i++){
                            listApprove[i].status=2;
                        }
                		service.approveAll(listApprove, function successCallback(data) {
                            toastr.info('Xuất bản thành công!', 'Thông báo');
                            // Reload users
                            vm.getAllEnventDate();
                            vm.getAllEnventDateView();
                            vm.isCheckAll = false;

                        }, function errorCallback(response) {
                            toastr.error('Xuất bản thất bại, có lỗi xảy ra.', 'Thông báo');
                        }).then(function () {
                            // Close the modal
                            if (vm.modalInstance) {
                                vm.modalInstance.close();
                            }
                        });
                		
					}
                }
                else {
                }
            });
            
        }
        

        vm.unPublish=function(){
            vm.isApprove=false;
            vm.hasCheck = false;
            var listUnPublish = [];

            if (vm.eventDates != null) {
        		for (var i = 0; i < vm.eventDates.length; i++) {
    				if (vm.eventDates[i].events != null) {
    					for (var j = 0; j < vm.eventDates[i].events.length; j++) {
    						if (vm.eventDates[i].events[j].isChecked != null && vm.eventDates[i].events[j].isChecked == true) {
                                vm.hasCheck = true;
                                listUnPublish.push(vm.eventDates[i].events[j]);
    						}
    					}
    				}
    			}
			}
        	
        	if (!vm.hasCheck) {
                toastr.warning('Bạn chưa chọn sự kiện nào để hủy xuất bản.', 'Thông báo');
				return;
			}

            vm.modalApproveAll = modal.open({
                animation: true,
                templateUrl: 'modalUnApproveAll.html',
                scope: $scope,
                size: 'md'
            });

            vm.modalApproveAll.result.then(function (confirm) {
                if (confirm == 'yes') {
                	
                	if (listUnPublish.length > 0) {
                		for(var i=0;i<listUnPublish.length;i++){
                			listUnPublish[i].status=1;
                        }
                		service.unPublishAll(listUnPublish, function successCallback(data) {
                            toastr.info('Hủy xuất bản lịch thành công!', 'Thông báo');
                            // Reload users
                            vm.getAllEnventDate();
                            vm.getAllEnventDateView();
                            vm.isCheckAll = false;

                        }, function errorCallback(response) {
                            toastr.error('Hủy xuất bản lịch thất bại, có lỗi xảy ra.', 'Thông báo');
                        }).then(function () {
                            // Close the modal
                            if (vm.modalInstance) {
                                vm.modalInstance.close();
                            }
                        });
                		
					}
                }
            });
        }

        
        vm.Approve=function(){
            vm.isApprove=true;
            vm.hasCheck = false;
            var listApprove = [];

            if (vm.eventDates != null) {
        		for (var i = 0; i < vm.eventDates.length; i++) {
    				if (vm.eventDates[i].events != null) {
    					for (var j = 0; j < vm.eventDates[i].events.length; j++) {
    						if (vm.eventDates[i].events[j].isChecked != null && vm.eventDates[i].events[j].isChecked == true) {
                                vm.hasCheck = true;
    							if ((vm.eventDates[i].events[j].status == 1)||(vm.eventDates[i].events[j].status == 2) ) {
                                    
                                    toastr.warning('Sự kiện: "'+vm.eventDates[i].events[j].title+'" đã được phê duyệt hoặc xuất bản.', 'Thông báo');

        							// listApprove.push(vm.eventDates[i].events[j]);
								}
    							else {
                                    // vm.eventDates[i].events[j].status=1;
                                    listApprove.push(vm.eventDates[i].events[j]);

								}
    						}
    					}
    				}
    			}
			}
        	
        	if (!vm.hasCheck) {
                toastr.warning('Bạn chưa chọn sự kiện nào để phê duyệt.', 'Thông báo');
				return;
			}

            vm.modalApproveAll = modal.open({
                animation: true,
                templateUrl: 'modalApproveAll.html',
                scope: $scope,
                size: 'md'
            });

            vm.modalApproveAll.result.then(function (confirm) {
                if (confirm == 'yes') {
                	
                	
                	if (listApprove.length > 0) {
                		for(var i=0;i<listApprove.length;i++){
                            listApprove[i].status=1;
                        }
                		service.approveAll(listApprove, function successCallback(data) {
                            toastr.info('Phê duyệt thành công!', 'Thông báo');
                            // Reload users
                            vm.getAllEnventDate();
                            vm.getAllEnventDateView();
                            vm.isCheckAll = false;

                        }, function errorCallback(response) {
                            toastr.error('Phê duyệt thất bại, có lỗi xảy ra.', 'Thông báo');
                        }).then(function () {
                            // Close the modal
                            if (vm.modalInstance) {
                                vm.modalInstance.close();
                            }
                        });
                		
					}
                }
                else {
                }
            });
        }
        

        vm.unApprove=function(){
            vm.isApprove=true;
            vm.hasCheck = false;
            var listUnApprove = [];

            if (vm.eventDates != null) {
        		for (var i = 0; i < vm.eventDates.length; i++) {
    				if (vm.eventDates[i].events != null) {
    					for (var j = 0; j < vm.eventDates[i].events.length; j++) {
    						if (vm.eventDates[i].events[j].isChecked != null && vm.eventDates[i].events[j].isChecked == true) {
                                vm.hasCheck = true;
    							listUnApprove.push(vm.eventDates[i].events[j]);
    						}
    					}
    				}
    			}
			}
        	
        	if (!vm.hasCheck) {
                toastr.warning('Bạn chưa chọn sự kiện nào để phê duyệt.', 'Thông báo');
				return;
			}

            vm.modalApproveAll = modal.open({
                animation: true,
                templateUrl: 'modalUnApproveAll.html',
                scope: $scope,
                size: 'md'
            });

            vm.modalApproveAll.result.then(function (confirm) {
                if (confirm == 'yes') {
                	
                	if (listUnApprove.length > 0) {
                		for(var i=0;i<listUnApprove.length;i++){
                			listUnApprove[i].status=0;
                        }
                		service.unApproveAll(listUnApprove, function successCallback(data) {
                            toastr.info('Hủy phê duyệt thành công!', 'Thông báo');
                            // Reload users
                            vm.getAllEnventDate();
                            vm.getAllEnventDateView();
                            vm.isCheckAll = false;

                        }, function errorCallback(response) {
                            toastr.error('Hủy phê duyệt thất bại, có lỗi xảy ra.', 'Thông báo');
                        }).then(function () {
                            // Close the modal
                            if (vm.modalInstance) {
                                vm.modalInstance.close();
                            }
                        });
                		
					}
                }
            });
        }


        vm.approveAll = function(){
            console.log(vm.eventDates);
        	vm.hasCheck = false;
        	
        	var listApprove = [];
        	
        	if (vm.eventDates != null) {
        		for (var i = 0; i < vm.eventDates.length; i++) {
    				if (vm.eventDates[i].events != null) {
    					for (var j = 0; j < vm.eventDates[i].events.length; j++) {
    						if (vm.eventDates[i].events[j].isChecked != null && vm.eventDates[i].events[j].isChecked == true) {
                                vm.hasCheck = true;

    							if (vm.eventDates[i].events[j].status == 0) {
                                    

        							listApprove.push(vm.eventDates[i].events[j]);
								}
    							else {
    				                toastr.warning('Sự kiện: "'+vm.eventDates[i].events[j].title+'" đã được phê duyệt hoặc xuất bản.', 'Thông báo');
    								return;
								}
    						}
    					}
    				}
    			}
			}
        	
        	if (!vm.hasCheck) {
                toastr.warning('Bạn chưa chọn sự kiện nào để phê duyệt.', 'Thông báo');
				return;
			}

            vm.modalApproveAll = modal.open({
                animation: true,
                templateUrl: 'modalApproveAll.html',
                scope: $scope,
                size: 'md'
            });

            vm.modalApproveAll.result.then(function (confirm) {
                if (confirm == 'yes') {
                	var lstApprove = [];
                	if (vm.eventDates != null) {
                		for (var i = 0; i < vm.eventDates.length; i++) {
            				if (vm.eventDates[i].events != null) {
            					for (var j = 0; j < vm.eventDates[i].events.length; j++) {
            						if (vm.eventDates[i].events[j].isChecked != null && vm.eventDates[i].events[j].isChecked == true) {
            							lstApprove.push(vm.eventDates[i].events[j]);
            						}
            					}
            				}
            			}
        			}
                	
                	if (lstApprove.length > 0) {
                		
                		service.approveAll(lstApprove, function successCallback(data) {
                            toastr.info('Phê duyệt thành công!', 'Thông báo');
                            // Reload users
                            vm.getAllEnventDate();
                            vm.getAllEnventDateView();
                            vm.isCheckAll = false;

                        }, function errorCallback(response) {
                            toastr.error('Phê duyệt thất bại, có lỗi xảy ra.', 'Thông báo');
                        }).then(function () {
                            // Close the modal
                            if (vm.modalInstance) {
                                vm.modalInstance.close();
                            }
                        });
                		
					}
                }
                else {
                }
            });
            
        }
        
        

    }

})();
