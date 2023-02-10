(function () {

    'use strict';

    /* Setup App Main Controller */
    Education.controller('AppController', ['$rootScope', '$scope', '$cookies', '$state', '$timeout', 'constants', 'settings', '$uibModal', 'toastr', 'Upload', 'focus', 'UserService', 'OAuth',
        function ($rootScope, $scope, $cookies, $state, $timeout, constants, settings, modal, toastr, Upload, focus, userService, OAuth) {
            $scope.$on('$viewContentLoaded', function () {

            });
            $scope.administrativeUnits = [];
            $scope.selectedCity = [];
            $scope.selectedDist = [];
            $scope.currentUser = { displayName: '' };
            $rootScope.$on('$onCurrentUserData', function (event, data) {
                if (data != null) {
                    $scope.currentUser = data;
                    // $scope.checkDataUser();
                }

                if ($scope.currentUser.person && $scope.currentUser.person.displayName) {
                    $scope.currentUser.displayName = $scope.currentUser.person.displayName;
                } else {
                    $scope.currentUser.displayName = $scope.currentUser.username;
                }
            });

            $rootScope.$on('$userPhotoChanged', function (event, data) {
                $scope.currentUser.hasPhoto = true;

                angular.element('#_user_profile_photo_small').attr('src', settings.api.baseUrl + 'public/users/photo/' + $scope.currentUser.username + '?d=' + moment().format('MMDDYY-hhmmss'));
                angular.element('#_user_profile_photo_big').attr('src', settings.api.baseUrl + 'public/users/photo/' + $scope.currentUser.username + '?d=' + moment().format('MMDDYY-hhmmss'));
            });


            function getDataByCombobox(au) {
                $scope.selectedCity = [];
                $scope.selectedDist = [];
                // get all city

                if (!au || au.length == 0) return;

                let checkCity = au.filter(a => a.parentDto != null).length == 0 ? true : false;
                if (checkCity) {
                    $scope.selectedCity = au;
                    $scope.administrativeUnits = au;
                    console.log($scope.administrativeUnits);
                    // nếu là tk cấp tỉnh
                    if (au.length == 1) {
                        userService.getAllByParentId(au[0].id).then(function (data) {
                            if (data != null && data.length > 0) {
                                $scope.adminstrativeUnits_Dist = data;
                            }
                        })
                    }
                    // nếu là tài khoản liên tỉnh thì tạm thời ko cho chọn huyện
                    if (au.length > 1) {
                        $scope.adminstrativeUnits_Dist = [];
                    }

                    return;
                }

                let checkDist = au.filter(a => a.parentDto.parentDto != null).length == 0 ? true : false;

                if (checkDist) {
                    // nếu là tài khoản cấp huyện
                    if (!$scope.selectedCity) $scope.selectedCity = [];
                    $scope.selectedCity.push(au[0].parentDto);
                    $scope.selectedDist = au;
                    $scope.administrativeUnits = au;
                    console.log($scope.administrativeUnits);
                    userService.getAllByParentId(au[0].parentDto.id).then(function (data) {
                        if (data != null && data.length > 0) {
                            $scope.adminstrativeUnits_Dist = data;
                            // $scope.administrativeUnits = data;
                            // console.log($scope.administrativeUnits);
                        }
                    })
                }

            }

            /**
             * Tải lên mẫu chữ ký
             */
            $scope.files = [];
            $scope.changeUploadSignatureTemplate = function () {
                userService.getUser($scope.currentUser.id).then(function (data) {
                    if (data && data.id) {
                        userService.getAdministrativeUnitByUserId(data.id).then(function (data) {
                            if (data != null && data.length > 0) {
                                getDataByCombobox(data);
                                $scope.administrativeUnits = data;
                            }
                        });
                    }
                });
                $scope.listAdministrativeOrganization = [];
                userService.getAdministrativeOrganizationByUser().then(function (data) {
                    $scope.listAdministrativeOrganization = data;
                    console.log("abc: ",data);
                });

                userService.getAdminstrativeUnitsCity().then(function (data) {
                    $scope.adminstrativeUnits_City = data;
                });

                userService.getUserAttachmentsByUserId($scope.currentUser.id).then(function (data) {
                    if (data) {
                        $scope.files = data;
                        if ($scope.files.length == 0) {
                            $scope.files.push({ a: "" });
                        }
                        if ($scope.files && $scope.files.length > 0) {
                            $scope.organizationName = $scope.files[0].organizationName;
                            $scope.positionName = $scope.files[0].positionName;
                        }
                        $scope.modalInstance_upload_signature_template = modal.open({
                            animation: true,
                            templateUrl: 'upload_signature_template.html',
                            scope: $scope,
                            backdrop: 'static',
                            size: 'lg'
                        });
                    }
                    else {
                        toastr.error("Có lỗi xảy ra khi tải dữ liệu.", "Lỗi");
                    }
                });
            }

            $scope.onFmsadminstrativeUnitCitySelected = function (city) {
                if (city != null && city.id != null) {
                    $scope.selectedCity = [];
                    $scope.selectedCity.push(city);
                    // console.log($scope.selectedCity);
                    $scope.selectedDist = [];
                    $scope.administrativeUnits = [];
                    $scope.administrativeUnits.push(city);
                    userService.getAllByParentId(city.id).then(function (data) {
                        if (data != null && data.length > 0) {
                            $scope.adminstrativeUnits_Dist = data;
                            console.log($scope.adminstrativeUnits_Dist);
                            $scope.selectedDist = null;
                        } else {
                            $scope.selectedDist = null;
                            $scope.district = null;
                            $scope.adminstrativeUnits_Dist = [];
                            console.log($scope.adminstrativeUnits_Dist);
                        }
                    });
                } else {
                    $scope.selectedDist = null;
                    $scope.district = null;
                    $scope.adminstrativeUnits_Dist = [];
                }
            }
            $scope.onFmsadminstrativeUnitDistSelected = function (district) {
                $scope.selectedDist = [];
                $scope.selectedDist.push(district);
                $scope.administrativeUnits = $scope.selectedDist;
            }

            //tran huu dat start thêm hàm lưu hình ảnh
            $scope.uploadFile = null;
            $scope.forestProductfile = null;
            $scope.uploadFilesForest = function (file, errFile) {
                $scope.uploadFile = file;
                if ($scope.uploadFile != null) {
                    Upload.upload({
                        url: settings.api.baseUrl + 'api/file/uploadArticleImg',
                        method: 'POST',
                        data: { uploadfile: $scope.uploadFile }
                    }).then(function (successResponse) {
                        var file = successResponse.data;
                        if (file) {
                            $scope.forestProductfile = file;
                        }

                    }, function (errorResponse) {
                        toastr.error('Error submitting data...', 'Error');
                    });
                }
            }
            //tran huu dat end thêm hàm lưu hình ảnh

            //tran huu dat start theem hamf lay thong bao
            $scope.notifications = null;
            $scope.countNotificationNotViewed = 0;
            $scope.getAllNotification = function () {
                $scope.countNotificationNotViewed = 0;
                userService.findNotification().then(function (data) {
                    if (data) {
                        $scope.notifications = data;
                        for (let i = 0; i < $scope.notifications.length; i++) {
                            if ($scope.notifications[i].viewed === false) {
                                $scope.countNotificationNotViewed += 1;
                            }
                        }
                    }
                })
            }
            $scope.getAllNotification();

            //tran huu dat end theem hamf lay thong bao

            //tran huu dat ham xuat file word start
            $scope.myBlobObjectWord = undefined;
            $scope.getFileWord = function (detailsMessage) {
                console.log('download started, you can show a wating animation');
                UserService.getStreamDocument(detailsMessage)
                    .then(function (data) {//is important that the data was returned as Aray Buffer
                        console.log('Stream download complete, stop animation!');
                        $scope.myBlobObjectWord = new Blob([data], { type: 'application/msword' });
                    }, function (fail) {
                        console.log('Download Error, stop animation and show error message');
                        $scope.myBlobObjectWord = [];
                    });
            };

            //tran huu dat ham xuat file word end

            //tran huu dat thêm hàm hiển thi modal thông báo start
            $scope.detailsMessage = null;
            $scope.tableName = null;
            $scope.isviewed = null;
            $scope.notificationDto;
            $scope.typeNotification;
            $scope.getDetailsSystemMessage = function (notification) {
                $scope.tableName = notification.systemMessage.tableName;
                $scope.notificationDto = notification;
                if ($scope.notificationDto.viewed === false) {
                    $scope.notificationDto.viewed = true;
                    userService.updateNotification($scope.notificationDto).then(function (data) {
                    });
                    $scope.getAllNotification();
                }


                userService.getDetailsSystemMessage(notification.systemMessage.id).then(function (data) {
                    if (data) {
                        $scope.detailsMessage = data;
                        if ($scope.tableName == "AnimalRequired") {
                            $scope.goDetails();
                        } else {
                            $scope.modalInstance_system_message = modal.open({
                                animation: true,
                                templateUrl: 'system_message.html',
                                scope: $scope,
                                size: 'md'
                            });
                        }

                    }
                    else {
                        toastr.error("Có lỗi xảy ra khi tải dữ liệu.", "Lỗi");
                    }
                });

            }
            //tran huu dat thêm hàm hiển thi modal thông báo end
            $scope.goDetails = function () {
                $state.go('application.animal_required', {
                    id: $scope.detailsMessage.id
                });
                //$state.go('application.animal_required');
            }

            //// Upload file
            $scope.uploadedFile = null;
            $scope.errorFile = null;
            $scope.uploadFiles = function (file, errFiles) {
                $scope.uploadedFile = file;
                if ($scope.uploadedFile != null) {
                    Upload.upload({
                        url: settings.api.baseUrl + 'api/file/userAttachments',
                        method: 'POST',
                        data: { uploadfile: $scope.uploadedFile }
                    }).then(function (successResponse) {
                        var userAttachment = successResponse.data;
                        if ($scope.uploadedFile && (!$scope.errorFile || ($scope.errorFile && $scope.errorFile.$error == null))
                            && userAttachment && userAttachment.file && userAttachment.file.id) {
                            if (!$scope.files || $scope.files.length <= 0) {
                                $scope.files = [];
                            }
                            //tạm thời chỉ update 1 ảnh
                            // $scope.files.push(userAttachment);

                            if ($scope.files && $scope.files.file) {
                                $scope.files.file = [];
                            } else {
                                $scope.files.file = [];
                            }
                            if ($scope.files.length == 0) {
                                $scope.files.push(userAttachment);
                            }

                            $scope.files[0].file = userAttachment.file;
                            $scope.files[0].fileName = userAttachment.fileName;
                            $scope.myImage = $rootScope.API_SERVER_URL + "/public/publicAPI/downloadArticleImg/" + userAttachment.file.id;

                        }
                    }, function (errorResponse) {
                        toastr.error('Error submitting data...', 'Error');
                    }, function (evt) {
                        console.log('progress: ' + parseInt(100.0 * evt.loaded / evt.total) + '%');
                    });
                }
            };

            $scope.removeImage = function (index) {
                if ($scope.files != null && $scope.files.length > 0) {
                    for (var i = 0; i < $scope.files.length; i++) {
                        if (i == index) {
                            // $scope.files.splice(i, 1);
                            if ($scope.files[i].file) {
                                $scope.files[i].file = {};
                                $scope.files[i].fileName = null;
                            }
                            $scope.closeShowImage();
                        }
                    }
                }
            }

            $scope.openFile = function (file) {
                $scope.fileOpen = null;
                if (file != null) {
                    $scope.fileOpen = file;
                    $scope.modalInstanceOpenImageHome = modal.open({
                        animation: true,
                        templateUrl: 'open_file_images_user_attachment_home.html',
                        scope: $scope,
                        size: 'lg'
                    });
                }
            }

            $scope.closeShowImage = function () {
                if ($scope.modalInstanceOpenImageHome != null) {
                    $scope.modalInstanceOpenImageHome.close();
                }
            }

            $scope.saveUploadSignatureTemplate = function () {
                if ($scope.files && $scope.files.length > 0) {
                    console.log($scope.administrativeUnits);
                    
                    userService.saveAdministrativeUnit($scope.currentUser.id, $scope.administrativeUnits);
                    userService.saveFiles($scope.files).then(function (data) {
                        //vm.getUsers();	 
                        if (data && data.length > 0) {
                            $scope.files = [];
                            $scope.myImage = null;
                            if ($scope.modalInstance_upload_signature_template != null) {
                                $scope.modalInstance_upload_signature_template.close();
                            }
                            toastr.success("Bạn đã lưu thành công.", "Thông báo");
                        }
                        else {
                            toastr.error("Có lỗi xảy ra khi lưu một bản ghi.", "Lỗi");
                        }
                    });
                }
            }

            //tran huu dat test cat anh start
            $scope.myImage;
            $scope.myCroppedImage = '';
            $scope.imageData;
            $scope.saveFileAfterCrop = function (myCropped) {
                // if( $scope.files[0].file==null){
                //     toastr.warning('Vui lòng chọn ảnh để lưu', 'Cảnh báo');
                //     return;
                // }
                console.log($scope.files[0])
                if (!$scope.files[0].administrativeOrganization.id || !$scope.files[0].displayNameAccountUser || !$scope.files[0].email
                    || !$scope.files[0].department || !$scope.files[0].phoneNumber || !$scope.files[0].email) {
                    toastr.warning('Bạn vui lòng nhập đầy đủ thông tin tài khoản', 'Thông báo');
                    return;
                } else {
                    if ($scope.files[0] && $scope.files[0].file && document.getElementById("imageCropped")) {
                        var image = document.getElementById("imageCropped");
                        $scope.myCroppedImage = image.getAttribute("ng-src");
                        var mimeString = $scope.myCroppedImage.split(',')[0].split(':')[1].split(';')[0];
                        var dataString = window.atob($scope.myCroppedImage.split(',')[1]);
                        var dataArray = [];
                        for (var i = 0; i < dataString.length; i++) {
                            dataArray.push(dataString.charCodeAt(i));
                        }
                        var name = $scope.uploadedFile.name;
                        console.log("dataArray", dataArray);
                        $scope.imageData = new Blob([new Uint8Array(dataArray)], { type: mimeString });
                        $scope.imageData.name = name;
                        console.log("imageData", $scope.imageData);
                        $scope.uploadFiles($scope.imageData, null);

                    }
                    $scope.saveUploadSignatureTemplate();
                }
            }

            $scope.checkDataUser = function () {
                userService.getUserAttachmentsByUserId($scope.currentUser.id).then(function (data) {
                    if (data) {
                        $scope.files = data;
                        if ($scope.files.length == 0) {
                            $scope.files.push({ a: "" });
                        }
                        if ($scope.files && $scope.files.length > 0) {
                            $scope.organizationName = $scope.files[0].organizationName;
                            $scope.positionName = $scope.files[0].positionName;
                        }
                        // if (!$scope.files[0].organizationName || !$scope.files[0].organizationAddress || !$scope.files[0].numberPhoneOffice
                        //     || !$scope.files[0].emailOffice || !$scope.files[0].displayName || !$scope.files[0].positionName
                        //     || !$scope.files[0].phoneNumberAgencyRepresentative || !$scope.files[0].emailAgencyRepresentative || !$scope.files[0].displayNameAccountUser || !$scope.files[0].department
                        //     || !$scope.files[0].positionNameAccountUser || !$scope.files[0].phoneNumber || !$scope.files[0].email) {
                        //     toastr.warning('Bạn vui lòng nhập thông tin tài khoản', 'Thông báo');
                        //     $scope.changeUploadSignatureTemplate();
                        // }
                        if (
                            !$scope.files[0].administrativeOrganization ||
                            !$scope.files[0].displayNameAccountUser || !$scope.files[0].department ||
                            !$scope.files[0].positionNameAccountUser || !$scope.files[0].phoneNumber ||
                            !$scope.files[0].email) {
                            toastr.warning('Bạn vui lòng nhập thông tin tài khoản', 'Thông báo');
                            $scope.changeUploadSignatureTemplate();
                        }
                    }
                });
            };

            //tran huu dat test cat anh end
            /**
             * hết Tải lên mẫu chữ ký
             */

            /**
             * Change password
             */
            $scope.changePassword = function () {
                var modalInstance = modal.open({
                    animation: true,
                    templateUrl: 'change_password_modal.html',
                    scope: $scope,
                    size: 'md'
                });

                $scope.changePasswordObj = {
                    currentPassword: null,
                    newPassword: null,
                    newPasswordRep: null,
                    saveNewPassword: function () {
                        if (!$scope.changePasswordObj.currentPassword || $scope.changePasswordObj.currentPassword.trim() == '') {
                            toastr.error('Vui lòng nhập mật khẩu hiện thời của bạn.', 'Thông báo');
                            focus('_frm_change_password.current_password');
                            return;
                        }

                        if (!$scope.changePasswordObj.newPassword || $scope.changePasswordObj.newPassword.trim() == '') {
                            toastr.error('Vui lòng nhập mật khẩu mới.', 'Thông báo');
                            focus('_frm_change_password.new_password');
                            return;
                        }

                        if ($scope.changePasswordObj.newPassword != $scope.changePasswordObj.newPasswordRep) {
                            toastr.error('Mật khẩu mới không khớp nhau. Vui lòng kiểm tra lại!', 'Thông báo');
                            focus('_frm_change_password.new_password_rep');
                            return;
                        }

                        userService.passwordValid({ password: $scope.changePasswordObj.currentPassword }).then(function (data) {
                            if (!data) {
                                toastr.error('Mật khẩu hiện thời không đúng. Vui lòng kiểm tra lại!', 'Thông báo');
                                focus('_frm_change_password.current_password');
                                return;
                            } else {
                                var userObj = {
                                    id: $scope.currentUser.id,
                                    username: $scope.currentUser.username,
                                    password: $scope.changePasswordObj.newPassword
                                };
                                userService.changePasswordSelf(userObj, function success() {

                                    toastr.info('Bạn đã cập nhật thành công mật khẩu của mình.', 'Thông báo');

                                    modal.open({
                                        animation: true,
                                        templateUrl: 'password_changed_modal.html',
                                        scope: $scope,
                                        backdrop: 'static',
                                        keyboard: false,
                                        size: 'md'
                                    });

                                }, function error() {
                                    toastr.error('Có lỗi xảy ra khi cập nhật mật khẩu. Mật khẩu của bạn vẫn được giữ nguyên như cũ.', 'Thông báo');
                                }).then(function (data) {
                                    modalInstance.close();
                                });
                            }
                        });
                    }
                };
            };

            /**
             * Upload profile photo
             */
            $scope.profilePhoto = {
                uploadedFile: null,
                errorFile: null,
                modalDialog: null,
                loadedImageData: '',
                cropper: { x: 0, y: 0, w: 0, h: 0, cropWidth: 300, cropHeight: 300 },
                croppedImage: '',
                photoUrl: '',
                showUploadModal: function () {
                    if (!$scope.currentUser || !$scope.currentUser.id) {
                        return;
                    }

                    $scope.profilePhoto.modalDialog = modal.open({
                        animation: true,
                        templateUrl: 'upload_photo_modal.html',
                        scope: $scope,
                        backdrop: 'static',
                        keyboard: false,
                        size: 'md'
                    });
                },
                triggerUpload: function (file, errFiles) {
                    $scope.profilePhoto.uploadedFile = file;
                    $scope.profilePhoto.errorFile = errFiles && errFiles[0];
                },
                startUploadFile: function (file) {
                    if (file) {
                        var url = settings.api.baseUrl + settings.api.apiPrefix + 'users/photo/upload';

                        file.upload = Upload.upload({
                            url: url,
                            data: { file: file }
                        }).progress(function (evt) {
                            $scope.profilePhoto.uploadedFile.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                        }).success(function (data, status, headers, config) {
                            $timeout(function () {
                                $scope.profilePhoto.uploadedFile = null;
                                $scope.profilePhoto.errorFile = null;
                                $scope.profilePhoto.modalDialog.close();

                                $scope.profilePhoto.photoUrl = settings.api.baseUrl + 'public/users/photo/' + $scope.currentUser.username;

                                // Start cropping...
                                $scope.profilePhoto.modalDialog = modal.open({
                                    animation: true,
                                    templateUrl: 'crop_photo_modal.html',
                                    scope: $scope,
                                    backdrop: 'static',
                                    keyboard: false,
                                    size: 'md'
                                });

                                $scope.profilePhoto.modalDialog.result.then(function (confirm) {
                                    $scope.profilePhoto.cropper.x = $scope.profilePhoto.cropper.cropImageLeft;
                                    $scope.profilePhoto.cropper.y = $scope.profilePhoto.cropper.cropImageTop;
                                    $scope.profilePhoto.cropper.w = $scope.profilePhoto.cropper.cropImageWidth;
                                    $scope.profilePhoto.cropper.h = $scope.profilePhoto.cropper.cropImageHeight;
                                    $scope.profilePhoto.cropper.user = $scope.currentUser;

                                    userService.cropProfilePhoto($scope.profilePhoto.cropper).then(function (data) {
                                        // emit event...
                                        $rootScope.$emit('$userPhotoChanged', data);
                                    });
                                });
                            }, 500);
                        });
                    }
                },
                closeUploadFile: function () {
                    $scope.profilePhoto.uploadedFile = null;
                    $scope.profilePhoto.errorFile = null;

                    if ($scope.profilePhoto.modalDialog) {
                        $scope.profilePhoto.modalDialog.close();
                    }
                }
            };

            /**
             * Check if the current user has role [ROLE NAME]
             *
             * @param user
             * @param roleName
             */
            $scope.hasRole = function (user, roleName) {

                if (!user || !roleName || !user.id || !user.roles || user.roles.length <= 0) {
                    return false;
                }

                var roles = user.roles;
                angular.forEach(roles, function (role) {
                    if (role.name && role.name.toLowerCase() == roleName.toLowerCase()) {
                        return true;
                    }
                });

                return false;
            };

            /**
             * Check if the current user has any of the given roles
             *
             * @param user
             * @param roleNames
             * @returns {boolean}
             */
            $scope.hasAnyRoles = function (user, roleNames) {

                if (!user || !user.id || !user.roles || user.roles.length <= 0 || !roleNames || roleNames.length <= 0) {
                    return false;
                }

                var ret = false;
                var _roles = user.roles;
                angular.forEach(_roles, function (_role) {
                    if (_role.authority) {
                        angular.forEach(roleNames, function (roleName) {
                            if (roleName) {
                                if (roleName.toLowerCase() == _role.authority.toLowerCase()) {
                                    ret = true;
                                }
                            }
                        });
                    }
                });

                return ret;
            };

            /**
             * Check if the current user has all of the given roles
             *
             * @param user
             * @param roleNames
             * @returns {boolean}
             */
            $scope.hasAllRoles = function (user, roleNames) {

                if (!user || !user.id || !user.roles || user.roles.length <= 0 || !roleNames || roleNames.length <= 0) {
                    return false;
                }

                var _roles = user.roles;
                angular.forEach(_roles, function (_role) {
                    if (_role.authority) {
                        var inAllRole = true;
                        angular.forEach(roleNames, function (roleName) {
                            if (roleName) {
                                inAllRole = inAllRole && (roleName.toLowerCase() == _role.authority.toLowerCase());
                            }
                        });

                        return inAllRole;
                    }
                });

                return false;
            };

            /**
             * Logout...
             */
            $scope.logout = function () {
                OAuth.revokeToken();
                $cookies.remove(constants.oauth2_token);
                $state.go('login');
            };
        }
    ]);

    /***
     Layout Partials.
     By default the partials are loaded through AngularJS ng-include directive. In case they loaded in server side(e.g: PHP include function) then below partial
     initialization can be disabled and Layout.init() should be called on page load complete as explained above.
     ***/

    /* Setup Layout Part - Header */
    Education.controller('HeaderController', ['$scope', '$state', '$translate', 'settings', function ($scope, $state, $translate, settings) {
        $scope.$on('$includeContentLoaded', function () {
            Layout.initHeader($state);
            Layout.initContent();
        });
        // Change language key -> language label. E.g. en-US -> English
        function getLanguageLabel() {
            if ($scope.currentLanguage == null || $scope.currentLanguage == '') {
                $scope.currentLanguage = $translate.use();
            }
            if ($scope.currentLanguage == '') {
                $scope.currentLanguage = settings.locale;
            }

            angular.forEach(settings.supportedLanguages, function (value, key) {
                if (key == $scope.currentLanguage) {
                    $scope.currentLanguage = value;
                }
            });
        }

        // Getting current language
        getLanguageLabel();

        $scope.changeLanguage = function (languageKey) {
            if (languageKey != '') {
                $translate.use(languageKey);
                $scope.currentLanguage = languageKey;
            }

            getLanguageLabel();
        };
    }]);

    /* Setup Layout Part - Sidebar/Page header */
    Education.controller('SidebarController', ['$scope', 'settings', function ($scope, settings) {
        $scope.$on('$includeContentLoaded', function () {
            Layout.initSidebar(); // init sidebar
        });
    }]);

    /* Setup Layout Part - Quick Sidebar */
    Education.controller('QuickSidebarController', ['$scope', function ($scope) {
        $scope.$on('$includeContentLoaded', function () {
            setTimeout(function () {
                // QuickSidebar.init(); // init quick sidebar
            }, 2000)
        });
    }]);

    Education.controller('PageHeadController', ['$scope', function ($scope) {
        $scope.$on('$includeContentLoaded', function () {
            // Demo.init(); // init theme panel
        });
    }]);

    /* Setup Layout Part - Theme Panel */
    Education.controller('ThemePanelController', ['$scope', function ($scope) {
        $scope.$on('$includeContentLoaded', function () {
            // ThemeSettings.init(); // init theme panel
        });
    }]);

    /* Setup Layout Part - Footer */
    Education.controller('FooterController', ['$scope', 'settings', function ($scope, settings) {
        $scope.$on('$includeContentLoaded', function () {
            Layout.initFooter();
        });
    }]);





})();