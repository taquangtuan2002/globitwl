/**
 * Created by bizic on 28/8/2016.
 */
(function () {
	'use strict';

	angular.module('Education.Report').controller('FarmPositionController', FarmPositionController);

	FarmPositionController.$inject = [
		'$rootScope',
		'$scope',
		'$http',
		'$timeout',
		'settings',
		'FarmService',
		'FmsRegionService',
		'FmsAdministrativeService',
		'QuantityReportService',
		'blockUI',
		'$translate',
		'$filter'
	];

	function FarmPositionController($rootScope, $scope, $http, $timeout, settings, farmService, regionService, fmsAdministrativeService, service, blockUI, $translate, $filter) {
		$scope.$on('$viewContentLoaded', function () {
			// initialize core components
			App.initAjax();
		});

		// set sidebar closed and body solid layout mode
		$rootScope.settings.layout.pageContentWhite = true;
		$rootScope.settings.layout.pageBodySolid = false;
		$rootScope.settings.layout.pageSidebarClosed = false;

		var vm = this;
		vm.pageIndex = 1;
		vm.pageSize = 10;
		vm.searchDto = {};
		vm.isRole = false;
		vm.isFamer = false;
		vm.isSdah = false;
		vm.isDistrict = false;
		vm.isWard = false;
		vm.isSdahView = false;
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
			if (vm.isRole) {//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
				vm.getAllCity();
				// vm.getListFarm();
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
			if (vm.searchDto == null) {
				vm.searchDto = {};
			}
			if (administrativeUnit.parentDto == null) {
				vm.searchDto.province = administrativeUnit.id;

				if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
				vm.adminstrativeUnits_City.push(administrativeUnit);
				farmService.getAllByParentId(vm.searchDto.province).then(function (data) {
					if (data != null && data.length > 0) {
						vm.adminstrativeUnits_Dist = data;
					}

				});
				vm.provinceName = administrativeUnit.name;
			} else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
				vm.searchDto.district = administrativeUnit.id;
				vm.searchDto.province = administrativeUnit.parentDto.id;

				if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
				vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
				if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
				vm.adminstrativeUnits_Dist.push(administrativeUnit);
				farmService.getAllByParentId(vm.searchDto.district).then(function (data) {
					if (data != null && data.length > 0) {
						vm.adminstrativeUnits_Wards = data;
					}

				});
				vm.districtName = administrativeUnit.name;
			} else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
				vm.searchDto.ward = administrativeUnit.id;
				vm.searchDto.district = administrativeUnit.parentDto.id;
				vm.searchDto.province = administrativeUnit.parentDto.parentDto.id;


				if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
				vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
				if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
				vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
				if (vm.adminstrativeUnits_Wards == null) vm.adminstrativeUnits_Wards = [];
				vm.adminstrativeUnits_Wards.push(administrativeUnit);
				vm.wardsName = administrativeUnit.name;

			}
			vm.getListFarm();
		}
		//--------End ----Phân quyền-------------
		vm.positionSelected = null;
        /*
         * Chọn vùng, tỉnh, huyện, xã
         */
		vm.getAllCity = function () {
			fmsAdministrativeService.getAllCity().then(function (data) {
				vm.adminstrativeUnits_City = data;
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
		//vm.getAllCity();
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
		vm.onFmsadminstrativeUnitCitySelected = function (city) {
			if (city != null && city.id != null) {
				farmService.getAllByParentId(city.id).then(function (data) {
					if (data != null && data.length > 0) {
						vm.adminstrativeUnits_Dist = data;
						vm.selectedDist = null;
						vm.selectWards = null;
						vm.searchDto.district = null;
						vm.searchDto.ward = null;
					}
					else {
						vm.searchDto.district = null;
						vm.searchDto.ward = null;
						vm.selectedDist = null;
						vm.selectWards = null;
						vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
					}
				});
			}
			else {
				vm.searchDto.district = null;
				vm.searchDto.ward = null;
				vm.selectedDist = null;
				vm.selectWards = null;
				vm.adminstrativeUnits_Dist = [];
				vm.adminstrativeUnits_Wards = [];
			}
			vm.getListFarm();
		}
		vm.onFmsadminstrativeUnitDistSelected = function (dist) {
			if (dist != null && dist.id != null) {
				farmService.getAllByParentId(dist.id).then(function (data) {
					if (data != null && data.length > 0) {
						vm.adminstrativeUnits_Wards = data;
						vm.searchDto.ward = null;
						vm.selectWards = null;
					}
					else {
						vm.searchDto.ward = null;
						vm.selectWards = null;
						vm.adminstrativeUnits_Wards = [];
					}

				});
			}
			else {
				vm.searchDto.ward = null;
				vm.selectWards = null;
				vm.adminstrativeUnits_Wards = [];
			}
			vm.getListFarm();
		}
		vm.onFmsadminstrativeUnitWardsSelected = function (item) {
			vm.getListFarm();
		}
        /*
         * End
         */

		//        vm.data = [];
		//        farmService.getAll().then(function(data) {
		//            vm.data = data.map(element => element);
		//            initMap();
		//        });
		var markers = [];
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
			if (vm.farms != null && vm.farms.length > 0) {
				markers = [];
				vm.farms.forEach(function (element) {
					if (element.latitude && element.longitude && element.wardsName && element.districtName && element.provinceName) {
						let icon = {
							//url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png", // url
							url: "assets/images/spotlight-poi2_hdpi.png", // url
							scaledSize: new google.maps.Size(35, 50), // scaled size
						}
						var marker = new google.maps.Marker({
							position: {
								lat: Number(element.latitude),
								lng: Number(element.longitude)
							},
							map: map,
							id: element.id,
							name: element.name,
							ownerName: element.ownerName,
							ownerPhoneNumber: element.ownerPhoneNumber,

							adressDetail: {
								// village: element.village,
								wardsName: element.wardsName,
								districtName: element.districtName,
								provinceName: element.provinceName
							},
							maxNumberOfAnimal: element.maxNumberOfAnimal,
							code: element.code,
							balanceNumber: element.balanceNumber,
							totalAcreage: element.totalAcreage,
							lodgingAcreage: element.lodgingAcreage,

							farmAnimalProductTargetExists: element.farmAnimalProductTargetExists,
							icon: icon,
							animalReportDatas: element.animalReportDatas

						});
						attachSecretMessage(marker, element);
						markers.push(marker);
					}
				});
			}

			map.addListener('click', function (e) {
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
			console.log(marker);
			console.log(element);
			//        	vm.positionSelected = element;
			var infowindow = new google.maps.InfoWindow({
				content: element.name + ' - ' + element.adressDetail
			});
			vm.positionSelected = {};
			console.log(marker);
			//            marker.name=element.name;
			marker.addListener('click', function () {
				//                infowindow.open(marker.get('map'), marker);
				console.log(marker);
				// vm.positionSelected = marker.gm_accessors_;
				vm.getAnimalReportDatasByFarmId(marker.id);
				vm.positionSelected.name = marker.name;
				$('#farm_id').html('<a class="green-dark margin-right-5" href="#/detail_farm/' + marker.id + '" target="_blank">' + $translate.instant('report.farmDetailLink') + '</a>');
				$('#farm_name').html(marker.name);
				$('#farm_code').html(marker.code);
				$('#farm_ownerName').html(marker.ownerName);
				$('#farm_ownerPhoneNumber').html(marker.ownerPhoneNumber);
				$('#farm_adressDetail').html( marker.adressDetail.wardsName + ", " + marker.adressDetail.districtName + ", " + marker.adressDetail.provinceName);
				$('#maxNumberOfAnimal').html($filter('number')(marker.maxNumberOfAnimal,0));
				$('#balanceNumber').html($filter('number')(marker.balanceNumber,0));
				$('#totalAcreage').html($filter('number')(marker.totalAcreage,0));
				$('#lodgingAcreage').html($filter('number')(marker.lodgingAcreage,0));
				let stringView="<ul>";
				if(marker.farmAnimalProductTargetExists){
					for(let index = 0; index < marker.farmAnimalProductTargetExists.length;index++){
						stringView+="<li>"
						stringView += marker.farmAnimalProductTargetExists[index].animal.name 
					+ " " + marker.farmAnimalProductTargetExists[index].productTarget.name
					+ " :  " + $filter('number')(marker.farmAnimalProductTargetExists[index].quantity,0) +' ('+ $translate.instant("report.unitItem")+')';
					stringView+="</li>";
					}
				}
				stringView+="</ul>";
				$('#viewAnimalProductTargetExist').html(stringView);

				
				for (var j = 0; j < markers.length; j++) {
					if(marker && markers[j] && markers[j].id == marker.id){
						// marker.setIcon("http://maps.google.com/mapfiles/ms/icons/blue-dot.png");
						marker.setIcon({
							url: "assets/images/spotlight-poi2_hdpi_blue.png", // url
							scaledSize: new google.maps.Size(35, 50)
						});
						continue;
					}
				  	// markers[j].setIcon("https://maps.gstatic.com/mapfiles/api-3/images/spotlight-poi2_hdpi.png");
				  	markers[j].setIcon({
						//url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png", // url
						url: "assets/images/spotlight-poi2_hdpi.png", // url
						scaledSize: new google.maps.Size(35, 50), // scaled size
					});
				}
				
				// marker.setIcon("/assets/images/pin.png");
				

				//                alert(vm.positionSelected.name);
			});
		};
		initMap();
		vm.getListFarm = function () {
			if (vm.textSearch != null && vm.textSearch != "") {
				vm.searchDto.nameOrCode = vm.textSearch;
			}
			else {
				vm.searchDto.nameOrCode = null;
			}
			console.log(vm.searchDto);
			
			vm.searchDto.viewAnimalProductTargetExist= true;
			farmService.getPageSearchFarmNonePage(vm.searchDto).then(function (data) {
				vm.farms = data;
				vm.positionSelected = {};
				vm.positionSelected.name = "";
				

				$('#farm_id').html("");

				$('#farm_name').html("");
				$('#farm_code').html("");
				$('#farm_ownerName').html("");
				$('#farm_ownerPhoneNumber').html("");
				$('#farm_adressDetail').html("");

				$('#maxNumberOfAnimal').html("");
				$('#balanceNumber').html("");
				$('#totalAcreage').html("");
				$('#lodgingAcreage').html("");

				$('#viewAnimalProductTargetExist').html("");


				initMap();
				if (vm.textSearch!=null && vm.textSearch.length>0&&vm.farms != null && vm.farms.length == 1) {
					vm.farm = vm.farms[0];
					//vm.search();
					initMapItem(vm.farm);
					
					
				}
				//vm.bsTableControl.options.data = vm.farms;
				//                vm.bsTableControl.options.totalRows = data.totalElements;

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
		//popup - thông tin chi tiết cơ sở chăn nuôi động vật
		vm.showPopupDetail = function (item) {
			vm.subModalInstance = modal.open({
				animation: true,
				templateUrl: 'detail_modal.html',
				scope: $scope,
				size: 'lg'
			});
			vm.farm = item;

			vm.subModalInstance.result.then(function (confirm) {
				if (confirm == 'yes') {

				}
			}, function () {

				console.log("cancel");
			});
		}
		vm.enterSearch = function () {
			// console.log(event.keyCode);
			if (event.keyCode == 13) {//Phím Enter

				vm.search();
			}
		};
		vm.search = function () {
			vm.getListFarm();
			vm.getListFarm();
		}
		// view thông tin 1 cơ sở 
		function initMapItem(item) {
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
			if (item.latitude && item.longitude && item.wardsName && item.districtName && item.provinceName) {
				var marker = new google.maps.Marker({
					position: {
						lat: Number(item.latitude),
						lng: Number(item.longitude)
					},
					map: map,
					id: item.id,
					name: item.name,
					ownerName: item.ownerName,
					ownerPhoneNumber: item.ownerPhoneNumber,

					adressDetail: {
						// village: item.village,
						wardsName: item.wardsName,
						districtName: item.districtName,
						provinceName: item.provinceName
					},
					maxNumberOfAnimal: item.maxNumberOfAnimal,
					code: item.code,
					balanceNumber: item.balanceNumber,
					totalAcreage: item.totalAcreage,
					lodgingAcreage: item.lodgingAcreage,
					farmAnimalProductTargetExists: item.farmAnimalProductTargetExists,
					icon: {
						url: "assets/images/spotlight-poi2_hdpi_blue.png", // url
						scaledSize: new google.maps.Size(35, 50)
					}
				});
				vm.getAnimalReportDatasByFarmId(marker.id);
				vm.positionSelected.name = marker.name;
				$('#farm_id').html('<a class="green-dark margin-right-5" href="#/detail_farm/' + marker.id + '" target="_blank">' + $translate.instant('report.farmDetailLink') + '</a>');

				$('#farm_name').html(marker.name);
				$('#farm_code').html(marker.code);
				$('#farm_ownerName').html(marker.ownerName);
				$('#farm_ownerPhoneNumber').html(marker.ownerPhoneNumber);
				$('#farm_adressDetail').html(marker.adressDetail.wardsName + ", " + marker.adressDetail.districtName + ", " + marker.adressDetail.provinceName);

				$('#maxNumberOfAnimal').html($filter('number')(marker.maxNumberOfAnimal,0));
				$('#balanceNumber').html($filter('number')(marker.balanceNumber,0));
				$('#totalAcreage').html($filter('number')(marker.totalAcreage,0));
				$('#lodgingAcreage').html($filter('number')(marker.lodgingAcreage,0));
				let stringView="<ul>";
				if(marker.farmAnimalProductTargetExists){
					for(let index = 0; index < marker.farmAnimalProductTargetExists.length;index++){
						stringView+="<li>"
						stringView += marker.farmAnimalProductTargetExists[index].animal.name 
					+ " " + marker.farmAnimalProductTargetExists[index].productTarget.name
					+ " : " + $filter('number')(marker.farmAnimalProductTargetExists[index].quantity,0) +' ('+ $translate.instant("report.unitItem")+')';
					stringView+="</li>";
					}
				}
				stringView+="</ul>";
				$('#viewAnimalProductTargetExist').html(stringView);

				// marker.setIcon("/assets/images/pin.png");
			}
			// map.addListener('click', function(e) {
			// 	placeMarkerAndPanTo(e.latLng, map);
			// });

			// function placeMarkerAndPanTo(latLng, map) {
			// 	//    	  var marker = new google.maps.Marker({
			// 	//    	    position: latLng,
			// 	//    	    map: map
			// 	//    	  });
			// 	console.log(latLng);
			// 		//   alert(latLng);
			// }
		}

		vm.getAnimalReportDatasByFarmId = function(id){
			vm.animalReportDatas = [];
			//farmService.getFarm(id).then(function(data){
			farmService.getFarmSummary(id).then(function(data){
				if(data){
					vm.animalReportDatas = [];
					vm.animalReportDatas=data.animalReportDataSummarys;
					if(vm.animalReportDatas!=null && vm.animalReportDatas.length>0){
                        	vm.animalReportDatas.sort(function (a, b) {
                                if(a.year != b.year){
									return b.year - a.year;
								}else if(a.year == b.year){
									return b.month -  a.month;
								}
							});
							 
                        	
                        }
					/*vm.animalReportDatasTemplate = data.animalReportDatas;

					vm.animalReportDatasTemplate.sort(function(a, b){
						if(a.year != b.year){
							return b.year - a.year;
						}else if(a.year == b.year){
							return b.month -  a.month;
						}
					});
					console.log(vm.animalReportDatasTemplate);
					let maxYear = -1;
					let maxMonth;
					vm.animalReportDatasTemplate.forEach(function(item){
						if(maxYear < 0 && item.year > 0){
							maxYear = item.year;
							maxMonth = item.month;
						}
						if(maxYear == item.year && item.month == maxMonth){
							vm.animalReportDatas.push(item);
						}
					});*/
				}else{
					vm.animalReportDatas = [];
				}
			}).catch(function(){
				vm.animalReportDatas = [];
			});
		}
		vm.getAnimalReportDataType = function (value) {
            const res = settings.AnimalReportDataType.find(el => el.value == value);
            if (res) {
                return res.name;
            } else
                return "";
        }
	}
})();