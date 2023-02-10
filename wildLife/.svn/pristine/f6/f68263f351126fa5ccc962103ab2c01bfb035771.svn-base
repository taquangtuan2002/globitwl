/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('SumQuantityReportDataController', SumQuantityReportDataController);

    SumQuantityReportDataController.$inject = [
			'$rootScope',
			'$scope',
			'$http',
			'$timeout',
			'settings',
			'SumQuantityReportDataService',
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
			'AnimalService'
    ];
    
    function SumQuantityReportDataController ($rootScope, $scope, $http, $timeout,settings,service,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService, animalService) {
		$scope.$on('$viewContentLoaded', function() {
				// initialize core components
				App.initAjax();
		});

		$rootScope.settings.layout.pageContentWhite = true;
		$rootScope.settings.layout.pageBodySolid = false;
		$rootScope.settings.layout.pageSidebarClosed = false;

		/** declare */
		var vm = this;
		var today = new Date();
		vm.currentYear=today.getFullYear();
		
		// blockUI.stop();
		// blockUI.start();
		vm.recordId = null;
		vm.pageIndex = 1;
		vm.pageSize = 25;
		vm.farmSizes = []
		vm.modalFarmSize={};
		vm.farmSize = {isNew : false};
		vm.modalConfirmDelete = {};
		vm.selectedFarmSize = [];
		vm.count = 0;
		vm.viewCheckDuplicateCode = {};
		vm.tempCode = '';
		vm.results=[];
		
		vm.listYear = [];
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

		vm.listYear = [

			"2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000"

		]
        
        /** generate */
		
		
        vm.paramDto={};
        vm.paramDto.periodType="0";
        vm.paramDto.currentYear=today.getFullYear().toString();
        vm.paramDto.fromMonth=1;
        vm.paramDto.toMonth=12;
        vm.paramDto.fromYear=vm.currentYear;
        vm.paramDto.toYear=vm.currentYear;
        vm.groupByItems=[
//        	{
//        		name:"Mã lô",
//        		value:"batchCode"
//        	},
        	{
        		name:"Động vật",
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
				vm.animals = [];

				vm.searchDto = {};

        $scope.years = [];
        var today = new Date();
        vm.currentYear=today.getFullYear();
		for(var i=vm.currentYear; i >= 2000; i--) {
			$scope.years.push({value: i, name: i+""});
        }
        vm.pageSizeReportForm = 25;
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
                vm.paramDto.provinceId = administrativeUnit.id;
                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit);
                service.getAllByParentId(vm.paramDto.provinceId).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                vm.paramDto.districtId = administrativeUnit.id;
                vm.paramDto.provinceId = administrativeUnit.parentDto.id;
                vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
                vm.adminstrativeUnits_Dist = [];
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit);
                service.getAllByParentId(vm.paramDto.provinceId).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                });
                service.getAllByParentId(vm.paramDto.districtId).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                vm.paramDto.communeId = administrativeUnit.id;
                vm.paramDto.districtId = administrativeUnit.parentDto.id;
                vm.paramDto.provinceId = administrativeUnit.parentDto.parentDto.id;
                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
                if (vm.adminstrativeUnits_Wards == null) vm.adminstrativeUnits_Wards = [];
                vm.adminstrativeUnits_Wards.push(administrativeUnit);
            }
            if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') {//trường hợp tài khoản nông dân
                vm.textSearch = vm.user.username;
            }
		}
		function getAllProvince() {
            service.getAdminstrativeUnitsCity().then(function (data) {
                vm.adminstrativeUnits_City = data || [];
            });
        }
		vm.getAllAnimal = function(){
			service.getAllAnimal().then(function(data){
				if(data != null){
					vm.animals = data;
				}
			})
		}
		vm.getAllAnimal();

		vm.getListAnimalClass = function () {
			animalService.getListAnimalClass().then(function (data) {
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

			// vm.getListAnimalOrdo();
			vm.getListAnimalOrdoParam();
		}

		vm.getListAnimalOrdo = function (callSearchFunction) {
			animalService.getListAnimalOrdo(vm.paramDto.animalClass).then(function (data) {
				vm.listAnimalOrdo = [];
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

		// Sửa API GET thành POST
		vm.getListAnimalOrdoParam = function(callSearchFunction) {
			animalService.getListAnimalOrdoParam(vm.paramDto).then(function (data) {
				vm.listAnimalOrdo = [];
				vm.ordo = null;
				vm.family = null;
				data.forEach(item => {
					var title = '-';
					if (item && item.trim().length > 0) {
						title = item;
					}
					vm.listAnimalOrdo.push({ name: title });
				});
				vm.getListAnimalFamilyParam(true);
			});
		}

		vm.getListAnimalFamily = function (callSearchFunction) {
			animalService.getListAnimalFamily(vm.paramDto.animalClass, vm.paramDto.ordo).then(function (data) {
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

		// Sửa API GET thành POST
		vm.getListAnimalFamilyParam = function (callSearchFunction) {
			animalService.getListAnimalFamilyParam(vm.paramDto).then(function (data) {
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
		// vm.getListAnimalOrdo();
		// vm.getListAnimalFamily();

		


		vm.animalClassSelected = function () {
			if (vm.paramDto.animalClass && vm.paramDto.animalClass == '-') {
				vm.paramDto.animalClass = null;
				vm.paramDto.ordo = null;
				vm.paramDto.family = null;
			}
			else {
				vm.paramDto.animalClass = vm.paramDto.animalClass;
				vm.paramDto.ordo = null;
				vm.paramDto.family = null;
			}
			// vm.getListAnimalOrdo(true);
			vm.getListAnimalOrdoParam(true);
		}

		vm.animalOrdoSelected = function () {
			if (vm.paramDto.ordo && vm.paramDto.ordo == '-') {
				vm.paramDto.ordo = null;
				vm.paramDto.family = null;
			}
			else {
				vm.paramDto.ordo = vm.paramDto.ordo;
				vm.paramDto.family = null;
			}
			// vm.getListAnimalFamily(true);
			vm.getListAnimalFamilyParam(true);
		}

		vm.familySelected = function () {
			if (vm.paramDto.family && vm.paramDto.family == '-') {
				vm.paramDto.family = null;
			}
			else {
				vm.paramDto.family = vm.paramDto.family;
			}
			vm.searchByCode();
		}
		vm.searchByCode = function () {
			if (vm.paramDto == null) {
				vm.paramDto = {};
			}
			animalService.getPageSearchAnimal(vm.paramDto, 1, 1000000).then(function (data) {
				vm.animals = data.content;
			});
		}


		//--------End ----Phân quyền-------------
        /** bussiness */
        vm.getQuantityReport=function (){
			vm.title=$translate.instant('report.provinceSumReport')+ " ";
			//Title: title + province + distric + ward
			if(vm.wardsName!=null){
				vm.title=vm.title+ vm.wardsName+ ", ";
			}
			if(vm.districtName!=null){
				vm.title=vm.title+ vm.districtName+ ", ";
			}
			if(vm.provinceName!=null){
				vm.title=vm.title+ vm.provinceName+ " ";
			}
			if(vm.regionName!=null){
				vm.title=vm.title+", "+ vm.regionName+ " ";
			}
        	vm.paramDto.groupByItems=[];
        	var sortStr="";
        	if(vm.paramDto.periodType=='0'){
        		vm.rowsPivot = [{
							"uniqueName": "year",
							"sort": "asc",
							"caption":$translate.instant('report.year')            	                    
						}];
        		sortStr="yearReport."+vm.paramDto.toYear;
        	}
					// vì dto trên server nó khác tên
					
					vm.paramDto.animalClass = vm.paramDto.animalClass;
					vm.paramDto.animalOrdo = vm.paramDto.ordo;
					vm.paramDto.animalFamily = vm.paramDto.family;
					// vm.paramDto.year = vm.paramDto.yea;
					
					service.reportAnimalDataAccordingBySearch(vm.paramDto).then(function(data){
						vm.results=data;
						console.log(vm.paramDto)
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
            	        slice: {
            	            rows: vm.rowsPivot,
            	            columns: [{
        	                    uniqueName: "animalName",
        	                    sort: "asc",
        	                    caption:$translate.instant('report.typeanimal')
            	            }],
            	            measures: [{
            	                uniqueName: "totalAnimalByYear",
            	                //"aggregation": "sum",
            	                caption:$translate.instant('report.quantity')
            	            }
            	            ],

            	            sorting: {
            	                row: {
            	                    type: "desc",
            	                    tuple: [
            	                    	sortStr
            	                    ],
            	                    measure: "totalAnimalByYear"
            	                }
            	            }
            	        },
            	        options: {
            	            grid: {
            	                title: vm.title,
            	                type:"compact",
            	            	showTotals: "rows",
            	                showGrandTotals: "rows"
								
            	            }
            	        },
            	        localization: "assets/scripts/pivot/vi.json"
            	    }
            		,
        	        reportcomplete: function() {
        	        	pivot.off("reportcomplete");
        	        	createChart();
        	        },
        	        global: {
        	    		// replace this path with the path to your own translated file
        	    		localization: "assets/scripts/pivot/vi.json"
        	    	}
            	});
            	//createChart
            	function createChart() {
        		    pivot.highcharts.getData({
        		        //type: "column"//hien thi bang theo cot
						//Tran huu dat start
						type: "line"
						//Tran huu dat end		        		
        		    }, function(data) {
        		    	console.log(data);
        		    	data.chart.options3d={
        		    			enabled: true,
        		                alpha: 0,
        		                beta: 0,
        		                depth: 20,
        		                viewDistance: 10
        		    	}
						if(data!=null && data.series!=null && data.series.length<=3){
							data.colors = ['#009800','#ff9800', '#95CEFF'];		
						}
						
        		    	data.plotOptions={};
        		    	data.plotOptions.pie={};
        		    	data.plotOptions.pie.colors = ['blue', 'red', '#FF0000'];
						data.plotOptions.series={};
						data.plotOptions.series.pointWidth=40;//độ rộng của biểu đồ
        		    	data.title.text=$translate.instant('report.provinceSumReport')+ " ";
						
						if(vm.wardsName!=null){
							data.title.text=data.title.text+ vm.wardsName+ ", ";
						}
						if(vm.districtName!=null){
							data.title.text=data.title.text+ vm.districtName+ ", ";
						}
						if(vm.provinceName!=null){
							data.title.text=data.title.text+ vm.provinceName+ " ";
						}
						if(vm.regionName!=null){
							data.title.text=data.title.text+", "+ vm.regionName+ " ";
						}
						if(vm.paramDto.periodType==0){
							data.title.text=data.title.text+ " ("+$translate.instant('report.item')+")";
						}else if(vm.paramDto.periodType==1){
							data.title.text=data.title.text+ " ("+$translate.instant('report.item')+")";
						}
						data.xAxis.title.text="";//bỏ title trục ngang
						data.yAxis[0].title.text="";//bỏ title trục dọc
            		    console.log(data);
        		        Highcharts.chart('highchartsContainer', data);
        		    }, function(data) {
        		    	console.log(data);
        		    	data.chart.options3d={
        		    			enabled: true,
        		                alpha: 0,
        		                beta: 0,
        		                depth: 20,
        		                viewDistance: 10
        		    	}
						if(data!=null && data.series!=null && data.series.length<=3){
							data.colors = ['#009800','#ff9800', '#95CEFF'];		
						}
        		    	data.plotOptions={};
        		    	data.plotOptions.pie={};
        		    	data.plotOptions.pie.colors = ['blue', 'red', '#FF0000'];
						data.plotOptions.series={};
						data.plotOptions.series.pointWidth=40;//độ rộng của biểu đồ
        		    	data.title.text=$translate.instant('report.provinceSumReport')+" ";
						if(vm.wardsName!=null){
							data.title.text=data.title.text+ vm.wardsName+ " ";
						}else if(vm.districtName!=null){
							data.title.text=data.title.text+ vm.districtName+ " ";
						}
						else if(vm.provinceName!=null){
							data.title.text=data.title.text+ vm.provinceName+ " ";
						}
						else if(vm.regionName!=null){
							// data.title.text=data.title.text+ vm.regionName+ " ";
						}
						if(vm.paramDto.periodType==0){
							// data.title.text=data.title.text+ vm.paramDto.toYear+" ("+$translate.instant('report.item')+")";
						}else if(vm.paramDto.periodType==1){
							// data.title.text=data.title.text+ vm.paramDto.toMonth+'/'+vm.paramDto.currentYear+" ("+$translate.instant('report.item')+")";
						}
						data.xAxis.title.text="";//bỏ title trục ngang
						data.yAxis[0].title.text="";//bỏ title trục dọc
        		    	console.log(data);
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
			console.log(city);
			vm.selectedCity = city;
			if (city != null && city.id != null) {
				
				vm.provinceName=city.name;
				farmService.getAllByParentId(city.id).then(function (data) {
					
					if (data != null && data.length > 0) {						
		                vm.adminstrativeUnits_Dist = data;
						if(data.length==1){
							auService.getChildrenByParentId(city.id).then(function (data) {
								if (data != null && data.length > 0) {
									vm.adminstrativeUnits_Dist = data;
								}
							});
						}
		                vm.selectedDist = null;
		                vm.selectWards=null;
						vm.paramDto.districtId=null;
						vm.paramDto.communeId=null;
						vm.districtName=null;
						vm.wardsName=null;
					}
					else {
						vm.paramDto.districtId=null;
						vm.paramDto.communeId=null;
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
				vm.paramDto.communeId=null;
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
						if(data.length== 1&&  vm.isFamer == false){
							auService.getChildrenByParentId(dist.id).then(function (data) {
								if (data != null && data.length > 0) {
									vm.adminstrativeUnits_Wards = data;
								}
							});
						
						}
					}
					else {
						vm.paramDto.communeId=null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Wards = [];
						vm.wardsName=null;
					}
					vm.paramDto.communeId=null;
					vm.selectWards=null;
					vm.wardsName=null;
	            });
			}
			else {
				vm.paramDto.communeId=null;
	        	vm.selectWards = null;
				vm.adminstrativeUnits_Wards = [];
				vm.wardsName=null;
				vm.districtName=null;
			}
		}
		vm.onFmsadminstrativeUnitWardsSelected=function(item){
			if(item!=null){
				vm.paramDto.communeId=item.id;
				vm.wardsName=item.name;
			}else{
				vm.wardsName=null;
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
        console.log(service.getTableDefinition());
    }

})();
