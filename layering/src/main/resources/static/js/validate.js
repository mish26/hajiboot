$(function() {
	$("form").submit(
			function() {
				if ($("input[name='username']").val() == "") {
					if ($("span").css("color") != "red") {
						$("input[name='username']").css("border", "1px solod red").before("<span>ユーザー名を入力してください</span>");
						$("span").css("color", "red");
					}
					return false;
				}
			})
			
			$("form").submit(
			function() {
				if ($("input[name='password']").val() == "") {
					if ($("span").css("color") != "red") {
						$("input[name='password']").css("border", "1px solod red").before("<span>パスワードを入力してください</span>");
						$("span").css("color", "red");
					}
					return false;
				}
			})
		
});

