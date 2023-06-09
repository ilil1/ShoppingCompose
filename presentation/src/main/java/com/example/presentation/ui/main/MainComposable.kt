package com.example.presentation.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.ModelType
import com.example.domain.model.Product
import com.example.presentation.R
import com.example.presentation.ui.component.BannerCard
import com.example.presentation.ui.component.BannerListCard
import com.example.presentation.ui.component.ProductCard
import com.example.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainInsideScreen(viewModel: MainViewModel) {

    val modelList by viewModel.modelList.collectAsState(initial = listOf())
    val columnCount by viewModel.columnCount.collectAsState()

    LazyVerticalGrid(cells = GridCells.Fixed(columnCount)) {
        items(modelList.size, span = { index ->
            val item = modelList[index]
            val spanCount = getSpanCountByType(item.type, columnCount)
            GridItemSpan(spanCount)
        }) {
            val item = modelList[it]
            when(item.type) {
                ModelType.BANNER -> {
                    BannerCard(model = item as Banner)
                }
                ModelType.PRODUCT -> {
                    ProductCard(product = item as Product) {
                    }
                }
                ModelType.BANNER_LIST -> {
                    BannerListCard(model = item as BannerList)
                }

            }
        }
    }
//    LazyColumn {
//        items(productList.size) {
//            ProductCard(product = productList[it]) {
//
//            }
//        }
//    }
}

private fun getSpanCountByType(type : ModelType, defaultColumnCount : Int) : Int {
    return when(type) {
        ModelType.PRODUCT -> 1
        ModelType.BANNER, ModelType.BANNER_LIST-> defaultColumnCount
    }
}

