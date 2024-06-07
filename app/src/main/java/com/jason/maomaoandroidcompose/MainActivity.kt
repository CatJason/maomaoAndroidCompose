package com.jason.maomaoandroidcompose

import MaoMaoAndroidComposeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaoMaoAndroidComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MyApp()
                }
            }
        }
    }

    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            NavigationHost(navController, Modifier.padding(innerPadding))
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        val items = listOf("Home", "Discover", "Send", "Message", "My")
        val selectedItem = remember { mutableStateOf(0) }

        BottomNavigation(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ) {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    icon = {
                        when (item) {
                            "Home" -> Image(painterResource(id = R.drawable.home), contentDescription = null, modifier = Modifier.size(30.dp))
                            "Discover" -> Image(painterResource(id = R.drawable.discover), contentDescription = null, modifier = Modifier.size(30.dp))
                            "Send" -> Image(painterResource(id = R.drawable.send), contentDescription = null, modifier = Modifier.size(30.dp))
                            "Message" -> Image(painterResource(id = R.drawable.message), contentDescription = null, modifier = Modifier.size(30.dp))
                            "My" -> Image(painterResource(id = R.drawable.my), contentDescription = null, modifier = Modifier.size(30.dp))
                        }
                    },
                    label = { Text(item) },
                    selected = selectedItem.value == index,
                    onClick = {
                        selectedItem.value = index
                        when (item) {
                            "Home" -> navController.navigate("home")
                            "Discover" -> navController.navigate("discover")
                            "Send" -> navController.navigate("send")
                            "Message" -> navController.navigate("message")
                            "My" -> navController.navigate("my")
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
        NavHost(navController, startDestination = "home", modifier = modifier) {
            composable("home") { HomeScreen() }
            composable("discover") { ScreenContent("Discover Screen") }
            composable("send") { ScreenContent("Send Screen") }
            composable("message") { ScreenContent("Message Screen") }
            composable("my") { ScreenContent("My Screen") }
        }
    }

    @Composable
    fun HomeScreen() {
        val imageList = List(18) { index ->
            when (index % 6) {
                0 -> R.drawable.image0
                1 -> R.drawable.image1
                2 -> R.drawable.image2
                3 -> R.drawable.image3
                4 -> R.drawable.image4
                5 -> R.drawable.image5
                else -> R.drawable.image0 // Fallback case, should never happen
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(imageList) { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 8.dp)
                )
            }
        }
    }

    @Composable
    fun ScreenContent(text: String) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = text)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MaoMaoAndroidComposeTheme {
            MyApp()
        }
    }
}