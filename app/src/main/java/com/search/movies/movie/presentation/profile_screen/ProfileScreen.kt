package com.search.movies.movie.presentation.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.search.movies.core.ui.theme.Pink80
import com.search.movies.core.ui.theme.Purple40
import com.search.movies.core.ui.theme.Purple80

@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 42.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = Pink80)
            ) {
                Text(
                    text = "Name profile",
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = Pink80)
            ) {
                Text(
                    text = "Phone number",
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }
            Spacer(Modifier.height(8.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = Purple40)
            ) {
                Text(
                    text = "Get premium",
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}