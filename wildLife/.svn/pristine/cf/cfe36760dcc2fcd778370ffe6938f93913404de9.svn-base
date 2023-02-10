(function () {
    'use strict';

    angular.module('Education.ForestProductList').service('ForestProductListService', ForestProductListService);

    ForestProductListService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities'
    ];

    function ForestProductListService($http, $q, $filter, settings, utils) {
        var self = this;
        var Url = settings.api.baseUrl + settings.api.apiPrefix;
        console.log("thanh", Url)
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'import_export/animal/';
        var type = 1;//Kiểu phiếu: 1= Nhập đàn
        self.getTableDefinition = getTableDefinition;
        self.getImportAnimal = getImportAnimal;
        self.getPageImportAnimal = getPageImportAnimal;
        self.updateImportAnimal = updateImportAnimal;
        self.createImportAnimal = createImportAnimal;
        self.deleteImportAnimal = deleteImportAnimal;
        self.getTableDefinitionInjectionPlant = getTableDefinitionInjectionPlant;
        self.getFileById = getFileById;
        self.getPageSearchImportAnimal = getPageSearchImportAnimal;
        self.getPageSearchImportAnimalSeed = getPageSearchImportAnimalSeed;
        //  self.getsearchImportAnimalDto=getsearchImportAnimalDto;
        self.getBatchCode = getBatchCode;
        self.getStream = getStream;
        self.getAdministrativeUnitDtoByLoginUser = getAdministrativeUnitDtoByLoginUser;
        self.getCurrentUser = getCurrentUser;
        self.getForestProductListById = getForestProductListById;
        self.saveForestProductList = saveForestProductList;
        self.getPageForestProductList = getPageForestProductList;
        self.deletePageForestProductList = deletePageForestProductList;
        self.getTableDefinitionCode = getTableDefinitionCode;
        self.deleteLinkedByExportAnimalId = deleteLinkedByExportAnimalId;

        //tran huu dat them cac ham can thiet cho them sua xoa ban ke start
        self.getStreamDocument = getStreamDocument;
        self.getAllOriginal = getAllOriginal;
        self.getAdminstrativeUnitsCity = getAdminstrativeUnitsCity;
        self.getFarmByUserLogin = getFarmByUserLogin;
        self.getFarmByPage = getFarmByPage;
        self.getAllDistrict = getAllDistrict;
        self.getAllByParentId = getAllByParentId;
        self.sendEmail = sendEmail;
        self.getEmailAddressToSend = getEmailAddressToSend;
        self.getChildrenByParentId = getChildrenByParentId;
        self.getAllAnimalLastReported = getAllAnimalLastReported;
        self.downloadeForestProductsList = downloadeForestProductsList;
        self.getDetailsByStatusFP = getDetailsByStatusFP;
        self.getUserLogined = getUserLogined;
        //tran huu dat them cac ham can thiet cho them sua xoa ban ke end
        
        function getUserLogined(id) {
            var url = Url + 'user_attachments/getByUserId/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getDetailsByStatusFP(dto, successCallback, errorCallback) {
            var url = Url + 'forestproductslist/getDetailsByStatusFP';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function downloadeForestProductsList(searchDto) {
            var deferred = $q.defer();
            $http({
                url: settings.api.baseUrl + settings.api.apiPrefix + 'file/exportForestProductsList',
                method: "POST",
                data: searchDto,
                headers: { 'Content-type': 'application/json' },
                responseType: 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data);
                });
            return deferred.promise;
        }

        self.getListAnimalFromReportForm16BySearch = getListAnimalFromReportForm16BySearch;
        function getListAnimalFromReportForm16BySearch(dto, successCallback, errorCallback) {
            var url = Url + 'reportForm16/getListAnimalFromReportForm16BySearch';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function getAllAnimalLastReported(farmId) {
            var url = Url + 'animal_report/getAllAnimalLastReported/' + farmId;
            return utils.resolveAlt(url, 'POST', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }

        function getChildrenByParentId(parentId) {
            var url = Url + 'administrative/getChildrenByParentId/' + parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllByParentId(parentId) {
            var url = Url + 'administrative/getAllByParentId/' + parentId;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getCurrentUser() {
            var url = Url + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getForestProductListById(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function saveForestProductList(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function deletePageForestProductList(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist/' + id;

            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function deleteLinkedByExportAnimalId(id) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist/deleteLinkedByExportAnimalId/' + id;

            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getPageForestProductList(dto, pageIndex, pageSize) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist/searchByPage/' + pageIndex + '/' + pageSize;

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getAdministrativeUnitDtoByLoginUser() {
            var url = Url + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function sendEmail(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist/sendEmail';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getEmailAddressToSend(dto) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'forestproductslist/getEmailAddressToSend';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, angular.noop, angular.noop);
        }
        function getStream(searchFarmDto) {
            console.log("RUNNING");
            var deferred = $q.defer();

            $http({
                url: settings.api.baseUrl + settings.api.apiPrefix + 'file/downloadImportAnimal',
                method: "POST",//you can use also GET or POST
                data: searchFarmDto,
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
        function getFileById(id) {
            var url = baseUrl + 'file/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteImportAnimal(id, successCallback, errorCallback) {
            var url = baseUrl + 'delete/' + id;

            return utils.resolveAlt(url, 'DELETE', null, id, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }


        function getImportAnimal(id) {
            var url = baseUrl + 'get/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function createImportAnimal(dto, successCallback, errorCallback) {
            var url = baseUrl + 'create';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function updateImportAnimal(id, dto, successCallback, errorCallback) {
            var url = baseUrl + 'update/' + id;

            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getPageImportAnimal(pageIndex, pageSize) {
            var url = baseUrl + 'page/' + type + '/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        function getAllOriginal() {
            var url = Url + 'original/getalldtos';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getPageSearchImportAnimal(dto, pageIndex, pageSize, successCallback, errorCallback) {
            if (dto != null) {
                dto.isImportExportAnimalSeed = false;
                var url = baseUrl + 'searchDto/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }
        function getPageSearchImportAnimalSeed(dto, pageIndex, pageSize, successCallback, errorCallback) {
            if (dto != null) {
                dto.isImportExportAnimalSeed = null;
                var url = baseUrl + 'searchDto/' + pageIndex + '/' + pageSize;
                return utils.resolveAlt(url, 'POST', null, dto, {
                    'Content-Type': 'application/json; charset=utf-8'
                }, successCallback, errorCallback);

            }
        }
        function getBatchCode(importDate) {
            var url = baseUrl + 'autoGenericBatchCode/' + importDate;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        //tran huu dat tạo hàm xuất file word start
        function getStreamDocument(object) {
            console.log("RUNNING");
            var deferred = $q.defer();
            $http({
                url: settings.api.baseUrl + settings.api.apiPrefix + 'file/downloadForestList',
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
        //tran huu dat tạo hàm xuất file word end		

        function getAdminstrativeUnitsCity() {
            var url = Url + 'administrative/getall/city';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getFarmByUserLogin() {
            var url = Url + 'farm/getFarmByUserLogin';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getFarmByPage(pageIndex, pageSize) {
            var url = Url + 'farm/page/' + pageIndex + '/' + pageSize;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getAllDistrict(level) {
            var url = Url + 'administrative/getallbylevel/' + level;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }


        // function getSpeciality() {
        //     var url = baseUrl + 'speciality/1/1000';
        //     return utils.resolve(url, 'GET', angular.noop, angular.noop);
        // }
        var statusColor;
        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {

                if (row.canceled == 1 || row.canceled == 3 || row.canceled == 5) {
                    return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.addForestProductList(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">Chi tiết</i></a>'
                        + '<a href="#" class="btn btn-default margin-left-10" file-name="' + "'BangKeLamSan.doc'" + '" ng-click="$parent.vm.getFileWord(' + "'" + row.id + "'" + ')" file-download="$parent.vm.myBlobObjectWord"><i class="fa fa-file-excel-o"> Xuất dữ liệu word</i></a>';
                } else {
                    if (row.canceled == 2) {
                        return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.addForestProductList(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>'
                            + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.cancelForestProductList2(' + "'" + row.id + "'" + ')"><i class="icon-share margin-right-5">Xác nhận</i></a>'
                            // + '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5 text-danger" href="#" data-ng-click="$parent.vm.confirmDeleteForestProductList(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>'
                            // +'<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.vm.viewForestProductList(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                            + '<a href="#" class="btn btn-default margin-left-10" file-name="' + "'BangKeLamSan.doc'" + '" ng-click="$parent.vm.getFileWord(' + "'" + row.id + "'" + ')" file-download="$parent.vm.myBlobObjectWord"><i class="fa fa-file-excel-o"> Xuất dữ liệu word</i></a>';
                    } else if (row.canceled == 4) {
                        return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.addForestProductList(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">Chi tiết</i></a>'
                            + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.cancelForestProductList1(' + "'" + row.id + "'" + ')"><i class="icon-share margin-right-5">Chuyển đi</i></a>'
                            // + '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5 text-danger" href="#" data-ng-click="$parent.vm.confirmDeleteForestProductList(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>'
                            // +'<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.vm.viewForestProductList(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                            + '<a href="#" class="btn btn-default margin-left-10" file-name="' + "'BangKeLamSan.doc'" + '" ng-click="$parent.vm.getFileWord(' + "'" + row.id + "'" + ')" file-download="$parent.vm.myBlobObjectWord"><i class="fa fa-file-excel-o"> Xuất dữ liệu word</i></a>';
                    } else {
                        return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.successFPL(' + "'" + row.id + "'" + ')"><i class="icon-print margin-right-5">Hoàn thành</i></a>'
                            + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.cancelForestProductList(' + "'" + row.id + "'" + ')"><i class="icon-trash margin-right-5">Hủy</i></a>'
                            // + '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5 text-danger" href="#" data-ng-click="$parent.vm.confirmDeleteForestProductList(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>'
                            // +'<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.vm.viewForestProductList(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                            + '<a href="#" class="btn btn-default margin-left-10" file-name="' + "'BangKeLamSan.doc'" + '" ng-click="$parent.vm.getFileWord(' + "'" + row.id + "'" + ')" file-download="$parent.vm.myBlobObjectWord"><i class="fa fa-file-excel-o"> Xuất dữ liệu word</i></a>';
                    }

                }

            };
            //status
            var _tableExportRow = function (value, row, index) {
                return '<a href="#" class="btn btn-default margin-left-10" file-name="' + "'BangKeLamSan.doc'" + '" ng-click="$parent.vm.getFileWord(' + "'" + row.id + "'" + ')" file-download="$parent.vm.myBlobObjectWord"><i class="fa fa-file-excel-o"> Xuất dữ liệu word</i></a>';
            }
            //statusColor=2
            var _tableEditRow = function (value, row, index) {
                if (row.canceled == 2) {
                    return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.addForestProductList(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>';
                } else {
                    return '<a ng-show="!$parent.vm.isSdahView" disabled="disabled" class="green-dark margin-right-5 row-disabled-bkls" href="#" data-ng-click="$parent.vm.addForestProductList(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>';
                }

            }
            //statusColor=2
            var _tableConfirmRow = function (value, row, index) {
                if (row.canceled == 2) {
                    return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.cancelForestProductList2(' + "'" + row.id + "'" + ')"><i class="icon-share margin-right-5">Xác nhận</i></a>';
                } else {
                    return '<a class="green-dark margin-right-5 row-disabled-bkls" href="#" data-ng-click="$parent.vm.cancelForestProductList2(' + "'" + row.id + "'" + ')"><i class="icon-share margin-right-5">Xác nhận</i></a>';
                }

            }
            //statusColor=4
            var _tableDetailRow = function (value, row, index) {
                return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.addForestProductList(' + "'" + row.id + "'" + ',' + "'" + 2 + "'" + ')"><i class="icon-pencil margin-right-5">Chi tiết</i></a>';

            }
            //statusColor=4
            var _tableSendRow = function (value, row, index) {
                if (row.canceled == 4) {
                    return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.cancelForestProductList1(' + "'" + row.id + "'" + ')"><i class="icon-share margin-right-5">Chuyển đi</i></a>';
                } else {
                    return '<a class="green-dark margin-right-5 row-disabled-bkls" href="#" data-ng-click="$parent.vm.cancelForestProductList1(' + "'" + row.id + "'" + ')"><i class="icon-share margin-right-5">Chuyển đi</i></a>';
                }

            }
            //statusColor=6
            var _tableCompleteRow = function (value, row, index) {
                if (row.canceled != 1 && row.canceled != 3 && row.canceled != 5 && row.canceled != 4 && row.canceled != 2) {
                    return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.successFPL(' + "'" + row.id + "'" + ')"><i class="icon-print margin-right-5">Hoàn thành</i></a>';
                } else {
                    return '<a ng-show="!$parent.vm.isSdahView" class="green-dark margin-right-5 row-disabled-bkls" href="#" data-ng-click="$parent.vm.successFPL(' + "'" + row.id + "'" + ')"><i class="icon-print margin-right-5">Hoàn thành</i></a>';
                }

            }
            //statusColor=6
            var _tableStopRow = function (value, row, index) {
                if (row.canceled != 1 && row.canceled != 3 && row.canceled != 5 && row.canceled != 4) {
                    return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.vm.cancelForestProductList(' + "'" + row.id + "'" + ')"><i class="icon-trash margin-right-5">Hủy</i></a>';
                } else {
                    return '<a class="green-dark margin-right-5 row-disabled-bkls" href="#" data-ng-click="$parent.vm.cancelForestProductList(' + "'" + row.id + "'" + ')"><i class="icon-trash margin-right-5">Hủy</i></a>';
                }

            }

            var _tableInput = function (value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            //tran huu dat start
            var _imageFormat = function (value, row, index, field) {
                if (value && value.id) {
                    return "<img ng-src='" + settings.api.baseUrl + "public/publicAPI/downloadArticleImg/" + value.id + "' ngf-accept=''image/*'' style='height:100px;'>";
                }
                return "";
            }
            //tran huu dat end
            let _statusFormat = function (value, row, index) {
                if (value == 1) {
                    statusColor = 1;
                    return "Đã hủy";
                } else if (value == 3) {
                    statusColor = 3;
                    return "Đã hoàn thành";
                } else if (value == 2) {
                    statusColor = 2;
                    return "Mới tạo";
                } else if (value == 4) {
                    statusColor = 4;
                    return "Đã xác nhận";
                } else if (value == 5) {
                    statusColor = 5;
                    return "Không xác nhận";
                } else {
                    statusColor = 6;
                    return "Đã gửi";
                }
            }
            var _cellNowrap = function (value, row, index, field) {
                if (statusColor == 1) {
                    return {
                        classes: 'row-bg-color-orange',
                        css: { 'white-space': 'nowrap', }
                    };
                } else if (statusColor == 3) {
                    return {
                        classes: 'row-bg-color-green',
                        css: { 'white-space': 'nowrap', }
                    };
                } else if (statusColor == 5) {
                    return {
                        classes: 'row-bg-color-orange',
                        css: { 'white-space': 'nowrap', }
                    };
                } else {
                    return {
                        classes: '',
                        css: { 'white-space': 'nowrap', }
                    };
                }
            };

            var _cellNowrap2 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _formatterName = function (value, row, index, field) {
                if (value && value.name) {
                    return value.name;
                }
                return '';
            }
            var _numberFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return $filter('number')(value, 0);
            };
            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            let _farmOwnerName = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.ownerName;
            };

            let _farmBusinessRegistrationNumber = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.businessRegistrationNumber;
            };
            var _farmnewRegistrationCode = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.newRegistrationCode;
            };
            var _farmadressDetail = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.adressDetail;
            };
            var _farmownerPhoneNumber = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.ownerPhoneNumber;
            };
            var _farmNoteConfirm = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return value.noteConfirm;
            };
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };
            var _createDateFormatter = function (value, row, index) {

                if (!value) {
                    return '';
                } else {
                    var year = value[0];
                    var month = value[1];
                    var date = value[2];
                    var hour = value[3];
                    var minutes = value[4];
                    var seconds = value[5];
                    return date + '/' + month + '/' + year + ' | ' + hour + ':' + minutes + ':' + seconds;
                }

            };
            var _tableIndex = function (value, row, index, field) {
                return index + 1;
            };
            var _tableTextRight = function (value, row, index, field) {
                return {
                    classes: 'text-center'
                };
            };

            return [
                // {
                //     field: 'state',
                //     checkbox: true
                // }, 
                {
                    field: '',
                    title: 'TT',
                    switchable: true,
                    visible: true,
                    formatter: _tableIndex,
                    cellStyle: _tableTextRight
                },
                // {
                //     field: '',
                //     title: '{{"importAnimal.operating" | translate}}',
                //     switchable: true,
                //     visible: true,
                //     formatter: _tableOperation,
                //     cellStyle: _operationColStyle
                // }
                {
                    field: '',
                    title: 'Xuất ra word',
                    switchable: true,
                    visible: true,
                    formatter: _tableExportRow,
                    cellStyle: _operationColStyle
                }
                , {
                    field: '',
                    title: 'Xem BKLS',
                    switchable: true,
                    visible: true,
                    formatter: _tableDetailRow,
                    cellStyle: _operationColStyle
                }
                , {
                    field: '',
                    title: 'Sửa',
                    switchable: true,
                    visible: true,
                    formatter: _tableEditRow,
                    cellStyle: _operationColStyle
                }
                , {
                    field: '',
                    title: 'Xác nhận',
                    switchable: true,
                    visible: true,
                    formatter: _tableConfirmRow,
                    cellStyle: _operationColStyle
                }
                , {
                    field: '',
                    title: 'Gửi email',
                    switchable: true,
                    visible: true,
                    formatter: _tableSendRow,
                    cellStyle: _operationColStyle
                }
                , {
                    field: '',
                    title: 'Hoàn thành',
                    switchable: true,
                    visible: true,
                    formatter: _tableCompleteRow,
                    cellStyle: _operationColStyle
                }
                , {
                    field: '',
                    title: 'Hủy',
                    switchable: true,
                    visible: true,
                    formatter: _tableStopRow,
                    cellStyle: _operationColStyle
                }
                , {
                    field: 'canceled',
                    title: 'Trạng thái',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _statusFormat,
                    switchable: true
                }
                , {
                    field: 'farm',
                    title: 'Tên chủ lâm sản',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _farmOwnerName,
                    switchable: true
                }
                , {
                    field: 'farm',
                    title: 'Mã đăng ký doanh nghiệp',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _farmnewRegistrationCode,
                    switchable: true
                }
                , {
                    field: 'noteConfirm',
                    title: 'Kết quả xác minh kiểm tra',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    // formatter: _farmNoteConfirm,
                    switchable: true
                }
                , {
                    field: 'farm',
                    title: 'Địa chỉ',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _farmadressDetail,
                    switchable: true
                }
                , {
                    field: 'farm',
                    title: 'Số điện thoại',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _farmownerPhoneNumber,
                    switchable: true
                }
                , {
                    field: 'farm',
                    title: 'Số đăng ký doanh nghiệp',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _farmBusinessRegistrationNumber,
                    switchable: true
                }
                , {
                    field: 'code',
                    title: 'Mã kê lâm sản',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    // formatter:_dateFormatter,
                    switchable: true
                }
                , {
                    field: 'dateIssue',
                    title: 'Ngày khai báo',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _dateFormatter,
                    switchable: true
                }
                , {
                    field: 'original',
                    title: 'Nguồn gốc lâm sản',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }
                , {
                    field: 'invoiceCode',
                    title: 'Mã hóa đơn kèm theo',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }
                , {
                    field: 'vehicle',
                    title: 'Phương tiện vận chuyển',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }
                , {
                    field: 'invoiceDate',
                    title: 'Ngày hóa đơn kèm theo(Nếu có)',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _dateFormatter,
                    switchable: true
                }
                , {
                    field: 'vehicleRegistrationPlate',
                    title: 'Biển số/Số hiệu phương tiện',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    //formatter:_dateFormatter,//bỏ date
                    switchable: true
                }
                , {
                    field: 'transportDuration',
                    title: 'Thời gian vận chuyển',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    // formatter:_dateFormatter,
                    switchable: true
                }
                , {
                    field: 'transportStart',
                    title: 'Ngày bắt đầu vận chuyển',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _dateFormatter,
                    switchable: true
                }
                , {
                    field: 'transportEnd',
                    title: 'Ngày kết thúc vận chuyển',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _dateFormatter,
                    switchable: true
                }
                , {
                    //tran huu dat đổi biến cột administrativeUnitFrom
                    //field: 'transportFrom',
                    field: 'administrativeUnitFrom',
                    title: 'Vận chuyển từ',
                    sortable: true,
                    formatter: _formatterName,
                    cellStyle: _cellNowrap,
                    // formatter:_dateFormatter,
                    switchable: true
                }
                , {
                    //tran huu dat đổi biến cột administrativeUnitTo
                    field: 'administrativeUnitTo',
                    //field: 'transportTo',
                    title: 'Vận chuyển đến',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName,
                    // formatter:_dateFormatter,
                    switchable: true
                }, {
                    //tran huu dat đổi biến cột administrativeUnitTo
                    field: 'buyerName',
                    //field: 'transportTo',
                    title: 'Người mua',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }
                , {
                    field: 'buyerDetailAddress',
                    title: 'Thông tin chi tiết',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                }
                , {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName,
                    switchable: true,
                }
                , {
                    field: 'dateImport',
                    title: '{{"importAnimal.dateImport" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _dateFormatter,
                    switchable: true
                },
                {
                    field: 'statusConfirmName',
                    title: 'Người xác nhận',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true
                },
                {
                    field: 'statusConfirmDate',
                    title: 'Ngày xác nhận',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _dateFormatter,
                    switchable: true
                },
                //tran huu dat start them cot image
                {
                    field: 'file',
                    title: 'ảnh',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _imageFormat,
                    switchable: true
                },
                //tran huu dat end
            ];
        };

        function getTableDefinitionInjectionPlant() {
            var _tableOperation = function (value, row, index) {
                var ret = '';
                ret += '<a class="text-danger margin-right-10" uib-tooltip="{{\'importAnimal.edit\' | translate}}" tooltip-trigger="mouseenter" ng-show="!$parent.isView" data-ng-click="$parent.editInjectionPlant(' + "'" + index + "'" + ')"><i class="fa icon-pencil"></i>{{"importAnimal.edit" | translate}}</a>';
                ret += '<a class="green-dark" uib-tooltip="{{\'importAnimal.delete\' | translate}}" tooltip-trigger="mouseenter" ng-show="!$parent.isView" data-ng-click="$parent.deleteInjectionPlant(' + "'" + index + "'" + ')"><i class="fa icon-trash"></i>{{"importAnimal.delete" | translate}}</a>';

                return ret;
            };

            var _fileSize = function (value, row, index) {
                return $filter('fileSize')(value);
            };

            var _fileType = function (value, row, index) {
                return $filter('contentType')(value);
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };

            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '90px', 'text-align': 'center' }
                };
            };

            return [
                {
                    field: 'state',
                    checkbox: true
                },
                {
                    field: '',
                    title: '{{farm.operation | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _operationColStyle
                }, {
                    field: 'injectionDate',
                    title: '{{"importAnimal.injectionDate" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _dateFormatter
                }
                , {
                    field: 'drug',
                    title: '{{"importAnimal.drug" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
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

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editImportAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteImportAnimal(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>';
            };
            var _tableInput = function (value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _formatterName = function (value, row, index, field) {
                if (value && value.name) {
                    return value.name;
                }
                return '';
            }
            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
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

        function getTableDefinitionCode() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.editImportAnimal(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5">{{"importAnimal.edit" | translate}}</i></a>'
                    // + '<a class="green-dark margin-right-10" href="#" data-ng-click="$parent.previewOriginal(' + "'" + row.id + "'" + ')"><i class="fa fa-eye"></i></a>'
                    + '<a class="green-dark margin-right-5" href="#" data-ng-click="$parent.deleteImportAnimal(' + "'" + row.id + "'" + ')"><i class="fa fa-trash"></i>{{"importAnimal.delete" | translate}}</a>';
            };
            var _tableInput = function (value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };
            var _formatterName = function (value, row, index, field) {
                if (value && value.name) {
                    return value.name;
                }
                return '';
            }
            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };


            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };
            var _numberFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return $filter('number')(value, 0);
            };
            var _createDateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                } else {
                    var year = value[0];
                    var month = value[1];
                    var date = value[2];
                    var hour = value[3];
                    var minutes = value[4];
                    var seconds = value[5];
                    return date + '/' + month + '/' + year + ' | ' + hour + ':' + minutes + ':' + seconds;
                }

            };
            return [
                {
                    field: 'state',
                    checkbox: true,
                }

                , {
                    field: 'batchCode',
                    title: '{{"importAnimal.batchCode" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,

                }, {
                    field: 'animal',
                    title: '{{"importAnimal.animal.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName,
                    switchable: true,
                }, {
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter: _formatterName,
                    switchable: true,
                }, {
                    field: 'quantity',
                    title: '{{"importAnimal.quantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }, {
                    field: 'remainQuantity',
                    title: '{{"importAnimal.remainQuantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _numberFormatter
                }, {
                    field: 'dateImport',
                    title: '{{"importAnimal.dateImport" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    switchable: true,
                    formatter: _dateFormatter

                }
                ,
                {
                    field: 'created_by',
                    title: '{{"farm.createBy" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    // formatter: _numberFormat
                },
                {
                    field: 'create_date',
                    title: '{{"farm.create_date" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _createDateFormatter

                },


            ];
        }
    }
})();