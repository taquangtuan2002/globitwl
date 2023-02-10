(function() {
    'use strict';

    angular.module('Education.AnimalCertificate').service('AnimalCertificateService', AnimalCertificateService);

    AnimalCertificateService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities'
    ];

    function AnimalCertificateService($http, $q, $filter, settings, utils) {
        var self = this;
        var Url = settings.api.baseUrl + settings.api.apiPrefix;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'animalCertificate/';
        var baseUrl2 = settings.api.baseUrl + settings.api.apiPrefix + 'import_export/animal/';

        self.getTableDefinition = getTableDefinition;
        self.checkDuplicateCode = checkDuplicateCode;
        self.getAllAnimalCertificateDto = getAllAnimalCertificateDto;
        self.getAllAnimalCertificate = getAllAnimalCertificate;
        self.getAniamlCertificateById = getAniamlCertificateById;
        self.createAnimalCertificate = createAnimalCertificate;
        self.updateAnimalCertificate = updateAnimalCertificate;
        self.deleteAnimalCertificate = deleteAnimalCertificate; 
        self.deleteAnimalCertificates = deleteAnimalCertificates;
        self.getPageSearchAnimalCertificate = getPageSearchAnimalCertificate;
        self.getCurrentUser = getCurrentUser;
        self.getAdministrativeUnitDtoByLoginUser = getAdministrativeUnitDtoByLoginUser;
        self.getStreamDocument = getStreamDocument;
        self.getAdminstrativeUnitsCity = getAdminstrativeUnitsCity;
        self.getAllByParentId = getAllByParentId;
        self.getChildrenByParentId = getChildrenByParentId;
        self.getDetailsByStatusAC = getDetailsByStatusAC;
        self.getUserLogined = getUserLogined;
        self.searchByPage = searchByPage;

        function getUserLogined(id) {
            var url = Url + 'user_attachments/getByUserId/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function checkDuplicateCode(code) {
            var url = baseUrl + 'checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllAnimalCertificateDto(){
            var url = baseUrl + 'getall';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getDetailsByStatusAC(dto, successCallback, errorCallback) {
            var url = Url + 'animalCertificate/getDetailsByStatusAC';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getAllAnimalCertificate(pageIndex, pageSize) {
            var url = baseUrl + 'page/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getPageSearchAnimalCertificate(dto,pageIndex, pageSize, successCallback, errorCallback) {
            if(dto != null){

                var url = baseUrl + 'searchDto/'+ pageIndex +'/'+pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }
        self.getListAnimalFromReportForm16BySearch = getListAnimalFromReportForm16BySearch;
        function getListAnimalFromReportForm16BySearch(dto, successCallback, errorCallback) {
            var url = Url + 'reportForm16/getListAnimalFromReportForm16BySearch';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function getAniamlCertificateById(id) {
            var url = baseUrl + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteAnimalCertificate(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function deleteAnimalCertificates(ids, successCallback, errorCallback) {
            var url = baseUrl + 'delete';

            return utils.resolveAlt(url, 'DELETE', null, ids, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function createAnimalCertificate(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function updateAnimalCertificate(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getAllByParentId(parentId) {
            var url = Url + 'administrative/getAllByParentId/' + parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        // Xuất word
        function getStreamDocument(object) {
            console.log("RUNNING");
            var deferred = $q.defer();
            $http({
                url: settings.api.baseUrl + settings.api.apiPrefix + 'file/downloadAnimalCertificate',
                method: "POST",//you can use also GET or POST
                data: object,
                headers: { 'Content-type': 'application/json' },
                responseType: 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                    console.error("ERROR");
                    deferred.reject(data);
                });

            return deferred.promise;
        };

        function getAdminstrativeUnitsCity() {
            var url = Url + 'administrative/getall/city';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getCurrentUser() {
            var url = Url + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAdministrativeUnitDtoByLoginUser() {
            var url = Url + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getChildrenByParentId(parentId) {
            var url = Url + 'administrative/getChildrenByParentId/' + parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function searchByPage(searchDto, successCallback, errorCallback) {
            var url = baseUrl + 'searchByPage';
            return utils.resolveAlt(url, 'POST', null, searchDto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getTableDefinition() {
            var _tableIndex = function (value, row, index, field) {
                return index + 1;
            };

            var _tableTextRight = function (value, row, index, field) {
                return {
                    classes: 'text-center'
                };
            };

            var _tableExportRow = function (value, row, index) {
                return '<a href="#" class="btn btn-default margin-left-10" file-name="' + "'ChungChiDongVatNuoi.doc'" + '" ng-click="$parent.vm.getFileWord(' + "'" + row.id + "'" + ')" file-download="$parent.vm.myBlobObjectWord"><i class="fa fa-file-excel-o"> Xuất dữ liệu word</i></a>';
            }

            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };

            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };

            var _tableOperation = function (value, row, index) {

                return '<a class="green-dark" style="margin-right: 10px;" href="#" data-ng-click="$parent.detailAnimalCertificate(' + "'" + row.id + "'" + ',' + "'" + 2 + "'" + ')"><i class="icon-pencil "></i>Chi Tiết</a>'
                
                    + '<a class="green-dark" style="margin-right: 10px;"  href="#" data-ng-click="$parent.editAnimalCertificate(' + "'" + row.id + "'" + ')"><i class="icon-pencil"></i>Sửa</a>'
                 
                    + '<a class="green-dark" style="margin-right: 10px;"  href="#" data-ng-click="$parent.deleteAnimalCertificateRecord(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>Xóa</a>';
            };

            var _farmadressDetail = function (value, row, index) {
                if (!value) {
                    return '';
                }
                var rs = " ";
                if(value.administrativeUnit != null){
                    rs += value.administrativeUnit.name;
                }
                if (value.administrativeUnit != null && value.administrativeUnit.parentDto != null ){
                    rs += value.administrativeUnit.parentDto.name;
                }
                if(value.administrativeUnit != null && value.administrativeUnit.parentDto != null && value.administrativeUnit.parentDto.parentDto != null){
                    rs += value.administrativeUnit.parentDto.parentDto.name;
                }
                return rs;
            };

            var _farmnameDetail = function (value, row, index) {
                if(!value) {
                    return '';
                }
                return value.name;
            }

            return [
                {
                    field: '',
                    title: 'TT',
                    switchable: true,
                    visible: true,
                    formatter: _tableIndex,
                    cellStyle: _tableTextRight
                },
                {
                    field: '',
                    title: 'Xuất ra word',
                    switchable: true,
                    visible: true,
                    formatter: _tableExportRow,
                    cellStyle: _operationColStyle
                },
                {
                    field: '',
                    title: 'Thao tác',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                },
                {
                    field: 'code',
                    title: 'Mã số cơ sở',
                    switchable: true,
                    sortable: true,
                    cellStyle: _operationColStyle
                },
                {
                    field: 'farm',
                    title: 'Tên trang trại',
                    switchable: true,
                    sortable: true,
                    cellStyle: _operationColStyle,
                    formatter: _farmnameDetail
                },
                {
                    field: 'farm.dateRegistration',
                    title: 'Ngày khai báo',
                    switchable: true,
                    sortable: true,
                    cellStyle: _operationColStyle,
                    formatter: _dateFormatter
                },
                {
                    field: 'farm',
                    title: 'Địa chỉ',
                    switchable: true,
                    sortable: true,
                    cellStyle: _operationColStyle,
                    formatter: _farmadressDetail
                }
            ]
        }

        //---------Popup Farm-------//
        self.getTableDefinitionFarm = getTableDefinitionFarm;
        self.getPageSearchFarm = getPageSearchFarm;
        var baseUrlFarm = settings.api.baseUrl + settings.api.apiPrefix + 'farm/';

        function getPageSearchFarm(searchFarmDto, pageIndex, pageSize, successCallback, errorCallback) {
            if (searchFarmDto != null) {

                var url = baseUrlFarm + 'searchDto/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, searchFarmDto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }

        function getTableDefinitionFarm() {//cái này là view danh sách cơ sở chăn nuôi

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            return [
                {
                    field: 'state',
                    checkbox: true,
                }
                ,
                {
                    field: 'code',
                    title: '{{"farm.farmCode" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                }
                , {
                    field: 'name',
                    title: '{{"farm.name" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'ownerName',
                    title: '{{"farm.ownerName" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'provinceName',
                    title: '{{"administrativeUnit.province" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }

            ];
        }
    }


})();