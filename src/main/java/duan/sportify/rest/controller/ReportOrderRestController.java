package duan.sportify.rest.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.dao.BookingDAO;
import duan.sportify.dao.OrderDAO;
import duan.sportify.entities.Bookings;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/reportOrder/")
public class ReportOrderRestController {
	@Autowired
	OrderDAO orderDAO;

	// rp doanh thu báng hàng trong tháng
	@GetMapping("rpDoanhThuOrderTrongThang")
	public List<Object[]> rpDoanhThuOrderTrongThang(@RequestParam("year") String year,
			@RequestParam("month") String month) {
		return orderDAO.rpDoanhThuOrderTrongThang(year, month);
	}

	// rp doanh thu ban hàng trong nam
	@GetMapping("rpDoanhThuOrderTrongNam")
	public List<Object[]> rpDoanhThuOrderTrongNam(@RequestParam("year") String year) {
		return orderDAO.rpDoanhThuOrderTrongNam(year);
	}

	// rp so luong phieu dat hang trong thang rpSoLuongBookingTrongThang
	@GetMapping("rpSoLuongOrderTrongThang")
	public List<Object[]> rpSoLuongOrderTrongThang(@RequestParam("year") String year,
			@RequestParam("month") String month) {
		return orderDAO.rpSoLuongOrderTrongThang(year, month);
	}

	// rp so luong phieu dat hang trong nam rpSoLuongBookingTrongNam
	@GetMapping("rpSoLuongOrderTrongNam")
	public List<Object[]> rpSoLuongOrderTrongNam(@RequestParam("year") String year) {
		return orderDAO.rpSoLuongOrderTrongNam(year);
	}

	// lấy năm của phiếu dắt
	@GetMapping("getYearOrder")
	public List<Object[]> getYearOrder() {
		return orderDAO.getYearOrder();
	}

	// xuất excel
	// excel Doanh thu đạt hang trong năm
	@GetMapping("/downloadExcelDTOrderNam")
	public void downloadExcelDTOrderNam(HttpServletResponse response, @RequestParam("year") String year) {
	    try {
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Report doanh thu đặt hàng trong năm " + year);
	        LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        String formattedDate = currentDate.format(formatter);

	        List<Object[]> dataList = orderDAO.rpDoanhThuOrderTrongNam(year);
	        int dataRowIndex = 5; // Start from row 5 after the headers

	        Map<Integer, Object[]> data = new TreeMap<>();
	        data.put(1, new Object[] { "Danh Sách Thông Tin Doanh Thu Phiếu Đặt Hàng Trong Năm " + year });
	        data.put(2, new Object[] { "Ngày tạo:", formattedDate });
	        data.put(3, new Object[] {});
	        data.put(4, new Object[] { "STT", "Tháng", "Tiền đang đợi thanh toán", "Tiền đã thanh toán",
	                "Doanh thu ước tính", "Doanh thu thực tế" });

	        for (int i = 0; i < dataList.size(); i++) {
	            Object[] rpData = dataList.get(i);

	            String thang = String.valueOf(rpData[0]);
	            String huy = String.valueOf(rpData[2]);
	            String done = String.valueOf(rpData[3]);
	            String thucte = String.valueOf(rpData[1]);
	            String uoctinh = String.valueOf(rpData[4]);

	            data.put(dataRowIndex, new Object[] { dataRowIndex - 4, thang, huy, done, uoctinh, thucte });
	            dataRowIndex++;
	        }

	        int lastDataRowIndex = dataRowIndex - 1;
	        int startRow = lastDataRowIndex + 2;

	        // Thêm dữ liệu vào trang tính (sheet)
	        for (Map.Entry<Integer, Object[]> entry : data.entrySet()) {
	            Integer rowNum = entry.getKey();
	            Object[] rowData = entry.getValue();

	            Row row = sheet.createRow(rowNum);

	            for (int i = 0; i < rowData.length; i++) {
	                Cell cell = row.createCell(i);
	                Object value = rowData[i];

	                if (value == null) {
	                    cell.setCellValue("");
	                } else if (value instanceof String) {
	                    cell.setCellValue((String) value);
	                } else if (value instanceof Integer) {
	                    cell.setCellValue((Integer) value);
	                } else if (value instanceof LocalDate) {
	                    cell.setCellValue(java.sql.Date.valueOf((LocalDate) value));
	                    CellStyle dateStyle = workbook.createCellStyle();
	                    dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));
	                    cell.setCellStyle(dateStyle);
	                }
	            }
	        }

