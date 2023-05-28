package com.example.presentation.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.presentation.R
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.presentation.ui.theme.ShoppingComposeTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShoppingComposeTheme {
        MainScreen()
    }
}

/**
 * 앱에서 화면 A에서 화면 B로 이동했다가 다시 화면 A로 돌아오는 경우,
 * 이전에 어떤 화면으로 이동했었는지 알 수 있도록
 * NavController의 상태를 유지하는 것이 중요합니다.
 * 이를 위해 rememberNavController() 함수를 사용합니다.
 */
@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            MainBottomNavigationBar(navController)
        }
    ) {
        //Scaffold 본문
        MainNavigationScreen(navController = navController)
    }
}

sealed class MainNavigationItem(var route: String, var name: String) {
    object Main : MainNavigationItem("Main", "Main")
    object Category : MainNavigationItem("Category", "Category")
    object MyPage : MainNavigationItem("MyPage", "MyPage")
}

@Composable
fun MainNavigationScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = MainNavigationItem.Main.route,
        builder = {
            composable(MainNavigationItem.Main.route) {
                Text(text = "Hello Main")
            }
            composable(MainNavigationItem.Category.route) {
                Text(text = "Hello Category")
            }
            composable(MainNavigationItem.MyPage.route) {
                Text(text = "Hello MyPage")
            }
        }

    )
}

@Composable
fun MainBottomNavigationBar(navController: NavHostController) {

    val bottomNavigationItems = listOf(
        MainNavigationItem.Main,
        MainNavigationItem.Category,
        MainNavigationItem.MyPage
    )

    BottomNavigation(
        backgroundColor = Color(0xffff0000),//배경 색
        contentColor = Color(0xff00ff00)//내부 아이콘 색
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_launcher_foreground),
                        item.route
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}
