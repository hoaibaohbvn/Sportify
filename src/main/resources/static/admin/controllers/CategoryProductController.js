app.controller('CategoryProductController', function ($scope, CategoryProductService) {
	// Gọi API để lấy danh sách tuyển dụng
	$scope.categoryProducts = [];
	CategoryProductService.getAll().then(function (response) {
	    $scope.categoryProducts = response.data;
	    $(document).ready(function () {
	        $scope.loadDataTable($scope.categoryProducts);
	    });
	}).catch(function (error) {
	    console.error('Lỗi khi lấy danh sách phụ cấp:', error);
	});
	// Hàm đổ dữ liệu lên table
	$scope.loadDataTable = function (categoryProducts) {
	    var table = $('#tableCategoryProduct');
	
	    if ($.fn.DataTable.isDataTable(table)) {
	        table.DataTable().destroy();
	    }
	
	    table.DataTable({
	        searching: false,
	        data: categoryProducts, // Dữ liệu được truyền vào DataTables
	        columns: [
	            {
	                data: null,
	                render: function (data, type, row, meta) {
	                    // Render giao diện cho cột "#"
	                    return meta.row + 1;
	                }
	            },
	            { data: 'categoryname' }, // Cột "Name"
	            
	            {
	                data: null, // Cột "Action"
	                render: function (data, type, row) {
	                    // Render giao diện cho cột "Action"
	                    return '<div class="dropdown dropdown-action d-flex justify-content-end"><a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="material-icons">more_vert</i></a><div class="dropdown-menu dropdown-menu-right"><a class="dropdown-item edit-categoryproduct" href="#" data-toggle="modal" data-target="#add_categoryproduct" data-categoryproduct-id="' + row.categoryid + '"><i class="fa fa-pencil m-r-5"></i>Chỉnh sửa</a><a class="dropdown-item delete-categoryproduct" data-target="#delete_categoryproduct"  data-categoryproduct-id="' + row.categoryid + '" data-toggle="modal"><i class="fa fa-trash-o m-r-5"></i>Xóa</a></div></div>';
	                }
	            }
	        ]
	    });
	    // Gắn trình xử lý sự kiện click vào phần tử chỉnh sửa
	    $(document).on('click', '.edit-categoryproduct', function () {
	        var categoryproductid = $(this).data('categoryproduct-id');
	        $scope.edit(categoryproductid);
	    });
	    $(document).on('click', '.delete-categoryproduct', function () {
	         allowanceId = $(this).data('categoryproduct-id');
	 
	        // $scope.deleteAllowanceType(allowanceId);
	    });
	
	}
	        var categoryproductid;
	
	$scope.categoryproductData = {};
	
	// Gọi API để tạo mới phụ cấp
	$scope.newForm = function () {
	    $scope.submitButtonText = "Add";
	    $scope.errors = [];
	CategoryProductService.getNew().then(function (resp) {
	        $scope.categoryproductData = resp.data;
	        console.log($scope.categoryproductData);
	        // Xử lý sau khi tạo tuyển dụng thành công
	    }).catch(function (error) {
	        console.error('Lỗi khi tạo tuyển dụng mới:', error);
	    });
	};
	
	// Gọi API để thêm phụ cấp vào CSDL
	function add() {
	    
	    CategoryProductService.create($scope.categoryproductData).then(function (resp) {
	        // Xử lý sau khi tạo tuyển dụng thành công
	        showSuccessToast("Thêm mới thành công !");
	        setTimeout(function () {
	            $('#add_categoryproduct').modal('hide');
	        }, 2000);
	        CategoryProductService.getAll().then(function (response) {
	            $scope.categoryProducts = response.data;
	            console.log(response.data)
	            $(document).ready(function () {
	                $scope.loadDataTable($scope.categoryProducts);
	            });
	        }).catch(function (error) {
	            console.error('Lỗi khi lấy danh sách tuyển dụng:', error);
	        });
	        // $('#add_allowancetype').modal('hide')
	    }).catch(function (error) {
	        // Xử lý lỗi phản hồi từ máy chủ
	        if (error.data && error.data.errors) {
	            $scope.errors = error.data.errors;
	        }
	        if (error.data) {
	            showErrorToast(error.data.message);
	        }
	
	        console.log($scope.categoryProducts);
	        console.log($scope.errors);
	        console.log(error);
	    });
	};
	// Cập nhật tuyển dụng
	function update() {
	    CategoryProductService.update($scope.categoryproductData).then(function (resp) {
	        // Xử lý sau khi cập nhật tuyển dụng thành công
	        showSuccessToast("Cập nhật thành công !");
	        setTimeout(function () {
	            $('#add_allowancetype').modal('hide');
	        }, 3000);
	        CategoryProductService.getAll().then(function (response) {
	            $scope.categoryProducts = response.data;
	            console.log(response.data);
	            $(document).ready(function () {
	                $scope.loadDataTable($scope.categoryProducts);
	            });
	        }).catch(function (error) {
	            console.error('Lỗi khi lấy danh sách tuyển dụng:', error);
	        });
	        $('#add_allowancetype').modal('hide');
	    }).catch(function (error) {
	        // Xử lý lỗi phản hồi từ máy chủ
	        if (error.data && error.data.errors) {
	            $scope.errors = error.data.errors;
	        }
	        if (error.data) {
	            showErrorToast(error.data.message);
	        }
	console.log($scope.categoryproductData);
	        console.log($scope.errors);
	        console.error('Lỗi khi cập nhật tuyển dụng:', error);
	    });
	}
	$scope.addOrUpdate = function () {
	    if ($scope.submitButtonText === "Edit") {
	        // Gọi hàm xử lý cập nhật tuyển dụng
	        update();
	    } else {
	        // Gọi hàm xử lý thêm mới tuyển dụng
	        add();
	    }
	};
	// Toast function
	function toast({ title = "", message = "", type = "info", duration = 3000 }) {
	    const main = document.getElementById("toast");
	    if (main) {
	        const toast = document.createElement("div");
	
	        // Auto remove toast
	        const autoRemoveId = setTimeout(function () {
	            main.removeChild(toast);
	        }, duration + 1000);
	
	        // Remove toast when clicked
	        toast.onclick = function (e) {
	            if (e.target.closest(".toast__close")) {
	                main.removeChild(toast);
	                clearTimeout(autoRemoveId);
	            }
	        };
	
	        const icons = {
	            success: "fas fa-check-circle",
	            info: "fas fa-info-circle",
	            warning: "fas fa-exclamation-circle",
	            error: "fas fa-exclamation-circle"
	        };
	        const icon = icons[type];
	        const delay = (duration / 1000).toFixed(2);
	
	        toast.classList.add("toastDesign", `toast--${type}`);
			toast.style.animation = `slideInLeft ease .3s, fadeOut linear 1s ${delay}s forwards`;
	
	        toast.innerHTML = `
	                  <div class="toast__icon">
	                      <i class="${icon}"></i>
	                  </div>
	                  <div class="toast__body">
	                      <h3 class="toast__title">${title}</h3>
	                      <p class="toast__msg">${message}</p>
	                  </div>
	                  <div class="toast__close">
	                      <i class="fas fa-times"></i>
	                  </div>
	              `;
	        main.appendChild(toast);
	    }
	};
	

	// Thông báo Toast Success
	function showSuccessToast(message) {
	    var toastMessage = message || "Đã thêm nhân viên thành công.";
	    toast({
	        title: "Thành công!",
	        message: toastMessage,
	        type: "success",
	        duration: 5000
	    });
	}
	
	function showErrorToast(error) {
	    toast({
	        title: "Thất bại!",
	        message: error,
	        type: "error",
	        duration: 5000
	    });
	}
	
	$scope.clearErrors = function() {
	    // Iterate through the errors array and remove errors with matching field names
	    $scope.errors = [];
	};
})