	        // Điều chỉnh chiều rộng cột
	        sheet.setColumnWidth(0, 4000); // Chiều rộng cột A (STT)
	        sheet.setColumnWidth(1, 9000); // Chiều rộng cột B
	        sheet.setColumnWidth(2, 9000); // Chiều rộng cột C
	        sheet.setColumnWidth(3, 9000); // Chiều rộng cột D
	        sheet.setColumnWidth(4, 9000); // Chiều rộng cột E
	        sheet.setColumnWidth(5, 9000); // Chiều rộng cột F

	        for (int i = 0; i <= lastDataRowIndex + 1; i++) {
	            Row row = sheet.getRow(i);
	            if (row != null) {
	                for (int j = 0; j <= 9; j++) {
	                    Cell cell = row.getCell(j);
	                    if (cell != null) {
	                        XSSFCellStyle cellStyle = (XSSFCellStyle) cell.getCellStyle();
	                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
	                        cell.setCellStyle(cellStyle);
	                    }
	                }
	            }
	        }

	        // Áp dụng kiểu cho hàng đầu và dòng dữ liệu
	        XSSFCellStyle headerStyle = workbook.createCellStyle();
	        Font headerFont = workbook.createFont();
	        headerStyle.setFont(headerFont);

	        XSSFCellStyle dataStyle = workbook.createCellStyle();
	        dataStyle.setWrapText(true);

	        // Áp dụng kiểu dữ liệu số tiền tệ Việt Nam (VND)
	        CellStyle currencyStyle = workbook.createCellStyle();
	        currencyStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#,##0 VNĐ"));

	        for (int i = 5; i <= lastDataRowIndex; i++) {
	            Row row = sheet.getRow(i);
	            if (row != null) {
	                Cell cell = row.getCell(3); // Cột huy
	                if (cell != null) {
	                    cell.setCellValue(Double.parseDouble(cell.getStringCellValue())); // Chuyển đổi giá trị thành số
	                    cell.setCellStyle(currencyStyle); // Áp dụng kiểu dữ liệu số tiền tệ
	                }

	                cell = row.getCell(4); // Cột done
	                if (cell != null) {
	                    cell.setCellValue(Double.parseDouble(cell.getStringCellValue())); // Chuyển đổi giá trị thành số
	                    cell.setCellStyle(currencyStyle); // Áp dụng kiểu dữ liệu số tiền tệ
	                }

	                cell = row.getCell(2); // Cột thucte
	                if (cell != null) {
	                    cell.setCellValue(Double.parseDouble(cell.getStringCellValue())); // Chuyển đổi giá trị thành số
	                    cell.setCellStyle(currencyStyle); // Áp dụng kiểu dữ liệu số tiền tệ
	                }

	                cell = row.getCell(5); // Cột uoctinh
	                if (cell != null) {
	                    cell.setCellValue(Double.parseDouble(cell.getStringCellValue())); // Chuyển đổi giá trị thành số
	                    cell.setCellStyle(currencyStyle); // Áp dụng kiểu dữ liệu số tiền tệ
	                }
	            }
	        }

	        // Ghi workbook vào luồng HttpServletResponse output
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=ReportNhanVien.xlsx");

	        try (ServletOutputStream out = response.getOutputStream()) {
	            workbook.write(out);
	            out.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}


