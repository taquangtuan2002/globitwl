/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ReportOfFarmer').controller('ReportOfFarmerController', ReportOfFarmerController);

    ReportOfFarmerController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'QuantityReportService',
		'FmsRegionService',		
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate',
		'FarmService',
        'FmsAdministrativeService',
        'AnimalService',
        'ReportOfFarmerService'
		
    ];
    
    function ReportOfFarmerController ($rootScope, $scope, $http, $timeout,settings, quantityReportService,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService, animalService, service) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
        vm.farm = null;
        var today = new Date();
        vm.currentYear=today.getFullYear();
        vm.isCallReport = false;
		$rootScope.$on('$translateChangeSuccess', function(event, current, previous) {
			if(vm.isCallReport){
				vm.getQuantityReport();
			}
		});
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.farmSizes = []
        vm.modalFarmSize={};
        vm.farmSize = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedFarmSize = [];
        vm.count = 0;
		vm.searchDto={};
		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.results=[];
        
        vm.selectedCity={};
        $scope.labels = ["Toàn tỉnh"];
        $scope.series = [];
        $scope.data = [];
        vm.viewType="table";
		$scope.years = [];
		vm.regionName=null;
		vm.provinceName=null;
		vm.districtName=null;
		vm.wardsName=null;
		vm.title=null;
		vm.isShow=true;
		for(var i=2000; i <= vm.currentYear; i++) {
			$scope.years.push(i);
		}
		vm.months=[
        	{
        		name:"Tháng 1",
        		value:1
        	},
        	{
        		name:"Tháng 2",
        		value:2
        	},        	
        	{
        		name:"Tháng 3",
        		value:3
        	},
        	{
        		name:"Tháng 4",
        		value:4
        	},
        	{
        		name:"Tháng 5",
        		value:5
        	},
        	{
        		name:"Tháng 6",
        		value:6
        	},
        	{
        		name:"Tháng 7",
        		value:7
        	},
        	{
        		name:"Tháng 8",
        		value:8
        	},
        	{
        		name:"Tháng 9",
        		value:9
        	},
        	{
        		name:"Tháng 10",
        		value:10
        	},
        	{
        		name:"Tháng 11",
        		value:11
        	},
			{
        		name:"Tháng 12",
        		value:12
        	}
        ];
        
        /** generate */
		
		vm.types =[
            {id:1, name: $translate.instant('report.up')},
            {id:-1, name: $translate.instant('report.down')},
            {id:0, name: $translate.instant('report.general_herd')}
        ]
        
        vm.paramDto={};
        vm.paramDto.periodType="2";//mặc định biểu đồ theo tháng
        vm.paramDto.startTimeType=1;//Lấy mốc đầu tháng - đầu năm
        vm.paramDto.type=1;//mặc định là phiếu nhập đàn
        vm.paramDto.currentYear=today.getFullYear().toString();
        var lastYear =today.getFullYear() -1;
        /*vm.paramDto.fromMonth=1;
        vm.paramDto.toMonth=today.getMonth()+1;
        vm.paramDto.fromYear=vm.currentYear;
        vm.paramDto.toYear=vm.currentYear;*/     
		vm.paramDto.fromMonth=today.getMonth();
        vm.paramDto.toMonth=today.getMonth();
		vm.month=vm.currentYear-1;
		console.log(vm.month);
        vm.paramDto.fromYear=lastYear.toString();
        vm.paramDto.toYear=vm.currentYear;
        vm.groupByItems=[
//        	{
//        		name:"Mã lô",
//        		value:"batchCode"
//        	},
        	{
        		name:"động vật",
        		value:"animalId"
        	},        	
        	{
        		name:"Loại động vật",
        		value:"animalParent"
        	},
        	{
        		name:"Loại động vật",
        		value:"animalType"
        	},
        	{
        		name:"Cơ sở",
        		value:"farm"
        	},
//        	{
//        		name:"Nguồn gốc",
//        		value:"original"
//        	},
        	{
        		name:"Xã",
        		value:"ward"
        	},
        	{
        		name:"Huyện",
        		value:"district"
        	},
        	{
        		name:"Tỉnh",
        		value:"province"
        	},
        	{
        		name:"Vùng sinh thái",
        		value:"region"
        	}//,
//        	{
//        		name:"Hướng sản phẩm",
//        		value:"productTarget"
//        	}
        ];
		vm.isRole=false;
		vm.isFamer=false;
		vm.isSdah=false;
		vm.isDistrict=false;
		vm.isWard=false;
		vm.isSdahView = false;
		
		//------Start--Phân quyền theo user đăng nhập-----------
		blockUI.start();
        quantityReportService.getCurrentUser().then(function (data) {
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
				vm.getCombo();
			}else{// trường hợp này thì phân quyền theo user
				if(vm.isRole==false){
					getAdministrativeUnitDtoByLoginUser();
					
				}
				
			}
            blockUI.stop();
		});
		
		vm.onSelectType = function () {
			if (vm.paramDto != null&& vm.paramDto.type != null) {
				if (vm.paramDto.type == 0) {
					vm.paramDto.periodType="0";//mặc định biểu đồ theo năm
					vm.paramDto.animalParentId = null;	//nếu là tổng đàn thì bỏ lọc theo loại động vật
				}else{
					vm.paramDto.periodType="2";//mặc định biểu đồ theo tháng
				}
			}
		}

        //get farm by userLogin
        function getFarmByUserLogin(){
            service.getFarmByUserLogin().then(function(data) {
                vm.listFarm = data;
                if(vm.listFarm != null && vm.listFarm.length == 1){
                    vm.farm = vm.listFarm[0];
                }
                console.log('vm.listFarm');
                console.log(vm.listFarm);
            });
        }
