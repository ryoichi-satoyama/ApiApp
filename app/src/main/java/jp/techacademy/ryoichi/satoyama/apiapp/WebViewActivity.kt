package jp.techacademy.ryoichi.satoyama.apiapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
//    private val isFavorite: Boolean by lazy {isFavorite}
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.loadUrl(intent.getStringExtra(KEY_URL).toString())
        isFavorite = intent.getBooleanExtra("isFavorite", false)
    }

    companion object {
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, url: String) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, url))
        }
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
            item.apply {
                //Realm更新処理
                isFavorite = if(isFavorite) {
                    setIcon(R.drawable.ic_star_border)

                    //お気に入り削除処理

                    false
                } else {
                    setIcon(R.drawable.ic_star)

                    //お気に入り登録処理
                    
                    true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}