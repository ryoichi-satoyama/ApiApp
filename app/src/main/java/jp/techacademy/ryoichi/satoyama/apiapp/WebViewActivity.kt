package jp.techacademy.ryoichi.satoyama.apiapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
//    private val isFavorite: Boolean by lazy {isFavorite}
    private var isFavorite = false
    private var shopId: String? = ""
    private lateinit var shop: Shop
    var onClickAddFavorite: ((Shop) -> Unit)? = null
    var onClickDeleteFavorite: ((Shop) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.loadUrl(intent.getStringExtra(KEY_URL).toString())
//        isFavorite = intent.getBooleanExtra("isFavorite", false)
//        shopId = intent.getStringExtra("shopId")
        shop = intent.getSerializableExtra("shop") as Shop
    }

    companion object {
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, url: String, shopId: String, isFavorite: Boolean) {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.apply {
                putExtra(KEY_URL, url)
                putExtra("isFavorite", isFavorite)
                putExtra("shopId", shopId)
            }
            activity.startActivity(intent)
        }
//        fun start(activity: Activity,shop: Shop) {
//            val intent = Intent(activity, WebViewActivity::class.java)
//            intent.apply {
//                putExtra("shop", shop)
//            }
//            activity.startActivity(intent)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_web_view, menu)

        if(menu != null) {
            val favoriteButton: MenuItem = menu.findItem(R.id.favoriteButton)
            favoriteButton.apply {
                if(isFavorite) setIcon(R.drawable.ic_star) else setIcon(R.drawable.ic_star_border)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.favoriteButton) {
//            val realm = Realm.getDefaultInstance()
//            realm.close()
            //TODO FavoriteShopのオブジェクトを作成してinsertやdeleteを実装

            item.apply {
                //Realm更新処理
                isFavorite = if(isFavorite) {
                    setIcon(R.drawable.ic_star_border)

                    //お気に入り削除処理
//                    onClickDeleteFavorite?.invoke(data)

                    false
                } else {
                    setIcon(R.drawable.ic_star)

                    //お気に入り登録処理
//                    onClickAddFavorite?.invoke(data)

                    true
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}