package jp.techacademy.ryoichi.satoyama.apiapp

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class FavoriteShop: RealmObject() {
    @PrimaryKey
    var id: String = ""
    var imageUrl: String = ""
    var name: String = ""
    var address: String = ""
    var url: String = ""
    //TODO 論理削除用のフラグを作成

    companion object {
        //todo 論理削除　フラグでフィルターを行う
        // お気に入りのShopを全件取得
        fun findAll(): List<FavoriteShop> =
            Realm.getDefaultInstance().use {realm ->
                realm.where(FavoriteShop::class.java).findAll().let { realm.copyFromRealm(it) }
            }

        // お気に入りされているShopをidで検索して返す。お気に入りに登録されていなければnullで返す
        fun  findBy(id: String): FavoriteShop? =
            Realm.getDefaultInstance().use { realm->
                realm.where(FavoriteShop::class.java)
                    .equalTo(FavoriteShop::id.name, id)
                    .findFirst()?.let {
                        realm.copyFromRealm(it)
                    }
            }

        // お気に入り追加
        fun insert(favoriteShop: FavoriteShop) =
            Realm.getDefaultInstance().executeTransaction {
                it.insertOrUpdate(favoriteShop)
            }

        //todo 論理削除　フラグを変更
        // idでお気に入りから削除する
        fun delete(id: String): FavoriteShop? =
            Realm.getDefaultInstance().use { realm->
                realm.where(FavoriteShop::class.java)
                    .equalTo(FavoriteShop::id.name, id)
                    .findFirst()?.also { deleteShop->
                        realm.executeTransaction {
                            deleteShop.deleteFromRealm()
                    }
                 }
            }
    }
}