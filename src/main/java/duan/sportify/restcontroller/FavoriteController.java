package duan.sportify.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavoriteController {
	 private boolean isFavorite = false; // Biến trạng thái yêu thích, giả sử ban đầu là không yêu thích

	    @PostMapping("/favorite")
	    public ResponseEntity<String> addToFavorites() {
	        if (!isFavorite) {
	            // Thực hiện xử lý thêm vào yêu thích ở đây
	            isFavorite = true;
	            return ResponseEntity.ok("Yêu thích đã được thêm vào.");
	        } else {
	            return ResponseEntity.ok("Yêu thích đã tồn tại."); // Trường hợp yêu thích đã tồn tại
	        }
	    }

	    @DeleteMapping("/favorite")
	    public ResponseEntity<String> removeFromFavorites() {
	        if (isFavorite) {
	            // Thực hiện xử lý bỏ khỏi yêu thích ở đây
	            isFavorite = false;
	            return ResponseEntity.ok("Yêu thích đã được xóa.");
	        } else {
	            return ResponseEntity.ok("Yêu thích không tồn tại."); // Trường hợp yêu thích không tồn tại
	        }
	    }
}
