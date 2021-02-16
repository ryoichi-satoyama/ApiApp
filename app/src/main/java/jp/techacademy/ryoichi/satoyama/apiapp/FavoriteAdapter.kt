package jp.techacademy.ryoichi.satoyama.apiapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    // お気に入り登録したShopを格納
    private val items = mutableListOf<FavoriteShop>()
    // お気に入り画面から削除するときのコールバック（ApiFragmentへ通知するメソッド)
    var onClickDeleteFavorite: ((FavoriteShop) -> Unit)? = null
    // Itemを押したときのメソッド
//    var onClickItem: ((String) -> Unit)? = null
    var onClickItem: ((String, Shop, Boolean) -> Unit)? = null

    // 更新用のメソッド
    fun refresh(list: List<FavoriteShop>) {
        items.apply {
            clear() // items を 空にする
            addAll(list) // itemsにlistを全て追加する
        }
        notifyDataSetChanged() // recyclerViewを再描画させる
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            // ViewTypeがVIEW_TYPE_EMPTY（つまり、お気に入り登録が0件）の場合
            VIEW_TYPE_EMPTY -> EmptyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_favorite_empty,parent, false))
            // 上記以外（つまり、1件以上のお気に入りが登録されている場合
            else -> FavoriteItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_favorite, parent, false))
        }
    }
    // お気に入りが登録されているときのリスト
    class FavoriteItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val rootView: ConstraintLayout = view.findViewById(R.id.rootView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val addressTextView: TextView = view.findViewById(R.id.addressTextView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val favoriteImageView: ImageView = view.findViewById(R.id.favoriteImageView)
    }

    // お気に入り登録がまだ行われていないとき
    class EmptyViewHolder(view: View): RecyclerView.ViewHolder(view)

    // お気に入りが1件もない時に、「お気に入りはありません」を出すため
    override fun getItemCount(): Int {
        return if(items.isEmpty()) 1 else items.size
    }

    // onCreateViewHolderの第二引数はここで決める。条件によってViewTypeを返すようにすると、一つのRecyclerViewで様々なViewがある物が作れる
    override fun getItemViewType(position: Int): Int {
        return if(items.isEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is FavoriteItemViewHolder) {
            updateFavoriteItemViewHolder(holder, position)
        }
    }

    // ViewHolder内のUI部品に値などをセット
    private fun updateFavoriteItemViewHolder(holder: FavoriteItemViewHolder, position: Int) {
        val data = items[position]
        holder.apply {
            rootView.apply {
                setOnClickListener {
//                    onClickItem?.invoke(data.url)
                    var shop = Shop(CouponUrls(data.url, data.url), data.id, data.imageUrl, data.name, data.address)
                    onClickItem?.invoke(data.url, shop, true)
                }
                setBackgroundColor(ContextCompat.getColor(context,
                if(position % 2 == 0) android.R.color.white else android.R.color.darker_gray))
            }
            nameTextView.text = data.name
            addressTextView.text = data.address
            Picasso.get().load(data.imageUrl).into(imageView)
            favoriteImageView.setOnClickListener {
                onClickDeleteFavorite?.invoke(data)
                notifyItemChanged(position)
            }
        }
    }

    companion object {
        // Viewの種類を表現する定数、こちらはお気に入りのお店
        private const val VIEW_TYPE_ITEM = 0
        // Viewの種類を表現する定数、こちらはお気に入りが１件もないとき
        private const val VIEW_TYPE_EMPTY = 1
    }
}