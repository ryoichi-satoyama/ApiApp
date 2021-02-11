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
        fun findAll(): List<FavoriteShop> =
            Realm.getDefaultInstance().use {realm ->
                realm.where(FavoriteShop::class.java).findAll().let { realm.copyFromRealm(it) }
            }

        fun  findBy(id: String): FavoriteShop? =
            Realm.getDefaultInstance().use { realm->
                realm.where(FavoriteShop::class.java)
                    .equalTo(FavoriteShop::id.name, id)
                    .findFirst()?.let {
                        realm.copyFromRealm(it)
                    }
            }

        fun insert(favoriteShop: FavoriteShop) =
            Realm.getDefaultInstance().executeTransaction {
                it.insertOrUpdate(favoriteShop)
            }

        //todo 論理削除　フラグを変更
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