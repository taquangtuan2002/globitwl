/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('ComparePredictTheNumberOfLiveMeatSlightlyController', ComparePredictTheNumberOfLiveMeatSlightlyController);

    ComparePredictTheNumberOfLiveMeatSlightlyController.$inject = [
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
		'AnimalService'
		
    ];
    
    function ComparePredictTheNumberOfLiveMeatSlightlyController ($rootScope, $scope, $http, $timeout,settings,service,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService,animalService) {
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
        vm.currentMonth = today.getMonth() + 1;
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
		for(var i=vm.currentYear; i <= (vm.currentYear + 10); i++) {
			$scope.years.push(i);
		}
		vm.months = [];
		for (let i = vm.currentMonth; i <= 12; i++) {
			var month = {value: i, name: "Tháng " + i};
			vm.months.push(month);
		}
        
        /** generate */
		
		
        vm.paramDto={};
        vm.paramDto.periodType="1";
        vm.paramDto.currentYear=today.getFullYear().toString();
        
//        for (var i = 0; i < vm.months.length; i++) {
//			if(vm.months[i].value==(today.getMonth()+1)){
//				vm.paramDto.fromMonth=vm.months[i];
//			}
//			if(vm.months[i].value==(today.getMonth()+2)){
//				vm.paramDto.toMonth=vm.months[i];
//			}
//		}
        vm.paramDto.fromMonth=(today.getMonth()+1).toString();
        vm.paramDto.toMonth=(today.getMonth()+2).toString();
        if(vm.paramDto.periodType=="1"){
        	vm.paramDto.fromYear=today.getFullYear().toString();
        	vm.paramDto.toYear=today.getFullYear().toString();
        }
        else{
        	vm.paramDto.fromYear=today.getFullYear().toString();
        	vm.paramDto.toYear=(today.getFullYear()+1).toString();
        }
        vm.groupByItems=[
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
        	}
        ];
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
			if( vm.paramDto==null){
                vm.paramDto={};
            }
			if(administrativeUnit.parentDto==null){
				vm.getCombo();
				vm.paramDto.provinceId=administrativeUnit.id;
				vm.paramDto.regionId=administrativeUnit.regionDto.id;				
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				
				if(vm.paramDto.regionId!=null){
				
					auService.getAllCityByRegion(vm.paramDto.regionId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_City = data;
							
						}

					});
				}
				farmService.getAllByParentId(vm.paramDto.provinceId).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    
                });
				vm.provinceName=administrativeUnit.name;
				vm.regionName=administrativeUnit.regionDto.name;
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				vm.getCombo();
				vm.paramDto.districtId=administrativeUnit.id;
				vm.paramDto.provinceId = administrativeUnit.parentDto.id;
				vm.paramDto.regionId=administrativeUnit.parentDto.regionDto.id;
				
	
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				//vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
				if(vm.adminstrativeUnits_Dist==null) vm.adminstrativeUnits_Dist=[];
				vm.adminstrativeUnits_Dist.push(administrativeUnit);
				farmService.getAllByParentId(vm.paramDto.districtId).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                    
                });
				vm.districtName=administrativeUnit.name;
				vm.provinceName=administrativeUnit.parentDto.name;
				vm.regionName=administrativeUnit.parentDto.regionDto.name;
				if(vm.paramDto.regionId!=null){
				
					auService.getAllCityByRegion(vm.paramDto.regionId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_City = data;
							
						}

					});
				}
				if(vm.paramDto.provinceId!=null){
				
					auService.getChildrenByParentId(vm.paramDto.provinceId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_Dist = data;
							
						}

					});
				}

				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){
				vm.getCombo();
				vm.paramDto.wardId=administrativeUnit.id;				
                vm.paramDto.districtId = administrativeUnit.parentDto.id;
				vm.paramDto.provinceId = administrativeUnit.parentDto.parentDto.id;
				vm.paramDto.regionId=administrativeUnit.parentDto.parentDto.regionDto.id;
				if(vm.regions==null) vm.regions=[];
				//vm.regions.push(administrativeUnit.parentDto.parentDto.regionDto);
				
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				//vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
				if(vm.adminstrativeUnits_Dist==null) vm.adminstrativeUnits_Dist=[];
				//vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
				if(vm.adminstrativeUnits_Wards==null) vm.adminstrativeUnits_Wards=[];
				//vm.adminstrativeUnits_Wards.push(administrativeUnit);
				if( vm.isFamer == false){
					vm.wardsName=administrativeUnit.name;				
				}
				vm.districtName=administrativeUnit.parentDto.name;
				vm.provinceName=administrativeUnit.parentDto.parentDto.name;
				vm.regionName=administrativeUnit.parentDto.parentDto.regionDto.name;
				if(vm.paramDto.regionId!=null){
				
					auService.getAllCityByRegion(vm.paramDto.regionId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_City = data;
							
						}

					});
				}
				if(vm.paramDto.provinceId!=null){
				
					auService.getChildrenByParentId(vm.paramDto.provinceId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_Dist = data;
							
						}

					});
				}
				if(vm.paramDto.districtId!=null &&  vm.isFamer == false){
				
					auService.getChildrenByParentId(vm.paramDto.districtId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_Wards = data;
							
						}

					});
				}else if(vm.isFamer == true){
					vm.adminstrativeUnits_Wards.push(administrativeUnit);
				}
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
        vm.getListAnimalParent = function() {
			animalService.getListParent().then(function(data){
                vm.animalParents = data;
            });
        };
        vm.getListAnimalParent();

		function validate(){			
			 return true;
		}
		
        vm.getQuantityReport=function (){
			vm.title=$translate.instant('productivityForecast.compare_predict_the_number_of_live_meat_slightly')+ " ";
			if(vm.wardsName!=null){
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
				vm.title=vm.title + "- " + $translate.instant('report.toYear') + " " + vm.paramDto.toYear+" ("+$translate.instant('report.item')+")";
			}else if(vm.paramDto.periodType==1){
				vm.title=vm.title + "- " + $translate.instant('report.toMonth') + " " + vm.paramDto.toMonth+'/'+vm.paramDto.currentYear+" ("+$translate.instant('report.item')+")";
			}
        	vm.paramDto.groupByItems=[];
        	vm.rows=[];
        	var sortStr="";
        	if(vm.paramDto.periodType=='0'){
        		vm.columnsPivot = [
        			{
    	            	"uniqueName": "parentlName",
	                    "sort": "asc",
	                    "caption":$translate.instant('report.typeanimal')
    	            },{
                    "uniqueName": "yearReport",
                    "sort": "asc",
                    "caption":$translate.instant('report.year')            	                    
                }];
        		vm.rows.push("yearReport");
        		sortStr="yearReport."+vm.paramDto.toYear;
        	}
        	else if(vm.paramDto.periodType=='1'){
        		vm.columnsPivot = [
        			{
    	            	"uniqueName": "parentlName",
	                    "sort": "asc",
	                    "caption":$translate.instant('report.typeanimal')
    	            },{
        			"uniqueName": "monthInYear",
                    "sort": "asc",
                    "caption":$translate.instant('report.month')            	                    
                }];
        		vm.rows.push("monthReport");
        		vm.rows.push("yearReport");
        		sortStr="monthInYear."+vm.paramDto.toMonth+"/"+vm.paramDto.currentYear;
        	}
        	console.log(sortStr);
			//vm.paramDto.type=1;
			vm.paramDto.groupByItems.push('animalId');
        	vm.paramDto.groupByItems.push('animalParent');
			vm.paramDto.groupByItems.push('region');
			//vm.paramDto.groupByItems.push('importAnimal');
//        	vm.paramDto.fromYear = vm.paramDto.toYear;
//        	vm.paramDto.fromMonth  = vm.paramDto.toMonth;
        	//console.log(vm.paramDto.groupByItems.includes('district'));			
			console.log(vm.paramDto);
        	
            service.getComparePredictTheNumberOfLiveMeatSlightly(vm.paramDto).then(function(data){
            	vm.results=data;
				vm.data=vm.results[0];
				console.log(data);
				if(data==null || data==[] ||data.length==0){
					vm.isShow=false;
				}else{
					vm.isShow=true;
				}
				vm.isChanged=false;

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
            	            "columns": vm.columnsPivot,
            	            "rows": [{
        	                    "uniqueName": "regionName",
        	                    "sort": "asc",
        	                    "caption":$translate.instant('region.information')
	            	            }
//	            	            ,
//	        	            	{
//	    	                    "uniqueName": "regionName",
//	    	                    "sort": "asc",
//	    	                    "caption":$translate.instant('Vùng')
//	            	            },
//            	            	{
//        	                    "uniqueName": "provinceName",
//        	                    "sort": "asc",
//        	                    "caption":$translate.instant('Tỉnh/Thành')
//	            	            }
//	            	            ,
//            	            	{
//        	                    "uniqueName": "districtName",
//        	                    "sort": "asc",
//        	                    "caption":$translate.instant('Quận/huyện')
//	            	            }
//	            	            ,
//            	            	{
//        	                    "uniqueName": "wardName",
//        	                    "sort": "asc",
//        	                    "caption":$translate.instant('Xã/phường')
//	            	            }
            	            ],
            	            "measures": [{
            	                "uniqueName": "quantityChildren",
//            	                "aggregation": "sum",
            	                "caption":$translate.instant('report.amount')
            	            }
//            	            ,{
//            	                "uniqueName": "amount",
//            	                //"aggregation": "sum",
//            	                "caption":"Khối lượng"
//            	            }
            	            ],
//	            	        "drills": {
//	            	            "drillAll": false,
//	            	            "rows": [{
//	            	                "tuple": [
//	            	                    "animal"
//	            	                ]
//	            	            }]
//	            	        }
            	        	
            	            "expands": {
            	            	 "expandAll": true  
            	            },
            	            "sorting": {
            	                "row": {
            	                    "type": "desc",
            	                    "tuple": [
            	                    	sortStr
            	                    ],
            	                    "measure": "quantityChildren"
            	                }
            	            }
            	        },
            	        options: {
            	            grid: {
            	                title: vm.title,
            	                type:"compact",
            	                showGrandTotals: "off"
            	            }
            	        }
            	    },
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
        		    	data.title.text=$translate.instant('productivityForecast.compare_predict_the_number_of_live_meat_slightly')+ " ";
						
						if(vm.wardsName!=null){
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
							vm.title=vm.title + "- " + $translate.instant('report.toYear') + " " + vm.paramDto.toYear+" ("+$translate.instant('report.item')+")";
						}else if(vm.paramDto.periodType==1){
							vm.title=vm.title + "- " + $translate.instant('report.toMonth') + " " + vm.paramDto.toMonth+'/'+vm.paramDto.currentYear+" ("+$translate.instant('report.item')+")";
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
        		    	data.title.text=$translate.instant('productivityForecast.compare_predict_the_number_of_live_meat_slightly')+" ";
						if(vm.wardsName!=null){
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
							vm.title=vm.title + "- " + $translate.instant('report.toYear') + " " + vm.paramDto.toYear+" ("+$translate.instant('report.item')+")";
						}else if(vm.paramDto.periodType==1){
							vm.title=vm.title + "- " + $translate.instant('report.toMonth') + " " + vm.paramDto.toMonth+'/'+vm.paramDto.currentYear+" ("+$translate.instant('report.item')+")";
						}
						data.xAxis.title.text="";//bỏ title trục ngang
						data.yAxis[0].title.text="";//bỏ title trục dọc
        		    	console.log(data);
        		        Highcharts.chart('highchartsContainer', data);
        		    });
        		}           	
            	
            });
        }
		vm.isChanged=false;
		vm.changed=function(){			
			if(vm.isChanged==false){
				vm.isChanged=true;
				vm.getQuantityReportDetail();
			}else{
				vm.isChanged=false;								
			}
		}
		vm.getQuantityReportDetail=function (){			
        	vm.paramDto.groupByItems=[];      	       	
			//vm.paramDto.type=1;
			vm.paramDto.groupByItems.push('animalId');
			vm.paramDto.groupByItems.push('region');
        	vm.paramDto.fromYear = vm.paramDto.toYear;
        	vm.paramDto.fromMonth  = vm.paramDto.toMonth;			
			console.log(vm.paramDto);
       	
            service.getMeatDetailProductivityForecastReport(vm.paramDto).then(function(data){
            	vm.resultDetails=data;				
				console.log(data);         	
           	
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
			console.log("city");
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
						if(data.length== 1&&  vm.isFamer == false){
							
							auService.getChildrenByParentId(dist.id).then(function (data) {
								if (data != null && data.length > 0) {
									vm.adminstrativeUnits_Wards = data;
									
								}

							});
						
						}
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
		vm.onFmsadminstrativeUnitWardsSelected=function(item){
			if(item!=null){
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
