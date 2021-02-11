package jp.techacademy.ryoichi.satoyama.apiapp

interface FragmentCallback {
//    fun onClickItem(url: String)
    fun onClickItem(url: String, shp: Shop, isFavorite: Boolean)
    fun onAddFavorite(shop: Shop)
    fun onDeleteFavorite(id: String)
}