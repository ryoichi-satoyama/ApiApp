package jp.techacademy.ryoichi.satoyama.apiapp

interface FragmentCallback {
    fun onAddFavorite(shop: Shop)
    fun onDeleteFavorite(id: String)
}