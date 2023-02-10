/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ImportAnimalSeed').controller('ImportAnimalSeedController', ImportAnimalSeedController);

    ImportAnimalSeedController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ImportAnimalSeedService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate',
        'Upload',
        'FarmService',
        'FmsAdministrativeService',
        'AnimalService',
        'OriginalService',
        'ProductTargetService'
    ];
    angular.module('Education.ImportAnimal').directive('fileDownload',function(){
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
    
    function ImportAnimalSeedController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr,$state,blockUI,$stateParams,utils,$translate,Upload,FarmService,fmsAdministrativeService,animalService,originalService,productTargetService) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
		vm.baseUrl = settings.api.baseUrl + settings.api.apiPrefix;
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.farms = []
        vm.modalFarm={}
        vm.farm = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedFarms = [];
        vm.count = 0;
        vm.listDistrictId=null;
        vm.getPageImportAnimal=function (){
            service.getPageImportAnimal(vm.pageIndex,vm.pageSize).then(function(data){
                vm.farms = data.content;
                vm.bsTableControl.options.data = vm.farms;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        //vm.getPageImportAnimal();
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

        animalService.getAll().then(function (data) {
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
				vm.province=administrativeUnit;
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				vm.adminstrativeUnits_City.push(administrativeUnit);
				fmsAdministrativeService.getAllByParentId(vm.province.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    
                });
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				vm.district=administrativeUnit;
				vm.province = administrativeUnit.parentDto;
				
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
                vm.adminstrativeUnits_Dist = [];
				if(vm.adminstrativeUnits_Dist==null) vm.adminstrativeUnits_Dist=[];
                // vm.adminstrativeUnits_Dist.push(administrativeUnit);
                fmsAdministrativeService.getAllByParentId(vm.province.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    
                });
				fmsAdministrativeService.getAllByParentId(vm.district.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                    
                });
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){
				vm.ward=administrativeUnit;				
                vm.district = administrativeUnit.parentDto;
				vm.province = administrativeUnit.parentDto.parentDto;
				
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

        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.farms,
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
                columns: service.getTableDefinitionSeedLevel(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selectedFarms.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedFarms = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedFarms);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedFarms.splice(index, 1);
                        });
                        
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedFarms = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;

                    //vm.getPageImportAnimal();
					vm.findBy();
                }
            }
        };
		vm.selecteAnimal = function(){
            vm.searchByCode();
        }
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
            if(vm.province!=null && vm.province.id!=null){
                vm.searchDto.cityId=vm.province.id;
            }else{
                vm.searchDto.cityId=null;
            }
            if(vm.district!=null && vm.district.id!=null){
                vm.searchDto.districtId=vm.district.id;
            }else{
                vm.searchDto.districtId=null;
            }
            if(vm.ward!=null && vm.ward.id!=null){
                vm.searchDto.wardsId=vm.ward.id;
            }else{
                vm.searchDto.wardsId=null;
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
          
			vm.searchDto.type=1;
            
            console.log(vm.searchDto);
           if(changeDate()){
			   service.getPageSearchImportAnimal(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
					vm.farms = data.content;
					vm.bsTableControl.options.data = vm.farms;
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

        $scope.editImportAnimal = function(id){
            $state.go("application.import_animal_seed_detail",{id:id})
        }

        vm.createImportAnimal = function (){
            vm.farm = {isNew : true};
            $state.go('application.import_animal_seed_detail');
        };

        $scope.deleteOriginal =function deleteOriginal(id){
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.deletefarms =function (){
            if(vm.selectedFarms.length == 0){
                toastr.warning("Bạn chưa chọn!")
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

        vm.confirmDelete =function (){
            vm.pageIndex = 1;
            var ids = vm.selectedFarms.map(function(element){
                return element.id;
            });
            service.deleteServiceByIds(ids,function success(data){
               // vm.getPageImportAnimal();
			   vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedFarms = [];
            },function failure(error){
                console.log(error);
            });
       }
       $scope.deleteImportAnimal = function(id){
        if(id){
            vm.recordId = id;
        }else {
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
       vm.confirmDeleteRecord = function(){
           if(vm.recordId){
            service.deleteImportAnimal(vm.recordId).then(function (data){
                if(data!=null && data.code=="-1"){
					toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
				}else if(data!=null && data.code=="-2"){
					toastr.warning($translate.instant("importAnimal.deleteImport"), $translate.instant("toastr.warning"));
				}
				else{
					toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
				}
				vm.findBy();
                vm.modalConfirmDelete.close(); 
                vm.selectedFarms = [];
            });
			/*,function failure(error){
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                vm.modalConfirmDelete.close(); 
            });*/
           }
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
                    url: vm.baseUrl + 'file/importExAnimal',
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


        vm.importAnimal= function () {
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
        ///
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
						vm.district=null;
						vm.ward=null;
                        vm.listDistrictId=[];
                        console.log(data);
                        
					}
					else {						
						vm.district=null;
						vm.ward=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
					}
	            });
			}
			else {
				vm.district=null;
				vm.ward=null;
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
                        vm.ward=null;
					}
					else {
						vm.ward=null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Wards = [];
					}
					
	            });
			}
			else {
				vm.ward=null;
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
				if(vm.searchDto==null)
					vm.searchDto={};
				vm.searchDto.type=1;
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

        // --------------------------------------SHOW ADD LIST ANIMAL------------------
        vm.modalPopupImportAnimal = null;
        vm.listImportAnimal = [];
        vm.individual = {};
        vm.showPopupImportAnimal = function(){
            vm.listIndividual = [];
            vm.listImportAnimal = [];
            vm.individual = {};
            vm.modalPopupImportAnimal = modal.open({
                animation: true,
                templateUrl: 'modal_add_list.html',
                scope: $scope,
                backdrop: 'static',
                windowClass:"customer-modal-lg",
                keyboard: false,
                size: 'lg'
            });
        }

        vm.onFarmSelected = function (item) {
            vm.importAnimal.farm = vm.farmSelected;
        };

        vm.onAnimalSelected = function (item) {
            /*vm.importAnimal.animal = vm.animalSelected;*/
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
            }

        }
        vm.getListSeedlevel = function () {
            service.getListSeedlevel().then(function (data) {
                vm.seedLevels = data;
            });
        }
        vm.getListSeedlevel();
        
        vm.getSeedLevelByOption = function(){
            return vm.seedLevels;
        }

        //--------------Popup Farm----------//
        vm.pageIndexFarm = 1, vm.pageSizeFarm = 10;
        vm.subModalInstance = {};

        vm.selectedFarms = [];
        vm.farmSelected = {};
        vm.showPopupFarm = function () {
            vm.importAnimal = {};
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
            service.getPageSearchFarm(vm.searchDtoFarm, vm.pageIndexFarm, vm.pageSizeFarm).then(function (data) {
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

        vm.addImportAnimal = function(){
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
            if (!vm.importAnimal.dayOld || vm.importAnimal.dayOld < 1) {
                toastr.warning($translate.instant("importAnimal.requiredDayOld"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.importAnimal.dayOld > 2147483647) {
                toastr.warning($translate.instant("importAnimal.exceedingDayOld"), $translate.instant("toastr.warning"));
                return;
            }
            if (!vm.importAnimal.productTarget || vm.importAnimal.productTarget == null) {
                toastr.warning($translate.instant("importAnimal.requiredProductTarget"), $translate.instant("toastr.warning"));
                return;
            }
            vm.importAnimal.type = 1;
            vm.listImportAnimal.push(angular.copy(vm.importAnimal));
            vm.importAnimal = {};
        }

        vm.saveListImportAnimal = function(){
            if(vm.importAnimal && vm.importAnimal.farm && vm.importAnimal.animal && vm.importAnimal.quantity > 0){
                vm.listImportAnimal.push(angular.copy(vm.importAnimal));
                if(vm.listImportAnimal.length > 0){
                    service.saveListImportAnimal(vm.listImportAnimal).then(function(data){
                        vm.listImportAnimal = [];
                        vm.modalPopupImportAnimal.close();
                        vm.importAnimal = {};
                    });
                }
            }else{
                if(vm.listImportAnimal.length > 0){
                    service.saveListImportAnimal(vm.listImportAnimal).then(function(data){
                        vm.listImportAnimal = [];
                        vm.modalPopupImportAnimal.close();
                        vm.importAnimal = {};
                    });
                }
            }
        }

        vm.updateQuantity = function(obj){
            obj.quantity = 0;
            if(obj.male && obj.male > 0){
                obj.quantity += obj.male/1;
            }
            if(obj.female && obj.female > 0){
                obj.quantity += obj.female/1;
            }
            if(obj.unGender && obj.unGender > 0){
                obj.quantity += obj.unGender/1;
            }
        }

        vm.listIndividual = [];
        vm.individuals = [];
        vm.addIndividual = function () {
            vm.indexIndividual = listIndividual.length;
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




    }

})();
