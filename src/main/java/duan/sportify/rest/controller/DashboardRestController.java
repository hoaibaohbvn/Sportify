package duan.sportify.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.dao.BookingDAO;
import duan.sportify.dao.EventDAO;
import duan.sportify.dao.FieldDAO;
import duan.sportify.dao.OrderDAO;
import duan.sportify.dao.ProductDAO;
import duan.sportify.dao.UserDAO;



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
	public List<Object[]> totalPriceOn6YearReturn(){
		return bookingDAO.getBookingPriceSummary();
	}
	// cột b
	@GetMapping("barcharts_b")
	public List<Object[]> totalOrderOn6YearReturn(){
		return orderDAO.getOrderSumary();
	}
	// linecharts
	// line a
	@GetMapping("linecharts_a")
	public List<Object[]> conutBookingOnDuring6Years(){
		return bookingDAO.countBookingOn6YearReturn();
	}
	// line b
	@GetMapping("linecharts_b")
	public List<Object[]> countOrderOnDuring6Years(){
		return orderDAO.countOrderDuring6Years();
	}
	//Tính tổng số phiếu trong tháng hiện tại và tháng trước cho bảng bookings và orders
	@GetMapping("thisthatMonth")
	public List<Object[]> thisThatMonth(){
		return orderDAO.countThisMonthAndThatMonth();
	}
	// Tính tổng số doanh thu dặt sân tháng hiện tại và tháng trước cho bảng bookings
	@GetMapping("sumThisThatMonth")
	public List<Object[]> sumThisThatMonth(){
		return bookingDAO.sumThisThatMonth();
	}
	//Tính tổng số doanh thu bán hàng tháng hiện tại và tháng trước cho bảng order
	@GetMapping("sumRevenueOrder2Month")
	public List<Object[]> sumRevenueOrder2Month(){
		return orderDAO.sumRevenueOrder2Month();
	}
	//  // tính tổng hoàn tiền tháng này và tháng trước
	@GetMapping("total_cancelled_amount_this_that_month")
	public List<Object[]> total_cancelled_amount_this_that_month(){
		return bookingDAO.total_cancelled_amount_this_that_month();
	}
	// số liệu thống kê booking
	@GetMapping("statisticsBooking")
	public List<Object[]> statisticsBooking(){
		return bookingDAO.statisticsBooking();
	}
	// đếm số hoa đơn trong ngày
	@GetMapping("countBookingInDate")
	public Integer countBookingInDate(){
		return bookingDAO.countBookingInDate();
	}
	// đếm sân dang hoạt động
	@GetMapping("countFieldActiving")
	public Integer countFieldActiving(){
		return fieldDAO.countFieldActiving();
	}
	
	// đếm phiếu dặt trong ngày
	@GetMapping("countOrderInDate")
	public Integer countOrderInDate(){
		return orderDAO.countOrderInDate();
	}
	// đếm sản phẩm đang bày bán
	@GetMapping("countProductActive")
	public Integer countProductActive(){
		return productDAO.countProductActive();
	}
	// thong ke booking trong ngay
		@GetMapping("thongkebookingtrongngay")
		public List<Object[]> thongkebookingtrongngay(){
			return bookingDAO.thongkebookingtrongngay();
		}
}
