/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('FarmPositionVGMapController', FarmPositionVGMapController);

    FarmPositionVGMapController.$inject = [
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
        '$filter',
        'toastr',
        'SumQuantityReportDataService',
        "$location",
        "$sce",
        "$cookies"
    ];

    function FarmPositionVGMapController($rootScope, $scope, $http, $timeout, settings, farmService, regionService, fmsAdministrativeService, service, blockUI, $translate, $filter, toastr, sumQuantityReportDataService, $location,$sce, $cookies) {
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
        vm.pronvices = [];
        vm.districts = [];
        vm.communes = [];
        vm.yearSelected = null;
        vm.showMenu = true;
        vm.adminstrativeUnits = null;
        vm.listAdminstrativeUnitCode = null;
        vm.viewPosition = false;

        // Xem chi tiết địa chỉ farm

        // let {id} = $location.search();
        // if(id) {
        //     vm.viewPosition = false;
        //     let {lat, long, name} = $location.search();

        //     let mapCore = new VGMap.VGMapCore('map', {
        //         center: new VGMap.VGLatLng(lat, long),
        //         zoom: 8,
        //         minZoom: 5,
        //         maxZoom: 17,
        //         draggable: true,
        //         zoomControl: true,
        //         allowEdit: true,
        //         contextMenuEnabled: true,
        //         contextMenus: [{
        //             title: 'Lấy tọa độ',
        //             onClick: function (e) {
        //                 alert(e.lat + " - " + e.lng);
        //             }
        //         }]
        //     });

        //     new VGMap.VGFeature({
        //         bearing: 0, // độ nghiêng của feature
        //         geometry: new VGMap.VGPoint(new VGMap.VGLatLng(lat, long)), // geometry dạng Point
        //         style: function (e) {
        //             return VGMap.VGPointStyle.defaultStyle; // style măc định
        //         },
        //         onClick: function (e) { // event khi click vào feature
        //             // xử lý khi click vào feature
        //             e.target.showPopup(); // hiển thị popup khi click vào feature
        //         },
        //         onHover: function (e) { // event khi di chuyển chuột vào feature
        //             // xử lý khi hover vào feature
        //             e.target.showTooltip(); // hiển thị tooltip
        //         },
        //         tooltipContent: `Cơ sở nuôi: ${name}`, // nội dung của tooltip
        //         popupContent: `Cơ sở nuôi: ${name}`, // nội dung của popup có thể là string hoặc html markup
        //         attributes: { // thuộc tính của feature
        //             license_plate: '30F - 243.84',
        //             speed: 60
        //         }
        //     }).addTo(mapCore); // biến mapCore đã khởi tạo

        // } else {
        //     //------Start--Phân quyền theo user đăng nhập-----------
        //     blockUI.start();
    
        //     vm.getProvinces = function () {
        //         if (vm.isRole) {
        //             $.get('https://fao.gisgo.vn/region/listProvinces').done(function (xhr) {
        //                 $('#province_id').append('<option disabled selected>Vui lòng chọn tỉnh/thành phố</option>');
        //                 //
        //                 $.each(xhr.data, function (idx, item) {
        //                     $('#province_id').append('<option value="' + item.area_id + '">' + item.name_vn + '</option>')
        //                 });
        //             });
        //         }
        //         else if (!vm.isRole && vm.adminstrativeUnits && vm.adminstrativeUnits.length > 0) {
        //             var url = "https://fao.gisgo.vn/region/listProvinces?area_ids=";
        //             if (vm.isSdah) {
        //                 for (let index = 0; index < vm.adminstrativeUnits.length; index++) {
        //                     if (index == (vm.adminstrativeUnits.length - 1)) {
        //                         url += vm.adminstrativeUnits[index].code;
        //                     }
        //                     else {
        //                         url += vm.adminstrativeUnits[index].code + ",";
        //                     }
        //                 }
        //             }
        //             else if (vm.isDistrict) {
        //                 for (let index = 0; index < vm.adminstrativeUnits.length; index++) {
        //                     if (index == (vm.adminstrativeUnits.length - 1)) {
        //                         url += (vm.adminstrativeUnits[index].parentDto ? vm.adminstrativeUnits[index].parentDto.code : 0);
        //                     }
        //                     else {
        //                         url += (vm.adminstrativeUnits[index].parentDto ? (vm.adminstrativeUnits[index].parentDto.code + ",") : (0 + ","));
        //                     }
        //                 }
        //             }
        //             else if (vm.isWard) {
        //                 for (let index = 0; index < vm.adminstrativeUnits.length; index++) {
        //                     if (index == (vm.adminstrativeUnits.length - 1)) {
        //                         url += ((vm.adminstrativeUnits[index].parentDto && vm.adminstrativeUnits[index].parentDto.parentDto)
        //                             ? vm.adminstrativeUnits[index].parentDto.parentDto.code : 0);
        //                     }
        //                     else {
        //                         url += ((vm.adminstrativeUnits[index].parentDto && vm.adminstrativeUnits[index].parentDto.parentDto)
        //                             ? (vm.adminstrativeUnits[index].parentDto.parentDto.code + ",") : (0 + ","));
        //                     }
        //                 }
        //             }
        //             else{
        //                 url += "0";
        //             }
    
        //             $.get(url).done(function (xhr) {
        //                 $('#province_id').append('<option disabled selected>Vui lòng chọn tỉnh/thành phố</option>');
        //                 $.each(xhr.data, function (idx, item) {
        //                     $('#province_id').append('<option value="' + item.area_id + '">' + item.name_vn + '</option>')
        //                 });
        //             });
        //         }
        //     };
    
        //     service.getCurrentUser().then(function (data) {
        //         console.log(data);
        //         vm.user = data;
        //         var roles = data.roles;
    
        //         if (roles != null && roles.length > 0) {
        //             for (var i = 0; i < roles.length; i++) {
        //                 if (roles[i] != null && roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' || roles[i].authority.toLowerCase() == 'role_dlp')) {
        //                     vm.isRole = true;
        //                 }
        //                 if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah') {
        //                     vm.isSdah = true;
        //                 }
        //                 if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_district') {
        //                     vm.isDistrict = true;
        //                 }
        //                 if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_ward') {
        //                     vm.isWard = true;
        //                 }
        //                 if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_famer') {
        //                     vm.isFamer = true;
        //                 }
        //                 if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah_view') {
        //                     vm.isSdahView = true;
        //                     vm.isSdah = true;
        //                 }
        //             }
        //         } else {
        //             vm.isRole = false;
        //             vm.isFamer = false;
        //             vm.isSdah = false;
        //             vm.isDistrict = false;
        //             vm.isWard = false;
        //             vm.isSdahView = false;
        //         }
        //         if (vm.isRole) {//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
        //             vm.getProvinces();
    
        //             getFarms();
        //             // vm.getListFarm();
        //         } else {// trường hợp này thì phân quyền theo user
        //             if (vm.isRole == false) {
        //                 getAdministrativeUnitDtoByLoginUser();
        //             }
    
        //         }
        //         blockUI.stop();
        //     });
    
        //     //load user administrativeunit
        //     function getAdministrativeUnitDtoByLoginUser() {
        //         vm.adminstrativeUnits = null;
        //         vm.listAdminstrativeUnitCode = null;
        //         service.getAdministrativeUnitDtoByLoginUser().then(function (data) {
        //             if (data != null && data.length > 0) {
        //                 vm.adminstrativeUnits = data;
        //                 service.getListAdministrativeUnitCodeByUserLogin().then(function (data) {
        //                     if (data != null && data.length > 0) {
        //                         vm.listAdminstrativeUnitCode = data;
        //                         vm.getProvinces();
        //                         getFarms();
        //                     }
        //                 });
        //             }
        //         });
        //     }
        //     var getFarmHandler;
        //     //--------End ----Phân quyền-------------
        //     vm.positionSelected = null;
        //     /*
        //      * Chọn vùng, tỉnh, huyện, xã
        //      */
        //     vm.getAllCity = function () {
        //         fmsAdministrativeService.getAllCity().then(function (data) {
        //             vm.adminstrativeUnits_City = data;
        //         });
        //     }
        //     vm.onFmsadminstrativeUnitCitySelected = function (city) {
        //         if (city != null && city.id != null) {
        //             farmService.getAllByParentId(city.id).then(function (data) {
        //                 if (data != null && data.length > 0) {
        //                     vm.adminstrativeUnits_Dist = data;
        //                     vm.selectedDist = null;
        //                     vm.selectWards = null;
        //                     vm.searchDto.district = null;
        //                     vm.searchDto.ward = null;
        //                 }
        //                 else {
        //                     vm.searchDto.district = null;
        //                     vm.searchDto.ward = null;
        //                     vm.selectedDist = null;
        //                     vm.selectWards = null;
        //                     vm.adminstrativeUnits_Dist = [];
        //                     vm.adminstrativeUnits_Wards = [];
        //                 }
        //             });
        //         }
        //         else {
        //             vm.searchDto.district = null;
        //             vm.searchDto.ward = null;
        //             vm.selectedDist = null;
        //             vm.selectWards = null;
        //             vm.adminstrativeUnits_Dist = [];
        //             vm.adminstrativeUnits_Wards = [];
        //         }
        //         vm.getListFarm();
        //     }
        //     vm.onFmsadminstrativeUnitDistSelected = function (dist) {
        //         if (dist != null && dist.id != null) {
        //             farmService.getAllByParentId(dist.id).then(function (data) {
        //                 if (data != null && data.length > 0) {
        //                     vm.adminstrativeUnits_Wards = data;
        //                     vm.searchDto.ward = null;
        //                     vm.selectWards = null;
        //                 }
        //                 else {
        //                     vm.searchDto.ward = null;
        //                     vm.selectWards = null;
        //                     vm.adminstrativeUnits_Wards = [];
        //                 }
    
        //             });
        //         }
        //         else {
        //             vm.searchDto.ward = null;
        //             vm.selectWards = null;
        //             vm.adminstrativeUnits_Wards = [];
        //         }
        //         vm.getListFarm();
        //     }
        //     vm.onFmsadminstrativeUnitWardsSelected = function (item) {
        //         vm.getListFarm();
        //     }
        //     /*
        //      * End
        //      */
    
        //     vm.updateFarm = function () {
        //         vm.updateDto = {};
        //         vm.updateDto.farmCode = $('#farmCode').val();
        //         vm.updateDto.lat = $('#lat').val();
        //         vm.updateDto.lng = $('#lng').val();
        //         sumQuantityReportDataService.updateCoordinates(vm.updateDto).then(function (data) {
        //             toastr.info("Cập nhật thành công", 'Thông báo');
        //             getFarms();
        //         }, function (respone) {
        //         });
        //     }
    
        //     function updateFarmCoo(prop) {
        //         console.log(prop);
        //         if (prop != null && prop.length > 0) {
        //             for (var i = 0; i < prop.length; i++) {
        //                 vm.updateDto = {};
        //                 vm.updateDto.farmCode = prop[i].feature.properties.code;
        //                 vm.updateDto.lat = prop[i].newLatlng._lat;
        //                 vm.updateDto.lng = prop[i].newLatlng._lng;
        //                 sumQuantityReportDataService.updateCoordinates(vm.updateDto).then(function (data) {
        //                     toastr.info("Cập nhật thành công", 'Thông báo');
        //                 }, function (respone) {
        //                 });
        //             }
        //         }
        //     }
        //     /*
        //      * VGMap Start here
        //      * */
        //     var _iconSizes = [18, 24, 30, 36];
    
        //     $('#iconSize-modal').on('shown.bs.modal', function () {
        //         $.each(_iconSizes, function (idx, s) {
        //             $('#iconSize-form').find('[name=iconSize' + idx + "]").val(s);
        //         });
        //     });
    
        //     $('#btnUpdateIconSize').on('click', function () {
        //         $.each(_iconSizes, function (idx, s) {
        //             _iconSizes[idx] = $('#iconSize-form').find('[name=iconSize' + idx + "]").val();
        //         });
        //         //
        //         if ($('#commune_id').val()) {
        //             getFarms($('#commune_id').val());
        //         } else if ($('#district_id').val()) {
        //             getFarms($('#district_id').val());
        //         } else if ($('#province_id').val()) {
        //             getFarms($('#province_id').val());
        //         } else {
        //             getFarms();
        //         }
        //         //
        //         $('#iconSize-modal').modal('hide');
        //     });
    
        //     $.ajaxSetup({
        //         beforeSend: function () {
        //             mapCore.showProgress();
        //         },
        //         complete: function () {
        //             mapCore.hideProgress();
        //         }
        //     });
    
        //     var getFarmHandler;
    
        //     $('#q').select2({
        //         placeholder: 'Nhập từ khóa',
        //         minimumInputLength: 2,
        //         theme: 'bootstrap4',
        //         ajax: {
        //             url: 'https://fao.gisgo.vn/farms/',
        //             data: function (params) {
        //                 var query = {
        //                     q: params.term,
        //                     year: $('#year').val() || 2020
        //                 }
    
        //                 return query;
        //             },
        //             dataType: 'json',
        //             processResults: function (data) {
        //                 var items = data.data;
        //                 $.each(items, function (idx, item) {
        //                     item.id = item.code;
        //                 })
        //                 return {
        //                     results: items
        //                 };
        //             }
        //         },
        //         templateResult: function (repo) {
        //             if (repo.loading) {
        //                 return repo.text;
        //             }
        //             //
        //             var $container = $(
        //                 "<div class='select2-result-repository clearfix'>" +
        //                 "<div class='select2-result-repository__meta'>" +
        //                 "<div class='select2-result-repository__title'></div>" +
        //                 "<div class='select2-result-repository__description'></div>" +
        //                 "</div>" +
        //                 "</div>" +
        //                 "</div>"
        //             );
    
        //             $container.find(".select2-result-repository__title").text(repo.name);
        //             $container.find(".select2-result-repository__description").text(repo.code);
    
        //             return $container;
        //         },
        //         templateSelection: function (repo) {
        //             if (!repo.id) {
        //                 return repo.text;
        //             }
        //             return repo.name + ' (' + repo.code + ')'
        //         }
        //     }).on('select2:select', function (e) {
        //         var farm = e.params.data;
        //         if (farm && farm.latitude * farm.longitude > 0) {
        //             //                mapCore.setCenter(new VGMap.VGLatLng(farm.latitude, farm.longitude), 14);
        //             mapCore.panTo(new VGMap.VGLatLng(farm.latitude, farm.longitude), 14);
        //             mapCore.highlight(new VGMap.VGLatLng(farm.latitude, farm.longitude));
        //         }
        //     });
    
        //     var features = [];
    
        //     var loaded = false;
    
        //     var mapCore = new VGMap.VGMapCore('map', {
        //         center: new VGMap.VGLatLng(16.22028539097923, 106.18286132812501),
        //         zoom: 5.5,
        //         minZoom: 5,
        //         maxZoom: 17,
        //         draggable: true,
        //         zoomControl: true,
        //         allowEdit: true,
        //         contextMenuEnabled: true,
        //         contextMenus: [{
        //             title: 'Lấy tọa độ',
        //             onClick: function (e) {
        //                 alert(e.lat + " - " + e.lng);
        //             }
        //         }]
        //     });
    
        //     new VGMap.VGMapLegends(mapCore, {
        //         html: function () {
        //             return $('#legend-template').html();
        //         }
        //     });
    
    
        //     $('#linkShowIconSize', document).on('click', function () {
        //         $('#iconSize-modal').modal('show');
        //     });
        //     var onDropDownChange = function () {
        //         if ($('#province_id').val() || $('#district_id').val() || $('#commune_id').val()) {
        //             filterByArea(false);
        //         } else {
        //             getFarms();
        //         }
        //     };
    
        //     var lastLevel = 1;
    
        //     mapCore.on('zoom_changed', function (e) {
        //         if ($('#province_id').val() || $('#district_id').val() || $('#commune_id').val()) {
        //             filterByArea(false);
        //         }
        //         else {
        //             if (e.getZoom() >= 0 && e.getZoom() < 11 && lastLevel !== 1) {
        //                 lastLevel = 1;
        //                 getFarms();
        //             } else if (e.getZoom() >= 11 && e.getZoom() < 13 && lastLevel !== 2) {
        //                 lastLevel = 2;
        //                 getFarms();
        //             } else if (e.getZoom() >= 13 && lastLevel !== 3) {
        //                 lastLevel = 3;
        //                 getFarms();
        //             }
        //         }
        //     });
    
        //     mapCore.on('dragend', function (e) {
        //         if (e.getZoom() >= 13) {
        //             if ($('#province_id').val() || $('#district_id').val() || $('#commune_id').val()) {
        //                 filterByArea(false);
        //             } else {
        //                 getFarms();
        //             }
        //         }
        //     });
    
        //     mapCore.on('features:edit', function (e) {
        //         console.log(e);
        //         updateFarmCoo(e);
        //     });
    
        //     // mapCore.on('click', function (e) {
        //     //     var feature = new VGMap.VGFeature({
        //     //         geometry: new VGMap.VGPoint(new VGMap.VGLatLng(e.latlng.lat, e.latlng.lng)),
        //     //         style: new VGMap.VGPointStyle({
        //     //             imageUrl: 'https://fao.gisgo.vn/samples/images/mix.png',
        //     //             size: [36, 36]
        //     //         })
        //     //     });
        //     //     feature.addTo(mapCore);
        //     // });
    
        //     $.get('https://fao.gisgo.vn/farms/years').done(function (xhr) {
        //         $.each(xhr.data, function (idx, item) {
        //             $('#year').append('<option value="' + item + '">' + item + '</option>')
        //         });
        //     });
    
        //     $.get('https://fao.gisgo.vn/animals/groups').done(function (xhr) {
        //         $('#ani_group').append('<option value="">Tất cả</option>')
        //         $.each(xhr.data, function (idx, item) {
        //             $('#ani_group').append('<option value="' + item.ani_group + '">(' + item.ani_group + ') ' + item.animal_class + '</option>')
        //         });
        //     });
    
        //     $('#year').on('change', onDropDownChange);
    
        //     $('#ani_group').on('change', onDropDownChange);
    
        //     $('#province_id').on('change', function () {
        //         $.get('https://fao.gisgo.vn/region/listDistricts', {
        //             parent_id: $(this).val()
        //         }).done(function (xhr) {
        //             if (xhr.status === 'OK') {
        //                 $('#district_id').empty();
        //                 $('#commune_id').empty();
        //                 $('#district_id').append('<option value="" disabled selected>Vui lòng chọn quận/huyện</option>');
        //                 //
        //                 $.each(xhr.data, function (idx, item) {
        //                     if (!vm.isRole && vm.listAdminstrativeUnitCode && vm.listAdminstrativeUnitCode.length > 0) {
        //                         var area = vm.listAdminstrativeUnitCode.find(element => element === item.area_id);
        //                         if (area) {
        //                             $('#district_id').append('<option value="' + item.area_id + '">' + item.name_vn + '</option>')
        //                         }
        //                     } else if (vm.isRole) {
        //                         $('#district_id').append('<option value="' + item.area_id + '">' + item.name_vn + '</option>')
        //                     }
        //                 });
        //             }
        //         });
        //     });
    
        //     $('#district_id').on('change', function () {
        //         $.get('https://fao.gisgo.vn/region/listCommunes', {
        //             parent_id: $(this).val()
        //         }).done(function (xhr) {
        //             if (xhr.status === 'OK') {
        //                 $('#commune_id').empty();
        //                 $('#commune_id').append('<option disabled selected>Vui lòng chọn phường/xã</option>');
        //                 //
        //                 $.each(xhr.data, function (idx, item) {
        //                     if (!vm.isRole && vm.listAdminstrativeUnitCode && vm.listAdminstrativeUnitCode.length > 0) {
        //                         var area = vm.listAdminstrativeUnitCode.find(element => element === item.area_id);
        //                         if (area) {
        //                             $('#commune_id').append('<option value="' + item.area_id + '">' + item.name_vn + '</option>')
        //                         }
        //                     } else if (vm.isRole) {
        //                         $('#commune_id').append('<option value="' + item.area_id + '">' + item.name_vn + '</option>')
        //                     }
        //                 });
        //             }
        //         });
        //     });
    
        //     $('#filter').on('click', function () {
        //         mapCore.clearPermanentFeatures();
        //         filterByArea(true);
        //     });
    
        //     $('#reset').on('click', function () {
        //         $('#province_id').val($("#province_id option:first").val());
        //         $('#district_id').empty();
        //         $('#commune_id').empty();
        //         $('#ani_group').val($("#ani_group option:first").val());
        //         //
        //         mapCore.clearFeatures();
        //         mapCore.clearClusterFeatures();
        //         mapCore.clearPermanentFeatures();
        //         mapCore.fullExtent();
        //         mapCore.clearHighlight();
        //         //
        //         getFarms();
        //     });
    
        //     $('#is_cluster').on('change', function () {
        //         if ($('#province_id').val() || $('#district_id').val() || $('#commune_id').val()) {
        //             filterByArea(false);
        //         } else {
        //             getFarms();
        //         }
        //     });
    
        //     function filterByArea(loadBounds) {
        //         if ($('#commune_id').val()) {
        //             getFarms($('#commune_id').val(), loadBounds);
        //         } else if ($('#district_id').val()) {
        //             getFarms($('#district_id').val(), loadBounds);
        //         } else if ($('#province_id').val()) {
        //             getFarms($('#province_id').val(), loadBounds);
        //         } else {
        //             getFarms(undefined, loadBounds);
        //         }
        //     }
        //     $('#province_id').select2({
        //         placeholder: 'Chọn tỉnh',
        //         theme: 'bootstrap4',
        //         ajax: {
        //             url: 'https://fao.gisgo.vn/region/listProvinces',
        //             data: function (params) {
        //                 var query = {
        //                     q: params.term,
        //                 }
    
        //                 return query;
        //             },
        //             dataType: 'json',
        //             processResults: function (data) {
        //                 var newData = [];
        //                 if (!vm.isRole && vm.listAdminstrativeUnitCode && vm.listAdminstrativeUnitCode.length > 0) {
        //                     if (vm.isSdah && vm.adminstrativeUnits) {
        //                         const found = data.data.find(element => element.area_id == vm.adminstrativeUnits[0].code);
        //                         if (found) {
        //                             newData.push(found);
        //                         }
        //                     }
        //                     else if (vm.isDistrict && vm.adminstrativeUnits && vm.adminstrativeUnits[0].parentDto) {
        //                         const found = data.data.find(element => element.area_id == vm.adminstrativeUnits[0].parentDto.code);
        //                         if (found) {
        //                             newData.push(found);
        //                         }
        //                     }
        //                     else if (vm.isWard && vm.adminstrativeUnits && vm.adminstrativeUnits[0].parentDto && vm.adminstrativeUnits[0].parentDto.parentDto) {
        //                         const found = data.data.find(element => element.area_id == vm.adminstrativeUnits[0].parentDto.parentDto.code);
        //                         if (found) {
        //                             newData.push(found);
        //                         }
        //                     }
        //                 } else if (vm.isRole) {
        //                     newData = data.data;
        //                 }
        //                 var items = newData;
    
        //                 $.each(items, function (idx, item) {
        //                     item.id = item.area_id;
        //                     item.text = item.name_vn;
        //                 });
        //                 return {
        //                     results: $.merge([{
        //                         id: -1,
        //                         text: 'Tất cả'
        //                     }], items)
        //                 };
        //             }
        //         },
        //     }).on('select2:select', function (e) {
        //         var p = e.params.data;
        //         $("#district_id").trigger("change");
        //         $("#commune_id").trigger("change");
        //     });
    
    
    
        //     //        var options = {
        //     //		  enableHighAccuracy: true,
        //     //		  timeout: 5000,
        //     //		  maximumAge: 0
        //     //		};
        //     //
        //     //		function success(pos) {
        //     //		  var crd = pos.coords;
        //     //		  var windowlocation = crd;
        //     //		  console.log('Your current position is:');
        //     //		  console.log(`Latitude : ${crd.latitude}`);
        //     //		  console.log(`Longitude: ${crd.longitude}`);
        //     //		  console.log(`More or less ${crd.accuracy} meters.`);
        //     //		}
        //     //		function error(err) {
        //     //		  console.warn(`ERROR(${err.code}): ${err.message}`);
        //     //		}
        //     vm.openGoogleMaps = function (latitude, longitude) {
        //         var location = mapCore.getLocation();
        //         if (location) {
        //             window.open(
        //                 "http://www.google.com/maps/dir/" + location.lat + "," + location.lng + "/" + latitude + "," + longitude, "_blank");
        //         } else {
        //             alert('Vui lòng cho phép lấy vị trí của bạn!');
        //         }
        //     }
    
        //     /*vm.openGoogleMaps(11.0025343584324, 107.174377441406);*/
        //     //        $('#district_id').select2({
        //     //            placeholder: 'Chọn Huyện',
        //     //            theme: 'bootstrap4',
        //     //            ajax: {
        //     //                url: 'https://fao.gisgo.vn/region/listDistricts',
        //     //                data: function (params) {
        //     //                    var query = {
        //     //                        q: params.term,
        //     //                    }
        //     //
        //     //                    return query;
        //     //                },
        //     //                dataType: 'json',
        //     //                processResults: function (data) {
        //     //                    var items = data.data;
        //     //                    
        //     //                    $.each(items, function (idx, item) {
        //     //                        item.id = item.area_id;
        //     //                        item.text = item.name_vn;
        //     //                    });
        //     //                    return {
        //     //                        results: $.merge([{
        //     //                            id: -1,
        //     //                            text: 'Tất cả'
        //     //                        }], items)
        //     //                    };
        //     //                }
        //     //            },
        //     //        }).on('select2:select', function (e) {
        //     //            var p = e.params.data;
        //     //            $("#district_id").trigger("change");
        //     //            $("#commune_id").trigger("change");
        //     //        });
    
    
        //     function getFarms(area_id, loadBounds) {
        //         var maxFeature = 500;
        //         //
        //         if (getFarmHandler) {
        //             getFarmHandler.abort();
        //         }
        //         //
        //         var is_cluster = $('#is_cluster').is(':checked');
        //         //
        //         mapCore.clearFeatures();
        //         mapCore.clearClusterFeatures();
        //         //
        //         if (!area_id && !vm.isRole && (area_id == null || !area_id) && vm.isSdah) {
        //             if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
        //                 area_id = vm.adminstrativeUnits[0].code;
        //             }
        //         }
        //         else if (!area_id && !vm.isRole && (area_id == null || !area_id) && vm.isDistrict) {
        //             if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
        //                 area_id = vm.adminstrativeUnits[0].parentDto ? vm.adminstrativeUnits[0].parentDto.code : null;
        //             }
        //         }
        //         else if (!area_id && !vm.isRole && (area_id == null || !area_id) && vm.isWard) {
        //             if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
        //                 area_id = vm.adminstrativeUnits[0].parentDto ? vm.adminstrativeUnits[0].parentDto.code : null;
        //             }
        //         }
    
        //         if (area_id && loadBounds) {
        //             $.get('https://fao.gisgo.vn/region/shape', {
        //                 area_id: area_id,
        //             }).done(function (xhr) {
        //                 if (xhr.status === 'OK' && xhr.data) {
        //                     mapCore.addGeoJSON(xhr.data, {
        //                         permanent: true
        //                     });
        //                     mapCore.fitGeoJSON(xhr.data);
        //                 }
        //             });
        //         }
        //         //
        //         getFarmHandler = $.get('https://fao.gisgo.vn/map/farms', {
        //             area_id: area_id,
        //             zoom: mapCore.getZoom(),
        //             bbox: mapCore.getBounds().toString(),
        //             cluster: is_cluster,
        //             maxFeatures: 10000,
        //             year: $('#year').val() || 2020,
        //             aniGroup: $('#ani_group').val() || ""
        //         }).done(function (xhr) {
        //             if (xhr.status === 'OK') {
        //                 var newFeatures = [];
        //                 if (!vm.isRole) {
        //                     if (xhr && xhr.data && xhr.data.data && xhr.data.data.features && xhr.data.data.features.length > 0
        //                         && vm.listAdminstrativeUnitCode && vm.listAdminstrativeUnitCode.length > 0) {
        //                         xhr.data.data.features.forEach(item => {
        //                             var newItem = vm.listAdminstrativeUnitCode.find(element => element == item.id
        //                                 || (vm.isSdah && element == item.properties.province_code)
        //                                 || (vm.isDistrict && element == item.properties.district_code)
        //                                 || (vm.isWard && element == item.properties.ward_code));
        //                             if (newItem) {
        //                                 newFeatures.push(item);
        //                             }
        //                         });
        //                     }
        //                     xhr.data.data.features = newFeatures;
        //                 }
    
        //                 if (xhr.data.cluster) {
        //                     mapCore.addGeoJSON(xhr.data.data, {
        //                         style: new VGMap.VGDivStyle({
        //                             html: function (properties) {
        //                                 return '<div><span>' + properties.count + '</span></div>';
        //                             },
        //                             className: 'marker-cluster marker-cluster-small',
        //                             size: [40, 40],
        //                             anchor: [20, 45]
        //                         }),
        //                         onMarkerClick: function (e) {
        //                             if (e.feature) {
        //                                 if (e.feature.properties.level === 'p') {
        //                                     if (mapCore.getZoom() <= 8) {
        //                                         mapCore.setCenter(new VGMap.VGLatLng(e.feature.geometry.coordinates[1], e.feature.geometry.coordinates[0]), mapCore.getZoom() + 2);
        //                                     } else {
        //                                         mapCore.setCenter(new VGMap.VGLatLng(e.feature.geometry.coordinates[1], e.feature.geometry.coordinates[0]), 11);
        //                                     }
        //                                 } else if (e.feature.properties.level === 'd') {
        //                                     if (mapCore.getZoom() <= 8) {
        //                                         mapCore.setCenter(new VGMap.VGLatLng(e.feature.geometry.coordinates[1], e.feature.geometry.coordinates[0]), mapCore.getZoom() + 2);
        //                                     } else {
        //                                         mapCore.setCenter(new VGMap.VGLatLng(e.feature.geometry.coordinates[1], e.feature.geometry.coordinates[0]), 13);
        //                                     }
        //                                 }
        //                             }
        //                         }
        //                     })
        //                 } else {
        //                     mapCore.addGeoJSON(xhr.data.data, {
        //                         cluster: true,
        //                         editable: true,
        //                         style: function (feature) {
        //                             var size = _iconSizes[0];
        //                             if (feature.animal_total >= 51 && feature.animal_total <= 200) {
        //                                 size = _iconSizes[1];
        //                             } else if (feature.animal_total >= 201 && feature.animal_total <= 500) {
        //                                 size = _iconSizes[2];
        //                             } else if (feature.animal_total > 500) {
        //                                 size = _iconSizes[3];
        //                             }
        //                             if (feature.animal_code) {
        //                                 var codes = feature.animal_code.toUpperCase().replace(/\d+/g, '').split(',');
        //                                 codes = Array.from(new Set(codes));
        //                                 if (codes.length > 1) {
        //                                     return new VGMap.VGPointStyle({
        //                                         imageUrl: 'https://fao.gisgo.vn/samples/images/mix.svg',
        //                                         size: [size, size]
        //                                     });
        //                                 } else {
        //                                     var classCode = codes[0];
        //                                     switch (classCode) {
        //                                         case 'A': {
        //                                             return new VGMap.VGPointStyle({
        //                                                 imageUrl: 'https://fao.gisgo.vn/samples/images/Amphibians.png',
        //                                                 size: [size, size]
        //                                             });
        //                                         }
        //                                         case 'C': {
        //                                             return new VGMap.VGPointStyle({
        //                                                 imageUrl: 'https://fao.gisgo.vn/samples/images/Arachnida.png',
        //                                                 size: [size, size]
        //                                             });
        //                                         }
        //                                         case 'B':
        //                                         case 'D':
        //                                             return new VGMap.VGPointStyle({
        //                                                 imageUrl: 'https://fao.gisgo.vn/samples/images/aves.svg',
        //                                                 size: [size, size]
        //                                             });
        //                                         case 'M': {
        //                                             return new VGMap.VGPointStyle({
        //                                                 imageUrl: 'https://fao.gisgo.vn/samples/images/Mammalia.svg',
        //                                                 size: [size, size]
        //                                             });
        //                                         }
        //                                         case 'R': {
        //                                             return new VGMap.VGPointStyle({
        //                                                 imageUrl: 'https://fao.gisgo.vn/samples/images/reptile.svg',
        //                                                 size: [size, size]
        //                                             });
        //                                         }
        //                                     }
        //                                 }
        //                             }
        //                             return new VGMap.VGPointStyle({
        //                                 imageUrl: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAlCAYAAAAjt+tHAAAACXBIWXMAAAsTAAALEwEAmpwYAAAE6klEQVRYhbWXX2xTVRzHP21v10tbNug2rIqj4kCnLsD454QpD50IEkQMkwkPGMUlivikRh1BjUA0JvrmTHxAo4ksi0YTI7omYuKc0TCYokWjZJphRmSl6drbu95/PvTeete1rGXll5zctvd3zvdzfud3Ts/PYRgGAO3t7QLgBKrMp0BlTQV0IAPo/f39KoDDMAxLvAoQzVZlAjgrJK6bABlANlumv79fdYTDYUvcC1SbT5GrEwEZkICE+cxYsxRN8QWRYHSwwsJTLDzW1Gp+1AHVHnpvJBgd9IVCCF4vLlGsqLAmy6iSRIToYHisqRlzGeyJJwJXJK6pKko6jZJO4xAEDF3H4/PhFkUcDgdA/phWnjmtbLda+eKKQmp8nDrDQ9eqbRx94EU2hlYhj8eQE4kpvraxc5pWol1xtkvxOIurg7y78yBti1sA6GzZxMEv3uaVr95hTk1Nsa7OWQlbpikKr27elxO3rPuevSX1L3mrKbKMkk6j6zoALkGgyutF1zQa626Y5u92lTZ0SV6aojCZSnH30jVsCq1iIpOm9+wJ/rgwAoaBrGZKncc0m3EJNEUhnUjQWLuQh5beRc9ALydHfqZr2X3M81ZnBzEzveIAmqIgxeM01Yd4dv1uPjtzgt+e/5QX2h/jh7/O8ExrJ36PF8F55YdmUQBd00gnEiwJhjiw4RF+HfuT3j1vALB2UTOP3/kgGAb71u+k9/SXlQUwDIPJZJKAr4bnWh/mrW8+pGP5RjyCO+fTtriFeHoCVVcZjV/g6Y9frxxARpJw4KCr5X5+/PsXFs67hutq6qf5bb19A9+eO8WGxtV8N3KaJ/sOVwZAU1W23dKGy+HgYipOT0c311bXTfNb3XAb7+86xPGzA+xr66RvOMLeYy/PHsAlCCyaF2RoNEpPRzc1or/oAEvqG+jZ0c33Iz+xt3U758bPlwVQMH11TePNwWNsb1xHjejn3+QlkpMSkiKjG9mDSHAKiEIVC+YGqPfP5/jZAcJL17L+xhWzBxDnzkW6dAmnIxugJ/oO0Tcc4Y5FzWQ0hSqXm7GJcUZi//DB7sPsWrkZwemiZ8cBXM7yTveCAI68g0U3DDbd2kbHsnb2rNkKwGtfH6V36DgZVcn5ZTSFOU7P7AHyLSEnObJlP8Pnf+fRj14CYOeKe0lIE8TTE2UJ5lvxeJm3ZcsUTePc+CjLr7+ZBf4Ao/ELuSW6KgCCKObO+DrffLo+OULbTS081dbJkS37iU8meW/oc4LVtbk+c9zlhR/AEQ6HA0AdEIgEo4OBluz/+mQySSoWw+PzMZlK4XK70RRlSmenIKCrKm5RRJHlggKBhoYp32NDQ9bFNAZctHJAz+/o8fvx+LP731dbm/+6EqZDdglUW0MrMpNKmG3snKaT/8slGUCVpKsCYV3LTZNNTV2wiUvhsabWCIULE18ohCcQKElsMhYjNTJS8J25/hK2ukA3vyRMh2amlmZOgAjRAWBGCEs8PNa0zvzJyq/80kwG9FKK09wdPhKMnrxcJGziK5maW8WL0xnKc+tpL99OFYKwia+whzgPoHB5fjkzwUTAbzZvJBgdtkPYxJeZ4kmzyZZQMZsRoAiE34oEYJ95kjLESwYoAFFtQpwErDVPkk2uksXLAsiD8JqtynyVIRt6qRzxsgFsEFZSWlvV2mKZcsQB/gOeGCyXyKrimgAAAABJRU5ErkJggg==',
        //                                 size: [32, 37]
        //                             });
        //                         },
        //                         onMarkerClick: function (e) {
        //                             var latlng = new VGMap.VGLatLng(e.feature.geometry.coordinates[1], e.feature.geometry.coordinates[0]);
        //                             vm.current_lat = latlng._lat;
        //                             vm.current_lng = latlng._lng;
        //                             mapCore.showPopup(latlng, Mustache.render($('#popup-template').html(), e.feature.properties), {
        //                                 offset: [0, -18]
        //                             });
        //                         },
        //                         onMarkerEdit: function (e) {
        //                             $('#latlng-modal').modal('show');
        //                             $('#latlng-form').find('[name=lng]').val(e.latlng.lng);
        //                             $('#latlng-form').find('[name=lat]').val(e.latlng.lat);
        //                             $('#latlng-form').find('[name=farmCode]').val(e.feature.properties.code);
        //                         }
        //                     })
        //                 }
        //             }
        //         });
        //     }

        // }

       
        // getFarms();
        $scope.token_id="";
        vm.getApiUsersAndRoles = function() {
            var listUserAndRoles = [];
            var tokenCookies = $cookies.getObject('token');
            var currentUserCookies = $cookies.getObject('currentUser');
            var adminUnitByUserCookies = $cookies.getObject('adminUnitByUser');
            vm.updateDto = {};
            vm.updateDto.role = [];
            vm.updateDto.is_admin = false;
            vm.updateDto.provinces = [];
            vm.updateDto.districts = [];
            vm.updateDto.commune = [];

            vm.updateDto.token_id = tokenCookies.access_token;
            for(var i=0; i<currentUserCookies.roles.length; i++){
                vm.updateDto.role.push(currentUserCookies.roles[i].name);
                if(currentUserCookies.roles[i].name === 'ROLE_ADMIN'){
                    vm.updateDto.is_admin = true;
                }
            }
            
            for(var i=0; i<adminUnitByUserCookies.length; i++){
                if(adminUnitByUserCookies[i].level == 1) {
                    vm.updateDto.provinces.push(adminUnitByUserCookies[i].code);
                }
                else if(adminUnitByUserCookies[i].level == 2) {
                    vm.updateDto.districts.push(adminUnitByUserCookies[i].code);
                }
            }
            listUserAndRoles.push(vm.updateDto);
            sumQuantityReportDataService.getApiUsersAndRoles(listUserAndRoles).then(function (data) {
                toastr.info("Lấy dữ liệu thành công.", 'Thông báo');
                vm.viewPosition = true;
                $scope.token_id = vm.updateDto.token_id;
                let ts = new Date().getTime();
                $rootScope.iframeUrl=$sce.trustAsResourceUrl("https://fao.gisgo.vn/samples/index.html?token_id="+$scope.token_id+ "&ts="+ts) ;
            }, function (respone){

            });

        }
        vm.getApiUsersAndRoles();
    }
})();