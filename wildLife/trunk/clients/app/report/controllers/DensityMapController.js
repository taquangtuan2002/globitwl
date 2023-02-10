/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.Report').controller('DensityMapController', DensityMapController);

    DensityMapController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'FarmService',
        'FmsRegionService',
        'FmsAdministrativeService',
		'QuantityReportService',
		'$uibModal',
		'blockUI',
		'$translate'
    ];
	angular.module('Education.Report').directive('fileDownload',function(){
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

    function DensityMapController($rootScope, $scope, $http, $timeout, settings, farmService,regionService,fmsAdministrativeService, service,modal,blockUI,$translate) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;
        vm.pageIndex=1;
        vm.pageSize=10;
        vm.searchDto={};
        vm.positionSelected = null;
		var data = [];
		vm.listFarmDistrict=null;
		vm.nameCity=null;
		vm.countCity=null;
		vm.listFarmWards=null;
		vm.nameDistrict=null;
		vm.countDistrict=null;
		vm.code=null;
		vm.level=null;
		
		vm.id=null;
		vm.subModalInstance = {};
		vm.regionDto=null;
		vm.regions=[];
        
     // Prepare demo data
     // Data is joined to map using value of 'hc-key' property by default.
     // See API docs for 'joinBy' for more info on linking data and map.
     var data1 = [
         ['vn-yb', 0],
         ['vn-pt', 1],
         ['vn-3655', 2],
         ['vn-qn', 3],
         ['vn-kh', 4],
         ['vn-tg', 5],
         ['vn-bv', 6],
         ['vn-bu', 7],
         ['vn-hc', 8],
         ['vn-br', 9],
         ['vn-st', 10],
         ['vn-li', 11],
         ['vn-311', 12],
         ['vn-ty', 13],
         ['vn-318', 14],
         ['vn-hd', 15],
         ['vn-bn', 16],
         ['vn-317', 17],
         ['vn-vc', 18],
         ['vn-nb', 19],
         ['vn-hm', 20],
         ['vn-ho', 21],
         ['vn-bg', 22],
         ['vn-tb', 23],
         ['vn-ld', 24],
         ['vn-bp', 25],
         ['vn-tn', 26],
         ['vn-py', 27],
         ['vn-bd', 28],
         ['vn-3623', 29],
         ['vn-724', 30],
         ['vn-qg', 31],
         ['vn-331', 32],
         ['vn-dt', 33],
         ['vn-333', 34],
         ['vn-la', 35],
         ['vn-337', 36],
         ['vn-bl', 37],
         ['vn-vl', 38],
         ['vn-hg', 39],
         ['vn-nd', 40],
         ['vn-db', 41],
         ['vn-ls', 42],
         ['vn-th', 43],
         ['vn-307', 44],
         ['vn-tq', 45],
         ['vn-328', 46],
         ['vn-na', 47],
         ['vn-qb', 48],
         ['vn-723', 49],
         ['vn-nt', 50],
         ['vn-6365', 51],
         ['vn-299', 52],
         ['vn-300', 53],
         ['vn-qt', 54],
         ['vn-tt', 55],
         ['vn-kg', 56],
         ['vn-da', 57],
         ['vn-ag', 58],
         ['vn-cm', 59],
         ['vn-tv', 60],
         ['vn-cb', 61],
         ['vn-lo', 62],
         ['vn-bi', 63],
         ['vn-hs', 64],
         ['vn-ts', 65]
     ];
	// console.log(data1);
	
	vm.name=null;
	vm.isShowDetail=false;
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
			vm.name=null;
			if(administrativeUnit.parentDto==null){				
				vm.name=administrativeUnit.mapCode;
				console.log(vm.name);
				
			}
			/*else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				vm.name=administrativeUnit.parentDto.mapCode;
				console.log(vm.name);
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){				
				vm.name=administrativeUnit.parentDto.parentDto.mapCode;
				console.log(vm.name);
			}*/			
            
        }			
		//--------End ----Phân quyền-------------
		
	 vm.getAllCity = function() {
            fmsAdministrativeService.getAllCity().then(function(data) {
                vm.adminstrativeUnits_City = data;
				if(vm.adminstrativeUnits_City!=null && vm.adminstrativeUnits_City.length>0){
					vm.ids=[];
					for(var i=0;i<vm.adminstrativeUnits_City.length;i++){
						vm.ids.push(vm.adminstrativeUnits_City[i].id);
					}
				}
				if(vm.ids!=null && vm.ids.length>0){
					farmService.countFarmByListAdministrativeUnit(vm.ids,1).then(function(data) {
                       
						//console.log(data);
						if(data!=null){
							vm.farmAus=data;
							if(vm.farmAus!=null && vm.farmAus.length>0){
								data=[];
								for(var i=0;i<vm.farmAus.length;i++){
									vm.values=[];
									vm.values.push(vm.farmAus[i].mapCodeAu);
									vm.values.push(vm.farmAus[i].count);
									data.push(vm.values);
								}
								vm.values=[];
								vm.values.push("vn-hs");
								vm.values.push(0);
								data.push(vm.values);
								vm.values=[];
								vm.values.push("vn-ts");
								vm.values.push(0);
								data.push(vm.values);
							}
						}
						console.log(data);
						// Create the chart
						 Highcharts.mapChart('container', {
							 chart: {
								 map: 'countries/vn/vn-all'
							 },

							 title: {
								 text: $translate.instant('report.densityMap')
							 },

							 /*subtitle: {
								 text: 'Source map: <a href="assets/scripts/chart/highcharts/vn-all.js">Vietnam</a>'
							 },*/

							 mapNavigation: {
								 enabled: true,
								 buttonOptions: {
									 verticalAlign: 'bottom'
								 }
							 },

							 colorAxis: {
								 min: 0,
								 max: 1000,
								 /*labels: {
											format: '{value}con'
										},*/
								 stops: [
										[0, '#ADFF2F'],
										[0.3, '#0000ff'],
										[0.6, '#ffff00'],
										[1, '#ff0000']
										
									]								
								 
							 },
							 

							 series: [{
								 data: data,
								 name: $translate.instant('report.density'),
								 borderColor: '#007f3f',
								 states: {
									 hover: {
										 color: '#BADA55'
									 }
								 },
								 dataLabels: {
									 enabled: false,
									 format: '{point.name}'
									 
								 },
								 events: {
									click: function (e) {
										vm.id=null;
										vm.nameDistrict=null;
										vm.countDistrict=null;
										vm.listFarmWards=null;
										vm.listFarmDistrict=[];
										vm.isShowDetail=false;
										vm.nameCity=e.point.name;
										vm.countCity=e.point.value;
										vm.level=2;
										vm.regionDto={};
										vm.regions =[];
										console.log(e);
										console.log(e.point["hc-key"]);
										vm.getAdministrativeUnitByMapCode(e.point["hc-key"]);	
										vm.countDensityMapCodeAdministrativeUnit(e.point["hc-key"]);	
										vm.code=e.point["hc-key"];
										/*if((vm.code!=null && vm.name!=null && vm.code==vm.name && vm.isRole==false) || vm.isRole==true){
											vm.isShowDetail=true;
										}*/
										if(vm.isFamer==true||vm.isWard==true){
											vm.isShowDetail=true;
										}
									}
								}
							 }]
							 
						 });
                    }, function (respone) {
                        //toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
				}
                // console.log()
                // if (data && data.length > 0) {
                //     vm.regions = data;
                //     vm.adminstrativeUnits_City = [];
                //     data.forEach(el => {
                //         vm.onFmsRegionSelected(el,function(list){
                //             vm.adminstrativeUnits_City.concat(list);
                //         });
                //     });
                // } else {
                //     vm.regions = [];
                // }
            });
        }
	  vm.getAllCity();
	   //mật độ của tất cả các vùng
		vm.getListDensity=function(){
			farmService.countDensityListRegion().then(function(data) {
                vm.regions = data;
				console.log(vm.regions);
                
            });
		}
		//mật độ của 1 vùng
		vm.countDensityMapCodeAdministrativeUnit = function(code) {
            farmService.countDensityMapCodeAdministrativeUnit(code).then(function(data) {
                vm.regionDto = data;
				console.log(vm.regionDto);
                
            });
        }
		//danh sách số lượng động vật của các huyện
		vm.getAdministrativeUnitByMapCode = function(code) {
            farmService.countFarmListAdministrativeUnitByMapCode(code,2).then(function(data) {
                vm.listFarmDistrict = data;
				console.log(vm.listFarmDistrict);
                
            });
        }
		//danh sách số lượng cscn  của các xã
		vm.detail=function(districtId,name,count){
			vm.id=districtId;
			vm.nameDistrict=name;
			vm.countDistrict=count;
			vm.listFarmWards=[];
            farmService.countFarmListAdministrativeUnitById(districtId,3).then(function(data) {
                vm.listFarmWards = data;
				console.log(vm.listFarmWards);
                
            });
        
		}
		//popup - danh sách số lượng cscn  của các xã
		vm.showPopupDetail = function (districtId,name,count) {
			if(vm.isShowDetail==false ){
					vm.subModalInstance = modal.open({
					animation: true,
					templateUrl: 'detail_modal.html',
					scope: $scope,
					size: 'lg'
					});
				   vm.detail(districtId,name,count);
				   //vm.level=3;
				   
					vm.subModalInstance.result.then(function (confirm) {
						if (confirm == 'yes') {
						   
						}
					}, function () {

						console.log("cancel");
					});
			}
            
        }

     // Create the chart
     /*Highcharts.mapChart('container', {
         chart: {
             map: 'countries/vn/vn-all'
         },

         title: {
             text: 'Bản đồ mật độ cơ sở chăn nuôi Việt Nam'
         },

         subtitle: {
             text: 'Source map: <a href="assets/scripts/chart/highcharts/vn-all.js">Vietnam</a>'
         },

         mapNavigation: {
             enabled: true,
             buttonOptions: {
                 verticalAlign: 'bottom'
             }
         },

         colorAxis: {
             min: 0
         },

         series: [{
             data: data,
             name: 'Random data',
             states: {
                 hover: {
                     color: '#BADA55'
                 }
             },
             dataLabels: {
                 enabled: true,
                 format: '{point.name}'
             }
         }]
     });*/

        
        
        /*
         * Chọn vùng, tỉnh, huyện, xã
         */
        
       
        // vm.onFmsRegionSelected=function(region){
		// 	if(region!=null && region.id!=null){
		// 		fmsAdministrativeService.getAllCityByRegion(region.id).then(function (data) {
		// 			if (data != null && data.length > 0) {
        //                 vm.adminstrativeUnits_City = data;
		// 				vm.selectedCity=null;
		//                 vm.selectedDist = null;
		//                 vm.selectWards=null;
		// 			}
		// 			else {
		// 				vm.selectedCity=null;
		// 	        	vm.selectedDist = null;
		// 	        	vm.selectWards = null;
		// 				 vm.adminstrativeUnits_City=[];
		//                 vm.adminstrativeUnits_Dist = [];
		// 				vm.adminstrativeUnits_Wards = [];
		// 			}
	    //         });
		// 	}
        // }
        vm.onFmsadminstrativeUnitCitySelected = function(city) {
			if (city != null && city.id != null) {
				farmService.getAllByParentId(city.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Dist = data;
		                vm.selectedDist = null;
		                vm.selectWards=null;
					}
					else {
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
					}
	            });
			}
			else {
	        	vm.selectedDist = null;
	        	vm.selectWards = null;
                vm.adminstrativeUnits_Dist = [];
				vm.adminstrativeUnits_Wards = [];
			}
		}
        vm.onFmsadminstrativeUnitDistSelected = function(dist) {
			if (dist != null && dist.id != null) {
				farmService.getAllByParentId(dist.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Wards = data;
					}
					else {
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Wards = [];
					}
					vm.selectWards=null;
	            });
			}
			else {
	        	vm.selectWards = null;
				vm.adminstrativeUnits_Wards = [];
			}
		}
        /*
         * End
         */
        
