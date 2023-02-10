/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.ExportAnimal').controller('ExportAnimalController', ExportAnimalController);

    ExportAnimalController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        'ExportAnimalService',
        'AnimalService',
        'FarmService',
        '$state',
		'$translate',
        'Upload',
        'FmsAdministrativeService'
    ];
	// angular.module('Education.ExportAnimal').directive('fileDownload',function(){
    //     return{
    //         restrict:'A',
    //         scope:{
    //             fileDownload:'=',
    //             fileName:'=',
    //         },

    //         link:function(scope,elem,atrs){


    //             scope.$watch('fileDownload',function(newValue, oldValue){

    //                 if(newValue!=undefined && newValue!=null){
    //                     console.debug('Downloading a new file');
    //                     var isFirefox = typeof InstallTrigger !== 'undefined';
    //                     var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
    //                     var isIE = /*@cc_on!@*/false || !!document.documentMode;
    //                     var isEdge = !isIE && !!window.StyleMedia;
    //                     var isChrome = !!window.chrome && !!window.chrome.webstore || window.chrome!=null;;
    //                     var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
    //                     var isBlink = (isChrome || isOpera) && !!window.CSS;

    //                     if(isFirefox || isIE || isChrome){
    //                         if(isChrome){
    //                             console.log('Manage Google Chrome download');
    //                             var url = window.URL || window.webkitURL;
    //                             var fileURL = url.createObjectURL(scope.fileDownload);
    //                             var downloadLink = angular.element('<a></a>');//create a new  <a> tag element
    //                             downloadLink.attr('href',fileURL);
    //                             downloadLink.attr('download',scope.fileName);
    //                             downloadLink.attr('target','_self');
    //                             downloadLink[0].click();//call click function
    //                             url.revokeObjectURL(fileURL);//revoke the object from URL
    //                         }
    //                         if(isIE){
    //                             console.log('Manage IE download>10');
    //                             window.navigator.msSaveOrOpenBlob(scope.fileDownload,scope.fileName);
    //                         }
    //                         if(isFirefox){
    //                             console.log('Manage Mozilla Firefox download');
    //                             var url = window.URL || window.webkitURL;
    //                             var fileURL = url.createObjectURL(scope.fileDownload);
    //                             var a=elem[0];//recover the <a> tag from directive
    //                             a.href=fileURL;
    //                             a.download=scope.fileName;
    //                             a.target='_self';
    //                             a.click();//we call click function
    //                         }


    //                     }else{
    //                         alert('SORRY YOUR BROWSER IS NOT COMPATIBLE');
    //                     }
    //                 }
    //             });

    //         }
    //     }
    // });

    function ExportAnimalController($rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, service, AnimalService,FarmService,$state,$translate,Upload,fmsAdministrativeService) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;
		vm.baseUrl = settings.api.baseUrl + settings.api.apiPrefix;
        vm.user = {}; // Doi tượng
        vm.users = []; // Mảng
        vm.exportAnimal = {}; // mảng Region
        vm.exportAnimals = [];
        vm.id = $state.params.id;
        console.log(vm.id)
        vm.selectedExportAnimal = [];
        

        // UI
        vm.modalInstance = null;
        vm.animals = [];
        vm.farms = []

        // pagination
        vm.pageIndex = 0;
        vm.pageSize = 10;

        vm.changePass = false;
        vm.searchDto= {};
        vm.searchDto.district = null;
        vm.searchDto.cityId=null;
        vm.searchDto.districtId = null;
        vm.searchDto.listDistrictId=null;
        vm.searchDto.districtIds=null;
        vm.searchDto.ward=null;
        vm.searchDto.wardId=null;
		vm.isRole=false;
		vm.isFamer=false;
		vm.isSdah=false;
		vm.isDistrict=false;
		vm.isWard=false;
		vm.isSdahView = false;
		//------Start--Phân quyền theo user đăng nhập-----------
		blockUI.start();
        service.getCurrentUser().then(function (data) {
            console.log(data);
            vm.user=data;
            var roles = data.roles;
           
            if(roles!=null && roles.length>0){
                for(var i=0; i<roles.length;i++){
                    if (roles[i]!=null &&  roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' ||roles[i].authority.toLowerCase() == 'role_dlp')) {
                        vm.isRole = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah' ) {
                        vm.isSdah = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_district' ) {
                        vm.isDistrict = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_ward' ) {
                        vm.isWard = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_famer' ) {
                        vm.isFamer = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah_view' ){
                        vm.isSdahView = true;
						vm.isSdah = true;							
                    }
                }
            }else{
                vm.isRole=false;
				vm.isFamer=false;
				vm.isSdah=false;
				vm.isDistrict=false;
				vm.isWard=false;
				vm.isSdahView = false;
            }
			if(vm.isRole){//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
				vm.getAllCity();
				vm.findBy();
			}else{// trường hợp này thì phân quyền theo user
				if(vm.isRole==false){
					getAdministrativeUnitDtoByLoginUser();
					
				}
				
			}
            


            blockUI.stop();
        });

        AnimalService.getAll().then(function (data) {
            vm.animals = data;
        }).catch(function (error) {
            vm.animals = [];
        });
		
		//load user administrativeunit
		function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function(data) {
                vm.adminstrativeUnits = data;
				if(vm.adminstrativeUnits!=null && vm.adminstrativeUnits.length>0){
					getDataByCombobox(vm.adminstrativeUnits[0]);
					
				}
            });
        }
		function getDataByCombobox(administrativeUnit) {
			if( vm.searchDto==null){
                vm.searchDto={};
            }
			if(administrativeUnit.parentDto==null){
				vm.searchDto.province=administrativeUnit.id;
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				vm.adminstrativeUnits_City.push(administrativeUnit);
				fmsAdministrativeService.getAllByParentId(vm.searchDto.province).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    
                });
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				vm.searchDto.district=administrativeUnit.id;
				vm.searchDto.province = administrativeUnit.parentDto.id;
				
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
                vm.adminstrativeUnits_Dist=[];
				if(vm.adminstrativeUnits_Dist==null) vm.adminstrativeUnits_Dist=[];
                // vm.adminstrativeUnits_Dist.push(administrativeUnit);
                fmsAdministrativeService.getAllByParentId(vm.searchDto.province).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    
                });
				fmsAdministrativeService.getAllByParentId(vm.searchDto.district).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                    
                });
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){
				vm.searchDto.ward=administrativeUnit.id;				
                vm.searchDto.district = administrativeUnit.parentDto.id;
				vm.searchDto.province = administrativeUnit.parentDto.parentDto.id;
				
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
				if(vm.adminstrativeUnits_Dist==null) vm.adminstrativeUnits_Dist=[];
				vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
				if(vm.adminstrativeUnits_Wards==null) vm.adminstrativeUnits_Wards=[];
				vm.adminstrativeUnits_Wards.push(administrativeUnit);
			}
			if(vm.isRole == false && vm.isFamer==true &&  vm.user!==null && vm.user.username!==null && vm.user.username!==''){//trường hợp tài khoản nông dân
                //gán tên cơ sở chăn nuôi
                //vm.searchDto.nameOrCode=vm.user.username;
				vm.textSearch=vm.user.username;
               // console.log("username");
				//console.log(vm.user.username);
            }
			vm.findBy();
            
        }
		
		//--------End ----Phân quyền-------------
		
        /**
         * Get a list of ExportAnimal
         */
        vm.getExportAnimals = function() {
            service.getExportAnimals(vm.pageIndex, vm.pageSize).then(function(data) {
                vm.exportAnimals = data.content;
                vm.bsTableControl.options.data = vm.exportAnimals;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        };

       // vm.getExportAnimals();

        vm.saveExportAnimal = function() {


            // if (!vm.exportAnimal.name) {
            //     toastr.error('Vui lòng nhập đầy đủ tên !', 'Thông báo');
            //     return;
            // }

            // if (!vm.exportAnimal.code) {
            //     toastr.error('Vui lòng nhập đầy đủ mã !', 'Thông báo');
            //     return;
            // }

            // if (!vm.fmsRegion.description) {
            //     toastr.error('Vui lòng nhập đầy đủ mô tả vùng!', 'Thông báo');
            //     return;
            // }

            // if (!vm.fmsRegion.acreage) {
            //     toastr.error('Vui lòng nhập đầy đủ diện tích vùng!', 'Thông báo');
            //     return;
            // }

            service.saveExportAnimal(vm.exportAnimal).then(function(data) {
                toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));

                // Reload ExportAnimal
                //vm.getExportAnimals();
				vm.findBy();
                vm.modalInstance.close();
            }, function errorCallback(response) {
                toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
            });

        };

        /**
         * Create a new user
         */



        vm.newExportAnimal = function() {
            vm.exportAnimal = { isNew: true };
            
            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_modal.html',
                backdrop: 'static',
                scope: $scope,
                size: 'lg'
            });
        };
        vm.selecteAnimal = function(){
            vm.searchByCode();
        }

        
        $scope.editExportAnimal = function(id){
            $state.go("application.exportAnimalDetail",{id:id})
        }

        vm.createExportAnimal = function (){
            vm.farm = {isNew : true};
            $state.go('application.exportAnimalDetail');
        };

        /**
         * Edit an existing user
         *
         * @param userId
         */
        // $scope.editUser = function(userId) {

        //     service.getUser(userId).then(function(data) {
        //         if (data && data.id) {

        //             vm.user = data;
        //             vm.user.isNew = false;

        //             vm.modalInstance = modal.open({
        //                 animation: true,
        //                 templateUrl: 'edit_modal.html',
        //                 scope: $scope,
        //                 size: 'lg'
        //             });
        //         }
        //     });
        //     vm.changePass = false;
        // };

        /**
         * Get Firstname & Lastname from fullname
         */
        $scope.$watch('vm.user.person.displayName', function(newVal, oldVal) {

            if (!newVal) {
                return;
            }

            var fullname = String(newVal).trim();
            if (fullname.length <= 0) {
                return;
            }

            var spaceIndex = fullname.indexOf(' ');

            if (spaceIndex > 0) {
                vm.user.person.firstName = fullname.substr(0, spaceIndex);
                vm.user.person.lastName = fullname.substr(spaceIndex + 1);
            }
        });


        // search user by username
        vm.textSearch = '';

        // function findUserByUserName(username, pageIndex, pageSize) {
        //     service.findUserByUserName(username, pageIndex, pageSize).then(function(data) {
        //         vm.exportAnimal = data.content;
        //         vm.bsTableControl.options.data = vm.exportAnimal;
        //         vm.bsTableControl.options.totalRows = data.totalElements;
        //         console.log(data);
        //     });
        // }

        // vm.searchByCode = function() {
        //     vm.textSearch = String(vm.textSearch).trim();
        //     if (vm.textSearch != '') {
        //         findUserByUserName(vm.textSearch, vm.pageIndex, vm.pageSize);
        //     }
        //     if (vm.textSearch == '') {
        //         vm.getExportAnimals();
        //     }
        // }

        vm.bsTableControl = {
            options: {
                data: vm.exportAnimal,
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
                onCheck: function(row, $element) {
                    $scope.$apply(function() {
                        if (row.username && row.username != 'admin') {
                            vm.selectedExportAnimal.push(row);
                        } else {
                            bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                        }
                        // vm.selectedExportAnimal.push(row);
                    });
                },
                onCheckAll: function(rows) {
                    $scope.$apply(function() {
                        angular.forEach(rows, function(row) {
                            if (row.username && row.username != 'admin') {
                                vm.selectedExportAnimal.push(row);
                            } else {
                                bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                            }
                        });
                        // vm.selectedExportAnimal = rows;
                    });
                },
                onUncheck: function(row, $element) {
                    var index = utils.indexOf(row, vm.selectedExportAnimal);
                    if (index >= 0) {
                        $scope.$apply(function() {
                            vm.selectedExportAnimal.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function(rows) {
                    $scope.$apply(function() {
                        vm.selectedExportAnimal = [];
                    });
                },
                onPageChange: function(index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
					vm.findBy();

                    //vm.getExportAnimals();
                }
            }
        };
		
		vm.enterSearch = function(){
            // console.log(event.keyCode);
            if(event.keyCode == 13){//Phím Enter
                
                vm.searchByCode();
            }
        };
		 vm.searchByCode = function () {
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.findBy();
        };
		vm.findBy=function () {
            if( vm.searchDto==null){
                vm.searchDto={};
            }
            if(vm.textSearch!=null&& vm.textSearch!=""){
                vm.searchDto.nameOrCode=vm.textSearch;
            }
            else{
                vm.searchDto.nameOrCode=null;
            }
            if(vm.searchDto.province!=null ){
                vm.searchDto.cityId=vm.searchDto.province;
            }
            else{
                vm.searchDto.cityId= null;
            }
            if(vm.searchDto.district!=null){
                vm.searchDto.districtId=vm.searchDto.district;
            }
            else{
                vm.searchDto.districtId=null;
            }
            if(vm.searchDto.ward!=null){
                vm.searchDto.wardsId=vm.searchDto.ward;
            }
            else{
                vm.searchDto.wardsId=null;
            }
            if(vm.searchDto.listDistrictId!=null&&vm.searchDto.listDistrictId.length>0){
                vm.searchDto.districtIds=vm.searchDto.listDistrictId;
            }else{
                vm.searchDto.districtIds=null;
            }
			if(vm.fromDate!=null){
				vm.searchDto.fromDate=vm.fromDate;
			}else{
				vm.searchDto.fromDate=null;
			}
			if(vm.toDate!=null){
				vm.searchDto.toDate=vm.toDate;
			}else{
				vm.searchDto.toDate=null;
			}
            vm.searchDto.animalId = vm.animalSelected ? vm.animalSelected.id: null;
			vm.searchDto.type=-1;
            
            console.log(vm.searchDto);
			if(changeDate()){
				service.getPageSearchExportAnimal(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
					vm.exportAnimal = data.content;
					vm.bsTableControl.options.data = vm.exportAnimal;
					vm.bsTableControl.options.totalRows = data.totalElements;
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
        // $scope.editExportAnimal = function(id) {
        //     service.getExportAnimalById(id).then(function(data) {
        //         if (data) {
        //             vm.exportAnimal = data;
        //             vm.exportAnimal.isNew = false;
        //             vm.animal = data.animal;
        //             vm.farm = data.farm;
        //             AnimalService.getAll().then(function(data){
        //                 vm.animals = data;
        //             })
        //             FarmService.getAll().then(function(data){
        //                 vm.farms = data;
        //             })
        //             vm.modalInstance = modal.open({
        //                 animation: true,
        //                 templateUrl: '',
        //                 backdrop: 'static',
        //                 scope: $scope,
        //                 size: 'lg'
        //             });
        //         }
        //     });
        // }

        $scope.deleteExportAnimal = function(id) {
            vm.id = id;
            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'delete_modal.html',
                backdrop: 'static',
                scope: $scope,
                size: 'lg'
            });


        }
        vm.confirmDelete = function() {
            // alert(vm.id)
            service.deleteExportAnimalById(vm.id).then(function(data) {
            	if(data.code=="-2"){
            		toastr.warning(data.name, $translate.instant("toastr.del4"));
            	}
            	else{
            		vm.findBy();
            	}  				
                vm.modalInstance.close();
            });
        }
        vm.onAnimalSelected = function(item){
            vm.exportAnimal.animal = vm.animal;
        }
        vm.onFarmSelected = function(item){
            vm.exportAnimal.farm = vm.farm;
        }
		
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
                    url: vm.baseUrl + 'file/exportImAnimal',
                    data: {uploadfile: file}
                });

                file.upload.then(function (response) {
                    console.log(response);
                    file.result = response.data;
                   // getAllStudent(vm.pageIndex,vm.pageSize);
				    vm.findBy();
					//getTreeData(vm.pageIndex,vm.pageSize);
                    toastr.info($translate.instant("upload.importSuccess"), $translate.instant("toastr.info"));
                },function errorCallback(response) {
                    toastr.error($translate.instant("upload.importError"), $translate.instant("toastr.error"));
                });
            }
        };


        vm.importExAnimal= function () {
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
        ///////////////////////////
        vm.getAllCity = function() {
            fmsAdministrativeService.getAllCity().then(function(data) {
                vm.adminstrativeUnits_City = data;
                
            });
        }
//        vm.getAllCity();
        vm.onFmsadminstrativeUnitCitySelected = function(city) {
			if (city != null && city.id != null) {
                FarmService.getAllByParentId(city.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Dist = data;
		                vm.selectedDist = null;
                        vm.selectWards=null;
						vm.searchDto.district=null;
						vm.searchDto.ward=null;
                        vm.searchDto.listDistrictId=[];
                        console.log(data);
                        if(vm.adminstrativeUnits_Dist!=null && vm.adminstrativeUnits_Dist.length>0){
                            for(var i=0; i<vm.adminstrativeUnits_Dist.length;i++){
                                vm.searchDto.listDistrictId.push(vm.adminstrativeUnits_Dist[i].id);
                            }
                        }
					}
					else {
						vm.searchDto.district=null;
						vm.searchDto.ward=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
					}
	            });
			}
			else {
				vm.searchDto.district=null;
				vm.searchDto.ward=null;
	        	vm.selectedDist = null;
	        	vm.selectWards = null;
                vm.adminstrativeUnits_Dist = [];
				vm.adminstrativeUnits_Wards = [];
			}
			vm.findBy();
        }
        vm.onFmsadminstrativeUnitDistSelected = function(dist) {
			if (dist != null && dist.id != null) {
				FarmService.getAllByParentId(dist.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Wards = data;
						vm.searchDto.ward=null;
						vm.selectWards=null;
					}
					else {
						vm.searchDto.ward=null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Wards = [];
					}
					
	            });
			}
			else {
				vm.searchDto.ward=null;
	        	vm.selectWards = null;
				vm.adminstrativeUnits_Wards = [];
			}
			vm.findBy();
		}
		vm.onFmsadminstrativeUnitWardsSelected=function(item){
			vm.findBy();
		}
		//export
		$scope.myBlobObject=undefined;
        $scope.getFile=function(){
            console.log('download started, you can show a wating animation');
			if(changeDate()){
				vm.searchDto.type=-1;
				service.getStream(vm.searchDto)
                .then(function(data){//is important that the data was returned as Aray Buffer
                    console.log('Stream download complete, stop animation!');
                    $scope.myBlobObject=new Blob([data],{ type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
                },function(fail){
                    console.log('Download Error, stop animation and show error message');
                    $scope.myBlobObject=[];
                });
			}
            
        };

    }

})();