	// excel Doanh thu đạt hang trong thang
	@GetMapping("/downloadExcelDTOrderThang")
	public void downloadExcelDTOrderThang(HttpServletResponse response, @RequestParam("year") String year,
			@RequestParam("month") String month) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Report doanh thu đặt hàng trong tháng " + month + " năm " + year);
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String formattedDate = currentDate.format(formatter);

			List<Object[]> dataList = orderDAO.rpDoanhThuOrderTrongThang(year, month);
			int dataRowIndex = 5; // Start from row 5 after the headers

			Map<Integer, Object[]> data = new TreeMap<>();
			data.put(1, new Object[] {
					"Danh Sách Thông Tin Doanh Thu Phiếu Đặt Sân Trong Tháng " + month + "năm " + year });
			data.put(2, new Object[] { "Ngày tạo:", formattedDate });
			data.put(3, new Object[] {});
			data.put(4, new Object[] { "STT", "Ngày", "Tiền đang đợi thanh toán", "Tiền đã thanh toán",
					"Doanh thu ước tính", "Doanh thu thực tế" });

			for (int i = 0; i < dataList.size(); i++) {
				Object[] rpData = dataList.get(i);

				String thang = String.valueOf(rpData[0]);
				String huy = String.valueOf(rpData[2]);
				String done = String.valueOf(rpData[3]);
				String thucte = String.valueOf(rpData[1]);
				String uoctinh = String.valueOf(rpData[4]);

				data.put(dataRowIndex, new Object[] { dataRowIndex - 4, thang, huy, done, uoctinh, thucte });
				dataRowIndex++;
			}

			int lastDataRowIndex = dataRowIndex - 1;
			int startRow = lastDataRowIndex + 2;

