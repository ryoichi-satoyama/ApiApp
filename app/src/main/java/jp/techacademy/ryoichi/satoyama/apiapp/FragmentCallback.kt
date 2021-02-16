package jp.techacademy.ryoichi.satoyama.apiapp

interface FragmentCallback {
    // Itemを押したときの処理
//    fun onClickItem(url: String)
    fun onClickItem(url: String, shp: Shop, isFavorite: Boolean)
    // お気に入り追加時の処理
    fun onAddFavorite(shop: Shop)
    // お気に入り削除時の処理
    fun onDeleteFavorite(id: String)
}