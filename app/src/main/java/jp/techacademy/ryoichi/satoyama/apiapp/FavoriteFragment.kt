package jp.techacademy.ryoichi.satoyama.apiapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment: Fragment() {
    private val favoriteAdapter by lazy { FavoriteAdapter(requireContext())}
    // FavoriteFragment -> MainActivity に削除を通知する
    private var fragmentCallback: FragmentCallback? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentCallback) {
            fragmentCallback = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment_favorite.xmlが反映されたViewを作成して、returnします
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ここから初期化処理を行う
        // FavoriteAdapterのお気に入り削除用のメソッドの追加を行う
        favoriteAdapter.apply {
            // Adapterの処理をそのままActivityに通知する
            onClickDeleteFavorite = {
                fragmentCallback?.onDeleteFavorite(it.id)
            }

            // Itemをクリックしたとき
//            onClickItem = {
//                fragmentCallback?.onClickItem(it)
//            }
            onClickItem = {url: String, shop: Shop, isFavorite: Boolean -> fragmentCallback?.onClickItem(url, shop, isFavorite)}
        }

        // RecyclerViewの初期化
        recyclerView.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        swipeRefreshLayout.setOnRefreshListener {
            // 引っ張った時の処理
            updateData()
        }
        updateData()
    }

    fun updateData() {
        favoriteAdapter.refresh(FavoriteShop.findAll())
        swipeRefreshLayout.isRefreshing = false
    }
}