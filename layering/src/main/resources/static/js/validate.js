function validate() {

	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;

	if (inputCheck(username) == 0 && inputCheck(password) == 0) {
		return true;
		exit;
	}

	var errDiv = document.getElementById('errDiv');
	
	if (inputCheck(username) != 0) {
		var nameErrDiv = document.createElement('div');
		nameErrDiv.classList.add('alert');
		nameErrDiv.classList.add('alert-danger');
		errDiv.insertBefore(nameErrDiv, errDiv.firstChild);
		
		if (inputCheck(username) == 1) {
			nameErrDiv.innerHTML = "ユーザー名を入力してください";
		} else {
			nameErrDiv.innerHTML = "ユーザー名は半角英数字で入力してください";
		}
	}

	if (inputCheck(password) != 0) {
		var passErrDiv = document.createElement('div');
		passErrDiv.classList.add('alert');
		passErrDiv.classList.add('alert-danger');
		errDiv.insertBefore(passErrDiv, errDiv.lastChild);
		// 未入力
		if (inputCheck(password) == 1) {
			passErrDiv.innerHTML = "パスワードを入力してください";
		} else {
			passErrDiv.innerHTML = "パスワードは半角英数字で入力してください";
		}

		return false;
	}
}

// 入力チェック
function inputCheck(val) {
	if (val == "" || val == null) {
		return 1;

		// 半角英数字以外の文字が存在する場合、エラー
	} else if (val.match(/[^A-Za-z0-9]+/)) {
		return 2;

	} else {
		return 0;
	}
}
