package com.example.presentation.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.presentation.ui.main.MainInsideScreen
import com.example.presentation.ui.theme.ShoppingComposeTheme
import com.example.presentation.viewmodel.MainViewModel

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShoppingComposeTheme {
        MainScreen()
    }
}


sealed class MainNavigationItem(var route: String, val icon: ImageVector, var name: String) {
    object Main : MainNavigationItem("Main", Icons.Filled.Home, "Main")
    object Category : MainNavigationItem("Category", Icons.Filled.Star, "Category")
    object MyPage : MainNavigationItem("MyPage", Icons.Filled.AccountBox, "MyPage")
}

/**
 * 앱에서 화면 A에서 화면 B로 이동했다가 다시 화면 A로 돌아오는 경우,
 * 이전에 어떤 화면으로 이동했었는지 알 수 있도록
 * NavController의 상태를 유지하는 것이 중요합니다.
 * 이를 위해 rememberNavController() 함수를 사용합니다.
 */
@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    Scaffold(
        topBar = { Header(viewModel) },
        bottomBar = {
            MainBottomNavigationBar(navController)
        }
    ) {
        //Scaffold 본문
        MainNavigationScreen(viewModel = viewModel, navController = navController)
    }
}

/**
 * TopAppBar는 일반적으로 세 가지 주요 컴포넌트로 구성됩니다:
 * navigationIcon: 왼쪽에 표시되는 아이콘. 주로 드로어 메뉴나 뒤로 가기 버튼이 위치합니다.
 * title: 중앙에 표시되는 텍스트.
 * actions: 오른쪽에 표시되는 아이콘들.
 * 이 부분은 일반적으로 검색, 공유, 설정 등의 액션을 수행하는 아이콘들로 구성됩니다.
 */

@Composable
fun Header(viewModel: MainViewModel) {
    TopAppBar(
        title = { Text("My App") },
        actions = {
            IconButton(onClick = {
                viewModel.openSearchForm()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "SearchIcon"
                )
            }
        }
    )
}

@Composable
fun MainNavigationScreen(viewModel: MainViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = MainNavigationItem.Main.route,
        builder = {
            composable(MainNavigationItem.Main.route) {
                MainInsideScreen(viewModel)
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
                        item.icon,
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
