package com.search.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.search.movies.core.data.HttpClientFactory
import com.search.movies.core.domain.Result
import com.search.movies.core.navigation.AppNavHost
import com.search.movies.movie.presentation.main.MainScreen
import com.search.movies.core.ui.theme.MyTimerTheme
import com.search.movies.movie.data.network.RemoteMovieDataSource
import com.search.movies.movie.data.network.RemoteMovieDataSourceImpl
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTimerTheme {
                AppNavHost()
            }
        }
    }
}