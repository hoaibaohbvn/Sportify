package duan.sportify.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.dao.BookingDAO;
import duan.sportify.dao.BookingDetailDAO;
import duan.sportify.dao.ContactDAO;
import duan.sportify.dao.EventDAO;
import duan.sportify.dao.FieldDAO;
import duan.sportify.dao.OrderDAO;
import duan.sportify.dao.ProductDAO;
import duan.sportify.dao.UserDAO;
import duan.sportify.entities.Bookingdetails;
import duan.sportify.entities.Contacts;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/dashboard/")
public class DashboardRestController {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	BookingDAO bookingDAO;
	@Autowired
	FieldDAO fieldDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	EventDAO eventDAO;
	@Autowired
	ContactDAO contactDAO;
	@Autowired
	BookingDetailDAO bookingDetailDAO;
	// tổng sản phẩm
	@GetMapping("totalProduct")
	public long countProduct() {
		return productDAO.count();
	}

	// tổng sân
	@GetMapping("totalField")
	public long countField() {
		return fieldDAO.count();
	}

	// tổng người dùng
	@GetMapping("totalUser")
	public long countUser() {
		return userDAO.count();
	}

	// tổng phiếu đặt trong ngày
	@GetMapping("totalOrderBooking")
	public long sumOrderBooking() {
		return bookingDAO.sumOrderBooking();
	}

	// barcharts
	// cột a
	@GetMapping("barcharts_a")
	public List<Object[]> totalPriceOn6YearReturn() {
		return bookingDAO.getBookingPriceSummary();
	}

	// cột b
	@GetMapping("barcharts_b")
	public List<Object[]> totalOrderOn6YearReturn() {
		return orderDAO.getOrderSumary();
	}

	// linecharts
	// line a
	@GetMapping("linecharts_a")
	public List<Object[]> conutBookingOnDuring6Years() {
		return bookingDAO.countBookingOn6YearReturn();
	}

	// line b
	@GetMapping("linecharts_b")
	public List<Object[]> countOrderOnDuring6Years() {
		return orderDAO.countOrderDuring6Years();
	}

	// Tính tổng số phiếu trong tháng hiện tại và tháng trước cho bảng bookings và
	// orders
	@GetMapping("thisthatMonth")
	public List<Object[]> thisThatMonth() {
		return orderDAO.countThisMonthAndThatMonth();
	}

	// Tính tổng số doanh thu bán hàng tháng hiện tại và tháng trước cho bảng order
	@GetMapping("sumRevenueOrder2Month")
	public List<Object[]> sumRevenueOrder2Month() {
		return orderDAO.sumRevenueOrder2Month();
	}

	// đếm số hoa đơn trong ngày
	@GetMapping("countBookingInDate")
	public Integer countBookingInDate() {
		return bookingDAO.countBookingInDate();
	}

	// đếm sân dang hoạt động
	@GetMapping("countFieldActiving")
	public Integer countFieldActiving() {
		return fieldDAO.countFieldActiving();
	}

	// đếm phiếu dặt trong ngày
	@GetMapping("countOrderInDate")
	public Integer countOrderInDate() {
		return orderDAO.countOrderInDate();
	}

	// đếm sản phẩm đang bày bán
	@GetMapping("countProductActive")
	public Integer countProductActive() {
		return productDAO.countProductActive();
	}

	// thong ke booking trong ngay
	@GetMapping("thongkebookingtrongngay")
	public List<Object[]> thongkebookingtrongngay() {
		return bookingDAO.thongkebookingtrongngay();
	}

	// lấy danh sach 3 con tac trongg ngay
	@GetMapping("danhsach3contact")
	public List<Contacts> danhsach3contact() {
		return contactDAO.fill3ContactOnDate();
	}

	// dem liên hệ trong ngày
	@GetMapping("demLienHeTrongNgay")
	public int demLienHeTrongNgay() {
		return contactDAO.demLienHeTrongNgay();
	}

	// dem tổng số phiếu dat san tháng này vs thang trước
	@GetMapping("tongSoPhieuDatSan2Thang")
	public List<Object[]> tongSoPhieuDatSan2Thang() {
		return bookingDAO.tongSoPhieuDatSan2Thang();
	}

	// dem tổng số phiếu order tháng này vs thang trước
	@GetMapping("tongSoPhieuOrder2Thang")
	public List<Object[]> tongSoPhieuOrder2Thang() {
		return orderDAO.tongSoPhieuOrder2Thang();
	}

	// dem tổng dat san doanh thu tháng này vs thang trước
	@GetMapping("tongDoanhThuBooking2Month")
	public List<Object[]> tongDoanhThuBooking2Month() {
		return bookingDAO.tongDoanhThuBooking2Month();
	}

	// tổng doanh thu ban hang tháng này vs thang trước
	@GetMapping("doanhThuOrder2Month")
	public List<Object[]> doanhThuOrder2Month() {
		return orderDAO.doanhThuOrder2Month();
	}
	// top 3 san dat nhiều nhất
		@GetMapping("top3SanDatNhieu")
		public List<Object[]> top3SanDatNhieu() {
			return bookingDetailDAO.top3SanDatNhieu();
		}
	// top san pham ban nhieu nhat
	@GetMapping("top3SanPhamBanNhieu")
	public List<Object[]> top3SanPhamBanNhieu(){
		return orderDAO.top3SanPhamBanNhieu();
	}
	// top 5 user dat san nhiều nhat
	
	@GetMapping("top5UserDatSan")
	public List<Object[]> top5UserDatSan(){
		return bookingDetailDAO.top5UserDatSan();
	}
	// top 5 user mua hang nhieu nhat
	@GetMapping("top5UserOrder")
	public List<Object[]> top5UserOrder(){
		return orderDAO.top5UserOrder();
	}
	// thong ke order trong ngay thongKeOrderInDay
	@GetMapping("thongKeOrderInDay")
	public List<Object[]> thongKeOrderInDay(){
		return orderDAO.thongKeOrderInDay();
	}
}
