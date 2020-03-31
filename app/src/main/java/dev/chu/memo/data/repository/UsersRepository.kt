package dev.chu.memo.data.repository

import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.entity.User
import dev.chu.memo.ui.mvi.etc.LCE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UsersRepository(private val api: ApiService) {

    suspend fun getUsersFromServer(): LCE<List<User>> {
        val response = api.getUserAsync()

        return if(response.isSuccessful) {
            if(response.body()!!.isNotEmpty())
                LCE.Success(data = response.body()!!)
            else
                LCE.Error("No result found")
        } else {
            LCE.Error("Failed to get Nes")
        }
    }

    fun getUsersFromServerByRx(callback: (LCE<List<User>>) -> Unit) =
        api.getUserAsyncRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.isNotEmpty())
                    callback(LCE.Success(data = it))
                else
                    callback(LCE.Error("No result found"))
            }, {
                callback(LCE.Error("Failed to get Nes"))
                it.printStackTrace()
            })
}
