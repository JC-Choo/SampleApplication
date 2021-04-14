package dev.chu.mvvmarchitecture

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.chu.mvvmarchitecture.api.Api
import dev.chu.mvvmarchitecture.data.MainRepository
import dev.chu.mvvmarchitecture.data.local.CatDatabase
import dev.chu.mvvmarchitecture.data.local.LocalDataSource
import dev.chu.mvvmarchitecture.data.remote.RemoteDataSource
import dev.chu.mvvmarchitecture.ui.MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("dev.chu.mvvmarchitecture", appContext.packageName)
//    }
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: CatDatabase
    private lateinit var localDataSource: LocalDataSource
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var repository: MainRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = CatDatabase.getInstance(appContext)
        localDataSource = LocalDataSource(database)
        remoteDataSource = RemoteDataSource(Api.createApi())
        repository = MainRepository(localDataSource, remoteDataSource)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun fetchData() {

        runBlocking {
            viewModel.fetchData { list ->
                list?.let {
                    viewModel.insert(it)
                }
            }

            viewModel.getAllCats().value?.let {
                assertTrue(it.isEmpty())
            }
        }
    }
}