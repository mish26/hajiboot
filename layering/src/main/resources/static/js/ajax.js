$(function() {

	// CSRF対策のトークンを取得
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	// トークンをヘッダーにセット
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});

	// 新規作成
	$("#create-button").click(function() {
		// 多重送信を防ぐため通信完了までボタンをdisableにする
		var button = $(this);
		button.attr("disabled", true);

		// 各フィールドから値を取得してJSONデータを作成
		var data = {
			lastName : $("#lastName").val(),
			firstName : $("#firstName").val()
		};

		$.ajax({
			type : "POST",
			url : "/customers/ajax/create",
			data : JSON.stringify(data),
			contentType : 'application/json',
			// 正常終了時
			success : function(jsonData) {
				// 最後の行に追加
				$('#table').append(getRowData(jsonData));
				alert("新規ユーザーを要録しました");
			},
			// エラー時
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("エラーが発生しました:" + textStatus + ":\n" + errorThrown);
			},
			complete : function() {
				button.attr("disabled", false);
			}
		});
	})
	

	
});


function getRowData(data) {
	var id = data.id;
	var lastName = data.lastName;
	var firstName = data.firstName;
	var userName = data.user.username;
	var buttonArea = '<td><form th:action="@{/customers/edit}" method="get"><input class="btn btn-default" type="submit" name="form" value="編集" /><input type="hidden" name="id" th:value="${customer.id}" /></form></td><td><form th:action="@{/customers/delete}" method="post"><input class="btn btn-danger" type="submit" name="form" value="削除" /><input type="hidden" name="id" th:value="${customer.id}" /></form></td>';
	return '<tr><td>' + id + '</td><td>' + lastName + '</td><td>' + firstName
			+ '</td><td>' + userName + '</td>' + buttonArea;
}
