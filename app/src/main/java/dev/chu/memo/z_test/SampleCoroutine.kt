package dev.chu.memo.z_test

import dev.chu.memo.common.Const
import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.entity.User
import dev.chu.module_network.Api
import kotlinx.coroutines.*

class SampleCoroutine {
    val api = Api.createService(ApiService::class.java, Const.URL_GITHUB)

    suspend fun fetchUser2(): List<User>? {
        return withContext(Dispatchers.IO) {
            val responseListUser = api.getUserAsync()
            if(responseListUser.isSuccessful) {
                responseListUser.body()!!
            } else {
                null
            }
        }
    }
    suspend fun fetchUser(): List<User>? {
        return GlobalScope.async(Dispatchers.IO) {
            val responseListUser = api.getUserAsync()
            if(responseListUser.isSuccessful) {
                responseListUser.body()!!
            } else {
                null
            }
        }.await()
    }

    suspend fun sampleSuspend() {
        println("Kotlin Coroutines")
    }

    fun main() = runBlocking {
        println("Start")

        val x = launch {
            delay(2000)
            sampleSuspend()
            println("launch")
        }

        fetchUser()
        fetchUser2()

        println("Finish")
        x.join()
        // The sampleSuspend method does not run because the function just only fires and forgets.
        // We should use the join function, which waits for the completion.
    }

}