//        vm.data = [];
//        farmService.getAll().then(function(data) {
//            vm.data = data.map(element => element);
//            initMap();
//        });

        function initMap() {
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 5,
                center: {
                    lat: 21.017342,
                    lng: 105.818178
                }
            });

            var bounds = {
                north: 23.322070,
                south: 8.690170,
                east: 123.926840,
                west: 92.053184
            };

            // Căn phạm vi bản đồ
//            map.fitBounds(bounds);
            //Tạo các điểm trên bản đồ thể hiện vị trí cơ sở chăn nuôi
            if(vm.farms!=null && vm.farms.length>0){
            	vm.farms.forEach(function(element) {
                    if (element.gMapX && element.gMapY) {
                        var marker = new google.maps.Marker({
                            position: {
                                lat: Number(element.gMapX),
                                lng: Number(element.gMapY)
                            },
                            map: map,
                            name:element.name,
                            ownerName:element.ownerName,
                            ownerPhoneNumber:element.ownerPhoneNumber,
                            adressDetail:element.adressDetail
                        });
                        attachSecretMessage(marker, element);
                    }
                });
            }            
            
            map.addListener('click', function(e) {
                placeMarkerAndPanTo(e.latLng, map);
            });

            function placeMarkerAndPanTo(latLng, map) {
                //    	  var marker = new google.maps.Marker({
                //    	    position: latLng,
                //    	    map: map
                //    	  });
                console.log(latLng);
                   	//   alert(latLng);
            }
        }
        
        // Tạo sự kiện khi click vào vị trí cơ sở chăn nuôi
        function attachSecretMessage(marker, element) {
//        	vm.positionSelected = element;
            var infowindow = new google.maps.InfoWindow({
                content: element.name+' - '+element.adressDetail
            });
            vm.positionSelected={};
            console.log(marker);
//            marker.name=element.name;
            marker.addListener('click', function() {
//                infowindow.open(marker.get('map'), marker);
                vm.positionSelected.name = marker.name;
                $('#farm_name').html(marker.name);
                $('#farm_ownerName').html(marker.ownerName);
                $('#farm_ownerPhoneNumber').html(marker.ownerPhoneNumber);
                $('#farm_adressDetail').html(marker.adressDetail);
//                alert(vm.positionSelected.name);
            });
        };
//        initMap();
        vm.getListFarm=function () {
            console.log(vm.searchDto);
            farmService.getPageSearchFarmNonePage(vm.searchDto).then(function(data) {
                vm.farms = data;
                vm.bsTableControl.options.data = vm.farms;
//                vm.bsTableControl.options.totalRows = data.totalElements;
                initMap();
            });
        }
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
	            columns: farmService.getTableDefinition(),
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
	                vm.getListFarm();
	                //vm.getPageFarm();
	            }
	        }
	    };
		
		//export
		$scope.myBlobObject=undefined;
        $scope.getFile=function(){
            console.log('download started, you can show a wating animation');
			if(vm.id==null) vm.id=0;
            service.getStream(vm.code,vm.id,vm.level)
                .then(function(data){//is important that the data was returned as Aray Buffer
                    console.log('Stream download complete, stop animation!');
                    $scope.myBlobObject=new Blob([data],{ type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
                },function(fail){
                    console.log('Download Error, stop animation and show error message');
                    $scope.myBlobObject=[];
                });
        };
    }
})();