//        getFarmByUserLogin();

        
        /*
         * Loại động vật
         */
        vm.getListParentAnimal = function(){
        	animalService.getListParent().then(function (data) {
        		vm.parentAnimals = data;
            });
        }
		vm.getListParentAnimal();
		
		//--------------Popup Farm----------//
        vm.pageIndexFarm=1, vm.pageSizeFarm=10;
        vm.subModalInstance = {};

        vm.selectedFarms=[];
		vm.farmSelected={};
		vm.removeFarmSelected = function () {
			vm.farm = null;
		}

        vm.showPopupFarm = function () {
            vm.subModalInstance = modal.open({
                animation: true,
                templateUrl: 'choose_farm_modal.html',
                scope: $scope,
                size: 'lg'
            });
            vm.code=null;
            vm.selectedFarms=[];
			vm.farmSelected={};
			//lấy danh sách cơ sở chăn nuôi phân theo quyền
			if(vm.isRole){//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
				vm.findByFarm();
			}else{// trường hợp này thì phân quyền cơ sở chăn nuôi theo user
				if(vm.isRole==false){
					getAdministrativeUnitDtoByLoginUser();
				}
				
			}
			
           
            vm.subModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                   
                }
            }, function () {
				vm.farmCode=null;
                console.log("cancel");
            });
        }

        var closeModal = function () {
			
            vm.subModalInstance.close();

		};

        function checkAgreeFarm(){
            console.log("checkAgree");
            console.log(vm.selectedFarms);
            if(angular.isUndefined(vm.selectedFarms)|| vm.selectedFarms.length==0 ){
                toastr.warning($translate.instant("exportAnimal.notChooseFarm"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.agreeFarm=function () {
            if(checkAgreeFarm()){
                vm.farmSelected=vm.selectedFarms[0];
				vm.farm=vm.selectedFarms[0];
				
				//vm.findBy();
				vm.farmCode=null;
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
                    if( vm.selectedFarms!=null){
                        vm.selectedFarms=[];
                    }
                    $scope.$apply(function () {
                        vm.selectedFarms.push(row);
                    });
                },
              
                onUncheck: function (row, $element) {
                    
                    if( vm.selectedFarms!=null){
                        vm.selectedFarms=[];
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
		
		
		
		vm.enterSearchFarm = function(){
			// console.log(event.keyCode);
			if(event.keyCode == 13){//Phím Enter
				
				vm.searchByFarm();
			}
        };
		 vm.searchByFarm = function () {
            vm.pageIndexFarm = 1;
            vm.bsTableControlFarm.state.pageNumber = 1;
            vm.findByFarm();
        };
		vm.findByFarm=function () {
            if( vm.searchDtoFarm==null){
                vm.searchDtoFarm={};
            }
            if(vm.farmCode!=null&& vm.farmCode!=""){
                vm.searchDtoFarm.nameOrCode=vm.farmCode;
            }
            else{
                vm.searchDtoFarm.nameOrCode=null;
            }
			
            console.log("vm.searchDtoFarm");
            console.log(vm.searchDtoFarm);
            service.getPageSearchFarm(vm.searchDtoFarm,vm.pageIndexFarm,vm.pageSizeFarm).then(function(data) {
                vm.farms = data.content;
                vm.bsTableControlFarm.options.data = vm.farms;
                vm.bsTableControlFarm.options.totalRows = data.totalElements;
				console.log("vm.farms");
				console.log(vm.farms);
				if(vm.isFamer==true&& vm.farms!=null && vm.farms.length>0){
					if(vm.importAnimal==null)vm.importAnimal={};
					vm.farmName=vm.farms[0].name;
					vm.farmCode=vm.farms[0].code;
					vm.importAnimal.farm = vm.farms[0];
				}
				//console.log(vm.farms);
            });
        }
		
		//load user administrativeunit
		function getAdministrativeUnitDtoByLoginUser() {
            quantityReportService.getAdministrativeUnitDtoByLoginUser().then(function(data) {
                vm.adminstrativeUnits = data;
				if(vm.adminstrativeUnits!=null && vm.adminstrativeUnits.length>0){
					getDataByCombobox(vm.adminstrativeUnits[0]);
					searchFarmByCombobox(vm.adminstrativeUnits[0]);
				}
            });
        }
		function searchFarmByCombobox(administrativeUnit) {
			if( vm.searchDtoFarm==null){
                vm.searchDtoFarm={};
            }
			if(administrativeUnit.parentDto==null){
				vm.searchDtoFarm.province=administrativeUnit.id;
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				vm.searchDtoFarm.district=administrativeUnit.id;
				vm.searchDtoFarm.province = administrativeUnit.parentDto.id;
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){
				vm.searchDtoFarm.ward=administrativeUnit.id;				
                vm.searchDtoFarm.district = administrativeUnit.parentDto.id;
				vm.searchDtoFarm.province = administrativeUnit.parentDto.parentDto.id;
			}
			if(vm.isRole == false && vm.isFamer==true &&  vm.user!==null && vm.user.username!==null && vm.user.username!==''){//trường hợp tài khoản nông dân
                //gán tên cơ sở chăn nuôi
				vm.farmCode=vm.user.username;
                vm.searchDtoFarm.nameOrCode=vm.user.username;
            }
			
			vm.findByFarm();
			
            
        }
		function getDataByCombobox(administrativeUnit) {
			if( vm.paramDto==null){
                vm.paramDto={};
            }
			if(administrativeUnit.parentDto==null){
				vm.paramDto.provinceId=administrativeUnit.id;
				vm.paramDto.regionId=administrativeUnit.regionDto.id;
				
				if(vm.regions==null) vm.regions=[];
				vm.regions.push(administrativeUnit.regionDto);
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				vm.adminstrativeUnits_City.push(administrativeUnit);
				farmService.getAllByParentId(vm.paramDto.provinceId).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    
                });
				vm.provinceName=administrativeUnit.name;
				if(vm.paramDto.regionId!=null){				
					auService.getAllCityByRegion(vm.paramDto.regionId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_City = data;
							
						}

					});
				}
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				vm.paramDto.districtId=administrativeUnit.id;
				vm.paramDto.provinceId = administrativeUnit.parentDto.id;
				vm.paramDto.regionId=administrativeUnit.parentDto.regionDto.id;
				
				if(vm.regions==null) vm.regions=[];
				vm.regions.push(administrativeUnit.parentDto.regionDto);
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
				if(vm.adminstrativeUnits_Dist==null) vm.adminstrativeUnits_Dist=[];
				vm.adminstrativeUnits_Dist.push(administrativeUnit);
				farmService.getAllByParentId(vm.paramDto.districtId).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                    
                });
				vm.districtName=administrativeUnit.name;
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){
				vm.paramDto.wardId=administrativeUnit.id;				
                vm.paramDto.districtId = administrativeUnit.parentDto.id;
				vm.paramDto.provinceId = administrativeUnit.parentDto.parentDto.id;
				vm.paramDto.regionId=administrativeUnit.parentDto.parentDto.regionDto.id;
				if(vm.regions==null) vm.regions=[];
				vm.regions.push(administrativeUnit.parentDto.parentDto.regionDto);
				
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
				if(vm.adminstrativeUnits_Dist==null) vm.adminstrativeUnits_Dist=[];
				vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
				if(vm.adminstrativeUnits_Wards==null) vm.adminstrativeUnits_Wards=[];
				vm.adminstrativeUnits_Wards.push(administrativeUnit);
				vm.wardsName=administrativeUnit.name;
			}			
            
        }	
		
		//--------End ----Phân quyền-------------
        /** bussiness */

		vm.getCombo = function () {
            regionService.getAll().then(function (data) {
                console.log()
                if (data && data.length > 0) {
                    vm.regions = data;
                } else {
                    vm.regions = [];
                }
            });
        }
		//vm.getCombo();
		vm.getAllCity = function() {
			auService.getAllCity().then(function(data) {
                vm.adminstrativeUnits_City = data;                
            });
        }
//        vm.getAllCity();
        
		 
		//vm.getCombo();
		function validate(){			
			 return true;
		}
		
        vm.getQuantityReport=function (){
			vm.isCallReport = true;
			if(vm.paramDto.type==0){
				vm.paramDto.startTimeType=null;
			}
			// vm.title=$translate.instant('report.provinceIncreaseReport')+ " ";
			
			// if(vm.wardsName!=null){
			// 	vm.title=vm.title+ vm.wardsName+ " ";
			// }else if(vm.districtName!=null){
			// 	vm.title=vm.title+ vm.districtName+ " ";
			// }
			// else if(vm.provinceName!=null){
			// 	vm.title=vm.title+ vm.provinceName+ " ";
			// }
			// else if(vm.regionName!=null){
			// 	vm.title=vm.title+ vm.regionName+ " ";
			// }
			
			if (vm.paramDto.type == -1) {	//giảm đàn
				vm.title=$translate.instant('report.provinceReductionReport')+" ";
			}
			else if (vm.paramDto.type == 1) {	//tăng đàn
				vm.title=$translate.instant('report.provinceIncreaseReport')+" ";
			}
			else if (vm.paramDto.type == 0) {	//Tổng đàn
				vm.title=$translate.instant('report.provinceGeneralReport')+" ";
			}

			if (vm.farm != null && vm.farm.name != null) {
				vm.title=vm.title + "- " + $translate.instant('report.farm')+" " + vm.farm.name+ " ";
			}
			else if(vm.wardsName!=null){
				vm.title=vm.title+ vm.wardsName+ " ";
			}else if(vm.districtName!=null){
				vm.title=vm.title+ vm.districtName+ " ";
			}
			else if(vm.provinceName!=null){
				vm.title=vm.title+ vm.provinceName+ " ";
			}
			else if(vm.regionName!=null){
				vm.title=vm.title+ vm.regionName+ " ";
			}

			if(vm.paramDto.periodType==0){
				vm.title=vm.title+ vm.paramDto.toYear+" ("+$translate.instant('report.item')+")";
			}else if(vm.paramDto.periodType==1){
				vm.title=vm.title+ vm.paramDto.toMonth+'/'+vm.paramDto.currentYear+" ("+$translate.instant('report.item')+")";
			}else if(vm.paramDto.periodType==2){
				if(vm.paramDto.toMonth!=null && vm.paramDto.toMonth==vm.paramDto.fromMonth && vm.paramDto.toYear!=null && vm.paramDto.toYear==vm.paramDto.fromYear){
					vm.title=vm.title+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear+" ("+$translate.instant('report.item')+")";
				}else
				vm.title=vm.title+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear+ " - "+vm.paramDto.toMonth+'/'+vm.paramDto.toYear+" ("+$translate.instant('report.item')+")";
			}
        	vm.paramDto.groupByItems=[];
        	vm.rows=[];
        	var sortStr="";
        	if(vm.paramDto.periodType=='0'){
        		vm.rowsPivot = [{
                    "uniqueName": "yearReport",
                    "sort": "asc",
                    "caption":$translate.instant('report.year')            	                    
                }];
        		vm.rows.push("yearReport");
        		sortStr="yearReport."+vm.paramDto.toYear;
        	}
        	else if(vm.paramDto.periodType=='2'){
        		vm.rowsPivot = [{
        			"uniqueName": "monthInYear",
                    "sort": "asc",
                    "caption":$translate.instant('report.month')           	                    
                }];
        		vm.rows.push("monthReport");
        		vm.rows.push("yearReport");
        		sortStr="monthInYear."+vm.paramDto.toMonth+"/"+vm.paramDto.currentYear;
        	}
        	console.log(sortStr);
        	vm.paramDto.groupByItems.push('animalParent');
			if(vm.paramDto.periodType==0){
				vm.paramDto.fromYear = vm.paramDto.toYear;
			}
            console.log(vm.paramDto.groupByItems.includes('district'));
            
            //Nếu chọn farm thì lọc theo farm
            if (vm.farm != null && vm.farm.id != null) {
                vm.paramDto.farmId = vm.farm.id;
            }
            else{
                vm.paramDto.farmId = null;
            }
        	
            quantityReportService.getQuantityReport(vm.paramDto).then(function(data){
            	vm.results=data;
            	if(data==null || data==[] ||data.length==0){
					vm.isShow=false;
				}else{
					vm.isShow=true;
				}
            	//Pivot here     
            	var pivot = new WebDataRocks({
            	    container: "#wdr-component",
            	    toolbar: true,
            	    height: 530,
            	    report: {
            	        dataSource: {
            	            data: vm.results
            	        },
            	        "slice": {
            	            "rows": vm.rowsPivot,
            	            "columns": [{
        	                    "uniqueName": "parentlName",
        	                    "sort": "asc",
        	                    "caption":$translate.instant('report.typeanimal')
            	            }],
            	            "measures": [{
            	                "uniqueName": "quantity",
            	                //"aggregation": "sum",
            	                "caption":$translate.instant('report.quantity')
            	            }],
            	            "sorting": {
            	                "row": {
            	                    "type": "desc",
            	                    "tuple": [
            	                    	sortStr
            	                    ],
            	                    "measure": "quantity"
            	                }
            	            }
            	        },
            	        options: {
            	            grid: {
            	                title: vm.title,
            	                type:"compact",
            	                "showGrandTotals": "rows"
            	            }
            	        }
            	    }
            		,
        	        reportcomplete: function() {
        	        	pivot.off("reportcomplete");
        	        	createChart();
        	        },
        	        global: {
        	    		// replace this path with the path to your own translated file
        	    		localization: $translate.use()=="vi-VN"?"assets/scripts/pivot/vi.json":"assets/scripts/pivot/en.json"
        	    	}
            	});
            	//createChart
            	function createChart() {
        		    pivot.highcharts.getData({
        		        type: "column"		        		
        		    }, function(data) {
        		    	data.chart.options3d={
        		    			enabled: true,
        		                alpha: 0,
        		                beta: 0,
        		                depth: 20,
        		                viewDistance: 10
						}
						
						if (vm.paramDto.type == -1) {	//giảm đàn
							data.title.text=$translate.instant('report.provinceReductionReport')+" ";
						}
						else if (vm.paramDto.type == 1) {	//tăng đàn
							data.title.text=$translate.instant('report.provinceIncreaseReport')+" ";
						}
						else if (vm.paramDto.type == 0) {	//Tổng đàn
							data.title.text=$translate.instant('report.provinceGeneralReport')+" ";
						}

						if (vm.farm != null && vm.farm.name != null) {
							data.title.text=data.title.text + "- " + $translate.instant('report.farm')+" " + vm.farm.name+ " ";
						}
						else if(vm.wardsName!=null){
							data.title.text=data.title.text+ vm.wardsName+ " ";
						}else if(vm.districtName!=null){
							data.title.text=data.title.text+ vm.districtName+ " ";
						}
						else if(vm.provinceName!=null){
							data.title.text=data.title.text+ vm.provinceName+ " ";
						}
						else if(vm.regionName!=null){
							data.title.text=data.title.text+ vm.regionName+ " ";
						}
						// if(vm.wardsName!=null){
						// 	data.title.text=data.title.text+ vm.wardsName+ " ";
						// }else if(vm.districtName!=null){
						// 	data.title.text=data.title.text+ vm.districtName+ " ";
						// }
						// else if(vm.provinceName!=null){
						// 	data.title.text=data.title.text+ vm.provinceName+ " ";
						// }
						// else if(vm.regionName!=null){
						// 	data.title.text=data.title.text+ vm.regionName+ " ";
						// }

						if(vm.paramDto.periodType==0){
							data.title.text=data.title.text+ vm.paramDto.toYear+" ("+$translate.instant('report.item')+")";
						}else if(vm.paramDto.periodType==1){
							data.title.text=data.title.text+ vm.paramDto.toMonth+'/'+vm.paramDto.currentYear+" ("+$translate.instant('report.item')+")";
						}else if(vm.paramDto.periodType==2){
							if(vm.paramDto.toMonth!=null && vm.paramDto.toMonth==vm.paramDto.fromMonth && vm.paramDto.toYear!=null && vm.paramDto.toYear==vm.paramDto.fromYear){
								data.title.text=data.title.text+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear+" ("+$translate.instant('report.item')+")";
							}else
							data.title.text=data.title.text+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear+ " - "+vm.paramDto.toMonth+'/'+vm.paramDto.toYear+" ("+$translate.instant('report.item')+")";
						}
						if(data!=null && data.series!=null && data.series.length<=3){
							data.colors = ['#009800','#ff9800', '#BA21FB'];		
						}
						data.plotOptions={};
        		    	data.plotOptions.pie={};
        		    					
						data.plotOptions.series={};
						data.plotOptions.series.pointWidth=40;//độ rộng của biểu đồ	
						data.xAxis.title.text="";//bỏ title trục ngang
						data.yAxis[0].title.text="";//bỏ title trục dọc
						console.log(data);
        		        Highcharts.chart('highchartsContainer', data);
        		    }, function(data) {
        		    	data.chart.options3d={
        		    			enabled: true,
        		                alpha: 0,
        		                beta: 0,
        		                depth: 20,
        		                viewDistance: 10
        		    	}
						// data.title.text=$translate.instant('report.provinceIncreaseReport')+" ";
						// if(vm.wardsName!=null){
						// 	data.title.text=data.title.text+ vm.wardsName+ " ";
						// }else if(vm.districtName!=null){
						// 	data.title.text=data.title.text+ vm.districtName+ " ";
						// }
						// else if(vm.provinceName!=null){
						// 	data.title.text=data.title.text+ vm.provinceName+ " ";
						// }
						// else if(vm.regionName!=null){
						// 	data.title.text=data.title.text+ vm.regionName+ " ";
						// }
						
						if (vm.paramDto.type == -1) {	//giảm đàn
							data.title.text=$translate.instant('report.provinceReductionReport')+" ";
						}
						else if (vm.paramDto.type == 1) {	//tăng đàn
							data.title.text=$translate.instant('report.provinceIncreaseReport')+" ";
						}

						if (vm.farm != null && vm.farm.name != null) {
							data.title.text=data.title.text + "- " + $translate.instant('report.farm')+" " + vm.farm.name+ " ";
						}
						else if(vm.wardsName!=null){
							data.title.text=data.title.text+ vm.wardsName+ " ";
						}else if(vm.districtName!=null){
							data.title.text=data.title.text+ vm.districtName+ " ";
						}
						else if(vm.provinceName!=null){
							data.title.text=data.title.text+ vm.provinceName+ " ";
						}
						else if(vm.regionName!=null){
							data.title.text=data.title.text+ vm.regionName+ " ";
						}

						if(vm.paramDto.periodType==0){
							data.title.text=data.title.text+ vm.paramDto.toYear+" ("+$translate.instant('report.item')+")";
						}else if(vm.paramDto.periodType==1){
							data.title.text=data.title.text+ vm.paramDto.toMonth+'/'+vm.paramDto.currentYear+" ("+$translate.instant('report.item')+")";
						}else if(vm.paramDto.periodType==2){
							if(vm.paramDto.toMonth!=null && vm.paramDto.toMonth==vm.paramDto.fromMonth && vm.paramDto.toYear!=null && vm.paramDto.toYear==vm.paramDto.fromYear){
								data.title.text=data.title.text+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear+" ("+$translate.instant('report.item')+")";
							}else
							data.title.text=data.title.text+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear+ " - "+vm.paramDto.toMonth+'/'+vm.paramDto.toYear+" ("+$translate.instant('report.item')+")";
						}
						if(data!=null && data.series!=null && data.series.length<=3){
							data.colors = ['#009800','#ff9800', '#BA21FB'];		
						}
						
						data.plotOptions={};
        		    	data.plotOptions.pie={};
						data.plotOptions.series={};
						data.plotOptions.series.pointWidth=40;//độ rộng của biểu đồ	
						data.xAxis.title.text="";//bỏ title trục ngang
						data.yAxis[0].title.text="";//bỏ title trục dọc
        		        Highcharts.chart('highchartsContainer', data);
        		    });
                }
            });
        }
        
		vm.onFmsRegionSelected=function(region){
			if(region!=null && region.id!=null){
				vm.regionName=region.name;
				auService.getAllCityByRegion(region.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_City = data;
						vm.selectedCity=null;
		                vm.selectedDist = null;
		                vm.selectWards=null;
						vm.paramDto.provinceId=null;
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
						vm.provinceName=null;
						vm.districtName=null;
						vm.wardsName=null;
					}
					else {
						vm.paramDto.provinceId=null;
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
						vm.selectedCity=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
						vm.adminstrativeUnits_City=[];
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
						vm.provinceName=null;
						vm.districtName=null;
						vm.wardsName=null;
					}				
					
	            });
			}else {
						vm.paramDto.provinceId=null;
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
						vm.selectedCity=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
						vm.adminstrativeUnits_City=[];
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
						vm.regionName=null;
						vm.provinceName=null;
						vm.districtName=null;
						vm.wardsName=null;
					}		
		}
		
		vm.onFmsadminstrativeUnitCitySelected = function(city) {
			vm.selectedCity = city;
			if (city != null && city.id != null) {
				vm.provinceName=city.name;
				farmService.getAllByParentId(city.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Dist = data;
		                vm.selectedDist = null;
		                vm.selectWards=null;
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
						vm.districtName=null;
						vm.wardsName=null;
					}
					else {
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
						vm.districtName=null;
						vm.wardsName=null;
					}
	            });
			}
			else {
				vm.paramDto.districtId=null;
				vm.paramDto.wardId=null;
	        	vm.selectedDist = null;
	        	vm.selectWards = null;
                vm.adminstrativeUnits_Dist = [];
				vm.adminstrativeUnits_Wards = [];
				vm.districtName=null;
				vm.wardsName=null;
				vm.provinceName=null;
			}
		}
        
        vm.onFmsadminstrativeUnitDistSelected = function(dist) {
			if (dist != null && dist.id != null) {
				vm.districtName=dist.name;
				farmService.getAllByParentId(dist.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Wards = data;
					}
					else {
						vm.paramDto.wardId=null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Wards = [];
						vm.wardsName=null;
					}
					vm.paramDto.wardId=null;
					vm.selectWards=null;
					vm.wardsName=null;
	            });
			}
			else {
				vm.paramDto.wardId=null;
	        	vm.selectWards = null;
				vm.adminstrativeUnits_Wards = [];
				vm.wardsName=null;
				vm.districtName=null;
			}
		}

		vm.showCalcaulation = function (){
          
            vm.modal = modal.open({
                animation: true,
                templateUrl: 'calculation.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });

        };


		vm.onFmsadminstrativeUnitWardsSelected=function(item){
			if(item!=null){
				vm.wardsName=item.name;
			}else{
				vm.wardsName=null;
			}
		}
        console.log(quantityReportService.getTableDefinition());
        
        
    }

})();
