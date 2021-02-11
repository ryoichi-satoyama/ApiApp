package jp.techacademy.ryoichi.satoyama.apiapp

interface FragmentCallback {
//    fun onClickItem(url: String)
    fun onClickItem(url: String, shopId: String, isFavorite: Boolean)
//    fun onClickItem(shop: Shop)
    fun onAddFavorite(shop: Shop)
    fun onDeleteFavorite(id: String)
}