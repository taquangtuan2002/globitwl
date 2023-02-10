(function () {

    'use strict';

    /* Setup global constants */
    Education.constant('constants', {
        cookies_user: 'education.user',
        oauth2_token: 'token'
    });

    Education.filter('weekDay', [function () {
        return function (input) {
            if (input < 2 || input > 8) {
                return '';
            }

            var arr = ['Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy', 'Chủ Nhật'];
            return arr[input - 2];
        };
    }]);

    /* Setup global settings */
    Education.factory('settings', ['$rootScope', '$state', 'constants', function ($rootScope, $state, constants) {
        // supported languages
        var settings = {
            layout: {
                pageSidebarClosed: false, // sidebar menu state
                pageContentWhite: true, // set page content layout
                pageBodySolid: true, // solid body color state
                pageAutoScrollOnLoad: 1000, // auto scroll to top on page load,
            },
            locale: 'vi-VN',
            //supportedLanguages: {"vi-VN": "Tiếng Việt", "en-US": "English", "pl-PL":"Język polski"},
			supportedLanguages: {"vi-VN": "Tiếng Việt", "en-US": "English"},
            assetsPath: 'assets',
            api: {
                baseUrl: Education.API_SERVER_URL,
                fileUrl:Education.FILE_SERVER_URL,
                apiPrefix: Education.API_PREFIX,
                clientId: Education.API_CLIENT_ID,
                clientKey: Education.API_CLIENT_KEY,
                oauth: {
                    token: constants.oauth2_token
                }
            },
            reasons: [
                
                {value: 'Xuất chết', name: 'Xuất chết'},
                {value: 'Xuất hủy', name: 'Xuất hủy'},
    			{value: 'Xuất thải loại', name: 'Xuất thải loại'},
    			{value: 'Xuất chuyển loại', name: 'Xuất chuyển loại'},
                
            ],
            types: [
                {value: 1, name: 'Xuất bán'},
                {value: 3, name: 'Điều chuyển loại'},
                {value: 2, name: 'Xuất khác'}
                
            ],
            animalGenders: [// Giới tính của động vật trong individual_animal
                {value: 1, name: 'Đực'},
                {value: 2, name: 'Cái'},
                {value: 0, name: 'Không rõ'}
            ],
            ENUM_AnimalGenders:{
                male:1,
                female:2,
                unGender: 0
            },
            IndividualAnimalStatuses:[// Trạng thái trong individual_animal
                {value: 0, name: "Bình thường"},
            ],
            AnimalReportDataType: [// loại khai báo động vật nuôi
                {value: 1, name: "Con bố mẹ"},
                {value: 2, name: "Con non trên 1 tuổi"},
                {value: 3, name: "Con con dưới 1 tuổi"},
                {value: 4, name: "Con hậu bị"},
                {value: 7, name: "Nhập"},
                {value: 8, name: "Xuất"},
            ],
            AnimalReportDataFormType: {
                "firstType": 1,
                "secondType": 2,
                "thirdType": 3
            },
            AnimalReportDataTypeENUM: {
                animalParent: 1,//Con bố mẹ
                childOver1YearOld: 2,//Con non trên 1 tuổi
                childUnder1YearOld: 3,//Con con dưới 1 tuổi
                gilts: 4//Con hậu bị
            },
            // Hình thức sinh sản: đẻ trứng , đẻ con
            ReproductionForm:{
                giveAnimalBirth: {value: 1, name: "Đẻ con"},
                giveEggBirth: {value: 2, name: "Đẻ trứng"}
            },
            //tran huu dat them lí do nhập vào form16 start
            ImportReason:[
                {value: 1, name: "Mua"},
                {value: 2, name: "Sinh sản"},
                {value: 3, name: "Chuyển loại"},
                {value: 4, name: "Nhận cho tặng"},
                {value: 5, name: "Khác"}
            ],
            ExportReason:[
                {value: 1, name: "Bán"},
                {value: 2, name: "Chết"},
                {value: 3, name: "Chuyển loại"},
                {value: 4, name: "Biếu, cho tặng"},
                {value: 5, name: "Khác"}
            ],
            
            //tran huu dat them lí do nhập vào form16 end
            AnimalRequiredStatus:{
                PENDING:{value: 1, name: 'Chờ duyệt'},//chờ duyệt
                DONE:{value:2, name: 'Đã được duyệt'},// duyệt
                REJECTED:{value: 3, name: 'Đã bị hủy bỏ'},// từ chối
                DELETED:{value: 4, name: 'Deleted'},
                NEW:{value: 5,name:"Mới tạo"},
            },
            NumberZero: 0,
            BEAR_CODE: "BEAR",
            DUPLICATE_CHIP_CODE: "ErrorDuplicatChipCode",
            BEAR_WITH_CHIP_LEVEL: 6,
            BEAR_WITHOUT_CHIP_LEVEL: 7,
            animalParent: 4,// Con bố mẹ
            GILTS: 6,
            MONTHS: [
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
            ]
        };

        $rootScope.settings = settings;

        return settings;
    }]);

    /**
     * Set focus on element
     */
    Education.factory('focus', ['$timeout', '$window', function ($timeout, $window) {
        return function (id) {
            $timeout(function () {
                var element = $window.document.getElementById(id);
                if (element)
                    element.focus();
            });
        };
    }]);

    /**
     * Invoke bstable API_PREFIX
     */
    Education.factory('bsTableAPI', ['$window', function ($window) {
        return function (id, api, parameter) {
            var element = $window.document.getElementById(id);
            if (element && element.hasAttribute('bs-table-control')) {
                return $(element).bootstrapTable(api, parameter);
            }
        };
    }]);

    /**
     * No server response interceptor
     */
    Education.factory('ServerExceptionHandlerInterceptor', [
        '$q',
        'toastr',
        '$cookies',
        '$injector',
        'blockUI',
        'constants',
        function ($q, toastr, $cookies, $injector, blockUI, constants) {
            return {
                responseError: function (rejection) {
                    if (rejection.status <= 0) {
                        toastr.warning('Không thể kết nối đến máy chủ.', 'Cảnh báo');
                    }

                    if (rejection.status == 400) {
                        toastr.error('Đã có lỗi xảy ra. Xin vui lòng thử lại.', 'Lỗi (400)');
                    }

                    if (rejection.status == 401) {
                        // Force refresh token in application.run()
                    }

                    if (rejection.status == 403) {
                        toastr.error('Bạn không có quyền thực hiện thao tác này.', 'Lỗi (403)');
                    }

                    if (rejection.status == 500) {
                        toastr.error('Đã có lỗi xảy ra với hệ thống. Xin vui lòng thử lại sau.', 'Lỗi (500)');
                    }

                    blockUI.stop();

                    return $q.reject(rejection);
                }
            };
        }
    ]);

})();