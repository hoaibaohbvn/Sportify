<script>
    function favorite() {
        $.ajax({
            url: '/favorite',
            type: 'POST',
            success: function(response) {
                console.log(response); // Xử lý phản hồi từ server
                // ...
            },
            error: function(error) {
                console.error(error); // Xử lý lỗi nếu có
                // ...
            }
        })}
    

    function unfavorite() {
        $.ajax({
            url: '/favorite',
            type: 'DELETE',
            success: function(response) {
                console.log(response); // Xử lý phản hồi từ server
                // ...
            },
            error: function(error) {
                console.error(error); // Xử lý lỗi nếu có
                // ...
            }
        })
    }
</script>