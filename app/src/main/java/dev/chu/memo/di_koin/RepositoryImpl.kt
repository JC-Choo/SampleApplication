package dev.chu.memo.di_koin

class RepositoryImpl : Repository {
    override fun getMyData(): String {
        return "Hello Koin"
    }
}