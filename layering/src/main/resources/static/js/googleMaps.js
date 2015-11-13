function initialize() {
	// 中心の位置座標を指定する（緯度・経度:日本, 表参道駅）
	var latlng = new google.maps.LatLng(35.6652470, 139.7123140);
	
	/* 地図のオプション設定 */
	var myOptions = {
		/*初期のズーム レベル */
		zoom : 18,
		/* 地図の中心点 */
		center : latlng,
		/* 地図タイプ */
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	
}