			// Thêm dữ liệu vào trang tính (sheet)
			for (Map.Entry<Integer, Object[]> entry : data.entrySet()) {
				Integer rowNum = entry.getKey();
				Object[] rowData = entry.getValue();

				Row row = sheet.createRow(rowNum);

				for (int i = 0; i < rowData.length; i++) {
					Cell cell = row.createCell(i);
					Object value = rowData[i];

					if (value == null) {
						cell.setCellValue("");
					} else if (value instanceof String) {
						cell.setCellValue((String) value);
					} else if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					} else if (value instanceof LocalDate) {
						cell.setCellValue(java.sql.Date.valueOf((LocalDate) value));
						CellStyle dateStyle = workbook.createCellStyle();
						dateStyle
								.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));
						cell.setCellStyle(dateStyle);
					}

				}
			}

			// Điều chỉnh chiều rộng cột
			sheet.setColumnWidth(0, 4000); // Chiều rộng cột A (STT) được đặt là 2000 đơn vị 1/256 của độ rộng ký tự
			sheet.setColumnWidth(1, 9000); // Chiều rộng cột B
			sheet.setColumnWidth(2, 9000); // Chiều rộng cột C
			sheet.setColumnWidth(3, 9000); // Chiều rộng cột D
			sheet.setColumnWidth(4, 9000); // Chiều rộng cột E
			sheet.setColumnWidth(5, 9000); // Chiều rộng cột F

			for (int i = 0; i <= lastDataRowIndex + 1; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					for (int j = 0; j <= 9; j++) {
						Cell cell = row.getCell(j);
						if (cell != null) {
							XSSFCellStyle cellStyle = (XSSFCellStyle) cell.getCellStyle();
							cellStyle.setAlignment(HorizontalAlignment.CENTER);
							cell.setCellStyle(cellStyle);
						}
					}
				}
			}
			// Định dạng số tiền tệ Việt Nam (VND)
			CellStyle currencyStyle = workbook.createCellStyle();
			currencyStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#,##0 VNĐ"));

			for (int i = 5; i <= lastDataRowIndex; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					Cell cell = row.getCell(3); // Cột huy
					if (cell != null) {
						cell.setCellValue(Double.parseDouble(cell.getStringCellValue())); // Chuyển đổi giá trị thành số
						cell.setCellStyle(currencyStyle); // Áp dụng kiểu dữ liệu số tiền tệ
					}

					cell = row.getCell(4); // Cột done
					if (cell != null) {
						cell.setCellValue(Double.parseDouble(cell.getStringCellValue())); // Chuyển đổi giá trị thành số
						cell.setCellStyle(currencyStyle); // Áp dụng kiểu dữ liệu số tiền tệ
					}

					cell = row.getCell(2); // Cột thucte
					if (cell != null) {
						cell.setCellValue(Double.parseDouble(cell.getStringCellValue())); // Chuyển đổi giá trị thành số
						cell.setCellStyle(currencyStyle); // Áp dụng kiểu dữ liệu số tiền tệ
					}

					cell = row.getCell(5); // Cột uoctinh
					if (cell != null) {
						cell.setCellValue(Double.parseDouble(cell.getStringCellValue())); // Chuyển đổi giá trị thành số
						cell.setCellStyle(currencyStyle); // Áp dụng kiểu dữ liệu số tiền tệ
					}
				}
			}
			// Áp dụng kiểu cho hàng đầu và dòng dữ liệu
			XSSFCellStyle headerStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerStyle.setFont(headerFont);

			XSSFCellStyle dataStyle = workbook.createCellStyle();
			dataStyle.setWrapText(true);

			// Ghi workbook vào luồng HttpServletResponse output
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=ReportNhanVien.xlsx");

			try (ServletOutputStream out = response.getOutputStream()) {
				workbook.write(out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	// excel số lượng đạt hang trong năm
	@GetMapping("/downloadExcelSLOrderNam")
	public void downloadExcelSLOrderNam(HttpServletResponse response, @RequestParam("year") String year) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Report số lượng đặt hàng trong năm " + year);
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String formattedDate = currentDate.format(formatter);

			List<Object[]> dataList = orderDAO.rpSoLuongOrderTrongNam(year);
			int dataRowIndex = 5; // Start from row 5 after the headers

			Map<Integer, Object[]> data = new TreeMap<>();
			data.put(1, new Object[] { "Danh Sách Thông Tin Số Lượng Phiếu Đặt Hàng Trong Năm " + year });
			data.put(2, new Object[] { "Ngày tạo:", formattedDate });
			data.put(3, new Object[] {});
			data.put(4, new Object[] { "STT", "Tháng", "Số lượng phiếu chưa thanh toán", "Số lượng phiếu đã thanh toán",
					"Tổng số lượng phiếu" });

			for (int i = 0; i < dataList.size(); i++) {
				Object[] rpData = dataList.get(i);

				String thang = String.valueOf(rpData[0]);
				String huy = String.valueOf(rpData[2]);
				String done = String.valueOf(rpData[3]);
				String tong = String.valueOf(rpData[1]);

				data.put(dataRowIndex, new Object[] { dataRowIndex - 4, thang, huy, done, tong });
				dataRowIndex++;
			}

			int lastDataRowIndex = dataRowIndex - 1;
			int startRow = lastDataRowIndex + 2;

			// Thêm dữ liệu vào trang tính (sheet)
			for (Map.Entry<Integer, Object[]> entry : data.entrySet()) {
				Integer rowNum = entry.getKey();
				Object[] rowData = entry.getValue();

				Row row = sheet.createRow(rowNum);

				for (int i = 0; i < rowData.length; i++) {
					Cell cell = row.createCell(i);
					Object value = rowData[i];

					if (value == null) {
						cell.setCellValue("");
					} else if (value instanceof String) {
						cell.setCellValue((String) value);
					} else if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					} else if (value instanceof LocalDate) {
						cell.setCellValue(java.sql.Date.valueOf((LocalDate) value));
						CellStyle dateStyle = workbook.createCellStyle();
						dateStyle
								.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));
						cell.setCellStyle(dateStyle);
					}

				}
			}

			// Điều chỉnh chiều rộng cột
			sheet.setColumnWidth(0, 4000); // Chiều rộng cột A (STT) được đặt là 2000 đơn vị 1/256 của độ rộng ký tự
			sheet.setColumnWidth(1, 9000); // Chiều rộng cột B
			sheet.setColumnWidth(2, 9000); // Chiều rộng cột C
			sheet.setColumnWidth(3, 9000); // Chiều rộng cột D
			sheet.setColumnWidth(4, 9000); // Chiều rộng cột E

			for (int i = 0; i <= lastDataRowIndex + 1; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					for (int j = 0; j <= 5; j++) {
						Cell cell = row.getCell(j);
						if (cell != null) {
							XSSFCellStyle cellStyle = (XSSFCellStyle) cell.getCellStyle();
							cellStyle.setAlignment(HorizontalAlignment.CENTER);
							cell.setCellStyle(cellStyle);
						}
					}
				}
			}
			// chỉnh dữ liệu
			// Tạo một đối tượng kiểu dữ liệu số
			CellStyle numberCellStyle = workbook.createCellStyle();
			numberCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#,##0"));

			for (int i = 5; i <= lastDataRowIndex; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					Cell cellHuy = row.getCell(3); // Cột huy
					if (cellHuy != null) {
						cellHuy.setCellValue(Double.parseDouble(cellHuy.getStringCellValue())); // Chuyển đổi giá trị
																								// thành số
						cellHuy.setCellStyle(numberCellStyle); // Áp dụng kiểu dữ liệu số
					}

					Cell cellDone = row.getCell(4); // Cột done
					if (cellDone != null) {
						cellDone.setCellValue(Double.parseDouble(cellDone.getStringCellValue())); // Chuyển đổi giá trị
																									// thành số
						cellDone.setCellStyle(numberCellStyle); // Áp dụng kiểu dữ liệu số
					}

					Cell cellTong = row.getCell(2); // Cột tong
					if (cellTong != null) {
						cellTong.setCellValue(Double.parseDouble(cellTong.getStringCellValue())); // Chuyển đổi giá trị
																									// thành số
						cellTong.setCellStyle(numberCellStyle); // Áp dụng kiểu dữ liệu số
					}
				}
			}

			// Áp dụng kiểu cho hàng đầu và dòng dữ liệu
			XSSFCellStyle headerStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerStyle.setFont(headerFont);

			XSSFCellStyle dataStyle = workbook.createCellStyle();
			dataStyle.setWrapText(true);

			// Ghi workbook vào luồng HttpServletResponse output
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=ReportNhanVien.xlsx");

			try (ServletOutputStream out = response.getOutputStream()) {
				workbook.write(out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	// excel số lượng đạt san trong năm
	@GetMapping("/downloadExcelSLOrderThang")
	public void downloadExcelSLOrderThang(HttpServletResponse response, @RequestParam("year") String year,
			@RequestParam("month") String month) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Report số lượng đặt hàng trong tháng " + month + " năm " + year);
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String formattedDate = currentDate.format(formatter);

			List<Object[]> dataList = orderDAO.rpSoLuongOrderTrongThang(year, month);
			int dataRowIndex = 5; // Start from row 5 after the headers

			Map<Integer, Object[]> data = new TreeMap<>();
			data.put(1, new Object[] {
					"Danh Sách Thông Tin Số Lượng Phiếu Đặt Sân Trong Tháng " + month + " Năm " + year });
			data.put(2, new Object[] { "Ngày tạo:", formattedDate });
			data.put(3, new Object[] {});
			data.put(4, new Object[] { "STT", "Tháng", "Số lượng phiếu chưa thanh toán", "Số lượng phiếu đã thanh toán",
					"Tổng số lượng phiếu" });

			for (int i = 0; i < dataList.size(); i++) {
				Object[] rpData = dataList.get(i);

				String thang = String.valueOf(rpData[0]);
				String huy = String.valueOf(rpData[2]);
				String done = String.valueOf(rpData[3]);
				String tong = String.valueOf(rpData[1]);

				data.put(dataRowIndex, new Object[] { dataRowIndex - 4, thang, huy, done, tong });
				dataRowIndex++;
			}

			int lastDataRowIndex = dataRowIndex - 1;
			int startRow = lastDataRowIndex + 2;

			// Thêm dữ liệu vào trang tính (sheet)
			for (Map.Entry<Integer, Object[]> entry : data.entrySet()) {
				Integer rowNum = entry.getKey();
				Object[] rowData = entry.getValue();

				Row row = sheet.createRow(rowNum);

				for (int i = 0; i < rowData.length; i++) {
					Cell cell = row.createCell(i);
					Object value = rowData[i];

					if (value == null) {
						cell.setCellValue("");
					} else if (value instanceof String) {
						cell.setCellValue((String) value);
					} else if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					} else if (value instanceof LocalDate) {
						cell.setCellValue(java.sql.Date.valueOf((LocalDate) value));
						CellStyle dateStyle = workbook.createCellStyle();
						dateStyle
								.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));
						cell.setCellStyle(dateStyle);
					}

				}
			}

			// Điều chỉnh chiều rộng cột
			sheet.setColumnWidth(0, 4000); // Chiều rộng cột A (STT) được đặt là 2000 đơn vị 1/256 của độ rộng ký tự
			sheet.setColumnWidth(1, 9000); // Chiều rộng cột B
			sheet.setColumnWidth(2, 9000); // Chiều rộng cột C
			sheet.setColumnWidth(3, 9000); // Chiều rộng cột D
			sheet.setColumnWidth(4, 9000); // Chiều rộng cột E

			for (int i = 0; i <= lastDataRowIndex + 1; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					for (int j = 0; j <= 5; j++) {
						Cell cell = row.getCell(j);
						if (cell != null) {
							XSSFCellStyle cellStyle = (XSSFCellStyle) cell.getCellStyle();
							cellStyle.setAlignment(HorizontalAlignment.CENTER);
							cell.setCellStyle(cellStyle);
						}
					}
				}
			}
			// chỉnh dữ liệu
			// Tạo một đối tượng kiểu dữ liệu số
			CellStyle numberCellStyle = workbook.createCellStyle();
			numberCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#,##0"));

			for (int i = 5; i <= lastDataRowIndex; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					Cell cellHuy = row.getCell(3); // Cột huy
					if (cellHuy != null) {
						cellHuy.setCellValue(Double.parseDouble(cellHuy.getStringCellValue())); // Chuyển đổi giá trị
																								// thành số
						cellHuy.setCellStyle(numberCellStyle); // Áp dụng kiểu dữ liệu số
					}

					Cell cellDone = row.getCell(4); // Cột done
					if (cellDone != null) {
						cellDone.setCellValue(Double.parseDouble(cellDone.getStringCellValue())); // Chuyển đổi giá trị
																									// thành số
						cellDone.setCellStyle(numberCellStyle); // Áp dụng kiểu dữ liệu số
					}

					Cell cellTong = row.getCell(2); // Cột tong
					if (cellTong != null) {
						cellTong.setCellValue(Double.parseDouble(cellTong.getStringCellValue())); // Chuyển đổi giá trị
																									// thành số
						cellTong.setCellStyle(numberCellStyle); // Áp dụng kiểu dữ liệu số
					}
				}
			}

			// Áp dụng kiểu cho hàng đầu và dòng dữ liệu
			XSSFCellStyle headerStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerStyle.setFont(headerFont);

			XSSFCellStyle dataStyle = workbook.createCellStyle();
			dataStyle.setWrapText(true);

			// Ghi workbook vào luồng HttpServletResponse output
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=ReportNhanVien.xlsx");

			try (ServletOutputStream out = response.getOutputStream()) {
				workbook.write(out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
